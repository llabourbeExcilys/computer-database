package com.excilys.cdb.model;

import java.sql.Timestamp;

public class Computer {
	
	private long id;
	private String name;
	
	// OPTIONNAL
	private Timestamp introduced = null;
	private Timestamp discontinued = null;
	private long companyId = -1;

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
				(introduced!=null?"\t"+introduced+"\n":"")+
				(discontinued!=null?"\t"+discontinued+"\n":"")+
				(companyId!=-1?"\t"+companyId+"\n":"")+
				"}";
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
