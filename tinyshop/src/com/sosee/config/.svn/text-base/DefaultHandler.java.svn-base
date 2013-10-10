package com.sosee.config;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.plugin.activerecord.Record;

public class DefaultHandler extends Handler {

	private Map<String,String> initMap;
	private Map<String,List<Record>> paramMap;
	
	public DefaultHandler(){
		
	}
	public void setInitMap(Map<String,String> initMap){
		this.initMap=initMap;
	}
	
	public void setParamMap(Map<String,List<Record>> paramMap){
		this.paramMap=paramMap;
	}
	
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		
		if(initMap!=null && initMap.size()>0){
			for(String strKey : initMap.keySet()){
				request.setAttribute(strKey, initMap.get(strKey));
			}
		}
		
		if(paramMap!=null && paramMap.size()>0){
			for(String strKey : paramMap.keySet()){
				request.setAttribute(strKey, paramMap.get(strKey));
			}
		}
		nextHandler.handle(target, request, response, isHandled);
	}

}
