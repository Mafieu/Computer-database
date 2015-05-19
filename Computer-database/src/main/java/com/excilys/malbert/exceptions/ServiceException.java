package com.excilys.malbert.exceptions;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;

	public ServiceException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
