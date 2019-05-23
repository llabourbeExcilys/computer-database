package com.excilys.cdb.back.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping(value = "errors", method = RequestMethod.GET)
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
		int httpErrorCode = getErrorCode(httpRequest);
		switch (httpErrorCode) {
			case 403: {
				return new ModelAndView("403");
			}
			case 404: {
				return new ModelAndView("404");
			}
			case 500: {
				return new ModelAndView("500");
			}
			default:
				return new ModelAndView("500");
		}
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}
}