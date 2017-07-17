package com.zyl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Properties getProperties(String path){
		Properties p=new Properties();
		InputStream is;	
		try {
			is = new FileInputStream(path);
			p.load(is);
			return p;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
