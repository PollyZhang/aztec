function add(){
	resetForm();
	$('#roletitle').text('添加角色');
	$('#roleAddOrEdit').modal('show');
}
function edit(id){
	resetForm();
	$('#roletitle').text('编辑角色');
	$('#roleid').val(id);
	$.getJSON(baseUrl+"/role/edit/"+id,function(data){
		if(data.result==1){
			$('#roleName').val(data.role.roleName);
			$('#roleAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
function del(id,name){
	if(confirm("确定要删除角色\""+name+"\"吗?")){
		$('#roleid').val(id);
		$("#roleForm").attr("action",baseUrl+"/role/del/");
		$("#roleForm").submit();
	}
}
function query(){
	$("#roleForm").attr("action",baseUrl+"/role/");
	$("#roleForm").submit();
}
function save(){
	$("#roleForm").attr("action",baseUrl+"/role/save/");
	$("#roleForm").submit();
}
function resetForm(){
	$('#roleid').val("");
	$('#roleName').val("");
}
function configUser(id,name,pageIndex,b){
	$('#btnConfig').unbind("click");
	$.post(baseUrl+"/role/configUserQuery/",{"id":id,pageIndex:pageIndex},function(data){
		if(data.result==1){
			if(data.userRole.length>0){
				var strHtml='<table class="table table-striped table-bordered bootstrap-datatable datatable"><tbody>';
				strHtml+='<thead><tr><th width="30px">选择</th><th>用户账号</th><th>用户名称</th><th>状态</th></tr></thead>';
				for(var x in data.userRole){
					strHtml+='<tr><td><cneter><input type="checkbox" name="'+id+'" value="'+data.userRole[x].id+'" '+(data.userRole[x].userId==null?"":"checked")+'>';
					strHtml+='</center></td><td>'+data.userRole[x].account+'</td><td>'+data.userRole[x].name+'</td>';
					strHtml+='<td><span class=\"label '+(data.userRole[x].status=='正常'?"label-success":"label-warning")+'\">'+data.userRole[x].status+'</span></td></tr>';
				}
				strHtml+="</tbody></table>";
				if(data.pageCount!=1){
					strHtml+='<div class="pagination pagination-centered"><ul>';
					strHtml+='<li><a href="#" onclick="javascript:configUser(\''+id+'\',\''+name+'\',1,false);">首页</a></li>';
					strHtml+='<li><a href="#" onclick="javascript:configUser(\''+id+'\',\''+name+'\','+((data.pageIndex-1)>=1?(data.pageIndex-1):1)+',false);">上一页</a></li>';
					strHtml+='<li><a href="#" onclick="javascript:configUser(\''+id+'\',\''+name+'\','+((data.pageIndex+1)<=data.pageCount?(data.pageIndex+1):data.pageCount)+',false);">下一页</a></li>';
					strHtml+='<li><a href="#" onclick="javascript:configUser(\''+id+'\',\''+name+'\','+data.pageCount+',false);">尾页</a></li></ul></div>';
				}
				$('#roleConfigtitle').text(name+"角色授权用户");
				$('#roleConfigBody').html(strHtml);
				if(b){
					$('#roleConfig').modal('show');
				}
				//$("input:checkbox").uniform();
				$('#btnConfig').bind("click",function(){
					var userArray=new Array();
					var nouserArray=new Array();
					$("[name='"+id+"']").each(function(){
						if($(this).attr("checked")){
							userArray[userArray.length]=$(this).val();
						}else{
							nouserArray[nouserArray.length]=$(this).val();
						}
					});
					if(userArray.length>0 || nouserArray.length>0){
						$.post(baseUrl+"/role/configUserSave",{"id":id,"user":userArray,"nouser":nouserArray,"pageIndex":pageIndex},function(d){
							if(d.result==1){
								noty({"text":d.msg,timeout: 1000,"layout":"center","type":"success"});
							}else{
								noty({"text":d.msg,timeout: 1000,"layout":"center","type":"error"});
							}
						},"json");
					}else{
						noty({"text":"请先选择模块后，再进行授权!",timeout: 1000,"layout":"center","type":"error"});
					}
				});
			}else{
				noty({"text":"请先添加用户后，再进行授权!",timeout: 1000,"layout":"center","type":"error"});
			}
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function configMoudle(id,name){
	$('#btnConfig').unbind("click");
	$.getJSON(baseUrl+"/role/configMoudleQuery/"+id,function(data){
		if(data.result==1){
			if(data.moudleRole.length>0){
				var strHtml='<table class="table table-striped table-bordered bootstrap-datatable datatable"><tbody>';
				strHtml+='<thead><tr><th width="30px">选择</th><th>模块编码</th><th>模块名称</th><th>模块类型</th></tr></thead>';
				for(var x in data.moudleRole){
					strHtml+='<tr><td><cneter><input type="checkbox" name="'+id+'" value="'+data.moudleRole[x].id+'" '+(data.moudleRole[x].moudleId==null?"":"checked")+'>';
					strHtml+='</center></td><td>'+data.moudleRole[x].code+'</td><td>'+data.moudleRole[x].moudleName+'</td>';
					strHtml+='<td>'+(data.moudleRole[x].parentId==null?"<span class=\"btn-info\">父模块</span>":"<span class=\"btn-danger\">子模块</span>")+'</td></tr>';
				}
				strHtml+="</tbody></table>";
				$('#roleConfigtitle').text(name+"角色授权模块");
				$('#roleConfigBody').html(strHtml);
				$('#roleConfig').modal('show');
				//$("input:checkbox").uniform();
				$('#btnConfig').bind("click",function(){
					var modelArray=new Array();
					$("[name='"+id+"']:[checked]").each(function(){
						modelArray[modelArray.length]=$(this).val();
					});
					if(modelArray.length>0 || nomodelArray.length>0){
						$.post(baseUrl+"/role/configMoudleSave",{"id":id,"model":modelArray},function(d){
							if(d.result==1){
								$('#roleConfigBody').html("");
								$('#roleConfig').modal('hide');
								noty({"text":d.msg,timeout: 1000,"layout":"center","type":"success"});
							}else{
								noty({"text":d.msg,timeout: 1000,"layout":"center","type":"error"});
							}
						},"json");
					}else{
						noty({"text":"请先选择模块后，再进行授权!",timeout: 1000,"layout":"center","type":"error"});
					}
				});
			}else{
				noty({"text":"请先添加模块后，再进行授权!",timeout: 1000,"layout":"center","type":"error"});
			}
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}