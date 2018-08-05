package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.DiaryTypeDao;
import com.imei.app.entity.DiaryType;
import com.imei.app.entity.Item;
import com.imei.app.service.DiaryTypeService;

@Service
public class DiaryTypeServiceImpl implements DiaryTypeService {
	
	@Autowired
	private DiaryTypeDao diaryTypeDao;
	
	@Override
	public List<DiaryType> queryAllDiaryTypes() {
		// TODO Auto-generated method stub
		return diaryTypeDao.queryAllDiaryTypes();
	}
}
