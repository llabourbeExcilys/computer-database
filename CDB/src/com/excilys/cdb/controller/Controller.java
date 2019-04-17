package com.excilys.cdb.controller;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Service;

public class Controller {

	private Service service;
	
	
	public Controller() {
		super();
		service = new Service();
	}

	public List<Computer> getComputerList() {
		return service.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return service.getCompanyList();
	}

	public Computer getComputerById(long id) {	
		return service.getComputerById(id);
	}

	public void addComputer(String name) {
		if(name!=null) {
			service.addComputer(name);
		}
	}

	public void deleteComputerById(long idL2) {
		service.deleteComputerById(idL2);
	}

	public void updateName(Computer c, String name) {
		service.updateName(c,name);
	}

	public void updateComputerCompany(Computer c, long company) {
		service.updateComputerCompany(c,company);
		
	}
	
	
	
}
