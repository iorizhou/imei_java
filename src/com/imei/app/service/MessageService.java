package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Message;

public interface MessageService {
	int save(Message msg);

	List<Message> queryUnreadList(long recverId);
	int updateMessageReadStatus(long msgId,int status);
}
