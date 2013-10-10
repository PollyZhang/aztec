package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;

public class UserValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		if(c.getModel(User.class).getStr("id")!=null && !c.getModel(User.class).getStr("id").trim().equals("")){
			c.setAttr("type", "edit");
		}else{
			c.setAttr("type", "add");
		}
		validateToken("userToken", "tokenMsg", "请勿重复提交!");		
		validateString("user.account", 5, 30, "accountMsg", "账号不能为空,且必须在5-30位之间!");
		//判断用户名是否重复
		try{
			User user=null;
			if(c.getAttr("type").equals("add")){
				user=c.getModel(User.class).findFirst("select id from t_user where account='"+c.getModel(User.class).getStr("account").replace("'", "")+"'");
			}else{
				user=c.getModel(User.class).findFirst("select id from t_user where account='"+c.getModel(User.class).getStr("account").replace("'", "")
						+"' and id!='"+c.getModel(User.class).getStr("id").replace("'", "")+"'");
			}
			if(user!=null && user.getStr("id")!=null && !user.getStr("id").trim().equals("")){
				this.addError("accountMsg", "用户账号已存在!");
			}
		}catch(Exception e){
			this.addError("accountMsg", "用户账号已存在!");
		}
		validateString("user.name", 3, 20, "nameMsg", "用户名称不能为空,且必须在3-20位之间!");
		validateRequiredString("user.accountType","accountTypeMsg", "请选择用户类型!");
		if(!c.getModel(User.class).getStr("accountType").equals(SysConstants.USER_TYPE_1) 
		  && !c.getModel(User.class).getStr("accountType").equals(SysConstants.USER_TYPE_2) 
		  && !c.getModel(User.class).getStr("accountType").equals(SysConstants.USER_TYPE_3)){
			this.addError("accountTypeMsg", "用户类型出错!");
		}
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(User.class);
		c.keepPara();
		c.forwardAction("/user/");
	}
}
