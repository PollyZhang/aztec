package com.sosee.app.member.interceptor;


import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.kit.StringKit;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.util.AppConstants;

/**
 * @author  :outworld
 * @date    :2013-3-1 上午10:37:25
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function: 过滤是否登陆
 */

public class WebLoginInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Member member=(Member)ai.getController().getSessionAttr(AppConstants.MEMBER);
		if(member!=null && !member.equals("")){
			if(StringKit.notNull(member)){
				
			}
			
//			//去除顶部菜单中个人信息和密码修改的过滤，前提是修改的id和登录人id一样
//			String userid=user.getStr("id");//session内保存id
//			String id=ai.getController().getPara();//查询id
//			String sid=ai.getController().getPara("id");//保存id
//			if(userid!=null && !userid.trim().equals("") && ((id!=null && !id.trim().equals("") && id.trim().equals(userid) && ai.getActionKey().equals("/user/edit"))
//				|| (sid!=null && !sid.trim().equals("") && sid.trim().equals(userid) && ai.getActionKey().equals("/user/personSave")))){
//				b=true;
//			}
//			if(b){
				ai.invoke();
//			}else{
//				ai.getController().render("/WEB-INF/sys/error/norole.html");
//			}
		}else{
			ai.getController().forwardAction("/index/");
		}
	}

}
