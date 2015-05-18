package com.bko;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;


public class WriteExcelDemo 
{
	public static void main(String[] args) 
	{
		//Blank workbook
		XSSFWorkbook wb = new XSSFWorkbook(); 
		
		//Create a blank sheet
		
		XSSFSheet sheet1 = wb.createSheet("Patch List");
	    XSSFSheet sheet2 = wb.createSheet("Patch members list");
	    XSSFSheet sheet3 = wb.createSheet("TFT operations");
	    XSSFSheet sheet4 = wb.createSheet("YPD23 operations");
	    XSSFSheet sheet5 = wb.createSheet("Missing Members");
	    
	    try 
		{
			//Write the workbook in file system
		    FileOutputStream out = new FileOutputStream(new File("Excel-example-1.xlsx"));
		    wb.write(out);
		    out.close();
		    
		    System.out.println("Excel-example-1.xlsx written successfully on disk.");
		     
		} 
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
		 
	}
}
