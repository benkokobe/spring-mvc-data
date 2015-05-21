package com.bko.viewresolver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bko.domain.DeploymentRequest;
import com.bko.domain.Patch;
import com.bko.service.PatchService;

@Component
public class ExcelGenerator extends AbstractExcelView {

	

	@Autowired
	private PatchService patchService;

	public PatchService getPatchService() {
		return patchService;
	}
	public void setPatchService(PatchService patchService) {
		this.patchService = patchService;
	}

	private static final Logger log = LoggerFactory.getLogger(ExcelGenerator.class);

	//private ArrayList<TransferOperationRule> transferOperationRuleList;

	private ArrayList<String> touchyMembersList;

	class TransferOperationRule {
		String category;
		String complement;
	}
	
    private HSSFWorkbook workbook;
	
	private Sheet sheet1 ;
    private Sheet sheet2 ;
    private Sheet sheet3 ;
    private Sheet sheet4, sheet5, sheet6;
    
    Cell cell_11, cell_12, cell_13, cell_14,
         cell_21, cell_22, cell_23, cell_24,cell_25,
         cell_31, cell_32, cell_33, cell_34,cell_35, cell_301, cell_302,
         cell_41,cell_42, cell_43, cell_44,
         cell_51, cell_52,
         cell_61, cell_62, cell_63;
    
    Row row, row2, row3, row4, row5;

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook wb,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

        
		this.workbook = new HSSFWorkbook();
		
		this.sheet1 = wb.createSheet("Patch List");
	    this.sheet2 = wb.createSheet("Patch members list");
	    this.sheet3 = wb.createSheet("TFT operations");
	    this.sheet4 = wb.createSheet("YPD23 operations");
	    //this.sheet5 = wb.createSheet("Missing Members");
	    //this.sheet6 = wb.createSheet("Missing YE");
	    

		//this.loadTransferOpRule(getCorrectPath("transfer_op_rule.txt"));
		//this.loadTouchyMembers(getCorrectPath("touchy_members.txt"));
		
		//List<Patch> patchList = (List<Patch>)model.get("deploymentRequest");
	    //deploymentRequest
	    DeploymentRequest deploymentRequest = (DeploymentRequest)model.get("deploymentRequest");
	    List<Patch> patchList = deploymentRequest.getPatchList();
	    
		log.info("buildExcelDocument() :Size of patch list received from model:" + patchList.size());
		
		generateList(wb, patchList, (String)model.get("drName"));
		
		
		

	}
	public void generateList(HSSFWorkbook wb, List<Patch> patchList, String drName){
		log.info("generateDocument() :Size of patch list:" + patchList.size());
	    int i = 0;
	    int indexMembers = 1;
		//Row row, row2, row3;
		//Cell cell1, cell2, cell21, cell22, cell23, cell24,cell31, cell32, cell33, cell34, cell35;

		for (Patch patch : patchList) {
			
			log.info("Patch ID: " + patch.getPatchId());
			List<Patch> patchCompleteList = (List<Patch>) this.patchService.getPatchDescription(patch.getPatchId());
			log.info("Patch ID2: " + patch.getPatchId());
			row = sheet1.createRow((short) i);
			cell_11 = row.createCell((short) 0);
			cell_12 = row.createCell((short) 1);
			cell_13 = row.createCell((short) 2);
			cell_14 = row.createCell((short) 3);
			//get patch member here
			//get patch TFT oper. here
			cell_11.setCellValue(drName);
			cell_12.setCellValue(patch.getPatchId());
			//Adding patch group and subject in the excel column
			for ( Patch patchComplete : patchCompleteList){
				cell_13.setCellValue(patchComplete.getNomGrp());
				cell_14.setCellValue(patchComplete.getSujPat());
				//i++;
			}
			i++;
			
			log.debug("Cell written: " + i);
		} 
		//generateTransferOperation();
		//generateYpd23("0000000E");
		//generateYpd23(this.reflot);
		//generateYAmissingList(this.reflot);
	}

	public String getCorrectPath(String file_name) {
		String f_path = System.getProperty("user.home")
		// + System.getProperty("file.seperator")
				+ "/" + file_name;
		return f_path;
	}

}
