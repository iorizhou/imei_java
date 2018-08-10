package com.imei.app.service;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Hospital;

public interface HospitalService {
	Hospital queryById(long id);
}
