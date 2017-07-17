package com.zyl.service.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 接口参数封装
 * @author ZhouGuoQiang
 * 2017年3月18日
 * @version 1.0
 * InterFace
 */
public class InterFace {
	/**
	 * 接口读取错误时提示错误信息
	 */
	protected String msg;
	/**
	 * 接口名
	 */
	protected String interFace;// 接口名
	/**
	 * 方法名
	 */
	protected String mothed;
	/**
	 * 所需传入参数名
	 */
	protected List<String> input;
	/**
	 * 所需输出参数名
	 */
	protected List<String> output;
	/**
	 * sql查询删除等限定语句
	 */
	protected String condition;
	/**
	 * 接口拦截器
	 */
	protected String[] validations;
	/**
	 * 参数拦截器
	 */
	protected Map<String, String> validationsMap;
	/**
	 * 默认参数设置
	 */
	protected Map<String, String> defalutValue;
	/**
	 * 实体名
	 */
	protected String modleName;
	/**
	 * 接口执行方法所在类
	 */
	protected String controlPackage;
	/**
	 * 外键链接实体以及链接字段
	 */
	protected Map<String, String>  foreignKeyModel;
	/**
	 * 更新model,以逗号隔开,其主要作用是用于缓存
	 */
	protected String updateModel;
	
	/******************************* getter setter **********************************/
	public String getInterFace() {
		return interFace;
	}

	public void setInterFace(String interFace) {
		this.interFace = interFace;
	}

	public String getMothed() {
		return mothed;
	}

	public void setMothed(String mothed) {
		this.mothed = mothed;
	}

	public List<String> getInput() {
		return input;
	}

	public void setInput(List<String> input) {
		this.input = input;
	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String[] getValidations() {
		return validations;
	}

	public void setValidations(String[] validations) {
		this.validations = validations;
	}

	public Map<String, String> getValidationsMap() {
		return validationsMap;
	}

	public void setValidationsMap(Map<String, String> validationsMap) {
		this.validationsMap = validationsMap;
	}

	public Map<String, String> getDefalutValue() {
		return defalutValue;
	}

	public void setDefalutValue(Map<String, String> defalutValue) {
		this.defalutValue = defalutValue;
	}

	public String getModleName() {
		return modleName;
	}

	public void setModleName(String modleName) {
		this.modleName = modleName;
	}

	public String getControlPackage() {
		return controlPackage;
	}

	public void setControlPackage(String controlPackage) {
		this.controlPackage = controlPackage;
	}
	
	public Map<String, String> getForeignKeyModel() {
		return foreignKeyModel;
	}

	public void setForeignKeyModel(Map<String, String> foreignKeyModel) {
		this.foreignKeyModel = foreignKeyModel;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUpdateModel() {
		return updateModel;
	}

	public void setUpdateModel(String updateModel) {
		this.updateModel = updateModel;
	}

	@Override
	public String toString() {
		return "InterFace [interFace=" + interFace + ", mothed=" + mothed + ", input=" + input + ", output=" + output
				+ ", condition=" + condition + ", validations=" + Arrays.toString(validations) + ", validationsMap="
				+ validationsMap + ", defalutValue=" + defalutValue + ", modleName=" + modleName + ", controlPackage="
				+ controlPackage + "]";
	}

}
