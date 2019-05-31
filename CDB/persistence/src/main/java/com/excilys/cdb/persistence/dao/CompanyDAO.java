package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;

@Component
public class CompanyDAO{

	private static Logger logger = LoggerFactory.getLogger( CompanyDAO.class );

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}

    @PersistenceContext
    private EntityManager em;

	// Read
	
	public List<Company> getCompanyList() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Company> q = cb.createQuery(Company.class);
		Root<Company> c = q.from(Company.class);
		q.select(c);

		TypedQuery<Company> query = em.createQuery(q);
		List<Company> results = query.getResultList();
		return results;
	}

	public Optional<Company> getCompanyById(long idL) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> q = cb.createQuery(Company.class);
		Root<Company> r = q.from(Company.class);

		q.where(cb.equal(r.get("id"), idL));

		TypedQuery<Company> tq = em.createQuery(q);
		
		try {
			Company company = tq.getSingleResult();
			return Optional.ofNullable(company);
		} catch (NoResultException e) {
			return Optional.empty();	
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getMessage());
			return Optional.empty();
		}
	}
	
	@Transactional
	public void deleteCompanyById(long idL) {
		CriteriaBuilder cb = this.em.getCriteriaBuilder();

		CriteriaDelete<Computer> deleteComputer = cb.createCriteriaDelete(Computer.class);
		Root<Computer> rootComputer = deleteComputer.from(Computer.class);
		deleteComputer.where(cb.equal(rootComputer.get("company").get("id"), idL));
		this.em.createQuery(deleteComputer).executeUpdate();

		CriteriaDelete<Company> deleteCompany = cb.createCriteriaDelete(Company.class);
		Root<Company> rootCompany = deleteCompany.from(Company.class);
		deleteCompany.where(cb.equal(rootCompany.get("id"), idL));
		this.em.createQuery(deleteCompany).executeUpdate();
	}
}


























