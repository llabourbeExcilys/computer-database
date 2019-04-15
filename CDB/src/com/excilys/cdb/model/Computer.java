package com.excilys.cdb.model;

import java.sql.Timestamp;

public class Computer {
	
	private long id;
	private String name;
	
	private Timestamp introduced;
	private Timestamp discontinued;

	private long companyId;

	public Computer(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	// GETTER AND SETTER
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Timestamp getIntroduced() {return introduced;}
	public void setIntroduced(Timestamp introduced) {this.introduced = introduced;}
	public Timestamp getDiscontinued() {return discontinued;}
	public void setDiscontinued(Timestamp discontinued) {this.discontinued = discontinued;}
	public long getCompanyId() {return companyId;}
	public void setCompanyId(long companyId) {this.companyId = companyId;}


}
