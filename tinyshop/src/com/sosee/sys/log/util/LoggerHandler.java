package com.sosee.sys.log.util;

import java.io.PipedReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.sosee.sys.log.pojo.LogInfo;

@SuppressWarnings("serial")
public final class LoggerHandler extends Thread implements Serializable {
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	private final String SPLIT_FLAG = "#";
	
	private boolean working=true;
	
	//logInfo缓冲区
	private LinkedList<LogInfo> logInfoBuffer = new LinkedList<LogInfo>();

	PrintWriter pw;
	PipedReader reader;
	private static LoggerHandler handler;
	public static LoggerHandler getInstace(PipedReader reader){
		if(handler==null){
			if(reader!=null){
				handler=new LoggerHandler(reader);	
			}
		}
		return handler;
	}
	
    private LoggerHandler(PipedReader reader) {
        this.reader = reader;
    }
    
    public void run() {
        Scanner scanner = new Scanner(reader);
        LogInfo logInfo = null;
        while (scanner.hasNext()) {
        	String logInfoString = scanner.nextLine();
        	logInfoString.replaceAll(SPLIT_FLAG, " ");

            //解析LogInfo对象并放入缓冲区，当缓冲区满时，入库并清空缓冲区
            logInfo = parseStringToLogInfo(logInfoString);
            if(logInfo!=null){
            	logInfoBuffer.add(logInfo);            	
            }
            
        }
        try{
            scanner.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    
    /**
     * 把字符串解析成LogInfo对象
     * @return  LogInfo
     * 样例数据：
     */
    private LogInfo parseStringToLogInfo(String logInfoString){
    	LogInfo logInfo = null;
    	try{
        	String[] strArray = logInfoString.split(SPLIT_FLAG);
    		logInfo = new LogInfo();
    		logInfo.setType(strArray[2].replace("[", "").replace("]",""));
    		logInfo.setPriority(strArray[1]);
    		Date od = sdf.parse(strArray[0]);
    		logInfo.setOccurtTime(od);
    		logInfo.setTypeTitle(logInfo.getType());
    		logInfo.setLevel("1");
    		logInfo.setOperator(strArray[3]);
    		logInfo.setComputer(strArray[4]);
    		//目前的信息统一设计为用下划线分隔的三部分内容，第一个是功能子系统，第2个是结果，第3个是操作内容
    		String[] msgs=strArray[5].split("_");
    		if(msgs.length>=3){
    			logInfo.setSource(msgs[0]);
    			logInfo.setResult(msgs[1]);
    			logInfo.setMessage(msgs[2]);    
    		}else{
    			logInfo.setSource("未知");
    			logInfo.setResult("未知");
    			logInfo.setMessage(strArray[3]);    			
    		}

    		
    	}catch(Exception e){
    		LoggerUtil.business.debug("日志解析_失败_解析："+e.getMessage());
    	}
		
    	return logInfo;
    }
    
	//获得缓冲中存在的日志对象
    public List<LogInfo> getBatchLog(){
    	List<LogInfo> result=new ArrayList<LogInfo>();
    	while(!logInfoBuffer.isEmpty()){
    		LogInfo log=logInfoBuffer.poll();
    		if(log!=null){
    			result.add(log);
    		}else{
    			break;
    		}
    	}
    	return result;    		
    }
    
    /**
	 * @return the working
	 */
	public boolean isWorking() {
		return working;
	}
	/**
	 * @param working the working to set
	 */
	public void setWorking(boolean working) {
		this.working = working;
	}
	
}
