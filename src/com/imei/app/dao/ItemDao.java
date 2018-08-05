package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Item;

public interface ItemDao {
	
	/**
	 * Í¨¹ýid²éitem 
	 * @param id
	 * @return
	 */
	Item queryById(long id);
	
	
	
//	List<Item> queryAll(@Param("index")int index,@Param("count")int count);
//	
	List<Item> queryItemListByName(@Param("name")String name,@Param("city")String city,@Param("index")int index,@Param("count")int count);
	
	List<Item> getItemListByType(@Param("typeid")long typeid,@Param("city")String city,@Param("index")int index,@Param("count")int count);
	
}
