package com.zyl.service.listener;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 监听器,所有监听器都需要基础此类
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0
 */
public abstract class Lister implements Cloneable{
	protected Logger logger = Logger.getLogger(Lister.class);
	
	protected HttpServletRequest request;
	
	protected String url = "/*";
	/**
	 * 监听器执行方法
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return int
	 */
	public abstract int doLister()throws Exception;

	public Lister() {
	}

	public Lister(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	protected Lister clone() throws CloneNotSupportedException {
		Lister lister=(Lister) super.clone();
		return lister;
	}
	
	
}
