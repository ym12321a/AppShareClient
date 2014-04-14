package com.ym.appshare.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class SaveLogoToSD {                           //    把应用的logo保存在sd卡的logo文件夹上
	
		 static String path = Environment.getExternalStorageDirectory()+File.separator+"logo"+File.separator ;
	
		 public static void saveLogo(String fileName,Drawable drawable){
			 System.out.println("----------path------------"+path);
			 String filepath = path+fileName+".png" ;    
			 File file = new File(filepath) ;
			 if(!file.getParentFile().exists()){            //    判断path是否存在    
				 file.getParentFile().mkdir() ;             // 不存在  则创建
			 }
			 System.out.println("----------filepath------------"+filepath);
			BitmapDrawable bp = (BitmapDrawable) drawable ;      //    把drawable转化成Bitmap类型
			Bitmap bitmap = bp.getBitmap() ;
			 try {
				FileOutputStream stream = new FileOutputStream(file) ;               //   输出流  
				bitmap.compress(CompressFormat.PNG, 100, stream) ;          //  以.PNG格式保存logo
				try {
					stream.close() ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("文件没有找到...");
			}       
			 
		 }
	
}
