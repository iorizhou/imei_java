package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.WKRedPacket;
import com.imei.app.entity.YYRedPacket;

public interface YYRedPacketDao {
	int save(YYRedPacket packet);
	int use(@Param("id")long id,@Param("order_id")long orderId,@Param("use_date")String useDate);
	List<YYRedPacket> queryListByUserId(@Param("user_id")long userId,@Param("status")int status,@Param("hospital_id")long hospitalId);
	YYRedPacket queryById(@Param("id")long id,@Param("user_id")long userId);
}
