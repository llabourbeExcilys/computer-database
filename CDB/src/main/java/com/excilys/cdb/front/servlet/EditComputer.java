package com.excilys.cdb.front.servlet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.back.controller.Controller;
import com.excilys.cdb.back.dto.CompanyDTO;
import com.excilys.cdb.back.dto.ComputerDTO;


@org.springframework.stereotype.Controller
@RequestMapping("/editComputer")
public class EditComputer {

	@Autowired
	private Controller controller;
	
	@GetMapping
	public ModelAndView doGet(Model model, @RequestParam(name = "idToEdit") String idToEdit){
		Optional<ComputerDTO> computerOptional = controller.getComputerById(idToEdit);
		if(computerOptional.isPresent()) {
			ComputerDTO computer = computerOptional.get();
			model.addAttribute("computer", computer);
			model.addAttribute("companies", controller.getCompanyList());
			return new ModelAndView("editComputer");
		}else {
			return new ModelAndView("redirect:/dashboard");
		}
	}

	
	@PostMapping
	public ModelAndView doPost(Model model,
			@RequestParam(name = "id") String id,
			@RequestParam(name = "computerName") String computerName,
			@RequestParam(name = "introduced") String introduced,
			@RequestParam(name = "discontinued") String discontinued,
			@RequestParam(name = "companyId") String companyId){

		Optional<String> intrOptional = Optional.ofNullable(introduced.equals("") ? null : introduced);
		Optional<String> discOptional = Optional.ofNullable(discontinued.equals("") ? null : discontinued);		
		Optional<ComputerDTO> optComputerDTO = controller.getComputerById(id);
		Optional<CompanyDTO> cOptional = controller.getCompanyById(companyId);
		
		if(optComputerDTO.isPresent()) {
			ComputerDTO computerDTO = optComputerDTO.get();
			controller.updateName(computerDTO, computerName);
		
			if(intrOptional.isPresent() && discOptional.isPresent()) 
				controller.updateComputerIntroDiscon(computerDTO, intrOptional.get(), discOptional.get());
			else if (intrOptional.isPresent())
				controller.updateComputerIntroduced(computerDTO, intrOptional.get());
			else if (discOptional.isPresent())
				controller.updateComputerDiscontinued(computerDTO, discOptional.get());
			
			if(cOptional.isPresent())
				controller.updateComputerCompany(computerDTO, companyId);
		}
		return new ModelAndView("redirect:/dashboard");
	}

}
