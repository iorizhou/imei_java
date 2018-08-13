package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.SubscriptionRedPacket;

public interface SubscriptionRedPacketService {
	int save(SubscriptionRedPacket packet);
	int use(long id,int status);
	List<SubscriptionRedPacket> queryListByUserId(long userId,int status);
}
