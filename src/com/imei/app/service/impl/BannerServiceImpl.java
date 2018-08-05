package com.imei.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.BannerDao;
import com.imei.app.entity.Banner;
import com.imei.app.service.BannerService;
@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	BannerDao bannerDao;

	@Override
	public List<Banner> getTopBanner() {
		// TODO Auto-generated method stub
		return bannerDao.getTopBanner();
	}
	
	
}
