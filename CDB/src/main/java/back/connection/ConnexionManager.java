package back.connection;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnexionManager {
	
	
	private static DataSource dataDource;
	
	public static DataSource getDataSource() {
		if (dataDource == null) {
			ResourceBundle bundle = ResourceBundle.getBundle("config");

			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(bundle.getString("sgbd.url"));
			config.setDriverClassName(bundle.getString("sgbd.driver"));
			config.setUsername(bundle.getString("sgbd.login"));
			config.setPassword(bundle.getString("sgbd.pwd"));

			dataDource = new HikariDataSource(config);
		}
		return dataDource;
	}

}
