package com.sosee.app.ticket.controller;


import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.sosee.app.ticket.pojo.WangShangShouPiaoJiLu;
import com.sosee.app.util.AppConstants;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.util.SysConstants;

/**
 * 
 * @author  :outworld
 * @date    :2013-3-31 上午11:19:19
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:后台定单查询管理
 */
@Before(LoginInterceptor.class)
public class OrdersController extends Controller {
	/**
	 * 登陆跳转
	 */
	public void index(){
		this.createToken("ordersToken",1800);
	    try{
	    	setAttr("zhuangTaiMap", AppConstants.getZhuangTaiMap());
		  	   
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			setAttr("ordersPage", getModel(WangShangShouPiaoJiLu.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select * ", "from WangShangShouPiaoJiLu where 1=1 "+getParaStr()+" order by ShouPiaoRiQiShiJian desc"));
			this.render("/WEB-INF/sys/orders/ordersList.html");
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 撤销定单
	 */
	public void cancelOrder(){
		this.createToken("ordersToken",1800);
		try {
			String id = this.getPara();
			if (StringKit.notBlank(id)) {
				WangShangShouPiaoJiLu order = this.getModel(WangShangShouPiaoJiLu.class).findById(id);
				if (StringKit.notNull(order)) {
					boolean bInfo=false;
					order.set("ZhuangTai", AppConstants.PAY_STATUS_CANCEL);
					order.set("ZhuangTaiRiQi", new Date());
					order.set("TongBuBiaoZhi", 0);
					
					bInfo=order.update();
					if(bInfo){
						this.renderJson("{\"result\":1,\"msg\":\"撤销成功!\"}");
					}else{
						this.renderJson("{\"result\":0,\"msg\":\"撤销失败!\"}");
					}
					
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"撤销失败!\"}");
		}
	}
	
	/**
	 * 查看定单
	 */
	public void lookOrder(){
		try {
			String id = this.getPara();
			if (StringKit.notBlank(id)) {
				WangShangShouPiaoJiLu order = this.getModel(WangShangShouPiaoJiLu.class).findById(id);
				if (StringKit.notNull(order)) {
					this.renderJson("{\"result\":1,\"order\":"
							+ order.toJson() + ",\"msg\":\"\"}");
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String getParaStr(){
		String sqlParam="";
		if(StringKit.notBlank(getPara("XiaKeZhanQuery"))){
			sqlParam+=" and XiaKeZhan like '%"+getPara("XiaKeZhanQuery").trim().replace("'", "")+"%' ";
			this.setAttr("XiaKeZhanQuery", getPara("XiaKeZhanQuery"));
		}
		
		if(StringKit.notBlank(getPara("startFaCheRiQiQuery"))){
			sqlParam+=" and FaCheRiQi>='"+getPara("startFaCheRiQiQuery")+" 00:00:00' ";
			this.setAttr("startFaCheRiQiQuery", getPara("startFaCheRiQiQuery"));
		}
		if(StringKit.notBlank(getPara("endFaCheRiQiQuery"))){
			sqlParam+=" and FaCheRiQi<='"+getPara("endFaCheRiQiQuery")+" 23:59:59' ";
			this.setAttr("endFaCheRiQiQuery", getPara("endFaCheRiQiQuery"));
		}
		
		if(StringKit.notBlank(getPara("GouPiaoRenDengLuMingQuery"))){
			sqlParam+=" and GouPiaoRenDengLuMing like '%"+getPara("GouPiaoRenDengLuMingQuery").trim().replace("'", "")+"%' ";
			this.setAttr("GouPiaoRenDengLuMingQuery", getPara("GouPiaoRenDengLuMingQuery"));
		}
		
		if(StringKit.notBlank(getPara("zhuangTaiQuery"))){
			sqlParam+=" and ZhuangTai='"+getPara("zhuangTaiQuery")+"' ";
			this.setAttr("zhuangTaiQuery", getPara("zhuangTaiQuery"));
		}
		
		if(StringKit.notBlank(getPara("startShouPiaoRiQiShiJianQuery"))){
			sqlParam+=" and ShouPiaoRiQiShiJian>='"+getPara("startShouPiaoRiQiShiJianQuery")+" 00:00:00' ";
			this.setAttr("startShouPiaoRiQiShiJianQuery", getPara("startShouPiaoRiQiShiJianQuery"));
		}
		if(StringKit.notBlank(getPara("endShouPiaoRiQiShiJianQuery"))){
			sqlParam+=" and ShouPiaoRiQiShiJian<='"+getPara("endShouPiaoRiQiShiJianQuery")+" 23:59:59' ";
			this.setAttr("endShouPiaoRiQiShiJianQuery", getPara("endShouPiaoRiQiShiJianQuery"));
		}
		
		return sqlParam;
	}
}
