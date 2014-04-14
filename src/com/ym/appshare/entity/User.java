package com.ym.appshare.entity;

public class User {                  //    用户实体类
	
	private String userId ;      //账号  邮箱
	private String nickName ;     //  昵称
	private String password ;         //密码
	private String confirmPw ;    //再次输入密码
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPw() {
		return confirmPw;
	}
	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
	}
	public User() {}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", nickName=" + nickName
				+ ", password=" + password + "]";
	}
	public User(String userId, String nickName, String password) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.password = password;
	}
	
	
	
	
}
