package com.excilys.cdb.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.dao.mapper.CompanyMapper;

public class CompanyDAO extends DAO{

	private CompanyMapper companyMapper;
	 
	public CompanyDAO() {
		super();
		companyMapper = new CompanyMapper();
	}

	public List<Company> getCompanyList() {
		List<Company> resultList = new ArrayList();
		
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
