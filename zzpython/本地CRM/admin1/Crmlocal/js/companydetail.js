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
		content="��Ӫ��ʽ:"+com_jyfs_value+";";
		content+="��Ӫ:"+com_zyyw_value+";";
		content+="����:"+com_mysl_value+";";
		content+="����:"+com_area_value;
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
		alert("�����빫˾����!");
		frm.cname.focus();
		return false;
	}
	getprovincename();
	
	if(frm.cadd.value.length<=0 && frm.cadd_en.value.length<=0)
	{
		alert("�������ַ!");
		frm.cadd.focus();
		return false;
	}
	
	if (frm.lxcontactflag.value=="1")
	{
		if (frm.PersonName.value=="")
		{
		alert("��������ϵ������!");
		frm.PersonName.focus();
		return false;
		}
	}
	
	//if(frm.cemail.value.length<=0)
//	{
//		alert("����������ʼ�!");
//		frm.cemail.focus();
//		return false;
//	}
//
//	if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
//		alert("���������ʽ����");
//		frm.cemail.focus();
//		return  false;
//	}  
	if(frm.ccontactp.value.length<=0 && frm.ccontactp_en.value.length<=0)
	{
		alert("��������ϵ��!");
		frm.ccontactp.focus();
		return false;
	}
	dotype=document.getElementById("dotype").value
	
//�ͻ�����������
	if (frm.contactflag.value=="1")
	{
		if(frm.com_rank.value.length<=0)
		{
			alert("��ѡ��ͻ��ȼ�!");
			frm.com_rank.focus();
			return false;
		}
		
		if (GetValueChoose(document.getElementsByName("contacttype"))=="12")
		{
			if(GetValueChoose(document.getElementsByName("c_Nocontact"))=="")
			{
				alert("��ѡ����Ч��ϵ����!");
				return false;
			}
		}else
		{
			if (frm.ckeywords.value=="")
			{
				alert("��ѡ����ҵ!");
				frm.ckeywords.focus();
				return false;
			}
			if (frm.ckind.value=="")
			{
				alert("��ѡ��˾����!");
				frm.ckind.focus();
				return false;
			}
			if (frm.countryselect.value=="1")
			{
				if (frm.province1.value=="")
				{
					alert("��ѡ��ʡ��!");
					frm.province.focus();
					return false;
				}
				if (frm.city1.value=="")
				{
					alert("��ѡ�����!");
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
			alert("��ѡ����ϵ���!");
			return false;
		}
		
		//�жϻ��ǿͻ���Ҫ����
		//vap�ͻ������޸�
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
					alert("��ѡ���������!");
					return false;
				}
				payfromdate=document.getElementById("payfromdate").value
				if (payfromdate=="")
				{
					alert("�����뿪ͨʱ�䣡");
					return false;
				}
				paytodate=document.getElementById("paytodate").value
				if (paytodate=="")
				{
					alert("���������ʱ�䣡");
					return false;
				}
			}
		}
		
		var obj1=document.getElementsByName("comp_sale_type")
		var comp_sale_type=GetValueChoose(obj1)
		var com_rankflag=document.getElementById("com_rankflag").value
		if (com_rankflag == "5" && dotype.substr(0,3)!="vap")
		{
			if (comp_sale_type == "�ٵ�")
			{
				if(frm.com_rank.value==5)
				{
					alert("ѡ��ٵ��ͻ����뽵��!");
					return false;
				}
				if(frm.tuohuireson.value == "")
				{
					alert("����д�ϵ���ٵ���ԭ��!");
					frm.tuohuireson.focus();
					return false;
				}
			}else{
				if (comp_sale_type == "�ϵ�")
				{
					if(frm.tuohuireson.value == "")
					{
						alert("����д�ϵ���ٵ���ԭ��!");
						frm.tuohuireson.focus();
						return false;
					}
				}else{
					if (frm.com_rank.value<5)
					{
						alert("��ѡ���ϵ���ٵ�!");
						return false;
					}
				}
			}
		}
		
		if(frm.com_rank.value==5 && dotype.substr(0,3)!="vap")
		{
			if (Choose5star(frm)==false)
			{
				alert("�Ƿ�5�ǵ�����ѡ����붼ѡ��һ��!");
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
			alert("��ѡ��˿ͻ��ı�ע ��������!");
			return false;
		}
		if (document.getElementById("detail").value.length<=0)
		{
			alert("������С�����ݣ�")
			document.getElementById("detail").focus();
			return false;
		}
	}
	dotype=document.getElementById("dotype").value
	if (dotype.substr(0,3)!="vap")
	{
		if (frm.com_rank.value=="4")
		{
			alert("��������4�ǻ���4��")
			return false;
		}
	}else{
		if (frm.com_rank.value=="4")
		{
			alert("�����ڳ�4�ǻ��4��")
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
		alert ("ѡ����Ҫ�ύ����Ϣ��")
		return false
	}
	else
	{
	  if (confirm("ȷʵҪ�ύ����Ϣ��?"))
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
		if(confirm("ȷʵҪ��4-5�ǵĿͻ����빫����?"))
		{
			if (n==1)
			{
				document.getElementById("alerttile").innerHTML="����д���빫����ԭ��"
				openPageCover()
			}else{
				
				window.location='crm_assign_save.asp?selectcb='+idprod+'&dostay='+dostay+'&closed=1&zhuangdanFlag='+zhuangdanFlag+'&guangLiangFlag='+guangLiangFlag+'&dotype='+dotype+''
			}
		}
	}else
	{
		if (n==1)
		{
			document.getElementById("alerttile").innerHTML="����д���빫����ԭ��"
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
		jlButton.value="���ۼ�¼"
	}else
	{
		jlflagT.style.display=""
		contactflag.value="1"
		jlButton.value="��ȡ����¼"
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
		lxButton.value="������ϵ��"
	}
	else
	{
		lxflagT.style.display=""
		lxcontactflag.value="1"
		lxButton.value="��ȡ��������ϵ��"
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
		str="��ɱ�ڿͻ�Ϊ�ڿ�ͨ��10���µ�12�����ڵĿͻ�"
	}
	if(n==2)
	{
		str="�ƽ�ͻ�Ϊ�ڿ�ͨ��2���µ�10�����ڵĿͻ�"
	}
	if(n==3)
	{
		str="�¿ͻ�Ϊ�ڿ�ͨ��1�����ڵĿͻ�"
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
