package com.excilys.cdb.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	public Computer getComputer(ResultSet result) {
		long id;
		String name;
		try {
			id = result.getLong("id");
			name = result.getString("name");
			return new Computer(id,name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
