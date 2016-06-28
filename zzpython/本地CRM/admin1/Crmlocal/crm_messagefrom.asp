<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/pagefunction.asp"-->
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">
		   <TABLE id=secTable cellSpacing=0 cellPadding=0 border=0>
                                    <TR>
                                        <TD class=unselect onClick="window.location='<%if request.QueryString("service")="1" then response.Write("../compinfo/")%>crm_cominfoedit.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>&service=<%response.Write(request("service"))%>'">基本信息</TD>
                                        <TD class=unselect onClick="window.location='crm_postlist.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>&service=<%response.Write(request("service"))%>'">供求信息</TD>                                       
                                        <TD class=unselect onClick="window.location='crm_messageto.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>&service=<%response.Write(request("service"))%>'">发送的询盘信息</TD>                                        
                                        <TD class=selected onclick=secBoard(3)>收到的询盘信息</TD>											
                                        
										<TD width="99%" class=unselect>&nbsp;</TD>
									
                			        </TR>
		    </TABLE>
		  </td>
        </tr>
        <tr>
          <td valign="top"><%
		  sear=sear&"idprod="&request("idprod")&"&cemail="&request("cemail")
		   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,mtitle,mcontent,mdate,fromcom_id,tocom_id,sendflag,viewflag,backflag,fromactionid,fromaction,delflag"
				     .FROMTbl = "comp_question"
				     .sqlOrder= "order by id desc"
				     .sqlWhere= "WHERE tocom_id="&request("idprod")&""
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 20
				     .getConn = conn
				     Set rs  = .pageRsid
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "crm_messagefrom.asp?"&sear,""
				   totalpg=cint(total/20)
		  %>
		  <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class=ee id=ListTable>
            <tr class="topline">
              <td>&nbsp;</td>
              <td>产品</td>
              <td>时间</td>
              <td>发件人</td>
              <td>&nbsp;</td>
            </tr>
			 <%
			      
				   if not rs.eof then
				   do while not rs.eof 
			 %>
            <tr>
              <td nowrap>
			  <%
			   'response.Write(rs("backflag"))
			  if rs("sendflag")="1" then
			  response.Write("群发信息")
			  end if
			  if rs("sendflag")="2" then
			  response.Write("POST群发")
			  end if
			  response.write(" | ")
			  
			  if rs("backflag")="1" then
			  response.Write("回复的信息")
			  end if
			  if rs("viewflag")=0 then
			  response.Write("| <font color=#ff0000>未读</font>")
			  end if
			  if rs("delflag")="1" then
			  response.Write("| <font color=#ff0000>已删除</font>")
			  end if
			  %>
			  </td>
              <td>
			  <%
			  if rs("fromaction")="1" then
				sqlpdt="select pdt_name from products where pdt_id="&rs("fromactionid")&" and com_id="&rs("tocom_id")&""
				set rsp=conn.execute(sqlpdt)
				if not rsp.eof then
				 pdt_name=rsp("pdt_name")
				else
				 pdt_name="无"
				end if
				
				rsp.close()
				set rsp=nothing
			  end if
				
				%>
				<a target="_blank" href="../postedit.asp?postid=<%=rs("fromactionid")%>"><%=pdt_name%></a>			  </td>
              <td><%=rs("mdate")%></td>
              <td>
			  <%
		  if rs("fromcom_id")<>"0" then
		  sqlcomp="select com_email,com_name,com_subname,vip_check,vipflag from comp_info where com_id="&rs("fromcom_id")
		  set rsc=conn.execute(sqlcomp)
			  if not rsc.eof then
				  if rsc("vip_check")=1 and rsc("vipflag")=2 then
				  response.Write("<a href='http://"&rsc("com_subname")&".zz91.com' target=_blank>"&rsc("com_name")&"</a> <img src=http://www.zz91.com/cn/newimages/recycle.gif width=20 height=20 align=absmiddle>")
				  else
				  response.Write(rsc("com_name")&"<br>"&rsc("com_email"))
				  end if
			  else
			  response.Write("匿名会员")
			  end if
		  rsc.close
		  set rsc=nothing
		  else
		  response.Write("匿名会员")
		  end if
		  %>	
			  </td>
              <td><%=rs("mcontent")%></td>
            </tr>
			       <%
				   rs.movenext
				   loop
				   end if
				   %>
          </table></td>
        </tr>
    </table></td>
  </tr>

</table>
</body>
</html>