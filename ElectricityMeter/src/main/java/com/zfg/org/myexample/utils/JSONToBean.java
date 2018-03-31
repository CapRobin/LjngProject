package com.zfg.org.myexample.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON字符串转换成指定对象的工具
 * @author Administrator
 *
 */
public class JSONToBean{
	
	/**
	 * 将JSON字符串转换成指定的对象数组
	 * 
	 * @param model 指定的类
	 *        jsonString 要转换的JSON字符串     
	 * @return 指定的对象数组
	 */
	public static Object getModelListFormJSONString(Class<?> model,String jsonString){
		List<Object> list = null;
        try{
        	JSONArray jsonArray = new JSONArray(jsonString);
        	list = new LinkedList<Object>();
    		for(int i = 0;i<jsonArray.length();i++){
    			try {
    				JSONObject jsonObject = jsonArray.getJSONObject(i);
    				Object newVo = toModel(model, jsonObject);
    				list.add(newVo);
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}			
    		}
        } catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 将JSON字符串转换成指定的对象
	 * 
	 * @param model 指定的类
	 *        jsonString 要转换的JSON字符串     
	 * @return 指定的对象
	 */
	public static Object getModelFormJSONString(Class<?> model,String jsonString){
		Object object=null;
        try{
        	JSONObject jsonObject=new JSONObject(jsonString);
        	object=toModel(model,jsonObject);
        } catch (JSONException e) {
			e.printStackTrace();
		}
		return object;	
	}
	
	/**
	 * 将JSON数组转换成指定的对象数组
	 * 
	 * @param model 指定的类
	 *        jsonString 要转换的JSON数组    
	 * @return 指定的对象数组
	 */
	public static Object getModelList(Class<?> model,JSONArray jsonArray){
	    List<Object> list = new LinkedList<Object>();
		for(int i = 0;i<jsonArray.length();i++){
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Object newVo = toModel(model, jsonObject);
				list.add(newVo);
			} catch (JSONException e) {
				e.printStackTrace();
			}			
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static Object toModel(Class<?> model,JSONObject object){
		Object newVo = null;
		try {
			//克隆传入的bean
			newVo = model.newInstance();

			//获得所有的属性
			Field[] fields = model.getDeclaredFields();
			Field field;
			for (int i = 0; i < fields.length; i++) {
				field = fields[i];
				String voAttrName = field.getName();
				Class type = field.getType();
				String methodName = "set" + voAttrName.substring(0, 1).toUpperCase()
						+ voAttrName.substring(1, voAttrName.length());
				Object[] paras = {};
				Class[] para = { type };
				Method methoded = model.getDeclaredMethod(methodName, para);
				Class[] returnTypes = methoded.getParameterTypes();
				Class returnType = returnTypes[0];
				Object returnValue = null;
				String value = null;
				try {
					value = object.getString(voAttrName);
				} catch (JSONException e) {
				}
				if (returnType == String.class) {
					if (value != null) {
						returnValue = ((String) value).trim();						
					}else {
						returnValue="";
					}
				}else if(returnType == int.class){
					if (value != null) {
						try {
							returnValue = Integer.parseInt(value.toString());
						} catch (NumberFormatException ex1) {
							ex1.printStackTrace();
							returnValue =0;
						}
					}
				}else if(returnType == Integer.class){
					if (value != null) {
						try {
							returnValue = new Integer(Integer.parseInt(value.toString()));
						} catch (NumberFormatException ex1) {
							ex1.printStackTrace();
							returnValue =0;
						}
					}
				}else if(returnType == List.class){
					if (value != null) {
						try {
							JSONArray array = new JSONArray(value.toString());
							List list = new LinkedList();
							for(int j = 0;j<array.length();j++){
								list.add(toModel(model,array.getJSONObject(j)));
							}
							returnValue = list;
						} catch (NumberFormatException ex1) {
							ex1.printStackTrace();
							returnValue =0;
						}
					}else{
						returnValue = new ArrayList();
					}
				}else {
					continue;
				}
				if(returnValue!=null){
					Object[] params = { returnValue };
					methoded.invoke(newVo, params);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newVo;
	}
}