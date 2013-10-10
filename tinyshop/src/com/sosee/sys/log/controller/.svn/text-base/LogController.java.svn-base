package com.sosee.sys.log.controller;


import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.log.pojo.LogPojo;
import com.sosee.sys.log.util.LoggerUtil;
import com.sosee.sys.util.SysConstants;

@Before(LoginInterceptor.class)
public class LogController extends Controller {
	/**
	 * 登陆跳转
	 */
	public void index(){
		this.createToken("logToken",1800);
	    setAttr("typeMap", LoggerUtil.getLoggerTypeMap());
	    
		int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
		setAttr("logPage", getModel(LogPojo.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select * ", "from t_loginfo where 1=1 "+getParaStr()+" order by occurtTime desc"));
		this.render("/WEB-INF/sys/user/logList.html");

	}
	
	
	/**
	 * 删除选定的记录
	 */
	public void del(){
		this.createToken("roleToken",1800);
		LogPojo logPojo= getModel(LogPojo.class);
		String[] model=this.getParaValues("logId");
		try{
			if(model!=null && model.length>0){
				for(String m:model){
					logPojo.deleteById(m);
				}
			}
			this.setAttr("msg", "数据删除成功!");
		}catch(Exception e){
			this.setAttr("errorinfo", "数据删除失败!");
		}
		this.keepPara();
		index();
	}
	
	/**
	 * 删除指定条件的全部记录
	 */
	public void delAll(){
		this.createToken("roleToken",1800);
		try{
			Db.update("delete from t_loginfo where 1=1 " + getParaStr());
			this.setAttr("msg", "数据删除成功!");
		}catch(Exception e){
			this.setAttr("errorinfo", "数据删除失败!");
		}
		this.keepPara();
		index();
	}
	
	/**
	 * 
	 * @return
	 */
	private String getParaStr(){
		String sqlParam="";
		if(StringKit.notBlank(getPara("accountQuery"))){
			sqlParam+=" and operator like '%"+getPara("accountQuery").trim().replace("'", "")+"%' ";
			this.setAttr("accountQuery", getPara("accountQuery"));
		}
		
		if(StringKit.notBlank(getPara("typeQuery"))){
			sqlParam+=" and type='"+getPara("typeQuery")+"' ";
			this.setAttr("typeQuery", getPara("typeQuery"));
		}
		
		if(StringKit.notBlank(getPara("startOccurtTimeQuery"))){
			sqlParam+=" and occurtTime>='"+getPara("startOccurtTimeQuery")+" 00:00:00' ";
			this.setAttr("startOccurtTimeQuery", getPara("startOccurtTimeQuery"));
		}
		if(StringKit.notBlank(getPara("endOccurtTimeQuery"))){
			sqlParam+=" and occurtTime<='"+getPara("endOccurtTimeQuery")+" 23:59:59' ";
			this.setAttr("endOccurtTimeQuery", getPara("endOccurtTimeQuery"));
		}
		
		return sqlParam;
	}
}
