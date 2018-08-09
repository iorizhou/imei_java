package com.imei.app.dto;

import java.util.Date;

public class DiaryItemDTO {
	private long id;
	private long diaryId;
	private String title;
	private String content;
	private String publishDate;
	private String simpleContent;
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
	
	
	public String getSimpleContent() {
		return simpleContent;
	}
	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}
	
	public DiaryItemDTO(long id, long diaryId, String title, String content, String publishDate, String simpleContent) {
		super();
		this.id = id;
		this.diaryId = diaryId;
		this.title = title;
		this.content = content;
		this.publishDate = publishDate;
		this.simpleContent = simpleContent;
	}
	public DiaryItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
