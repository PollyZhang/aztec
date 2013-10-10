package com.sosee.web.controller;

import java.util.Date;
import java.util.UUID;


import com.jfinal.aop.Before;
import com.jfinal.kit.StringKit;

import com.sosee.web.validator.QuestionsQueryValidator;
import com.sosee.app.member.pojo.Member;
import com.sosee.app.ticket.pojo.Questions;
import com.sosee.app.util.AppConstants;
import com.sosee.web.util.Constants;
/**
 * 
 * @author  :outworld
 * @date    :2013-3-31 上午11:19:19
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:前台旅客问答查询管理
 */

public class QuestionsQueryController extends WebController {
	/**
	 * 登陆跳转
	 */
	public void index(){
		try{
		this.createToken("questionsToken",1800);	
		String type=this.getPara("type");
		if(StringKit.notBlank(type) && isExistType(type)){
			this.init();
			this.setAttr("type", type);
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			
			setAttr("questionsNewList", getModel(Questions.class).paginate(pageIndex, 8, "select * ", "from t_questions where 1=1 "+getParaStr()+" order by askTime desc"));
			
			setAttr("questionsHotList", getModel(Questions.class).paginate(1, 5, "select * ", "from t_questions order by viewTimes desc"));
	 
		}
		this.render("/WEB-INF/web/questionsQueryList.html");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private String createNewToken(){
		  this.createToken("questionsToken",1800);	
		  return this.getAttrForStr("questionsToken");
	}
	
	
	/**
	 * 保存
	 */
	@Before(QuestionsQueryValidator.class)
	public void save(){
		try {
			
			Questions questions= this.getModel(Questions.class);
			boolean bInfo=false;
			
			Member memberSession=this.getSessionAttr(AppConstants.MEMBER);
			if(StringKit.notNull(memberSession)){
				questions.set("id", UUID.randomUUID().toString());
				questions.set("askTime", new Date());
				questions.set("memberName", memberSession.getStr("account"));
				questions.set("viewTimes", 0);
				bInfo=questions.save();
				if(bInfo){
					this.renderJson("{\"result\":1,\"msg\":\"问题提交成功!\"}");
					//this.keepPara();
					//index();
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"请先登录再提问!\"}");
			}
			
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
		}
		
	}
	
	
	public void ask(){
		Member memberSession=this.getSessionAttr(AppConstants.MEMBER);
		if(StringKit.notNull(memberSession)){
			this.renderJson("{\"result\":1,\"questionsToken\":"+createNewToken()+",\"msg\":\"可以提问\"}");
		}else{
			this.renderJson("{\"result\":0,\"msg\":\"请先登录再提问!\"}");
		}
	}
	
	/**
	 * 查看问题
	 */
	public void lookQuestions(){
		try {
			String id = this.getPara();
			if (StringKit.notBlank(id)) {
				Questions questions = this.getModel(Questions.class).findById(id);
				if (StringKit.notNull(questions)) {
					questions.set("viewTimes", questions.getInt("viewTimes")+1).update();
					this.renderJson("{\"result\":1,\"questions\":"
							+ questions.toJson() + ",\"msg\":\"\"}");
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String getParaStr(){
		String sqlParam="";
		if(StringKit.notBlank(getPara("titleQuery"))){
			sqlParam+=" and title like '%"+getPara("titleQuery").trim().replace("'", "")+"%' ";
			this.setAttr("titleQuery", getPara("titleQuery"));
		}
		
		return sqlParam;
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
}
