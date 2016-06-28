<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
fromdate=request("fromdate")
todate=request("todate")
sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 rsuser.close
 set rsuser=nothing
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>访问日志查询</title>
<style type="text/css">
<!--
td {
	font-size: 12px;
}
-->
</style>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#999999">
 <form id="form1" name="form1" method="post" action="viewHistory.asp"> <tr>
    <td colspan="3" align="center" bgcolor="f2f2f2">Email:
      
        <input name="com_email" type="text" id="com_email" size="10" />
        查询者:      
        <select name="doperson" class="button" id="doperson" >
          <option value="" >请选择--</option>
          <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			  	  sqlc="select code,meno from cate_adminuser where code in ("&ywadminid&") and closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'  and closeflag=1"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'  and closeflag=1"
				  end if
			  end if
			  if session("userid")="10" then
				  sqlc="select code,meno from cate_adminuser where code like '13%'  and closeflag=1"
			  end if
			  sqlc=sqlc&" and closeflag=1"
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
          <option value="" <%=sle%>>┆&nbsp;&nbsp;┿
            <%response.Write(rsc("meno"))%>
          </option>
          <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
          <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿
            <%response.Write(rsu("realname"))%>
          </option>
          <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
					 %>
        </select><br>
      <script language=javascript>createDatePicker("fromdate",true,"<%=fromdate%>",false,false,false,false)</script>
      <script language=javascript>createDatePicker("todate",true,"<%=todate%>",false,false,false,false)</script>  
      <input type="submit" name="button" id="button" value="查询" /></td>
 </tr></form>
  <tr>
    <td colspan="3" bgcolor="f2f2f2">
    <%
   sql=""
   
   if request("com_email")<>"" then
   	  sql=sql&" and com_email like '%"&request("com_email")&"%'"
	  sear=sear&"&com_email="&request("com_email")
   end if
   if request("doperson")<>"" then
   	  sql=sql&" and personid="&request("doperson")&""
	  sear=sear&"&doperson="&request("doperson")
   end if
   if request("fromdate")<>"" then
   	  sql=sql&" and fdate>='"&request("fromdate")&"'"
	  sear=sear&"&fromdate="&request("fromdate")
   end if
   if request("todate")<>"" then
   	  sql=sql&" and fdate<='"&cdate(request("todate"))&"'"
	  sear=sear&"&todate="&request("todate")
   end if
   response.Write(sql)
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "crm_viewHistory"
	 .sqlOrder= "order by id desc"
	 .sqlWhere= "WHERE not EXISTS (select id from test where id=crm_viewHistory.id) "&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 10
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=int(total/10)
  %>    </td>
  </tr>
  <tr>
    <td width="100" bgcolor="f2f2f2">查询者</td>
    <td bgcolor="f2f2f2">访问地址</td>
    <td width="200" bgcolor="f2f2f2">查询时间</td>
  </tr>
  <%
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF">
    <%sqluser="select realname from users where id="&rs("personid")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
	response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%>    </td>
    <td bgcolor="#FFFFFF">
      <%=rs("url")%>
    </td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
  </tr>
  <%
  rs.movenext
  wend
  rs.close
  set rs=nothing
  end if
  %>
</table>
<%
conn.close
set conn=nothing
%>
<br />
<a href="rizhitongji.asp"><br />
访问数统计
</a>
</body>
</html>
