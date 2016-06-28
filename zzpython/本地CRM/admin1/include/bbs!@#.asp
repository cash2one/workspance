<%
Fy_Cl = 2		'处理方式：1=提示信息,2=转向页面,3=先提示再转向
Fy_Zx = "Error.Asp"	'出错时转向的页面
'On Error Resume Next
Fy_Url=Request.ServerVariables("QUERY_STRING")
Fy_a=split(Fy_Url,"&")
redim Fy_Cs(ubound(Fy_a))
'On Error Resume Next
for Fy_x=0 to ubound(Fy_a)
Fy_Cs(Fy_x) = left(Fy_a(Fy_x),instr(Fy_a(Fy_x),"=")-1)
Next
For Fy_x=0 to ubound(Fy_Cs)
If Fy_Cs(Fy_x)<>"" Then
If Instr(LCase(Request(Fy_Cs(Fy_x))),"'")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"select")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"update")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"chr")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"delete%20from")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),";")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"insert")<>0 or Instr(LCase(Request(Fy_Cs(Fy_x))),"mid")<>0 Or Instr(LCase(Request(Fy_Cs(Fy_x))),"master.")<>0 Then
Select Case Fy_Cl
  Case "1"
Response.Write "<Script Language=JavaScript>alert('      出现错误！参数 "&Fy_Cs(Fy_x)&" 的值中包含非法字符串！\n\n ！');window.close();</Script>"
  Case "2"
Response.Write "<Script Language=JavaScript>location.href='"&Fy_Zx&"'</Script>"
  Case "3"
Response.Write "<Script Language=JavaScript>alert('      出现错误！参数 "&Fy_Cs(Fy_x)&" 的值中包含非法字符串！\n\n ！ ');location.href='"&Fy_Zx&"';</Script>"
End Select
Response.End
End If
End If
Next
'On Error Resume Next
set conn=server.CreateObject("ADODB.connection")
strconn="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm"
conn.open strconn
set conn_bbs=server.CreateObject("ADODB.connection")
strconn1="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=zz91_bbs;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=zz91_bbs"
conn_bbs.open strconn1
sub endConnection() 
	if IsObject(conn) then
		if conn.state=1 then
			'response.Write("+")
			conn.close
			set conn=nothing
		else
			'response.Write("-")
		end if
		if conn_bbs.state=1 then
			'response.Write("+")
			conn_bbs.close
			set conn_bbss=nothing
		else
			'response.Write("-")
		end if
	end if
end sub 
%>