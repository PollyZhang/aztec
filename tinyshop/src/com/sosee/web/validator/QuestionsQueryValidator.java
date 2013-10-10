package com.sosee.web.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sosee.app.ticket.pojo.Questions;
/**
 * 
 * 作者:强乐
 * 功能:验证前台传递的token是否失效
 * 时间:2013-4-4 下午12:36:03
 */
public class QuestionsQueryValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateString("questions.title", 6, 200, "title", "标题不能为空,且必须在6-200位之间!");
		validateString("questions.askContent", 10, 300, "askContent", "问题内容不能为空,且必须在10-300位之间!");
		
	//	this.validateToken("questionsToken", "errorMsg", "请勿重复提交表单!");
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepPara();	
		c.keepModel(Questions.class);
		c.createToken("questionsToken",1800);
				
		c.renderJson("{\"result\":0,\"questionsToken\":\""+c.getAttr("questionsToken")+"\",\"msg\":\""+c.getAttr("errorMsg")+"!\"}");

	}

}
