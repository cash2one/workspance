<%

function ssname(personid)
	 if not isnull(personid) and personid<>"" then
	 sqluser="select realname from users where id="&personid
	 set rsuser=conn.execute(sqluser)
	 if not rsuser.eof then
	 response.Write(rsuser("realname"))
	 end if
	 end if
end function
function resalutq(id)
	select case id
	case 1
	response.Write("已经解决")
	case 2
	response.Write("待做中")
	case 3
	response.Write("有问题需要咨询")
	case 4
	response.Write("<font color=#0033CC>下周安排</font>")
	case else
	response.Write("<font color=#ff0000>未解决</font>")
	end select
end function

sqlu="select userqx from users where id="&session("personid")
set rsu=conn.execute(sqlu)
if not rsu.eof then
userqx=rsu(0)
end if
rsu.close
set rsu=nothing
if (session("userid")="14" and userqx="1") or session("userid")="10" then
techadmin=1
else
techadmin=0
end if
if session("userid")="14"  or session("userid")="10" then
dotechman=1
else
dotechman=0
end if
%>

