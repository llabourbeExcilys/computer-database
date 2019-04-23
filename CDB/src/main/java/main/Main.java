package main;


import java.util.ResourceBundle;

import controller.Controller;
import model.Service;
import model.dao.CompanyDAO;
import model.dao.ComputerDAO;
import model.dao.mapper.CompanyMapper;
import model.dao.mapper.ComputerMapper;
import view.CLIview;
import view.View;

public class Main {

	public static void main(String[] args) {

		ResourceBundle bundle = ResourceBundle.getBundle("config");
		boolean debug = bundle.getString("debug").equals("true");
		System.out.println("base de " + (debug ? "test" : "prod"));		
		
		// MAPPER
		ComputerMapper computerMapper = new ComputerMapper();
		CompanyMapper companyMapper = new CompanyMapper();
		
		// DAO
		ComputerDAO computerDAO = new ComputerDAO(computerMapper);
		CompanyDAO companyDAO = new CompanyDAO(companyMapper);
		
		// SERVICE 
		Service service = new Service(computerDAO,companyDAO);

		// CONTROLLER
		Controller controller = new Controller(service);
		
		View view = new CLIview(controller);
		view.start();
		       
	}
}

