package com.bko;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@Configuration
@ComponentScan(basePackages = "com.bko")
@ImportResource("classpath:root-context.xml")
public class MyBeansDefinition {
	

}
