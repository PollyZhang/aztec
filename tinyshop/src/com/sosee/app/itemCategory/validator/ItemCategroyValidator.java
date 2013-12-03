package com.sosee.app.itemCategory.validator;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.sosee.app.itemCategory.pojo.ItemCategory;
import com.sosee.sys.util.SysFileManage;

public class ItemCategroyValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		UploadFile advImgUrl1=null;
		UploadFile advImgUrl2=null;
		UploadFile advImgUrl3=null;
		UploadFile advImgUrl4=null;
		UploadFile advImgUrl5=null;
		UploadFile advImgUrl6=null;
		UploadFile advImgUrl7=null;
		try{
			advImgUrl1=c.getFile("advImgUrl1","",c.getRequest().getContentLength());
			advImgUrl2=c.getFile("advImgUrl2","",c.getRequest().getContentLength());
			advImgUrl3=c.getFile("advImgUrl3","",c.getRequest().getContentLength());
			advImgUrl4=c.getFile("advImgUrl4","",c.getRequest().getContentLength());
			advImgUrl5=c.getFile("advImgUrl5","",c.getRequest().getContentLength());
			advImgUrl6=c.getFile("advImgUrl6","",c.getRequest().getContentLength());
			advImgUrl7=c.getFile("advImgUrl7","",c.getRequest().getContentLength());
		}catch(Exception e){}

		//this.validateToken("contentsToken", "errorinfo", "请勿重复提交");
		String msgString=c.getAttrForStr("errorinfo");
		if(advImgUrl1!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl1.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl1", SysFileManage.setFileProperties(advImgUrl1.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl1.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl2!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl2.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl2", SysFileManage.setFileProperties(advImgUrl2.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl2.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl3!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl3.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl3", SysFileManage.setFileProperties(advImgUrl3.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl3.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl4!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl4.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl4", SysFileManage.setFileProperties(advImgUrl4.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl4.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl5!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl5.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl5", SysFileManage.setFileProperties(advImgUrl5.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl5.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl6!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl6.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl6", SysFileManage.setFileProperties(advImgUrl6.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl6.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
		if(advImgUrl7!=null){
			if(SysFileManage.isSizeOrSuffix(advImgUrl7.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
				c.setAttr("advImgUrl7", SysFileManage.setFileProperties(advImgUrl7.getFile(), SysFileManage.FILE_IMAGE));
			}else{
				advImgUrl7.getFile().delete();
				this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
			}
		}
	}
	
	
	@Override
	protected void handleError(Controller c) {

		c.keepModel(ItemCategory.class);
		c.keepPara();
		
	}



}
