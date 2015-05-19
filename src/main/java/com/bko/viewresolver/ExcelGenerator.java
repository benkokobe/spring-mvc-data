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

	private ArrayList<TransferOperationRule> transferOperationRuleList;

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

		/*
		 * HSSFSheet sheet1 = wb.createSheet("Patch List");
		 *
		HSSFSheet sheet2 = wb.createSheet("Patch members list");
		HSSFSheet sheet3 = wb.createSheet("TFT operations");
		HSSFSheet sheet4 = wb.createSheet("YPD23 operations");
		HSSFSheet sheet5 = wb.createSheet("Missing Members");
		HSSFSheet sheet6 = wb.createSheet("Missing YE");
		*/
        
		this.workbook = new HSSFWorkbook();
		
		this.sheet1 = wb.createSheet("Patch List");
	    this.sheet2 = wb.createSheet("Patch members list");
	    this.sheet3 = wb.createSheet("TFT operations");
	    this.sheet4 = wb.createSheet("YPD23 operations");
	    this.sheet5 = wb.createSheet("Missing Members");
	    this.sheet6 = wb.createSheet("Missing YE");
	    

		this.loadTransferOpRule(getCorrectPath("transfer_op_rule.txt"));
		this.loadTouchyMembers(getCorrectPath("touchy_members.txt"));
		
		List<Patch> patchList = (List<Patch>)model.get("patchList");
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
			List<Patch> patchCompleteList = (List<Patch>) this.patchService.getPatchListComplete(patch.getPatchId());
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
			//generateDrMembers(patch.getPatchId());
			
			//System.out.println("DR-name   : " + "PACK-PF0-0001");
			//System.out.println("patch: " + patch.getPatchId());
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

	public void loadTouchyMembers(String file_name) {

		this.touchyMembersList = new ArrayList<String>();
		try {
			// "transfer_op_rule.txt"
			FileInputStream fstream = new FileInputStream(file_name);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				log.debug(strLine);
				this.touchyMembersList.add(strLine.trim());

			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			log.error("Error reading touchy file: " + e.getMessage());
		}
		log.info("**********************************************");
		log.info("Touchy members in file " + file_name + " loaded.");
		log.info("**********************************************");
	}

	public void loadTransferOpRule(String file_name) {

		this.transferOperationRuleList = new ArrayList<TransferOperationRule>();
		TransferOperationRule transferOpertionRule;
		String category, complement;
		try {
			// "transfer_op_rule.txt"
			FileInputStream fstream = new FileInputStream(file_name);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				strLine.split("=");
				log.debug(strLine);
				String[] tokens = strLine.split("=");
				if (tokens.length > 2) {
					log.error("The transfer rule has eror!! please only use one = as a delimitter!!!");
					return;
				} else {
					transferOpertionRule = new TransferOperationRule();
					category = tokens[1];
					complement = tokens[0];
					transferOpertionRule.category = category;
					transferOpertionRule.complement = complement;
					transferOperationRuleList.add(transferOpertionRule);
					// log.debug("Category  :" + category);
					// log.debug("complement:" + complement);
				}

			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			log.error("Error reading transfer rule file: " + e.getMessage());
		}
	}

}
