// JavaScript Document
// gaofei603@gmail.com   2011-4-8
// helpcenter step tabs
$(function(){
	//确定当前内容显示
	$(".xszn_zcdl_con > .xszn_zcdl_tab").eq($(".xszn_zcdl_tit li").index(this)).slideDown();
	$(".xszn_zcdl_tit li").click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
		$(".xszn_zcdl_con > .xszn_zcdl_tab").eq($(".xszn_zcdl_tit li").index(this)).fadeIn().siblings().hide();
	});
});	