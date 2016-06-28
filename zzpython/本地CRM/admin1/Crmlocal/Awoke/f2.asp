<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
  Response.ContentType="text/xml"
   if Request.ServerVariables("REQUEST_METHOD")="POST" then
        Response.Write "<?xml version=""1.0""  encoding=""gb2312"" ?>"
        Response.Write "<mess>"
		'**************
		  sqlf="select top 1 id,tdate from crm_awoke where personid="&session("personid")&" order by tdate desc"
		  set rsf=server.createobject("adodb.recordset")
		  rsf.open sqlf,conn,1,2
		  if not rsf.eof or not rsf.bof then
		    response.Write("<user_ID>"&rsf(0)&"</user_ID>")
			response.Write("<user_code>"&rsf(1)&"</user_code>")
			'response.Write("<user_content>"&rsf(2)&"</user_content>")
		  end if
		  rsf.close
		  set rsf=nothing
          Response.Write "</mess>"
end if
conn.close
set conn=nothing
%>
