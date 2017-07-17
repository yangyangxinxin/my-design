package com.zyl.service.servlet;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;
import com.zyl.base.util.JSONUtil;
import com.zyl.service.listener.Listers;
import com.zyl.service.util.InterFace;

import org.apache.log4j.Logger;

@MultipartConfig
@WebSocket
public class AsyServlet extends BaseServlet {
	private static final long serialVersionUID = -7961142381908269982L;
	private static Logger logger = Logger.getLogger(AsyServlet.class);

//	private String getUrl(HttpServletRequest request) {
//		String url = request.getServletPath();
//		int pos = url.lastIndexOf(".");
//		return url.substring(0, pos);
//	}
//
//	private void setHeader(HttpServletResponse response) {
//		response.setCharacterEncoding("utf-8");
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Expires", "0");
//		response.setStatus(HttpServletResponse.SC_OK);
//		response.setHeader("Content-Type", "text/html; charset=utf-8");
//	}

	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setHeader(response);
		logger.info("action:"+getUrl(request));
		try {
			//获取接口
			InterFace inter=HandlerServlet.getInterFace(request, response);
			if(inter==null){
				return;
			}
			//拦截器拦截
			if(!HandlerServlet.InterceptionsUrl(inter, request, response)){
				return;
			}
			//判断是否有传入参数
			if(inter.getInput()==null||inter.getInput().size()==0){
				doPost(request,response);
				return;
			}
			//接口执行
			HandlerServlet.doGet(inter, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		setHeader(response);
		try {
			//获取接口
			InterFace inter=HandlerServlet.getInterFace(request, response);
			if(inter==null){
				return;
			}
			//获取model
			Model obj=HandlerServlet.getModel(inter, request, response);
			//拦截器拦截
			if(!HandlerServlet.InterceptionsField(inter, request, response, obj)){
				return;
			}
			
			//接口执行
			HandlerServlet.doPost(inter, request, response, obj);
			//监听器监听
			Listers.lister(request);
		} catch (Exception e) {
			UseCase u=new UseCase();
			u.setMsg("系统错误");
			u.setRes(-2);
			logger.error(e);
			e.printStackTrace();
			try {
				response.getWriter().println(JSONUtil.ToJson(u));
			} catch (IOException e1) {
				logger.error(e);
			}
		}
	}

}
