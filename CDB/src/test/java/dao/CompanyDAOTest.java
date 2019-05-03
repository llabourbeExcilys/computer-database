package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import back.dao.CompanyDAO;
import back.model.Company;

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
		Assert.assertEquals("La list de company renvoyée par la DAO devrait etre identique a celle de test"
				,companyList, testCompanyList);
	}
	
	@Test
	public void getCompanyByIdTest() {
		
		Optional<Company> company = companyDAO.getCompanyByID(2);
		if(!company.isPresent())
			Assert.fail();
		
		Company testCompany = testDataBase.findCompanyById(2L);
		if(testCompany == null)
			Assert.fail();
		
		Assert.assertEquals("La company renvoyée par la companyDAO n'est pas identique a celle de test",
				company.get(), testCompany);
		
		company = companyDAO.getCompanyByID(5);
		if(!company.isPresent())
			Assert.fail();
		
		testCompany = testDataBase.findCompanyById(5L);
		if(testCompany == null)
			Assert.fail();
		
		Assert.assertEquals("La company renvoyée par la companyDAO n'est pas identique a celle de test",
				company.get(), testCompany);
	}
	
	@Test
	public void getEmptyCompanyByIdTest() {
		long id = 19;
		Optional<Company> company = companyDAO.getCompanyByID(id);
		Assert.assertTrue("La companyDAO n'aurait pas du renvoyer de company pour l'id "+id,
				!company.isPresent());
	}
	
	
	
}
