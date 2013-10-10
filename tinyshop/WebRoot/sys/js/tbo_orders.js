function query(){
	$("#pageIndex").val(1);
	$("#ordersForm").attr("action",baseUrl+"/orders/");
	$("#ordersForm").submit();
}

function cancelOrders(id,dingDanHao){
	if(confirm("确定要撤销此订单:\""+dingDanHao+"\"吗?")){
		$.getJSON(baseUrl+"/orders/cancelOrder/"+id,function(data){
			if(data.result==1){
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"success"});
				//$("#pageIndex").val(1);
				$("#ordersForm").attr("action",baseUrl+"/orders/");
				$("#ordersForm").submit();
			}else{
				noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
			}
		});
		
	}
}

function lookOrder(id){
	$.getJSON(baseUrl+"/orders/lookOrder/"+id,function(data){
		if(data.result==1){
			$("#lookDingDanHao").text(data.order.DingDanHao);
			$("#lookCheCi").text(data.order.CheCi);
			$("#lookXianLuMingCheng").text(data.order.XianLuMingCheng);
			$("#lookZhongDianZhan").text(data.order.ZhongDianZhan);
			$("#lookXiaKeZhan").text(data.order.XiaKeZhan);
			$("#lookFaCheRiQi").text(data.order.FaCheRiQi);
			$("#lookFaCheShiJian").text(data.order.FaCheShiJian);
			$("#lookPiaoShu").text(data.order.PiaoShu);
			$("#lookPiaoJia").text(data.order.PiaoJia);
			$("#lookZuoHao").text(data.order.ZuoHao);
			$("#lookGouPiaoRenDengLuMing").text(data.order.GouPiaoRenDengLuMing);
			$("#lookGouPiaoRenXingMing").text(data.order.GouPiaoRenXingMing);
			$("#lookQuPiaoRenXingMing").text(data.order.QuPiaoRenXingMing==null?"":data.order.QuPiaoRenXingMing);
			$("#lookShenFenZhengHao").text(data.order.ShenFenZhengHao==null?"":data.order.ShenFenZhengHao);
			$("#lookQuPiaoRiQi").text(data.order.QuPiaoRiQi==null?"":data.order.QuPiaoRiQi);
			$("#lookQuPiaoCaoZuoYuan").text(data.order.QuPiaoCaoZuoYuan==null?"":data.order.QuPiaoCaoZuoYuan);
			$("#lookJinZhanKou").text(data.order.JinZhanKou);
			$("#lookFaCheQu").text(data.order.FaCheQu);
			$("#lookZhuangTai").text(data.order.ZhuangTai);
			$("#lookShouPiaoRiQiShiJian").text(data.order.ShouPiaoRiQiShiJian);
			$('#ordersLook').modal('show');
		}else{
			noty({"text":data.msg,timeout: 1000,"layout":"center","type":"error"});
		}
	});	
}
//
//
//function resetForm(){
//	$("#accountQuery").val('');
//	$("#startOccurtTimeQuery").val('');
//	$("#endOccurtTimeQuery").val('');
//	$("#typeQuery").val('');
//}