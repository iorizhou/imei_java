package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.TypeDao;
import com.imei.app.entity.Type;
import com.imei.app.service.TypeService;
@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeDao typeDao;
	
	@Override
	public List<Type> queryTopRecommendTypeList() {
		
		return typeDao.queryTopRecommendTypeList();
	}

	@Override
	public List<Type> queryTopTypeList() {
		// TODO Auto-generated method stub
		return typeDao.queryTopTypeList();
	}

	@Override
	public List<Type> queryChildTypeByTypeId(long typeid) {
		// TODO Auto-generated method stub
		return typeDao.queryChildTypeByTypeId(typeid);
	}
	
	
	

}
