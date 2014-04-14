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
	//String userId = null ;             //    ����ӵ�¼ҳ�洫�ص�idֵ   
	boolean is_login = false ;              //   �ж��Ƿ��ѵ�¼
	SharedPreferences preferences  ;          //   ȡ��is_login��ֵ 
	WeiboUserDao userDao = null ;
	List<WeiboUser> wbus = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
		is_login = preferences.getBoolean("is_login", false) ;         // ȡ�� is_login     Ĭ��false
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




//�˳�����
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
		case 0:                         //  �ҵ�Ӧ��
			Toast.makeText(this, R.string.myApp, Toast.LENGTH_SHORT).show() ;
			Intent intent = new Intent() ;
			intent.setClass(MainActivity.this, AppShowActivity.class) ;
			startActivity(intent) ;
			break ;
		case 1:    //�ҵĹ�ע
			if(is_login){              //  �Ե�¼  //      ��ת����ע���� 
				if(ToolsUtil.saveUser != null || ToolsUtil.saveUser instanceof WeiboUser){
					System.out.println("---------1------"+is_login) ;
					Intent intent1 = new Intent(MainActivity.this, FriendshipsActivity.class) ;
					startActivity(intent1) ;
				}else if(ToolsUtil.userId  != null){
					Toast.makeText(MainActivity.this, "����ʱû�й�ע���û���", Toast.LENGTH_SHORT).show() ;
				}else{
					choose() ;
				}
				
			}else{             //  δ��¼         
				Toast.makeText(MainActivity.this, "���ȵ�¼��", Toast.LENGTH_SHORT).show() ;   
				/*Intent intent1 = new Intent() ;
				intent1.setClass(MainActivity.this, LoginActivity.class) ;
				startActivityForResult(intent1, 1) ;           //    �����ע   ��ת����¼           ������  1 ��
*/					choose() ;
				}
			Toast.makeText(this, R.string.myFcous, Toast.LENGTH_SHORT).show() ;
			break ;
		case 2://              ����
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
		if(requestCode == 0){          //   �ӵ�¼���淵��
			if(resultCode == 1){      //   ��¼�ɹ�  ���� userId
				preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
				is_login = preferences.getBoolean("is_login", false) ;         // ȡ�� is_login     Ĭ��false
			}else{
				//      
			}
		}else if(requestCode == 1){
			if(resultCode == 1){      //   ��¼�ɹ�  ���� userId
				preferences = getSharedPreferences("login", MODE_PRIVATE) ;       
				is_login = preferences.getBoolean("is_login", false) ;         // ȡ�� is_login     Ĭ��false
			}else{
				//  
			}
		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("*********2********"+is_login) ;
		if(is_login){                       //    �Ե�¼   ��ת��  �ҵĹ�ע  ����       
			if(ToolsUtil.saveUser != null || ToolsUtil.saveUser instanceof WeiboUser){
				System.out.println("---------1------"+is_login) ;
				Intent intent1 = new Intent(MainActivity.this, FriendshipsActivity.class) ;
				startActivity(intent1) ;
			}else if(ToolsUtil.userId  != null){
				Toast.makeText(MainActivity.this, "����ʱû�й�ע���û���", Toast.LENGTH_SHORT).show() ;
			}else{
				choose() ;
			}
			//startActivity(intent) ;
			//MainActivity.this.finish() ;
		}else{                                         //    δ��¼
			System.out.println("*********is_login********"+is_login) ;
			Toast.makeText(MainActivity.this, "���ȵ�¼��", Toast.LENGTH_SHORT).show() ;
		/*	Intent intent = new Intent() ;
			intent.setClass(this, LoginActivity.class) ;
			startActivityForResult(intent, 0) ;   */             //    0  ��ת����¼ҳ��
			choose() ;
			Toast.makeText(MainActivity.this, "��עҳ��", Toast.LENGTH_SHORT).show() ;
		}		
	}
	//   �ж���ע���û���½����΢���˺ŵ�½  
	 public void choose(){
		 wbus = userDao.queryAll() ;
		 if(wbus == null || wbus.isEmpty()){
			 Intent intent = new Intent() ;
			 intent.setClass(MainActivity.this, LoginActivity.class) ;
				//startActivity(intent) ;
				startActivityForResult(intent, 0) ;                //    0  ��ת����¼ҳ��
			//	finish() ;
		 }else{
			 Intent intent1 = new Intent() ;
				intent1.setClass(MainActivity.this, WbuLoginActivity.class) ;  
			 startActivity(intent1) ;
			//	startActivityForResult(intent1, 1) ;         //    0  ��ת��weibo��¼ҳ��
			//	finish() ;
		 }
	 }
	
	
	
}