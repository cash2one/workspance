<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
fromdate=request("fromdate")
todate=request("todate")
sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 rsuser.close
 set rsuser=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<script>
// 排序 tableId: 表的id;iCol: 第几列;dataType：iCol对应的列显示数据的数据类型
function sortAble(tableId, iCol, dataType) {
 var table = document.getElementById(tableId);
 var tbody = table.tBodies[0];
 var colRows = tbody.rows;
 var aTrs = new Array;
 // 将得到的列放入数组，备用
 for (var i=0; i < colRows.length; i++) {
  aTrs[i] = colRows[i];
 }

 // 判断上一次排列的列和现在需要排列的是否同一个。
 if (table.sortCol == iCol) {
  aTrs.reverse();
 } else {
  // 如果不是同一列，使用数组的sort方法，传进排序函数
  aTrs.sort(compareEle(iCol, dataType));
 }

 var oFragment = document.createDocumentFragment();
 for (var i=0; i < aTrs.length; i++) {
  oFragment.appendChild(aTrs[i]);
 }
 tbody.appendChild(oFragment);
 // 记录最后一次排序的列索引
 table.sortCol = iCol;
}

// 将列的类型转化成相应的可以排列的数据类型
function convert(sValue, dataType) {
 switch(dataType) {
  case "int":
   return parseInt(sValue);
  case "float":
   return parseFloat(sValue);
  case "date":
   return new Date(Date.parse(sValue));
  default:
   return sValue.toString();
 }
}

// 排序函数，iCol表示列索引，dataType表示该列的数据类型
function compareEle(iCol, dataType) {
 return function (oTR1, oTR2) {
  var vValue1 = convert(ieOrFireFox(oTR1.cells[iCol]), dataType);
  var vValue2 = convert(ieOrFireFox(oTR2.cells[iCol]), dataType);
  if (vValue1 < vValue2) {
   return -1;
  } else if (vValue1 > vValue2) {
   return 1;
  } else {
   return 0;
  }
 };
}

function ieOrFireFox(ob) {
 if (ob.textContent != null)
  return ob.textContent;
 var s = ob.innerText;
 return s.substring(0, s.length);
}
</script>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
<form id="form1" name="form1" method="post" action="rizhitongji.asp"> <tr>
    <td colspan="3" align="center" bgcolor="#FFFFFF"><script language=javascript>createDatePicker("fromdate",false,"<%=fromdate%>",false,false,false,false)</script>
      <script language=javascript>createDatePicker("todate",false,"<%=todate%>",false,false,false,false)</script>  
      <input type="submit" name="button" id="button" value="查询" /></td>
 </tr></form>
 </table>
 <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666" id="tableId">
 <thead>
  <tr>
   <th bgcolor="#CCCCCC" style="cursor:pointer" onClick="sortAble('tableId', 0)">销售</th>
   <th bgcolor="#CCCCCC" style="cursor:pointer" onClick="sortAble('tableId', 1)">战队</th>
   <th bgcolor="#CCCCCC" style="cursor:pointer" onClick="sortAble('tableId', 2,'int')">访问量</th>
   
  </tr>
 </thead>
<%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			  	  sqlc="select code,meno from cate_adminuser where code in ("&ywadminid&") and closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'  and closeflag=1"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'  and closeflag=1"
				  end if
			  end if
			  if session("userid")="10" then
				  sqlc="select code,meno from cate_adminuser where code like '13%'  and closeflag=1"
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
  
 
  
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
                        
  <tr>
    <td bgcolor="#FFFFFF"><a href="rizhitongji_person.asp?personid=<%=rsu("id")%>" target="_blank"><%response.Write(rsu("realname"))%></a></td>
    <td bgcolor="#FFFFFF"><%response.Write(rsc("meno"))%></td>
    <td bgcolor="#FFFFFF">
    <a href="viewHistory.asp?fromdate=<%=fromdate%>&todate=<%=todate%>&doperson=<%=rsu("id")%>" target="_blank"><%
	sql=""
	if request("fromdate")<>"" then
   	  sql=sql&" and fdate>='"&request("fromdate")&"'"
	  sear=sear&"&fromdate="&request("fromdate")
   end if
   if request("todate")<>"" then
   	  sql=sql&" and fdate<='"&request("todate")&"'"
	  sear=sear&"&todate="&request("todate")
   end if
	sqlp="select count(0) from crm_viewHistory where personid="&rsu("id")&" "&sql
	set rsp=conn.execute(sqlp)
	response.Write(rsp(0))
	%></a>
    </td>
  </tr>
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
</table>
</body>
</html>
