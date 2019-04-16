package com.excilys.cdb.view;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.model.Computer;

public class CLIview implements View {
	
	private Controller controller;

	public CLIview(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void start() {
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		
		do {
			showActions();
			 System.out.print("->");
			String result = sc.nextLine();
			System.out.println();
			switch(result) {
				case "1": 
					showList(controller.getCompanyList());
					break;
				case "2": 
					showList(controller.getComputerList());
					break;
				case "3":
					System.out.println("id of computer ?");
					System.out.print("->");
					String id = sc.nextLine();
					System.out.println(controller.getComputerById(id));
					break;
				case "exit": 
					System.out.println("Goodbye !\n");
					exit = true;
					break;
				default : 
					System.out.println("Unknown action, try again !\n");
					break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!exit);
		
	}
	
	private <T> void showList(List<T> list) {
		for(T t : list)
			System.out.println(t);
	}

	private void showActions() {
		System.out.println("List of action:\n"
						+  "---------------\n"
						+  "1 Show companies\n"
						+  "2 Show computers\n"
						+  "3 Show computer detail\n"
						+  "exit Exit\n");
	}
	
	
}
