var contentCategoryId="";
var contentCategoryName="";
var contentCategoryList=new Array();
function getUrl(treeId, treeNode) {
	if(treeNode!=null && treeNode.id!=null && treeNode.id!=''){
		return baseUrl+"/contentCategory/getContentCategoryJson/"+treeNode.id;
	}else{
		return baseUrl+"/contentCategory/getContentCategoryJson/";
	}
}
var setting = {
	async: {
		enable: true,
		url:getUrl,
		autoParam:["id"],
		dataFilter: filter
	},
	callback: {
		onClick: zTreeOnClick
	}
};
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
function zTreeOnClick(event, treeId, treeNode) {
	contentCategoryId=treeNode.id;
	contentCategoryName=treeNode.name;
    query();
    $("#contentCategoryTitle").text(contentCategoryName+'栏目列表');
    $("#contentCategoryMain").removeClass("hide");
};
$(function(){
	$("#contentCategoryForm").validationEngine();
	$.fn.zTree.init($("#contentCategoryTree"), setting);
	query();
	expandAll();
	setTimeout(expandAll,600);
});
function expandAll(){
	var zTree = $.fn.zTree.getZTreeObj("contentCategoryTree");
	$("._ico_docu").removeClass("button");
	expandNodes(zTree,zTree.getNodes());
}
function expandNodes(zTree,nodes){
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(zTree,nodes[i].children);
		} else {
			zTree.expandAll(true);
		}
	}
}
function add(){
	resetForm();
	$('#contentCategorytitle').text((contentCategoryName!=''?"“"+contentCategoryName+"”栏目添加子栏目":"添加主栏目"));
	$('#contentCategoryAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	
	if(contentCategoryList!=null && contentCategoryList.length>0){
		var b=false;
		for(var i in contentCategoryList){
			if(contentCategoryList[i].id==id){
				$("#id").val(id);
				$("#name").val(contentCategoryList[i].name);
				$("#code").val(contentCategoryList[i].code);
				$("#shortName").val(contentCategoryList[i].shortName);
				$("#url").val(contentCategoryList[i].url);
				$("#imageFile").val(contentCategoryList[i].imageFile);
				$("#comments").val(contentCategoryList[i].comments);
				b=true;
				break;
			}
		}
		if(b){
			$('#contentCategorytitle').text((contentCategoryName!=''?"“"+contentCategoryName+"”栏目编辑子栏目":"编辑主栏目"));
			$('#contentCategoryAddOrEdit').modal('show');
		}else{
			noty({"text":"编辑出错!",timeout: 1000,"layout":"center","type":"error"});			
		}
	}else{
		noty({"text":"编辑出错!",timeout: 1000,"layout":"center","type":"error"});
	}
}
function save(){
	var id=$("#id").val();
	var name=$("#name").val();
	var code=$("#code").val();
	var shortName=$("#shortName").val();
	var url=$("#url").val();
	var imageFile=$("#imageFile").val();
	var comments=$("#comments").val();
	if(name==null || name==''){
		noty({"text":"请输入栏目名称!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(code==null || code==''){
		noty({"text":"请输入栏目编码!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	$.post(baseUrl+"/contentCategory/save",{"id":(id!=null && id!=''?id:""),"name":name,"code":code,"shortName":shortName,"url":url,"imageFile":imageFile,"comments":comments,"code":code,"pId":contentCategoryId},function(data){
		if(data.result==1){
			var tree=$.fn.zTree.getZTreeObj("contentCategoryTree");
			tree.reAsyncChildNodes(null, "refresh");
			setTimeout(expandAll,600);
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
			query();
			$('#contentCategoryAddOrEdit').modal('hide');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function del(id,name){
	if(id==null || id==''){
		noty({"text":"数据异常!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(!confirm("确定要删除栏目“"+name+"”（删除后将删除其所有子栏目和相关联的数据）?")){
		return;
	}
	$.post(baseUrl+"/contentCategory/del",{"id":id},function(data){
		if(data.result==1){
			var tree=$.fn.zTree.getZTreeObj("contentCategoryTree");
			tree.reAsyncChildNodes(null, "refresh");
			setTimeout(expandAll,600);
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
			query();
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function resetForm(){
	$("#id").val("");
	$("#name").val("");
	$("#code").val("");
	$("#shortName").val("");
	$("#url").val("");
	$("#imageFile").val("");
	$("#comments").val("");
}
function query(){
	$.post(baseUrl+"/contentCategory/query",{"pId":(contentCategoryId!=null && contentCategoryId!=''?contentCategoryId:"")},function(data){
		if(data.result==1){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable">';
			strTable+='<thead><tr class="alert alert-info"><th width="30px">序号</th><th>栏目名称</th><th>栏目编码</th><th>栏目简称</th><th>栏目地址</th><th>栏目图标</th><th>栏目备注</th><th>操作</th></tr></thead>';
			strTable+="<tbody>";
			if(data.contentCategoryList.length>0){
				for(var i in data.contentCategoryList){
					strTable+="<tr>";
					strTable+="<td><center>"+(parseInt(i)+1)+"</center></td>";
					strTable+="<td>"+data.contentCategoryList[i].name+"</td>";
					strTable+="<td>"+data.contentCategoryList[i].code+"</td>";
					strTable+="<td>"+data.contentCategoryList[i].shortName+"</td>";
					strTable+="<td>"+data.contentCategoryList[i].url+"</td>";
					strTable+="<td>"+data.contentCategoryList[i].imageFile+"</td>";
					strTable+="<td>"+data.contentCategoryList[i].comments+"</td>";
					strTable+="<td style=\"width:140px;\"><a class=\"btn btn-mini btn-info\" href=\"#\" onclick=\"javascript:edit('"+data.contentCategoryList[i].id+"');\"><i class=\"icon-edit icon-white\"></i>编辑</a> <a class=\"btn btn-mini btn-danger\" href=\"#\" onclick=\"javascript:del('"+data.contentCategoryList[i].id+"','"+data.contentCategoryList[i].name+"');\"><i class=\"icon-trash icon-white\"></i>删除</a></td>";
					strTable+="</tr>";
				}
			}else{
				strTable+="<tr><td colspan='9'><br><br><br><br><br><br><br><center>"+data.msg+"</center><br><br><br><br><br><br></td></tr>";
			}
			contentCategoryList=data.contentCategoryList;
			strTable+='</tbody></table>';
			$("#contentCategoryDiv").html(strTable);
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function mainHide(){
	contentCategoryId="";
	contentCategoryName="";
    query();
    $("#contentCategoryTitle").text('主栏目列表');
    $("#contentCategoryMain").addClass("hide");
}