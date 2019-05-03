package main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import back.controller.Controller;
import front.cli.CLIview;



public class Main {

	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main(String[] args) {
		
		Controller controller = Controller.getInstance();
		
		logger.debug("test log debug");
		
		CLIview view = new CLIview(controller);
		view.start();
		       
	}
}

