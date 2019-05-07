package back.dao;

public enum SortingField {
	ID("id"),
	NAME("name"),
	DATE_INTRODUCTION("introduced"),
	DATE_DISCONTINUATION("discontinued"),
	COMPANY("company_name");
	
	private final String name;
	
	private SortingField(String name) {
		this.name=name;
	}
}
