package controller;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.back.controller.Controller;
import com.excilys.cdb.back.dto.ComputerDTO;
import com.excilys.cdb.back.model.Company;
import com.excilys.cdb.back.model.Computer;
import com.excilys.cdb.back.model.ComputerBuilder;
import com.excilys.cdb.back.service.Service;

//@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
	
	@Mock
	private Service service;
	
	@Autowired
	private Controller controller;// = Controller.getInstance();
	
	public void testAddComputer() {
		String name = "My Netronics computer";
		LocalDate intD = LocalDate.of(1990, 12, 12);
		LocalDate disD = LocalDate.of(2000, 04, 24);
		Company company = new Company(4,"Netronics");
		
		
		Mockito.when(service.addComputer(new ComputerBuilder(-1L,"name").build())).thenReturn(21L);
		
		controller.setService(service);
		
		long id = controller.addComputer(name, 
										Optional.ofNullable(intD.toString()),
										Optional.ofNullable(disD.toString()),
										Optional.ofNullable(""+company.getId()));
		
		Assert.assertEquals(21L, id);
		
		
	}
	
	
}
