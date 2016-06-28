<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<%
com_id=request.QueryString("com_id")
gonghai_out=request.QueryString("gonghai_out")
c_NoContact=request.QueryString("c_NoContact")
if gonghai_out="" then
	sqlp="delete from crm_category_info where property_id='"&com_id&"' and property_value='10040002'"
	conn.execute(sqlp)
else
	sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='10040002'"
	set rsp=conn.execute(sqlp)
	if rsp.eof or rsp.bof then
		sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','10040002')"
		conn.execute(sqli)
	end if
	rsp.close
	set rsp=nothing
end if
if c_NoContact="" then
	sqlp="delete from crm_category_info where property_id='"&com_id&"' and property_value='10040008'"
	conn.execute(sqlp)
else
	sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='10040008'"
	set rsp=conn.execute(sqlp)
	if rsp.eof or rsp.bof then
		sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','10040008')"
		conn.execute(sqli)
	end if
	rsp.close
	set rsp=nothing
end if
conn.close
set conn=nothing
%>
