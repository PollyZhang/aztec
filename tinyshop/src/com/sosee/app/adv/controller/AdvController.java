package com.sosee.app.adv.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Page;
import com.sosee.app.adv.pojo.Adv;
import com.sosee.app.adv.validator.AdvValidator;
import com.sosee.app.brand.pojo.Brand;
import com.sosee.app.content.pojo.Contents;
import com.sosee.sys.base.controller.BaseController;
import com.sosee.sys.base.pojo.BaseCode;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.util.SysConstants;

public class AdvController extends BaseController {

	public void index()
	{
		List<BaseCode> bcList = getModel(BaseCode.class).find("select id,name,code from t_baseCode "
				+ "where categoryId = 'adv_category'");
		setAttr("advCategoryList", bcList);
		render("/WEB-INF/sys/adv/advList.html");
	}
	@Before(AdvValidator.class)
	public void save()
	{
		try {
			this.keepPara();
			Adv adv = getModel(Adv.class);
			String imageFile = this.getAttrForStr("imgUrl");
			if (imageFile != null && !imageFile.equals("")) {
				adv.set("imgUrl", imageFile);
			}
			boolean bInfo = false;
			User user = this.getSessionAttr(SysConstants.USER);
			adv.set("categoryId","fd21c2e2-235b-11e3-8d92-68782241e1a2");
			adv.set("categoryName","成功案例");
			if (StringKit.notBlank(adv.getStr("id"))) {
				bInfo = adv.update();
			} else {
				adv.set("id", UUID.randomUUID().toString());
				adv.set("createTime", new Date());
				bInfo = adv.save();
			}
			
			if (bInfo) {
				this.setAttr("msg", "数据保存成功!");
				index();
			} else {
				this.keepModel(Contents.class);
				this.setAttr("errorinfo", "数据保存失败!");
				render("/WEB-INF/sys/adv/adv.html");
			}
		} catch (Exception e) {
			this.keepModel(Adv.class);
			this.setAttr("errorinfo", "数据保存失败!");
			render("/WEB-INF/sys/adv/adv.html");
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
	
	public void query()
	{
		try {
			int pageIndex = this.getParaToInt("pageIndex") != null
					&& this.getParaToInt("pageIndex") != 0 ? this
					.getParaToInt("pageIndex") : 1;
			Page<Adv> advPage = getModel(Adv.class).paginate(
					pageIndex,
					SysConstants.PAGE_NORMAL_SIZE,
					"select c.id,c.name,c.imgUrl,c.code,c.order,c.descrip,c.categoryId,c.categoryName,c.clickCount",
					" from t_adv as c where 1=1 " + getParaStr()
							+ " order by createTime desc");

			if (StringKit.notNull(advPage)) {
				if (advPage.getList() != null
						&& advPage.getList().size() > 0) {
					renderJson(advPage);
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
	public void add() {
		render("/WEB-INF/sys/adv/adv.html");
	}
	
	
	public void edit(){
		try{
			this.keepPara();
			String id=this.getPara("id");
			if(StringKit.notBlank(id)){
				Adv adv=this.getModel(Adv.class).findById(id);
				if(StringKit.notNull(adv)){
					this.setAttr("adv", adv);
					this.setAttr("imgUrl", adv.getStr("imgUrl"));
					render("/WEB-INF/sys/adv/adv.html");
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
	
	public void del()
	{
		try{
			Adv adv=getModel(Adv.class);
			if(StringKit.notBlank(getPara())){
				boolean bInfo= adv.deleteById(getPara());
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
