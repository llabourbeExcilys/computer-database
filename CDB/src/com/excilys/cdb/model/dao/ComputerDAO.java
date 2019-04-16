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

public class ComputerDAO extends DAO{
	
	private ComputerMapper computerMapper;
	
	
	public ComputerDAO() {
		super();
		computerMapper = new ComputerMapper();
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
			PreparedStatement state = conn.prepareStatement(
					"  SELECT C.id, C.name, C.introduced, C.discontinued, B.id, B.name"
					+ " FROM computer C JOIN company B "
					+ "WHERE C.company_id = B.id "
					+ "AND C.id = ? "); 
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
