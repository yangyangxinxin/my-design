package com.zyl.util;

import java.util.UUID;
/**
 * 获取唯一id工具类
 * @author lenovo
 *
 */
public class OnlyId {
	
	/**
	 * 获得全球唯一值
	 * @return String
	 */
	public static String getId(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	/**
	 * 获取使用md5加密的全球唯一值
	 * @return String
	 */
	public static String getMd5Id(){
		UUID uuid=UUID.randomUUID();
		return Md5Maker.buf_MD5(uuid.toString(), "1");
	}
	
	
}
