package com.imei.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.HospitalDao;
import com.imei.app.entity.Hospital;
import com.imei.app.service.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService {
	@Autowired
	HospitalDao hospitalDao;
	@Override
	public Hospital queryById(long id) {
		// TODO Auto-generated method stub
		return hospitalDao.queryById(id);
	}

}
