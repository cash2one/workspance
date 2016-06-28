<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
GroupID=DatePart("yyyy",now())&DatePart("m",now())&DatePart("d",now())&DatePart("h",now())&DatePart("n",now())&DatePart("s",now())
comid=request("comid")
arrcomp=split(comid,",")
sqlmain="insert into crm_compLink_main (GroupID,personid) values('"&GroupID&"',"&session("personid")&")"
conn.execute(sqlmain)
if comid<>"" then
	for i=0 to ubound(arrcomp)
		com_id=trim(arrcomp(i))
		'判断是否在个人库里
		flag=0
		sqlu="select com_id from crm_assign where com_id="&com_id
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rsu.bof then
			flag=1
		end if
		rsu.close
		set rsu=nothing
		'-----------------------
		sql="select com_id from crm_compLink where com_id="&com_id&" and GroupID='"&GroupID&"'"
		set rs=conn.execute(sql)
		sqlp=""
		if rs.eof or rs.bof then
			sqlp="insert into crm_compLink(Groupid,com_id,flag) values('"&Groupid&"',"&com_id&","&flag&")"
		end if
		rs.close
		set rs=nothing
		if sqlp<>"" then conn.execute(sqlp)
	next
end if
response.Write("提交成功！")
response.Redirect("p_suc.asp")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>提交客户关联成功</title>
</head>

<body>
</body>
</html>
<%
conn.close
set conn=nothing
%>