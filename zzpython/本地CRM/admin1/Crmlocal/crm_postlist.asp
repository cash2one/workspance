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
<body scroll=yes>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">
		   <TABLE id=secTable cellSpacing=0 cellPadding=0 border=0>
                                    <TR>
                                        <TD class=unselect onClick="window.location='<%if request.QueryString("service")="1" then response.Write("../compinfo/")%>crm_cominfoedit.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>'">基本信息</TD>
                                        <TD class=selected onclick=secBoard(1)>供求信息</TD>                                       
                                        <TD class=unselect onClick="window.location='crm_messageto.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>&service=<%response.Write(request("service"))%>'">发送的询盘信息</TD>                                        
                                        <TD class=unselect onClick="window.location='crm_messagefrom.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>&service=<%response.Write(request("service"))%>'">收到的询盘信息</TD>											
                                        
										<TD width="99%" class=unselect>&nbsp;</TD>
									
                			        </TR>
                			    </TABLE>
		  </td>
        </tr>
        <tr>
          <td valign="top">
		  <%
		  sear=sear&"idprod="&request("idprod")&"&cemail="&request("cemail")
		   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "pdt_id,fabuflag,pdt_image,pdt_kind,pdt_name,pdt_time_en,outflag"
				     .FROMTbl = "products"
				     .sqlOrder= "order by pdt_id desc"
				     .sqlWhere= "WHERE pdt_comemail='"&request("cemail")&"'"
				     .keyFld  = "pdt_id"    '不可缺少
				     .pageSize= 10
				     .getConn = conn
				     Set Rspdt  = .pageRsID
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "crm_postlist.asp?"&sear,""
				   totalpg=cint(total/10)
		  %>
		  <table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class=ee id=ListTable>
            <tr class="topline">
              <td>&nbsp;</td>
              <td>供需</td>
              <td>收到询盘</td>
              <td><A href="../?fabu=1&outuser=">审核发布时间</A></td>
              </tr>
			 <%
			      
				   if not Rspdt.eof then
				   do while not Rspdt.eof 
			 %>
            <tr>
              <td>
			  <%
			  if rspdt("outflag")="1" then
			  response.Write("询盘导出")
			  end if
			  %>
			  </td>
              <td><a href="../postedit.asp?postid=<%=rspdt("pdt_id")%>&frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>">
                                      <%
									  'response.Write(rspdt("fabuflag"))
							  if rspdt("fabuflag")="1" then
							  response.Write("<font color=#ff0000>◆</font>")
							  end if
							  %>
                                      (<%=getpdtkind(rspdt("pdt_kind"))%>)<%=replace(rspdt("pdt_name"),trim(request("pdt_name")),"<font color=#ff0000><b>"&trim(request("pdt_name"))&"</b></font>")%></a>
									   <a href="../postimage.asp?postid=<%=rspdt("pdt_id")%>&frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>">
                                      <%
								sqlpp="select * from productimg_biao where pdt_id="&rspdt("pdt_id")
								set rspp=conn.execute(sqlpp)
								if not rspp.eof or not rspp.bof  then
								  'serverpath= Server.MapPath("\")
								  'Set fso = CreateObject("Scripting.FileSystemObject")
								  'If (fso.fileExists(serverpath&"/upimages/"&rspp("path"))) Then
								   response.Write("<font color=#ff0000>图片</font>")
								  'else
								   'response.Write("图片")
								  'end if
								else
								  if rspdt("pdt_image")<>"" then
								  serverpath= Server.MapPath("\")
								  Set fso = CreateObject("Scripting.FileSystemObject")
									  If (fso.fileExists(serverpath&"/upimages/"&rspdt("pdt_image"))) Then
									  response.Write("<font color=#ff0000>图片</font>")
									  else
									  response.Write("图片")
									  end if
								  else
								  response.Write("图片")
								  end if
								%>
                                
                                <%end if%>
                                    </a>
    			</td>
              <td>
			  <%
	  		  'sqlm="select count(msg_id) from messages where msg_pdt_id="&rspdt("pdt_id")&""
              'set rsm=conn.execute(sqlm)
			  'oldmesfrom=rsm(0)
			  'rsm.close
			  'set rsm=nothing
			  sqlc="select com_id from comp_info where com_email='"&request("cemail")&"'"
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  	com_id=rsc(0)
			  end if
			  rsc.close
			  set rsc=nothing
			  sqlm="select count(id) from comp_question where fromactionid="&rspdt("pdt_id")&" and tocom_id="&com_id&""
              set rsm=conn.execute(sqlm)
			  mesfrom=rsm(0)
			  rsm.close
			  set rsm=nothing
			  response.Write(oldmesfrom+mesfrom)
			  %>
			  </td>
              <td><%=rspdt("pdt_time_en")%></td>
              </tr>
			       <%
				   Rspdt.movenext
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