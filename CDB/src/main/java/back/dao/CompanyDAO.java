package back.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.sql.DataSource;

import back.connection.ConnexionManager;
import back.mapper.CompanyMapper;
import back.model.Company;

public class CompanyDAO{

	private final static String SQL_SELECT_ALL_COMPANY = 
			"SELECT "
			+	"* "
			+"FROM "
			+	"company";
	private final static String SQL_SELECT_COMPANY_BY_ID = 
			"SELECT "
			+	"* "
			+"FROM "
			+	"company "
			+"WHERE "
			+	"company.id = ?";

	private static CompanyMapper companyMapper;
    private static CompanyDAO INSTANCE = null;
	private static DataSource dataSource;

    static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));};

    
    
    private CompanyDAO(){
		companyMapper = CompanyMapper.getInstance();
		dataSource = ConnexionManager.getDataSource();
    }
     
    public static synchronized CompanyDAO getInstance(){
        if (INSTANCE == null)
        	INSTANCE = new CompanyDAO(); 
        return INSTANCE;
    }
	

	// Read
	
	public List<Company> getCompanyList() {
		List<Company> resultList = new ArrayList<Company>();
		
		try (Connection conn = dataSource.getConnection();
			 Statement state = conn.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);){
			
			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPANY);
			
			while(result.next()){
				result.previous();
				Optional<Company> company = companyMapper.getCompany(result);
				if(company.isPresent())
					resultList.add(company.get());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Optional<Company> getCompanyByID(long idL) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPANY_BY_ID); ){	

			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			return companyMapper.getCompany(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}


	
}
