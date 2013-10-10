package com.sosee.app.contentCategory.pojo;

import com.jfinal.plugin.activerecord.Model;
/**
 * @author  :outworld
 * @date    :2012-11-28 上午10:27:42
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:栏目对象
 * String id;			    //标识 UUID
 * String name;			    //栏目名称
 * String code;				//栏目编码
 * String shortName;		//简称
 * String url;				//url
 * String imageFile;		//图标文件
 * String parentId;			//上级栏目
 * String comments;			//备注
 * boolean isParent;//是否父结点
 */
@SuppressWarnings("serial")
public class ContentCategory extends Model<ContentCategory> {
}
