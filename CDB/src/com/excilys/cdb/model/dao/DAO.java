package com.excilys.cdb.model.dao;

import java.util.ResourceBundle;

public abstract class DAO{
	
	protected String driver;
	protected String url;
	protected String user;
	protected String passwd;
	
	public DAO() {
		super();

		ResourceBundle bundle = ResourceBundle.getBundle("resources.config");
		boolean debug = bundle.getString("debug").equals("true");
		
		driver = bundle.getString("sgbd.driver");
		user = bundle.getString("sgbd.login");		
		url = debug ? bundle.getString("sgbd.test.url") : bundle.getString("sgbd.url");
		passwd = bundle.getString("sgbd.pwd");
	}
	
	
}
