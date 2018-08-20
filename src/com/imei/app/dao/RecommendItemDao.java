package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Item;
import com.imei.app.entity.RecommendItem;

public interface RecommendItemDao {
	
	List<RecommendItem> queryListByActivityid(@Param("activityid")long activityid,@Param("index")int index,@Param("count")int count);
	
	
	
	
}
