package com.zyl.service.Interception;

import java.util.HashMap;
import java.util.Map;

import com.zyl.service.container.Container;


public class LinkWithModel extends Interception{
	
	@Override
	public int doInterception() throws Exception{
		this.rule=Container.getRules("VerificationModel");
		Map<String, Object> map=new HashMap<String, Object>();
		String name=(String) this.request.getAttribute("name");
		String value=(String) this.request.getAttribute("value");
		String rule=(String) this.request.getAttribute("rule");
		map.put("name", name);
		map.put("value", value);
		map.put("rule", rule);
		this.rule.setMap(map);
		if(this.rule.doIt()!=1){
			this.msg=this.rule.getMsg();
			return 0;
		}
		return 1;
	}

}
