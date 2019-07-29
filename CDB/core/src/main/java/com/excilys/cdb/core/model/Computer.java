package com.excilys.cdb.core.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "computer")
public class Computer {
	
	//REQUIRED
	@Id
	private long id;
	
    @Column(nullable = false)
	private String name;
	
	// OPTIONNAL
    @Column(name = "introduced")
	private LocalDate introduced;
    @Column(name = "discontinued")
	private LocalDate discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	public Computer() {
		super();
	}

	protected Computer(ComputerBuilder computerBuilder) {
		this.id = computerBuilder.getId();
		this.name = computerBuilder.getName();
		this.introduced = computerBuilder.getLdIntroduced();
		this.discontinued = computerBuilder.getLdDiscontinued();
		this.company = computerBuilder.getCompany();		
	}

	@Override
	public String toString() {
		return "{\n"+
				"\t"+"id:"+id+"\n"+
				"\t"+"name:"+name+"\n"+
				(introduced!=null?"\tintroduced:"+introduced+"\n":"")+
				(discontinued!=null?"\tdiscontinued:"+discontinued+"\n":"")+
				(company!=null?"\t\tcomputer:"+company+"\n":"")+
				"}";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
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
	public LocalDate getIntroduced() {return introduced;}
	public void setLdIntroduced(LocalDate ldIntroduced) {this.introduced = ldIntroduced;}
	public LocalDate getDiscontinued() {return discontinued;}
	public void setLdDiscontinued(LocalDate ldDiscontinued) {this.discontinued = ldDiscontinued;}

}
