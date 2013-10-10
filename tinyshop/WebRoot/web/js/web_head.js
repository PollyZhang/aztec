$(document).ready(function(){
	//themes, change CSS with JS
	//default theme(CSS) is cerulean, change it if needed
	var current_theme = $.cookie('current_theme')==null ? 'cerulean' :$.cookie('current_theme');
	//switch_theme(current_theme);
	
	//$('#themes a[data-value="'+current_theme+'"]').find('i').addClass('icon-ok');
				 
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
			window.location.href=baseUrl+"/webLogin/logout";
		}
	});
	$("#personForm").validationEngine();
	$("#memberForm").validationEngine();
	$("#loginForm").validationEngine();
	
	
	if($.browser.msie){
		$(".span6").attr("style","min-height:18px")
	}else if($.browser.mozilla){
	}
	
});

function person(id){
	if(id==null || id==''){
		return;
	}
	uid=id;
	$.getJSON(baseUrl+"/member/edit/"+id,function(data){
		if(data.result==1){
			$("#personAccount").text(data.member.account);
			$("#personName").val(data.member.name);
			$("input[id='personSex'][value="+data.member.sex+"]").attr('checked',true);
			$("#personIdCard").val(data.member.idCard);
			$("#personTelephone").val(data.member.telephone);
			$("#personEmail").val(data.member.email);
			$("#personLoginSum").text(data.member.loginSum);
			$("#personLastLoginTime").text(data.member.lastLoginTime);
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
	$.post(baseUrl+"/member/personSave",{"id":uid,"personName":personName,"personSex":$("input[name='personSex']:[checked]").val(),"personIdCard":$("#personIdCard").val(),"personTelephone":$("#personTelephone").val(),"personEmail":$("#personEmail").val()},function(data){
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
function personSavePw(){
	;
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
	$.post(baseUrl+"/member/personSave",{"id":uid,"password":pw1,"newpassword":pw2},function(data){
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


function changeCode(){
	$("#codeImg").attr("src",baseUrl+"/webLogin/generateCode/"+new Date());
}

function reset(){
	$("#account").val('');
	$("#password").val('');
	$("#verifycode").val('');
}

function login(){
	$("#loginForm").attr("action",baseUrl+"/webLogin/login/");
	$("#loginForm").submit();
}


function SetHome(obj,url){
	try{
	    obj.style.behavior='url(#default#homepage)';
	    obj.setHomePage(url);
	}catch(e){
	    if(window.netscape){
	        try{
	            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	        }catch(e){
	            alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
	        }
	    }else{
	        alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
	    }
	}
}
	
function AddFavorite(title, url) {
	    try {
	        window.external.addFavorite(url, title);
	    }
	    catch (e) {
	        try {
	            window.sidebar.addPanel(title, url, "");
	        	}
	        catch (e) {
	            alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
	        }
    }
}	