package com.excilys.cdb.back.dao;

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

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.back.mapper.CompanyMapper;
import com.excilys.cdb.back.model.Company;

import ch.qos.logback.classic.Logger;

@Component
public class CompanyDAO{

	private static final String SQL_SELECT_ALL_COMPANY = 
			"SELECT "
			+	"id, "
			+	"name "
			+"FROM "
			+	"company";
	
	private static final String SQL_SELECT_COMPANY_BY_ID = 
			"SELECT "
			+	"id, "
			+	"name "
			+"FROM "
			+	"company "
			+"WHERE "
			+	"company.id = ?";
	
	private static final String SQL_DELETE_COMPUTER_BY_ID = 
			"DELETE FROM "
			+	"computer "
			+"WHERE "
			+	"company_id = ?";
	
	
	private static final String SQL_DELETE_COMPANY_BY_ID =
			"DELETE FROM "
			+	"company "
			+"WHERE "
			+	"id = ?";


	
	private final CompanyMapper companyMapper;
	private final DataSource dataSource;
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(CompanyDAO.class );

    public CompanyDAO(CompanyMapper companyMapper, DataSource dataSource) {
		this.companyMapper = companyMapper;
		this.dataSource = dataSource;
	}

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));};

	// Read
	
	public List<Company> getCompanyList() {
		List<Company> resultList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
			 Statement state = conn.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
			 ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPANY);){
			
			
			while(result.next()){
				result.previous();
				Optional<Company> company = companyMapper.getCompany(result);
				if(company.isPresent())
					resultList.add(company.get());				
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
		return resultList;
	}

	public Optional<Company> getCompanyById(long idL) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPANY_BY_ID); ){	

			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			return companyMapper.getCompany(result);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
		return Optional.empty();
	}
	
	public void deleteCompanyById(long idL) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement deleteComputerPreparedStatement = conn.prepareStatement(SQL_DELETE_COMPUTER_BY_ID);
			 PreparedStatement deleteCompanyPreparedStatement = conn.prepareStatement(SQL_DELETE_COMPANY_BY_ID)){	
			
			conn.setAutoCommit(false);
			
			deleteComputerPreparedStatement.setLong(1, idL);
			deleteComputerPreparedStatement.executeUpdate();
				
			deleteCompanyPreparedStatement.setLong(1, idL);
			deleteCompanyPreparedStatement.executeUpdate();
			
			conn.commit();
			conn.setAutoCommit(true);	
			} catch (SQLException e) {
				logger.debug(e.getMessage());
				logger.error(e.getMessage());
			}
	}
	
}

