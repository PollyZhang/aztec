function query(){
	$("#pageIndex").val(1);
	$("#logForm").attr("action",baseUrl+"/log/");
	$("#logForm").submit();
}

function del(){
	if(confirm("确定要删除选择的记录吗?")){
		$("#logForm").attr("action",baseUrl+"/log/del/");
		$("#logForm").submit();
	}
}

function delAll(id,name){
	if(confirm("确定要清空当前记录吗?")){
		$("#logForm").attr("action",baseUrl+"/log/delAll/");
		$("#logForm").submit();
	}
}

function reset(){
	$("#accountQuery").val('');
	$("#startOccurtTimeQuery").val('');
	$("#endOccurtTimeQuery").val('');
	$("#typeQuery").val('');
}