package com.imei.app.entity;

import sun.net.www.content.text.plain;

public class DiaryType {
	private long id;
	private String name;
	private int sortOrder;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public DiaryType(String name, int sortOrder) {
		super();
		this.name = name;
		this.sortOrder = sortOrder;
	}
	public DiaryType() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
