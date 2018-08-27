package com.imei.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.PushTokenDao;
import com.imei.app.entity.PushToken;
import com.imei.app.service.PushTokenService;

@Service
public class PushTokenServiceImpl implements PushTokenService {
	@Autowired
	PushTokenDao dao;
	
	@Override
	public int save(PushToken pushToken) {
		// TODO Auto-generated method stub
		return dao.save(pushToken);
	}

	@Override
	public int deleteByUserId(long userId) {
		// TODO Auto-generated method stub
		return dao.deleteByUserId(userId);
	}

	@Override
	public PushToken queryById(long id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public PushToken queryByUserId(long userId) {
		// TODO Auto-generated method stub
		return dao.queryByUserId(userId);
	}

	@Override
	public PushToken queryByToken(String token) {
		// TODO Auto-generated method stub
		return dao.queryByToken(token);
	}

}
