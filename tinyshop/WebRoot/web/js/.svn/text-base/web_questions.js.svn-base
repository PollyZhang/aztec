function searchQuestions(){
	$("#pageIndex").val(1);
	$("#busForm").attr("action",baseUrl+"/busQuery/");
	$("#busForm").submit();
}

function ask(){
	resetForm();
	$.getJSON(baseUrl+"/questionsQuery/ask/",function(data){
		if(data.result==1){
			//$('#questionsToken').val(data.questionsToken);
			$('#questionsAddOrEdit').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});		
}

function resetForm(){
	$('#title').val("");	
	$("#askContent").val("");
}

function save(){

	$.post(baseUrl+"/questionsQuery/save",$('#questionsForm').serialize(),function(data){
		if(data.result==1){
			$('#questionsAddOrEdit').modal('hide');
			$("#questionsForm").attr("action",baseUrl+"/questionsQuery");
			$("#questionsForm").submit();
		//	 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
		}else{			
			 noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
		$('#questionsToken').val(data.questionsToken);
		
	},'json');
	//$("#questionsForm").attr("action",baseUrl+"/questionsQuery/save");
	//$("#questionsForm").submit();
}

function view(id){

	$.getJSON(baseUrl+"/questionsQuery/lookQuestions/"+id,function(data){
		if(data.result==1){
			$("#lookTitle").text(data.questions.title);
			$("#lookAskContent").text(data.questions.askContent);
			$("#lookAnswerContent").text(data.questions.answerContent==null?"":data.questions.answerContent);
			$("#lookViewTimes").text(data.questions.viewTimes);
			$('#lookQuestionsDiv').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});	
}
