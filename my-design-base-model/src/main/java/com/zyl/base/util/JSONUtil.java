package com.zyl.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;

/**
 * 将UseCase中的Result JSON化
 * @author ZhouGuoQiang
 * 2017年3月5日
 * @version 1.0
 * JSONUtil
 */
@SuppressWarnings("unchecked")
public class JSONUtil {
	
	/**
	 * 将获得数据进行json解析
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 * @return String
	 * @param useCase UseCase
	 */
	public static String ToJson(UseCase useCase){
		Map<String, Object> map = new HashMap<String, Object>();
		//获得control处理结果
		Object result=useCase.getResult();
		
		List<Object> list=new ArrayList<>();
		
		if(useCase.getRes()!=1){
			map.put("msg", useCase.getMsg());
			map.put("res", useCase.getRes());
			return new Gson().toJson(map);
		}
		//日过result是model直接取出传出参数.如果是list,这依次取出传出参数容器,
		if(result instanceof Model){
			map.put("data", ((Model)result).getModelValue());
		}else if(result instanceof List){
			try {
				List<Model> modelList =(List<Model>) result;
				for (Model model : modelList) {
					list.add(model.getModelValue());
				}
				map.put("data",list);
			} catch (Exception e) {
				//兼容处理,当后续自定义方法中已其他list的方式传出时,直接将参数放入data中,值得注意:此方式处理数据可能导致数据错误,
				//可以保证list的泛型是Sting类型,那么也不会出错,或是可以被json化的数据也可以处理.
				List<Object> objList=(List<Object>) result;
				map.put("data",objList);
			}
		}else if(result instanceof Map){
			//为方便自定义接口是,自定义数据输出结果
			map.put("data",result);
		}
		map.put("res", useCase.getRes());
		//如果有提示信息放入数据中
		String msg=useCase.getMsg();
		if(msg!=null){
			map.put("msg", msg);
		}
		return new Gson().toJson(map);
	}
}
