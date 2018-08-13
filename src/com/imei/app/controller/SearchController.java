package com.imei.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.ItemDTO;
import com.imei.app.entity.Hospital;
import com.imei.app.entity.Item;
import com.imei.app.service.HospitalService;
import com.imei.app.service.ItemService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/search")
public class SearchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItemService itemService;
	@Autowired
	HospitalService hospitalService;
	@RequestMapping(value ="/{kw}", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result search(@PathVariable("kw") String kw,@RequestParam("city")String city,@RequestParam("type")int type,@RequestParam("index")int index,@RequestParam("count")int count) {
		System.out.println("search kw = "+kw);
		if (type != 0) {
			return new Result<ItemDTO>(-1,"暂不支持此类搜索");
		}
		
        List<Item> list = itemService.queryByName(kw, city, index, count);
        if (list == null || list.size() == 0) {
            return new Result<ItemDTO>(-1,"未搜索到项目结果");
        }
        List<ItemDTO> datas = new ArrayList<ItemDTO>();
        for (Item item : list) {
        	ItemDTO dto = new ItemDTO();
        	dto.setCity(item.getCity());
        	dto.setCover(item.getCover());
        	dto.setDetailsUrl(item.getDetailsUrl());
        	dto.setDjCount(item.getDjCount());
        	dto.setDoctorId(item.getDoctorId());
        	dto.setDoctorName(item.getDoctorName());
        	if (dto.getHospitalId()!=0) {
    			Hospital hospital = hospitalService.queryById(dto.getHospitalId());
    			if (hospital!=null) {
    				dto.setHospitalAddr(hospital.getAddr());
    				dto.setHospitalCover(hospital.getAvatar());
    				dto.setHospitalGps(hospital.getGps());
    				dto.setHospitalName(hospital.getName());
    				dto.setHospitalWebsite(hospital.getWebsite());
    			}
    		}
        	dto.setHospitalId(item.getHospitalId());
        	dto.setId(item.getId());
        	dto.setJumpType(item.getJumpType());
        	dto.setJumpUrl(item.getJumpUrl());
        	dto.setName(item.getName());
        	dto.setParentParentTypeId(item.getParentParentTypeId());
        	dto.setParentTypeId(item.getParentTypeId());
//        	dto.setRelateDatas(item.getre);
        	dto.setSortOrder(item.getSortOrder());
        	dto.setTypeId(item.getTypeId());
			datas.add(dto);
		}
        
        return new Result(0,"success",datas);
	}
}
