package back.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import back.dto.CompanyDTO;
import back.model.Company;

public class CompanyMapper {
	
	/** Constructeur privé */
    private CompanyMapper(){}
     
    /** Instance unique non préinitialisée */
    private static CompanyMapper INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized CompanyMapper getInstance(){           
        if (INSTANCE == null)
        	INSTANCE = new CompanyMapper(); 
        
        return INSTANCE;
    }
	
	// Create a company from a ResultSet
	public Optional<Company> getCompany(ResultSet result) {
		try {
			
			if (!result.next())
				return Optional.empty();
			
			long id = result.getLong("id");
			String name = result.getString("name");
			
			if(id != 0 && name != null)
				return Optional.ofNullable(new Company(id,name));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public CompanyDTO companyToDto(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public Optional<CompanyDTO> companyToDto(Optional<Company> cOptional) {
		if (cOptional.isPresent())
			return Optional.ofNullable(companyToDto(cOptional.get()));
		return Optional.empty();
	}

}
