package main;


import java.util.ResourceBundle;

import controller.Controller;
import view.CLIview;
import view.View;



public class Main {

	
	public static void main(String[] args) {

		ResourceBundle bundle = ResourceBundle.getBundle("config");
		boolean debug = bundle.getString("debug").equals("true");
		System.out.println("base de " + (debug ? "test" : "prod"));		
		

		//logger.debug("test message");
		//logger.error("this is to be logged");
		
		// CONTROLLER
		Controller controller = Controller.getInstance();
		
		View view = new CLIview(controller);
		view.start();
		       
	}
}

