package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.WKRedPacketDao;
import com.imei.app.entity.WKRedPacket;
import com.imei.app.service.WKRedPacketService;
@Service
public class WKRedPacketServiceImpl implements WKRedPacketService {
	@Autowired
	WKRedPacketDao dao;
	@Override
	public int save(WKRedPacket packet) {
		// TODO Auto-generated method stub
		return dao.save(packet);
	}

	@Override
	public int use(long id, int status) {
		// TODO Auto-generated method stub
		return dao.use(id, status);
	}

	@Override
	public List<WKRedPacket> queryListByUserId(long userId, int status) {
		// TODO Auto-generated method stub
		return dao.queryListByUserId(userId, status);
	}

}
