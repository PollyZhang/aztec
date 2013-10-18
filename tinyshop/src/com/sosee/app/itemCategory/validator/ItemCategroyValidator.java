package com.sosee.app.itemCategory.validator;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.sosee.sys.util.SysFileManage;

public class ItemCategroyValidator extends Validator {

	@Override
	protected void handleError(Controller c) {
		UploadFile itemPicUrl=null;
		try{
			itemPicUrl=c.getFile("itemPicUrl","",c.getRequest().getContentLength());
		}catch(Exception e){}
		validateString("items.itemName",1,200, "errorinfo", "标题不能为空,且必须在1-200位之间!");
		validateString("items.itemPrice",1,20, "errorinfo", "标题不能为空!");
		//this.validateToken("contentsToken", "errorinfo", "请勿重复提交");
		String msgString=c.getAttrForStr("errorinfo");
		if(itemPicUrl!=null){
			if(SysFileManage.isSizeOrSuffix(itemPicUrl.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("imageFile", SysFileManage.setFileProperties(itemPicUrl.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				itemPicUrl.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		
		
	}

	@Override
	protected void validate(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

}
