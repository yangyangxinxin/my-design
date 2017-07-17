package com.zyl.service.listener;

import com.google.gson.Gson;
import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;
import com.zyl.base.util.InitModel;
import com.zyl.service.filter.GlobalFilter;
import com.zyl.service.util.TimeFamart;

/**
 * 监听接口访问情况
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0 VisitLister
 */
public class VisitLister extends Lister {
	//private static Logger logger = Logger.getLogger(VisitLister.class);

	@Override
	public int doLister() throws Exception{
		Model model=InitModel.getModel("Visitlog");
		model.setValue("Ip", GlobalFilter.getIpAddress(request));
		model.setValue("Para", new Gson().toJson(request.getParameterMap()));
		model.setValue("Url", this.request.getServletPath());
		model.setValue("PostDate",TimeFamart.getTime("yyyy-MM-dd HH:mm:ss"));
		Model user=	(Model) request.getSession().getAttribute("user");
		if(user!=null){
			model.setValue("UserId",(String) user.getValue("Guid"));
		}
		UseCase u=new UseCase();
		u.setModel(model);
		u.add();
		return 1;
	}

}
