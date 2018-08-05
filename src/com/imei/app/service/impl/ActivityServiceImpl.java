package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.ActivityDao;
import com.imei.app.entity.Activity;
import com.imei.app.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	
	@Autowired
	private ActivityDao activityDao;
	@Override
	public List<Activity> getHomepageActivity() {
		// TODO Auto-generated method stub
		return activityDao.getHomepageActivity();
	}

}
