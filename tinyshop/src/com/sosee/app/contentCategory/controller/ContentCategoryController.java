package com.sosee.app.contentCategory.controller;

import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.app.contentCategory.pojo.ContentCategory;
import com.sosee.app.contentCategory.validator.ContentCategoryValidator;
import com.sosee.sys.base.interceptor.LoginInterceptor;
/**
 * 
 *作者：Polly
 *功能：内容管理栏目
 * 2012-12-7 上午9:47:25
 */
public class ContentCategoryController extends Controller {

	/*
	 * 查询
	 */
	public void index(){
		this.render("/WEB-INF/sys/contentCategory/contentCategoryList.html");
	}
	
	/*
	 * ajax查询
	 */
	public void query(){
		try{
			String pId=this.getPara("pId");
			String strSql="";
			if(pId!=null && !pId.trim().equals("")){
				strSql=" and parentId='"+pId.trim().replace("'", "")+"'";
			}else{
				strSql=" and (parentId is null or parentId='') ";
			}
			List<ContentCategory> contentCategoryList=this.getModel(ContentCategory.class).find("select * from t_contentcategory where 1=1 "+strSql+" order by code asc");
			if(contentCategoryList!=null && contentCategoryList.size()>0){
				this.renderJson("{\"result\":1,\"contentCategoryList\":"+JsonKit.listToJson(contentCategoryList,3)+",\"msg\":\"\"}");
			}else{
				this.renderJson("{\"result\":1,\"contentCategoryList\":[],\"msg\":\"没有查询到数据\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	/*
	 * 保存（添加和编辑）
	 */
	@Before(ContentCategoryValidator.class)
	public void save() {
		try{
			ContentCategory contentCategory=this.getModel(ContentCategory.class);
			contentCategory.set("id", this.getPara("id"));
			contentCategory.set("name", this.getPara("name"));
			contentCategory.set("code", this.getPara("code"));
			contentCategory.set("shortName", this.getPara("shortName"));
			contentCategory.set("url", this.getPara("url"));
			contentCategory.set("imageFile", this.getPara("imageFile"));
			contentCategory.set("comments", this.getPara("comments"));
			contentCategory.set("parentId", this.getPara("pId"));
			if(contentCategory.getStr("id")!=null && !contentCategory.getStr("id").equals("")){
				contentCategory.update();
			}else{
				contentCategory.set("id",UUID.randomUUID().toString());
				if(contentCategory.getStr("parentId")!=null && !contentCategory.getStr("parentId").trim().equals("")){
					Db.update("update t_contentcategory set isParent=true where id='"+contentCategory.getStr("parentId").trim()+"'");
				}
				contentCategory.save();
			}
			this.renderJson("{\"result\":1,\"msg\":\"保存成功!\"}");
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
		}
	}
	
	/*
	 * 删除
	 */
	public void del() {
		try{
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
		
				//---------------------------------------------------------------删除栏目之前，需要相关关联数据和子栏目----------------------------------------------------------------------
				Db.update("delete from t_contentcategory where parentId='"+id+"' ");
				Db.update("delete from t_contents where contentCategoryId='"+id+"' ");
				ContentCategory contentCategory=getModel(ContentCategory.class).findById(id);
				
				Db.deleteById("t_contentcategory", id);
				if(StringKit.notNull(contentCategory)){
					List<ContentCategory> contentCategoryList=getModel(ContentCategory.class).find("select * from t_contentcategory where parentId='"+contentCategory.getStr("parentId")+"'");
					if(!StringKit.notNull(contentCategoryList) || contentCategoryList.size()<1){
						Db.update("update t_contentcategory set isParent=null where id='"+contentCategory.getStr("parentId")+"'");
					}
				}
				this.renderJson("{\"result\":1,\"msg\":\"删除成功!\"}");
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"删除失败!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"删除失败!\"}");
		}
	}
	
	/*
	 * 获取栏目数json
	 */
	public void getContentCategoryJson(){
		try{
			String id=this.getPara("id");
			List<ContentCategory> contentCategoryList=null;
			if(StringKit.notBlank(id)){
				contentCategoryList=this.getModel(ContentCategory.class).find("select * from t_contentcategory where parentId='"+id+"' order by code asc");
			}else{
				contentCategoryList=this.getModel(ContentCategory.class).find("select * from t_contentcategory where parentId is null or parentId='' order by code asc");
			}
			if(contentCategoryList!=null && contentCategoryList.size()>0){
				String str="[";
				boolean b=false;
				for(ContentCategory contentCategory : contentCategoryList){
					if(b){
						str+=",";
					}
					str+="{\"id\":\""+contentCategory.getStr("id")+"\",\"name\":\""+contentCategory.getStr("name")+"\",\"pid\":\""+id+"\",\"isParent\":"+contentCategory.getBoolean("isParent")+"}";
					b=true;
				}
				str+="]";
				this.renderJson(str);
			}else{
				this.renderJson("[]");
			}
		}catch(Exception e){
			this.renderJson("[]");
		}		
	}
}
