package com.imei.app.entity;

import java.util.List;

public class Type {
	private long id;
	private String picUrl;
	private String name;
	private int sortOrder;
	private long parentId;
	private long parentParentId;
	private List<Type> childItem;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	public Type(String picUrl, String name, int sortOrder) {
		super();
		this.picUrl = picUrl;
		this.name = name;
		this.sortOrder = sortOrder;
	}
	public Type() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getParentParentId() {
		return parentParentId;
	}
	public void setParentParentId(long parentParentId) {
		this.parentParentId = parentParentId;
	}
	public List<Type> getChildItem() {
		return childItem;
	}
	public void setChildItem(List<Type> childItem) {
		this.childItem = childItem;
	}
	
	
	
}
