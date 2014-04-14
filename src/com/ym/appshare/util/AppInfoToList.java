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
	                   //   ��Ӧ����Ϣ��������ͼƬ����Ӧ��������װ�� List<HashMap<String,Object>>   ����
	public static List<HashMap<String,Object>> infoToList(List<PackageInfo> packageInfos,PackageManager pm){
		
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>() ;
		HashMap<String,Object> map = null ;
		ByteArrayOutputStream stream = null ;
		for(PackageInfo info : packageInfos){
			map = new HashMap<String, Object>() ;
		//	stream  = new ByteArrayOutputStream() ;
			map.put("appPackageName", info.packageName) ;                   //  ����
	//	    map.put("appLogoName", info.applicationInfo.loadIcon(pm)) ;              //  logo��
			/*Drawable drawable = info.applicationInfo.loadLogo(pm) ;
			 BitmapDrawable bp = (BitmapDrawable) drawable ;
			 Bitmap bitmap = bp.getBitmap() ;
			 bitmap.compress(CompressFormat.PNG, 100, stream) ;
			 FileOutputStream stream2 = new FileOutputStream(new File()) ;
			*/
		//	 Drawable drawable = info.applicationInfo.loadIcon(pm) ;
			map.put("appVersionCode", info.versionCode+"") ;               //   �汾��          
			map.put("appName", info.applicationInfo.loadLabel(pm)) ;           // ������
			data.add(map) ;
		}
		return data ;	
	}
	
	
	
	
}
