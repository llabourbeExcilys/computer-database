package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.NotFoundException;
import model.Company;
import model.dao.mapper.CompanyMapper;

public class CompanyDAO extends DAO{

	private final static String SQL_SELECT_ALL_COMPANY = "SELECT * FROM company";
	private final static String SQL_SELECT_COMPANY_BY_ID = "SELECT * FROM company WHERE C.id = ?";

	
	
	private CompanyMapper companyMapper;
	 
	public CompanyDAO(CompanyMapper companyMapper) {
		super();
		this.companyMapper = companyMapper;
	}

	// Read
	
	public List<Company> getCompanyList() {
		List<Company> resultList = new ArrayList<Company>();
		
		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");         

			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPANY);
			
			while(result.next()){
				Company company = companyMapper.getCompany(result);
				resultList.add(company);				
			}
			result.close();
			state.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	public Company getCompanyByID(long idL) {
		Company company = null;

		try {	
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, passwd);

			//Création d'un objet prepared statement
			PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPANY_BY_ID); 
			//On renseigne le paremetre
			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			boolean next = result.next();
			if(!next)
				throw new NotFoundException("Id not found");
			
			company = companyMapper.getCompany(result);
			
			result.close();
			state.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return company;
	}


	
}
