package com.sosee.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.contentCategory.pojo.ContentCategory;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.app.items.pojo.Items;
import com.sosee.sys.base.controller.BaseController;

/**
 * 
 * @author  :outworld
 * @date    :2013-2-22 上午10:51:06
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:
 */

public class IndexController extends BaseController{

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
	/**
	 * 跳转到商城首页
	 */
	public void shopView()
	{
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
		//新品上架
		List<Items> newItemsList = getModel(Items.class).find(""
				+ "select * from t_items order by createTime desc limit 6");
		this.setAttr("newItemsList", newItemsList);
		//各分类楼层 
		List<ItemCategory> categoryList = getModel(ItemCategory.class).find(""
				+ "select c.id,c.name,c.code from t_itemCategory as c where isDeleted =0");
		this.setAttr("categoryList", categoryList);
		List<List<Items>> allList = new ArrayList<>();
		for (ItemCategory itemCategory : categoryList) {
			List<Items> itemList = getModel(Items.class).find(""
					+ "select * from t_items where itemCategoryId = '"+itemCategory.getStr("id")+"' order by createTime limit 8");
			allList.add(itemList);
		}
		this.setAttr("allList", allList);
		render("/WEB-INF/web/shop.html");		
	}
	
	/**
	 * 跳转到商品列表首页
	 */
	public void itemListView()
	{
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
		String itemCategoryId = getPara("itemCategoryId");
		
		int pageIndex = this.getParaToInt("pageIndex") != null
				&& this.getParaToInt("pageIndex") != 0 ? this
				.getParaToInt("pageIndex") : 1;
		Page<Record> itemsPage = Db.paginate(
						pageIndex,
						20,
						"select c.id,c.itemName,c.itemPrice,c.itemPicUrl",
						"from t_items as c "
						+ " where 1=1 "
						+ "and c.itemCategoryId='"+itemCategoryId+"' "
								+ " order by c.createTime desc");

		this.setAttr("itemList", itemsPage.getList());
		this.setAttr("totalRow", itemsPage.getTotalRow());
		this.setAttr("pageNumber", itemsPage.getPageNumber());
		this.setAttr("totalPage", itemsPage.getTotalPage());
		//各分类楼层 
		List<ItemCategory> categoryList = getModel(ItemCategory.class).find(""
				+ "select * from t_itemCategory where isDeleted =0");
		this.setAttr("categoryList", categoryList);
	
		render("/WEB-INF/web/shopItemList.html");		
	}
	
	/**
	 * 跳转到商品列表首页
	 */
	public void itemView()
	{
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
		String itemId = getPara("itemId");
		
		Items item = getModel(Items.class).findById(itemId);
		this.setAttr("item", item);
		//各分类楼层 
		List<ItemCategory> categoryList = getModel(ItemCategory.class).find(""
				+ "select * from t_itemCategory where isDeleted =0");
		this.setAttr("categoryList", categoryList);
	
		render("/WEB-INF/web/shop_items.html");		
	}
}
