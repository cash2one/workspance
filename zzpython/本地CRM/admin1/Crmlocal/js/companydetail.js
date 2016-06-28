function setContent()
{
	document.getElementById("cproductslist_enCheck").checked=true;
	var content="";
		var infobox=document.getElementById("cproductslist_en");
		var com_zyyw_value=document.getElementById("com_zyyw").value;
		var com_mysl_value=document.getElementById("com_mysl").value;
		var com_Area=document.getElementById("com_Area");
		var com_area_value=com_Area.options[com_Area.selectedIndex].text;
		
		var com_jyfs=document.getElementById("com_jyfs");
		var com_jyfs_value=com_jyfs.options[com_jyfs.selectedIndex].text;
		content="经营方式:"+com_jyfs_value+";";
		content+="主营:"+com_zyyw_value+";";
		content+="数量:"+com_mysl_value+";";
		content+="区域:"+com_area_value;
		infobox.value=content;
		infobox.className = "crmcheckmod";
		infobox.readOnly=false;
}
function opencalltishi()
{
	var obj=document.getElementById("calltishi")
	if(obj.style.display=="")
	{
		obj.style.display="none"
	}else
	{
		obj.style.display=""
	}
}
function rehit()
{
	alert (document.all.crmeidt.height)
	document.all.crmeidt.style.display="none"
}
function GetValueChoose(elementname)
{
	var sValue = "";
	var tmpels = elementname;
	for(var i=0;i<tmpels.length;i++)
	{
		if(tmpels[i].checked)
		{
			sValue= tmpels[i].value;
		}
	}
	return sValue;
}

function Choose5star(form)
{
	n=0;
	m=0;
	var reson_checklist=""
	for (var i=0;i<form.elements.length;i++)
    {
    	var e = form.elements[i];
    	if (e.name.substr(0,11)=='reson_check')
		{
			if (e.checked==true)
			{
				n=n+1
				reson_checklist=reson_checklist+e.value+","
			}
			m=m+1
		}
    }
	m=m-1
	document.getElementById("reson_checklist").value=reson_checklist
	if (m != n*2)
	{
		return false;
	}else{
		return true;
	}
}

function getstrstar(form)
{
	//var reson_checklist=""
//	for (var i=0;i<form.elements.length;i++)
//    {
//    	var e = form.elements[i];
//    	if (e.name.substr(0,8)=='resontel')
//		{
//			reson_checklist=reson_checklist+e.value+"<br>"
//		}
//    }
//	document.getElementById("allresontel").value=reson_checklist
}

function chkfrm(frm)
{
    //document.all.crmeidt.style.display="none"
	if(frm.cname.value.length<=0 && frm.cname_en.value.length<=0)
	{
		alert("请输入公司名称!");
		frm.cname.focus();
		return false;
	}
	getprovincename();
	
	if(frm.cadd.value.length<=0 && frm.cadd_en.value.length<=0)
	{
		alert("请输入地址!");
		frm.cadd.focus();
		return false;
	}
	
	if (frm.lxcontactflag.value=="1")
	{
		if (frm.PersonName.value=="")
		{
		alert("请输入联系人姓名!");
		frm.PersonName.focus();
		return false;
		}
	}
	
	//if(frm.cemail.value.length<=0)
//	{
//		alert("请输入电子邮件!");
//		frm.cemail.focus();
//		return false;
//	}
//
//	if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
//		alert("电子邮箱格式错误");
//		frm.cemail.focus();
//		return  false;
//	}  
	if(frm.ccontactp.value.length<=0 && frm.ccontactp_en.value.length<=0)
	{
		alert("请输入联系人!");
		frm.ccontactp.focus();
		return false;
	}
	dotype=document.getElementById("dotype").value
	
//客户的销售类型
	if (frm.contactflag.value=="1")
	{
		if(frm.com_rank.value.length<=0)
		{
			alert("请选择客户等级!");
			frm.com_rank.focus();
			return false;
		}
		
		if (GetValueChoose(document.getElementsByName("contacttype"))=="12")
		{
			if(GetValueChoose(document.getElementsByName("c_Nocontact"))=="")
			{
				alert("请选择无效联系类型!");
				return false;
			}
		}else
		{
			if (frm.ckeywords.value=="")
			{
				alert("请选择行业!");
				frm.ckeywords.focus();
				return false;
			}
			if (frm.ckind.value=="")
			{
				alert("请选择公司类型!");
				frm.ckind.focus();
				return false;
			}
			if (frm.countryselect.value=="1")
			{
				if (frm.province1.value=="")
				{
					alert("请选择省份!");
					frm.province.focus();
					return false;
				}
				if (frm.city1.value=="")
				{
					alert("请选择城市!");
					frm.city.focus();
					return false;
				}
			}
			
		}
		
		var teltypecheck=document.getElementsByName("contacttype");
		var teltischeck=false;
		for(var i=0;i<teltypecheck.length;i++)
		{
			if(teltypecheck[i].checked){
				teltischeck=true;
			}
		}
		if(!teltischeck)
		{
			alert("请选择联系情况!");
			return false;
		}
		
		//判断毁星客户需要降级
		//vap客户类型修改
		if (dotype.substr(0,3)=="vap")
		{
			var teltypecheck=document.getElementsByName("paystats");
			var teltischeck=false;
			for(var i=0;i<teltypecheck.length;i++)
			{
				if(teltypecheck[i].checked){
					teltischeck=true;
					paystats=teltypecheck[i].value;
				}
			}
			if (paystats>0)
			{
				
				var teltypecheck=document.getElementsByName("paykind");
				var teltischeck=false;
				for(var i=0;i<teltypecheck.length;i++)
				{
					if(teltypecheck[i].checked){
						teltischeck=true;
					}
				}
				if(!teltischeck)
				{
					alert("请选择服务类型!");
					return false;
				}
				payfromdate=document.getElementById("payfromdate").value
				if (payfromdate=="")
				{
					alert("请输入开通时间！");
					return false;
				}
				paytodate=document.getElementById("paytodate").value
				if (paytodate=="")
				{
					alert("请输入结束时间！");
					return false;
				}
			}
		}
		
		var obj1=document.getElementsByName("comp_sale_type")
		var comp_sale_type=GetValueChoose(obj1)
		var com_rankflag=document.getElementById("com_rankflag").value
		if (com_rankflag == "5" && dotype.substr(0,3)!="vap")
		{
			if (comp_sale_type == "毁单")
			{
				if(frm.com_rank.value==5)
				{
					alert("选择毁单客户必须降级!");
					return false;
				}
				if(frm.tuohuireson.value == "")
				{
					alert("请填写拖单或毁单的原因!");
					frm.tuohuireson.focus();
					return false;
				}
			}else{
				if (comp_sale_type == "拖单")
				{
					if(frm.tuohuireson.value == "")
					{
						alert("请填写拖单或毁单的原因!");
						frm.tuohuireson.focus();
						return false;
					}
				}else{
					if (frm.com_rank.value<5)
					{
						alert("请选择拖单或毁单!");
						return false;
					}
				}
			}
		}
		
		if(frm.com_rank.value==5 && dotype.substr(0,3)!="vap")
		{
			if (Choose5star(frm)==false)
			{
				alert("是否5星的所有选项必须都选择一个!");
				return false;
			}else{
				
			}
		}
		var telflagcheck=document.getElementsByName("telflag");
		var telischeck=false;
		for(var i=0;i<telflagcheck.length;i++)
		{
			if(telflagcheck[i].checked){
				telischeck=true;
			}
		}
		if(!telischeck)
		{
			alert("请选择此客户的备注 销售类型!");
			return false;
		}
		if (document.getElementById("detail").value.length<=0)
		{
			alert("请输入小记内容！")
			document.getElementById("detail").focus();
			return false;
		}
	}
	dotype=document.getElementById("dotype").value
	if (dotype.substr(0,3)!="vap")
	{
		if (frm.com_rank.value=="4")
		{
			alert("请现在普4星或钻4星")
			return false;
		}
	}else{
		if (frm.com_rank.value=="4")
		{
			alert("请现在长4星或短4星")
			return false;
		}
	}
	if (GetValueChoose(document.getElementsByName("contacttype"))=="13"){
		//var weixinmobile=document.getElementById("weixinmobile").value;
//			var ajaxurl="http://adminasto.zz91.com/sendsms.html?mobile="+weixinmobile+"&company_id="+document.getElementById("weixincom_id").value;
//			$.getScript(ajaxurl, function() {
//					var result = _suggest_result_.result;
//					//alert(result)
//					if (result=="0"){
//						
//					}
//			});
	}
	document.getElementById("submitsave").disabled=true;
	document.getElementById("submitsave1").disabled=true;
	document.getElementById("submitsave2").disabled=true;
	return true;
}
function selecttextname(obj)
{
    var infoboxOkClass="crmcheckmod";
	var infoboxinfo=obj.id+"Check"
	var infobox= document.getElementById(infoboxinfo);
	
	if (infobox.checked==false)
	{
		infobox.checked=true
		var infoname=document.getElementById(obj.id);
		infoname.className = infoboxOkClass;
	}
}
function checkmod(obj)
{
    var infoboxOkClass="crmcheckmod";
	var infoboxinfo=obj.id.substring(0,obj.id.length-5)
	var infobox= document.getElementById(infoboxinfo);
	var infoname=document.getElementById(obj.id);
	if (infoname.checked==true)
	{
		infobox.className = infoboxOkClass;
		infobox.readOnly=false
	}
	if (infoname.checked==false)
	{
		infobox.className = "text";
		infobox.readOnly=true
	}
}
function selectcountry(id)
{
	if (id=="1")
	{
		document.getElementById("othercountrys").style.display="none"
		document.getElementById("mycountry").style.display=""
	}
	else
	{
		document.getElementById("othercountrys").style.display=""
		document.getElementById("mycountry").style.display="none"
		document.getElementById("othercity").value=""
	}
}
function changeTab(ID)
{
	if (ID=="13")
	{
		document.getElementById("tabinfo1").style.display="none"
	}else
	{
		document.getElementById("tabinfo1").style.display=""
	}
}
function changestar(id)
{
	var obj=document.getElementById("change5star")
	var dotype=document.getElementById("dotype").value
	if (id=="5" && dotype.substr(0,3)!="vap")
	{
		obj.style.display=""
	}else{
		obj.style.display="none"
	}
	
}
function getcom(form)
{
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='glcom')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	if (selectcb=="0")
	{
		alert ("选择你要提交的信息！")
		return false
	}
	else
	{
	  if (confirm("确实要提交该信息吗?"))
	  {
	  	 window.open("pipei/p_add_save.asp?comid="+selectcb.substr(2),"_blank","")
	  }
	}
}

function groupclose()
{
	document.getElementById("regform").style.display='none';
	document.getElementById("page_cover").style.display='none';
}
function openPageCover(){
	var screenHeight = screen.height;
	var screenWidth = screen.width;
	var aa=document.getElementById("wrap").offsetHeight
	screenHeight=document.getElementsByTagName('body')[0].offsetHeight;
	if (screenHeight<aa)
	{
		screenHeight=aa;
	}
    document.getElementById("page_cover").style.display="";
	document.getElementById("page_cover").style.width=(screenWidth-20)+"px";
	document.getElementById("page_cover").style.height=screenHeight+"px";
	document.getElementById("page_cover").className="t"
	document.getElementById("regform").style.position="absolute"
	document.getElementById("regform").style.left=(screenWidth/2-300)+"px";
	document.getElementById("regform").style.display="";
}
function DropToSea(n,dostay)
{
	com_rankFlag=document.getElementById("com_rankFlag").value
	idprod=document.getElementById("com_id").value
	zhuangdanFlag=document.getElementById("zhuangdanFlag").value
	guangLiangFlag=document.getElementById("guangLiangFlag").value
	dotype=document.getElementById("dotype").value
	if (com_rankFlag>=4)
	{
		if(confirm("确实要将4-5星的客户放入公海吗?"))
		{
			if (n==1)
			{
				document.getElementById("alerttile").innerHTML="请填写放入公海的原因！"
				openPageCover()
			}else{
				
				window.location='crm_assign_save.asp?selectcb='+idprod+'&dostay='+dostay+'&closed=1&zhuangdanFlag='+zhuangdanFlag+'&guangLiangFlag='+guangLiangFlag+'&dotype='+dotype+''
			}
		}
	}else
	{
		if (n==1)
		{
			document.getElementById("alerttile").innerHTML="请填写放入公海的原因！"
			openPageCover()
		}else{
			window.location='crm_assign_save.asp?selectcb='+idprod+'&dostay='+dostay+'&closed=1&zhuangdanFlag='+zhuangdanFlag+'&guangLiangFlag='+guangLiangFlag+'&dotype='+dotype+''
		}
	}
}
function changesales(id)
	{
		if (id=="1")
		{
			document.getElementById("tabzhuyin1").style.display="";
			document.getElementById("tabzhuyin2").style.display="none";
		}
		if (id=="2")
		{
			document.getElementById("tabzhuyin1").style.display="none";
			document.getElementById("tabzhuyin2").style.display="";
		}
		if (id=="0")
		{
			document.getElementById("tabzhuyin1").style.display="";
			document.getElementById("tabzhuyin2").style.display="";
		}
	}
function jlcontact()
{
	var jlflagT=document.getElementById("jlflag");
	var contactflag=document.getElementById("contactflag");
	var jlButton=document.getElementById("jlButton")
	
	if (jlflagT.style.display=="")
	{
		jlflagT.style.display="none"
		contactflag.value="0"
		jlButton.value="销售记录"
	}else
	{
		jlflagT.style.display=""
		contactflag.value="1"
		jlButton.value="×取消记录"
	}
}
function linksstack()
{
	var linkss=document.getElementById("linkss");
	if (linkss.style.display=="")
	{
		linkss.style.display="none"
	}
	else
	{
		linkss.style.display=""
	}
}
function lxcontact()
{
	var lxflagT=document.getElementById("lxflag");
	var lxcontactflag=document.getElementById("lxcontactflag");
	var lxButton=document.getElementById("lxButton")
	if (lxflagT.style.display=="")
	{
		lxflagT.style.display="none"
		lxcontactflag.value="0"
		lxButton.value="增加联系人"
	}
	else
	{
		lxflagT.style.display=""
		lxcontactflag.value="1"
		lxButton.value="×取消增加联系人"
	}
}

function openpayinput(n,flag)
{
	if (flag==1)
	{
		document.getElementById("payinput").style.display="";
	}else
	{
		document.getElementById("payinput").style.display="none";
	}
	if(n==1)
	{
		str="必杀期客户为在开通后10个月到12个月内的客户"
	}
	if(n==2)
	{
		str="黄金客户为在开通后2个月到10个月内的客户"
	}
	if(n==3)
	{
		str="新客户为在开通后1个月内的客户"
	}
	if(n==4)
	{
		str=""
	}
	if(n==0)
	{
		str=""
	}
	document.getElementById("paytyletxt").innerHTML=str
}
