package com.excilys.cdb.back.converter;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate>{

	@Override
	public LocalDate convert(String date) {
		String[] datePart = date.split("-");
		if (datePart.length == 3) {
			int yearI = Integer.parseInt(datePart[0]);
			int monthI = Integer.parseInt(datePart[1]);
			int dayI = Integer.parseInt(datePart[2]);
			try {
				return LocalDate.of(yearI, monthI, dayI);
			} catch (DateTimeException  e) {
				return null;
			}
		}
		return null;
	}

}
