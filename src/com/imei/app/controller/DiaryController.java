package com.imei.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imei.app.dto.DiaryDTO;
import com.imei.app.dto.DiaryTypeDTO;
import com.imei.app.dto.ItemDTO;
import com.imei.app.entity.Diary;
import com.imei.app.entity.DiaryType;
import com.imei.app.entity.Item;
import com.imei.app.service.DiaryService;
import com.imei.app.service.DiaryTypeService;
import com.imei.app.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/diary")
public class DiaryController {
	
	@Autowired
	DiaryTypeService diaryTypeService;
	@Autowired
	DiaryService diaryService; 
	@RequestMapping(value ="/getAllDiaryTypes", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result getAllDiaryTypes() {
        List<DiaryType> lists = diaryTypeService.queryAllDiaryTypes();
        if (lists == null || lists.size() == 0) {
            return new Result<ItemDTO>(0,"暂无美容日记分类信息");
        }
        List<DiaryTypeDTO> datas = new ArrayList<DiaryTypeDTO>();
        for(DiaryType type : lists) {
        	DiaryTypeDTO dto = new DiaryTypeDTO(type.getId(), type.getName(), type.getSortOrder());
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
    }
	
	@RequestMapping(value ="/getDiaryListByType", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
    private Result getDiaryListByType(@RequestParam("typeid")long typeid,@RequestParam("index")int index,@RequestParam("count")int count) {
		System.out.println("typeid= "+typeid +" index = "+index + " count = "+count);
        List<Diary> lists = diaryService.getDiaryListByType(typeid,index,count);
        if (lists == null || lists.size() == 0) {
            return new Result<ItemDTO>(0,"该分类下暂无美容日记");
        }
        List<DiaryDTO> datas = new ArrayList<DiaryDTO>();
        for(Diary diary : lists) {
        	DiaryDTO dto = new DiaryDTO(diary.getId(), diary.getAuthor(), diary.getPublishTime(), diary.getAuthorAvatar(), diary.getCoverImg(), diary.getSimpleContent(), diary.getTag(), diary.getItemId(), diary.getItemName(), diary.getItemDiscount(), diary.getViewCount(), diary.getCommentCount(), diary.getDiaryTypeId());
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
    }
	
}
