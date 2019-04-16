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

public class CompanyDAO {

	private String driver;
	private String url;
	private String user;
	private String passwd;
	
	private CompanyMapper companyMapper;
	 
	public CompanyDAO() {
		super();
		companyMapper = new CompanyMapper();
		
		ResourceBundle bundle = ResourceBundle.getBundle("resources.config");
		boolean debug = bundle.getString("debug").equals("true");
		
		driver = bundle.getString("sgbd.driver");
		user = bundle.getString("sgbd.login");		
		url = debug ? bundle.getString("sgbd.test.url") : bundle.getString("sgbd.url");
		passwd = bundle.getString("sgbd.pwd");
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
