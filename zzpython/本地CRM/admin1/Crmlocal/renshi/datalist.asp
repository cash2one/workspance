<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<%
datefrom=request("datefrom")
dateto=request("dateto")
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 rsuser.close
 set rsuser=nothing
if request("del")="1" and request("id")<>"" then
	sql="delete from renshi_salesIncome where id="&request("id")&""
	conn.execute(sql)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
function tian_sum(nianyue) 
	nian=cint(left(nianyue,instr(nianyue,"-")-1)) 
	yue=cint(right(nianyue,len(nianyue)-instr(nianyue,"-"))) 
	if yue=1 or yue=3 or yue=5 or yue=7 or yue=8 or yue=10 or yue=12 then 
		tian_sum=31 
	elseif yue=4 or yue=6 or yue=9 or yue=11 then 
		tian_sum=30 
	else 
	if ryear(nian)=true then 
		tian_sum=29 
	elseif ryear(nian)=false then 
		tian_sum=28 
	else 
		tian_sum="Error" 
	end if 
	end if 
end function 
function ryear(years) 
	If years Mod 400 = 0 Or (years Mod 4 = 0 And years Mod 100 <> 0) Then 
		ryear=true 
	else 
		ryear=false 
	end If 
end function
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
.hl {
background-color: #ededec;
}
.sl {
background-color: #e0edf7;

}
-->
</style>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../../../cn/sources/pop.js"></SCRIPT>
<script>
function outform(frm)
{
	frm.action="datalistout.asp";
	frm.target="_blank";
	frm.submit();
}
function onsearch(frm)
{
	frm.action="datalist.asp";
	frm.target="_self";
	frm.submit();
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<form id="form2" name="form2" method="get" action="datalist.asp">
  <tr>
    <td><select name="userid" id="userid" style="width:150px">
      <option value="">��ѡ����</option>
      <%
		    if ywadminid<>"" and not isnull(ywadminid)  then
			  sqlw="select code,meno from cate_adminuser where code in("&ywadminid&")  and closeflag=1 "
			else
			  if session("userid")="10" then
				 sqlw="select code,meno from cate_adminuser where (code like '13__' or code like '24%') and closeflag=1 "
			  else
				 sqlw="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1 "
			  end if
			end if
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
      <option value="<%=rsw("code")%>">��&nbsp;&nbsp;��<%=rsw("meno")%></option>
     
      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		 set rsw=nothing
		  %>
    </select>
    <script>selectOption("userid","<%=request.QueryString("userid")%>")</script>
    <select name="doperson" class="button" id="doperson" style="width:150px;">
              <option value="" >��ѡ����Ա</option>
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >��&nbsp;&nbsp;��&nbsp;&nbsp;��<%=userName%></option>
			  <% End If %>
			  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  sqlc=sqlc&" and closeflag=1"
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where userid="&rsc("code")&""
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
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
      ����<input name="realname" type="text" id="realname" size="10" /> ���� <input name="sales_email" type="text" id="sales_email" size="10" /> �ֻ� <input name="sales_mobile" type="text" id="sales_mobile" size="10" />
        <br>����ʱ���<script language=javascript>createDatePicker("datefrom",true,"<%=datefrom%>",false,true,true,true)</script>��<script language=javascript>createDatePicker("dateto",true,"<%=dateto%>",false,true,true,true)</script>
      <select name='service_type' id='service_type' class="inputselect" >
          <option value=''>��ѡ��...</option>
          <option value="����ͨ">����ͨ</option>
          <option value="Ʒ��ͨ">Ʒ��ͨ</option>
          <option value="չ���Ʒ">չ���Ʒ</option>
          <option value="���">���</option>
          <option value="����ֽý">����ֽý</option>
          <option value="���ű���">���ű���</option>
          <option value="�ٶ��Ż�">�ٶ��Ż�</option>
          <option value="����վ">����վ</option>
          <option value="�ƶ�����ܼ�">�ƶ�����ܼ�</option>
          <option value="����ͨ������">����ͨ������</option>
          <option value="�������">�������</option>
          <option value="���Ż�Ա">���Ż�Ա</option>
          <option value="����">����</option>
          <option value="���籦">���籦</option>
          <option value="���籦��Ԫ">���籦��Ԫ</option>
          <option value="���籦һԪ">���籦һԪ</option>
          <option value="����">����</option>
        </select>
        <script>selectOption("service_type","<%=request("service_type")%>")</script>
        <select name='sales_type' id='sales_type' ><option value=''>��ѡ��...</option>
          <option value="��ǩ">��ǩ</option>
          <option value="��ǩ">��ǩ</option>
          <option value="��ֵ">��ֵ</option>
        </select>
        <select name='sales_priceflag' id='sales_priceflag' ><option value=''>ҵ��</option>
          <option value="0">=0</option>
          <option value="1">>0</option>
        </select>
        <script>selectOption("sales_priceflag","<%=request("sales_priceflag")%>")</script>
      <input type="button" name="button" id="button" value="����" onClick="onsearch(this.form)" />
      <input type="button" name="button2" id="button2" value="����" onClick="outform(this.form)" /> 
      һ��ֻ�ܵ���500��</td>
  </tr></form>
</table>
<%
sear="n="
sql=""
    if request.QueryString("userid")<>"" then
		sql=sql&" and userid like '"&request.QueryString("userid")&"%'"
		sear=sear&"&userid="&request.QueryString("userid")
	end if
	
	if request.QueryString("service_type")<>"" then
		sql=sql&" and service_type='"&request.QueryString("service_type")&"'"
		sear=sear&"&service_type="&request.QueryString("service_type")
	end if
	
	if request.QueryString("doperson")<>"" then
		sql=sql&" and personid='"&request.QueryString("doperson")&"'"
		sear=sear&"&doperson="&request.QueryString("doperson")
	end if
	
	if request.QueryString("datefrom")<>"" then
		sql=sql&" and sales_date>='"&request.QueryString("datefrom")&"'"
		sear=sear&"&datefrom="&request.QueryString("datefrom")
	end if
	if request.QueryString("dateto")<>"" then
		sql=sql&" and sales_date<='"&request.QueryString("dateto")&"'"
		sear=sear&"&dateto="&request.QueryString("dateto")
	end if
	
	if request.QueryString("realname")<>"" then
		sql=sql&" and realname like '%"&request.QueryString("realname")&"%'"
		sear=sear&"&realname="&request.QueryString("realname")
	end if
	
	if request.QueryString("sales_email")<>"" then
		sql=sql&" and sales_email like '%"&request.QueryString("sales_email")&"%'"
		sear=sear&"&sales_email="&request.QueryString("sales_email")
	end if
	
	if request.QueryString("sales_mobile")<>"" then
		sql=sql&" and sales_mobile like '%"&request.QueryString("sales_mobile")&"%'"
		sear=sear&"&sales_mobile="&request.QueryString("sales_mobile")
	end if
	
	if request.QueryString("onstaton")="1" then
		sql=sql&" and id not in (select id from users where renshi_zaizhi='1203')"
		sear=sear&"&onstaton="&request.QueryString("onstaton")
	end if
	if request.QueryString("sales_type")<>"" then
		sql=sql&" and sales_type='"&request.QueryString("sales_type")&"'"
		sear=sear&"&sales_type="&request.QueryString("sales_type")
	end if
	if ywadminid<>"" and not isnull(ywadminid)  then
		'sql=sql&" and userid in ("&ywadminid&")"
	end if
	if request.QueryString("sales_priceflag")<>"" then
		if request.QueryString("sales_priceflag")=0 then
			sql=sql&" and sales_price=0"
		else
			sql=sql&" and sales_price>0"
		end if
	end if
	response.Write(sql)
  Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "renshi_salesIncome"
	 .sqlOrder= "order by sales_date desc"
	 .sqlWhere= "WHERE  id>0 "&sql
	 .keyFld  = "id"    '����ȱ��
	 .pageSize= 15
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=cint(total/15)
   if total/15 > totalpg then
	  totalpg=totalpg+1
   end if
%>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
  <tr>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ͻ��ֻ�</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ͻ�����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�����·�</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ܽ���</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">������Ա</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��������</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��������</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��������</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��Ʒ����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ͻ�����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">���ʽ��</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">������Դ</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��ֵ����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��������</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">��Ʒ����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">���ʽ</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">������ʽ</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">&nbsp;</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ͻ�����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">�ͻ���Ӫ��Ʒ</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2"><strong>�������</strong></td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">����ͨ����</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">����</td>
  </tr>
  <form id="form1" name="form1" method="post" action=""><%
  
    if not rs.eof  then 
	While Not rs.EOF
  %>
  <SPAN onMouseOver="DoHL()" onMouseOut="DoLL()" onClick="DoSL();">
  <tr bgcolor="#ffffff">
    <td nowrap="nowrap"><%=rs("sales_mobile")%></td>
    <td nowrap="nowrap"><%=rs("com_contactperson")%></td>
    <td nowrap="nowrap">
    <%
	gsdate=rs("sales_date")
	if isnull(gsdate) then gsdate=now
	mgsdate=month(gsdate)
	if len(mgsdate)<2 then mgsdate="0"&mgsdate
	gsdatestr=year(gsdate)&mgsdate
	todate=cdate(year(gsdate)&"-"&month(gsdate)&"-"&tian_sum(year(gsdate)&"-"&month(gsdate)))
	nowmonth=year(gsdate)&"-"&month(gsdate)&"-1"
	partmonth=datediff("ww",nowmonth,gsdate)
	nowpartmonth=datediff("ww",nowmonth,todate)
	saleweek=DatePart("w",cdate(gsdate))
	select case saleweek
		case 1
			zwweek="��"
		case 2
			zwweek="һ"
		case 3
			zwweek="��"
		case 4
			zwweek="��"
		case 5
			zwweek="��"
		case 6
			zwweek="��"
		case 7
			zwweek="��"
	end select
	response.Write(gsdatestr)
	
	%>
    </td>
    <td align="center" nowrap="nowrap">W<%=partmonth%></td>
    <td nowrap="nowrap">��<%=zwweek%></td>
    <td nowrap="nowrap"><%=rs("personid")%><%=rs("realname")%></td>
    <td nowrap="nowrap">
    	<%
		userid=rs("userid")
		if userid="1315" then
			response.Write("VAP")
		elseif left(userid,2)="24" then
			response.Write("CS")
		else
			response.Write("ICD")
		end if
		%>
    </td>
    <td nowrap="nowrap">
    	<%
		if userid<>"" or not isnull(userid) then
		sqlu="select meno from cate_adminuser where code='"&userid&"'"
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rsu.bof then
			response.Write(rsu(0))
		end if
		rsu.close
		set rsu=nothing
		end if
		%>
    </td>
    <td nowrap="nowrap"><%=rs("sales_date")%></td>
    <td nowrap="nowrap"><%=rs("sales_type")%></td>
    <td nowrap="nowrap"><%=rs("service_type")%></td>
    <td nowrap="nowrap"><%=rs("sales_price")%></td>
    <td nowrap="nowrap"><%=rs("sales_email")%>
      <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank">�鿴�ͻ�</a>
    </td>
    <td nowrap="nowrap"><%=rs("com_ly1")%></td>
    <td nowrap="nowrap"><%=rs("service_type")%></td>
    <td nowrap="nowrap"><%=rs("com_zq")%></td>
    <td nowrap="nowrap"><%=rs("com_cpjb")%></td>
    <td nowrap="nowrap"><%=rs("com_hkfs")%></td>
    <td nowrap="nowrap"><%=rs("com_cxfs")%></td>
    <td nowrap="nowrap"><%=rs("com_khdq")%></td>
    <td nowrap="nowrap">
      
      <%
	sqlc="select com_province from comp_info where com_id="&rs("com_id")&""
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		response.Write(rsc(0))
	end if
	rsc.close
	set rsc=nothing
	%>
      
    </td>
    <td nowrap="nowrap">
      <%
	com_pro=rs("com_pro")
	if com_pro<>"" or not isnull(com_pro) then
	sqls="select cb_chn_name from cls_b where cb_id="&com_pro&""
	set rss=conn.execute(sqls)
	if not rss.eof or not rss.bof then
		response.Write(rss(0))
	end if
	rss.close
	set rss=nothing
	end if
	%></td>
    <td nowrap="nowrap"><%=rs("com_logincount")%></td>
    <td nowrap="nowrap"><%=rs("com_servernum")%></td>
    <td nowrap="nowrap"><a href="dataedit0.asp?id=<%=rs("id")%>">�޸�</a> | <a href="?del=1&id=<%=rs("id")%>" onClick="return confirm('ȷʵҪɾ����')">ɾ��</a></td>
  </tr>
  </SPAN>
  <%
  rs.movenext
  
  wend
 END IF
  %></form>
</table>
</body>
</html>
