package com.excilys.cdb.test.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.excilys.cdb.binding.config.BindingConfig;
import com.excilys.cdb.persistence.config.PersistenceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Import({PersistenceConfig.class,BindingConfig.class})
public class TestConfig {

	@Bean
	public TestDatabase getTestDatabase(DataSource dataSource) {
		return new TestDatabase(dataSource); 
	}
	
	@Primary
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		
		config.setJdbcUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		config.setDriverClassName("org.h2.Driver");
		config.setUsername("admincdb");
		config.setPassword("qwerty1234");
		
		return new HikariDataSource(config);
	}

	
}
