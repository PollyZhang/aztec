

function viewContent(contentId){
	window.location.href=ctx_path+"/contentView?contentId="+contentId; 
}

function viewList(contentCategoryId){
	window.location.href=ctx_path+"/contentListView?contentCategoryId="+contentCategoryId; 
}

function viewHome()
{
	window.location.href=ctx_path+"/"; 
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