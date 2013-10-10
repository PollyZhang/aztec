package com.sosee.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.member.interceptor.WebLoginInterceptor;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.ticket.pojo.WangShangShouPiaoJiLu;
import com.sosee.app.util.AppConstants;
import com.sosee.config.DefaultConfig;
import com.sosee.util.DateUtil;
import com.sosee.web.util.AlipayUtils;
import com.sosee.web.util.Constants;
import com.sosee.web.validator.QueryValidator;
/**
 * 
 * 作者:强乐
 * 功能:前台订单查询
 * 时间:2013-4-2 下午8:07:40
 */
@Before(WebLoginInterceptor.class)
public class QueryController extends WebController {

//	/**
//	 * 订单查询
//	 */
//	public void index(){
//		init();
//		this.keepPara();
//		this.createToken("cancelToken", 1800);
//		try{
//			String strSql="";
//			int pageIndex=1;
//	    	setAttr("zhuangTaiMap", AppConstants.getZhuangTaiMap());
//			try{
//				pageIndex=Integer.parseInt(this.getPara("pageIndex"));
//			}catch(Exception e){}
//			String xianKeZhanQuery=this.getPara("xianKeQuery");
//			String zhuangTaiQuery=this.getPara("zhuangTaiQuery");
//			String zhuangTaiAttr=this.getAttrForStr("zhuangTaiQuery");//为查询未完成订单服务
//			String startQuery=this.getPara("startQuery");
//			String endQuery=this.getPara("endQuery");
//			if(xianKeZhanQuery!=null && !xianKeZhanQuery.trim().equals("") && xianKeZhanQuery.length()<10){
//				strSql+=" and w.XiaKeZhan='"+xianKeZhanQuery.trim()+"' ";
//			}
//			if(zhuangTaiQuery!=null && !zhuangTaiQuery.trim().equals("") && zhuangTaiQuery.length()<10){
//				strSql+=" and w.ZhuangTai='"+zhuangTaiQuery.trim()+"' ";
//			}
//			if(zhuangTaiAttr!=null && !zhuangTaiAttr.trim().equals("") && zhuangTaiAttr.length()<10){
//				strSql+=" and w.ZhuangTai='"+zhuangTaiAttr.trim()+"' ";
//			}
//			if(startQuery!=null && !startQuery.trim().equals("") && endQuery!=null && !endQuery.trim().equals("")){
//				strSql+=" and w.FaCheRiQi between '"+DateUtil.formatDate(DateUtil.parseDate(startQuery))+" 00:00:00' and '"+DateUtil.formatDate(DateUtil.parseDate(endQuery))+" 23:59:59' ";
//			}
//			Member member=this.getSessionAttr(AppConstants.MEMBER);
//			Page<WangShangShouPiaoJiLu> listPage= getModel(WangShangShouPiaoJiLu.class).paginate((pageIndex>0?pageIndex:1), Constants.PAGE_SIZE, "select *", "from WangShangShouPiaoJiLu as w where w.GouPiaoRenDengLuMing='"+member.getStr("account")+"' "+strSql+" order by ShouPiaoRiQiShiJian desc ");
//			this.setAttr("listPage", listPage);
//			this.render("/WEB-INF/web/query.html");
//		}catch(Exception e){
//			this.render("/WEB-INF/web/index.html");
//		}
//	}
//	
//	/**
//	 * 撤销订单
//	 */
//	@Before(QueryValidator.class)
//	public void cancelOrder(){
//		try{
//			//网上售票记录id
//			String id=this.getPara("id").trim();
//			if(id==null || id=="" || id.length()>40){
//				//数据异常
//				throw new Exception();
//			}
//			WangShangShouPiaoJiLu wangShangShouPiaoJiLu=this.getModel(WangShangShouPiaoJiLu.class).findById(id);
//			if(wangShangShouPiaoJiLu!=null && !wangShangShouPiaoJiLu.equals("")){
//				String ZhuangTai=wangShangShouPiaoJiLu.getStr("ZhuangTai");
//				if(ZhuangTai!=null && ZhuangTai.equals(AppConstants.PAY_STATUS_NO)){
//					Constants.createLog(wangShangShouPiaoJiLu.getStr("GouPiaoRenXingMing"), wangShangShouPiaoJiLu.getStr("DingDanHao"), AppConstants.PAY_STATUS_CANCEL, new Date());
//					//售票系统取消占座
//					String FaCheRiQiStr=wangShangShouPiaoJiLu.get("FaCheRiQi").toString().split(" ")[0].replace("-", "");
//					Db.update("update WangShangShouPiaoJiLu as w set w.ZhuangTai='"+AppConstants.PAY_STATUS_CANCEL+"',w.ZhuangTaiRiQi='"+DateUtil.formatLongDateTime(new Date())+"',w.TongBuBiaoZhi=0 where w.id='"+id+"'");
//					List<Record> zuoHaoList=Db.find(DefaultConfig.c3p0Plugin2.getDataSource() ,"select ID from "+AppConstants.SYNC_RiCheCiJiHuaZuoHao+FaCheRiQiStr+" where CheCi='"+wangShangShouPiaoJiLu.getStr("CheCi")+"' and ZuoHao "+AppConstants.zuoHaoString(wangShangShouPiaoJiLu.getStr("ZuoHao"))+" order by ZuoHao ");
//					if(zuoHaoList!=null && zuoHaoList.size()>0){
//						for(Record r : zuoHaoList){
//							Db.update(DefaultConfig.c3p0Plugin2.getDataSource(),"update "+AppConstants.SYNC_RiCheCiJiHuaZuoHao+FaCheRiQiStr+" set ZhanYongBiaoJi=0 where ID='"+r.get("ID")+"'");
//						}
//						//更新余票信息
//						Db.update(DefaultConfig.c3p0Plugin2.getDataSource(),"update "+AppConstants.SYNC_RiCheCiJiHua+FaCheRiQiStr+" set YiShouZuoWei=YiShouZuoWei-"+zuoHaoList.size()+" where CheCi='"+wangShangShouPiaoJiLu.getStr("CheCi")+"'");
//					}
//					this.setAttr("msg", "订单撤销成功!");
//				}else{
//					this.setAttr("error", "订单撤销失败,只能撤销状态为未支付的订单!");
//				}
//			}else{
//				this.setAttr("error", "订单撤销失败，未查到订单信息!");
//			}
//		}catch(Exception e){
//			this.setAttr("error", "订单撤销失败!");
//		}
//		index();
//	} 
//	
//	/**
//	 * 获取支付URL
//	 */
//	public void getAlipayUrl(){
//		try{
//			//网上售票记录id
//			String id=this.getPara("id").trim();
//			//默认银行编码
//			String defaultbank=this.getPara("bankCode").trim();
//			//客户端ip
//			String clientIp=getIpAddr();
//			if(id==null || id=="" || id.length()>40 || defaultbank==null || defaultbank=="" || defaultbank.length()>20){
//				//数据异常
//				throw new Exception();
//			}
//			WangShangShouPiaoJiLu wangShangShouPiaoJiLu=this.getModel(WangShangShouPiaoJiLu.class).findById(id);
//			if(wangShangShouPiaoJiLu!=null && !wangShangShouPiaoJiLu.equals("")){
//				String ZhuangTai=wangShangShouPiaoJiLu.getStr("ZhuangTai");
//				if(ZhuangTai!=null && ZhuangTai.equals(AppConstants.PAY_STATUS_NO)){
//					String workOrderNum=wangShangShouPiaoJiLu.getStr("DingDanHao");
//					float totalMoney=wangShangShouPiaoJiLu.getFloat("PiaoJia")*wangShangShouPiaoJiLu.getInt("PiaoShu");
//					String url=AlipayUtils.buildRequest(wangShangShouPiaoJiLu, totalMoney+"", defaultbank, clientIp);
//					this.renderJson("{\"result\":1,\"url\":\""+url+"\"}");
//				}else{
//					this.renderJson("{\"result\":0,\"url\":\"只有状态为未付款的订单，才能进行支付操作！\"}");
//				}
//			}else{
//				this.renderJson("{\"result\":0,\"url\":\"未找到订单信息，请核对订单信息是否正确！\"}");
//			}
//		}catch(Exception e){
//			this.renderJson("{\"result\":0,\"url\":\"获取支付地址失败！\"}");
//		}
//	}
//	
//	/**
//	 * 获取IP地址
//	 * @return 客户端ip地址
//	 */
//	private String getIpAddr() {
//		HttpServletRequest request=this.getRequest();
//		String ip = request.getHeader("x-forwarded-for");
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//	       ip = request.getRemoteAddr();
//		}
//	       return ip;
//	}
//	
//	/**
//	 * 查询未支付订单
//	 */
//	public void noFinish(){
//		this.setAttr("zhuangTaiQuery", AppConstants.PAY_STATUS_NO);
//		index();
//	}
}
