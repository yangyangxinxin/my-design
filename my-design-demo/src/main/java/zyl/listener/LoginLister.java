package zyl.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;
import com.zyl.service.filter.GlobalFilter;
import com.zyl.service.listener.Lister;
import com.zyl.service.util.ContainerUtil;
import com.zyl.service.util.Md5Maker;

/**
 * 登录监听器
 * @author ZhouGuoQiang
 * 2017年3月10日
 * @version 1.0
 * LoginLister
 */
public class LoginLister  extends Lister {
	
	public LoginLister() {
	}

	public LoginLister(String url) {
		super(url);
	}
	//记录登录用户,以及用户登录
	private static Map<String, Object> onlineNum=new HashMap<>();
	private static Map<String, Object> onlineTime=new HashMap<>();
	
	static{
		try {
			ContainerUtil.setContainer("onlineNum", LoginLister.class, "getOnlineNum");
			ContainerUtil.setContainer("onlineTime", LoginLister.class, "getOnlineTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public int doLister() {
		//获得登录UseCase
		try {
			UseCase u= (UseCase) this.request.getAttribute("useCase");
			List<Model> login=(List<Model>) u.getResult();
			if(login!=null&&login.size()!=0){
				Model model=login.get(0);
				this.request.getSession().setAttribute("user", model);		
				//以ip+id作为键存放当前用户
				String key=	Md5Maker.buf_MD5(GlobalFilter.getIpAddress(this.request)+model.getId(), "1");
				onlineNum.put(key, model);
				//存放当前用户登录时间
				onlineTime.put(key, new Date().getTime());
				logger.info("---------用户"+model.getModelValue().get("UserName")+"登录---------");
				logger.info("---------当前在线人数:"+onlineNum.size()+"---------");
			}
		} catch (Exception e) {
			logger.error(e);
			return 0;
		}
		return 1;
	}
	
	public static Map<String, Object> getOnlineNum() {
		return onlineNum;
	}
	public static Map<String, Object> getOnlineTime() {
		return onlineTime;
	}
	private static Logger logger = Logger.getLogger(LoginLister.class);
	static{
		new Timer().schedule(new TimerTask() {
	        public void run() {
	        	logger.info("--------当前在线人数: "+LoginLister.getOnlineNum().size()+"--------");
	        }
		}, 1000 , 30000);
	}
}
