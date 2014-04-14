package com.ym.appshare.entity;

public class Params {   //参数类
	 
	private String name ;     //键
	private String value ;     //值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Params(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public Params() {}
	
	
	
	
}
