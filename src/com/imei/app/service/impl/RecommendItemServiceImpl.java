package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.RecommendItemDao;
import com.imei.app.entity.RecommendItem;
import com.imei.app.service.RecommendItemService;

@Service
public class RecommendItemServiceImpl implements RecommendItemService {
	@Autowired
	RecommendItemDao dao;

	@Override
	public List<RecommendItem> queryListByActivityid(long activityid, int index, int count) {
		// TODO Auto-generated method stub
		return dao.queryListByActivityid(activityid, index, count);
	}
	

}
