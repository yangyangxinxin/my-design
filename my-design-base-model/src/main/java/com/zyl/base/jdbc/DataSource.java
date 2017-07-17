package com.zyl.base.jdbc;

import java.util.List;

import com.zyl.base.model.Model;

/**
 * 数据源接口
 * @author ZhouGuoQiang
 * 2017年2月27日
 * @version 1.0
 * DataSource
 */
public interface DataSource {
	
	
	/**
	 * 新增数据
	 * @author ZhouGuoQiang
	 * 2017年2月27日
	 * @return Model
	 * @param model Model
	 * @throws Exception
	 */
	public Model add(Model model) throws Exception;
	
	/**
	 * 修改数据
	 * @author ZhouGuoQiang
	 * 2017年2月27日
	 * @return Model
	 * @param model Model
	 * @throws Exception
	 */
	public Model edit(Model model) throws Exception;
	
	/**
	 * 根据model,和sql查询所需数据并完成封装
	 * @author ZhouGuoQiang
	 * 2017年2月27日
	 * @return List
	 * @param sql 部分sql语句
	 * @param model Model
	 * @throws Exception
	 */
	public List<Model> select(String sql, Model model) throws Exception;
	
	/**
	 * 根据model的Id去获得该数据
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return Model
	 * @param model Model
	 * @throws Exception
	 */
	public Model selectById(Model model) throws Exception;
	
	/**
	 * 根据model和sql去获得所有查询的数据
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return List
	 * @param model Model
	 * @param sql	限定sql语句 如"where id =3"或则order by id 等等
	 * @throws Exception
	 */
	public List<Model> selectSearch(Model model, String sql) throws Exception;
	
	/**
	 * 
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return List
	 * @param model	Model
	 * @param pageNum 页码
	 * @param sql	限定sql语句 如"where id =3"或则order by id 等等
	 * @throws Exception
	 */
	public List<Model> listByPage(Model model, int pageNum, String sql)throws Exception;
	
	/**
	 * 根据原生sql语句查询
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return Object
	 * @param sql 部分sql语句
	 * @throws Exception
	 */
	public Object getBySql(String sql) throws Exception;
	
	/**
	 * 根据model删除数据
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return int
	 * @param model Model
	 * @throws Exception
	 */
	public int delete(Model model) throws Exception;
	
	/**
	 * 查询在限定条件下的数量
	 * @author ZhouGuoQiang
	 * 2017年2月28日
	 * @return long
	 * @param model Model
	 * @param sql 限定sql语句 如"where id =3"不能使用分组查询
	 * @throws Exception
	 */
	public int count(Model model, String sql) throws Exception;
	
	/**
	 * 删除指定的model数据
	 * @author ZhouGuoQiang
	 * 2017年3月4日
	 * @return int
	 * @param model Model
	 * @param sql 限定sql
	 * @throws Exception
	 */
	public int delete(Model model, String sql) throws Exception;
}






