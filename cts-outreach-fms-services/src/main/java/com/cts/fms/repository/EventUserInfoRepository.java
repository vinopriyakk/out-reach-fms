package com.cts.fms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.fms.domain.EventUserInfo;

public interface EventUserInfoRepository extends JpaRepository<EventUserInfo, Long> {
	
	@Query("select eventInfo from EventUserInfo eventInfo left join "
			+ "eventInfo.eventInformation info where info.eventId = :eventId and eventInfo.isFeedbackCompleted = :i")
	List<EventUserInfo> findByEventIdAndIsFeedbackSent(String eventId, int i);

	List<EventUserInfo> findByIsFeedbackSent(int i);
	
	@Query("select eventInfo from EventUserInfo eventInfo"
			+ " left join eventInfo.eventInformation info where eventInfo.empId = :employeeId ")
	EventUserInfo findByEmployeeId(@Param("employeeId") String employeeId);
	
	@Query("select eventInfo from EventUserInfo eventInfo"
			+ " left join eventInfo.eventInformation info where info.eventId = :eventId and eventInfo.empId = :employeeId ")
	EventUserInfo findByEventIdAndEmployeeId(@Param("eventId")String eventId, @Param("employeeId") String employeeId);

	@Query("select eventInfo from EventUserInfo eventInfo left join "
			+ "eventInfo.eventInformation info where info.eventId = :eventId and eventInfo.empId = :employeeId and eventInfo.isFeedbackCompleted = :i")
	Object findByEventIdAndEmployeeIdAndIsFeedbackCompleted(@Param("eventId")String eventId, @Param("employeeId")String employeeId, @Param("i") int i);

}
