<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="adfsfs!@#.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="../../color.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css" />
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
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
 <form id="form1" name="form1" method="post" action=""> <tr>
    <td bgcolor="#CCCCCC">诚信通</td>
    <td bgcolor="#CCCCCC">
	<select name="cxt">
	  <option value="">请选择..</option>
      <option value="1" <%if request("cxt")="1" then response.Write("selected")%>>诚信通</option>
      <option value="0" <%if request("cxt")="0" then response.Write("selected")%>>普通</option>
    </select>    </td>
    <td bgcolor="#CCCCCC">公司名称</td>
    <td bgcolor="#CCCCCC"><input name="com_name" type="text" id="com_name" /></td>
    <td bgcolor="#CCCCCC">地址</td>
    <td bgcolor="#CCCCCC"><input name="com_addr" type="text" id="com_addr" /></td>
  </tr>
  <tr>
    <td bgcolor="#CCCCCC">电话</td>
    <td bgcolor="#CCCCCC"><input name="com_tel" type="text" id="com_tel" /></td>
    <td bgcolor="#CCCCCC">手机</td>
    <td bgcolor="#CCCCCC"><input name="com_mobile" type="text" id="com_mobile" /></td>
    <td bgcolor="#CCCCCC">联系人</td>
    <td bgcolor="#CCCCCC"><input name="com_contactperson" type="text" id="com_contactperson" /></td>
  </tr>
  <tr>
    <td bgcolor="#CCCCCC">省份</td>
    <td bgcolor="#CCCCCC"><input name="com_province" type="text" id="com_province" /></td>
    <td bgcolor="#CCCCCC">城市</td>
    <td bgcolor="#CCCCCC"><input name="com_city" type="text" id="com_city" /></td>
    <td bgcolor="#CCCCCC">性别</td>
    <td bgcolor="#CCCCCC">
	<select name="com_desi">
      <option value="" selected="selected">请选择..</option>
      <option value="先生">先生</option>
      <option value="女士">女士</option>
    </select>
	<input name="Submit" type="submit" class="button" value="搜索" /></td>
  </tr>
</form>
</table>
<%
		sear="n="
		sql=""
		if request("cxt")<>"" then
			sql=sql&" and cxt="&request("cxt")&""
			sear=sear&"&cxt="&request("cxt")
		end if
		if request("com_name")<>"" then
			sql=sql&" and com_name like '%"&request("com_name")&"%'"
			sear=sear&"&com_name="&request("com_name")
		end if
		if request("com_addr")<>"" then
			sql=sql&" and com_addr like '%"&request("com_addr")&"%'"
			sear=sear&"&com_addr="&request("com_addr")
		end if
		if request("com_tel")<>"" then
			sql=sql&" and com_tel like '%"&request("com_tel")&"%'"
			sear=sear&"&com_tel="&request("com_tel")
		end if
		if request("com_mobile")<>"" then
			sql=sql&" and com_mobile like '%"&request("com_mobile")&"%'"
			sear=sear&"&com_mobile="&request("com_mobile")
		end if
		if request("com_contactperson")<>"" then
			sql=sql&" and com_contactperson like '%"&request("com_contactperson")&"%'"
			sear=sear&"&com_contactperson="&request("com_contactperson")
		end if
		if request("com_province")<>"" then
			sql=sql&" and com_province like '%"&request("com_province")&"%'"
			sear=sear&"&com_province="&request("com_province")
		end if
		if request("com_city")<>"" then
			sql=sql&" and com_city like '%"&request("com_city")&"%'"
			sear=sear&"&com_city="&request("com_city")
		end if
		if request("com_desi")<>"" then
			sql=sql&" and com_desi like '%"&request("com_desi")&"%'"
			sear=sear&"&com_desi="&request("com_desi")
		end if
		response.Write(sql)
		Set oPage=New clsPageRs2
		With oPage
		 .sltFld  = "*"
		 .FROMTbl = "company1"
		 .sqlOrder= "order by id desc"
		 .sqlWhere= "WHERE not EXISTS (select cid from pipeicomp where cid=company1.id) "&sql
		 .keyFld  = "id"    '不可缺少
		 .pageSize= 10
		 .getConn = conn
		 Set Rs  = .pageRs
		End With
		total=oPage.getTotalPage
		oPage.pageNav "?"&sear,""
		totalpg=cint(total/10)
		if total/10 > totalpg then
		totalpg=totalpg+1
		end if
if not rs.eof then			   
%>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td nowrap="nowrap" bgcolor="#CCCCCC">&nbsp;</td>
    <td nowrap="nowrap" bgcolor="#CCCCCC">&nbsp;</td>
    <td nowrap="nowrap" bgcolor="#CCCCCC">诚信通</td>
    <td bgcolor="#CCCCCC">公司名称</td>
    <td bgcolor="#CCCCCC">电话</td>
    <td bgcolor="#CCCCCC">手机</td>
    <td bgcolor="#CCCCCC">传真</td>
    <td bgcolor="#CCCCCC">联系人</td>
    <td bgcolor="#CCCCCC">性别</td>
    <td bgcolor="#CCCCCC">职务</td>
    <td bgcolor="#CCCCCC">省份</td>
    <td bgcolor="#CCCCCC">城市</td>
    <td bgcolor="#CCCCCC">国家</td>
    <td bgcolor="#CCCCCC">地址</td>
    <td bgcolor="#CCCCCC">邮编</td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
  </tr>
  <form id="form2" name="form2" method="post" action="company_save.asp">
 <%
 i=0
 while not rs.eof
 if i mod 2 =0 then
 bg="#f2f2f2"
 else
 bg="#ffffff"
 end if
 %>
  <tr>
    <td bgcolor="<%=bg%>"><input name="comid" type="checkbox" id="comid" value="checkbox" /></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><a href="company_save.asp?id=<%=rs("id")%>" target="_blank">放入我的客户</a></td>
    <td bgcolor="<%=bg%>">
	<%
	if rs("cxt")="1" then
	response.Write("<font color=#ff0000>诚信通</font>")
	else
	response.Write("普通")
	end if
	%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><a href="company_show.asp?id=<%=rs("id")%>" target="_blank"><%=rs("com_name")%></a></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_tel")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_mobile")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_fax")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_contactperson")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_desi")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_station")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_province")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_city")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_country")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_addr")%></td>
    <td nowrap="nowrap" bgcolor="<%=bg%>"><%=rs("com_zip")%></td>
    <td bgcolor="#FFFFFF">
	<%
	com_subname=trim(rs("weburl"))
	if rs("cxt")="1" then
	com_subname=replace(com_subname,".cn.alibaba.com/","")
	com_subname=replace(com_subname,"http://","")
	else
	com_subname=replace(com_subname,"http://china.alibaba.com/company/detail/","")
	com_subname=replace(com_subname,".html","")
	end if
	response.Write(com_subname)
	%>	</td>
  </tr>
  
  <%
  i=i+1
  rs.movenext
  wend
  
  %>
  <tr>
    <td colspan="16" bgcolor="#FFFFFF">
      <input name="chktoggle" type="checkbox" id="chktoggle" value="1" onClick="CheckAll(this.form)"/>
      全选      </td>
  </tr></form>
</table>
<%
end if
%>
</body>
</html>
