package com.zyl.service.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zyl.base.control.UseCase;

/**
 * 接口解析
 * @author ZhouGuoQiang
 * 2017年3月18日
 * @version 1.0
 * ReaderXml
 */
public class ReaderXml {
	//接口容器
	public static Map<String, InterFace> map =new HashMap<>();
	
	public static String path;
	private SAXReader reader = new SAXReader();
	private Document doc = null;
	
	@SuppressWarnings("unchecked")
	public InterFace getInterFace(String InterFaceName) throws Exception{
//测试环境下可以将此注释掉,以避免修改接口而重新启动服务器
//		if(map.containsKey(InterFaceName)){
//			System.out.println("读取接口");
//			return map.get(InterFaceName);
//		}
		String[] arr=InterFaceName.split("/");
		int i=InterFaceName.lastIndexOf("/");
		doc = reader.read(new File(path+"/"+InterFaceName.substring(1, i)+".xml"));
		InterFace interFace=new InterFace();
		int index=InterFaceName.lastIndexOf("/");
		
		//获取接口名
		String inter=InterFaceName.substring(index+1, InterFaceName.length());
		//获取根标签
		Element root=doc.getRootElement();
		//获取接口标签
		Element interE=root.element(inter);
		
		//获取modelName
		String modelName=interE.attributeValue("modelName");
		//获取actionName
		String actionName=interE.attributeValue("actionName");
		String controlName=root.attributeValue("package");
		String validations=interE.attributeValue("validations");
		String updateModel=interE.attributeValue("updateModel");
		if(root.attributeValue("validations")!=null){
			validations+=","+root.attributeValue("validations");
		}
		String output=interE.attributeValue("output");
		String condition=interE.attributeValue("condition");
		List<String> intputList=new ArrayList<>();
		List<String> outputList=new ArrayList<>();
		Map<String, String> validationsMap=new HashMap<String, String>();
		Map<String, String> defalutValue=new HashMap<String, String>();
		Map<String, String> foreignKeyModel=new HashMap<String, String>();
		Element eInput=interE.element("input");
		
		//填入输入字段默认参数以及参数拦截器
		if(eInput!=null){
			for (Element e : (List<Element>)eInput.elements()) {
				if(e.attribute("defaultValue")!=null){
					defalutValue.put(e.getName(), e.attributeValue("defaultValue"));
				}
				if(e.attribute("validations")!=null){
					validationsMap.put(e.getName(), e.attributeValue("validations"));
				}
				intputList.add(e.getName());
			}
		}
		//填入输出字段名
		if(output==null){
			Element eOutput=interE.element("output");
			if(eOutput!=null){
				for (Element e : (List<Element>)eOutput.elements()) {
					outputList.add(e.getName());
					if(e.attribute("sourceModel")!=null){
						String sourceId;
						if(e.attributeValue("sourceId")==null||e.attributeValue("sourceId").isEmpty()){
							sourceId="Guid";
						}else{
							sourceId=e.attributeValue("sourceId");
						}
						foreignKeyModel.put(e.getName(), e.attributeValue("sourceModel")+","+sourceId);
					}
				}
			}
		}else{
			Element	eOutput=root.element(output).element("output");
			for (Element e : (List<Element>)eOutput.elements()) {
				outputList.add(e.getName());
				if(e.attribute("sourceModel")!=null){
					String sourceId;
					if(e.attributeValue("sourceId")==null||e.attributeValue("sourceId").isEmpty()){
						sourceId="Guid";
					}else{
						sourceId=e.attributeValue("sourceId");
					}
					foreignKeyModel.put(e.getName(), e.attributeValue("sourceModel")+","+sourceId);
				}
			}
		}
		//填入接口调用方法名,以及方法所在类
		if(actionName==null){
			actionName=inter;
			try {
				Class<?> cla=UseCase.class;
				cla.getMethod(inter, new Class[]{});
				controlName=UseCase.class.getName();
			} catch (Exception e) {
				controlName+="."+arr[arr.length-2];
			}
		}else{
			controlName=UseCase.class.getName();
		}
		interFace.setCondition(condition);
		interFace.setInterFace(inter);
		interFace.setModleName(modelName);
		interFace.setMothed(actionName);
		interFace.setControlPackage(controlName);
		interFace.setInput(intputList);
		interFace.setOutput(outputList);
		interFace.setUpdateModel(updateModel);
		if(foreignKeyModel!=null){
			interFace.setForeignKeyModel(foreignKeyModel);
		}
		if(validations!=null){
			interFace.setValidations(validations.split(","));
		}
		if(defalutValue!=null){
			interFace.setDefalutValue(defalutValue);
		}
		if(validationsMap!=null){
			interFace.setValidationsMap(validationsMap);
		}
		//more code here
		
		//将已读取的接口放入全局变量中,以避免重复读取文件
		map.put(InterFaceName, interFace);
		return interFace;
	}
}
