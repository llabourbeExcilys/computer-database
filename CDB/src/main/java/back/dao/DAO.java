package back.dao;

import java.util.ResourceBundle;
import java.util.TimeZone;

public abstract class DAO{
	
	protected String driver;
	protected String url;
	protected String user;
	protected String passwd;
	
	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));};
	
	public DAO() {
		super();

		// Look for config.properties file
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		
		url = bundle.getString("sgbd.url");
		driver = bundle.getString("sgbd.driver");
		user = bundle.getString("sgbd.login");
		passwd = bundle.getString("sgbd.pwd");
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	
}
