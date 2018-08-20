package com.imei.app.dto;

import java.util.Date;

public class RecommendItemDTO {
	private long id;
	private long itemId;
	private int recommend;  //0不推荐 1推荐
	private String createTime;
	private String beginTime;
	private String endTime;
	private long activityId;   //对应的活动ID
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public RecommendItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecommendItemDTO(long id,long itemId, int recommend, String createTime, String beginTime, String endTime,long activityId) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.recommend = recommend;
		this.createTime = createTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.activityId = activityId;
	}
	
	
}
