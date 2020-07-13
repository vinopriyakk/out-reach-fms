package com.cts.fms.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "cts_or_fms_event")
public class EventInformation {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	*/
	//@Column(unique = true)
	@Id
	@Column(name = "event_id", nullable = false)
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
	
	@OneToMany(mappedBy = "eventInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<EventUserInfo> eventUserInfo = new LinkedHashSet<>();
	
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

	public Set<EventUserInfo> getEventUserInfo() {
		return eventUserInfo;
	}

	public void setEventUserInfo(Set<EventUserInfo> eventUserInfo) {
		this.eventUserInfo = eventUserInfo;
	}
	
	/*@OneToMany(mappedBy = "eventInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<EventPocDetails> eventPocDetails = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "eventInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<EventEnrollNotAttend> eventEnrollNotAttend = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "eventInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<EventUnRegisteredUser> eventVolunteers = new LinkedHashSet<>();*/
	
	
}
