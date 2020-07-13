package com.cts.fms.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fms.config.ApplicationProperties;
import com.cts.fms.domain.EventInformation;
import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.User;
import com.cts.fms.repository.EventInformationRepository;
import com.cts.fms.repository.EventUserInfoRepository;
import com.cts.fms.service.dto.EventInformationDTO;
import com.cts.fms.service.util.FmsUtil;

@Named
public class EventServiceImpl implements EventService {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());


	@Inject
	private EventInformationRepository eventInformationRepository;

	@Inject
	private EventUserInfoRepository eventUserInfoRepository;

	@Inject
	private MailService mailService;

	@Inject
	Environment env;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserService userService;
	
	@Inject
	private RoleService roleService;
	
	@Inject
	private ApplicationProperties applicationProperties;

	@Override
	public List<EventInformation> getEvents(String userId, Pageable pageable) {
		User user = userService.findByEmail(userId);
		if(user != null && user.getRoles().iterator().next().getName().equalsIgnoreCase("ADMIN") ) {
			return eventInformationRepository.getEventInformationList(pageable);
		}
		return eventInformationRepository.getEventInformationListByUser(userId, pageable);
	}

	@Override
	public EventInformation getEvents(String id) {
		return Optional.ofNullable(eventInformationRepository.findById(id)).get().orElse(null);
	}

	@Override
	public void sendMail(String id) {

		List<EventUserInfo> participantFeedbackStatus = null;
		List<EventUserInfo> participantFeedbackStatusNew = new ArrayList<EventUserInfo>();
		if (id != null && !(id.isEmpty())) {
			participantFeedbackStatus = eventUserInfoRepository.findByEventIdAndIsFeedbackSent(id, 0);
			;
		} else {
			participantFeedbackStatus = eventUserInfoRepository.findByIsFeedbackSent(0);
		}

		if (!participantFeedbackStatus.isEmpty()) {
			participantFeedbackStatus.parallelStream().forEach(x -> {
				mailService.sendFeedBackEmail(x.getEmpId().trim() + "@cognizant.com", id,
				 x.getEmpId());
				x.setIsFeedbackSent(1);
				participantFeedbackStatusNew.add(x);
			});
			 eventUserInfoRepository.saveAll(participantFeedbackStatusNew);
		}
	}

	@Override
	public List<EventInformation> saveAll(List<EventInformationDTO> listAllEventBatchData, String userEventInfoType) {
		Map<String, List<EventInformationDTO>> map = listAllEventBatchData.parallelStream()
				.collect(Collectors.groupingBy(x -> x.getEventId()));
		List<EventInformation> eventInformations = new ArrayList<>();
		for (String eventInformationDTO2 : map.keySet()) {
			List<EventInformationDTO> dtos = map.get(eventInformationDTO2);
			EventInformation eventInformation = mapEventInformationDTOtoEventInformation(dtos.get(0));
			eventInformation
					.setEventUserInfo(mapEventInformationDTOtoEventUserInfo(dtos, eventInformation, userEventInfoType));
			eventInformations.add(eventInformation);
		}
		return eventInformationRepository.saveAll(eventInformations);
	}

	private EventInformation mapEventInformationDTOtoEventInformation(EventInformationDTO dto) {
		EventInformation eventInformation = new EventInformation();
		eventInformation.setEventId(dto.getEventId());
		eventInformation.setBaseLocation(dto.getBaseLocation());
		eventInformation.setBeneficiaryName(dto.getBeneficiaryName());
		eventInformation.setCouncilName(dto.getCouncilName());
		eventInformation.setEventName(dto.getEventName());
		eventInformation.setEventDescription(dto.getEventDescription());
		eventInformation.setEventDate(dto.getEventDate());
		eventInformation.setStatus(dto.getStatus());
		eventInformation.setIIEPCategory(dto.getiIEPCategory());
		eventInformation.setLivesImpacted(Float.valueOf(dto.getLivesImpacted()));
		return eventInformation;
	}

	private Set<EventUserInfo> mapEventInformationDTOtoEventUserInfo(List<EventInformationDTO> eventDTOList,
			EventInformation eventInformation, String userStatus) {
		Set<EventUserInfo> eventEmployeAssocs = new LinkedHashSet<>();
		for (EventInformationDTO eventInformationDTO : eventDTOList) {
			EventUserInfo info  = findByEventIdAndEmployeeId(eventInformation.getEventId(), eventInformationDTO.getEmpId());
			if(info == null) {
				EventUserInfo employeAssoc = new EventUserInfo();
				employeAssoc.setEmpId(eventInformationDTO.getEmpId());
				employeAssoc.setEmpName(eventInformationDTO.getEmpName());
				employeAssoc.setVolunteerHours(eventInformationDTO.getVolunteerHours());
				employeAssoc.setTravelHours(eventInformationDTO.getTravelHours());
				employeAssoc.setBusinessUnit(eventInformationDTO.getBusinessUnit());
				employeAssoc.setEventInformation(eventInformation);
				employeAssoc.setEventStatusCode(1);
				employeAssoc.setEventStatus(userStatus);
				employeAssoc.setIsFeedbackSent(0);
				employeAssoc.setIsFeedbackCompleted(0);
				eventEmployeAssocs.add(employeAssoc);
			}
		}
		return eventEmployeAssocs;
	}

	private Set<EventUserInfo> mapEventInformationDTOtoEventEnrollNotAttend(List<EventInformationDTO> eventDTOList,
			String eventUserStatus) {
		Set<EventUserInfo> eventVolunteers = new LinkedHashSet<>();
		eventDTOList.stream().forEach(x -> {
			EventInformation eventInformation = getEvents(x.getEventId());
			if (eventInformation != null) {
				EventUserInfo info  = findByEventIdAndEmployeeId(eventInformation.getEventId(), x.getEmpId());
				if(info == null) {
					EventUserInfo employeAssoc = new EventUserInfo();
					log.info("x.getEmpId() = {}", x.getEmpId());
					employeAssoc.setEmpId(x.getEmpId());
					employeAssoc.setEmpName(x.getEmpName());
					employeAssoc.setVolunteerHours(x.getVolunteerHours());
					employeAssoc.setTravelHours(x.getTravelHours());
					employeAssoc.setBusinessUnit(x.getBusinessUnit());
					employeAssoc.setEventInformation(eventInformation);
					employeAssoc.setEventStatusCode(1);
					employeAssoc.setEventStatus(eventUserStatus);
					employeAssoc.setIsFeedbackSent(0);
					employeAssoc.setIsFeedbackCompleted(0);
					eventVolunteers.add(employeAssoc);
				}
			}
		});
		return eventVolunteers;
	}

	@Override
	public void updateEventPocDetails(List<EventInformationDTO> listAllEventBatchData) {
		listAllEventBatchData.stream().forEach(x -> {
			EventInformation eventInformation = getEvents(x.getEventId());
			if (eventInformation != null) {
				userService.saveAllUser(updateEventPocDetails(x, eventInformation));
			}
		});
	}

	private List<User> updateEventPocDetails(EventInformationDTO dto, EventInformation eventInformation) {
		List<String> pocIds = FmsUtil.split(dto.getPocID());
		List<String> pocNames = FmsUtil.split(dto.getPocName());
		List<String> pocContacts = FmsUtil.split(dto.getPocContactNumber());
		List<User> details = new ArrayList<>();
		for (int i = 0; i < pocIds.size(); i++) {
			EventUserInfo eventUserInfo = findByEventIdAndEmployeeId(eventInformation.getEventId(), pocIds.get(i));
			if (eventUserInfo != null) {
				User user = userService.findByEmail(pocIds.get(i));
				if (user == null) {
					user = new User();
					user.setEmail(eventUserInfo.getEmpId());
					user.setFirstName(eventUserInfo.getEmpName());
					user.setConductNume(pocContacts.get(i));
					user.getRoles().add(roleService.findByName("POC"));
					user.setPassphrase(passwordEncoder.encode("test@123"));
				}
				details.add(user);
			}
		}
		return details;
	}

	@Override
	public Map<String, Object> getDashboard(String userId) {
		Map<String, Object> map = new ConcurrentHashMap<>();
		User user = userService.findByEmail(userId);
		if(user != null && user.getRoles().iterator().next().getName().equalsIgnoreCase("ADMIN") ) {
			long eventCount = eventInformationRepository.count();
			long totalParticipants = eventInformationRepository.getEventInformationList().size();
			long totalVolunteers = eventInformationRepository.getEventInformationList().size();
			long livesImpacted = eventInformationRepository.getEventLivesImpactedCount();
			map.put("totalEvents", eventCount);
			map.put("totalParticipants", totalParticipants);
			map.put("totalVolunteers", totalVolunteers);
			map.put("livesImpacted", livesImpacted);
			return map;
		}

		List<EventInformation> eventInformations = eventInformationRepository.getEventInformationListyUser(userId);
		long eventCount = eventInformations.size();
		
		long totalParticipants = eventInformations.stream().mapToInt(x -> x.getEventUserInfo().size()).count();
		long totalVolunteers = eventInformations.stream().mapToInt(x -> x.getEventUserInfo().size()).count();
		long livesImpacted = (long) eventInformations.stream().mapToDouble(x-> x.getLivesImpacted()).sum();
		map.put("totalEvents", eventCount);
		map.put("totalParticipants", totalParticipants);
		map.put("totalVolunteers", totalVolunteers);
		map.put("livesImpacted", livesImpacted);

		return map;
	}

	@Override
	public List<EventInformation> getAllEvents(String userId) {
		return eventInformationRepository.findAll();
	}

	@Override
	public List<EventUserInfo> updateEvenNotAttendDetailsaAndUnRegistered(
			List<EventInformationDTO> listAllEventBatchData, String eventUserStatus) {
		Set<EventUserInfo> eventParticipants = mapEventInformationDTOtoEventEnrollNotAttend(listAllEventBatchData,
				eventUserStatus);
		log.info("eventParticipants Size = {}", eventParticipants.size());
		System.out.println("");
		return eventUserInfoRepository.saveAll(eventParticipants);
	}

	@Override
	public void save(MultipartFile file) {
		Path path = Paths.get(applicationProperties.getShared().getFile().getPath());
		try {
			Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public EventUserInfo findByEventIdAndEmployeeId(String eventId, String employeeId) {
		return eventUserInfoRepository.findByEventIdAndEmployeeId(eventId, employeeId);
	}

}
