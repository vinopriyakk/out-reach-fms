package com.cts.fms.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.fms.config.ApplicationProperties;
import com.cts.fms.config.RunnableExc;
import com.cts.fms.service.dto.EventInformationDTO;
import com.cts.fms.web.rest.errors.ErrorConstants;
import com.cts.fms.web.rest.errors.FMSBusinessException;

public class FmsUtil {

	private static final Logger log = LoggerFactory.getLogger(FmsUtil.class);

	public static List<EventInformationDTO> readEventInfo(String filePath, String fileNewPath)
			throws IOException, InvalidFormatException {
		List<EventInformationDTO> listAllEventBatchData = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			Sheet sheet = workbook.getSheetAt(0);
			
			validateEventInfoExcelHeader(sheet.getRow(0));
			for (Row row : sheet) {

				EventInformationDTO eventInformationDTO = new EventInformationDTO();
				// try {
				for (Cell cell : row) {
					if (row.getRowNum() != 0) {
						switch (cell.getColumnIndex()) {
						case 0:
							eventInformationDTO.setEventId(getCellValueAsString(cell));
							break;
						case 1:
							eventInformationDTO.setBaseLocation(getCellValueAsString(cell));
							break;
						case 2:
							eventInformationDTO.setBeneficiaryName(getCellValueAsString(cell));
							break;

						case 3:
							eventInformationDTO.setCouncilName(getCellValueAsString(cell));
							break;
						case 4:
							eventInformationDTO.setEventName(getCellValueAsString(cell));
							break;
						case 5:
							eventInformationDTO.setEventDescription(getCellValueAsString(cell));
							break;
						case 6:
							eventInformationDTO.setEventDate(getCellValueAsString(cell));
							break;
						case 7:
							eventInformationDTO.setEmpId(getCellValueAsString(cell));
							break;
						case 8:
							eventInformationDTO.setEmpName(getCellValueAsString(cell));
							break;
						case 9:
							eventInformationDTO.setVolunteerHours(Double.valueOf(getCellValueAsString(cell)));
							break;
						case 10:
							eventInformationDTO.setTravelHours(Double.valueOf(getCellValueAsString(cell)));
							break;
						case 11:
							eventInformationDTO.setLivesImpacted(getCellValueAsString(cell));
							break;
						case 12:
							eventInformationDTO.setBusinessUnit(getCellValueAsString(cell));
							break;
						case 13:
							eventInformationDTO.setStatus(getCellValueAsString(cell));
							break;
						case 14:
							eventInformationDTO.setiIEPCategory(getCellValueAsString(cell));
							break;
						default:
							// log.info("Invalid Column");
							break;
						}
					}

				}
				if (row.getRowNum() != 0) {
					listAllEventBatchData.add(eventInformationDTO);
				}
			}

			workbook.close();
			moveFile(new File(filePath), fileNewPath);
			return listAllEventBatchData;
		} catch (FileNotFoundException ignored) {
		}
		return listAllEventBatchData;

	}

	public static List<EventInformationDTO> readEventSummary(String filePath, String fileNewPath)
			throws IOException, InvalidFormatException {
		List<EventInformationDTO> listAllEventBatchData = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			Sheet sheet = workbook.getSheetAt(0);
			
			validateEventSummaryExcelHeader(sheet.getRow(0));
			for (Row row : sheet) {

				EventInformationDTO eventInformationDTO = new EventInformationDTO();
				// try {
				for (Cell cell : row) {
					if (row.getRowNum() != 0) {
						switch (cell.getColumnIndex()) {
						case 0:
							eventInformationDTO.setEventId(getCellValueAsString(cell));
							break;
						case 4:
							eventInformationDTO.setVenueAddress(getCellValueAsString(cell));
							break;
						case 18:
							eventInformationDTO.setPocID(getCellValueAsString(cell));
							break;
						case 19:
							eventInformationDTO.setPocName(getCellValueAsString(cell));
							break;
						case 20:
							eventInformationDTO.setPocContactNumber(getCellValueAsString(cell));
							break;
						default:
							// log.info("Invalid Column");
							break;
						}
					}

				}
				if (row.getRowNum() != 0) {
					listAllEventBatchData.add(eventInformationDTO);
				}
			}
			workbook.close();
			moveFile(new File(filePath), fileNewPath);
			return listAllEventBatchData;
		} catch (FileNotFoundException ignored) {
		}
		return listAllEventBatchData;

	}

	public static List<EventInformationDTO> readEventVolunteersAndNotAttend(String filePath, String fileNewPath)
			throws IOException, InvalidFormatException {
		List<EventInformationDTO> listAllEventBatchData = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			Sheet sheet = workbook.getSheetAt(0);
			listAllEventBatchData = new ArrayList<>();
			validateEventVolunteersHeader(sheet.getRow(0));
			for (Row row : sheet) {
				EventInformationDTO eventInformationDTO = new EventInformationDTO();
				// try {
				for (Cell cell : row) {
					if (row.getRowNum() != 0) {
						switch (cell.getColumnIndex()) {
						case 0:
							eventInformationDTO.setEventId(getCellValueAsString(cell));
							break;
						case 1:
							eventInformationDTO.setEventName(getCellValueAsString(cell));
							break;
						case 2:
							eventInformationDTO.setBeneficiaryName(getCellValueAsString(cell));
							break;
						case 3:
							eventInformationDTO.setBaseLocation(getCellValueAsString(cell));
							break;
						case 4:
							eventInformationDTO.setEventDate(getCellValueAsString(cell));
							break;
						case 5:
							eventInformationDTO.setEmpId(getCellValueAsString(cell));
							break;
						default:
							// log.info("Invalid Column");
							break;
						}
					}

				}
				if (row.getRowNum() != 0) {
					listAllEventBatchData.add(eventInformationDTO);
				}
			}
			workbook.close();
			moveFile(new File(filePath), fileNewPath);
			return listAllEventBatchData;
		} catch (FileNotFoundException ignored) {
		}
		return listAllEventBatchData;

	}

	public static void validateEventInfoExcelHeader(Row row) {
		// ("Row No :" + row.getRowNum());
		for (Cell cell : row) {
			if (row.getRowNum() == 0) {
				switch (cell.getColumnIndex()) {
				case 0:
					validate(getCellValueAsString(cell), "Event ID");
					break;
				case 1:
					validate(getCellValueAsString(cell), "Base Location");
					break;
				case 2:
					validate(getCellValueAsString(cell), "Beneficiary Name");
					break;
				case 3:
					validate(getCellValueAsString(cell), "Council Name");
					break;
				case 4:
					validate(getCellValueAsString(cell), "Event Name");
					break;
				case 5:
					validate(getCellValueAsString(cell), "Event Description");
					break;
				case 6:
					validate(getCellValueAsString(cell), "Event Date (DD-MM-YY)");
					break;
				case 7:
					validate(getCellValueAsString(cell), "Employee ID");
					break;
				case 8:
					validate(getCellValueAsString(cell), "Employee Name");
					break;
				case 9:
					validate(getCellValueAsString(cell), "Volunteer Hours");
					break;
				case 10:
					validate(getCellValueAsString(cell), "Travel Hours");
					break;
				case 11:
					validate(getCellValueAsString(cell), "Lives Impacted");
					break;
				case 12:
					validate(getCellValueAsString(cell), "Business Unit");
					break;
				case 13:
					validate(getCellValueAsString(cell), "Status");
					break;
				case 14:
					validate(getCellValueAsString(cell), "IIEP Category");
					break;
				default:
					// log.info("Invalid Column");
					break;
				}
			}
		}
	}

	public static void validateEventSummaryExcelHeader(Row row) {
		// ("Row No :" + row.getRowNum());
		for (Cell cell : row) {
			if (row.getRowNum() == 0) {
				switch (cell.getColumnIndex()) {
				case 0:
					validate(getCellValueAsString(cell), "Event ID");
					break;
				case 4:
					validate(getCellValueAsString(cell), "Venue Address");
					break;
				case 18:
					validate(getCellValueAsString(cell), "POC ID");
					break;
				case 19:
					validate(getCellValueAsString(cell), "POC Name");
					break;
				case 20:
					validate(getCellValueAsString(cell), "POC Contact Number");
					break;
				default:
					// log.info("Invalid Column");
					break;
				}
			}
		}
	}

	public static void validateEventVolunteersHeader(Row row) {
		// ("Row No :" + row.getRowNum());
		for (Cell cell : row) {
			if (row.getRowNum() == 0) {
				switch (cell.getColumnIndex()) {
				case 0:
					validate(getCellValueAsString(cell), "Event ID");
					break;
				case 1:
					validate(getCellValueAsString(cell), "Event Name");
					break;
				case 2:
					validate(getCellValueAsString(cell), "Beneficiary Name");
					break;
				case 3:
					validate(getCellValueAsString(cell), "Base Location");
					break;
				case 4:
					validate(getCellValueAsString(cell), "Event Date (DD-MM-YY)");
					break;
				case 5:
					validate(getCellValueAsString(cell), "EmployeeID");
					break;
				default:
					// log.info("Invalid Column");
					break;
				}
			}
		}
	}

	/**
	 * @param cell
	 * @return String value from any cell
	 */
	private static String getCellValueAsString(Cell cell) {
		DataFormatter df = new DataFormatter();
		String value = df.formatCellValue(cell);
		if (value.endsWith(".0")) { // xls,xlsx only stores doubles, so integers get ".0" appended
			value = value.substring(0, value.length() - 2);
		}
		return value;
	}

	public static void validate(String source, String target) {
		log.info("Source ={}, target = {} ", source, source);
		if (!source.contains(target)) {
			throw new FMSBusinessException(ErrorConstants.BATCH_INVALID_FILE_HEADERS);
		}
	}

	public static void moveFile(File file, String filePath) {
		String dirName = new SimpleDateFormat("yyyyMMddHH").format(new Date());
		String destFile = filePath + dirName + "/" + file.getName();
		log.info("destFile path : {}", destFile);
		File file2 = new File(destFile);
		try {
			if (!file2.exists()) {
				Files.createDirectories(Paths.get(destFile));
				Files.move(Paths.get(file.getAbsolutePath()), Paths.get(destFile));
				log.info("Delete File : {} ", file.getAbsolutePath());
			}
			deleteFile(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void deleteFile(String fileString) {
		File file = new File(fileString);
		if (file.delete()) {
			log.info("File deleted successfully");
		} else {
			log.info("Failed to delete the file");
		}
	}

	public static List<String> split(String str) {
		return Stream.of(str.split(",")).map(elem -> new String(elem)).collect(Collectors.toList());
	}

	public static boolean isFileExists(String files, String fileName) {
		File file = new File(files);
		file.getAbsoluteFile().exists();
		log.info("file.exists() {} ,file Name {}", file.getAbsoluteFile().exists(), file.getAbsoluteFile().getName());
		if (file.getAbsoluteFile().getName().equals(fileName)) {
			log.info("File Exists");
			return true;
		}
		return false;
	}

}
