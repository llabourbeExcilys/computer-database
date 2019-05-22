package com.excilys.cdb.back.validator;

import java.time.LocalDate;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.cdb.back.dto.ComputerDTO;

public class DateConstraintValidator  implements ConstraintValidator<DateConstraint,ComputerDTO >{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean isValid(ComputerDTO computerDTO, ConstraintValidatorContext context) {
		LocalDate ldIntro = computerDTO.getLdIntroduced();
		LocalDate ldDiscon = computerDTO.getLdDiscontinued();
		
		if(!(ldIntro == null || ldDiscon == null || ldIntro.isBefore(ldDiscon))) {
			Locale locale = LocaleContextHolder.getLocale();
			String errogMessageString = messageSource.getMessage(
					"computerDTO_dateIntro_before_dateDiscon.computerDTO.ldDiscontinued", null, locale);
			context.buildConstraintViolationWithTemplate(errogMessageString)
        		.addPropertyNode("ldDiscontinued")
        		.addConstraintViolation();
			return false;
		}
		return true;
	}
	
}
