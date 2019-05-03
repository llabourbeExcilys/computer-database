package back.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import back.dto.ComputerDTO;
import back.model.Company;
import back.model.Computer;
import back.model.ComputerBuilder;

public class ComputerMapper {
	
	private static Logger logger = LoggerFactory.getLogger( ComputerMapper.class );

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
			
			if (!result.next())
				return Optional.empty();
			
			Computer computer = new ComputerBuilder(result.getLong("computer_id"),
									result.getString("computer_name")).build();
			
			
			// Check if introduction date was given
			String introduced = result.getString("computer_introduced");
			if(introduced != null) {
				Timestamp t = Timestamp.valueOf(introduced);
				LocalDate locald = t.toLocalDateTime().plusDays(1).toLocalDate();
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
			logger.error("An exception occured",e);
		}
		return Optional.empty();
	}
	
	public ComputerDTO computerToDTO(Computer c) {
		return new ComputerDTO(c.getId(),
				c.getName(),
				c.getLdIntroduced(),
				c.getLdDiscontinued(),
				(c.getCompany() != null) ? c.getCompany().getId() : null,
				(c.getCompany() != null) ? c.getCompany().getName() : null);
	}
	
	public Optional<ComputerDTO> computerToDTO(Optional<Computer> c) {
		if (c.isPresent())
			return Optional.ofNullable(computerToDTO(c.get()));
		return Optional.empty();
	}
	
}
