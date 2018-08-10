package com.imei.app.dto;

import java.util.List;

import com.imei.app.entity.Message;

public class MessageListDTO {
	private long senderId;
	private List<MessageDTO> msgList;
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public List<MessageDTO> getMsgList() {
		return msgList;
	}
	public void setMsgList(List<MessageDTO> msgList) {
		this.msgList = msgList;
	}
	public MessageListDTO(long senderId, List<MessageDTO> msgList) {
		super();
		this.senderId = senderId;
		this.msgList = msgList;
	}
	public MessageListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
