package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.ActivityDTO;
import com.imei.app.dto.RecommendItemDTO;
import com.imei.app.entity.Activity;
import com.imei.app.entity.Item;
import com.imei.app.entity.RecommendItem;
import com.imei.app.service.ActivityService;
import com.imei.app.service.ItemService;
import com.imei.app.service.RecommendItemService;
import com.imei.app.util.DateUtil;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityService activityService;
	@Autowired
	private RecommendItemService recommendItemService;
	@Autowired
	private ItemService itemService;
	@RequestMapping(value ="/getHomepageActivity", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getHomepageActivity() {
        List<Activity> list = activityService.getHomepageActivity();
        if (list == null || list.size() == 0) {
            return new Result(0,"无活动信息");
        }
        List<ActivityDTO> datas = new ArrayList<ActivityDTO>();
        for(Activity activity : list) {
        	ActivityDTO dto = new ActivityDTO(activity.getId(),activity.getName(),activity.getDescription(),activity.getCreateTime(),activity.getIsRecommendToHomepage(),activity.getPicUrl(),activity.getJumpUrl(),activity.getBeginTime(),activity.getEndTime(),activity.getCity());
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
	}
	
	
	@RequestMapping(value ="/getRecomendActivity", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getRecomendActivity(@RequestParam("city")String city,@RequestParam("index")int index,@RequestParam("count")int count) {
        List<Activity> list = activityService.getRecommendActivity(city);
        if (list == null || list.size() == 0) {
            return new Result(0,"无活动信息");
        }
        List<ActivityDTO> datas = new ArrayList<ActivityDTO>();
        for(Activity activity : list) {
        	if (DateUtil.isNowAvailable(activity.getBeginTime(), activity.getEndTime())) {
        		ActivityDTO dto = new ActivityDTO(activity.getId(),activity.getName(),activity.getDescription(),activity.getCreateTime(),activity.getIsRecommendToHomepage(),activity.getPicUrl(),activity.getJumpUrl(),activity.getBeginTime(),activity.getEndTime(),activity.getCity());
            	List<RecommendItem> itemList = recommendItemService.queryListByActivityid(activity.getId(), index, count);
            	List<RecommendItemDTO> dtoList = new ArrayList<RecommendItemDTO>();
            	for(RecommendItem item : itemList) {
            		RecommendItemDTO recommendItemDTO = new RecommendItemDTO(item.getId(), item.getItemId(), item.getRecommend(), item.getCreateTime(), item.getBeginTime(), item.getEndTime(), item.getActivityId());
            		Item itemEntity = itemService.queryById(item.getItemId());
            		if (itemEntity!=null) {
						recommendItemDTO.setName(itemEntity.getName());
						recommendItemDTO.setDiscount(itemEntity.getDiscountPrice());
						recommendItemDTO.setOrigPrice(itemEntity.getOrigPrice());
						recommendItemDTO.setPicUrl(itemEntity.getCover());
					}
            		if (DateUtil.isNowAvailable(recommendItemDTO.getBeginTime(), recommendItemDTO.getEndTime())) {
    					dtoList.add(recommendItemDTO);
    				}
            	}
            	dto.setItems(dtoList);
            	datas.add(dto);
			}
        	
        	
        }
        return new Result(0,"success",datas);
	}
}
