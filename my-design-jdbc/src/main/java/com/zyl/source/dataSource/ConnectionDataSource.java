package com.zyl.source.dataSource;

import java.sql.Connection;

/**
 * 数据源接口
 * 
 * @author ZhouGuoQiang
 * @data 2017年3月2日
 * @version 1.0 DataSource
 */
public interface ConnectionDataSource {
	/**
	 * 获得数据库连接对象
	 * 
	 * @author ZhouGuoQiang
	 * @data 2017年3月2日
	 * @return Connection
	 * @return
	 */
	public Connection getConnection()throws Exception;
}
