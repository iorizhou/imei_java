package com.imei.app.service;

import java.util.List;

import com.imei.app.entity.WKRedPacket;

public interface WKRedPacketService {
	int save(WKRedPacket packet);
	int use(long id,long orderId,String useDate);
	List<WKRedPacket> queryListByUserId(long userId,int status);
	WKRedPacket queryById(long id,long userId);
}
