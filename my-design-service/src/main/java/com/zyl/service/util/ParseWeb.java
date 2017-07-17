package com.zyl.service.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zyl.base.util.InitModel;
import com.zyl.service.Initial.InitialPara;
import com.zyl.service.Interception.Interception;
import com.zyl.service.filter.Filter;
import com.zyl.service.listener.Lister;
import com.zyl.service.rule.Rule;
import com.zyl.service.servlet.Service;

/**
 *	解析配置文件,完成服务器配置.获取所有拦截器过滤器验证器监听器,获得接口,获取bean
 * @author ZhouGuoQiang
 * 2017年3月25日
 * @version 1.0
 * ParseWeb
 */
public class ParseWeb {
	/**
	 * 默认为当前工程路径下的web.xml文件
	 */
	private static String path=ParseWeb.class.getClass().getResource("/").getPath()+"/web.xml";
	
	private SAXReader reader = new SAXReader();
	private Document doc = null;
	
	@SuppressWarnings("unchecked")
	public void init()throws Exception{
		doc = reader.read(new File(path));
		
		//获取根标签
		Element root=doc.getRootElement();
		Element service=root.element("service");
		//获取端口号
		String port=service.element("port").getText();
		
		String servicePath=service.element("servicePath").getText();

		//初始化接口基础路径
		String urlPath=service.element("url").getText();
		ReaderXml.path=ParseWeb.class.getResource("/").getPath()+urlPath;
		
		//初始化bean
		for (Element ele : (List<Element>)service.elements("bean")) {
			InitModel.initModel(ParseWeb.class.getResource("/").getPath()+ele.getText());
		}
		//创建4大容器,过滤器,拦截器,监听器,验证器
		List<Filter> filters = new ArrayList<Filter>();
		Map<String, Interception> interceptions=new HashMap<>();
		List<Lister> listers = new ArrayList<Lister>();
		Map<String, Rule> rules=new HashMap<>();
		
		//获得过滤器,拦截器,监听器,验证器
		Element filtersXml=root.element("filters");
		Element interceptionsXml=root.element("interceptions");
		Element listersXml=root.element("listeners");
		Element rulesXml=root.element("rules");
		
		//获得过滤器
		if(filtersXml!=null){
			for (Element ele : (List<Element>)filtersXml.elements("filter")) {
				Class<Filter> cla=(Class<Filter>) Class.forName(ele.element("filter-class").getText());
				Constructor<Filter>  constructor =cla.getConstructor(new Class[]{String.class});
				if(ele.element("filter-path").getText()!=null||!ele.element("filter-path").getText().isEmpty()){
					Filter filter= constructor.newInstance(ele.element("filter-path").getText());
					filters.add(filter);
				}else{
					Filter filter= cla.newInstance();
					filters.add(filter);
				}
			}
		}
		
		//获得监听器
		if(listersXml!=null){
			for (Element ele : (List<Element>)listersXml.elements("listener")) {
				Class<Lister> cla=(Class<Lister>) Class.forName(ele.element("listener-class").getText());
				Constructor<Lister>  constructor =cla.getConstructor(new Class[]{String.class});
				if(ele.element("listener-path").getText()!=null||!ele.element("listener-path").getText().isEmpty()){
					Lister lister= constructor.newInstance(ele.element("listener-path").getText());
					listers.add(lister);
				}else{
					Lister lister= cla.newInstance();
					listers.add(lister);
				}
			}
		}
		
		//获得验证器
		if(rulesXml!=null){
			for (Element ele : (List<Element>)rulesXml.elements("rule")) {
				Class<Rule> cla=(Class<Rule>) Class.forName(ele.element("rule-class").getText());
				rules.put(ele.element("rule-name").getText(), cla.newInstance());
			}
		}
		
		//获得拦截器
		if(interceptionsXml!=null){
			for (Element ele : (List<Element>)interceptionsXml.elements("interception")) {
				Class<Interception> cla=(Class<Interception>) Class.forName(ele.element("interception-class").getText());
				interceptions.put(ele.element("interception-name").getText(), cla.newInstance());
			}
		}
		//初始化拦截器,验证器,监听器,过滤器
		InitialPara.init(interceptions,rules,listers,filters);
		//设置端口
		Service.setPost(Integer.parseInt(port));
		//设置服务器路径
		Service.setTempPath(servicePath);
		
		//Service.start();
	}
	
	
	public static void setPath(String path) {
		ParseWeb.path = path;
	}
}
