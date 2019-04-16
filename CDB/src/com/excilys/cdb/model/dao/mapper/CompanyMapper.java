package com.excilys.cdb.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.excilys.cdb.model.Company;

public class CompanyMapper {
	
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
