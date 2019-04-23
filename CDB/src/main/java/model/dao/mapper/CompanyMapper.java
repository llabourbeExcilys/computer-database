package model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

public class CompanyMapper {
	
	// Create a company from a ResultSet
	public Company getCompany(ResultSet result) {
		long id;
		String name;
		try {
			id = result.getLong("id");
			name = result.getString("name");
			return new Company(id,name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
