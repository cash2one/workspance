<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
frompage=request.QueryString("frompage")
frompagequrstr=request.QueryString("frompagequrstr")
idprod=trim(request.querystring("idprod"))
cemail=trim(request.querystring("cemail"))
sqlb="select vipflag,vip_check from temp_salescomp where com_id="&idprod
set rsb=conn.execute(sqlb)
if not rsb.eof then
  if rsb("vipflag")=2 and rsb("vip_check")=1 then
  else
	  sqla="select personid from crm_Active_assign where com_id="&idprod
	  set rsa=conn.execute(sqla)
	  if not rsa.eof then
		  if cstr(session("personid"))<>cstr(rsa(0)) and zguserqx=0 then
			if session("userid")<>"10" then
				response.Write("你没有权限查看该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
				response.End()
			end if
		  end if
	  end if
	  rsa.close
	  set rsa=nothing
  end if
 end if
%>
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.crmcheckmod {
	background-color: #FFFFCC;
}
-->
</style>
</head>
<body>
<form name="form1" method="post" target="crmeidt" action="ActiveCrm_cominfoedit_re.asp?frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>#top" onSubmit="return chkfrm(this)">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">
		  <%
if idprod="" then
  response.end
end if
set rscom=server.CreateObject("ADODB.recordset")
sql="select * from comp_info where com_id="&idprod
rscom.open sql,conn,1,1
if rscom.eof or rscom.bof then
response.Write("无此信息！")
rscom.close
set rscom=nothing
response.end
end if
%>
		   <TABLE id=secTable cellSpacing=0 cellPadding=0 border=0>
                                    <TR>
                                        <TD nowrap class=selected onclick=secBoard(0)>基本信息</TD>
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_postlist_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_a','width=700,height=500')">供求信息</TD>                                       
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_messageto_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_b','width=700,height=500')">发送的询盘信息</TD>                                        
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_messagefrom_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_c','width=700,height=500')">收到的询盘信息</TD>											
                                        
										<TD width="99%" class=unselect><input name="Submit" type="submit" class="button" value="保存"> 
                          <input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="关闭">
                          <input type="button" name="Submit" class="button" value="放到我的客户里" onClick="window.location='Activecrm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1tmycrm&closed=1'">
                          <input type="button" name="Submit3" class="button" value="放到废品池" onClick="window.location='Activecrm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1twastecom&closed=1'">
                          <%
						  dotype=request("dotype")
						  if dotype<>"all" and dotype<>"xm" and dotype<>"Especial" then%>
                          <input type="button" name="Submit" class="button" value="放入公海" onClick="window.location='Activecrm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=delselec1tcrm&closed=1'">
						  <%end if%>					    </TD>
									
                			        </TR>
                			    </TABLE>
		  </td>
        </tr>
        <tr>
          <td>

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="searchtable">

             
           
            <tr>
              <td class="td_s2"><table width="100%" border="0" align="center" cellspacing="0">
                    <script language="javascript">
				  function rehit()
				  {
				  alert (document.all.crmeidt.height)
				  document.all.crmeidt.style.display="none"
				  }
function chkfrm(frm)
{
 document.all.crmeidt.style.display="none"
if(frm.cname.value.length<=0 && frm.cname_en.value.length<=0)
{
alert("请输入公司名称!");
//frm.cname.focus();
return false;
}
if(frm.cadd.value.length<=0 && frm.cadd_en.value.length<=0)
{
alert("请输入地址!");
//frm.cadd.focus();
return false;
}
if(frm.com_rank.value.length<=0)
{
alert("请选择客户等级!");
frm.com_rank.focus();
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
if(frm.cprovince.value.length<=0)
{
alert("请输入省!");
frm.cprovince.focus();
return false;
}

if(frm.ctel.value.length<=0)
{
//alert("请输入电话!");
//frm.ctel.focus();
//return false;
}
if(frm.cemail.value.length<=0)
{
alert("请输入电子邮件!");
frm.cemail.focus();
return false;
}
if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
alert("电子邮箱格式错误");
frm.cemail.focus();
return  false;
}  
if(frm.ccontactp.value.length<=0 && frm.ccontactp_en.value.length<=0)
{
alert("请输入联系人!");
frm.ccontactp.focus();
return false;
}
/*if(frm.cdesi.value.length<=0)
{
alert("请输入联系人称呼!");
frm.cdesi.focus();
return false;
}*/
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
	infobox.className = "text";
	infobox.readOnly=true
	}
}
                </script>
                    
                      <tr align="center"> 
                        <td colspan="6"><a name="top"></a> <iframe id=crmeidt name=crmeidt height="1" style="VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="###kang"></iframe>                        </td>
                      </tr>
                      
                      <tr> 
                        <td width="13%" align="right">
						 <%
					  sql="select * from comp_sales where com_id="&idprod&""
					  set rssales=conn.execute(sql)
					  if rssales.eof then
					    sqls="select top 1 * from comp_tel where com_id="&idprod&" order by id desc"
						set rss=server.CreateObject("adodb.recordset")
						rss.open sqls,conn,1,2
						if not rss.eof then
						contacttype=rss("contacttype")
						com_type=rss("com_type")
						com_rank=rss("com_rank")
						contactnext_time=rss("contactnext_time")
						contactkind=rss("contactkind")
						com_kind=rss("com_kind")
						com_sting=rss("com_sting")
						else
						com_sting=0
						end if
						rss.close
						set rss=nothing
						com_Especial="0"
					else
					    contactType=rssales("contactType")
						com_type=rssales("com_type")
						com_rank=rssales("com_rank")
						contactnext_time=rssales("contactnext_time")
						xiaoShouFlag=rssales("xiaoShouFlag")
						com_kind=rssales("com_kind")
						com_sting=rssales("com_sting")
						com_Especial=rssales("com_Especial")
					end if
					rssales.close
					set rssales=nothing
					  %>
						公司名称：<input name="re" type="hidden" id="re" value="1"><input name="crmCheck" type="checkbox" id="cnameCheck" value="1" onClick="checkmod(this)"></td>
                        <td colspan="3">
                          <input name="cname" class="text" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" readonly maxlength="96"> 
                          <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>"> 
                          <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">
						 特殊意向客户<input type="radio" <%if com_Especial="1" then response.Write("checked")%> name="com_Especial" value="1">
是
  <input type="radio" name="com_Especial" value="0" <%if com_Especial="0" then response.Write("checked")%>>
否
					    </td>
                        <td align="right" nowrap>地址：<input name="crmCheck" type="checkbox" id="caddCheck" value="2" onClick="checkmod(this)"></td>
                        <td nowrap>
                          <input name="cadd" type="text" class="text" id="cadd" value="<%=replacequot1(trim(rscom("com_add")))%>" size="50" readonly maxlength="255"></td>
                      </tr>
                      <tr> 
                        <td align="right" nowrap>国家/地区：<input name="crmCheck" type="checkbox" id="ccountryCheck" value="3" onClick="checkmod(this)"></td>
                        <td>
<%getcountrylist trim(rscom("com_ctr_id")) 'ccountry%></td>
                        <td align="right" nowrap>邮编：<input name="crmCheck" type="checkbox" id="czipCheck" value="4" onClick="checkmod(this)"></td>
                        <td>
<input name="czip" type="text" class="text" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20" readonly></td>
                        <td align="right">省市：<input name="crmCheck" type="checkbox" id="cprovinceCheck" value="5" onClick="checkmod(this)"></td>
                        <td>
						<SELECT id="province" name="province"></SELECT>
<SELECT id="city" name="city"></SELECT>
<SCRIPT type=text/javascript>
	//类的调用
	new Dron_City("province","city",'<%=replacequot1(trim(rscom("com_province")))%>').init();
</SCRIPT>
                        <input name="textfield" type="text" class="text" readonly size="8" value="<%response.Write(replacequot1(trim(rscom("com_province"))))%>"></td>
                      </tr>
                    
                      <tr> 
                        <td align="right">电话：<input name="crmCheck" type="checkbox" id="ctelCheck" value="6" onClick="checkmod(this)"></td>
                        <td colspan="3">
<input name="ctel" type="text" class="text" id="ctel" readonly value="<%=trim(rscom("com_tel"))%>" size="30" maxlength="96"></td>
                        <td align="right">手机：<input name="crmCheck" type="checkbox" id="cmobileCheck" value="7" onClick="checkmod(this)"></td>
                        <td>
<input name="cmobile" type="text" class="text" id="cmobile" readonly value="<%=trim(rscom("com_mobile"))%>" size="30" maxlength="96" ></td>
                      </tr>
                      <tr> 
                        <td align="right">传真：<input name="crmCheck" type="checkbox" id="cfaxCheck" value="8" onClick="checkmod(this)"></td>
                        <td colspan="3">
                          
                          <input name="cfax" type="text" class="text" id="cfax" readonly value="<%=trim(rscom("com_fax"))%>" size="30" maxlength="96"></td>
                        <td align="right" nowrap>电子邮箱：<input name="crmCheck" type="checkbox" id="newemailCheck" value="9" onClick="checkmod(this)"></td>
                        <td>
<%
					  if rscom("com_email")="recyclinginfo@gmail.com" then
					  redo="readonly"
					  else
					  redo=""
					  end if
					  %> <input name="newemail" type="text" class="text" id="newemail" readonly value="<%=trim(rscom("com_email"))%>" size="30" maxlength="48" <%=redo%>> 
                          <input name="cemail" type="hidden" id="cemail" value="<%=trim(rscom("com_email"))%>"></td>
                      </tr>
                      <tr> 
                        <td align="right">网站：<input name="crmCheck" type="checkbox" id="cwebCheck" value="10" onClick="checkmod(this)"></td>
                        <td colspan="3"> 
                          <input name="cweb" type="text" class="text" id="cweb" readonly value="<%=trim(rscom("com_website"))%>" size="30" maxlength="255"></td>
                        <td align="right">联系人：<input name="crmCheck" type="checkbox" id="ccontactpCheck" value="11" onClick="checkmod(this)"></td>
                        <td>
<input name="ccontactp" type="text" class="text" id="ccontactp" readonly value="<%=replacequot1(trim(rscom("com_contactperson")))%>" size="20" maxlength="48">
                          <%
						  select case rscom("com_desi")
						  case "先生"
						  deselect1="selected"
						  deselect2=""
						  case "女士"
						  deselect1=""
						  deselect2="selected"
						  end select
						  
						  %>
						  <input name="crmCheck" type="checkbox" id="cdesiCheck" value="16" onClick="checkmod(this)">
						  <select name="cdesi" id="cdesi">
                            <option value="先生" <%response.Write(deselect1)%>>先生</option>
                            <option value="女士" <%response.Write(deselect2)%>>女士</option>
                          </select>						  </td>
                      </tr>
                      <tr> 
                        <td align="right">公司类型：<input name="crmCheck" type="checkbox" id="ckindCheck" value="12" onClick="checkmod(this)"></td>
                        <td colspan="3">
<%getcomkindlist trim(rscom("com_kind"))%></td>
                        <td><div align="right">二级域名：<input name="crmCheck" type="checkbox" id="com_subnameCheck" value="15" onClick="checkmod(this)"></div></td>
                        <td>
						 
<input name="com_subname" type="text" class="text" readonly id="com_subname" value="<%=trim(rscom("com_subname"))%>"> 
                          <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">
                          .zz91.com						</td>
                      </tr>
                      <tr> 
                        <td align="right">公司简介：<input name="crmCheck" type="checkbox" id="cintroduceCheck" value="13" onClick="checkmod(this)"></td>
                        <td colspan="3" align="left" valign="top">
<textarea name="cintroduce" cols="50" rows="4" readonly id="cintroduce"><%=replacequot1(trim(rscom("com_intro")))%></textarea></td>
                        <td align="right">主营业务：<input name="crmCheck" type="checkbox" id="cproductslist_enCheck" value="14" onClick="checkmod(this)"></td>
                        <td align="left" valign="top">
						
<textarea name="cproductslist_en" cols="50" readonly rows="4" id="cproductslist_en"><%=replacequot1(trim(rscom("com_productslist_en")))%></textarea>						</td>
                      </tr>
                     
                
                     
                      <tr align="center">
                        <td align="right" nowrap>所处行业：</td>
                        <td colspan="5" align="left">
						<%
								if rscom("com_keywords")="" or isnull(rscom("com_keywords")) then
								keywords=0
								else
								keywords=rscom("com_keywords")
								end if
	                            getcomkeywords  keywords
							    %>						</td>
                      </tr>
                      <tr align="center">
                        <td align="right" nowrap>客户类型：</td>
                        <td colspan="5" align="left">
						<%sqlk="select * from cate_compkind"
								set rsk=server.CreateObject("adodb.recordset")
								rsk.open sqlk,conn,1,2
								if not rsk.eof then
								do while not rsk.eof 
								sqla="select KindID from comp_comKind where com_id="&idprod&""
								set rsa=server.CreateObject("adodb.recordset")
								rsa.open sqla,conn,1,2
								if not rsa.eof then
								do while not rsa.eof
								if cstr(rsk("code"))=cstr(rsa("kindID")) then
								checkvalue="checked"
								else
								checkvalue=""
								end if
								rsa.movenext
								loop
								end if
								rsa.close
								set rsa=nothing
								%>
								<input name="comkindof" type="checkbox" <%=checkvalue%> id="comkindof" value="<%response.Write(rsk("code"))%>"><%response.Write(rsk("meno"))%>
								<%
								rsk.movenext
								loop
								end if
								rsk.close
								set rsk=nothing
								%>						</td>
                      </tr>
                  </table>
                </td>
            </tr>
           
          </table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25" align="center" bgcolor="#243F74">
                        <script>
						function jlcontact(jl)
						{
						    //alert (jlflag.style.display)
							if (jl==1)
							{
							jlflag.style.display=""
							form1.contactflag.value="1"
							}
							if (jl==0)
							{
							jlflag.style.display="none"
							form1.contactflag.value="0"
							}
						}
						function lxcontact(jl)
						{
							if (jl==1)
							{
							lxflag.style.display=""
							form1.lxcontactflag.value="1"
							}
							if (jl==0)
							{
							lxflag.style.display="none"
							form1.lxcontactflag.value="0"
							}
						}
						</script>
						<input type="button" class=button name="Submit" onClick="jlcontact(1)" value="销售记录">
                        <input type="button" name="Submit" onClick="jlcontact(0)" class=button value="×取消记录">
                        <input name="contactflag" type="hidden" id="contactflag" value="0">
						<input name="lxcontactflag" type="hidden" id="lxcontactflag" value="0">
						<input type="button" class=button name="Submit" onClick="lxcontact(1)" value="增加联系人">
						<input type="button" class=button name="Submit" onClick="lxcontact(0)" value="×取消增加联系人">
&nbsp;&nbsp;&nbsp;&nbsp;		
					<input name="Submit4" type="submit" class="button" value="保存">
				 </td>
  </tr>
</table>
<%
dip="style=display:none"
%>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="jlflag"  class="searchtable" <%=dip%>>
  <tr align="center">
    <td width="140" align="right" bgcolor="#FFDBA4">下次联系时间：</td>
    <td align="left" bgcolor="#FFDBA4"><%
						sqld="select fdate from crm_Active_Assign where com_id="&idprod&""
						set rsd=conn.execute(sqld)
						if not rsd.eof then
							webl=datediff("d",cdate(rsd(0)),now)
							if webl>=0 and webl<=7 then
								contactdate=cdate(rsd(0))+7
								contacttimes=1
							end if
							if webl>7 and webl<=14 then
								contactdate=cdate(rsd(0))+14
								contacttimes=2
							end if
							if webl>14 and webl<=21 then
								contactdate=cdate(rsd(0))+21
								contacttimes=3
							end if
							if webl>21 and webl<=28 then
								contactdate=cdate(rsd(0))+28
								contacttimes=4
							end if
						else
								contactdate=now
								contacttimes=1
						end if
						rsd.close
						set rsd=nothing
						response.Write(contactdate)
						%>
      <input name="contactnext_time" type="hidden" id="contactnext_time" value="<%=contactdate%>">
        
      <input name="contactTimes" type="hidden" id="contactTimes" value="<%=contacttimes%>"></td>
    <td colspan="4" align="left" bgcolor="#FFDBA4">客户等级：
      <select name="com_rank" id="com_rank" style="background:#FFFFCC">
          <option value="">--请选择等级--</option>
          <%
					  sqlcate="select * from cate_kh_csd order by code asc"
					  set rscc=server.CreateObject("ADODB.recordset")
					  rscc.open sqlcate,conn,1,2
					  if not rscc.eof then 
					  do while not rscc.eof
					  if isnull(com_rank) then
					  else
						  if cstr(rscc("meno"))=cstr(com_rank) then
						  rankchecked="selected"
						  else
						  rankchecked=""
						  end if
					  end if
					  %>
          <option value="<%=rscc("meno")%>" <%=rankchecked%>>
            <%for i=0 to cint(rscc("meno"))-1%>
            ★
            <%next%>
            <%=rscc("bz")%></option>
          <%
					  rscc.movenext
					  loop
					  end if
					  rscc.close()
					  set rscc=nothing
					  %>
        </select>
      联系情况：
      <%
						if not isnull(contacttype) then
						contacttype=""
						
						%>
      <%call cate_radio1("cate_contact_about","contacttype",trim(contacttype))%>
      <%end if
						 %>
    </td>
  </tr>
  <tr align="center">
    <td align="right" bgcolor="#FFFFCC">兴趣点：</td>
    <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case1" rows="3" id="case1"></textarea></td>
    <td align="right" bgcolor="#FFFFCC">异议点：</td>
    <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case2" rows="3" id="case2"></textarea></td>
    <td align="right" bgcolor="#FFFFCC">下次跟进点：</td>
    <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case3" rows="3" id="case3"></textarea></td>
  </tr>
  <tr align="center">
    <td align="right" bgcolor="#FFFFCC">联系内容：</td>
    <td colspan="5" align="left" bgcolor="#FFFFCC"><textarea name="detail" cols="50" rows="5" id="detail"></textarea></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="lxflag" <%=dip%> class="searchtable">
  <tr>
    <td bgcolor="#FFCCCC"><table width="100%" border="0" cellspacing="0" cellpadding="3">
      <tr>
        <td align="right" width="130">联系人：</td>
        <td nowrap><input name="PersonName" type="text" id="PersonName">
          <input name="PersonSex" type="radio" value="1" checked>
          先生
          <input type="radio" name="PersonSex" value="0">
          女士</td>
        <td align="right">关键联系人： </td>
        <td>是
          <input type="radio" name="PersonKey" value="1">
          不是
          <input name="PersonKey" type="radio" value="0" checked></td>
        <td align="right">职务：</td>
        <td><input name="PersonStation" type="text" id="PersonStation"></td>
      </tr>
      <tr>
        <td align="right">电话：</td>
        <td><input name="PersonTel" type="text" id="PersonTel"></td>
        <td align="right">手机：</td>
        <td><input name="PersonMoblie" type="text" id="PersonMoblie"></td>
        <td align="right">Email：</td>
        <td><input name="PersonEmail" type="text" id="PersonEmail"></td>
      </tr>
      
      <tr>
        <td align="right">传真： </td>
        <td><input name="PersonFax" type="text" id="PersonFax"></td>
        <td align="right">其他联系方式：</td>
        <td><input name="PersonOther" type="text" id="PersonOther"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="right">备注：</td>
        <td colspan="5"><textarea name="PersonBz" rows="3" id="PersonBz"></textarea></td>
        </tr>
    </table></td>
  </tr>
</table>  
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td><iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 100px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="crm_personinfo.asp?com_id=<%=rscom("com_id")%>"></iframe></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td><iframe id=topt name=topt  style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="ActiveCrm_tel_comp.asp?com_id=<%=rscom("com_id")%>"></iframe></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><input name="Submit" type="submit" class="button" value="保存"> 
&nbsp;&nbsp;                          &nbsp;&nbsp; <input name="reset2" type="button" class="button" value="关闭" onClick="parent.window.close();"></td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</td>
        </tr>
    </table>
	
	</td>
  </tr>
<%
				  rscom.close
				  set rscom=nothing
				  conn.close
				  set conn=nothing
				  %>              
</table></form>
</body>
</html>