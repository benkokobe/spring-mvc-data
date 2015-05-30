package com.bko.viewresolver;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bko.domain.DeploymentRequest;
import com.bko.domain.Patch;
import com.bko.domain.PatchMember;
import com.bko.domain.TransferOperation;
import com.bko.service.DeploymentRequestService;
import com.bko.service.PatchService;

public class ExcelGenerator extends AbstractExcelView {

	private PatchService patchService;
	
	public void setPatchService(PatchService patchService) {
		this.patchService = patchService;
	}
	
	private DeploymentRequestService deploymentRequestService;
	
	public void setDeploymentRequestService(DeploymentRequestService deploymentRequestService) {
		this.deploymentRequestService = deploymentRequestService;
	}

	private DeploymentRequest deploymentRequest;
	
	private String deploymentRequestName;
	
	private List<Patch> patchList;

	private static final Logger log = LoggerFactory.getLogger(ExcelGenerator.class);

	// private ArrayList<TransferOperationRule> transferOperationRuleList;

	//private ArrayList<String> touchyMembersList;

	class TransferOperationRule {
		String category;
		String complement;
	}

	private HSSFWorkbook workbook;

	private Sheet sheet1;
	private Sheet sheet2;
	private Sheet sheet3;
	private Sheet sheet4, sheet5, sheet6;

	Cell cell_11, cell_12, cell_13, cell_14, cell_21, cell_22, cell_23,
			cell_24, cell_25, cell_31, cell_32, cell_33, cell_34, cell_35,
			cell_301, cell_302, cell_41, cell_42, cell_43, cell_44, cell_51,
			cell_52, cell_61, cell_62, cell_63;

	Row row, row2, row3, row4, row5;

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook wb,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//this.workbook = new HSSFWorkbook();

		this.sheet1 = wb.createSheet("Patch List");
		this.sheet2 = wb.createSheet("Patch members list");
		this.sheet3 = wb.createSheet("TFT operations");
		this.sheet4 = wb.createSheet("YPD23 operations");
		// this.sheet5 = wb.createSheet("Missing Members");
		// this.sheet6 = wb.createSheet("Missing YE");

		// this.loadTransferOpRule(getCorrectPath("transfer_op_rule.txt"));
		// this.loadTouchyMembers(getCorrectPath("touchy_members.txt"));

		// List<Patch> patchList = (List<Patch>)model.get("deploymentRequest");
		// deploymentRequest
		this.deploymentRequest = (DeploymentRequest) model.get("deploymentRequest");

		this.patchList = this.deploymentRequest.getPatchList();

		this.deploymentRequest.setPatchList(patchList);

		log.info("buildExcelDocument() :Name of the DR    :" + this.deploymentRequest.getDrName());
		log.info("buildExcelDocument() :Size of patch list:" + this.deploymentRequest.getPatchList().size());

		generateList(wb);

	}

	public void generateList(HSSFWorkbook wb) {
		log.info("generateDocument() :Size of patch list:" + patchList.size());
		int rows = 0;
		// int indexMembers = 1;

		for (Patch patch : this.patchList) {

			List<Patch> patchCompleteList = (List<Patch>) this.patchService.getPatchDescription(patch.getPatchId());

			// TODO find a better way of finding patch description
			Patch patchDescription = patchCompleteList.get(0);

			log.info("generateList patch  ref     :"+ patchDescription.getPatchId());
			log.info("generateList patch  GROUP   :"+ patchDescription.getNomGrp());
			log.info("generateList patch  SUBJECT :"+ patchDescription.getSujPat());

			row = sheet1.createRow((short) rows);

			// creation of columns
			cell_11 = row.createCell((short) 0);
			cell_12 = row.createCell((short) 1);
			cell_13 = row.createCell((short) 2);
			cell_14 = row.createCell((short) 3);

			cell_11.setCellValue(this.deploymentRequest.getDrName());
			cell_12.setCellValue(patch.getPatchId());
			cell_13.setCellValue(patchDescription.getNomGrp());
			cell_14.setCellValue(patchDescription.getSujPat());

			rows++;

			log.debug("Rows written: " + rows);
		}
		generateDRMembers(wb);
		generateTransferOperation(wb);
		// generateTransferOperation();
		// generateYpd23("0000000E");
		// generateYpd23(this.reflot);
		// generateYAmissingList(this.reflot);
	}
	public void generateDRMembers(HSSFWorkbook wb){
		
		log.info("generateList DR members for    :" + this.deploymentRequest.getDrName());
		
		List<PatchMember> patchMembersList = this.deploymentRequestService.getDRMembers(this.deploymentRequest.getDrName());

		//Style
        //HSSFWorkbook wblocal;
        HSSFCellStyle myStyle = wb.createCellStyle();
       
        myStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        myStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //myStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        Font font = wb.createFont();
        font.setColor(HSSFColor.WHITE.index);
        myStyle.setFont(font);
        
        row2 = sheet2.createRow((short) 0);
		//category and complement
        cell_21 = row2.createCell((short) 0);
        cell_22 = row2.createCell((short) 1);
		cell_23 = row2.createCell((short) 2);
        cell_24 = row2.createCell((short) 3);
        cell_25 = row2.createCell((short) 4);
        
               
        cell_21.setCellStyle(myStyle);
        cell_22.setCellStyle(myStyle);
        cell_23.setCellStyle(myStyle);
        cell_24.setCellStyle(myStyle);
        cell_25.setCellStyle(myStyle);
        
       
        cell_21.setCellValue("REFPAT");
        cell_22.setCellValue("TYPMBR");
        cell_23.setCellValue("TYPACT");
        cell_24.setCellValue("NOMMBR");
        cell_25.setCellValue("TOUCHY");
        
        int indexMembers = 1; // first row for the titles
		for (PatchMember patchMember : patchMembersList){
			
			log.info("patchMember    :" + patchMember.getPatchMember());
			
			
			row2 = sheet2.createRow((short) indexMembers);
			cell_21 = row2.createCell((short) 0);
			cell_22 = row2.createCell((short) 1);
			cell_23 = row2.createCell((short) 2);
			cell_24 = row2.createCell((short) 3);
			//cell_25 = row2.createCell((short) 4);

			
			cell_21.setCellValue(patchMember.getPatchId());
			cell_22.setCellValue(patchMember.getMemberType());
			cell_23.setCellValue(patchMember.getTypAct());
			cell_24.setCellValue(patchMember.getPatchMember());

			indexMembers++;
		}
		
	}
	public void generateTransferOperation(HSSFWorkbook wb){
		
		List<TransferOperation> transferOperationList = this.deploymentRequestService.getTransferOperation(this.deploymentRequest.getDrName());
		
		HSSFCellStyle myStyle = wb.createCellStyle();
	       
        myStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        myStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //myStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        Font font = wb.createFont();
        font.setColor(HSSFColor.WHITE.index);
        myStyle.setFont(font);
        
        row3 = sheet3.createRow((short) 0);
		//category and complement
		cell_301 = row3.createCell((short) 0);
		cell_302 = row3.createCell((short) 1);
		
		cell_31 = row3.createCell((short) 2);
		cell_32 = row3.createCell((short) 3);
		cell_33 = row3.createCell((short) 4);
		cell_34 = row3.createCell((short) 5);
		cell_35 = row3.createCell((short) 6);
        
        cell_301.setCellStyle(myStyle);
        cell_302.setCellStyle(myStyle);
        cell_31.setCellStyle(myStyle);
        cell_32.setCellStyle(myStyle);
        
        cell_33.setCellStyle(myStyle);
        cell_34.setCellStyle(myStyle);
        cell_35.setCellStyle(myStyle);
       
        cell_301.setCellValue("Category");
        cell_302.setCellValue("Complement");
        cell_31.setCellValue("STPALL");
        cell_32.setCellValue("REFPAT");
        cell_33.setCellValue("SWICHK");
        cell_34.setCellValue("SWIMAN");
        cell_35.setCellValue("TFT - ITTCMD");
        
        int rowsInTransferOpTab = 1;
        for ( TransferOperation transferOperation : transferOperationList){
        	row3 = sheet3.createRow((short) rowsInTransferOpTab);
        	cell_301 = row3.createCell((short) 0);
			cell_302 = row3.createCell((short) 1);
			
			cell_31 = row3.createCell((short) 2);
			cell_32 = row3.createCell((short) 3);
			cell_33 = row3.createCell((short) 4);
			cell_34 = row3.createCell((short) 5);
			cell_35 = row3.createCell((short) 6); 
			
			cell_31.setCellValue(transferOperation.getStpAll());
			cell_32.setCellValue(transferOperation.getPatchRef());
			cell_33.setCellValue(transferOperation.getSwiChk());
			cell_34.setCellValue(transferOperation.getSwiMan());
			cell_35.setCellValue(transferOperation.getIttCmd());
			
			rowsInTransferOpTab++;
        }
        
	}

	public String getCorrectPath(String file_name) {
		String f_path = System.getProperty("user.home")
		// + System.getProperty("file.seperator")
				+ "/" + file_name;
		return f_path;
	}

}
