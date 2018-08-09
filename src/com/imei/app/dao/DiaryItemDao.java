package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.DiaryItem;

public interface DiaryItemDao {
	List<DiaryItem> queryListByDiaryId(@Param("diary_id")long id,@Param("index")int index,@Param("count")int count);
	DiaryItem queryById(@Param("id")long id);
}
