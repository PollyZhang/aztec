package com.sosee.sys.log.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.sosee.sys.util.SysConstants;
/**
 * @author  :outworld
 * @date    :2012-12-5 下午9:30:16
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:日志过滤器，主要获取当前登录人的相关信息
 */
public class LogFilter implements Filter {
    private final static String DEFAULT_STRING="未知";
    
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session= req.getSession();
        if (session==null){
        	MDC.put(SysConstants.LOGIN_NAME,DEFAULT_STRING);  
        	MDC.put(SysConstants.LOGIN_IP,DEFAULT_STRING);  
        }
        else{
        	if(session.getAttribute(SysConstants.LOGIN_NAME)!=null){
        		MDC.put(SysConstants.LOGIN_NAME,session.getAttribute(SysConstants.LOGIN_NAME));
        	}else{
        		MDC.put(SysConstants.LOGIN_NAME,DEFAULT_STRING);  
        	}
        	if(session.getAttribute(SysConstants.LOGIN_IP)!=null){
        		MDC.put(SysConstants.LOGIN_IP,session.getAttribute(SysConstants.LOGIN_IP));
        	}else{
        		MDC.put(SysConstants.LOGIN_IP,DEFAULT_STRING);
        	}
        }
        
        chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		
	}

}
