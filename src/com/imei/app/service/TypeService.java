package com.imei.app.service;

import java.util.List;

import com.imei.app.entity.Type;

public interface TypeService {
	
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
	List<Type> queryChildTypeByTypeId(long typeid);
}
