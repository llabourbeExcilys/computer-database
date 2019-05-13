package com.excilys.cdb.back.dao;

public enum SortingField {
	ID("C.id"),
	NAME("C.name"),
	DATE_INTRODUCTION("C.introduced"),
	DATE_DISCONTINUATION("C.discontinued"),
	COMPANY("B.name");
	
	private final String identifier;
	
	private SortingField(String identifier) {
		this.identifier=identifier;
	}

	public String getIdentifier() {return identifier;}
	
}
