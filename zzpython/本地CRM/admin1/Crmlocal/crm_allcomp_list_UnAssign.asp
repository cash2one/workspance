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
sear="en="
    if isnumeric(page) then
    else
	    page=1
    end if
if page="" then 
page=1 
end if
  ''''''''''''''''''''''''''''''''''''''''''
 selectcb=request("selectcb")
 dostay=request("dostay")
 dotype=request("dotype")
 if dostay="selec1tcrm" then
 	pid=request.Form("personid")
	arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
		sqlt="select com_id from crm_continue_assign where com_id="&trim(arrcom(i))&""
		set rst=conn.execute(sqlt)
		if rst.eof and rst.bof then
			sqlp="insert into crm_continue_assign(com_id,personid) values("&trim(arrcom(i))&","&pid&")"
			conn.execute(sqlp)
		else
			sqlp="update crm_continue_assign set personid="&pid&" where com_id ="&trim(arrcom(i))
			conn.execute(sqlp)
		end if
		rst.close
		set rst=nothing
		
		sqlt="select com_id from crm_assign where com_id="&trim(arrcom(i))&""
		set rst=conn.execute(sqlt)
		if rst.eof and rst.bof then
			sqlp="insert into crm_assign(com_id,personid) values("&trim(arrcom(i))&","&pid&")"
			conn.execute(sqlp)
		else
			sqlp="update crm_assign set personid="&pid&" where com_id ="&trim(arrcom(i))
			conn.execute(sqlp)
		end if
		rst.close
		set rst=nothing
	next
	response.redirect "crm_allcomp_list_UnAssign.asp?dotype="&dotype&"&lmcode=3926"
 end if
 
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")

doaction=request("action")
docach=request("cach")
'response.Write(docach)
 sqluser="select realname,ywadminid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 rsuser.close
 set rsuser=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>crm ��˾�б�</title>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<script language=Javascript>
<!--
function showHideMenu1() {
	if (frmMenu1.style.display=="") {
		frmMenu1.style.display="none"
		//switchPoint.innerText=4
		}
	else {
		frmMenu1.style.display=""
		//switchPoint.innerText=3
		}
}


//-->
</script>
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
	background-color: #F6F6F6;
	padding: 2px;
	border: 1px solid #FF9900;
	color: #CC0000;
}
-->
</style>
</head>

<body>
<table width="100%" id=secTable border="0" cellspacing="0" cellpadding="0">
  <tr>
   
    <td height="28" class="<%if doaction="" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>'">���пͻ�</td>
	<% If dotype="myzst" Then %>
	<td align="left" class="<%if doaction="needResign" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=needResign'">
	 ����ǩ����ͨ</td>
	 <% End If %>
	<%if dotype<>"myzst" then%>
    <td class="<%if doaction="reg" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=reg'">ע��</td>
    <!--<td class="<%if doaction="vip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=vip'">VIP</td>-->
    <!--<td class="<%if doaction="sqvip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqvip'">����VIP</td>
    <td class="<%if doaction="sqzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst'">��������ͨ</td>-->
	<td class="<%if doaction="exhibit" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=exhibit'">��չ�ͻ�</td>
	<%if dotype="allall" or dotype="today" then%>
	<td class="<%if doaction="allzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=allzst'">����ͨ</td>
	<%end if%>
	<%end if%>
	<%if session("userid")="10" or session("Partadmin")<>"0" then%>
	<td align="left" class="<%if doaction="gonghainocontact" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=all&action=gonghainocontact'">��������δ��ϵ</td>
	<%end if%>
    <td align="left" class="unselect" onClick="window.open('crm_comp_add.asp?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst','_blank','width=700,height=500')">
	 �ֶ�¼��</td>
	 <td align="left" class="unselect" onClick="window.open('pass/search.asp','_blank','width=600,height=400')">
	 <font color="#FF0000">�ͻ������ѯ</font></td>
     <%if session("userid")="13" or session("userid")="10" then%>
     <td align="left" class="unselect" onClick="window.open('pass/searchlist.asp','_blank','width=600,height=500')">
	 �����ѯ���</td>
     <%end if%>
     <%if dotype="bmzd" then%>
     <td align="left" class="<%if request("action")="zhongdian" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=zhongdian&doperson=<%=request("doperson")%>'">
	 δ����ص�ͻ�</td>
     <%end if%>
     <td align="left" class="<%if request("action")="zsh" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=zsh&doperson=<%=request("doperson")%>'">
	 ������ͻ�</td>
     <td align="left" class="<%if request("action")="25nocontact" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=25nocontact&doperson=<%=request("doperson")%>'">
	 25��δ��Ч��ϵ</td>
     <td align="left" class="unselect" onClick="window.open('pipei/p_add.asp','_blank','width=600,height=500')">
	 �����ͻ��ύ</td>
    <td width="100%" align="left" class="unselect">&nbsp;</td>
  </tr>
</table>
<%
searchflag=1
if session("userid")<>"10" or zguserqx<>"1" then
	if request("searchflag")="1" then
		searchflag=0
	else
		searchflag=1
	end if
end if
if searchflag=1 then
%>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <form name="form2" method="get" action="">
 <!--
 <tr>
      <td height="30" align="center" bgcolor="#f2f2f2">
	    <p>
  <input class="<%if request("zhuce")="zhudong" then response.Write("pl") else response.Write("button") end if%>" type="button" name="Submit" value="��Ծ�ͻ�" onClick="window.location='?dotype=<%response.Write(dotype)%>&action=<%response.Write(doaction)%>&zhuce=zhudong&doperson=<%=request("doperson")%>'">
  &nbsp;&nbsp;
  <input type="button" class="<%if request("zhuce")="shouji" then response.Write("pl") else response.Write("button") end if%>" name="Submit" value="�ǻ�Ծ�ͻ�" onClick="window.location='?dotype=<%response.Write(dotype)%>&action=<%response.Write(doaction)%>&zhuce=shouji&doperson=<%=request("doperson")%>'">
  &nbsp;&nbsp;
  <input type="button" class="<%if request("zhuce")="jihuo" then response.Write("pl") else response.Write("button") end if%>" name="Submit4" value="�������Ŀͻ�" onClick="window.location='?dotype=<%response.Write(dotype)%>&action=<%response.Write(doaction)%>&zhuce=jihuo&doperson=<%=request("doperson")%>'">
	    </p>
      </td>
  </tr>
 -->
    <tr>
      <td height="30" align="center" bgcolor="#FFFFFF">
	    <table width="100%" border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td nowrap>��˾��:</td>
          <td><input name="com_name" type="text" class=text id="com_name" style="background-color:#fff;" size="15"><input name="action" type="hidden" id="action" value="<%=doaction%>">
        <input name="dotype" type="hidden" id="dotype" value="<%=dotype%>">
        <input name="cach" type="hidden" id="cach" value="<%response.Write(docach)%>">
        <input name="zhuce" type="hidden" id="zhuce" value=<%response.write(request("zhuce"))%>>
        <input name="expired" type="hidden" id="expired" value="<%=request("expired")%>"></td>
          <td nowrap>��ϵ��:</td>
          <td><input name="com_contactperson" type="text" class=text id="com_contactperson" style="background-color:#fff;" size="15" value="<%=request("com_contactperson")%>"></td>
          <td nowrap>�����ϵʱ��: </td>
          <td nowrap>
		  <%
	if request("fromdate")="" then
	fromdate=""
	else
	fromdate=request("fromdate")
	end if
	if request("todate")="" then
	todate=""
	else
	todate=request("todate")
	end if
	
	%>
		  <script language=javascript>createDatePicker("fromdate",true,"<%=fromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("todate",true,"<%=todate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td>��&nbsp; ��: </td>
          <td><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15"></td>
          <td>��&nbsp; ��:</td>
          <td><input name="com_mobile" type="text" class=text id="com_mobile" style="background-color:#fff;" size="15"></td>
          <td>�´���ϵʱ��: </td>
          <td><script language=javascript>createDatePicker("nfromdate",true,"<%=nfromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("ntodate",true,"<%=ntodate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td>��&nbsp; ַ:</td>
          <td><input name="com_add" type="text" class=text id="com_add" style="background-color:#fff;" size="15"></td>
          <td>��&nbsp; ҵ:</td>
          <td>
		  <select name="clscb" id="clscb" style="width:130px">
		  <option value="">--��ѡ��--</option>
		  <%sqls="select cb_chn_name,cb_id from cls_b where delflag=0"
		  set rss=server.CreateObject("adodb.recordset")
		  rss.open sqls,conn,1,1
		  if not rss.eof then
		  do while not rss.eof 
		  %>
            <option value="<%response.Write(rss(1))%>"><%response.Write(rss(0))%></option>
		  <%
		  rss.movenext
		  loop
		  end if
		  rss.close
		  set rss=nothing
		  %>
          </select>          </td>
          <td>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��:</td>
          <td>
<SELECT id="province" name="province"></SELECT>
<SELECT id="city" name="city"></SELECT>
<SCRIPT type=text/javascript>
	//��ĵ���
	new Dron_City("province","city",'<%=request("province")%>|<%=request("city")%>').init();
</SCRIPT></td>
          </tr>
        <tr>
          <td>��&nbsp; ��:</td>
          <td nowrap><%call cate_cn("cate_kh_csd","com_rank",request("com_rank"))%>
            ��</td>
          <td>��&nbsp; ��:</td>
          <td nowrap> <%
		  select case request("comporder")
		  case "1"
		  sl1="selected"
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "2"
		  sl1=""
		  sl2="selected"
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "3"
		  sl1=""
		  sl2=""
		  sl3="selected"
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "4"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4="selected"
		  sl5=""
		  sl6=""
		  sl7=""
		  case "5"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5="selected"
		  sl6=""
		  sl7=""
		  case "6"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6="selected"
		  sl7=""
		  case "7"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7="selected"
		  case "8"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  sl8="selected"
		  end select
		  %>
            <select name="comporder" id="comporder">
              <option>Ĭ��</option>
              <option value="1" <%response.Write(sl1)%>>�ͻ��ȼ�</option>
              <option value="2" <%response.Write(sl2)%>>��¼����</option>
              <option value="3" <%response.Write(sl3)%>>�´���ϵʱ��</option>
              <option value="4" <%response.Write(sl4)%>>����ʱ��</option>
			  <option value="5" <%response.Write(sl5)%>>�����ϵʱ��</option>
			  <option value="6" <%response.Write(sl6)%>>ע��ʱ��</option>
			  <option value="7" <%response.Write(sl7)%>>�����½ʱ��</option>
			   <option value="8" <%response.Write(sl8)%>>����ʱ��</option>
            </select>
			<select name="ascdesc" id="ascdesc">
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>����</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>����</option>
	        </select>
            </td>
          <td>�����¼ʱ��:</td>
          <td><script language=javascript>createDatePicker("Lfromdate",true,"<%=Lfromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("Ltodate",true,"<%=Ltodate%>",false,true,true,true)</script></td>
          </tr>
      </table></td>
    </tr>
    
    
    <tr>
      <td height="30" align="center" bgcolor="#f2f2f2">
	    ��Ӫҵ��:
	  	<input type="text" name="zyyw" class="text" id="zyyw">
	  	<select name="selectgg" id="selectgg" onChange="document.getElementById('zyyw').value=this.value">
          <option value="">���ɿ���</option>
	  	  <option value="����">����</option>
          <option value="���">���</option>
          <option value="�����">�����</option>
          <option value="��е">��е</option>
  	    </select>
	  	Email:
	    <input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15">
	    <%'response.Write(session("Partadmin"))%>
	  <input name="ss" type="hidden" id="ss" value="1">
	  <% =session("Partadmin") %>
      <%if session("Partadmin")<>"0" and (dotype="allall" or dotype="allbm" or dotype="bmzd" or dotype="all" or dotype="bmzd" or dotype="3daynodo" or (dotype="my" and session("userid")="10") or (dotype="today" and session("userid")="10") or (dotype="contact" and session("userid")="10") or (dotype="nocontact" and session("userid")="allPay") or (dotype="unAssgin" and session("userid")="10") or (dotype="myzst" and session("userid")="10") or (dotype="xm") or session("personid")="93" or session("personid")="227") then%>
            <select name="doperson" class="button" id="doperson" >
              <option value="" >��ѡ��--</option>
			  <%
			  'if ywadminid<>"" and not isnull(ywadminid) and session("personid")<>"93" and session("personid")<>"227" then
			      'sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  'else
				  if session("userid")="10" or session("personid")="93" or session("personid")="227" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  'end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
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
            <%end if%>
			<%if dotype="sbcomp" then%>
		    <input name="linfen" type="checkbox" id="linfen" value="1">
	    �ַ��ռ�
		<%end if%>
      <input type="submit" name="Submit3" value="  �� ��  " class=button>	  </td>
    </tr>
  </form>
</table>
<% If dotype="MyContinue" Then 
	subType=request("subType")%>
	<table width="90%" align="center" cellpadding="3" cellspacing="1" bgcolor="#ff6600" style="margin:2px 0;">
		<tr>
			<td align="center" bgcolor="<% If subType="noExpired" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=MyContinue&subType=noExpired">δ��������ͨ</a></td>
			<td align="center" bgcolor="<% If subType="ExpiredOneMonth" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=MyContinue&subType=ExpiredOneMonth">����1����������ͨ</a></td>
			<td align="center" bgcolor="<% If subType="ExpiredExceedOneMonth" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=MyContinue&subType=ExpiredExceedOneMonth">����1����������ͨ</a></td>
		</tr>
	</table>
<% End If %>

<% If dotype="allzstGh" Then 
	subType=request("subType")%>
	<table width="90%" align="center" cellpadding="3" cellspacing="1" bgcolor="#ff6600" style="margin:2px 0;">
		<tr>
			<td align="center" bgcolor="<% If subType="allCurrentZstGh" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=allzstGh&subType=allCurrentZstGh">δ��������ͨ����</a></td>
			<td align="center" bgcolor="<% If subType="allExpireZstGh" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=allzstGh&subType=allExpireZstGh">�ѹ�������ͨ����</a></td>
			
		</tr>
	</table>
<% End If %>
<%
end if
sear=sear&"&searchflag="&request("searchflag")
'***********����/start
					sql=""
					if dotype<>"allall" and dotype<>"allcontinue" then
					sql=sql&" and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))"
					end if
					if dotype="xjj" then
					sql=sql&" and com_id in (select com_id from v_groupcomid where personid=128) and com_id not in (select com_id from crm_assign where personid=128)"
					sear=sear&"&dotype="&request("dotype")
					end if
					if trim(request("com_email"))<>"" then
					sql=sql&" and com_email like '%"&trim(request("com_email"))&"%'"
					sear=sear&"&com_email="&request("com_email")
					end if
					if trim(request("zyyw1"))<>"" then
						sql=sql&" and exists(select null from Crm_CompOtherInfo where com_id=v_salescomp.com_id and com_zyyw like '%"&trim(request("zyyw"))&"%')"
						sear=sear&"&zyyw="&request("zyyw")
					end if
					if trim(request("zyyw"))<>"" then
						sql=sql&" and com_productslist_en like '%"&trim(request("zyyw"))&"%'"
						sear=sear&"&zyyw="&request("zyyw")
					end if
					if trim(request("nfromdate"))<>"" then
					sql=sql&" and com_id in (select com_id from comp_tel where contactnext_time>'"&cdate(request("nfromdate"))&"'"
					sear=sear&"&nfromdate="&request("nfromdate")
					end if
					if trim(request("ntodate"))<>"" then
					sql=sql&" and contactnext_time<'"&cdate(request("ntodate"))+1&"')"
					sear=sear&"&ntodate="&request("ntodate")
					elseif trim(request("nfromdate"))<>"" then
					sql=sql&")"
					end if
					
					'---------------begin
					'�����¼ʱ��
					if trim(request("Lfromdate"))<>"" then
						sql=sql&" and lastlogintime>'"&cdate(request("Lfromdate"))&"'"
						sear=sear&"&Lfromdate="&request("Lfromdate")
					end if
					if trim(request("Ltodate"))<>"" then
						sql=sql&" and lastlogintime<'"&cdate(request("Ltodate"))+1&"'"
						sear=sear&"&Ltodate="&request("Ltodate")
					end if
					'---------------end 
					if trim(request("fromdate"))<>"" then
					sql=sql&" and teldate>'"&cdate(request("fromdate"))&"'"
					sear=sear&"&fromdate="&request("fromdate")
					end if
					if trim(request("todate"))<>"" then
					sql=sql&" and teldate<'"&cdate(request("todate"))+1&"'"
					sear=sear&"&todate="&request("todate")
					end if
					if request("regtimefrom")<>"" then
					sql=sql&" and com_regtime>'"&cdate(request("regtimefrom"))&"' and com_regtime<'"&cdate(request("regtimefrom"))+1&"'"
					sear=sear&"&regtimefrom="&request("regtimefrom")
					end if
					if trim(request("com_name"))<>"" then
					sql=sql&" and com_name like '%"&trim(request("com_name"))&"%'"
					sear=sear&"&com_name="&request("com_name")
					end if
					if trim(request("com_add"))<>"" then
					sql=sql&" and com_add like '%"&trim(request("com_add"))&"%'"
					sear=sear&"&com_add="&request("com_add")
					end if
					if trim(request("com_subname"))<>"" then
					sql=sql&" and com_subname like '%"&trim(request("com_subname"))&"%'"
					sear=sear&"&com_subname="&request("com_subname")
					end if
					if trim(request("com_tel"))<>"" then
					sql=sql&" and (com_tel like '%"&trim(request("com_tel"))&"%' or (com_id in (select com_id from crm_personinfo where PersonTel like '%"&trim(request("com_tel"))&"%')))"
					sear=sear&"&com_tel="&request("com_tel")
					end if
					if trim(request("com_mobile"))<>"" then
					sql=sql&" and (com_mobile like '%"&trim(request("com_mobile"))&"%' or (com_id in (select com_id from crm_personinfo where PersonMoblie like '%"&trim(request("com_mobile"))&"%')))"
					sear=sear&"&com_mobile="&request("com_mobile")
					end if
					if trim(request("com_rank"))<>"" then
					sql=sql&" and com_rank='"&trim(request("com_rank"))&"'"
					sear=sear&"&com_rank="&request("com_rank")
					end if
					if request("province")<>"" and request("city")="����" and request("province")<>"ʡ��" then
					sql=sql&" and com_province like '%"&request("province")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("city")
					elseif request("province")<>"" and request("city")<>"" and request("province")<>"ʡ��" and request("city")<>"����" then
					sql=sql&" and com_province like '%"&request("city")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("city")
					end if
					if request("clscb")<>"" then
					sql=sql&" and EXISTS (select com_id from Comp_ComTrade where com_id=v_salescomp.com_id and TradeID="&request("clscb")&")"
					sear=sear&"&clscb="&request("clscb")
					end if
					if trim(request("com_contactperson"))<>"" then
					sql=sql&" and (com_contactperson like '%"&trim(request("com_contactperson"))&"%' or (com_id in (select com_id from crm_personinfo where personname like '%"&trim(request("com_contactperson"))&"%')))"
					sear=sear&"&com_contactperson="&request("com_contactperson")
					end if
					if trim(request("linfen"))<>"" then
					sql=sql&" and adminuser=59"
					sear=sear&"&linfen="&request("linfen")
					end if
				'***********����/end
errtext=""				
if dotype<>"allall" then
	searchflag=1
else
	if request("ss")<>"" and sql<>"" then
	    '-----------------------begin
		'ʱ������
		getcompany=0
		if formatdatetime(now,4)>"5:00" and formatdatetime(now,4)<"9:00" then
			getcompany=1
		end if
		if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:30" then
			getcompany=1
		end if
		if formatdatetime(now,4)>"18:00" and formatdatetime(now,4)<"24:00" then
			getcompany=1
		end if
		'----------------------end
		searchflag=1
		sear=sear&"&ss=1"
	else
		searchflag=0
		errtext="<br><font color=#ff0000>�ͻ��б���ͨ������������ʾ��</font>"
	end if
end if
week=weekday(now)
if dotype="all" then
    nhour=Hour(now())
	nMinute=Minute(now())
	searchflag=0

		if formatdatetime(now,4)>"5:00" and formatdatetime(now,4)<"9:00" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:00" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"17:30" and formatdatetime(now,4)<"24:00" then
			searchflag=1
		end if

	if searchflag=0 then
	errtext="<br><font color=#ff0000>�������ͻ����ܿ���ʱ�䣺����  12:00-13:00     17:30-21:00</font>"
	end if
	'searchflag=1
end if

if session("userClass")="1" or session("userID")="10" or session("Partadmin")<>"0"  then	'42ΪС��
	searchflag=1
end if
if searchflag=1 then
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	                <%
				
					'**************�ҵĿͻ�/start
					'sql=sql&" and not EXISTS(select com_id from crm_assign where com_id=v_salescomp.com_id and personid=14)"

					'******************���찲����ϵ�Ŀͻ�/start
					if dotype="unAssign" then
						'if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
							
						
						'end if
						sql=sql&" and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and vip_datefrom>'2008-3-19'   and not exists(select null from crm_Continue_Assign where com_id=v_salescomp.com_id) and not exists(select null from crm_continue_Nodo where com_id=v_salescomp.com_id) "
						'and not exists(select null from users,crm_assign where crm_assign.com_id=v_salescomp.com_id and crm_assign.personid=users.id and users.id in (47,225,30,185,247))
					    sear=sear&"&dotype="&dotype
					end if
					
					if dotype="allPay" then
						'if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
							
						
						'end if
						sql=sql&" and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and not exists(select null from crm_continue_Nodo where com_id=v_salescomp.com_id) "
						
					    sear=sear&"&dotype="&dotype
					end if
					'******************���찲����ϵ�Ŀͻ�/end
					

					if session("userid")="10" then
						response.write(sql)
					end if
					sqlorder="order by "
				    ascdesc=request("ascdesc")
					select case request("comporder")
						case "1"
						sqlorder=sqlorder&"com_rank "&ascdesc&""
						case "2"
						sqlorder=sqlorder&" logincount "&ascdesc&""
						case "3"
						sqlorder=sqlorder&"contactnext_time "&ascdesc&""
						case "4"
						sqlorder=sqlorder&"vip_date "&ascdesc&""
						case "5"
						sqlorder=sqlorder&"teldate "&ascdesc&""
						case "6"
						sqlorder=sqlorder&"com_regtime "&ascdesc&""
						case "7"
						sqlorder=sqlorder&"lastlogintime "&ascdesc&""
						case "8"
						sqlorder=sqlorder&"vip_dateto "&ascdesc&""
						
						case else
						sqlorder=sqlorder&"teldate asc"
					end select
				    sqlorder=sqlorder&",com_id desc"
					
                    sear=sear&"&ascdesc="&request("ascdesc")
					sear=sear&"&comporder="&request("comporder")
				   startime=timer()
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "com_tel,com_mobile,com_email,com_province,com_id,com_name,com_pass,vip_date,vip_datefrom,vipflag,viptype,vip_check,contactnext_time,com_rank,com_regtime,viewcount,logincount,lastlogintime,teldate,vip_dateto,adminuser,com_productslist_en,com_contactperson,com_desi"
					 .FROMTbl = "v_salescomp"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql
				     .keyFld  = "com_id"    '����ȱ��
				     .pageSize= 10
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/10)
				   if total/10 > totalpg then
				   	  totalpg=totalpg+1
				   end if
				   %>
	</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" class=ee id=ListTable border="0" cellpadding="2" cellspacing="0">
              <form action="crm_allcomp_list_UnAssign.asp" method="post" name=form1>
                <tr class="topline"> 
                  <td width="4" bgcolor="#DFEBFF"><span class="STYLE2"></span></td>
                  <td nowrap bgcolor="#DFEBFF">�ͻ�<br>
                  ����</td>
                 

                  <td bgcolor="#DFEBFF"><span class="STYLE2">������</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">�ͻ�<br>
�ȼ�</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">Email</span></td>

                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">�����¼</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">��¼<br>
����</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">ע��ʱ��</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">��ϵ��</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">��˾�绰</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">�ֻ�</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>ʡ��</td>
                 
                  
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>�����ϵʱ��</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>�´���ϵʱ��</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>ȷ�ϵ�</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">�鿴<br>
                  ��Ϣ</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">����ʱ��</td>
                 
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>����ʱ��</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">����ͨ</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">����ͨӵ����</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">��ǩӵ����</td>
                </tr>
                <%
				 if not rs.eof or not rs.bof then 
				 i=0                                                                      
				Do While Not rs.EOF 
				'if dotype="allExpired" then
'					sqlu="select * from crm_continue_assign where com_id="&rs("com_id")
'					set rsu=conn.execute(sqlu)
'					if rsu.eof or rsu.bof then
'						sqln="insert into crm_continue_assign(com_id,personid) values("&rs("com_id")&",30)"
'						conn.execute(sqln)
'					end if
'					rsu.close
'					set rsu=nothing
'				end if
				if cdate(rs("com_regtime"))>date()-1 and cdate(rs("com_regtime"))<date() then
					cbg="#CCFFCC"
				else
					cbg="#ffffff"
				end if
				sqlwe="select fdate,personid from crm_AssignWeb where com_id="&rs("com_id")&""
				set rswe=conn.execute(sqlwe)
				if not rswe.eof then
					sqlsales="select lastteldate,TozstContact from comp_websales where com_id="&rs("com_id")&""
					set rswebsales=conn.execute(sqlsales)
					if not rswebsales.eof then
						lastcotdate=rswebsales(0)
						TozstContact=rswebsales(1)
					else
						lastcotdate=rswe(0)
						TozstContact=0
					end if
					rswebsales.close
					set rswebsales=nothing
					
					webl=datediff("d",cdate(lastcotdate),now)
					if TozstContact=0 then
						if webl<60 and webl>=0 then
							webexiats=1
						else
							webexiats=0
						end if
					else
					webexiats=0
					end if
				else
					webexiats=0
				end if
				rswe.close
				set rswe=nothing
				%>
                <tr style="cursor:hand" bgcolor="<%=cbg%>"> 
                  <td height="24">
				  <input name="cbb<%=i%>" type="checkbox" title="<%response.Write(rs("com_email"))%>" value="<%response.Write(rs("com_id"))%>"></td>
                  <%
				  sqls="select top 1 teldate from comp_tel where telflag=1 and com_id="&rs("com_id")&" and ContactType=13 order by id desc"
				  set rss=conn.execute(sqls)
				  if not rss.eof then
				  lteldate=rss(0)
				  else
				  lteldate=""
				  end if
				  rss.close
				  set rss=nothing
				  'response.Write(lteldate)
				  if lteldate="" or isnull(lteldate) then
				  	l=0
					nl=0
				  else
				  	l=datediff("d",cdate(lteldate),now)
					nl=1
				  end if
				   '-----------------------
				  '*����ͻ� /start
				  '---------------------------
				  
				  sqlzs="select com_id from Crm_Comp_Share where com_id="&rs("com_id") &" and salesType=2"
				  set rszs=conn.execute(sqlzs)
				  if not rszs.eof then
					  webexiats=0
				  end if
				  rszs.close
				  set rszs=nothing
				   '-----------------------
				  '*����ͻ� /end 
				  '---------------------------
				  if dotype="allcontinue" or dotype="allExpired" then
				  	webexiats=0
				end if
				  %>
				  <%
				 if session("userid")<>"10" then
				  if webexiats=1 then
				  	op=1
				  else
                    op=1
				  end if
				 else 
					op=1
				 end if%>
				 <%if op=1 then%>
				  <SPAN onmouseover=DoHL();  onDblClick="javascript:DoSL();javascript:oow('../crmlocal/crm_cominfoedit.asp?idprod=<%response.Write(rs("com_id"))%>&cemail=<%response.Write(rs("com_email"))%>&dotype=<%response.Write(dotype)%>');" onmouseout=DoLL();>
				 <%end if%>
				  <td nowrap>
				  
                  <%
				  comLx=""
				  comLxzd=""
				  sqlzd="select com_id,Emphases_check from Crm_Assign where Emphases=1 and com_id="&rs("com_id")
				  set rszd=conn.execute(sqlzd)
				  if not rszd.eof or not rszd.bof then
				  	comLxzd="<font color=#ff0000>�ص�"
					if rszd("Emphases_check")="0" then
					comLxzd=comLxzd&"(δ��)"
					end if
					comLxzd=comLxzd&"</font>"
				  end if
				  rszd.close
				  set rszd=nothing
				  if rs("vipflag")=0 and rs("viptype")=0 then
				  	comLx="ע��"
				  end if
				  if (rs("viptype")<>0 and rs("vipflag")<>2) or (rs("viptype")<>0 and rs("vip_check")=0) then
				  	comLx="ע��"
				  end if
				  if rs("vipflag")=2 and rs("vip_check")=1 then
				  	comLx="<font color=#009900>����ͨ</font>"
				  end if
				  '---------------�ж�ע����Դ
				  sqlL="select id from comp_regFrom where com_id="&rs("com_id")&" and RegFrom=20"
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	comLx="<font color=#CC9900>����ע��</font>"
				  end if
				  rsL.close
				  set rsL=nothing
				  '-------------------------�ж�¼��ͻ�
				  sqlP="select com_id from crm_InsertCompWeb where com_id="&rs("com_id")&" and saletype=1"
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then
				  	comLx="<font color=#003399>¼��ͻ�</font>"
				  end if
				  rsp.close
				  set rsp=nothing
				  '------------------------------------
				  if comlxzd<>"" then
				  response.Write(comLxzd&"<br>")
				  end if
				  response.Write(comLx)
				  '----------------------
				  '---------------------���ɿ����ͻ�
				  ggstr="��е,�����,���,����"
				  arrggstr=split(ggstr,",")
				  productslist=rs("com_productslist_en")
				  ggg=0
				  for i=0 to ubound(arrggstr)
				  	if instr(productslist,arrggstr(i))>0 then
						ggg=1
					end if
				  next
				  if ggg=1 then
				  response.Write("<br><font color=#ff34de>���ɿ���</font>")
				  end if
				  
				  '--------------------------------------
				'�Ƿ�Ϊ��չ�ͻ�
				sql0="select * from comp_exhibit where com_id="&rs("com_id")
				set rs0=conn.execute(sql0)
				if not (rs0.eof and rs0.bof ) then
					select case rs0("exhibit")
						case 1:ex="�Ϻ�����չ"
						case 2:ex="���ݽ���չ"
						case 3:ex="��������չ"
					end select
					select case rs0("customerType")
						case 1:cu="չλչ��"
						case 2:cu="�ι�չ��"
						case 3:cu="�ص�չ��"
					end select	
					response.write("<br><a href='#' title='��չ���ƣ�"&ex&vbcrlf&"չ����Դ��"&cu&"'><font color='red'>(��չ�ͻ�)</font></a>")
				end if
				rs0.close
				set rs0=nothing
				  %>                  </td>
                  
                  
                   
                  <td nowrap >
				  
				  <%response.Write(rs("com_name"))%>
				  <%
				  'response.Write(l)
				  if l<30 and l>=0 and nl=1 then
				  %>
				  <div class="crm_tishi">
				  <%=formatdatetime(lteldate,2)%>��վ���۲���ϵ��				  </div>
				  <%
				  end if
				  if webexiats=1 then
				   		response.Write("<div class='crm_tishi'>")
					  	response.Write("<font color=#000000>�˿ͻ�����Ʒ��ͨ����</font>")
						response.Write("</div>")
				  end if
				  if rs("vipflag")=2 and rs("vip_check")=1 then
					  if cdate(rs("vip_datefrom"))<date()-30 and cdate(rs("vip_dateto"))>date()+90 then
					    response.Write("<div class='crm_tishi'>")
					  	response.Write("�˿ͻ�����Ʒ��ͨ���۷�Χ����ҽ�����ǩ����")
						response.Write("</div>")
					  end if
					  if cdate(rs("vip_dateto"))<date()+30 and cdate(rs("vip_dateto"))>date()-30 then
					    response.Write("<div class='crm_tishi'>")
					  	response.Write("<font color=#9966ff>�˿ͻ�������ǩ���۷�Χ���ɽ�����ǩ����</font>")
						response.Write("</div>")
					  end if
				  end if
				  
				  %>				  </td>
                  <td>
                    <%
				vcount=rs("viewcount")
				logincount=rs("logincount")
				lastlogintime=rs("lastlogintime")
				if rs("contactnext_time")<>"1900-1-1" then
				contactnext_time=rs("contactnext_time")
				else
				contactnext_time=""
				end if
				  if rs("com_rank")<>"-1" then
				  response.Write(rs("com_rank")&"��")
				  end if
				  %>                 </td>
                  <td><%response.Write(rs("com_email"))%></td>
                  <td nowrap>
				  <%
				  if lastlogintime<>"" or not isnull(lastlogintime) then
				  response.Write(lastlogintime)
				  end if
				  %>				  </td>
                 
                  <td nowrap>
				<%  
				
				response.Write(logincount)
				%>
				  
				  </td>
                  <td nowrap><%response.Write(rs("com_regtime"))%></td>
                  <td nowrap><%=rs("com_contactperson")%><%=rs("com_desi")%></td>
                  <td nowrap>
				  <%
				  if dotype="allall" then 
				    if session("userid")="10" or session("userClass")="1" then
						response.Write(rs("com_tel"))
					else
				    	response.Write("******")
					end if
				  else
				   response.Write(rs("com_tel"))
				  end if
				  %>				  </td>
                  <td nowrap>
				  <%
				  if dotype="allall" then 
				    if session("userid")="10" or session("userClass")="1" then
						response.Write(rs("com_mobile"))
					else
				    	response.Write("******")
					end if
				  else
				   response.Write(rs("com_mobile"))
				  end if
				  %>				  </td>
                  <td nowrap><%response.Write(rs("com_province"))%>				  				  </td>
                  
                  
                  <td nowrap>
                    
                    <%
					if rs("teldate")="1900-1-1" then
					response.write("δ��ϵ")
					else
					response.Write(rs("teldate"))
					end if
					%>                  </td>
                  <td nowrap>
                    <%response.Write(contactnext_time)%>                  </td>
                  <td nowrap><!--<iframe src="http://admin.zz91.com/admin1/compinfo/hasConfirm.asp?com_id=<%= rs("com_id") %>" frameborder='0' width="100%" height="100%" scrolling="no" allowtransparency="true" ></iframe>-->                                      </td>
                  
                  <td align="center" nowrap> <a href="#kang" target="topt" onClick="window.open('../admin_user_viewlist.asp?com_email=<%response.Write(rs("com_email"))%>','_blank','width=500,height=500')"><%response.Write(vcount)%></A></td>
                  
                  <td nowrap>
				  <%
				  if rs("vip_date")<>"1905-3-14" then
				  response.Write(rs("vip_date"))
				  end if
				  %></td>
                 
				  <td nowrap><%= rs("vip_dateto") %></td>
				  <%
				  sqluser="select a.personid,b.realname,a.Waiter_Open from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  Waiter_Open=rsuser("Waiter_Open")
				  else
				  realname=""
				  Waiter_Open=""
				  end if
				  if Waiter_Open="1" then
				  Waiter_Open="<font color=#ff0000>����ͨ</font>"
				  end if
				  %>
                  <td align="center" nowrap><%response.write(Waiter_Open)%></td>
                  </SPAN> 
                  <td align="center" nowrap>
				 <%
				 
				 response.write(realname)%>				 </td>
                  <td align="center" nowrap>
				  <% 
				 	 sqluser2="select a.personid,b.realname from crm_continue_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
					  set rsuser2=conn.execute(sqluser2)
					 if not rsuser2.eof then
				 		 realname=rsuser2("realname")
					 else
					 	realname=""
				    end if
					rsuser2.close
					set rsuser2=nothing
				
				 response.write(realname) %>
				  </td>
                </tr>
                <%
			                                   
     		RowCount = RowCount - 1
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="30">��ʱ����Ϣ��</td>
                </tr>
                <%end if
			  rs.close
			  set rs=nothing
			  %>
                <script language="JavaScript">
<!--

function CheckAll(form)
  {
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       e.checked = form.cball.checked;
    }
  }
  function selectopenzst(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ��ͨ�Ĺ�˾��")
	return false
	}
	else
	{
	  if (confirm("��ͨ��Щ��˾��?"))
	  {
	  form.dostay.value="waitOpenZST";
	  form.target="_blank";
	  form.userName.value="<%= userName %>"
	  form.submit()
	  //window.open ("http://www.zz91.com/admin1/compinfo/cominfo_openConfirm.asp?selectcb="+selectcb.substr(2)+"&userName=")
	  //form.dostay.value="openzst"
	  //form.submit()
	  }
	}
  }
  function selectclosevip(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ�رյ�VIP�ͻ���")
	return false
	}
	else
	{
	  if (confirm("�رյ���ЩVIP���ÿͻ���?"))
	  {
	  window.open ("http://www.zz91.com/admin1/compinfo/cominfo_closevip.asp?selectcb="+selectcb.substr(2),"_a","width=500,height=500")
	  //form.dostay.value="openzst"
	  //form.submit()
	  }
	}
  }
  function selectcrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ�������Ϣ��")
	return false
	}
	else
	{
	  if (confirm("������Щ��Ϣ��?"))
	  {
	  form.dostay.value="selec1tcrm"
	  form.submit()
	  }
	}
  }
  function delselectcrm (form)
  {
   selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ�������Ϣ��")
	return false
	}
	else
	{
	  if (confirm("�ò����������Ŀͻ��ŵ���δ����Ŀͻ����ȷ��Ҫȡ��������Щ�ͻ���?"))
	  {
	  form.dostay.value="delselec1tcrm"
	  form.submit()
	  }
	}
  }
function outselectcrm (form)
  {
   selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ�������Ϣ��")
	return false
	}
	else
	{
	  if (confirm("�ò����������Ŀͻ��ŵ���δ����Ŀͻ����ȷ��Ҫȡ��������Щ�ͻ���?"))
	  {
	  form.dostay.value="outselec1tcrm"
	  form.submit()
	  }
	}
  }
function outselectcrmtt (form)
  {
   selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ�������Ϣ��")
	return false
	}
	else
	{
	  if (confirm("�ò����������Ŀͻ��ŵ���δ����Ŀͻ����ȷ��Ҫȡ��������Щ�ͻ���?"))
	  {
	  form.dostay.value="outselec1tcrmtt"
	  form.submit()
	  }
	}
  }
 function selectmycrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ����ҵĿͻ����?"))
	  {
	  form.dostay.value="selec1tmycrm"
	  form.submit()
	  }
	}
  }
  function zhongdian(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ���õĿͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ����ҵ��ص�ͻ����?"))
	  {
	  form.dostay.value="zhongdian"
	  form.submit()
	  }
	}
  }
  function zhongdianout(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ���õĿͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ�ȡ�����ص�ͻ�����?"))
	  {
	  form.dostay.value="zhongdianout"
	  form.submit()
	  }
	}
  }
  function zhongdiansh(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ���õĿͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ����˸á��ص�ͻ�����?"))
	  {
	  form.dostay.value="zhongdiansh"
	  form.submit()
	  }
	}
  }
  function zhongdianshno(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ���õĿͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ��ȡ����˸á��ص�ͻ�����?"))
	  {
	  form.dostay.value="zhongdianshno"
	  form.submit()
	  }
	}
  }
  function selectoutmycrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ����ҵĿͻ�������?"))
	  {
	  form.dostay.value="selec1toutmycrm"
	  form.submit()
	  }
	}
  }
    function putInAd(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ����ҵĹ��ͻ��⡱����?"))
	  {
	  form.dostay.value="putInAd"
	  form.submit()
	  }
	}
  }
  function selectdelcom(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ�����ɾ����������?"))
	  {
	  form.dostay.value="selec1tdelcom"
	  form.submit()
	  }
	}
  }
   function selectwastecom(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��ŵ�����Ʒ�ء�����?"))
	  {
	  form.dostay.value="selec1twastecom"
	  form.submit()
	  }
	}
  }
  function outselectcrmTozst(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ���ŵĿͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��š�Ʒ��ͨ������ϵ��?"))
	  {
	  form.dostay.value="selec1tToweb"
	  form.submit()
	  }
	}
  }
   function inselectcrmToshare(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪ���ÿͻ��š�����ͻ�����?"))
	  {
	  form.dostay.value="selec1tToshare"
	  form.submit()
	  }
	}
  }
   function outselectcrmToshare(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪȡ���ÿͻ�Ϊ������ͻ�����?"))
	  {
	  form.dostay.value="outselec1tToshare"
	  form.submit()
	  }
	}
  }
  function outselectcrmPin(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("ѡ����Ҫ����Ŀͻ���")
	return false
	}
	else
	{
	  if (confirm("ȷʵҪȡ���ÿͻ�Ϊ������?"))
	  {
	  form.dostay.value="outselectcrmPin"
	  form.submit()
	  }
	}
  }
  function IntoContinueCrm(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true){
	   	var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0"){
		alert ("ѡ����Ҫ����Ŀͻ���")
		return false
	}else{
	  if (confirm("ȷʵ�ŵ��ҵ���ǩ�ͻ�����?"))
	  {
		  form.dostay.value="IntoContinueCrm"
		  form.submit()
	  }
	}
  }
  function ZNoContinueCrm(form,n)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true){
	   	var selectcb=selectcb+","+e.value
	   }
    }
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0"){
		alert ("ѡ����Ҫ����Ŀͻ���")
		return false
	}else{
	  if (confirm("ȷʵ�ŵ��ݲ���ǩ�ͻ�����?"))
	  {
		  form.dostay.value="ZNoContinueCrm"
		  form.doflag.value=n
		  //alert(form.doflag.value)
		  form.submit()
	  }
	}
  }
-->
    </script>
               
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="31"> ȫѡ 
                    <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"> 
                    &nbsp;
					<%if session("userid") ="10" or (ywadminid<>"0" and ywadminid<>"" and (not isnull(ywadminid)) and (dotype="allbm" or dotype="allPay" or dotype="unAssign" or dotype="my"))  then%>
                    <input name="Submit" type="button" class="button" value="����/���·����" onClick="selectcrm(this.form)">
                    <%response.Write(session("Partadmin"))%>
					<select name="personid" class="button" id="dopersonid" >
              <option value="" >��ѡ��--</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >��&nbsp;&nbsp;��&nbsp;&nbsp;��������</option>
			  <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >��&nbsp;&nbsp;��&nbsp;&nbsp;�����</option>
			  <% End If %>
			  <%
			 ' if session("Partadmin")="13" then
			  	 'sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  'else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			 ' end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
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
                    <%
					end if
					%>
					
                    
                    
					
					
                    <input type="hidden" name="dostay" id="dostay"> 
					<input type="hidden" name="selectcb" id="selectcb"> 
                    <input type="hidden" name="userName">
					<input type="hidden" name="flag" id="flag">
                    <input type="hidden" name="doflag" id="doflag"> 
                    <input type="hidden" name="page" value="<%=page%>">
					</td>
                </tr>
              </form>
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
<%
else
response.Write(errtext)
end if%>

</body>
</html>