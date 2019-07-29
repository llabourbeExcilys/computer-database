package com.excilys.cdb.console.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.console.cli.CLIview;
import com.excilys.cdb.console.config.ConsoleConfig;



public class Main {

	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConsoleConfig.class);
         		
		logger.debug("test log debug");
		
		CLIview view = ctx.getBean(CLIview.class);
		view.start();
		ctx.close();
	}
}

