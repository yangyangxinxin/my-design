package com.zyl.cache.model;

import java.util.Date;

import com.zyl.base.control.UseCase;
import com.zyl.service.util.InterFace;

public class CacheModel {
	/**
	 * 接口名
	 */
	private String url;
	/**
	 * 接口参数
	 */
	private String para;
	/**
	 * 接口涉及model
	 */
	private String[] models;
	/**
	 * 缓存数据
	 */
	private UseCase useCase;
	/**
	 * 存入时间
	 */
	private Date date;
	/**
	 * 接口描述
	 */
	private InterFace inter;

	public InterFace getInter() {
		return inter;
	}

	public void setInter(InterFace inter) {
		this.inter = inter;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String[] getModels() {
		return models;
	}

	public void setModels(String[] models) {
		this.models = models;
	}

	public UseCase getUseCase() {
		return useCase;
	}

	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
