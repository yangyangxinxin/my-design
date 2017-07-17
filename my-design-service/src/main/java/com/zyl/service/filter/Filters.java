package com.zyl.service.filter;

import javax.servlet.http.HttpServletRequest;

import com.zyl.base.control.UseCase;
import com.zyl.service.container.Container;
import com.zyl.service.listener.Listers;
import com.zyl.service.util.InterFace;

public class Filters {
	public static UseCase doFilter(HttpServletRequest request,UseCase useCase,InterFace inter)throws Exception{
		for (Filter filter : Container.filters) {
			if (Listers.match(request.getServletPath(), filter.getUrl())) {
				Filter newFilter=filter.clone();
				newFilter.setRequest(request);
				newFilter.setUescase(useCase);
				newFilter.setInter(inter);
				useCase=newFilter.doFilter();
			}
		}
		return useCase;
	}
	
}
