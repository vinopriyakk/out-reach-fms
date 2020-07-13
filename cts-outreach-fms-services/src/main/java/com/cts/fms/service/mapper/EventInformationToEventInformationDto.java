package com.cts.fms.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.cts.fms.domain.EventInformation;
import com.cts.fms.domain.EventUserInfo;
import com.cts.fms.domain.UserFeedbackAnswer;
import com.cts.fms.repository.UserFeedbackAnswerRepository;
import com.cts.fms.service.dto.EventInformationResponse;

@Named
public class EventInformationToEventInformationDto implements DTOMapper<EventInformation, EventInformationResponse> {
    
	@Inject
	private UserFeedbackAnswerRepository userFeedbackAnswerRepository;
	
	@Override
    public EventInformationResponse apply(final EventInformation input) {
        final EventInformationResponse eventInformation = new EventInformationResponse();
		eventInformation.setEventId(input.getEventId());
		eventInformation.setBaseLocation(input.getBaseLocation());
		eventInformation.setBeneficiaryName(input.getBeneficiaryName());
		eventInformation.setCouncilName(input.getCouncilName());
		eventInformation.setEventName(input.getEventName());
		eventInformation.setEventDescription(input.getEventDescription());
		eventInformation.setEventDate(input.getEventDate());
		eventInformation.setStatus(input.getStatus());
		eventInformation.setIIEPCategory(input.getIIEPCategory());
		eventInformation.setLivesImpacted(Float.valueOf(input.getLivesImpacted()));
		eventInformation.setTotalVolunteers(input.getEventUserInfo().size());
		eventInformation.setTotalTravelHours(sumTravelHours(input.getEventUserInfo()));
		eventInformation.setTotalVolunteersHours(sumVolunteerHours(input.getEventUserInfo()));
		eventInformation.setAverageRating(Double.valueOf(getRating(input)));
        return eventInformation;
    }
    
    private Double sumTravelHours(Set<EventUserInfo> eventUserInfos) {
    	return eventUserInfos.stream().filter(Objects :: nonNull)
    			.filter(x-> Objects.nonNull(x.getTravelHours()))
    					.mapToDouble(x-> x.getTravelHours()).sum();
    			
    }
    
    private Double sumVolunteerHours(Set<EventUserInfo> eventUserInfos) {
    	return eventUserInfos.stream().filter(Objects :: nonNull)
    			.filter(x-> Objects.nonNull(x.getVolunteerHours()))
    			.mapToDouble(x->x.getVolunteerHours()).sum();
    }
    
    public int getRating(EventInformation event){
    	List<UserFeedbackAnswer> answers = userFeedbackAnswerRepository.findByEventId(event.getEventId());
		int overallRating = 0;
		if (!answers.isEmpty()) {
			for (UserFeedbackAnswer answer : answers) {
				overallRating += answer.getRating();
			}
			overallRating = (overallRating / (answers.size() + 1));
    }
		return overallRating;
}
    
}
