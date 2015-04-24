package com.excilys.malbert.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperties {

    private String url;
    private String db;
    private String dbTest;
    private String option;
    private String user;
    private String passwd;

    public ConnectionProperties() throws IOException {
	getPropValues();
    }

    public void getPropValues() throws IOException {
	Properties prop = new Properties();
	String propFileName = "config.properties";

	InputStream inputStream = getClass().getClassLoader()
		.getResourceAsStream(propFileName);

	if (inputStream != null) {
	    prop.load(inputStream);
	} else {
	    throw new FileNotFoundException("property file '" + propFileName
		    + "' not found in the classpath");
	}

	user = prop.getProperty("user");
	passwd = prop.getProperty("passwd");
	db = prop.getProperty("db");
	dbTest = prop.getProperty("db-test");
	url = prop.getProperty("url");
	option = prop.getProperty("option");
    }

    public String getUrl() {
	return url;
    }

    public String getDb() {
	return db;
    }

    public String getDbTest() {
	return dbTest;
    }

    public String getOption() {
	return option;
    }

    public String getUser() {
	return user;
    }

    public String getPasswd() {
	return passwd;
    }
}
