package com.zyl.service.servlet;

import java.util.HashMap;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.zyl.service.filter.GlobalFilter;
import com.zyl.service.listener.ApplicationListener;
import com.zyl.service.listener.SessionListener;


public class jetty {
	public static void run(int port, String tempPath, HashMap<String, HttpServlet> servlets) throws Exception {
		Server server = new Server(port);
		HandlerList handlers = new HandlerList();
		ResourceHandler resourceHandler = new ResourceHandler();
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.setAttribute("org.eclipse.jetty.webapp.basetempdir", tempPath);
		for (String key : servlets.keySet()) {
			context.addServlet(new ServletHolder(servlets.get(key)), key);
		}
		context.addEventListener(new ApplicationListener());
		context.getSessionHandler().addEventListener(new SessionListener());
		context.addFilter(GlobalFilter.class, "/*", null);
		handlers.setHandlers(new Handler[] { resourceHandler, context });
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}
