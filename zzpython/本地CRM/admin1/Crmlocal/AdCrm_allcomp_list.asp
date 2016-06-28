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
 
  if dostay="putInZst" then
 	response.Redirect("crm_assign_save.asp?selectcb="&request("selectcb")&"&dostay=selec1toutmycrm&personid="&request("personid")&"&userName="&request("userName")&"&doflag="&request("doflag"))
 end if
 
 if selectcb<>"" and dostay<>"" then
    response.Redirect("AdCrm_Assign_save.asp?selectcb="&request("selectcb")&"&dostay="&request("dostay"))&"&personid="&request("personid")
 end if
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
dotype=request("dotype")
doaction=request("action")
docach=request("cach")

'-------------判断是否用非法篡改DOTYPE
 
arrTempDotype=split(lcase(tempDotype),"|")
'if session("userid")="10" then
	if ubound(arrTempDotype)>0 then
		dotypeExists=0
		for o=0 to ubound(arrTempDotype)
			if arrTempDotype(o)<>"" then
				if cstr(lcase(dotype))=cstr(trim(arrTempDotype(o))) then
					dotypeExists=1
				end if
			end if
		next
		if dotypeExists=0 then
			response.Write("你没有权限操作！")
			response.End()
		end if
	end if
'end if

'response.Write(docach)
 sqluser="select realname,ywadminid,xuqianFlag from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
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

function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	for(var i=0;i<menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
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
	background-color: #FFFFCC;
	padding: 2px;
	border: 1px solid #CCCCCC;
}
-->
</style>
</head>

<body>
<table width="100%" id=secTable border="0" cellspacing="0" cellpadding="0">
  <tr>
   
    <td height="28" nowrap class="<%if doaction="" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>'">所有客户</td>
	<%if dotype<>"myzst" then%>
    <!--<td class="<%if doaction="reg" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=reg'">注册</td>
    <td class="<%if doaction="vip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=vip'">VIP</td>
    <td class="<%if doaction="sqvip" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqvip'">申请VIP</td>
    <td class="<%if doaction="sqzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst'">申请再生通</td>-->
	<td nowrap class="<%if doaction="exhibit" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=exhibit'">会展客户</td>
    <%if dotype="allzst" then%>
    <td nowrap class="<%if doaction="4star" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=4star'">服务部4/5星客户</td>
	<%end if%>
	<%if dotype="allall" or dotype="qingdao" then%>
	<td nowrap class="<%if doaction="allzst" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=allzst'">再生通</td>
	<%end if%>
	<%end if%>
    <td align="left" nowrap class="unselect" onClick="window.open('Crm_comp_add.asp?addtype=zst&dotype=<%response.Write(dotype)%>&cach=<%response.Write(docach)%>&action=sqzst','_blank','width=700,height=500')">
	 手动录入</td>
	 <td align="left" nowrap class="unselect" onClick="window.open('http://admin.zz91.com/admin1/adv/ggpos/index.html','_blank','')">
	 广告位示意图</td>
     <td align="left" nowrap class="unselect" onClick="window.open('http://admin.zz91.com/admin1/adv/localADlist.asp?personid=<%=session("personid")%>&userName=<%=userName%>','_blank','')">
    客户广告列表</td>
	 
	 <td align="left" class="unselect" onClick="window.location='?dotype=chongtu'">
	 冲突客户</td>
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
          <td nowrap>最后联系时间: </td>
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
		  <script language=javascript>createDatePicker("fromdate",true,"<%=fromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("todate",true,"<%=todate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td>电&nbsp; 话: </td>
          <td><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15"></td>
          <td>手&nbsp; 机:</td>
          <td><input name="com_mobile" type="text" class=text id="com_mobile" style="background-color:#fff;" size="15"></td>
          <td>下次联系时间: </td>
          <td><script language=javascript>createDatePicker("nfromdate",true,"<%=nfromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("ntodate",true,"<%=ntodate%>",false,true,true,true)</script></td>
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
			  <option value="8" <%response.Write(sl8)%>>到期时间</option>
        </select>
			<select name="ascdesc" id="ascdesc">
			  
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>降序</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>升序</option>
	        </select></td>
          <td>最近登录时间:</td>
          <td><script language=javascript>createDatePicker("Lfromdate",true,"<%=Lfromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("Ltodate",true,"<%=Ltodate%>",false,true,true,true)</script></td>
          </tr>
      </table></td>
    </tr>
    
    
    <tr>
      <td height="30" align="center" bgcolor="#f2f2f2"><script>selectOption("compkind",<%=request("compkind")%>)</script>
        <input name="ss" type="hidden" id="ss" value="1">
      <%if dotype="allall" or dotype="all" or dotype="3daynodo" or (dotype="my" and session("userid")="10") or (dotype="today" and session("userid")="10") or (dotype="contact" and session("userid")="10") or (dotype="nocontact" and session("userid")="10") or (dotype="myzst" and session("userid")="10") or (dotype="xm") or dotype="allbm" then%>
            <select name="doperson" class="button" id="doperson" >
              <option value="" >请选择--</option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and id in (select personid from crm_adOtherqx where userid=25)"
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>><%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
					
					 %>
            </select>			
            <%end if%>

		<select name="loginselect" id="loginselect">
		  <option value="" selected>登录时间</option>
		  <option value="3m">0-3个月内登陆</option>
		  <option value="6m">3-6个月内登陆</option>
		  <option value="9m">6-9个月内登陆</option>
		  <option value="12m">9-12个月内登陆</option>
		  <option value="15m">9-18个月内登陆</option>
		  <option value="18m">18个月未登陆</option>
	    </select>
		    <input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15">
      <input type="submit" name="Submit3" value="  搜 索  " class=button>	  </td>
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
					sql=sql&" and com_id in (select com_id from comp_TelAd where contactnext_time>'"&cdate(request("nfromdate"))&"'"
					sear=sear&"&nfromdate="&request("nfromdate")
					end if
					if trim(request("ntodate"))<>"" then
					sql=sql&" and contactnext_time<'"&cdate(request("ntodate"))+1&"')"
					sear=sear&"&ntodate="&request("ntodate")
					elseif trim(request("nfromdate"))<>"" then
					sql=sql&")"
					end if
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
					sql=sql&" and AD_comrank="&trim(request("com_rank"))&""
					sear=sear&"&com_rank="&request("com_rank")
					end if
					if request("province")<>"" and request("city")="城市" and request("province")<>"省份" then
					sql=sql&" and com_province like '%"&request("province")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("province")
					elseif request("province")<>"" and request("city")<>"" and request("province")<>"省份" and request("city")<>"城市" then
					sql=sql&" and com_province like '%"&request("city")&"%'"
					sear=sear&"&province="&request("province")&"&city="&request("province")
					end if
					'---------------begin
					'最近登录时间
					if trim(request("Lfromdate"))<>"" then
						sql=sql&" and lastlogintime>'"&cdate(request("Lfromdate"))&"'"
						sear=sear&"&Lfromdate="&request("Lfromdate")
					end if
					if trim(request("Ltodate"))<>"" then
						sql=sql&" and lastlogintime<'"&cdate(request("Ltodate"))+1&"'"
						sear=sear&"&Ltodate="&request("Ltodate")
					end if
					'---------------end 
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
					if request("ppcomp")="1" then
						sql=sql&" and exists (select null from comp_info where com_mobile = v_salescomp.com_mobile and com_id=v_salescomp.com_id and adminuser=10000) and com_mobile<>''"
						sear=sear&"&ppcomp="&request("ppcomp")
					end if
					'--------------客户类型 会议，展会，杂志
					if request("compkind")<>"" then
						sql=sql&" and exists(select null from crm_AdcompKind where com_id=v_salescomp.com_id and com_type like '"&request("compkind")&"%')"
						sear=sear&"&compkind="&request("compkind")
					end if
					'--------------------end 
				'***********搜索/end
errtext=""				
if dotype<>"allall" then
	searchflag=1
else
	if request("ss")<>"" and sql<>"" then
		searchflag=1
		sear=sear&"&ss=1"
	else
		searchflag=1
		errtext="<br><font color=#ff0000>客户列表需通过搜索才能显示！</font>"
	end if
end if
week=weekday(now)
if dotype="allGh" then
    nhour=Hour(now())
	nMinute=Minute(now())
	searchflag=0
    'if ywadminid="" or isnull(ywadminid) or ywadminid="0" then
		if formatdatetime(now,4)>"05:00" and formatdatetime(now,4)<"8:30" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:30" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"18:00" and formatdatetime(now,4)<"22:00" then
			searchflag=1
		end if
    'else
		'searchflag=1
	'end if
	if searchflag=0 then
		errtext="<br><font color=#ff0000>公海挑客户功能开放时间：中午  12:00-13:30     18:00-21:00</font>"
	end if

end if
'response.Write(session("userID"))
'if session("userClass")="1" or session("userID")="10"  then
if session("userID")="10"  then
	searchflag=1
end if
if searchflag=1 then%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	                <%
				
					'**************我的客户/start
					if dotype="serviceStar" then
						sql=sql&" and exists(select com_id from Crm_service where com_id=v_salescomp.com_id and com_rank>=4)  and vipflag=2 and vip_check=1"
						sear=sear&"&dotype="&request("dotype")
					end if
					if dotype="my" then
					    if session("userid")="10" then
							sql=sql&"  and com_id in (select com_id from crm_assignAD)"
						else
							sql=sql&" and com_id in (select com_id from crm_assignAD where personid="&session("personid")&")"
						end if
						'sql=sql&" and vip_datefrom<'"&date()-30&"' and vip_dateto>'"&date()+60&"'"
						'datediff("d",cdate(rs("vip_datefrom")),now)<30 or datediff("d",now,cdate(rs("vip_dateto")))<60
						sear=sear&"&dotype="&dotype
						cach=docach
						
						    if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
						sql=sql&"  "
					end if
					'**************我的客户/end
					'****************我的共享客户 /start
					if dotype="myShare" then
						sql=sql&" and com_id in (select com_id from crm_comp_share where salesType=0) and ADpersonid is null"
						sear=sear&"&dotype="&dotype
					end if
					'****************我的共享客户 /end
					'******************所有安排联系的客户/start
					if dotype="contact" then
						if session("userid")="10" or session("personid")="32" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1' and not EXISTS(select com_id from comp_ADsales where com_type='13' and com_id=v_salescomp.com_id)"
						else
							sql=sql&" and personid='"&session("personid")&"' and not EXISTS(select com_id from comp_ADsales where com_type='13' and com_id=v_salescomp.com_id) and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
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
								sql=sql&" and ADpersonid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and ADcontactnext_time>'"&date()&"' and ADcontactnext_time<'"&date()+1&"' and (ADpersonid is not null or ADpersonid<>'')"
						else
							sql=sql&" and ADpersonid="&session("personid")&" and ADcontactnext_time>'"&date()&"' and ADcontactnext_time<'"&date()+1&"'"
						end if
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+60&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
					sear=sear&"&dotype="&dotype
					end if
					'******************今天安排联系的客户/end
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
							sql=sql&" and teldate is not null and com_id=v_salescomp.com_id) "
							sql=sql&" and not EXISTS(select com_id from comp_ADsales where com_type='13' and com_id=v_salescomp.com_id) "
						else
							sql=sql&" and not EXISTS(select com_id from v_SalesTelTime where personid="&session("personid")&" and teldate is not null and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_ADsales where com_type='13' and com_id=v_salescomp.com_id) and personid="&session("personid")&" and Cushion<>-1 "
						end if
					sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/end
					'******************公海挑入客户/start
					if dotype="outgonghai" then
					sql=sql&" and personid="&session("personid")&" and EXISTS(select com_id from v_groupcomid where telid in (select id from comp_TelAd where personid<>"&session("personid")&") and com_id=v_salescomp.com_id)"
					sear=sear&"&dotype="&dotype
					end if
					'******************公海挑入客户/end
					'******************三天内未处理客户/start
					if dotype="3daynodo" then
					if session("userid")="10" then
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
					sql=sql&" and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) and EXISTS(select com_id from Crm_AssignAD where com_id=v_salescomp.com_id and fdate<'"&date()-2&"')"
					else
					sql=sql&" and personid="&session("personid")&" and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) and EXISTS(select com_id from Crm_AssignAD where com_id=v_salescomp.com_id and fdate<'"&date()-2&"')"
					end if
					sear=sear&"&dotype="&dotype
					end if
					'******************三天内未处理客户/end
					'******************我的重点客户/start
					if dotype="zhongdian" then
					if session("userid")="10" then
					sql=sql&" and EXISTS(select com_id from crm_assignAD where Emphases=1 and com_id=v_salescomp.com_id)"
					else
					sql=sql&" and ADpersonid="&session("personid")&" and EXISTS(select com_id from crm_assignAD where Emphases=1 and com_id=v_salescomp.com_id and personid="&session("personid")&")"
					end if
					sear=sear&"&dotype="&dotype
					end if
					'******************我的重点客户/end
					'--------------------广告签订成功
					if dotype="myad" then
						sql=sql&" and EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id) and EXISTS(select null from crm_OpenADConfirm where com_id=v_salescomp.com_id and isOpen=1"
						if request("expired")="1" then
							sql=sql&" and adCloseTime<'"&date()+30&"'"
						end if
						if request("expired")="true" then
							sql=sql&" and adCloseTime<'"&date()&"'"
						end if
						sql=sql&")"
						    if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
						sear=sear&"&dotype="&dotype
					end if
					'--------------------------------end
					'--------------------广告签订成功
					if dotype="adGh" then
						sql=sql&" and not EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id) and EXISTS(select null from crm_OpenADConfirm where com_id=v_salescomp.com_id )"
						    if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
						sear=sear&"&dotype="&dotype
					end if
					'--------------------------------end
					'--------------------部门所有客户
					if dotype="allbm" then
						sql=sql&" and EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id)"
						    if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
						sear=sear&"&dotype="&dotype
					end if
					'--------------------------------end
					'******************设备中心客户/star
				    if dotype="sbcomp" then
						sql=sql&" and EXISTS(select com_id from Sb_Com_Cls where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'******************设备中心客户/end
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
					
					end if
					'******************我的再生通客户/end
					'******************公海客户/start
					if dotype="all" then
					        if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							'***********2006-7-1到2007-1-1注册 未登录的客户
							'sql=sql&" and ((com_regtime>'2006-7-1' and com_regtime<'2007-1-1' and not exists(select null from comp_logincount where com_id=v_salescomp.com_id))"
							'***********9-18月登录过的客户
							'frdate=cdate(date()-18*30)
						    'frdate=year(frdate)&"-"&month(frdate)&"-1"
						    'trdate=cdate(date()-9*30)
						    'trdate=year(trdate)&"-"&month(trdate)&"-1"
							'sql=sql&" or (EXISTS(select null from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id) and not EXISTS(select null from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id)))"
							'sql=sql&" and not exists(select null from comp_zstinfo where com_id=v_salescomp.com_id)"
							'sql=sql&" and ADpersonid is null and teldate<'"&date()-60&"' and com_regtime<'"&date()-30&"' and com_id not in (select com_id from comp_zstinfo)"
							'********再生通私海
							'sql=sql&" and not exists(select null from Crm_AssignAD where com_id=v_salescomp.com_id)"
							''调整
							sql=sql&" and exists(select null from crm_web_compall where com_id=v_salescomp.com_id) and not exists(select null from crm_assignAD where com_id=v_salescomp.com_id)"
							'*******排出激活部
							sql=sql&" and not exists(select null from Crm_Active_CompAll where com_id=v_salescomp.com_id)"
							sear=sear&"&dotype="&dotype
							if session("Partadmin")="0" then
							sql=sql&" and (com_province like '%江苏%' or com_province like '%浙江%')"
							end if
							
					end if
					'******************公海客户/end
					'******************再生通客户/start
					if dotype="allzst" then
					sql=sql&" and vipflag=2 and vip_check=1"
					sql=sql&" and vip_datefrom<'"&date()-30&"' and vip_dateto>'"&date()+60&"' and not exists(select null from crm_assignad where com_id=v_salescomp.com_id)"
					sear=sear&"&dotype="&dotype
					'---------------服务部4，5星客户
						if doaction="4star" then
							sql=sql&" and exists(select com_id from Crm_service where com_id=v_salescomp.com_id and com_rank>=4)"
							sear=sear&"&action="&doaction
						end if
					end if
					'******************再生通客户/start
					if dotype="allall" then
					    if request("doperson")<>"" then
							sql=sql&" and ADpersonid="&request("doperson")&" "
							sear=sear&"&doperson="&request("doperson")
						end if
						
						sear=sear&"&dotype="&dotype
						   
					elseif dotype<>"xm" then
						sql=sql&" and not EXISTS(select com_id from crm_adwaste where waste=1 and com_id=v_salescomp.com_id)"
					end if
					'****************青岛展会客户
					if dotype="qingdao" then
						sql=sql&" and EXISTS(select com_id from email_othercomp where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'****************青岛展会客户
					'******************所有客户/end
					'******************登录情况 3个月
					if request("loginselect")<>"" then
						if request("loginselect")="3m" then
						frdate=cdate(date()-3*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						sql=sql&" and EXISTS(select com_id from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id)"
						end if
						frdate=cdate(date()-6*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						trdate=cdate(date()-3*30)
						trdate=year(trdate)&"-"&month(trdate)&"-1"
						if request("loginselect")="6m" then
						sql=sql&" and EXISTS(select com_id from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id)"
						end if
						if request("loginselect")="9m" then
						frdate=cdate(date()-9*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						trdate=cdate(date()-6*30)
						trdate=year(trdate)&"-"&month(trdate)&"-1"
						sql=sql&" and EXISTS(select com_id from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id)"
						end if
						if request("loginselect")="12m" then
						frdate=cdate(date()-12*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						trdate=cdate(date()-9*30)
						trdate=year(trdate)&"-"&month(trdate)&"-1"
						sql=sql&" and EXISTS(select com_id from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id)"
						end if
						if request("loginselect")="15m" then
						frdate=cdate(date()-18*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						trdate=cdate(date()-9*30)
						trdate=year(trdate)&"-"&month(trdate)&"-1"
						sql=sql&" and EXISTS(select com_id from comp_logincount where fdate>='"&frdate&"' and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id)"
						end if
						if request("loginselect")="18m" then
						frdate=cdate(date()-18*30)
						frdate=year(frdate)&"-"&month(frdate)&"-1"
						trdate=cdate(date()-18*30)
						trdate=year(trdate)&"-"&month(trdate)&"-1"
						sql=sql&" and not EXISTS(select com_id from comp_logincount where fdate>='"&trdate&"' and  com_id=v_salescomp.com_id) and com_regtime<='"&trdate&"'"
						end if
						sear=sear&"&loginselect="&request("loginselect")
					end if
				    '******************登录情况 3个月
					'******************废品池/start
					if dotype="xm" then
					sql=sql&" and com_id in (select com_id from crm_adwaste) "
					        if request("doperson")<>"" then
								sql=sql&" and ADpersonid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
					sear=sear&"&dotype="&dotype
					end if
					'******************废品池/end
					
					
					'******************特殊意向客户/start
					if dotype="Especial" then
					sql=sql&" and EXISTS(select com_id from comp_ADsales where com_Especial='1' and com_id=v_salescomp.com_id)"
					sear=sear&"&dotype="&dotype
					end if
					'******************特殊意向客户/end
					'******************新客户分配/start
					if dotype="fenpei" then
					sql=sql&" and com_regtime>='"&date()-10&"' and com_regtime<='"&date()&"' and com_id not in (select com_id from Crm_AssignAD where Cushion<>-1) and (adminuser=0 or com_id in (select com_id from comp_logincount))"
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
					
					'******************所有正在联系的客户/
					if dotype="myyescontact" then
						if session("userid")="10" then
						sql=sql&" and EXISTS (select com_id from  comp_TelAd where com_id=v_salescomp.com_id and telflag=1) and ADpersonid is not null"
						else
						sql=sql&" and ADpersonid="&session("personid")&" and EXISTS (select com_id from comp_TelAd where personid="&session("personid")&" and com_id=v_salescomp.com_id)"
						end if
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
								sql=sql&" and ADpersonid="&request("doperson")
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and not EXISTS (select com_id from comp_telad where com_id=v_salescomp.com_id) and ADpersonid is not null"
					else
							sql=sql&" and ADpersonid="&session("personid")&" and not EXISTS (select com_id from comp_telad where  com_id=v_salescomp.com_id)"
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
						sql=sql&" and personid="&request("personid")&" and not exists(select personid from v_salescomp where v_salescomp.personid is null and v_salescomp.com_id=com_id)) and adcontactnext_time>'"&cdate(request("datetime"))&"' and adcontactnext_time<'"&cdate(request("datetime"))+1&"' and teldate<'"&cdate(request("datetime"))&"'"
						sear=sear&"&dotype="&dotype&"&datetime="&request("datetime")&"&personid="&request("personid")
					end if
					'******************今日未联系的客户/
					'******************明日联系的客户/
					if dotype="tomocontact" then
						sql=sql&" and adpersonid="&request("personid")&" and adcontactnext_time >='"&cdate(request("datetime"))+1&"' and adcontactnext_time<='"&cdate(request("datetime"))+2&"'"
						sear=sear&"&dotype="&dotype&"&datetime="&request("datetime")&"&personid="&request("personid")
					end if
					'******************明日联系的客户/
					
					if dotype="noassign" then
						sql=sql&" and not EXISTS(select com_id from Crm_AssignAD where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_publiccomp where com_id=v_salescomp.com_id) "
						sear=sear&"&dotype="&dotype
					end if
					if dotype="tnoassign" then
						sql=sql&" and not EXISTS(select com_id from Crm_AssignAD where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and com_regtime>'"&date()-1&"' and com_regtime<'"&date()&"' and (adminuser=0 or com_id in (select com_id from comp_logincount))"
						sear=sear&"&dotype="&dotype
					end if
					'本部门录入的客户/公海 /star
					if dotype="insertComp" then
						sql=sql&" and EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2) and not EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'本部门录入的客户/公海 /end
					'我录入的客户 /star
					if dotype="myinsertComp" then
						sql=sql&" and EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and personid="&session("personid")&") "
						sear=sear&"&dotype="&dotype
					end if
					'我录入的客户 /end
					'公海普通的客户 /star
					if dotype="allputong" then
						sql=sql&" and teldate<='"&date()-30&"' and not EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id) and not exists(select null from comp_zstinfo where com_check=1 and com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'公海普通的客户 /end
					'-------------------与再生通销售冲突客户
					if dotype="chongtu" then
						sql=sql&" and com_id in(select a.com_id from crm_assignad a,crm_assign b where a.com_id=v_salescomp.com_id and a.com_id=b.com_id and a.personid<>b.personid)"
						sear=sear&"&dotype="&dotype
					end if
					'------------------------------------------
					'公海再生通的客户 /star
					if dotype="allzstGh" then
'						sql=sql&"  and not EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp.com_id) and exists(select null from comp_zstinfo where com_check=1 and com_id=v_salescomp.com_id)"
'						sear=sear&"&dotype="&dotype
						response.redirect("crm_allcomp_list.asp?dotype=allzstGh")
					end if
					'公海再生通的客户 /end
					'公海客户 /star
					if dotype="allGh" then
						if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" and Cushion<>-1"
								sear=sear&"&doperson="&request("doperson")
						    end if
						   
							sql=sql&" and com_id not in (select com_id from comp_sales where com_type=13 )"
							'--------不在关联客户里
							sql=sql&" and not EXISTS (select com_id from crm_complink where flag=0 and shflag=1 and com_id=v_salescomp.com_id) "
							'sql=sql&" and (adminuser=0 or com_email in (select com_email from comp_login)) "
							'-----排除垃圾客户
							sql=sql&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
							'*****排出私人库
							sql=sql&" and not exists(select null from crm_assignAD where com_id=v_salescomp.com_id)"
							'*****要有广告销售记录
							sql=sql&" and exists(select null from comp_telAD where com_id=v_salescomp.com_id)"
							'*****排出品牌通客户
							sql=sql&" and not exists(select null from Crm_web_CompAll where com_id=v_salescomp.com_id)"
							if doaction="gonghainocontact" then
								sql=sql&" and not EXISTS(select com_id from v_groupcomid where  com_id=v_salescomp.com_id)"
								sear=sear&"&action="&doaction
							end if
							if(request("aaa")="zst") then
								sql=sql&"and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
								sear=sear&"&aaa="&request("aaa")
							end if
								sear=sear&"&dotype="&dotype
					end if
					'公海客户 /end
					'公海过期再生通的客户 /star
					if dotype="allExpireZstGh" then
'						sql=sql&"  and not EXISTS(select com_id from crm_assignAD where com_id=v_salescomp.com_id)  and exists(select com_id from v_compContinue where com_id=v_salescomp.com_id   and dateto<'"&date()&"')" '临时将时间限定为已过期30天内
'						sear=sear&"&dotype="&dotype
						response.redirect("crm_allcomp_list.asp?dotype=allExpireZstGh")
					end if
					
					'公海过期再生通的客户 /end
					if dotype="vipnoassign" then
						sql=sql&" and (vipflag=1 and vip_check=1) and not EXISTS (select com_id from Crm_AssignAD where Cushion<>-1 and com_id=v_salescomp.com_id) and not EXISTS (select com_id from crm_assignVIP where  com_id=v_salescomp.com_id) and not EXISTS (select com_id from comp_ADsales where com_type='13' and com_id=v_salescomp.com_id) "
						sear=sear&"&dotype="&dotype
					end if
                    
					if session("userid")="10" then
						response.write(sql)
					end if
					sqlorder="order by "
				    ascdesc=request("ascdesc")
					select case request("comporder")
						case "1"
						sqlorder=sqlorder&"AD_comrank "&ascdesc&""
						case "2"
						sqlorder=sqlorder&" logincount "&ascdesc&""
						case "3"
						sqlorder=sqlorder&"ADcontactnext_time "&ascdesc&""
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
				     .sltFld  = "com_tel,com_mobile,com_email,com_province,com_id,com_name,com_pass,vip_date,vip_datefrom,vipflag,viptype,vip_check,ADcontactnext_time,com_rank,AD_comrank,com_regtime,viewcount,logincount,lastlogintime,teldate,vip_dateto"
					 .FROMTbl = "v_salescomp"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) and not exists(select com_id from crm_miluoComp where com_id=v_salescomp.com_id)"&sql
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
              <form action="AdCrm_allcomp_list.asp" method="post" name=form1>
                <tr class="topline"> 
                  <td width="4" bgcolor="#DFEBFF"><span class="STYLE2"></span></td>
                  <td nowrap bgcolor="#DFEBFF">客户<br>
                  类型</td>
                  
                  <td bgcolor="#DFEBFF"><span class="STYLE2">中文名</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">客户<br>
等级</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">Email</span></td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">最近登录</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">登录<br>
次数</td>
                  
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">公司电话</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">手机</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>省份</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>最后联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>下次联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>再生通服务期</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>注册时间</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">再生通<br>
                  销售人</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">广告销售</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">BD销售</td>
                </tr>
                <%
				function showbdtype(code)
					sqlty="select meno from cate_AdCompType where code='"&code&"'"
					set rsty=conn.execute(sqlty)
					if not rsty.eof or not rsty.bof then
						showbdtype=rsty(0)
					end if
					rsty.close
					set rsty=nothing
				end function
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF 
				
				if formatdatetime(rs("com_regtime"),2)=formatdatetime(date()-1,2) then
					cbg="#CCFFCC"
				else
					cbg="#ffffff"
				end if
				if rs("ADcontactnext_time")<>"1900-1-1" then
					contactnext_time=rs("ADcontactnext_time")
				else
					contactnext_time=""
				end if
				logincount=rs("logincount")
				lastlogintime=rs("lastlogintime")
				
				  sqls="select top 1 teldate,personid from comp_TelAd where telflag=0 and com_id="&rs("com_id")&" and ContactType=13 order by id desc"
				  set rss=conn.execute(sqls)
				  if not rss.eof then
					  lteldate=rss(0)
					  ADpersonid=rss(1)
				  else
					  ADpersonid=0
					  lteldate=""
				  end if
				  rss.close
				  set rss=nothing
				  if lteldate="" or isnull(lteldate) then
				  	l=0
				  else
				  	l=datediff("d",cdate(lteldate),now)
				  end if
				  if l<30 and l>0 then
				  	salesexiats=1
				  else
				  	salesexiats=0
				  end if
				  
				  
				%>
                <tr style="cursor:hand" bgcolor="<%=cbg%>"> 
                  <td height="24">
				  <input name="cbb<%=i%>" type="checkbox" title="<%response.Write(rs("com_email"))%>" value="<%response.Write(rs("com_id"))%>"></td>
                  <%
				  'sqlas="select fdate from crm_assignAD where com_id="&rs("com_id")&""
'				  set rsas=conn.execute(sqlas)
'				  if not rsas.eof then
'				  	if cdate(rsas(0))<cdate("2008-1-5") then
'					assignop=1
'					else
'					assignop=0
'					end if
'				  else
'				  	assignop=0
'				  end if
'				  rsas.close
'				  set rsas=nothing
				  'sqlzs="select TowebContact from comp_ADsales where com_id="&rs("com_id")
'				  set rszs=conn.execute(sqlzs)
'				  if not rszs.eof then
'					  if rszs(0)="1" then
'					  assignop=1
'					  end if
'				  end if
'				  rszs.close
'				  set rszs=nothing
				  '-----------------------
				  '*共享客户 /start
				  '---------------------------
				 
				  sqlzs="select com_id from Crm_Comp_Share where com_id="&rs("com_id")&" and salesType=0"
				  set rszs=conn.execute(sqlzs)
				  if not rszs.eof then
				  salesexiats=0
				  end if
				  rszs.close
				  set rszs=nothing
				   '-----------------------
				  '*共享客户 /end 
				  '---------------------------
				 %>
				 <SPAN onmouseover=DoHL();  onDblClick="javascript:DoSL();javascript:oow('../crmlocal/Crm_cominfoedit.asp?idprod=<%response.Write(rs("com_id"))%>&cemail=<%response.Write(rs("com_email"))%>&dotype=<%response.Write(dotype)%>');" onmouseout=DoLL();>
				
				  <td nowrap>
				  
                    <%
					sqltp="select com_type from crm_AdcompKind where com_id="&rs("com_id")&""
					set rstp=conn.execute(sqltp)
					if not rstp.eof or not rstp.eof then
					while not rstp.eof 
						response.Write(showbdtype(rstp(0)))
					rstp.movenext
					wend
					end if
					rstp.close
					set rstp=nothing
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
				
				'是否已分配
				'sql0="select selected from crm_assignAD where com_id="&rs("com_id")
'				
'				set rs0=conn.execute(sql0)
'				
'				if not (rs0.eof and rs0.bof ) then
'					response.write "<br>"
'					if rs0(0)="0" then
'						
'						response.write("<font color='#999999'>未分配</font>")
'					elseif rs0(0)="1" then
'						response.write("<font color='#ff0000'>已分配</font>")
'					end if
'				end if
'				rs0.close
'				set rs0=nothing
				  %>         </td>
                  
                   
                  <td nowrap> 
				  <%
				  response.Write(rs("com_name"))%>
				  <%
				  'response.Write(lteldate)
				 
				  if l<30 and l>0 then
				  %>
				  <div class="crm_tishi">
				  <%
				  sqlr="select realname from users where id="&ADpersonid
				  set rsr=conn.execute(sqlr)
				  if not rsr.eof then
				  realname=rsr(0)
				  else
				  realname=""
				  end if
				  rsr.close
				  set rsr=nothing
				  %>
				  <%=lteldate%><%=realname%>联系过				  </div>
				  <%
				  end if
				  if rs("vipflag")=2 and rs("vip_check")=1 then
					  if datediff("d",cdate(rs("vip_datefrom")),now)<30 or datediff("d",now,cdate(rs("vip_dateto")))<30 then
					    response.Write("<div class='crm_tishi'>")
					  	response.Write("<font color=#9966ff>此客户处于签单后一个月或续签前一个月，请匆进行广告销售</font>")
						response.Write("</div>")
					  end if
					  
				  end if
				  %>				  </td>
                  <td>
                    <%
				  if rs("AD_comrank")<>"-1" then
				  response.Write(rs("AD_comrank")&"星")
				  end if
				  %>                 </td>
                  <td><%response.Write(rs("com_email"))%></td>
                  <td nowrap><%
				  if lastlogintime<>"" or not isnull(lastlogintime) then
				  response.Write(lastlogintime)
				  end if
				  %></td>
                  <td nowrap><%  
				
				response.Write(logincount)
				%></td>
                  <!--<td nowrap><%response.Write(rs("com_pass"))%></td>-->
                  <td nowrap><%response.Write(rs("com_tel"))%></td>
                  <td nowrap>
				  <%
				  tel=""
				  mobile=right(rs("com_mobile"),6)
				  if rs("com_tel")<>"" then
				  tel=right(rs("com_tel"),6)
				  end if
				  com_id=rs("com_id")
				  existsmobile=0
				  if dotype="myinsertComp" then
				    'sqlr="select com_id,com_name,com_mobile,com_tel,com_email from comp_info where (com_mobile like '%"&mobile&"'" 
'					if tel<>"" then
'					sqlr=sqlr&" or com_tel like '%"&tel&"'"
'					end if
'					sqlr=sqlr&" ) and com_id<>"&com_id&""
					sqlr="select com_id from crm_webPipeiComp where fromcom_id="&rs("com_id")
					set rsr=conn.execute(sqlr)
					if not rsr.eof then
					existsmobile=1
					end if
					rsr.close
					set rsr=nothing
				  end if
				  %>
				  <%if existsmobile=1 then%>
				  <span onMouseOver=DoHL();  onDblClick="javascript:DoSL();javascript:oow('../crmlocal/AdCrm_cominfoedit.asp?idprod=<%response.Write(rs("com_id"))%>&cemail=<%response.Write(rs("com_email"))%>&dotype=<%response.Write(dotype)%>');" onMouseOut=DoLL();><a href="#kang" onClick="window.open('pp_comp.asp?mobile=<%response.Write(rs("com_mobile"))%>&tel=<%=rs("com_tel")%>&dotype=<%=dotype%>&com_id=<%=rs("com_id")%>','_blank','width=600,height=500')">
				  <%response.Write(rs("com_mobile"))%>
				  </a></span>
				  <%else%>
				  <%response.Write(rs("com_mobile"))%>
				  <%end if%>				  </td>
                  <td nowrap><%response.Write(rs("com_province"))%>				  				  				</td>
                  <td nowrap>
                    
                    <%
					if rs("teldate")="1900-1-1" then
					response.write("未联系")
					else
					response.Write(rs("teldate"))
					end if
					%>                  </td>
                  <td nowrap>
                    <%response.Write(contactnext_time)%>                  </td>
                  <td nowrap>
                    <%
				  if rs("vip_datefrom")<>"1900-1-1" then
				     response.Write(rs("vip_datefrom")&"<br>"&rs("vip_dateto"))
				  end if
				  %>                  </td>
                  
                  <td nowrap><%response.Write(rs("com_regtime"))%>			      </td>
                  <td align="center" nowrap>
                  <%
				     sqluser2="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
					 set rsuser2=conn.execute(sqluser2)
					 if not rsuser2.eof then
				 		realname=rsuser2("realname")
					 else
					 	realname=""
				     end if
					 rsuser2.close
					 set rsuser2=nothing
					 response.Write(realname)
				  %>
                  </td>
				  <td align="center" nowrap>
				  <%
				  sqluser="select a.personid,b.realname from Crm_AssignAD as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	realname=rsuser("realname")
				  else
				  	realname=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  %>
				 <%response.write(realname)%></td>
                  </SPAN> 
                  <td align="center" nowrap>
				  <%
				  sqluser="select a.personid,b.realname from crm_assignweb as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	realname=rsuser("realname")
				  else
				  	realname=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  %>
				 <%response.write(realname)%>				  </td>
                </tr>
                <%
			                                   
     		RowCount = RowCount - 1
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="24">暂时无信息！</td>
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
  function selectopenAD(form)
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
	  if (confirm("开通这些公司的广告吗?"))
	  {
	  window.open ("http://www.zz91.com/admin1/compinfo/cominfo_openADConfirm.asp?selectcb="+selectcb.substr(2)+"&userName=<%= userName %>")
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
  function putInZst(form)
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
	  if (confirm("分配这些客户到再生通销售吗?"))
	  {
	  form.dostay.value="putInZst"
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
	  if (confirm("该操作将会把你的客户放到“广告公海”里，确定要分配这些客户吗?"))
	  {
	  form.dostay.value="delselec1tcrm"
	  form.submit()
	  }
	}
  }
    function tempselectmycrm (form)
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
	  if (confirm("确定要分配这些客户吗?"))
	  {
	  form.dostay.value="tempselectmycrm"
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
	  if (confirm("确实要将该客户放到“我的客户表”里吗?"))
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
	alert ("选择你要安排的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放“广告部”联系吗?"))
	  {
	  form.dostay.value="selec1tTozst"
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
	alert ("选择你要放入的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要将该客户放“共享客户”吗?"))
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
	alert ("选择你要放入的客户！")
	return false
	}
	else
	{
	  if (confirm("确实要取消该客户为“共享客户”吗?"))
	  {
	  form.dostay.value="outselec1tToshare"
	  form.submit()
	  }
	}
  }
-->
    </script>
               
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="25"> 全选 
                    <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"> 
                    &nbsp;
					<input type="button" name="Submit" class="button" value="放到我的再生通客户" onClick="putInZst(this.form)">
					<%if dotype<>"my" and session("userid")="10" then%>
					<input type="button" name="Submit" class="button" value="放到我的广告客户" onClick="selectoutmycrm(this.form)">
					<%end if%>
					<%if session("userid") ="10" or session("personid")="227" or session("personid")="93" then%>
                    <input name="Submit" type="button" class="button" value="分配/从新分配给(广告)" onClick="selectcrm(this.form)">
                    <select name="personid" class="button" id="personid" >
              <option value="" >请选择--</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿汤建云</option>
			  <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿靳冲</option>
			  <% End If %>
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
						if cstr(request("doperson"))=cstr(rsu("id")) then
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
                    <%
					end if
					if dotype<>"all" and dotype<>"adGh" and dotype<>"xm" and dotype<>"allGh" and dotype<>"Especial" and dotype<>"allall" and dotype<>"allputong"  then
					%>
					<!--<input name="Submit5" type="button"  value="挑入我的客户" style="color:#f00;" onClick="tempselectmycrm(this.form)">-->
                    <input name="Submit5" type="button" class="button" value="放入广告公海" onClick="delselectcrm(this.form)">
					
					<input name="Submit5" type="button" class="button" value="设置为重点" onClick="zhongdian(this.form)">
					<input name="Submit5" type="button" class="button" value="取消为重点" onClick="zhongdianout(this.form)">
					<%
					end if
					%>
                    <!--<input type="button" name="Submit" class="button" value="放到非缓冲区" onClick="selectmycrm(this.form)">-->
                    
					<%if dotype="my" or dotype="nocontact" or dotype="today" or dotype="contact" and dotype<>"allall"  then%>
					<input type="button" name="Submit" class="button" value="放入广告待开通" onClick="selectopenAD(this.form)">
					<input type="button" name="Submit2" class="button" value="放到废品池" onClick="selectwastecom(this.form)">
					<%end if%>
					

					
					<input type="hidden" name="dostay"> <input type="hidden" name="selectcb"> 
                    <input type="hidden" name="flag" value="<%=orderby%>"> 
                    <input type="hidden" name="page" value="<%=page%>">                  </td>
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