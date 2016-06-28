// JavaScript Document
hb={};//申明用于命名空间注册
hb.register= function(fname){
	var nArray = fname.split('.');//分置方法
	var tfn = '';
	var feval= '';
	
	for(var i= 0; i< nArray.length;i++){
		if(i!=0){tfn += '.';}
		tfn += nArray[i];
		feval += "if (typeof("+tfn+") == 'undefined'){" + tfn + "={};}";
	}
	//alert(feval);
	if(feval!=''){eval(feval)}
}
hb.register('hb.util');//huanbao通用方法
hb.util.topBar={};//topbar 动作封装

hb.util.topBar.HoverList = function(){
	var liHover= $('#hb_bar_navls>li')||{};
	var viewLsStatus = false;
	//hb.util.msg.Error({m:"commonError"});
	//alert(a);
	
	liHover
	.mouseover(function(){
		if(!viewLsStatus){
			$(this).attr({'class':'ishover'})
			$(this).find('#hb_bar_nav_block').slideDown(300);
			var viewLsStatus=true;
		}
	})
	.mouseleave(function(){
		if(!viewLsStatus){
			$(this).attr({'class':''})
			$(this).find('#hb_bar_nav_block').slideUp(100);
			var viewLsStatus=false;
		}
	});
};
