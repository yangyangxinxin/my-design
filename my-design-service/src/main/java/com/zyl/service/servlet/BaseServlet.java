package com.zyl.service.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 基础servlet类,用于扩展接口
 * @author lenovo
 *
 */
public  class BaseServlet extends HttpServlet{

	private static final long serialVersionUID = 7072451910389123385L;
	
	/**
	 * 活动接口信息
	 * @param request
	 * @return
	 */
	protected String getUrl(HttpServletRequest request) {
		String url = request.getServletPath();
		int pos = url.lastIndexOf(".");
		return url.substring(0, pos);
	}
	
	/**
	 * html头设置,编码设置
	 * @param response
	 */
	protected void setHeader(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Type", "text/html; charset=utf-8");
	}
	
}
