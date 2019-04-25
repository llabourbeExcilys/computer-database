package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import model.Company;
import model.Computer;
import model.dao.mapper.ComputerMapper;

@RunWith(MockitoJUnitRunner.class)
public class ComputerMapperTest {

	private ComputerMapper computerMapper = ComputerMapper.getInstance();
	
	@Mock
    private ResultSet resultSet;
	

	
	@Test
	public void testGetComputerOk() {
		
		try {
			Mockito.when(resultSet.getLong("computer_id")).thenReturn(23L);
			Mockito.when(resultSet.getString("computer_name")).thenReturn("Asus Computer");
			Mockito.when(resultSet.getString("computer_introduced")).thenReturn("1990-02-10 00:00:00");
			Mockito.when(resultSet.getString("computer_discontinued")).thenReturn("2005-10-10 00:00:00");
			Mockito.when(resultSet.getLong("company_id")).thenReturn(5L);
			Mockito.when(resultSet.getString("company_name")).thenReturn("Asus");

			Optional<Computer> optComputer = computerMapper.getComputer(resultSet);
			Computer computer = optComputer.get();
					
			Company testCompany = new Company(5L,"Asus");
			Computer testComputer = new Computer(23l,"Asus Computer");
			testComputer.setLdIntroduced(LocalDate.of(1990, 02, 10));
			testComputer.setLdDiscontinued(LocalDate.of(2005, 10, 10));
			testComputer.setCompany(testCompany);
			
			Assert.assertEquals(computer,testComputer);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetComputerEmpty() {
		
		try {
			Mockito.when(resultSet.getLong("computer_id")).thenReturn(0L);
			Mockito.when(resultSet.getString("computer_name")).thenReturn(null);
			Mockito.when(resultSet.getString("computer_introduced")).thenReturn(null);
			Mockito.when(resultSet.getString("computer_discontinued")).thenReturn(null);
			Mockito.when(resultSet.getLong("company_id")).thenReturn(0L);
			Mockito.when(resultSet.getString("company_name")).thenReturn(null);

			Optional<Computer> optComputer = computerMapper.getComputer(resultSet);
			Assert.assertTrue(optComputer.isEmpty());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
