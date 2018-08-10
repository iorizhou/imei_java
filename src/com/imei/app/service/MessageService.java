package com.imei.app.service;

import java.util.List;

import com.imei.app.entity.Message;

public interface MessageService {
	List<Message> queryUnreadList(long recverId);
	int updateMessageReadStatus(long msgId,int status);
}
