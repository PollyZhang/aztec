function add(){
	resetForm(false);
	$('#usertitle').text('添加用户');
	$('#userAddOrEdit').modal('show');
}
function edit(id){
	resetForm(true);
	$('#usertitle').text('编辑用户');
	$('#userid').val(id);
	$.getJSON(baseUrl+"/user/edit/"+id,function(data){
		if(data.result==1){
			$("#account").val(data.user.account);
			$("#name").val(data.user.name);
			$("#accountType").val(data.user.accountType);
			$("input[name='user.sex'][value="+data.user.sex+"]").attr('checked',true);
			$("#age").val(data.user.age);
			$("#telephone").val(data.user.telephone);
			$("#email").val(data.user.email);
			$("#status").val(data.user.status);
			$("#loginSum").val(data.user.loginSum);
			$("#lastLoginTime").val(data.user.lastLoginTime);
			$("#createTime").val(data.user.createTime);
			$('#userAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
function del(id,name){
	if(confirm("确定要删除用户\""+name+"\"吗?")){
		$('#userid').val(id);
		$("#userForm").attr("action",baseUrl+"/user/del/");
		$("#userForm").submit();
	}
}
function look(id){
	$.getJSON(baseUrl+"/user/look/"+id,function(data){
		if(data.result==1){
			$("#accountLook").text(data.user.account);
			$("#nameLook").text(data.user.name);
		   
		    if(data.user.accountType=='emp'){
				$("#accountTypeLook").text("普通用户");
			}else if(data.user.accountType=='mana'){
				$("#accountTypeLook").text("管理员");
			}else{
				$("#accountTypeLook").text("超级管理员");
			}
			
			$("#sexLook").text(data.user.sex==null?"":data.user.sex);
			
			$("#ageLook").text(data.user.age==null?"":data.user.age);
			$("#telephoneLook").text(data.user.telephone==null?"":data.user.telephone);
			$("#emailLook").text(data.user.email==null?"":data.user.email);
			$("#statusLook").html("<span class=\"label "+(data.user.status=='正常'?"label-success":"label-important")+"\">"+data.user.status+"</span>");
			$("#loginSumLook").text(data.user.loginSum==null?"0":data.user.loginSum);
			$("#lastLoginTimeLook").text(data.user.lastLoginTime==null?"":data.user.lastLoginTime);
			$("#createTimeLook").text(data.user.createTime==null?"":data.user.createTime);
			$('#userLook').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});	
}
function query(){
	$("#pageIndex").val(1);
	$("#userForm").attr("action",baseUrl+"/user/");
	$("#userForm").submit();
}
function save(){
	$("#userForm").attr("action",baseUrl+"/user/save/");
	if($('#userid').val()==null || $('#userid').val()==""){
		$.getJSON(baseUrl+"/user/isExist/"+$("#account").val(),function(data){
			if(data.result==1){
				$("#userForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
	}else{
		$("#userForm").submit();
	}
}
function resetForm(b){
	$('#userid').val("");	
	$("#account").val("");
	$("#name").val("");
	$("#accountType").val("");
	$("input[name='user.sex'][value='SE01']").attr('checked',true);
	$("#age").val("");
	$("#telephone").val("");
	$("#email").val("");
	$("#status").val("");
	$("#loginSum").val("");
	$("#lastLoginTime").val("");
	$("#createTime").val("");
	if(b){
		$("#account").attr("readonly",true); 
		$("#account").addClass("uneditable-input");
	}else{
		$("#account").removeAttr("readonly");
		$("#account").removeClass("uneditable-input");
	}
}
function change(id,name,status){
	if(confirm("确定要"+status+"用户\""+name+"\"吗?")){
		$("#userForm").attr("action",baseUrl+"/user/changeStatuts/"+id);
		$("#userForm").submit();
	}
}