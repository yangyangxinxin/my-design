package zyl.Interception;

import com.zyl.service.Interception.Interception;

/**
 * 登录拦截器
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0
 * LoginFilter
 */
public class NeedLogin extends Interception{
	
	public int doInterception(){
		try {
			//获得当前访问ip
			//String ip=GlobalFilter.getIpAddress(this.request);
			Object obj = this.request.getSession().getAttribute("user");
			if(obj==null){
				this.msg="请先登录";
				return 0;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
