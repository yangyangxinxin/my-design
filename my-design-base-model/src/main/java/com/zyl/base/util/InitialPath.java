package com.zyl.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件读取路径配置
 * @author ZhouGuoQiang
 * 2017年2月28日
 * @version 1.0
 * InitialPath
 */
public class InitialPath {
	//默认为工程当前路径
	public static String dataSourcePath=InitialPath.class.getClass().getResource("/").getPath()+"/dataSource.xml";
	
	private static Map<String, String> sqlPathMap =new HashMap<>();
	
	public void setMap(String className,String sqlPath){
		sqlPathMap.put(className,sqlPath);
	}
	public String getSqlPath(String className){
		return sqlPathMap.get(className);
	}
		
}
