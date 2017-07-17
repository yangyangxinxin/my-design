package com.zyl.base.util;

import com.zyl.base.control.UseCase;
import com.zyl.base.model.Model;

/**
 * 分页查询设置
 * @author ZhouGuoQiang
 * 2017年3月5日
 * @version 1.0
 * Page
 */
public class Page {
	public Page() {
		
	}
	/**
	 * 根据Model初始化数据
	 * @param model Model
	 */
	public Page(Model model) {
		UseCase u=new UseCase();
		u.setModel(model);
		this.total= u.count();
	}
	
	/**
	 * 默认没页查询数量
	 */
	private int PageSize=10;
	/**
	 * 总条数
	 */
	private int total;
	
	/**
	 * 获得总页数
	 * @author ZhouGuoQiang
	 * 2017年3月5日
	 * @return int
	 */
	public int getPageTotal(){
		if(total%PageSize==0){
			return total/PageSize;
		}
		return total/PageSize+1;
	}
	
	/**
	 * 获得当前页第一条的序列
	 * @author ZhouGuoQiang
	 * 2017年3月5日
	 * @return int
	 * @param PageNum 页码
	 */
	public int getStartPage(int PageNum) {
		return 0 + (PageNum - 1) * PageSize;
	}
	
	/**
	 * 获得总条数
	 * @author ZhouGuoQiang
	 * 2017年3月5日
	 * @return int
	 */
	public int getTotal() {
		return total;
	}
	
	public int getPageSize() {
		return this.PageSize;
	}
	
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	
	
}
