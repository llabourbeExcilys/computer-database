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
	
	private final static String SQL_SELECT_ALL_COMPUTER = 
			"SELECT C.id AS computer_id,"
			+ " C.name AS computer_name,"
			+ " C.introduced AS computer_introduced,"
			+ "	C.discontinued AS computer_discontinued,"
			+ " B.id AS company_id,"
			+ " B.name AS company_name"
			+ "	FROM computer C LEFT JOIN company B"
			+ " ON C.company_id = B.id";
	
	private final static String SQL_SELECT_COMPUTER_BY_ID =  
			SQL_SELECT_ALL_COMPUTER
			+ " WHERE C.id = ?";
	
	private final static String SQL_CREATE_COMPUTER = 
			"INSERT INTO computer (name,introduced,discontinued,company_id) "
			+ "values (?,?,?,?)";

	
	private final static String SQL_UPDATE_COMPUTER = "UPDATE computer " + 
			  										  	"SET name = ?, " +
			  												"introduced = ?,"+
			  												"discontinued = ?,"+
			  												"company_id = ? "+
			  											"WHERE id = ? ";
	
	private final static String SQL_DELETE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id = ? ";

	
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
					conn.prepareStatement(SQL_CREATE_COMPUTER, Statement.RETURN_GENERATED_KEYS); 
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
	
	public long addComputer(String name, LocalDate ldateIntroduction, LocalDate ldateDiscontinuation, String companyId) {
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = 
					conn.prepareStatement(SQL_CREATE_COMPUTER, Statement.RETURN_GENERATED_KEYS); 
			//On renseigne le paremetre
			state.setString(1, name);
			state.setDate(2, ldateIntroduction != null ? Date.valueOf(ldateIntroduction) : null);
			state.setDate(3, ldateDiscontinuation != null ? Date.valueOf(ldateDiscontinuation) : null);
			state.setString(4, companyId);


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
			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPUTER);
			
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
			PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPUTER_BY_ID); 
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
	
	public void update(Computer c) {
		Date ldateIntroduction = c.getLdIntroduced() != null ? Date.valueOf(c.getLdIntroduced()) : null;
		Date ldateDiscontinuation = c.getLdDiscontinued() != null ? Date.valueOf(c.getLdDiscontinued()) : null;
		
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = conn.prepareStatement(SQL_UPDATE_COMPUTER); 
			//On renseigne le paremetre
			state.setString(1, c.getName());
			state.setDate(2, ldateIntroduction);
			state.setDate(3, ldateDiscontinuation);
			state.setLong(4, c.getCompany().getId());
			state.setLong(5, c.getId());
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
			
			state.close();
		}catch ( SQLIntegrityConstraintViolationException e) {
				throw new BadCompanyIdException("There is no company with id "+c.getCompany().getId());
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
			PreparedStatement state = conn.prepareStatement(SQL_DELETE_COMPUTER_BY_ID); 
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
