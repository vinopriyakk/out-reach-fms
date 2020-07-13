package com.cts.fms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "cts_or_fms_event_user_info")
public class EventUserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String empId; //Empolyee ID
	private String empName; //EmployeeName
	private Double volunteerHours;//Total Volunteer Hours	
	private Double travelHours;//Total Travel Hours	
	private String businessUnit;
	
	private String employeeEmail;
	private int eventStatusCode;
	private String eventStatus;
	private int isFeedbackSent;
	private int isFeedbackCompleted;

	@ManyToOne
	@JsonBackReference
	private EventInformation eventInformation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Double getVolunteerHours() {
		return volunteerHours;
	}

	public void setVolunteerHours(Double volunteerHours) {
		this.volunteerHours = volunteerHours;
	}

	public Double getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(Double travelHours) {
		this.travelHours = travelHours;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public EventInformation getEventInformation() {
		return eventInformation;
	}

	public void setEventInformation(EventInformation eventInformation) {
		this.eventInformation = eventInformation;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public int getEventStatusCode() {
		return eventStatusCode;
	}

	public void setEventStatusCode(int eventStatusCode) {
		this.eventStatusCode = eventStatusCode;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public int getIsFeedbackSent() {
		return isFeedbackSent;
	}

	public void setIsFeedbackSent(int isFeedbackSent) {
		this.isFeedbackSent = isFeedbackSent;
	}

	public int getIsFeedbackCompleted() {
		return isFeedbackCompleted;
	}

	public void setIsFeedbackCompleted(int isFeedbackCompleted) {
		this.isFeedbackCompleted = isFeedbackCompleted;
	}
	
}
