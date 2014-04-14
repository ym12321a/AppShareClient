package com.ym.appshare.util;

import java.util.HashMap;
import java.util.List;

import com.ym.appshare.activity.R;
import com.ym.appshare.activity.StartActivity;
import com.ym.appshare.entity.WeiboUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ToolsUtil {    //    
	
	public static WeiboUser saveUser ;
	public static String userId ;
//	public static  List<HashMap<String, Object>> lists  ;
}



/*boolean flag  = false ;
if(!ValNetWork(context)){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			context).setIcon(R.drawable.ic_launcher)
			.setTitle("提示").setMessage("没有网络,前往设置...")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				//	startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);   //设置网络 并返回
					context.startActivityForResult((new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)),0);   //设置网络 
				//	context.finish() ;
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
	builder.create().show() ;
}else{
	flag = true ;
}
return flag ;
}

// 检查网络是否开启
public static boolean ValNetWork(Context context) {
boolean flag = false;
ConnectivityManager manager = (ConnectivityManager) context
		.getSystemService((Context.CONNECTIVITY_SERVICE));
NetworkInfo[] netInfos = manager.getAllNetworkInfo();
if(netInfos != null){
for (NetworkInfo info : netInfos) {
		if (info.getState() == NetworkInfo.State.CONNECTED) {
			return true ;
		} else {
			flag = false;
			
		}
	}
}
return flag;
}*/