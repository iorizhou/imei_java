package com.imei.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imei.app.entity.Type;

public interface TypeDao {
	
	/**
	 * ��ѯ���б��Ƽ��Ķ�������
	 * @return
	 */
	List<Type> queryTopRecommendTypeList();
	
	/**
	 *  ��ѯ���ж������� ��parent_id=0 parent_parent_id=0��
	 * @return
	 */
	List<Type> queryTopTypeList();
	
	/**
	 * ����typeid��ѯ���Ķ��ӷ��� ��parent_id = typeid���б�
	 * @param typeid
	 * @return
	 */
	List<Type> queryChildTypeByTypeId(@Param("typeid")long typeid);
}
