package com.sosee.sys.job;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author  :outworld
 * @date    :2012-12-5 下午4:56:39
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:系统初始化
 */
@SuppressWarnings("serial")
public class StartServlet extends HttpServlet {
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		//启动日志等缓冲区对象转存处理,消息服务器发送心跳信号
		GlobalJob.getInstance().start();
	}
	
}
