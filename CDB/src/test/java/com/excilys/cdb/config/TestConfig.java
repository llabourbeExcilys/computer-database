package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.dao.TestDatabase;

@Configuration
@Import(AppConfig.class)
public class TestConfig {

	@Bean
	public TestDatabase getTestDatabase(DataSource dataSource) {
		return new TestDatabase(dataSource); 
	}
	
}
