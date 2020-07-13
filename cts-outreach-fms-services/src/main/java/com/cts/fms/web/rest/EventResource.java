package com.cts.fms.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fms.domain.EventInformation;
import com.cts.fms.service.EventService;
import com.cts.fms.service.dto.EventInformationResponse;
import com.cts.fms.service.mapper.DTOMapper;
import com.cts.fms.service.mapper.EventInformationToEventInformationDto;

@RestController
@RequestMapping("/api")
public class EventResource {

	private final Logger log = LoggerFactory.getLogger(EventResource.class);

	private EventService eventService;

	private DTOMapper<EventInformation, EventInformationResponse> eventInformationToEventInformationDto;

	public EventResource(EventService eventService,
			EventInformationToEventInformationDto eventInformationToEventInformationDto) {
		this.eventService = eventService;
		this.eventInformationToEventInformationDto = eventInformationToEventInformationDto;
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<EventInformationResponse> getUser(@PathVariable String id) {
		return new ResponseEntity<>(eventInformationToEventInformationDto.apply(eventService.getEvents(id)),
				HttpStatus.OK);
	}

	@GetMapping("/events")
	public ResponseEntity<List<EventInformationResponse>> getAllUsers(
			@RequestHeader(name = "userId", required = false) String userId, Pageable pageable) {
		final List<EventInformationResponse> page = eventInformationToEventInformationDto
				.convertToList(eventService.getEvents(userId, pageable));
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@GetMapping("/events/dashboard")
	public ResponseEntity<?> getDashboard(@RequestHeader(name = "userId", required = false) String userId) {
		return new ResponseEntity<>(eventService.getDashboard(userId), HttpStatus.OK);
	}

	@GetMapping("/events/send-remainder-mail")
	public ResponseEntity<EventInformation> sendRemainderMail(
			@RequestHeader(name = "userId", required = false) String userId, @RequestParam(value = "eventId", required = false ) String eventId) {
		if (eventId != null) {
			eventService.sendMail(eventId);
		} else {
			eventService.sendMail(eventId);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/events/upload/excel")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			eventService.save(file);
			return new ResponseEntity<>("Uploaded the file successfully: " + file.getOriginalFilename(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Could not upload the file: " + file.getOriginalFilename() + "!",
					HttpStatus.NOT_ACCEPTABLE);

		}
	}

}