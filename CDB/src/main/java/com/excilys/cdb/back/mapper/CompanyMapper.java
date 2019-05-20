package com.excilys.cdb.back.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.back.dto.CompanyDTO;
import com.excilys.cdb.back.model.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {

	
	@Override
	public Company mapRow(ResultSet result, int rowNum) throws SQLException {
		Long id = result.getLong("id");
		String name = result.getString("name");
		if(id == 0 || name == null)
			return null;
		
		return new Company(id, name);
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
