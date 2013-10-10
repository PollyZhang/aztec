function add(){
	resetForm();
	$('#baseDicttitle').text('添加字典分类');
	$('#baseDictAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	$('#baseDicttitle').text('编辑字典分类');
	$('#baseDictid').val(id);
	$.getJSON(baseUrl+"/baseDict/edit/"+id,function(data){
		
		if(data.result==1){
			$('#categoryId').val(data.baseDict.categoryId);	
			$("#categoryName").val(data.baseDict.categoryName);
			$("#typeName").val(data.baseDict.typeName);
			$('#baseDictAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
function del(id,name){
	if(confirm("确定要删除\""+name+"\"吗?")){
		$('#baseDictid').val(id);
		$("#baseDictForm").attr("action",baseUrl+"/baseDict/del/");
		$("#baseDictForm").submit();
	}
}
function query(){
	$("#baseDictForm").attr("action",baseUrl+"/baseDict/");
	$("#baseDictForm").submit();
}

function save(){
	$("#baseDictForm").attr("action",baseUrl+"/baseDict/save/");
	if($('#baseDictid').val()==null || $('#baseDictid').val()==""){
		$.getJSON(baseUrl+"/baseDict/isExist/"+$("#categoryId").val(),function(data){
			if(data.result==1){
				$("#baseDictForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
	}else{
		$("#baseDictForm").submit();
	}
}


function resetForm(){
	$('#baseDictid').val("");
	$('#categoryId').val("");
	$('#categoryName').val("");	
	$("#typeName").val("");
}