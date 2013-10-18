package com.sosee.app.itemCategory.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.app.itemCategory.validator.ItemCategroyValidator;
import com.sosee.sys.base.controller.BaseController;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.DateUtil;

public class ItemCategoryController extends BaseController {

	
	private void init() {

		if (StringKit.notBlank(getPara("itemCategoryQuery"))) {
			this.setAttr("itemTypeQuery", getPara("itemTypeQuery"));
		}
	}

	public void index() {
		// this.createToken("contentsToken",1800);
		render("/WEB-INF/sys/itemCategory/itemCategoryList.html");
	}

	public void query() {
		try{
			String pId=this.getPara("pId");
			String strSql="";
			if(pId!=null && !pId.trim().equals("")){
				strSql=" and parentId='"+pId.trim().replace("'", "")+"'";
			}else{
				strSql=" and (parentId is null or parentId='') ";
			}
			List<ItemCategory> itemCategoryList=this.getModel(ItemCategory.class).find("select * from t_itemcategory where 1=1 "+strSql+" order by code asc");
			if(itemCategoryList!=null && itemCategoryList.size()>0){
				this.renderJson("{\"result\":1,\"itemCategoryList\":"+JsonKit.listToJson(itemCategoryList,3)+",\"msg\":\"\"}");
			}else{
				this.renderJson("{\"result\":1,\"itemCategoryList\":[],\"msg\":\"没有查询到数据\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}

	private String getParaStr() {

		String sqlParam = "";

		if (StringKit.notBlank(getPara("itemTypeQuery"))) {
			sqlParam += " and itemType='" + getPara("itemTypeQuery") + "' ";
			this.setAttr("itemTypeQuery", getPara("itemTypeQuery"));
		}

		if (StringKit.notBlank(getPara("itemId"))) {
			sqlParam += " and itemId='" + getPara("itemId") + "' ";
			this.setAttr("itemId", getPara("itemId"));
		}

		if (StringKit.notBlank(getPara("titleQuery"))) {
			sqlParam += " and title like '%"
					+ getPara("titleQuery").trim().replace("'", "") + "%' ";
			this.setAttr("titleQuery", getPara("titleQuery"));
		}

		if (StringKit.notBlank(getPara("contentTypeQuery"))) {
			sqlParam += " and contentType='" + getPara("contentTypeQuery")
					+ "' ";
			this.setAttr("contentTypeQuery", getPara("contentTypeQuery"));
		}

		if (StringKit.notBlank(getPara("statusQuery"))) {
			sqlParam += " and status='" + getPara("statusQuery") + "' ";
			this.setAttr("statusQuery", getPara("statusQuery"));
		}

		if (StringKit.notBlank(getPara("startNewsDateQuery"))) {
			sqlParam += " and newsDate>='" + getPara("startNewsDateQuery")
					+ "'";
			this.setAttr("startNewsDateQuery", getPara("startNewsDateQuery"));
		}
		if (StringKit.notBlank(getPara("endNewsDateQuery"))) {
			sqlParam += " and newsDate<='" + getPara("endNewsDateQuery") + "' ";
			this.setAttr("endNewsDateQuery", getPara("endNewsDateQuery"));
		}

		return sqlParam;
	}
	@Before(ItemCategroyValidator.class)
	public void save() {
		try {
			//this.keepPara();
			ItemCategory itemCategory = this.getModel(ItemCategory.class);
			
			itemCategory.set("id", this.getPara("id"));
			itemCategory.set("name", this.getPara("name"));
			itemCategory.set("code", this.getPara("code"));
			itemCategory.set("parentId", this.getPara("pId"));
			itemCategory.set("createById","1111");
			itemCategory.set("createTime", new Date());
		
			boolean bInfo = false;
			User user = this.getSessionAttr(SysConstants.USER);
			
			if(itemCategory.getStr("id")!=null && !itemCategory.getStr("id").equals("")){
				itemCategory.set("modifyById", user.getStr("id"));
				itemCategory.set("modifyTime", new Date());
				bInfo = itemCategory.update();
			} else {
				itemCategory.set("id", UUID.randomUUID().toString());
				if(itemCategory.getStr("parentId")!=null && !itemCategory.getStr("parentId").trim().equals("")){
					Db.update("update t_itemCategory set isParent=true where id='"+itemCategory.getStr("parentId").trim()+"'");
				}
				itemCategory.set("createById", user.getStr("id"));
				itemCategory.set("createTime", new Date());
				bInfo = itemCategory.save();
			}
			if (bInfo) {
				this.renderJson("{\"result\":1,\"msg\":\"保存成功!\"}");
			} else {
				this.keepModel(ItemCategory.class);
				this.setAttr("errorinfo", "数据保存失败!");
				render("/WEB-INF/sys/itemCategory/itemCategoryList.html");
			}
		} catch (Exception e) {
			this.keepModel(ItemCategory.class);
			this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
		}
	}

	public void add() {
		try {
			this.keepPara();
			this.createToken("contentsToken", 1800);
			String itemName = Db.queryStr("select name from t_items where id='"
					+ this.getAttrForStr("itemId") + "'");
			if (StringKit.notBlank(itemName)) {
				this.setAttr("itemName", itemName);
				if (StringKit.notBlank(getPara("itemTypeQuery"))) {
					this.setAttr("itemTypeQuery", getPara("itemTypeQuery"));
				}
				this.setAttr("newsDate", DateUtil.formatDate(new Date()));
				render("/WEB-INF/sys/content/contents.html");
			} else {
				index();
			}
		} catch (Exception e) {
			index();
		}
	}

	public void edit() {
		try {
			this.keepPara();
			this.createToken("contentsToken", 1800);
			String id = this.getPara("id");
			if (StringKit.notBlank(id)) {
				Contents contents = this.getModel(Contents.class).findById(id);
				if (StringKit.notNull(contents)) {
					this.setAttr("contents", contents);
					this.setAttr("imageFile", contents.getStr("imageFile"));
					this.setAttr("videoFile", contents.getStr("videoFile"));
					this.setAttr("attachFile", contents.getStr("attachFile"));
					render("/WEB-INF/sys/content/contents.html");
				} else {
					index();
				}
			} else {
				index();
			}
		} catch (Exception e) {
			index();
		}
	}

	public void del() {
		try {
			String id = this.getPara("id");
			ItemCategory itemCategory=getModel(ItemCategory.class).findById(id);
			
			if (StringKit.notBlank(id)) {
				Db.update("update t_itemcategory set isDeleted = 1 where parentId = '"+id+"'");
				Db.update("update t_itemcategory set isDeleted = 1 where id ='"+id+"'");
				
				if(itemCategory.getStr("parentId")!=null)
				{
					if(StringKit.notNull(itemCategory)){
						List<ItemCategory> contentCategoryList=getModel(ItemCategory.class).find("select * from t_itemcategory where isDeleted = 0 and  parentId='"+itemCategory.getStr("parentId")+"'");
						if(!StringKit.notNull(contentCategoryList) || contentCategoryList.size()<1){
							Db.update("update t_itemcategory set isParent=null where id='"+itemCategory.getStr("parentId")+"'");
						}
					}
				}
					this.renderJson("{\"result\":1,\"msg\":\"数据删除成功!\"}");
				
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"数据删除失败!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"数据删除失败\"}");
		}
		this.keepPara();
	}
	
	/*
	 * 获取栏目数json
	 */
	public void getItemCategoryJson(){
		try{
			String id=this.getPara("id");
			List<ItemCategory> itemCategoryList=null;
			if(StringKit.notBlank(id)){
				itemCategoryList=this.getModel(ItemCategory.class).find("select * from t_itemCategory where parentId='"+id+"' and isDeleted = 0 order by code asc");
			}else{
				itemCategoryList=this.getModel(ItemCategory.class).find("select * from t_itemCategory where parentId is null or parentId='' and isDeleted = 0 order by code asc");
			}
			if(itemCategoryList!=null && itemCategoryList.size()>0){
				String str="[";
				boolean b=false;
				for(ItemCategory itemCategory : itemCategoryList){
					if(b){
						str+=",";
					}
					str+="{\"id\":\""+itemCategory.getStr("id")+"\",\"name\":\""+itemCategory.getStr("name")+"\",\"pid\":\""+id+"\",\"isDeleted\":\""+itemCategory.getBoolean("isDeleted")+"\",\"isParent\":"+itemCategory.getBoolean("isParent")+"}";
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
