package com.ym.appshare.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
                                                              //   构建JSON数据    工具类
public class CreateJsonData {

	//把List<HashMap<String,Object>>  数据 转换成   JSON  数据  
	public static String ListToJson(String arrayName,List<HashMap<String,Object>> lhm){
		
		JSONObject jsonObject = null  ;
		JSONObject jsonObject2 = new JSONObject() ;
		JSONArray jsonArray = new JSONArray() ;

		for(HashMap<String,Object> data : lhm){
			
			jsonObject = new JSONObject() ;  //   声明JSON  对象  （单个实体对象）
			Set<String> keys = data.keySet() ;                     //   获取 map中key 集合
			Iterator iterator = keys.iterator() ;  
			 															//   迭代  keys  
			while(iterator.hasNext()){   
		//		jsonObject = new JSONObject() ;  //   声明JSON  对象  （单个实体对象）
				String key = (String) iterator.next() ;                 //   取出key值
				String value = (String) data.get(key) ;                   //   value  值
				try {
					jsonObject.put(key, value) ;                       //   构建JSON  对象  （单个实体对象）
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                    
			}
			jsonArray.put(jsonObject) ;                         //构建JSON数组  
		} 
		try {
			jsonObject2.put(arrayName, jsonArray) ;                     //    构建JSON数组   对象
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}            
		return jsonObject2.toString() ;                  //  返回JSON数据          
		
	}
}
