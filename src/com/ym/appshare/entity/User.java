package com.ym.appshare.entity;

public class User {                  //    �û�ʵ����
	
	private String userId ;      //�˺�  ����
	private String nickName ;     //  �ǳ�
	private String password ;         //����
	private String confirmPw ;    //�ٴ���������
	
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
