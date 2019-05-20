package com.excilys.cdb.front.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.back.controller.Controller;
import com.excilys.cdb.back.dto.ComputerDTO;

@org.springframework.stereotype.Controller
@RequestMapping("/addComputer")
public class AddComputer{
      
	@Autowired
	private Controller controller;
	
	@GetMapping
	public String doGet(Model model) {
		model.addAttribute("companies", controller.getCompanyList());
		model.addAttribute("ComputerForm", new ComputerDTO());
		model.addAttribute("placeHolderName", "Computer name");
		return "addComputer";
	}
	
	@PostMapping
	public ModelAndView doPost(Model model, @ModelAttribute(value = "ComputerForm") ComputerDTO computerDTO) {
		controller.addComputer(computerDTO);
		return new ModelAndView("redirect:/dashboard");
	}
	
}
