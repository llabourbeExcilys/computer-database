package com.excilys.cdb.persistence.dao;

public enum SortingField {
	ID("id"),
	NAME("name"),
	DATE_INTRODUCTION("introduced"),
	DATE_DISCONTINUATION("discontinued"),
	COMPANY("company");
	
	private final String identifier;
	
	private SortingField(String identifier) {
		this.identifier=identifier;
	}

	public String getIdentifier() {return identifier;}
	
}
