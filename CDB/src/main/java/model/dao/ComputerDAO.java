package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.BadCompanyIdException;
import exception.NotFoundException;
import model.Computer;
import model.dao.mapper.ComputerMapper;

public class ComputerDAO extends DAO{
	
	private ComputerMapper computerMapper;
	
	public ComputerDAO(ComputerMapper computerMapper) {
		super();
		this.computerMapper = computerMapper;
	}
	
	// Create
	
	public long addComputer(String name) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement(
							"INSERT INTO computer (name) values (?)",Statement.RETURN_GENERATED_KEYS); 
			//On renseigne le paremetre
			state.setString(1, name);
			state.executeUpdate();
			ResultSet generatedKeys = state.getGeneratedKeys();
			generatedKeys.first();
			long id = generatedKeys.getLong(1);
			state.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Read
	
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
			
			boolean next = result.next();
			if(!next)
				throw new NotFoundException("Id not found");
			
			computer = computerMapper.getComputer(result);
			
			result.close();
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return computer;
	}

	// Update
	
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
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
			
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
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
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
		
			
			state.close();
		} catch ( SQLIntegrityConstraintViolationException e) {
			throw new BadCompanyIdException("There is no company with id "+company);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	public void updateComputerIntroduced(Computer c, LocalDate ldate) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement("UPDATE computer " + 
										  "SET computer.introduced = ? " + 
										  "WHERE computer.id = ? "); 
			//On renseigne le paremetre
			state.setDate(1,  Date.valueOf(ldate));
			state.setLong(2, c.getId());
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
		
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void updateComputerDiscontinued(Computer c, LocalDate ldate) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement("UPDATE computer " + 
										  "SET computer.discontinued = ? " + 
										  "WHERE computer.id = ? "); 
			//On renseigne le paremetre 
			state.setDate(1,  Date.valueOf(ldate));
			state.setLong(2, c.getId());
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
		
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Delete
	
	public void deleteComputerById(long id) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = conn.prepareStatement(
					"DELETE FROM computer WHERE id = ? "); 
			//On renseigne le paremetre
			state.setLong(1, id);
			int resultCode = state.executeUpdate();			
			if(resultCode == 0) 
				throw new NotFoundException("There is no computer with id "+id);
			
			System.out.println("Delete OK !");
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
