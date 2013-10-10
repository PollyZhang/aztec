package com.sosee.sys.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

import com.sosee.sys.log.util.LoggerBatchJob;

/**
 * @author  :outworld
 * @date    :2012-12-5 下午5:04:39
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:
 */
public  final class GlobalJob  {
	private static GlobalJob globalJob;

	private GlobalJob() {
		super();
	}
	public static GlobalJob getInstance(){
		if(globalJob==null){
			globalJob=new GlobalJob();
		}
		return globalJob;
	}
	
	//启动不同的批处理任务
	public void start(){
		SchedulerFactory schedFact = new StdSchedulerFactory();
		Scheduler sched=null;
		try {
			sched = schedFact.getScheduler();
			logJob(sched);//日志作业
			
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 定制日志的批处理转存
	 * @param sched
	 */
	private void logJob(Scheduler sched){
		JobDetail jobDetail = new JobDetail("logJob",
                null,
                LoggerBatchJob.class);
		  Trigger trigger = TriggerUtils.makeMinutelyTrigger(2); //fire every two minute
		  Date runTime = TriggerUtils.getEvenMinuteDate(new Date());
		  trigger.setStartTime(runTime);  //start on the next even hour
		  trigger.setGroup("batchGroup");
		  trigger.setName("logTrigger");
		  try {
			sched.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
}
