package com.imei.app.dto;

public class NewCommentReplyNoticeDTO {
	private long id;
	private String newReply;
	private String oriReply;
	private String replyTime;
	private long replyUserId;
	private long origUserId;
	private long diaryId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNewReply() {
		return newReply;
	}
	public void setNewReply(String newReply) {
		this.newReply = newReply;
	}
	public String getOriReply() {
		return oriReply;
	}
	public void setOriReply(String oriReply) {
		this.oriReply = oriReply;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(long replyUserId) {
		this.replyUserId = replyUserId;
	}
	public long getOrigUserId() {
		return origUserId;
	}
	public void setOrigUserId(long origUserId) {
		this.origUserId = origUserId;
	}
	public long getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(long diaryId) {
		this.diaryId = diaryId;
	}
	public NewCommentReplyNoticeDTO(long id, String newReply, String oriReply, String replyTime, long replyUserId,
			long origUserId, long diaryId) {
		super();
		this.id = id;
		this.newReply = newReply;
		this.oriReply = oriReply;
		this.replyTime = replyTime;
		this.replyUserId = replyUserId;
		this.origUserId = origUserId;
		this.diaryId = diaryId;
	}
	public NewCommentReplyNoticeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
