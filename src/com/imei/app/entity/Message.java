package com.imei.app.entity;

import org.omg.CORBA.LongLongSeqHelper;

public class Message {
	private long id;
	private String content;
	private String sendTime;
	private long senderId;
	private long recverId;
	private int status;   //阅读状态 是否已读。0为未读，1为已读
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getRecverId() {
		return recverId;
	}
	public void setRecverId(long recverId) {
		this.recverId = recverId;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String content, String sendTime, long senderId, long recverId) {
		super();
		this.content = content;
		this.sendTime = sendTime;
		this.senderId = senderId;
		this.recverId = recverId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
