package com.excilys.malbert.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.malbert.core.model.User;
import com.excilys.malbert.persistence.IUserDAO;

@Service("userDetailsService")
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private IUserDAO userDao;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthorities(user
				.getAuthority().toString());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> buildUserAuthorities(String role) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		List<GrantedAuthority> res = new ArrayList<GrantedAuthority>(
				authorities);
		return res;
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(
			User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), true, true, true, true,
				authorities);
	}
}
