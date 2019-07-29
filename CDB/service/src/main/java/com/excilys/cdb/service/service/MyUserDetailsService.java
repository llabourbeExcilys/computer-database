package com.excilys.cdb.service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.excilys.cdb.core.model.User;
import com.excilys.cdb.persistence.dao.UserDAO;
import com.excilys.cdb.service.user.MyUserPrincipal;

@Component
public class MyUserDetailsService implements UserDetailsService {

	private UserDAO userDao;
	
	
	@Autowired
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userDao.getUserByName(userName);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(userName);
		}
		return new MyUserPrincipal(user.get());
	}
}
