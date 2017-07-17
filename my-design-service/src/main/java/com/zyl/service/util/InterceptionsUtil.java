package com.zyl.service.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.zyl.service.Interception.Interception;
import com.zyl.service.container.Container;


/**
 * 拦截器工具类
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0 FilterUtil
 */
public class InterceptionsUtil {
	/**
	 * 接口拦截方法
	 * 
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return String
	 * @param request
	 * @param inter
	 * @throws Exception
	 */
	public static String interceptions(HttpServletRequest request, InterFace inter) throws Exception {
		String msg = "";
		for (String filter : inter.getValidations()) {
			//根据拦截器名字获取到拦截器
			Interception fter=null;
			if(filter!=null){
				try {
					fter = Container.getInterceptions(filter);
				} catch (Exception e) {
					fter=null;
				}
				
			}
			if(fter==null){
				continue;
			}
			fter.setRequest(request);
			int i =fter.doInterception();
			if (i != 1) {
				msg = fter.getMsg();
				return msg;
			}
		}
		return msg;
	}

	public static String feildInterceptions(HttpServletRequest request, InterFace inter,Map<String, Object> input) throws Exception{
	
		for (Entry<String, String> interceptions: inter.getValidationsMap().entrySet()) {
			Interception interception = Container.getInterceptions(interceptions.getValue().split(":")[0]);
			if(interception==null){
				continue;
			}
			request.setAttribute("name", interceptions.getKey());
			request.setAttribute("value", input.get(interceptions.getKey()));
			request.setAttribute("rule", interceptions.getValue().split(":")[1]);
			interception.setRequest(request);
			int i =interception.doInterception();
			if(i!=1){
				return interception.getMsg();
			}
		}
		return "";
	}
}
