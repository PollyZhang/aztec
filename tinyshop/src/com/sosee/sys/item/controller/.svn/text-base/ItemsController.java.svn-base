package com.sosee.sys.item.controller;

import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.item.pojo.Items;
import com.sosee.sys.item.validator.ItemsValidator;
/**
 * 
 *作者：强乐
 *功能：栏目管理
 * 2012-12-7 上午9:47:25
 */
@Before(LoginInterceptor.class)
public class ItemsController extends Controller {

	/*
	 * 查询
	 */
	public void index(){
		this.render("/WEB-INF/sys/items/itemsList.html");
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
			List<Items> itemsList=this.getModel(Items.class).find("select * from t_items where 1=1 "+strSql+" order by code asc");
			if(itemsList!=null && itemsList.size()>0){
				this.renderJson("{\"result\":1,\"itemsList\":"+JsonKit.listToJson(itemsList,3)+",\"msg\":\"\"}");
			}else{
				this.renderJson("{\"result\":1,\"itemsList\":[],\"msg\":\"没有查询到数据\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	/*
	 * 保存（添加和编辑）
	 */
	@Before(ItemsValidator.class)
	public void save() {
		try{
			Items items=this.getModel(Items.class);
			items.set("id", this.getPara("id"));
			items.set("name", this.getPara("name"));
			items.set("code", this.getPara("code"));
			items.set("shortName", this.getPara("shortName"));
			items.set("url", this.getPara("url"));
			items.set("imageFile", this.getPara("imageFile"));
			items.set("comments", this.getPara("comments"));
			items.set("parentId", this.getPara("pId"));
			if(items.getStr("id")!=null && !items.getStr("id").equals("")){
				items.update();
			}else{
				items.set("id",UUID.randomUUID().toString());
				if(items.getStr("parentId")!=null && !items.getStr("parentId").trim().equals("")){
					Db.update("update t_items set isParent=true where id='"+items.getStr("parentId").trim()+"'");
				}
				items.save();
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
				Db.update("delete from t_items where parentId='"+id+"' ");
				Db.update("delete from t_contents where itemId='"+id+"' ");
				Items items=getModel(Items.class).findById(id);
				
				Db.deleteById("t_items", id);
				if(StringKit.notNull(items)){
					List<Items> itemsList=getModel(Items.class).find("select * from t_items where parentId='"+items.getStr("parentId")+"'");
					if(!StringKit.notNull(itemsList) || itemsList.size()<1){
						Db.update("update t_items set isParent=null where id='"+items.getStr("parentId")+"'");
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
	public void getItemsJson(){
		try{
			String id=this.getPara("id");
			List<Items> itmesList=null;
			if(StringKit.notBlank(id)){
				itmesList=this.getModel(Items.class).find("select * from t_items where parentId='"+id+"' order by code asc");
			}else{
				itmesList=this.getModel(Items.class).find("select * from t_items where parentId is null or parentId='' order by code asc");
			}
			if(itmesList!=null && itmesList.size()>0){
				String str="[";
				boolean b=false;
				for(Items items : itmesList){
					if(b){
						str+=",";
					}
					str+="{\"id\":\""+items.getStr("id")+"\",\"name\":\""+items.getStr("name")+"\",\"pid\":\""+id+"\",\"isParent\":"+items.getBoolean("isParent")+"}";
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
