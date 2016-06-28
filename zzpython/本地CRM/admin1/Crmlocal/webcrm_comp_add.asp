<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>添加公司</title>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
</head>

<body scroll=yes>

<table width="100%"  border="0" align="center" cellpadding="4" cellspacing="0" class=se id=ListTable>
<script language="javascript">
function chkfrm(frm)
{
if(frm.cname.value.length<=0)
{
alert("请输入公司名称!");
frm.cname.focus();
return false;
}
if(frm.cadd.value.length<=0)
{
alert("请输入地址!");
frm.cadd.focus();
return false;
}
/*if(frm.czip.value.length<=0)
{
alert("请输入邮编!");
frm.czip.focus();
return false;
}*/

if(frm.province.value=="省份")
{
alert("请输入省!");
frm.province.focus();
return false;
}

if(frm.ctel.value.length<=0)
{
alert("请输入电话!");
frm.ctel.focus();
return false;
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
if(frm.ccontactp.value.length<=0)
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
}
function enteremail(frm)
{
	if (frm.cmobile.value=="" && frm.ctel.value=="")
	{
		alert ("请输入联系方式！")
	}else
	{
	    if (frm.cmobile.value!="")
		{
		frm.cemail.value=frm.cmobile.value+"@zz91.com"
		}else
		{
		frm.cemail.value=frm.ctel.value+"@zz91.com"
		}
	}
}
                </script>
                  <form name="form1" method="post" action="http://www.zz91.com/admin1/Crmlocal/webcrm_comp_save.asp" onSubmit="return chkfrm(this)">
                   
                    <tr bgcolor="#D9ECFF">
                      <td height="30" colspan="4" class="tbar"><span class="bold">基本信息</span></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td width="80" align="right">公司名称：                      </td>
                      <td width="223"><input name="cname" type="text" id="cname" class=text value="" size="30" maxlength="96">                      </td>
                      <td width="77" align="right" nowrap>国家/地区：</td>
                      <td width="272"><SELECT id="province" name="province"></SELECT>
<SELECT id="city" name="city"></SELECT>
<SCRIPT type=text/javascript>
	//类的调用
	new Dron_City("province","city",'').init();
</SCRIPT></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">地址：</td>
                      <td colspan="3"><input name="cadd" type="text" class=text id="cadd" size="50">
                      邮编：
                      <input class=text name="czip" type="text" id="czip" size="20" maxlength="48"></td>
                    </tr>
					
					
					
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">电话：</td>
                      <td><input name="ctel" type="text" id="ctel" class=text size="30" maxlength="96"></td>
                      <td align="right">手机：</td>
                      <td><input name="cmobile" type="text" id="cmobile" class=text size="30" maxlength="96"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">传真：</td>
                      <td><input name="cfax" type="text" class=text id="cfax" size="30" maxlength="96"></td>
                      <td align="right">电子邮箱：</td>
                      <td nowrap><input name="cemail" class=text type="text" id="cemail" size="30" maxlength="48" <%=redo%>>
                        <input name="Submit" type="button" class="button" onClick="enteremail(this.form)" value="生成">
                        <br>
                      如果没有email请用 (手机或电话@zz91.com)</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">网站：</td>
                      <td colspan="3"><input name="cweb" class=text type="text" id="cweb" size="50" maxlength="255"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">联系人：</td>
                      <td colspan="3"><input class=text name="ccontactp" type="text" id="ccontactp" size="20" maxlength="48">
                      称呼：
                      <input type="radio" name="cdesi" id="cdesi" <%=ccf1%> value="先生">
                          先生 
                          <input type="radio" name="cdesi" id="cdesi" <%=ccf2%> value="女士">
                          女士</td>
                    </tr>
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">公司类型：</td>
                      <td colspan="3" align="left"><%getcomkindlist "0"%></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" nowrap>公司简介：</td>
                      <td colspan="3" align="left"><textarea name="cintroduce" cols="60" rows="10" id="cintroduce"></textarea></td>
                    </tr>
                   
                    <tr align="center" bgcolor="#FFFFFF">
                      <td align="right">主营业务：</td>
                      <td colspan="3" align="left"><textarea name="cproductslist_en" cols="50" rows="4" id="cproductslist_en"></textarea></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >客户类型：</td>
                      <td colspan="3">
                      
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <%
					  sqlt="select code,meno from cate_webCompType where code like '__'"
					  set rst=conn.execute(sqlt)
					  if not rst.eof or not rst.bof then
					  while not rst.eof 
					  %>
                        <tr>
                          <td width="100"><%=rst("meno")%></td>
                          <td>
                          <%
						  sqlt1="select code,meno from cate_webCompType where code like '"&rst("code")&"__'"
						  set rst1=conn.execute(sqlt1)
						  if not rst1.eof or not rst1.bof then
						  while not rst1.eof 
						  %>
                          <input name="compkind" type="checkbox" id="compkind" value="<%=rst1("code")%>"><%=rst1("meno")%>
                          <%
						  rst1.movenext
						  wend
						  end if
						  rst1.close
						  set rst1=nothing
						  %>
                          </td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  end if
					  rst.close
					  set rst=nothing
					  %>
                      </table>
                                            
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >客户等级：</td>
                      <td colspan="3">
                      <%
					  
					  sqlcate="select * from cate_kh_csd order by code desc"
					  set rscc=server.CreateObject("ADODB.recordset")
					  rscc.open sqlcate,conn,1,1
					  if not rscc.eof then 
					  do while not rscc.eof
					  if cint(rscc("meno"))=cint(com_rank) then
					  rankchecked="checked"
					  else
					  rankchecked=""
					  end if
					  %>                        
                        <input type="radio" <%=rankchecked%> name="com_rank" value="<%=rscc("meno")%>">
                        <%
					  for i=0 to cint(rscc("meno"))-1
					  %>
                        <img src="../../admin1/newimages/art1.gif" width="13" height="12" align="absmiddle">
                      <%next
					  rscc.movenext
					  loop
					  end if
					  rscc.close()
					  set rscc=nothing
					  %></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >二级域名：</td>
                      <td>
					  <input name="com_subname" class=text type="text" id="com_subname">
					
					  <input name="com_subname1" type="hidden" id="com_subname1">
					  
                        .zz91.com</td>
                      <td nowrap>&nbsp;</td>
                      <td>                      </td>
                    </tr>
                    <tr align="center" bgcolor="#FFFFFF">
                      <td colspan="4" ><input name="Submit" type="submit" class="button" value="保存"></td>
                    </tr>
                  </form>
</table>
</body>
</html>
