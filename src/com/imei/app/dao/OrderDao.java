package com.imei.app.dao;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Order;

public interface OrderDao {
	int create(Order order);
	int beOverdue(@Param("id")long id);
	int pay(@Param("id")long id,@Param("payChannel")int payChannel,@Param("payOrderId")String payOrderId,@Param("payedCount")long payedCount);
	int consume(@Param("id")long id,@Param("consumeCode")String consumeCode,@Param("consumeUserId")long consumeUserId);
}
