package com.sosee.app.content.controller;

import java.util.Date;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.content.validator.ContentsValidator;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.base.controller.BaseController;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;
import com.sosee.util.DateUtil;
/**
 * @author  :outworld
 * @date    :2012-12-9 下午6:41:01
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:内容对象
 */
@Before(LoginInterceptor.class)
public class ContentsController extends BaseController {
	
	public void init(){
		if(StringKit.notBlank(getPara("contentCategoryTypeQuery"))){
			this.setAttr("contentCategoryTypeQuery", getPara("contentCategoryTypeQuery"));
		}
	}
	
	public void index(){
		//this.createToken("contentsToken",1800);
		render("/WEB-INF/sys/content/contentsList.html");
	}
	
	public void query(){
		try{    
			init();
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			Page<Contents> contentsPage= getModel(Contents.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select c.id,c.contentCategoryType,c.title,b.name as contentType,c.status,c.isTop,c.isShared,c.newsDate,c.browseSum,c.author,c.creator,c.createTime,c.checker,c.reviewTime,c.reviewReason,c.contentCategoryId", "from t_contents as c left JOIN t_basecode as b on c.contentType=b.code and b.categoryId='"+AppConstants.BASECODE_CONTENTS_CONTENTTYPE_STRING+"' where 1=1 "+getParaStr()+" order by createTime desc");
			
			if(StringKit.notNull(contentsPage)){
				if(contentsPage.getList()!=null && contentsPage.getList().size()>0){
					renderJson(contentsPage);
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
		
		if(StringKit.notBlank(getPara("contentCategoryTypeQuery"))){
			sqlParam+=" and contentCategoryType='"+getPara("contentCategoryTypeQuery")+"' ";
			this.setAttr("contentCategoryTypeQuery", getPara("contentCategoryTypeQuery"));
		}
		
		if(StringKit.notBlank(getPara("contentCategoryId"))){
			sqlParam+=" and contentCategoryId='"+getPara("contentCategoryId")+"' ";
			this.setAttr("contentCategoryId", getPara("contentCategoryId"));
		}
		
		if(StringKit.notBlank(getPara("titleQuery"))){
			sqlParam+=" and title like '%"+getPara("titleQuery").trim().replace("'", "")+"%' ";
			this.setAttr("titleQuery", getPara("titleQuery"));
		}
		
		if(StringKit.notBlank(getPara("contentTypeQuery"))){
			sqlParam+=" and contentType='"+getPara("contentTypeQuery")+"' ";
			this.setAttr("contentTypeQuery", getPara("contentTypeQuery"));
		}
		
		if(StringKit.notBlank(getPara("statusQuery"))){
			sqlParam+=" and status='"+getPara("statusQuery")+"' ";
			this.setAttr("statusQuery", getPara("statusQuery"));
		}
		
		if(StringKit.notBlank(getPara("startNewsDateQuery"))){
			sqlParam+=" and newsDate>='"+getPara("startNewsDateQuery")+"'";
			this.setAttr("startNewsDateQuery", getPara("startNewsDateQuery"));
		}
		if(StringKit.notBlank(getPara("endNewsDateQuery"))){
			sqlParam+=" and newsDate<='"+getPara("endNewsDateQuery")+"' ";
			this.setAttr("endNewsDateQuery", getPara("endNewsDateQuery"));
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
	
	@Before(ContentsValidator.class)
	public void save(){
		try{
			this.keepPara();
			Contents contents=getModel(Contents.class);
			String imageFile=this.getAttrForStr("imageFile");
			String videoFile=this.getAttrForStr("videoFile");
			String attachFile=this.getAttrForStr("attachFile");
			if(imageFile!=null && !imageFile.equals("")){
				contents.set("imageFile", imageFile);
			}
			if(videoFile!=null && !videoFile.equals("")){
				contents.set("videoFile", videoFile);							
			}
			if(attachFile!=null && !attachFile.equals("")){
				contents.set("attachFile", attachFile);
			}
			contents.set("contentCategoryId", this.getAttrForStr("contentCategoryId"));
			
			boolean bInfo=false;
			User user=this.getSessionAttr(SysConstants.USER);
			
			
			if(StringKit.notBlank(contents.getStr("contents"))){
				contents.set("contents", contents.getStr("contents").replaceAll("noBorderTable", ""));
			}
			
			if(StringKit.isBlank(contents.getStr("contentCategoryType"))){
				if(StringKit.isBlank(getAttrForStr("contentCategoryTypeQuery"))){
					//公共信息
					contents.set("contentCategoryType", AppConstants.BASECODE_CONTENTS_CATEGORY_TYPE_PUBLIC);
				}else{
					contents.set("contentCategoryType", getAttrForStr("contentCategoryTypeQuery"));
				}
			}
			if(StringKit.notBlank(contents.getStr("id"))){
				//contents.set("status", AppConstants.CONTANTS_STATUS_NONE);
				contents.set("modifier", user.getStr("account"));
				contents.set("modifyTime", new Date());
				bInfo=contents.update();				
			}else{
				contents.set("id", UUID.randomUUID().toString());
				contents.set("creator", user.getStr("account"));
				contents.set("status", AppConstants.CONTANTS_STATUS_DRAFT);
				contents.set("createTime", new Date());
				contents.set("browseSum", 0);
				bInfo=contents.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
				index();
			}else{
				this.keepModel(Contents.class);
				this.setAttr("errorinfo", "数据保存失败!");
				render("/WEB-INF/sys/content/contents.html");
			}
		}catch (Exception e) {
			this.keepModel(Contents.class);
			this.setAttr("errorinfo", "数据保存失败!");
			render("/WEB-INF/sys/content/contents.html");
		}
	}
	
	public void add(){
		try{
			this.keepPara();
			this.createToken("contentsToken",1800);
			String contentCategoryName=Db.queryStr("select name from t_contentcategory where id='"+this.getAttrForStr("contentCategoryId")+"'");
			if(StringKit.notBlank(contentCategoryName)){
				this.setAttr("contentCategoryName", contentCategoryName);
				if(StringKit.notBlank(getPara("contentCategoryTypeQuery"))){
					this.setAttr("contentCategoryTypeQuery", getPara("contentCategoryTypeQuery"));
				}
				this.setAttr("newsDate", DateUtil.formatDate(new Date()));
				render("/WEB-INF/sys/content/contents.html");
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
			this.createToken("contentsToken",1800);
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
				Contents contents=this.getModel(Contents.class).findById(id);
				if(StringKit.notNull(contents)){
					this.setAttr("contents", contents);
					this.setAttr("imageFile", contents.getStr("imageFile"));
					this.setAttr("videoFile", contents.getStr("videoFile"));
					this.setAttr("attachFile", contents.getStr("attachFile"));
					render("/WEB-INF/sys/content/contents.html");
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
			Contents contents=getModel(Contents.class);
			if(StringKit.notBlank(getPara())){
				boolean bInfo=contents.deleteById(getPara());
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
	
	public void verifySave(){
		try{
			String id=this.getPara("id");
			String reviewReason=this.getPara("reviewReason");
			String status=this.getPara("status");
			if(StringKit.notBlank(id)){
				Contents contents=this.getModel(Contents.class).findById(id);
				if(StringKit.notNull(contents)){
					contents.set("reviewReason", reviewReason);
					contents.set("status", status);
					User user=this.getSessionAttr(SysConstants.USER);
					contents.set("checker", user.getStr("name"));
					contents.set("reviewTime",new Date());
					boolean bInfo=contents.update();
					if(bInfo){
						this.renderJson("{\"result\":1,\"msg\":\"审核成功!\"}");
					}else{
						this.renderJson("{\"result\":0,\"msg\":\"审核失败!\"}");
					}
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"审核失败!\"}");
				}
				
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"审核失败!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"审核失败!\"}");
		}
	}
	
	public void verify(){
		try{
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
				Contents contents=this.getModel(Contents.class).findFirst("select c.id,c.title,b.name as contentType,c.subTitle,i.name as contentCategoryId,c.source,c.summary,c.contents,c.reviewReason,c.creator,c.createTime from t_contents as c INNER JOIN t_basecode as b INNER JOIN t_contentcategory as i where c.contentType=b.code and b.categoryId='"+AppConstants.BASECODE_CONTENTS_CONTENTTYPE_STRING+"' and c.contentCategoryId=i.id and c.id='"+id+"'");
				
				if(StringKit.notNull(contents)){
					renderJson(contents);
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
	
	
	public void goNext(){
		try{
			String id=this.getPara("id");
			String status=this.getPara("status");
			if(StringKit.notBlank(id)){
				boolean bInfo=false;
				Contents contents = this.getModel(Contents.class).findById(id);
				contents.set("status", status);
				bInfo=contents.update();
				if (bInfo) {
					//createStaffLog(staff);
					this.renderJson("{\"result\":1,\"msg\":\"操作成功!\"}");
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"操作失败!\"}");
				}
				
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
}
