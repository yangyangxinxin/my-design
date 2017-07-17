package com.zyl.base.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyl.base.control.UseCase;
import com.zyl.base.util.Page;


/**
 * 数据封装原型
 * 
 * @author ZhouGuoQiang 2017年3月5日
 * @version 1.0 BaseModel
 */
public class Model implements Cloneable{

	/**
	 * 栏位设置
	 */
	protected List<String> columns = new ArrayList<>();
	/**
	 * 传出参数容器
	 */
	protected Map<String, Object> modelValue = new HashMap<>();
	/**
	 * 字段对应的数据类型
	 */
	protected Map<String, String> type = new HashMap<>();
	/**
	 * 传出参数名
	 */
	protected List<String> outputs = new ArrayList<>();
	/**
	 * 传入参数容器
	 */
	private Map<String, Object> inputs = new HashMap<>();
	/**
	 * 设置对应表名
	 */
	protected String tableName;
	/**
	 * 设置主键即id名称
	 */
	protected String primayKey = "Guid";
	/**
	 * 判断该实体是否新的model
	 */
	protected boolean isNew = true;
	/**
	 * 实体类分页设置
	 */
	protected Page page;

	/**
	 * 主键默认id
	 */
	protected String id;
	
	/**
	 * 链接外键
	 */
	protected Map<String, String> foreignKeyModel;
	
	/**
	 * ip
	 */
	protected String ip;
	
	protected String sql;
	
	/**
	 * 根据传入的id获取完整的model
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @return Model 
	 * @param id model的Id
	 */
	public Model getModel(String id){
		UseCase u =new UseCase();
		setId(id);
		u.setModel(this);
		return u.view();
	}

	/**
	 * 可通过字段名直接获取值
	 * 
	 * @author ZhouGuoQiang 2017年2月28日
	 * @return Object
	 * @param name 字段名
	 */
	public Object getValue(String name) {
		return modelValue.get(name);
	}

	/**
	 * 传入参数值
	 * @author ZhouGuoQiang 2017年2月28日
	 * @param name 字段名
	 * @param value 值
	 */
	public void setValue(String name, String value) {
		inputs.put(name, value);
	}
	
	/**
	 * 初始化分页(默认)
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 */
	public void initPage(){
		this.page=new Page(this);
	}
	
	
	
	
	
	/**
	 * 重写克隆方法
	 * @author ZhouGuoQiang
	 * 2017年3月18日
	 */
	@Override
	public Model clone() throws CloneNotSupportedException {
		Model modle=(Model) super.clone();
		return modle;
	}
	
	/********************** getter setter start ***************************/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPrimayKey() {
		return primayKey;
	}

	public void setPrimayKey(String primayKey) {
		this.primayKey = primayKey;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean getIsNew() {
		return this.isNew;
	}
	public void setIsNew(boolean isNew){
		this.isNew=isNew;
	}
	public Map<String, Object> getModelValue() {
		return modelValue;
	}

	public void setModelValue(Map<String, Object> modelValue) {
		this.modelValue = modelValue;
	}
	
	public Map<String, String> getForeignKeyModel() {
		return foreignKeyModel;
	}

	public void setForeignKeyModel(Map<String, String> foreignKeyModel) {
		this.foreignKeyModel = foreignKeyModel;
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public Map<String, Object> getInputs() {
		return inputs;
	}
	
	public void setInputs(Map<String, Object> inputs) {
		this.inputs = inputs;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	/********************** getter setter end ***************************/
	
}
