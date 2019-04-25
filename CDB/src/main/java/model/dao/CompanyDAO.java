package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.NotFoundException;
import model.Company;
import model.dao.mapper.CompanyMapper;

public class CompanyDAO extends DAO{

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
	
    private CompanyDAO(){
		companyMapper = CompanyMapper.getInstance();
    }
     
    public static synchronized CompanyDAO getInstance(){
        if (INSTANCE == null)
        	INSTANCE = new CompanyDAO(); 
        return INSTANCE;
    }
	

	// Read
	
	public List<Company> getCompanyList() {
		List<Company> resultList = new ArrayList<Company>();
		
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 Statement state = conn.createStatement();) {
			Class.forName(driver);

			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPANY);
			
			while(result.next()){
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
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPANY_BY_ID); ){	

			Class.forName(driver);
			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			boolean next = result.next();
			if(!next)
				throw new NotFoundException("Id not found");
			
			return companyMapper.getCompany(result);
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}


	
}
