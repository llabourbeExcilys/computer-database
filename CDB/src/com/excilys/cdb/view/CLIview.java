package com.excilys.cdb.view;

import java.time.DateTimeException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.exception.BadCompanyIdException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.page.Page;

public class CLIview implements View {
	
	private Controller controller;
	private Scanner sc;
	
	public CLIview(Controller controller) {
		super();
		this.controller = controller;
		this.sc = new Scanner(System.in);
	}

	public void start() {
		boolean exit = false;
		do {
			// Show possible actions
			showActions();
			System.out.print("->");
			if(sc.hasNextLine()) {
				String result = sc.nextLine();
				// Read and execute user input, return true to finish
				exit = readSwitchAction(result);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!exit);
		sc.close();
	}
	
	private void showActions() {
		System.out.println("List of action:\n"
						+  "---------------\n"
						+  "1.......Show companies\n"
						+  "2.......Show computers\n"
						+  "3.......Show computer detail\n"
						+  "4.......Create a computer\n"
						+  "5.......Delete a computer\n"
						+  "6.......Update a computer\n"
						+  "7.......Show a page of computer\n"
						+  "exit....Exit\n");
	}
	
	private boolean readSwitchAction(String s) {
		String id;
		Computer c;
		try {
			switch(s) {
			case "1": //Show Company list
				showList(controller.getCompanyList());
				break;
			case "2": //Show Computer list
				showList(controller.getComputerList());
				break;
			case "3": //Show computer detail
				System.out.print("id of computer to show details ?\n->");
				id = sc.nextLine();
				c = controller.getComputerById(id);
				System.out.println(c);
				break;
			case "4": //Create a computer
				System.out.println("Creating a computer");
				registerComputer(); //REGISTER COMPUTER
				break;
			case "5": //Delete a computer
				System.out.print("id of computer to delete ?\n->");
				id = sc.nextLine(); 
				controller.deleteComputerById(id);
				break;
			case "6": //Update a computer
				System.out.print("id of computer to update ?\n->");
				id = sc.nextLine();
				c = controller.getComputerById(id);
				System.out.println(c);
				updateComputer(c); //UPDATE COMPUTER
				break; 
			case "7": //Show page
				System.out.print("Show page number ?\n->");
				String sNumPage = sc.nextLine();
				int iNumPage = Integer.parseInt(sNumPage);
				Page p = new Page(10);
				p.show(controller.getComputerList(), iNumPage);
				break;
			case "exit": 
				System.out.println("Goodbye !\n");
				return true;
			default : 
				System.out.println("Unknown action, try again !\n");
				break;
			}
		}catch(NotFoundException e) {
			System.out.println(e.getMessage()+"\n");
		}catch(DateTimeException e) {
			System.out.println(e.getMessage()+"\n");
		}catch(BadCompanyIdException e) {
			System.out.println(e.getMessage()+"\n");
		}catch(NumberFormatException e) {
			System.out.println("Bad number format\n");
		}
		return false;
	}
	
	//Perform system.out.println on a list of T
	private <T> void showList(List<T> list) {
		for(T t : list)
			System.out.println(t);
	}
	 
	private void registerComputer() {
		String name = obtainInformation("name");		
		String companyID = obtainInformation("company id");
		String dateIntroduction = obtainInformation("date of introduction");
		String dateDiscontinued = obtainInformation("date of discontinuation");

		if(name!=null) {
			long id = controller.addComputer(name);
			Computer c = controller.getComputerById(id);
			if(companyID!=null)
				controller.updateComputerCompany(c, companyID);
			if(dateIntroduction!=null)
				controller.updateComputerIntroduced(c, dateIntroduction);
			if(dateDiscontinued!=null)
				controller.updateComputerDiscontinued(c, dateDiscontinued);
		}
	}
	
	private void updateComputer(Computer c){
		boolean ok = true;
		do {		
			System.out.println("Updating computer:\n"
				+  "---------------\n"
				+  "1.......Update name\n"
				+  "2.......Update company\n"
				+  "3.......Update introduced\n"
				+  "4.......Update discontinued\n"
				+  "abort....Abort\n");
		
			String result = sc.nextLine();
			switch(result) {
			case "1": 
				String name = obtainInformation("name");
				controller.updateName(c,name);
				break;
			case "2": 
				String company = obtainInformation("company id");
				controller.updateComputerCompany(c,company);
				break;
			case "3": 
				String date = obtainInformation("date of introduction");
				controller.updateComputerIntroduced(c,date);
				break;
			case "4": 
				date = obtainInformation("date of discontinuation");
				controller.updateComputerDiscontinued(c,date);
				break;
			case "abort":
				break;
			default : ok = false; 
					  System.out.println("");
			}
		}while(!ok);		
	}
	
	// Ask the user to give a name for a variable
	private String obtainInformation(String toOtain) {
		do {
			System.out.print("computer "+toOtain+" ?\n->");
			String obtained = sc.nextLine();
			if (obtained.equals("")) 
				return null;
			System.out.println("\n"+toOtain+":"+obtained+"\n"
								+ "Enter.......OK\n"
								+ "abort.......Abort");
			String result = sc.nextLine();
			switch(result) {
				case "": return obtained; 	//Enter key pressed 
				case "abort": return null;
				default : break;
			}
		}while(true);
	}

	
}
