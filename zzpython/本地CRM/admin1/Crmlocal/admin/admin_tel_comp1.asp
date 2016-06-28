<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
frompagequrstr=Request.ServerVariables("QUERY_STRING")
dotype=request.QueryString("dotype")
lmaction=request("lmaction")
action=request("action")
adminuser=request("adminuser")
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>联系记录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../../css.css" rel="stylesheet" type="text/css">

<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#666666">
  <tr>
    <td height="25" align="center" bgcolor="#FFFFFF"><a href="?<%=replace(frompagequrstr,"&dotype=vapcomp","")%>">再生通销售</a></td>
    <td height="25" align="center" <%if dotype="vapcomp" then response.Write("bgcolor='#FF0000'") else response.Write("bgcolor='#FFFFFF'")%>><a href="?<%=replace(frompagequrstr,"&dotype=vapcomp","")%>&dotype=vapcomp">VAP销售</a></td>
    
  </tr>
</table>
<%

		comtype=request.QueryString("type")
		sear=sear&"personid="&request.QueryString("personid")&"&contacttype="&request.QueryString("contacttype")
		sql="select * from comp_tel where 1=1"
		if request.QueryString("contacttype")<>"" then
			sql=sql&" and contacttype='"&request.QueryString("contacttype")&"'"
		end if
		if request.QueryString("personid")<>"" then
			sql=sql&" and personid="&request.QueryString("personid")
		end if
		if request.QueryString("star")<>"" then
			sql=sql&" and com_rank='"&request.QueryString("star")&"'"
		end if
		tuohuiflag=request.QueryString("tuohuiflag")
		if tuohuiflag<>"" then
			sql=sql&" and com_id in (select com_id from crm_tuo_hui_comp where personid="&request.QueryString("personid")&" "
			
			sql=sql&")"
		end if
		if request.QueryString("yubeippt") then
			sql=sql&" and exists(select property_id from crm_category_info where comp_tel.com_id=property_id and property_value='10050001')"
		end if
		if request.QueryString("seoflag")<>"" then
			sql=sql&" and exists(select com_id from crm_seotel where comp_tel.com_id=com_id)"
		end if
		if cstr(request("fromdate"))<>cstr(request("todate")) then
			if request("fromdate")<>"" then
				sql=sql&" and teldate>='"&request("fromdate")&"'"
				sear=sear&"&fromdate="&request("fromdate")
			end if
			if request("todate")<>"" then
				sql=sql&" and teldate<='"&cdate(request("todate"))+1&"'"
				sear=sear&"&todate="&request("todate")
			end if
		else
			if request("fromdate")<>"" and request("todate")<>"" then
				sql=sql&" and teldate>'"&cdate(request("fromdate"))&"' and teldate<'"&cdate(request("todate"))+1&"'"
				sear=sear&"&fromdate="&request("fromdate")
				sear=sear&"&todate="&request("todate")
			end if
		end if
		if comtype="xuqian" then
			sql=sql&" and telflag=2"
		else
			if dotype="vapcomp" then
				sql=sql&" and telflag=4"
			elseif dotype="sms" then
				sql=sql&" and telflag=5"
			else
				sql=sql&" and telflag<4"
			end if
		end if
		if request("code")<>"" then
		  	sql=sql&" and personid in (select id from users where userid="&request("code")&"  and closeflag=1)"
			sear=sear&"&code="&request("code")
		end if
		
		'------大客户
		if lmaction="ad_dakehu" then
			sql=sql&" and exists(select com_id from crm_vap_complist where comp_tel.com_id=com_id) and exists(select com_id from comp_vapinfo where comp_tel.com_id=com_id) and (exists(select com_id from comp_payinfo where comp_tel.com_id=com_id and money>=8000)) and not EXISTS(select com_id from crm_notBussiness where com_id=comp_tel.com_id)"
		end if
		'------必杀期
		if lmaction="ad_bisha" then
			sql=sql&" and exists(select com_id from crm_vap_complist where comp_tel.com_id=com_id) and exists(select com_id from comp_vapinfo where comp_tel.com_id=com_id) and (exists(select com_id from AdvKeyWords where comp_tel.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 12 and DATEDIFF(MM, fromDate, GETDATE()) >= 10)) and not EXISTS(select com_id from crm_notBussiness where com_id=comp_tel.com_id)"
		end if
		'------小广告
		if lmaction="ad_xiaoguangao" then
			sql=sql&" and (exists(select com_id from comp_payinfo where comp_tel.com_id=com_id and money<2000))"
		end if
		'------黄金广告
		if lmaction="ad_huangjin" then
			sql=sql&" and exists(select com_id from crm_vap_complist where comp_tel.com_id=com_id) and exists(select com_id from comp_vapinfo where comp_tel.com_id=com_id) and (exists(select com_id from AdvKeyWords where comp_tel.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and (exists(select com_id from AdvKeyWords where comp_tel.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and not EXISTS(select com_id from crm_notBussiness where com_id=comp_tel.com_id)"
		end if
		'------新客户
		if lmaction="ad_newcomp" then
			sql=sql&" and exists(select com_id from crm_vap_complist where comp_tel.com_id=com_id) and exists(select com_id from comp_vapinfo where comp_tel.com_id=com_id) and (exists(select com_id from AdvKeyWords where comp_tel.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 1 and DATEDIFF(MM, fromDate, GETDATE()) >= 0)) and not EXISTS(select com_id from crm_notBussiness where com_id=comp_tel.com_id)"
		end if
		'------过期客户
		if lmaction="ad_guoqi" then
			sql=sql&" and exists(select com_id from crm_vap_complist where comp_tel.com_id=com_id) and exists(select com_id from comp_vapinfo where comp_tel.com_id=com_id) and (exists(select com_id from AdvKeyWords where comp_tel.com_id=com_id and DATEDIFF(DD, toDate, GETDATE()) > 0 )) and not EXISTS(select com_id from crm_notBussiness where com_id=comp_tel.com_id)"
		end if
		if action="huangzhan" then
			sql=sql&" and exists(select com_id from advkeywords where  comp_tel.com_id=com_id and typeid='11' )"
		end if
		if action="biaowang" then
			sql=sql&" and exists(select com_id from advkeywords where  comp_tel.com_id=com_id and typeid='13' )"
		end if
		if action="dujia" then
			sql=sql&" and exists(select com_id from advkeywords where  comp_tel.com_id=com_id and typeid in(18,10,19,23,20,24,12,22,14,15) )"
		end if	
		sear=sear&"&lmaction="&lmaction
		sear=sear&"&action="&action
		if request.QueryString("orderaction")<>"" then
			sql=sql&" order by "&request.QueryString("orderaction")&" desc"
			sear=sear&"&orderaction="&request("orderaction")
		else
			sql=sql&" order by teldate desc"
		end if
		'response.Write(sql)
	    set rs=server.CreateObject("ADODB.recordset")
	    rs.open sql,conn,1,1
		%>
		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../../include/pagetop.asp"--></td>
          </tr>
		</table>
        <table width="90%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td bgcolor="#CED7E7"><a href="?personid=<%=request.QueryString("personid")%>&contacttype=<%=request.QueryString("contacttype")%>&from_date=<%=request.QueryString("from_date")%>&to_date=<%=request.QueryString("to_date")%>&orderaction=com_id">公司名称</a></td>
            <td bgcolor="#CED7E7"><a href="?personid=<%=request.QueryString("personid")%>&contacttype=<%=request.QueryString("contacttype")%>&from_date=<%=request.QueryString("from_date")%>&to_date=<%=request.QueryString("to_date")%>&orderaction=teldate">联系时间</a></td>
            <td bgcolor="#CED7E7">&nbsp;</td>
            <td bgcolor="#CED7E7">有效无效</td>
            <td bgcolor="#CED7E7">星级</td>
            <td bgcolor="#CED7E7">联系情况</td>
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
			  sqlc="select com_id,com_name,com_email from temp_salescomp where com_id="&rs("com_id")
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
				  com_name=rsc("com_name")
				  com_email=rsc("com_email")
			  end if
			  rsc.close
			  set rsc=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=com_name%><a href="../crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=<%=dotype%>" target="_blank">查看</a></td>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td nowrap bgcolor="#FFFFFF">
            <%
				sqlt="select id,filepath from crm_callrecord where telid="&rs("id")&""
				set rst=conn.execute(sqlt)
				pp=1
				if not rst.eof or not rst.bof then
					while not rst.eof
					response.Write("<a href='"&luyinurl&rst("filepath")&".WAV' target=_blank>录音("&pp&")</a><br>")
					pp=pp+1
					rst.movenext
					wend
				else
				%>
                <a href="/admin1/crmlocal/recordservice/gltel.asp?telid=<%=rs("id")%>&com_id=<%=rs("com_id")%>&personid=<%=rs("personid")%>" target="_blank">关联 </a>
                <%
				end if
				rst.close
				set rst=nothing
				%> 
            </td>
            <td bgcolor="#FFFFFF">
			<%
			select case rs("contacttype")
				case "12"
					response.Write("无效")
				case "13"
					response.Write("有效")
			end select
			%></td>
            <td nowrap bgcolor="#FFFFFF"><%=rs("com_rank")%>星</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%></td>
            </tr>
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  rs.close
		  set rs=nothing
		  
		  %>
		  <tr>
            <td colspan="6" align="right" bgcolor="#FFFFFF"><!-- #include file="../../include/page.asp" --></td>
          </tr>
</table>
<%
conn.close
set conn=nothing
%>
</body>
</html>
