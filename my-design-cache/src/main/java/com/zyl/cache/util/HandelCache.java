package com.zyl.cache.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zyl.base.control.UseCase;
import com.zyl.cache.container.CacheContainer;
import com.zyl.cache.model.CacheModel;
import com.zyl.service.util.InterFace;

/**
 * 缓存操作
 * @author ZhouGuoQiang
 * 2017年3月25日
 * @version 1.0
 * HandelCache
 */
public class HandelCache {
	private static Logger logger = Logger.getLogger(HandelCache.class);
	
	//添加缓存
	public static int addCache(String key,InterFace inter,UseCase u,String url){
		CacheModel cacheModel=InitialCacheModel.initCacheModel(u, inter, url);
		CacheContainer.addCache(inter.getModleName(), key, cacheModel);
		return 1;
	}
	
	public static int removeCacheByModel(String modelName){
		CacheContainer.removeCache(modelName);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public static int refreshByModelName(String modelName) throws Exception{
		Map<String, CacheModel> modelCaches=CacheContainer.getByModelName(modelName);
		if(modelCaches==null){
			return 1;
		}
		Map<String, CacheModel> newModelCaches=new HashMap<>();
		for (Entry<String, CacheModel> cache : modelCaches.entrySet()) {
			InterFace inter=cache.getValue().getInter();
			UseCase u = cache.getValue().getUseCase();
			Class<UseCase> useCase=(Class<UseCase>) Class.forName(inter.getControlPackage());
			Method methed=useCase.getMethod(inter.getMothed(), new Class[]{});
			methed.invoke(u, new Object[]{});
			u.setState(true);
			CacheModel cacheModel=InitialCacheModel.initCacheModel(u, inter, cache.getValue().getUrl());
			newModelCaches.put(cache.getKey(), cacheModel);
		}
		CacheContainer.addCacheByModelName(newModelCaches, modelName);
		return 1;
	}
	static{
		new Timer().schedule(new TimerTask() {
	        public void run() {
	        	logger.info("--------清理超时缓存数: "+AutomaticCleanCache.automaticCleanCache()+"--------");
	        	logger.info("--------当前缓存数: "+CacheContainer.cachesDate.size()+"--------");
	        }
		}, 1000 , AutomaticCleanCache.claencCycle);
	}
}
