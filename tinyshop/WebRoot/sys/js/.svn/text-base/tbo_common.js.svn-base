Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

String.prototype.formatDate = function(format)
{
	var str=this;
	if(str==null || str=='') return "";
	str=str.split('.')[0];
	return new Date(str.replace(/-/g, "/")).format(format);
}
function validateFile(strName,field,str,fileSize){
	var fileName=field.val();
	if(fileName!=null && fileName!=""){
		var seat=fileName.lastIndexOf(".");
		var extension=fileName.substring(seat).toLowerCase();
		var b=true;
		for(var i in strName){
			if(strName[i]==extension){
				b=false;
				break;
			}
		}
		if(b){
			return "上传的文件类型不对,只能上传"+str+"格式!";
		}
		var fz = 0;          
	    if ($.browser.msie) {      
	          var filePath = fileName;     
	          var fileSystem = null
	          try{
	        	  fileSystem=new ActiveXObject("Scripting.FileSystemObject");  
	          }catch(e){}
	          if(fileSystem!=null){
		          if(!fileSystem.FileExists(filePath)){  
		             return "上传文件不存在，请重新选择！";
		          }  
		          var file = fileSystem.GetFile (filePath);  
		          fz = file.Size;
	          }else{
	        	  //没有访问文件的权限权限将不做校验，（一般为IE设置问题）
	        	  fz=fileSize*1024*1000;
	          }
	    } else {     
	    	fz = document.getElementById(field.attr("id")).files[0].size;   
	    }    
	      
	    var size = fz / 1024/1024;     
	    if(size> fileSize){   
	    	return "上传文件大小不能大于"+fileSize+"M！";
	    }    
	    if(size<=0){  
	        return "上传文件大小不能为0M！";  
	    }
	}
}