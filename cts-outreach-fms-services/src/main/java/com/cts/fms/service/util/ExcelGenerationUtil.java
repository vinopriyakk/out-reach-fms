package com.cts.fms.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cts.fms.service.dto.EventInformationResponse;
import com.cts.fms.service.dto.UserDTO;


public final class ExcelGenerationUtil {
	
	private ExcelGenerationUtil() {}
	
	public static ByteArrayInputStream eventsToExcel(List<EventInformationResponse> eventInformations) throws IOException {
		String[] COLUMNs = {"Event ID", "Event Name","Event Date (DD-MM-YY)",
				"Status","Venue Address","Total no. of volunteers",
				"Total Volunteer Hours","Total Travel Hours"};
		try(
				
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Events");
	 
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
	 
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
	 
			// Row for Header
			Row headerRow = sheet.createRow(0);
	 
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
	 
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	 
			int rowIdx = 1;
			for (EventInformationResponse eventInformation : eventInformations) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(eventInformation.getEventId());
				row.createCell(1).setCellValue(eventInformation.getEventName());
				row.createCell(2).setCellValue(eventInformation.getEventDate());
				row.createCell(3).setCellValue(eventInformation.getStatus());
				row.createCell(4).setCellValue(eventInformation.getVenueAddress());
				row.createCell(5).setCellValue(eventInformation.getTotalVolunteers());
				row.createCell(6).setCellValue(eventInformation.getTotalVolunteersHours());
				row.createCell(7).setCellValue(eventInformation.getTotalTravelHours());
			}
	 
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	public static ByteArrayOutputStream eventsToExcelReport(List<EventInformationResponse> eventInformations) throws IOException {
		String[] COLUMNs = {"Event ID", "Event Name","Event Date (DD-MM-YY)",
				"Status","Venue Address","Total no. of volunteers",
				"Total Volunteer Hours","Total Travel Hours"};
		try(
				
				Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			CreationHelper createHelper = workbook.getCreationHelper();
	 
			Sheet sheet = workbook.createSheet("Events");
	 
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());
	 
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
	 
			// Row for Header
			Row headerRow = sheet.createRow(0);
	 
			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}
	 
			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	 
			int rowIdx = 1;
			for (EventInformationResponse eventInformation : eventInformations) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(eventInformation.getEventId());
				row.createCell(1).setCellValue(eventInformation.getEventName());
				row.createCell(2).setCellValue(eventInformation.getEventDate());
				row.createCell(3).setCellValue(eventInformation.getStatus());
				row.createCell(4).setCellValue(eventInformation.getVenueAddress());
				row.createCell(5).setCellValue(eventInformation.getTotalVolunteers());
				row.createCell(6).setCellValue(eventInformation.getTotalVolunteersHours());
				row.createCell(7).setCellValue(eventInformation.getTotalTravelHours());
			}
	 
			workbook.write(out);
			return out;
		}
	}
	
	public static ByteArrayInputStream generateExcelForUser(List<UserDTO> users) throws IOException {
		String[] COLUMNs = { "Email", "Name"};
		try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Users");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			int rowIdx = 1;
			for (UserDTO user : users) {
				Row row = sheet.createRow(rowIdx++);
				row.createCell(0).setCellValue(user.getEmail());
				row.createCell(1).setCellValue(user.getName());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}
}
