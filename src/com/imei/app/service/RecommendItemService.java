package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.RecommendItem;

public interface RecommendItemService {
	List<RecommendItem> queryListByActivityid(@Param("activityid")long activityid,@Param("index")int index,@Param("count")int count);
}
