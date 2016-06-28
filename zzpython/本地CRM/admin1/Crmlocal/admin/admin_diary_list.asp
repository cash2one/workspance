<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<% 
sear=sear&"n="
if request("cmd")="del" then
	diaryid=request("diaryid")
	conn.execute "delete from crm_diary where id="&diaryid
	conn.execute "delete from crm_rankTotal where diaryid="&diaryid
end if
sqluser="select realname,ywadminid,xuqianFlag from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 rsuser.close
 set rsuser=nothing
 
 
menuid=request("menuid")
if menuid="" then menuid="1"
if menuid="1" then
	bReply="0"
else
	bReply="1"
end if
sear=sear&"&menuid="&menuid
page=Request("page")
if isnumeric(page) then
else
	page=1
end if
if page="" then 
	page=1 
end if
pid=request("pid")
sql=""
if pid<>"" then
	if left(pid,1)="a" then
		sql=" and personid in (select id from users where userid="&right(pid,len(pid)-1)&")"
	else
		sql=" and personid="&pid
	end if
	sear=sear&"&pid="&pid
end if
'if session("userid")<>"10" and session("personid")<>"49" and session("personid")<>"142" then	'为各个主管助理
'	sql=sql&" and exists(select null from users where userid="&session("userid")&" and closeflag=1 and id=crm_diary.personid)"
'elseif session("personid")="49" then 'D组的提交给涂昀
'	sql=sql&" and exists(select null from users where userid=1306 and closeflag=1 and id=crm_diary.personid)"
'elseif session("personid")="142" then 'E组的提交给朱立云
'	sql=sql&" and exists(select null from users where userid=1301 and closeflag=1 and id=crm_diary.personid)"
'end if

 %>
<html>
<head>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function del(id){
	if(confirm('你确定要删除吗?')){
		location.href="admin_diary_list.asp?menuid=<%= menuid %>&page=<%= page %>&pid=<%= pid %>&diaryid="+id+"&cmd=del";
	}
	else{
		return false;
	}
}
</script>
</head>
<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6"></td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">日报管理</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE"><form name="" method="post" action="admin_diary_list.asp">
          <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center">
              <select name="pid" class="button" id="pid" >
              <option value="" >请选择--</option>
			  <%
			if session("userid")="10" then
				sqlc="select code,meno from cate_adminuser where code like '13%' "
			else
			  if session("Partadmin")<>"0" then
			  	  sqlc="select code,meno from cate_adminuser where code like '"&session("Partadmin")&"%'"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 if ywadminid<>"" and not isnull(ywadminid)  then
					 	sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
					 else
				  	 	sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
					 end if
				  end if
			  end if
			end if
			sqlc=sqlc&" and closeflag=1"
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  if cstr(request("pid"))=cstr("a"&rsc("code")) then
						sle="selected"
						else
						sle=""
						end if
			  %>
			  <option <%=sle%> value="a<%response.Write(rsc("code"))%>">┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("pid"))=cstr(rsu("id")) then
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
                  <input name="Submit2" type="submit" class="button" value="搜索"></td>
            </tr>
          </table>
        </form>
          <!--#include file="admin_diary_menu.asp"-->
          <br>
          
          <table width="80%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
<%
'response.Write(session("userClass"))
if session("Partadmin")<>"" and session("userClass")="1" and len(session("Partadmin"))>2  then'组长权限
   sql=sql&" and personid in (select id from users where userid="&session("Partadmin")&")"	
end if
if (session("Partadmin")="13" and session("userClass")="1" )  then'主管  权限
   sql=sql&" and personid in (select id from users where userid in ("&ywadminid&"))"
end if
if (session("Partadmin")="0" or isnull(session("Partadmin"))) and session("userid")<>"10" then
	sql=sql&" and personid="&session("personid")&""
end if

'response.Write(sql)
Set oPage=New clsPageRs2
With oPage
	.sltFld  = "*"
	.FROMTbl = "crm_diary"
	.sqlOrder= "order by id desc"
	.sqlWhere= "WHERE isDraft=0 and haveReply="&bReply&sql
	.keyFld  = "id"    '不可缺少
	.pageSize= 15
	.getConn = conn
	Set Rs  = .pageRs
End With
total=oPage.getTotalPage
oPage.pageNav "?"&sear,""
totalpg=cint(total/15)
if total/15 > totalpg then
totalpg=totalpg+1
end if %></td>
            </tr>
          </table>          
          <table width="80%" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
            <tr>
              <td height="25" bgcolor="#B0CAFF">日报标题</td>
              <td bgcolor="#B0CAFF">发布者</td>
              <td bgcolor="#B0CAFF">是否回复</td>
              <td bgcolor="#B0CAFF">&nbsp;</td>
            </tr>
            <% 
j=1
while not rs.eof 
if j mod 2 = 1 then
	trColor="#ffffff"
else
	trColor="#eeeeee"
end if%>
            <tr bgcolor="<%= trColor %>">
              <td width="25%"><a href="my_oldDiary.asp?diaryid=<%= rs("id") %>&actontype=back"><%= rs("fdate") %>的日报</a></td>
              <td width="25%"><%= showPerson(rs("personid")) %> <a href="my_diary_reply.asp?personid=<%=rs("personid")%>" target="_blank" style="color:#999">查看所有回复</a></td>
              <td width="19%"><%
		if rs("haveReply")="1" then
			response.write "<font color=green>已回复</font>"
		else
			response.write "<font color=red>未回复</font>"
		end if
	 %>
              </td>
              <% If bReply=1 Then 
	 		t="修改回复"
		else
			t="我要回复"
		end if%>
              <td width="31%"><input type="button" name="Submit" value="<%= t %>" class="button" onClick="location.href='my_oldDiary.asp?diaryid=<%= rs("id") %>&actontype=back'">
                <input type="button" name="Submit3" value="删除" class="button" onClick="del(<%= rs("id") %>)"></td>
            </tr>
            <% rs.movenext
  j=j+1
  wend
   %>
          </table>  <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25"><!-- #include file="../../include/page.asp" --></td>
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
</html>
<% 
rs.close
set rs=nothing
conn.close
set conn=nothing

function showPerson(id)
	dim sql,rs
	sql="select realname from users where id="&id
	set rs=conn.execute(sql)
	if not (rs.eof and rs.bof) then
		showPerson=rs(0)
	end if
end function
 %>