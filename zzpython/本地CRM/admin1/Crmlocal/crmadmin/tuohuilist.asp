<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<%
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="tuohuilist.asp"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30">
    <%
	   if ywadminid<>"0" and ywadminid<>"" and not isnull(ywadminid) then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
				 setlect=1
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
					 setlect=1
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  sqlc=sqlc&" and closeflag=1"
			  if setlect=1 then
	%>操作者：
    <select name="dopersonid" class="button" id="dopersonid" >
              <option value="" >请选择--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("dopersonid"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
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
            <%end if%>
    email:
    <input name="com_email" type="text" id="com_email" size="20" value="<%=request("com_email")%>" />
    <input type="radio" name="comp_sale_type" id="radio" value="拖单" />
    拖单
    <input type="radio" name="comp_sale_type" id="radio2" value="毁单" />
    毁单
    <input type="submit" name="button" id="button" class="button" value="搜索" /> <input type="button" name="button2" id="button2"  class="button" value="添加拖/毁单" onClick="window.location='tuohui_add.asp'" /></td>
  </tr>
</table></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><%
	sear=sear&"n="
	sql=""
	if session("userid")<>"10" then
		'sql=sql&" and personid="&session("personid")&""
	end if
	if ywadminid<>"0" and ywadminid<>"" and not isnull(ywadminid) then
		sql=sql&" and personid in (select id from users where userid in("&ywadminid&"))"
	elseif session("userid")<>"10" then
		sql=sql&" and personid="&session("personid")&""
	end if
	if request("dopersonid")<>"" then
		sql=sql&" and personid="&request("dopersonid")&""
		sear=sear&"&dopersonid="&request("dopersonid")
	end if
	if request("comp_sale_type")<>"" then
		sql=sql&" and comp_sale_type='"&request("comp_sale_type")&"'"
		sear=sear&"&comp_sale_type="&request("comp_sale_type")
	end if
	if request("com_email")<>"" then
		sql=sql&" and exists(select com_id from comp_info where com_id=crm_tuo_hui_comp.com_id and com_email like '"&request("com_email")&"%')"
		sear=sear&"&personid="&request("com_email")
	end if

	               sqlorder=" order by fdate desc"
	               Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
					 .FROMTbl = "crm_tuo_hui_comp"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select id from test where id=crm_tuo_hui_comp.id) "&sql
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 20
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/20)
				   if total/20 > totalpg then
				   	  totalpg=totalpg+1
				   end if
	%></td>
    </tr>
  </table></td>
</tr>
</table>
<table width="100%" border="0" class=ee id=ListTable cellspacing="0" cellpadding="5">
  <tr  class="topline">
    <td>公司名称</td>
    <td>现在所有者</td>
    <td>操作人员</td>
    <td>最近登录</td>
    <td>登录次数</td>
    <td>最后有效联系时间</td>
    <td>下次联系时间</td>
    <td>邮箱</td>
    <td>拖单或毁单</td>
    <td>原因</td>
    <td width="80">操作时间</td>
    <td width="80">操作</td>
  </tr>
  <%
  comidList=""
  comidListid=""
  if not rs.eof then
  do while not rs.eof
  
  comidList=comidList&rs("com_id")&","
  comidListid=comidListid&rs("com_id")&"|"&rs("id")&","
  com_id=rs("com_id")
  id=rs("id")
  sqlc="select com_name,com_email from comp_info where com_id="&rs("com_id")
  set rsc=conn.execute(sqlc)
  if not rsc.eof or not rsc.bof then
  	com_name=rsc("com_name")
	com_email=rsc("com_email")
  end if
  rsc.close
  set rsc=nothing
  sqlz="select count(0) from comp_zstinfo where com_id="&rs("com_id")&" and com_check=1"
  set rsz=conn.execute(sqlz)
  if rsz(0)>0 then
  	vip=1
  else
  	vip=0
  end if
  rsz.close
  set rsz=nothing
  %>
  <tr>
    <td><a href="../crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=my&lmcode=2701" target="_blank"><%response.Write(com_name)%></a>
    <%if vip=1 then response.Write("<font color=#ff0000>【再生通】</font>")%>
    </td>
    <td><div id="xiansuoy<%=com_id%><%=id%>"></div>
    
    </td>
    <td>
	<%
	sqlu="select realname from users where id="&rs("personid")&""
	set rsu=conn.execute(sqlu)
	if not rsu.eof then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%></td>
    <td><div id="lastlogintime<%=com_id%><%=id%>"></div></td>
    <td><div id="logincount<%=com_id%><%=id%>"></div></td>
    <td><div id="lastteldate<%=com_id%><%=id%>"></div></td>
    <td><div id="contactnext_time<%=com_id%><%=id%>"></div></td>
    <td><%response.Write(com_email)%></td>
    <td><%response.Write(rs("comp_sale_type"))%></td>
    <td>
    <%=rs("reson")%>
    </td>
    <td><%=rs("fdate")%></td>
    <td><a href="../crm_tel_comp.asp?com_id=<%=rs("com_id")%>&telflag=0" target="_blank">查看小记</a></td>
  </tr>
  <%
  rs.movenext
  loop
  end if
  rs.close
  set rs=nothing
  %>
</table>
</body>
</html>
<script>
<%
comidList=left(comidList,len(comidList)-1)
comidListid=left(comidListid,len(comidListid)-1)
arrcomidListid=split(comidListid,",")
if comidList<>"" then
	 sqlm="select a.realname,b.com_id from users as a,crm_assign as b where a.id=b.personid and b.com_id in ("&comidList&")"
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			Str=rsm("realname")
			for i=0 to ubound(arrcomidListid)
				existscomid=split(arrcomidListid(i),"|")
				if cstr(existscomid(0))=cstr(com_id) then
					response.Write("document.getElementById(""xiansuoy"&com_id&existscomid(1)&""").innerHTML="""&Str&""";"&chr(13))
				end if
			next
			
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
	 
	 sqlm="select lastlogintime,logincount,lastteldate,contactnext_time,com_id from v_salescomp where com_id in ("&comidList&")"
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			
			for i=0 to ubound(arrcomidListid)
				existscomid=split(arrcomidListid(i),"|")
				if cstr(existscomid(0))=cstr(com_id) then
					response.Write("document.getElementById(""lastlogintime"&com_id&existscomid(1)&""").innerHTML="""&rsm("lastlogintime")&""";"&chr(13))
					response.Write("document.getElementById(""logincount"&com_id&existscomid(1)&""").innerHTML="""&rsm("logincount")&""";"&chr(13))
					response.Write("document.getElementById(""lastteldate"&com_id&existscomid(1)&""").innerHTML="""&rsm("lastteldate")&""";"&chr(13))
					response.Write("document.getElementById(""contactnext_time"&com_id&existscomid(1)&""").innerHTML="""&rsm("contactnext_time")&""";"&chr(13))
				end if
			next
			
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if
%>
</script>
<%
conn.close
set conn=nothing
%>
