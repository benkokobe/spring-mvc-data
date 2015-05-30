package com.bko;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.bko.viewresolver.ExcelViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bko")
public class WebConfig extends WebMvcConfigurerAdapter{
	
	/*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
 
        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
 
        //resolvers.add(jsonViewResolver());
        resolvers.add(jspViewResolver());
        //resolvers.add(pdfViewResolver());
        resolvers.add(excelViewResolver());
         
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
	
	@Bean
    //public ViewResolver getViewResolver(){
	public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver; 
    }
	@Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); 
		///WEB-INF/resources/*
		//registry.addResourceHandler("/resources/**").addResourceLocations("WEB-INF/resources/*");
		
	}

}
