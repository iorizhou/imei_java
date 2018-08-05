package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dao.BannerDao;
import com.imei.app.dto.BannerDTO;
import com.imei.app.dto.DiaryTypeDTO;
import com.imei.app.dto.ItemDTO;
import com.imei.app.entity.Banner;
import com.imei.app.entity.DiaryType;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/banner")
public class BannerController {
	@Autowired
	BannerDao bannerDao;
	
	@RequestMapping(value ="/getTopBanner", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result getAllDiaryTypes() {
        List<Banner> lists = bannerDao.getTopBanner();
        if (lists == null || lists.size() == 0) {
            return new Result<ItemDTO>(0,"ÔÝÎÞbannerÍÆ¼ö");
        }
        List<BannerDTO> datas = new ArrayList<BannerDTO>();
        for(Banner banner : lists) {
        	BannerDTO dto = new BannerDTO(banner.getId(), banner.getName(), banner.getDescription(), banner.getJumpType(), banner.getJumpUrl(), banner.getCreateTime(), banner.getRecommendTime(), banner.getIsRecommend(), banner.getPicUrl());
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
    }
}
