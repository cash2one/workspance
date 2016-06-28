<!-- #include file="adfsfs!@#.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
set conn1=server.CreateObject("ADODB.connection")
strconn1="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm"
conn1.open strconn1
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
sear=sear&"n="
Set oPage=New clsPageRs2
		With oPage
		 .sltFld  = "com_mobile,com_tel,id"
		 .FROMTbl = "company1"
		 .sqlOrder= "order by id desc"
		 .sqlWhere= "WHERE not EXISTS (select id from p where id=company1.id) "&sql
		 .keyFld  = "id"    '不可缺少
		 .pageSize= 50
		 .getConn = conn
		 Set Rs  = .pageRs
		End With
		total=oPage.getTotalPage
		oPage.pageNav "?"&sear,""
		totalpg=cint(total/50)
		if total/50 > totalpg then
			totalpg=totalpg+1
		end if
		if not rs.eof then
		while not rs.eof
				mobile=right(rs("com_mobile"),6)
				com_id=rs("id")
				tel=right(rs("com_tel"),6)
				existsmobile=0
			if mobile<>"" or tel<>"" then
				sqlr="select com_id from comp_info where not EXISTS (select null from test where com_id=comp_info.com_id)"
				 
								if mobile="" or isnull(mobile) then
									ext=0
								else
									ext=1
									sqlr=sqlr&" and ( com_mobile like '%"&mobile&"')" 
								end if
								
								'if mobile="" or isnull(mobile) then
'									if tel="" or isnull(tel) then
'									else
'										sqlr=sqlr&" com_tel like '%"&tel&"'"
'									end if
'								else
'									if tel="" or isnull(tel) then
'									else
'										sqlr=sqlr&" or com_tel like '%"&tel&"'"
'									end if
'								end if
								'sqlr=sqlr&" ) "
				'set rsr=conn1.execute(sqlr)
				if ext=1 then
					set rsr=conn1.execute(sqlr)
					if not rsr.eof then
					while not rsr.eof
					
						exiatcom=rsr("com_id")
						sqlb="select cid from PipeiComp where cid="&rs("id")&" and com_id="&exiatcom&""
						set rsb=conn.execute(sqlb)
						if  rsb.eof or rsb.bof then
							sqlu="insert into PipeiComp(cid,com_id) values("&rs("id")&","&exiatcom&")"
							conn.execute(sqlu)
							response.Write("匹配成功！"&sqlr&"<br>")
						end if
						
					rsr.movenext
					wend
					end if
					rsr.close
					set rsr=nothing
					'response.Write(sqlr&"<br>")
					
				end if	
			end if
			sqlu="insert into p(id) values("&rs("id")&")"
			conn.execute(sqlu)
		rs.movenext
		wend
		outcom=0
	    else
		outcom=1
		end if
		rs.close
		set rs=nothing
		conn.close
		set conn=nothing
		conn1.close
		set conn1=nothing
if outcom=0 then
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
response.Write("<script>setTimeout(""window.location='pipei.asp'"",1000)</script>")
end if
%>
</body>
</html>
