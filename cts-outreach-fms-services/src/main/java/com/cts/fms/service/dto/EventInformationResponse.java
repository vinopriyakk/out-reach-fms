package com.cts.fms.service.dto;

public class EventInformationResponse {
	
	private String eventId; //Event ID
	private String baseLocation; //Base Location	
	private String beneficiaryName; //Beneficiary Name	
	private String councilName;//Council Name	
	private String eventName;//Event Name	
	private String eventDescription;//Event Description	
	private String eventDate; //Event Date (DD-MM-YY)
	private String status; //Status	
	private String IIEPCategory;//IIEP Category
	private Float livesImpacted;//Lives Impacted	
	private String venueAddress;
	
	private Integer totalVolunteers;
	private Double totalVolunteersHours;
	private Double totalTravelHours;
	private Double averageRating;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIIEPCategory() {
		return IIEPCategory;
	}
	public void setIIEPCategory(String iIEPCategory) {
		IIEPCategory = iIEPCategory;
	}
	public Float getLivesImpacted() {
		return livesImpacted;
	}
	public void setLivesImpacted(Float livesImpacted) {
		this.livesImpacted = livesImpacted;
	}
	public String getVenueAddress() {
		return venueAddress;
	}
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}
	public Integer getTotalVolunteers() {
		return totalVolunteers;
	}
	public void setTotalVolunteers(Integer totalVolunteers) {
		this.totalVolunteers = totalVolunteers;
	}
	public Double getTotalVolunteersHours() {
		return totalVolunteersHours;
	}
	public void setTotalVolunteersHours(Double totalVolunteersHours) {
		this.totalVolunteersHours = totalVolunteersHours;
	}
	public Double getTotalTravelHours() {
		return totalTravelHours;
	}
	public void setTotalTravelHours(Double totalTravelHours) {
		this.totalTravelHours = totalTravelHours;
	}
	public Double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
}
