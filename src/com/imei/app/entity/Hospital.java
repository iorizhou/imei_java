package com.imei.app.entity;

public class Hospital {
	private long id;
	private String avatar;
	private String name;
	private String addr;
	private String website;
	private String gps;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public Hospital(long id, String avatar, String name, String addr, String website, String gps) {
		super();
		this.id = id;
		this.avatar = avatar;
		this.name = name;
		this.addr = addr;
		this.website = website;
		this.gps = gps;
	}
	public Hospital() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
