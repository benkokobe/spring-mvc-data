package com.bko.controller;

import java.io.IOException;

import org.apache.log4j.Logger;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bko.domain.DeploymentRequest;
import com.bko.service.DeploymentRequestService;
import com.bko.service.PatchService;


@Controller
//@RequestMapping(value="/deployment")
public class DeploymentRequestController {
	
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final Logger logger = Logger.getLogger(DeploymentRequestController.class);
	
	//private DeploymentRequestDaoImpl deploymentReqestService;
	@Autowired
	private DeploymentRequestService deploymentRequestService;
    @Autowired
	private PatchService patchService;
    
    private DeploymentRequest deploymentRequest;
	
	//private DeploymentRequestGenerate3 exGen;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	//@RequestMapping(method=RequestMethod.GET)
	@RequestMapping(value="/deployment", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("DR page!");
		//generateName();
		logger.info("generated names!");
		this.deploymentRequest = new DeploymentRequest();
		//this.deploymentRequest.setDrName(deploymentRequestName);
		model.addAttribute("deploymentRequest", this.deploymentRequest);
		return "home";
	}
	
	
	@RequestMapping(value="/generate", method = RequestMethod.POST)
	public ModelAndView submitForm(@ModelAttribute("deploymentRequest") DeploymentRequest deploymentRequest) {
		//String dr = "PACK-TF0-0014";//this is over-written by properties file
		
		//this.deploymentRequest.setDrName(deploymentRequest.getDrName());
		this.deploymentRequest.setPatchList(deploymentRequestService.getPatchList(deploymentRequest.getDrName()));

		
		//exGen = new DeploymentRequestGenerate3();
		//exGen.setDeploymentRequestService(deploymentRequestService);
		//exGen.setPatchService(patchService);
		//exGen.init(dr);
		logger.info("To generate an excel document:" + deploymentRequest.getDrName());
		//exGen.setReflot(reflot);
		//exGen.generateDocument(dr);
		return new ModelAndView("redirect:/deployment");
		
	}


}