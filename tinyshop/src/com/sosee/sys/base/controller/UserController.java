package com.sosee.sys.base.controller;

import java.util.Date;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.base.validator.UserValidator;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.StringUtils;

/**
 * 
 *作者：强乐
 *功能：用户管理
 * 2012-12-4 上午10:24:51
 */
@Before(LoginInterceptor.class)
public class UserController extends BaseController {
   
	/*
	 * 用户列表
	 */
	public void index() {
		this.createToken("userToken",1800);
		this.setAttr("userTypeList", SysConstants.getUserTypeList());
				
		int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
		setAttr("userPage", getModel(User.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select *", "from t_user where account!='admin' "+getParaString()+" order by createtime desc"));
		render("/WEB-INF/sys/user/userList.html");
	}
	
	
	/**
	 * 查询参数串
	 * @return
	 */
	private String getParaString(){
		String sqlParam="";
		if(StringKit.notBlank(getPara("accountQuery"))){
			sqlParam+=" and account like '%"+getPara("accountQuery").trim().replace("'", "")+"%' ";
			this.setAttr("accountQuery", getPara("accountQuery"));
		}
		if(StringKit.notBlank(getPara("nameQuery"))){
			sqlParam+=" and name like '%"+getPara("nameQuery").trim().replace("'", "")+"%' ";
			this.setAttr("nameQuery", getPara("nameQuery"));
		}
		if(StringKit.notBlank(getPara("accountTypeQuery"))){
			sqlParam+=" and accountType='"+getPara("accountTypeQuery")+"' ";
			this.setAttr("accountTypeQuery", getPara("accountTypeQuery"));
		}
		if(StringKit.notBlank(getPara("statusQuery"))){
			sqlParam+=" and status='"+getPara("statusQuery")+"' ";
			this.setAttr("statusQuery", getPara("statusQuery"));
		}
		return sqlParam;
	}
	
	/*
	 * 判断用户名是否存在
	 */
	public void isExist(){
		try{
			String account=this.getPara();
			User user=getModel(User.class).findFirst("select id from t_user where account='"+account+"'");
			if(user!=null && user.getStr("id")!=null && !user.getStr("id").trim().equals("")){
				this.renderJson("{\"result\":0,\"msg\":\"用户账号已存在!\"}");
			}else{
				this.renderJson("{\"result\":1,\"msg\":\"用户账号可用!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/*
	 * 保存（添加和编辑）
	 */
	@Before(UserValidator.class)
	public void save() {
		try{
			User user=getModel(User.class);
			boolean bInfo=false;
			if(user.getStr("id")!=null && !user.getStr("id").trim().equals("")){
				//编辑
				this.setAttr("type", "edit");
				bInfo=user.update();				
			}else{
				//添加
				this.setAttr("type", "add");
				user.set("id", UUID.randomUUID().toString());
				user.set("password", StringUtils.encodeBase64(SysConstants.USER_DEFAULT_PASSWORD));
				user.set("status",SysConstants.USER_STATUS_NORMAL);
			    user.set("loginSum",0);
				user.set("createTime",new Date());
				bInfo=user.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
			}else{
				this.setAttr("errorinfo", "数据保存失败!");
			}
		}catch (Exception e) {
			this.keepModel(User.class);
			this.setAttr("errorinfo", "数据保存失败!");
		}
		index();
	}
	
	/*
	 * 编辑
	 */
	public void edit() {
		try{
			String id=this.getPara();
			if(id!=null && id!=null){
				User user=this.getModel(User.class).findById(id);
				if(user!=null && !user.equals("")){
					this.renderJson("{\"result\":1,\"user\":"+user.toJson()+",\"msg\":\"\"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/*
	 * 查看
	 */
	public void look() {
		try{
			String id=this.getPara();
			if(StringKit.notBlank(id)){
				
				User user=this.getModel(User.class).findFirst("select a.id,a.account,a.name,a.accountType,b.name as sex,c.name as status,a.age,a.telephone,a.email,a.loginSum,a.lastLoginTime,a.createTime from t_user a left join t_basecode b on a.sex=b.code and b.categoryId='"+SysConstants.BASECODE_USER_SEX+"' left join t_basecode c on a.status=c.code and c.categoryId='"+SysConstants.BASECODE_USER_STATUS+"'");
				if(StringKit.notNull(user)){
					this.renderJson("{\"result\":1,\"user\":"+user.toJson()+",\"msg\":\"\"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	/*
	 * 删除
	 */
	public void del() {
		try{
			User user=getModel(User.class);
			if(user.getStr("id")!=null && !user.getStr("id").trim().equals("")){
				Db.update("delete from t_userrole where userId='"+user.getStr("id").trim()+"'");
				boolean bInfo=user.deleteById(user.getStr("id"));
				if(bInfo){
					this.setAttr("msg", "数据删除成功!");
				}else{
					this.setAttr("errorinfo", "数据删除失败!");
				}
			}else{
				this.setAttr("errorinfo", "数据删除失败!");
			}
		}catch(Exception e){
			this.setAttr("errorinfo", "数据删除失败!");
		}
		index();
	}
	
	/*
	 * 状态修改
	 */
	public void changeStatuts() {
		try{
			User user=null;
			String id=this.getPara();
			if(id!=null && !id.equals("")){
				user=getModel(User.class).findById(id);
				if(user.getStr("status").equals(SysConstants.USER_STATUS_NORMAL)){
					user.set("status",SysConstants.USER_STATUS_DISABLE);
				}else if(user.getStr("status").equals(SysConstants.USER_STATUS_DISABLE)){
					user.set("status",SysConstants.USER_STATUS_NORMAL);
				}
				boolean bInfo=user.update();
				if(bInfo){
					this.setAttr("msg", "状态修改成功!");
				}else{
					this.setAttr("errorinfo", "状态修改失败!");
				}
			}else{
				this.setAttr("errorinfo", "状态修改失败!");
			}
		}catch(Exception e){
			this.setAttr("errorinfo", "状态修改失败!");
		}
		index();
	}
	
	/*
	 * 首页“个人信息”和修改密码保存
	 */
	public void personSave(){
		try{
			String id=this.getPara("id");
			String name=this.getPara("personName");
			String sex=this.getPara("personSex");
			String age=this.getPara("personAge");
			String telephone=this.getPara("personTelephone");
			String email=this.getPara("personEmail");
			String password1=this.getPara("password");
			String password2=this.getPara("newpassword");
			User user=this.getSessionAttr(SysConstants.USER);
			if(id!=null && !id.equals("") && user.getStr("id").equals(id)){
				if(name!=null && !name.trim().equals("")){
					//保存个人信息
					Db.update("update t_user set name=?,sex=?,age=?,telephone=?,email=? where id='"+user.getStr("id")+"'",name,sex,age,telephone,email);
					this.renderJson("{\"result\":1,\"msg\":\"数据保存成功!\"}");
				}else if(id!=null && !id.equals("") && password1!=null && !password1.trim().equals("") && password2!=null && !password2.trim().equals("")){
					//修改密码
					if(StringUtils.encodeBase64(password1).equals(user.getStr("password"))){
						if(password2!=null && !password2.trim().equals("") && password2.length()>=3 && password2.length()<=30){
							Db.update("update t_user set password=? where id='"+user.getStr("id")+"'",StringUtils.encodeBase64(password2.trim().replace("'","")));
							this.renderJson("{\"result\":1,\"msg\":\"密码修改成功!\"}");
						}else{
							this.renderJson("{\"result\":1,\"msg\":\"新密码为空或长度不在6-30之内!\"}");
						}
					}else{
						this.renderJson("{\"result\":0,\"msg\":\"原始密码不正确!\"}");
					}
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"数据保存失败!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"数据保存失败!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"数据保存失败!\"}");
		}
	}

}
