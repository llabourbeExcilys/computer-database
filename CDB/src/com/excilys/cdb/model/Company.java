package com.excilys.cdb.model;

public class Company {
	
	private long id;
	private String name;
	
	public Company(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "{\n"+
				"\t"+"id:"+id+"\n"+
				"\t"+"name:"+name+"\n"+
				"}";
	}
	
	// GETTER AND SETTER
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
}
