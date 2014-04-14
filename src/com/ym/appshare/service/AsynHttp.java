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
	                  //          Post 方法 提交参数  
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
			//设置post请求参数
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
		
		 //     把Paramster类型集合转换为NameValuePair类型集合
		 
	private static List<BasicNameValuePair> buildNameValuePair (List<Params> params) {
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for(Params param : params) {
			BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
			result.add(pair);
		}
		return result;
	}
	// 封装登录  方法      返回      : // 登陆  参数为空    2     //     1   成功     //// 0 用户不存在
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
	//注册     返回 :  // 参数  为空   2    //  1 注册成功  返回   // 注册出错   返回 0     //  3  用户名 已注册   
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
	
	//  把信息 以JSON数据  提交到服务器
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
