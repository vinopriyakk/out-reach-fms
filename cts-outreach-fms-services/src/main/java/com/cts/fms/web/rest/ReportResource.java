package com.cts.fms.web.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fms.domain.EventInformation;
import com.cts.fms.domain.User;
import com.cts.fms.service.EventService;
import com.cts.fms.service.MailService;
import com.cts.fms.service.UserService;
import com.cts.fms.service.dto.EventInformationResponse;
import com.cts.fms.service.dto.UserDTO;
import com.cts.fms.service.mapper.DTOMapper;
import com.cts.fms.service.mapper.EventInformationToEventInformationDto;
import com.cts.fms.service.util.ExcelGenerationUtil;


@RestController
@RequestMapping("/api")
public class ReportResource {
	
	private EventService eventService;
	
	private MailService  mailService;
	
	private UserService userService;

	private DTOMapper<User, UserDTO> userDTOMapper;
	
	private DTOMapper<EventInformation, EventInformationResponse> eventInformationToEventInformationDto;;
	
	public ReportResource(EventService eventService, MailService  mailService, 
			EventInformationToEventInformationDto eventInformationToEventInformationDto,
			UserService userService,DTOMapper<User, UserDTO> userDTOMapper) {
		this.eventService = eventService;
		this.mailService = mailService;
		this.eventInformationToEventInformationDto = eventInformationToEventInformationDto;
	}
 
    @GetMapping(value = "/event/download")
    public ResponseEntity<InputStreamResource> excelEvents(@RequestHeader(name = "userId", required =false) String userId,@RequestParam(name = "filename")String filename) throws IOException {
    	List<EventInformationResponse> eventInformations = eventInformationToEventInformationDto.convertToList(eventService.getAllEvents(userId));
		ByteArrayInputStream in = ExcelGenerationUtil.eventsToExcel(eventInformations);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=events.xlsx");
        headers.add("filename", filename + ".xls");
		 return ResponseEntity
	                .ok()
	                .headers(headers)
	                .body(new InputStreamResource(in));
    }
    
    @GetMapping(value = "/event/report/download")
    public ResponseEntity<InputStreamResource> excelEventsReport(@RequestHeader(name = "userId", required =false) String userId, @RequestParam(name = "filename")String filename) throws IOException {
    	List<EventInformationResponse> eventInformations = eventInformationToEventInformationDto.convertToList(eventService.getAllEvents(userId));
		ByteArrayInputStream in = ExcelGenerationUtil.eventsToExcel(eventInformations);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=events.xlsx");
        headers.add("filename", filename + ".xls");
		 return ResponseEntity
	                .ok()
	                .headers(headers)
	                .body(new InputStreamResource(in));
    }
    
    @GetMapping(value = "/event/{emp-id}/send-report")
    public void sendReport(@RequestHeader(name = "userId", required =false)String userId,@PathVariable("emp-id") String id) throws IOException, AddressException, MessagingException {
        List<EventInformationResponse> eventInformations = eventInformationToEventInformationDto.convertToList(eventService.getAllEvents(userId));
        ByteArrayOutputStream out = ExcelGenerationUtil.eventsToExcelReport(eventInformations);
        mailService.sendEmailReportAttachment(userId+"@cognizant.com", "Report Attachment", out);

    }
    
    @GetMapping("/users/download")
	public ResponseEntity<InputStreamResource> excelUsers(@RequestHeader(name = "userId", required =false)String userId,@RequestParam(name = "filename")String filename) throws IOException {
		final List<UserDTO> users = userDTOMapper.convertToList(userService.getUsers());
		ByteArrayInputStream in = ExcelGenerationUtil.generateExcelForUser(users);
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");
        headers.add("filename", filename + ".xls");
		 return ResponseEntity
	                .ok()
	                .headers(headers)
	                .body(new InputStreamResource(in));
	}
}

