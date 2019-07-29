package com.excilys.cdb.binding.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.core.model.Company;

@Component
public class CompanyMapper {

	public CompanyDTO companyToDto(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public Optional<CompanyDTO> companyToDto(Optional<Company> cOptional) {
		if (cOptional.isPresent())
			return Optional.ofNullable(companyToDto(cOptional.get()));
		return Optional.empty();
	}
	
}
