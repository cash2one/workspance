<%

'set rscount=server.CreateObject("ADODB.recordset")
'sqlcout="exec sp_ppages "&tblName&",'"&strGetFields&"',"&fldName&","&rn&","&page&","&doCount&","&OrderType&",'"&strWhere&"'"
'rscount.open sqlcout,conn,1,1
rn=20
'set rscount=Cmd.execute
total=tTotalCount
'total=Cmd.Parameters("@intPageCount").Value
'response.Write(total)
'rscount.close
'set rscount=nothing
totalpg=cint(total/rn)
'response.Write(total)
'arraytotalpg=split(totalpg,".")
'if ubound(arraytotalpg)>0 then
'totalpg=cint(arraytotalpg(0))+1
'end if
%>