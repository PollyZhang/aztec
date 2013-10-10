package com.sosee.sys.base.controller;

import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.base.pojo.BaseCode;
import com.sosee.sys.base.pojo.BaseDict;
import com.sosee.sys.base.validator.BaseDictValidator;
import java.util.List;
/**
 * @author  :outworld
 * @date    :2012-12-6 下午5:39:51
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:
 */
@Before(LoginInterceptor.class)
public class BaseDictController extends BaseController {
   
	public void index(){
		this.createToken("baseDictToken",1800);
		setAttr("baseDictList", getModel(BaseDict.class).find("select * from t_basedict  order by categoryId desc"));
		render("/WEB-INF/sys/user/baseDictList.html");
	}
	
	public void isExist(){
		try{
			String categoryId=this.getPara();
			BaseDict baseDict=getModel(BaseDict.class).findFirst("select id from t_basedict where categoryId='"+categoryId+"'");
			if(baseDict!=null && baseDict.getStr("id")!=null && !baseDict.getStr("id").trim().equals("")){
				this.renderJson("{\"result\":0,\"msg\":\"此分类已存在!\"}");
			}else{
				this.renderJson("{\"result\":1,\"msg\":\"此分类可用!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	@Before(BaseDictValidator.class)
	public void save(){
		try{
			BaseDict baseDict=getModel(BaseDict.class);
			boolean bInfo=false;
			if(StringKit.notBlank(baseDict.getStr("id"))){
				//编辑
				this.setAttr("type", "edit");
				bInfo=baseDict.update();				
			}else{
				//添加
				this.setAttr("type", "add");
				baseDict.set("id", UUID.randomUUID().toString());
				bInfo=baseDict.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
			}else{
				this.setAttr("errorinfo", "数据保存失败!");
			}
		}catch (Exception e) {
			this.keepModel(BaseDict.class);
			this.setAttr("errorinfo", "数据保存失败!");
		}
		index();
	}
	
	public void edit(){
		try{
			String id=this.getPara();
			if(id!=null && id!=null){
				BaseDict baseDict=this.getModel(BaseDict.class).findById(id);
				if(baseDict!=null && !baseDict.equals("")){
					this.renderJson("{\"result\":1,\"baseDict\":"+baseDict.toJson()+",\"msg\":\"\"}");
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
	
	public void del(){
		try{
			BaseDict baseDict=getModel(BaseDict.class);
			if(StringKit.notBlank(baseDict.getStr("id"))){
				baseDict=baseDict.findById(baseDict.getStr("id"));
				List<BaseCode> baseCodes = getModel(BaseCode.class).find("select * from t_basecode where categoryId='"+baseDict.getStr("categoryId")+"'");
                if(StringKit.notNull(baseCodes) && baseCodes.size()>0){
                	this.setAttr("errorinfo", "字典已使用此分类，请先删除字典再删除分类!");
                }else{
                	boolean bInfo=baseDict.deleteById(baseDict.getStr("id"));
					if(bInfo){
						this.setAttr("msg", "数据删除成功!");
					}else{
						this.setAttr("errorinfo", "数据删除失败!");
					}
                }
			}else{
				this.setAttr("errorinfo", "数据删除失败!");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.setAttr("errorinfo", "数据删除失败!");
		}
		index();
	}

}
