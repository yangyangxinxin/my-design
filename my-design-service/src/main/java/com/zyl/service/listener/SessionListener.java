package com.zyl.service.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


@WebListener("This is only a demo listener")
public class SessionListener implements HttpSessionListener {
	private static Logger logger = Logger.getLogger(SessionListener.class); 
	public void sessionCreated(HttpSessionEvent event) {
		logger.info(String.format("sessionCreated:%s",event.getSession().getId()));
		event.getSession().setAttribute("onlineUserBindingListener", new OnlineUserBindingListener(event.getSession())); 
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		logger.info(String.format("sessionDestroyed:%s",event.getSession().getId()));
		OnlineUserBindingListener l=(OnlineUserBindingListener) event.getSession().getAttribute("onlineUserBindingListener");
		l.unBind();
	}
	class OnlineUserBindingListener implements HttpSessionBindingListener {
		@SuppressWarnings("unused")
		private HttpSession session;
		public OnlineUserBindingListener(HttpSession session){
			this.session=session;
		}
		public void unBind(){
		}
		public void valueBound(HttpSessionBindingEvent arg0) {
		}
		public void valueUnbound(HttpSessionBindingEvent arg0) {
		}
	}
}
