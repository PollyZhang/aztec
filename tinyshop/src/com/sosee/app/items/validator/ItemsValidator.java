package com.sosee.app.items.validator;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.sosee.app.items.pojo.Items;
import com.sosee.sys.util.SysFileManage;

public class ItemsValidator extends Validator{

	
	@Override
	protected void validate(Controller c) {
		UploadFile itemPicUrl=null;
		try{
			itemPicUrl=c.getFile("itemPicUrl","",c.getRequest().getContentLength());
		}catch(Exception e){}
		//this.validateToken("contentsToken", "errorinfo", "请勿重复提交");
		String msgString=c.getAttrForStr("errorinfo");
		if(itemPicUrl!=null){
			if(SysFileManage.isSizeOrSuffix(itemPicUrl.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("itemPicUrl", SysFileManage.setFileProperties(itemPicUrl.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				itemPicUrl.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
	}
	
	
	@Override
	protected void handleError(Controller c) {
		c.keepModel(Items.class);
		c.keepPara();
		if(c.getAttrForStr("id")==null || c.getAttrForStr("id").equals("")){
			c.forwardAction("/items/add");
		}else{
			c.forwardAction("/items/add");
		}
		
	}



}
