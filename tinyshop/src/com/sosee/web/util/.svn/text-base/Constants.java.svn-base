package com.sosee.web.util;

import java.util.Date;
import java.util.UUID;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author  :outworld
 * @date    :2012-11-28 上午11:12:23
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:前面应用常量类
 */
public final class Constants {
	
	//前台顶级菜单分类
	public static final String[] CONTENTS_CODE={"ITEM001","ITEM002","ITEM003","ITEM004","ITEM005","ITEM006"};
	
	//前台页大小
	public static final int PAGE_SIZE=15;
	
	/**
	 * 生成状态日志
	 * @param gouPiaoRenXingMing
	 * @param dingDanHao
	 * @param zhuangTai
	 * @param createTime
	 */
	public static void createLog(String gouPiaoRenXingMing,String dingDanHao,String zhuangTai,Date createTime){
		try{
			Record cRecord = new Record().set("id", UUID.randomUUID().toString())
					.set("GouPiaoRenXingMing", gouPiaoRenXingMing)
					.set("DingDanHao", dingDanHao)
					.set("ZhuangTai", zhuangTai)
					.set("createTime", createTime);
			Db.save("t_orderslog", cRecord);
        		
		}catch (Exception e) {
			System.out.println("购票人姓名:"+gouPiaoRenXingMing+",订单号:"+dingDanHao+",状态:"+zhuangTai+",创建时间:"+createTime+",状态日志生成错误!");
			e.printStackTrace();
		}
	}
}
