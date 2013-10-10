package com.sosee.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
/**
*@Author   : outworld
*@Date     : 2011-5-29 下午04:27:47
*@Copyright: 2011 outworld Studio Inc. All rights reserved.
*@Function : 数组转换为对象或对象集合
 */
public class BeanConvertUtils<T> {
	private final Class<T> clazz;
	
	public BeanConvertUtils(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 
	 * @param types
	 * @param objArray
	 * @return 直接转换为指定对象
	 */
	@SuppressWarnings("rawtypes")
	public Object convertObjectArrayToBean(Class[] types,Object[] objArray){
		Constructor<T> cons;
		try {
			cons = clazz.getConstructor(types);
			return BeanUtils.instantiateClass(cons, objArray);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 
	 * @param types
	 * @param objList
	 * @return 数组对象转换为集合对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> convertObjectCollectionToBean(Class[] types,List<Object> objList){
		List<T> newObjectList = new ArrayList<T>();
		if(objList!=null && objList.size()>0){
			for(Object obj:objList){
				Object ret= convertObjectArrayToBean(types,(Object[])obj);
				if(ret!=null){
					newObjectList.add((T)ret);
				}
			}
		}
	   return newObjectList;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}

}
