package com.imei.app.entity;

//日记的评论
public class Comment {	
	private long id;
	private long diaryId;
	private long userId;
	private String publishDate;
	private String content;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(long diaryId) {
		this.diaryId = diaryId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(long diaryId, long userId, String publishDate, String content) {
		super();
		this.diaryId = diaryId;
		this.userId = userId;
		this.publishDate = publishDate;
		this.content = content;
	}
	
	
}
