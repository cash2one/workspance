<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style>
table
{
	font-size:12px;
}
.line
{
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #666;	
}
</style>
</head>

<body>
<%
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
function getdingdanlist(datestr,useridstr,userid)
	strvalue="<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
	sqld="select sales_price,realname from renshi_salesIncome where sales_date='"&datestr&"' and "&useridstr&""
	set rsd=conn.execute(sqld)
	if not rsd.eof or not rsd.bof then
		while not rsd.eof
			salesprice=rsd(0)
			if not isnull(salesprice) and salesprice<>"" then
				salespricestr=FormatNumber(salesprice,2)
			else
				salespricestr="0.00"
			end if 
			strvalue=strvalue&"<tr>"
			strvalue=strvalue&"<td align=left width='80' class='line'>"&rsd(1)&"</td>"
			strvalue=strvalue&"<td align=left class='line'>"&salespricestr&"</td>"
			strvalue=strvalue&"</tr>"
		rsd.movenext
		wend
	end if
	rsd.close
	set rsd=nothing
	strvalue=strvalue&"<tr>"
	strvalue=strvalue&"<td align=left width='80'><a href='/admin1/Crmlocal/renshi/datalist.asp?userid="&userid&"&datefrom="&datestr&"&dateto="&datestr&"' target=_blank>小计</a></td>"
	strvalue=strvalue&"<td align=left>"&getpricetongji("sales_date='"&datestr&"'"," and "&useridstr&"")&"</td>"
	strvalue=strvalue&"</tr>"
	strvalue=strvalue&"</table>"
	getdingdanlist=strvalue
end function
function getpricetongji(datestr,useridstr)
	sqld="select sum(sales_price) from renshi_salesIncome where "&datestr&" "&useridstr&""
	set rsd=conn.execute(sqld)
	if not rsd.eof or not rsd.bof then
		salesprice=rsd(0)
		if not isnull(salesprice) and salesprice<>"" then
			getpricetongji=FormatNumber(rsd(0),2)
		else
			getpricetongji="0.00"
		end if
	else
		getpricetongji="0.00"
	end if
	rsd.close
	set rsd=nothing
end function
nowdate=now
if request.Form("fmonth")<>"" then 
	nowdate=request.Form("fmonth")
end if
todate=cdate(year(nowdate)&"-"&month(nowdate)&"-"&tian_sum(year(nowdate)&"-"&month(nowdate)))
fromdate=year(nowdate)&"-"&month(nowdate)&"-1"
%>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <form id="form2" name="form2" method="post" action=""><tr>
    <td>选择月份：<script language=javascript>createDatePicker("fmonth",false,"<%=nowdate%>",true,true,true,true)</script>
      
        <input type="submit" name="button" id="button" value="提交" />
    </td>
  </tr></form>
</table>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="#000000">
  <tr>
    <td width="100" rowspan="3" align="center" bgcolor="#ebebeb">到单日期</td>
    <td align="center" bgcolor="#ebebeb">雪豹</td>
    <td align="center" bgcolor="#ebebeb">赤鹰</td>
    <td align="center" bgcolor="#ebebeb">战龙</td>
    <td rowspan="2" align="center" bgcolor="#ebebeb">ICD总计</td>
    <td rowspan="2" align="center" bgcolor="#ebebeb">VAP</td>
    <td rowspan="2" align="center" bgcolor="#ebebeb">CS</td>
    <td rowspan="2" align="center" bgcolor="#ebebeb">来电宝</td>
    <td rowspan="2" align="center" bgcolor="#ebebeb">合计</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#ebebeb">ICD1</td>
    <td align="center" bgcolor="#ebebeb">ICD3</td>
    <td align="center" bgcolor="#ebebeb">ICD4</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='1306'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='1302'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='1307'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid in (1306,1302,1307)")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='1315'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='24'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," and userid='1322'")%></td>
    <td align="center" bgcolor="#FFFF33"><%=getpricetongji("sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"," ")%></td>
  </tr>
<%
for i=fromdate to todate
%>
  <tr>
    <td align="center" bgcolor="#FFFFFF"><%=i%></td>
    <td align="center" bgcolor="#FFFFFF">
    
      <%
	  response.Write(getdingdanlist(i,"userid='1306'","1306"))
	  %>
    
    </td>
    <td align="center" bgcolor="#FFFFFF">
    
      <%
	  response.Write(getdingdanlist(i,"userid='1302'","1302"))
	  %>
    
    </td>
    <td align="center" bgcolor="#FFFFFF">
    
      <%
	  response.Write(getdingdanlist(i,"userid='1307'","1307"))
	  %>
   
    </td>
    <td align="center" bgcolor="#FFFFFF"><%=getpricetongji("userid in (1306,1302,1307)"," and sales_date='"&i&"'")%></td>
    <td align="center" bgcolor="#FFFFFF">
      <%
	  response.Write(getdingdanlist(i,"userid='1315'","1315"))
	  %>
    </td>
    <td align="center" bgcolor="#FFFFFF">
      
    <%
	  response.Write(getdingdanlist(i,"userid='24'","24"))
	  %></td>
    <td align="center" bgcolor="#FFFFFF"> 
    <%
	  response.Write(getdingdanlist(i,"userid='1322'","1322"))
	  %></td>
    <td align="center" bgcolor="#FFFFFF"><%=getpricetongji(""," sales_date='"&i&"'")%><br />
      <a href="/admin1/Crmlocal/renshi/datalist.asp?userid=&datefrom=<%=i%>&dateto=<%=i%>" target="_blank">查看</a></td>
  </tr>
<%
next
%>
</table>
</body>
</html>
