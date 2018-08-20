package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Activity;

public interface ActivityDao {
	List<Activity> getHomepageActivity();
	List<Activity> getRecommendActivity(@Param("city")String city);
}
