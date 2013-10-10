package com.sosee.web.controller;


import java.util.Calendar;
import java.util.List;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.util.AppConstants;

import com.sosee.util.DateUtil;
import com.sosee.web.util.Constants;

public class BusQueryController extends  WebController {
	/*
	 * 车次查询
	 */
	public void index(){
		try{
		this.createToken("busToken",1800);	
		String type=this.getPara("type");
		String yyyymmddString="";
		String tableNameString="";
		if(StringKit.notBlank(getPara("dateQuery"))){
			yyyymmddString=DateUtil.getDateString(DateUtil.parseDate(getPara("dateQuery")));
			this.setAttr("dateQuery", getPara("dateQuery"));
		}else{
			Calendar calendar = Calendar.getInstance();
			yyyymmddString=DateUtil.getDateString(calendar.getTime());
			this.setAttr("dateQuery", DateUtil.formatDate(calendar.getTime()));
		}
		tableNameString=AppConstants.SYNC_RiCheCiJiHua+yyyymmddString;
		if(!isExists(tableNameString)){
			this.setAttr("dateQuery", null);
			tableNameString=AppConstants.SYNC_RiCheCiJiHua;
		}
		//select a.CheCi,b.FaWangCheZhan,a.FaCheShiJian,b.CheXingMingCheng,a.ZhunShouZuoWei from RiCheCiJiHua20130411 a,XianLuBianMa b where a.XianLuBianMa=b.BianMa
		if(StringKit.notBlank(type) && isExistType(type)){
			this.init();
			this.setAttr("type", type);
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			setAttr("busList", Db.paginate(pageIndex, Constants.PAGE_SIZE, "select a.CheCi,b.FaWangCheZhan,a.FaCheShiJian,b.CheXingMingCheng,a.ZhunShouZuoWei "," from "+tableNameString+" a,XianLuBianMa b where a.XianLuBianMa=b.BianMa "+getParaStr()+" order by b.FaWangCheZhan,a.FaCheShiJian asc"));
				 
		}
		this.render("/WEB-INF/web/busQueryList.html");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isExists(String tablename){
		boolean b=false;
		try{
			List<Record> records=Db.find("SELECT table_name as cs FROM information_schema.TABLES WHERE table_name='"+tablename+"'");
			if(StringKit.notNull(records) && records.size()>0){
	        	b=true;
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	/*
	 * 比对传递类型是否正确
	 */
	private boolean isExistType(String type){
		boolean b=false;
		if(Constants.CONTENTS_CODE.length>0){
			for(int i=0;i<Constants.CONTENTS_CODE.length;i++){
				String str=Constants.CONTENTS_CODE[i];
				if(str.equals(type)){
					b=true;
					this.setAttr("menuNum", i+1);
					break;
				}
			}
		}
		return b;
	}
	
	/**
	 * 
	 * @return
	 */
	private String getParaStr(){
		String sqlParam="";
		if(StringKit.notBlank(getPara("faWangCheZhanQuery"))){
			sqlParam+=" and b.FaWangCheZhan like '%"+getPara("faWangCheZhanQuery").trim().replace("'", "")+"%' ";
			this.setAttr("faWangCheZhanQuery", getPara("faWangCheZhanQuery"));
		}
		
		if(StringKit.notBlank(getPara("faCheShiJianStartQuery"))){
			sqlParam+=" and a.FaCheShiJian >= '"+getPara("faCheShiJianStartQuery").trim().replace(":", ".")+"' ";
			this.setAttr("faCheShiJianStartQuery", getPara("faCheShiJianStartQuery"));
		}
		
		if(StringKit.notBlank(getPara("faCheShiJianEndQuery"))){
			sqlParam+=" and a.FaCheShiJian <= '"+getPara("faCheShiJianEndQuery").trim().replace(":", ".")+"' ";
			this.setAttr("faCheShiJianEndQuery", getPara("faCheShiJianEndQuery"));
		}
		return sqlParam;
	}
	
}