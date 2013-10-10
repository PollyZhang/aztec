package com.sosee.sys.log.pojo;

import java.io.Serializable;
import java.util.Date;

import com.sosee.sys.log.util.LoggerUtil;

/**
 * @author  :outworld
 * @date    :2012-12-5 上午11:19:10
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:日志的pojo
 */
@SuppressWarnings("serial")
public class LogInfo implements Serializable{
   private String type;		//目前类型分为system/debug/security/business等
   private String typeTitle;//类型的中文标示
   private String priority;	//没有明确用途，
   private String level;	//级别，没有明确用途
   private String source;	//哪个模块
   private String result;	//操作结果
   private String message;	//日志信息
   private String operator;	//操作员姓名
   private String computer;	//操作员所使用的计算机ip
   private Date occurtTime;	//记录时间
   
   public LogInfo() {}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getOccurtTime() {
		return occurtTime;
	}
	public void setOccurtTime(Date occurtTime) {
		this.occurtTime = occurtTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getComputer() {
		return computer;
	}
	public void setComputer(String computer) {
		this.computer = computer;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = LoggerUtil.getLoggerTypeName(type);
	}

	@Override
	public String toString() {
		return "LogInfo [type=" + type + ", typeTitle=" + typeTitle
				+ ", priority=" + priority + ", level=" + level + ", source="
				+ source + ", result=" + result + ", message=" + message
				+ ", operator=" + operator + ", computer=" + computer
				+ ", occurtTime=" + occurtTime + "]";
	}
	
}
