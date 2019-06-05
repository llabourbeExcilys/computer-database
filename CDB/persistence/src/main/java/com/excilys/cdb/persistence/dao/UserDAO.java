package com.excilys.cdb.persistence.dao;

import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.core.model.User;

@Component
public class UserDAO {
	
	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));}
	
	private static Logger logger = LoggerFactory.getLogger( CompanyDAO.class );
	
    @PersistenceContext
    private EntityManager em;
    
	public Optional<User> getUserByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> r = q.from(User.class);

		q.where(cb.equal(r.get("name"), name));

		TypedQuery<User> tq = em.createQuery(q);
		
		try {
			User user = tq.getSingleResult();
			return Optional.ofNullable(user);
		} catch (NoResultException e) {
			return Optional.empty();	
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getMessage());
			return Optional.empty();
		}
	}
}
