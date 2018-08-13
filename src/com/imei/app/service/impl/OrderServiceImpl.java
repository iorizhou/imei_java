package com.imei.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.OrderDao;
import com.imei.app.entity.Order;
import com.imei.app.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderDao;
	@Override
	public int create(Order order) {
		// TODO Auto-generated method stub
		return orderDao.create(order);
	}

	@Override
	public int beOverdue(long id) {
		// TODO Auto-generated method stub
		return orderDao.beOverdue(id);
	}

	@Override
	public int pay(long id, int payChannel, String payOrderId, long payedCount) {
		// TODO Auto-generated method stub
		return orderDao.pay(id, payChannel, payOrderId, payedCount);
	}

	@Override
	public int consume(long id, String consumeCode,long consumeUserId) {
		// TODO Auto-generated method stub
		return orderDao.consume(id, consumeCode,consumeUserId);
	}

}
