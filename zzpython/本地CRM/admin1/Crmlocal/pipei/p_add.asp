<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>关联客户提交系统</title>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<script>
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name=='comid')
	   {
		  e.checked = form.chktoggle.checked;
	   }
	}
}
function getcom(form)
{
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
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
	  	form.submit()
	  }
	}
}
</script>
</head>

<body scroll=yes>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <form id="form1" name="form1" method="post" action="">
  <tr>
    <td align="right" bgcolor="#FFFFFF">公司名</td>
    <td bgcolor="#FFFFFF">
      <input type="text" name="com_name" id="com_name" />
      <input name="add" type="hidden" id="add" value="1" /></td>
    <td align="right" bgcolor="#FFFFFF">联系人</td>
    <td bgcolor="#FFFFFF"><input type="text" name="com_contactp" id="com_contactp" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">电话</td>
    <td bgcolor="#FFFFFF"><input type="text" name="com_tel" id="com_tel" /></td>
    <td align="right" bgcolor="#FFFFFF">手机</td>
    <td bgcolor="#FFFFFF"><input type="text" name="com_mobile" id="com_mobile" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">地址</td>
    <td bgcolor="#FFFFFF"><input type="text" name="com_addr" id="com_addr" /></td>
    <td align="right" bgcolor="#FFFFFF">电子邮箱</td>
    <td bgcolor="#FFFFFF"><input name="com_email" type="text" id="com_email" /></td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="查询" /></td>
    </tr>
  </form>
</table>
<br />
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">公司名称</td>
    <td bgcolor="#FFFFFF">Email</td>
    <td bgcolor="#FFFFFF">电话</td>
    <td bgcolor="#FFFFFF">手机</td>
    <td bgcolor="#FFFFFF">联系人</td>
    <td bgcolor="#FFFFFF">省份</td>
    <td bgcolor="#FFFFFF">注册时间</td>
    <td bgcolor="#FFFFFF">最后联系时间</td>
    <td bgcolor="#FFFFFF">销售者</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <form id="form2" name="form2" method="post" action="p_add_save.asp">
  <%
  sql=""
  if request("add")<>"" then
  	if trim(request("com_name"))<>"" then
	sql=sql&" and com_name like '%"&trim(request("com_name"))&"%'"
	sear=sear&"&com_name="&request("com_name")
	end if
	if trim(request("com_add"))<>"" then
	sql=sql&" and com_add like '%"&trim(request("com_add"))&"%'"
	sear=sear&"&com_add="&request("com_add")
	end if
	if trim(request("com_email"))<>"" then
	sql=sql&" and com_email like '%"&trim(request("com_email"))&"%'"
	sear=sear&"&com_email="&request("com_email")
	end if
	
	if trim(request("com_tel"))<>"" then
	sql=sql&" and (com_tel like '%"&trim(request("com_tel"))&"%' or (com_id in (select com_id from crm_personinfo where PersonTel like '%"&trim(request("com_tel"))&"%')))"
	sear=sear&"&com_tel="&request("com_tel")
	end if
	if trim(request("com_mobile"))<>"" then
	sql=sql&" and (com_mobile like '%"&trim(request("com_mobile"))&"%' or (com_id in (select com_id from crm_personinfo where PersonMoblie like '%"&trim(request("com_mobile"))&"%')))"
	sear=sear&"&com_mobile="&request("com_mobile")
	end if
	
	if request("province")<>"" and request("city")="城市" and request("province")<>"省份" then
	sql=sql&" and com_province like '%"&request("province")&"%'"
	sear=sear&"&province="&request("province")&"&city="&request("city")
	elseif request("province")<>"" and request("city")<>"" and request("province")<>"省份" and request("city")<>"城市" then
	sql=sql&" and com_province like '%"&request("city")&"%'"
	sear=sear&"&province="&request("province")&"&city="&request("city")
	end if
	
	if trim(request("com_contactp"))<>"" then
	sql=sql&" and (com_contactperson like '%"&trim(request("com_contactp"))&"%' or (com_id in (select com_id from crm_personinfo where personname like '%"&trim(request("com_contactp"))&"%')))"
	sear=sear&"&com_contactp="&request("com_contactp")
	end if
					
	  sqlm="select top 50 com_id,com_name,com_tel,com_email,com_contactperson,com_mobile,com_province,com_regtime,contactnext_time,personid from v_salescomp where not EXISTS (select com_id from crm_complink where com_id=v_salescomp.com_id) "&sql
	  'response.Write(sqlm)
	  set rs=conn.execute(sqlm)
	  if not rs.eof or not rs.bof then
	  while not rs.eof
	  %>
	  <tr>
		<td bgcolor="#FFFFFF">
		  <input type="checkbox" name="comid" id="comid" value="<%=rs("com_id")%>" />		</td>
		<td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a></td>
		<td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_province")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
		<td bgcolor="#FFFFFF"><%=rs("contactnext_time")%></td>
		<td bgcolor="#FFFFFF">
		 <%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  response.Write(realname)
				  %>        </td>
		<td bgcolor="#FFFFFF">&nbsp;</td>
	  </tr>
	  
	  <%
	  rs.movenext
	  wend
	  end if
	  rs.close
	  set rs=nothing
  end if
  %> 
  <tr>
	    <td colspan="11" bgcolor="#FFFFFF"><input name="chktoggle" type="checkbox" id="chktoggle" value="1" onClick="CheckAll(this.form)"/>
      全选      
	     
          <input type="button" name="button2" id="button2" value="提交给主管" onClick="return getcom(this.form)" /></td>
    </tr>
  </form>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>