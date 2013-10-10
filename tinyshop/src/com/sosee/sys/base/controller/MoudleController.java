package com.sosee.sys.base.controller;

import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.base.pojo.Moudle;
import com.sosee.sys.base.validator.MoudleValidator;

/**
 * 
 *作者：强乐
 *功能：模块管理
 * 2012-12-4 上午9:48:12
 */
@Before(LoginInterceptor.class)
public class MoudleController extends BaseController {
   
	/*
	 * 查询
	 */
	public void index(){
		this.createToken("moudleToken",1800);
		List<Moudle> list=getModel(Moudle.class).find("select * from t_moudle where parentId is null order by code asc");
		this.setAttr("parentList", list);
		this.setAttr("moudleList", getModel(Moudle.class).find("select * from t_moudle order by code asc"));
		this.render("/WEB-INF/sys/user/moudleList.html");
	}
	
	/*
	 * 保存（添加和编辑）
	 */
	@Before(MoudleValidator.class)
	public void save() {
		try{
			Moudle moudle=getModel(Moudle.class);
			boolean bInfo=false;
			if(moudle.getStr("id")!=null && !moudle.getStr("id").trim().equals("")){
				//编辑
				this.setAttr("type", "edit");
				bInfo=moudle.update();				
			}else{
				//添加
				this.setAttr("type", "add");
				moudle.set("id", UUID.randomUUID().toString());
				bInfo=moudle.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
			}else{
				this.setAttr("errorinfo", "数据保存失败!");
			}
		}catch (Exception e) {
			this.keepModel(Moudle.class);
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
				Moudle moudle=this.getModel(Moudle.class).findById(id);
				if(moudle!=null && !moudle.equals("")){
					this.renderJson("{\"result\":1,\"moudle\":"+moudle.toJson()+",\"msg\":\"\"}");
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
			Moudle moudle=getModel(Moudle.class);
			if(moudle.getStr("id")!=null && !moudle.getStr("id").trim().equals("")){
				Db.update("delete from t_rolemoudle where moudleId='"+moudle.getStr("id").trim()+"'");
				boolean bInfo=moudle.deleteById(moudle.getStr("id"));
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
}
