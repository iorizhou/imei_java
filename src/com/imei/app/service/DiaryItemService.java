package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.DiaryItem;

public interface DiaryItemService {
	 List<DiaryItem> queryListByDiaryId(long id,int index,int count);
	
	
	 DiaryItem queryById(@Param("id")long id);
}
