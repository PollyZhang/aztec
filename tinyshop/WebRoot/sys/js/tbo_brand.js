var verifyContentsId;

function add(){
	$('#id').val('');
	$('#brandQueryForm').attr('action',baseUrl+'/brand/add');
	$('#brandQueryForm').submit();
}

function edit(id){
	$('#id').val(id);
	$('#brandQueryForm').attr('action',baseUrl+'/brand/edit');
	$('#brandQueryForm').submit();
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
		strTable+="<tr><td>发布人</td><td>"+(data.creator?data.creator:'')+"</td></tr>";
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


function del(id,name){
	if(id==null || id==''){
		noty({"text":"数据异常!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	if(confirm("确定要删除内容\""+name+"\"吗?")){
		jQuery.ajax({
			 url: baseUrl+"/brand/del/"+id,
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
	
	$.post(baseUrl+"/brand/query",dataParam,function(data){
		if(data){
			var strTable='<table class="table table-striped table-bordered bootstrap-datatable datatable ellipsisTable">';
			strTable+='<thead><tr class="alert alert-info"><th width="25px">序号</th><th width="80px">名称</th><th width="25px">图片</th><th width="25px">描述</th><th width="160px;">操作</th></tr></thead>';
			strTable+="<tbody>";
			
			if(data.list && data.list.length>0){
				$.each(data.list,function(n,value) {
					strTable+="<tr>";
					strTable+="<td><center>"+(n+1)+"</center></td>";
					strTable+="<td>"+value.name+"</td>";
					
					strTable+="<td><img src="+ctx_path+"/upload"+value.imgUrl+" ;width:200px;height:50px;></td>";
					strTable+="<td>"+value.details+"</td>";
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
			$("#brandDiv").html(strTable);
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