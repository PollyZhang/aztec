package com.sosee.web.controller;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.contentCategory.pojo.ContentCategory;

/**
 * 
 * @author  :outworld
 * @date    :2013-2-22 上午10:51:06
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:
 */

public class IndexController extends WebController{

	public void init()
	{
		index();
	}

	public void index(){
		try {
			List<Record> renmeixinwenList = Db.find("SELECT c.title,c.id,c.imageFile FROM t_contents as c"
					+ " where c.contentCategoryId = '509ffc01-2b79-4bfe-bc5a-675ebfe05208'");
			this.setAttr("renmeixinwenList", renmeixinwenList);
			List<Record> taokefengcaiList = Db.find("SELECT c.title,c.id,c.imageFile FROM t_contents as c"
					+ " where c.contentCategoryId = '7c24be68-0929-4b60-9f84-fab4ef6bc0c0'");
			this.setAttr("taokefengcaiList", taokefengcaiList);
			List<Record> qiyefengcaiList = Db.find("SELECT c.title,c.id,c.imageFile FROM t_contents as c"
					+ " where c.contentCategoryId = '39b1ccc3-e7b8-4042-8fda-148538fb7b57'");
			this.setAttr("qiyefengcaiList", qiyefengcaiList);
			List<Record> imageNewsList = Db.find("SELECT c.title,c.id,c.imageFile FROM t_contents as c"
					+ " where c.contentCategoryId = '2e51ba23-4757-46f3-bd95-aac08121b18c' order by c.order asc");
			this.setAttr("imageNewsList", imageNewsList);
			render("/WEB-INF/web/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void contentListView()
	{
		try {
			String contentCategoryId = getPara("contentCategoryId");
			ContentCategory contentCategory = getModel(ContentCategory.class).findById(contentCategoryId);
			List<Record> contentList = Db.find("select c.id,c.title,subTitle,"
					+ "c.author,c.contents,c.newsDate  "
					+ "from t_contents as c where c.contentCategoryId='"+contentCategoryId+"'");
			this.setAttr("contentList",contentList);
			this.setAttr("contentCategory",contentCategory);
			render("/WEB-INF/web/listContent.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void contentView()
	{
		try {
			String contentId = getPara("contentId");
			Contents content = getModel(Contents.class).findById(contentId);
			this.setAttr("content", content);
			render("/WEB-INF/web/content.html");	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void contactView()
	{
		render("/WEB-INF/web/index_contact.html");	
	}
	
}
