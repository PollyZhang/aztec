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
	
	//disbaling some functions for Internet Explorer
	if($.browser.msie)
	{
		$('.login-box').find('.input-large').removeClass('span10');
		$("#code").attr("style","width:149px");
		$("#codeSpan").attr("style","width:60px;height:26px;padding:0px;margin-top:0px");
	}else if($.browser.mozilla){
		$("#code").attr("style","width:82px");
	}
	$("#loginForm").validationEngine();
	
	$("#loginForm").submit(function(){
		var account=$("#account").val();
		var password=$("#password").val();
		var remember=$("#remember");
		$.cookie('account', null,{path:'/'});
		$.cookie('password',null,{path:'/'});
		$.cookie('remember',null,{path:'/'});
		if(remember.attr("checked")=="checked"){
			$.cookie('account',account,{expires:365,path:'/'});
			$.cookie('password',password,{expires:365,path:'/'});
			$.cookie('remember',remember.val(),{expires:365,path:'/'});
		}
	});
	
	var account=$.cookie('account');
	var password=$.cookie('password');
	var remember=$.cookie('remember');
	if(account!=null && account!='' && password!=null && password!="" && noerror){
		$("#account").val(account);
		$("#password").val(password);
		$("#remember").attr("checked",true);
	}else if(remember!=null && remember!=''){
		$("#remember").attr("checked",true);
	}
	//uniform - styler for checkbox, radio and file input
	$("input:checkbox").not('[data-no-uniform="true"]').uniform();

	//tooltip
	$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }});
});

function changeCode(){
	$("#codeImg").attr("src",baseUrl+"/login/generateCode/"+new Date());
}
