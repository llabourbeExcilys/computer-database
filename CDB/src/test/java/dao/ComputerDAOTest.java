package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exception.NotFoundException;
import model.Company;
import model.Computer;
import model.dao.ComputerDAO;

public class ComputerDAOTest {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	private TestDatabase testDataBase;
	
	@Before
	public void setUp() { 
		testDataBase = TestDatabase.getInstance();	
		try {
			testDataBase.reload();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addComputerTest() {
		long id = 21;
		String name = "My Netronics computer";
		LocalDate intD = LocalDate.of(1990, 12, 12);
		LocalDate disD = LocalDate.of(2000, 04, 24);
		Company company = new Company(4,"Netronics");

		Computer c = new Computer(id,name);
		c.setLdIntroduced(intD);
		c.setLdDiscontinued(disD);
		c.setCompany(company);
		
		computerDAO.addComputer(name, intD, disD, Optional.ofNullable(company.getId()));
		
		Optional<Computer> cReturned = computerDAO.getComputerById(id);
		
		if(!cReturned.isPresent())
			Assert.fail();
		
		Assert.assertEquals(cReturned.get(), c);
		
	}
	
	@Test
	public void getAllComputerTest() {
		List<Computer> ComputerList = computerDAO.getComputerList();
		List<Computer> testComputerList = testDataBase.findAllComputers();
		Assert.assertEquals(testComputerList,ComputerList);	
	}
	
	@Test
	public void getComputerByIdTest() {		
		Optional<Computer> computer = computerDAO.getComputerById(2L);
		if(!computer.isPresent())
			Assert.fail();
		
		Computer testComputer = testDataBase.findComputerById(2L);
		if(testComputer == null)
			Assert.fail();
		
		Assert.assertEquals(computer.get(), testComputer);
		
		computer = computerDAO.getComputerById(5L);
		if(!computer.isPresent())
			Assert.fail();
		
		testComputer = testDataBase.findComputerById(5L);
		if(testComputer == null)
			Assert.fail();
		
		Assert.assertEquals(computer.get(), testComputer);
	}
	
	@Test
	public void getEmptyComputerByIdTest() {
		Optional<Computer> computer = computerDAO.getComputerById(34L);
		Assert.assertTrue(!computer.isPresent());
	}
	
	@Test
	public void getComputerByNameTest() {
		Optional<Computer> computer = computerDAO.getComputerByName("MacBook Pro 15.4 inch");
		Assert.assertTrue(computer.isPresent());
		Assert.assertEquals(computer.get(), testDataBase.findComputerById(computer.get().getId()));
	}
	
	@Test
	public void getEmptyComputerByNameTest() {
		Optional<Computer> computer = computerDAO.getComputerByName("A computer that's not present");
		Assert.assertTrue(!computer.isPresent());
	}

	
	@Test
	public void getComputerPageTest() {
		int page = 2;
		int nbByPage = 5;
		List<Computer> computers = computerDAO.getComputerPage(page, nbByPage);
		
		List<Computer> expectedComputers = testDataBase.findAllComputers()
				.stream().skip(((page-1)*nbByPage)).limit(nbByPage)
				.collect(Collectors.toList());
		Assert.assertEquals(computers, expectedComputers);
	}
	
	
	
	
	
	@Test
	public void updateComputerNameTest() {
		long id = 15L;
		
		Optional<Computer> optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue(optComputer.isPresent());
		Computer c = optComputer.get();
		 
		String newName = c.getName()+"modified";
		c.setName(newName);
		computerDAO.update(c);
		
		optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue(optComputer.isPresent());
		c = optComputer.get();
		
		Assert.assertEquals(c.getName(),newName);
	}
	
	@Test
	public void updateComputerDateIntroductionTest() {
		long id = 5L;
		
		Optional<Computer> optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue(optComputer.isPresent());
		Computer c = optComputer.get();
		
		Assert.assertTrue(c.getLdIntroduced()!=null);
		 
		LocalDate dateModified= c.getLdIntroduced().plusYears(1);
		c.setLdIntroduced(dateModified);
		computerDAO.update(c);
		
		optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue(optComputer.isPresent());
		c = optComputer.get();
		
		Assert.assertEquals(c.getLdIntroduced(),dateModified);

	}
	
	@Test(expected = NotFoundException.class)
	public void updateComputerIdNotFound() {
		Computer computer = new Computer(50, "a computer");
		computerDAO.update(computer);
	}
	
	@Test
	public void deleteComputerByIDTest() {
		long id = 20;
		
		Optional<Computer> cOptional = computerDAO.getComputerById(id);
		Assert.assertTrue(cOptional.isPresent());
		
		computerDAO.deleteComputerById(id);
		cOptional = computerDAO.getComputerById(id);
		Assert.assertTrue(!cOptional.isPresent());
	}
	
	
	
	
}
