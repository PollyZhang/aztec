package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.User;
/**
 * 
 *作者：强乐
 *功能：登陆过滤
 * 2012-12-5 上午10:00:45
 */
public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		this.validateString("code", 4, 4, "errorinfo", "验证码有误!");
		this.validateString("user.password", 6, 50, "errorinfo", "密码不能为空,且必须在6-30位之间!");
		this.validateString("user.account", 5, 30, "errorinfo", "账号不能为空,且必须在5-30位之间!");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(User.class);
		c.keepPara();
		c.forwardAction("/login/");
	}

}
