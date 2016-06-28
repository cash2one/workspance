<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<script>
parent.document.getElementById("trcom").style.display="none";
parent.document.getElementById("succtext").innerHTML="正在导入数据...";

</script>

<%
function changeNum(str)
	changeNum=""
	if isnull(str) then
	else
	for i=1 to len(str)
		if IsNumeric(mid(str,i,1)) then
			changeNum=changeNum&cint(mid(str,i,1))
		else
			'changeNum=changeNum&cstr(mid(str,i,1))
		end if
	next
	end if
end function
function clearcompmobile(com_mobile)
	'----------------判断手机重复
	if com_mobile<>"" and not isnull(com_mobile) then
		n=1
		b=""
		a=""
		com_mobile=changeNum(com_mobile)
		com_mobile=replace(com_mobile,"-","")
		for i=1 to len(com_mobile)
			a=mid(com_mobile,i,1)
			if cstr(a)=cstr(b) then
				n=n+1
			end if
			b=a
		next
		if n>4 or left(com_mobile,7)="1234567" then 
			com_mobile=""
		end if
		if len(com_mobile)>11 then
			com_mobile=left(com_mobile,11)
		end if
		if len(com_mobile)<11 then
			com_mobile=""
		end if
	else
		com_mobile=""
	end if
	clearcompmobile=com_mobile
end function
  outsql=request.Form("outsql")
  companyIds=request.Form("companyIds")
  if companyIds<>"" then
  	companyIds=left(companyIds,len(companyIds)-1)
  end if
  

  outsql="select com_id,com_mobile from v_salescomp_g where not EXISTS (select com_id from test where com_id=v_salescomp_g.com_id)"&outsql
  'outsql=outsql&" and com_id not in ("&companyIds&")"
 ' response.Write(outsql)
  set rs=conn.execute(outsql)
  comidlist=""
  mobiles=""
  if not rs.eof or not rs.bof then
	  while not rs.eof
		com_mobile=rs("com_mobile")
		com_mobile=clearcompmobile(com_mobile)
		if com_mobile<>"" then
			comidlist=comidlist&rs("com_id")&","
			mobiles=mobiles&com_mobile&","
		end if
	  rs.movenext
	  wend
  else
  	
  end if
  rs.close
  set rs=nothing
  'response.Write(comidlist)
  comidlist=left(comidlist,len(comidlist)-1)
  mobiles=left(mobiles,len(mobiles)-1)
  %>
  <script>
  parent.document.getElementById("trcom").style.display="";
  parent.document.getElementById("succtext").innerHTML="导入成功！";
  parent.form1.companyIds.value="<%=comidlist%>"
  parent.form1.mobiles.value="<%=mobiles%>"
  
  parent.form2.companyIds.value="<%=comidlist%>"
  parent.form2.mobiles.value="<%=mobiles%>"
  //parent.form2.submit();
  </script>
  <%
conn.close
set conn=nothing
%>