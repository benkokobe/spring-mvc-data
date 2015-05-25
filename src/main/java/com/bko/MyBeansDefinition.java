package com.bko;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@Configuration
@ImportResource("classpath:root-context.xml")
//@ComponentScan(basePackages = "com.bko" )
@ComponentScan({ "com.bko", "com.bko.viewresolver" })
public class MyBeansDefinition {
	

}
