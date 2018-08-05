package com.imei.app.service;

import java.util.List;

import com.imei.app.entity.Item;

public interface ItemService {
	Item queryById(long id);
	List<Item> queryByName(String name,String city,int index,int count);
	List<Item> getItemListByType(long typeid,String city,int index,int count);
}
