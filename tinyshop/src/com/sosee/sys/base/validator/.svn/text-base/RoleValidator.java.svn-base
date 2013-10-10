package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.Role;
/**
 * 
 *作者：强乐
 *功能：角色添加，编辑校验
 * 2012-12-4 上午11:23:22
 */
public class RoleValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		if(c.getModel(Role.class).getStr("id")!=null && !c.getModel(Role.class).getStr("id").trim().equals("")){
			c.setAttr("type", "edit");
		}else{
			c.setAttr("type", "add");
		}
		validateToken("roleToken", "tokenMsg", "请勿重复提交!");
		validateString("role.roleName",1,15, "roleNameMsg", "角色名称不能为空,且必须在1-15位之间!");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(Role.class);
		c.keepPara();
		c.forwardAction("/role/");
	}

}
