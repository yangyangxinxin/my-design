package com.zyl.cache.util;

import java.util.Date;

import com.google.gson.Gson;
import com.zyl.base.control.UseCase;
import com.zyl.cache.model.CacheModel;
import com.zyl.service.util.InterFace;

/**
 * 缓存数据封装
 * @author ZhouGuoQiang
 * 2017年3月25日
 * @version 1.0
 * InitialCacheModel
 */
public class InitialCacheModel {
	
	public static CacheModel initCacheModel(UseCase u,InterFace inter,String url){
		CacheModel cacheModel=new CacheModel();
		cacheModel.setDate(new Date());
		cacheModel.setInter(inter);
		cacheModel.setPara(new Gson().toJson(u.getInput()));
		cacheModel.setUseCase(u);
		cacheModel.setUrl(url);
		return cacheModel;
	}
}
