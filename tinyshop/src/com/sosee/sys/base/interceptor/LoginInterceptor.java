package com.sosee.sys.base.interceptor;

import java.util.List;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.sosee.sys.base.pojo.Moudle;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;

/**
 * 
 *作者：强乐
 *功能：过滤是否登陆
 * 2012-12-3 下午6:49:46
 */
public class LoginInterceptor implements Interceptor {

	@SuppressWarnings({"unchecked"})
	public void intercept(ActionInvocation ai) {
		User user=(User)ai.getController().getSessionAttr(SysConstants.USER);
		List<Moudle> moudleList=(List<Moudle>)ai.getController().getSessionAttr(SysConstants.MOUDLES);
		if(user!=null && !user.equals("")){
			boolean b=false;//默认没有访问权限
			//判断用户是否有访问权限
			if(moudleList!=null && moudleList.size()>0){
				String controllerKey=ai.getControllerKey();
				for(Moudle m : moudleList){
					if(m.getStr("url")!=null && m.getStr("url").matches("^"+controllerKey+".*")){
						b=true;
					}
				}
			}
			//admin管理员直接不做过滤
			if(user.getStr("id").equals(SysConstants.ADMIN_ID)){
				b=true;
			}
			//去除顶部菜单中个人信息和密码修改的过滤，前提是修改的id和登录人id一样
			String userid=user.getStr("id");//session内保存id
			String id=ai.getController().getPara();//查询id
			String sid=ai.getController().getPara("id");//保存id
			if(userid!=null && !userid.trim().equals("") && ((id!=null && !id.trim().equals("") && id.trim().equals(userid) && ai.getActionKey().equals("/user/edit"))
				|| (sid!=null && !sid.trim().equals("") && sid.trim().equals(userid) && ai.getActionKey().equals("/user/personSave")))){
				b=true;
			}
			if(b){
				ai.invoke();
			}else{
				ai.getController().render("/WEB-INF/sys/error/norole.html");
			}
		}else{
			ai.getController().forwardAction("/login/");
		}
	}
}
