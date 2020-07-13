package com.cts.fms.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cts.fms.service.EventService;
import com.cts.fms.service.dto.EventInformationDTO;
import com.cts.fms.service.util.FmsUtil;

@Configuration
@EnableScheduling
public class FMSScheduledTasks {

	public static final String EVENT_SUMMARY_XLSX_FILE = "Outreach_Events_Summary.xlsx";
	public static final String EVENT_INFO_XLSX_FILE = "Outreach_Event_Information.xlsx";
	public static final String EVENT_VOLUNTEERS_XLSX_FILE = "Volunteer_Enrollment_Details_Unregister.xlsx";
	public static final String EVENT_NOT_ATTEND_XLSX_FILE = "Volunteer_Enrollment_Details_NotAttempted.xlsx";

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	private EventService eventService;

	private ApplicationProperties applicationProperties;

	@Inject
	public FMSScheduledTasks(EventService eventService, ApplicationProperties applicationProperties) {
		this.eventService = eventService;
		this.applicationProperties = applicationProperties;
	}

	@Scheduled(cron = "0/10 * * * * *")
	public void eventBatchSchedular() throws FileNotFoundException {
		log.info("Scheduler Start : BatchSchedular");
		try {
			if (validFile()) {
				
				String path = applicationProperties.getShared().getFile().getPath();
				
				log.info("");
				List<EventInformationDTO> listAllEventBatchData = convertExcelltoDto(path+
						applicationProperties.getShared().getFile().getExcelVolunteerEventInformation(), 0);
				// Save Participated Info
				eventService.saveAll(listAllEventBatchData, "PARTICIPATED");
				
				// Save Unregistered User
				List<EventInformationDTO> listAllEventBatchVolunteersData = convertExcelltoDto(path+
						applicationProperties.getShared().getFile().getExcelVolunteerUnregistered(), 2);
				eventService.updateEvenNotAttendDetailsaAndUnRegistered(listAllEventBatchVolunteersData,
						"UNREGISTERED");
				// Save Not Attented User
				List<EventInformationDTO> listAllEventBatchVoluntersdData = convertExcelltoDto(path+
						applicationProperties.getShared().getFile().getExcelVolunteerNotAttended(), 3);
				eventService.updateEvenNotAttendDetailsaAndUnRegistered(listAllEventBatchVoluntersdData,
						"NOT ATTENDED");
				
				// Update POC Details
				List<EventInformationDTO> listAllEventBatchSummaryData = convertExcelltoDto(path+
						applicationProperties.getShared().getFile().getExcelVolunteerEventSummary(), 1);
				eventService.updateEventPocDetails(listAllEventBatchSummaryData);
				
				// Send Mail
				//Enable mail Feedback service after persisting all the data in DB
				
				eventService.sendMail(null);
			} else {

			}
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private List<EventInformationDTO> convertExcelltoDto(String file, int val)
			throws InvalidFormatException, IOException {
		switch (val) {
		case 0:
			return FmsUtil.readEventInfo(file, applicationProperties.getShared().getFile().getNewPath());
		case 1:
			return FmsUtil.readEventSummary(file,applicationProperties.getShared().getFile().getNewPath());
		case 2:// Volunteers
			return FmsUtil.readEventVolunteersAndNotAttend(file,applicationProperties.getShared().getFile().getNewPath());
		case 3: // Not Attended
			return FmsUtil.readEventVolunteersAndNotAttend(file,applicationProperties.getShared().getFile().getNewPath());
		default:
			log.info("Invalid ConversionType");
			break;
		}
		return null;

	}

	private boolean validFile() {

		boolean isInfo = true;
		boolean isSummary = true;
		boolean isNotAtted = true;
		boolean isUnRegistered = true;

		log.info("File Property {}", applicationProperties.getShared().getFile().getPath()
				+ applicationProperties.getShared().getFile().getExcelVolunteerEventInformation());
		
		String path = applicationProperties.getShared().getFile().getPath()
				+ applicationProperties.getShared().getFile().getPath();
		if (!FmsUtil.isFileExists(
				path + applicationProperties.getShared().getFile().getExcelVolunteerEventInformation(),
				EVENT_INFO_XLSX_FILE)) {
			isInfo = false;
		}
		if (!FmsUtil.isFileExists(path + applicationProperties.getShared().getFile().getExcelVolunteerEventSummary(),
				EVENT_SUMMARY_XLSX_FILE)) {
			isSummary = false;
		}
		if (!FmsUtil.isFileExists(path + applicationProperties.getShared().getFile().getExcelVolunteerUnregistered(),
				EVENT_VOLUNTEERS_XLSX_FILE)) {
			isUnRegistered = false;

		}
		if (!FmsUtil.isFileExists(path + applicationProperties.getShared().getFile().getExcelVolunteerNotAttended(),
				EVENT_NOT_ATTEND_XLSX_FILE)) {
			isNotAtted = false;
		}

		if (!isInfo || !isSummary || !isNotAtted || !isUnRegistered) {
			log.info("final {}", isNotAtted);
			return false;
		}
		return true;
	}

}
