package com.zyl.service.Initial;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zyl.service.Interception.Interception;
import com.zyl.service.Interception.LinkWithModel;
import com.zyl.service.container.Container;
import com.zyl.service.filter.DefaultFilter;
import com.zyl.service.filter.Filter;
import com.zyl.service.listener.Lister;
import com.zyl.service.listener.VisitLister;
import com.zyl.service.rule.Rule;
import com.zyl.service.rule.VerificationModel;


/**
 * 初始化监听器,拦截器,验证器
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0 InitialPara
 */
public class InitialPara {
	private static Logger logger = Logger.getLogger(InitialPara.class);
	/**
	 * 初始化监听器,拦截器,验证器
	 * 
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return void
	 * @param filters
	 * @param rules
	 * @param listers
	 */
	public static void init(Map<String, Interception> interceptions, Map<String, Rule> rules, List<Lister> listers,List<Filter> filters) {
		//接口访问记录监听器加入
		Container.listers.add(new VisitLister());
		//默认拦截器
		Container.interceptions.put("LinkWithModel", new LinkWithModel());
		//默认验证器
		Container.rules.put("VerificationModel", new VerificationModel());
		//默认过滤器
		Container.filters.add(new DefaultFilter());
		if(interceptions!=null){
			for (Entry<String, Interception> key : interceptions.entrySet()) {
				Container.interceptions.put(key.getKey(), key.getValue());
			}
		}
		logger.info("拦截器启动");
		if(rules!=null){
			for (Entry<String, Rule> key : rules.entrySet()) {
				Container.rules.put(key.getKey(), key.getValue());
			}
		}
		logger.info("验证器启动");
		if(listers!=null){
			for (Lister lister : listers) {
				Container.listers.add(lister);
			}
		}
		logger.info("监听器启动");
		if(filters!=null){
			for (Filter filter : filters) {
				Container.filters.add(filter);
			}
		}
		logger.info("过滤器启动");
	}
}
