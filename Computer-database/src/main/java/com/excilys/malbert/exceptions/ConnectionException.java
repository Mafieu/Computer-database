package com.excilys.malbert.exceptions;

public class ConnectionException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -5330617405890158991L;

    private String message;

    public ConnectionException(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return message;
    }
}
