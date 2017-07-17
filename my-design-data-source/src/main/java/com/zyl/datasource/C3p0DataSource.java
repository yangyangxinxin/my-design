package com.zyl.datasource;


import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import com.zyl.source.dataSource.ConnectionDataSource;

/**
 * 数据源配置
 * @author ZhouGuoQiang
 * @data 2017年3月4日
 * @version 1.0
 * C3p0DataSource
 */
public class C3p0DataSource implements ConnectionDataSource{
	private static Logger logger = Logger.getLogger(C3p0DataSource.class);
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	/**
	 * 配置获得Connection方法
	 * @author ZhouGuoQiang
	 * @data 2017年3月4日
	 * @return Connection
	 * @return
	 * @throws Exception
	 */
	public Connection getCom() throws Exception{
		cpds.setDriverClass( "com.mysql.jdbc.Driver" );         
		cpds.setJdbcUrl( "jdbc:mysql://127.1.1.1:3306/mydesign" );
		cpds.setUser("root");                                  
		cpds.setPassword("zhou123");  
		cpds.setMaxPoolSize(100);
		cpds.setMinPoolSize(1);
		cpds.setInitialPoolSize(5);
		cpds.setAcquireIncrement(50);
		cpds.setBreakAfterAcquireFailure(false);
		cpds.setAcquireRetryAttempts(10);
		cpds.setIdleConnectionTestPeriod(30);
		cpds.setMaxIdleTime(10);
		cpds.setCheckoutTimeout(10000);
		cpds.setUnreturnedConnectionTimeout(20);
		cpds.setDebugUnreturnedConnectionStackTraces(true);
		return cpds.getConnection();
	}	
	public Connection getConnection() throws Exception{
		return new C3p0DataSource().getCom();
	}
	/**
	 * 数据源链接状态日志输出
	 * @author ZhouGuoQiang
	 * @data 2017年3月5日
	 * @return void
	 */
	public static void showConnPoolInfo(){    
        PooledDataSource pds = (PooledDataSource) cpds;    
        if(null != pds){    
            try {
            	logger.info("------------c3p0连接池链接状态--------------");
            	logger.info("c3p0连接池中 【 总共 】 连接数量："+pds.getNumConnectionsDefaultUser());
            	logger.info("c3p0连接池中 【  忙  】 连接数量："+pds.getNumBusyConnectionsDefaultUser());
            	logger.info("c3p0连接池中 【 空闲 】 连接数量："+pds.getNumIdleConnectionsDefaultUser());
            	logger.info("c3p0连接池中 【未关闭】 连接数量："+pds.getNumUnclosedOrphanedConnectionsAllUsers());
            } catch (Exception e) {
            	logger.info("------------c3p0连接池异常！--------------");
            	logger.error(e);
            }    
        }    
    } 
	static{
		new Timer().schedule(new TimerTask() {
	        public void run() {
	        	C3p0DataSource.showConnPoolInfo();
	        }
		}, 1000 , 30000);
	}
}






