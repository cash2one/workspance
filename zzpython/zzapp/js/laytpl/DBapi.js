var lastpage=100;
function loadCommlisttplData(url, pageTpl, pagelist, pageaction,datavalue) {
	if (pageaction == "pageup") {
		page--;
	}
	if (pageaction == "pagedown") {
		page++;
	}
	
	if (page < 1) {
		page = 1;
		return
	}
	if (page > lastpage) {
		page = lastpage;
	}
	window.scrollTo(0, 0);
	var nWaiting;
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting("数据加载中，请稍候......");
	}
	var arg = defaultqueryString;
	arg['page']=page;
	if (datavalue){arg=$.extend({}, arg,datavalue);}
	mui.ajax(zzweburl + url, {
		data: arg,
		dataType: 'json', //服务器返回json格式数据
		type: 'get', //HTTP请求类型
		success: function(data) {
			var pageHtml = "";
			var ret = data;
			if (ret.error_code == 0) {
				$.each(ret.result, function(index, item) {
					var getTpl = document.getElementById(pageTpl).innerHTML;
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				})
				var pages = document.getElementById("pages");
				if (pages) {
					pages.innerHTML = page;
				}
				if (page >= ret.lastpage) {
					lastpage=ret.lastpage;
					page = ret.lastpage;
				}
				document.getElementById(pagelist).innerHTML = pageHtml;
				var listcount=ret.listcount;
				var listcountdiv = document.getElementById("listcountdiv");
				if (listcountdiv){listcountdiv.innerHTML = listcount;}
				if (nWaiting) {
					nWaiting.close();
				}
				hidbj()
			} else {
				mui.toast('网络错误,请检查网络是否正常！');
			}
			if (nWaiting) {
				nWaiting.close();
			}
		},
		error: function(xhr, type, errorThrown) {
			//异常处理；
			mui.toast('网络错误,请检查网络是否正常！');
			if (nWaiting) {
				nWaiting.close();
			}
		}
	});
}
function hidbj(){
	var list=$(".supply-btn")
	for (var i=0;i<=list.length;i++){
		var thi=list[i];
		if (thi){
			if (thi.title=="交易完成"){
				thi.style.display="none";
				thi.parentNode.innerHTML="停止交易"
			}
		}
	}
}
//读取本地数据
function getcommtplData(url,querytring,localhtml,tplid){
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting("数据加载中，请稍候......");
	}
	
	if(url){
		mui.get(url,querytring,function(data){
			document.getElementById('tplcontent').innerHTML=localhtml;
			var tplcontent = document.getElementById('tplcontent').innerHTML;
			laytpl(tplcontent).render(data, function(html){
			    $("#"+tplid).html(html);
			});
//			$("#tplcontent").html(localhtml);
//			var tplcontent=document.getElementById("tplcontent").innerHTML;
//			laytpl(tplcontent).render(data, function(html){
//			    $("#"+tplid).html(html);
//			});
			if (nWaiting) {
				nWaiting.close();
			}
		},"json");
	}else{
		$("#tplcontent").html(localhtml);
		var tplcontent=document.getElementById("tplcontent").innerHTML;
		laytpl(tplcontent).render("{'aa':1}", function(html){
		    $("#"+tplid).html(html);
		});
		if (nWaiting) {
			nWaiting.close();
		}
	}
	if (nWaiting) {
		nWaiting.close();
	}
}
function getindexData(url,datavalue){
	if (window.plus) {
		nWaiting = plus.nativeUI.showWaiting("数据加载中，请稍候......");
	}
	mui.ajax(url,{
		data:datavalue,
		dataType: 'json', //服务器返回json格式数据
		type: 'get', //HTTP请求类型
		success: function(data){
		var pageHtml="";
		$.each(data.pricelist.list, function(index, item) {
			var getTpl = document.getElementById("pricelistTpl").innerHTML;
			laytpl(getTpl).render(item, function(html) {
				pageHtml = pageHtml + html;
			});
		})
		document.getElementById("pricelist").innerHTML = pageHtml;
//		pageHtml="";
//		if (data.productlist.list){
//			$.each(data.productlist.list, function(index, item) {
//				var getTpl = document.getElementById("productlistTpl").innerHTML;
//				laytpl(getTpl).render(item, function(html) {
//					pageHtml = pageHtml + html;
//				});
//			})
//			document.getElementById("productlist").innerHTML = pageHtml;
//		}
		var ad1_1Tpl = document.getElementById("ad1_1Tpl").innerHTML;
		laytpl(ad1_1Tpl).render(data.ad1_1, function(html){
		    $("#ad1_1").html(html);
		});
		
		var ad1_2Tpl = document.getElementById("ad1_2Tpl").innerHTML;
		laytpl(ad1_2Tpl).render(data.ad1_2, function(html){
		    $("#ad1_2").html(html);
		});
		
		var ad1_3Tpl = document.getElementById("ad1_3Tpl").innerHTML;
		laytpl(ad1_3Tpl).render(data.ad1_3, function(html){
		    $("#ad1_3").html(html);
		});
		
		var ad1_4Tpl = document.getElementById("ad1_4Tpl").innerHTML;
		laytpl(ad1_4Tpl).render(data.ad1_4, function(html){
		    $("#ad1_4").html(html);
		});
		
		var ad1_5Tpl = document.getElementById("ad1_5Tpl").innerHTML;
		laytpl(ad1_5Tpl).render(data.ad1_5, function(html){
		    $("#ad1_5").html(html);
		});
		
		var ad1_6Tpl = document.getElementById("ad1_6Tpl").innerHTML;
		laytpl(ad1_6Tpl).render(data.ad1_6, function(html){
		    $("#ad1_6").html(html);
		});
		
		if (data.ad2_1){
			$("#meiriqiangou").css("display","block");
			var ad2_1Tpl = document.getElementById("ad2_1Tpl").innerHTML;
			laytpl(ad2_1Tpl).render(data.ad2_1, function(html){
			    $("#ad2_1").html(html);
			});
		}else{
			$("#meiriqiangou").css("display","none");
		}
		
		
		var ad3Tpl = document.getElementById("ad3Tpl").innerHTML;
		laytpl(ad3Tpl).render(data.ad3, function(html){
		    $("#ad3").html(html);
		});
		if (data.adlist){
			//$("#adlist").css("display","block");
//			var adlist_Tpl = document.getElementById("adlist_Tpl").innerHTML;
//			laytpl(adlist_Tpl).render(data.adlist, function(html){
//				if (html){$("#adlist").html(html);}
//			});
		}else{
			$("#adlist").css("display","none");
		}
		if (data.adlistall){
			pageHtml="";
			$("#adlistall").css("display","block");
			$.each(data.adlistall, function(index, item) {
				var getTpl = document.getElementById("adlistall_Tpl").innerHTML;
				laytpl(getTpl).render(item, function(html) {
					pageHtml = pageHtml + html;
				});
			})
			document.getElementById("adlistall").innerHTML = pageHtml+pageHtml;
		}else{
			$("#adlistall").css("display","none");
		}
		if (data.qiandao){
			$("#qiandao").css("display","none");
		}else{
			$("#qiandao").css("display","block");
		}
		if (nWaiting) {
			nWaiting.close();
		}
		var slider = mui("#slider");
		slider.slider({interval: 5000});
	},
	error: function(xhr, type, errorThrown) {
		mui.toast('网络错误,请检查网络是否正常！');
	}
	});
	if (nWaiting) {
		nWaiting.close();
	}
}
