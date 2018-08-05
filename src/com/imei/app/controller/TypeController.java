package com.imei.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.imei.app.dto.TypeDTO;
import com.imei.app.entity.Item;
import com.imei.app.entity.Type;
import com.imei.app.service.TypeService;
import com.imei.app.util.Result;

@Controller
@RequestMapping("/type")
public class TypeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping(value ="/getTopRecommendItem", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getTopRecommendItem() {
        List<Type> list = typeService.queryTopRecommendTypeList();
        if (list == null || list.size() == 0) {
            return new Result(0,"���������");
        }
        List<TypeDTO> datas = new ArrayList<TypeDTO>();
        for(Type type : list) {
        	TypeDTO dto = new TypeDTO(type.getId(),type.getPicUrl(),type.getName(),type.getSortOrder());
        	dto.setParentId(0);
        	dto.setParentParentId(0);
        	dto.setChildItem(null);
        	datas.add(dto);
        }
        return new Result(0,"success",datas);
	}
	
	@RequestMapping(value ="/getAllType", method = RequestMethod.GET, produces = {
    "application/json; charset=utf-8" })
	@ResponseBody
	private Result getAllType() {
		//��ʵ�� ��Ϊ����ʵ�ʲ�����̫�ࡣ������˴˶δ���̫�꣬ʵ��Ӱ��Ҳ��Ƚ�С���������Ż�
		List<TypeDTO> firstDTOList = new ArrayList<TypeDTO>();
        List<Type> firstTypeList = typeService.queryTopTypeList();
        //��ѯ���˶������࣬���������������
        for(Type topType: firstTypeList) {
        	TypeDTO firtstDTO = new TypeDTO(topType.getId(), topType.getPicUrl(), topType.getName(), topType.getSortOrder());
        	firtstDTO.setParentId(0);
        	firtstDTO.setParentParentId(0);
        	//top_dto �Ͳ�childitem�� �����Ȳ���
        	List<Type> secondTypeList = typeService.queryChildTypeByTypeId(topType.getId());
        	List<TypeDTO> secondDtoList = new ArrayList<TypeDTO>();
        	//��ѯ���˶������� ����ͨ��������������
        	for(Type secondType : secondTypeList) {
        		TypeDTO secondDTO = new TypeDTO(secondType.getId(), secondType.getPicUrl(), secondType.getName(), secondType.getSortOrder());
        		secondDTO.setParentId(topType.getId());
        		secondDTO.setParentParentId(0);
        		
        		List<Type> thirdTypeList = typeService.queryChildTypeByTypeId(secondType.getId());
        		
        		List<TypeDTO> thirdDTOList = new ArrayList<TypeDTO>();
        		for(Type thirdType :thirdTypeList) {
        			TypeDTO thirdDTO = new TypeDTO(thirdType.getId(), thirdType.getPicUrl(), thirdType.getName(), thirdType.getSortOrder());
        			thirdDTO.setParentId(secondType.getId());
        			thirdDTO.setParentParentId(topType.getId());
        			thirdDTO.setChildItem(null);
        			thirdDTOList.add(thirdDTO);
        		}
        		secondDTO.setChildItem(thirdDTOList);
        		secondDtoList.add(secondDTO);
        	}
        	firtstDTO.setChildItem(secondDtoList);
        	firstDTOList.add(firtstDTO);
        }
        //�������з�����Ϣ���鵽��
        return new Result(0,"success",firstDTOList);
	}
	
}
