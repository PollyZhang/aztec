package com.sosee.sys.base.pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author  :outworld
 * @date    :2012-11-28 上午9:13:26
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:用户对象
 * 	 String 		id;			        //标识 UUID
 * 	 String 		account;			//用户账号
 *	 String 		name;				//用户名称
 *	 String 		password;			//用户密码
 *	 String 		accountType;		//用户类型
 *	 String 		sex;				//性别
 *	 int    		age;				//年龄
 *	 String 		telephone;			//联系电话
 *	 String 		email;				//电子邮箱
 *	 String 		status;				//状态 ST01:正常,ST02:禁用
 *	 Boolean 	    isAdmin;			//是否是超级管理员组
 *	 int			loginSum;			//登录次数
 *	 Date		    lastLoginTime;		//最后登录时间
 *	 Date           createTime;			//创建时间
 */
@SuppressWarnings("serial")
public class User extends Model<User> {
   
}
