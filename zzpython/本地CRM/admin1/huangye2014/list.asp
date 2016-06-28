<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
action=request.QueryString("action")
if action="del" and request.QueryString("id")<>"" then
	sql="delete from huangye_list where id="&request.QueryString("id")&""
	conn.execute(sql)
	response.Write("<script>window.location='list.asp'</script>")
	response.End()
end if
 sqluser="select realname,ywadminid,ywadminid_hy from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 ywadminid_hy=rsuser("ywadminid_hy")
 rsuser.close
 set rsuser=nothing
 ckeywords=request("ckeywords")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>2013商务大全客户资料管理</title>
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
				window.open("add.asp?id="+e.value,"","")
			}
		}
	}
	form.selectcb.value=selectcb.substr(2)
}
function selectleibie(obj)
{
	document.getElementById("leibie1").style.display="none";
	document.getElementById("leibie2").style.display="none";
	var obj1=document.getElementById("leibie"+obj);
	obj1.style.display=""
	
}
</script>
</head>

<body scroll=yes>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30"><strong style="font-size:16px">商务大全客户资料管理 </strong></td>
    <td align="right"><strong style="font-size:16px"><a href="out.asp">退出</a></strong></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="6" bgcolor="#000000">
  <form id="form1" name="form1" method="post" action="list.asp" onSubmit="return searchfrm(this.form)"><tr>
<td bgcolor="#FFFFFF">
<table border="0" cellspacing="0" cellpadding="0" style="float:left">
  <tr>
    <td><select name="pcheck" id="pcheck">
      <option value="">审核情况</option>
      <option value="1">已审核</option>
      <option value="0">未审核</option>
    </select>
      <select name="membertype" id="membertype">
        <option value="">会员类型</option>
        <option value="1">高会</option>
        <option value="0">普会</option>
      </select>
      大类
      <select name="ckeywords" id="ckeywords" onChange="selectleibie(this.value)">
        <option value="">请选择业务类型--</option>
        <option value="1">废金属</option>
        <option value="2">废塑料</option>
      </select></td>
  </tr>
</table>
<script>selectOption("pcheck","<%=request("pcheck")%>")</script>
<script>selectOption("membertype","<%=request("membertype")%>")</script>


                <table border="0" cellspacing="0" cellpadding="0" style="display:none; float:left" id="leibie1">
        <tr>
          <td>必选
            <select name="js1" id="js1">
              <option value="">金属细类</option>
              <option value="贵金属">贵金属（金银铂钯铑等）</option>
              <option value="稀有金属">稀有金属（钨钼钛锢等）</option>
              <option value="有色金属">有色金属（锡铜铝锌镍等）</option>
              <option value="钢铁">钢铁（铁钢工具钢合金钢不锈钢）</option>
              <option value="其他类">其他类</option>
            </select>
            <script>selectOption("js1","<%=js1%>")</script>
            其他
            <select name="js2" id="js2">
              <option value="">金属细类</option>
              <option value="贵金属">贵金属（金银铂钯铑等）</option>
              <option value="稀有金属">稀有金属（钨钼钛锢等）</option>
              <option value="有色金属">有色金属（锡铜铝锌镍等）</option>
              <option value="钢铁">钢铁（铁钢工具钢合金钢不锈钢）</option>
              <option value="其他类">其他类</option>
            </select>
            <script>selectOption("js2","<%=js2%>")</script>
            </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="display:none; float:left" id="leibie2">
          <tr>
            <td>必选
              <select name="sl1" id="sl1">
                <option value="">塑料细类</option>
                <option value="PP">PP</option>
                <option value="PET">PET</option>
                <option value="PE">PE</option>
                <option value="ABS">ABS</option>
                <option value="PVC">PVC</option>
                <option value="PS">PS</option>
                <option value="PA">PA</option>
                <option value="PC">PC</option>
                <option value="PVB">PVB</option>
                <option value="PMMA">PMMA</option>
                <option value="EVA">EVA</option>
                <option value="PU">PU</option>
                <option value="其他类">其他类</option>
              </select>
              <script>selectOption("sl1","<%=sl1%>")</script>
             其他
              <select name="sl2" id="sl2">
                <option value="">塑料细类</option>
                <option value="PP">PP</option>
                <option value="PET">PET</option>
                <option value="PE">PE</option>
                <option value="ABS">ABS</option>
                <option value="PVC">PVC</option>
                <option value="PS">PS</option>
                <option value="PA">PA</option>
                <option value="PC">PC</option>
                <option value="PVB">PVB</option>
                <option value="PMMA">PMMA</option>
                <option value="EVA">EVA</option>
                <option value="PU">PU</option>
                <option value="其他类">其他类</option>
              </select>
              <script>selectOption("sl2","<%=sl2%>")</script>
              <script>selectOption("ckeywords","<%=ckeywords%>")</script>
              <script>selectleibie("<%=ckeywords%>")</script>
              
            </td>
          </tr>
        </table>
        <select name="ppuserid" class="button" id="ppuserid" style="width:150px;">
          <option value="" >请选择部门---</option>
          
          <%
			  if ywadminid_hy<>"" and not isnull(ywadminid_hy)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid_hy&")"
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
          <option value="<%=rsc("code")%>" <%=sle%>>
            <%response.Write(rsc("meno"))%>
          </option>
         
          <%
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
					 %>
      </select>
        </td>
  </tr>
    <tr>
      <td bgcolor="#FFFFFF">邮箱
        <input name="com_email" type="text" id="com_email" size="10" />
        公司名称
        <input name="com_name" type="text" id="com_name" size="10" />
主营
      <input name="cproductslist" type="text" id="cproductslist" size="15" />
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
          <option value="" >请选择人员查询--</option>
          <% If session("userid")="13" Then %>
          <option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
          <% End If %>
          <%
			  if ywadminid_hy<>"" and not isnull(ywadminid_hy)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid_hy&")"
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
        <input type="submit" name="button" id="button" value="搜索" />
<script>selectOption("doperson","<%=request("doperson")%>")</script>
      <input type="button" name="button2" id="button2" value="添加客户资料" onClick="window.location='add.asp'" /></td>
    </tr>
  </form>
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
huangye_qukan=request("huangye_qukan")

if comkeywords<>"0" then
	sql=sql&" and comkeywords='"&comkeywords&"'"
	sear=sear&"&comkeywords="&request("comkeywords")
end if

if membertype<>"" then
	sql=sql&" and membertype='"&membertype&"'"
	sear=sear&"&membertype="&request("membertype")
end if

if huangye_qukan<>"" then
	sql=sql&" and huangye_qukan='"&huangye_qukan&"'"
	sear=sear&"&huangye_qukan="&request("huangye_qukan")
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
if request("ppuserid")<>"" then
	sql=sql&" and personid in (select id from users where userid in ("&request("ppuserid")&"))"
	sear=sear&"&ppuserid="&request("ppuserid")
end if

if ywadminid_hy<>"" and ywadminid_hy<>"0" then
	if session("userid")="10" then
	else
		sql=sql&" and personid in (select id from users where userid in ("&ywadminid_hy&"))"
	end if
else
	sql=sql&" and personid="&session("personid")&""
end if


Set oPage=New clsPageRs2
With oPage
 .sltFld  = "*"
 .FROMTbl = "huangye_list"
 .sqlOrder= "order by id desc"
 .sqlWhere= "WHERE huangye_qukan='2014' "&sql
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
    <td nowrap="nowrap" bgcolor="#ebebeb">帐号（邮箱）</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">更正邮箱</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">会员类型</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">联系人</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">联系电话/手机</td>
    
    <td nowrap="nowrap" bgcolor="#ebebeb">大类</td>
    <td nowrap="nowrap" bgcolor="#ebebeb">细类</td>
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
    <td bgcolor="#FFFFFF"><input name="cbb" id="cbb<%=rs("id")%>" type="checkbox" value="<%=rs("id")%>"></td>
    <td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=my&lmcode=134" target="_blank"><%=rs("cname")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("newemail")%></td>
    <td bgcolor="#FFFFFF">
    <%
	if rs("membertype")="1" then
		response.Write("高会")
	else
		response.Write("普会")
	end if
	%>
    </td>
    <td bgcolor="#FFFFFF"><%=rs("ccontactp")%></td>
    <td bgcolor="#FFFFFF"><%=rs("cmobile")%></td>
    
    <td bgcolor="#FFFFFF"><%=gethangname(rs("comkeywords"))%></td>
    <td bgcolor="#FFFFFF"><%=rs("js1")%><%=rs("js2")%><%=rs("sl1")%>  <%=rs("sl2")%></td>
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
