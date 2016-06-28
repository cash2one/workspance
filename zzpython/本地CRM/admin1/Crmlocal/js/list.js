// JavaScript Document

function postAll(form,promptText,value)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要的信息！")
	return false
	}
	else
	{
	  if (confirm(promptText))
	  {
	  form.dostay.value=value
	  form.submit()
	  }
	}
	
  }

function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		   e.checked = form.cball.checked;
	}
}

//控制公用函数
function selectcontrolComon(form,dotypevalue)
{
    selectcb="0"
    for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		{
		   if (e.checked==true)
		   {
			  var selectcb=selectcb+","+e.value
		   }
		}
	}
	form.selectcb.value=selectcb.substr(2)
	//----------------
	if (selectcb=="0")
	{
		alert ("选择你要操作的信息！")
		return false
	}
	else
	{
	  switch (dotypevalue)
	  {
		  case "assignTotemp":
			  var confirmvalue="确实要放到临时库吗?";
			  break;
		  case "cancerAssignTotemp":
		  	  var confirmvalue="确实要在临时库中删除该客户吗?";
			  break;
		  
	  }
	  if (confirm(confirmvalue))
	  {
		  form.dostay.value=dotypevalue
		  form.submit()
	  }
	}
}
function selectopenzst(form)
{
	selectcb="0"
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		if (e.checked==true)
		{
			var selectcb=selectcb+","+e.value
		}
	}
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
		alert ("选择你要开通的公司！")
		return false
	}
	else
	{
		if (confirm("开通这些公司吗?"))
		{
			  form.dostay.value="waitOpenZST";
			  form.target="_blank";
			  form.submit()
		}
	}
}
function selectsendsms(form)
{
	selectcb="0";
	selectmobile="0";
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		if (e.checked==true)
		{
			
			var objmoble=document.getElementById("smobile"+e.value);
			if (objmoble.value!="")
			{
				var selectcb=selectcb+","+e.value;
				var selectmobile=selectmobile+","+objmoble.value;
			}
		}
	}
	selectsmssend.companyIds.value=selectcb.substr(2);
	selectsmssend.mobiles.value=selectmobile.substr(2);
	if (selectcb=="0")
	{
		alert ("选择你要发送短信的公司！");
		return false;
	}
	else
	{
		selectsmssend.submit();
	}
}
  function selectclosevip(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要关闭的VIP客户！")
	return false
	}
	else
	{
	  if (confirm("关闭的这些VIP试用客户吗?"))
	  {
	  window.open ("http://www.zz91.com/admin1/compinfo/cominfo_closevip.asp?selectcb="+selectcb.substr(2),"_a","width=500,height=500")
	  //form.dostay.value="openzst"
	  //form.submit()
	  }
	}
  }
function selectcrm(form)
{
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("分配这些信息吗?"))
	  {
		  form.dostay.value="selec1tcrm"
		  form.submit()
	  }
	}
  }
function delselectcrm(form,n)
{
    selectcb="0"
    for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
		alert ("选择你要分配的信息！")
		return false
	}
	else
	{
	  if(n==0)
	  {
		  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
		  {
			  form.dostay.value="delselec1tcrm"
			  form.submit()
		  }
	  }else
	  {
		  DropToSea(form,n)
	  }
	}
}
function DropToSea(form,n)
{
	if (n==1)
	{
		selectcb="0"
		for (var i=0;i<form.elements.length;i++)
		{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		   if (e.checked==true)
		   {
		   var selectcb=selectcb+","+e.value
		   }
		}
		selectcb=selectcb.substr(2)
		document.getElementById("form2").action="crm_assign_save.asp?selectcb="+selectcb+"&dostay=delselec1tcrm"
		document.getElementById("alerttile").innerHTML="请填写放入公海的原因！"
		openPageCover()
	}
}
function outselectcrm (form)
  {
   selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
	  {
	  form.dostay.value="outselec1tcrm"
	  form.submit()
	  }
	}
  }
function outselectcrmtt (form)
  {
   selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
	  {
	  form.dostay.value="outselec1tcrmtt"
	  form.submit()
	  }
	}
  }
 function selectmycrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的客户里”吗?"))
	  {
	  form.dostay.value="selec1tmycrm"
	  form.submit()
	  }
	}
  }
  function zhongdian(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的重点客户里”吗?"))
	  {
	  form.dostay.value="zhongdian"
	  form.submit()
	  }
	}
  }
  function zhongdianout(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户取消“重点客户”吗?"))
	  {
	  form.dostay.value="zhongdianout"
	  form.submit()
	  }
	}
  }
  function zhongdiansh(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将审核该“重点客户”吗?"))
	  {
	  form.dostay.value="zhongdiansh"
	  form.submit()
	  }
	}
  }
  function zhongdianshno(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将取消审核该“重点客户”吗?"))
	  {
	  form.dostay.value="zhongdianshno"
	  form.submit()
	  }
	}
  }
  function selectoutmycrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的客户表”里吗?"))
	  {
	  form.dostay.value="selec1toutmycrm"
	  form.submit()
	  }
	}
  }
  function putInAd(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的广告客户库”里吗?"))
	  {
	  form.dostay.value="putInAd"
	  form.submit()
	  }
	}
  }
  function shenheluru(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要审核的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要审核该客户吗?"))
	  {
	  form.dostay.value="shenheluru"
	  form.submit()
	  }
	}
  }
  function selectdelcom(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“待删除区”里吗?"))
	  {
	  form.dostay.value="selec1tdelcom"
	  form.submit()
	  }
	}
  }
   function selectwastecom(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“废品池”里吗?"))
	  {
	  form.dostay.value="selec1twastecom"
	  form.submit()
	  }
	}
  }
  function outselectcrmTozst(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要安排的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放“余姚办事处”联系吗?"))
	  {
	  form.dostay.value="selec1tToyuyao"
	  form.submit()
	  }
	}
  }
   function inselectcrmToshare(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要放入的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放“共享客户”吗?"))
	  {
	  form.dostay.value="selec1tToshare"
	  form.submit()
	  }
	}
  }
   function outselectcrmToshare(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要放入的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要取消该客户为“共享客户”吗?"))
	  {
	  form.dostay.value="outselec1tToshare"
	  form.submit()
	  }
	}
  }
  function outselectcrmPin(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要放入的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要取消该客户为“”吗?"))
	  {
	  form.dostay.value="outselectcrmPin"
	  form.submit()
	  }
	}
  }
  function IntoContinueCrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true){
	   	var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0"){
		alert ("选择你要放入的客户！")
		return false
	}else{
	  if (confirm("确实放到我的续签客户库吗?"))
	  {
		  form.dostay.value="IntoContinueCrm"
		  form.submit()
	  }
	}
  }
  function ZNoContinueCrm(form,n)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true){
	   	var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0"){
		alert ("选择你要放入的客户！")
		return false
	}else{
	  if (confirm("确实放到暂不续签客户库吗?"))
	  {
		  form.dostay.value="ZNoContinueCrm"
		  form.doflag.value=n
		  //alert(form.doflag.value)
		  form.submit()
	  }
	}
  }
  
  
  function showHideMenu1() {
	if (frmMenu1.style.display=="") {
		frmMenu1.style.display="none"
		//switchPoint.innerText=4
		}
	else {
		frmMenu1.style.display=""
		//switchPoint.innerText=3
		}
}
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}