package com.sosee.web.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.util.AppConstants;
import com.sosee.web.util.Constants;

public class WebController extends Controller {

	public void init(){
		Page<Contents> contentsList= getModel(Contents.class).paginate(1, 10, "select *", "from t_contents as c where c.itemId in (select id From t_items where code='"+Constants.CONTENTS_CODE[1]+"') and c.status='"+AppConstants.CONTANTS_STATUS_OK+"' order by isTop desc,createTime desc");
		this.setAttr("contentsList", contentsList);
		Member member=this.getSessionAttr(AppConstants.MEMBER);
		if(member!=null){
			//查询未完成订单数量
			Record noFinishRecord=Db.findFirst("select count(*) as noFinish from WangShangShouPiaoJiLu as w where w.GouPiaoRenDengLuMing='"+member.getStr("account")+"' and w.ZhuangTai='"+AppConstants.PAY_STATUS_NO+"' ");
			if(noFinishRecord!=null && noFinishRecord.get("noFinish")!=null && !noFinishRecord.get("noFinish").toString().equals("")){
				this.setAttr("noFinish", noFinishRecord.get("noFinish").toString());
			}
			//查询已订购订单数量
			Record finishRecord=Db.findFirst("select count(*) as finish from WangShangShouPiaoJiLu as w where w.GouPiaoRenDengLuMing='"+member.getStr("account")+"' and w.ZhuangTai='"+AppConstants.PAY_STATUS_YES+"' ");
			if(finishRecord!=null && finishRecord.get("finish")!=null && !finishRecord.get("finish").toString().equals("")){
				this.setAttr("finish", finishRecord.get("finish").toString());
			}
		}
	}
}
