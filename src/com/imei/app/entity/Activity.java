package com.imei.app.entity;

import java.util.Date;
import java.util.List;

import com.imei.app.dto.RecommendItemDTO;

public class Activity {
	
	private long id;
	private String name;
	private String description;
	private String createTime;
	private int isRecommendToHomepage;
	private String picUrl;
	private String jumpUrl;
	private String beginTime;
	private String endTime;
	private String city;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getIsRecommendToHomepage() {
		return isRecommendToHomepage;
	}
	public void setIsRecommendToHomepage(int isRecommendToHomepage) {
		this.isRecommendToHomepage = isRecommendToHomepage;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Activity(String name, String description, String createTime, int isRecommendToHomepage, String picUrl,
			String jumpUrl, String beginTime, String endTime,String city) {
		super();
		this.name = name;
		this.description = description;
		this.createTime = createTime;
		this.isRecommendToHomepage = isRecommendToHomepage;
		this.picUrl = picUrl;
		this.jumpUrl = jumpUrl;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.city = city;
	}
	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
