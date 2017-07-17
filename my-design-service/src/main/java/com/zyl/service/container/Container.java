package com.zyl.service.container;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyl.service.Interception.Interception;
import com.zyl.service.filter.Filter;
import com.zyl.service.listener.Lister;
import com.zyl.service.rule.Rule;


/**
 * 服务器容器,将一些全局属性尽量都放入其中,或是提供方法放于container_method与container_name中,以方便管理
 * @author ZhouGuoQiang
 * 2017年3月10日
 * @version 1.0
 * Container
 */
public class Container {
	
	/**
	 * 容器提供类,容器获取接口
	 */
	public static Map<String, Method> container_method=new HashMap<>();
	/**
	 * 容器名称,容器提供类
	 */
	public static Map<String, String> container_name=new HashMap<>();
	
	/**
	 * 拦截器容器
	 */
	public static Map<String, Interception>interceptions = new HashMap<>();
	/**
	 * 验证器容器
	 */
	public static Map<String, Rule> rules = new HashMap<>();
	/**
	 * 监听器容器
	 */
	public static List<Lister> listers =new ArrayList<>();
	/**
	 * 过滤器容器
	 */
	public static List<Filter> filters =new ArrayList<>();
	
	/**
	 * 根据名称来获得容器中的值
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @return Object
	 */
	public static Object getContainer(String name){
		try {
			Class<?> cla=Class.forName(container_name.get(name));
			Method mothod=container_method.get(name);
			return	mothod.invoke(cla.newInstance(), new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Interception getInterceptions(String name) throws Exception{
		if(name==null){
			return null;
		}
		return interceptions.get(name).clone();
	}

	public static Rule getRules(String name) throws Exception{
		if(name==null){
			return null;
		}
		return	rules.get(name).clone();
	}

}
