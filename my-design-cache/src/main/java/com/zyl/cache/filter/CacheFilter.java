package com.zyl.cache.filter;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.gson.Gson;
import com.zyl.base.control.UseCase;
import com.zyl.cache.container.CacheContainer;
import com.zyl.cache.model.CacheModel;
import com.zyl.cache.util.HandelCache;
import com.zyl.cache.util.Md5Maker;
import com.zyl.service.filter.Filter;
import com.zyl.service.util.InterFace;

/**
 * 缓存过滤器,用于缓存处理
 * @author ZhouGuoQiang
 * 2017年3月25日
 * @version 1.0
 * CacheFilter
 */
public class CacheFilter extends Filter{

	public CacheFilter(){}
	
	public CacheFilter(String path){
		super(path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UseCase doFilter() throws Exception {
		//获取传入的参数
		Map<String, Object> para=this.uescase.getInput();
		//获取接口参数
		InterFace inter = this.inter;
		//获取url
		String url = request.getServletPath();
		
		UseCase u=this.uescase;
		if(inter.getUpdateModel()!=null){
			Class<UseCase> useCase=(Class<UseCase>) Class.forName(inter.getControlPackage());
			Method methed=useCase.getMethod(inter.getMothed(), new Class[]{});
			methed.invoke(u, new Object[]{});
			u.setState(true);
			//HandelCache.removeCacheByModel(inter.getModleName());
			inter.getUpdateModel();
			for (String cacheModel : inter.getUpdateModel().split(",")) {
				if(cacheModel==null){
					continue;
				}
				HandelCache.refreshByModelName(cacheModel);
				this.logger.info("由"+url+"刷新"+cacheModel+"缓存");
			}
			
		}else{
			String key=url+new Gson().toJson(para)+u.getPageNum();
			key=Md5Maker.buf_MD5(key, "1");
			CacheModel cacheModel=CacheContainer.getCacheModel(inter.getModleName(), key);
			if(cacheModel==null){
				this.logger.info("非缓存数据");
				Class<UseCase> useCase=(Class<UseCase>) Class.forName(inter.getControlPackage());
				Method methed=useCase.getMethod(inter.getMothed(), new Class[]{});
				methed.invoke(u, new Object[]{});
				u.setState(true);
				this.logger.info("缓存存储----->"+key);
				HandelCache.addCache(key,inter,u,url);
			}else{
				u=cacheModel.getUseCase();
				this.logger.info(url+":"+"缓存读取"+"------->"+ key);
			}
		}
		return u;
	}

}
