package com.bko;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bko.domain.Course;
import com.bko.domain.Patch;
import com.bko.persistence.CourseDao;
import com.bko.persistence.DeploymentRequestDao;
import com.bko.persistence.PatchDao;
import com.bko.service.DeploymentRequestService;
import com.bko.service.PatchService;

import java.util.GregorianCalendar;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("root-context.xml"); // or 'beans-hibernate.xml'
    	//ApplicationContext ctx = new AnnotationConfigApplicationContext(MyBeansDefinition.class);
    	//MyBeansDefinition

        //CourseDao courseDao = (CourseDao) context.getBean("patchDao");
        PatchDao patchDao = (PatchDao) ctx.getBean("patchDao");
        DeploymentRequestDao deployementRequest = (DeploymentRequestDao) ctx.getBean("deploymentRequestDao");
        
        DeploymentRequestService deploymentRequestService = (DeploymentRequestService)ctx.getBean("deploymentRequestService");
        
        PatchService patchService = (PatchService)ctx.getBean("patchService");
        
        
        List<Patch> patchList = deployementRequest.getPatchList("PACK-PND-0691");
        
        List<Patch> patchList2 = deploymentRequestService.getPatchList("PACK-PND-0691");
        System.out.println("Requesting DR");
        for (Patch patch :patchList2 ){
        	System.out.println("Patch" + patch.getPatchId());
        	System.out.println("Description:" + patchService.getPatchDescription(patch.getPatchId()));
        }
        System.out.println("Completed");
        



        //courseDao.delete(courseId);
    }
}
