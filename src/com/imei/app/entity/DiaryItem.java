package com.imei.app.entity;

import java.util.Date;

public class DiaryItem {
	private long id;
	private long diaryId;
	private String title;
	private String content;
	private String publishDate;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public DiaryItem(long diaryId, String title, String content, String publishDate) {
		super();
		this.diaryId = diaryId;
		this.title = title;
		this.content = content;
		this.publishDate = publishDate;
	}
	public DiaryItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
