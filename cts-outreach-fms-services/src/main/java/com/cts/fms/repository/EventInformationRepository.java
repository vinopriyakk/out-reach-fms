package com.cts.fms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.fms.domain.EventInformation;

public interface EventInformationRepository extends JpaRepository<EventInformation, String> {

	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where e.eventStatus = 'PARTICIPATED'")
	List<EventInformation> getEventInformationList(Pageable pageable);

	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where e.eventStatus = 'PARTICIPATED' and e.empId = :empId")
	List<EventInformation> getEventInformationListByUser(@Param("empId") String empId, Pageable pageable);

	@Query("select sum(event.livesImpacted) from EventInformation event")
	Long getEventLivesImpactedCount();

	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where e.eventStatus = 'PARTICIPATED'")
	List<EventInformation> getEventInformationList();
	
	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where e.eventStatus = 'PARTICIPATED' and e.empId = :empId")
	List<EventInformation> getEventInformationListyUser(@Param("empId") String empId);

	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where event.eventId = :eventId ")
	EventInformation getEventInformationList(@Param("eventId") String eventId);

	@Query("select event from EventInformation event left join fetch event.eventUserInfo e where event.eventId = :eventId  and e.empId = :empId")
	EventInformation getEventInformation(@Param("eventId") String eventId, @Param("empId") String empId);

}
