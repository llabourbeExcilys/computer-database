package com.excilys.cdb.back.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

public class LocalDateToStringConverter implements Converter<LocalDate,String> {

	@Override
	public String convert(LocalDate source) {
		return source.toString();
	}

}
