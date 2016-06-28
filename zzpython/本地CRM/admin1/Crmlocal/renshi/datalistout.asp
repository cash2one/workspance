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
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>

</head>

<body>
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
	'response.Write(sql)
  Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "renshi_salesIncome"
	 .sqlOrder= "order by sales_date desc"
	 .sqlWhere= "WHERE  id>0 and sales_price>0"&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 500
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   'oPage.pageNav "?"&sear,""
   totalpg=cint(total/500)
   if total/500 > totalpg then
	  totalpg=totalpg+1
   end if
%>
<table width="100%" border="1" cellspacing="0" cellpadding="0" bgcolor="#666666">
  <tr>
    <td nowrap="nowrap" bgcolor="#f2f2f2">客户手机</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">客户姓名</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">归属月份</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">周节奏</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">星期</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">销售人员</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">归属部门</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">归属连队</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">到单日期</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">产品分类</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">客户分类</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">到帐金额</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">邮箱</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">到单来源</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">增值类型</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">到单周期</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">产品量级</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">付款方式</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">促销形式</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">客户地区</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">客户经营产品</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">登入次数</td>
    <td nowrap="nowrap" bgcolor="#f2f2f2">再生通年限</td>
  </tr>
 <%
  
    if not rs.eof  then 
	While Not rs.EOF
  %>
  <tr>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("sales_mobile")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
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
			zwweek="日"
		case 2
			zwweek="一"
		case 3
			zwweek="二"
		case 4
			zwweek="三"
		case 5
			zwweek="四"
		case 6
			zwweek="五"
		case 7
			zwweek="六"
	end select
	response.Write(gsdatestr)
	
	%>
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">W<%=partmonth%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">周<%=zwweek%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("realname")%><%=rs("personid")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
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
    <td nowrap="nowrap" bgcolor="#FFFFFF">
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
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("sales_date")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("sales_type")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("service_type")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("sales_price")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("sales_email")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_ly1")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("service_type")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_zq")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_cpjb")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_hkfs")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_cxfs")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
      
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
    <td nowrap="nowrap" bgcolor="#FFFFFF">
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
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_logincount")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_servernum")%></td>
    </tr>
  <%
  rs.movenext
  
  wend
 END IF
  %>
</table>
</body>
</html>
<%
'导出excel文件
  response.Buffer
  Response.ContentType = "application/msexcel"
  Response.AddHeader "Content-disposition","attachment;filename="&request.QueryString("datefrom")&"-"&request.QueryString("dateto")&"销售到单列表.xls"
%>
