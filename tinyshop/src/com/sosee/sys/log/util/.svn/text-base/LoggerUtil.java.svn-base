package com.sosee.sys.log.util;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.WriterAppender;


/**
 * @author :outworld
 * @date :2012-12-5 上午9:54:45
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function: 由于Logger有继承性，所以下面的一个获得了配置之后，其他的也会获得 目前的信息统一设计为用下划线分隔的三部分内容，
 *            第一个是功能子系统，第2个是结果，第3个是操作内容
 *  格式：功能模块名_结果_操作内容
 */
@SuppressWarnings("serial")
public final class LoggerUtil implements Serializable {
    private static Map<String,String> loggerTypeMap;
    public static final String LOGGER_TYPE_SYSTEM="system";//系统级的日志
    public static final String LOGGER_TYPE_SECURITY="security";//系统安全事件如登录
    public static final String LOGGER_TYPE_BUSINESS="business";//业务逻辑操作日志
    
    public static final String LOGGER_APPENDER="tbo";//appder name
    
	public static Logger system = Logger.getLogger(LOGGER_TYPE_SYSTEM);
	public static Logger security = Logger.getLogger(LOGGER_TYPE_SECURITY);
	public static Logger business = Logger.getLogger(LOGGER_TYPE_BUSINESS);

	static {
			
			try {
				init();
				security();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 初始化日志类别和名称
	 */
	private static void init(){
		loggerTypeMap = new HashMap<String,String>();
		loggerTypeMap.put(LOGGER_TYPE_SYSTEM, "系统日志");
		loggerTypeMap.put(LOGGER_TYPE_SECURITY, "系统安全");
		loggerTypeMap.put(LOGGER_TYPE_BUSINESS, "业务操作");
	}
	
	/**
	 * @return
	 */
	public static Map<String, String> getLoggerTypeMap(){
		init();
		return loggerTypeMap;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static String getLoggerTypeName(String type){
		if(loggerTypeMap.get(type)!=null){
			return loggerTypeMap.get(type);
		}else{
			return "未定义";
		}
	}
	
	private static void security() throws IOException {
		PipedReader reader = new PipedReader();
		RollingFileAppender securityAppender = (RollingFileAppender) security
				.getAppender(LOGGER_APPENDER);

		Writer writer = new PipedWriter(reader);
		PrintWriter pw = new PrintWriter(writer, true);
		((WriterAppender) securityAppender).setWriter(pw);

		Thread t = LoggerHandler.getInstace(reader);
		t.start();
	}

	@SuppressWarnings("unused")
	private static void system() throws IOException {
		PipedReader reader = new PipedReader();
		RollingFileAppender systemAppender = (RollingFileAppender) system
				.getAppender(LOGGER_APPENDER);

		Writer writer = new PipedWriter(reader);
		PrintWriter pw = new PrintWriter(writer, true);
		((WriterAppender) systemAppender).setWriter(pw);

		Thread t = LoggerHandler.getInstace(reader);
		t.start();
	}

}
