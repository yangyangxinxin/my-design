package zyl.control;

import java.util.HashMap;
import java.util.Map;

import com.zyl.base.control.UseCase;

public class Manager extends UseCase{
	public int loginOut(){
		this.setRes(1);
		Map<String, String> map=new HashMap<>();
		map.put("msg", "退出成功");
		this.setResult(map);
		return 1;
	}
}
