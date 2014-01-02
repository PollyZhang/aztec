var verifyContentsId;

function add(){
	$('#id').val('');
	$('#advQueryForm').attr('action',baseUrl+'/adv/add');
	$('#advQueryForm').submit();
}

function edit(id){
	$('#id').val(id);
	$('#advQueryForm').attr('action',baseUrl+'/adv/edit');
	$('#advQueryForm').submit();
}
function del(id,name){
	if(id==null || id==''){
		noty({"text":"数据异常!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(confirm("确定要删除内容\""+name+"\"吗?")){
		jQuery.ajax({
			 url: baseUrl+"/adv/del/"+id,
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
	var dataParam={"nameQuery":$('#nameQuery').val(),
	"pageIndex":($('#pageIndex').val()!=null && $('#pageIndex').val()!='')?$('#pageIndex').val():"1"
	};
	var pageNumber=0,totalPage=0,totalRow=0;
	
	$.post(baseUrl+"/adv/query",dataParam,function(data){
		if(data){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable ellipsisTable">';
			strTable+='<thead><tr class="alert alert-info"><th width="25px">序号</th><th width="180px">名称</th><th width="45px">图片</th><th width="25px">编码</th><th width="25px">排序</th><th width="155px">描述</th><th width="80px;">操作</th></tr></thead>';
			strTable+="<tbody>";
			
			if(data.list && data.list.length>0){
				$.each(data.list,function(n,value) {
					strTable+="<tr>";
					strTable+="<td><center>"+(n+1)+"</center></td>";
					strTable+="<td>"+value.name+"</td>";
					strTable+="<td><a href='"+ctx_path+"/upload"+value.imgUrl+"' target=_blank>点击查看</a></td>";
					strTable+="<td>"+value.code+"</td>";
					strTable+="<td>"+value.order+"</td>";
					strTable+="<td>"+value.descrip+"</td>";
					strTable+="<td>";
					strTable+="<a class='btn btn-mini btn-info' href='#' onclick=\"javascript:edit('"+value.id+"');\">";
					strTable+="<i class='icon icon-edit icon-white'></i>编辑</a>&nbsp;";
					strTable+="<a class='btn btn-mini btn-danger' href='#' onclick=\"javascript:del('"+value.id+"','"+value.name+"');\">";
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
			$("#advDiv").html(strTable);
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
}
