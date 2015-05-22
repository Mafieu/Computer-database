package com.excilys.malbert.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "users")
public class User {
	public enum Role {
		USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
		private String role;

		Role(String role) {
			this.role = role;
		}

		public String toString() {
			return role;
		}
		
		public static Role map(String str) {
			if (str.equalsIgnoreCase("ROLE_USER")) {
				return USER;
			}
			if (str.equalsIgnoreCase("ROLE_ADMIN")) {
				return ADMIN;
			}
			return null;
		}
	}

	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "authority")
	private String authority;

	public User() {
	}

	public User(String username, String password, Role authority) {
		this.username = username;
		this.password = password;
		this.authority = authority.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole(){
		return Role.map(authority);
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(Role authority) {
		this.authority = authority.toString();
	}
	
	public void setAuthority(String authority) {
		if(Role.map(authority) != null)
			this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", authority=" + authority + "]";
	}
}
