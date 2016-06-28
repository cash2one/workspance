<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<%

set rs=server.createobject("adodb.recordset")
sql="select * from users where id="&request("id") 
  rs.open sql,conn,1,1
  userid=rs("userid")
  chat_userid=rs("chat_userid")
  username=rs("name")
  password=rs("password")
  realname=rs("realname")
  userbg=rs("userbg")
  adminuserid=rs("adminuserid")
  email=rs("email")
  emailpass=rs("emailpass")
  userqx=rs("userqx")
  nicheng=rs("nicheng")
  partuserid=rs("partuserid")
  ywadminID=rs("ywadminID")
  usertel=rs("usertel")
  partid=rs("partid")
  rongyi=rs("rongyi")
  chat_warden=rs("chat_warden")
  chat_department=rs("chat_department")
  popchat_warden=rs("popchat_warden")
  popchat_department=rs("popchat_department")
  popchat_all=rs("popchat_all")
  chat_partuserid=rs("chat_partuserid")
  recordflag=rs("recordflag")
  userdengji=rs("userdengji")
  ywadminid_hy=rs("ywadminid_hy")
  huangye_check=rs("huangye_check")
  rs.close()
  set rs=nothing

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>用户修改</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
 <script language=JavaScript>
 function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
function subchk(frm)
{
	if(frm.userid.value=="")
	{
		alert("请选择部门!\n");
		frm.userid.focus();
		return  false;
	}
	if(frm.chat_userid.value=="")
	{
		alert("请选择聊天板部门!\n");
		frm.chat_userid.focus();
		return  false;
	}
	if  (frm.uname.value=="")       
	{   
		alert("请输入用户名称!\n");
		frm.uname.focus();
		return  false;                 
	} 
	if  (frm.password.value=="")       
	{   
		alert("请输入登录密码!\n");
		frm.password.focus();
		return  false;                 
	}  
}
    </script>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<style>
.plist
{
	float:left;
	width:150px;
}
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">用户修改</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        <table width="100%" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="600" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#666666">
              <form name="company_sec"  method="post" action="admin_aduser_save.asp" onSubmit="return subchk(this)">
              
                <tr>
                  <td width="200" align="right" bgcolor="#ebebeb">CRM部门</td>
                  <td bgcolor="#FFFFFF">
				  
				    <select name="userid" id="userid">
                    <option value="">请选择...</option>
				      <%
		  sqlw="select * from cate_adminuser where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
				      <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
				      <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__'  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
				      <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
				      <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__'  order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,1
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
				      <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
				      <%
		 rs2.movenext
		 loop
		 end if
		 rs2.close
		  %>
				      <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
				      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
				      </select>
                      <script>selectOption("userid","<%=userid%>")</script>
                      </td>
                  <td align="right" nowrap bgcolor="#ebebeb">聊天板部门</td>
                  <td bgcolor="#FFFFFF">
                  <select name="chat_userid" id="chat_userid">
				  <option value="">请选择...</option>
                      <%
		  sqlw="select * from cate_adminuser where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
				      <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
				      <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' and closeflag=1  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
				      <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
				      <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__' and closeflag=1  order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,1
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
				      <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
				      <%
		 rs2.movenext
		 loop
		 end if
		 rs2.close
		  %>
				      <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
				      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
				      </select>
                       <script>selectOption("chat_userid","<%=chat_userid%>")</script>
                      </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ebebeb">用户名
                    <input type="hidden" name="m" value="1">
                    <input type="hidden" name="id" value=<%=request("id")%>>
                    <input type="hidden" name="uname1" value="<%=trim(username)%>">                  </td>
                  <td bgcolor="#FFFFFF"><input name="uname" type="text" id="uname2" value="<%=trim(username)%>">                  </td>
                  <td align="right" bgcolor="#ebebeb">密码</td>
                  <td bgcolor="#FFFFFF"><input name="password" type="password" id="password2" value="<%=trim(password)%>"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ebebeb">真实姓名</td>
                  <td bgcolor="#FFFFFF"><input name="realname" type="text" id="realname2" value="<%=trim(realname)%>"></td>
                  <td align="right" bgcolor="#ebebeb">呢称</td>
                  <td bgcolor="#FFFFFF"><input name="nicheng" type="text" id="nicheng" value="<%=nicheng%>"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ebebeb">业务电话</td>
                  <td bgcolor="#FFFFFF"><input name="usertel" type="text" id="usertel" maxlength="8" value="<%=usertel%>"></td>
                  <td align="right" bgcolor="#ebebeb">是否掉公海</td>
                  <td bgcolor="#FFFFFF"><%
				  sqlg="select personid from crm_notdropTogonghai where personid="&request("id")&""
				  set rsg=conn.execute(sqlg)
				  if not rsg.eof or not rsg.bof then
				  	gonghaiflag="1"
				  else
				  	gonghaiflag="0"
				  end if
				  rsg.close
				  set rsg=nothing
				  %>
                    <input type="radio" name="gonghaiflag" id="radio3" value="1" <%if gonghaiflag="0" then response.Write("checked")%>>
是
<input type="radio" name="gonghaiflag" id="radio4" value="0" <%if gonghaiflag="1" then response.Write("checked")%>>
否 </td>
                </tr>
                
                <tr style="display:none">
                  <td align="right" bgcolor="#ebebeb">所在区</td>
                  <td bgcolor="#FFFFFF"><select name="partid" id="partid">
                    <option value="" >请选择...</option>
                    <%
					sql="select code,meno from cate_adminuserPart"
					set rs=conn.execute(sql)
					if not rs.eof then
					while not rs.eof
					%>
                    <option value="<%=rs("code")%>" >┆&nbsp;&nbsp;┿<%=rs("meno")%></option>
                    <%
					rs.movenext
					wend
					end if
					rs.close
					set rs=nothing
					%>
                  </select>
                    荣誉标志
<script>selectOption("partid","<%=partid%>")</script>
                  <input type="text" name="rongyi" id="rongyi" value="<%=rongyi%>"></td>
                  <td align="right" nowrap bgcolor="#ebebeb">&nbsp;</td>
                  <td bgcolor="#FFFFFF">&nbsp;</td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#ebebeb">录音</td>
                  <td bgcolor="#FFFFFF">
                    <input type="radio" name="recordflag" id="radio" value="1" <%if recordflag="1" then response.Write("checked")%>>
                    是
                      <input type="radio" name="recordflag" id="radio2" value="0" <%if recordflag="0" then response.Write("checked")%>>
                      否
                    </td>
                  <td align="right" bgcolor="#ebebeb">主管</td>
                  <td nowrap bgcolor="#FFFFFF"><input type="radio" name="userqx" value="1" <%if userqx="1" then response.Write("checked")%>>
是
  <input type="radio" name="userqx" value="0" <%if userqx="0" then response.Write("checked")%>>
否<select name="Partuserid" id="Partuserid">
<option value="">选择部门</option>
          <%
		  sqlw="select * from cate_adminuser where code like '__'  and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
		    <%if trim(partuserid)=trim(rsw("code")) then%>
                    <option value="<%=rsw("code")%>" selected>┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
            <%else%>
			       <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option> 
			<%end if%>       
				    <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' and closeflag=1 order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
			 <%if trim(partuserid)=trim(rs1("code")) then%>
                    <option value="<%=rs1("code")%>" selected>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
             <%else%>
			        <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
			 <%end if%>      
			<%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__' and closeflag=1 order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,1
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
			 <%if trim(partuserid)=trim(rs2("code")) then%>
                    <option value="<%=rs2("code")%>" selected>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
             <%else%> 
			        <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
			 <%end if%>    
					<%
		 rs2.movenext
		 loop
		 end if
		 rs2.close
		  %>
                    <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
                  </select></td>
                </tr>
                <!--<tr>
                  <td align="right">代表颜色</td>
                  <td><input name="userbg" type="text" id="userbg" value="<%=userbg%>"></td>
                </tr>-->
                
                <tr align="center">
                  <td align="right" bgcolor="#ebebeb">用户级别管理权限</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF">
                  <%
					sqlcate="select meno,code from crm_category where code like '1001____' order by ord asc"
				  set rscate=conn.execute(sqlcate)
				  if not rscate.eof or not rscate.bof then
				  while not rscate.eof
				  	checkflag=0
					if userdengji<>"" or not isnull(userdengji) then
						myuseriy=split(userdengji,",",-1,1)
						for ii=0 to ubound(myuseriy)
							if cstr(trim(myuseriy(ii)))=cstr(rscate("code")) then
								checkflag=1
							end if
						next
						if checkflag=1 then
							checkvalue="checked"
						else
							checkvalue=""
						end if
					end if
					%>
                    <div class="plist"><input name="userdengji" type="checkbox" id="userdengji" value="<%=rscate("code")%>" <%=checkvalue%>><%=rscate("meno")%></div>
				  <%
				  rscate.movenext
				  wend
				  end if
				  rscate.close
				  set rscate=nothing
				  %>
                  </td>
                </tr>
                <tr align="center" style="display:none">
                  <td align="right" bgcolor="#ebebeb">区长限制</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF">
				   <%
		  sqlw="select * from cate_adminuserPart where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
			'response.Write(adminuserid)
			checkflag=0
			if adminuserid<>"" or not isnull(adminuserid) then
			myuseriy=split(adminuserid,",",-1,1)
			for ii=0 to ubound(myuseriy)
				if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
				checkflag=1
				
				end if
			next
			if checkflag=1 then
			checkvalue="checked"
			else
			checkvalue=""
			end if
			'response.Write(checkvalue)
			end if
		   %>
				  <input name="adminuserid" type="checkbox" id="adminuserid" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%>
	                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>				  </td>
                </tr>
				<tr align="center">
                  <td align="right" bgcolor="#ebebeb">销售部门权限</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF">
				   <%
		  sqlw="select * from cate_adminuser where (code like '13%' or code like '__' or code like '33%' or  code like '24%' or  code like '42%') and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
		rsw.open sqlw,conn,1,1
		if not rsw.eof or not rsw.bof then
		do while not rsw.eof
		checkflag=0
		if ywadminid<>"" or not isnull(ywadminid) then
			myuseriy=split(ywadminid,",",-1,1)
			for ii=0 to ubound(myuseriy)
				if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
					checkflag=1
				end if
			next
			
		end if
		if checkflag=1 then
			checkvalue="checked"
		else
			checkvalue=""
		end if
		   %>
				  <div class="plist"><input name="ywadminid" type="checkbox" id="ywadminid" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
	                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>				  </td>
                </tr>
                <tr align="center">
                  <td align="right" bgcolor="#ebebeb">黄页录入权限</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF">
				   <%
		  sqlw="select * from cate_adminuser where (code like '13%' or code like '__' or code like '33%' or  code like '24%' or  code like '42%') and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
		rsw.open sqlw,conn,1,1
		if not rsw.eof or not rsw.bof then
		do while not rsw.eof
		checkflag=0
		if ywadminid_hy<>"" or not isnull(ywadminid_hy) then
			myuseriy=split(ywadminid_hy,",",-1,1)
			for ii=0 to ubound(myuseriy)
				if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
					checkflag=1
				end if
			next
			
		end if
		if checkflag=1 then
			checkvalue="checked"
		else
			checkvalue=""
		end if
		   %>
				  <div class="plist"><input name="ywadminid_hy" type="checkbox" id="ywadminid_hy" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
	                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>				  </td>
                </tr>
                <!---
                <tr align="center">
                  <td align="right" bgcolor="#ebebeb">聊天版主管权限</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF">
                  <%
		  sqlw="select * from cate_adminuser where code like '__'  order by ord asc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
				checkflag=0
				if chat_partuserid<>"" or not isnull(chat_partuserid) then
					myuseriy=split(chat_partuserid,",",-1,1)
					for ii=0 to ubound(myuseriy)
						if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
						checkflag=1
						end if
					next
					if checkflag=1 then
						checkvalue="checked"
					else
						checkvalue=""
					end if
				else
					checkvalue=""
				end if
		   %>
				  <div class="plist"><input name="chat_partuserid" type="checkbox" id="chat_partuserid" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
                  
		 <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
          <div style="clear:both"></div>
          ----------------------------------------------销售子部门
          <%
				  sqlw1="select * from cate_adminuser where code like '13__' and closeflag=1 order by ord asc"
				  set rsw1=server.CreateObject("adodb.recordset")
					rsw1.open sqlw1,conn,1,1
					if not rsw1.eof or not rsw1.bof then
					do while not rsw1.eof
						checkflag=0
						if chat_partuserid<>"" or not isnull(chat_partuserid) then
							myuseriy=split(chat_partuserid,",",-1,1)
							for ii=0 to ubound(myuseriy)
								if cstr(trim(myuseriy(ii)))=cstr(rsw1("code")) then
								checkflag=1
								end if
							next
							if checkflag=1 then
								checkvalue="checked"
							else
								checkvalue=""
							end if
						else
							checkvalue=""
						end if
				   %>
                    <div class="plist"><input name="chat_partuserid" type="checkbox" id="chat_partuserid" value="<%=rsw1("code")%>" <%=checkvalue%>>
				  <%=rsw1("meno")%></div>
                  <%
				 rsw1.movenext
				 loop
				 end if
				 rsw1.close
				  %>
              <div style="clear:both"></div>
          ----------------------------------------------环保子部门
          <%
				  sqlw1="select * from cate_adminuser where code like '33__' and closeflag=1 order by ord asc"
				  set rsw1=server.CreateObject("adodb.recordset")
					rsw1.open sqlw1,conn,1,1
					if not rsw1.eof or not rsw1.bof then
					do while not rsw1.eof
						checkflag=0
						if chat_partuserid<>"" or not isnull(chat_partuserid) then
							myuseriy=split(chat_partuserid,",",-1,1)
							for ii=0 to ubound(myuseriy)
								if cstr(trim(myuseriy(ii)))=cstr(rsw1("code")) then
								checkflag=1
								end if
							next
							if checkflag=1 then
								checkvalue="checked"
							else
								checkvalue=""
							end if
						else
							checkvalue=""
						end if
				   %>
                    <div class="plist"><input name="chat_partuserid" type="checkbox" id="chat_partuserid" value="<%=rsw1("code")%>" <%=checkvalue%>>
				  <%=rsw1("meno")%></div>
                  <%
				 rsw1.movenext
				 loop
				 end if
				 rsw1.close
				  %>
                  <div style="clear:both"></div>
                  ----------------------------------------------ZZ91运营子部门
          <%
				  sqlw1="select * from cate_adminuser where code like '42__' and closeflag=1 order by ord asc"
				  set rsw1=server.CreateObject("adodb.recordset")
					rsw1.open sqlw1,conn,1,1
					if not rsw1.eof or not rsw1.bof then
					do while not rsw1.eof
						checkflag=0
						if chat_partuserid<>"" or not isnull(chat_partuserid) then
							myuseriy=split(chat_partuserid,",",-1,1)
							for ii=0 to ubound(myuseriy)
								if cstr(trim(myuseriy(ii)))=cstr(rsw1("code")) then
								checkflag=1
								end if
							next
							if checkflag=1 then
								checkvalue="checked"
							else
								checkvalue=""
							end if
						else
							checkvalue=""
						end if
				   %>
                    <div class="plist"><input name="chat_partuserid" type="checkbox" id="chat_partuserid" value="<%=rsw1("code")%>" <%=checkvalue%>>
				  <%=rsw1("meno")%></div>
                  <%
				 rsw1.movenext
				 loop
				 end if
				 rsw1.close
				  %>
                  </td>
                </tr>
                <tr align="center">
                  <td align="right" bgcolor="#ebebeb">聊天版群发查看权限</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
                    <tr style="display:none">
                      <td width="80" align="right" valign="top" bgcolor="#FFFFFF">区</td>
                      <td align="left" valign="top" bgcolor="#FFFFFF">
		  <%
		  'response.Write(chat_warden)
		  sqlw="select * from cate_adminuserPart where code like '__' and closeflag=0 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
				checkflag=0
				if chat_warden<>"" or not isnull(chat_warden) then
					myuseriy=split(chat_warden,",")
					for ii=0 to ubound(myuseriy)
						if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
							checkflag=1
						end if
					next
					if checkflag=1 then
						checkvalue="checked"
					else
						checkvalue=""
					end if
				else
					checkvalue=""
				end if
		   %>
				  <div class="plist"><input name="chat_warden" type="checkbox" id="chat_warden" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
			<%
			 rsw.movenext
			 loop
			 end if
			 rsw.close
		   %></td>
                    </tr>
                    <tr>
                      <td width="80" align="right" valign="top" bgcolor="#FFFFFF">部门</td>
                      <td align="left" valign="top" bgcolor="#FFFFFF">
		  <%
		  sqlw="select * from cate_adminuser where code like '__' order by ord asc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
				checkflag=0
				if chat_department<>"" or not isnull(chat_department) then
					myuseriy=split(chat_department,",",-1,1)
					for ii=0 to ubound(myuseriy)
						if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
						checkflag=1
						end if
					next
					if checkflag=1 then
						checkvalue="checked"
					else
						checkvalue=""
					end if
				else
					checkvalue=""
				end if
		   %>
				  <div class="plist"><input name="chat_department" type="checkbox" id="chat_department" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
                  
		 <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
          <div style="clear:both"></div>
          -------------------------------------------销售子部门<br>
          <%
				  sqlw1="select * from cate_adminuser where code like '13__' and closeflag=1 order by ord asc"
				  set rsw1=server.CreateObject("adodb.recordset")
					rsw1.open sqlw1,conn,1,1
					if not rsw1.eof or not rsw1.bof then
					do while not rsw1.eof
						checkflag=0
						if chat_department<>"" or not isnull(chat_department) then
							myuseriy=split(chat_department,",",-1,1)
							for ii=0 to ubound(myuseriy)
								if cstr(trim(myuseriy(ii)))=cstr(rsw1("code")) then
								checkflag=1
								end if
							next
							if checkflag=1 then
								checkvalue="checked"
							else
								checkvalue=""
							end if
						else
							checkvalue=""
						end if
				   %>
                   <div class="plist"><input name="chat_department" type="checkbox" id="chat_department" value="<%=rsw1("code")%>" <%=checkvalue%>>
				  <%=rsw1("meno")%></div>
                  <%
				 rsw1.movenext
				 loop
				 end if
				 rsw1.close
				  %>
          </td>
                    </tr>
                  </table></td>
                </tr>
                <tr align="center">
                  <td align="right" bgcolor="#ebebeb">聊天版群发权限<br>
                    （自动弹出）</td>
                  <td colspan="3" align="left" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
                    <tr>
                      <td width="80" align="right" valign="top" nowrap bgcolor="#FFFFFF">取消所有人</td>
                      <td align="left" valign="top" bgcolor="#FFFFFF"><input name="popchat_all" type="checkbox" id="popchat_all" value="1" <%if popchat_all="1" then response.Write("checked")%>></td>
                    </tr>
                    <tr style="display:none">
                      <td align="right" valign="top" bgcolor="#FFFFFF">区</td>
                      <td align="left" valign="top" bgcolor="#FFFFFF">
		  <%
		  'response.Write(chat_warden)
		  sqlw="select * from cate_adminuserPart where code like '__' and closeflag=0 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
				checkflag=0
				if popchat_warden<>"" or not isnull(popchat_warden) then
					myuseriy=split(popchat_warden,",")
					for ii=0 to ubound(myuseriy)
						if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
							checkflag=1
						end if
					next
					if checkflag=1 then
						checkvalue="checked"
					else
						checkvalue=""
					end if
				else
					checkvalue=""
				end if
		   %>
				  <div class="plist"><input name="popchat_warden" type="checkbox" id="popchat_warden" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
			<%
			 rsw.movenext
			 loop
			 end if
			 rsw.close
		   %></td>
                    </tr>
                    <tr>
                      <td align="right" valign="top" bgcolor="#FFFFFF">部门</td>
                      <td align="left" valign="top" bgcolor="#FFFFFF">
		 <%
		  sqlw="select * from cate_adminuser where code like '__' order by ord asc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
				checkflag=0
				if popchat_department<>"" or not isnull(popchat_department) then
					myuseriy=split(popchat_department,",")
					for ii=0 to ubound(myuseriy)
						if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
							checkflag=1
						end if
					next
					if checkflag=1 then
						checkvalue="checked"
					else
						checkvalue=""
					end if
				else
					checkvalue=""
				end if
		   %>
				  <div class="plist"><input name="popchat_department" type="checkbox" id="popchat_department" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%></div>
                  
		<%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
         
          <div style="clear:both"></div>
           -----------------------销售子部门<br>
          <%
				  sqlw1="select * from cate_adminuser where code like '13__' and closeflag=1 order by ord asc"
				  set rsw1=server.CreateObject("adodb.recordset")
					rsw1.open sqlw1,conn,1,1
					if not rsw1.eof or not rsw1.bof then
					do while not rsw1.eof
						checkflag=0
						if popchat_department<>"" or not isnull(popchat_department) then
							myuseriy=split(popchat_department,",")
							for ii=0 to ubound(myuseriy)
								if cstr(trim(myuseriy(ii)))=cstr(rsw1("code")) then
									checkflag=1
								end if
							next
							if checkflag=1 then
								checkvalue="checked"
							else
								checkvalue=""
							end if
						else
							checkvalue=""
						end if
				   %>
                   <div class="plist"><input name="popchat_department" type="checkbox" id="popchat_department" value="<%=rsw1("code")%>" <%=checkvalue%>>
				  <%=rsw1("meno")%></div>
                  <%
				 rsw1.movenext
				 loop
				 end if
				 rsw1.close
				  %>
          </td>
                    </tr>
                  </table></td>
                </tr>
                -->
                <tr>
                  <td align="right" bgcolor="#FFFFFF">黄页审核</td>
                  <td colspan="3" bgcolor="#FFFFFF"><input name="huangye_check" type="checkbox" id="huangye_check" value="1" <%if huangye_check="1" then response.Write("checked")%>></td>
                  </tr>
                <tr align="center">
                  <td colspan="4" bgcolor="#FFFFFF">
                    <input type="submit" name="Submit" value="修改">                  </td>
                </tr>
              </form>
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../../conn_end.asp" -->