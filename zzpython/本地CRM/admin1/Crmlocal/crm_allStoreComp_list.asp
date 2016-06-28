<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
DIM startime,endtime
startime=timer()
page=Request("page")
'''''''''''''''''''''''''''''''''''
sear="en=1"
    if isnumeric(page) then
    else
	    page=1
    end if
if page="" then 
page=1 
end if
  ''''''''''''''''''''''''''''''''''''''''''
 sqluser="select realname,ywadminid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 rsuser.close
 set rsuser=nothing
 
searchComName=trim(request("searchComName"))
searchStoreType=trim(request("searchStoreType"))
selectCount=trim(request("selectCount"))
selectPrice=trim(request("selectPrice"))
dopersonid=trim(request("dopersonid"))
if searchComName<>"" then
	sql0=sql0&" and exists(select null from comp_info where com_id=crm_compStore.com_id and com_name like '%"&searchComName&"%')"
	sear=sear&"&searchComName="&searchComName
end if
if searchStoreType<>"" then
	sql0=sql0&" and StoreCate like '%"&searchStoreType&"%'"
	sear=sear&"&searchStoreType="&searchStoreType
end if
if dopersonid<>"" then
	sql0=sql0&" and personid ="&dopersonid
	sear=sear&"&dopersonid="&dopersonid
end if
if selectCount<>"" then
	if selectCount="1" then
		sqlorder=" order by storeCount"
	elseif selectCount="2" then	
		sqlorder=" order by storeCount desc"
	end if
	sear=sear&"&selectCount="&selectCount
elseif selectPrice<>"" then
	if selectPrice="1" then
		sqlorder=" order by storePrice"
	elseif selectPrice="2" then	
		sqlorder=" order by storePrice desc"
	end if
	sear=sear&"&selectPrice="&selectPrice
else
	sqlorder="order by id desc"
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>crm 公司列表</title>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>

<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.crm_tishi {
	background-color: #FFFFCC;
	padding: 2px;
	border: 1px solid #CCCCCC;
}
-->
</style>
</head>

<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <form name="form2" method="get" action="">
  

    
    <tr>
      <td height="30" align="center" bgcolor="#f2f2f2">
      	<label for="searchComName">公司名:</label>
      	<input type="text" name="searchComName" id="searchComName" value="<%= searchComName %>">
      	
      	<label for="searchStoreType">产品类别</label>
      	<input type="text" name="searchStoreType" id="searchStoreType" size="15" value="<%= searchStoreType %>">
      	 数量排序:
      	 <select name="selectCount" id="selectCount">
      	 	<option value="">无</option>
      	 	<option value="1" <% If selectCount="1" Then response.write "selected" %>>升序</option>
      	 	<option value="2" <% If selectCount="2" Then response.write "selected" %>>降序</option>
      	 	</select>
      	 价格排序:
      	 <select name="selectPrice" id="selectPrice">
				<option value="">无</option>
				<option value="1" <% If selectPrice="1" Then response.write "selected" %>>升序</option>
				<option value="2" <% If selectPrice="2" Then response.write "selected" %>>降序</option>
			</select>
      	 拥有者:<select name="dopersonid" class="button" id="dopersonid" >
              <option value="" >请选择--</option>
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("dopersonid"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
					 %>
            </select>		
      	 <input type="submit" name="Submit3" value="搜 索" class=button>	  </td>
    </tr>
  </form>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" class=ee id=ListTable border="0" cellpadding="2" cellspacing="0">
             
                <tr class="topline"> 
                  <td bgcolor="#DFEBFF"><span class="STYLE2">公司名称</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">囤货产品类别</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">囤货价格(元/吨)</td>
                  
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>囤货数量(吨)</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>囤货描述</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>入库时间</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">拥有者</td>
                  </tr>
               <% 
			   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
					 .FROMTbl = "crm_CompStore"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE 1=1 "&sql0
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 16
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				  ' oPage.pageNav "?"&sear,""
				   totalpg=cint(total/16)
				   if total/16 > totalpg then
				   	  totalpg=totalpg+1
				   end if
			   
			   if not rs.eof then
				   while not rs.eof
					%>
					<tr> 
					 
					 
					
					  <td height="24" nowrap><a href="http://www.zz91.com/admin1/crm/compAbstract.asp?com_id=<%= rs("com_id") %>" target="_blank"><%= getComName(rs("com_id")) %></a></td>
					  <td><%= rs("storeCate") %></td>
					  <td><%= rs("storePrice") %></td>
					 
					  <td nowrap><%= rs("storeCount") %></td>
					  <td nowrap><%= rs("storeDesc") %></td>
					  
					  <td nowrap><%= rs("fdate") %></td>
					  <td align="center" nowrap>
					  <%  sqluser="select realname from users where id="&rs("personid")
						 set rsuser=conn.execute(sqluser)
						 if not(rsuser.eof and rsuser.bof) then
							 response.write rsuser(0)
						end if
						 rsuser.close
						 set rsuser=nothing %>					  </td>
									 </tr>
					<%
												   
				
				rs.movenext
				wend 
			
			else

				  %>
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="14">暂时无信息！</td>
                </tr>
                <%end if
			  rs.close
			  set rs=nothing
			  %>
              
            </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25"><!-- #include file="../include/page.asp" --></td>
  </tr>
 
</table>

</body>
</html>
<!-- #include file="../../conn_end.asp" -->
<% 
function getComName(com_id)
	dim sql,rs
	sql="select com_name from comp_info where com_id="&com_id
	set rs=conn.execute(sql)
	if not(rs.eof and rs.bof) then
		getComName=rs(0)
	else
		getComName=""
	end if
	rs.close
	set rs=nothing
end function
 %>