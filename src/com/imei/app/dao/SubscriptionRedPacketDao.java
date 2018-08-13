package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.SubscriptionRedPacket;
import com.imei.app.entity.WKRedPacket;

public interface SubscriptionRedPacketDao {
	int save(SubscriptionRedPacket packet);
	int use(@Param("id")long id,@Param("status")int status);
	List<SubscriptionRedPacket> queryListByUserId(@Param("user_id")long userId,@Param("status")int status);
	SubscriptionRedPacket queryById(@Param("id")long id);
	SubscriptionRedPacket queryById(@Param("id")long id,@Param("user_id")long userId);
}
