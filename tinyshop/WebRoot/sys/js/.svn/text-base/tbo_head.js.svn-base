$(document).ready(function(){
	//themes, change CSS with JS
	//default theme(CSS) is cerulean, change it if needed
	var current_theme = $.cookie('current_theme')==null ? 'cerulean' :$.cookie('current_theme');
	switch_theme(current_theme);
	
	$('#themes a[data-value="'+current_theme+'"]').find('i').addClass('icon-ok');
				 
	$('#themes a').click(function(e){
		e.preventDefault();
		current_theme=$(this).attr('data-value');
		$.cookie('current_theme',null,{path:'/'});
		$.cookie('current_theme',current_theme,{expires:365,path:'/'});
		switch_theme(current_theme);
		$('#themes i').removeClass('icon-ok');
		$(this).find('i').addClass('icon-ok');
	});
	
	function switch_theme(theme_name)
	{
		$('#bs-css').attr('href',baseUrl+'/sys/css/bootstrap-'+theme_name+'.css');
	}
	
	//highlight current / active link
	$('ul.main-menu li a').each(function(){
		if($($(this))[0].href==String(window.location))
			$(this).parent().addClass('active');
	});
	
	//animating menus on hover
	$('ul.main-menu li:not(.nav-header)').hover(function(){
		$(this).animate({'margin-left':'+=5'},300);
	},
	function(){
		$(this).animate({'margin-left':'-=5'},300);
	});
	
	$('#logout').click(function(e){
		if(confirm("确定要退出系统?")){
			window.location.href=baseUrl+"/login/logout";
		}
	});
	$("#personForm").validationEngine();
});

function person(id){
	if(id==null || id==''){
		return;
	}
	uid=id;
	$.getJSON(baseUrl+"/user/edit/"+id,function(data){
		if(data.result==1){
			$("#personAccount").text(data.user.account);
			$("#personName").val(data.user.name);
			if(data.user.accountType=='emp'){
				$("#personAccountType").text("普通用户");
			}else if(data.user.accountType=='mana'){
				$("#personAccountType").text("管理员");
			}else{
				$("#personAccountType").text("超级管理员");
			}
			$("input[name='personSex'][value="+data.user.sex+"]").attr('checked',true);
			$("#personAge").val(data.user.age);
			$("#personTelephone").val(data.user.telephone);
			$("#personEmail").val(data.user.email);
			$("#personLoginSum").text(data.user.loginSum);
			$("#personLastLoginTime").text(data.user.lastLoginTime);
			$('#personLook').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});
}
var uid="";
function personSave(){
	
	var personName=$("#personName").val();
	if(uid==null || uid==""){
		return;
	}
	if(personName==null || personName=="" || personName.length<3 || personName.length>20){
		noty({"text":"用户名称不能为空,且必须在3-20位之间!",timeout: 1000,"layout":"center","type":"error"});
		return;
	}
	$.post(baseUrl+"/user/personSave",{"id":uid,"personName":personName,"personSex":$("input[name='personSex']:[checked]").val(),"personAge":$("#personAge").val(),"personTelephone":$("#personTelephone").val(),"personEmail":$("#personEmail").val()},function(data){
		if(data.result==1){
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}
function personPw(id){
	if(id==null || id==''){
		return;
	}
	uid=id;
	$('#personPw').modal('show');
}
function personSavePw(id){
	var pw1=$('#pw1').val();
	var pw2=$('#pw2').val();
	var pw3=$('#pw3').val();
	if(uid==null || uid==""){
		return;
	}
	if(pw1==null || pw1==''){
		return;
	}
	if(pw2==null || pw2==''){
		return;
	}
	if(pw3==null || pw3==''){
		return;
	}
	if(pw2!=pw3){
		return;
	}
	$.post(baseUrl+"/user/personSave",{"id":uid,"password":pw1,"newpassword":pw2},function(data){
		if(data.result==1){
			$('#pw1').val("");
			$('#pw2').val("");
			$('#pw3').val("");
			$('#personPw').modal('hide');	
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	},"json");
}