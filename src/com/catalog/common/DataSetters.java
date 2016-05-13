package com.catalog.common;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DataSetters {
	
	public static String pValue;

//	public static String PropertiesPath="/Users/Simon/Documents/Selenium/Workspace/com.catalogobjct.selenium/src/main/resources/config.properties";

	public static Logger logger= Logger.getLogger(DataSetters.class);
	public String propertiesReader(String file, String key) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("./config.properties");
		prop.load(input);
		String outVale = prop.getProperty(key);
		return outVale;
	}

}