package com.zyl.cache.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyl.cache.model.CacheModel;
import com.zyl.cache.util.AutomaticCleanCache;
import com.zyl.service.util.ContainerUtil;

/**
 * 容器
 * @author ZhouGuoQiang
 * 2017年3月18日
 * @version 1.0
 * Container
 */
public class CacheContainer {
	private static Logger logger = Logger.getLogger(CacheContainer.class);
	/**
	 * 缓存容器
	 */
	private static Map<String,  Map<String, CacheModel>> caches=new HashMap<>(AutomaticCleanCache.MaxCahceNum);
	/**
	 * 缓存时间标记容器
	 */
	public static List<String> cachesDate=new ArrayList<String>();
	
	/**
	 * 添加缓存
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 * @return void
	 */
	public static void addCache(String modelName,String urlKey,CacheModel cache){
		Map<String,CacheModel> cacheMap=caches.get(modelName);
		if(cacheMap==null){
			cacheMap=new HashMap<>(1024);
		}
		cacheMap.put(urlKey, cache);
		if(caches.size()>=AutomaticCleanCache.MaxCahceNum){
			AutomaticCleanCache.reomveCacheBySaturation();
		}
		caches.put(modelName, cacheMap);
		cachesDate.add(cache.getDate().getTime()+","+modelName+","+urlKey);
	}
	/**
	 * 获得缓存
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 * @return CacheModel
	 */
	public static CacheModel getCacheModel(String modelName,String urlKey){
		 Map<String, CacheModel> modelCache=caches.get(modelName);
		 if(modelCache==null){
			 return null;
		 }
		return caches.get(modelName).get(urlKey);
	}
	
	/**
	 * 根据modelName获取当下所有缓存
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return Map<String,CacheModel>
	 */
	public static Map<String, CacheModel> getByModelName(String modelName){
		return caches.get(modelName);
	}
	
	/**
	 * 根据modelName进行缓存储存
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return void
	 */
	public static void addCacheByModelName(Map<String, CacheModel> modelCahces,String modelName){
		caches.put(modelName, modelCahces);
	}
	
	/**
	 * 清除当下所有model缓存
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 * @return void
	 */
	public static void removeCache(String modelName){
		caches.remove(modelName);
	}
	
	/**
	 * 清除指定缓存
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return void
	 */
	public static void removeCache(String modelName,String urlKey){
		 Map<String, CacheModel> cacheMap=caches.get(modelName);
		 if(cacheMap==null){
			 return;
		 }
		 cacheMap.remove(urlKey);
		 caches.put(modelName, cacheMap);
	}
	/**
	 * 获得缓存容器
	 * @author ZhouGuoQiang
	 * 2017年3月25日
	 * @return Map<String,Map<String,CacheModel>>
	 */
	public  Map<String,Map<String, CacheModel>> getCaches(){
		return caches;
	}
	
	//放入容器管理器中
	static{
		try {
			ContainerUtil.setContainer("caches", CacheContainer.class, "getCaches");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
}
