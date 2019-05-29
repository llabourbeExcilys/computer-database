package com.excilys.cdb.persistence.config;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("com.excilys.cdb.persistence")
@EnableTransactionManagement
public class PersistenceConfig {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		ResourceBundle bundle = ResourceBundle.getBundle("db");
		HikariConfig config = new HikariConfig();
		
		config.setJdbcUrl(bundle.getString("sgbd.url"));
		config.setDriverClassName(bundle.getString("sgbd.driver"));
		config.setUsername(bundle.getString("sgbd.login"));
		config.setPassword(bundle.getString("sgbd.pwd"));
		
		return new HikariDataSource(config);
	}

//	@Bean
//	public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}

	
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan(new String[] { "com.excilys.cdb.core.model" });
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());
 
      return em;
   }
   
   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);
    
       return transactionManager;
   }
    
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
       return new PersistenceExceptionTranslationPostProcessor();
   }
    
   Properties additionalProperties() {
       Properties properties = new Properties();
       //properties.setProperty("hibernate.hbm2ddl.auto", "validate");
       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
           
       return properties;
   }
	

}
