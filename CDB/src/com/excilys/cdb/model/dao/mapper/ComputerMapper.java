package com.excilys.cdb.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	public Computer getComputer(ResultSet result) {

		try {
			Computer computer;
			computer = new Computer(result.getLong("computer_id"),
									result.getString("computer_name"));
			
			String introduced = result.getString("computer_introduced");
			if(introduced != null) {
				Timestamp t = Timestamp.valueOf(introduced);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdIntroduced(locald);
			}
			
			String discontinued = result.getString("computer_discontinued");
			if(discontinued != null) {
				Timestamp t = Timestamp.valueOf(discontinued);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdDiscontinued(locald);
			}
			
			long company_id = result.getLong("company_id");
			String company_name = result.getString("company_name");

			if(company_id!=0 && company_name!=null) {
				Company company = new Company(company_id,company_name);
				computer.setCompany(company);
			}

			
			return computer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
