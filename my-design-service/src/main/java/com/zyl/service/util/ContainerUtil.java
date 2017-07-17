package com.zyl.service.util;

import com.zyl.service.container.Container;

public class ContainerUtil {
	/**
	 * 将一些全局变量加入到Container容器中,已方便管理
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @return void
	 */
	public static void setContainer(String name,Class<?> cla,String methedName) throws Exception{
		Container.container_name.put(name, cla.getName());
		Container.container_method.put(name, cla.getMethod(methedName, new Class[]{}));
	}
	
}
