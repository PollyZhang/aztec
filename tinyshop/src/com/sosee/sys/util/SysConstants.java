package com.sosee.sys.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  :outworld
 * @date    :2012-11-28 上午11:08:20
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:系统管理部分常量类
 */
public final class SysConstants {
	public static final String USER = "user";//登录用户
	public static final String MOUDLES = "moudle";//登陆用户的权限模块
	public static final String ADMIN = "admin";//管理员账户名
	public static final String ADMIN_ID = "06996C4A-8C50-4BBA-B7B7-B3340B04DE0B";//管理员ID
	
	public static final String LOGIN_IP = "loginIP";//登录用户的IP
	public static final String LOGIN_NAME = "loginName";//登录用户名称
	public static final String LOGIN_CODE = "code";//登录用户验证码
		
	//用户类型
	public static final String USER_TYPE_1="adm";//超级管理员
	public static final String USER_TYPE_2="mana";//管理员
	public static final String USER_TYPE_3="auditor";//审核员
	public static final String USER_TYPE_4="emp";//普通用户
	
	public static final String USER_DEFAULT_PASSWORD="666666";//默认密码
	
	public static final String USER_STATUS_NORMAL="ST01";//正常
	public static final String USER_STATUS_DISABLE="ST02";//禁用

	public static final int PAGE_NORMAL_SIZE = 10;//页大小
	
	/**
	 * 得到用户类型列表
	 * @return
	 */
	public static List<String[]> getUserTypeList(){
		List<String[]> userTypeList = new ArrayList<String[]>();
		//userTypeList.add(new String[]{"系统管理员",SysConstants.USER_TYPE_1});
		userTypeList.add(new String[]{"管理员",SysConstants.USER_TYPE_2});
		userTypeList.add(new String[]{"审核员",SysConstants.USER_TYPE_3});
		userTypeList.add(new String[]{"普通用户",SysConstants.USER_TYPE_4});
		
		return userTypeList;
	}
	
	//-----------------系统管理部分字典标识-------------------------------
	public static final String BASECODE_USER_SEX="user_sex";//用户性别
	public static final String BASECODE_USER_STATUS="user_status";//用户状态

}
