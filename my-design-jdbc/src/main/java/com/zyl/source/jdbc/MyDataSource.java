package com.zyl.source.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyl.base.control.UseCase;
import com.zyl.base.jdbc.DataSource;
import com.zyl.base.model.Model;
import com.zyl.base.util.InitModel;
import com.zyl.source.dataSource.ConnectionDataSource;
import com.zyl.source.util.SqlBuilder;

/**
 * 依赖myDesign,对其进行数据处理,实现myDesign的DataSource接口
 * @author ZhouGuoQiang
 * @data 2017年3月1日
 * @version 1.0
 * MyDataSource
 */
public class MyDataSource implements DataSource{
	private static Logger logger = Logger.getLogger(MyDataSource.class);
	
	Connection c=null;
	PreparedStatement p=null;
	
	public static ConnectionDataSource dataSource;
	
	/**
	 * 初始化UseCase中dataSource
	 * @author ZhouGuoQiang
	 * @data 2017年3月1日
	 * @return void
	 */
	public static void init(ConnectionDataSource Source){
		UseCase.datasource=new MyDataSource();
		MyDataSource.dataSource=Source;
		logger.info("初始化数据源:获得数据源"+Source.getClass().getName());
	}
	
	
	/**
	 * 根据sql与Model进行查询,获得model列表
	 */
	public List<Model> select(String sql,Model model)throws Exception{
		//logger.info(sql);
		List<Model> list=new ArrayList<>();
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement(sql);
			ResultSet r=p.executeQuery();
			List<String> output=model.getOutputs();
			//判断model是否有指定输出字段,如果没有则视为输出所有字段内容
			if(output==null||output.size()==0){
				output=model.getColumns();
			}
			while(r.next()){
				//从容器中拿到model
				Model mo=InitModel.getModel(model.getTableName());
				Map<String, Object> map =new HashMap<>();
				for(String clom:output){
					//如果该字段的值为null,则为其赋上空串
					if(r.getString(clom)==null){
						map.put(clom, "");
					}else{
						map.put(clom, r.getString(clom));
					}
					if(clom.equals(mo.getPrimayKey())){
						mo.setId(r.getString(clom));
					}
				}
				mo.setModelValue(map);
				mo.setIsNew(false);
				list.add(mo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}		
		return list;
	}
	
	/**
	 * 根据id获得Model
	 */
	@Override
	public Model selectById(Model model) throws Exception{
		//String sql="select * from "+model.getTableName()+" where "+model.getPrimayKey()+" ='" +model.getId()+"'";
		String 	sql=SqlBuilder.select(model, " where "+model.getTableName()+"."+model.getPrimayKey()+" ='" +model.getId()+"'");
		List<Model> list =select(sql,model);
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询所有符合要求的Model
	 */
	@Override
	public List<Model> selectSearch(Model model, String sql) throws Exception{
		if(sql==null){
			sql=SqlBuilder.select(model, "");
		}else{
			sql=SqlBuilder.select(model, sql);
		}
		return select(sql,model);
	}
	
	/**
	 * 根据原生sql获得数据
	 */
	@Override
	public Object getBySql(String sql) throws Exception{
		List<Object> list=new ArrayList<>();
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement(sql);
			ResultSet r=p.executeQuery();
			while(r.next()){
				List<Object> objList=new ArrayList<>();
				for(int i=1;;i++){
					String temp;
					try {
						temp=r.getString(i);
					} catch (Exception e) {
						break;
					}
					objList.add(temp);
				}
				list.add(objList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}	
		return list;
	}
	
	/**
	 * 根据model添加数据
	 */
	@Override
	public Model add(Model model) throws Exception{
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement(SqlBuilder.insert(model));
			int i =p.executeUpdate();
			if(i==1){
				return model;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			c.close();
			p.close();
		}
		return null;
	}
	
	/**
	 * 修改数据
	 */
	@Override
	public Model edit(Model model) throws Exception{
		//封装update sql语句
		//完成数据更新
		int i;
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement(SqlBuilder.update(model));
			i =p.executeUpdate();
			if(i==1){
				return model;
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}
		return null;
	}

	@Override
	public int delete(Model model) throws Exception{
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement("delete from "+model.getTableName()+" where "+model.getPrimayKey()+" = '"+model.getId()+"'");
			int i=p.executeUpdate();
			if(i!=0){
				return 1;
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}	
		return 0;
	}
	
	@Override
	public int delete(Model model, String sql) throws Exception{
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement("delete from "+model.getTableName()+" "+sql);
			int i=p.executeUpdate();
			if(i!=0){
				return 1;
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}	
		return 0;
	}
	
	@Override
	public int count(Model model, String sql) throws Exception{
		
		sql="select count(*) from "+model.getTableName()+" "+SqlBuilder.getCountSql(model, sql);
		int count=0;
		try {
			c=dataSource.getConnection();
			p=c.prepareStatement(sql);
			ResultSet r=p.executeQuery();
			while(r.next()){
				count=r.getInt(1);
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			c.close();
			p.close();
		}
		return count;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public List<Model> listByPage(Model model, int pageNum ,String sql) throws Exception {
		if(sql==null){
			sql=SqlBuilder.select(model, "")+" limit "+model.getPage().getStartPage(pageNum)+","+model.getPage().getPageSize();
		}else{
			sql=SqlBuilder.select(model, sql)+" limit "+model.getPage().getStartPage(pageNum)+","+model.getPage().getPageSize();
		}
		return select(sql, model);
	}

	
}






