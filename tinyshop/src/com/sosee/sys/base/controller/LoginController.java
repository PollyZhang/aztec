package com.sosee.sys.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.sosee.sys.base.pojo.BaseCode;
import com.sosee.sys.base.pojo.Moudle;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.base.validator.LoginValidator;
import com.sosee.sys.log.util.LoggerUtil;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.StringUtils;
import com.sosee.util.UtilTools;

public class LoginController extends BaseController {
   
	/**
	 * 登陆跳转
	 */
	public void index(){
		
		this.setAttr("userSexList", getModel(BaseCode.class).find("select * from t_basecode where categoryId='"+SysConstants.BASECODE_USER_SEX+"' order by orderNum"));

		//判断是否已经登陆了
		User userSession=this.getSessionAttr(SysConstants.USER);
		if(StringKit.notNull(userSession)){
			this.render("/WEB-INF/sys/main.html");
		}else{
			this.render("/WEB-INF/sys/login.html");
		}
	}
		
	/**
	 * 登陆
	 */
	@Before(LoginValidator.class)
	public void login(){
		try{

			this.setAttr("userSexList", getModel(BaseCode.class).find("select * from t_basecode where categoryId='"+SysConstants.BASECODE_USER_SEX+"' order by orderNum"));

			User user=this.getModel(User.class);
			String account=user.getStr("account");
			String password=user.getStr("password");
			String code=this.getPara("code");
			String systemCode=this.getSessionAttr(SysConstants.LOGIN_CODE);
			boolean bLogin=false;//是否登陆成功
					
			//判断是否已经登陆了
			User userSession=this.getSessionAttr(SysConstants.USER);
			if(userSession!=null){
					bLogin=true;
			}else{
				if(StringKit.notBlank(account) && StringKit.notBlank(password)){
					if(StringKit.notBlank(code) && StringKit.notBlank(systemCode) && code.length()==4 && code.equals(systemCode)){
						User userDB=this.getModel(User.class).findFirst("select * from t_user where account='"+account.replace("'", "'").replace("\"", "\"").replace(" or ", " or ")+"'");
						if(StringKit.notNull(userDB) && StringKit.notBlank(userDB.getStr("account"))){
							if(!userDB.getStr("account").equals(SysConstants.ADMIN)){
								//普通人员初始化
								if(userDB.getStr("status")!=null && userDB.getStr("status").equals(SysConstants.USER_STATUS_NORMAL)){
									if(StringUtils.encodeBase64(password).equals(userDB.getStr("password"))){
										bLogin=true;
										//记录用户
										this.setSessionAttr(SysConstants.USER, userDB);
										//初始化权限信息
										String strSql="select * from t_moudle where id in ";
											   strSql+="(select mr.moudleId from t_rolemoudle as mr where mr.roleId in ";
											   strSql+="(select r.id from t_role as r where r.id in ";
											   strSql+="(select ur.roleId from t_userrole as ur where ur.userId='"+userDB.getStr("id")+"')))";
										List<Moudle> parentMoudleList=this.getModel(Moudle.class).find(strSql+" and (parentId is null or parentId='') order by code asc");
										List<Moudle> moudleList=this.getModel(Moudle.class).find(strSql+" order by code asc");
										Map<String,List<Moudle>> moudleMap=new HashMap<String,List<Moudle>>();
										//过滤掉没有选择父模块的子模块
										if(moudleList!=null && moudleList.size()>0){
											for(int i=0;i<moudleList.size();i++){
												Moudle m=moudleList.get(i);
												//boolean b=true;
												for(Moudle pm : parentMoudleList){
													if(m.getStr("parentId")!=null &&  m.getStr("parentId").equals(pm.getStr("id"))){
														List<Moudle> ml=moudleMap.get(pm.getStr("id"));
														if(ml!=null && ml.size()>0){
															moudleMap.remove(pm.getStr("id"));
														}else{
															ml=new ArrayList<Moudle>();
														}
														ml.add(m);
														moudleMap.put(pm.getStr("id"), ml);
														break;
													}
												}
											}
											//设置用户权限模块
											this.setSessionAttr(SysConstants.MOUDLES, parentMoudleList);
											this.setSessionAttr("moudleMap", moudleMap);
										}
										userDB.set("loginSum", userDB.getInt("loginSum")+1);
										userDB.set("lastLoginTime", new Date());
										userDB.update();
									}else{
										this.setAttr("errorinfo", "用户名或密码有误!");
									}
								}else{
									this.setAttr("errorinfo", "用户状态为禁用，请与管理员联系!");
								}
							}else{
								//管理员初始化
								if(StringUtils.encodeBase64(password).equals(userDB.getStr("password"))){
									bLogin=true;
									//记录用户
									this.setSessionAttr(SysConstants.USER, userDB);
									//初始化权限信息
									String strSql="select * from t_moudle where 1=1 ";
									List<Moudle> parentMoudleList=this.getModel(Moudle.class).find(strSql+" and (parentId is null or parentId='') order by code asc");
									List<Moudle> moudleList=this.getModel(Moudle.class).find(strSql+" order by code asc");
									Map<String,List<Moudle>> moudleMap=new HashMap<String,List<Moudle>>();
									//过滤掉没有选择父模块的子模块
									if(moudleList!=null && moudleList.size()>0){
										for(int i=0;i<moudleList.size();i++){
											Moudle m=moudleList.get(i);
											for(Moudle pm : parentMoudleList){
												if(m.getStr("parentId")!=null &&  m.getStr("parentId").equals(pm.getStr("id"))){
													List<Moudle> ml=moudleMap.get(pm.getStr("id"));
													if(ml!=null && ml.size()>0){
														moudleMap.remove(pm.getStr("id"));
													}else{
														ml=new ArrayList<Moudle>();
													}
													ml.add(m);
													moudleMap.put(pm.getStr("id"), ml);
													break;
												}
											}
										}
										//设置用户权限模块
										this.setSessionAttr(SysConstants.MOUDLES, parentMoudleList);
										this.setSessionAttr("moudleMap", moudleMap);
									}
									userDB.set("loginSum", userDB.getInt("loginSum")+1);
									userDB.set("lastLoginTime", new Date());
									userDB.update();
								}else{
									this.setAttr("errorinfo", "用户名或密码有误!");
								}
							}
						}else{
							this.setAttr("errorinfo", "用户名或密码有误!");
						}
					}else{
						this.setAttr("errorinfo", "验证码有误!");
					}
				}else{
					this.setAttr("errorinfo", "用户名和密码不能为空!");
				}
			}
			if(bLogin){
				this.setSessionAttr(SysConstants.LOGIN_NAME,account );
				this.setSessionAttr(SysConstants.LOGIN_IP, UtilTools.getIpAddr(this.getRequest()));
				LoggerUtil.security.info("用户登录_成功_用户验证登录");
				this.render("/WEB-INF/sys/main.html");
			}else{
				this.setAttr("user", user);
				this.removeSessionAttr(SysConstants.LOGIN_CODE);
				index();
			}
		}catch(Exception e){
			this.setAttr("errorinfo", "登陆异常!");
			this.removeSessionAttr(SysConstants.LOGIN_CODE);
			index();
		}
	}
	
	/**
	 * 退出
	 */
	public void logout(){
		User user=this.getSessionAttr(SysConstants.USER);
		if(user!=null){
			this.removeSessionAttr(SysConstants.USER);
		}
		List<Moudle> moudleList=this.getSessionAttr(SysConstants.MOUDLES);
		if(moudleList!=null){
			this.removeSessionAttr(SysConstants.MOUDLES);
		}
		index();
	}
	
	/**
	 * 生成验证码
	 */
	public void generateCode(){
		UtilTools.generateCode(this.getSession(), SysConstants.LOGIN_CODE, this.getResponse());
	}

}
