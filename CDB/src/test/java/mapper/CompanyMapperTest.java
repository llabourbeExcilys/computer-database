package mapper;


import  org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import model.Company;
import model.dao.mapper.CompanyMapper;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CompanyMapperTest {
	
	private CompanyMapper companyMapper= CompanyMapper.getInstance();
	
	@Mock
    private ResultSet resultSet;
	
	@Test
	public void testGetCompanyOK() {
		
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(5L);
			Mockito.when(resultSet.getString("name")).thenReturn("Asus");
			
			Optional<Company> company = companyMapper.getCompany(resultSet);
			
			Company testCompany = new Company(5L,"Asus");
			
			Assert.assertEquals(company.get(), testCompany);
							
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}
	
	@Test
	public void testGetCompanyEmpty() {
		
		try {
			Mockito.when(resultSet.getLong("id")).thenReturn(0L);
			Mockito.when(resultSet.getString("name")).thenReturn(null);
			
			Optional<Company> company = companyMapper.getCompany(resultSet);
						
			Assert.assertTrue(company.isEmpty());
							
		} catch (SQLException e) {
			Assert.fail("SQL Exception");
		}
	}
	
	

}
