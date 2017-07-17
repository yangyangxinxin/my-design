package com.zyl.service.filter;


import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.apache.log4j.Logger;

@WebFilter(urlPatterns = "/*.json", asyncSupported = true)
public class GlobalFilter implements Filter {
	private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(
			System.getProperty("java.io.tmpdir"));
	private static Logger logger = Logger.getLogger(GlobalFilter.class); 

	public final static String getIpAddress(HttpServletRequest request)
			throws IOException {
		// ��ȡ��������IP��ַ,���ͨ�������������͸������ǽ��ȡ��ʵIP��ַ
		String ip = request.getHeader("X-Real-IP");
//		Enumeration<String> enm = request.getHeaderNames();
//		do {
//			String item = enm.nextElement();
//			System.out.println(item+":"+request.getHeader(item));
//		} while (enm.hasMoreElements());
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Forwarded-For");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		if (request.getContentType() != null
				&& request.getContentType().startsWith("multipart/form-data")) {
			request.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT,
					MULTI_PART_CONFIG);
		}
		Cookie[] cookies = servletRequest.getCookies();
		if(cookies!=null){
			Hashtable<String, String> cookie = new Hashtable<String, String>();
			if (cookies != null) {
				for (Cookie c : cookies) {
					cookie.put(c.getName(), c.getValue());
				}
			}
			session.setAttribute("Cookie", cookie);
		}
		try {
			String device = servletRequest.getHeader("USER-AGENT")
					.toLowerCase();
			session.setAttribute("Device", device);
		} catch (Exception e) {
			logger.error(e);
		}
		session.setAttribute("IP", getIpAddress(servletRequest));
		session.setAttribute("Reponse", response);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
