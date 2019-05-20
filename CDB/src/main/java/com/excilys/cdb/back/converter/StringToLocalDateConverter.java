package com.excilys.cdb.back.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate>{

	@Override
	public LocalDate convert(String date) {
		String[] datePart = date.split("-");

		if (datePart.length == 3) {
			String year = datePart[0];
			String month = datePart[1];
			String day = datePart[2];
			int yearI = Integer.parseInt(year);
			int monthI = Integer.parseInt(month);
			int dayI = Integer.parseInt(day);

			return LocalDate.of(yearI, monthI, dayI);
		}
		
		return null;
	}

}
