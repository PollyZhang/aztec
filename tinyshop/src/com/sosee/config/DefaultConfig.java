package com.sosee.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.i18n.I18N;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.FreeMarkerRender;
import com.sosee.app.brand.controller.BrandController;
import com.sosee.app.brand.pojo.Brand;
import com.sosee.app.content.controller.ContentsController;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.contentCategory.controller.ContentCategoryController;
import com.sosee.app.contentCategory.pojo.ContentCategory;
import com.sosee.app.itemCategory.controller.ItemCategoryController;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.app.items.controller.ItemsController;
import com.sosee.app.items.pojo.Items;
import com.sosee.app.member.controller.MemberController;
import com.sosee.app.member.controller.WebLoginController;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.ticket.controller.OrdersController;
import com.sosee.app.ticket.controller.QuestionsController;
import com.sosee.app.ticket.pojo.Questions;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.base.controller.BaseCodeController;
import com.sosee.sys.base.controller.BaseDictController;
import com.sosee.sys.base.controller.LoginController;
import com.sosee.sys.base.controller.MoudleController;
import com.sosee.sys.base.controller.RoleController;
import com.sosee.sys.base.controller.UserController;
import com.sosee.sys.base.interceptor.I18nInterceptor;
import com.sosee.sys.base.pojo.BaseCode;
import com.sosee.sys.base.pojo.BaseDict;
import com.sosee.sys.base.pojo.Moudle;
import com.sosee.sys.base.pojo.Role;
import com.sosee.sys.base.pojo.RoleMoudle;
import com.sosee.sys.base.pojo.User;
import com.sosee.sys.base.pojo.UserRole;
import com.sosee.sys.log.controller.LogController;
import com.sosee.sys.log.pojo.LogPojo;
import com.sosee.sys.util.SysConstants;
import com.sosee.web.controller.IndexController;

import freemarker.template.TemplateModelException;


/**
 * API引导式配置
 */
public class DefaultConfig extends JFinalConfig {
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("db_config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		I18N.init("i18n.message", null, null);
	}
	
	@Override
	public void afterJFinalStart() {
		//页面调用：${i18n.getText('key')}
		try {
			FreeMarkerRender.getConfiguration().setSharedVariable("i18n", I18N.me());
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/login", LoginController.class);
		me.add("/user", UserController.class);
		me.add("/baseCode", BaseCodeController.class);
		me.add("/baseDict", BaseDictController.class);
		me.add("/moudle", MoudleController.class);
		me.add("/role", RoleController.class);
		me.add("/log", LogController.class);
		me.add("/contentCategory", ContentCategoryController.class);
		me.add("/contents", ContentsController.class);
		me.add("/member", MemberController.class);
		me.add("/itemCategory", ItemCategoryController.class);
		me.add("/brand", BrandController.class);	
		me.add("/items", ItemsController.class);	
		//前台控制器
		me.add("/",IndexController.class);
		me.add("/webLogin",WebLoginController.class);

		
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("jdbcUser"), getProperty("jdbcPwd").trim(),getProperty("jdbcDriver").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.setDialect(new MysqlDialect());		
		
		arp.addMapping("t_user", User.class);	
		arp.addMapping("t_basecode", BaseCode.class);	
		arp.addMapping("t_basedict", BaseDict.class);	
		arp.addMapping("t_moudle", Moudle.class);	
		arp.addMapping("t_role", Role.class);	
		arp.addMapping("t_rolemoudle", RoleMoudle.class);
		arp.addMapping("t_userrole", UserRole.class);	
		arp.addMapping("t_loginfo", LogPojo.class);	
		arp.addMapping("t_contentcategory", ContentCategory.class);	
		arp.addMapping("t_contents", Contents.class);	
		arp.addMapping("t_member", Member.class);
		arp.addMapping("t_itemcategory", ItemCategory.class);
		arp.addMapping("t_questions", Questions.class);
		arp.addMapping("t_brand", Brand.class);
		arp.addMapping("t_items", Items.class);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new I18nInterceptor());
	}
	
	
	/**
	 * 用于获取指定的字典列表
	 * @param categoryId
	 * @return
	 */
	private List<Record> getBaseCodeList(String categoryId){
		return Db.find("select * from t_basecode where categoryId='"+categoryId+"' order by orderNum");
	}
	
	/**
	 * 用于初始化常量
	 * @return
	 */
	private Map<String,String> initConstMap(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("title", getProperty("title"));
		map.put("logoLarge", getProperty("logoLarge"));
		map.put("logoSmall", getProperty("logoSmall"));
		map.put("logo", getProperty("logo"));
		map.put("webUrl", getProperty("webUrl"));
		map.put("registeruser", getProperty("registeruser"));
		map.put("imageFile_Size", getProperty("imageFile_Size"));
		map.put("videoFile_Size", getProperty("videoFile_Size"));
		map.put("attachFile_Size", getProperty("attachFile_Size"));
			
		//下面添加字典的json串
		map.put("userSexJson",JsonKit.toJson("{\"data\":"+JsonKit.listToJson(getBaseCodeList(SysConstants.BASECODE_USER_SEX),3)+"}"));
		map.put("statusJson",JsonKit.toJson("{\"data\":"+JsonKit.listToJson(getBaseCodeList(AppConstants.BASECODE_CONTENTS_STATUS),3)+"}"));
			
		return map;
	}
	
	
	private Map<String,List<Record>> initParamMap(){
		Map<String,List<Record>> paramMap=new HashMap<String,List<Record>>();
		
		//内容字典
		paramMap.put("contentTypeList", getBaseCodeList(AppConstants.BASECODE_CONTENTS_CONTENTTYPE_STRING));
		paramMap.put("contentStatusList", getBaseCodeList(AppConstants.BASECODE_CONTENTS_STATUS));
		paramMap.put("contentCategoryTypeList", getBaseCodeList(AppConstants.BASECODE_CONTENT_CATEGORY_TYPE));
		
		//用户字典
		paramMap.put("userSexList", this.getBaseCodeList(SysConstants.BASECODE_USER_SEX));
		paramMap.put("userStatusList", this.getBaseCodeList(SysConstants.BASECODE_USER_STATUS));	
	
		return paramMap;
	}
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx_path"));
		
		DefaultHandler handler = new DefaultHandler();
		
		handler.setInitMap(initConstMap());
		handler.setParamMap(initParamMap());
		
		me.add(handler);
	}
}
