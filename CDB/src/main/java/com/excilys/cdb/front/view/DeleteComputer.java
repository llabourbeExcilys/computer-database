package com.excilys.cdb.front.view;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.back.controller.WebController;

@org.springframework.stereotype.Controller
@RequestMapping("/deleteComputer")
public class DeleteComputer{

	@Autowired
	private WebController controller;
	
	@GetMapping
	public ModelAndView doGet() {
		return new ModelAndView("redirect:/dashboard");
	}
	
	@PostMapping
	public ModelAndView doPost(Model model, @RequestParam(name = "selection") String computerIds) {
		String[] reString = computerIds.split(",");
		Arrays.stream(reString).forEach(controller::deleteComputerById);
		return new ModelAndView("redirect:/dashboard");
	}
	
}
