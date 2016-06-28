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
 if selectcb<>"" and dostay<>"" then
    response.Redirect("ActiveCrm_assign_save.asp?selectcb="&request("selectcb")&"&dostay="&request("dostay"))&"&personid="&request("personid")
 end if
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
dotype=request("dotype")
doaction=request("action")
docach=request("cach")
'response.Write(docach)
 sqluser="select realname from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 rsuser.close
 set rsuser=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>crm 公司列表</title>
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
   
    <td height="28" class="<%if doaction="" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>'">所有客户</td>
	<%if dotype<>"myzst" then%>
    <td class="<%if doaction="reg" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=reg'">注册</td>
    <td class="<%if doaction="vip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=vip'">VIP</td>
    <td class="<%if doaction="sqvip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqvip'">申请VIP</td>
    <td class="<%if doaction="sqzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst'">申请再生通</td>
	<td class="<%if doaction="exhibit" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=exhibit'">会展客户</td>
	<%if dotype="allall" then%>
	<td class="<%if doaction="allzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=allzst'">再生通</td>
	<%end if%>
	<%end if%>
    <td align="left" class="unselect" onClick="window.open('crm_comp_add.asp?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst','_blank','width=700,height=500')">
	 手动录入</td>
	 <td align="left" class="unselect" onClick="window.location='awoke/awoke_add.asp'">
	 新增提醒</td>
    <td width="100%" align="left" class="unselect">&nbsp;</td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <form name="form2" method="get" action="">

 
    <tr>
      <td height="30" align="center" bgcolor="#FFFFFF">
	    <table width="100%" border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td nowrap>公司名:</td>
          <td><input name="com_name" type="text" class=text id="com_name" style="background-color:#fff;" size="15"><input name="action" type="hidden" id="action" value="<%=doaction%>">
        <input name="dotype" type="hidden" id="dotype" value="<%=dotype%>">
        <input name="cach" type="hidden" id="cach" value="<%response.Write(docach)%>">
        <input name="zhuce" type="hidden" id="zhuce" value=<%response.write(request("zhuce"))%>></td>
          <td nowrap>联系人:</td>
          <td><input name="com_contactperson" type="text" class=text id="com_contactperson" style="background-color:#fff;" size="15" value="<%=request("com_contactperson")%>"></td>
          <td nowrap>分配时间: </td>
          <td nowrap>
		 <%
		 fromdate=request("fromdate")
		 todate=request("todate")
		 %>
		  <script language=javascript>createDatePicker("fromdate",true,"<%=fromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("todate",true,"<%=todate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td>电&nbsp; 话: </td>
          <td><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15"></td>
          <td>手&nbsp; 机:</td>
          <td><input name="com_mobile" type="text" class=text id="com_mobile" style="background-color:#fff;" size="15"></td>
          <td>分配次数</td>
          <td>
            <select name="AssignTimes">
			<option value="">选择次数...</option>
			<%
			sqlt="select AssignTimes from crm_Active_AssignTimes where id>1"
			set rst=conn.execute(sqlt)
			while not rst.eof
			%>
              <option value="<%=rst("AssignTimes")%>" <%if cstr(request("AssignTimes"))=cstr(rst(0)) then response.Write("selected")%>><%=rst("AssignTimes")%></option>
			<%
			rst.movenext
			wend
			rst.close
			set rst=nothing
			%>
            </select>
            次</td>
        </tr>
        <tr>
          <td>地&nbsp; 址:</td>
          <td><input name="com_add" type="text" class=text id="com_add" style="background-color:#fff;" size="15"></td>
          <td>行&nbsp; 业:</td>
          <td>
		  <select name="clscb" id="clscb" style="width:130px">
		  <option value="">--请选择--</option>
		  <%sqls="select cb_chn_name,cb_id from cls_b where delflag=0"
		  set rss=server.CreateObject("adodb.recordset")
		  rss.open sqls,conn,1,2
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
          <td>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 区:</td>
          <td>
<SELECT id="province" name="province"></SELECT>
<SELECT id="city" name="city"></SELECT>
<SCRIPT type=text/javascript>
	//类的调用
	new Dron_City("province","city",'<%=request("province")%>|<%=request("city")%>').init();
</SCRIPT></td>
          </tr>
        <tr>
          <td>等&nbsp; 级:</td>
          <td nowrap><%call cate_cn("cate_kh_csd","com_rank",request("com_rank"))%>
            星</td>
          <td>排&nbsp; 序:</td>
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
		  end select
		  %>
            <select name="comporder" id="comporder">
              <option>默认</option>
              <option value="1" <%response.Write(sl1)%>>客户等级</option>
              <option value="2" <%response.Write(sl2)%>>登录次数</option>
              <option value="3" <%response.Write(sl3)%>>下次联系时间</option>
              <option value="4" <%response.Write(sl4)%>>申请时间</option>
			  <option value="5" <%response.Write(sl5)%>>最后联系时间</option>
			  <option value="6" <%response.Write(sl6)%>>注册时间</option>
			  <option value="7" <%response.Write(sl7)%>>最近登陆时间</option>
        </select>
			<select name="ascdesc" id="ascdesc">
			  
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>降序</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>升序</option>
	        </select></td>
          <td>Email:</td>
          <td><input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15"></td>
          </tr>
      </table></td>
    </tr>
    
    
    <tr>
      <td height="30" align="center" bgcolor="#f2f2f2"><input name="ss" type="hidden" id="ss" value="1">
        <select name="doperson" class="button" id="doperson" >
              <option value="" >请选择...</option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid=21"
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,2
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>><%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
					
					 %>
        </select>			
         
			
		    <input type="submit" name="Submit3" value="  搜 索  " class=button>
		    <input name="Submit4" type="button" class="button" value="打印分配的客户" onClick="window.open('ActiveCrm_comp_printmain.asp','_blank','')"></td>
    </tr>
  </form>
</table>
<%
'***********搜索/start
					sql=""
					if trim(request("com_email"))<>"" then
					sql=sql&" and com_email like '%"&trim(request("com_email"))&"%'"
					sear=sear&"&com_email="&request("com_email")
					end if
					if trim(request("nfromdate"))<>"" then
					sql=sql&" and com_id in (select com_id from Crm_Active_Tel where contactnext_time>'"&cdate(request("nfromdate"))&"'"
					sear=sear&"&nfromdate="&request("nfromdate")
					end if
					if trim(request("ntodate"))<>"" then
					sql=sql&" and contactnext_time<'"&cdate(request("ntodate"))+1&"')"
					sear=sear&"&ntodate="&request("ntodate")
					elseif trim(request("nfromdate"))<>"" then
					sql=sql&")"
					end if
					if trim(request("fromdate"))<>"" then
					sql=sql&" and AssignDate>='"&cdate(request("fromdate"))&"'"
					sear=sear&"&fromdate="&request("fromdate")
					end if
					if trim(request("todate"))<>"" then
					sql=sql&" and AssignDate<='"&cdate(request("todate"))&"'"
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
					sql=sql&" and ACcom_rank='"&trim(request("com_rank"))&"'"
					sear=sear&"&com_rank="&request("com_rank")
					end if
					if request("province")<>"" and request("city")="城市" and request("province")<>"省份" then
					sql=sql&" and com_province like '%"&request("province")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("province")
					elseif request("province")<>"" and request("city")<>"" and request("province")<>"省份" and request("city")<>"城市" then
					sql=sql&" and com_province like '%"&request("city")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("province")
					end if
					if request("clscb")<>"" then
					sql=sql&" and EXISTS (select com_id from Comp_ComTrade where com_id=v_SalesCompActive.com_id and TradeID="&request("clscb")&")"
					sear=sear&"&clscb="&request("clscb")
					end if
					if trim(request("com_contactperson"))<>"" then
					sql=sql&" and (com_contactperson like '%"&trim(request("com_contactperson"))&"%' or (com_id in (select com_id from crm_personinfo where personname like '%"&trim(request("com_contactperson"))&"%')))"
					sear=sear&"&com_contactperson="&request("com_contactperson")
					end if
					if request("AssignTimes")<>"1" and request("AssignTimes")<>"" then
					sql=sql&" and EXISTS (select null from crm_Active_Assign where com_id=v_SalesCompActive.com_id and AssignTimes="&request("AssignTimes")&")"
					sear=sear&"&AssignTimes="&request("AssignTimes")
					end if
					
				'***********搜索/end
errtext=""				
if dotype<>"allall" then
	searchflag=1
else
	if request("ss")<>"" and sql<>"" then
		searchflag=1
		sear=sear&"&ss=1"
	else
		searchflag=0
		errtext="<br><font color=#ff0000>客户列表需通过搜索才能显示！</font>"
	end if
end if
week=weekday(now)
if dotype="all" then
    nhour=Hour(now())
	nMinute=Minute(now())
	searchflag=1
	if searchflag=0 then
	errtext="<br><font color=#ff0000>公海挑客户功能开放时间：中午  12:00-13:30     18:00-21:00</font>"
	end if
end if
if session("userClass")="1" or session("userID")="10" or session("userID")="22" then
searchflag=1
end if
if searchflag=1 then%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	                <%
				
					'**************我的客户/start
					sql=sql&" and exists(select com_id from Crm_Active_CompAll where com_id=v_SalesCompActive.com_id)"
					if dotype="my" and docach<>"-1" then
					    'if session("userid")="10" then
						sql=sql&" and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and EXISTS(select com_id from crm_Active_assign where com_id=v_SalesCompActive.com_id)"
						'else
						'sql=sql&" and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and personid="&session("personid")&" "
						'end if
						sear=sear&"&dotype="&dotype
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
					end if
					'**************我的客户/end
					
					'******************所有安排联系的客户/start
					if dotype="contact" then
						if session("userid")="10" or session("personid")="32" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1' and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id)"
						else
							sql=sql&" and personid='"&session("personid")&"' and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
						end if
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
					sear=sear&"&dotype="&dotype
					end if
					'******************所有安排联系的客户/end
					'******************今天安排联系的客户/start
					if dotype="today" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and contactnext_time>='"&date()&"' and contactnext_time<='"&date()+1&"' and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						else
							sql=sql&" and personid="&session("personid")&" and contactnext_time>'"&date()&"' and contactnext_time<'"&date()+1&"' and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						end if
					sear=sear&"&dotype="&dotype
					end if
					'******************今天安排联系的客户/end
					
					'******************今天分配的客户/start
					if dotype="todayfeipei" then
						'if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and AssignDate>='"&date()&"' and AssignDate<='"&date()+1&"' and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						'else
							'sql=sql&" and personid="&session("personid")&" and AssignDate>'"&date()&"' and AssignDate<'"&date()+1&"' and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						'end if
					sear=sear&"&dotype="&dotype
					end if
					'******************今天分配的客户/end
					
					'******************所有未联系的客户/start
					if dotype="nocontact" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and not EXISTS (select com_id from v_SalesTelTime where 1=1"
							if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&" "
							end if
							sql=sql&" and teldate is not null and com_id=v_SalesCompActive.com_id) "
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) "
						else
							sql=sql&" and not EXISTS(select com_id from v_SalesTelTime where personid="&session("personid")&" and teldate is not null and com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) and personid="&session("personid")&" and Cushion<>-1 "
						end if
					sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/end
					
					'******************三天内未处理客户/start
					if dotype="3daynodo" then
					if session("userid")="10" then
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
					sql=sql&" and not EXISTS(select com_id from v_groupcomid where com_id=v_SalesCompActive.com_id) and EXISTS(select com_id from Crm_Assign where com_id=v_SalesCompActive.com_id and fdate<'"&date()-2&"')"
					else
					sql=sql&" and personid="&session("personid")&" and not EXISTS(select com_id from v_groupcomid where com_id=v_SalesCompActive.com_id) and EXISTS(select com_id from Crm_Assign where com_id=v_SalesCompActive.com_id and fdate<'"&date()-2&"')"
					end if
					sear=sear&"&dotype="&dotype
					end if
					'******************三天内未处理客户/end
					'******************我的重点客户/start
					if dotype="zhongdian" then
					
					sql=sql&" and personid="&session("personid")&" and EXISTS(select com_id from Crm_Assign where Emphases=1 and com_id=v_SalesCompActive.com_id)"
					sear=sear&"&dotype="&dotype
					end if
					'******************我的重点客户/end
					'******************我的再生通客户/start
					if dotype="myzst" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and vipflag=2 and vip_check=1"
						else
							sql=sql&" and personid='"&session("personid")&"' and vipflag=2 and vip_check=1"
						end if
					sear=sear&"&dotype="&dotype
					else
						if dotype<>"today" and doaction<>"allzst" and dotype<>"contact" and dotype<>"allall" and dotype<>"allcontinue" and request("subdotype")<>"continue" then
							sql=sql&" and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_SalesCompActive.com_id)"
						end if
					end if
					'******************我的再生通客户/end
					'******************公海客户/start
					if dotype="all" then
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and personid is null and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) and (com_id in (select com_id from comp_logincount)) and not EXISTS(select com_id from crm_assignVIP where com_id=v_SalesCompActive.com_id)"
							sear=sear&"&dotype="&dotype
					end if
					'******************公海客户/end
					'******************所有客户/start
					if dotype="allall" then
					    if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************所有客户/end
					
					'******************已激活客户/start
					if dotype="yesActive" then
					    '******联系三次以上
						'sql=sql&" and ContactTimes>=3 "
						'******联系之日开始登陆三次以上的
						'sql=sql&" and (select sum(LCount) from comp_logincount where fdate+1>(select fdate from crm_Active_assign where com_id=comp_logincount.com_id)) as Nowlogincount>=1 and lastlogintime>(select fdate from crm_Active_assign where com_id=v_SalesCompActive.com_id)"
						'调整
						sql=sql&" and EXISTS(select null from Crm_Active_ActiveComp where com_id=v_SalesCompActive.com_id)"
						if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************已激活客户/End
					
					'******************待激活客户/start
					if dotype="NoActive" then
						sql=sql&" and not EXISTS(select null from Crm_Active_ActiveComp where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************待激活客户/End
					
					'******************未能激活客户/start
					if dotype="CannotActive" then
						sql=sql&" and not EXISTS(select com_id from Crm_Active_Sales where com_id=v_SalesCompActive.com_id and ContactTimes>=3) and ContactTimes>=3 and logincount<3 and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************未能激活客户/End
					
					'******************联系三次的客户/start
					if dotype="3times" then
						sql=sql&" and ContactTimes>=3 and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id)"
						if session("userid")<>"10" then
							sql=sql&" and personid="&session("personid")&""
						else
						if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						end if
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************联系三次的客户/End
					
					'******************废品池/start
					if dotype="xm" then
					sql=sql&" and EXISTS(select com_id from Crm_Active_Waste where com_id=v_SalesCompActive.com_id)"
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
					sear=sear&"&dotype="&dotype
					end if
					'******************废品池/end
					
					'******************vip客户/star
				    if dotype="myvip" then
					sql=sql&" and EXISTS(select com_id from crm_assignVIP where com_id=v_SalesCompActive.com_id and personid="&session("personid")&") and not exists(select com_id from Crm_Assign where com_id=v_SalesCompActive.com_id)"
					sear=sear&"&dotype="&dotype
					end if
					'******************vip客户/end
					'******************特殊意向客户/start
					if dotype="Especial" then
					sql=sql&" and EXISTS(select com_id from comp_sales where com_Especial='1' and com_id=v_SalesCompActive.com_id)"
					sear=sear&"&dotype="&dotype
					end if
					'******************特殊意向客户/end
					'******************新客户分配/start
					if dotype="fenpei" then
					sql=sql&" and com_regtime>='"&date()-10&"' and com_regtime<='"&date()&"' and com_id not in (select com_id from crm_assign where Cushion<>-1) and (adminuser=0 or com_id in (select com_id from comp_logincount))"
					sear=sear&"&dotype="&dotype
						if request("zhuce")="zhudong" then
						sql=sql&" and (adminuser=0)"
						sear=sear&"&zhuce="&request("zhuce")
						end if
						if request("zhuce")="shouji" then
						sql=sql&" and adminuser<>''"
						sear=sear&"&zhuce="&request("zhuce")
						end if
					end if
					sear=sear&"&ss="&request("ss")
					'******************新客户分配/end
					'******************注册客户/start
					if doaction="reg" then
					sql=sql&" and (vipflag<1 or vip_check=0)"
					sear=sear&"&action="&doaction
					end if
					'******************注册客户/end
					'******************VIP试用客户/start
					if doaction="vip" then
					sql=sql&" and (vipflag=1 and vip_check=1)"
					sear=sear&"&action="&doaction
					end if
					'******************VIP试用客户/end
					'******************会展客户/start
					if doaction="exhibit" then
					sql=sql&" and com_id in (select com_id from comp_exhibit)"
					sear=sear&"&action="&doaction
					end if
					'******************会展客户/end
					'******************申请VIP试用客户/start
					if doaction="sqvip" then
					sql=sql&" and (vipflag=1 and vip_check=0)"
					sear=sear&"&action="&doaction
					end if
					'******************申请VIP试用客户/end
					'******************申请再生通/start
					if doaction="sqzst" then
					sql=sql&" and ((viptype<>0 and vipflag<>2) or (viptype<>0 and vip_check=0)) and vip_date<'"&date()&"'"
					sear=sear&"&action="&doaction
					end if
					'******************申请再生通/end
					'******************所有再生通/start
					if doaction="allzst" then
					sql=sql&" and vipflag=2 and vip_check=1"
					sear=sear&"&action="&doaction
					end if
					'******************所有再生通/end
					'续签CRM
					if dotype="allcontinue" then
						sql=sql&" and vipflag=2 and vip_check=1"
						if request("expired")<>"" then
							sql=sql&" and vip_dateto<'"&date()+30*int(request("expired"))&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&expired="&request("expired")
						end if
						if session("userid")<>"10" then
							sql=sql&" and personid="&session("personid")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'end 续签
					'******************所有正在联系的客户/
					if dotype="myyescontact" then
						sql=sql&" and personid="&session("personid")&"  and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) and EXISTS (select com_id from v_groupcomid where  com_id=v_SalesCompActive.com_id and EXISTS (select id from comp_tel where personid="&session("personid")&" and id=v_groupcomid.telid))"
						sear=sear&"&dotype="&dotype
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
					end if
					'******************所有正在联系的客户/
					'******************所有未联系的客户/
					if dotype="mynocontact" then
					if session("userid")="10" or session("personid")="32" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and not EXISTS (select com_id from Crm_Active_Sales where  com_id=v_SalesCompActive.com_id)"
					else
							sql=sql&" and personid="&session("personid")&" and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and not EXISTS (select com_id from Crm_Active_Sales where  com_id=v_SalesCompActive.com_id)"
					end if
					if request("subdotype")="continue" then
						sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
						sear=sear&"&subdotype="&request("subdotype")
					end if
					sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/
					'******************今日未联系的客户/
					if dotype="mynocontact_today" then
					sql=sql&" and personid="&request("personid")&" and not exists(select personid from v_SalesCompActive where v_SalesCompActive.personid is null and v_SalesCompActive.com_id=com_id)) and contactnext_time>'"&cdate(request("datetime"))&"' and contactnext_time<'"&cdate(request("datetime"))+1&"' and teldate<'"&cdate(request("datetime"))&"'"
					sear=sear&"&dotype="&dotype&"&datetime="&request("datetime")&"&personid="&request("personid")
					end if
					'******************今日未联系的客户/
					'******************明日联系的客户/
					if dotype="tomocontact" then
					sql=sql&" and personid="&request("personid")&" and contactnext_time >='"&cdate(request("datetime"))+1&"' and contactnext_time<='"&cdate(request("datetime"))+2&"'"
					sear=sear&"&dotype="&dotype&"&datetime="&request("datetime")&"&personid="&request("personid")
					end if
					'******************明日联系的客户/
					if dotype="chongtu" then
					sql=sql&" and com_email in (select com_email from c1 where com_id>44025 and com_id<44233)"
					sear=sear&"&dotype="&dotype
					end if
					if dotype="noassign" then
					sql=sql&" and not EXISTS(select com_id from Crm_Assign where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from crm_publiccomp where com_id=v_SalesCompActive.com_id) "
					sear=sear&"&dotype="&dotype
					end if
					if dotype="tnoassign" then
					sql=sql&" and not EXISTS(select com_id from Crm_Assign where com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_SalesCompActive.com_id) and com_regtime>'"&date()-1&"' and com_regtime<'"&date()&"' and (adminuser=0 or com_id in (select com_id from comp_logincount))"
					sear=sear&"&dotype="&dotype
					end if
					if dotype="vipnoassign" then
					sql=sql&" and (vipflag=1 and vip_check=1) and not EXISTS (select com_id from crm_assign where Cushion<>-1 and com_id=v_SalesCompActive.com_id) and not EXISTS (select com_id from crm_assignVIP where  com_id=v_SalesCompActive.com_id) and not EXISTS (select com_id from comp_sales where com_type='13' and com_id=v_SalesCompActive.com_id) "
					sear=sear&"&dotype="&dotype
					end if
					
					
					
					'if left(trim(sql),3)="and" then
					'sql=right(trim(sql),len(trim(sql))-3)
					'end if
					if session("userid")="10" then
					response.write(sql)
					end if
					sqlorder="order by "
				    ascdesc=request("ascdesc")
					select case request("comporder")
						case "1"
						sqlorder=sqlorder&"ACcom_rank "&ascdesc&""
						case "2"
						sqlorder=sqlorder&" logincount "&ascdesc&""
						case "3"
						sqlorder=sqlorder&"ACcontactnext_time "&ascdesc&""
						case "4"
						sqlorder=sqlorder&"vip_date "&ascdesc&""
						case "5"
						sqlorder=sqlorder&"LastTelDate "&ascdesc&""
						case "6"
						sqlorder=sqlorder&"com_regtime "&ascdesc&""
						case "7"
						sqlorder=sqlorder&"lastlogintime "&ascdesc&""
						case else
						sqlorder=sqlorder&"LastTelDate asc"
					end select
				    sqlorder=sqlorder&",com_id desc"
					
                    sear=sear&"&ascdesc="&request("ascdesc")
					sear=sear&"&comporder="&request("comporder")
				   startime=timer()
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "com_tel,com_mobile,com_email,com_province,com_id,com_name,com_pass,vipflag,viptype,vip_check,ACcontactnext_time,ACcom_rank,com_regtime,viewcount,logincount,lastlogintime,LastTelDate,adminuser"
					 .FROMTbl = "v_SalesCompActive"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select com_id from test where com_id=v_SalesCompActive.com_id) "&sql
				     .keyFld  = "com_id"    '不可缺少
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
              <form action="" method="post" name=form1>
                <tr class="topline"> 
                  <td width="4" bgcolor="#DFEBFF"><span class="STYLE2"></span></td>
                  <td nowrap bgcolor="#DFEBFF">客户<br>
                  类型</td>
                  <td bgcolor="#DFEBFF"><span class="STYLE2">中文名</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">客户<br>
等级</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">Email</span></td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">密码</td>
				  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">公司电话</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">手机</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>省份</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>登录<br>
                  次数</td>
                  
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>最后联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>下次联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>最近登录</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address><span class="style1 STYLE2">查看<br>
                  信息</span></td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>注册时间</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">拥有者</td>
                </tr>
                <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF 
				if formatdatetime(rs("com_regtime"),2)=formatdatetime(date()-1,2) then
				cbg="#CCFFCC"
				else
				cbg="#ffffff"
				end if
				
				%>
                <tr style="cursor:hand" bgcolor="<%=cbg%>"> 
                  <td height="24">
				  <input name="cbb<%=i%>" type="checkbox" title="<%response.Write(rs("com_email"))%>" value="<%response.Write(rs("com_id"))%>"></td>
                  
				  <SPAN onmouseover=DoHL();  onDblClick="javascript:DoSL();javascript:oow('../crmlocal/ActiveCrm_cominfoedit1.asp?idprod=<%response.Write(rs("com_id"))%>&cemail=<%response.Write(rs("com_email"))%>&dotype=<%response.Write(dotype)%>');" onmouseout=DoLL();>
				 
				  
				  <td nowrap>
				  
                    <%
				  if rs("vipflag")=0 and rs("viptype")=0 then
				  response.Write("注册")
				  end if
				  if rs("vipflag")=1 and rs("vip_check")=1 then
				  response.Write("<font color=#0066CC>VIP</font>")
				  end if
				  if rs("vipflag")=1 and rs("vip_check")=0 then
				  response.Write("<font color=#0066CC>申请VIP</font>")
				  end if
				  
				  if (rs("viptype")<>0 and rs("vipflag")<>2) or (rs("viptype")<>0 and rs("vip_check")=0) then
				  response.Write("<br><font color=#ff0000>申请再生通</font>")
				  end if
				  
				  if rs("vipflag")=2 and rs("vip_check")=1 then
				  response.Write("<font color=#009900>再生通</font>")
				  end if
				  
				'是否为会展客户
				sql0="select * from comp_exhibit where com_id="&rs("com_id")
				set rs0=conn.execute(sql0)
				if not (rs0.eof and rs0.bof ) then
					select case rs0("exhibit")
						case 1:ex="上海橡塑展"
						case 2:ex="广州金属展"
						case 3:ex="广州塑料展"
					end select
					select case rs0("customerType")
						case 1:cu="展位展商"
						case 2:cu="参观展商"
						case 3:cu="重点展商"
					end select	
					response.write("<br><a href='#' title='会展名称："&ex&vbcrlf&"展商来源："&cu&"'><font color='red'>(会展客户)</font></a>")
				end if
				rs0.close
				set rs0=nothing
				  %>                  </td>
                  <td nowrap>
				  <%response.Write(rs("com_name"))%>				  </td>
                  <td>
                  <%
				  if rs("ACcom_rank")<>"-1" then
				  response.Write(rs("ACcom_rank")&"星")
				  end if
				  %>				  </td>
                  <td><%response.Write(rs("com_email"))%></td>
                  <td nowrap><%response.Write(rs("com_pass"))%></td>
                  <td nowrap><%response.Write(rs("com_tel"))%></td>
                  <td nowrap><%response.Write(rs("com_mobile"))%></td>
                  <td nowrap><%response.Write(rs("com_province"))%>				  				  </td>
                  <td align="center" nowrap>
				<%  
				vcount=rs("viewcount")
				logincount=rs("logincount")
				lastlogintime=rs("lastlogintime")
				if rs("ACcontactnext_time")<>"1900-1-1" then
				contactnext_time=rs("ACcontactnext_time")
				else
				contactnext_time=""
				end if
					  %>
				  <%response.Write(logincount)%></td>
                  
                  <td nowrap>
                    
                    <%
					if rs("LastTelDate")="1900-1-1" then
					response.write("未联系")
					else
					response.Write(rs("LastTelDate"))
					end if
					%>                  </td>
                  <td nowrap>
                    <%response.Write(contactnext_time)%>                  </td>
                  <td nowrap>
                    <%
				  if lastlogintime<>"" or not isnull(lastlogintime) then
				  response.Write(lastlogintime)
				  end if
				  %>                  </td>
                  
                  <td align="center" nowrap> <a href="#kang" target="topt" onClick="window.open('../admin_user_viewlist.asp?com_email=<%response.Write(rs("com_email"))%>','_blank','width=500,height=500')"><%response.Write(vcount)%></A></td>
                  
                  <td nowrap><%response.Write(rs("com_regtime"))%>			      </td>
				  <%
				  sqluser="select a.personid,b.realname from crm_Active_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  
				  %>
                  </SPAN> 
                  <td align="center" nowrap>
				 <%response.write(realname)%>				 </td>
                </tr>
                <%
			                                   
     		RowCount = RowCount - 1
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="22">暂时无信息！</td>
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
	alert ("选择你要开通的公司！")
	return false
	}
	else
	{
	  if (confirm("开通这些公司吗?"))
	  {
	  window.open ("http://www.zz91.com/admin1/compinfo/cominfo_openConfirm.asp?selectcb="+selectcb.substr(2)+"&userName=<%= userName %>","_a","width=500,height=500")
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
	alert ("选择你要关闭的VIP客户！")
	return false
	}
	else
	{
	  if (confirm("关闭的这些VIP试用客户吗?"))
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
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("分配这些信息吗?"))
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
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
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
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
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
	alert ("选择你要分配的信息！")
	return false
	}
	else
	{
	  if (confirm("该操作将会把你的客户放到“未分配的客户表”里，确定要取消分配这些客户吗?"))
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
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的客户里”吗?"))
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
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的重点客户里”吗?"))
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
	alert ("选择你要设置的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户取消“重点客户”吗?"))
	  {
	  form.dostay.value="zhongdianout"
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
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“我的缓冲客户表”里吗?"))
	  {
	  form.dostay.value="selec1toutmycrm"
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
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“待删除区”里吗?"))
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
	alert ("选择你要分配的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放到“废品池”里吗?"))
	  {
	  form.dostay.value="selec1twastecom"
	  form.submit()
	  }
	}
  }
  function selectEmailcom(form)
  {
      form.selectcb.value="<%=sql%>"
	  form.dostay.value="selectEmailcom"
	  form.submit()
  }
  function delactive(form)
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
	alert ("选择你要删除的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要删除该客户吗?"))
	  {
	  form.dostay.value="delactive"
	  form.submit()
	  }
	}
  }
-->
    </script>
               
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="23"> 全选 
                    <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"> 
                    &nbsp;
					<%if session("userid") ="10" then%>
                    <input name="Submit" type="button" class="button" value="分配/从新分配给" onClick="selectcrm(this.form)">
                    <select name="personid" class="button" id="personid">
                      <option value="">请选择</option>
                      <%
					
					sqlu="select realname,id from users where closeflag=1 and userid=21"
					
					set rsu=server.CreateObject("ADODB.recordset")
					rsu.open sqlu,conn,1,2
					if not rsu.eof then
				    do while not rsu.eof
					%>
                      <option value="<%=rsu("id")%>"><%=rsu("realname")%></option>
                      <%
					rsu.movenext
					loop
					end if
					rsu.close()
					set rsu=nothing
					 %>
                    </select>					
                    <%
					end if
					if dotype="my" or dotype="mynocontact" or dotype="today" or dotype="todayfeipei" or dotype="3times"  then
					%>
                    <input name="Submit5" type="button" class="button" value="放入公海" onClick="delselectcrm(this.form)">
                    <%
					end if
					%>
                    <%if dotype<>"my" and dotype<>"allall" and dotype<>"mynocontact" and dotype<>"todayfeipei" and dotype<>"today" and dotype<>"3times" then%>
					<input type="button" name="Submit" class="button" value="放到我的客户" onClick="selectoutmycrm(this.form)">
					<%end if%>
					<%if dotype="my" or dotype="mynocontact" or dotype="today" or dotype="todayfeipei" or dotype="3times"  then%>
					<input type="button" name="Submit2" class="button" value="放到废品池" onClick="selectwastecom(this.form)">
					<%end if%>
					<input type="button" name="Submit22" class="button" value="放到EMAIL发送器" onClick="selectEmailcom(this.form)">
					<input type="hidden" name="dostay"> <input type="hidden" name="selectcb"> 
                    <input type="hidden" name="flag" value="<%=orderby%>"> 
                    <input type="hidden" name="page" value="<%=page%>">  
                    <input type="submit" name="button2" id="button2" value="从激活库里删除" onClick="delactive(this.form)">                </td>
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
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr> 
    <td align="center"> 
      <%endtime=timer()%>
      本页面执行时间：<%=FormatNumber((endtime-startime)*1000,3)%>毫秒</td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../conn_end.asp" -->