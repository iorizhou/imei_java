package com.imei.app.dto;

import java.util.Date;

public class UserDTO {
	private long id;
	private String userId;
	private String token;
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
		return userId;
	}
	public void setPhoneNum(String phoneNum) {
		this.userId = phoneNum;
	}
	public String getPwd() {
		return token;
	}
	public void setPwd(String pwd) {
		this.token = pwd;
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
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDTO(long id, String phoneNum, String token, String nickName, Date regDate,String city) {
		super();
		this.id = id;
		this.userId = phoneNum;
		this.token = token;
		this.nickName = nickName;
		this.regDate = regDate;
		this.city = city;
	}
	
	
}
