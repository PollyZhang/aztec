var verifyContentsId;
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
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}
function zTreeOnClick(event, treeId, treeNode) {
	$('#itemCategoryId').val(treeNode.id);
    query();
    $("#itemCategoryTitle").text(itemCategoryName+'栏目列表');
    $("#itemCategoryMain").removeClass("hide");
};
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
	$('#id').val('');
	var itemCategoryId=$('#itemCategoryId').val();
	if(itemCategoryId==null || itemCategoryId==''){
		 noty({"text":"请先选择商品类别,再进行添加操作！",timeout: 1000,"layout":"center","type":"error"});
		 return;
	}
	$('#itemsQueryForm').attr('action',baseUrl+'/items/add');
	$('#itemsQueryForm').submit();
}

function edit(id,itemCategoryId){
	$('#id').val(id);
	$('#itemCategoryId').val(itemCategoryId);
	$('#itemsQueryForm').attr('action',baseUrl+'/items/edit');
	$('#itemsQueryForm').submit();
}




function del(id,name){
	if(id==null || id==''){
		noty({"text":"数据异常!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(confirm("确定要删除内容\""+name+"\"吗?")){
		jQuery.ajax({
			 url: baseUrl+"/items/del/"+id,
			 type: "POST",
			 success: function(data)
			 {
				 query();
				 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
			 },
			 error:function(){
				 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			 }
			 });
	}
}
function query(){
	var itemCategoryId=$('#itemCategoryId').val();
	var dataParam={"itemCategoryId":(itemCategoryId!=null && itemCategoryId!=''?itemCategoryId:""),
	"nameQuery":$('#nameQuery').val(),
	"sizeQuery":$('#sizeQuery').val(),
	"colorQuery":$('#colorQuery').val(),
	"pageIndex":($('#pageIndex').val()!=null && $('#pageIndex').val()!='')?$('#pageIndex').val():"1"
	};
	var pageNumber=0,totalPage=0,totalRow=0;
	
	$.post(baseUrl+"/items/query",dataParam,function(data){
		if(data){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable ellipsisTable">';
			strTable+='<thead><tr class="alert alert-info"><th width="25px">序号</th><th width="80px">名称</th><th width="25px">价格</th><th width="25px">数量</th><th width="25px">售出</th><th width="160px;">操作</th></tr></thead>';
			strTable+="<tbody>";
			if(data.list && data.list.length>0){
				$.each(data.list,function(n,value) {
					strTable+="<tr>";
					strTable+="<td><center>"+(n+1)+"</center></td>";
					strTable+="<td>"+value.itemName+"</td>";
					strTable+="<td>"+value.itemPrice+"</td>";
					strTable+="<td>"+value.count+"</td>";
					strTable+="<td>"+value.salesVolume+"</td>";
					strTable+="</td>";
					strTable+="<td>";
					strTable+="<a class='btn btn-mini btn-info' href='#' onclick=\"javascript:edit('"+value.id+"','"+value.itemCategoryId+"');\">";
					strTable+="<i class='icon icon-edit icon-white'></i>编辑</a>&nbsp;";
					strTable+="<a class='btn btn-mini btn-danger' href='#' onclick=\"javascript:del('"+value.id+"','"+value.itemName+"');\">";
					strTable+="<i class='icon icon-white icon-trash'></i>删除</a>";
					strTable+="</td></tr>";
				});
			   
				pageNumber=data.pageNumber;
				totalPage=data.totalPage;
				totalRow=data.totalRow;
			}
			strTable+='</tbody></table>';
			
			strTable+='<div style="float:left;">';
			strTable+='总记录数:'+totalRow+'条  当前'+pageNumber+'页/共'+totalPage+'页';
			strTable+='</div>';
			strTable+='<div class="pagination pagination-right">';
			strTable+='<ul>';
			strTable+='<li><a href="#" onclick="javascript:doPage(1,'+pageNumber+','+totalPage+');">首页</a></li>';
			strTable+='<li><a href="#" onclick="javascript:doPage('+(pageNumber - 1)+','+pageNumber+','+totalPage+');">上一页</a></li>';
			strTable+='<li><a href="#" onclick="javascript:doPage('+(pageNumber + 1)+','+pageNumber+','+totalPage+');">下一页</a></li>';
			strTable+='<li><a href="#" onclick="javascript:doPage('+totalPage+','+pageNumber+','+totalPage+');">尾页</a></li>';
			strTable+='</ul>';
			strTable+='</div>';
			$("#itemsDiv").html(strTable);
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}

function doPage(pageNum,pageIndex,pageCount){
	if(pageNum>pageCount){
		pageNum=pageCount==0?1:pageCount;
	}else if(pageNum<1){
		pageNum=1;
	}
	if(pageIndex!=pageNum){
		$("#pageIndex").val(pageNum);
		query();
	}
}


function resetQuery(){
	$('#nameQuery').val("");
	$('#sizeQuery').val("");	
	$("#colorQuery").val("");
}
