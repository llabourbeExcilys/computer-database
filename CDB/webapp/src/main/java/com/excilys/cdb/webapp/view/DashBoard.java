package com.excilys.cdb.webapp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;
import com.excilys.cdb.webapp.controller.WebController;

@org.springframework.stereotype.Controller
@RequestMapping("/dashboard")
public class DashBoard {

	@Autowired 
	private WebController controller;
	private static int nbByPage = 10;
	private static int page = 1;
	private static String field = "";
	private static String order = "";
	private static SortingField sortingField;
	private static SortingOrder sortingOrder;
	
   @GetMapping
    public String handle(Model model,
    		@RequestParam(name = "field", required = false) String fieldString,
    		@RequestParam(name = "order", required = false) String orderString,
    		@RequestParam(name = "nbByPage", required = false) String nbByPageString,
    		@RequestParam(name = "page", required = false) String pageString,
    		@RequestParam(name = "search", required = false) String computerSearch) {
        
	   if(fieldString!=null && !fieldString.equals(""))
			field = fieldString;
		if(orderString!=null && !orderString.equals(""))
			order = orderString;
		if(nbByPageString!=null)
			nbByPage = Integer.parseInt(nbByPageString);
		if(pageString!=null)
			page = Integer.parseInt(pageString);

		long nbComputerFound = controller.getNumberOfComputer();
		int lastPage = (int) Math.ceil(nbComputerFound/(double)nbByPage) ;
		
		switch (field) {
			case "name":	  sortingField = SortingField.NAME; 				break;
			case "introDate": sortingField = SortingField.DATE_INTRODUCTION;	break;
			case "disconDate":sortingField = SortingField.DATE_DISCONTINUATION;	break;
			case "company":	  sortingField = SortingField.COMPANY; 				break;
			default: 		  sortingField = SortingField.ID; 					break;
		}
		switch (order) {
			case "asc": sortingOrder = SortingOrder.ASC;  break;
			case "desc":sortingOrder = SortingOrder.DESC; break;
			default: 	sortingOrder = SortingOrder.ASC;  break;
		}	
		
		List<ComputerDTO> computers	= new ArrayList<>();
		if(computerSearch!=null) {
			Optional<ComputerDTO> optComputer = controller.getComputerByName(computerSearch);
			if (optComputer.isPresent())
				computers.add(optComputer.get());
		}else {
			computers = controller.getComputerPage(page, nbByPage,sortingField, sortingOrder);
		}
		
		model.addAttribute("nbComputerFound", nbComputerFound);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("page", page);
		model.addAttribute("computers", computers);
		
        return "dashboard";
    }
   
}
