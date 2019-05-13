package com.excilys.cdb.back.model;

import java.time.LocalDate;

public class ComputerBuilder {

	//REQUIRED
	private long id;
	private String name;
	
	// OPTIONNAL
	private LocalDate ldIntroduced = null;
	private LocalDate ldDiscontinued = null;
	private Company company=null;
	
	
	public ComputerBuilder(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ComputerBuilder withIntroductionDate(LocalDate ldIntroduced) {
		this.ldIntroduced = ldIntroduced;
		return this;
	}
	
	public ComputerBuilder withdiscontinuationDate(LocalDate ldDiscontinued) {
		this.ldDiscontinued = ldDiscontinued;
		return this;
	}
	
	public ComputerBuilder withCompany(Company company) {
		this.company = company;
		return this;
	}
	
	public Computer build() {
		return new Computer(this);
	}
	
	//GETTER AND SETTER
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
