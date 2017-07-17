package com.zyl.cache.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zyl.cache.container.CacheContainer;

/**
 * 自动清理缓存
 * @author ZhouGuoQiang
 * 2017年3月25日
 * @version 1.0
 * RegularCleanCache
 */
public class AutomaticCleanCache {
	
	/**
	 * 缓存存在最大时间,默认30分钟
	 */
	private static long time=30*60*1000;
	/**
	 * 最大缓存数量
	 */
	public static int MaxCahceNum=2048;
	
	/**
	 * 自动清理周期
	 */
	public static long claencCycle=10*60*1000;
	/**
	 * 判断是否达到最大存在时间
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return boolean
	 */
	public static boolean isClean(Long date){
		if(new Date().getTime()-date>=time){
			return true;
		}
		return false;
	}
	
	/**
	 * 清除第一个缓存
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return void
	 */
	public static void reomveCacheBySaturation(){
		if(CacheContainer.cachesDate.size()==0){
			return;
		}
		String[] cache= CacheContainer.cachesDate.get(0).split(",");
		CacheContainer.removeCache(cache[1], cache[2]);
	}
	
	/**
	 * 自动清理超时缓存
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return void
	 */
	public static int automaticCleanCache(){
		int claenNum=0;
		if(CacheContainer.cachesDate.size()==0){
			return 0;
		}
		List<String> removeCahcesDate=new ArrayList<String>();
		for(String cache : CacheContainer.cachesDate) {
			String[] caches= cache.split(",");
			if(isClean(Long.parseLong(caches[0]))){
				CacheContainer.removeCache(caches[1], caches[2]);
				removeCahcesDate.add(cache);
				claenNum++;
			}else{
				break;
			}
		}
		for (String remove : removeCahcesDate) {
			CacheContainer.cachesDate.remove(remove);
		}
		return claenNum;
	}
	
	
	/**************************** getters setters *******************************/
	public long getTime() {
		return AutomaticCleanCache.time;
	}
	public void setTime(long time) {
		AutomaticCleanCache.time = time;
	}
	
}
