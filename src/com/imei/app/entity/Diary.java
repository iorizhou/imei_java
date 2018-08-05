package com.imei.app.entity;

import java.util.Date;

public class Diary {
	private long id;
	private String author;
	private Date publishTime;
	private String authorAvatar;
	private String coverImg;   //在首页显示时的两张封面图 以,号分隔
	private String simpleContent;
	private String tag;  //#小标签  例如#假体隆胸  不带#号
	private long itemId;  //对应的美容项目id 
	private String itemName;   //美容项目名称
	private String itemDiscount;  //美容项目的优惠价
	private String viewCount;   //日记查看次数
	private int commentCount;   //日记评论数
	private long diaryTypeId;   //日记所属的日记分类id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getAuthorAvatar() {
		return authorAvatar;
	}
	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	public String getSimpleContent() {
		return simpleContent;
	}
	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(String itemDiscount) {
		this.itemDiscount = itemDiscount;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	
	
	public long getDiaryTypeId() {
		return diaryTypeId;
	}
	public void setDiaryTypeId(long diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}
	public Diary(String author, Date publishTime, String authorAvatar, String coverImg, String simpleContent,
			String tag, long itemId, String itemName, String itemDiscount, String viewCount, int commentCount,long diaryTypeId) {
		super();
		this.author = author;
		this.publishTime = publishTime;
		this.authorAvatar = authorAvatar;
		this.coverImg = coverImg;
		this.simpleContent = simpleContent;
		this.tag = tag;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDiscount = itemDiscount;
		this.viewCount = viewCount;
		this.commentCount = commentCount;
		this.diaryTypeId = diaryTypeId;
	}
	public Diary() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
