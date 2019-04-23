package model.dao;

import java.util.ResourceBundle;

public abstract class DAO{
	
	protected String driver;
	protected String url;
	protected String user;
	protected String passwd;
	
	public DAO() {
		super();

		// Look for config.properties file
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		boolean debug = bundle.getString("debug").equals("true");
		
		// if debug is true, use test database
		url = debug ? bundle.getString("sgbd.test.url") : bundle.getString("sgbd.url");
		driver = bundle.getString("sgbd.driver");
		user = bundle.getString("sgbd.login");
		passwd = bundle.getString("sgbd.pwd");
	}
	
	
}
