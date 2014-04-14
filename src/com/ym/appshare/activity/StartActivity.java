package com.ym.appshare.activity;

import java.util.ArrayList;

import java.util.List;
import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.service.WeiboUserDao;
import com.ym.appshare.util.ToolsUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends Activity {
	
	private ImageView iv_start = null ;
	
	boolean flag = false ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_start) ;
		
		iv_start = (ImageView) super.findViewById(R.id.iv_start) ;
		
		
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f) ;    // �����Ե�����
		animation.setDuration(3000);
		iv_start.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				Toast.makeText(StartActivity.this, "������ʼ", Toast.LENGTH_SHORT)
				.show();
			//	init() ;
				//ToolsUtil.checkNetWork(StartActivity.this) ;
				flag = checkNetWork(StartActivity.this);
				System.out.println("--------NetworkInfo.State------->"+flag) ;
				if (flag) {
					Toast.makeText(StartActivity.this, "����������",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(StartActivity.this, "û����������",
							Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Toast.makeText(StartActivity.this, "��������", Toast.LENGTH_SHORT)
				.show();
		//		choose() ;
				Intent intent = new Intent(StartActivity.this,MainActivity.class) ;
				startActivity(intent) ;
				StartActivity.this.finish() ;
			}
		}) ;
		
	}
	// ��������Ƿ���
		public boolean checkNetWork(StartActivity activity) {
			boolean flag = false;
			// ConnectivityManager manager = (ConnectivityManager)
			// Context.getSystemService((Context.CONNECTIVITY_SERVICE)) ;
			ConnectivityManager manager = (ConnectivityManager) activity
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

		}
		//�����Ի���
		public void createDialog(){
			/*Dialog dialog = new Dialog(LoadActivity.this, R.style.dialog_style);
			dialog.setTitle("tishi") ;*/
			
			AlertDialog.Builder builder = new AlertDialog.Builder(
					StartActivity.this).setIcon(R.drawable.ic_launcher)
					.setTitle("��ʾ").setMessage("û������,ǰ������...")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						//	startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)) ;    //������������ 
						//	LoadActivity.this.finish() ;   //�˳�
							startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)); 
							StartActivity.this.finish() ;
						}
					}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
			builder.create().show() ;
		}
		
		 /*
		   *    ��̬�� ����user����
		   */
		  public static class  Wbu{
			  static WeiboUser wbu = null ;       //  ��������ݿ��ȡ��user����
			  static List<WeiboUser> ulist = null ;
		  }
		
}
