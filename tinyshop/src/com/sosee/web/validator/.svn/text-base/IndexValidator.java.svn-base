package com.sosee.web.validator;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.validate.Validator;
import com.sosee.app.content.pojo.Contents;
import com.sosee.app.util.AppConstants;
import com.sosee.web.util.Constants;
/**
 * 
 * 作者:强乐
 * 功能:验证前台传递的token是否失效
 * 时间:2013-4-4 下午12:36:03
 */
public class IndexValidator extends Validator {


	@Override
	protected void validate(Controller c) {
		String type=c.getPara("type");
		if(type!=null && type.trim().equals("1")){
			this.validateToken("piaoToken", "msg", "请勿重复提交表单!");
		}
	}
	
	@Override
	protected void handleError(Controller c) {
		Page<Contents> contentsList= c.getModel(Contents.class).paginate(1, 10, "select *", "from t_contents as c where c.itemId in (select id From t_items where code='"+Constants.CONTENTS_CODE[1]+"') and c.status='"+AppConstants.CONTANTS_STATUS_OK+"' order by isTop desc,createTime desc");
		c.setAttr("contentsList", contentsList);
		
		c.render("/WEB-INF/web/index.html");
	}

}
