package com.excilys.cdb.binding.validator;

import java.time.LocalDate;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.excilys.cdb.binding.dto.ComputerDTO;

public class DateConstraintValidator  implements ConstraintValidator<DateConstraint,ComputerDTO >{

	private MessageSource messageSource;
	
	public DateConstraintValidator(MessageSource messageSource) {
		this.messageSource=messageSource;
	}
	
	@Override
	public boolean isValid(ComputerDTO computerDTO, ConstraintValidatorContext context) {
		LocalDate ldIntro = computerDTO.getLdIntroduced();
		LocalDate ldDiscon = computerDTO.getLdDiscontinued();
		
		if(ldIntro != null && ldDiscon != null && ldDiscon.isBefore(ldIntro)) {
			Locale locale = LocaleContextHolder.getLocale();
			String errogMessageString = messageSource.getMessage("DateConstraint.computerDTO.ldDiscontinued", null, locale);
			context.buildConstraintViolationWithTemplate(errogMessageString)
        		.addPropertyNode("ldDiscontinued")
        		.addConstraintViolation();
			return false;
		}
		return true;
	}
	
}
