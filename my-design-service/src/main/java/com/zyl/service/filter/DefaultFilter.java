package com.zyl.service.filter;

import java.util.Map;
import java.util.Map.Entry;

import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;
import com.zyl.service.util.InterFace;

/**
 * 默认参数设置(过滤器)
 * @author ZhouGuoQiang
 * 2017年3月22日
 * @version 1.0
 * DefaultFilter
 */
public class DefaultFilter extends Filter{

	public DefaultFilter(){}
	
	public DefaultFilter(String path){
		super(path);
	}
	
	@Override
	public UseCase doFilter() throws Exception{
		UseCase u=this.uescase;
		InterFace inter=(InterFace) this.request.getAttribute("interFace");
		Map<String, Object> input = u.getInput();
		for (Entry<String, String> defaultValue : inter.getDefalutValue().entrySet()) {
			if(input.get(defaultValue.getKey())==null||input.get(defaultValue.getKey()).toString().isEmpty()){
				if(!defaultValue.getValue().equals("%")){
					input.put(defaultValue.getKey(), defaultValue.getValue());
				}
			}
			if(defaultValue.getValue().equals("%")){
				if(input.get(defaultValue.getKey())==null){
					input.put(defaultValue.getKey(), "%");
				}else{
					input.put(defaultValue.getKey(), "%"+input.get(defaultValue.getKey())+"%");
				}
			}
		}
		Model model=u.getModel();
		String sql=u.getSql();
		
		for (String field :inter.getInput() ) {
			if(sql!=null&&sql.contains("${"+field+"}")){
				String temp="'"+input.get(field)+"'";
				sql=sql.replace("${"+field+"}", temp);
			}
		}
		u.setSql(sql);
		if(model.getColumns().contains("Ip")){
			Map<String, Object> modelValue = model.getModelValue();
			modelValue.put("Ip", GlobalFilter.getIpAddress(request));
			model.setModelValue(modelValue);
			u.setModel(model);
		}
		inter.getDefalutValue();
		return u;
	}

}
