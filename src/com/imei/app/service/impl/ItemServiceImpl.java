package com.imei.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imei.app.dao.ItemDao;
import com.imei.app.entity.Item;
import com.imei.app.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public Item queryById(long id) {
		// TODO Auto-generated method stub
		return itemDao.queryById(id);
	}

	@Override
	public List<Item> queryByName(String name,String city,int index,int count) {
		// TODO Auto-generated method stub
		return itemDao.queryItemListByName(name, city, index, count);
	}

	@Override
	public List<Item> getItemListByType(long typeid, String city, int index, int count) {
		// TODO Auto-generated method stub
		return itemDao.getItemListByType(typeid, city, index, count);
	}
	
	
	
}
