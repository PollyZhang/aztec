function register(){
	resetForm();
	$('#membertitle').text('会员注册');
	$('#memberAddOrEdit').modal('show');
}
//function edit(id){
//	resetForm(true);
//	$('#usertitle').text('编辑用户');
//	$('#userid').val(id);
//	$.getJSON(baseUrl+"/user/edit/"+id,function(data){
//		if(data.result==1){
//			$("#account").val(data.user.account);
//			$("#name").val(data.user.name);
//			$("#accountType").val(data.user.accountType);
//			$("input[name='user.sex'][value="+data.user.sex+"]").attr('checked',true);
//			$("#age").val(data.user.age);
//			$("#telephone").val(data.user.telephone);
//			$("#email").val(data.user.email);
//			$("#status").val(data.user.status);
//			$("#loginSum").val(data.user.loginSum);
//			$("#lastLoginTime").val(data.user.lastLoginTime);
//			$("#createTime").val(data.user.createTime);
//			$('#userAddOrEdit').modal('show');
//		}else{
//			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
//		}
//	});
//}
//function del(id,name){
//	if(confirm("确定要删除用户\""+name+"\"吗?")){
//		$('#userid').val(id);
//		$("#userForm").attr("action",baseUrl+"/user/del/");
//		$("#userForm").submit();
//	}
//}
//function look(id){
//	$.getJSON(baseUrl+"/user/edit/"+id,function(data){
//		if(data.result==1){
//			$("#accountLook").text(data.user.account);
//			$("#nameLook").text(data.user.name);
//			if(data.user.accountType=='emp'){
//				$("#accountTypeLook").text("普通用户");
//			}else if(data.user.accountType=='mana'){
//				$("#accountTypeLook").text("管理员");
//			}else{
//				$("#accountTypeLook").text("超级管理员");
//			}
//			$("#sexLook").text(data.user.sex==null?"":data.user.sex);
//			$("#ageLook").text(data.user.age==null?"":data.user.age);
//			$("#telephoneLook").text(data.user.telephone==null?"":data.user.telephone);
//			$("#emailLook").text(data.user.email==null?"":data.user.email);
//			$("#statusLook").html("<span class=\"label "+(data.user.status=='正常'?"label-success":"label-important")+"\">"+data.user.status+"</span>");
//			$("#loginSumLook").text(data.user.loginSum==null?"0":data.user.loginSum);
//			$("#lastLoginTimeLook").text(data.user.lastLoginTime==null?"":data.user.lastLoginTime);
//			$("#createTimeLook").text(data.user.createTime==null?"":data.user.createTime);
//			$('#userLook').modal('show');
//		}else{
//			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
//		}
//	});	
//}
//function query(){
//	$("#pageIndex").val(1);
//	$("#userForm").attr("action",baseUrl+"/user/");
//	$("#userForm").submit();
//}

function save(){
	$("#memberForm").attr("action",baseUrl+"/member/save/");
		$.getJSON(baseUrl+"/member/isExist/"+$("#account").val(),function(data){
			if(data.result==1){
				$("#memberForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
}
function resetForm(){
	$("#account").val("");
	$("#name").val("");
	$("#password").val("");
	$("#password2").val("");
	$("input[name='user.sex'][value=男]").attr('checked',true);
	$("#telephone").val("");
	$("#idCard").val("");
	$("#email").val("");
}

//function change(id,name,status){
//	if(confirm("确定要"+status+"用户\""+name+"\"吗?")){
//		$("#userForm").attr("action",baseUrl+"/user/changeStatuts/"+id);
//		$("#userForm").submit();
//	}
//}