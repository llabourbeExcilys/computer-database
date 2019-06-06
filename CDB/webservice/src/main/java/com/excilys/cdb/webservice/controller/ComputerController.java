package com.excilys.cdb.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.service.service.Service;
import com.excilys.cdb.webservice.exception.BadRequestException;
import com.excilys.cdb.webservice.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/computers")
class ComputerController {
	 
	private Service service;
	
	@Autowired
	public void setService(Service service) {
		this.service = service;
	}
 
    @GetMapping
    public List<Computer> findAll() {
        return service.getComputerList();
    }
 
    @GetMapping(value = "/{id}")
    public Computer findById(@PathVariable("id") Long id) {
    	return checkFound(service.getComputerById(id).get());
    }
 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Computer computer) {
        checkNotNull(computer);
        return service.addComputer(computer);
    }
 
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody Computer computer) {
        checkNotNull(computer);
        checkNotNull(service.getComputerById(computer.getId()).get());
        if(id != computer.getId())
        	throw new BadRequestException();
        service.update(computer);
    }
 
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteComputerById(id);
    }

    
    private <T> T checkNotNull(T resource) {
        if (resource == null) {
            throw new BadRequestException();
        }
        return resource;
    }
    
    private <T> T checkFound(T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }
    
}


