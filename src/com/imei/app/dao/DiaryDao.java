package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Diary;

public interface DiaryDao {
	List<Diary> getDiaryListByType(@Param("typeid")long typeid,@Param("index")int index,@Param("count")int count);
}
