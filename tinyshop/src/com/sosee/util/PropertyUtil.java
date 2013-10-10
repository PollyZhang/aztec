package com.sosee.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.fileupload.RequestContext;

public class PropertyUtil {
	private static String getFilePath(){
		return RequestContext.class.getResource("/").getPath()+"license.key";
	}
	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					getFilePath()));
			props.load(in);
			String value = props.getProperty(key);
			return value==null?"":value;
		} catch (Exception e) {
			return "";
		}
	}

	//写资源文件，含中文   
    public static void writePropertiesFile(String key,String value)  
    {  
        Properties properties = new Properties();  
        try  
        {  
            OutputStream outputStream = new FileOutputStream(getFilePath());  
            properties.setProperty(key, value);  
          
            properties.store(outputStream, "user register");  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
    }  

    public static boolean isEquals(String key,String value){
    	return readValue(key).equals(value);
    }
    
    public static void main(String[] args) {
		System.out.println(StringUtils.encodeBase64("jincheng#晋城车站电子导向系统"));
	}

}
