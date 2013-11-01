package com.sosee.web.controller;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.content.pojo.Contents;

/**
 * 
 * @author  :outworld
 * @date    :2013-2-22 上午10:51:06
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:
 */

public class IndexController extends WebController{


	public void index(){
		//this.createToken("contentsToken",1800);
		render("/WEB-INF/web/index.html");
	}
	
	public void contentListView()
	{
		String contentCategoryId = getPara("contentCategoryId");
		List<Record> contentList = Db.find("select c.title,subTitle,"
				+ "c.author,c.contents,c.newsDate  from t_contents as c");
		this.setAttr("contentList",contentList);
		render("/WEB-INF/web/listContent.html");
	}
	
	public void contentView()
	{
		String contentId = getPara("contentId");
		Contents content = getModel(Contents.class).findById(contentId);
		this.setAttr("content", content);
		render("/WEB-INF/web/content.html");
	}
	
}
