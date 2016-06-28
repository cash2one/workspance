<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<style type="text/css">
	body{
		margin:0;
		padding:0;
	}
</style>
<%

	  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&request("com_id")&" and a.personid=b.id"
	  set rsuser=conn.execute(sqluser)
	  if not rsuser.eof then
	  response.Write(rsuser("realname"))
	  end if
	  rsuser.close
	  set rsuser=nothing

conn.close
set conn=nothing

%>
