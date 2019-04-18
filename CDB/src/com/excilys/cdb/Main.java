package com.excilys.cdb;


import java.util.ResourceBundle;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.model.Service;
import com.excilys.cdb.model.dao.CompanyDAO;
import com.excilys.cdb.model.dao.ComputerDAO;
import com.excilys.cdb.model.dao.mapper.CompanyMapper;
import com.excilys.cdb.model.dao.mapper.ComputerMapper;
import com.excilys.cdb.view.CLIview;
import com.excilys.cdb.view.View;

public class Main {

	public static void main(String[] args) {

		// Mapper
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
		
		ResourceBundle bundle = ResourceBundle.getBundle("resources.config");
		boolean debug = bundle.getString("debug").equals("true");
		System.out.println("base de " + (debug ? "test" : "prod"));         
	}
}

