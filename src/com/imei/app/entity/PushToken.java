package com.imei.app.entity;

public class PushToken {
	private long id;
	private long userId;
	private String pushToken;
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getPushToken() {
		return pushToken;
	}


	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}


	public PushToken(long userId, String pushToken) {
		super();
		this.userId = userId;
		this.pushToken = pushToken;
	}


	public PushToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
