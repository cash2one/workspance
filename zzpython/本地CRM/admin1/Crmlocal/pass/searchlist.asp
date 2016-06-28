<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
if session("personid")="93" or session("userid")="10" then
else
response.End()
end if
sear="n="%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>密码查询情况</title>
<style type="text/css">
<!--
td {
	font-size: 12px;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#999999">
 <form id="form1" name="form1" method="post" action="searchlist.asp"> <tr>
    <td colspan="5" align="center" bgcolor="f2f2f2">Email:
      
        <input type="text" name="com_email" id="com_email" />
        查询者:      
        <select name="doperson" class="button" id="doperson" >
          <option value="" >请选择--</option>
          <%
			  if session("Partadmin")<>"0" then
			  	  sqlc="select code,meno from cate_adminuser where code like '"&session("Partadmin")&"%'"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  if session("userid")="10" then
				  sqlc="select code,meno from cate_adminuser where code like '13%'"
			  end if
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
        </select>
      <input type="submit" name="button" id="button" value="查询" /></td>
 </tr></form>
  <tr>
    <td colspan="5" bgcolor="f2f2f2">
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
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "crm_searchPass"
	 .sqlOrder= "order by id desc"
	 .sqlWhere= "WHERE not EXISTS (select id from test where id=crm_searchPass.id) "&sql
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
  %>    </td>
  </tr>
  <tr>
    <td bgcolor="f2f2f2">查询者</td>
    <td bgcolor="f2f2f2">被查询公司(Email)</td>
    <td bgcolor="f2f2f2">查询次数</td>
    <td bgcolor="f2f2f2">查询时间</td>
    <td bgcolor="f2f2f2">&nbsp;</td>
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
    
	<%
	sqluser="select com_name,com_id from comp_info where com_email='"&rs("com_email")&"'"
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
	response.Write("<a href=/admin1/crmlocal/crm_cominfoedit.asp?idprod="&rsu("com_id")&" target=_blank>"&rsu(0)&"("&rs("com_email")&")")
	end if
	rsu.close
	set rsu=nothing
	%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("Lcount")%></td>
    <td bgcolor="#FFFFFF"><%=rs("Ldate")%></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
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
</body>
</html>
