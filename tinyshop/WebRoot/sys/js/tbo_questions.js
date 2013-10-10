function query(){
	$("#pageIndex").val(1);
	$("#questionsForm").attr("action",baseUrl+"/questions/");
	$("#questionsForm").submit();
}

function returnQuestions(id){	
		$.getJSON(baseUrl+"/questions/returnQuestion/"+id,function(data){
			$("#questionsToken").val(data.questionsToken);
			
			if(data.result==1){
				$("#questionsid").val(data.questions.id);
				
				$("#vmemberName").text(data.questions.memberName);
				$("#vtitle").text(data.questions.title);
				$("#vaskTime").text(data.questions.askTime);
				$("#vaskContent").text(data.questions.askContent);
				$("#answerContent").val(data.questions.answerContent);
				$('#questionsAddOrEdit').modal('show');
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
		
}


function del(id,title){	
	if(confirm("确定要删除:\""+title+"\"吗?")){
		$.getJSON(baseUrl+"/questions/del/"+id,function(data){
			if(data.result==1){
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
				$("#questionsForm").attr("action",baseUrl+"/questions/");
				$("#questionsForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
	}
	
}

function save(){
	$("#questionsForm").attr("action",baseUrl+"/questions/save");
	$("#questionsForm").submit();
}

function look(id){

	$.getJSON(baseUrl+"/questions/lookQuestions/"+id,function(data){
		if(data.result==1){
			$("#lookTitle").text(data.questions.title);
			$("#lookMemberName").text(data.questions.memberName);
			$("#lookAskContent").text(data.questions.askContent);
			$("#lookAskTime").text(data.questions.askTime==null?"":data.questions.askTime);
			$("#lookAnswerContent").text(data.questions.answerContent);
			$("#lookAnswerTime").text(data.questions.answerTime==null?"":data.questions.answerTime);
			$("#lookViewTimes").text(data.questions.viewTimes);
			$('#lookQuestionsDiv').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});	
}
