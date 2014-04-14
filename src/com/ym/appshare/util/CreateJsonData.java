package com.ym.appshare.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
                                                              //   ����JSON����    ������
public class CreateJsonData {

	//��List<HashMap<String,Object>>  ���� ת����   JSON  ����  
	public static String ListToJson(String arrayName,List<HashMap<String,Object>> lhm){
		
		JSONObject jsonObject = null  ;
		JSONObject jsonObject2 = new JSONObject() ;
		JSONArray jsonArray = new JSONArray() ;

		for(HashMap<String,Object> data : lhm){
			
			jsonObject = new JSONObject() ;  //   ����JSON  ����  ������ʵ�����
			Set<String> keys = data.keySet() ;                     //   ��ȡ map��key ����
			Iterator iterator = keys.iterator() ;  
			 															//   ����  keys  
			while(iterator.hasNext()){   
		//		jsonObject = new JSONObject() ;  //   ����JSON  ����  ������ʵ�����
				String key = (String) iterator.next() ;                 //   ȡ��keyֵ
				String value = (String) data.get(key) ;                   //   value  ֵ
				try {
					jsonObject.put(key, value) ;                       //   ����JSON  ����  ������ʵ�����
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                    
			}
			jsonArray.put(jsonObject) ;                         //����JSON����  
		} 
		try {
			jsonObject2.put(arrayName, jsonArray) ;                     //    ����JSON����   ����
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}            
		return jsonObject2.toString() ;                  //  ����JSON����          
		
	}
}
