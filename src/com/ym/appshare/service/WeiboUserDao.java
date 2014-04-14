package com.ym.appshare.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.util.DataBaseHelper;

public class WeiboUserDao {

	DataBaseHelper helper ;
	SQLiteDatabase db ;
	String[] columns = new String[]{"uid","screen_name","token","expires_in","location","description","gender","followersCount","friendsCount","statusesCount","uhead"} ;
	public WeiboUserDao(Context context){
		helper = new DataBaseHelper(context) ;
	}
	
	//  保存用户数据信息
	
	public long insertToBase(WeiboUser wbu){
		long i = 0 ;
		db = helper.getWritableDatabase() ;
		ContentValues values = new ContentValues() ;  
		values.put("uid", wbu.getUid()) ;
		values.put("screen_name", wbu.getScreenName()) ;
		values.put("token", wbu.getToken()) ;
		values.put("expires_in", wbu.getExpires_in()) ;
		values.put("location", wbu.getLocation()) ;
		values.put("description", wbu.getDescription()) ;
		values.put("gender", wbu.getGender()) ;
		values.put("followersCount", wbu.getFollowersCount()) ;
		values.put("friendsCount", wbu.getFriendsCount()) ;
		values.put("statusesCount", wbu.getStatusesCount()) ;
		BitmapDrawable drawable = (BitmapDrawable) wbu.getUhead() ;
		Bitmap bitmap = drawable.getBitmap() ;
		ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream) ;
		values.put("uhead", stream.toByteArray()) ;
		i = db.insert("wbusers", null, values) ;
		db.close() ;
		return i;
	}
	
	//        所有信息
	public List<WeiboUser> queryAll(){
		List<WeiboUser> wbus = new ArrayList<WeiboUser>() ;
		WeiboUser wbu = null ;
		db = helper.getWritableDatabase() ;
		Cursor cursor = db.query("wbusers", columns, null, null, null, null, null) ;
		if( cursor != null ){
			while(cursor.moveToNext()){
				wbu = new WeiboUser() ;
				wbu.setUid(cursor.getString(cursor.getColumnIndex("uid"))) ;
				wbu.setScreenName(cursor.getString(cursor.getColumnIndex("screen_name"))) ;
				wbu.setToken(cursor.getString(cursor.getColumnIndex("token"))) ;
				wbu.setExpires_in(cursor.getString(cursor.getColumnIndex("expires_in"))) ;
				wbu.setLocation(cursor.getString(cursor.getColumnIndex("location"))) ;
				wbu.setDescription(cursor.getString(cursor.getColumnIndex("description"))) ;
				wbu.setGender(cursor.getString(cursor.getColumnIndex("gender"))) ;
				wbu.setFollowersCount(cursor.getInt(cursor.getColumnIndex("followersCount"))) ;
				wbu.setFriendsCount(cursor.getInt(cursor.getColumnIndex("friendsCount"))) ;
				wbu.setStatusesCount(cursor.getInt(cursor.getColumnIndex("statusesCount"))) ;
				byte[] bt = cursor.getBlob(cursor.getColumnIndex("uhead")) ;
				Bitmap bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length) ;
				Drawable drawable = new BitmapDrawable(bitmap) ;
				wbu.setUhead(drawable) ;
				wbus.add(wbu) ;
			}
		}
		db.close() ;
		return wbus ;
	}
	
	//  根据 user_id  查找用户
	public WeiboUser findUserByUser_id(String uid){
		db = helper.getReadableDatabase() ;
		WeiboUser wbu = null ;
		Cursor cursor =  db.query("wbusers", columns, "uid = ?", new String[]{uid}, null, null, null) ;
		while(cursor.moveToNext()){
			wbu = new WeiboUser() ;
			wbu.setUid(cursor.getString(cursor.getColumnIndex("uid"))) ;
			wbu.setScreenName(cursor.getString(cursor.getColumnIndex("screen_name"))) ;
			wbu.setToken(cursor.getString(cursor.getColumnIndex("token"))) ;
			wbu.setExpires_in(cursor.getString(cursor.getColumnIndex("expires_in"))) ;
			wbu.setLocation(cursor.getString(cursor.getColumnIndex("location"))) ;
			wbu.setDescription(cursor.getString(cursor.getColumnIndex("description"))) ;
			wbu.setGender(cursor.getString(cursor.getColumnIndex("gender"))) ;
			wbu.setFollowersCount(cursor.getInt(cursor.getColumnIndex("followersCount"))) ;
			wbu.setFriendsCount(cursor.getInt(cursor.getColumnIndex("friendsCount"))) ;
			wbu.setStatusesCount(cursor.getInt(cursor.getColumnIndex("statusesCount"))) ;
			byte[] bt = cursor.getBlob(cursor.getColumnIndex("uhead")) ;
			Bitmap bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length) ;
			Drawable drawable = new BitmapDrawable(bitmap) ;
			wbu.setUhead(drawable) ;
		}
		db.close() ;
		return wbu ;
	}
	
	
}
