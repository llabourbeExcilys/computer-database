package com.excilys.cdb.back.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.back.dto.ComputerDTO;
import com.excilys.cdb.back.model.Company;
import com.excilys.cdb.back.model.Computer;
import com.excilys.cdb.back.model.ComputerBuilder;

@Component
public class ComputerMapper {
	
	private static Logger logger = LoggerFactory.getLogger( ComputerMapper.class );
	
	// Create a computer from a ResultSet
	public Optional<Computer> getComputer(ResultSet result) {
		try {
			
			if (!result.next())
				return Optional.empty();
			
			Computer computer = new ComputerBuilder(result.getLong("computer_id"),
									result.getString("computer_name")).build();
			
			
			// Check if introduction date was given
			String introduced = result.getString("computer_introduced");
			if(introduced != null) {
				Timestamp t = Timestamp.valueOf(introduced);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdIntroduced(locald);
			}
			
			// Check if discontinuation date was given
			String discontinued = result.getString("computer_discontinued");
			if(discontinued != null) {
				Timestamp t = Timestamp.valueOf(discontinued);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdDiscontinued(locald);
			}
			
			long companyId = result.getLong("company_id");
			String companyName = result.getString("company_name");

			// Check if company id and name was given
			// Create company and set it to computer.company field
			if(companyId!=0 && companyName!=null) {
				Company company = new Company(companyId,companyName);
				computer.setCompany(company);
			}
			if(computer.getId() != 0L && computer.getName() !=null)
				return Optional.ofNullable(computer);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
		return Optional.empty();
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
