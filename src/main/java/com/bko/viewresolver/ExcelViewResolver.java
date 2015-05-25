package com.bko.viewresolver;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.bko.service.DeploymentRequestService;
import com.bko.service.PatchService;

@Component
public class ExcelViewResolver implements ViewResolver{
	
	@Autowired
	private PatchService patchService;
	@Autowired
	private DeploymentRequestService deploymentRequestService;

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		//ExcelView view = new ExcelView();
		ExcelGenerator view = new ExcelGenerator();
		view.setPatchService(patchService);
		view.setDeploymentRequestService(deploymentRequestService);
		return view;
      }
	
}