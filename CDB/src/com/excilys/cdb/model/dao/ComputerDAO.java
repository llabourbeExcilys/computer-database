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
			String query = "SELECT C.id AS computer_id,"
					+ " C.name AS computer_name,"
					+ " C.introduced AS computer_introduced,"
					+ "	C.discontinued AS computer_discontinued,"
					+ " B.id AS company_id,"
					+ " B.name AS company_name"
					+ "	FROM computer C LEFT JOIN company B"
					+ "	ON C.company_id = B.id";
					
					
			ResultSet result = state.executeQuery(query);
			
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

			//Création d'un objet prepared statement
			
			String query = 
					  " SELECT C.id AS computer_id,"
					+ " C.name AS computer_name,"
					+ " C.introduced AS computer_introduced,"
					+ "	C.discontinued AS computer_discontinued,"
					+ " B.id AS company_id,"
					+ " B.name AS company_name"
					+ " FROM computer C LEFT JOIN company B"
					+ " ON C.company_id = B.id"
					+ " WHERE C.id = ?";
			
			PreparedStatement state = conn.prepareStatement(query); 
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

	public void addComputer(String name) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement("INSERT INTO computer (name) values (?)"); 
			//On renseigne le paremetre
			state.setString(1, name);
			state.executeUpdate();
		
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void deleteComputerById(long id) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = conn.prepareStatement(
					"DELETE FROM computer WHERE id = ? "); 
			//On renseigne le paremetre
			state.setLong(1, id);
			state.executeUpdate();
			System.out.println("Delete OK !");
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void updateName(Computer c, String name) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement("UPDATE computer " + 
										  "SET computer.name = ? " + 
										  "WHERE computer.id = ? "); 
			//On renseigne le paremetre
			state.setString(1, name);
			state.setLong(2, c.getId());
			state.executeUpdate();
		
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void updateCompany(Computer c, long company) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement("UPDATE computer " + 
										  "SET computer.company_id = ? " + 
										  "WHERE computer.id = ? "); 
			//On renseigne le paremetre
			state.setLong(1, company);
			state.setLong(2, c.getId());
			state.executeUpdate();
		
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
