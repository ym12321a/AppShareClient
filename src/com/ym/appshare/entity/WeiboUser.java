package com.ym.appshare.entity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class WeiboUser {               //  授权后   微博账号登陆
            
	//
	private String uid  ;   // 用户id
	private String screenName; // 微博昵称
	private String token ;
	private String expires_in ;
	private String location ; // 地址
	private String description ; // 个人描述
	private Drawable uhead ; // 自定义图像          头像
	private String gender ; // 性别,m--男，f--女,n--未知
	private int followersCount ; // 粉丝数
	private int friendsCount ; // 关注数
	private int statusesCount ; // 微博数
	
	
	public WeiboUser() {
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Drawable getUhead() {
		return uhead;
	}
	public void setUhead(Drawable uhead) {
		this.uhead = uhead;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}
	public int getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	public int getStatusesCount() {
		return statusesCount;
	}
	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public WeiboUser(String uid, String screenName, String token,
			String expires_in, String location) {
		this.uid = uid;
		this.screenName = screenName;
		this.token = token;
		this.expires_in = expires_in;
		this.location = location;
	}
	@Override
	public String toString() {
		return "WeiboUser [uid=" + uid + ", screenName=" + screenName
				+ ", token=" + token + ", expires_in=" + expires_in
				+ ", location=" + location + ", description=" + description
				+ ", uhead=" + uhead + ", gender=" + gender
				+ ", followersCount=" + followersCount + ", friendsCount="
				+ friendsCount + ", statusesCount=" + statusesCount + "]";
	}
	
	
	
	
	
	
}
