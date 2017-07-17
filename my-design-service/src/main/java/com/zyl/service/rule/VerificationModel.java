package com.zyl.service.rule;

import com.zyl.base.model.Model;
import com.zyl.base.util.InitModel;

/**
 * 验证外键
 * @author ZhouGuoQiang
 * @data 2017年3月5日
 * @version 1.0
 * LinkWithModel
 */
public class VerificationModel extends Rule{
	/**
	 * 验证外键是是否存在
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return int
	 * @param model
	 * @return
	 */
	@Override
	public int doIt(){
		Model model=null;
		try {
			 model=InitModel.getModel(this.map.get("rule").toString());
		} catch (Exception e) {
			this.msg="拦截器执行错误,无法获取验证model";
			this.logger.error(e);
		}
		model=model.getModel(this.map.get("value").toString());
		if(model==null){
			this.msg=this.map.get("rule").toString()+"没有主键为"+this.map.get("value").toString()+"的记录";
			return 0;
		}
		return 1;
	}
}
