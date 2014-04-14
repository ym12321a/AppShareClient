package com.ym.appshare.activity;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import com.ym.appshare.adapter.SpListViewAdapter;
import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.service.WeiboUserDao;
import com.ym.appshare.util.ToolsUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class WbuLoginActivity extends Activity implements OnClickListener{

	private ImageView iv_uhead ,iv_addwb,iv_login_wb;
	private TextView tv_scname,tv_des ;
	private Spinner sp_uname ;
	WeiboUser wbu ;
	List<WeiboUser> wbus ;
	SharedPreferences preferences; // 保存登陆信息
	boolean  is_login = false ;      //     判断  是否登录过
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_wlogin) ;
		preferences = getSharedPreferences("login", MODE_PRIVATE); // 保存登陆信息 到
		init() ;   //初始化
		iv_addwb.setOnClickListener(this) ;
		iv_login_wb.setOnClickListener(this) ;
	//	iv_uhead.setImageDrawable(wbu.getUhead()) ;
		//tv_des.setText(wbu.getDescription()) ;
		
	}
	
	//初始化
	public void init(){
		WeiboUserDao userDao = new WeiboUserDao(WbuLoginActivity.this) ;
		iv_uhead = (ImageView) super.findViewById(R.id.iv_uhead) ;
		iv_addwb = (ImageView) super.findViewById(R.id.iv_addwb) ;
		iv_login_wb = (ImageView) super.findViewById(R.id.iv_login_wb) ;
		tv_scname = (TextView) super.findViewById(R.id.tv_scname) ;
		tv_des = (TextView) super.findViewById(R.id.tv_des) ;
		sp_uname = (Spinner) super.findViewById(R.id.sp_uname) ;
		wbus = userDao.queryAll() ;
		if(wbus != null){
			SpListViewAdapter adapter = new SpListViewAdapter(WbuLoginActivity.this, wbus) ;
			sp_uname.setAdapter(adapter) ;
			sp_uname.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					WeiboUser user = wbus.get(position) ;
					ToolsUtil.saveUser = user ;
					iv_uhead.setImageDrawable(user.getUhead()) ;
					tv_des.setText(user.getDescription()) ;
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			}) ;
			}
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.iv_addwb :              //  跳转到授权   
			Intent intent = new Intent(WbuLoginActivity.this, LoginActivity.class) ;
			startActivity(intent) ;
			finish() ;
			break ;
		case R.id.iv_login_wb :            //   微博账号登陆
			Editor editor = preferences.edit(); // 编辑对象
			is_login = true ;
			editor.putBoolean("is_login", is_login) ;         //   true   登录过
			editor.commit() ;
			Intent intent1 = new Intent(WbuLoginActivity.this, MainActivity.class) ;
			startActivity(intent1) ;
			//setResult(1, intent1) ;
			finish() ;
			break ;
		}
	}

	
	
	
	
	
}
	
	

