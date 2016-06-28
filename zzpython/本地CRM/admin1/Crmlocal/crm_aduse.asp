<!-- #include file="../include/adfsfs!@#.asp" -->
<%
com_id=request.QueryString("com_id")
com_email=request.QueryString("com_email")
'sql="select com_subName,com_id from comp_info where com_id='"&com_id&"'"
'set rs=conn.execute(sql)
'if not(rs.eof and rs.bof) then
'	com_subName=rs(0)
'	com_id=rs(1)
'end if
'rs.close
'set rs=nothing

'url="http://"&com_subName&".zz91.com"

existsad=0
sql="select pdt_id from comp_keycomp where com_id="&com_id
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	existsad=1
end if
rs.close
set rs=nothing

sql="select id from adv_comshows where com_email='"&com_email&"'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	existsad=1
end if
rs.close
set rs=nothing

sql="select id from Adv_KeyOfferlist where com_email='"&com_email&"'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	existsad=1
end if
rs.close
set rs=nothing

sql="select id from Adv_KeyOfferlist_right where com_email='"&com_email&"'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	existsad=1
end if
rs.close
set rs=nothing

if existsad=1 then
	response.Write("<script>parent.document.getElementById('aduseButton').value='做过广告';parent.document.getElementById('aduseButton').className='aduse'</script>")
end if

conn.close
set conn=nothing
%>

