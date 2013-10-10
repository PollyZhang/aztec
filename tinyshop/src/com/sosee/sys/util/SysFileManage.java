package com.sosee.sys.util;

import java.io.File;
import java.util.UUID;

/**
 * 
 *作者：强乐
 *功能：上传文件管理
 * 2012-12-10 下午3:00:49
 */
public class SysFileManage {
	
	public static final String FILE_IMAGE="image";//图片
	public static final String FILE_VIDEO="video";//视频
	public static final String FILE_ATTACH="attach";//其他文件	
	private static final String imageSuffix="gif,png,jpg,bmp";//图片文件允许后缀
	private static final String videoSuffix="3gp,rm,rmvb,mp4";//视频文件允许后缀
	private static final String attachSuffix="txt,rar,zip,doc,docx";//其他文件允许后缀
	
	/**
	 * 修改文件属性(随机文件名,默认:“其他文件”)
	 * @param 需要修改的文件
	 * @return 返回文件保存路径(返回空或者null，说明保存失败)
	 */
	public static String setFileProperties(File file){
		return setFileProperties(file,FILE_ATTACH);
	}
	
	/**
	 * 修改文件属性(随机文件名)
	 * @param 需要修改的文件
	 * @param 修改文件的类型
	 * @return 返回文件保存路径(返回空或者null，说明保存失败)
	 */
	public static String setFileProperties(File file,String fileType){
		String url="";
		try{
			fileType=fileType.equals(FILE_IMAGE) || fileType.equals(FILE_VIDEO) || fileType.equals(FILE_ATTACH)?fileType:FILE_ATTACH;
			if(file!=null){
				String fileSuffix="txt";
				File fileParent=null;
				File renamefile=null;
				String[] fileNames=file.getName().split("\\.");
				if(fileNames.length>1){
					fileSuffix=fileNames[fileNames.length-1];
				}
				do{
					fileParent=new File(file.getParent()+"\\"+fileType);
					renamefile=new File(file.getParent()+"\\"+fileType+"\\"+getFileName()+"."+fileSuffix);
				}while(renamefile.exists());
				if(!fileParent.exists()){
					fileParent.mkdirs();
				}
				file.renameTo(renamefile);
				url=renamefile.getPath().replace("upload", "@").split("@")[1].replace("\\\\", "\\").replace("\\", "/");
			}
		}catch(Exception e){}
		return url;
	}
	
	/**
	 * 批量修改文件属性(随机文件名,同一种类型)
	 * @param 需要修改的文件数组
	 * @param 修改文件的类型
	 * @return 返回文件保存路径数组(返回空或者null，说明保存失败)
	 */
	public static String[] setFileProperties(File[] files,String fileType){
		String[] strUrl=null;
		if(files!=null && files.length>0){
			strUrl=new String[files.length];
			for(int i=0;i<files.length;i++){
				strUrl[i]=setFileProperties(files[i],FILE_ATTACH);
			}
		}
		return strUrl;
	}
	
	/**
	 * 批量修改文件属性(随机文件名,多种类型)
	 * @param 需要修改的文件数组
	 * @param 修改文件的类型数组
	 * @return 返回文件保存路径数组(返回空或者null，说明保存失败)
	 */
	public static String[] setFileProperties(File[] files,String[] fileType){
		String[] strUrl=null;
		if(files!=null && files.length>0){
			strUrl=new String[files.length];
			for(int i=0;i<files.length;i++){
				strUrl[i]=setFileProperties(files[i],fileType[i]);
			}
		}
		return strUrl;
	}
	
	/**
	 * 判断文件大小和后缀名是否在允许的类型里边
	 * @param 文件
	 * @param 文件类型
	 * @param 允许文件大小
	 * @return 是否验证通过
	 */
	public static boolean isSizeOrSuffix(File file,String fileType,int fileSize){
		boolean b=false;
		fileType=fileType.equals(FILE_IMAGE) || fileType.equals(FILE_VIDEO) || fileType.equals(FILE_ATTACH)?fileType:FILE_ATTACH;
		String[] fileSuffixs=fileType.equals(FILE_IMAGE)?imageSuffix.split(","):fileType.equals(FILE_VIDEO)?videoSuffix.split(","):attachSuffix.split(",");
		String fileSuffix="";
		String[] fileNames=file.getName().split("\\.");
		if(fileNames.length>1){
			fileSuffix=fileNames[fileNames.length-1];
		}
		if(file!=null){
			if(fileSuffix!=""){
				for(String s : fileSuffixs){
					if(s.equals(fileSuffix) && file.length()>0 && file.length()<fileSize*1024*1024){
						b=true;
						break;
					}
				}
			}
		}
		return b;
	}
	
	private static String getFileName(){
		return UUID.randomUUID().toString();
	}
}
