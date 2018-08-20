package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Activity;

public interface ActivityService {
	List<Activity> getHomepageActivity();
	List<Activity> getRecommendActivity(@Param("city")String city);
}
