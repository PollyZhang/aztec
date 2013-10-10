function add(){
	resetForm();
	$('#moudletitle').text('添加模块');
	$('#moudleAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	$('#moudletitle').text('编辑模块');
	$('#moudleid').val(id);
	$.getJSON(baseUrl+"/moudle/edit/"+id,function(data){
		if(data.result==1){
			$('#moudleName').val(data.moudle.moudleName);	
			$("#code").val(data.moudle.code);
			$("#grade").val(data.moudle.grade);
			$("#parentId").val(data.moudle.parentId);
			$("#image").val(data.moudle.image);
			$("#url").val(data.moudle.url);
			$("#indexUrl").val(data.moudle.indexUrl);
			$('#moudleAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
function del(id,name){
	if(confirm("确定要删除模块\""+name+"\"吗?")){
		$('#moudleid').val(id);
		$("#moudleForm").attr("action",baseUrl+"/moudle/del/");
		$("#moudleForm").submit();
	}
}
function query(){
	$("#moudleForm").attr("action",baseUrl+"/moudle/");
	$("#moudleForm").submit();
}
function save(){
	$("#moudleForm").attr("action",baseUrl+"/moudle/save/");
	$("#moudleForm").submit();
}
function resetForm(){
	$('#moudleid').val("");
	$('#moudleName').val("");	
	$("#code").val("");
	$("#grade").val("");
	$("#parentId").val("");
	$("#image").val("");
	$("#url").val("");
	$("#indexUrl").val("");
}