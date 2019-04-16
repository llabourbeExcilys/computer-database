package com.excilys.cdb.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	public Computer getComputer(ResultSet result) {

		try {
			Computer computer;
			computer = new Computer(result.getLong("id"),result.getString("name"));
			
			String introduced = result.getString("introduced");
			if(introduced != null) {
				Timestamp t = Timestamp.valueOf(introduced);
				computer.setIntroduced(t);
			}
			
			String discounted = result.getString("discontinued");
			if(discounted != null) {
				Timestamp t = Timestamp.valueOf(discounted);
				computer.setDiscontinued(t);
			}
			
			return computer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
