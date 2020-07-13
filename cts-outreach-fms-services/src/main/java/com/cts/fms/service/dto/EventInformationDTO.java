package com.cts.fms.service.dto;

public class EventInformationDTO {
	
	private String eventId; //Event ID
	private String  baseLocation; //Base Location	
	private String beneficiaryName; //Beneficiary Name	
	private String councilName;//Council Name	
	private String eventName;//Event Name	
	private String eventDescription;//Event Description	
	private String eventDate; //Event Date (DD-MM-YY)
	private String empId; //Empolyee ID
	private String empName; //EmployeeName
	private Double volunteerHours;//Total Volunteer Hours	
	private Double travelHours;//Total Travel Hours	
	private String livesImpacted;//Lives Impacted	
	private String businessUnit; //Business Unit	
	private String status; //Status	
	private String iIEPCategory;//IIEP Category	
	
	private String venueAddress;//Venue Address
	private String pocID ;//POC ID
	private String pocName; //POC Name
	private String pocContactNumber;//POC Contact Number
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getBaseLocation() {
		return baseLocation;
	}
	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getCouncilName() {
		return councilName;
	}
	public void setCouncilName(String councilName) {
		this.councilName = councilName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
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
	public String getLivesImpacted() {
		return livesImpacted;
	}
	public void setLivesImpacted(String livesImpacted) {
		this.livesImpacted = livesImpacted;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getiIEPCategory() {
		return iIEPCategory;
	}
	public void setiIEPCategory(String iIEPCategory) {
		this.iIEPCategory = iIEPCategory;
	}
	public String getVenueAddress() {
		return venueAddress;
	}
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}
	public String getPocID() {
		return pocID;
	}
	public void setPocID(String pocID) {
		this.pocID = pocID;
	}
	public String getPocName() {
		return pocName;
	}
	public void setPocName(String pocName) {
		this.pocName = pocName;
	}
	public String getPocContactNumber() {
		return pocContactNumber;
	}
	public void setPocContactNumber(String pocContactNumber) {
		this.pocContactNumber = pocContactNumber;
	}
}
