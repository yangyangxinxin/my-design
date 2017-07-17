package com.zyl.service.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class ApplicationListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(ApplicationListener.class); 
	public void contextDestroyed(ServletContextEvent event) {
		logger.info(String.format("contextDestroyed:%s",event.getServletContext().getContextPath()));
	}
	public void contextInitialized(ServletContextEvent event) {
		logger.info(String.format("contextInitialized:%s",event.getServletContext().getContextPath()));
	}
}
