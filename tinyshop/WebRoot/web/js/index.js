

function viewContent(contentId){
	$("#contentId").val(contentId);
	$('#queryForm').attr('action',ctx_path+'/index/contentView');
	$('#queryForm').submit();
}

function viewList(contentCategoryId){
	$("#contentCategoryId").val(contentCategoryId);
	$('#queryForm').attr('action',ctx_path+'/index/contentListView');
	$('#queryForm').submit();
}

function viewHomePage()
{
    window.location.href=ctx_path+"/index"; 
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