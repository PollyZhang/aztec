package com.sosee.sys.item.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
/**
 * 
 *作者：强乐
 *功能：栏目保存数据校验
 * 2012-12-7 下午4:17:19
 */
public class ItemsValidator extends Validator {

	protected void handleError(Controller c) {
		this.validateString("code", 1,10,"msg", "栏目名称不能为空，且长度为1-10个字符!");
		this.validateString("name", 3,15,"msg", "栏目名称不能为空，且长度为3-15个字符!");
	}

	protected void validate(Controller c) {
		String msg=c.getAttr("msg");
		c.renderJson("{\"result\":0,\"msg\":\""+msg+"\"}");
	}

}
