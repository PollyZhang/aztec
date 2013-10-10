package com.sosee.app.member.validator;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.validate.Validator;
import com.sosee.app.member.pojo.Member;

public class MemberValidator extends Validator {


	@Override
	protected void validate(Controller c) {
	//	validateToken("memberToken", "tokenMsg", "请勿重复提交!");		
		validateString("member.account", 5, 30, "accountMsg", "账号不能为空,且必须在5-30位之间!");
		//判断会员名是否重复
		try{
			Member member=null;
			member=c.getModel(Member.class).findFirst("select id from t_member where account='"+c.getModel(Member.class).getStr("account").replace("'", "")+"'");
			if(StringKit.notNull(member) && StringKit.notBlank(member.getStr("id"))){
				this.addError("accountMsg", "会员账号已存在!");
			}
		}catch(Exception e){
			this.addError("accountMsg", "会员注册失败!");
		}
		validateString("member.name", 3, 20, "nameMsg", "会员姓名不能为空,且必须在3-20位之间!");
		validateString("member.idCard", 15, 18, "nameMsg", "会员姓名不能为空,且必须在15-18位之间!");
		validateString("member.telephone", 11, 11, "nameMsg", "会员姓名不能为空,且必须为11位!");
		validateString("member.password", 3, 30, "nameMsg", "会员姓名不能为空,且必须在3-20位之间!");
	}
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(Member.class);
		c.keepPara();
		c.forwardAction("/index/");
	}

}
