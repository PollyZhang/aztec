function add(){
	resetForm();
	$('#baseCodetitle').text('添加字典');
	$('#baseCodeAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	$('#baseCodetitle').text('编辑字典');
	$('#baseCodeid').val(id);
	$.getJSON(baseUrl+"/baseCode/edit/"+id,function(data){
		if(data.result==1){
			$('#categoryId').val(data.baseCode.categoryId);	
			$('#name').val(data.baseCode.name);	
			$("#code").val(data.baseCode.code);
			$("#orderNum").val(data.baseCode.orderNum);
			$('#baseCodeAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}

function del(id,name){
	if(confirm("确定要删除字典\""+name+"\"吗?")){
		jQuery.ajax({
			 url: baseUrl+"/baseCode/del/"+id,
			 type: "POST",
			 success: function(data)
			 {
				 queryTable(data)
				 noty({"text":"删除成功!",timeout: 1000,"layout":"center","type":"success"});
			 },
			 error:function(){
				 noty({"text":"删除失败!",timeout: 1000,"layout":"center","type":"error"});
			 }
			 });
	}
}

function save(){
	var queryStr={'baseCodeToken':$('#baseCodeToken').val(),'baseCodeid':$('#baseCodeid').val(),'categoryId':$('#categoryId').val(),'code':$('#code').val(),'name':$('#name').val(),'orderNum':$('#orderNum').val()};
    
	$.post(baseUrl+"/baseCode/save",$('#baseCodeForm').serialize(),function(data){
		if(data!=null && data.data!=null && data.data.length>0){
			 noty({"text":"保存成功!",timeout: 1000,"layout":"center","type":"success"});
			queryTable(data.data);
			$('#baseCodeAddOrEdit').modal('hide');
		}else{			
			 noty({"text":data.errorinfo,timeout: 1000,"layout":"center","type":"error"});
		}
		$('#baseCodeToken').val(data.baseCodeToken);
		
	},'json');
	
		
}

function resetForm(){
	$('#baseCodeid').val("");
	$('#name').val("");	
	$("#code").val("");
	$("#orderNum").val("");
}

function queryTable(data){
	 var strHtml='<table class="table table-striped table-bordered bootstrap-datatable datatable"><tbody>';
		strHtml+='<thead><tr class="alert alert-info"><th width="30px">序号</th><th>字典编码</th><th>名称</th><th>排列序号</th><th>操作</th></tr></thead>';

	if(data!=null && data.length>0){
			 $.each(data,function(n,value) {
			 strHtml+="<tr><td><center>"+ (n+1) +"</center></td>";
			 strHtml+="<td>"+value.code+"</td>";
			 strHtml+="<td>"+value.name+"</td>";
			 strHtml+="<td>"+value.orderNum+"</td>";
			 strHtml+="<td><a class='btn btn-mini btn-info' href='#' onclick=javascript:edit('"+value.id+"');>";
			 strHtml+="<i class='icon-edit icon-white'></i>编辑</a>";  
			 strHtml+="&nbsp;<a class='btn btn-mini btn-danger' href='#' onclick=javascript:del('"+value.id+"','"+value.name+"');>"
			 strHtml+="<i class='icon-trash icon-white'></i>删除</a></td>";
			 strHtml+="</tr>";
	      });
	 
	}else{
		noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
	}
	strHtml+="</tbody></table>";
	 $('#dictListDiv').html(strHtml);	
}

function queryList(url){
	 $('#categoryId').val(url.substring(url.lastIndexOf("/")+1));
	 $.getJSON(url,function(data){
		 if(data.data!=null && data.data.length>0){
			queryTable(data.data);
		}else{
			 noty({"text":data.errorinfo,timeout: 1000,"layout":"center","type":"error"});
		}
		$('#baseCodeToken').val(data.baseCodeToken);
		
	   });

}