<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<% 
reply=trim(request("reply"))
diaryid=request("diaryid")
if reply="" then
	response.write("<script>alert('请输入回复内容!');history.back();</script>")
	response.end
else
	reply=replace(reply,"'","‘")
	conn.execute "update crm_diary set haveReply=1,replyContent='"&reply&"' where id="&diaryid
end if
conn.close
set conn=nothing
response.write ("<script>alert('回复成功!');location.href='admin_diary_list.asp?menuid=1';</script>")
response.end
 %>