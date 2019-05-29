package com.excilys.cdb.test.binding.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.excilys.cdb.binding.config.BindingConfig;
import com.excilys.cdb.binding.mapper.CompanyMapper;
import com.excilys.cdb.core.model.Company;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BindingConfig.class)
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
			
			Company company = companyMapper.mapRow(resultSet,0);
			Company companyTest = new Company(5L,"Asus");
			
			Assert.assertNotNull("La company ne devrait pas être null.",company);			
			Assert.assertEquals("Les deux companies devraient être égales.",companyTest,company);				
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}
	
	@Test
	public void testGetCompanyEmpty() {
		try {
			Mockito.when(resultSet.next()).thenReturn(false);
			Company company = companyMapper.mapRow(resultSet, 0);
			Assert.assertNull(company);				
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}

}
