package com.cts.fms.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fms.domain.EventInformation;
import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.service.dto.EventInformationDTO;

public interface EventService {

	List<EventInformation> getEvents(String userId, Pageable pageable);

	List<EventInformation> getAllEvents(String userId);

	EventInformation getEvents(String id);
	
	EventUserInfo findByEventIdAndEmployeeId(String eventId, String employeeId);
	
	List<EventInformation> saveAll(List<EventInformationDTO> listAllEventBatchData, String userInfoType);

	List<EventUserInfo> updateEvenNotAttendDetailsaAndUnRegistered(List<EventInformationDTO> listAllEventBatchData,
			String userInfoType);

	void updateEventPocDetails(List<EventInformationDTO> listAllEventBatchData);

	Map<String, Object> getDashboard(String userId);

	void sendMail(String id);

	public void save(MultipartFile file);
}
