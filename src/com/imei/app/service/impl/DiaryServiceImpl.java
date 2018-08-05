package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.DiaryDao;
import com.imei.app.entity.Diary;
import com.imei.app.service.DiaryService;

@Service
public class DiaryServiceImpl implements DiaryService {
	@Autowired
	private DiaryDao diaryDao;
	@Override
	public List<Diary> getDiaryListByType(long typeid, int index, int count) {
		// TODO Auto-generated method stub
		return diaryDao.getDiaryListByType(typeid, index, count);
	}

}
