<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
<%
if request("del")="1" then
	sqldel="delete from crm_zgtel_content where id="&request("id")
	conn.execute(sqldel)
	response.Redirect("crm_zgtel_comp.asp?com_id="&request("com_id")&"&page="&request("page"))
end if
if request("add")<>"" then
	if trim(request("content"))<>"" then
		sql="insert into crm_zgtel_content(com_id,content,personid) values("&request("com_id")&",'"&request("content")&"',"&request("personid")&")"
		conn.execute(sql)
		response.Redirect("crm_zgtel_comp.asp?com_id="&request("com_id")&"&page="&request("page"))
	end if
end if
if request("back")<>"" then
	if trim(request("recontent"))<>"" then
		if session("personid")="" then
		response.Write("请刷新页面再保存")
		response.End()
		end if
		sql="update crm_zgtel_content set recontent='"&request("recontent")&"',repersonid="&session("personid")&" where id="&request("id")&""
		response.Write(sql)
		conn.execute(sql)
		response.Redirect("crm_zgtel_comp.asp?com_id="&request("com_id")&"&page="&request("page"))
	end if
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售服务记录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.readon {
	font-size: 14px;
	font-weight: normal;
	color: #FFF;
	background-color: #900;
}
.readon a{
	color:#FFF;
}
.readon a:link{
	color:#FFF;
}
.readon a:hover{
	color:#FFF;
}
.readon a:visited{
	color:#FFF;
}
-->
</style>
<link href="../css.css" rel="stylesheet" type="text/css">

<link href="../inc/Style.css" rel="stylesheet" type="text/css">
<script>
var bb=0
function showsan(id)
{
	var obj = document.getElementById("addsan"+id);
	obj.style.display=""
	var objb = document.getElementById("addsan"+bb);
	if (objb && id!=bb)
	{
		objb.style.display="none"
	}
	bb=id
}
function closesan(id)
{
	var obj = document.getElementById("addsan"+id);
	obj.style.display="none"
}
</script>
</head>

<body  scroll=yes>
<%
'sqlz="update crm_zgtel_content set read_check=1 where com_id="&request("com_id")&" and personid<>"&session("personid")&""
'conn.execute(sqlz)
		  sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")
		  sql="select * from crm_zgtel_content where  com_id="&request("com_id")&""
		  set rs=server.CreateObject("ADODB.recordset")
		  rs.open sql,conn,1,1
		%>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <!--<td width="200" height="30" align="center" class="<%if telflag="1" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=1">线下BD部电话记录</a></td>-->
            <td width="200" height="30" align="center" class="<%if telflag="0" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=0">再生通<b>新签</b>销售电话记录</a></td>
            <td width="200" align="center" class="<%if telflag="2" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=2">再生通<b>续签</b>销售电话记录</a></td>
            <!--<td width="150" align="center" ><a href="crm_Adtel_comp.asp?com_id=<%=request("com_id")%>">广告部电话记录</a></td>-->
			<td width="150" align="center" class="readon" >
            <%
			sqlz="select count(0) from crm_zgtel_content where com_id="&request("com_id")&" and read_check=0"
			set rsz=conn.execute(sqlz)
			if not rsz.eof or not rsz.bof then
				zgcount=rsz(0)
			end if
			rsz.close
			set rsz=nothing
			%>
            <a href="crm_zgtel_comp.asp?com_id=<%=request("com_id")%>">主管建议</a>(<font color="#FF0000"><%=zgcount%></font>)</td>
            <td>&nbsp;</td>
          </tr>
        </table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../include/pagetop_notop.asp"--></td>
          </tr>
</table>
          
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td width="150" nowrap bgcolor="#CED7E7">时间</td>
            <td nowrap bgcolor="#CED7E7">内容</td>
            <td nowrap bgcolor="#CED7E7">发表人</td>
            <td nowrap bgcolor="#CED7E7">回复</td>
            <td nowrap bgcolor="#CED7E7">回复人</td>
            <td nowrap bgcolor="#CED7E7">操作</td>
        </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
            <td bgcolor="#FFFFFF"><%=rs("content")%>
              <table width="100%" border="1" bgcolor="#FFFFFF" cellspacing="0" cellpadding="3" id="addsan<%=rs("id")%>" style="display:none;position: absolute;">
               <form name="form2" method="post" action=""> <tr>
                  <td align="center">
                    <textarea name="recontent" id="recontent" cols="45" rows="5"></textarea>                  </td>
                </tr>
                <tr>
                  <td align="center" style="background:#ffffff">
                  <input name="back" type="hidden" id="back" value="1">
                  <input name="com_id" type="hidden" id="com_id" value="<%=request("com_id")%>">
                  <input name="id" type="hidden" id="id" value="<%=rs("id")%>">
                  <input type="submit" name="button2" id="button2" value="提交">
                  <input type="button" name="button3" id="button3" value="关闭" onClick="closesan(<%=rs("id")%>)"></td>
                </tr></form>
            </table></td>
            <td bgcolor="#FFFFFF"><%call selet_("realname","users","id",rs("personid"))%></td>
            <td bgcolor="#FFFFFF"><%=rs("recontent")%></td>
            <td bgcolor="#FFFFFF"><%call selet_("realname","users","id",rs("repersonid"))%></td>
            <td bgcolor="#FFFFFF">
            <%if rs("recontent")="" or isnull(rs("recontent")) then%>
            <a href="####" onClick="showsan(<%=rs("id")%>)">回复此条</a>
            <%else%>
            <font color="#999999">已经回复</font>
            <%end if%>
            </td>
        </tr>
         
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  %>
		   <tr>
            <td colspan="6" align="right" bgcolor="#FFFFFF"><!-- #include file="../include/page.asp" --></td>
        </tr>
</table>
    </td>
    
    <td width="300" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="5">
      <form name="form1" method="post" action="">
        <tr>
          <td>
          <%if session("Partadmin")<>"0" or session("userid")="10" then%>
          主管建议
          <%else%>
          建议回复
          <%end if%>
            <input type="hidden" name="personid" id="personid" value="<%=session("personid")%>">
            <input type="hidden" name="com_id" id="com_id" value="<%=request("com_id")%>">
            <input type="hidden" name="page" id="page" value="<%=request("page")%>">
            <input name="add" type="hidden" id="add" value="1">
            （500字内）</td>
        </tr>
        <tr>
        <td>
          <textarea name="content" id="content" cols="45" rows="5"></textarea>        </td>
      </tr>
      <tr>
        <td align="center"><input type="submit" name="button" id="button" value="提交"></td>
      </tr> </form>
    </table>
    </td>
    
  </tr>
</table>
</body>
</html>
