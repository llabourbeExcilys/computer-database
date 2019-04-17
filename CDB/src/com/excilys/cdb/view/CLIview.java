package com.excilys.cdb.view;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.excilys.cdb.controller.Controller;
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
			System.out.println();
			if(sc.hasNextLine()) {
				String result = sc.nextLine();
				exit = showSwitchAction(result);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!exit);
		sc.close();
	}
	
	private boolean showSwitchAction(String s) {
		String id;
		long idL;
		switch(s) {
			case "1": 
				showList(controller.getCompanyList());
				break;
			case "2": 
				showList(controller.getComputerList());
				break;
			case "3":
				System.out.println("id of computer to show details ?");
				System.out.print("->");
				id = sc.nextLine();
				idL = Long.parseLong(id);
				System.out.println(controller.getComputerById(idL));
				break;
			case "4":
				registerComputer();
				break;
			case "5":
				System.out.println("id of computer to delete ?");
				System.out.print("->");
				id = sc.nextLine();
				idL = Long.parseLong(id);
				controller.deleteComputerById(idL);
				break;
			case "6":
				System.out.println("id of computer to update ?");
				System.out.print("->");
				id = sc.nextLine();
				idL = Long.parseLong(id);
				Computer c = controller.getComputerById(idL);
				if(c!=null) {
					System.out.println(c);
					updateComputer(c);
				}
				break;
			case "exit": 
				System.out.println("Goodbye !\n");
				return true;
			default : 
				System.out.println("Unknown action, try again !\n");
				break;
		}
		return false;
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
				updateComputerName(c);
				break;
			case "2":
				updateComputerCompany(c);
				break;
			case "3":
				break;
			case "4":
				break;
			case "abort":
				break;
			default : 
				ok = false;
				System.out.println("");
			}
		}while(!ok);		
	}
	
	private void updateComputerName(Computer c) {
		boolean ok = false;
		do {
			System.out.print("computer name ?\n->");
			String name = sc.nextLine();
			System.out.println("\nDoes it look ok ? - name:"+name+"\n");
			System.out.println("Press :\n"
					+ "Enter to confirm\n"
					+ "try to try again\n"
					+ "Anything else to abort\n");
			String result = sc.nextLine();
			switch(result) {
				case ""://Enter key pressed 
					ok=true;
					controller.updateName(c,name);
					break;
				case "try": 
					break;
				default : 
					System.out.println("");
					return;
			}
		}while(!ok);
	}
	
	private void updateComputerCompany(Computer c) {
		boolean ok = false;
		do {
			System.out.print("computer company id ?\n->");
			String company = sc.nextLine();
			long companyId  = Long.parseLong(company);

			System.out.println("\nDoes it look ok ? - company:"+companyId+"\n");
			System.out.println("Press :\n"
					+ "Enter to confirm\n"
					+ "try to try again\n"
					+ "Anything else to abort\n");
			String result = sc.nextLine();
			switch(result) {
				case ""://Enter key pressed 
					ok=true;
					controller.updateComputerCompany(c,companyId);
					break;
				case "try": 
					break;
				default : 
					System.out.println("");
					return;
			}
		}while(!ok);
	}
	
	
	private void registerComputer() {
		boolean ok = false;
		do {
			System.out.print("computer name ?\n->");
			String name = sc.nextLine();
			System.out.println("\nDoes it look ok ?\n");
			System.out.println("computer:");
			System.out.println("\tname:"+name+"\n");
			System.out.println("Press :\n"
					+ "Enter to confirm\n"
					+ "try to try again\n"
					+ "Anything else to abort\n");
			String result = sc.nextLine();
			switch(result) {
			case ""://Enter key pressed 
				ok=true;
				controller.addComputer(name);
				break;
			case "try": 
				break;
			default : 
				System.out.println("");
				return;
			}
		}while(!ok);
	}

	private <T> void showList(List<T> list) {
		for(T t : list)
			System.out.println(t);
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
	
	
}
