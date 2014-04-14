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

public class SaveLogoToSD {                           //    ��Ӧ�õ�logo������sd����logo�ļ�����
	
		 static String path = Environment.getExternalStorageDirectory()+File.separator+"logo"+File.separator ;
	
		 public static void saveLogo(String fileName,Drawable drawable){
			 System.out.println("----------path------------"+path);
			 String filepath = path+fileName+".png" ;    
			 File file = new File(filepath) ;
			 if(!file.getParentFile().exists()){            //    �ж�path�Ƿ����    
				 file.getParentFile().mkdir() ;             // ������  �򴴽�
			 }
			 System.out.println("----------filepath------------"+filepath);
			BitmapDrawable bp = (BitmapDrawable) drawable ;      //    ��drawableת����Bitmap����
			Bitmap bitmap = bp.getBitmap() ;
			 try {
				FileOutputStream stream = new FileOutputStream(file) ;               //   �����  
				bitmap.compress(CompressFormat.PNG, 100, stream) ;          //  ��.PNG��ʽ����logo
				try {
					stream.close() ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("�ļ�û���ҵ�...");
			}       
			 
		 }
	
}
