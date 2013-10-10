package com.sosee.app.content.validator;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;
import com.sosee.app.content.pojo.Contents;
import com.sosee.sys.util.SysFileManage;
/**
 * 
 * @author  :outworld
 * @date    :2012-12-6 下午5:54:38
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:
 */
public class ContentsValidator extends Validator {

	@Override
	protected void validate(Controller c) {
			UploadFile imageFile=null;
			UploadFile videoFile=null;
			UploadFile attachFile=null;
			try{
				imageFile=c.getFile("imageFile","",c.getRequest().getContentLength());
			}catch(Exception e){}
			try{
				videoFile=c.getFile("videoFile","",c.getRequest().getContentLength());
			}catch(Exception e){}
			try{
				attachFile=c.getFile("attachFile","",c.getRequest().getContentLength());
			}catch(Exception e){}
			validateString("contents.contentType",1,30, "errorinfo", "类型不能为空,且必须在1-30位之间!");
			validateString("contents.title",1,200, "errorinfo", "标题不能为空,且必须在1-200位之间!");
			this.validateDate("contents.newsDate", "2010-01-01", "2099-01-01", "errorinfo", "新闻日期不能为空!");
			this.validateToken("contentsToken", "errorinfo", "请勿重复提交");
			String msgString=c.getAttrForStr("errorinfo");
			if(imageFile!=null){
				if(SysFileManage.isSizeOrSuffix(imageFile.getFile(), SysFileManage.FILE_IMAGE,3) && (msgString==null || msgString=="")){
					c.setAttr("imageFile", SysFileManage.setFileProperties(imageFile.getFile(), SysFileManage.FILE_IMAGE));
				}else{
					imageFile.getFile().delete();
					this.addError("errorinfo", "图片附件超过"+c.getAttrForStr("imageFile_Size")+"MB或者文件类型不对!");
				}
			}
			if(videoFile!=null){
				if(SysFileManage.isSizeOrSuffix(videoFile.getFile(), SysFileManage.FILE_VIDEO,10) && (msgString==null || msgString=="")){
					c.setAttr("videoFile", SysFileManage.setFileProperties(videoFile.getFile(), SysFileManage.FILE_VIDEO));
				}else{
					videoFile.getFile().delete();
					this.addError("errorinfo", "视频附件超过"+c.getAttrForStr("videoFile_Size")+"MB或者文件类型不对!");
				}
			}
			if(attachFile!=null){
				if(SysFileManage.isSizeOrSuffix(attachFile.getFile(), SysFileManage.FILE_ATTACH,10) && (msgString==null || msgString=="")){
					c.setAttr("attachFile", SysFileManage.setFileProperties(attachFile.getFile(), SysFileManage.FILE_ATTACH));
				}else{
					attachFile.getFile().delete();
					this.addError("errorinfo", "普通附件超过"+c.getAttrForStr("attachFile_Size")+"MB或者文件类型不对!");
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
