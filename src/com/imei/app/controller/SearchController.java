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
import com.imei.app.entity.Item;
import com.imei.app.service.ItemService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/search")
public class SearchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value ="/{kw}", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result search(@PathVariable("kw") String kw,@RequestParam("city")String city,@RequestParam("type")int type,@RequestParam("index")int index,@RequestParam("count")int count) {
		System.out.println("search kw = "+kw);
		if (type != 0) {
			return new Result<ItemDTO>(0,"暂不支持此类查询");
		}
		
        List<Item> list = itemService.queryByName(kw, city, index, count);
        if (list == null || list.size() == 0) {
            return new Result<ItemDTO>(0,"无搜索结果");
        }
        List<ItemDTO> datas = new ArrayList<ItemDTO>();
        for (Item item : list) {
        	ItemDTO dto = new ItemDTO(item.getId(), item.getName(), item.getCover(), item.getDoctorName(), item.getDoctorId(), item.getJumpType(), item.getJumpUrl(), item.getSortOrder(), item.getDetailsUrl(), item.getHospitalId(), item.getTypeId(), item.getParentTypeId(), item.getParentParentTypeId(),item.getCity());
			datas.add(dto);
		}
        
        return new Result(0,"success",datas);
	}
}
