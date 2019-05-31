package com.excilys.cdb.persistence.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.mapper.ComputerMapper;
import com.excilys.cdb.core.exception.ComputerCountException;
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
	

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	
	
	private static Logger logger = LoggerFactory.getLogger( CompanyDAO.class );
	
    @PersistenceContext
    private EntityManager em;

	private final ComputerMapper computerMapper;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert; 
	
	public ComputerDAO(ComputerMapper computerMapper, DataSource dataSource){
		this.computerMapper=computerMapper;
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
	
//	public int getNumberOfComputer() {
//		return jdbcTemplate.queryForObject(SQL_COUNT_ALL_COMPUTER, Integer.class);
//	}
	
	
	public long getNumberOfComputer() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Computer.class)));
		try {
			return em.createQuery(criteriaQuery).getSingleResult();
		} catch (PersistenceException pe) {
			logger.error(pe.getMessage());
			throw new ComputerCountException("Une erreur est survenue en cherchant le nombre d'ordinateur présent en base.");
		}
	}
	
	public List<Computer> getComputerList() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Computer> q = cb.createQuery(Computer.class);
		Root<Computer> c = q.from(Computer.class);
		q.select(c);

		TypedQuery<Computer> query = em.createQuery(q);
		List<Computer> results = query.getResultList();
		return results;
	}
	
	public Optional<Computer> getComputerById(long idL){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> q = cb.createQuery(Computer.class);
		Root<Computer> r = q.from(Computer.class);

		q.where(cb.equal(r.get("id"), idL));

		TypedQuery<Computer> tq = em.createQuery(q);
		
		try {
			Computer computer = tq.getSingleResult();
			return Optional.ofNullable(computer);
		} catch (NoResultException e) {
			return Optional.empty();	
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getMessage());
			return Optional.empty();
		}
	}
	
	public Optional<Computer> getComputerByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> q = cb.createQuery(Computer.class);
		Root<Computer> r = q.from(Computer.class);

		q.where(cb.equal(r.get("name"), name));

		TypedQuery<Computer> tq = em.createQuery(q);
		
		try {
			Computer computer = tq.getSingleResult();
			return Optional.ofNullable(computer);
		} catch (NoResultException e) {
			return Optional.empty();	
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getMessage());
			return Optional.empty();
		}
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
	
	@Transactional
	public void deleteComputerById(long id) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();

		CriteriaDelete<Computer> deleteComputer = cb.createCriteriaDelete(Computer.class);
		Root<Computer> rootComputer = deleteComputer.from(Computer.class);
		deleteComputer.where(cb.equal(rootComputer.get("id"), id));
		this.em.createQuery(deleteComputer).executeUpdate();
	}

}
