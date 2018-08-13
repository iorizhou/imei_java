package com.imei.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Diary;

public interface DiaryService {
	public List<Diary> getDiaryListByType(long typeid,int index,int count);
	Diary queryById(long id);
	int getCountByItemId(long itemId);
	List<Diary> getListByItemId(long itemId,int index,int count);
}
