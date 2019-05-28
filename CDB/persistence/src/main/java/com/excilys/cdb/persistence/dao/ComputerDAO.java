package com.excilys.cdb.persistence.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.mapper.ComputerMapper;
import com.excilys.cdb.core.exception.ComputerNotFoundException;
import com.excilys.cdb.core.model.Computer;

@Component
public class ComputerDAO {
	
	private static final String SQL_SELECT_ALL_COMPUTER = 
			"SELECT "
			+	"C.id AS computer_id, "
			+	"C.name AS computer_name, "
			+	"C.introduced AS computer_introduced, "
			+	"C.discontinued AS computer_discontinued, "
			+	"B.id AS company_id, "
			+	"B.name AS company_name "
			+"FROM "
			+	"computer C LEFT JOIN company B "
			+"ON "
			+	"C.company_id = B.id ";
	
	private static final String SQL_COUNT_ALL_COMPUTER = 
			"SELECT COUNT(*) AS count from computer";

	private static final String SQL_SELECT_COMPUTER_BY_ID =  
			SQL_SELECT_ALL_COMPUTER
			+"WHERE "
			+ 	"C.id = :id ";
	
	private static final String SQL_SELECT_COMPUTER_BY_NAME = 
			SQL_SELECT_ALL_COMPUTER+
			"WHERE "
			+	"C.name = :name";
	
	private static final String SQL_SELECT_COMPUTER_PAGE = 
			SQL_SELECT_ALL_COMPUTER
			+"ORDER BY "
			+	"%field IS NULL, %field %order "
			+"LIMIT "
			+	":limit "
			+"OFFSET "
			+	":offset ";
	
	private static final String SQL_UPDATE_COMPUTER = 
			"UPDATE "
			+ 	"computer " 
		    +"SET "
		    +	"name = :name , "
			+	"introduced = :introduced , "
			+	"discontinued = :discontinued , "
			+	"company_id = :company_id "
			+"WHERE "
			+	"id = :id ";
	
	private static final String SQL_DELETE_COMPUTER_BY_ID = 
			"DELETE FROM "
			+	"computer "
			+"WHERE "
			+	"id = :id ";

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}

	private final ComputerMapper computerMapper;
	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert; 
	
	public ComputerDAO(ComputerMapper computerMapper, DataSource dataSource){
		this.computerMapper=computerMapper;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("computer").usingGeneratedKeyColumns("id");
	}
     
	// Create	

	public long addComputer(Computer computer) {
		 Map<String, Object> parameters = new HashMap<String, Object>();
		 parameters.put("name", computer.getName());
		 parameters.put("introduced", computer.getLdIntroduced() != null ? Date.valueOf(computer.getLdIntroduced()) : null);
		 parameters.put("discontinued", computer.getLdDiscontinued() != null ? Date.valueOf(computer.getLdDiscontinued()) : null);
		 parameters.put("company_id", computer.getCompany() != null ? computer.getCompany().getId() : null);
		return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	}


	// Read
	
	public int getNumberOfComputer() {
		return jdbcTemplate.queryForObject(SQL_COUNT_ALL_COMPUTER, Integer.class);
	}
	
	public List<Computer> getComputerList() {
		return jdbcTemplate.query(SQL_SELECT_ALL_COMPUTER, computerMapper);
	}
	
	public Optional<Computer> getComputerById(long idL){
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", idL);
		List<Computer> computerList = namedParameterJdbcTemplate
				.query(SQL_SELECT_COMPUTER_BY_ID, namedParameters, computerMapper);
		return Optional.ofNullable(computerList.isEmpty() ? null : computerList.get(0));
	}
	
	public Optional<Computer> getComputerByName(String name) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", name);
		List<Computer> computerList = namedParameterJdbcTemplate
				.query(SQL_SELECT_COMPUTER_BY_NAME,namedParameters,computerMapper);
		return Optional.ofNullable(computerList.isEmpty() ? null : computerList.get(0));
	}

	public List<Computer> getComputerPage(int page, int nbByPage, SortingField field, SortingOrder order) {
		String requestString = SQL_SELECT_COMPUTER_PAGE
				.replace("%field", field.getIdentifier())
				.replace("%order", order.name());
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("OrderToReplace", order.name())
				.addValue("offset", (page-1)*nbByPage)
				.addValue("limit", nbByPage);
		return namedParameterJdbcTemplate.query(requestString,namedParameters,computerMapper);
	}
	
	// Update
	
	public void update(Computer c) {
		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("id", c.getId())
				.addValue("name", c.getName())
				.addValue("introduced", c.getLdIntroduced() != null ? Date.valueOf(c.getLdIntroduced()) : null)
				.addValue("discontinued", c.getLdDiscontinued() != null ? Date.valueOf(c.getLdDiscontinued()) : null)
				.addValue("company_id", c.getCompany() != null ? c.getCompany().getId() : null);
		
		int nbOfRowAffected = namedParameterJdbcTemplate.update(SQL_UPDATE_COMPUTER, namedParameters);	
		if(nbOfRowAffected==0)
			throw new ComputerNotFoundException("La requête n'a rien modifié dans la base de donnée.");
	}

	// Delete
	
	public void deleteComputerById(long id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		namedParameterJdbcTemplate.update(SQL_DELETE_COMPUTER_BY_ID, namedParameters);
	}

}
