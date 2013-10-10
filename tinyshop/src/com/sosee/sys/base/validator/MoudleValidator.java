package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.Moudle;

/**
 * 
 *作者：强乐
 *功能： 模块数据校验
 * 2012-12-4 上午11:21:20
 */
public class MoudleValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		if(c.getModel(Moudle.class).getStr("id")!=null && !c.getModel(Moudle.class).getStr("id").trim().equals("")){
			c.setAttr("type", "edit");
		}else{
			c.setAttr("type", "add");
		}
		validateToken("moudleToken", "tokenMsg", "请勿重复提交!");
		validateString("moudle.moudleName", 1, 30, "moudleNameMsg", "模块名称不能为空,且必须在1-30位之间!");
		validateString("moudle.code", 1, 30, "codeMsg", "模块编码不能为空,且必须在1-30位之间!");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(Moudle.class);
		c.keepPara();
		c.forwardAction("/moudle/");
	}

}
