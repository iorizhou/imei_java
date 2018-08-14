
package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Order;

public interface OrderService {
	int create(Order order);
	int beOverdue(long id);
	int pay(long id,int payChannel,String payOrderId,long payedCount);
	int consume(long id,String consumeCode,long consumeUserId);
	Order queryByIdWithUserId(long id,long userId);
	List<Order> queryListByUserId(long userId);
	Order queryById(long id);
}
