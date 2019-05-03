package controller;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;

import back.controller.Controller;
import back.model.Company;
import back.service.Service;

//@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
	
	@Mock
	private Service service;
	
	private Controller controller = Controller.getInstance();
	
	public void testAddComputer() {
		String name = "My Netronics computer";
		LocalDate intD = LocalDate.of(1990, 12, 12);
		LocalDate disD = LocalDate.of(2000, 04, 24);
		Company company = new Company(4,"Netronics");
		
		Mockito.when(service.addComputer(name, intD, disD, Optional.ofNullable(company.getId()))).thenReturn(21L);
		
		Controller.setService(service);
		
		long id = controller.addComputer(name, 
										Optional.ofNullable(intD.toString()),
										Optional.ofNullable(disD.toString()),
										Optional.ofNullable(""+company.getId()));
		
		Assert.assertEquals(21L, id);
		
		
	}
	
	
}
