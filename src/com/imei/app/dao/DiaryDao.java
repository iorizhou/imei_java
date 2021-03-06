package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Diary;

public interface DiaryDao {
	List<Diary> getDiaryListByType(@Param("typeid")long typeid,@Param("index")int index,@Param("count")int count);
	Diary queryById(@Param("id")long id);
	int getCountByItemId(@Param("item_id")long itemId);
	List<Diary> getListByItemId(@Param("item_id")long itemId,@Param("index")int index,@Param("count")int count);
}
