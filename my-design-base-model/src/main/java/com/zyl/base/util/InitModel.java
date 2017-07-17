package com.zyl.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zyl.base.model.Model;
import com.zyl.util.Md5Maker;


/**
 * 程序启动时model 的获取,并放入容器中
 * @author ZhouGuoQiang
 * 2017年3月18日
 * @version 1.0
 * InitModel
 */
public class InitModel {
	/**
	 * model容器
	 */
	public static Map<String, Model> map =new HashMap<>();
	
	/**
	 * 初始化model容器
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @param path 配置文件路径
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static void initModel(String path) throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = null;
		doc = reader.read(new File(path));
		Element root=doc.getRootElement();
		root.elements();
		for (Element ele : (List<Element>)root.elements()) {
			Model model=new Model();
			model.setTableName(ele.attributeValue("name"));
			if(ele.attributeValue("primayKey")!=null){
				model.setPrimayKey(ele.attributeValue("primayKey"));
			}
			Element property=ele.element("property");
			 List<String> columns = new ArrayList<>();
			 //Map<String,String> type = new HashMap<>();
			for (Element field : (List<Element>)property.elements()) {
				columns.add(field.getName());
			}
			model.setColumns(columns);
			map.put(ele.attributeValue("name"), model);
		}
	}
	/**
	 * 获取model
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @return Model
	 * @throws Exception 
	 * @param modelName model名
	 */
	public static Model getModel(String modelName) throws Exception{
		Model model = map.get(modelName).clone();
		model.setId(getId());
		model.initPage();
		return model;
	}
	
	/**
	 * 获取全球唯一ID,为model初始化id
	 * @author ZhouGuoQiang
	 * 2017年3月10日
	 * @return String
	 */
	public static String getId(){
		UUID uuid=UUID.randomUUID();
		return Md5Maker.buf_MD5(uuid.toString(), "1");
	}
}
