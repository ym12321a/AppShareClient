package com.ym.appshare.activity;

import com.ym.appshare.service.AsynHttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private ImageView btn_back ;
	private EditText user_Name,user_Nick,user_Pass,user_Repass ;
	private Button btn_register ;
	String name,nick,pass,repass ;     //����༭������
	MyEditListener editListener ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_register) ;
		editListener = new MyEditListener() ;
		init() ;   
		
		user_Name.setOnFocusChangeListener(editListener) ;
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent() ;
				intent.setClass(RegisterActivity.this, LoginActivity.class) ;
				startActivity(intent) ;
				RegisterActivity.this.finish() ;
			}
		}) ;
		
		
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(register()){    //    true  
					Toast.makeText(RegisterActivity.this, "Register", Toast.LENGTH_LONG).show() ;
					Intent intent  = new Intent();
					intent.putExtra("userId", name) ;
					intent.putExtra("pass", pass) ;
				//	setResult(1, data)                      //  ע��ɹ�    �����ݷ��ص�¼����   ������  1   ������  1
					setResult(1, intent) ;		
					RegisterActivity.this.finish() ;
				}else{
					Toast.makeText(RegisterActivity.this, "can't Register ", Toast.LENGTH_LONG).show() ;
					//		setResult(0) ;       //  ע��ʧ��    ������  0 ��
				}
			}
		}) ;
		
	}
	
	//��ʼ��
	public void init(){
		user_Name = (EditText) super.findViewById(R.id.ed_userId) ;
		user_Nick = (EditText) super.findViewById(R.id.ed_nick) ;
		user_Pass = (EditText) super.findViewById(R.id.ed_pass) ;
		user_Repass = (EditText) super.findViewById(R.id.ed_cfpass) ;
		btn_register = (Button) super.findViewById(R.id.btn_reg) ;
		btn_back =  (ImageView) super.findViewById(R.id.btn_back) ;
		/*name = user_Name.getText().toString() ;
		nick = user_Nick.getText().toString() ;
		pass = user_Pass.getText().toString() ;       ��ʱ  �༭��������
		repass = user_Repass.getText().toString() ;*/
	}
	//�û�ע��                   // ����  Ϊ��   2    //  1 ע��ɹ�  ����   // ע�����   ���� 0     //  3  �û��� ��ע��   
	public boolean register(){
		boolean temp = false ;
		name = user_Name.getText().toString() ;
		nick = user_Nick.getText().toString() ;
		pass = user_Pass.getText().toString() ;
		repass = user_Repass.getText().toString() ;
		String result = AsynHttp.register(name, nick, pass) ;
		System.out.println("----------register result-------------"+result);
		if(result != null ){
			if(result.equals("0")){
				Toast.makeText(this, R.string.regback_result0, Toast.LENGTH_SHORT).show() ;
				temp = false ;
			}else if(result.equals("1")){
				Toast.makeText(this, R.string.regback_result1, Toast.LENGTH_SHORT).show() ;
				temp = true ;
			}else if(result.equals("2")){
				Toast.makeText(this, R.string.regback_result2, Toast.LENGTH_SHORT).show() ;
				temp = false ;
			}else if(result.equals("3")){
				Toast.makeText(this, R.string.regback_result3, Toast.LENGTH_SHORT).show() ;
				temp = false ;
			}
		}
		return temp ;
	}
	//��� �༭������
	public boolean validate(){
		boolean flag = false ;
		return flag ;
	}
	//����  ���༭������
		class MyEditListener implements OnFocusChangeListener{

			@Override
			public void onFocusChange(View v, boolean hasFocus) {      
				// TODO Auto-generated method stub
				if(hasFocus){                   //user_Name,user_Nick,user_Pass,user_Repass ;
					System.out.println("------------��ý���------>"+v.getId()) ;
				}else{
					System.out.println("------------ʧȥ����------>"+v.getId()) ;
					if(user_Name.getText().length() > 0){
					//	user_Name.set
						    
					}else{
						//user_Name.setEms(R.string.nameId_error) ;
						user_Name.setError("ddd") ;
					}
				}
			}
		}
	
}
