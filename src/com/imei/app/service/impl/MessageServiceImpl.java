package com.imei.app.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.MessageDao;
import com.imei.app.entity.Message;
import com.imei.app.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageDao messageDao;

	@Override
	public List<Message> queryUnreadList(long recverId) {
		// TODO Auto-generated method stub
		return messageDao.queryUnreadList(recverId);
	}

	@Override
	public int updateMessageReadStatus(long msgId,int status) {
		// TODO Auto-generated method stub
		return messageDao.updateMessageReadStatus(msgId,status);
	}
	
	
}
