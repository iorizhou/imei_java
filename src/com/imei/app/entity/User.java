package com.imei.app.entity;

import java.util.Date;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class User {
	private long id;
	private String phoneNum;
	private String pwd;
	private String nickName;
	private Date regDate;
	private String city;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String phoneNum, String pwd, String nickName, Date regDate,String city) {
		super();
		this.phoneNum = phoneNum;
		this.pwd = pwd;
		this.nickName = nickName;
		this.regDate = regDate;
		this.city = city;
	}
	
	
}
