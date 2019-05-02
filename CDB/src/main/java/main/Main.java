package main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import view.CLIview;
import view.View;



public class Main {

	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main(String[] args) {
		
		Controller controller = Controller.getInstance();
		
		logger.debug("test log debug");
		
		View view = new CLIview(controller);
		view.start();
		       
	}
}

