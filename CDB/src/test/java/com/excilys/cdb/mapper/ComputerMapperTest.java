package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.cdb.back.mapper.ComputerMapper;
import com.excilys.cdb.back.model.Company;
import com.excilys.cdb.back.model.Computer;
import com.excilys.cdb.back.model.ComputerBuilder;
import com.excilys.cdb.config.AppConfig;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ComputerMapperTest {

	@Autowired
	private ComputerMapper computerMapper;
	
	@Mock
    private ResultSet resultSet;
	
	@Before
	public void setup() {
	        MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void mapRowTest() {
		
		try {
			Mockito.when(resultSet.getLong("computer_id")).thenReturn(23L);
			Mockito.when(resultSet.getString("computer_name")).thenReturn("Asus Computer");
			Mockito.when(resultSet.getString("computer_introduced")).thenReturn("1990-02-10 00:00:00");
			Mockito.when(resultSet.getString("computer_discontinued")).thenReturn("2005-10-10 00:00:00");
			Mockito.when(resultSet.getLong("company_id")).thenReturn(5L);
			Mockito.when(resultSet.getString("company_name")).thenReturn("Asus");
			Mockito.when(resultSet.next()).thenReturn(true);

			Computer computer = computerMapper.mapRow(resultSet,0);
					
			Company testCompany = new Company(5L,"Asus");
			Computer testComputer = new ComputerBuilder(23l,"Asus Computer")
					.withIntroductionDate(LocalDate.of(1990, 02, 10))
					.withdiscontinuationDate(LocalDate.of(2005, 10, 10))
					.withCompany(testCompany).build();
			
			Assert.assertEquals(computer,testComputer);

		} catch (SQLException e) {
			Assert.fail();
		}
	}
	
}
