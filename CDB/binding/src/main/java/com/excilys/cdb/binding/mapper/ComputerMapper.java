package com.excilys.cdb.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.core.model.ComputerBuilder;

@Component
public class ComputerMapper implements RowMapper<Computer>{
		
	@Override
	public Computer mapRow(ResultSet result, int rowNum) throws SQLException {
		
		long id = result.getLong("computer_id");
		String name = result.getString("computer_name");
		String introduced = result.getString("computer_introduced");
		String discontinued = result.getString("computer_discontinued");
		long companyId = result.getLong("company_id");
		String companyName = result.getString("company_name");
		
		if(id == 0L || name == null)
			return null;
		
		Computer computer = new ComputerBuilder(id,name).build();
	
		// Check if introduction date was given
		if(introduced != null) {
			Timestamp t = Timestamp.valueOf(introduced);
			LocalDate locald = t.toLocalDateTime().toLocalDate();
			computer.setLdIntroduced(locald);
		}
		
		// Check if discontinuation date was given
		if(discontinued != null) {
			Timestamp t = Timestamp.valueOf(discontinued);
			LocalDate locald = t.toLocalDateTime().toLocalDate();
			computer.setLdDiscontinued(locald);
		}

		// Check if company id and name was given and create it
		if(companyId!=0 && companyName!=null) {
			Company company = new Company(companyId,companyName);
			computer.setCompany(company);
		}
		
		return computer;
	}
	
	public ComputerDTO computerToDTO(Computer c) {
		return new ComputerDTO(c.getId(),
				c.getName(),
				c.getLdIntroduced(),
				c.getLdDiscontinued(),
				(c.getCompany() != null) ? c.getCompany().getId() : null,
				(c.getCompany() != null) ? c.getCompany().getName() : null);
	}
	
	public Optional<ComputerDTO> computerToDTO(Optional<Computer> c) {
		if (c.isPresent())
			return Optional.ofNullable(computerToDTO(c.get()));
		return Optional.empty();
	}


	
}
