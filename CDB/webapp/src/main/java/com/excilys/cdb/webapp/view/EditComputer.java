package com.excilys.cdb.webapp.view;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.webapp.controller.WebController;


@org.springframework.stereotype.Controller
@RequestMapping("/editComputer")
public class EditComputer {

	@Autowired
	private WebController controller;
	

	@ModelAttribute(name = "companies")
	private List<CompanyDTO> getCompanyList() {return controller.getCompanyList();}
	
	@GetMapping
	public ModelAndView doGet(Model model, @RequestParam(name = "idToEdit") Long idToEdit){
		Optional<ComputerDTO> computerOptional = controller.getComputerById(idToEdit);
		if(computerOptional.isPresent()) {
			ComputerDTO computerDTO = computerOptional.get();
			model.addAttribute("computerDTO", computerDTO);
			return new ModelAndView("editComputer");
		}else {
			return new ModelAndView("redirect:/dashboard");
		}
	}
	
	@PostMapping
	public ModelAndView doPost(Model model, @ModelAttribute(value = "computerDTO") @Validated ComputerDTO computerDTO,BindingResult result){
		if(result.hasErrors()) 
			return new ModelAndView("editComputer");
		
		controller.updateComputer(computerDTO);
		return new ModelAndView("redirect:/dashboard");
	}
	
}
