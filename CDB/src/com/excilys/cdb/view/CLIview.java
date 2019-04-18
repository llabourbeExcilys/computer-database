package com.excilys.cdb.view;

import java.time.DateTimeException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.exception.BadCompanyIdException;
import com.excilys.cdb.exception.NotFoundException;
import com.excilys.cdb.model.Computer;

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
			showActions();
			System.out.print("->");
			if(sc.hasNextLine()) {
				String result = sc.nextLine();
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
						+  "exit....Exit\n");
	}
	
	private boolean readSwitchAction(String s) {
		String id;
		Computer c;
		try {
			switch(s) {
			case "1": 
				showList(controller.getCompanyList());
				break;
			case "2": 
				showList(controller.getComputerList());
				break;
			case "3":
				System.out.print("id of computer to show details ?\n->");
				id = sc.nextLine();
				c = controller.getComputerById(id);
				System.out.println(c);
				break;
			case "4":
				System.out.println("Creating a computer");
				registerComputer(); //REGISTER COMPUTER
				break;
			case "5":
				System.out.print("id of computer to delete ?\n->");
				id = sc.nextLine(); 
				controller.deleteComputerById(id);
				break;
			case "6":  
				System.out.print("id of computer to update ?\n->");
				id = sc.nextLine();
				c = controller.getComputerById(id);
				System.out.println(c);
				updateComputer(c); //UPDATE COMPUTER
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
		}
		return false;
	}
	
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
	
	private String obtainInformation(String toOtain) {
		do {
			System.out.print("computer "+toOtain+" ?\n->");
			String obtained = sc.nextLine();
			if (obtained.equals("")) 
				return null;
			System.out.println("\n"+toOtain+":"+obtained+"\n"
								+ "(Enter) OK (abort) Abort");
			String result = sc.nextLine();
			switch(result) {
				case "": return obtained; 	//Enter key pressed 
				case "abort": return null;
				default : break;
			}
		}while(true);
	}
	
	
	
	
	
}
