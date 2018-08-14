package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Order;

public interface OrderDao {
	int create(Order order);
	//使订单过期失效  由定时任务进行调度
	int beOverdue(@Param("id")long id);
	int pay(@Param("id")long id,@Param("payChannel")int payChannel,@Param("payOrderId")String payOrderId,@Param("payedCount")long payedCount);
	int consume(@Param("id")long id,@Param("consumeCode")String consumeCode,@Param("consumeUserId")long consumeUserId);
	Order queryByIdWithUserId(@Param("id")long id,@Param("userId")long userId);
	List<Order> queryListByUserId(@Param("userId")long userId);
	Order queryById(@Param("id")long id);
}
