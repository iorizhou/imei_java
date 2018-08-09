package com.imei.app.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.DiaryItemDao;
import com.imei.app.entity.Diary;
import com.imei.app.entity.DiaryItem;
import com.imei.app.service.DiaryItemService;
import com.imei.app.service.DiaryService;

@Service
public class DiaryItemServiceImpl implements DiaryItemService {
	
	@Autowired
	private DiaryItemDao diaryItemDao;
	
	@Override
	public List<DiaryItem> queryListByDiaryId(long id,int index,int count) {
		// TODO Auto-generated method stub
		return diaryItemDao.queryListByDiaryId(id,index,count);
	}

	@Override
	public DiaryItem queryById(long id) {
		// TODO Auto-generated method stub
		return diaryItemDao.queryById(id);
	}

	
}
