<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../include/province.js"></SCRIPT>
<SCRIPT language=javascript src="../include/compkind.js"></SCRIPT>
<script>
function clickcontact()
{
if (document.all.contacttext.style.display!="")
{
document.all.contacttext.style.display=""
}else
{
document.all.contacttext.style.display="none"
}
}
function showhidefun(obj)
{
if(eval(document.getElementById(obj)))
{
if(document.getElementById(obj).style.display!="")
{document.getElementById(obj).style.display=""}
else
{document.getElementById(obj).style.display="none";}
}
}
</script>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.crmcheckmod {
	background-color: #FFFFCC;
}
.textt {
	background-color: #FFFFFF;
	border: 1px solid #666666;
}
-->
</style>
</head>
<body>
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td bgcolor="E7EBDE">
		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                         <%
  idprod=trim(request.querystring("com_id"))
  if idprod="" then
  	response.end
  end if
  set rscom=server.CreateObject("ADODB.recordset")
	sql="select * from comp_info where com_id="&idprod
	ch=0
	rscom.open sql,conn,1,1
	if rscom.eof or rscom.bof then
		rscom.close
		set rscom=nothing
		response.end
	end if

	sqla="select com_intro from comp_comIntro where com_id="&idprod
	set rsa=conn.execute(sqla)
	if not rsa.eof then
		com_intro1=trim(rsa("com_intro"))
	end if
	rsa.close
	set rsa=nothing
	if com_intro1="" or isnull(com_intro1) then
		com_intro1=rscom("com_intro")
	end if
	
sqllit="select pdt_com_productslist,pdt_com_productslist_en from products where pdt_comemail='"&rscom("com_email")&"'"
set rscit=conn.execute(sqllit)
if not rscit.eof then
	if rscom("com_productslist")="" or isnull(rscom("com_productslist")) then
	com_productslist=rscit("pdt_com_productslist")
	else
	com_productslist=rscom("com_productslist")
	end if
	if rscom("com_productslist_en")="" or isnull(rscom("com_productslist_en")) then
	com_productslist_en=rscit("pdt_com_productslist_en")
	else
	com_productslist_en=rscom("com_productslist_en")
	end if
else
	com_productslist=rscom("com_productslist")
	com_productslist_en=rscom("com_productslist_en")
end if
rscit.close
set rscit=nothing
sqlc="select * from comp_Evaluate where com_id="&idprod
set rsc=conn.execute(sqlc)
if not rsc.eof then
	C_company=rsc("C_company")
	C_address=rsc("C_address")
	C_tel=rsc("C_tel")
	C_email=rsc("C_email")
	C_intro=rsc("C_intro")
else
	C_company=2
	C_address=2
	C_tel=2
	C_email=2
	C_intro=2
end if
com_id=idprod
'----------客户来源函数
function showlyfrom(comID)
	showly1=""
 	sqlyy="select lyCode from comp_comly where com_id="&comID&""
	set rsyy=conn.execute(sqlyy)
	if not rsyy.eof or not rsyy.bof then
	while not rsyy.eof
		lycode=rsyy(0)
		sqlly="select meno from cate_product_ly where code="&lycode&""
		set rsly=conn.execute(sqlly)
		if not rsly.eof or not rsly.bof then
			showly1=showly1&","&trim(rsly(0))
		end if
		rsly.close
		set rsly=nothing
		rsyy.movenext
	wend
		showlyfrom=showly1
	else
		showlyfrom=""
	end if
	rsyy.close
	set rsyy=nothing
end function
        '---------------客户来源
		othercode=""
		sqlf="select lyCode from comp_comLy where com_id="&com_id
		set rsf=conn.execute(sqlf)
		if not rsf.eof or not rsf.bof then
			while not rsf.eof
			othercode=othercode&rsf("lyCode")&","
			rsf.movenext
			wend
			othercode=left(othercode,len(othercode)-1)
		end if
		
		rsf.close
		set rsf=nothing
		'--------------客户类别
		sql="select * from comp_exhibit where com_id="&com_id
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			customerType=rs("customerType")
			remark=rs("remark")
		end if
		rs.close
		set rs=nothing
%>
		    <tr>
              <td class="td_s2">
              <form name="form1" method="post" action="http://192.168.1.2/admin1/Crmlocal/crm_comp_savelocal.asp" onSubmit="return chkfrm(this)"><table width="90%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#8EA365" style="display:none">
<script language="javascript">
function chkfrm(frm)
{
	getprovincename();
	if(frm.cname.value=="")
	{
		alert("请输入公司名称!");
		return false;
	}
	if (frm.ckeywords.value=="")
	{
		alert("请选择行业")
		return false;
	}

	if(frm.ctel.value.length<=0)
	{
		alert("请输入电话!");
		return false;
	}

	if(frm.ccontactp.value.length<=0)
	{
		alert("请输入联系人!");
		return false;
	}
	if (frm.ckind.value=="")
	{
		alert("请选择公司类型");
		return false;
	}
	return true;
}
function checkmod(obj)
{
    var infoboxOkClass="crmcheckmod";
	var infoboxinfo=obj.id.substring(0,obj.id.length-5)
	//alert (infoboxinfo)
	var infobox= document.getElementById(infoboxinfo);
	var infoname=document.getElementById(obj.id);
	if (infoname.checked==true)
	{
	infobox.className = infoboxOkClass;
	infobox.readOnly=false
	}
	if (infoname.checked==false)
	{
	infobox.className = "textt";
	//infobox.readOnly=true
	}
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
	infoname.readOnly=false
	infoname.focus()
	}else
	{
	//infobox.checked=false
	//var infoname=document.getElementById(obj.id);
	//infoname.className = "textt";
	//infoname.readOnly=true
	}
}
function modaldailog(id)
{
	var screenHeight = screen.height-500;
	var screenWidth = screen.width-600;
	url="/admin1/cardMember/selectSort.asp?com_id=<%=com_id%>"
	var aa=window.showModalDialog("/admin1/modaldealog_body.asp?url="+url,window,"scrollbars=yes;resizable=no;help=no;status=no;dialogHeight=500px;dialogWidth=600px;dialogTop="+screenHeight+"; dialogLeft="+screenWidth+";");
	window.dialogArguments;
}
                </script>
                  
                   
                    <tr bgcolor="#E7EBDE">
                      <td width="13%" align="right">公司名称：
                        <input name="re" type="hidden" id="re" value="1">                      </td>
                      <td bgcolor="#E7EBDE"><input name="cnameCheck" type="checkbox" id="cnameCheck" value="1" onClick="checkmod(this)">
                      <input name="cname" class="textt" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" maxlength="96" onClick="selecttextname(this)">
                        <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>">
                        <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">
                        <input name="loginCheck" type="checkbox" id="loginCheck" value="1">
                        更新登录信息</td>
                      <td><%call cate_cn("cate_score","C_company",C_company)%></td>
                    </tr>
                    <tr>
                        <td align="right" bgcolor="#D2DAC2"><font color="RED">*</font> 客户来源：</td>
                        <td colspan="2" bgcolor="#E7EBDE"><textarea name="Lysort" id="Lysort" class="textt" style="width:100% " readonly><%=showlyfrom(com_id)%></textarea>
                        <input type="hidden" name="Lysort_code" id="Lysort_code" value="<%=othercode%>">
<input type="button" name="button3" id="button3" value="空" onClick="document.getElementById('Lysort').value='';document.getElementById('Lysort_code').value=''"> <input type="button" name="button" id="button" value="选择来源" onClick="modaldailog(1)">
                         <a href="../cardMember/sort.asp" target="_blank">管理</a></td>
                      </tr>
                      <tr>
                        <td align="right" bgcolor="#D2DAC2"><font color="RED">*</font> 展商客户类型：</td>
                        <td colspan="2" bgcolor="#E7EBDE">
                        <select name="customerType" id="customerType">
                        <option value="">=选择展商类型=</option><%
						sqlt="select * from cate_exhibitType order by ord asc"
						set rst=conn.execute(sqlt)
						if not rst.eof or not rst.bof then
						while not rst.eof
						%>
                            <option value="<%=rst("code")%>"><%=rst("meno")%></option>
                        <%
						rst.movenext
						wend
						end if
						rst.close
						set rst=nothing
						%>
                        </select>
                        <script>selectOption("customerType","<%=customerType%>")</script>
                          <a href="../cardMember/sort_exhibitType.asp" target="_blank">管理</a></td>
                      </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">地址：</td>
                      <td><input name="caddCheck" type="checkbox" id="caddCheck" value="1" onClick="checkmod(this)">
                      <input name="cadd" type="text" id="cadd" class="textt" value="<%=replacequot1(trim(rscom("com_add")))%>" size="50" maxlength="255" onClick="selecttextname(this)">                      </td>
                      <td ><%call cate_cn("cate_score","C_address",C_address)%></td>
                    </tr>
                    
                    <tr bgcolor="#E7EBDE">
                      <td align="right">邮编：</td>
                      <td colspan="2"><input name="czipCheck" type="checkbox" id="czipCheck" value="1" onClick="checkmod(this)">
                      <input name="czip" type="text" class="textt" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20" onClick="selecttextname(this)"></td>
                    </tr>
					<tr bgcolor="#E7EBDE">
					  <td align="right"  class="regtext">行业：</td>
					  <td colspan="2">
                      <input name="ckeywordsCheck" type="checkbox" id="ckeywordsCheck" value="1" onClick="checkmod(this)">
                      <select name="ckeywords" id="ckeywords" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');selecttextname(this);getprovincevalue()" >
                        <option value="">请选择</option>
                        <option value="1">废金属</option>
                        <option value="2">废塑料</option>
                        <option value="3">废旧轮胎与废橡胶</option>
                        <option value="4">废纺织品与废皮革</option>
                        <option value="5">废纸</option>
                        <option value="6">废电子电器</option>
                        <option value="10">废玻璃</option>
                        <option value="12">废旧二手设备</option>
                        <option value="14">其他废料</option>
                        <option value="15">服务</option>
                      </select>
                      <script>selectOption("ckeywords","<%=rscom("com_keywords")%>")</script>
                      </td>
					  </tr>
					<tr bgcolor="#E7EBDE">
    <td align="right"  class="regtext"><font color="RED">* </font>国家/地区：</td>
    <td colspan="2">
	<script>
	function selectcountry(id)
	{
		if (id=="1")
		{
			document.all.othercountrys.style.display="none"
			document.all.othercountrys1.style.display="none"
			document.all.mycountry.style.display=""
		}
		else
		{
			document.all.othercountrys.style.display=""
			document.all.othercountrys1.style.display=""
			document.all.mycountry.style.display="none"
			document.all.othercity.value=""
			
		}
	}
	</script>
    <%com_ctr_id=rscom("com_ctr_id")%>
	<input name="countryselectCheck" type="checkbox" id="countryselectCheck" value="1" onClick="checkmod(this)">
	<select name="countryselect" id="countryselect" onChange="selectcountry(this.value);selecttextname(this)">
      <option value="1" <%if com_ctr_id="1" then response.Write("selected")%>>中国</option>
      <option value="0" <%if com_ctr_id<>"1" then response.Write("selected")%>>其他国家/地区</option>
    </select>		</td>
  </tr>
   <tr bgcolor="#E7EBDE" id="othercountrys" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
     <td align="right" class="regtext">其他国家/地区：</td>
     <td colspan="2" valign="top">
	   <input name="ccountryCheck" type="checkbox" id="ccountryCheck" value="1" onClick="checkmod(this)">
	   <select name="ccountry" id="ccountry" onChange="selecttextname(this)">
	<%
	sqlc="select ctr_id,ctr_chn_name from countrys where ctr_id<>1 order by ctr_index asc"
	set rsc=server.CreateObject("adodb.recordset")
	rsc.open sqlc,conn,1,2
	if not rsc.eof then
	do while not rsc.eof
	%>
	  <option value="<%=rsc(0)%>" <%if com_ctr_id=rsc(0) then response.Write("selected")%>><%=rsc(1)%></option>
	<%
	rsc.movenext
	loop
	end if
	rsc.close
	set rsc=nothing
	%>
	  </select></td>
   </tr>
   <tr bgcolor="#E7EBDE" id="othercountrys1" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
     <td align="right"  class="ttext_b1"><div align="right" class="regtext">城市：</div></td>
     <td colspan="2" valign="top"><input name="othercityCheck" type="checkbox" id="othercityCheck" value="1" onClick="checkmod(this)">
     <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" value="<%=replacequot1(rscom("com_province"))%>"/></td>
   </tr>
   <tr bgcolor="#E7EBDE" id="mycountry" <%if com_ctr_id="1" or com_ctr_id="" or isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
    <td align="right"  class="ttext_b1"><div align="right" class="regtext"><font color="RED">* </font>省份：</div></td>
    <td colspan="2" valign="top">
      <input name="provinceCheck" type="checkbox" id="provinceCheck" value="1" onClick="checkmod(this)">
        
        <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
        <input type="hidden" name="province" id="province">
        <input type="hidden" name="city" id="city">
	    <input type="hidden" name="cprovince" id="cprovince" value="0">
		<%
		'--------------------------根据地区名取得地区编码
		function getprovince(meno)
			sqlgp="select code from cate_province where meno='"&meno&"'"
			set rsgp=conn.execute(sqlgp)
			if not rsgp.eof or not rsgp.bof then
				getprovince=rsgp(0)
			end if
			rsgp.close
			set rsgp=nothing
		end function
        cprovince=replacequot1(rscom("com_province"))
		Arrprovince=split(cprovince,"|")
		if ubound(Arrprovince)>0 then
			province=Arrprovince(0)
			city=Arrprovince(1)
		end if
		'--------------------------直接取的地区编码
		sqlh="select * from comp_provinceID where com_id="&idprod
		set rsh=conn.execute(sqlh)
		if not rsh.eof or not rsh.bof then
			provinceID=rsh("province")
			cityID=rsh("city")
			GardenID=rsh("Garden")
		else
			provinceID=getprovince(province)
			cityID=getprovince(city)
			GardenID=""
		end if
		rsh.close
		set rsh=nothing
		'---------------------------------------
		%>
    	<script>
			//地区及园区
			var Fstyle="";
			var selectname1="province1";
			var selectname2="city1";
			var selectname3="Garden";
			var Fvalue1="<%=provinceID%>";
			var Fvalue2="<%=cityID%>";
			var Fvalue3="<%=GardenID%>";
			var hyID="";//行业ID号
			getprovincevalue();
		</script>
        <script>
		function getprovincename()
		{
			document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
			document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
		}
		</script>
	    <input type="hidden" name="cprovince" id="cprovince" value="0"></td>
   </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">电话：</td>
                      <td><input name="ctelCheck" type="checkbox" id="ctelCheck" value="1" onClick="checkmod(this)">
                      <input name="ctel" type="text" class="textt" onClick="selecttextname(this)" id="ctel" value="<%=trim(rscom("com_tel"))%>" size="30" maxlength="96"></td>
                      <td rowspan="2"><%call cate_cn("cate_score","C_tel",C_tel)%></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">手机：</td>
                      <td><input name="cmobileCheck" type="checkbox" id="cmobileCheck" value="1" onClick="checkmod(this)">                      
                        <input name="cmobile" type="text" class="textt" onClick="selecttextname(this)" id="cmobile" value="<%=trim(rscom("com_mobile"))%>" size="30" maxlength="96">                      </td>
                      </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">传真：</td>
                      <td colspan="2"><input name="cfaxCheck" type="checkbox" id="cfaxCheck" value="1" onClick="checkmod(this)">                      
                        <input name="cfax" type="text" id="cfax" class="textt" onClick="selecttextname(this)" value="<%=trim(rscom("com_fax"))%>" size="30" maxlength="96"></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">电子邮箱：</td>
                      <td>
					    <input name="newemailCheck" type="checkbox" id="newemailCheck" value="1" >
					    <%
					  if session("userid")="10" or session("personid")=23 then
					  	redo=""
					  else
					    redo="readonly"
					  end if
					  %>
					  <input name="newemail" type="text" id="newemail" class="textt" value="<%=trim(rscom("com_email"))%>" onClick="selecttextname(this)" size="30" maxlength="48"  <%=redo%>>
					  <input name="cemail" type="hidden" id="cemail" value="<%=trim(rscom("com_email"))%>">
					  如需修改EMAIL请和阿康说明</td>
                      <td><%call cate_cn("cate_score","C_email",C_email)%></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">QQ/MSN：</td>
                      <td colspan="2">
                      <input name="commsnCheck" type="checkbox" id="commsnCheck" value="1" ><%
					  sqlo="select com_Msn from comp_comintro where com_id="&idprod&""
					  set rso=conn.execute(sqlo)
					  if not rso.eof or not rso.bof then
					  	commsn=rso(0)
					  end if
					  rso.close
					  set rso=nothing
					  %>
                      <input type="text" name="commsn" class="textt" id="commsn" value="<%=commsn%>" onClick="selecttextname(this)"></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">网站：</td>
                      <td colspan="2"><input name="cwebCheck" type="checkbox" id="cwebCheck" value="1" onClick="checkmod(this)">
                      <input name="cweb" type="text" id="cweb" class="textt" onClick="selecttextname(this)" value="<%=trim(rscom("com_website"))%>" size="50" maxlength="255"></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">联系人：</td>
                      <td colspan="2"><input name="ccontactpCheck" type="checkbox" id="ccontactpCheck" value="1" onClick="checkmod(this)">
                      <input name="ccontactp" type="text" class="textt" onClick="selecttextname(this)" id="ccontactp" value="<%=replacequot1(trim(rscom("com_contactperson")))%>" size="20" maxlength="48"></td>
                    </tr>
                  
                    <tr bgcolor="#E7EBDE">
                      <td align="right">性别：</td>
                      <td colspan="2"><input name="cdesiCheck" type="checkbox" id="cdesiCheck" value="1" onClick="checkmod(this)">
                      <input name="cdesi" type="text" id="cdesi" class="textt" onClick="selecttextname(this)" value="<%=replacequot1(trim(rscom("com_desi")))%>" size="10" maxlength="24"></td>
                    </tr>
                    
                    <tr bgcolor="#E7EBDE">
                      <td align="right">职位：</td>
                      <td colspan="2" align="left"><input name="cstationCheck" type="checkbox" id="cstationCheck" value="1" onClick="checkmod(this)">
                      <input name="cstation" type="text" class="textt" onClick="selecttextname(this)" id="cstation" value="<%=replacequot1(trim(rscom("com_station")))%>" size="10" maxlength="24"></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right">公司类型：</td>
                      <td colspan="2" align="left">
                      <div style="float:left"><input name="ckindCheck" type="checkbox" id="ckindCheck" value="1" onClick="checkmod(this)"></div>
                      <div id="ckindmain" style="float:left"></div>
                      <%
					  ckeywords=rscom("com_keywords")
					  if ckeywords="" then ckeywords=0
					  %>
                      <script>changekind("<%=ckeywords%>","ckind","ckindmain")</script>
                      <script>selectOption("ckind","<%=rscom("com_kind")%>")</script>
					  <%'getcomkindlist trim(rscom("com_kind"))%></td>
                    </tr>
                    <tr bgcolor="#E7EBDE">
                      <td align="right" nowrap>公司简介：<br>
              (中文)</td>
                      <td align="left"><input name="cintroduceCheck" type="checkbox" id="cintroduceCheck" value="1" onClick="checkmod(this)">
                        <br>
                      <textarea name="cintroduce" cols="60" class="textt" onClick="selecttextname(this)" rows="3" style="width:100% " id="cintroduce"><%=replacequot1(trim(com_intro1))%></textarea></td>
                      <td align="left"><%call cate_cn("cate_score","C_intro",C_intro)%></td>
                    </tr>
                   
                    <tr align="center" bgcolor="#E7EBDE">
                      <td align="right">主营业务：<br>
                        (中文)              </td>
                      <td colspan="2" align="left">
					    <input name="cproductslist_enCheck" type="checkbox" id="cproductslist_enCheck" value="1" onClick="checkmod(this)">
					    <br>
					    <textarea name="cproductslist_en" cols="50" rows="3" class="textt" onClick="selecttextname(this)" style="width:100% " id="cproductslist_en"><%
					  if trim(com_productslist_en)="" or isnull(trim(com_productslist_en)) then
					  response.Write(trim(com_productslist))
					  else
					  response.Write(trim(com_productslist_en))
					  end if
					  %></textarea>					  </td>
                    </tr>

                    <tr bgcolor="#E7EBDE">
                      <td align="right" ><div align="right">二级域名</div></td>
                      <td colspan="2">
					    <input name="com_subnameCheck" type="checkbox" id="com_subnameCheck" value="1" onClick="checkmod(this)">
					    <input name="com_subname" type="text" class="textt" id="com_subname" onClick="selecttextname(this)" value="<%=trim(rscom("com_subname"))%>">
					    <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">.zz91.com</td>
                    </tr>
					<%
					arrtags=""
					sqltags="select kid from tags_infoID where Tid="&rscom("com_id")&" and Ttype=9"
					set rstags=conn.execute(sqltags)
					if not rstags.eof then
						while not rstags.eof
						sqlt="select kname from tags_info where id="&rstags(0)
						set rst=conn.execute(sqlt)
							if not rst.eof then
								arrtags=arrtags&rst(0)&","
							end if
						rst.close
						set rst=nothing
						rstags.movenext
						wend
					end if
					'response.write(arrtags)
					if arrtags<>"" then
					arrtags=left(arrtags,len(arrtags)-1)
					end if
					%>
					<tr align="center">
                      <td align="right">公司标签：</td>
                      <td colspan="2" align="left" bgcolor="#E7EBDE"><input name="Comp_TagsCheck" type="checkbox" id="Comp_TagsCheck" value="1" onClick="checkmod(this)"> 
                        <input name="Comp_Tags" type="text" id="Comp_Tags" class="textt" size="50" onClick="selecttextname(this)" value="<%=arrtags%>">
                      标签之间用“,”搁开</td>
                    </tr>
                    <tr>
                        <td align="right" bgcolor="#D2DAC2">备注：</td>
                        <td colspan="2" bgcolor="#E7EBDE"><input name="remarkCheck" type="checkbox" id="remarkCheck" value="1" onClick="checkmod(this)"><textarea name="remark" style="width:100%" class="textt" rows="3" id="remark"><%=remark%></textarea></td>
                      </tr>
                    <tr align="center" bgcolor="#E7EBDE">
                      <td colspan="3">&nbsp;</td>
                    </tr>
                  
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="52" align="center"><input name="yufenpai" type="hidden" id="yufenpai" value="1">                      <input type="button" name="button2" id="button2" value="确认申请" onClick="this.form.submit()">
                      <input name="personid" type="hidden" id="personid" value="<%=request("personid")%>"></td>
                  </tr>
                </table>
              </form>
                  <%
				  rscom.close
				  set rscom=nothing%>
              </td>
            </tr>
            
           
          </table></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6"></td>
  </tr>
</table>
</body>
</html><%endConnection()%>
