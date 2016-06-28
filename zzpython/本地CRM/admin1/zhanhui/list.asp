<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
action=request.QueryString("action")
if action="del" and request.QueryString("id")<>"" then
	sql="delete from zhanhui_list where id="&request.QueryString("id")&""
	conn.execute(sql)
	response.Write("<script>window.location='list.asp'</script>")
	response.End()
end if
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid,usertel from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 mytel=rsuser("usertel")
 rsuser.close
 set rsuser=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>展会客户资料管理</title>
<link href="s.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="http://192.168.2.2/cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="http://192.168.2.2/admin1/main.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/list.js"></SCRIPT>
<script>
function searchfrm(frm)
{
	getprovincename();
}
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
	var e = form.elements[i];
	if (e.name.substr(0,3)=='cbb')
	   e.checked = form.cball.checked;
	}
}
function opencomp(form)
{
	selectcb="0"
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		{
			if (e.checked==true)
			{
				var selectcb=selectcb+","+e.value
				window.open("add.asp?com_email="+e.value,"","")
			}
		}
	}
	form.selectcb.value=selectcb.substr(2)
}
</script>
</head>

<body scroll=yes>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30"><strong style="font-size:16px">展会客户资料管理 </strong></td>
    <td align="right"><strong style="font-size:16px"><a href="out.asp">退出</a></strong></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="6" bgcolor="#000000">
  <form id="form1" name="form1" method="post" action="list.asp" onSubmit="return searchfrm(this.form)"><tr>
<td bgcolor="#FFFFFF">

邮箱
<input name="com_email" type="text" id="com_email" size="10" />
<script>selectOption("membertype","<%=request("membertype")%>")</script>
手机
<input name="com_mobile" type="text" id="com_mobile" size="10" />
公司名称
<input name="com_name" type="text" id="com_name" size="10" />
行业
<select name="ckeywords" id="ckeywords">
<option value="">--请选择--</option>
                      <option value="1">废金属</option>
                      <option value="2">废塑料</option>
                      <option value="3">废旧轮胎与废橡胶</option>
                      <option value="4">废纺织品与废皮革</option>
                      <option value="5">废纸</option>
                      <option value="6">废电子电器</option>
                      <option value="10">废玻璃</option>
                      <option value="12">废旧二手设备</option>
                      <option value="14">其他废料</option>
                      <option value="15">服务</option>
                </select>
主营
<input name="cproductslist" type="text" id="cproductslist" size="15" />
<script>selectOption("ckeywords","<%=request("ckeywords")%>")</script>
    地区
    
    <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
              <input type="hidden" name="province" id="province">
              <input type="hidden" name="city" id="city">
              <input type="hidden" name="cprovince" id="cprovince" value="0">
  
	    <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=request("province1")%>";
                            var Fvalue2="<%=request("city1")%>";
                            var Fvalue3="<%=request("mainGarden")%>";
                            var hyID="ckeywords";//行业ID号
                            getprovincevalue();
                        </script>
        <script>
                        function getprovincename()
                        {
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
							//alert(document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text)
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
        <select name="doperson" class="button" id="doperson" style="width:150px;">
          <option value="" >请选择--</option>
          <% If session("userid")="13" Then %>
          <option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
          <% End If %>
          <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
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
						sqlu="select realname,id from users where chatclose=1 and userid="&rsc("code")&""
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
        <script>selectOption("doperson","<%=request("doperson")%>")</script>	
        <input type="submit" name="button" id="button" value="搜索" />
        <input type="button" name="button2" id="button2" value="添加客户资料" onClick="window.location='add.asp'" /></td>
  </tr></form>
</table>
<%
function gethangname(id)
	sqlh="select cb_chn_name from cls_b where cb_id="&id&""
	set rsh=conn.execute(sqlh)
	if not rsh.eof or not rsh.bof then
		gethangname=rsh(0)
	end if
	rsh.close
	set rsh=nothing
end function
page=request("page")
sear="n="
if page="" then page=1
sql=""
comkeywords=request("ckeywords")
province=request("province")
city=request("city")
comkeywords=replace(comkeywords,", ",",")
if comkeywords="" then comkeywords=0
membertype=request("membertype")
pcheck=request("pcheck")
if comkeywords<>"0" then
	sql=sql&" and comkeywords='"&comkeywords&"'"
	sear=sear&"&comkeywords="&request("comkeywords")
end if

if membertype<>"" then
	sql=sql&" and membertype='"&membertype&"'"
	sear=sear&"&membertype="&request("membertype")
end if
if pcheck<>"" then
	sql=sql&" and pcheck='"&pcheck&"'"
	sear=sear&"&pcheck="&request("pcheck")
end if
if request("com_name")<>"" then
	sql=sql&" and cname like '%"&request("com_name")&"%'"
	sear=sear&"&com_name="&trim(request("com_name"))
end if
if request("com_email")<>"" then
	sql=sql&" and com_email like '"&request("com_email")&"%'"
	sear=sear&"&com_email="&trim(request("com_email"))
end if
if request("com_mobile")<>"" then
	sql=sql&" and cmobile like '"&request("com_mobile")&"%'"
	sear=sear&"&com_mobile="&trim(request("com_mobile"))
end if
if city<>"" and city<>"请选择..." then
	sql=sql&" and province like '%"&city&"%'"
	sear=sear&"&city="&request("city")
end if
if cproductslist<>"" then
	sql=sql&" and cproductslist like '%"&cproductslist&"%'"
	sear=sear&"&cproductslist="&request("cproductslist")
end if

if province<>"" and province<>"请选择..." then
	sql=sql&" and province like '%"&province&"%'"
	sear=sear&"&province="&request("province")
end if
if request("doperson")<>"" then
	sql=sql&" and personid="&request("doperson")&""
	sear=sear&"&doperson="&request("doperson")
end if
if ywadminid<>"" then
	'sql=sql&" and personid in (select id from users where userid in ("&ywadminid&"))"
else
	'sql=sql&" and personid="&session("personid")&""
end if
if session("personid")<>"227" and session("personid")<>"61" and session("personid")<>"176" and session("personid")<>"328" and session("personid")<>"222" and session("personid")<>"14" and session("personid")<>"1152" then
	'sql=sql&" and personid="&session("personid")&""
end if
'response.Write(sql)

Set oPage=New clsPageRs2
With oPage
 .sltFld  = "*"
 .FROMTbl = "zhanhui_list"
 .sqlOrder= "order by id desc"
 .sqlWhere= "WHERE id>0 "&sql
 .keyFld  = "ID"    '不可缺少
 .pageSize= 10
 .getConn = conn
 Set rs  = .pageRs
End With
total=oPage.getTotalPage
oPage.pageNav "?"&sear,""
totalpg=cint(total/10)
if total/10 > totalpg then
totalpg=totalpg+1
end if
%>
<form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input type="button" name="button3" id="button3" value="一键打开" onClick="opencomp(this.form)" /></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="6" bgcolor="#000000">
  <tr>
    <td width="30" nowrap="nowrap" bgcolor="#ebebeb"><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"></td>
    <td nowrap="nowrap" bgcolor="#ebebeb">公司名称</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">联系人</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">邮箱</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">联系电话</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">手机</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">行业</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">主营业务</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">地址</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">添加人</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">添加时间</td>
    <td colspan="2" nowrap="nowrap" bgcolor="#ebebeb">操作</td>
  </tr>
  <%
  if not rs.eof then
  do while not rs.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF"><input name="cbb" id="cbb<%=rs("id")%>" type="checkbox" value="<%=rs("com_email")%>"></td>
    <td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=my&lmcode=134" target="_blank"><%=rs("cname")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("ccontactp")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("ctel")%></td>
    <td bgcolor="#FFFFFF"><%=rs("cmobile")%></td>
    <td bgcolor="#FFFFFF"><%=gethangname(rs("comkeywords"))%></td>
    <td bgcolor="#FFFFFF"><%=rs("cproductslist")%></td>
    <td bgcolor="#FFFFFF"><%=rs("cadd")%></td>
    <td bgcolor="#FFFFFF"><%=rs("personname")%></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><a href="add.asp?id=<%=rs("id")%>" target="_blank">修改</a></td>
    <td bgcolor="#FFFFFF"><a href="?action=del&id=<%=rs("id")%>" onClick="return confirm('确实要删除吗？')">删除</a></td>
  </tr>
  <%
  rs.movenext
  loop
  end if
  rs.close
  set rs=nothing
  
  %>
</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
