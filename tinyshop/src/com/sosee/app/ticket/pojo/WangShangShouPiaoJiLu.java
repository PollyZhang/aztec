package com.sosee.app.ticket.pojo;

import com.jfinal.plugin.activerecord.Model;

/**
 * 
 * @author  :outworld
 * @date    :2013-3-9 下午10:19:08
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:网上售票记录
 *  String 		id;			            	//标识 UUID,说明：web生成
 *  String 		FaCheZhan varchar(30),  	//发车站,说明：web生成
 *  String 		ShouPiaoZhan varchar(20), 	//售票站（值：互联网）,说明：web生成
 *  String 		CheCi varchar(20), 			//车次,说明：web生成
 *  String 		XianLuMingCheng varchar(30), //线路名称,说明：web生成
 *  String 		ZhongDianZhan varchar(20),	 //终点站,说明：web生成
 *  String 		XiaKeZhan varchar(20), 		//下客站,说明：web生成
 *  Date 		FaCheRiQi datetime NULL, 	//发车日期,说明：web生成
 *  String 		FaCheShiJian varchar(10), 	//发车时间,说明：web生成
 *  int 		PiaoShu int(10), 			//票数,说明：web生成
 *  double 		PiaoJia float,				//票价,说明：web生成
 *  double 		KouWeiJiFei float, 			//扣微机费,说明：web生成
 *  double 		KouZhanWuFei float, 		//扣站务费,说明：web生成
 *  double 		KouKongTiaoFei float,		//扣空调费 ,说明：web生成
 *  double 		KouQiTaFei float, 			//扣其他费,说明：web生成
 *  String 		ZuoHao varchar(50), 		//座号（每个座号用两位数表示：如：09）,说明：web生成
 *  String 		DingDanHao varchar(20), 	//订单号,说明：web生成
 *  String 		GouPiaoRenDengLuMing varchar(20), //购票人登录名,说明：web生成
 *  String 		GouPiaoRenDianHua varchar(20), //购票人电话,说明：从会员表中取
 *  String 		GouPiaoRenXingMing varchar(20), //购票人姓名,说明：web生成
 *  String 		QuPiaoRenXingMing varchar(20), //取票人姓名,说明：web生成
 *  String 		ShenFenZhengHao varchar(20), //身份证号用于取票,说明：web生成
 *  Date 		QuPiaoRiQi datetime NULL, 	//取票日期,由售票系统更新回web
 *  String 		QuPiaoCaoZuoYuan varchar(20), //取票操作员,由售票系统更新回web
 *  String 		JinZhanKou varchar(20), 	//进站口,说明：web生成
 *  String 		FaCheQu varchar(20), 		//发车区,说明：web生成
 *  String 		ZhuangTai varchar(10),		//状态,说明：web生成,最后取售票时要更新回已取票
 *  Date        ZhuangTaiRiQi Date,         //同步日期
 *  int 		TongBuBiaoZhi tinyint		//同步标志，1表示已同步，0表示未同步
 *  Date 		ShouPiaoRiQiShiJian datetime NULL, //售票日期时间,说明：web生成
 */
@SuppressWarnings("serial")
public class WangShangShouPiaoJiLu extends Model<WangShangShouPiaoJiLu> {

}
