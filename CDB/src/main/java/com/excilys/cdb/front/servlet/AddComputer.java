package com.excilys.cdb.front.servlet;

import java.util.List;
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

@org.springframework.stereotype.Controller
@RequestMapping("/addComputer")
public class AddComputer{
      
	@Autowired
	private Controller controller;
	
	@GetMapping
	public String doGet(Model model) {
		List<CompanyDTO> companies = controller.getCompanyList();
		model.addAttribute("companies", companies);
		return "addComputer";
	}
	
	@PostMapping
	public ModelAndView doPost(Model model,
    		@RequestParam(name = "computerName") String computerName,
    		@RequestParam(name = "introduced", required = false) String introduced,
    		@RequestParam(name = "discontinued", required = false) String discontinued,
    		@RequestParam(name = "companyId", required = false) String companyId) {
		
		Optional<String> optIntroduced = Optional.ofNullable(introduced.equals("") ? null : introduced );
		Optional<String> optDiscontinued = Optional.ofNullable(discontinued.equals("") ? null : discontinued );
		Optional<String> optCompanyId = Optional.ofNullable(companyId.equals("-1") ? null : companyId );
	
		System.out.println("companyId="+(companyId!=null ? companyId+"." : "null"));
		
		controller.addComputer(computerName, optIntroduced, optDiscontinued, optCompanyId);
		
		
		return new ModelAndView("redirect:/dashboard");
	}
	
}
