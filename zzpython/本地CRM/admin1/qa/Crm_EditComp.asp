<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
page=Request("page")

'''''''''''''''''''''''''''''''''''
sear="en="
    if isnumeric(page) then
    else
	    page=1
    end if
if page="" then 
page=1 
end if
  ''''''''''''''''''''''''''''''''''''''''''
selectcb=request("selectcb")
dostay=request("dostay")
if dostay="delitem" then
	sql="update Crm_Temp_SalesComp set crmcheck=0,fcheck=1 where com_id in ("&selectcb&")"
	conn.execute sql,ly
end if
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../bjq/editor.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../include/open_test.js"></script>
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
body,td,th {
	font-size: 12px;
}
-->
</style>
</head>

<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><form name="form2" method="get" action="">
      <label>
      <input name="pdtcount" type="hidden" id="pdtcount" value="<%=request("pdtcount")%>">
      Email:
        <input name="com_email" type="text" id="com_email">
      </label>
      <label>
      
      公司名：
      <input name="com_name" type="text" id="com_name">
      <input type="submit" name="Submit3" value="搜索">
      <input type="button" name="button" id="button" value="办事处CRM客户修改" onClick="window.open('http://office.zz91.com/admin1/qa/crm_editcomp.asp?lmcode=2005','','')">
      <br>
      </label>
    </form>    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="30"><strong><a href="?gonghai=1">公海</a></strong> | <strong><a href="?gonghai=0">私海</a></strong></td>
            </tr>
          </table>
        <table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#000000">
            <form action="crm_editcomp.asp" method="post" name=form1>
              <tr bgcolor="#FFFFFF">
                <td colspan="11">
                  <%
									
					sql=""
					if request("com_email")<>"" then
						sql=sql&" and com_email like '%"&trim(request("com_email"))&"%'"
						sear=sear&"&com_email="&request("com_email")
					end if
					
					
					if request("com_name")<>"" then
						sql=sql&" and com_name like '%"&trim(request("com_name"))&"%'"
						sear=sear&"&com_name="&request("com_name")
					end if
					if request("gonghai")="1" then
						sql=sql&" and not exists(select com_id from crm_assign where com_id=crm_temp_salescomp.com_id)"
						sear=sear&"&gonghai="&request("gonghai")
					end if
					if request("gonghai")="0" then
						sql=sql&" and exists(select com_id from crm_assign where com_id=crm_temp_salescomp.com_id)"
						sear=sear&"&gonghai="&request("gonghai")
					end if
				   startime=timer()
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "com_email,com_name,com_id,com_regtime"
				     .FROMTbl = "crm_temp_salescomp"
				     .sqlOrder= "order by com_id desc"
				     .sqlWhere= "WHERE exists(select null from comp_info where com_id=crm_temp_salescomp.com_id) and (fcheck=0) "&sql
				     .keyFld  = "com_id"    '不可缺少
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
					%>                  </td>
              </tr>
              <tr bgcolor="#4284FF">
                <td width="4">&nbsp;</td>
                <td><font color="#FFFFFF">公司名</font></td>
                <td align="left"><span class="style1">Email</span></td>
                <td align="center" bgcolor="#4284FF"><span class="style1">注册时间</span></td>
                <td align="center" bgcolor="#4284FF"><span class="sort style1">销售者</span></td>
                <td align="center"><span class="style1">销售记录</span></td>
                </tr>
              <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF
				if i mod 2=1 then
					bgcol="#f2f2f2"
				else
					bgcol="#ffffff"
				end if
				%>
              <tr bgcolor="<%=bgcol%>" >
                <td><input name="cbb<%=i%>" type="checkbox" title="<%=rs("com_email")%>" value="<%=rs("com_id")%>">                </td>
                
                <td>
				<%=rs("com_name")%>
				<a href='../compinfo/crm_compinfoEidtIframe.asp?idprod=<%=rs("com_id")%>&frompage=admin1/qa/<%=frompage%>&frompagequrstr=<%=frompagequrstr%>' target="_blank">修改</a></td>
                <td align="left" nowrap><%=rs("com_email")%></td>
                <td align="center"><%response.Write(rs("com_regtime"))%></td>
                <td align="center">
				<%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	response.Write(rsuser("realname"))
				  end if
				  rsuser.close
				  set rsuser=nothing
				  %>				</td>
                <td align="center"><a href="###KANG" onClick="window.open('http://192.168.1.2/admin1/crmlocal/crm_tel_comp.asp?com_id=<%=rs("com_id")%>','_blank')">查看</a></td>
                </tr>
              <%
			rs.movenext
			loop  
			else

				  %>
              <tr bgcolor="#FFFFFF">
                <td colspan="11">暂时无信息！</td>
              </tr>
              <%
			  end if
			  rs.close
			  set rs=nothing
			  %>
              <script language="JavaScript">
<!--

function CheckAll(form)
  {
  
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
	//alert (e.name)
       e.checked = form.cball.checked;
    }
  }
  function cbplay(form,n)
  {
 selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	//alert (selectcb)
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=='0')
	{
	alert ("选择你要改变的信息！")
	return false
	}
	
	else
	{
	  form.flag.value=n
	  form.dostay.value="dochange"
	  form.submit()
	 
	}
  }
  function cbplaych(form,n)
  {
 selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	//alert (selectcb)
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=='0')
	{
	alert ("选择你要改变的信息！")
	return false
	}
	
	else
	{
	  form.flag.value=n
	  form.dostay.value="dochangech"
	  form.submit()
	 
	}
  }
 function delitem(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要删除的信息！")
	return false
	}
	else
	{
	  if (confirm("删除这些信息?"))
	  {
	  form.dostay.value="delitem"
	  form.submit()
	  }
	}
	
  }
  function mima(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要的信息！")
	return false
	}
	else
	{
	  if (confirm("确实要设置密码吗?"))
	  {
	  form.dostay.value="mima"
	  form.submit()
	  }
	}
	
  }
   function yqh(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.title
	   }
	  
    }
	
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要的信息！")
	return false
	}
	else
	{
	  if (confirm("确实要发送邀请函吗?"))
	  {
	  form.dostay.value="yqh"
	  form.action="comp_yqh.asp"
	  form.submit()
	  }
	}
	
  }
  function chkz(cemail)
{//注册用户删除前提醒
if (confirm("确定将用户"+cemail+"转为注册会员吗？")==true)
	{
	window.location.href="comcheck3.asp?com_email="+cemail
	}
else
	{return false;}
}
-->
    </script>
              <tr bgcolor="#FFFFFF">
                <td colspan="12" align="right">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>全选
                        <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"></td>
                      <td align="right"><input type="button" name="Submit" value="删除" onClick="delitem(this.form)">
                      <input type="hidden" name="dostay">
                        <input type="hidden" name="selectcb">
                        <input type="hidden" name="en" value=<%=en%>>
                        <input type="hidden" name="ch" value="<%=ch%>">
                        <input type="hidden" name="flag" value="<%=orderby%>">
                        <input type="hidden" name="rn" value="<%=request("rn")%>">
                        <input type="hidden" name="page" value="<%=page%>">
                        <input type="hidden" name="b_name2" value="<%=b_name%>"></td>
                    </tr>
                  </table>                </td>
              </tr>
            </form>
            <tr bgcolor="#FFFFFF">
              <td colspan="12">
                <!-- #include file="../include/page.asp" -->              </td>
            </tr>
          
          </table>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../conn_end.asp" -->
