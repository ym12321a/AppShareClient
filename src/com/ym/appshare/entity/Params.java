package com.ym.appshare.entity;

public class Params {   //������
	 
	private String name ;     //��
	private String value ;     //ֵ
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
