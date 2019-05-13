package com.excilys.cdb.back.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.back.dto.CompanyDTO;
import com.excilys.cdb.back.model.Company;

import ch.qos.logback.classic.Logger;

@Component
public class CompanyMapper {

	private static Logger logger = (Logger) LoggerFactory.getLogger(CompanyMapper.class);	

	// Create a company from a ResultSet
	public Optional<Company> getCompany(ResultSet result) {
		try {
			if (!result.next())
				return Optional.empty();
			long id = result.getLong("id");
			String name = result.getString("name");
			if (id != 0 && name != null)
				return Optional.ofNullable(new Company(id, name));
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
		return Optional.empty();
	}

	public CompanyDTO companyToDto(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public Optional<CompanyDTO> companyToDto(Optional<Company> cOptional) {
		if (cOptional.isPresent())
			return Optional.ofNullable(companyToDto(cOptional.get()));
		return Optional.empty();
	}

}
