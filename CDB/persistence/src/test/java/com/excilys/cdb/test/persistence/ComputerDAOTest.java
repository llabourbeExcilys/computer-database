package com.excilys.cdb.test.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

import com.excilys.cdb.core.exception.ComputerNotFoundException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.core.model.ComputerBuilder;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {

	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private TestDatabase testDataBase;
	
	@Before
	public void setUp() { 
		try {
			if(testDataBase!=null)
				testDataBase.reload();
			else testDataBase.reload();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addComputerTest() {
		long id = 21;
		
		Computer c = new ComputerBuilder(21, "My Netronics computer2")
				.withIntroductionDate(LocalDate.of(1990, 12, 12))
				.withdiscontinuationDate(LocalDate.of(2000, 04, 24))
				.withCompany(new Company(4,"Netronics"))
				.build();
		
		computerDAO.addComputer(c);
		
		Optional<Computer> cReturned = computerDAO.getComputerById(id);
		
		if(!cReturned.isPresent())
			Assert.fail();
		
		Assert.assertEquals("Test de l'ajout d'un computer",c,cReturned.get());
	}
	
	@Test
	public void getAllComputerTest() {
		List<Computer> ComputerList = computerDAO.getComputerList();
		List<Computer> testComputerList = testDataBase.findAllComputers();
		Assert.assertEquals("La liste des computer n'est pas identique à la liste de test.",testComputerList,ComputerList);	
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
		
		Assert.assertEquals("Le getById ne renvoie pas un computer identique a celui de test",
				computer.get(), testComputer);
	}
	
	@Test
	public void getEmptyComputerByIdTest() {
		long id = 34L;
		Optional<Computer> computer = computerDAO.getComputerById(id);
		Assert.assertTrue("Il ne devrait pas y a avoir de computer d'id "+id,!computer.isPresent());
	}
	
	@Test
	public void getComputerByNameTest() {
		Optional<Computer> computer = computerDAO.getComputerByName("MacBook Pro 15.4 inch");
		Assert.assertTrue("Il n'existe pas de computer avec ce name dans la base de test",
				computer.isPresent());
		Assert.assertEquals("Le computer avec ce name ne correspond pas à celui de test.",
				computer.get(), testDataBase.findComputerById(computer.get().getId()));
	}
	
	@Test
	public void getEmptyComputerByNameTest() {
		Optional<Computer> computer = computerDAO.getComputerByName("A computer that's not present");
		Assert.assertTrue("Il ne devrait pas exister un computer avec ce name.",
				!computer.isPresent());
	}

	
	@Test
	public void getComputerPageTest() {
		int page = 2;
		int nbByPage = 5;
		List<Computer> computers = computerDAO.getComputerPage(page, nbByPage,SortingField.ID,SortingOrder.ASC);
		
		List<Computer> expectedComputers = testDataBase.findAllComputers().stream()
				.skip(((page-1)*nbByPage))
				.limit(nbByPage)
				.collect(Collectors.toList());
		Assert.assertEquals("Les computers de la page ne correspondent pas."
				,expectedComputers,computers);
	}
	
	
	
	
	
	@Test
	public void updateComputerNameTest() {
		long id = 15L;
		
		Optional<Computer> optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue("Il n'existe pas de computer avec l'id "+id,
				optComputer.isPresent());
		Computer c = optComputer.get();
		 
		String newName = c.getName()+"modified";
		c.setName(newName);
		computerDAO.update(c);
		
		optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue("Il n'existe plus de computer avec l'id "+id,
				optComputer.isPresent());
		c = optComputer.get();
		
		Assert.assertEquals("Les computer ne sont pas identiques."
				,c.getName(),newName);
	}
	
	@Test(expected = ComputerNotFoundException.class)
	public void updateComputerIdNotFound() {
		Computer computer = new ComputerBuilder(50, "a computer").build();
		computerDAO.update(computer);
	}
	
	@Test
	public void updateComputerDateIntroductionTest() {
		long id = 5L;
		
		Optional<Computer> optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue("Le computer d'id "+id+" n'est pas present",
				optComputer.isPresent());
		Computer c = optComputer.get();
		
		Assert.assertTrue("La date d'introduction ne devrait pas etre null",
				c.getIntroduced()!=null);
		 
		LocalDate dateModified= c.getIntroduced().plusYears(1);
		c.setIntroduced(dateModified);
		computerDAO.update(c);
		
		optComputer = computerDAO.getComputerById(id);
		Assert.assertTrue("Le computer d'id \"+id+\" n'est pas present",
				optComputer.isPresent());
		c = optComputer.get();
		
		Assert.assertEquals("La date d'introduction n'a pas été correctement modifiée."
				,c.getIntroduced(),dateModified);

	}
	

	
	@Test
	public void deleteComputerByIDTest() {
		long id = 20;
		
		Optional<Computer> cOptional = computerDAO.getComputerById(id);
		Assert.assertTrue("Le computer d'id "+id+" devrait etre présent",
				cOptional.isPresent());
		
		computerDAO.deleteComputerById(id);
		cOptional = computerDAO.getComputerById(id);
		Assert.assertTrue("Le computer d'id "+id+" aurait du etre supprimé.",
				!cOptional.isPresent());
	}
	
	
	@Test
	public void deleteComputerByIdWithIdNotFoundTest() {
		long id = 21;
		Optional<Computer> cOptional = computerDAO.getComputerById(id);
		Assert.assertTrue("Le computer d'id "+id+" ne devrait etre présent",
				!cOptional.isPresent());
	}
	
	
	
}
