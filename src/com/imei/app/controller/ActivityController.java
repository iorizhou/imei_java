package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.ActivityDTO;
import com.imei.app.dto.TypeDTO;
import com.imei.app.entity.Activity;
import com.imei.app.entity.Type;
import com.imei.app.service.ActivityService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/activity")
public class ActivityController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value ="/getHomepageActivity", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getHomepageActivity() {
        List<Activity> list = activityService.getHomepageActivity();
        if (list == null || list.size() == 0) {
            return new Result(0,"无活动数据");
        }
        List<ActivityDTO> datas = new ArrayList<ActivityDTO>();
        for(Activity activity : list) {
        	ActivityDTO dto = new ActivityDTO(activity.getId(),activity.getName(),activity.getDescription(),activity.getCreateTime(),activity.getIsRecommendToHomepage(),activity.getPicUrl(),activity.getJumpUrl(),activity.getBeginTime(),activity.getEndTime());
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
	}
}
