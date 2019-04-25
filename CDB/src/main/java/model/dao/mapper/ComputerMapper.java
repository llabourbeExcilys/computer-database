package model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import model.Company;
import model.Computer;

public class ComputerMapper {

	 /** Constructeur privé */
    private ComputerMapper(){}
     
    /** Instance unique non préinitialisée */
    private static ComputerMapper INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized ComputerMapper getInstance(){           
        if (INSTANCE == null)
           INSTANCE = new ComputerMapper(); 
        
        return INSTANCE;
    }	
	
	// Create a computer from a ResultSet
	public Optional<Computer> getComputer(ResultSet result) {
		try {
			Computer computer;
			computer = new Computer(result.getLong("computer_id"),
									result.getString("computer_name"));
			
			
			
			// Check if introduction date was given
			String introduced = result.getString("computer_introduced");
			if(introduced != null) {
				Timestamp t = Timestamp.valueOf(introduced);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdIntroduced(locald);
			}
			
			// Check if discontinuation date was given
			String discontinued = result.getString("computer_discontinued");
			if(discontinued != null) {
				Timestamp t = Timestamp.valueOf(discontinued);
				LocalDate locald = t.toLocalDateTime().toLocalDate();
				computer.setLdDiscontinued(locald);
			}
			
			long company_id = result.getLong("company_id");
			String company_name = result.getString("company_name");

			// Check if company id and name was given
			// Create company and set it to computer.company field
			if(company_id!=0 && company_name!=null) {
				Company company = new Company(company_id,company_name);
				computer.setCompany(company);
			}
			if(computer.getId() != 0L && computer.getName() !=null)
				return Optional.ofNullable(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
}
