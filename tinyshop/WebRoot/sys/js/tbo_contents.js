var verifyContentsId;
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
    $('#contentCategoryId').val(treeNode.id);
    query();
    $('#contentCategoryName').val(treeNode.name);
    $("#contentsTitleDiv").text(treeNode.name+'内容列表');
};
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
	$('#id').val('');
	var contentCategoryId=$('#contentCategoryId').val();
	if(contentCategoryId==null || contentCategoryId==''){
		 noty({"text":"请先选择栏目,再进行添加操作！",timeout: 1000,"layout":"center","type":"error"});
		 return;
	}
	$('#contentsQueryForm').attr('action',baseUrl+'/contents/add');
	$('#contentsQueryForm').submit();
}

function edit(id,contentCategoryId){
	$('#id').val(id);
	$('#contentCategoryId').val(contentCategoryId);
	$('#contentsQueryForm').attr('action',baseUrl+'/contents/edit');
	$('#contentsQueryForm').submit();
}

function verifySave(status,statusName){
	if(!verifyContentsId) return;
	var reviewReason=$('#reviewReason').val();
	
	var dataParam={'id':verifyContentsId,'reviewReason':reviewReason?reviewReason:'','status':status};
	jQuery.ajax({
		 url: baseUrl+"/contents/verifySave/",
		 type: "POST",
		 dataType:"json",
		 data:dataParam,
		 success: function(data)
		 {
			 noty({"text":statusName,timeout: 1000,"layout":"center","type":"success"});
			 query();
		 },
		 error:function(){
			 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		 }
		 });
}


function verifyMsg(data){
	if(data.id){
		verifyContentsId=data.id;
		var strTable="<table class='table table-striped table-bordered bootstrap-datatable datatable'>";
		strTable+="<tr><td style='width:30px;'>标题</td><td>"+(data.title?data.title:'')+"</td></tr>";
		strTable+="<tr><td>副标</br>题</td><td>"+(data.subTitle?data.subTitle:'')+"</td></tr>";
		strTable+="<tr><td>类型</td><td>"+(data.contentType?data.contentType:'')+"</td></tr>";
		strTable+="<tr><td>所属</br>栏目</td><td>"+(data.contentCategoryId?data.contentCategoryId:'')+"</td></tr>";
		strTable+="<tr><td>来源</td><td>"+(data.source?data.source:'')+"</td></tr>";
		strTable+="<tr><td>排序</td><td>"+(data.creator?data.order:'')+"</td></tr>";
		strTable+="<tr><td>发布</br>时间</td><td>"+data.createTime.formatDate('yyyy-MM-dd HH:mm:ss')+"</td></tr>";
		strTable+="<tr><td>摘要</td><td>"+(data.summary?data.summary:'')+"</td></tr>";
		strTable+="<tr><td>内容</td><td>"+(data.contents?data.contents:'')+"</td></tr>";
		strTable+="<tr><td>审核结果</br>(限500字)</td><td><textarea  id='reviewReason' name='reviewReason' style='width: 650px; height: 60px;'>"+(data.reviewReason?data.reviewReason:'审核通过')+"</textarea></td></tr>";
		strTable+="</table>";
		$('#contentsVerifyListDIV').html(strTable);
	}
} 

function verify(id,status,statusName){
	$('#contentsVerifyListDIV').html("");
	var dataParam={"id":id};
	jQuery.ajax({
		 url: baseUrl+"/contents/verify/",
		 type: "POST",
		 dataType:"json",
		 data:dataParam,
		 success: function(data)
		 {
			if(data.result==0){
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
				return;
			}	
			 verifyMsg(data);
			$('cTitle').val("内容"+statusName);
			$('#contentsVerifyDiv').modal('show');
		 },
		 error:function(){
			 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		 }
		 });
}

function goNext(id,status,statusName){	
	if(confirm("确定要把此文档\""+statusName+"\"吗?")){
		var dataParam={"id":id,"status":status};
		jQuery.ajax({
			 url: baseUrl+"/contents/goNext/",
			 type: "POST",
			 dataType:"json",
			 data:dataParam,
			 success: function(data)
			 {
				if(data.result==0){
					noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
					return;
				}else{
					query();
				}	
			 },
			 error:function(){
				 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			 }
			 });
		}
}


function del(id,name){
	if(id==null || id==''){
		noty({"text":"数据异常!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(confirm("确定要删除内容\""+name+"\"吗?")){
		jQuery.ajax({
			 url: baseUrl+"/contents/del/"+id,
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
	var contentCategoryId=$('#contentCategoryId').val();
	var dataParam={"contentCategoryId":(contentCategoryId!=null && contentCategoryId!=''?contentCategoryId:""),
	"titleQuery":$('#titleQuery').val(),
	"contentTypeQuery":$('#contentTypeQuery').val(),
	"statusQuery":$('#statusQuery').val(),
	"startNewsDateQuery":$('#startNewsDateQuery').val(),
	"endNewsDateQuery":$('#endNewsDateQuery').val(),
	"pageIndex":($('#pageIndex').val()!=null && $('#pageIndex').val()!='')?$('#pageIndex').val():"1"
	};
	var pageNumber=0,totalPage=0,totalRow=0;
	
	$.post(baseUrl+"/contents/query",dataParam,function(data){
		if(data){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable ellipsisTable">';
			strTable+='<thead><tr class="alert alert-info"><th width="25px">序号</th><th width="120px">标题</th><th width="25px">类型</th><th width="25px">置顶</th><th width="25px">分享</th><th width="25px">浏览次数</th><th width="50px">排序</th><th width="45px">发布人</th><th width="55px">发布日期</th><th width="100px;">操作</th></tr></thead>';
			strTable+="<tbody>";
			
			if(data.list && data.list.length>0){
				$.each(data.list,function(n,value) {
					strTable+="<tr>";
					strTable+="<td><center>"+(n+1)+"</center></td>";
					strTable+="<td>"+value.title+"</td>";
					strTable+="<td>"+value.contentType+"</td>";
					
					strTable+="<td>"+((value.isTop==1)?'是':'否')+"</td>";
					strTable+="<td>"+((value.isShared==1)?'是':'否')+"</td>";
					strTable+="<td>"+(value.browseSum?value.browseSum:0)+"</td>";
					strTable+="<td>"+(value.order?value.order:'')+"</td>";
					strTable+="<td >"+(value.creator?value.creator:'')+"</td>";
					strTable+="<td>"+ (value.newsDate?value.newsDate:'')+"</td>";
					strTable+="<td>";
					
					strTable+="<a class='btn btn-mini btn-info' href='#' onclick=\"javascript:edit('"+value.id+"','"+value.contentCategoryId+"');\">";
					strTable+="<i class='icon icon-edit icon-white'></i>编辑</a>&nbsp;";
					strTable+="<a class='btn btn-mini btn-danger' href='#' onclick=\"javascript:del('"+value.id+"','"+value.title+"');\">";
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
			$("#contentCategoryDiv").html(strTable);
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
	$('#titleQuery').val("");
	$('#contentTypeQuery').val("");	
	$("#statusQuery").val("");
	$("#startNewsDateQuery").val("");
	$("#endNewsDateQuery").val("");
}

$(document).ready(function() {
	      $( "#startNewsDateQuery" ).datepicker({
	            defaultDate: "+1w",
	            changeMonth: true,
	            numberOfMonths: 1,
	            onClose: function( selectedDate ) {
	                $( "#endNewsDateQuery" ).datepicker( "option", "minDate", selectedDate );
	            }
	        });
	        $( "#endNewsDateQuery" ).datepicker({
	            defaultDate: "+1w",
	            changeMonth: true,
	            numberOfMonths: 1,
	            onClose: function( selectedDate ) {
	                $( "#startNewsDateQuery" ).datepicker( "option", "maxDate", selectedDate );
	            }
	        });
	        
	  });