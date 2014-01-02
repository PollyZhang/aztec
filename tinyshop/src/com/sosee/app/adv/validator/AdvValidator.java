package com.sosee.app.adv.validator;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.sosee.app.content.pojo.Contents;
import com.sosee.sys.util.SysFileManage;

public class AdvValidator extends Validator {

	@Override
	protected void validate(Controller c) {
			UploadFile imgUrl=null;
			try{
				imgUrl=c.getFile("imgUrl","",c.getRequest().getContentLength());
			}catch(Exception e){}
			//this.validateToken("contentsToken", "errorinfo", "请勿重复提交");
			String msgString=c.getAttrForStr("errorinfo");
			if(imgUrl!=null){
				if(SysFileManage.isSizeOrSuffix(imgUrl.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
					c.setAttr("imgUrl", SysFileManage.setFileProperties(imgUrl.getFile(), SysFileManage.FILE_IMAGE));
				}else{
					imgUrl.getFile().delete();
					this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
				}
			}
	}

	@Override
	protected void handleError(Controller c) {
		c.keepModel(Contents.class);
		c.keepPara();
		if(c.getAttrForStr("id")==null || c.getAttrForStr("id").equals("")){
			c.forwardAction("/contents/add");
		}else{
			c.forwardAction("/contents/add");
		}
	}
}
