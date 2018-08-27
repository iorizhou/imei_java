package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.SubscriptionRedPacket;

public interface SubscriptionRedPacketService {
	int save(SubscriptionRedPacket packet);
	List<SubscriptionRedPacket> queryListByUserId(long userId,int status);
	SubscriptionRedPacket queryById(long id,long userId);
	int use(long id,long orderId,String useDate);
}
