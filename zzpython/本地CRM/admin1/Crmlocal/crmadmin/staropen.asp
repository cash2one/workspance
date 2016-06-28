<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<%
com_id=request.QueryString("com_id")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<script>
function closerecord()
{
	parent.document.getElementById('alertmsg').style.display='none';
	parent.document.getElementById('page_cover').style.display='none';
}
</script>
<style>
td
{
	font-size:16px;
	font-weight:bold;
}
</style>
</head>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
                              <%
							  sqlcate="select meno,code from crm_category where code like '1000____' order by ord asc"
							  set rscate=conn.execute(sqlcate)
							  if not rscate.eof or not rscate.bof then
							  i=0
							  while not rscate.eof
							  	sqls="select top 1 reson_check from crm_star5reson where com_id="&com_id&" and reson_typeid='"&rscate("code")&"' and personid="&session("personid")&""
								set rss=conn.execute(sqls)
								if not rss.eof or not rss.bof then
									if rss("reson_check")="1" then
										resoncheck1="checked"
										resoncheck2=""
									elseif rss("reson_check")="0" then
										resoncheck1=""
										resoncheck2="checked"
									else
										resoncheck1=""
										resoncheck2=""
									end if
									if rss("reson_check")="1" then
										bg="style='background-color:#CF0'"
									else
										bg="style='background-color:#ff0000'"
									end if
								else
									resoncheck1=""
									resoncheck2=""
								end if
								
								
								rss.close
								set rss=nothing
							  %>
                              <tr <%=bg%>>
                                        <td height="30" nowrap style="font-size:16px;"><%=rscate("meno")%><input type="hidden" name="reson_typeid" id="reson_typeid" value="<%=rscate("code")%>"></td>
                                <td><input type="radio" name="reson_check<%=rscate("code")%>" id="reson_check<%=rscate("code")%>" value="1" <%=resoncheck1%>>
                                  是
                                  <input type="radio" name="reson_check<%=rscate("code")%>" id="reson_check<%=rscate("code")%>" value="0" <%=resoncheck2%>>
                                  否</td>
                              </tr>
                              <%
							  i=i+1
							  rscate.movenext
							  wend
							  end if
							  rscate.close
							  set rscate=nothing
							  %><input type="hidden" name="reson_checklist" id="reson_checklist" value="0">
                            </table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><input type="button" name="button3" id="button3" value=" 关 闭 " onClick="closerecord()" /></td>
  </tr>
</table>
</body>
</html>
