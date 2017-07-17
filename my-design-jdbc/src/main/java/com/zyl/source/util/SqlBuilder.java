package com.zyl.source.util;

import java.util.Map.Entry;

import com.zyl.base.model.Model;



/**
 * sql语句的生成
 * @author ZhouGuoQiang
 * @data 2017年3月1日
 * @version 1.0 SqlBuilder
 */
public class SqlBuilder {
	
	/**
	 * 新增数据,根据传入model,进行sql封装
	 * @author ZhouGuoQiang
	 * @data 2017年3月1日
	 * @return String
	 * @param model
	 * @throws Exception
	 */
	public static String  insert(Model model) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + model.getTableName() + " (");
		StringBuffer value = new StringBuffer();
		value.append("(");
		for (Entry<String, Object> key : model.getInputs().entrySet()) {
			if(key.getKey().equals(model.getPrimayKey())){
				continue;
			}
			sb.append(key.getKey()+",");
			value.append("'"+key.getValue()+"',");
		}
		sb.append("Guid"+")");
		value.append("'"+model.getId()+"')");
		//System.out.println(sb.toString()+" values "+value.toString());
		return sb.toString()+" values "+value.toString();
	}
	
	/**
	 * 修改数据,根据传入model中的id,进行sql封装
	 * @author ZhouGuoQiang
	 * @data 2017年3月1日
	 * @return String
	 * @param model
	 * @throws Exception
	 */
	public static String update(Model model)throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("update "+model.getTableName()+" set ");
		for (Entry<String, Object> key : model.getInputs().entrySet()) {
			if(key.getValue()==null){
				continue;
			}
			if(key.getValue() instanceof String&&key.getValue().toString().isEmpty()){
				continue;
			}
			sb.append(key.getKey()+"='"+key.getValue()+"',");
		}
		return sb.toString().substring(0, sb.toString().length()-1)+" where "+model.getPrimayKey()+"='"+model.getId()+"'";
	}
	
	public static String select(Model model,String sql)throws Exception{
		StringBuffer sb=new StringBuffer();
		StringBuffer inner=new StringBuffer();
		if(model.getForeignKeyModel()==null||model.getForeignKeyModel().size()==0){
			return "select * from "+model.getTableName()+" "+sql;
		}
		sb.append("select");
		for (String out : model.getOutputs()) {
			if(model.getColumns().contains(out)){
				sb.append(" "+model.getTableName()+"."+out+",");
			}
		}
		for (Entry<String, String> foreignKeyModel : model.getForeignKeyModel().entrySet()) {
			String[] arr=foreignKeyModel.getValue().split(",");
			sb.append(arr[0]+" as "+foreignKeyModel.getKey()+",");
			inner.append(" left join "+arr[0].split("\\.")[0]+" on");
			inner.append(" "+model.getTableName()+"."+arr[1].split(":")[0]+" = ");
			inner.append(arr[1].split(":")[1]+" ");
		}
		sb.toString().subSequence(0, sb.toString().length()-1);
		String buldsql=sb.toString().subSequence(0, sb.toString().length()-1)+" from "+model.getTableName()+" "+inner.toString();
		return buldsql+" "+sql;
	}
	
	public static String getCountSql(Model model,String sql){
		StringBuffer inner=new StringBuffer();
		if(model.getForeignKeyModel()==null){
			return "";
		}
		for (Entry<String, String> foreignKeyModel : model.getForeignKeyModel().entrySet()) {
			String[] arr=foreignKeyModel.getValue().split(",");
			inner.append(" left join "+arr[0].split("\\.")[0]+" on");
			inner.append(" "+model.getTableName()+"."+arr[1].split(":")[0]+" = ");
			inner.append(arr[1].split(":")[1]+" ");
		}
		if(sql==null){
			sql="";
		}
		return inner.toString()+" "+sql;
	}
}
