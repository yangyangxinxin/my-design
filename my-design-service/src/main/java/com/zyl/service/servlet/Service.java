package com.zyl.service.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;

public class Service {
	private static String tempPath = "d:\\";
	private static int port = 8081;
	@SuppressWarnings("serial")
	private static HashMap<String, HttpServlet> servlets = new HashMap<String, HttpServlet>() {
		{
			put("*.json", new AsyServlet());
		}
	};

	public String getTempPath() {
		return tempPath;
	}

	public static void setTempPath(String tempPath) {
		Service.tempPath = tempPath;
	}

	public int getPost() {
		return port;
	}

	public static void setPost(int port) {
		Service.port = port;
	}

	public Map<String, HttpServlet> getServlets() {
		return servlets;
	}

	public void addServlet(String url, HttpServlet servlet) {
		servlets.put(url, servlet);
	}

	public void addServlet(Map<String, HttpServlet> servlets) {
		for (Entry<String, HttpServlet> key : servlets.entrySet()) {
			servlets.put(key.getKey(), key.getValue());
		}
	}
	
	public static void start() throws Exception{
		jetty.run(port, tempPath, servlets);
	}
}
