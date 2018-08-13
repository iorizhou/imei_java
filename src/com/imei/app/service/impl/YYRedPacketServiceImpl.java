package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.WKRedPacketDao;
import com.imei.app.dao.YYRedPacketDao;
import com.imei.app.entity.WKRedPacket;
import com.imei.app.entity.YYRedPacket;
import com.imei.app.service.WKRedPacketService;
import com.imei.app.service.YYRedPacketService;
@Service
public class YYRedPacketServiceImpl implements YYRedPacketService {
	@Autowired
	YYRedPacketDao dao;
	@Override
	public int save(YYRedPacket packet) {
		// TODO Auto-generated method stub
		return dao.save(packet);
	}

	@Override
	public int use(long id, int status) {
		// TODO Auto-generated method stub
		return dao.use(id, status);
	}

	@Override
	public List<YYRedPacket> queryListByUserId(long userId, int status) {
		// TODO Auto-generated method stub
		return dao.queryListByUserId(userId, status);
	}

	@Override
	public YYRedPacket queryById(long id, long userId) {
		// TODO Auto-generated method stub
		return dao.queryById(id, userId);
	}
	
	
}
