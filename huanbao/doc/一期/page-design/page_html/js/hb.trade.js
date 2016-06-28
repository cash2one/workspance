// JavaScript Document

$(document).ready(function(e) {
	new tdlist.sortsViewMore();
	
	//new serverboxtab()
});



//统一命名空间
var tdindex={},//交易中心首页
	tdlist={};//交易中心列表头
	
	
    tdlist.sortsViewMore = function(e){
		var btn  =  $(".hb_tradelist_l_sorts_s span")||{},
			l_smorebtn = $("#l_smorebtn")||{},
			hb_tradelist_l_more=$(".hb_tradelist_l_more")||{};
		
		btn.click(function(){
			 if($(this).parent().height()<$(this).parent().find("ul").height()){
				$(this).parent().css({"height":$(this).parent().find("ul").height()+"px"}).attr({"class":"hb_tradelist_l_sorts_s l_sorts_selected"});
				$(this).html("隐藏").attr({"class":"l_sorts_showhidebtn l_sorts_btnshow"})
			}else{
				$(this).parent().css({"height":$(this).css('line-height')}).attr({"class":"hb_tradelist_l_sorts_s l_sorts_notselected"});
				$(this).html("更多").attr({"class":"l_sorts_showhidebtn l_sorts_btnhide"})
			}
	   })
	   
	   
	   var e=1
	   
	   l_smorebtn.click(function(){
		   		hb_tradelist_l_more.slideToggle(200, function(){e++});
				//alert(e)	
				if(e%2==0){
					$(this).html("显示更多");
				}else{
					$(this).html("隐藏更多");
				}
		   })
	}
	

tdindex.serverboxtab = function(e){
		var btn =  $(".server_box_nav span");
		//alert(btn)
		btn.click(function(){
				//alert($(this).attr('class'))
				$(this).attr({'class':$(this).attr('class')+'_s'});
				
			})
	}