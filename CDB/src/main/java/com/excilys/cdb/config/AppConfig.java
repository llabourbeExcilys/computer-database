package com.excilys.cdb.config;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb")
public class AppConfig {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		
		ResourceBundle bundle = ResourceBundle.getBundle("config");

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(bundle.getString("sgbd.url"));
		config.setDriverClassName(bundle.getString("sgbd.driver"));
		config.setUsername(bundle.getString("sgbd.login"));
		config.setPassword(bundle.getString("sgbd.pwd"));

		return new HikariDataSource(config);
	}	

}
