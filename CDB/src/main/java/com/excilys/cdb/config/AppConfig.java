package com.excilys.cdb.config;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb")
@EnableTransactionManagement
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
	
	@Bean
	public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
		  return new DataSourceTransactionManager(dataSource);
	}

}
