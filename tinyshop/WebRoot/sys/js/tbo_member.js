function add(){
	resetForm(false);
	$('#membertitle').text('添加会员');
	$('#memberAddOrEdit').modal('show');
}
function edit(id){
	resetForm(true);
	$('#membertitle').text('编辑会员');
	$('#memberid').val(id);
	$.getJSON(baseUrl+"/member/edit/"+id,function(data){
		if(data.result==1){
			$("#account").val(data.member.account);
			$("#name").val(data.member.name);
			$("#accountType").val(data.member.accountType);
			$("input[name='member.sex'][value="+data.member.sex+"]").attr('checked',true);
			$("#age").val(data.member.age);
			$("#telephone").val(data.member.telephone);
			$("#email").val(data.member.email);
			$("#status").val(data.member.status);
			$("#loginSum").val(data.member.loginSum);
			$("#lastLoginTime").val(data.member.lastLoginTime);
			$("#createTime").val(data.member.createTime);
			$('#memberAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
function del(id,name){
	if(confirm("确定要删除会员\""+name+"\"吗?")){
		$('#memberid').val(id);
		$("#memberForm").attr("action",baseUrl+"/member/del/");
		$("#memberForm").submit();
	}
}
function look(id){
	$.getJSON(baseUrl+"/member/edit/"+id,function(data){
		if(data.result==1){
			$("#accountLook").text(data.member.account);
			$("#nameLook").text(data.member.name);
			if(data.member.accountType=='normal'){
				$("#accountTypeLook").text("普通会员");
			}else{
				$("#accountTypeLook").text("vip");
			}
			$("#sexLook").text(data.member.sex==null?"":data.member.sex);
//			$("#ageLook").text(data.member.age==null?"":data.member.age);
			$("#idCardLook").text(data.member.idCard==null?"":data.member.idCard);
			$("#telephoneLook").text(data.member.telephone==null?"":data.member.telephone);
			$("#emailLook").text(data.member.email==null?"":data.member.email);
			$("#statusLook").html("<span class=\"label "+(data.member.status=='正常'?"label-success":"label-important")+"\">"+data.member.status+"</span>");
			$("#loginSumLook").text(data.member.loginSum==null?"0":data.member.loginSum);
			$("#lastLoginTimeLook").text(data.member.lastLoginTime==null?"":data.member.lastLoginTime);
			$("#createTimeLook").text(data.member.createTime==null?"":data.member.createTime);
			$('#memberLook').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});	
}
function query(){
	$("#pageIndex").val(1);
	$("#memberForm").attr("action",baseUrl+"/member/");
	$("#memberForm").submit();
}
function save(){
	$("#memberForm").attr("action",baseUrl+"/member/save/");
	if($('#memberid').val()==null || $('#memberid').val()==""){
		$.getJSON(baseUrl+"/member/isExist/"+$("#account").val(),function(data){
			if(data.result==1){
				$("#memberForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
	}else{
		$("#memberForm").submit();
	}
}
function resetForm(b){
	$('#memberid').val("");	
	$("#account").val("");
	$("#name").val("");
	$("#accountType").val("");
	$("input[name='member.sex'][value=男]").attr('checked',true);
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
	if(confirm("确定要"+status+"会员\""+name+"\"吗?")){
		$("#memberForm").attr("action",baseUrl+"/member/changeStatuts/"+id);
		$("#memberForm").submit();
	}
}