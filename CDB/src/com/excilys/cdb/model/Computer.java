package com.excilys.cdb.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Computer {
	
	private long id;
	private String name;
	
	// OPTIONNAL
	private LocalDate ldIntroduced = null;
	private LocalDate ldDiscontinued = null;
	private Company company=null;

	public Computer(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "{\n"+
				"\t"+"id:"+id+"\n"+
				"\t"+"name:"+name+"\n"+
				(ldIntroduced!=null?"\tintroduced:"+ldIntroduced+"\n":"")+
				(ldDiscontinued!=null?"\tdiscontinued:"+ldDiscontinued+"\n":"")+
				(company!=null?"\t\tcomputer:"+company+"\n":"")+
				"}";
	}
	
	// GETTER AND SETTER
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Company getCompany() {return company;}
	public void setCompany(Company company) { this.company = company;}
	public LocalDate getLdIntroduced() {return ldIntroduced;}
	public void setLdIntroduced(LocalDate ldIntroduced) {this.ldIntroduced = ldIntroduced;}
	public LocalDate getLdDiscontinued() {return ldDiscontinued;}
	public void setLdDiscontinued(LocalDate ldDiscontinued) {this.ldDiscontinued = ldDiscontinued;}
	
	

}
