package com.ym.appshare.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class LoadImage {               //   异步加载图片   接口回调
	
	public String image_path ;
	
	public LoadImage(String image_path){
		this.image_path = image_path ;
	}
	
public void getImage(final LimageCallBack limageCallBack){
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Drawable d = (Drawable) msg.obj ;
				limageCallBack.getDrawable(d) ;
				super.handleMessage(msg);
			}
			
		};
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					InputStream is =  new URL(image_path).openStream()  ;
					Drawable drawable = Drawable.createFromStream(is, "") ;
					Message message = new Message() ;
					message.obj = drawable ;
					handler.sendMessage(message) ;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}
		}).start() ;
		
		
		
	}

	//接口回调   
	public interface LimageCallBack{
		public void getDrawable(Drawable drawable) ;
	}
	
}
