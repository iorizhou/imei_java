package com.imei.app.service;

import java.util.List;

import com.imei.app.entity.Type;

public interface TypeService {
	
	/**
	 * 查询所有被推荐的顶级分类
	 * @return
	 */
	List<Type> queryTopRecommendTypeList();
	/**
	 *  查询所有顶级分类 即parent_id=0 parent_parent_id=0的
	 * @return
	 */
	List<Type> queryTopTypeList();
	
	/**
	 * 根据typeid查询它的儿子分类 即parent_id = typeid的列表
	 * @param typeid
	 * @return
	 */
	List<Type> queryChildTypeByTypeId(long typeid);
}
