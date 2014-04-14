package com.ym.appshare.activity;

import java.util.ArrayList;
import java.util.List;

import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.service.WeiboUserDao;
import com.ym.appshare.util.ToolsUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,OnClickListener{
	private ImageView but_login = null ;
	//private ImageButton but_login = null ;
	long currentTime = System.currentTimeMillis() ;
	private GridView gridView = null ;
	int[] images = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4} ;
	MyAdapter adapter = null ;
	//String userId = null ;             //    保存从登录页面传回的id值   
	boolean is_login = false ;              //   判断是否已登录
	SharedPreferences preferences  ;          //   取得is_login的值 
	WeiboUserDao userDao = null ;
	List<WeiboUser> wbus = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
		is_login = preferences.getBoolean("is_login", false) ;         // 取得 is_login     默认false
		userDao = new WeiboUserDao(MainActivity.this) ;
		wbus = new ArrayList<WeiboUser>() ;
		System.out.println("---------0------"+is_login) ;
		adapter = new MyAdapter() ;
		gridView = (GridView) super.findViewById(R.id.gv) ;   
	//	but_login = (ImageButton) super.findViewById(R.id.but_tologin) ;
		but_login = (ImageView) super.findViewById(R.id.but_tologin) ;
		but_login.setOnClickListener(this) ;
		gridView.setAdapter(adapter) ;
		gridView.setOnItemClickListener(this) ;
		 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return images.length ;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
			View view = inflater.inflate(R.layout.icon_item, null );
			ImageView iv = (ImageView) view.findViewById(R.id.iv) ;
			iv.setImageResource(images[position]) ;
			return view ;
		}
		
	}




//退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK){
			if(System.currentTimeMillis() - currentTime > 2000){
				Toast.makeText(this, R.string.exit_msg, Toast.LENGTH_SHORT).show() ;
				currentTime = System.currentTimeMillis() ;
			}else{
				Editor editor = preferences.edit() ;
				editor.putBoolean("is_login", false) ;
				editor.commit() ;
				System.exit(0) ;
			}
			return true ;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		switch(position){
		case 0:                         //  我的应用
			Toast.makeText(this, R.string.myApp, Toast.LENGTH_SHORT).show() ;
			Intent intent = new Intent() ;
			intent.setClass(MainActivity.this, AppShowActivity.class) ;
			startActivity(intent) ;
			break ;
		case 1:    //我的关注
			if(is_login){              //  以登录  //      跳转到关注界面 
				if(ToolsUtil.saveUser != null || ToolsUtil.saveUser instanceof WeiboUser){
					System.out.println("---------1------"+is_login) ;
					Intent intent1 = new Intent(MainActivity.this, FriendshipsActivity.class) ;
					startActivity(intent1) ;
				}else if(ToolsUtil.userId  != null){
					Toast.makeText(MainActivity.this, "你暂时没有关注的用户！", Toast.LENGTH_SHORT).show() ;
				}else{
					choose() ;
				}
				
			}else{             //  未登录         
				Toast.makeText(MainActivity.this, "请先登录！", Toast.LENGTH_SHORT).show() ;   
				/*Intent intent1 = new Intent() ;
				intent1.setClass(MainActivity.this, LoginActivity.class) ;
				startActivityForResult(intent1, 1) ;           //    点击关注   跳转到登录           请求码  1 ；
*/					choose() ;
				}
			Toast.makeText(this, R.string.myFcous, Toast.LENGTH_SHORT).show() ;
			break ;
		case 2://              关于
			Toast.makeText(this, R.string.about, Toast.LENGTH_SHORT).show() ;
			break ;
		case 3:
			break ;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){          //   从登录界面返回
			if(resultCode == 1){      //   登录成功  返回 userId
				preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
				is_login = preferences.getBoolean("is_login", false) ;         // 取得 is_login     默认false
			}else{
				//      
			}
		}else if(requestCode == 1){
			if(resultCode == 1){      //   登录成功  返回 userId
				preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
				is_login = preferences.getBoolean("is_login", false) ;         // 取得 is_login     默认false
			}else{
				//  
			}
		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("*********2********"+is_login) ;
		if(is_login){                       //    以登录   跳转到  我的关注  界面       
			if(ToolsUtil.saveUser != null || ToolsUtil.saveUser instanceof WeiboUser){
				System.out.println("---------1------"+is_login) ;
				Intent intent1 = new Intent(MainActivity.this, FriendshipsActivity.class) ;
				startActivity(intent1) ;
			}else if(ToolsUtil.userId  != null){
				Toast.makeText(MainActivity.this, "你暂时没有关注的用户！", Toast.LENGTH_SHORT).show() ;
			}else{
				choose() ;
			}
			//startActivity(intent) ;
			//MainActivity.this.finish() ;
		}else{                                         //    未登录
			System.out.println("*********is_login********"+is_login) ;
			Toast.makeText(MainActivity.this, "请先登录！", Toast.LENGTH_SHORT).show() ;
		/*	Intent intent = new Intent() ;
			intent.setClass(this, LoginActivity.class) ;
			startActivityForResult(intent, 0) ;   */             //    0  跳转到登录页面
			choose() ;
			Toast.makeText(MainActivity.this, "关注页面", Toast.LENGTH_SHORT).show() ;
		}		
	}
	//   判断是注册用户登陆还是微博账号登陆  
	 public void choose(){
		 wbus = userDao.queryAll() ;
		 if(wbus == null || wbus.isEmpty()){
			 Intent intent = new Intent() ;
			 intent.setClass(MainActivity.this, LoginActivity.class) ;
				//startActivity(intent) ;
				startActivityForResult(intent, 0) ;                //    0  跳转到登录页面
			//	finish() ;
		 }else{
			 Intent intent1 = new Intent() ;
				intent1.setClass(MainActivity.this, WbuLoginActivity.class) ;  
			 startActivity(intent1) ;
			//	startActivityForResult(intent1, 1) ;         //    0  跳转到weibo登录页面
			//	finish() ;
		 }
	 }
	
	
	
}