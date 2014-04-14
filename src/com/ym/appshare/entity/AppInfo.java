package com.ym.appshare.entity;

public class AppInfo {     //  应用信息

	private String appPackageName ;              // 包名
	private String appVersionCode ;             //  版本号
	private String appName ;                         //  应用名
	public String getAppPackageName() {
		return appPackageName;
	}
	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}
	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Override
	public String toString() {
		return "AppInfo [appPackageName=" + appPackageName
				+ ", appVersionCode=" + appVersionCode + ", appName=" + appName
				+ "]";
	}
	public AppInfo() {}
	public AppInfo(String appPackageName, String appVersionCode, String appName) {
		this.appPackageName = appPackageName;
		this.appVersionCode = appVersionCode;
		this.appName = appName;
	}
	
	
	
}
