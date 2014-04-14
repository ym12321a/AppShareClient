package com.ym.appshare.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;
import com.ym.appshare.entity.Params;
import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.service.AsynHttp;
import com.ym.appshare.service.ConnectUrl;
import com.ym.appshare.service.SinaWbConstant;
import com.ym.appshare.service.WeiboUserDao;
import com.ym.appshare.util.ToolsUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	// String code,token,expires_in,id ; // 授权后返回 的参数
	String id ,token,expires_in; //
	public static Oauth2AccessToken accessToken;
	private Weibo weibo;
	private Button btn_login, btn_register; // 登录 注册
	private ImageView btn_back, iv_sina_login;
	// private ImageButton btn_back = null ;
	private EditText user_Name, user_Pas;
	private CheckBox save_box; // 保存单选框
	String user_N, user_P, s_user, s_pass; // s_user s_pass 表示从保存文件中读取信息
											// preference
	boolean flag; // 判断输入框验证结果
	// Intent intent = null ;
	SharedPreferences preferences; // 保存登陆信息
	boolean is_check = true; // 判断是否记住信息
	boolean  is_login = false ;      //     判断  是否登录过
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_login);
		MyEditListener editListener = new MyEditListener();
		preferences = getSharedPreferences("login", MODE_PRIVATE); // 保存登陆信息 到											// login
		init(); // 初始化控件
		initWeiBoSDK(); // 初始化 sina微博 SDK
		user_Name.setOnFocusChangeListener(editListener); // 编辑框监听
		user_Pas.setOnFocusChangeListener(editListener);

		is_check = preferences.getBoolean("is_save", true); // 从preference中读取保存信息
		if (is_check) {
			save_box.setChecked(true); // 勾选 记住信息
			s_user = preferences.getString("s_user", "");
			s_pass = preferences.getString("s_pass", "'");
			System.out.println("************ read preferences data**********"
					+ s_user + "、" + s_pass);
			user_Name.setText(s_user);
			user_Pas.setText(s_pass);
		} else {
			save_box.setChecked(false);
			user_Name.setText("");
			user_Pas.setText("");
		}

		// 返回按钮
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});
		// 登录按钮
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (login()) {
					is_login = true ;              //   login()为true      以登录   
					System.out
					.println("*********login user*** "+user_N  );
					if (save_box.isChecked()) {
						ToolsUtil.userId = user_N ;            //用户名保存
						Editor editor = preferences.edit(); // 编辑对象
						editor.putBoolean("is_save", true);
						editor.putBoolean("is_login", is_login) ;         //   true   登录过
						editor.putString("s_user", user_N);
						editor.putString("s_pass", user_P);
						System.out
								.println("************ save preferences data**********"
										+ user_N + "、" + user_P+"、"+is_login);
						editor.commit();
					} else {                                            //   不记住登录信息   但保存（登录过） is_login  true
						ToolsUtil.userId = user_N ;            //用户名保存
						Editor editor = preferences.edit();
						editor.putBoolean("is_save", false);
						editor.putBoolean("is_login", is_login) ;         //   true   登录过
						editor.putString("s_user", "");
						editor.putString("s_pass", "");
						editor.commit();
						System.out
						.println("************ don't save preferences data**********"
								+is_login);
					}
					Intent intent = new Intent(); // 登录成功 跳转到 MainActivity 页面 把								
			//		intent.putExtra("userId", user_N);           // 用户id传过去
					intent.setClass(LoginActivity.this, MainActivity.class);
					setResult(1, intent); // resultCode 1
					LoginActivity.this.finish();    
					// startActivity(intent) ;
					// Toast.makeText(LoginActivity.this, "Login",
					// Toast.LENGTH_LONG).show() ;
				} else {
					Editor editor = preferences.edit();              //   temp为false    未登录
					editor.putBoolean("is_login", false) ;
					editor.commit();
				}
			}
		});
		// 注册按钮
		btn_register.setOnClickListener(new OnClickListener() { 

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivityForResult(intent, 1);               // 请求码 1 从注册界面返回 ；
			}
		});
				// 使用sina微博 登陆   授权页面
		
		iv_sina_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
					weibo.authorize(LoginActivity.this, new AuthDialogListener()) ;
			}
		});

	}

	// 初始化
	public void init() {

		iv_sina_login = (ImageView) super.findViewById(R.id.iv_sina_login);
		btn_back = (ImageView) super.findViewById(R.id.btn_back);
		// btn_back = (ImageButton) super.findViewById(R.id.btn_back) ;
		user_Name = (EditText) super.findViewById(R.id.user_name);
		user_Pas = (EditText) super.findViewById(R.id.user_pass);
		btn_login = (Button) super.findViewById(R.id.btn_login);
		btn_register = (Button) super.findViewById(R.id.btn_register);
		save_box = (CheckBox) super.findViewById(R.id.save_login);
	}

	// 初始化 sina微博SDK
	public void initWeiBoSDK() {
		weibo = Weibo.getInstance(SinaWbConstant.APP_KEY,
				SinaWbConstant.REDIRECT_URL);
	}

	// 监听 检查编辑框内容

	class MyEditListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub

		}
	}

	// 判断返回码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) { // 1 表示 从注册界面返回
			if (resultCode == 1) { // 1 表示成功
				Intent intent = data;
				String userId = intent.getStringExtra("userId");
				String pass = intent.getStringExtra("pass");
				System.out.println("************ intent data**********"
						+ userId + "、" + pass);
				user_Name.setText(userId);
				user_Pas.setText(pass);
			} else if (resultCode == 0) { // 失败

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 用户登录 // 登陆 参数为空 2 // 1 成功 //// 0 用户不存在
	public boolean login() {
		boolean temp = false;
		String result = null;
		user_N = user_Name.getText().toString();
		user_P = user_Pas.getText().toString();
		result = AsynHttp.login(user_N, user_P);
		System.out.println("----------login result-------------" + result);
		if (result != null) {
			if (result.equals("0")) {
				Toast.makeText(this, R.string.logback_result0,
						Toast.LENGTH_SHORT).show();
				temp = false;
			} else if (result.equals("1")) {
				Toast.makeText(this, R.string.logback_result1,
						Toast.LENGTH_SHORT).show();
				temp = true;
			} else if (result.equals("2")) {
				Toast.makeText(this, R.string.logback_result2,
						Toast.LENGTH_SHORT).show();
				temp = false;
			}else if(result.equals("3")){
				Toast.makeText(this, "网络异常",
						Toast.LENGTH_SHORT).show();
				temp = false;
			}
		}
		return temp;
	}

	// 微博 认证
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			token = values.getString("access_token");
			expires_in = values.getString("expires_in");
			id = values.getString("uid"); // 用户id
			LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
			if (LoginActivity.accessToken.isSessionValid()) {
				// String date = new
				// SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new
				// java.util.Date(MainActivity.accessToken.getExpiresTime()));
				// Toast.makeText(LoginActivity.this, "认证成功:"+id,
				// Toast.LENGTH_SHORT).show();
				UsersAPI usersAPI = new UsersAPI(accessToken);           //      调用userApi  获取用户信息
				usersAPI.show(Long.parseLong(id), requestListener);
			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	/**
	 * request监听，用以获取返回的用户资料
	 */
	RequestListener requestListener = new RequestListener() {

		@Override
		public void onComplete(String arg0) {                   //   返回用户个人资料   
			// TODO Auto-generated method stub
		//	System.out.println("************************" + arg0);
			try {
				JSONObject json = new JSONObject(arg0) ;                       
				if(json != null){
					WeiboUser wbu = new WeiboUser() ;
					wbu.setUid(id) ;
					wbu.setToken(token) ;
					wbu.setExpires_in(expires_in) ;
					wbu.setScreenName(json.getString("screen_name")) ;
					wbu.setLocation(json.getString("location")) ;
					wbu.setDescription(json.getString("description")) ;
					wbu.setFollowersCount(json.getInt("followers_count")) ;
					wbu.setFriendsCount(json.getInt("friends_count")) ;
					wbu.setStatusesCount(json.getInt("statuses_count")) ;
					wbu.setGender(json.getString("gender")) ;
					wbu.setUhead(getUhead(json.getString("profile_image_url"))) ;
					//System.out.println("*************333***********"+wbu.toString() );
					WeiboUserDao userDao = new WeiboUserDao(LoginActivity.this) ;
					long i  = userDao.insertToBase(wbu) ;                                             //          解析并保存到本地数据库
					if(i>0){
						Intent intent = new Intent(LoginActivity.this,WbuLoginActivity.class) ;
						startActivity(intent) ;
					}
				//	System.out.println("*************333***********"+i );
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void onError(WeiboException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub

		}
	};
	
	
	/**
	 * 根据链接取得头像图片
	 */  
	  public Drawable getUhead(String headUrl){
		
		  try {
			Drawable drawable = Drawable.createFromStream(new URL(headUrl).openStream(), "") ;
			return drawable ;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			return null ;	
	  }
	  
	 
	  
}

   
