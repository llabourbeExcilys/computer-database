package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.mapper.CompanyMapper;
import com.excilys.cdb.core.model.Company;

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
			+	"company.id = :id";
	
	private static final String SQL_DELETE_COMPUTER_BY_COMPANY_ID = 
			"DELETE FROM "
			+	"computer "
			+"WHERE "
			+	"company_id = :id";
	
	
	private static final String SQL_DELETE_COMPANY_BY_ID =
			"DELETE FROM "
			+	"company "
			+"WHERE "
			+	"id = :id";


	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	
	private final CompanyMapper companyMapper;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
    public CompanyDAO(CompanyMapper companyMapper, DataSource dataSource) {
		this.companyMapper = companyMapper;
		this.jdbcTemplate =  new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// Read
	
	public List<Company> getCompanyList() {
		return jdbcTemplate.query(SQL_SELECT_ALL_COMPANY, companyMapper);
	}

	public Optional<Company> getCompanyById(long idL) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", idL);
		List<Company> companyList = namedParameterJdbcTemplate
				.query(SQL_SELECT_COMPANY_BY_ID, namedParameters, companyMapper);
		return Optional.ofNullable(companyList.isEmpty() ? null : companyList.get(0));
	}
	
	@Transactional
	public void deleteCompanyById(long idL) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", idL);
		namedParameterJdbcTemplate.update(SQL_DELETE_COMPUTER_BY_COMPANY_ID, namedParameters);
		namedParameterJdbcTemplate.update(SQL_DELETE_COMPANY_BY_ID, namedParameters);
	}
}

