package com.excilys.cdb.back.model;

import java.time.LocalDate;

public class Computer {
	
	//REQUIRED
	private long id;
	private String name;
	
	// OPTIONNAL
	private LocalDate ldIntroduced = null;
	private LocalDate ldDiscontinued = null;
	private Company company=null;


	protected Computer(ComputerBuilder computerBuilder) {
		this.id = computerBuilder.getId();
		this.name = computerBuilder.getName();
		this.ldIntroduced = computerBuilder.getLdIntroduced();
		this.ldDiscontinued = computerBuilder.getLdDiscontinued();
		this.company = computerBuilder.getCompany();		
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((ldDiscontinued == null) ? 0 : ldDiscontinued.hashCode());
		result = prime * result + ((ldIntroduced == null) ? 0 : ldIntroduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (id != other.id)
			return false;
		if (ldDiscontinued == null) {
			if (other.ldDiscontinued != null)
				return false;
		} else if (!ldDiscontinued.equals(other.ldDiscontinued))
			return false;
		if (ldIntroduced == null) {
			if (other.ldIntroduced != null)
				return false;
		} else if (!ldIntroduced.equals(other.ldIntroduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
