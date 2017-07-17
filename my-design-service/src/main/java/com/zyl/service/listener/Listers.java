package com.zyl.service.listener;

import javax.servlet.http.HttpServletRequest;

import com.zyl.service.container.Container;

/**
 * 监听器监听方法实现
 * @author ZhouGuoQiang
 * 2017年3月13日
 * @version 1.0
 * Listers
 */
public class Listers {
	
	/**
	 * 监听接口
	 * @author ZhouGuoQiang
	 * 2017年3月13日
	 * @return void
	 */
	public static void lister(HttpServletRequest request) throws Exception{
		for (Lister lister : Container.listers) {
			if (match(request.getServletPath(), lister.getUrl())) {
				Lister newLister=lister.clone();
				newLister.setRequest(request);
				newLister.doLister();
			}
		}
	}
	
	/**
	 * 监听器规则验证
	 * @author ZhouGuoQiang
	 * 2017年3月13日
	 * @return boolean
	 */
	// /* *.json /User/* /User/*/*.json User/login.json */User/*
	public static boolean match(String url, String path) {
		if (path.equals("/*")) {
			return true;
		}
		if (path.equals(url)) {
			return true;
		}
		return false;
	}
}
