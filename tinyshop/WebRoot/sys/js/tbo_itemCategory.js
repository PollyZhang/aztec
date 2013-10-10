
var itemCategoryId="";
var itemCategoryName="";
var itemCategoryList=new Array();
function getUrl(treeId, treeNode) {
	if(treeNode!=null && treeNode.id!=null && treeNode.id!=''){
		return baseUrl+"/itemCategory/getItemCategoryJson/"+treeNode.id;
	}else{
		return baseUrl+"/itemCategory/getItemCategoryJson/";
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
;
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
function zTreeOnClick(event, treeId, treeNode) {
	itemCategoryId=treeNode.id;
	itemCategoryName=treeNode.name;
    query();
    $("#itemCategoryTitle").text(itemCategoryName+'栏目列表');
    $("#itemCategoryMain").removeClass("hide");
};
$(function(){
	$("#itemCategoryForm").validationEngine();
	$.fn.zTree.init($("#itemCategoryTree"), setting);
	query();
	expandAll();
	setTimeout(expandAll,600);
});
function expandAll(){
	var zTree = $.fn.zTree.getZTreeObj("itemCategoryTree");
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
	$('#itemCategorytitle').text((itemCategoryName!=''?"“"+itemCategoryName+"”栏目添加子栏目":"添加主栏目"));
	$('#itemCategoryAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	if(itemCategoryList!=null && itemCategoryList.length>0){
		var b=false;
		for(var i in itemCategoryList){
			if(itemCategoryList[i].id==id){
				$("#id").val(id);
				$("#name").val(itemCategoryList[i].name);
				$("#code").val(itemCategoryList[i].code);
				b=true;
				break;
			}
		}
		if(b){
			$('#itemCategorytitle').text((itemCategoryName!=''?"“"+itemCategoryName+"”栏目编辑子栏目":"编辑主栏目"));
			$('#itemCategoryAddOrEdit').modal('show');
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
	if(name==null || name==''){
		noty({"text":"请输入栏目名称!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(code==null || code==''){
		noty({"text":"请输入栏目编码!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	$.post(baseUrl+"/itemCategory/save",{"id":(id!=null && id!=''?id:""),"name":name,"code":code,"pId":itemCategoryId},function(data){
		if(data.result==1){
			var tree=$.fn.zTree.getZTreeObj("itemCategoryTree");
			tree.reAsyncChildNodes(null, "refresh");
			setTimeout(expandAll,600);
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
			query();
			$('#itemCategoryAddOrEdit').modal('hide');
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
	$.post(baseUrl+"/itemCategory/del",{"id":id},function(data){
		if(data.result==1){
			var tree=$.fn.zTree.getZTreeObj("itemCategoryTree");
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
}
function query(){
	$.post(baseUrl+"/itemCategory/query",{"pId":(itemCategoryId!=null && itemCategoryId!=''?itemCategoryId:"")},function(data){
		if(data.result==1){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable">';
			strTable+='<thead><tr class="alert alert-info"><th width="30px">序号</th><th>分类名称</th><th>分类编码</th><th>操作</th></tr></thead>';
			strTable+="<tbody>";
			if(data.itemCategoryList.length>0){
				for(var i in data.itemCategoryList){
					if(data.itemCategoryList[i].isDeleted=='0')
					{
						strTable+="<tr>";
						strTable+="<td><center>"+(parseInt(i)+1)+"</center></td>";
						strTable+="<td>"+data.itemCategoryList[i].name+"</td>";
						strTable+="<td>"+data.itemCategoryList[i].code+"</td>";
						strTable+="<td style=\"width:140px;\"><a class=\"btn btn-mini btn-info\" href=\"#\" onclick=\"javascript:edit('"+data.itemCategoryList[i].id+"');\"><i class=\"icon-edit icon-white\"></i>编辑</a> <a class=\"btn btn-mini btn-danger\" href=\"#\" onclick=\"javascript:del('"+data.itemCategoryList[i].id+"','"+data.itemCategoryList[i].name+"');\"><i class=\"icon-trash icon-white\"></i>删除</a></td>"
						strTable+="</tr>";
					}
					
				}
			}else{
				strTable+="<tr><td colspan='9'><br><br><br><br><br><br><br><center>"+data.msg+"</center><br><br><br><br><br><br></td></tr>";
			}
			itemCategoryList=data.itemCategoryList;
			strTable+='</tbody></table>';
			$("#itemCategoryDiv").html(strTable);
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function mainHide(){
	itemCategoryId="";
	itemCategoryName="";
    query();
    $("#itemCategoryTitle").text('主栏目列表');
    $("#itemCategoryMain").addClass("hide");
}