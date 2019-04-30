package main;


import controller.Controller;
import view.CLIview;
import view.View;



public class Main {

	
	public static void main(String[] args) {
		
		Controller controller = Controller.getInstance();
		
		View view = new CLIview(controller);
		view.start();
		       
	}
}

