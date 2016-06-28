/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-9 by Ryan.
 */
package com.ast.ast1949.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ryan
 *
 */
public class CategoryUtils {
	public static String GetCategoryCode(String key) throws IOException{
		Properties prop=new Properties();
		ClassLoader  loader  =  Thread.currentThread().getContextClassLoader(); 
		InputStream fis=loader.getResourceAsStream("category.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
}
