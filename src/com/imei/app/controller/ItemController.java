package com.imei.app.controller;

import java.util.ArrayList;
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
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/item")
public class ItemController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private HospitalService hospitalService;
	@RequestMapping(value ="/{id}/detail", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result<ItemDTO> detail(@PathVariable("id") Long id) {
        System.out.println("detail id = "+id);
        Item item = itemService.queryById(id);
        ItemDTO dto = new ItemDTO();
        
        if (item == null) {
            return new Result<ItemDTO>(0,"����Ŀ������");
        }
        
        dto.setId(item.getId());
        dto.setName(item.getName());
        return new Result<ItemDTO>(0,"success",dto);
    }
	
	
	@RequestMapping(value ="/getItemListByType", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result<List<ItemDTO>> getItemListByType(@RequestParam("typeid") Long typeid,@RequestParam("city")String city,@RequestParam("index")int index,@RequestParam("count")int count) {
        List<Item> list = itemService.getItemListByType(typeid, city, index, count);
        if (list==null || list.size()==0) {
        	return new Result<List<ItemDTO>>(0,"此分类下无项目");
		} 
        List<ItemDTO> datas = new ArrayList<ItemDTO>();
        for(Item item :list) {
        	ItemDTO dto = new ItemDTO(item.getId(), item.getName(), item.getCover(), item.getDoctorName(), item.getDoctorId(), item.getJumpType(), item.getJumpUrl(), item.getSortOrder(), item.getDetailsUrl(), item.getHospitalId(), item.getTypeId(), item.getParentTypeId(), item.getParentParentTypeId(),item.getCity());
        	datas.add(dto);
        }
        return new Result<List<ItemDTO>>(0,"success",datas);
    }
	
	@RequestMapping(value ="/detail", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result detail(@RequestParam("id")long id) {
        Item item = itemService.queryById(id);
        if (item == null) {
			return new Result(-1,"项目不存在");
		}
        ItemDTO dto = new ItemDTO(item.getId(), item.getName(), item.getCover(), item.getDoctorName(), item.getDoctorId(), item.getJumpType(), item.getJumpUrl(), item.getSortOrder(), item.getDetailsUrl(), item.getHospitalId(), item.getTypeId(), item.getParentTypeId(), item.getParentParentTypeId(), item.getCity());
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
//        if (item.getRelateItemid()!=null) {
//			String[] relateList = item.getRelateItemid().split(",");
//			if (relateList.length>0) {
//				for(String relateId:relateList) {
//					Item item 
//				}
//			}
//		}
        return new Result(0,"success",dto);
    }
}
