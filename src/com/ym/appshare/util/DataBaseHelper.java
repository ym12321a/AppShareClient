package com.ym.appshare.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

public class DataBaseHelper extends SQLiteOpenHelper {
/*private String uid  ;   // 用户id
	private String screenName; // 微博昵称
	private String token ;
	private String token_secret ;
	private String location ; // 地址
	private String description ; // 个人描述
	private Drawable uhead ; // 自定义图像          头像
	private String gender ; // 性别,m--男，f--女,n--未知
	private int followersCount ; // 粉丝数
	private int friendsCount ; // 关注数
	private int statusesCount ; // 微博数
	*/
	private final static String DBNAME = "appshare.db" ;
	private final static int VERSION = 1 ;
	private final static String CREATE_TABLE = "create table if not exists wbusers(id Integer primary key autoincrement,uid varchar(50),"+
			"screen_name varchar(100),token varchar(100),expires_in varchar(50),location varchar(50),description varchar(1000),gender varchar(10),"+
			"followersCount int,friendsCount int,statusesCount int,uhead BLOB)" ;
	
	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DataBaseHelper(Context context){
		super(context, DBNAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE) ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
