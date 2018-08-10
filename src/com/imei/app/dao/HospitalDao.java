package com.imei.app.dao;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Hospital;

public interface HospitalDao {
	Hospital queryById(@Param("id")long id);
}
