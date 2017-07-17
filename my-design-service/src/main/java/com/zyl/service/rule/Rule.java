package com.zyl.service.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 验证器抽象类,验证器一般和拦截器配合使用
 * @author ZhouGuoQiang
 * 2017年3月13日
 * @version 1.0
 * Rule
 */
public abstract class Rule implements Cloneable{
	protected Logger logger = Logger.getLogger(Rule.class);
	/**
	 * 验证器验证参数
	 */
	protected Map<String, Object> map =new HashMap<>();
	protected String msg="";

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public abstract int doIt();

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public Rule clone() throws CloneNotSupportedException {
		Rule rule=(Rule) super.clone();
		return rule;
	}

}
