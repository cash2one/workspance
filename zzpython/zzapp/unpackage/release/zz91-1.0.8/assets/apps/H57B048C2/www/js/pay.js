var pays={};
function payplusReady(formobj){
	// 获取支付通道
	plus.payment.getChannels(function(channels){
		for(var i in channels){
			var channel=channels[i];
			pays[channel.id]=channel;
			checkServices(channel);
			payali(channel.id);
		}
	},function(e){
		submitfrm(formobj,'http://app.zz91.com/zz91pay.html');
		//outLine("获取支付通道失败："+e.message);
	});
}
function startpay(formobj){
	submitfrm(formobj,'http://app.zz91.com/zz91pay.html');
	//payplusReady(formobj)
}
// 检测是否安装支付服务
function checkServices(pc){
	if(!pc.serviceReady){
		var txt=null;
		switch(pc.id){
			case "alipay":
			txt="检测到系统未安装“支付宝快捷支付服务”，无法完成支付操作，是否立即安装？";
			break;
			default:
			txt="系统未安装“"+pc.description+"”服务，无法完成支付，是否立即安装？";
			break;
		}
		plus.nativeUI.confirm(txt,function(e){
			if(e.index==0){
				pc.installService();
			}
		},pc.description);
	}
}

var w=null;
var PAYSERVER='http://demo.dcloud.net.cn/helloh5/payment/alipay.php?total=';
var PAYSERVER='http://app.zz91.com/zz91payrequery.html';
// 支付
function payali(id){
	if(w){return;}//检查是否请求订单中
	w=plus.nativeUI.showWaiting();
	// 请求支付订单
	var amount = "1";
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		switch(xhr.readyState){
			case 4:
			w.close();w=null;
			if(xhr.status==200){
				plus.payment.request(pays[id],xhr.responseText,function(result){
					plus.nativeUI.alert("支付成功：感谢你的支持，我们会继续努力完善产品。",function(){
						back();
					},"捐赠");
				},function(error){
					plus.nativeUI.alert("详情错误信息请参考支付(Payment)规范文档：http://www.html5plus.org/#specification#/specification/Payment.html",null,"支付失败："+error.code);
				});
			}else{
				plus.nativeUI.alert("获取订单信息失败！",null,"捐赠");
			}
			break;
			default:
			break;
		}
	}
	xhr.open('GET',PAYSERVER+amount);
	xhr.send();
}