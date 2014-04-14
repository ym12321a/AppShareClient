package com.ym.appshare.activity;

import java.util.ArrayList;
import java.util.List;
import com.ym.appshare.adapter.*;
import com.ym.appshare.service.AsynHttp;
import com.ym.appshare.util.AppInfoToList;
import com.ym.appshare.util.CreateJsonData;
import com.ym.appshare.util.ToolsUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AppShowActivity extends Activity implements Runnable ,OnItemClickListener {
	
	private static final int SEARCH_APP = 0;       //搜索App
	private static final int DELETE_APP = 1;            //删除后
	private List<PackageInfo> packageInfos;     //  所有的应用 
	private List<PackageInfo> userPackageInfos;    //  用户安装的应用
	private List<PackageInfo> showPackageInfos;       // 要显示的应用
	private ProgressDialog pd;
	GridView gv;
	ListView lv;
	ImageButton ib_change_category;       //   切换    所有/ 用户安装    的 应用
	ImageButton ib_change_view;                  //  切换  视图       listview /  gridview
	private ImageView btn_back ;
	private boolean allApplication = true;                  //判断 显示所有应用  还是   用户安装的  
	private boolean isListView = false;               //  判断  是gridview还是  listview显示
	boolean is_login = false ;              //   判断是否已登录
	SharedPreferences preferences  ;          //   取得is_login的值 
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);       
			if(msg.what == SEARCH_APP) {
				showPackageInfos = packageInfos;   
				gv.setAdapter(new GridViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
				lv.setAdapter(new ListViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
				pd.dismiss();
				setProgressBarIndeterminateVisibility(false);
			}
			if(msg.what == DELETE_APP) {
				System.out.println("Delete App Success!!");
			}
			
		}
		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_appshow);
        setProgressBarIndeterminateVisibility(true);
        preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
		is_login = preferences.getBoolean("is_login", false) ;         // 取得 is_login     默认false
        gv = (GridView) super.findViewById(R.id.gv_apps);
        lv = (ListView)super.findViewById(R.id.lv_apps);
        ib_change_category = (ImageButton) super.findViewById(R.id.ib_change_category);
        ib_change_view = (ImageButton) super.findViewById(R.id.ib_change_view);
        btn_back = (ImageView) super.findViewById(R.id.btn_back) ;
        gv.setOnItemClickListener(this);                 //  item  点击事件
        lv.setOnItemClickListener(this);
    
      
        
        
        btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent() ;
				intent.setClass(AppShowActivity.this, MainActivity.class) ;
				startActivity(intent) ;
				AppShowActivity.this.finish() ;
			}
		}) ;
        
        
        
        
        
        ib_change_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isListView) {
					//Toast.makeText(ShowAppActivity.this, "网格显示", Toast.LENGTH_SHORT).show();
					ib_change_view.setImageResource(R.drawable.grids);
					lv.setVisibility(View.GONE);
					gv.setVisibility(View.VISIBLE);
					isListView  = false;
				} else {
					//Toast.makeText(ShowAppActivity.this, "列表显示", Toast.LENGTH_SHORT).show();
					ib_change_view.setImageResource(R.drawable.list);
					gv.setVisibility(View.GONE);
					lv.setVisibility(View.VISIBLE);
					isListView = true;
				}
				
			}
        	
        });
        ib_change_category.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(allApplication) {
					ib_change_category.setImageResource(R.drawable.user);
					//gv.setAdapter(new GridViewAdapter(ShowAppActivity.this,userPackageInfos));
					showPackageInfos = userPackageInfos;
					allApplication = false;
					//Toast.makeText(ShowAppActivity.this, "用户安装的程序列表", Toast.LENGTH_SHORT).show();
				} else {
					ib_change_category.setImageResource(R.drawable.all);
					//gv.setAdapter(new GridViewAdapter(ShowAppActivity.this,packageInfos));
					showPackageInfos = packageInfos;
					allApplication = true;
				}
				gv.setAdapter(new GridViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
				lv.setAdapter(new ListViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
				
			}});
        pd = ProgressDialog.show(this, "请稍候...", "正在搜索你所安装的应用程序...",true,false);
        Thread t = new Thread(this);
        t.start();
        
    }
      //   异步  取得 本机 所有的应用    并把应用信息发送给服务器 
	@Override
	public void run() {
		
		packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		  System.out.println("------------1-------------->"+packageInfos.size()) ;  
		/* if(is_login){
		//   把appinfo 封装成JSON数据  提交给服务器
			  String infoJson = CreateJsonData.ListToJson("appInfos", AppInfoToList.infoToList(packageInfos, getPackageManager())) ;
			  if(ToolsUtil.userId  == null || ToolsUtil.saveUser != null){
					 AsynHttp.sendToService(infoJson,ToolsUtil.saveUser.getScreenName()) ;
				 }else{
					 AsynHttp.sendToService(infoJson,ToolsUtil.userId) ;
				 }
		 }*/
		  chooseSendInfo() ;
		//  System.out.println("-----------------2--------->"+infoJson) ;
		  
		userPackageInfos = new ArrayList<PackageInfo>();
		for(int i=0;i<packageInfos.size();i++) {
			
			PackageInfo temp = packageInfos.get(i);
			ApplicationInfo appInfo = temp.applicationInfo;
			boolean flag = false;
			if((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {        // 系统应用  
				 // Updated system app
				flag = true;
			} else if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {            //用户安装应用
				// Non-system app
				flag = true;
			}
			if(flag) {
				userPackageInfos.add(temp);
			}
		}
		
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHandler.sendEmptyMessage(SEARCH_APP);
		
		try {
			Thread.currentThread().sleep(5000);
			mHandler.sendEmptyMessage(DELETE_APP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		final PackageInfo tempPkInfo = showPackageInfos.get(position);
		//new Dialog to choose a choice
		//创建Dialog的构造器
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选项");
		builder.setIcon(R.drawable.dialog_icon) ;
		builder.setItems(R.array.choice,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					String packageName = tempPkInfo.packageName;
					ActivityInfo activityInfo = tempPkInfo.activities[0];               //   取得 activity
					if(activityInfo == null) {
						Toast.makeText(AppShowActivity.this, "没有任何activity", Toast.LENGTH_SHORT).show();
						return;
					}
					String activityName = activityInfo.name;       
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(packageName,activityName));     //  启动  应用
					startActivity(intent);
					
					break;
				case 1:
					showAppDetail(tempPkInfo);            //   显示详细信息
					break;
				case 2:
					Uri packageUri = Uri.parse("package:" + tempPkInfo.packageName);        //  跳转   删除应用  
					Intent deleteIntent = new Intent();
					deleteIntent.setAction(Intent.ACTION_DELETE);
					deleteIntent.setData(packageUri);
					startActivityForResult(deleteIntent, 0);                 //    请求码   0
					break;
				}
				
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
		
	}
	
	
	//   删除 操作 后  返回  取得所有的应用           请求码  0
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		packageInfos = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		userPackageInfos = new ArrayList<PackageInfo>();
		for(int i=0;i<packageInfos.size();i++) {
			
			PackageInfo temp = packageInfos.get(i);
			ApplicationInfo appInfo = temp.applicationInfo;
			boolean flag = false;
			if((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
				 // Updated system app
				flag = true;
			} else if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				// Non-system app
				flag = true;
			}
			if(flag) {
				userPackageInfos.add(temp);
			}
		}
		
		if(allApplication) {
			showPackageInfos = packageInfos;
		} else {
			showPackageInfos = userPackageInfos;
		}
		
		gv.setAdapter(new GridViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
		lv.setAdapter(new ListViewAdapter(AppShowActivity.this,showPackageInfos,getPackageManager()));
		
		
	}
	
	//异步获取 应用 
	//class MyTask extends AsyncTask<Params, Progress, Result>{
	//}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	//   显示 应用详细信息  
	private void showAppDetail(PackageInfo packageInfo) {
		LayoutInflater inflater = LayoutInflater.from(AppShowActivity.this) ;  
		View view = inflater.inflate(R.layout.show_appdetail, null) ;         //自定义dialog 界面
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	//	Dialog dialog = new Dialog(this, R.style.appdetail) ;
		//dialog.setTitle("详细信息") ;
		builder.setTitle("详细信息");
		/*StringBuffer message = new StringBuffer();
		message.append("程序名称:" + packageInfo.applicationInfo.loadLabel(getPackageManager()));
	//	message.append("\n 包名:" + packageInfo.packageName);
		message.append("\n 版本号:" + packageInfo.versionCode);
		message.append("\n 版本名:" + packageInfo.versionName);
		builder.setMessage(message.toString());*/
		TextView app_Name = (TextView) view.findViewById(R.id.tv_appname) ;
		TextView app_Vcode = (TextView) view.findViewById(R.id.tv_appversioncode) ;
		TextView app_Vname = (TextView) view.findViewById(R.id.tv_appversionname) ;
		app_Name.setText(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString()) ;
		app_Vcode.setText(packageInfo.versionCode+"") ;
		app_Vname.setText(packageInfo.versionName) ;
		builder.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));       //应用 logo
		builder.setPositiveButton("确定", null);
		builder.setView(view) ;
	//	builder.create().s
	//	 dialog = builder.create().setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
		builder.create().show() ;
	}
	
	//  判断那种用户有登陆信息  后发送appinfo给服务器
	public void chooseSendInfo() {
		if (is_login) {
			// 把appinfo 封装成JSON数据 提交给服务器
			String infoJson = CreateJsonData
					.ListToJson("appInfos", AppInfoToList.infoToList(
							packageInfos, getPackageManager()));
			if (ToolsUtil.userId == null) {
				if (ToolsUtil.saveUser != null) {
					AsynHttp.sendToService(infoJson,
							ToolsUtil.saveUser.getScreenName());
				}
			} else {
				if (ToolsUtil.saveUser == null) {
					AsynHttp.sendToService(infoJson, ToolsUtil.userId);
				}
			}
		}
	}
	
}
