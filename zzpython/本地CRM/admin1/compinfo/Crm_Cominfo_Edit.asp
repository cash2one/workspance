<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
idprod=trim(request.querystring("idprod"))
sqlcrm="select * from crm_temp_salescomp where com_id="&idprod
set rscrm=conn.execute(sqlcrm)
if rscrm.eof then
	response.Write("�����޸�!")
	response.End()
end if
crmcheck=rscrm("crmcheck")
if crmcheck="0" then crmcheck="0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"
if crmcheck<>"" and not isnull(crmcheck) and crmcheck<>"0" then
	arrcrmcheck=split(crmcheck,",")
	dim checkstring(17)
	for n=1 to 17
	    checkstring(n)="0"
		for i=0 to ubound(arrcrmcheck)
			if cint(trim(arrcrmcheck(i)))=n then
			   checkstring(n)="1"
			end if
		next
	next
end if
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../sources/style.css" type="text/css">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../Crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="../Crmlocal/js/compkind.js"></SCRIPT>
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
.crmtext {
	background-color: #FFFFFF;
	border: 1px solid #FF6600;
}
.crmCheck {
	background-color: #FFFF00;
}
-->
</style>
</head>
<body>
<%
			  frompage=request.QueryString("frompage")
			  frompagequrstr=request.QueryString("frompagequrstr")
			  idprod=trim(request.querystring("idprod"))
			  cemail=trim(request.querystring("cemail"))
			  if idprod="" and cemail="" then response.end
			  set rscom=server.CreateObject("ADODB.recordset")
			  sql="select * from comp_info where com_id="&idprod
			  rscom.open sql,conn,1,1
			  if rscom.eof or rscom.bof then
					rscom.close
					set rscom=nothing
					response.end
			  end if
%>
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td width="50%" valign="top" bgcolor="E7EBDE">
            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#8EA365">
              <script language="javascript">
                function chkfrm(frm)
                {
					getprovincename();
                    if(frm.cname.value.length<=0 && frm.cname_en.value.length<=0)
                    {
                        alert("�����빫˾����!");
                        //frm.cname.focus();
                        return false;
                    }
                    if(frm.cadd.value.length<=0 && frm.cadd_en.value.length<=0)
                    {
                        alert("�������ַ!");
                        //frm.cadd.focus();
                        return false;
                    }
                    if(frm.ckeywords.value=="")
                    {
                        alert("��ѡ����ҵ!");
                        frm.ckeywords.focus();
                        return false;
                    }
                    if(frm.ckind.value=="")
                    {
                        alert("��ѡ��˾����!");
                        frm.ckind.focus();
                        return false;
                    }
                    if(frm.cprovince.value.length<=0)
                    {
                        alert("������ʡ!");
                        frm.cprovince.focus();
                        return false;
                    }
                    
                    if(frm.ctel.value.length<=0)
                    {
                        alert("������绰!");
                        frm.ctel.focus();
                        return false;
                    }
                    if(frm.cemail.value.length<=0)
                    {
                        alert("����������ʼ�!");
                        frm.cemail.focus();
                        return false;
                    }
                    if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
                        alert("���������ʽ����");
                        frm.cemail.focus();
                        return  false;
                    }  
                    if(frm.ccontactp.value.length<=0 && frm.ccontactp_en.value.length<=0)
                    {
                        alert("��������ϵ��!");
                        frm.ccontactp.focus();
                        return false;
                    }
                    
                    return true;
                }
                function checkmod(obj)
                {
                    var infoboxOkClass="crmCheck";
                    var infoboxinfo=obj.id.substring(0,obj.id.length-5)
                    //alert (infoboxinfo)
                    var infobox= document.getElementById(infoboxinfo);
                    var infoboxCrm= document.getElementById("Crm"+infoboxinfo);
                    var infoname=document.getElementById(obj.id);
                    if (infoname.checked==true)
                    {
                    infoboxCrm.className = infoboxOkClass;
                    infoboxCrm.name=infoboxinfo
                    infobox.name="Crm"+infoboxinfo
					
					infoboxCrm.id=infoboxinfo
                    infobox.id="Crm"+infoboxinfo
                    //infobox.readOnly=false
                    }
                    if (infoname.checked==false)
                    {
                    infoboxCrm.className = "crmtext";
                    infoboxCrm.name="Crm"+infoboxinfo
                    infobox.name=infoboxinfo
					
					infoboxCrm.id="Crm"+infoboxinfo
                    infobox.id=infoboxinfo
                    //infobox.readOnly=true
                    }
                }
                </script>
              <form name="form1" method="post" action="crm_comEdit_saveLocal.asp?frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>" target="saveifrim" onSubmit="return chkfrm(this)">
                
                <tr bgcolor="#FFFFFF">
                  <td width="10%" align="right">��˾���ƣ�
                    <input name="re" type="hidden" id="re" value="1"></td>
                  <td>
                    <input name="cname" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" maxlength="96">
                    <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>">
                    <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">
                    <%if CSTR(checkstring(1))="1" then%>
                    <input name="cnameCheck" type="checkbox" id="cnameCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmcname" type="text" class="crmtext" id="Crmcname" value="<%=replacequot1(trim(rscrm("com_name")))%>">
                    <%else%>
                    <input name="cnameCheckMod" type="checkbox" id="cnameCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                  </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="right">��ַ��</td>
                  <td>
                    <input name="cadd" type="text" id="cadd" value="<%=replacequot1(trim(rscom("com_add")))%>" size="30" maxlength="255">
                    <%if checkstring(2)="1" then%>
                    <input name="caddCheck" type="checkbox" id="caddCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmcadd" type="text" class="crmtext" id="Crmcadd" value="<%=replacequot1(trim(rscrm("com_add")))%>" size="30">
                    <%else%>
                    <input name="caddCheckMod" type="checkbox" id="caddCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                  </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="right">�ʱࣺ</td>
                  <td><input name="czip" type="text" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20">
                    <%if checkstring(4)="1" then%>
                    <input name="czipCheck" type="checkbox" id="czipCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmczip" type="text" class="crmtext" id="Crmczip" value="<%=trim(rscrm("com_zip"))%>">
                    <%else%>
                    <input name="czipCheckMod" type="checkbox" id="czipCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="right">��ҵ��</td>
                  <td>
                    <select name="ckeywords" id="ckeywords" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue()" >
                      <option value="">��ѡ��</option>
                      <option value="1">�Ͻ���</option>
                      <option value="2">������</option>
                      <option value="3">�Ͼ���̥�����</option>
                      <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
                      <option value="5">��ֽ</option>
                      <option value="6">�ϵ��ӵ���</option>
                      <option value="10">�ϲ���</option>
                      <option value="12">�Ͼɶ����豸</option>
                      <option value="14">��������</option>
                      <option value="15">����</option>
                    </select>
                    <script>selectOption("ckeywords","<%=rscom("com_keywords")%>")</script>
                    <%if checkstring(12)="1" then%>
                    <input name="ckeywordsCheck" type="checkbox" id="ckeywordsCheck" value="checkbox" onClick="checkmod(this)">
                    <select name="Crmckeywords" id="Crmckeywords" class="crmtext" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue()" >
                      <option value="">��ѡ��</option>
                      <option value="1">�Ͻ���</option>
                      <option value="2">������</option>
                      <option value="3">�Ͼ���̥�����</option>
                      <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
                      <option value="5">��ֽ</option>
                      <option value="6">�ϵ��ӵ���</option>
                      <option value="10">�ϲ���</option>
                      <option value="12">�Ͼɶ����豸</option>
                      <option value="14">��������</option>
                      <option value="15">����</option>
                    </select>
                    <script>selectOption("Crmckeywords","<%=rscrm("com_keywords")%>")</script>
                    <%else%>
                    <input name="ckeywordsCheckMod" type="checkbox" id="ckeywordsCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">��˾���ͣ�</td>
                  <td>
                    
                    
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                          <div id="ckindmain"></div>
                          <%
                          ckeywords=rscom("com_keywords")
                          if ckeywords="" then ckeywords=0
                          com_ctr_id=rscrm("com_ctr_id")
                          %>
                          <script>changekind("<%=ckeywords%>","ckind","ckindmain")</script>
                        <script>selectOption("ckind","<%=rscom("com_kind")%>")</script>
                        </td>
                        <%if checkstring(17)="1" then%>
                        <td>
                          <input name="ckindCheck" type="checkbox" id="ckindCheck" value="checkbox" onClick="checkmod(this)">
                        </td>
                        <td><div id="Crmckindmain"></div>
                          <script>changekind("<%=ckeywords%>","Crmckind","Crmckindmain")</script>
                          <script>selectOption("Crmckind","<%=rscrm("com_kind")%>")</script>
                        </td>
                        <%else%>
                        <td><input name="ckindCheckMod" type="checkbox" id="ckindCheckMod" value="checkbox"></td>
                        <%end if%>
                      </tr>
                  </table></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">����/������</td>
                  <td>
                    <script>
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
					  </script>
                    <select name="countryselect" id="countryselect" onChange="selectcountry(this.value);">
                      <option value="1" <%if com_ctr_id="1" then response.Write("selected")%>>�й�</option>
                      <option value="0" <%if com_ctr_id<>"1" then response.Write("selected")%>>��������/����</option>
                    </select>
                  </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">ʡ�У�</td>
                  <td>
                    <div id="othercountrys" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
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
                        </select>
                      <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" value="<%=replacequot1(rscrm("com_province"))%>"/>
                    </div>
                    <div id="mycountry" <%if com_ctr_id="1" or isnull(com_ctr_id) or com_ctr_id="" then response.Write("style=") else response.Write("style=display:none")%>>
                      <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">��԰��</font>
                      <input type="hidden" name="province" id="province">
                      <input type="hidden" name="city" id="city">
                      <input type="hidden" name="cprovince" id="cprovince" value="0">
                      <%
                        '--------------------------���ݵ�����ȡ�õ�������
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
                        '--------------------------ֱ��ȡ�ĵ�������
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
                            //������԰��
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="<%=ckeywords%>";//��ҵID��
                            getprovincevalue();
                        </script>
                      <script>
                        function getprovincename()
                        {
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
                      <input type="hidden" name="cprovince" id="cprovince" value="0">
                    </div>
                    <%
						if checkstring(5)="1" then
							response.Write("<font color=#ff0000>���л�԰�������޸�</font>")
						end if
						%>
                  </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">�绰��</td>
                  <td><input name="ctel" type="text" id="ctel" value="<%=trim(rscom("com_tel"))%>" size="30" maxlength="96">
                    <%if checkstring(6)="1" then%>
                    <input name="ctelCheck" type="checkbox" id="ctelCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmctel" type="text" class="crmtext" id="Crmctel" value="<%=trim(rscrm("com_tel"))%>" size="30">
                    <%else%>
                    <input name="ctelCheckMod" type="checkbox" id="ctelCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                  </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">�ֻ���</td>
                  <td><input name="cmobile" type="text" id="cmobile" value="<%=trim(rscom("com_mobile"))%>" size="30" maxlength="96">
                    <%if checkstring(7)="1" then%>
                    <input name="cmobileCheck" type="checkbox" id="cmobileCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmcmobile" type="text" class="crmtext" id="Crmcmobile" value="<%=trim(rscrm("com_mobile"))%>" size="30">
                    <%else%>
                    <input name="cmobileCheckMod" type="checkbox" id="cmobileCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                  </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">���棺</td>
                  <td><input name="cfax" type="text" id="cfax" value="<%=trim(rscom("com_fax"))%>" size="30" maxlength="96">
                    <%if checkstring(8)="1" then%>
                    <input name="cfaxCheck" type="checkbox" id="cfaxCheck" value="checkbox" onClick="checkmod(this)">
                    <input name="Crmcfax" type="text" class="crmtext" id="Crmcfax" value="<%=trim(rscrm("com_fax"))%>" size="30">
                    <%else%>
                    <input name="cfaxCheckMod" type="checkbox" id="cfaxCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">�������䣺</td>
                  <td>
                    <%
					  if rscom("com_email")="recyclinginfo@gmail.com" then
					  	redo="readonly"
					  else
					  	redo=""
					  end if
					  %>
                    <input name="newemail" type="text" id="newemail" value="<%=trim(rscom("com_email"))%>" size="30" maxlength="48" <%=redo%>>
                    <input name="cemail" type="hidden" id="cemail" value="<%=trim(rscom("com_email"))%>">
                    <%if checkstring(9)="1" then%>
                    <input name="crmCheck" type="checkbox" onClick="checkmod(this)" id="newemailCheck" value="checkbox">
                    <input name="Crmnewemail" type="text" class="crmtext" id="Crmnewemail" value="<%=trim(rscrm("com_email"))%>" size="30">
                    <%end if%>
                  </td>
                  </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">��վ��</td>
                  <td><input name="cweb" type="text" id="cweb" value="<%=trim(rscom("com_website"))%>" size="50" maxlength="255">
                    <%if checkstring(10)="1" then%>
                    <input name="cwebCheck" type="checkbox" onClick="checkmod(this)" id="cwebCheck" value="checkbox">
                    <input name="Crmcweb" type="text" class="crmtext" id="Crmcweb" value="<%=trim(rscrm("com_website"))%>" size="40">
                    <%else%>
                    <input name="cwebCheckMod" type="checkbox" id="cwebCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right">��ϵ�ˣ�</td>
                  <td><input name="ccontactp" type="text" id="ccontactp" value="<%=replacequot1(trim(rscom("com_contactperson")))%>" size="20" maxlength="48">
                    <%if checkstring(11)="1" then%>
                    <input name="ccontactpCheck" type="checkbox" onClick="checkmod(this)" id="ccontactpCheck" value="checkbox">
                    <input name="Crmccontactp" type="text" class="crmtext" id="Crmccontactp" value="<%=replacequot1(trim(rscrm("com_contactperson")))%>">
                    <%else%>
                    <input name="ccontactpCheckMod" type="checkbox" id="ccontactpCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                
                <tr bgcolor="#FFFFFF">
                  <td align="right">�ƺ���</td>
                  <td><input name="cdesi" type="text" id="cdesi" value="<%=replacequot1(trim(rscom("com_desi")))%>" size="10" maxlength="24">
                    <%if checkstring(16)="1" then%>
                    <input name="cdesiCheck" type="checkbox" id="cdesiCheck" onClick="checkmod(this)" value="checkbox">
                    <input name="Crmcdesi" type="text" class="crmtext" id="Crmcdesi" value="<%=replacequot1(trim(rscrm("com_desi")))%>">
                    <%else%>
                    <input name="cdesiCheckMod" type="checkbox" id="cdesiCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right" nowrap>��˾��飺<br>
                    (����)</td>
                  <td align="left" nowrap><textarea name="cintroduce" cols="45" rows="4" id="cintroduce"><%=replacequot1(trim(rscom("com_intro")))%></textarea>
                    <%if checkstring(13)="1" then%>
                    <input name="cintroduceCheck" type="checkbox" id="cintroduceCheck" value="checkbox" onClick="checkmod(this)">
                    <textarea name="Crmcintroduce" cols="45" rows="4" class="crmtext" id="Crmcintroduce"><%=replacequot1(trim(rscrm("com_intro")))%></textarea>
                    <%else%>
                    <input name="cintroduceCheckMod" type="checkbox" id="cintroduceCheckMod" value="checkbox">
                    <%end if%>
                  </td>
                  </tr>
                
                <tr align="center" bgcolor="#FFFFFF">
                  <td align="right">��Ӫҵ��<br>
                    (����)              </td>
                  <td align="left"><textarea name="cproductslist_en" cols="45" rows="3" id="cproductslist_en"><%=replacequot1(trim(rscom("com_productslist_en")))%></textarea>
                    <%if checkstring(14)="1" then%>
                    <input name="cproductslist_enCheck" type="checkbox" id="cproductslist_enCheck" onClick="checkmod(this)" value="checkbox">
                    <textarea name="Crmcproductslist_en" cols="45" rows="3" class="crmtext" id="Crmcproductslist_en"><%=replacequot1(trim(rscrm("com_productslist_en")))%></textarea>
                    <%else%>
                    <input name="cproductslist_enCheckMod" type="checkbox" id="cproductslist_enCheckMod" value="checkbox">
                    <%end if%>
                    </td>
                </tr>
                
                
                <tr bgcolor="#FFFFFF">
                  <td align="right" ><div align="left">��������</div></td>
                  <td>
                    <input name="com_subname" type="text" id="com_subname" value="<%=trim(rscom("com_subname"))%>">
                    
                    <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">
                    
                    .zz91.com
                    <%if checkstring(15)="1" then%>
                    <input name="com_subnameCheck" type="checkbox" id="com_subnameCheck" onClick="checkmod(this)" value="checkbox">
                    <input name="Crmcom_subname" type="text" class="crmtext" id="Crmcom_subname" value="<%=trim(rscrm("com_subname"))%>">
                    <%end if%>
                    </td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td align="right" >&nbsp;</td>
                  <td height="22"><a href="http://192.168.1.2/admin1/crmlocal/crm_tel_comp.asp?com_id=<%=idprod%>" target="_blank"><font color="#FF0000">�鿴���ۼ�¼</font></a></td>
                </tr>
                
                
                <tr align="center" bgcolor="#FFFFFF">
                  <td colspan="2" align="left">
                    <table width="100%" border="0" cellspacing="0" cellpadding="2" bgcolor="#FFCC00">
                      <tr>
                        <td width="50">��Ӫ����</td>
                        <td width="210" align="left">
                          <%
					sqlreg="select salestype,salestext,buytext,GoodCom,abroadCom,cmeet from comp_salestype where com_id="&idprod
					set rsreg=conn.execute(sqlreg)
					if not rsreg.eof or not rsreg.bof then
						salestype=rsreg("salestype")
						salestext=rsreg("salestext")
						buytext=rsreg("buytext")
						GoodCom=rsreg("GoodCom")
						abroadCom=rsreg("abroadCom")
						cmeet=rsreg("cmeet")
					end if
					rsreg.close
					set rsreg=nothing
					%>
                          <script>
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
					</script>
                          <input type="radio" name="salesType" value="1" onClick="changesales(1)" <%if salestype="1" then response.Write("checked")%>/>
                          ����
                          <input type="radio" name="salesType" value="2" onClick="changesales(2)" <%if salestype="2" then response.Write("checked")%>/>
                          �ɹ�
                          <input name="salesType" type="radio" value="0" onClick="changesales(0)" <%if salestype="0" then response.Write("checked")%>/>
                          ���߶���
                          </td>
                        <td>
                          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabzhuyin1">
                            <tr>
                              <td height="22"><font color="#FF0000">����</font>�Ĳ�Ʒ���ṩ�ķ���</td>
                            </tr>
                            <tr>
                              <td height="22"><input name="salestext" type="text" class="textt" id="salestext" size="30" maxlength="30"   value="<%=salestext%>"/>
                              (30���ַ���)</td>
                            </tr>
                          </table>
                          </td>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0"  id="tabzhuyin2">
                          <tr>
                            <td height="22"><font color="#FF0000">�ɹ�</font>�Ĳ�Ʒ����Ҫ�ķ���</td>
                            </tr>
                          <tr>
                            <td height="22"><input name="buytext" type="text" class="textt" id="buytext" size="30" maxlength="30" value="<%=buytext%>" />
                              (30���ַ���)</td>
                            </tr>
                          </table>
                        <script>changesales(<%=salestype%>)</script></td>
                        </tr>
                      </table>
                  </td>
                </tr>
                <tr align="center" bgcolor="#FFFFFF">
                  <td colspan="2" align="left">
                    <table width="100%" border="0" cellspacing="0" cellpadding="2">
                      <tr>
                        <td bgcolor="#FFCC99">
                        <iframe id="lex" name="lexin"  style="HEIGHT: 30px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="http://admin.zz91.com/admin1/localcontrol/LocalcompType.asp?com_id=<%=idprod%>"></iframe>
                        </td>
                      </tr>
                      <tr>
                        <td align="left" bgcolor="#FFCC99"><input name="GoodCom" type="checkbox" id="GoodCom" value="1" <%if GoodCom="1" then response.Write("checked")%>>
                          �Ƽ�Ϊ���ʴ�ͻ�����ģ�Ƚϴ�Ŀͻ���
                          <input name="abroadCom" type="checkbox" id="abroadCom" value="1" <%if abroadCom="1" then response.Write("checked")%>>
                          ��Դ���Թ���Ŀͻ�</td>
                      </tr>
                      <tr>
                        <td align="left" bgcolor="#FFCC99">
                          <%
						if isnull(CMeet) then
							CMeet=0
						else
							CMeet=replace(CMeet,"*","")
						end if
						%>
                          <input type="checkbox" name="CMeet1" id="CMeet1" value="1" <%if mid(CMeet,1,1)="1" then response.Write("checked")%>>�����ɹ������
                          <input type="checkbox" name="CMeet2" id="CMeet2" value="2" <%if mid(CMeet,2,1)="2" then response.Write("checked")%>>�м������
                          <input type="checkbox" name="CMeet3" id="CMeet3" value="3" <%if mid(CMeet,3,1)="3" then response.Write("checked")%>>��ͬ�бȽ�ǿ��
                          <input type="checkbox" name="CMeet4" id="CMeet4" value="4" <%if mid(CMeet,4,1)="4" then response.Write("checked")%>>�ɷ�չ�Ŀͻ�
                          <input type="checkbox" name="CMeet5" id="CMeet5" value="5" <%if mid(CMeet,5,1)="5" then response.Write("checked")%>>������˾��
                          <input type="checkbox" name="CMeet6" id="CMeet6" value="6" <%if mid(CMeet,6,1)="6" then response.Write("checked")%>>�������Ŀͻ�
                          <input type="checkbox" name="CMeet7" id="CMeet7" value="7" <%if mid(CMeet,7,1)="7" then response.Write("checked")%>>��ͻ�
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr align="center" bgcolor="#FFFFFF">
                  <td colspan="2"><input type="submit" name="Submit" value="����">
  &nbsp;&nbsp;
                  <input type="reset" name="reset" value="����"></td>
                </tr>
                </form>
              </table>
          </td>
        </tr>
    </table><%
				  rscom.close
				  set rscom=nothing
				  rscrm.close
				  set rscom=nothing
				  %>
    </td>
  </tr>
</table>
<iframe id="saveifrim" name="saveifrim"  style="HEIGHT: 0; VISIBILITY: inherit; WIDTH: 0; Z-INDEX: 2" frameborder="0" src=""></iframe>
</body>
</html><%endConnection()%>
