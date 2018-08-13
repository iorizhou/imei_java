package com.imei.app.service;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Order;

public interface OrderService {
	int create(Order order);
	int beOverdue(long id);
	int pay(long id,int payChannel,String payOrderId,long payedCount);
	int consume(long id,String consumeCode,long consumeUserId);
}
