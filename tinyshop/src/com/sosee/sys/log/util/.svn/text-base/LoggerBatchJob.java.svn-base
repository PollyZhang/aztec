package com.sosee.sys.log.util;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.sys.log.pojo.LogInfo;

@SuppressWarnings("serial")
public final class LoggerBatchJob implements Job,Serializable{

	//定时转存集合中的日志对象
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoggerHandler handler=LoggerHandler.getInstace(null);
		if(handler!=null){
			List<LogInfo> logs=handler.getBatchLog();
			for(LogInfo log:logs){
				Db.save("t_loginfo", new Record().set("id", UUID.randomUUID().toString())
				.set("type",log.getType())
				.set("typeTitle",log.getTypeTitle())
				.set("priority",log.getPriority())
				.set("level",log.getLevel())
				.set("source",log.getSource())
				.set("result",log.getResult())
				.set("message",log.getMessage())
				.set("operator",log.getOperator())
				.set("computer",log.getComputer())
				.set("occurtTime",log.getOccurtTime())
				);
			}
		}
			
	}

}
