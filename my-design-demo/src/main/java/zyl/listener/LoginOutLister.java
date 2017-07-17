package zyl.listener;

import com.zyl.base.model.Model;
import com.zyl.service.filter.GlobalFilter;
import com.zyl.service.listener.Lister;
import com.zyl.service.util.Md5Maker;

/**
 * 登录监听器
 * @author ZhouGuoQiang
 * 2017年3月10日
 * @version 1.0
 * LoginLister
 */
public class LoginOutLister  extends Lister {
	
	public LoginOutLister() {
	}

	public LoginOutLister(String url) {
		super(url);
	}
	@Override
	public int doLister() {
		//获得登录UseCase
		try {
			Model model=(Model) this.request.getSession().getAttribute("user"); 
			if(model==null){
				return 1;
			}
			this.request.getSession().removeAttribute("user");
			String key=	Md5Maker.buf_MD5(GlobalFilter.getIpAddress(this.request)+model.getId(), "1");
			LoginLister.getOnlineNum().remove(key);
			this.logger.info("---------用户"+model.getModelValue().get("UserName")+"退出---------");
			this.logger.info("---------当前在线人数:"+LoginLister.getOnlineNum().size()+"---------");
		} catch (Exception e) {
			this.logger.error(e);
			return 0;
		}
		return 1;
	}
	
	
}
