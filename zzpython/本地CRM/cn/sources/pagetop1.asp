<%
if request("page")="" then
page=1
else
page=request("page")
end if
set rscount=server.CreateObject("ADODB.recordset")
sqlcout="exec sp_ppages "&tblName&",'"&strGetFields&"',"&fldName&","&rn&","&page&","&doCount&","&OrderType&",'"&strWhere&"'"
rscount.open sqlcout,conn,1,1
total=rscount(0)
rscount.close
set rscount=nothing
totalpg=total/rn
arraytotalpg=split(totalpg,".")
if ubound(arraytotalpg)>0 then
totalpg=cint(arraytotalpg(0))+1
end if
%>
