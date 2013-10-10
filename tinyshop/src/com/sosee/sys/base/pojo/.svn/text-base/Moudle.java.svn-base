package com.sosee.sys.base.pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author  :outworld
 * @date    :2012-11-28 上午10:47:46
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:系统功能模块对象
 * String 	id;			//标识 UUID
 * String	moudleName;	//模块名称
 * String	code;		//模块编码
 * int		grade;		//模块级别
 * String	parentId;	//上级模块标识
 * String   image;		//图片路径
 * String   url;		//链接地址
 * String   indexUrl;	//模块首页
 */
@SuppressWarnings("serial")
public class Moudle extends Model<Moudle> {
	public static final Moudle dao = new Moudle();
	public Moudle getParent(){
		return Moudle.dao.findById(get("parentId"));
	}
}
