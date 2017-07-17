package com.zyl.service.servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;
import com.zyl.base.util.InitModel;
import com.zyl.base.util.JSONUtil;
import com.zyl.service.filter.Filters;
import com.zyl.service.filter.GlobalFilter;
import com.zyl.service.util.InterFace;
import com.zyl.service.util.InterceptionsUtil;
import com.zyl.service.util.ReaderXml;

/**
 * servlet基本操作方法类
 * @author lenovo
 *
 */
public class HandlerServlet {
	
	private static Logger logger = Logger.getLogger(AsyServlet.class);
	
	/**
	 * 根据request获得url(去除".???");
	 * @param request
	 * @return
	 */
	private static String getUrl(HttpServletRequest request) {
		String url = request.getServletPath();
		int pos = url.lastIndexOf(".");
		return url.substring(0, pos);
	}
	/**
	 * 输出设置头(html)
	 * @param response
	 */
	private static void setHeader(HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Type", "text/html; charset=utf-8");
	}
	
	/**
	 * 获得接口信息
	 * @param request
	 * @param response
	 * @return Interface
	 * @throws Exception
	 */
	public static InterFace getInterFace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//setHeader(response);
		try {
			//使用工具类rederXml中的getinterface方法获得Interface对象
			InterFace inter = new ReaderXml().getInterFace(getUrl(request));
			//将Interface对象放入当前回话中
			request.setAttribute("interFace", inter);
			return inter;
		} catch (Exception e) {
			logger.error(e);
			//如果解析错误给出提示
			response.getWriter().println("{\"res\":-1,\"msg\":\"该配置文件找不到或配置文件错误\"}");
			return null;
		}
	}
	
	/**
	 * 接口拦截判断,该接口是否应该被拦截
	 * @param inter
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static boolean InterceptionsUrl(InterFace inter,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(inter.getValidations()!=null){
			String msg=InterceptionsUtil.interceptions(request, inter);
			if(msg!=null&&!msg.isEmpty()){
				response.getWriter().println("{\"res\":-1,\"msg\":\""+msg+"\"}");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 执行doget方法
	 * @param inter
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void doGet(InterFace inter,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.getWriter().println("<form method='post'>");
		for (String input : inter.getInput()) {
			response.getWriter().println(input+":<input name='"+input+"' /><br/>");	
		}
		response.getWriter().println("<button type='sumbit'>sumbit</button>");
		response.getWriter().println("</form>");
		response.getWriter().flush();
	}
	
	/**
	 * 根据接口信息获得model
	 * @param inter
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static Model getModel(InterFace inter,HttpServletRequest request,HttpServletResponse response)throws Exception{
		//获取Model
		Model obj=InitModel.getModel(inter.getModleName());
		//获得外键model
		obj.setForeignKeyModel(inter.getForeignKeyModel());
		//获取限定条件
		String sql=inter.getCondition();
		//输入参数填入model
		Map<String, Object> input = new HashMap<>();
		for (String field : inter.getInput()) {
//			//判断限定参数中是否有输入条件
//			if(sql!=null&&sql.contains("${"+field+"}")){
//				String temp="'"+request.getParameter(field)+"'";
//				sql=sql.replace("${"+field+"}", temp);
//			}
			//判断是否是主键,如果是则设为id
			if(field.equals(obj.getPrimayKey())){
				String guid =request.getParameter(obj.getPrimayKey());
				if(guid!=null&&!guid.isEmpty()){
					obj.setId(guid);
				}
			}
			if(request.getParameter(field)!=null&&!request.getParameter(field).isEmpty()){
				input.put(field, request.getParameter(field));
				obj.setValue(field, request.getParameter(field));
			}else{
				input.put(field, "");
				obj.setValue(field, "");
			}
		}
		//obj.setModelValue(input);
		//输入参数传入
		obj.setInputs(input);
		obj.setIp(GlobalFilter.getIpAddress(request));
		obj.setOutputs(inter.getOutput());
		obj.setSql(sql);
		return obj;
	}
	
	/**
	 * 拦截器拦截验证
	 * @param inter
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static boolean InterceptionsField(InterFace inter,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		if(inter.getValidationsMap()!=null){
			String msg=InterceptionsUtil.feildInterceptions(request, inter, model.getModelValue());
			if(msg!=null&&!msg.isEmpty()){
				response.getWriter().println("{\"res\":-1,\"msg\":\""+msg+"\"}");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * psot方法执行
	 * @param inter
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void doPost(InterFace inter,HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
		
		//获取接口方法调用
		Class<UseCase> useCase=(Class<UseCase>) Class.forName(inter.getControlPackage());
		
		UseCase u=useCase.newInstance();
		//放入相关参数
		u.setInput(model.getInputs());
		u.setModel(model);
		u.setSql(model.getSql());
		//获取页码
		if(request.getParameter("pageNum")!=null){
			try {
				u.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
			} catch (Exception e) {
				u.setPageNum(1);
			}
		}else{
			u.setPageNum(1);
		}
		//过滤器执行
		u=Filters.doFilter(request, u,inter);
		//这里是为了缓存而存在的,如果有加入缓存,那么此处便会生效
		if(u.getState()){
			response.getWriter().println(JSONUtil.ToJson(u));
			request.setAttribute("useCase", u);
			request.setAttribute("model", model);
			return;
		}
		//调用方法
		Method methed=useCase.getMethod(inter.getMothed(), new Class[]{});
		methed.invoke(u, new Object[]{});
		//传出结果
		response.getWriter().println(JSONUtil.ToJson(u));
		//将参数放入request中,以便监听器使用
		request.setAttribute("useCase", u);
		request.setAttribute("model", model);
	}
}




















