package com.excilys.cdb.webapp.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.webapp.controller.WebController;

@org.springframework.stereotype.Controller
@RequestMapping("/addComputer")
public class AddComputer{
      
	@Autowired
	private WebController controller;
	
	@ModelAttribute(name = "computerDTO")
	private ComputerDTO getComputerDTO() {return new ComputerDTO();}
	
	@ModelAttribute(name = "companies")
	private List<CompanyDTO> getCompanyList() {return controller.getCompanyList();}

	@GetMapping
	public String doGet(Model model) {
		return "addComputer";
	}
		
	@PostMapping
	public ModelAndView doPost(Model model,@ModelAttribute(value = "computerDTO") @Validated ComputerDTO computerDTO,BindingResult result) {
		if(result.hasErrors()) 
			return new ModelAndView("addComputer");
		
		controller.addComputer(computerDTO);
		return new ModelAndView("redirect:/dashboard");
	}
	
}
