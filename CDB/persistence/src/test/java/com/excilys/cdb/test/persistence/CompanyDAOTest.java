package com.excilys.cdb.test.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {
	
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private TestDatabase testDataBase;
	
	@Before
	public void setUp() {
		try {
			testDataBase.reload();
		} catch (IOException | SQLException e) {
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
		
		Optional<Company> company = companyDAO.getCompanyById(2);
		if(!company.isPresent())
			Assert.fail("La company d'id 2 devrait etre renvoyé par le getCompanyById");
		
		Company testCompany = testDataBase.findCompanyById(2L);
		if(testCompany == null)
			Assert.fail("La company de test d'id 2 devrait etre renvoyé par le findCompanyById");
		
		Assert.assertEquals("La company renvoyée par la companyDAO n'est pas identique a celle de test",
				company.get(), testCompany);
		
		company = companyDAO.getCompanyById(5);
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
		Optional<Company> company = companyDAO.getCompanyById(id);
		Assert.assertTrue("La companyDAO n'aurait pas du renvoyer de company pour l'id "+id,
				!company.isPresent());
	}
	
	@Test
	public void deleteCompanyByIdTest() {
		
		long id = 1;
		
		Optional<Company> cOptional = companyDAO.getCompanyById(id);
		Assert.assertTrue("La company d'id "+id+" devrait etre présent",cOptional.isPresent());
		Company company = cOptional.get();
		
		 
		List<Computer> computersWithSpecifiedCompany = computerDAO.getComputerList().stream()
				 .filter(c -> c.getCompany()!=null && c.getCompany().equals(company))
				 .collect(Collectors.toList());
	
		List<Computer> otherComputersbeforeDelete = computerDAO.getComputerList().stream()
				 .filter(c -> c.getCompany()==null || !c.getCompany().equals(company))
				 .collect(Collectors.toList());
		 
		companyDAO.deleteCompanyById(id);
		
		List<Computer> otherComputersAfterDelete = computerDAO.getComputerList().stream()
				 .filter(c -> c.getCompany()==null || !c.getCompany().equals(company))
				 .collect(Collectors.toList());
		
		Assert.assertEquals("Les autres computers n'auraient pas du etre affectées par la suppression",otherComputersbeforeDelete, otherComputersAfterDelete);
		 
		cOptional = companyDAO.getCompanyById(1);
		
		Assert.assertTrue("La company d'id "+id+" ne devrait pas etre présente",!cOptional.isPresent());
		
		computersWithSpecifiedCompany = computerDAO.getComputerList().stream()
				 .filter(c -> c.getCompany()!=null && c.getCompany().equals(company))
				 .collect(Collectors.toList());

		Assert.assertTrue("Les computers de la base auraient du etre supprimés",computersWithSpecifiedCompany.isEmpty());
		 
	}
	
	
	@Test
	public void deleteCompanyByIdWithIdNotFoundTest() {
		long id = 11;
		Optional<Company> cOptional = companyDAO.getCompanyById(id);
		Assert.assertTrue("La company d'id "+id+" ne devrait etre présent",!cOptional.isPresent());
	}
	
	
}
