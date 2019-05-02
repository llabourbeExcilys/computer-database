package view;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import exception.BadCompanyIdException;
import exception.BadInputException;
import exception.NotFoundException;
import exception.RequestedPageException;
import main.Main;
import model.Computer;

public class CLIview implements View {
	
	private static Logger logger = LoggerFactory.getLogger( Main.class );

	
	private Controller controller;
	private Scanner sc;
	
	private final String dateFormat = "yyyy-mm-dd";
	
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
		Optional<Computer> c;
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
				if(c.isPresent())
					System.out.println(c.get());
				else	
					System.out.println("The computer with id:"+id+" was not found");
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
				
				if(c.isPresent())
					updateComputer(c.get());
				else 
					throw new NotFoundException("The computer with id:"+id+" cannot be found.");
				break; 
			case "7": //Show page
				System.out.print("Show page number ?\n->");
				String sNumPage = sc.nextLine();
				int iNumPage = Integer.parseInt(sNumPage);
				showList(controller.getComputerPage(iNumPage, 10));
				break;
			case "exit": 
				System.out.println("Goodbye !\n");
				return true;
			default : 
				System.out.println("Unknown action, try again !\n");
				break;
			}
		}catch(NotFoundException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
			System.out.println(e.getMessage()+"\n");
		}catch(DateTimeException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
		}catch(BadCompanyIdException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
		}catch(NumberFormatException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
			System.out.println("Bad number format\n");
		}catch(BadInputException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
		}catch(RequestedPageException e) {
			logger.debug("An exception occured.",e);
			logger.error("An exception occured.",e);
		}
		
		return false;
	}
	
	//Perform system.out.println on a list of T
	private <T> void showList(List<T> list) {
		for(T t : list)
			System.out.println(t);
	}
	 
	private void registerComputer() {
		Optional<String> name = obtainInformation("name");		
		Optional<String> companyID = obtainInformation("company id");
		Optional<String> dateIntroduction = obtainInformation("date of introduction");
		Optional<String> dateDiscontinued = obtainInformation("date of discontinuation");

		if (name.isPresent()) {
			controller.addComputer(name.get(),dateIntroduction,dateDiscontinued,companyID);
		}else {
			throw new BadInputException("The 'name' field must be provided.");
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
				Optional<String> name = obtainInformation("name");
				if(name.isPresent())
					controller.updateName(c,name.get());
				break;
			case "2": 
				Optional<String> company = obtainInformation("company id");
				if(company.isPresent())
					controller.updateComputerCompany(c,company.get());
				break;
			case "3": 
				Optional<String> date = obtainInformation("date of introduction",dateFormat);
				if(date.isPresent())
					controller.updateComputerIntroduced(c,date.get());
				break;
			case "4": 
				date = obtainInformation("date of discontinuation",dateFormat);
				if(date.isPresent())
					controller.updateComputerDiscontinued(c,date.get());
				break;
			case "abort":
				break;
			default : ok = false; 
					  System.out.println("");
			}
		}while(!ok);		
	}
	
	// Ask the user to give a name for a variable
	private Optional<String> obtainInformation(String toOtain) {
		return obtainInformation(toOtain, "");
	}
	
	private Optional<String> obtainInformation(String toOtain, String formatExpected) {
		do {
			System.out.print("computer "+toOtain+" ? "+formatExpected+"\n->");
			String obtained = sc.nextLine();
			if (obtained.equals("")) 
				return Optional.empty();
			System.out.println("\n"+toOtain+":"+obtained+"\n"
								+ "Enter.......OK\n"
								+ "abort.......Abort");
			String result = sc.nextLine();
			switch(result) {
				case "": return Optional.ofNullable(obtained); 	//Enter key pressed 
				case "abort": return Optional.empty();
				default : break;
			}
		}while(true);
	}

	
}