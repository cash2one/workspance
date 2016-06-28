<!-- #include file="../../include/ad!$#5V.asp" -->
<%
set connx=Server.CreateObject("Adodb.Connection")
Driver = "Driver={Microsoft Excel Driver (*.xls)};" 
DBPath = "DBQ=" & Server.MapPath("20个保留客户.xls")
connx.open Driver&DBPath
sqlx="select * from [Sheet1$]"
conn.execute("delete from temp_baoliucomp")
set rsx=connx.execute(sqlx)
i=1
while not rsx.eof
	
	email=replace(trim(rsx(0).value),"?","")
	sqlt="select com_id from comp_info where com_email='"&email&"'"
	set rst=conn.execute(sqlt)
	if not rst.eof or not rst.bof then
	   sqlg="select * from temp_baoliucomp where com_id="&rst(0)
	   set rsg=conn.execute(sqlg)
	   if not rsg.eof or not rsg.bof then
	   	 
	   else
	   	 sqlr="insert into temp_baoliucomp(com_id) values("&rst(0)&")"
		 conn.execute(sqlr)
	   end if
	   rsg.close
	   set rsg=nothing
	   response.Write(i&"<br>")
	else
	   response.Write(email&"<br>")
	end if
	i=i+1
	rsx.movenext
wend
rsx.close
set rsx=nothing
connx.close
set connx=nothing
conn.close
set conn=nothing
%>
