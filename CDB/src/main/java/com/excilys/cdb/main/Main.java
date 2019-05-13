package com.excilys.cdb.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.front.cli.CLIview;



public class Main {

	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
         		
		logger.debug("test log debug");
		
		CLIview view = ctx.getBean(CLIview.class);
		view.start();
		ctx.close();
	}
}

