package com.bko.viewresolver;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bko.domain.Member;
import com.bko.domain.User;


public class ExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//Member member = (Member) model.get("userList");
		List<User> listUsers = (List<User>) model.get("userList");

		Sheet sheet = workbook.createSheet("sheet 1");
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		Row row = null;
		Cell cell = null;
		int rowCount = 0;
		int colCount = 0;

		// Create header cells
		row = sheet.createRow(rowCount++);

		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("REFPAT");

		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("STATUS");

		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("GROUP");
		
		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("TYPEVL");
		
		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("SUBJECT");
		
		cell = row.createCell(colCount++);
		cell.setCellStyle(style);
		cell.setCellValue("SYNOPSIS");

		// Create data cells
		row = sheet.createRow(rowCount++);
		colCount = 0;
		row.createCell(colCount++).setCellValue(listUsers.get(0).getUsername());
		row.createCell(colCount++).setCellValue(listUsers.get(0).getEmail());
		row.createCell(colCount++).setCellValue(listUsers.get(0).getPassword());

		for (int i = 0; i < 3; i++)
			sheet.autoSizeColumn(i, true);

	}

}
