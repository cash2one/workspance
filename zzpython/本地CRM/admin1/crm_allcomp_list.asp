<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<!--#include file="inc.asp"-->
<%
page=Request("page")
sear="lmcode="&lmcode
if page="" then page=1 
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
dotype=request("dotype")
lmaction=request("lmaction")
doaction=request("action")
docach=request("cach")
lmcode=request("lmcode")
duochangoder=request("duochangoder")
Regfromdate=request("Regfromdate")
Regtodate=request("Regtodate")
'----------获得栏目名称
sqllm="select meno from cate_qx where id ='"&lmcode&"'"
set rslm=conn.execute(sqllm)
if not rslm.eof or not rslm.bof then
	lmtextname=rslm(0)
end if
rslm.close
set rslm=nothing
 ''''''''''''''''''''''''''''''''''''''''''
selectcb=request("selectcb")
dostay=request("dostay")

if selectcb<>"" and dostay<>"" then
	response.Redirect("crm_assign_save.asp?selectcb="&request("selectcb")&"&dostay="&request("dostay")&"&dotype="&dotype&"&personid="&request("personid")&"&userName="&request("userName")&"&doflag="&request("doflag"))
end if

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
if application("coundate")="" then
	sqlcc="select count(0) from comp_info where not exists(select com_id from crm_assign where com_id=comp_info.com_id)"
	set rscc=conn.execute(sqlcc)
	gonghaiallcount=rscc(0)
	rscc.close
	set rscc=nothing
	application("coundate")=now
	application("coungonghai")=gonghaiallcount
else
	if cdate(application("coundate"))<date-1 then
		sqlcc="select count(0) from comp_info where not exists(select com_id from crm_assign where com_id=comp_info.com_id)"
		set rscc=conn.execute(sqlcc)
		gonghaiallcount=rscc(0)
		rscc.close
		set rscc=nothing
		application("coundate")=now
		application("coungonghai")=gonghaiallcount
	else
		gonghaiallcount=application("coungonghai")
	end if
end if
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid,usertel from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 mytel=rsuser("usertel")
 rsuser.close
 set rsuser=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>crm 公司列表</title>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="js/province.js"></SCRIPT>
<SCRIPT language=javascript src="js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="js/list.js"></SCRIPT>
<link href="css/list.css" rel="stylesheet" type="text/css">
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style>
.testgonghai
{
	font-size: 18px;
	line-height: 30px;
	font-weight: bold;
	padding-top: 6px;
	padding-right: 6px;
	padding-bottom: 6px;
	padding-left: 20px;	
}
</style>
</head>
<body>
<div id="wrap">
<%dis="display:none"%>
<div  id="page_cover" style="position:absolute;display:none;z-index:100; width:230px; height:59px; color:#cccccc; left: 0; top: 0;"></div>
<div id="regform" style=" <%=dis%>;z-index:1002; top:100px; margin-left:100px; width:400px; height:100px;" > 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size:12px">
      <tr>
        <td height="29" background="../images/greentitlebg.gif" bgcolor="#003399" class="alerttitle"><div class="alerttitlen" id="alerttile">创建产品系列</div></td>
      </tr>
      <tr>
      <form id="form2" name="form2" method="post" action="crm_assign_save.asp">
      <tr>
      <td height="100" bgcolor="#FFFFFF" class="alerttitle_kuang">
      
      
	  	
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
	     <%
	  sqls="select * from cate_droppedinseaCause"
	  set rss=conn.execute(sqls)
	  if not rss.eof or not rss.bof then
	  while not rss.eof
	  %><tr>
	      <td>
         
      <input type="radio" name="CauseID" id="CauseID" value="<%=rss("id")%>" /><%=rss("meno")%>
      
          </td>
	      </tr><%
	  rss.movenext
	  wend
	  end if
	  rss.close
	  set rss=nothing
	  %>
	      <tr>
	        <td align="center"><textarea name="CauseOther" id="CauseOther" cols="45" rows="5"></textarea></td>
	        </tr>
	      <tr>
	      <td align="center"><input type="submit" name="button" id="button" value="提交"></td>
	      </tr>
	    </table></td>
	  </tr></form>
  </table>
  <a  href="#" onClick="this.parentNode.style.display='none';document.getElementById('page_cover').style.display='none';return false;" style="float:right;top:8px;right:8px;position:absolute;"><img src="../images/alertclose.gif" border="0"></a>
</div>
<script type="text/javascript">

<!--

lastScrollY=0;

function heartBeat(){ 

    var diffY;

    if (document.documentElement && document.documentElement.scrollTop)

     diffY = document.documentElement.scrollTop;

    else if (document.body)

     diffY = document.body.scrollTop

    else

        {/*Netscape stuff*/}    

    //alert(diffY);

    percent=.1*(diffY-lastScrollY); 

    if(percent>0)percent=Math.ceil(percent); 

    else percent=Math.floor(percent); 

    

    var d = document.getElementById("regform")

    d.style.top = d.style.top == "" ? "100px" : d.style.top
    //d.style.top ="120px"
    d.style.top = parseInt(d.style.top) + percent + "px"    
    lastScrollY=lastScrollY+percent; 
    //alert(lastScrollY);
}   

window.setInterval("heartBeat()",1);

//-->

</script>
<style type="text/css">
	<!--
	.t {
	filter: Alpha(Opacity=30);
	-moz-opacity: 0.3;
	background-color: #999999;
    }
	#NewSort1{position:relative;}
	#NewSort1 div{position:absolute;top:-70px;left:-480px;width:200px;height:28px;}
	-->
</style>
<script>
function groupclose()
	  {
	  document.getElementById("regform").style.display='none';
	  document.getElementById("page_cover").style.display='none';
	  }
function openPageCover(){
	var screenHeight = screen.height;
	var screenWidth = screen.width;
	//
	var aa=document.getElementById("wrap").offsetHeight
	screenHeight=document.getElementsByTagName('body')[0].offsetHeight;
	if (screenHeight<aa)
	{
	screenHeight=aa;
	}

    document.getElementById("page_cover").style.display="";
	document.getElementById("page_cover").style.width=(screenWidth-20)+"px";
	document.getElementById("page_cover").style.height=screenHeight+"px";
	document.getElementById("page_cover").className="t"
	document.getElementById("regform").style.position="absolute"
	document.getElementById("regform").style.left=(screenWidth/2-300)+"px";
	document.getElementById("regform").style.display="";
}
function DropToSea(n)
{
	if (n==1)
	{
	document.getElementById("alerttile").innerHTML="请填写放入公海的原因！"
	openPageCover()
	}else{
	window.location='crm_assign_save.asp?selectcb=<%response.Write(idprod)%>&dostay=delselec1tcrm&closed=1'
	}
}
</script>
<div class="tishi" style="width:1600px">你的电话号码为<%=mytel%>，如果号码不对请<a href="recordService/mytel.asp" target="_blank">点此修改</a>
<%
sqlw="select top 1 tcontent from crm_awoke where tcheck=1 order by id desc"
set rsw=conn.execute(sqlw)
if not rsw.eof or not rsw.bof then
%>

	<%=rsw(0)%>

<%
end if
rsw.close
set rsw=nothing
%></div>
<table width="100%" id=secTable border="0" cellspacing="0" cellpadding="0">
  <tr>
    
    <td height="28" nowrap class="<%=topselectbutton(doaction,"")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&lmcode=<%=lmcode%>'"><%=lmtextname%> -> </td>
    <%if dotype="all" then%>
    <td nowrap class="<%if request("gonghaifw")="-2" then response.Write("selected") else response.Write("unselect") end if%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&gonghaifw=-2&lmcode=<%=lmcode%>&action=week2day'">2周内注册的客户</td>
    <%end if%>
    <%if left(dotype,3)="vap" then%>
    <td nowrap class="<%=topselectbutton(doaction,"myppt")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=myppt&lmcode=<%=lmcode%>'">品牌通</td>
    <%end if%>
    <td nowrap class="<%=topselectbutton(doaction,"zhongdiankh")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=zhongdiankh&lmcode=<%=lmcode%>'">重点客户准备</td>
	<%if dotype="vappaycomp" then%>
    <td nowrap class="<%=topselectbutton(doaction,"yixing")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=yixing&lmcode=<%=lmcode%>'">一星客户</td>
    <td nowrap class="<%=topselectbutton(doaction,"huangzhan")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=huangzhan&lmcode=<%=lmcode%>'">黄展客户</td>
    <td nowrap class="<%=topselectbutton(doaction,"biaowang")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=biaowang&lmcode=<%=lmcode%>'">标王客户</td>
    <td nowrap class="<%=topselectbutton(doaction,"dujia")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=dujia&lmcode=<%=lmcode%>'">独家广告</td>
	<%end if%>
    <%if left(dotype,3)="vap" then%>
    <td nowrap class="<%=topselectbutton(doaction,"huangye")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=huangye&lmcode=<%=lmcode%>'">黄页</td>
    <td nowrap class="<%=topselectbutton(doaction,"baojia")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=baojia&lmcode=<%=lmcode%>'">报价</td>
    <td nowrap class="<%=topselectbutton(doaction,"dqguanggao")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=dqguanggao&lmcode=<%=lmcode%>'">短期广告</td>
	<%end if%>
	<%'If (adminuserid<>"" and not isnull(adminuserid)) or session("userid")="10" or session("userid")="13" Then%>
    <td nowrap class="<%=topselectbutton(doaction,"exhibit")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=exhibit&lmcode=<%=lmcode%>'">会展客户</td>
    <%'end if%>
    <td nowrap class="<%=topselectbutton(doaction,"dropTosear")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=dropTosear&lmcode=<%=lmcode%>'">自动掉公海客户查询</td>
    <!--<td nowrap class="<%=topselectbutton(doaction,"activeToday")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=activeToday&lmcode=<%=lmcode%>'">今天登陆激活的客户</td>
    <td nowrap class="<%=topselectbutton(doaction,"laizhou")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=laizhou&lmcode=<%=lmcode%>'">线下客户</td>-->
	
    <td align="left" nowrap class="unselect" onClick="window.open('http://adminasto.zz91.com/companyadd/?addtype=zst&personid=<%=session("personid")%>','_blank','width=700,height=500')">
	 手动录入</td>
    <td align="left" nowrap class="unselect" onClick="window.open('/admin1/huangye/add.asp','_blank','width=700,height=500')">
	 2013黄页客户录入</td>
    
    <td align="left" nowrap class="<%=topselectbutton(doaction,"30dayNocontact")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=30dayNocontact&lmcode=<%=lmcode%>'">
	 <font color="#FF0000">30天内未联系(明天掉)</font></td>
    <%if left(dotype,3)<>"vap" then%>
    <td align="left" nowrap class="<%=topselectbutton(doaction,"3dayNocontact")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=3dayNocontact&lmcode=<%=lmcode%>'">
	 <font color="#FF0000">(新)3天内未联系(即掉)</font></td>
    <%end if%>
	
	<%if dotype<>"myzst" then%>
	<!--<td align="left" nowrap class="unselect" onClick="window.open('http://admin.zz91.com/admin1/adv/localADlist.asp?personid=<%=session("personid")%>&userName=<%=userName%>','_blank','')">
    <font color="#0000CC">广告预定</font></td>-->
	<td align="left" nowrap class="unselect" onClick="window.open('http://apps2.zz91.com/ads/ad/ad/saleAd.htm?crmId=<%=session("personid")%>&userName=<%=userName%>','_blank','')">
    <font color="#0000CC">广告预定</font></td>
	<%if dotype="allall" or dotype="today" then%>
	<td nowrap class="<%=topselectbutton(doaction,"allzst")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=allzst&lmcode=<%=lmcode%>'">再生通</td>
	<%end if%>
    
	<%end if%>
	<td align="left" nowrap class="<%=topselectbutton(doaction,"gonghainocontact")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&action=gonghainocontact&lmcode=<%=lmcode%>'"><font color="#FF0000">从未联系</font></td>
    
     <!--<td align="left" nowrap class="<%=topselectbutton(doaction,"yuyaoperson")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=yuyaoperson&lmcode=<%=lmcode%>'">
	 余姚私海</td>-->
     <%if session("personid")="93" or session("userid")="10" or session("userid")="13" or session("personid")="449" then%>
	 <td align="left" nowrap class="unselect" onClick="window.open('http://admin.zz91.com/admin1/pass/login.asp','_blank','width=600,height=400')">
	 <font color="#FF0000">客户密码查询</font></td>
     <td align="left" nowrap class="unselect" onClick="window.open('pass/searchlist.asp','_blank','width=600,height=500')">
	 密码查询情况</td>
     <%end if%>
     <%if dotype="bmzd" then%>
     <td align="left" nowrap class="<%=topselectbutton(doaction,"zhongdian")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=zhongdian&doperson=<%=request("doperson")%>&lmcode=<%=lmcode%>'">
	 未审核重点客户</td>
     <%end if%>
     <td align="left" nowrap class="<%=topselectbutton(doaction,"25nocontact")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=25nocontact&doperson=<%=request("doperson")%>&lmcode=<%=lmcode%>'">
	 25天未有效联系</td>
     <td align="left" nowrap class="unselect" onClick="window.open('pipei/p_add.asp','_blank','width=600,height=500')">
	 关联客户提交</td>
     <!--<td align="left" nowrap class="unselect" onClick="window.location='?dotype=<%response.Write(dotype)%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=conflict&lmcode=<%=lmcode%>'">
	 冲突用户分配</td>-->
     <td align="left" nowrap class="unselect" onClick="window.open('/admin1/crmlocal/admin/admin_tongji_msb_search.asp?lmcode=<%=lmcode%>','','')">
	 门市部流量查询</td>
     
    <td width="100%" align="left" class="unselect">&nbsp;</td>
  </tr>
</table>
<div class="testgonghai">
<%
'------即掉客户提醒

sqlp="select count(0) from v_salescomp where personid="&session("personid")&" and TelDate<='"&date-30&"' and not exists(select com_id from crm_assign where fdate>'"&date()-3&"' and com_id=v_salescomp.com_id)"
set rsp=conn.execute(sqlp)
p30day=rsp(0)
rsp.close
set rsp=nothing
sqlp="select count(0) from v_salescomp where personid="&session("personid")&" and exists(select com_id from crm_assign where DATEDIFF(DD, fdate, GETDATE()) > 3 and (not exists(select com_id from v_groupcomid where com_id=crm_assign.com_id and telid in (select id from comp_tel where personid=crm_assign.personid and contacttype=13)) ) and com_id=v_salescomp.com_id)"
set rsp=conn.execute(sqlp)
p3day=rsp(0)
rsp.close
set rsp=nothing
'-今天安排联系的4星客户数
sqlp="select count(0) from v_salescomp where personid="&session("personid")&" and contactnext_time>'"&date&"' and contactnext_time<'"&date()+1&"' and com_rank>='4' and com_rank<5"
set rsp=conn.execute(sqlp)
panpai4star=rsp(0)
rsp.close
set rsp=nothing
response.Write("<font color=#ff0000><b>提醒：</b></font>")
if panpai4star<>0 then
	response.Write("今天安排联系的4星客户数：<b>"&panpai4star&"</b>")
end if

if p30day<>0 then
response.Write("30天未联系即将掉：<b>"&p30day&"</b> 个 。")
end if
if p3day<>0 then
response.Write(" 新客户或公海挑入三内未联系即将掉：<b>"&p3day&"</b> 个")
end if
%>
</div>
<%
mypartid=left(session("userid"),4)
partid=0
if mypartid="1301" or mypartid="1302" then partid="1"
if mypartid="1303" or mypartid="1306" then partid="2"
if session("personid")="93" then partid="1"
if session("personid")="227" then partid="2"
%>

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
<table  border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <form name="form2" method="get" action="crm_allcomp_list.asp" onSubmit="getprovincename()">
    <tr>
      <td height="30" align="left" bgcolor="#FFFFFF">
	    <table border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td nowrap>公司名:</td>
          <td>
            <input name="com_name" type="text" class=text id="com_name" style="background-color:#fff;" size="15">
            <input name="action" type="hidden" id="action" value="<%=doaction%>">
            <input name="dotype" type="hidden" id="dotype" value="<%=dotype%>">
            <input name="lmaction" type="hidden" id="lmaction" value="<%=lmaction%>">
            <input name="cach" type="hidden" id="cach" value="<%response.Write(docach)%>">
            <input name="lmcode" type="hidden" id="lmcode" value="<%response.Write(lmcode)%>">
            <input name="zhuce" type="hidden" id="zhuce" value=<%response.write(request("zhuce"))%>>
            <input name="expired" type="hidden" id="expired" value="<%=request("expired")%>">
            <input name="adid" type="hidden" id="adid" value="<%=request.QueryString("adid")%>">
            <input name="adgonghai" type="hidden" id="adgonghai" value="<%=request.QueryString("adgonghai")%>">
            <input name="adshihai" type="hidden" id="adshihai" value="<%=request.QueryString("adshihai")%>">
            <input name="zsttype" type="hidden" id="zsttype" value="<%=request.QueryString("zsttype")%>">
        </td>
          <td nowrap>联系人:</td>
          <td><input name="com_contactperson" type="text" class=text id="com_contactperson" style="background-color:#fff;" size="15" value="<%=request("com_contactperson")%>"></td>
          <td nowrap>最后有效联系时间: </td>
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
          <td nowrap>电话/手机: </td>
          <td><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15" value="<%=request("com_tel")%>"></td>
          <td><strong style="color:#F00">Email:</strong></td>
          <td><input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15"></td>
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
            <option value="1">废金属</option>
            <option value="2">废塑料</option>
            <option value="3">废旧轮胎与废橡胶</option>
            <option value="4">废纺织品与废皮革</option>
            <option value="5">废纸</option>
            <option value="6">废电子电器与废电脑设备</option>
            <option value="10">废玻璃与废木制品</option>
            <option value="12">废旧设备与旧交通工具</option>
          </select>
          <script>selectOption("clscb","<%=request("clscb")%>")</script> 
          </td>
          <td>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 区:</td>
          <td>

<font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
                        <input type="hidden" name="province" id="province">
                        <input type="hidden" name="city" id="city">
                        <input type="hidden" name="cprovince" id="cprovince" value="0">
                <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=request("province1")%>";
                            var Fvalue2="<%=request("city1")%>";
                            var Fvalue3="<%=request("Garden")%>";
                            var hyID="clscb";//行业ID号
                            getprovincevalue();
                        </script>
                <script>
                        function getprovincename()
                        {
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>

</td>
          </tr>
        <tr>
          <td>等&nbsp; 级:</td>
          <td nowrap>
          <%if left(dotype,3)="vap" then%>
                           <select name="com_rank" id="com_rank" style="background:#ebebeb; width:120px">
                            <option value="">--请选择等级--</option>
                            <option value="1" >★无条件，无意愿</option>
                            <option value="2" >★★有条件，无意愿</option>
                            <option value="3" >★★★意愿不明确</option>
                            <option value="4" >★★★★条件好，意向好</option>
                            <option value="4.8" >|——有意愿，时间未定</option>
                            <option value="4.1" >|——近期有意愿【一个月内】</option>
                            <option value="5" >★★★★★意愿好，近3天会合作</option>
                            <option value="6" >★★★★★★已合作</option>
                          </select>
                           <%else%>   
                          <select name="com_rank" id="com_rank" style="background:#ebebeb; width:120px">
                            <option value="">--请选择等级--</option>
                            <option value="1" >★无条件，无意向</option>
                            <option value="2" >★★有条件，目前无意向</option>
                            <option value="3" >★★★条件好，意向一般</option>
                            <option value="4" >★★★★条件好，意向好</option>
                            <%if left(dotype,3)<>"vap" then%>
                            <option value="4.1" >|——普通四星</option>
                            <option value="4.8" >|——钻石四星</option>
                            <%else%>
                            <option value="4.8" >|——长四星</option>
                            <option value="4.1" >|——短四星</option>
                            <%end if%>
                            <option value="5" >★★★★★口头确定付款</option>
                          </select>
                          <%end if%>
           
              <script>selectOption("com_rank","<%=request("com_rank")%>")</script></td>
          <td>&nbsp;</td>
          <td nowrap>
            
            <select name="showitems" id="showitems">
	        <option>请选择</option>
	        <option value="offer">有供求信息</option>
	        <option value="receive">有收到询盘</option>
	        <option value="send">有发送询盘</option>
          </select>
          <script>selectOption("showitems","<%=request("showitems")%>")</script>
            </td>
          <td>最近登录时间:</td>
          <td><script language=javascript>createDatePicker("Lfromdate",true,"<%=request("Lfromdate")%>",false,true,true,true)</script>&nbsp;到&nbsp;今天<script language=javascript>//createDatePicker("Ltodate",true,"<%=Ltodate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td>主营业务:</td>
          <td nowrap><input name="zyyw" type="text" class="text" id="zyyw" size="15"></td>
          <td>排&nbsp; 序:</td>
          <td nowrap>
          <script>
		  function opendcorder(obj)
		  {
			  if(obj.checked==true)
			  {
				  document.getElementById("dcorder").style.display=""
			  }else{
				  document.getElementById("dcorder").style.display="none"
			  }
		  }
		  </script>
           <select name="comporder" id="comporder" style="width:80px;">
              <option value="">默认</option>
              <option value="1">客户等级</option>
              <option value="2">登录次数</option>
              <option value="3">下次联系时间</option>
              <option value="4">申请时间</option>
			  <option value="5">最后联系时间</option>
			  <option value="6">注册时间</option>
			  <option value="7">最近登陆时间</option>
			  <option value="8">到期时间</option>
              <option value="9">门市部流量</option>
              <option value="10">挑入时间</option>
              
            </select><script>selectOption("comporder","<%=request("comporder")%>")</script><select name="ascdesc" id="ascdesc">
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>降序</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>升序</option>
	        </select><input name="duochangoder" type="checkbox" id="duochangoder" onClick="opendcorder(this)" value="1" <%if duochangoder="1" then response.Write("checked")%>></td>
          <td>注册时间:</td>
          <td><script language=javascript>createDatePicker("Regfromdate",true,"<%=Regfromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("Regtodate",true,"<%=Regtodate%>",false,true,true,true)</script></td>
        </tr>
        </table></td>
    </tr>
    
    <%
	if duochangoder="" or isnull(duochangoder) then
		orderstyle="style='display:none'"
	else
		orderstyle=""
	end if
	%>
    <tr id="dcorder" <%=orderstyle%>>
      <td align="center" bgcolor="#f2f2f2">
      1\
      <select name="comporder1" id="comporder1" style="width:80px;">
          <option value="">默认</option>
          <option value="1">客户等级</option>
          <option value="2">登录次数</option>
          <option value="3">下次联系时间</option>
          <option value="4">申请时间</option>
          <option value="5">最后联系时间</option>
          <option value="6">注册时间</option>
          <option value="7">最近登陆时间</option>
          <option value="8">到期时间</option>
          <option value="9">门市部流量</option>
          <option value="10">挑入时间</option>
      </select><script>selectOption("comporder1","<%=request("comporder1")%>")</script>
      2\
      <select name="comporder2" id="comporder2" style="width:80px;">
          <option value="">默认</option>
          <option value="1">客户等级</option>
          <option value="2">登录次数</option>
          <option value="3">下次联系时间</option>
          <option value="4">申请时间</option>
          <option value="5">最后联系时间</option>
          <option value="6">注册时间</option>
          <option value="7">最近登陆时间</option>
          <option value="8">到期时间</option>
          <option value="9">门市部流量</option>
          <option value="10">挑入时间</option>
      </select><script>selectOption("comporder2","<%=request("comporder2")%>")</script>
      3\
      <select name="comporder3" id="comporder13" style="width:80px;">
          <option value="">默认</option>
          <option value="1">客户等级</option>
          <option value="2">登录次数</option>
          <option value="3">下次联系时间</option>
          <option value="4">申请时间</option>
          <option value="5">最后联系时间</option>
          <option value="6">注册时间</option>
          <option value="7">最近登陆时间</option>
          <option value="8">到期时间</option>
          <option value="9">门市部流量</option>
          <option value="10">挑入时间</option>
      </select><script>selectOption("comporder3","<%=request("comporder3")%>")</script>
      4\
      <select name="comporder4" id="comporder4" style="width:80px;">
          <option value="">默认</option>
          <option value="1">客户等级</option>
          <option value="2">登录次数</option>
          <option value="3">下次联系时间</option>
          <option value="4">申请时间</option>
          <option value="5">最后联系时间</option>
          <option value="6">注册时间</option>
          <option value="7">最近登陆时间</option>
          <option value="8">到期时间</option>
          <option value="9">门市部流量</option>
          <option value="10">挑入时间</option>
      </select><script>selectOption("comporder4","<%=request("comporder4")%>")</script>
      </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#f2f2f2">
      <select name="telcount" id="telcount" style="background-color:#FC0">
			   <option value="">联系次数</option>
			   <option value="5">&lt;-5次</option>
			   <option value="10">5&lt;-&gt;10次</option>
          </select>
          <script>selectOption("telcount","<%=request("telcount")%>")</script>
      <%if left(dotype,3)="vap" then%>
      <select name="companytype" id="companytype" style="width:120px;">
        <option value="">选择客户类型</option>
        <option value="1">8000大客户</option>
        <!--
        <option value="A">cs-A类客户</option>
        <option value="B">cs-B类客户</option>
        <option value="C">cs-C类客户</option>-->
        <option value="180">过期180天客户</option>
        <option value="baoliu">ICD重点保留客户</option>
        <option value="out90">cs过期90天掉公海的客户</option>
        <option value="out90-7">cs过期90天掉公海的客户(最近7天)</option>
        <option value="bisha">cs必杀期客户</option>
        <option value="gq90180">90-180过期客户</option>
      </select>
      <script>selectOption("companytype","<%=request("companytype")%>")</script>
      <%end if%>
      <%if dotype="all" then%>
	    公海范围(注册时间)
	      <select name="gonghaifw" id="gonghaifw" style="width:70px;">
	        <option value="">请选择</option>
            <option value="-2">2周内</option>
	        <option value="-6">6个月内</option>
	        <option value="6-12">6-12个月内</option>
            <option value="12-18">12-18个月内</option>
            <option value="18-24">18-24个月内</option>
            <option value="24-">二年前</option>
          </select>
          <script>selectOption("gonghaifw","<%=request("gonghaifw")%>")</script>
       <%
	   end if
	   %>
	      
	  <input name="ss" type="hidden" id="ss" value="1">
	 
<%if session("Partadmin")<>"0" and (dotype="allall" or dotype="MyContinueBm" or dotype="allbm" or dotype="bmzd" or dotype="all" or dotype="bmzd" or dotype="3daynodo" or (dotype="my" and session("userid")="10") or (dotype="today" and session("userid")="10") or (dotype="contact" and session("userid")="10") or (dotype="nocontact" and session("userid")="10") or (dotype="MyContinue" and session("userid")="10") or (dotype="myzst" and session("userid")="10") or (dotype="xm") or session("personid")="93" or session("personid")="227" or session("personid")="449") or session("userid")="10" or session("userid")="13" or lmaction="allbm" then%>
            <select name="doperson" class="button" id="doperson" style="width:150px;">
              <option value="" >请选择--</option>
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  sqlc=sqlc&" and closeflag=1"
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
          <script>selectOption("doperson","<%=request("doperson")%>")</script>		
            <%end if%>
            
            <%if session("personid")="93" or session("userid")="10" or left(dotype,6)="vappay" then%>
            <select name="padminuserPart" id="padminuserPart">
              <option value="">请选择区...</option>
              <%
			  sqlw="select * from cate_adminuserPart where code like '__' and closeflag=0 order by id desc"
		      set rsw=server.CreateObject("adodb.recordset")
			  rsw.open sqlw,conn,1,1
			  if not rsw.eof or not rsw.bof then
			  while not rsw.eof
			  %>
              <option value="<%=rsw("code")%>"><%=rsw("meno")%></option>
              <%
			  rsw.movenext
			  wend
			  end if
			  rsw.close
			  set rsw=nothing
			  %>
            </select>
            <script>selectOption("padminuserPart","<%=request("padminuserPart")%>")</script>
              <!--<input name="paichu" type="checkbox" id="paichu" value="1" <%if request("paichu")<>"" then response.Write("checked")%>>排除保留客户-->
          <%
			  Kfromdate=request("Kfromdate")
			  Ktodate=request("Ktodate")
			  %>
              广告开通时间<script language=javascript>createDatePicker("Kfromdate",true,"<%=Kfromdate%>",false,true,true,true)</script>&nbsp;到&nbsp;<script language=javascript>createDatePicker("Ktodate",true,"<%=Ktodate%>",false,true,true,true)</script>
			 <%end if%>
			 
<br>
          <%
	  pdtly=request("pdt_ly")
	  if doaction="exhibit" or doaction="laizhou" then%>
          <strong style="color:#ff0000">来源</strong>
          <%=cateMeno(conn,"cate_product_ly","pdt_ly",pdtly,"")%>
          <input name="sihai" type="checkbox" id="sihai" value="1" <%if request("sihai")<>"" then response.Write("checked")%>> 
          私海
          <%end if%>
        <%if dotype="dropTosea" then%>掉公海时间
        <select name="dropToseaTime" id="dropToseaTime">
          <option value="">请选择...</option>
          <option value="3">最近三天</option>
          <option value="7">最近一周</option>
          <option value="30">最近一个月</option>
        </select>
        <script>selectOption("dropToseaTime","<%=request("dropToseaTime")%>")</script>
        <%end if%>
        <input type="submit" name="Submit3" value="  搜 索  " class=button>
        <input name="gaohaisearchFlag" type="hidden" id="gaohaisearchFlag" value="1"> 
        <a href="/admin1/crmlocal/rizhi/salesHistory.asp?mpersonid=<%=session("personid")%>" target="_blank">我的操作日志</a>
        <%if session("personid")="93" or session("userid")="10" then%>
        <a href="aboutad/adselectcomp.asp" target="_blank">广告相关客户</a>
        <%end if%>
        </td>
    </tr>
  </form>
</table>
<%
if left(dotype,3)<>"vap" then
	response.Write("<font color=#0000CC style='font-size:14px'><b>提醒：</b></font><font color=#ff0000 style='font-size:14px'><b>公海现有<font color='#03C'>"&gonghaiallcount&"</font>个客户，公海客户默认为六个月内注册的客户，需要查找更多客户可以选择“公海范围”进行查找</b></font>")
end if
%>


<% If dotype="allzstGh" Then 
	subType=request("subType")%>
	<table width="90%" align="center" cellpadding="3" cellspacing="1" bgcolor="#ff6600" style="margin:2px 0;">
		<tr>
			<td align="center" bgcolor="<% If subType="allCurrentZstGh" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=allzstGh&subType=allCurrentZstGh">未过期再生通公海</a></td>
			<td align="center" bgcolor="<% If subType="allExpireZstGh" Then response.write "#ff6600" else response.write "#ffffff" end if %>"><a href="?dotype=allzstGh&subType=allExpireZstGh">已过期再生通公海</a></td>
			
		</tr>
	</table>
<% End If %>
<%
end if
sear=sear&"&searchflag="&request("searchflag")
'***********搜索/start
					sql=""
					
					if left(dotype,3)="vap" then
						crmpersonid="vpersonid"'-----------vap销售id号
					else
						crmpersonid="personid"
					end if
					'----------三个月内到期的再生通客户
					if dotype<>"allall" and dotype<>"大客户列表" And Left(dotype,3)<>"vap" then
						'sql=sql&" and not exists(select com_id from crm_3monthexpired_vipcomp where com_id=v_salescomp.com_id and com_id not in (select com_id from crm_bigcomp))" 
						'and com_id not in (172765,100707,55432,160086,321746,416370,115495,184187,258539,433243,35006,258458,69010,277271,107675,171753,247814,295396,477404,326482,55843)"
					end if
					if dotype="xjj" then
						sql=sql&" and com_id in (select com_id from v_groupcomid where personid=128) and com_id not in (select com_id from crm_assign where personid=128)"
						sear=sear&"&dotype="&request("dotype")
					end if
					if trim(request("com_email"))<>"" then
						sql=sql&" and com_email like '"&trim(request("com_email"))&"%'"
						sear=sear&"&com_email="&request("com_email")
					end if
					if trim(request("zyyw1"))<>"" then
						sql=sql&" and exists(select com_id from Crm_CompOtherInfo where com_id=v_salescomp.com_id and com_zyyw like '%"&trim(request("zyyw"))&"%')"
						sear=sear&"&zyyw="&request("zyyw")
					end if
					if trim(request("zyyw"))<>"" then
						sql=sql&" and com_productslist_en like '%"&trim(request("zyyw"))&"%'"
						sear=sear&"&zyyw="&request("zyyw")
					end if
					'---------8000大客户
					if request("companytype")="1" then
						sql=sql&" and EXISTS(select com_id from crm_bigcomp where com_id=v_salescomp.com_id and checked=1)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------cs-A类客户
					if request("companytype")="A" then
						sql=sql&" and EXISTS(select com_id from temp_vapcomp1 where com_id=v_salescomp.com_id)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------cs-B类客户
					if request("companytype")="B" then
						sql=sql&" and EXISTS(select com_id from temp_vapcomp2 where com_id=v_salescomp.com_id)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------cs-C类客户
					if request("companytype")="C" then
						sql=sql&" and EXISTS(select com_id from temp_vapcomp3 where com_id=v_salescomp.com_id)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------过期180天
					if request("companytype")="180" then
						sql=sql&" and (vip_dateto<='"&date-180&"' and vip_dateto>'1900-1-1') "
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------IDC重点保留客户
					if request("companytype")="baoliu" then
						sql=sql&" and EXISTS(select com_id from crm_zdbaoliucomp where com_id=v_salescomp.com_id)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------过期90天客户
					if request("companytype")="out90" then
						sql=sql&" and EXISTS(select com_id from temp_out90day where com_id=v_salescomp.com_id)"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------过期90天客户（最近7天）
					if request("companytype")="out90-7" then
						sql=sql&" and EXISTS(select com_id from temp_out90day where com_id=v_salescomp.com_id and fdate>'"&date-7&"')"
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------cs必杀期客户
					if request("companytype")="bisha" then
						sql=sql&" and (vip_dateto<='"&date+60&"' and vip_dateto>'"&date-90&"') "
						sear=sear&"&companytype="&request("companytype")
					end if
					'---------90-180过期客户
					if request("companytype")="gq90180" then
						sql=sql&" and (vip_dateto<'"&date-90&"' and vip_dateto>'"&date-180&"') "
						sear=sear&"&companytype="&request("companytype")
					end if
					if request("nfromdate")<>"" And request("ntodate")<>"" Then
						if left(dotype,3)="vap" then
							sql=sql&" and vcontactnext_time>'"&cdate(request("nfromdate"))&"' and  vcontactnext_time<'"&cdate(request("ntodate"))+1&"'"
						else
							sql=sql&" and contactnext_time>'"&cdate(request("nfromdate"))&"' and  contactnext_time<'"&cdate(request("ntodate"))+1&"'"
						end if
						sear=sear&"&nfromdate="&request("nfromdate")&"&ntodate="&request("ntodate")
					End if
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
					'注册时间
					if trim(request("Regfromdate"))<>"" then
						sql=sql&" and com_regtime>'"&cdate(request("Regfromdate"))&"'"
						sear=sear&"&Regfromdate="&request("Regfromdate")
					end if
					if trim(request("Regtodate"))<>"" then
						sql=sql&" and com_regtime<'"&cdate(request("Regtodate"))+1&"'"
						sear=sear&"&Regtodate="&request("Regtodate")
					end if
					'---------------end 
					if trim(request("fromdate"))<>"" then
						if left(dotype,3)="vap" then
							sql=sql&" and vlastTelDate>'"&cdate(request("fromdate"))&"'"
						else
							sql=sql&" and teldate>'"&cdate(request("fromdate"))&"'"
						end if
						sear=sear&"&fromdate="&request("fromdate")
					end if
					if trim(request("todate"))<>"" then
						if left(dotype,3)="vap" then
							sql=sql&" and vlastTelDate<'"&cdate(request("todate"))+1&"'"
						else
							sql=sql&" and teldate<'"&cdate(request("todate"))+1&"'"
						end if
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
						'sql=sql&" and ((com_mobile like '%"&trim(request("com_tel"))&"%' or (com_id in (select com_id from crm_personinfo where PersonMoblie like '%"&trim(request("com_tel"))&"%'))) or (com_tel like '%"&trim(request("com_tel"))&"%' or (com_id in (select com_id from crm_personinfo where PersonTel like '%"&trim(request("com_tel"))&"%'))))"
						sql=sql&" and exists(select com_id from temp_contactSearch where searchtext like '%"&trim(request("com_tel"))&"%' and com_id=v_salescomp.com_id)"
						sear=sear&"&com_tel="&request("com_tel")
					end if
					'-----联系次数
					if trim(request("telcount"))<>"" then
						if request("telcount")=5 then
							sql=sql&" and exists(select com_id from temp_telcount where com_id=v_salescomp.com_id and telcount<="&request("telcount")&")"
						elseif request("telcount")=10 then
							sql=sql&" and exists(select com_id from temp_telcount where com_id=v_salescomp.com_id and telcount<=10 and telcount>5)"
						end if
						sear=sear&"&telcount="&request("telcount")
					end if
					if trim(request("com_mobile"))<>"" then
						sql=sql&" and (com_mobile like '%"&trim(request("com_mobile"))&"%' or (com_id in (select com_id from crm_personinfo where PersonMoblie like '%"&trim(request("com_mobile"))&"%')))"
						sear=sear&"&com_mobile="&request("com_mobile")
					end if
					if trim(request("com_rank"))<>"" then
						if left(dotype,3)="vap" then
							sql=sql&" and vcom_rank='"&trim(request("com_rank"))&"'"
						else
							sql=sql&" and com_rank='"&trim(request("com_rank"))&"'"
						end if
						sear=sear&"&com_rank="&request("com_rank")
					end if
					if request("province1")<>"" then
						sql=sql&" and exists(select com_id from comp_provinceID where com_id=v_salescomp.com_id and province="&trim(request("province1"))&""
						sear=sear&"&province1="&request("province1")
						if request("city1")<>"" then
							sql=sql&" and city = "&trim(request("city1"))&""
							sear=sear&"&city1="&request("city1")
						end if
						if request("Garden")<>"" then
							sql=sql&" and Garden = "&trim(request("Garden"))&""
							sear=sear&"&Garden="&request("Garden")
						end if
						sql=sql&")"
					end if	
					if request("clscb")<>"" then
						'sql=sql&" and EXISTS (select com_id from Comp_ComTrade where com_id=v_salescomp.com_id and TradeID="&request("clscb")&")"
						sql=sql&" and com_keywords='"&request("clscb")&"'"
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
					'---------客户基本情况搜索
					if request("showitems")<>"" then
						if request("showitems")="offer" then
							sql=sql&" and exists(select com_id from temp_statCount where countOffer>0 and com_id=v_salescomp.com_id)"
						end if
						if request("showitems")="receive" then
							sql=sql&" and exists(select com_id from temp_statCount where countreceiveQuestion>0 and com_id=v_salescomp.com_id)"
						end if
						if request("showitems")="send" then
							sql=sql&" and exists(select com_id from temp_statCount where countsendQuestion>0 and com_id=v_salescomp.com_id)"
						end if
						sear=sear&"&showitems="&request("showitems")
					end if
					'----------------区搜索
					if request("padminuserPart")<>"" then
						'sql=sql&" and personid in (select id from users where userid in (select code from cate_adminuser where partID="&request("padminuserPart")&"))"
						
						sql=sql&" and "&crmpersonid&" in (select id from users where partid="&request("padminuserPart")&")"
						sear=sear&"&padminuserPart="&request("padminuserPart")
					end if
					'if request("paichu")="1" then
'						sql=sql&" and com_id not in (select com_id from temp_baoliucomp)"
'						sear=sear&"&paichu="&request("paichu")
'					end if
					if request("Kfromdate")<>"" then
						sql=sql&" and exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and fromdate>='"&request("Kfromdate")&"')"
						sear=sear&"&Kfromdate="&request("Kfromdate")
					end if
					if request("Ktodate")<>"" then
						'sql=sql&" and vip_datefrom<='"&request("Ktodate")&"'"
						sql=sql&" and exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and todate>='"&request("Ktodate")&"')"
						sear=sear&"&Ktodate="&request("Ktodate")
					end if
				'***********搜索/end
errtext=""				
if dotype<>"allall" then
	searchflag=1
else
	if request("ss")<>"" and sql<>"" then
	    '-----------------------begin
		'时间条件
		getcompany=0
		if formatdatetime(now,4)>"05:00" and formatdatetime(now,4)<"08:30" then
			getcompany=1
		end if
		if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:00" then
			getcompany=1
		end if
		if formatdatetime(now,4)>"17:30" and formatdatetime(now,4)<"22:00" then
			getcompany=1
		end if
		'----------------------end
		searchflag=1
		sear=sear&"&ss=1"
	else
		searchflag=0
		errtext="<br><font color=#ff0000>客户列表需通过搜索才能显示！</font>"
	end if
end if
week=weekday(now)
if dotype="all" or dotype="allzstGh" then
    nhour=Hour(now())
	nMinute=Minute(now())
	searchflag=0
    'if ywadminid="" or isnull(ywadminid) or ywadminid="0" then
		if formatdatetime(now,4)>"07:00" and formatdatetime(now,4)<"08:30" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:30" then
			searchflag=1
		end if
		if formatdatetime(now,4)>"17:30" and formatdatetime(now,4)<"24:00" then
			searchflag=1
		end if
    'else
		'searchflag=1
	'end if
	if searchflag=0 then
		errtext="<br> <font color=#ff0000>公海挑客户功能开放时间：中午：  12:00-13:30   晚上：   17:30-22:00</font>"
	end if
	if cint(wtime)=7 or cint(wtime)=1 then
		searchflag=1
	end if
	errtext="<br> <br> <font color=#ff0000>请到“ICD公海客户”中搜索客户。</font>"
	searchflag=0
end if

'------关闭再生通公海客户2010-4-8
if dotype="allzstGh" and session("personid")<>"93" then
   searchflag=0
   errtext="<br><br>  <font color=#ff0000>再生通公海已经关闭</font>"
end if
if session("userid")="10" or session("userid")="13" or session("personid")="449" and dotype<>"all" then
	searchflag=1
end if
if searchflag=1 then
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%
				
					'**************我的客户/start
					'sql=sql&" and not EXISTS(select com_id from crm_assign where com_id=v_salescomp.com_id and personid=14)"
					if dotype="my" and doaction<>"dropTosear" then
					    sql=sql&" and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp.com_id)"
					    if session("userid")="10" then
						    sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id)"
							sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp.com_id)"
						else
						    sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and personid="&session("personid")&" "
							'-------临时库
							'sql=sql&" and not exists(select com_id from crm_assignTemp where com_id=v_salescomp.com_id and personid="&session("personid")&")"
						end if
						sear=sear&"&dotype="&dotype
						cach=docach
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							end if
						sql=sql&"  "
					end if
					'---------------------------begin
					'不包含品牌通录入匹配客户
					'---------------------------
					if dotype<>"allcontinue" then
					'sql=sql&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2)"
					'sql=sql&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id )"
					
					'---------------------------end 
					
					'-----------------------------------begin
					'与留客户给新的销售人员
					'-----------------------------------
					'sql=sql&" and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id)"
					'-----------------------------------end
					end if
					'**************我的客户/end
					
					'****************我的共享客户 /start
					if dotype="myShare" then
						sql=sql&" and com_id in (select com_id from crm_comp_share)"
						sear=sear&"&dotype="&dotype
					end if
					'****************我的共享客户 /end
					
					
					'******************所有安排联系的客户/start
					if dotype="contact" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
						else
							sql=sql&" and personid='"&session("personid")&"' "
							sear=sear&"&doperson="&request("doperson")
						end if
						    sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
							sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time>'1990-1-1'"
							'--------------begin
							'不包含公海挑入的客户
							'sql=sql&" and Not EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&session("personid")&") and com_id=v_salescomp.com_id)"
							sql=sql&" and Not EXISTS(select com_id from comp_tel where com_id=v_salescomp.com_id"
							
							if request("doperson")<>"" then
								sql=sql&" and personid>"&request("doperson")&" and personid<"&request("doperson")&""
								'sear=sear&"&doperson="&request("doperson")
							else
								sql=sql&" and personid>"&session("personid")&" and personid<"&session("personid")&""
						    end if
							
							sql=sql&")"
							
							'--------------end 
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
					    sear=sear&"&dotype="&dotype
					end if
					'******************所有安排联系的客户/end
					'******************今天安排联系的客户/start
					if dotype="fromService" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and exists(select com_id from comp_postToResign where com_id=v_salescomp.com_id)"
							
						else
							sql=sql&" and exists(select com_id from comp_postToResign where com_id=v_salescomp.com_id)"
						end if
					    sear=sear&"&dotype="&dotype
					end if
					'******************今天安排联系的客户/end
					'******************今天安排联系的客户/start
					if dotype="today" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and ((contactnext_time>'"&date()&"' and contactnext_time<'"&date()+1&"') )"
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
						else
							sql=sql&" and personid="&session("personid")&" and ((contactnext_time>'"&date()&"' and contactnext_time<'"&date()+1&"')) "
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id)"
						end if
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						else
							'sql=sql&" and not (vip_check=1 and vipflag>1)"
						end if
					    sear=sear&"&dotype="&dotype
					end if
					'******************今天安排联系的客户/end
					'-----------------激活库客户
					if dotype="jihuo" then
						sql=sql&" and exists(select com_id from temp_activeComp where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/start
					if dotype="nocontact" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and not EXISTS (select com_id from comp_tel where com_id=v_salescomp.com_id"
							if request("doperson")<>"" then
								'sql=sql&" and personid="&request("doperson")&" "
							end if
							sql=sql&" ) "
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
						else
							sql=sql&" and not EXISTS(select com_id from comp_tel where com_id=v_salescomp.com_id)"
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and personid="&session("personid")&" "
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/end
					'******************公海挑入客户/start
					if dotype="outgonghai" then
						if session("userid")<>"10" then
							sql=sql&" and personid="&session("personid")&" "
						end if
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&" "
							sear=sear&"&doperson="&request("doperson")
						end if
						sql=sql&" and EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where telflag<4 "
						'sql=sql&" and teldate>'1900-1-1' and not EXISTS(select com_id from comp_tel where   com_id=v_salescomp.com_id"
						
						if request("doperson")<>"" then
							sql=sql&" and personid<>"&request("doperson")&" "
						end if
						if session("userid")<>"10" then
							sql=sql&" and personid<>"&session("personid")&""
						end if
						
						sql=sql&" ) and com_id=v_salescomp.com_id ) "
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
							sql=sql&" and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) "
							sql=sql&" and EXISTS(select com_id from Crm_Assign where com_id=v_salescomp.com_id and fdate<'"&date()-2&"')"
						else
							sql=sql&" and personid="&session("personid")&" "
							sql=sql&" and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) "
							sql=sql&" and EXISTS(select com_id from Crm_Assign where com_id=v_salescomp.com_id and fdate<'"&date()-2&"')"
						end if
					    sear=sear&"&dotype="&dotype
					end if
					'******************三天内未处理客户/end
					'******************我的重点客户/start
					if dotype="zhongdian" then
						sql=sql&" and personid="&session("personid")&""
						sql=sql&" and EXISTS(select com_id from Crm_Assign where Emphases=1 and com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'******************我的重点客户/end
					'******************部门所有客户/start
					if dotype="allbm" then
						if session("userid")="13" then 
							sql=sql&" and exists(select id from users where (userid in ("&ywadminid&") or id="&session("personid")&") and id=v_salescomp.personid and closeflag=1)"
						else
							sql=sql&" and exists(select id from users where userid="&session("userid")&" and id=v_salescomp.personid and closeflag=1)"
						end if
						
						if request("doperson")<>"" and not isnull(request("doperson")) then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")&""
						end if
						sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
						sear=sear&"&dotype="&dotype
					end if
					'******************部门所有客户/end
					'******************部门重点客户/start
					if dotype="bmzd" then
						if session("userid")="13" or session("userid")="10" then
							if request("doperson")<>"" and not isnull(request("doperson")) then
								sql=sql&" and personid="&request("doperson")&""
							else
									sql=sql&" and exists(select id from users where userid in ("&ywadminid&") and v_salescomp.personid=id and closeflag=1)"
							end if
							
						else
							if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
							else
								sql=sql&" and exists(select id from users where userid="&session("userid")&" and v_salescomp.personid=id and closeflag=1)"
							end if
						end if
						
						sql=sql&" and EXISTS(select com_id from Crm_Assign where Emphases=1 and com_id=v_salescomp.com_id"
						if request("action")="zhongdian" then
							sql=sql&" and Emphases_check=0"
						end if
						if request("action")="zhongdiansh" then
							sql=sql&" and Emphases_check=1"
						end if
						sql=sql&")"
						sear=sear&"&dotype="&dotype&"&doperson="&request("doperson")&"&action="&request("action")
					end if
					'******************部门重点客户/end
					'******************我的再生通客户/start
					if dotype="myzst" then
						if session("userid")="10" or session("personid")="93" or session("personid")="227"  then
						    if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							'sql=sql&" and vipflag=2 and vip_check=1"
						else
							sql=sql&" and personid='"&session("personid")&"'"
						end if
						if doaction="needResign" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>'"&date()&"'"
						end if
						sql=sql&" and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype&"&action="&doaction
					else
						if dotype<>"TodayContinue" and dotype<>"MyContinueBm" and dotype<>"ContactContinue" and dotype<>"dropTosea" and dotype<>"today" and doaction<>"allzst" and dotype<>"contact" and dotype<>"allall" and dotype<>"allcontinue" and dotype<>"allExpired" and request("subdotype")<>"continue" and dotype<>"myShare" and dotype<>"MyContinue" and dotype<>"NoContinue" and dotype<>"ZoContinue" and dotype<>"allZST" and dotype<>"fromService" and dotype<>"allzstGh" and dotype<>"AllContinue" and dotype<>"AutoTogonghai" and dotype<>"大客户列表" And Left(dotype,3)<>"vap" and dotype<>"CustomIn" then
							sql=sql&" and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id )"
						end if
					end if
					'-------大客户列表列表
					if dotype="大客户列表" then
						sql=sql&" and EXISTS(select com_id from crm_bigcomp where com_id=v_salescomp.com_id and checked=1) "
						sear=sear&"&dotype="&dotype
					end if
					
					'******************我的再生通客户/end
					'******************公海客户/start
					if dotype="all" then
							
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
							'--------不在关联客户里
							sql=sql&" and not EXISTS (select com_id from crm_complink where flag=0 and shflag=1 and com_id=v_salescomp.com_id) "
							'-----排除垃圾客户
							sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
							'*****排出私人库
							sql=sql&" and not exists(select com_id from crm_assign where com_id=v_salescomp.com_id)"
							
							'----排除预申请未审核的客户
							sql=sql&" and not exists(select com_id from crm_assign_request where com_id=v_salescomp.com_id and assignflag=0)"
							'----排除录入未审核的客户
							sql=sql&" and not exists(select com_id from crm_InsertCompWeb where saletype=1 and com_id=v_salescomp.com_id and Ccheck=0)"
							'----排除展会录入客户
							'sql=sql&" and not exists(select com_id from comp_exhibit where com_id=v_salescomp.com_id)"
							sql=sql&" and not exists(select com_id from temp_assign_comp where com_id=v_salescomp.com_id)"
							'sql=sql&" and not exists(select com_id from temp_assign_comp_zhudong where com_id=v_salescomp.com_id)"
							loclurl="commType"
							txtname="xzproductLy"
							MakeHtmltime=60000*60*60
							if ExistsTxt(loclurl,txtname,MakeHtmltime)<>0 then
								lycodep=creatText2008(loclurl,txtname,"",MakeHtmltime)
							end if
							if session("userid")="10" or session("userid")="13" then
							else
								if lycodep<>"" then
								sql=sql&" and not exists(select com_id from comp_comly where lyCode in ("&lycodep&") and com_id=v_salescomp.com_id)"
								end if
							end if
							
							if(request("aaa")="zst") then
								sql=sql&"and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
								sear=sear&"&aaa="&request("aaa")
							end if
								sear=sear&"&dotype="&dotype
					end if
					if doaction="gonghainocontact" then
						sql=sql&" and not EXISTS(select com_id from v_groupcomid where  com_id=v_salescomp.com_id)"
						sear=sear&"&action="&doaction
					end if
					if doaction="dropTosear" then
						sql=sql&" and exists(select com_id from crm_dropedInsea where com_id=v_salescomp.com_id and personid="&session("personid")&")"
						sear=sear&"&action="&doaction
					end if
					'******************公海客户/end
					'----------------搜索公海范围
					if dotype="all" and doaction<>"laizhou" then
						gonghaifw=request("gonghaifw")
						if gonghaifw<>"" then
							arrghfw=split(gonghaifw,"-")
							fromT=arrghfw(0)
							toT=arrghfw(1)
							if fromT<>"" and toT<>"" then
								sql=sql&" and com_regtime>'"&date()-cint(toT)*30&"'"
								sql=sql&" and com_regtime<'"&date()-cint(fromT)*30&"'"
							end if
							if fromT="" and toT<>"" then
								if toT="2" then
									sql=sql&" and com_regtime>'"&date()+1-cint(toT)*7&"'"
								else
									sql=sql&" and com_regtime>'"&date()+1-cint(toT)*30&"'"
								end if
							end if
							
							if fromT<>"" and toT="" then
								sql=sql&" and com_regtime<'"&date()+1-cint(fromT)*30&"'"
							end if
							
							sear=sear&"&gonghaifw="&gonghaifw
						else
							
							gaohaisearchFlag=request("gaohaisearchFlag")
							sear=sear&"&gaohaisearchFlag="&gaohaisearchFlag
							if gaohaisearchFlag<>"" then
							else
								'sql=sql&" and com_regtime>'"&date()+1-6*30&"'"
							end if
						end if
						'--------------9点之前新注册的客户不能挑
						if formatdatetime(now,4)<"09:00" then
							sql=sql&" and com_regtime<'"&date()&"'"
						end if
					end if
						'------------------搜索公海 end
					'-------自定义广告客户
					if request.QueryString("adid")<>"" then
						if request.QueryString("adid")="all" then
							sql=sql&" and exists(select com_id from temp_adcompselect where com_id=v_salescomp.com_id)"
						else
							sql=sql&" and exists(select com_id from temp_adcompselect where code="&request.QueryString("adid")&" and com_id=v_salescomp.com_id)"
						end if
						sear=sear&"&adid="&request.QueryString("adid")
					end if
					if request.QueryString("adgonghai")="1" then
						sql=sql&" and not exists(select com_id from crm_assign where com_id=v_salescomp.com_id)"
						sear=sear&"&adgonghai="&request.QueryString("adgonghai")
					end if
					if request.QueryString("adsihai")="1" then
						sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp.com_id)"
						sear=sear&"&adsihai="&request.QueryString("adsihai")
					end if
					if request.QueryString("zsttype")="1" then
						sql=sql&" and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id)"
						sear=sear&"&zsttype="&request.QueryString("zsttype")
					end if
					if request.QueryString("zsttype")="0" then
						sql=sql&" and not exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id)"
						sear=sear&"&zsttype="&request.QueryString("zsttype")
					end if
					'******************所有客户/start
					sql=sql&" and not exists(select com_id from temp_assign_comp where com_id=v_salescomp.com_id)"
					if dotype="allall" or dotype="vapallallcomp" then
					    if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						
						sear=sear&"&dotype="&dotype
					end if
						if request("zhuce")="zhudong" then
							sql=sql&" and (adminuser=0 or com_id in (select com_id from comp_logincount))"
							sear=sear&"&zhuce="&request("zhuce")
						end if
						if request("zhuce")="shouji" then
							sql=sql&" and (com_id not in (select com_id from comp_logincount))"
							sear=sear&"&zhuce="&request("zhuce")
						end if
						'if request("zhuce")="jihuo" then
'							sql=sql&" and com_id in (select com_id from Crm_Active_ActiveComp)"
'							sear=sear&"&zhuce="&request("zhuce")
'						end if
						
					'******************所有客户/end
					'******************废品池/start
					if dotype="xm" then
					        sql=sql&" and com_type=13 "
					        if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
					        sear=sear&"&dotype="&dotype
					end if
					'******************废品池/end
					'******************设备中心客户/star
				    if dotype="sbcomp" then
						response.Write("请刷新左边菜单！")
						response.End()
						sql=sql&" and EXISTS(select com_id from Sb_Com_Cls where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'******************设备中心客户/end
					'******************vip客户/star
				    if dotype="myvip" then
						sql=sql&" and EXISTS(select com_id from crm_assignVIP where com_id=v_salescomp.com_id and personid="&session("personid")&") "
						sql=sql&" and not exists(select com_id from Crm_Assign where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'******************vip客户/end
					'******************特殊意向客户/start
					if dotype="Especial" then
						sql=sql&" and EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					else
						if dotype<>"myShare" and dotype<>"allcontinue" then
							'sql=sql&" and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
						end if
					end if
					'******************特殊意向客户/end
					'******************新客户分配/start
					if dotype="fenpei" then
						sql=sql&" and com_regtime>='"&date()-10&"' and com_regtime<='"&date()&"' and com_id not in (select com_id from crm_assign) "
						sear=sear&"&dotype="&dotype
					end if
					'-----------------我的临时库 /star
					if dotype="myTempcomp" then
						sql=sql&" and EXISTS(select com_id from crm_assignTemp where com_id=v_salescomp.com_id "
						if session("userid")<>"10" then	
							sql=sql&" and personid="&session("personid")&""
						end if
						sql=sql&")"
						sear=sear&"&dotype="&dotype
					end if
					'-----------------我的临时库 /end
					
					if dotype<>"allall" and  request("ss")="" and dotype<>"myzst" then
						'if request("zhuce")="shouji" then
'							sql=sql&" and (com_id not in (select com_id from comp_logincount))"
'						elseif request("zhuce")="jihuo" then
'							sql=sql&" and (com_id in (select com_id from Crm_Active_ActiveComp))"
'						else
'							if dotype<>"myShare" and dotype<>"allExpired" then
'							'sql=sql&" and (com_id in (select com_id from comp_logincount))"
'							end if
'						end if
'						sear=sear&"&zhuce="&request("zhuce")
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
						sql=sql&" and com_id in (select com_id from comp_comly where 1=1 "
						if pdtly<>"" then
							sql=sql&" and lyCode like '"&pdtly&"%'"
							sear=sear&"&pdt_ly="&pdtly
						end if
						sql=sql&")"
						sear=sear&"&action="&doaction
					end if
					'-----------预备品牌通  2011-9-6
					if doaction="yubeippt" then
						sql=sql&" and exists(select property_id from crm_category_info where v_salescomp.com_id=property_id and property_value='10050001')"
						sear=sear&"&action="&doaction
					end if
					'-----------重点客户准备  2012-2-10
					if doaction="zhongdiankh" then
						sql=sql&" and exists(select property_id from crm_category_info where v_salescomp.com_id=property_id and property_value='10050002')"
						sear=sear&"&action="&doaction
					end if
					'-----------我的品牌通  2012-2-1
					if doaction="myppt" then
						sql=sql&" and exists(select com_id from comp_ppt where v_salescomp.com_id=com_id)"
						sear=sear&"&action="&doaction
					end if
					'******************会展客户/end
					'******************一星客户/start
					if doaction="yixing" then
						sql=sql&" and (vcom_rank is null or vcom_rank=1)"
						sear=sear&"&action="&doaction
					end if
					'******************一星客户/end
					'******************黄展客户/start
					if doaction="huangzhan" then
						sql=sql&" and com_id in (select com_id from advkeywords where typeid='11' "
						
						sql=sql&")"
						sear=sear&"&action="&doaction
					end if
					'******************黄展客户/end
					
					'******************标王客户/start
					if doaction="biaowang" then
						sql=sql&" and com_id in (select com_id from advkeywords where typeid='13' "
						
						sql=sql&")"
						sear=sear&"&action="&doaction
					end if
					'******************标王客户/end
					'******************独家广告/start
					if doaction="dujia" then
						sql=sql&" and com_id in (select com_id from advkeywords where typeid in(18,10,19,23,20,24,12,22,14,15) "
						
						sql=sql&")"
						sear=sear&"&action="&doaction
					end if
					'******************独家广告/end
					
					
					if doaction="laizhou" then
						sql=sql&" and com_id in (select com_id from comp_comly where com_id>0"
						if pdtly<>"" then
							sql=sql&" and lyCode like '"&pdtly&"%'"
							sear=sear&"&pdt_ly="&pdtly
						else
							sql=sql&" and lyCode in (select code from cate_product_ly where show=1)"
							sear=sear&"&pdt_ly="&pdtly
						end if
						sql=sql&")"
						if request("sihai")<>"" then
							sql=sql&" and personid<>'' and personid<>227 and personid<>93"
							sear=sear&"&sihai="&request("sihai")
						end if
						sear=sear&"&action="&doaction
					end if
					'--------冲突客户分配
					if doaction="conflict" then
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&" "
							sear=sear&"&doperson="&request("doperson")
						end if
						'sql=sql&" and EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
						sql=sql&" and (com_id in(select a.com_id from crm_assign a,crm_continue_assign b where a.com_id=v_salescomp.com_id and a.com_id=b.com_id and a.personid<>b.personid) or com_id in(select a.com_id from crm_assign a,crm_assignad b where a.com_id=v_salescomp.com_id and a.com_id=b.com_id and a.personid<>b.personid))"
						sear=sear&"&action="&doaction
					end if
					'----------------------
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
					'----------------9-11青岛再生汇客户
					if doaction="zsh" then
						sql=sql&" and com_id in(188112,188116,188118,188124,188138,188151,188155,188161,188176,188192,66944,24917,59029,85293,44430)"
						sear=sear&"&action="&doaction
					end if
					'----------25天内无效联系
					if  doaction="25nocontact" then
						sql=sql&" and not EXISTS(select com_id from comp_tel where com_id=v_salescomp.com_id and telflag=0 and TelDate>'"&date-25&"' and contacttype=13) and TelDate>'"&date-25&"'"
						sear=sear&"&action="&doaction
					end if
					'--------------30天内未联系客户
					if doaction="30dayNocontact" then
						sql=sql&" and DATEDIFF(DD, TelDate, GETDATE())>=30 and not exists(select com_id from crm_assign where fdate>'"&date()-3&"' and com_id=v_salescomp.com_id)"
						sear=sear&"&action="&doaction
					end if
					'--------------3天内未联系客户
					if doaction="3dayNocontact" then
						'sql=sql&" and TelDate<'"&date-4&"' and exists(select com_id from crm_assign where fdate>'"&date()-4&"' and fdate<'"&date()-3&"' and com_id=v_salescomp.com_id)"
						sql=sql&" and exists(select com_id from temp_3dayNocontact where com_id=v_salescomp.com_id and DATEDIFF(DD, teldate, GETDATE())>=1)"
						'sql=sql&" and exists(select com_id from crm_assign where DATEDIFF(DD, fdate, GETDATE()) = 3  and (not exists(select com_id from v_groupcomid where com_id=crm_assign.com_id and telid in (select id from comp_tel where personid=crm_assign.personid and contacttype=13)) or not exists(select com_id from comp_tel where com_id=crm_assign.com_id and contacttype=13)) and com_id=v_salescomp.com_id)"
						'sql=sql&" and exists(select com_id from crm_assign where DATEDIFF(DD, fdate, GETDATE()) > 3 and (not exists(select com_id from comp_tel where com_id=crm_assign.com_id and personid=crm_assign.personid) and not exists(select com_id from comp_telad where com_id=crm_assign.com_id and DATEDIFF(DD, Teldate, GETDATE())<3) or not exists(select com_id from comp_tel where com_id=crm_assign.com_id and not exists(select com_id from comp_telad where com_id=comp_tel.com_id and DATEDIFF(DD, Teldate, GETDATE())<3))) and com_id=v_salescomp.com_id)"
						sear=sear&"&action="&doaction
					end if
					'------------------------------
					if dotype="yuyao" then
						sql=sql&" and exists(select com_id from crm_yuyaocomp where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						if  doaction="yuyaoperson" then
							sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp.com_id) and personid<>341 and personid<>328"
							sear=sear&"&action="&doaction
						end if
					else
						'if doaction<>"exhibit" then
						'sql=sql&" and not exists(select com_id from crm_yuyaocomp where com_id=v_salescomp.com_id)"
						'end if
					end if
					if  doaction="yuyaoperson" and dotype<>"yuyao" then
						'sql=sql&" and com_add like '%余姚%'"
						sql=sql&" and exists(select com_id from crm_yuyaocomp where com_id=v_salescomp.com_id)"
						'sear=sear&"&dotype="&dotype
						sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp.com_id)"
						sear=sear&"&action="&doaction
					end if
					'------------今天登陆激活的客户
					if doaction="activeToday"  then
						sql=sql&" and exists(select com_id from crm_activeComp where com_id=v_salescomp.com_id)"
						sear=sear&"&action="&doaction
					end if
					'----------------9-11青岛再生汇客户
					
					'--------------------------begin
					'续签CRM
					if dotype="AllContinue" then
						sql=sql&" and exists(select com_id from v_compContinue where com_id=v_salescomp.com_id "
						if request("expired")<>"" then
							sql=sql&" and dateto<'"&date()+30*int(request("expired"))&"' and dateto>='"&date()&"'"
							sear=sear&"&expired="&request("expired")
						end if
						sql=sql&")"
						if session("userid")<>"10" and session("userid")<>"13" then
							sql=sql&" and personid="&session("personid")
						end if
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'end 续签
					
					'-----------------------------end 
					'-----------------------------begin
					'*续签 我的续签客户
					'-----------------------------
					
					if dotype="MyContinue" then
					'-----------------------------begin
						'续签 过期再生通
						if subType<>"" then
							if subType="ExpiredOneMonth" then
								sql=sql&" and exists(select com_id from v_compContinue where com_id=v_salescomp.com_id "
								
								sql=sql&" and dateto<'"&date()&"' and dateto>'"&date()-30&"')" '临时将时间限定为已过期30天内
							elseif subType="ExpiredExceedOneMonth" then
								sql=sql&" and exists(select com_id from v_compContinue where com_id=v_salescomp.com_id "
								
								sql=sql&" and dateto<='"&date()-30&"')" '临时将时间限定为已过期30天内
							elseif subType="noExpired" then
								sql=sql&" and exists(select com_id from v_compContinue where com_id=v_salescomp.com_id "
								
								sql=sql&" and dateto>='"&date()&"')" '临时将时间限定为已过期30天内
							elseif subType="before3month" then
								sql=sql&" and vip_dateto<='"&date()+90&"' and vip_dateto>'"&date&"'"
								'sql=sql&" and exists(select com_id from comp_Continue where com_id=v_salescomp.com_id "
								
								'sql=sql&" and (firstdatefrom<='2008-3-19' or continueDateFrom<='2008-3-19'))" '临时将时间限定为已过期30天内
							elseif subType="Expired" then
								'sql=sql&" and exists(select com_id from comp_Continue where com_id=v_salescomp.com_id "
								
								'sql=sql&" and (firstdatefrom>'2008-3-19' or continueDateFrom>'2008-3-19'))" '临时将时间限定为已过期30天内
								sql=sql&" and vip_dateto<'"&date&"'"
							elseif subType="payOne" then
								sql=sql&" and exists(select com_id from comp_continue where continueFlag=0 and com_id=v_salescomp.com_id)"
							
							elseif subType="payTwo" then
								sql=sql&" and exists(select com_id from comp_continue where continueFlag=1 and com_id=v_salescomp.com_id)"
							
							elseif subType="pay35" then
								sql=sql&" and exists(select com_id from comp_continue where continueFlag=0 and com_id=v_salescomp.com_id) and vip_datefrom>'"&date()-35&"'"
							elseif subType="nocontact" then
								sql=sql&" and not EXISTS(select com_id from comp_tel where personid="&session("personid")&" and com_id=v_salescomp.com_id)"
							end if
							
							sear=sear&"&subType="&subType
						end if
						
						
					    'sql=sql&" and exists(select com_id from crm_continue_assign where com_id=v_salescomp.com_id "
						sql=sql&" and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id )"
						if session("userid")="10" or session("userid")="13" then
							if session("userid")<>"10" then
								if request("padminuserPart")="" then
									sql=sql&" and exists(select id from users where id=v_salescomp.personid and partid="&adminmypartid&")"
								end if
							end if
						end if
						if session("userid")="10" or session("userid")="13" then
							if request("doperson")<>"" then
								sql=sql&" and personid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
							else
								if session("userid")<>"10" then 
									sql=sql&" and personid='"&session("personid")&"'"
								end if
							end if
						else
							sql=sql&" and personid='"&session("personid")&"'"
						end if
						
						'sql=sql&" and not exists(select com_id from crm_continue_Nodo where com_id=v_salescomp.com_id and Ntype=0)"
						
						'续签CRM于2008-11-4放上去,所以就认为在该日之后联系的即为已联系客户
						if request("alreadyContact")="1" then
							sql=sql&" and teldate>'2008-11-4'"
							sear=sear&"&alreadyContact="&request("alreadyContact")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'-----------部门所有客户
					if dotype="MyContinueBm" then
						sql=sql&" and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id )"
						if session("userid")="10" or session("userid")="13" then
							if session("userid")<>"10" then
								'sql=sql&" and exists(select id from users where id=v_salescomp.personid and userid in ("&ywadminid&"))"
								if request("padminuserPart")="" then
									sql=sql&" and exists(select id from users where id=v_salescomp.personid and partid="&adminmypartid&")"
								end if
							end if
						else
							sql=sql&" and personid="&session("personid")&""
						end if
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						'response.Write(sql)
						sear=sear&"&dotype="&dotype
					end if
					'response.Write(sql)
					'------------
					if dotype="AllContinue" then
						sql=sql&" and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id)"
						
					end if
					'-----------------------------end
					if dotype="unAssign" then
						sql=sql&" and not exists(select com_id from crm_assign where com_id=v_salescomp.com_id) and com_id in (select com_id from comp_continue where continueFlag=0)"
					    sear=sear&"&dotype="&dotype
					end if
					'---------------------------
					'*录入客户管理
					'-----------------------------
					if dotype="CustomIn" then
						sql=sql&" and exists(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id "
						if session("userid")="13" then 
							sql=sql&" and exists (select id from users where id=crm_InsertCompWeb.personid and userid in ("&ywadminid&") and closeflag=1)"
						else
							if session("userid")="10" then
							else
								sql=sql&" and exists(select id from users where id=crm_InsertCompWeb.personid and userid="&session("userid")&"  and closeflag=1)"
							end if
						end if
						sql=sql&")"
						sear=sear&"&dotype="&dotype
					end if
					'-----------------------------begin
					'*续签 不续签客户
					'-----------------------------
					if dotype="NoContinue" then
					    sql=sql&" and exists(select com_id from crm_continue_Nodo where com_id=v_salescomp.com_id and Ntype=0 "
						
						sql=sql&")"
						
						sear=sear&"&dotype="&dotype
					end if
					
					'-----------------------------end
					'-----------------------------begin
					'*续签 暂不续签客户
					'-----------------------------
					if dotype="ZoContinue" then
					    sql=sql&" and exists(select com_id from crm_continue_Nodo where com_id=v_salescomp.com_id and Ntype=1 "
						
						sql=sql&")"
						
						sear=sear&"&dotype="&dotype
					end if
					
					'-----------------------------end
					'-----------------------------begin
					'*续签 今天安排联系
					'-----------------------------
					if dotype="TodayContinue" then
					    sql=sql&" and contactnext_time>'"&date()&"' and contactnext_time<'"&date()+1&"' and exists(select com_id from crm_continue_assign where com_id=v_salescomp.com_id "
						if session("userid")<>"10" then
							sql=sql&" and personid="&session("personid")
						end if
						sql=sql&")"
						
						sear=sear&"&dotype="&dotype
					end if
					'-----------------------------end
					'-----------------------------begin
					'*续签 安排联系未联系
					'-----------------------------
					if dotype="ContactContinue" then
					    sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1' and exists(select com_id from crm_continue_assign where com_id=v_salescomp.com_id "
						if session("userid")<>"10" then
							sql=sql&" and personid="&session("personid")
						end if
						sql=sql&")"
						sql=sql&"and Not EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&session("personid")&") and com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'-----------------------------end
					
					'-----------------------------begin
					'*续签 所有再生通
					'-----------------------------
					if dotype="allZST" then
					    sql=sql&" and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id and com_check=1"
						
						sql=sql&")"
						
						sear=sear&"&dotype="&dotype
					end if
					'-----超过500掉入公海
					if dotype="dropTosea" then
						sql=sql&" and exists(select com_id from crm_dropedInsea where com_id=v_salescomp.com_id "
						
						dropToseaTime=request("dropToseaTime")
						if dropToseaTime<>"" and not isnull(dropToseaTime) then
							sql=sql&" and fdate>'"&date()-cint(dropToseaTime)&"'"
							sear=sear&"&dropToseaTime="&dropToseaTime
						end if
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")
							sear=sear&"&doperson="&request("doperson")
						end if
						if session("personid")<>"" and session("userid")<>"10" and session("personid")<>"93" and session("personid")<>"227" then
							sql=sql&" and personid="&session("personid")
						end if
						sql=sql&")"
						'if session("personid")="225" then
'							sql=sql&" and exists(select com_id from crm_assignHistory where sDetail='30天未联系入公海' and personid="&session("personid")&" and com_id=v_salescomp.com_id)"
'						end if
						sear=sear&"&dotype="&dotype
					end if
					'-----------------------------end
					'所有再生通的客户 /star
					if dotype="allzstGh" then
						'未过期再生通的客户 /star
						if subType<>"" then
							if subType="allCurrentZstGh" then
								sql=sql&"  and not EXISTS(select com_id from crm_assign where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp.com_id) and exists(select com_id from comp_zstinfo where com_check=1 and com_id=v_salescomp.com_id)"
								sear=sear&"&subType="&subType
							
							'未过期再生通的客户 /end
							'公海过期再生通的客户 /star
							elseif subType="allExpireZstGh" then
								sql=sql&"  and not EXISTS(select com_id from crm_assign where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp.com_id)  and exists(select com_id from comp_zstinfo where com_check=0 and com_id=v_salescomp.com_id)" '临时将时间限定为已过期30天内
								sear=sear&"&subType="&subType
							end if
						else
							sql=sql&"  and not EXISTS(select com_id from crm_assign where com_id=v_salescomp.com_id) and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id )"
						end if
						sear=sear&"&dotype="&dotype
					end if
					'公海再生通的客户 /end
					
					'公海过期再生通的客户 /end
					'******************所有正在联系的客户/
					if dotype="myyescontact" then
					    sqlm=""
						if session("userid")="10" then
								if request("doperson")<>"" then
									sqlm=sqlm&" and personid="&request("doperson")
									sear=sear&"&doperson="&request("doperson")
								end if
						else
								sqlm=sqlm&" and personid="&session("personid")&"  "
						end if
						sql=sql&sqlm
						sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
						sql=sql&" and EXISTS (select com_id from v_groupcomid where  com_id=v_salescomp.com_id and EXISTS (select id from comp_tel where id=v_groupcomid.telid "&sqlm&"))"
						sear=sear&"&dotype="&dotype
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
					end if
					'******************所有正在联系的客户/
					'******************所有未联系的客户/
					if dotype="mynocontact" then
						if session("userid")="10" then
								if request("doperson")<>"" then
									sql=sql&" and personid="&request("doperson")
									sear=sear&"&doperson="&request("doperson")
								end if
						else
								sql=sql&" and personid="&session("personid")&"  "
						end if
						        sql=sql&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
								sql=sql&" and not EXISTS(select com_id from v_groupcomid where  com_id=v_salescomp.com_id)"
						if request("subdotype")="continue" then
							sql=sql&" and vip_dateto<'"&date()+90&"' and vip_dateto>='"&date()&"'"
							sear=sear&"&subdotype="&request("subdotype")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'******************所有未联系的客户/
					
					'******************今日未联系的客户/
					if dotype="mynocontact_today" then
						sql=sql&" and personid="&request("personid")&" and not exists(select personid from v_salescomp where v_salescomp.personid is null and v_salescomp.com_id=com_id)) and contactnext_time>'"&cdate(request("datetime"))&"' and contactnext_time<'"&cdate(request("datetime"))+1&"' and teldate<'"&cdate(request("datetime"))&"'"
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
						sql=sql&" and not EXISTS(select com_id from Crm_Assign where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and not EXISTS(select com_id from crm_publiccomp where com_id=v_salescomp.com_id) "
						sear=sear&"&dotype="&dotype
					end if
					if dotype="tnoassign" then
						sql=sql&" and not EXISTS(select com_id from Crm_Assign where com_id=v_salescomp.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and com_regtime>'"&date()-1&"' and com_regtime<'"&date()&"'"
						sear=sear&"&dotype="&dotype
					end if
					if dotype="vipnoassign" then
						sql=sql&" and (vipflag=1 and vip_check=1) and not EXISTS (select com_id from crm_assign where com_id=v_salescomp.com_id) and not EXISTS (select com_id from crm_assignVIP where  com_id=v_salescomp.com_id) and not EXISTS (select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
						sear=sear&"&dotype="&dotype
					end if
					if dotype="agentCompany" then
						sql=sql&" and exists(select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'-----------续签客户自动掉入公海
					if dotype="AutoTogonghai" then
						sql=sql&" and com_id in (select com_id from crm_assignHistory where sdetail like '挑入或新注册3天未联系入公海' and fdate>'"&date-3&"') "
						if request("doperson")<>"" then
							sql=sql&" and exists(select com_id from crm_dropedInsea where v_salescomp.com_id=com_id and personid="&request("doperson")&")"
							sear=sear&"&doperson="&request("doperson")
						else
							'sql=sql&" and not exists(select com_id from crm_assign where v_salescomp.com_id=com_id)"
						end if
						sear=sear&"&dotype="&dotype
					end if
					'---------------一个月未联系，当天登录的客户
					if dotype="1MonthNocontant" then
						sql=sql&" and not EXISTS (select com_id from crm_assign where com_id=v_salescomp.com_id) and not EXISTS (select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id ) and datediff(dd,LastTelDate,getdate())>30 and com_type<13 and exists(select com_id from Comp_LoginCount where com_id=v_salescomp.com_id and datediff(dd,fdate,getdate())<=1) and not EXISTS (select com_id from comp_comLy where com_id=v_salescomp.com_id) and not exists(select com_id from comp_comType where com_id=v_salescomp.com_id and comtype=10)"
						sear=sear&"&dotype="&dotype
					end if
                    '----------------------------曾经是4星/ begin
					cach=request("cach")
					if cach="h4star" then
						sql=sql&" and EXISTS (select com_id from comp_tel where com_rank>=4 and com_rank<5 and v_salescomp.com_id=com_id"
						sql=sql&" and telflag<4)"
						sear=sear&"&cach="&cach
					end if
					if cach="h5star" then
						sql=sql&" and EXISTS(select com_id from comp_tel where com_rank>=5 and v_salescomp.com_id=com_id"
						sql=sql&" and telflag<4)"
						sear=sear&"&cach="&cach
					end if
					
					if cach="vh4star" then
						sql=sql&" and EXISTS (select com_id from comp_tel where com_rank>=4 and com_rank<5 and v_salescomp.com_id=com_id"
						sql=sql&" and telflag=4)"
						sear=sear&"&cach="&cach
					end if
					if cach="vh5star" then
						sql=sql&" and EXISTS(select com_id from comp_tel where com_rank>=5 and v_salescomp.com_id=com_id"
						sql=sql&" and telflag=4)"
						sear=sear&"&cach="&cach
					end if
					'--------未审核转四星客户
					if dotype="changeto4star" then
						sql=sql&" and exists(select com_id from crm_To4star where checked=0 and v_salescomp.com_id=com_id)"
						if session("userid")="10" or session("userid")="13" then
							if session("userid")<>"10" then
								'sql=sql&" and exists(select id from users where id=v_salescomp.personid and userid in ("&ywadminid&"))"
								if request("padminuserPart")="" then
									sql=sql&" and exists(select id from users where id=v_salescomp.personid and partid="&adminmypartid&")"
								end if
							end if
						else
							sql=sql&" and personid="&session("personid")&""
						end if
						if request("doperson")<>"" then
							sql=sql&" and personid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						sear=sear&"&dotype="&dotype
					end if
					'--------未审核转五星客户
					if dotype="changeto5star" then
						sql=sql&" and exists(select com_id from crm_To5star where checked=0 and v_salescomp.com_id=com_id)"
						sear=sear&"&dotype="&dotype
					end if
					'----------------------------曾经是4星/ end
					'---------------新人无权限续签客户
					if xuqianFlag="0" and left(session("userid"),2)="13" then
						if dotype="MyContinue" or dotype="allzstGh" or dotype="TodayContinue" or dotype="AllContinue" then
							response.Write("没有权限操作此功能！")
							response.End()
						end if
					end if
					'---------------------------VAP客户管理 / begin
					'小广告客户
					if dotype="vapotheradComp" then
						sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money<2000)"
						sql=sql&")"
						sear=sear&"&dotype="&dotype
					end if
					
					'----黄页
					if doaction="huangye" then
						sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and paykind='黄页' ))"
						sear=sear&"&action="&doaction
					end if
					'----报价
					if doaction="baojia" then
						sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and paykind='报价' ))"
						sear=sear&"&action="&doaction
					end if
					'----短期广告
					if doaction="dqguanggao" then
						sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and DATEDIFF(DD, fromDate, todate)<12 and DATEDIFF(DD, fromDate, todate)>=1))"
						sear=sear&"&action="&doaction
					end if
						
					
					if dotype="vapcomp" or dotype="vappaycomp" or dotype="vapallcomp" then
						
							sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id)"
						if lmaction<>"kaitongdan" and lmaction<>"seo" then
							sql=sql&" and not exists(select com_id from crm_openConfirm where v_salescomp.com_id=com_id and payTime>='2011-11-15' and assignflag=0)"
						end if
						'--------排除idc再生通保留客户
						'sql=sql&" and not exists(select com_id from temp_baoliucomp where com_id=v_salescomp.com_id)"
						'-------新签VAP客户
						if dotype="vapcomp" then
							sql=sql&" and not exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id)"
						elseif dotype="vappaycomp" then
							sql=sql&" and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id)"
						else
							
						end if
						
						'=----------2000-8000的客户
						if lmaction="2-8pay" then
							sql=sql&" and exists(select com_id from temp_vap_zhuda where com_id=v_salescomp.com_id)"
							'sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money>2000 and money<8000)"
							'sql=sql&" or exists(select com_id from comp_Continue where v_salescomp.com_id=com_id and money>2000 and money<8000))"
						end if
						'=----------广告客户-必杀期
						if lmaction="adbishaqi" then
							sql=sql&" and exists(select com_id from temp_vap_zhuda where com_id=v_salescomp.com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 12 and DATEDIFF(MM, fromDate, GETDATE()) >= 10))"
						end if
						
						'=----------广告客户-黄金客户
						if lmaction="adhuangjin" then
							sql=sql&" and exists(select com_id from temp_vap_zhuda where com_id=v_salescomp.com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2))"
						end if
						'=----------广告客户-新客户
						if lmaction="adnewcustomer" then
							sql=sql&" and exists(select com_id from temp_vap_zhuda where com_id=v_salescomp.com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 1 and DATEDIFF(MM, fromDate, GETDATE()) >= 0))"
						end if
						'=----------广告客户-过期
						if lmaction="adguoqi" then
							sql=sql&" and exists(select com_id from temp_vap_zhuda where com_id=v_salescomp.com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(DD, toDate, GETDATE()) > 0 ))"
						end if
						'=----------国外客户
						if lmaction="guowaicomp" then
							sql=sql&" and  com_ctr_id>1"
						end if
						'=----------大客户
						if lmaction="dakehu" then
							sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money>=8000)"
							sql=sql&")"
						end if
						'=----------品牌通
						if lmaction="allppt" then
							sql=sql&" and exists(select com_id from comp_ppt where v_salescomp.com_id=com_id)"
						end if
						'=----------品牌通
						if lmaction="zstnotad" then
							sql=sql&" and not exists(select com_id from advKeywords where v_salescomp.com_id=com_id)"
						end if
						'-----------过期再生通
						
						if lmaction="zstguoqi" then
							sql=sql&" and exists(select com_id from comp_zstinfo where v_salescomp.com_id=com_id and com_check=0 and vip_dateto<'"&date()&"')"
						end if
						'-------------------
						'---------①今天安排联系的客户
						if lmaction="today" then
							'sql=sql&" and exists(select com_id from Crm_AssignVap where v_salescomp.com_id=com_id"
							'sql=sql&")"
							if session("userid")="10" then
								if request("doperson")<>"" then
									sql=sql&" and vpersonid="&request("doperson")&" "
									sear=sear&"&doperson="&request("doperson")
								end if
								sql=sql&" and ((vcontactnext_time>'"&date()&"' and vcontactnext_time<'"&date()+1&"') )"
							else
								sql=sql&" and vpersonid="&session("personid")&" and ((vcontactnext_time>'"&date()&"' and vcontactnext_time<'"&date()+1&"')) "
							end if
						end if
						'---------------②新分配客户
						if lmaction="newfenpai" then
							if session("userid")="10" then
								if request("doperson")<>"" then
									sql=sql&" and vpersonid="&request("doperson")&""
									sear=sear&"&doperson="&request("doperson")
								end if
								sql=sql&" and not EXISTS (select com_id from comp_tel where com_id=v_salescomp.com_id"
								sql=sql&" and telflag=4) "
							else
								sql=sql&" and not EXISTS(select com_id from comp_tel where com_id=v_salescomp.com_id and telflag=4)"
								sql=sql&" and vpersonid="&session("personid")&""
							end if
							sql=sql&" and exists(select com_id from crm_Assign_vapNew where com_id=v_salescomp.com_id )"
						end if
						'******************③公海挑入(未联系)/start
						if lmaction="nocontact" then
							if session("userid")<>"10" then
								sql=sql&" and vpersonid="&session("personid")&" "
							end if
							if request("doperson")<>"" then
								sql=sql&" and vpersonid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
							end if
							
							sql=sql&" and teldate>'1900-1-1' and not EXISTS(select com_id from comp_tel where   com_id=v_salescomp.com_id"
							
							if session("userid")<>"10" then
								sql=sql&" and personid="&session("personid")&""
							end if
							
							sql=sql&" and telflag=4)"
						end if
						'******************③公海挑入(未联系)/end
						'******************④跟丢客户/start
						if lmaction="outcontact" then
							if session("userid")="10" then
								if request("doperson")<>"" then
									sql=sql&" and vpersonid="&request("doperson")&""
								end if
							else
								sql=sql&" and vpersonid='"&session("personid")&"' "
							end if
							sear=sear&"&doperson="&request("doperson")
								sql=sql&" and vcontactnext_time<'"&date()&"' and vcontactnext_time>'1990-1-1'"
								'--------------begin
								'不包含公海挑入的客户
								'sql=sql&" and Not EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&session("personid")&") and com_id=v_salescomp.com_id)"
								sql=sql&" and Not EXISTS(select com_id from comp_tel where com_id=v_salescomp.com_id"
								
								if request("doperson")<>"" then
									sql=sql&" and personid>"&request("doperson")&" and personid<"&request("doperson")&""
									'sear=sear&"&doperson="&request("doperson")
								else
									sql=sql&" and personid>"&session("personid")&" and personid<"&session("personid")&""
								end if
								sql=sql&")"
						end if
						'******************④跟丢客户/end
						'---------我的所有客户
						if lmaction="mycomp" then
							if session("userid")="10" then
								if request("doperson")<>"" then
									sql=sql&" and vpersonid="&request("doperson")&""
									sear=sear&"&doperson="&request("doperson")
								end if
								sql=sql&" and exists(select com_id from crm_assignvap where com_id=v_salescomp.com_id)"
							else
								sql=sql&" and vpersonid='"&session("personid")&"' "
								sear=sear&"&doperson="&request("doperson")
							end if
						end if
						'******************我的重点客户/start
						if lmaction="zhongdian" then
							sql=sql&" and vpersonid="&session("personid")&""
							sql=sql&" and EXISTS(select com_id from Crm_Assignvap where Emphases=1 and com_id=v_salescomp.com_id)"
						end if
						'-----------已经填了开通单的客户
						if lmaction="kaitongdan" then
							sql=sql&" and exists(select com_id from crm_openConfirm where v_salescomp.com_id=com_id and payTime>='2011-11-15' and assignflag=0)"
							sql=sql&" and not EXISTS(select com_id from Crm_Assignvap where com_id=v_salescomp.com_id)"
							'sql=sql&" and not EXISTS (select com_id from crm_publiccomp_vap where com_id=v_salescomp.com_id)"
						end if
						
						'******************我的重点客户/end
						'------------------
						'-------------一个月内新签客户
						if lmaction="1mounthvip" then
							sql=sql&" and vip_datefrom>='"&date-30&"' and vip_datefrom <'"&date&"' and not exists(select com_id from crm_assignvap where com_id=v_salescomp.com_id)"
						end if
						'******************公海客户/start
						if lmaction="gonghai" then
							'sql=sql&" and vpersonid="&session("personid")&""
							sql=sql&" and not EXISTS(select com_id from Crm_Assignvap where com_id=v_salescomp.com_id)"
						end if
						'******************死海客户/start
						if lmaction="nozengzhi" then
							sql=sql&" and EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
						end if
						'----------排除死海的客户
						if lmaction<>"nozengzhi" and lmaction<>"seo" then
							sql=sql&" and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
						end if
						'******************部门所有客户/start
						if lmaction="allbm" then
							sql=sql&" and exists(select com_id from comp_vapinfo where com_id=v_salescomp.com_id)"
							if session("userid")="13" then 
								sql=sql&" and exists(select id from users where (userid in ("&ywadminid&") or id="&session("personid")&") and id=v_salescomp.vpersonid and closeflag=1)"
							else
								sql=sql&" and exists(select id from users where userid="&session("userid")&" and id=v_salescomp.vpersonid and closeflag=1)"
							end if
							
							if request("doperson")<>"" and not isnull(request("doperson")) then
								sql=sql&" and vpersonid="&request("doperson")&""
								'sear=sear&"&doperson="&request("doperson")&""
							end if
						end if
						if request("doperson")<>"" then
							sql=sql&" and vpersonid="&request("doperson")&""
							sear=sear&"&doperson="&request("doperson")
						end if
						'******************部门所有客户/end
						if request("companytype")="bisha" or request("companytype")="gq90180"  then
							if session("userid")="13" or session("userid")="10" then
							else
								if ywadminid="" or isnull(ywadminid) then
									sql=sql&" and vpersonid="&session("personid")&""
								end if
							end if
						end if
						'******************部门重点客户/start
						if lmaction="bmzd" then
							if session("userid")="13" or session("userid")="10" then
								if request("doperson")<>"" and not isnull(request("doperson")) then
									sql=sql&" and vpersonid="&request("doperson")&""
								else
									sql=sql&" and exists(select id from users where userid in ("&ywadminid&") and v_salescomp.vpersonid=id and closeflag=1)"
								end if
							else
								if request("doperson")<>"" then
									sql=sql&" and vpersonid="&request("doperson")&""
								else
									sql=sql&" and exists(select id from users where userid="&session("userid")&" and v_salescomp.vpersonid=id and closeflag=1)"
								end if
							end if
							
							sql=sql&" and EXISTS(select com_id from Crm_Assignvap where Emphases=1 and com_id=v_salescomp.com_id"
							if request("action")="zhongdian" then
								sql=sql&" and Emphases_check=0"
							end if
							if request("action")="zhongdiansh" then
								sql=sql&" and Emphases_check=1"
							end if
							sql=sql&")"
							sear=sear&"&doperson="&request("doperson")&"&action="&request("action")
						end if
						if lmactoin="zhongdian" then
							sql=sql&" and EXISTS(select com_id from Crm_Assignvap where Emphases=1 and com_id=v_salescomp.com_id)"
						end if 
						'SEO会员客户
						if lmaction="seo" then
							sql=sql&" and EXISTS(select company_id from company_service where company_id=v_salescomp.com_id and crm_service_code='10001002' and apply_status='1')"
						end if 
						'******************部门重点客户/end
						'---------------------------
						'*录入客户管理
						'-----------------------------
						if lmaction="regfromcrm" then
							sql=sql&" and exists(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id "
							if session("userid")="13" then 
								sql=sql&" and exists (select id from users where id=crm_InsertCompWeb.personid and userid in ("&ywadminid&") and closeflag=1)"
							else
								if session("userid")="10" then
								else
									sql=sql&" and exists(select id from users where id=crm_InsertCompWeb.personid and userid="&session("userid")&"  and closeflag=1)"
								end if
							end if
							sql=sql&")"
						end if
						'---------储备公海
						if lmaction="cbgonghai" then
							sql=sql&" and EXISTS(select com_id from crm_gonghai_cb where gtype='vap' and com_id=v_salescomp.com_id)"
						end if
						'----------废品池客户
						if lmaction="laji" then
							sql=sql&" and com_type=13 "
						end if
						sear=sear&"&lmaction="&lmaction
						sear=sear&"&dotype="&dotype
					end if
					function getordervalue(comporder)
						select case comporder
							case "1"
								if left(dotype,3)="vap" then
									getordervalue="vcom_rank"
								else
									getordervalue="com_rank"
								end if
							case "2"
								getordervalue="logincount"
							case "3"
								if left(dotype,3)="vap" then
									getordervalue="vcontactnext_time"
								else
									getordervalue="contactnext_time"
								end if
							case "4"
								getordervalue="vip_date"
							case "5"
								getordervalue="teldate"
							case "6"
								getordervalue="com_regtime"
							case "7"
								getordervalue="lastlogintime"
							case "8"
								getordervalue="vip_dateto"
								
							case "9"
								getordervalue="shopviewedcount"
							case "10"
								getordervalue="fdate"
						end select
					end function
					'---------------------------VAP客户管理 / end
					
					sqlorder=" order by "
				    ascdesc=request("ascdesc")
					if duochangoder="" then
						if request("comporder")<>"" then
							sqlorder=sqlorder&" "&getordervalue(request("comporder"))&" "&ascdesc
							sqlorder=sqlorder&",com_id desc"
						else
							sqlorder=sqlorder&" com_id desc"
						end if
					else
						comporder1=request("comporder1")
						comporder2=request("comporder2")
						comporder3=request("comporder3")
						comporder4=request("comporder4")
						if comporder1<>"" then
							sqlorder=sqlorder&" "&getordervalue(comporder1)&" "&ascdesc&","
							sear=sear&"&comporder1="&request("comporder1")
						end if
						if comporder2<>"" then
							sqlorder=sqlorder&" "&getordervalue(comporder2)&" "&ascdesc&","
							sear=sear&"&comporder2="&request("comporder2")
						end if
						if comporder3<>"" then
							sqlorder=sqlorder&" "&getordervalue(comporder3)&" "&ascdesc&","
							sear=sear&"&comporder3="&request("comporder3")
						end if
						if comporder4<>"" then
							sqlorder=sqlorder&" "&getordervalue(comporder4)&" "&ascdesc&","
							sear=sear&"&comporder4="&request("comporder4")
						end if
						sqlorder=sqlorder&" com_id desc"
					end if
				   
					if session("userid")="10" then
						response.write(sql&sqlorder)
					end if
                    sear=sear&"&ascdesc="&request("ascdesc")
					sear=sear&"&comporder="&request("comporder")
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "com_tel,com_mobile,com_email,com_province,com_id,com_name,com_pass,vip_date,vip_datefrom,vipflag,viptype,vip_check,contactnext_time,vcontactnext_time,vcom_rank,com_rank,com_regtime,viewcount,logincount,lastlogintime,lastteldate,teldate,vip_dateto,adminuser,com_productslist_en,com_contactperson,com_desi,fdate,vlastteldate,shopviewedcount,vfdate"
					 .FROMTbl = "v_salescomp"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql
				     .keyFld  = "com_id"    '不可缺少
				     .pageSize= 10
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=int(total/10)+1
				   %>
	</td>
  </tr>
</table>
<%if left(dotype,3)<>"vap" then%>
<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="22" align="center"  <%if request("cach")="h4star" then response.Write("bgcolor=""#CCCCCC""")%>><a href="?cach=h4star&<%=replace(replace(sear,"&cach=h4star",""),"&cach=h5star","")%>">曾是4星</a></td>
    <td height="22" align="center" <%if request("cach")="h5star" then response.Write("bgcolor=""#CCCCCC""")%>><a href="?cach=h5star&<%=replace(replace(sear,"&cach=h5star",""),"&cach=h4star","")%>">曾是5星</a></td>
  </tr>
</table>
<%else%>
<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="22" align="center"  <%if request("cach")="vh4star" then response.Write("bgcolor=""#CCCCCC""")%>><a href="?cach=vh4star&<%=replace(replace(sear,"&cach=vh4star",""),"&cach=vh5star","")%>">曾是4星</a></td>
    <td height="22" align="center" <%if request("cach")="vh5star" then response.Write("bgcolor=""#CCCCCC""")%>><a href="?cach=vh5star&<%=replace(replace(sear,"&cach=vh5star",""),"&cach=vh4star","")%>">曾是5星</a></td>
  </tr>
</table>
<%end if%>
<%if request("com_rank")="5" then%>
<table width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#666666">
  <tr>
  	<%
	  sqlcate="select meno,code from crm_category where code like '1000____'"
	  set rscate=conn.execute(sqlcate)
	  if not rscate.eof or not rscate.bof then
	  i=0
	  while not rscate.eof
	%>
    <td bgcolor="#FFFFFF"><a href="?dotype=<%=dotype%>&com_rank=<%=request("com_rank")%>&reson_typeid=<%=rscate("code")%>"><%=rscate("meno")%></a></td>
    <%
	  i=i+1
	  rscate.movenext
	  wend
	  end if
	  rscate.close
	  set rscate=nothing
	  %>
  </tr>
</table>
<%end if%>
<%
if left(dotype,3)<>"vap" then
	if dotype="MyContinue" then
	sqlp="select count(0) from comp_zstinfo where com_id in (select com_id from comp_info where vip_datefrom>'"&date-35&"') and com_id in (select com_id from Crm_Assign where personid="&session("personid")&")"
	set rsp=conn.execute(sqlp)
	count35=rsp(0)
	sqlp="select count(0) from v_salescomp where not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select com_id from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) and TelDate<='"&date-25&"' and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id ) and personid="&session("personid")&""
	'response.Write(sqlp)
	set rsp=conn.execute(sqlp)
	count25=rsp(0)
	rsp.close
	set rsp=nothing
%>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#FF6600">
  <tr>
    <td bgcolor="#FFFFCC">
	<b>
	<%
	if xuqianFlag="1" then
		if cint(total-count35)>100 then
			response.Write("您的库里已经有“"&total&"”个客户，已经超过100个限制，剩余客户24小时后将自动掉入公海，请及时处理")
		end if
	else
		if cint(total-count35)>35 then
			response.Write("您的库里已经有“"&total&"”个客户，已经超过35个限制，剩余客户24小时后将自动掉入公海，请及时处理")
		end if
	end if
	response.Write("<br>你有“"&count25&"”个25天未联系的客户")
	%></b>
	</td>
  </tr>
</table>
<%
	end if
%>

<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#FF6600">
  <tr>
    <td bgcolor="#FFFFCC">
	<b>请注意整理好自己的客户！<br>1、<font color="#FF0000">30天未有联系</font>的客户将自动掉入公海(不包括3天内新注册或公海挑入的客户)。<br>2、新注册或公海挑入的客户<font color="#FF0000">3天内</font>必须联系，否则也将掉入公海</b>
	</td>
  </tr>
</table>
<%
end if
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" class=ee id=ListTable border="0" cellpadding="2" cellspacing="0">
              <form action="crm_allcomp_list.asp" method="post" name=form1>
                <tr class="topline"> 
                  <td width="4" bgcolor="#DFEBFF"><span class="STYLE2"></span></td>
                  <td nowrap bgcolor="#DFEBFF">&nbsp;</td>
                  <td nowrap bgcolor="#DFEBFF">客户<br>
                  类型</td>
                 

                  <td bgcolor="#DFEBFF"><span class="STYLE2">中文名</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">客户<br>
等级</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">Email</span></td>

                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">最近登录</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">登录<br>
次数</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">注册时间</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">联系人</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">公司电话</td>
                  <td class=sort id=td_address nowrap bgcolor="#DFEBFF">手机</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>省份</td>
                 
                  
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>最后联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>下次联系时间</td>
                  <td nowrap bgcolor="#DFEBFF" class=sort id=td_address>确认单</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">查看<br>
                  信息</span></td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">门市部<br>
                    流量</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">申请时间</td>
                 
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>到期时间</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">待开通</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">挑入时间</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>录入者</td>
                  <%'if dotype="大客户列表" or left(dotype,3)="vap" then%>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">服务者</td>
                  
                  <%'end if%>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">VAP<br>
                    销售人员</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">SMS<br>
所有者</td>
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">再生通<br>
                    销售</td>
                </tr>
                <%
				 if not rs.eof or not rs.bof then 
				 i=0                                                                      
				Do While Not rs.EOF 
				com_id=rs("com_id")
				comidList=comidList&com_id&","
				if cdate(rs("com_regtime"))>date()-1 and cdate(rs("com_regtime"))<date() then
					cbg="#CCFFCC"
				else
					lastlogintime=rs("lastlogintime")
					if lastlogintime<>"" then
						if cdate(lastlogintime)>=date()-3 then
							cbg="#FFDFD0"
						else
							cbg="#ffffff"
						end if
					else
						cbg="#ffffff"
					end if
				end if
				%>
                <tr style="cursor:hand" bgcolor="<%=cbg%>"> 
                  <td height="24">
				  <input name="cbb<%=i%>" type="checkbox" title="<%response.Write(rs("com_email"))%>" value="<%response.Write(rs("com_id"))%>"></td>
                  <td nowrap><a href="/admin1/crmlocal/rizhi/salesHistory.asp?com_id=<%=rs("com_id")%>&dotype=<%=dotype%>" target="_blank">日志</a></td>
                  
				  <SPAN onmouseover=DoHL(); onClick="DoSL();" onDblClick="javascript:DoSL();javascript:oow('../crmlocal/crm_cominfoedit.asp?idprod=<%response.Write(rs("com_id"))%>&dotype=<%response.Write(dotype)%>&lmcode=<%=lmcode%>');" onmouseout=DoLL();>
				  <td nowrap>
				  
                  <%
				  comLx=""
				  comLxzd=""
				  sqlzd="select com_id,Emphases_check from Crm_Assign where Emphases=1 and com_id="&rs("com_id")
				  set rszd=conn.execute(sqlzd)
				  if not rszd.eof or not rszd.bof then
				  	comLxzd="<font color=#ff0000>重点"
					if rszd("Emphases_check")="0" then
					comLxzd=comLxzd&"(未审)"
					end if
					comLxzd=comLxzd&"</font>"
				  end if
				  rszd.close
				  set rszd=nothing
				  if rs("vipflag")=0 and rs("viptype")=0 then
				  	comLx="注册"
				  end if
				  if (rs("viptype")<>0 and rs("vipflag")<>2) or (rs("viptype")<>0 and rs("vip_check")=0) then
				  	comLx="注册"
				  end if
				  if rs("vipflag")=2 and rs("vip_check")=1 then
				  	comLx="<font color=#009900>再生通</font>"
				  end if
				  viprequest=0
				  sql0="select com_fqr,viprequest from comp_info where com_id="&rs("com_id")
				  set rs0=conn.execute(sql0)
				  if not(rs0.eof and rs0.bof) then
				  	if rs0(0)="1" then
				  		comLx="<font color=#ff9900>发起人</font>"
					end if
					viprequest=rs0(1)
				  end if
				  rs0.close
				  set rs0=nothing
				  '---------------判断注册来源
				  sqlL="select id,RegFrom from comp_regFrom where com_id="&rs("com_id")&" and RegFrom>19"
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	regLyid=rsL(1)
				  	comLx="<font color=#CC9900>"&showregFrom(regLyid)&"</font>"
				  end if
				  rsL.close
				  set rsL=nothing
				   '---------------判断其他网站是否注册
				  sqlL="select RegFromid from comp_snatchhaveExists where com_id="&rs("com_id")&""
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	regLyid=rsL(0)
				  	response.Write("该客户在"&showregFrom(regLyid)&"有注册过")
				  end if
				  rsL.close
				  set rsL=nothing
				  '-------------------------判断录入客户
				  sqlP="select com_id,Ccheck from crm_InsertCompWeb where com_id="&rs("com_id")&" and saletype=1"
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then
				  	if rsp(1)="0" then
				  		comLx="<font color=#003399>录入客户<font color=#ff0000>未审</font></font>"
					else
						comLx="<font color=#003399>录入客户</font>"
					end if
				  end if
				  rsp.close
				  set rsp=nothing
				  '-------------------------判断参加活动客户
				  sqlP="select fromname from comp_regfrom1 where com_id="&rs("com_id")&""
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then

						comLx="<font color=#003399>"&rsp(0)&"</font>"

				  end if
				  rsp.close
				  set rsp=nothing
				  '------------------------------------
				  if comlxzd<>"" then
				  	response.Write(comLxzd&"<br>")
				  end if
				  response.Write(comLx)
				  
                  if left(dotype,3)<>"vap" then
					  if viprequest=2 then
						response.Write("<br><font color=#ff34de>申请再生通</font>")
					  end if
					  if viprequest=3 then
						response.Write("<br><font color=#f534de>申请品牌通</font>")
					  end if
				  end if
				'--------------------------------------
				'是否为会展客户
				response.Write(showlyfrom(rs("com_id")))
				
				sqld="select com_id from crm_bigcomp where com_id="&rs("com_id")
				set rsd=conn.execute(sqld)
				if not rsd.eof or not rsd.bof then
					response.Write("<br><font color=#ff0000>8000大客户</font>")
				end if
				rsd.close
				set rsd=nothing
				if left(dotype,3)="vap" then
					'---------------判断cs客户类型
					'sqld="select com_id from temp_vapcomp1 where com_id="&rs("com_id")
'					set rsd=conn.execute(sqld)
'					if not rsd.eof or not rsd.bof then
'						response.Write("<br><font color=#ff0000>cs-A类客户</font>")
'					end if
'					rsd.close
'					set rsd=nothing
					'---------------判断cs客户类型
					'sqld="select com_id from temp_vapcomp2 where com_id="&rs("com_id")
'					set rsd=conn.execute(sqld)
'					if not rsd.eof or not rsd.bof then
'						response.Write("<br><font color=#ff0000>cs-B类客户</font>")
'					end if
'					rsd.close
'					set rsd=nothing
					'---------------判断cs客户类型
					'sqld="select com_id from temp_vapcomp3 where com_id="&rs("com_id")
'					set rsd=conn.execute(sqld)
'					if not rsd.eof or not rsd.bof then
'						response.Write("<br><font color=#ff0000>cs-C类客户</font>")
'					end if
'					rsd.close
'					set rsd=nothing
					'---------------判断cs客户类型
					'sqld="select com_id from crm_serviceOut180day where com_id="&rs("com_id")
'					set rsd=conn.execute(sqld)
'					if not rsd.eof or not rsd.bof then
'						response.Write("<br><font color=#ff0000>过期180天</font>")
'					end if
'					rsd.close
'					set rsd=nothing
					
					if dotype="vappaycomp" then
						'---------------判断cs客户类型
						sqld="select com_id,typeid from AdvKeyWords where com_id="&rs("com_id")&" and typeid='11'"
						set rsd=conn.execute(sqld)
						if not rsd.eof or not rsd.bof then
							typetxt=rsd("typeid")
							if typetxt="11" then
								response.Write("<br><font color=#f80400>标王客户</font>")
							end if
							if typetxt="13" then
								response.Write("<br><font color=#f30400>黄展客户</font>")
							end if
							if typetxt="18" or typetxt="10" or typetxt="19" or typetxt="23" then
								response.Write("<br><font color=#f30400>独家广告</font>")
							end if
						end if
						rsd.close
						set rsd=nothing
						
					end if
					sqld="select paykind,paytype from comp_payinfo where com_id="&rs("com_id")&""
					set rsd=conn.execute(sqld)
					if not rsd.eof or not rsd.bof then
						if rsd("paytype")="2" then
							paykind=rsd("paykind")
							response.Write("<br><font color=#f30400>")
							if paykind="1" then
								response.Write("银牌")
							end if
							if paykind="2" then
								response.Write("金牌")
							end if
							if paykind="3" then
								response.Write("钻石")
							end if
							response.Write("</font>")
						else
							response.Write("<br><font color=#f30400>"&rsd(0)&"</font>")
						end if
					end if
					rsd.close
					set rsd=nothing
					'------判断必杀期客户
					vip_dateto=rs("vip_dateto")
					if not isnull(vip_dateto) and vip_dateto<>"" then
						if cdate(vip_dateto)>cdate(date()-90) and cdate(vip_dateto)<=date()+60 then
							response.Write("<br><font color=#ff0000>(cs必杀期客户)</font>")
						end if
						if cdate(vip_dateto)<cdate(date()-90) and cdate(vip_dateto)>date()-180 then
							response.Write("<br><font color=#ff0000>(90-180过期客户)</font>")
						end if
						if cdate(vip_dateto)<cdate(date()-180) and cdate(vip_dateto)>cdate("1900-1-1") then
							response.Write("<br><font color=#ff0000>过期180客户</font>")
						end if
					end if
					'---------------判断死海客户
					sqld="select com_id from crm_notBussiness where com_id="&rs("com_id")
					set rsd=conn.execute(sqld)
					if not rsd.eof or not rsd.bof then
						response.Write("<br><font color=#f80400>死海客户</font>")
					end if
					rsd.close
					set rsd=nothing
					'---------------判断死海客户
					'sqld="select com_id from temp_baoliucomp where com_id="&rs("com_id")
'					set rsd=conn.execute(sqld)
'					if not rsd.eof or not rsd.bof then
'						response.Write("<br><font color=#f80400>创业版再生通</font>")
'					end if
'					rsd.close
'					set rsd=nothing
				end if
				%>
                <div id="laiyuan<%=rs("com_id")%>"></div>
                </td>
                  
                  
                   
                  <td nowrap >
				  
				  <%response.Write(rs("com_name"))%>
				  <span id="activeComp<%=com_id%>"></span>
				  <span id="fine<%=com_id%>"></span>
                  <%
				  teldate=rs("teldate")
				  if teldate<>"" then
					  l=datediff("d",cdate(teldate),now)
					  if l<=30 then
						diaostr="<div class='crm_tishi'>"&(30-l+1)&"天后掉公海</div>"
					  end if
					  
					  sqldd="select com_id from temp_3dayNocontact where DATEDIFF(DD, teldate, GETDATE())>=1 and com_id="&rs("com_id")
					  set rsdd=conn.execute(sqldd)
					  if not rsdd.eof or not rsdd.bof then
						diaostr="<div class='crm_tishi'>1天后掉公海</div>"
					  end if
					  rsdd.close
					  set rsdd=nothing
				  end if
				  response.Write(diaostr)
				  %>
				  </td>
                  <td nowrap>
                  <%
				vcount=rs("viewcount")
				logincount=rs("logincount")
				lastlogintime=rs("lastlogintime")
				if rs("contactnext_time")<>"1900-1-1" then
					if left(dotype,3)="vap" then
						contactnext_time=rs("vcontactnext_time")
					else
						contactnext_time=rs("contactnext_time")
					end if
				else
					contactnext_time=""
				end if
				if left(dotype,3)="vap" then
					nowcomrank=rs("vcom_rank")
					if nowcomrank="4.1" then
					 	response.Write("短4星")
					 elseif nowcomrank="4.8" then
					 	response.Write("长4星")
					 else
					 	response.Write(nowcomrank&"星")
					 end if
				else
				  if rs("com_rank")<>"-1" then
				  	 nowcomrank=rs("com_rank")
					 if nowcomrank="4.1" then
					 	response.Write("普4星")
					 elseif nowcomrank="4.8" then
					 	response.Write("钻4星")
					 else
					 	response.Write(nowcomrank&"星")
					 end if
				  end if
				end if
				  %><span id="comrankshenhe<%=com_id%>"></span></td>
                  <td><%response.Write(rs("com_email"))%></td>
                  <td nowrap>
				  <%
				  if lastlogintime<>"" or not isnull(lastlogintime) then
				  	 response.Write(lastlogintime)
				  end if
				  %>
                  </td>
                 
                  <td nowrap>
				<a href="/admin1/compinfo/loginmore.asp?com_id=<%=rs("com_id")%>" target="_blank"><%  
				
				response.Write(logincount)
				%></a>
				  
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
				  %>
                  </td>
                  <td nowrap><%response.Write(rs("com_province"))%></td>
                  <td nowrap>
				  <%
				  if left(dotype,3)="vap" then
				  response.Write(rs("vlastteldate"))
				  else
				  response.Write(rs("lastteldate"))
				  end if
				  %>
                  </td>
                  <td nowrap>
                    <%response.Write(contactnext_time)%>
                    </td>
                  <td nowrap></td>
                  <td align="center" nowrap> <a href="#kang" target="topt" onClick="window.open('../admin_user_viewlist.asp?com_email=<%response.Write(rs("com_email"))%>','_blank','width=500,height=500')"><%response.Write(vcount)%></A></td>
                  <td nowrap>
				  <iframe src="http://adminasto.zz91.com/getesitecount/?com_id=<%= com_id %>" scrolling="no" frameborder="0" width="50" height="25"></iframe>
				  <%'=rs("shopviewedcount")%></td>
                  
                  <td nowrap>
				  <%
				  if rs("vip_date")<>"1905-3-14" then
				  response.Write(rs("vip_date"))
				  end if
				  %></td>
                 
				  <td nowrap>
				  <%= rs("vip_datefrom") %><br><%= rs("vip_dateto") %></td>
                  <td align="center" nowrap><%response.write(Waiter_Open)%></td>
                  <td align="center" nowrap>
				  <%
				  if left(dotype,3)="vap" then
				  	response.Write(rs("vfdate"))
				  else
				  	response.Write(rs("fdate"))
				  end if
					%></td>
                  <td align="center" nowrap>
                    <span id="realnamelr<%=com_id%>"></span>
                  </td>
                  <%'if dotype="大客户列表" or left(dotype,3)="vap" then%>
                  <td align="center" nowrap>
                  	<iframe src="http://adminasto.zz91.com/getCsuserName/?com_id=<%= com_id %>" scrolling="no" frameborder="0" width="50" height="25"></iframe>
                  </td>
                  
                  <%
				  'end if
				  %>
                  </SPAN> 
                  <td align="center" nowrap><span id="realnamevap<%=com_id%>"></span></td>
                  <td align="center" nowrap><span id="realnamesms<%=com_id%>"></span></td>
                  <td align="center" nowrap>
				  <span id="realname<%=com_id%>"></span>
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
                  <td colspan="36">暂时无信息！</td>
                </tr>
                <%end if
			  rs.close
			  set rs=nothing
			  %>
               
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="37"> 全选 
                    <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                    <%
					if (((ywadminid<>"" and session("userid")="13") or session("userid")="10") and dotype="CustomIn") or session("personid")="571" then
					%>
                    <input name="Submit6" type="button" class="button" value="审核录入" onClick="shenheluru(this.form)">
                    <%
					end if
					%>
                    <%
					if dotype="changeto4star" then
					%>
                    <input name="Submit6" type="button" class="button" value="审核4星" onClick="postAll(this.form,'确实要该四星客户吗?','shenhe4star')">
                    <%
					end if
					%>
                    <%
					if dotype="changeto5star" then
					%>
                    <input name="Submit6" type="button" class="button" value="审核5星" onClick="postAll(this.form,'确实要该五星客户吗?','shenhe5star')">
                    <%
					end if
					%>
                    <%'response.Write(ywadminid)%>
             <%if session("userid") ="10" or session("userid")="13" or ((ywadminid<>"0" and ywadminid<>"" and (not isnull(ywadminid)) and (dotype="allbm" or dotype="all" or dotype="bmzd" or dotype="nocontact" or dotype="my" or dotype="sbcomp" or dotype="MyContinue" or dotype="outgonghai" or lmaction="gonghai" or lmaction="allbm" or lmaction="mycomp" or dotype="vapallallcomp" or dotype="vappaycomp" or dotype="vapallcomp")))  then%>
                    <input name="Submit" type="button" class="button" value="从新分配给" onClick="selectcrm(this.form)">
					<select name="personid" class="button" id="dopersonid" >
              <option value="" >请选择--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%' or code like '26%'"
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
					iszst=0
					if dotype="MyContinue" or dotype="TodayContinue" then iszst=1
					if dotype<>"all" and dotype<>"xm" and dotype<>"allall" and dotype<>"allzstGh" and  dotype<>"allExpireZstGh"  then
					%>
                    <input name="Submit5" type="button" class="button" value="放入公海" onClick="delselectcrm(this.form,<%=iszst%>)">
                    <%if left(dotype,3)="vap" then%>
                    <!--<input name="Submit5" type="button" class="button" value="放入储备公海" onClick="postAll(this.form,'确实要放到储备公海吗?','cbgonghai')">-->
                    <%end if%>
					<input name="Submit5" type="button" class="button" value="设置为重点" onClick="zhongdian(this.form)">
					<input name="Submit5" type="button" class="button" value="取消为重点" onClick="zhongdianout(this.form)">
                    <%if (dotype="bmzd" and session("Partadmin")<>"0") or session("userid")="10" then%>
                    <input name="Submit5" type="button" class="button" value="重点审核" onClick="zhongdiansh(this.form)">
                    <input name="Submit5" type="button" class="button" value="取消重点审核" onClick="zhongdianshno(this.form)">
                    <%end if%>
                    
					<%
					end if
					%>
                    <%if session("personid")="93" or session("userid")="10" then%>
                    <input name="Submit5" type="button" class="button" value="设置为大客户" onClick="postAll(this.form,'确实要设置为大客户吗?','dakehu')">
                    <input name="Submit5" type="button" class="button" value="取消设置为大客户" onClick="postAll(this.form,'确实要取消设置为大客户吗?','cencerdakehu')">
					<%
					end if
					%>
                    <%if (dotype<>"my" and dotype<>"allall" and dotype<>"bmzd" and dotype<>"allbm") or getcompany=1  then%>
					<input type="button" name="Submit" class="button" value="放到我的客户库" onClick="selectoutmycrm(this.form)">
                    <input type="button" name="Submit" class="button" value="放入预备品牌通" onClick="postAll(this.form,'确实要放入预备品牌通吗?','yubeippt')">
					<%end if%>
                    
					<%if dotype="my" or dotype="nocontact" or dotype="today" or dotype="contact" or dotype<>"allall"  then%>
						<%if left(dotype,3)="vap" then%>
                        	<%if lmaction<>"gonghai" and lmaction<>"nozengzhi" then%>
                            <input type="button" name="Submit2" class="button" value="放到死海" onClick="postAll(this.form,'提醒：请再次确认该客户该客户为VAP的死海客户，此操作将会把该客户同时丢入公海，请确认后再放入?','droptosihai')">
                            <%end if%>
                        <%else%>
                            <input type="button" name="Submit2" class="button" value="放到废品池" onClick="selectwastecom(this.form)">
                        <%end if%>
					<%end if%>
					<%if dotype<>"allall" then%>
						<input type="button" name="Submit" class="button" value="放入到款确认单" onClick="selectopenzst(this.form)">
                    <%else%>
						<!--<input name="Submit5" type="button" class="button" value="放入创业版再生通" onClick="postAll(this.form,'确实放到创业版再生通库吗?','idcbaoliu')">-->
					<%end if%>
                    <%if left("dotype",3)<>"vap" then%>
					<input name="Submit5" type="button" class="button" value="SEO上线确认" onClick="postAll(this.form,'确认SEO已经上线了吗?','SEOcomfirm')">
                    <input name="Submit5" type="button" class="button" value="取消SEO上线" onClick="postAll(this.form,'确认取消SEO已经上线了吗?','noSEOcomfirm')">
					<%end if%>
                    <input type="hidden" name="dostay" id="dostay">
                    <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
					<input type="hidden" name="selectcb" id="selectcb"> 
                    <input type="hidden" name="userName" value="<%=userName%>">
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
<!-- #include file="scriptstr.asp" -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25"><!-- #include file="../include/page.asp" --></td>
  </tr>
 
</table>
<%
else
response.Write(errtext)
end if
conn.close
set conn=nothing
%>
</div>
<script language="javascript" src="/admin1/crmlocal/viewhistory888.asp?personid=<%=session("personid")%>&com_id=0"></script>
<script language=javascript> 
document.body.oncopy=function(){ 
event.returnValue=false; 
var t=document.selection.createRange().text; 
document.getElementById("copycontent").value=t
formcopy.submit()
var s=""; 
window.clipboardData.setData('Text', t+s); 
} 
</script>

<form name="formcopy" method="post" action="rizhi/copysave.asp" target="copekuan">
  <input name="personid" type="hidden" id="personid" value="<%=session("personid")%>">
  <input type="hidden" name="copycontent" id="copycontent">
</form>
<iframe name="copekuan" src="" frameborder='0' width="0" height="0" scrolling="no" ></iframe>
</body>
</html>