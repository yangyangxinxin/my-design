package zyl.start;

import com.zyl.datasource.C3p0DataSource;
import com.zyl.service.servlet.Service;
import com.zyl.service.util.ParseWeb;
import com.zyl.source.jdbc.MyDataSource;


public class Start {
	public static void main(String[] args) {
		MyDataSource.init(new C3p0DataSource());
		//创建服务器.
		ParseWeb service=new ParseWeb();
		try {
			//初始服务器
			service.init();
			//服务器启动
			Service.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
