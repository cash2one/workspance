<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<% 
diaryid=request.Form("diaryid")
actontype=request.Form("actontype")
if diaryid="" then diaryid="0"
Total_today=request("Total_today")
Total_today1=request("Total_today1")
Total_tomo=request("Total_tomo")
star4cout=request.Form("star4cout")
star5cout=request.Form("star5cout")
replyContent=request.Form("replyContent")
star4comp=request.Form("star4comp")
star5comp=request.Form("star5comp")
coutzdcomp=request.Form("coutzdcomp")
coutzd=request.Form("coutzd")
rank5_today=request("rank5_today")
rank4_today=request("rank4_today")
rank3_today=request("rank3_today")
rank2_today=request("rank2_today")
rank1_today=request("rank1_today")
rank5_tomo=request("rank5_tomo")
rank4_tomo=request("rank4_tomo")
rank3_tomo=request("rank3_tomo")
rank2_tomo=request("rank2_tomo")
rank1_tomo=request("rank1_tomo")
writeDate=cdate(request("writeDate"))
isDraft=request("isDraft")
analysis_comid=request("analysis_comid")
array_comid=split(analysis_comid,",")
analysis_sign=request("analysis_sign")

Analysis_today=replace(request("Analysis_today"),"'","‘")
share=replace(request("share"),"'","‘")
if  share="" then
	response.write("<script>alert('日报请填写完整!');history.back();</script>")
	response.end	
end if
personid=request.Form("personid")
if personid="" then personid=session("personid")
set rs=server.createobject("adodb.recordset")

sql="select * from crm_diary where fDate='"&writeDate&"' and personid="&personid
rs.open sql,conn,1,3
if not (rs.eof and rs.bof) then
	if rs("haveReply")="1" and actontype<>"back" then
		response.write("<script>alert('你已经发表过"&writeDate&"的日志!');history.back();</script>")
		response.end
	elseif actontype="back" then
		'rs("share")=share
		rs("replyContent")=replyContent
		rs("haveReply")=1
		rs("replyperson")=session("personid")
		rs.update
	else
		rs("share")=share
		rs.update
	end if
else
	rs.addnew
	'rs("analysis_today")=analysis_today
	rs("share")=share
	rs("isDraft")=isDraft
	rs("total_today")=total_today
	rs("total_today1")=total_today1
	rs("star4cout")=star4cout
	rs("star5cout")=star5cout
	rs("Total_tomo")=Total_tomo
	rs("fDate")=date()
	rs("personid")=personid
	rs("haveReply")=0
	'rs("analysis_sign")=analysis_sign
	rs("star5comp")=star5comp
	rs("star4comp")=star4comp
	rs("coutzd")=coutzd
	rs("coutzdcomp")=coutzdcomp
	
	rs.update

	sql1="select * from crm_rankTotal  where id is null"
	set rs1=server.createobject("adodb.recordset")
	rs1.open sql1,conn,1,3
	rs1.addnew
	rs1("rank1")=rank1_today
	rs1("rank2")=rank2_today
	rs1("rank3")=rank3_today
	rs1("rank4")=rank4_today
	rs1("rank5")=rank5_today
	rs1("diaryid")=rs("id")
	rs1("todayOrTomo")=0
	rs1.update
	rs1.close
	set rs1=nothing
	sql1="select * from crm_rankTotal where id is null"
	set rs1=server.createobject("adodb.recordset")
	rs1.open sql1,conn,1,3
	rs1.addnew
	rs1("rank1")=rank1_tomo
	rs1("rank2")=rank2_tomo
	rs1("rank3")=rank3_tomo
	rs1("rank4")=rank4_tomo
	rs1("rank5")=rank5_tomo
	rs1("diaryid")=rs("id")
	rs1("todayOrTomo")=1
	rs1.update
	rs1.close
	set rs1=nothing
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
if diaryid="0" then
	response.write ("<script>alert('发表日志成功!');</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.end
elseif request("actontype")="back" then
	response.write ("<script>alert('回复成功!');</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.end
elseif request("actontype")="edit" then
	response.write ("<script>alert('修改成功!');</script>")
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.end
end if
 %>