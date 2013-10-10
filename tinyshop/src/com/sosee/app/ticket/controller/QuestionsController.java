package com.sosee.app.ticket.controller;

import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;

import com.sosee.app.ticket.pojo.Questions;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.util.SysConstants;
/**
 * 
 * @author  :outworld
 * @date    :2013-3-31 上午11:19:19
 * @Copyright:2013 outworld Studio Inc. All rights reserved.
 * @function:后台旅客问答查询管理
 */
@Before(LoginInterceptor.class)
public class QuestionsController extends Controller {
	/**
	 * 登陆跳转
	 */
	public void index(){
		this.createToken("questionsToken",1800);
	    try{	   
			int pageIndex=this.getParaToInt("pageIndex")!=null && this.getParaToInt("pageIndex")!=0?this.getParaToInt("pageIndex"):1;
			setAttr("questionsPage", getModel(Questions.class).paginate(pageIndex, SysConstants.PAGE_NORMAL_SIZE, "select * ", "from t_questions where 1=1 "+getParaStr()+" order by askTime desc"));
			this.render("/WEB-INF/sys/orders/questionsList.html");
	    }catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String createNewToken(){
		  this.createToken("questionsToken",1800);	
		  return this.getAttrForStr("questionsToken");
	}
	
	/**
	 * 回复问题
	 */
	public void returnQuestion(){
		try{
			String id=this.getPara();
			if(StringKit.notBlank(id)){
				Questions questions=this.getModel(Questions.class).findById(id);
				if(StringKit.notNull(questions)){
					this.setAttr("questions", questions);
					this.renderJson("{\"questionsToken\":\""+createNewToken()+"\",\"result\":1,\"questions\":"+questions.toJson()+",\"msg\":\"\"}");
				}else{
					this.renderJson("{\"questionsToken\":\""+createNewToken()+"\",\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"questionsToken\":\""+createNewToken()+"\",\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"questionsToken\":\""+createNewToken()+"\",\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/**
	 * 保存
	 */
	public void save(){
		try {
			
			Questions questions= this.getModel(Questions.class);
			boolean bInfo=false;
			questions.set("answerTime", new Date());
			bInfo=questions.update();
			if(bInfo){
				this.keepPara();
				index();
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"保存失败!\"}");
		}
		
	}
	
	public void del(){
		try {
			String id = this.getPara();
			if (StringKit.notBlank(id)) {
				boolean bInfo=false;
				bInfo=this.getModel(Questions.class).deleteById(id);
				
				if (bInfo) {
					this.renderJson("{\"result\":1,\"msg\":\"删除成功!\"}");
				} else {
					this.renderJson("{\"result\":0,\"msg\":\"删除失败!\"}");
				}
			} else {
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		} catch (Exception e) {
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
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
		
		if(StringKit.notBlank(getPara("startAskTimeQuery"))){
			sqlParam+=" and askTime>='"+getPara("startAskTimeQuery")+" 00:00:00' ";
			this.setAttr("startAskTimeQuery", getPara("startAskTimeQuery"));
		}
		if(StringKit.notBlank(getPara("endAskTimeQuery"))){
			sqlParam+=" and askTime<='"+getPara("endAskTimeQuery")+" 23:59:59' ";
			this.setAttr("endAskTimeQuery", getPara("endAskTimeQuery"));
		}
		
		return sqlParam;
	}
}
