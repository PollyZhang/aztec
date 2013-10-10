package com.sosee.app.member.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.member.validator.MemberValidator;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.StringUtils;

@Before(LoginInterceptor.class)
public class MemberController extends Controller {
	/*
	 * 会员列表
	 */
	public void index() {
		this.createToken("memberToken",1800);
		List<String[]> list=new ArrayList<String[]>();
		list.add(new String[]{"VIP",AppConstants.MEMBER_TYPE_VIP});
		list.add(new String[]{"普通会员",AppConstants.MEMBER_TYPE_NORMAL});
		this.setAttr("typeList", list);
		String sqlParam="";
		
		if(StringKit.notBlank(getPara("accountQuery"))){
			sqlParam+=" and account like '%"+getPara("accountQuery").trim().replace("'", "")+"%' ";
			this.setAttr("accountQuery", getPara("accountQuery"));
		}
		if(StringKit.notBlank(getPara("nameQuery"))){
			sqlParam+=" and name like '%"+getPara("nameQuery").trim().replace("'", "")+"%' ";
			this.setAttr("nameQuery", getPara("nameQuery"));
		}
		if(StringKit.notBlank(getPara("accountTypeQuery")) 
		&& (getPara("accountTypeQuery").equals(AppConstants.MEMBER_TYPE_VIP) || getPara("accountTypeQuery").equals(AppConstants.MEMBER_TYPE_NORMAL) )){
			sqlParam+=" and accountType='"+getPara("accountTypeQuery")+"' ";
			this.setAttr("accountTypeQuery", getPara("accountTypeQuery"));
		}
		if(StringKit.notBlank(getPara("statusQuery"))
		&& (getPara("statusQuery").equals("正常") || getPara("statusQuery").equals("禁用"))){
			sqlParam+=" and status='"+getPara("statusQuery")+"' ";
			this.setAttr("statusQuery", getPara("statusQuery"));
		}
		int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
		setAttr("memberPage", getModel(Member.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select *", "from t_member where 1=1 "+sqlParam+" order by createtime desc"));
		render("/WEB-INF/sys/member/memberList.html");
	}
	
	/*
	 * 判断会员名是否存在
	 */
	public void isExist(){
		try{
			String account=this.getPara();
			Member member=getModel(Member.class).findFirst("select id from t_member where account='"+account+"'");
			if(StringKit.notNull(member) && StringKit.notBlank(member.getStr("id"))){
				this.renderJson("{\"result\":0,\"msg\":\"会员账号已存在!\"}");
			}else{
				this.renderJson("{\"result\":1,\"msg\":\"会员账号可用!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"保存出错!\"}");
		}
	}
	
	/*
	 * 保存（添加和编辑）
	 */
	@Before(MemberValidator.class)
	public void save() {
		try{
			Member member=getModel(Member.class);
			boolean bInfo=false;
			
			member.set("id", UUID.randomUUID().toString());
			member.set("password", StringUtils.encodeBase64(member.getStr("password")));
			member.set("status","正常");
			member.set("age",0);
			member.set("address","");
			member.set("accountType",AppConstants.MEMBER_TYPE_NORMAL);
			member.set("loginSum",1);
			member.set("lastLoginTime",new Date());
			member.set("createTime",new Date());
			bInfo=member.save();	
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
				this.setSessionAttr(AppConstants.MEMBER, member);
				this.setSessionAttr(AppConstants.WEB_LOGIN_NAME,member.get("account") );
				this.setSessionAttr(AppConstants.WEB_LOGIN_IP, getIpAddr());
			}else{
				this.setAttr("errorinfo", "数据保存失败!");
			}
		}catch (Exception e) {
			this.keepModel(Member.class);
			this.setAttr("errorinfo", "数据保存失败!");
		}
		
		this.forwardAction("/index/");
	}
	
	/**
	 * 获取IP地址
	 */
	private String getIpAddr() {
		HttpServletRequest request=this.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getRemoteAddr();
		}
	       return ip;
	}
	/*
	 * 编辑
	 */
	public void edit() {
		try{
			String id=this.getPara();
			if(StringKit.notBlank(id)){
				Member member=this.getModel(Member.class).findById(id);
				if(StringKit.notNull(member)){
					this.renderJson("{\"result\":1,\"member\":"+member.toJson()+",\"msg\":\"\"}");
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
			Member member=getModel(Member.class);
			if(StringKit.notBlank(member.getStr("id"))){
				boolean bInfo=member.deleteById(member.getStr("id"));
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
			Member member=null;
			String id=this.getPara();
			if(StringKit.notBlank(id)){
				member=getModel(Member.class).findById(id);
				if(member.getStr("status").equals("正常")){
					member.set("status","禁用");
				}else if(member.getStr("status").equals("禁用")){
					member.set("status","正常");
				}
				boolean bInfo=member.update();
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
			String personIdCard=this.getPara("personIdCard");
			String telephone=this.getPara("personTelephone");
			String email=this.getPara("personEmail");
			String password1=this.getPara("password");
			String password2=this.getPara("newpassword");
			Member member=this.getSessionAttr(AppConstants.MEMBER);
			if(StringKit.notBlank(id) && StringKit.notBlank(member.getStr("id"))){
				if(StringKit.notBlank(name)){
					//保存个人信息
					Db.update("update t_member set name=?,sex=?,idCard=?,telephone=?,email=? where id='"+member.getStr("id")+"'",name,sex,personIdCard,telephone,email);
					this.renderJson("{\"result\":1,\"msg\":\"数据保存成功!\"}");
				}else if(StringKit.notBlank(id) && StringKit.notBlank(password1) && StringKit.notBlank(password2)){
					//修改密码
					if(StringUtils.encodeBase64(password1).equals(member.getStr("password"))){
						if(StringKit.notBlank(password2) && password2.length()>=3 && password2.length()<=30){
							Db.update("update t_member set password=? where id='"+member.getStr("id")+"'",StringUtils.encodeBase64(password2.trim().replace("'","")));
							this.renderJson("{\"result\":1,\"msg\":\"密码修改成功!\"}");
							//this.removeSessionAttr(AppConstants.MEMBER);
							//this.forwardAction("/index/");
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