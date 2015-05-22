package com.excilys.malbert.persistence;

import com.excilys.malbert.core.model.User;

public interface IUserDAO {
	User findByUserName(String username);
}
