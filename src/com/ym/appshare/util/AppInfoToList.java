package com.ym.appshare.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppInfoToList {
	                   //   把应用信息（包名，图片名，应用名）封装成 List<HashMap<String,Object>>   集合
	public static List<HashMap<String,Object>> infoToList(List<PackageInfo> packageInfos,PackageManager pm){
		
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>() ;
		HashMap<String,Object> map = null ;
		ByteArrayOutputStream stream = null ;
		for(PackageInfo info : packageInfos){
			map = new HashMap<String, Object>() ;
		//	stream  = new ByteArrayOutputStream() ;
			map.put("appPackageName", info.packageName) ;                   //  包名
	//	    map.put("appLogoName", info.applicationInfo.loadIcon(pm)) ;              //  logo名
			/*Drawable drawable = info.applicationInfo.loadLogo(pm) ;
			 BitmapDrawable bp = (BitmapDrawable) drawable ;
			 Bitmap bitmap = bp.getBitmap() ;
			 bitmap.compress(CompressFormat.PNG, 100, stream) ;
			 FileOutputStream stream2 = new FileOutputStream(new File()) ;
			*/
		//	 Drawable drawable = info.applicationInfo.loadIcon(pm) ;
			map.put("appVersionCode", info.versionCode+"") ;               //   版本号          
			map.put("appName", info.applicationInfo.loadLabel(pm)) ;           // 程序名
			data.add(map) ;
		}
		return data ;	
	}
	
	
	
	
}
