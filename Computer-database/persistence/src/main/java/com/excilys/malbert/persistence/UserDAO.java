package com.excilys.malbert.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.malbert.core.model.QUser;
import com.excilys.malbert.core.model.User;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class UserDAO implements IUserDAO {

	@Autowired
	private EntityManagerFactory emFactory;

	@Override
	public User findByUserName(String username) {
		EntityManager em = emFactory.createEntityManager();
		JPAQuery query = new JPAQuery(em);
		QUser user = QUser.user;
		User u = query.from(user).where(user.username.eq(username))
				.uniqueResult(user);
		em.close();
		return u;
	}
}
