package com.excilys.malbert.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    public String[] getPropValues() throws IOException {

	String[] result = new String[6];
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

	result[0] = prop.getProperty("user");
	result[1] = prop.getProperty("passwd");
	result[2] = prop.getProperty("db");
	result[3] = prop.getProperty("db-test");
	result[4] = prop.getProperty("url");
	result[5] = prop.getProperty("option");

	return result;
    }
}
