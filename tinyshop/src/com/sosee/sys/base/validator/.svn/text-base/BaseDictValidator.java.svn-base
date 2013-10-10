package com.sosee.sys.base.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.sys.base.pojo.BaseDict;
/**
 * 
 * @author  :outworld
 * @date    :2012-12-6 下午5:54:38
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:
 */
public class BaseDictValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		if(c.getModel(BaseDict.class).getStr("id")!=null && !c.getModel(BaseDict.class).getStr("id").trim().equals("")){
			c.setAttr("type", "edit");
		}else{
			c.setAttr("type", "add");
		}
		validateToken("baseDictToken", "tokenMsg", "请勿重复提交!");
		validateString("baseDict.categoryId",1,50, "categoryId", "分类编码不能为空,且必须在1-50位之间!");
		validateString("baseDict.categoryName",1,25, "categoryName", "分类名称不能为空,且必须在1-25位之间!");
		validateString("baseDict.typeName",1,20, "typeName", "所属类别不能为空,且必须在1-20位之间!");
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(BaseDict.class);
		c.keepPara();
		c.forwardAction("/baseDict/");
	}

}
