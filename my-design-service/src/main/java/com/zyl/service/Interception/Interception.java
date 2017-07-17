package com.zyl.service.Interception;

import javax.servlet.http.HttpServletRequest;

import com.zyl.service.rule.Rule;


/**
 * 拦截器
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0
 */
public abstract class Interception implements Cloneable{
	/**
	 * 拦截器参数通过request来传递
	 */
	protected HttpServletRequest request;

	/**
	 * 拦截提示信息
	 */
	protected String msg;
	
	/**
	 * 如果该拦截器是一个验证参数的拦截器,则需要穿入该参数
	 */
	protected Rule rule;

	/**
	 * 执行拦截方法
	 * 
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return int
	 * @return
	 */
	public abstract int doInterception()throws Exception;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@Override
	public Interception clone() throws CloneNotSupportedException {
		Interception interception=(Interception) super.clone();
		return interception;
	}
	
	
}
