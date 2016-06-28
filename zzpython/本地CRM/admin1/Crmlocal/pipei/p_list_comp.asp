<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
if request.QueryString("re")="1" then
	ssdetail=request("ssReply")
	ssdetail=replace(ssdetail,"'","”")
	ssdetail=replace(ssdetail,",","。")
	sql="update crm_complink_ss set ssReply='"&ssdetail&"' where id="&request("id")&""
	conn.execute(sql)
end if
%>
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
	  	form.action="p_list_comp_save.asp"
	  	form.submit()
	  }
	}
}
//预审
function yushenhe(form)
{
  selectcb="0"
  n=0
    for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
       if (e.checked==true)
	   {
	   		var selectcb=selectcb+","+e.value;
			n=n+1;
	   }
    }
	if (selectcb=="0")
	{
		alert ("选择你要提交的信息！")
		return false
	}
	else
	{
		if (n>1)
		{
			alert("预审只能选择一个企业给一个销售");
			return false;
		}
	  if (confirm("确实要提交该信息吗?"))
	  {
	  	form.action="yushenhe_save.asp"
	  	form.submit()
	  }
	}
}

function getassign(form)
{
  selectcbList="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
       if (e.checked==true)
	   {
	   var selectcbList=selectcbList+","+e.value
	   }
    }
	if (selectcbList=="0")
	{
		alert ("选择你要分配的信息！")
		return false
	}
	else
	{
	  if (confirm("确实要分配该信息吗?"))
	  {
		  form.dostay.value="selec1tcrm"
		  form.selectcb.value=selectcbList.substr(2)
	  	  form.action="../crm_assign_save.asp"
	  	  form.submit()
	  }
	}
}
function gotogonghai(form)
{
  selectcbList="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
       if (e.checked==true)
	   {
	   var selectcbList=selectcbList+","+e.value
	   }
    }
	if (selectcbList=="0")
	{
		alert ("选择你要分配的信息！")
		return false
	}
	else
	{
	  if (confirm("确实要分配该信息吗?"))
	  {
		  form.dostay.value="gotogonghaiAdmin"
		  form.selectcb.value=selectcbList.substr(2)
	  	  form.action="../crm_assign_save.asp"
	  	  form.submit()
	  }
	}
}
function delglcom(form)
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
	  if (confirm("确实要删除该关联信息吗?"))
	  {
	  	form.action="p_list_comp_del.asp"
	  	form.submit()
	  }
	}
}
</script>
</head>

<body scroll=yes>
<input type="button" name="button3" id="button3" value="返  回" onClick="window.location='p_list.asp?page=<%=request("page")%>'" />
<br />
<br />
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">公司名称</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">Email</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">销售者</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">预审情况</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">申述情况</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">提交情况</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">审核</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">电话</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">手机</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">联系人</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">省份</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">注册时间</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">最近登录</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">登录次数</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">最后联系时间</td>
    
  </tr>
  <form id="form2" name="form2" method="get" action="p_list_comp_save.asp">
  <%
    sql=" and groupid='"&request("groupid")&"'"
  	
					
	  sqlm="select top 50 com_id,com_name,com_tel,com_email,com_contactperson,com_mobile,com_province,com_regtime,lastteldate,personid,logincount,lastlogintime from v_salescomp where EXISTS (select com_id from crm_complink where com_id=v_salescomp.com_id "&sql&" ) "
	  'response.Write(sqlm)
	  set rs=conn.execute(sqlm)
	  if not rs.eof or not rs.bof then
	  while not rs.eof
	  %>
    <%
		shflag="0"
		tjflag="0"
		YSFlag=0
		ssFlag=0
		YSpersonid=""
		sqlg="select com_id,shflag,tjflag,YSFlag,ssFlag,YSpersonid from crm_compLink where com_id="&rs("com_id")&" and groupid='"&request("groupid")&"'"
		set rsg=conn.execute(sqlg)
		if not rsg.eof or not rsg.bof then
			shflag=rsg("shflag")
			tjflag=rsg("tjflag")
			YSFlag=rsg("YSFlag")
			ssFlag=rsg("ssFlag")
			YSpersonid=rsg("YSpersonid")
		end if
		rsg.close
		set rsg=nothing
		%>   
	  <tr>
		<td bgcolor="#FFFFFF">
		  <input type="checkbox" name="comid" id="comid" value="<%=rs("com_id")%>" <%if tjflag="1" then response.Write("checked onclick='return false'")%> />		</td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><a href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_email")%></td>
        <td nowrap="nowrap" bgcolor="#FFFFFF">
		 <%
				  sqluser="select b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof or not rsuser.bof then
				  	realname=rsuser("realname")
				  else
				  	realname=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  response.Write(realname)
				  %> 
         <%if trim(rs("personid"))<>"" and not isnull(rs("personid")) then%>
         <!--<input type="hidden" name="personid" id="personid" value="<%'=rs("personid")%>" />-->
         <%
		 end if
		 %>         </td>
		<td nowrap="nowrap" bgcolor="#FFFFFF">
        <%
		    if YSFlag="1" then
				response.Write("已审")
			else
				response.Write("<font color=#ff0000>未</font>")
			end if
			if YSpersonid<>"" and not isnull(YSpersonid) then
			sqlu="select realname from users where id="&YSpersonid&""
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				response.Write("审核人:"&rsu(0))
			end if
			rsu.close
			set rsu=nothing
			end if
		%>
        </td>
		<td nowrap="nowrap" bgcolor="#FFFFFF">
       	  <%
			if ssFlag=1 then
				
					response.Write("有")
					
			end if
			%>
        </td>
              
		<td nowrap="nowrap" bgcolor="#FFFFFF">
        <%
		    if tjflag="1" then
				response.Write("已提交")
			else
				response.Write("<font color=#ff0000>未提交</font>")
			end if
		%>        </td>
		<td nowrap="nowrap" bgcolor="#FFFFFF">
        <%
		    if shflag="1" then
				response.Write("已审核")
			else
				response.Write("<font color=#ff0000>未审核</font>")
			end if
		%>        </td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_province")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("lastlogintime")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("logincount")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("lastteldate")%></td>
		
	  </tr>
	  
	  <%
	  rs.movenext
	  wend
	  end if
	  rs.close
	  set rs=nothing
  %> 
  <tr>
	    <td colspan="16" bgcolor="#FFFFFF"><input name="chktoggle" type="checkbox" id="chktoggle" value="1" onClick="CheckAll(this.form)"/>
      全选
        <input type="button" name="button6" id="button6" value="预审" onClick="return yushenhe(this.form)" />
<select name="personid" class="button" id="dopersonid" >
<option value="" >请选择--</option>
			  
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
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
						if cstr(request("doperson"))=cstr(rsu("id")) then
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
	    <input type="button" name="button4" id="button4" value="重新分配" onClick="return getassign(this.form)" />
	    <input type="button" name="button5" id="button5" value="放到公海" onClick="return gotogonghai(this.form)"/>
        <input type="button" name="button2" id="button2" value="提交给服务部" onClick="return getcom(this.form)" />
        <input type="hidden" name="selectcb" id="selectcb" />
        <input type="hidden" name="dostay" id="dostay" />
        <input type="hidden" name="GroupID" id="GroupID" value="<%=request("groupid")%>" />
        <input type="button" name="button" id="button" value="删除关联" onClick="return delglcom(this.form)" /></td>
    </tr>
  </form>
</table>
<br />
<%
ssFlag=0
sql="select ssFlag from crm_complink_main where groupid='"&request("groupid")&"'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	if rs(0)="1" then
		ssFlag=1
	end if
end if
rs.close
set rs=nothing
if ssFlag=1 then
%>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
    <td bgcolor="#CCCCCC">内容</td>
    <td bgcolor="#CCCCCC">时间</td>
    <td bgcolor="#CCCCCC">申述人</td>
    <td bgcolor="#CCCCCC">回复</td>
  </tr>
<%
sqls="select top 10 a.id,a.ssdetail,a.fdate,a.ssReply,b.realname from crm_compLink_ss as a,users as b where a.groupid='"&request("groupid")&"' and a.personid=b.id order by a.fdate desc"
				set rss=conn.execute(sqls)
				if not rss.eof or not rss.bof then
					while not rss.eof
%>
  
  <tr>
    <td><%=rss("ssdetail")%></td>
    <td><%=rss("fdate")%></td>
    <td><%=rss("realname")%></td>
    <td width="300">
    <%
	if rss("ssReply")<>"" and not isnull(ssReply) then
	response.Write(rss("ssReply"))
	else
	%>
    <form id="form1" name="form1" method="get" action="">
      <textarea name="ssReply" id="ssReply" cols="45" rows="2"></textarea>
      <input type="submit" name="button7" id="button7" value="提交" />
      <input type="hidden" name="groupid" id="groupid" value="<%=request.QueryString("groupid")%>"/>
      <input type="hidden" name="id" id="id" value="<%=rss("id")%>"/>
      <input name="re" type="hidden" id="re" value="1" />
    </form>
    <%
	end if
	%>
    </td>
  </tr>
  <%
  rss.movenext
					wend
				end if
				rss.close
				set rss=nothing
  %>
</table>
<%
end if
%>
</body>
</html>
<%
conn.close
set conn=nothing
%>