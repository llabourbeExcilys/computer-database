package com.excilys.cdb.webapp.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;
import com.excilys.cdb.webapp.controller.WebController;
import com.excilys.cdb.webapp.page.Page;

@org.springframework.stereotype.Controller
@RequestMapping("/dashboard")
@SessionAttributes(value = "mypage",types = {Page.class})
public class DashBoard {
	
	private static Logger logger = LoggerFactory.getLogger( DashBoard.class );

	private WebController controller;
	
	public DashBoard(WebController webController) {
		this.controller = webController;
	}
	
	@ModelAttribute("mypage")
	public Page getMyPage() {
		return new Page(10,1,SortingField.ID,SortingOrder.ASC);
	}
	
   @GetMapping
    public String handle(Model model,
    		@ModelAttribute("mypage") Page mypage,
    		@RequestParam(name = "field", required = false) String fieldString,
    		@RequestParam(name = "order", required = false) String orderString,
    		@RequestParam(name = "nbByPage", required = false) String nbByPageString,
    		@RequestParam(name = "page", required = false) String pageString,
    		@RequestParam(name = "search", required = false) String computerSearch) {
	   
		try {
			if(nbByPageString!=null)
				mypage.setNbByPage(Integer.parseInt(nbByPageString));
			if(pageString!=null) 
				mypage.setPage(Integer.parseInt(pageString));
		} catch (NumberFormatException e) {
			logger.info(e.getMessage());
		}

		if(fieldString!=null && !fieldString.equals("")) {
			switch (fieldString) {
				case "name":	  mypage.setSortingField(SortingField.NAME); break;
				case "introDate": mypage.setSortingField(SortingField.DATE_INTRODUCTION);	break;
				case "disconDate":mypage.setSortingField(SortingField.DATE_DISCONTINUATION); break;
				case "company":	  mypage.setSortingField(SortingField.COMPANY); break;
				default: 		  mypage.setSortingField(SortingField.ID); break;
			}
		}
		
		if(orderString!=null && !orderString.equals("")) {
			switch (orderString) {
			case "asc": mypage.setSortingOrder(SortingOrder.ASC); break;
			case "desc":mypage.setSortingOrder(SortingOrder.DESC); break;
			default: 	mypage.setSortingOrder(SortingOrder.ASC); break;
			}
		}		
		
		List<ComputerDTO> computers	= new ArrayList<>();
		if(computerSearch!=null) {
			Optional<ComputerDTO> optComputer = controller.getComputerByName(computerSearch);
			if (optComputer.isPresent())
				computers.add(optComputer.get());
		}else {
			computers = controller.getComputerPage(mypage.getPage(), mypage.getNbByPage(),mypage.getSortingField(), mypage.getSortingOrder());
		}

		long nbComputerFound = controller.getNumberOfComputer();
		int lastPage = (int) Math.ceil(nbComputerFound/(double)mypage.getNbByPage()) ;
		
		model.addAttribute("mypage", mypage);
		model.addAttribute("nbComputerFound", nbComputerFound);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("page", mypage.getPage());
		model.addAttribute("computers", computers);
		
        return "dashboard";
    }
 }
   