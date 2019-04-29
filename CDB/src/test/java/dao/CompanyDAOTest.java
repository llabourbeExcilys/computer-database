package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.dao.CompanyDAO;

public class CompanyDAOTest {
	
	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	private TestDatabase testDataBase;
	
	@Before
	public void setUp() {
		testDataBase = TestDatabase.getInstance();
		try {
			testDataBase.reload();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllCompanyTest() {
		List<Company> companyList = companyDAO.getCompanyList();
		List<Company> testCompanyList = testDataBase.findAllCompanies();
		Assert.assertEquals(companyList, testCompanyList);
	}
	
	@Test
	public void getCompanyByIdTest() {
		
		Optional<Company> company = companyDAO.getCompanyByID(2);
		if(!company.isPresent())
			Assert.fail();
		
		Company testCompany = testDataBase.findCompanyById(2L);
		if(testCompany == null)
			Assert.fail();
		
		Assert.assertEquals(company.get(), testCompany);
		
		company = companyDAO.getCompanyByID(5);
		if(!company.isPresent())
			Assert.fail();
		
		testCompany = testDataBase.findCompanyById(5L);
		if(testCompany == null)
			Assert.fail();
		
		Assert.assertEquals(company.get(), testCompany);
	}
	
	@Test
	public void getEmptyCompanyByIdTest() {
		Optional<Company> company = companyDAO.getCompanyByID(19);
		Assert.assertTrue(!company.isPresent());
	}
	
	
	
}
