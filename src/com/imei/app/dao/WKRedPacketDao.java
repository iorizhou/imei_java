package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.WKRedPacket;

public interface WKRedPacketDao {
	int save(WKRedPacket packet);
	int use(@Param("id")long id,@Param("order_id")long orderId,@Param("use_date")String useDate);
	List<WKRedPacket> queryListByUserId(@Param("user_id")long userId,@Param("status")int status);
	WKRedPacket queryById(@Param("id")long id,@Param("user_id")long userId);
}
