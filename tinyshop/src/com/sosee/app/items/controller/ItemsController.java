package com.sosee.app.items.controller;

import java.util.Date;
import java.util.UUID;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.app.items.pojo.Items;
import com.sosee.app.items.validator.ItemsValidator;
import com.sosee.sys.base.controller.BaseController;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.DateUtil;
public class ItemsController extends BaseController {

	public void init(){
		if(StringKit.notBlank(getPara("itemCategoryQuery"))){
			this.setAttr("itemCategoryQuery", getPara("itemCategoryQuery"));
		}

	}
	
	public void index(){
		//this.createToken("contentsToken",1800);
		List<Record> brandList = Db.find("select * from t_brand");
		this.setAttr("brandList", brandList);
		render("/WEB-INF/sys/items/itemsList.html");
		
	}
	
	public void query(){
		try{    
			init();
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			Page<Items> itemsPage= getModel(Items.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select c.id,c.itemName,b.name as itemCategory,c.itemPrice,c.count,c.itemPicUrl,c.salesVolume", "from t_items as c left JOIN t_itemCategory as b on c.itemCategoryId=b.id   where 1=1 "+getParaStr()+" order by c.createTime desc");
			
			if(StringKit.notNull(itemsPage)){
				if(itemsPage.getList()!=null && itemsPage.getList().size()>0){
					renderJson(itemsPage);
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"没有数据!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"没有数据!\"}");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.renderJson("{\"result\":0,\"msg\":\"查询失败!\"}");
		}
		
	}
	
	private String getParaStr(){
		
		String sqlParam="";
		
		if(StringKit.notBlank(getPara("itemCategoryId"))){
			sqlParam+=" and itemCategoryId='"+getPara("itemCategoryId")+"' ";
			this.setAttr("contentCategoryTypeQuery", getPara("contentCategoryTypeQuery"));
		}
		return sqlParam;
	}
	
	public void returnList(){
		this.keepPara();

		if(StringKit.notBlank(getPara("contentCategoryd1"))){
			this.setAttr("contentCategoryId", getPara("contentCategoryId1"));
		}
		
		if(StringKit.notBlank(getPara("contentCategoryTypeQuery1"))){
			this.setAttr("contentCategoryTypeQuery", getPara("contentCategoryTypeQuery1"));
		}
		if(StringKit.notBlank(getPara("contentCategoryName1"))){
			this.setAttr("contentCategoryName", getPara("contentCategoryName1"));
		}
		
		if(StringKit.notBlank(getPara("titleQuery1"))){
			this.setAttr("titleQuery", getPara("titleQuery1"));
		}
		
		if(StringKit.notBlank(getPara("contentTypeQuery1"))){
			this.setAttr("contentTypeQuery", getPara("contentTypeQuery1"));
		}
		
		if(StringKit.notBlank(getPara("statusQuery1"))){
			this.setAttr("statusQuery", getPara("statusQuery1"));
		}
		
		if(StringKit.notBlank(getPara("startNewsDateQuery1"))){
			this.setAttr("startNewsDateQuery", getPara("startNewsDateQuery1"));
		}
		if(StringKit.notBlank(getPara("endNewsDateQuery1"))){
			this.setAttr("endNewsDateQuery", getPara("endNewsDateQuery1"));
		}
		
		
		if(StringKit.notBlank(getPara("pageIndex1"))){
			this.setAttr("pageIndex", getPara("pageIndex1"));
		}
		
		index();
	}
	
	@Before(ItemsValidator.class)
	public void save(){
		try{
			this.keepPara();
			Items items=getModel(Items.class);
			String picUrl=this.getAttrForStr("itemPicUrl");
			if(picUrl!=null && !picUrl.equals("")){
				items.set("itemPicUrl", picUrl);
			}
			items.set("itemCategoryId", this.getAttrForStr("itemCategoryId"));
			
			boolean bInfo=false;
			User user=this.getSessionAttr(SysConstants.USER);
			
			
			if(StringKit.notBlank(items.getStr("descrip"))){
				items.set("descrip", items.getStr("descrip").replaceAll("noBorderTable", ""));
			}
			
			if(StringKit.notBlank(items.getStr("id"))){
				//contents.set("status", AppConstants.CONTANTS_STATUS_NONE);
				items.set("modifyById", user.getStr("account"));
				items.set("modifyTime", new Date());
				bInfo=items.update();				
			}else{
				items.set("id", UUID.randomUUID().toString());
				items.set("createById", user.getStr("id"));
				items.set("createTime", new Date());
				bInfo=items.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
				index();
			}else{
				this.keepModel(Contents.class);
				this.setAttr("errorinfo", "数据保存失败!");
				render("/WEB-INF/sys/items/items.html");
			}
		}catch (Exception e) {
			this.keepModel(Contents.class);
			this.setAttr("errorinfo", "数据保存失败!");
			render("/WEB-INF/sys/items/items.html");
		}
	}
	

	
	public void add(){
		try{
			this.keepPara();
			List<Record> brandList = Db.find("select * from t_brand");
			this.setAttr("brandList", brandList);
			
			//this.createToken("contentsToken",1800);
			ItemCategory itemCategory=getModel(ItemCategory.class).findById(this.getAttrForStr("itemCategoryId"));
			if(StringKit.notNull(itemCategory)){
				this.setAttr("itemCategory", itemCategory);;
				if(StringKit.notBlank(getPara("itemCategoryQuery"))){
					this.setAttr("itemCategoryQuery", getPara("itemCategoryQuery"));
				}
				this.setAttr("newsDate", DateUtil.formatDate(new Date()));
				render("/WEB-INF/sys/items/items.html");
			}else{
				index();
			}
		}catch(Exception e){
			index();
		}
	}
	
	public void edit(){
		try{
			this.keepPara();
			//this.createToken("contentsToken",1800);
			List<Record> brandList = Db.find("select * from t_brand");
			this.setAttr("brandList", brandList);
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
				Items items=this.getModel(Items.class).findById(id);
				ItemCategory itemCategory=getModel(ItemCategory.class).findById(items.get("itemCategoryId"));
				if(StringKit.notNull(items)){
					this.setAttr("items", items);
					this.setAttr("itemCategory", itemCategory);;
					this.setAttr("imageFile", items.getStr("imageFile"));
					render("/WEB-INF/sys/items/items.html");
				}else{
					index();
				}
			}else{
				index();
			}
		}catch(Exception e){
			index();
		}
	}
	
	public void del(){
		try{
			Items items=getModel(Items.class);
			if(StringKit.notBlank(getPara())){
				boolean bInfo=items.deleteById(getPara());
				if(bInfo){
					this.renderJson("{\"result\":1,\"msg\":\"数据删除成功!\"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"数据删除失败!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"数据删除失败!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"数据删除失败\"}");
		}
		this.keepPara();
	}
}
