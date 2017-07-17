package com.zyl.service.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zyl.base.control.UseCase;
import com.zyl.service.util.InterFace;

/**
 * 过滤器
 * @author ZhouGuoQiang
 * 2017年3月18日
 * @version 1.0
 * Filter
 */
public abstract class Filter implements Cloneable{
	protected Logger logger = Logger.getLogger(Filter.class);
	protected HttpServletRequest request;
	protected UseCase uescase;
	protected String url="/*";
	protected InterFace inter;
	
	public Filter(){}
	
	public Filter(String path){
		this.url=path;
	}
	
	public abstract UseCase doFilter()throws Exception;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public UseCase getUescase() {
		return uescase;
	}

	public void setUescase(UseCase uescase) {
		this.uescase = uescase;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public InterFace getInter() {
		return inter;
	}

	public void setInter(InterFace inter) {
		this.inter = inter;
	}

	@Override
	protected Filter clone() throws CloneNotSupportedException {
		Filter filter=(Filter) super.clone();
		return filter;
	}
}
