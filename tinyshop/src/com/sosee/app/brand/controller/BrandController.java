package com.sosee.app.brand.controller;

import java.util.Date;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Page;
import com.sosee.app.brand.pojo.Brand;
import com.sosee.app.brand.validator.BrandValidator;
import com.sosee.app.content.pojo.Contents;
import com.sosee.sys.base.controller.BaseController;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;

public class BrandController extends BaseController {

	private void init() {
		if (StringKit.notBlank(getPara("brandQuery"))) {
			this.setAttr("brandQuery", getPara("brandQuery"));
		}
	}

	public void index() {
		render("/WEB-INF/sys/brand/brandList.html");
	}

	public void query() {
		try {
			init();
			int pageIndex = this.getParaToInt("pageIndex") != null
					&& this.getParaToInt("pageIndex") != 0 ? this
					.getParaToInt("pageIndex") : 1;
			Page<Brand> brandPage = getModel(Brand.class).paginate(
					pageIndex,
					SysConstants.PAGE_NORMAL_SIZE,
					"select id,name,imgUrl,details",
					" from t_brand where 1=1 " + getParaStr()
							+ " order by createTime desc");

			if (StringKit.notNull(brandPage)) {
				if (brandPage.getList() != null
						&& brandPage.getList().size() > 0) {
					renderJson(brandPage);
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"没有数据!\"}");
				}
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"没有数据!\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.renderJson("{\"result\":0,\"msg\":\"查询失败!\"}");
		}
	}

	private String getParaStr() {

		String sqlParam = "";

		if (StringKit.notBlank(getPara("nameQuery"))) {
			sqlParam += " and name like '%" + getPara("nameQuery") + "%' ";
			this.setAttr("nameQuery", getPara("nameQuery"));
		}

		return sqlParam;
	}

	public void add() {
		render("/WEB-INF/sys/brand/brand.html");
	}
	@Before(BrandValidator.class)
	public void save() {
		try {
			this.keepPara();
			Brand brand = getModel(Brand.class);
			String imageFile = this.getAttrForStr("imgUrl");
			String name = this.getAttrForStr("name");
			String details = this.getAttrForStr("details");
			if (imageFile != null && !imageFile.equals("")) {
				brand.set("imgUrl", imageFile);
			}
			if (StringKit.notBlank(name)) {
				brand.set("name",name);
			}
			if (StringKit.notBlank(details)) {
				brand.set("details",details);
			}
			boolean bInfo = false;
			User user = this.getSessionAttr(SysConstants.USER);

			if (StringKit.notBlank(brand.getStr("id"))) {
				// contents.set("status", AppConstants.CONTANTS_STATUS_NONE);
				brand.set("modifyById", user.getStr("account"));
				brand.set("modifyTime", new Date());
				bInfo = brand.update();
			} else {
				brand.set("id", UUID.randomUUID().toString());
				brand.set("createById", user.getStr("account"));
				brand.set("createTime", new Date());
				bInfo = brand.save();
			}
			if (bInfo) {
				this.setAttr("msg", "数据保存成功!");
				index();
			} else {
				this.keepModel(Contents.class);
				this.setAttr("errorinfo", "数据保存失败!");
				render("/WEB-INF/sys/banrd/brand.html");
			}
		} catch (Exception e) {
			this.keepModel(Brand.class);
			this.setAttr("errorinfo", "数据保存失败!");
			render("/WEB-INF/sys/brand/brand.html");
		}
	}
	
	public void edit(){
		try{
			this.keepPara();
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
				Brand brand=this.getModel(Brand.class).findById(id);
				if(StringKit.notNull(brand)){
					this.setAttr("brand", brand);
					this.setAttr("imgUrl", brand.getStr("imgUrl"));
					render("/WEB-INF/sys/brand/brand.html");
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
	

	public void del() {
		try{
			Brand brand=getModel(Brand.class);
			if(StringKit.notBlank(getPara())){
				boolean bInfo= brand.deleteById(getPara());
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

	public void upload() {
	}

}
