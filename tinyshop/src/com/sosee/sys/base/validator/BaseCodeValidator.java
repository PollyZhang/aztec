package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.BaseCode;
/**
 * 
 * @author  :outworld
 * @date    :2012-12-6 下午5:54:38
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:
 */
public class BaseCodeValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		
		if(c.getModel(BaseCode.class).getStr("id")!=null && !c.getModel(BaseCode.class).getStr("id").trim().equals("")){
			c.setAttr("type", "edit");
		}else{
			c.setAttr("type", "add");
		}
//		validateToken("baseCodeToken", "tokenMsg", "请勿重复提交!");
//		validateString("baseCode.name",1,30, "name", "分类名称不能为空,且必须在1-30位之间!");
//		validateString("baseCode.code",1,30, "code", "分类编码不能为空,且必须在1-30位之间!");

		validateString("baseCode.code",1,30, "errorMsg", "分类编码不能为空,且必须在1-30位之间!");
		validateString("baseCode.name",1,30, "errorMsg", "分类名称不能为空,且必须在1-30位之间!");
		validateToken("baseCodeToken", "errorMsg", "请勿重复提交!");
		
	}

	@Override
	protected void handleError(Controller c) {
		c.createToken("baseCodeToken",1800);
		c.keepModel(BaseCode.class);
		c.keepPara();		
		c.renderJson("{\"baseCodeToken\":\""+c.getAttr("baseCodeToken")+"\",\"errorinfo\":\""+c.getAttr("errorMsg")+"!\"}");
	}

}
