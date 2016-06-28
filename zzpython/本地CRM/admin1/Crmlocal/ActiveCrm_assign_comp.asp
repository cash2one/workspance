
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
sqlT="select max(id) from crm_active_AssignTimes"
set rst=conn.execute(sqlt)
if not rst.eof then
	maxTimes=rst(0)+1
end if
rst.close
set rst=nothing
if request("fdate")<>"" then

maxcomp=100
	  Set Fso = Server.CreateObject("Scripting.FileSystemObject")
	  Filen="E:\web\crm\ActiveAssign.txt"
	  Set Site_Config=FSO.CreateTextFile(Filen,true, False)
	  Site_Config.Write request("Toperson")
	  Site_Config.Close
	  Set Fso = Nothing
	filename="E:\web\crm\ActiveAssign.txt"
	set myfile=server.createobject("scripting.filesystemobject")
	set mytext=myfile.opentextfile(filename)
	content=mytext.readall
	arrperson=split(content,",",-1,1)
        for a=0 to ubound(arrperson)
								sqlc="select top "&maxcomp&" com_id from v_SalesCompActive where exists(select com_id from Crm_Active_CompAll where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from crm_Active_assign where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_SalesCompActive.com_id) "
								set rsc=conn.execute(sqlc)
								if not rsc.eof then
								while not rsc.eof
									 sqlt="insert into crm_Active_assign(com_id,personid,fdate,AssignTimes) values("&trim(rsc(0))&","&trim(arrperson(a))&",'"&request("fdate")&"',"&maxTimes&")"
			                         conn.execute(sqlt)
								rsc.movenext
								wend
							end if
						response.Write(arrperson(a)&"分配成功！<br>")
						rsc.close
						set rsc=nothing
		next
		sqlt="insert into crm_active_AssignTimes(AssignTimes) values("&maxTimes&")"
		conn.execute(sqlt)
		response.Redirect("ActiveCrm_Assign_Suc.asp?T="&maxTimes&"")
end if
%>
<form id="form1" name="form1" method="post" action="">
  <table width="100%" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td>分配时间：
    <input name="fdate" type="text" id="fdate" value="<%=date()%>" />
    分配次数
    <input name="textfield" type="text" size="5" readonly value="<%=maxTimes%>"/>
    次</td>
    </tr>
    <tr>
      <td>分配给
	<%
	filename="E:\web\crm\ActiveAssign.txt"
	set myfile=server.createobject("scripting.filesystemobject")
	set mytext=myfile.opentextfile(filename)
	content=mytext.readall
	arrperson=split(content,",",-1,1)
						sqlu="select realname,id from users where closeflag=1 and userid=21"
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,2
						if not rsu.eof then
						do while not rsu.eof
						sle=""
						for i=0 to ubound(arrperson)
							if cstr(trim(arrperson(i)))=cstr(rsu("id")) then
								sle="checked"
							end if
						next
						%>
    <input name="Toperson" type="checkbox" id="Toperson" <%=sle%> value="<%=rsu("id")%>"/><%=rsu("realname")%>
	<%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
					
					 %>
    <input type="submit" name="Submit" value="开始分配" />
</td>
    </tr>
  </table>
  
    </form>