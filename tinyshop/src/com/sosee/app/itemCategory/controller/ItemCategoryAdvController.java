package com.sosee.app.itemCategory.controller;

import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.app.itemCategory.validator.ItemCategroyValidator;
import com.sosee.sys.base.controller.BaseController;

public class ItemCategoryAdvController extends BaseController {
	
	public void index() {
		// this.createToken("contentsToken",1800);
		render("/WEB-INF/sys/itemCategory/itemCategoryList.html");
	}
	
	
	public void edit() {
		try {
			this.keepPara();
			
			String id = this.getPara("id");
			if (StringKit.notBlank(id)) {
				ItemCategory itemCategory = this.getModel(ItemCategory.class).findById(id);
				if (StringKit.notNull(itemCategory)) {
					this.setAttr("itemCategory", itemCategory);
					render("/WEB-INF/sys/itemCategory/itemCategoryAdv.html");
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
	@Before(ItemCategroyValidator.class)
	public void save()
	{
		try {
			this.keepPara();
			Map id3 = this.getParaMap();
			ItemCategory itemCategory = this.getModel(ItemCategory.class);
			String advImgUrl1=this.getAttrForStr("advImgUrl1");
			String advImgUrl2=this.getAttrForStr("advImgUrl2");
			String advImgUrl3=this.getAttrForStr("advImgUrl3");
			String advImgUrl4=this.getAttrForStr("advImgUrl4");
			String advImgUrl5=this.getAttrForStr("advImgUrl5");
			String advImgUrl6=this.getAttrForStr("advImgUrl6");
			String advImgUrl7=this.getAttrForStr("advImgUrl7");
			if(advImgUrl1!=null)
			itemCategory.set("advImgUrl1",advImgUrl1);
			if(advImgUrl2!=null)
			itemCategory.set("advImgUrl2",advImgUrl2);
			if(advImgUrl3!=null)
			itemCategory.set("advImgUrl3",advImgUrl3);
			if(advImgUrl4!=null)
			itemCategory.set("advImgUrl4",advImgUrl4);
			if(advImgUrl5!=null)
			itemCategory.set("advImgUrl5",advImgUrl5);
			if(advImgUrl6!=null)
			itemCategory.set("advImgUrl6",advImgUrl6);
			if(advImgUrl7!=null)
			itemCategory.set("advImgUrl7",advImgUrl7);

			itemCategory.update();
			index();
		} catch (Exception e) {
			index();
		}
	
	}
}
