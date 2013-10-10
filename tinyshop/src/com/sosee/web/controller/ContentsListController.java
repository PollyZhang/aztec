package com.sosee.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Page;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.contentCategory.pojo.ContentCategory;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.item.pojo.Items;
import com.sosee.sys.util.SysConstants;
import com.sosee.web.util.Constants;
/**
 * 作者:强乐
 * 功能:前台内容管理
 * 时间:2013-3-1 上午11:23:27
 */
public class ContentsListController extends WebController {
	
	/*
	 * 前台显示列表界面
	 */
	public void index(){
		
		String type=this.getPara("type");
		String first=this.getPara("first");
		int pageIndex=1;
		try{
			pageIndex=Integer.parseInt(this.getPara("pageIndex"));
		}catch(Exception e){}
		if(type!=null && !type.trim().equals("") && isExistType(type)){
			this.init();
			this.setAttr("type", type);
			ContentCategory contentCategory=this.getModel(ContentCategory.class).findFirst("select * from t_contentcategory where code='"+type+"'");
			this.setAttr("contentCategory", contentCategory);
			//判断主菜单是否直接显示第一条记录
			if(StringKit.isBlank(first)){
				Page<Contents> contentsPage= getModel(Contents.class).paginate((pageIndex>0?pageIndex:1), Constants.PAGE_SIZE, "select *", "from t_contents as c where c.contentCategoryId='"+contentCategory.getStr("id")+"' and c.status='"+AppConstants.CONTANTS_STATUS_OK+"' order by isTop desc,createTime desc");
				this.setAttr("contentsPage", contentsPage);
			}else{
				Contents contents=this.getModel(Contents.class).findFirst("select * from t_contents as c where c.itemId='"+contentCategory.getStr("id")+"' and c.status='"+AppConstants.CONTANTS_STATUS_OK+"' order by isTop desc,createTime desc");
				if(contents!=null){
					contents.set("browseSum", (contents.getInt("browseSum")!=null?contents.getInt("browseSum"):0)+1);
					contents.update();
					this.setAttr("contents", contents);
					this.setAttr("itemsName",contentCategory.get("name"));
				}else{
					//没有查到第一条内容跳转到首页
					this.forwardAction("/index");
					return;
				}
			}
			this.setAttr("pageIndex",(pageIndex>0?pageIndex:1));
			this.render("/WEB-INF/web/contents.html");
		}else{
			//没有传递类型或类型不在常量里的统一跳转到首页
			this.forwardAction("/index");
		}
	}
	
	public void look(){
		String id=this.getPara("id");
		int pageIndex=1;
		try{
			pageIndex=Integer.parseInt(this.getPara("pageIndex"));
		}catch(Exception e){}
		if(id!=null && !id.trim().equals("") && id.length()<=40){
			id=id.replace("'", "");
			Contents contents=this.getModel(Contents.class).findById(id);
			if(contents!=null){
				this.init();
				int browseSum=0;
				try{
					browseSum=contents.getInt("browseSum");
				}catch(Exception e){}
				contents.set("browseSum", browseSum+1);
				contents.update();
				this.setAttr("contents", contents);
				ContentCategory contentCategory=this.getModel(ContentCategory.class).findFirst("select * from t_contentcategory where id='"+contents.get("contentCategoryId")+"'");
				this.setAttr("type",contentCategory.get("code"));
				this.setAttr("itemsName",contentCategory.get("name"));
				this.setAttr("pageIndex",(pageIndex>0?pageIndex:1));
				
				isExistType(contentCategory.getStr("code"));
				
				this.render("/WEB-INF/web/contents.html");
				return;
			}
		}
		//传值异常跳转到主页
		this.forwardAction("/index");
	}
	
	/*
	 * 比对传递类型是否正确
	 */
	private boolean isExistType(String type){
		boolean b=false;
		if(Constants.CONTENTS_CODE.length>0){
			for(int i=0;i<Constants.CONTENTS_CODE.length;i++){
				String str=Constants.CONTENTS_CODE[i];
				if(str.equals(type)){
					b=true;
					this.setAttr("menuNum", i+1);
					break;
				}
			}
		}
		return b;
	}
}
