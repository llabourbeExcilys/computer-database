package com.excilys.cdb.binding.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.core.model.Computer;

@Component
public class ComputerMapper{
		
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
