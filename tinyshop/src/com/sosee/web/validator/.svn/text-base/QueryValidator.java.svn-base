package com.sosee.web.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
/**
 * 
 * 作者:强乐
 * 功能:验证前台传递的token是否失效
 * 时间:2013-4-4 下午12:36:03
 */
public class QueryValidator extends Validator {


	@Override
	protected void validate(Controller c) {
		this.validateToken("cancelToken", "error", "请勿重复提交表单!");
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepPara();
		c.forwardAction("/query/");
	}

}
