package com.excilys.cdb;


import java.util.ResourceBundle;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.view.CLIview;
import com.excilys.cdb.view.View;

public class Main {

	public static void main(String[] args) {

		Controller controller = new Controller();
		View view = new CLIview(controller);
		view.start();
		
		ResourceBundle bundle = ResourceBundle.getBundle("resources.config");
		boolean debug = bundle.getString("debug").equals("true");
		System.out.println("base de " + (debug ? "test" : "prod"));         
	}
}

