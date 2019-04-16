package com.excilys.cdb.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.dao.mapper.ComputerMapper;

public class ComputerDAO {
	
	private String driver;
	private String url;
	private String user;
	private String passwd;
	
	private ComputerMapper computerMapper;
	
	
	public ComputerDAO() {
		super();
		computerMapper = new ComputerMapper();
		
		ResourceBundle bundle = ResourceBundle.getBundle("resources.config");
		boolean debug = bundle.getString("debug").equals("true");
		
		driver = bundle.getString("sgbd.driver");
		user = bundle.getString("sgbd.login");		
		url = debug ? bundle.getString("sgbd.test.url") : bundle.getString("sgbd.url");
		passwd = bundle.getString("sgbd.pwd");
		
	}



	public List<Computer> getComputerList() {
		List<Computer> resultList = new ArrayList<Computer>();
		try {	
			Class.forName(driver);
			
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");         

			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM computer");
			
			while(result.next()){
				Computer computer = computerMapper.getComputer(result);
				resultList.add(computer);				
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}



	public Computer getComputerById(long idL) {

		Computer computer = null;
		
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");         

			//Création d'un objet prepared statement
			PreparedStatement state = conn.prepareStatement("SELECT * FROM computer WHERE id = ?"); 
			//On renseigne le paremetre
			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			while(result.next()){
				computer = computerMapper.getComputer(result);
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return computer;
	}

}
