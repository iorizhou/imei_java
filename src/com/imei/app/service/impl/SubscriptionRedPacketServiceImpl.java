package com.imei.app.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.SubscriptionRedPacketDao;
import com.imei.app.entity.SubscriptionRedPacket;
import com.imei.app.service.SubscriptionRedPacketService;

@Service
public class SubscriptionRedPacketServiceImpl implements SubscriptionRedPacketService {
	
	@Autowired
	SubscriptionRedPacketDao dao;
	@Override
	public int save(SubscriptionRedPacket packet) {
		// TODO Auto-generated method stub
		return dao.save(packet);
	}

	@Override
	public int use(long id,int status) {
		// TODO Auto-generated method stub
		return dao.use(id,status);
	}

	@Override
	public List<SubscriptionRedPacket> queryListByUserId(long userId,int status) {
		// TODO Auto-generated method stub
		return dao.queryListByUserId(userId,status);
	}
	
	
	
}
