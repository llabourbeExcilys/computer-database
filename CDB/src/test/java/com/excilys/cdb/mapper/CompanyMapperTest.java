package com.excilys.cdb.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import  org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.back.mapper.CompanyMapper;
import com.excilys.cdb.back.model.Company;
import com.excilys.cdb.config.AppConfig;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CompanyMapperTest {
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Mock
    private ResultSet resultSet;
	
	@Before
	public void setup() {
	        MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetCompanyOK() {
		
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(5L);
			Mockito.when(resultSet.getString("name")).thenReturn("Asus");
			Mockito.when(resultSet.next()).thenReturn(true);

			
			Optional<Company> optCompany = companyMapper.getCompany(resultSet);
			
			Company testCompany = new Company(5L,"Asus");
			
			if(!optCompany.isPresent())
				Assert.fail();
			
			Assert.assertEquals(optCompany.get(), testCompany);
							
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}
	
	@Test
	public void testGetCompanyEmpty() {
		
		try {
			Mockito.when(resultSet.next()).thenReturn(false);
			
			Optional<Company> company = companyMapper.getCompany(resultSet);
						
			Assert.assertTrue(!company.isPresent());
							
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}
	
	

}
