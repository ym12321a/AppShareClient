package com.ym.appshare.service;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import com.ym.appshare.entity.Params;



public class AsynHttp {
	                  //          Post ���� �ύ����  
	public static String httpPost(String url, List<Params> params) throws Exception {
		ByteArrayOutputStream outputStream = null ;
		InputStream is = null ;
		String result  = null;
		int statusCode ;
		int timeOutConnection = 3000;
		int timeOutSocket = 5000; 
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeOutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeOutSocket);
	
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		if(params.size() > 0) {
			//����post�������
			httpPost.setEntity(new UrlEncodedFormEntity(buildNameValuePair(params), HTTP.UTF_8));
		}
		HttpResponse httpResponse = httpClient.execute(httpPost);
		outputStream = new ByteArrayOutputStream() ;
		statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode == HttpStatus.SC_OK) {                    //  status   200
			is = httpResponse.getEntity().getContent() ;
			byte[] bt = new byte[1024] ;
			int len = 0 ;
			while((len = is.read(bt)) != -1){
				outputStream.write(bt, 0, len) ;
			}
			outputStream.close() ;
			result = outputStream.toString() ;
		//	result = EntityUtils.toString(httpResponse.getEntity());
		}
		else {
			result = "3";
		}
		return result;
	}
		
		 //     ��Paramster���ͼ���ת��ΪNameValuePair���ͼ���
		 
	private static List<BasicNameValuePair> buildNameValuePair (List<Params> params) {
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for(Params param : params) {
			BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
			result.add(pair);
		}
		return result;
	}
	// ��װ��¼  ����      ����      : // ��½  ����Ϊ��    2     //     1   �ɹ�     //// 0 �û�������
	public static String login(String name,String pass){
		String str = null ;
		Params p = new Params("name", name) ;
		Params p1 = new Params("pass", pass) ;
		List<Params> param = new ArrayList<Params>() ;
		param.add(p) ;
		param.add(p1) ;   
		try {
			str = httpPost(ConnectUrl.USERLOGIN_URL, param) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str ;
	}
	//ע��     ���� :  // ����  Ϊ��   2    //  1 ע��ɹ�  ����   // ע�����   ���� 0     //  3  �û��� ��ע��   
	public static String register(String userId,String nickName,String pass){
		String str = null ;
		Params p = new Params("userId", userId) ;
		Params p1 = new Params("nickName", nickName) ;
		Params p2 = new Params("pass", pass) ;
		List<Params> param = new ArrayList<Params>() ;
		param.add(p) ;
		param.add(p1) ;
		param.add(p2) ;
		try {
			str = httpPost(ConnectUrl.USEREGISTER_URL, param) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str ;
	}
	
	//  ����Ϣ ��JSON����  �ύ��������
	public static String sendToService(String appInfos,String userId){
		String str = null ;
		Params p = new Params("appInfo", appInfos) ;
		Params p1 = new Params("userId", userId) ;
		List<Params> param = new ArrayList<Params>() ;
		param.add(p) ;
		param.add(p1) ;
		try {
			str = httpPost(ConnectUrl.SAVEAPPINFOS_URL, param) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str  ;
	}
	
}
