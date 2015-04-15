package com.excilys.malbert.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class TestUtils {
    public static void initDB() {
	try {
	    Process p = Runtime.getRuntime().exec(
		    "./src/com/excilys/malbert/test/utils/script.sh");
	    BufferedReader input = new BufferedReader(new InputStreamReader(
		    p.getInputStream()));
	    while (input.readLine() != null)
		;
	    input.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
