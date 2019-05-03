package mapper;


import  org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import back.mapper.CompanyMapper;
import back.model.Company;

@RunWith(MockitoJUnitRunner.class)
public class CompanyMapperTest {
	
	private CompanyMapper companyMapper = CompanyMapper.getInstance();
	
	@Mock
    private ResultSet resultSet;
	
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
