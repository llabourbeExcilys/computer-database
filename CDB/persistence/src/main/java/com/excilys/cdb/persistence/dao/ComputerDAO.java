package com.excilys.cdb.persistence.dao;

import java.util.List;
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
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.core.exception.ComputerCountException;
import com.excilys.cdb.core.exception.ComputerNotFoundException;
import com.excilys.cdb.core.model.Computer;

@Component
public class ComputerDAO {	

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	
	private static Logger logger = LoggerFactory.getLogger( CompanyDAO.class );
	
    @PersistenceContext
    private EntityManager em;

	// Create	

	@Transactional
	public long addComputer(Computer computer) {
		 em.persist(computer);
		 return computer.getId();
	}


	// Read
	
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> q = cb.createQuery(Computer.class);
		Root<Computer> c = q.from(Computer.class);
		q.select(c);
		
		if(order.equals(SortingOrder.ASC)) {
			switch (field) {
				case COMPANY: q.orderBy(cb.asc(c.get("company").get("id")));
					break;
				default: q.orderBy(cb.asc(c.get(field.getIdentifier())));
					break;
			}
		}else{
			switch (field) {
				case COMPANY: q.orderBy(cb.desc(c.get("company").get("id")));
					break;
				default: q.orderBy(cb.desc(c.get(field.getIdentifier())));
					break;
			}
		}
		
		TypedQuery<Computer> query = em.createQuery(q);
		query.setFirstResult((page-1)*nbByPage).setMaxResults(nbByPage);
		List<Computer> results = query.getResultList();
		return results;
	}
	
	// Update
	
	@Transactional
	public void update(Computer c) {
		if (!getComputerById(c.getId()).isPresent()) 
			throw new ComputerNotFoundException("L'ordinateur à modifier n'existe pas.");
		
		em.merge(c);
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
