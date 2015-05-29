package com.excilys.malbert.core.exception;

/**
 * Permits the exception throwing when encountering a problem in the DAO
 * 
 * @author excilys
 */
public class DAOException extends RuntimeException {
	private static final long serialVersionUID = -5330617405890158991L;

	private String message;

	public DAOException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
