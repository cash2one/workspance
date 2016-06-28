<!-- #include file="../../include/ad!$#5V.asp" -->
<%
lastcontacttime1=request.Form("lastcontacttime1")
lastcontacttime2=request.Form("lastcontacttime2")
lastlogintime1=request.Form("lastlogintime1")
lastlogintime2=request.Form("lastlogintime2")
regtime1=request.Form("regtime1")
regtime2=request.Form("regtime2")
province=request.Form("province")
togonghaitime1=request.Form("togonghaitime1")
togonghaitime2=request.Form("togonghaitime2")
telcount1=request.Form("telcount1")
notelcount1=request.Form("notelcount1")
telcount2=request.Form("telcount2")
notelcount2=request.Form("notelcount2")
comrank=request.Form("comrank")
trade=request.Form("trade")
star5=request.Form("star5")
star4=request.Form("star4")
logincount1=request.Form("logincount1")
logincount2=request.Form("logincount2")
telpersoncount1=request.Form("telpersoncount1")
telpersoncount2=request.Form("telpersoncount2")

adminreg=request.Form("adminreg")
assign_time=request.Form("assign_time")
assigncount=request.Form("assigncount")

sql="select * from icd_gonghai_assign where id is null"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,3
if rs.eof or rs.bof then
	rs.addnew()
	if lastcontacttime1<>"" then
		rs("lastcontacttime1")=lastcontacttime1
	end if
	if lastcontacttime2<>"" then
		rs("lastcontacttime2")=lastcontacttime2
	end if
	if lastlogintime1<>"" then
		rs("lastlogintime1")=lastlogintime1
	end if
	if lastlogintime2<>"" then
		rs("lastlogintime2")=lastlogintime2
	end if
	if regtime1<>"" then
		rs("regtime1")=regtime1
	end if
	if regtime2<>"" then
		rs("regtime2")=regtime2
	end if
	if province<>"«Î—°‘Ò..." then
		rs("province")=province
	end if
	
	if togonghaitime1<>"" then
		rs("togonghaitime1")=togonghaitime1
	end if
	if togonghaitime2<>"" then
		rs("togonghaitime2")=togonghaitime2
	end if
	if telcount1<>"" then
		rs("telcount1")=telcount1
	end if
	if telcount2<>"" then
		rs("telcount2")=telcount2
	end if
	if notelcount1<>"" then
		rs("notelcount1")=notelcount1
	end if
	if notelcount2<>"" then
		rs("notelcount2")=notelcount2
	end if
	if telpersoncount1<>"" then
		rs("telpersoncount1")=telpersoncount1
	end if
	if telpersoncount2<>"" then
		rs("telpersoncount2")=telpersoncount2
	end if
	
	if comrank<>"" then
	rs("comrank")=comrank
	end if
	rs("trade")=trade
	if star5<>"" then
	rs("star5")=star5
	end if
	if star4<>"" then
	rs("star4")=star4
	end if
	if logincount1<>"" then
	rs("logincount1")=logincount1
	end if
	if logincount2<>"" then
	rs("logincount2")=logincount2
	end if
	rs("adminreg")=adminreg
	if assign_time<>"" then
		rs("assign_time")=assign_time
	end if
	if assigncount<>"" then
		rs("assigncount")=assigncount
	end if
	rs.update()
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
response.Redirect("gonghai_fl_list.asp")

%>
