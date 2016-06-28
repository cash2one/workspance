function moveup1(obj,obj2)
{
	if (obj2.style.display=="")
	{
	//alert (obj.style.className)
	for (i=1;i<=8;i++)
	{
	var listmenu="list_menu_"+i+"_list";
	document.getElementById(listmenu).style.display="";
	var listmenudiv="menu_item_"+i+"_div"
    document.getElementById(listmenudiv).className="main_sec_right"
	}
	//objlen=obj2.id.substr(4,1);
	//var listmenunow="list_menu_"+objlen+"_list";
	//document.getElementById(listmenunow).style.display="none";
	
	obj2.style.display="none";
	obj.className="list_menu_more_1"
	}else{
	//alert (obj.style.className)
	for (i=1;i<=8;i++)
	{
	var listmenu="list_menu_"+i+"_list";
	document.getElementById(listmenu).style.display="none";
	var listmenudiv="menu_item_"+i+"_div"
    document.getElementById(listmenudiv).className="main_sec_right_1"
	}
	objlen=obj2.id.substr(4,1);
	var listmenunow="list_menu_"+objlen+"_list";
	document.getElementById(listmenunow).style.display="";
	
	obj.className="list_menu_more_2"
	obj2.style.display="";
	}
}
function show_div(obj,showdiv)
{
//alert (obj.className)
if (obj.className=="main_sec_right")
{
obj.className="main_sec_right_1"
showdiv.style.display="none"
}else
{
obj.className="main_sec_right"
showdiv.style.display=""
}
}
n=1
function doclick(obj,id)
{
document.getElementById("node1").className="ntab2"
document.getElementById("node2").className="ntab2"
document.getElementById("node3").className="ntab2"
document.getElementById("node11").className="ntab2"
document.getElementById("node12").className="ntab2"
        switch (parseInt(id))
		{
		case 1:
		//alert (id)
		zz91search.action="/cn/offerlist_search.asp"
		zz91search.otype.value="1"
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		
		zz91search.searchname.value="请输入您感兴趣的产品名称！"
		}
		break;
		case 2:
		//alert (id)
		zz91search.action="/cn/offerlist_search.asp"
		zz91search.otype.value="2"
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		
		zz91search.searchname.value="请输入您感兴趣的产品名称！"
		}
		break;
		case 3:
		//alert (id)
		zz91search.action="/cn/baojia_search.asp"
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		
		zz91search.searchname.value="请输入您感兴趣的产品名称！"
		}
		break;
		case 11:
		//alert (id)
		zz91search.action="/cn/trade_search.asp"
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		
		zz91search.searchname.value="请输入您感兴趣的关键词！如：“废料”，“环保证”"
		}
		break;
		case 12:
		//alert (id)
		zz91search.action="/cn/company_search.asp"
		//zz91search.otype.value="5"
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		
		zz91search.searchname.value="请输入您感兴趣的产品名或公司名关键词！"
		}
		break;
		default:
		//alert (id)
		//document.all.search_text.value="请输入您感兴趣的产品名称！"
		}
obj.className="ntab4"
n=id
}
function getsearch(obj)
	{
		if (obj.value=="" || obj.value=="请输入您感兴趣的产品名称！" || obj.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || obj.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
		obj.value=""
		}else
		{
		
		}
	}
	function searchsub()
	{
		if (zz91search.searchname.value=="" || zz91search.searchname.value=="请输入您感兴趣的产品名称！" || zz91search.searchname.value=="请输入您感兴趣的关键词！如：“废料”，“环保证”" || zz91search.searchname.value=="请输入您感兴趣的产品名或公司名关键词！")
		{
			alert ("请输入关键字！")
			zz91search.searchname.focus()
			return false
		}
	}
function overtab(obj)
{

	if (obj.className=="ntab4")
	{
		obj.className="ntab4"
	}
	if (obj.className=="ntab2")
	{
		obj.className="ntab3"
	}
}
function outtab(obj)
{
	if (obj.className=="ntab3")
	{
		obj.className="ntab2"
	}
	if (obj.className=="ntab4")
	{
		obj.className="ntab4"
	}
}
function aliclick(u, param) {
    d = new Date();
    if(document.images) {
        (new Image()).src="http://www.zz91.com" + param + "&time=" + d.getTime();
    }
    return true;
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}