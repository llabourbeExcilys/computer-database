package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.dao.mapper.CompanyMapper;

public class CompanyDAO extends DAO{

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
			ResultSet result = state.executeQuery("SELECT * FROM company");
			
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


	
}