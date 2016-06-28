<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="inc.asp" -->
<%
frompage=request.QueryString("frompage")
frompagequrstr=request.QueryString("frompagequrstr")
idprod=trim(request.querystring("idprod"))
cemail=trim(request.querystring("cemail"))
email=trim(request.querystring("mail"))
dotype=request.QueryString("dotype")
if idprod="" then
	'response.End()
end if
if email<>"" then
	sql="select com_id from temp_salescomp where com_email='"&email&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		idprod=rs(0)
	else
		response.Write("无此用户")
		response.End()
	end if
	rs.close
	set rs=nothing
end if

sqluser="select ywadminid,adminuserid,Partuserid,huangye_check from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
ywadminid=rsuser("ywadminid")
adminuserid=rsuser("adminuserid")
Partuserid=rsuser("Partuserid")
huangye_check=rsuser("huangye_check")
rsuser.close
set rsuser=nothing
 
if session("userid")<>"1314" and session("userid")<>"13" and session("userid")<>"34" and session("userid")<>"4204" then
	if left(dotype,3)<>"vap"   then'---------不是VAP客户
		'sqla="select com_id from crm_3monthexpired_vipcomp where com_id="&idprod&" and com_id not in (select com_id from crm_bigcomp)"
'		set rsa=conn.execute(sqla)
'		if not rsa.eof or not rsa.bof then
'			if session("userid")<>"10" then
'				response.Write("3个月内到期的客户已转到核心客服部，新签部不能查看和销售")
'				response.End()
'			end if
'		end if
'		rsa.close
'		set rsa=nothing
		'-----------判断不在保留客户库的再生通客户新签销售不能查看
		if session("personid")="93" or session("personid")="456" or session("userid")="10" or huangye_check="1" or Partuserid<>"0" then
		else
			sqla="select com_id from comp_zstinfo where com_id="&idprod&" and closeflag=0"
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				response.Write("该客户已经转到VAP客户库，你已经不能操作查看！")
				response.End()
			end if
			rsa.close
			set rsa=nothing
		end if
	else
		'sqla="select * from renshi_salesIncome where service_type='1307' and sales_date>'2012-11-20' and not exists(select com_id from crm_seoconfirm_online where DATEDIFF(DD, fdate, GETDATE()) > 30 and renshi_salesIncome.com_id=com_id) and com_id="&idprod&""
'		'response.Write(sqla)
'		set rsa=conn.execute(sqla)
'		if not rsa.eof or not rsa.bof then
'			response.Write("该客户是商铺优化服务客户，在SEO上线后30天后才能打开！")
'			response.End()
'		end if
'		rsa.close
'		set rsa=nothing
			'sqla="select com_id from temp_baoliucomp where com_id="&idprod&" and com_id in (select com_id from crm_assign)"
'			set rsa=conn.execute(sqla)
'			if not rsa.eof or not rsa.bof then
'				if session("userid")<>"10" then
'					response.Write("该客户还在再生通新签保留客户库里，不能查看")
'					response.End()
'				end if
'			end if
'			rsa.close
'			set rsa=nothing
	end if
end if
idcbaoliu=0


	'-------
	if session("userid")="1314" or session("userid")="4204"  then
		'-----销售助理的权限
	else
	
	  if left(dotype,3)="vap" then
	  	sqla="select personid from crm_assignvap where com_id="&idprod
	  elseif left(dotype,3)="sms" then
	  	sqla="select personid from crm_assignsms where com_id="&idprod
	  else
	  	sqla="select personid from crm_assign where com_id="&idprod
	  end if
	  set rsa=conn.execute(sqla)
	  'response.Write(sqla)
	  if not rsa.eof or not rsa.bof  then
	  	  ownerpersonid=rsa(0)
	  	  if session("userid")="10" then
		  else
		  	  openqx=0
			  personopen=0
		  	  if ywadminid<>"" and not isnull(ywadminid)  then
				  sqlb="select userid from users where id="&ownerpersonid&" and userid in ("&ywadminid&")"
				  set rsb=conn.execute(sqlb)
				  if not rsb.eof or not rsb.bof then
				     openqx=1
				  end if
				  rsb.close
				  set rsb=nothing
			  end if
			  '------------个人客户
			  if cstr(session("personid"))=cstr(ownerpersonid)  then
				  personopen=1
				  openqx=1
			  end if
			  '--------主管权限
			  if partuserid<>"0" then
			  	  openqx=1
			  end if
			  if openqx=0 then
					response.Write("你没有权限查看该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
					response.End()
			  end if
		  end if
		  opengonghai=0
	  else
	    '-------未审核录入客户
	  	'sqlp="select com_id from crm_InsertCompWeb where com_id="&idprod&" and saletype=1 and ccheck=0"
'		set rsp=conn.execute(sqlp)
'		if not rsp.eof or not rsp.bof then
'			response.Write("改客户信息还没有通过审核，你没有权限查看该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
'			response.End()
'		end if
'		rsp.close
'		set rsp=nothing
		'----------预申请是否审核，未审核不能放到我的客户库里 2011-3-1
		sqlp="select com_id,assignflag from crm_assign_request where com_id="&idprod&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			requestassignFlag=0
			if cstr(rsp("assignflag"))="0" then
				requestassignFlag=1
			end if
		else
			requestassignFlag=0
		end if
		rsp.close
		set rsp=nothing
		opengonghai=1
		'----------
	  end if
	  rsa.close
	  set rsa=nothing
	  'response.Write(requestassignFlag)
	end if
'end if
'--------------判断是否是二次销售客户
sqlc="select com_id from comp_zstinfo where com_id="&idprod&" and closeflag=0"
set rsc=conn.execute(sqlc)
if not rsc.eof or not rsc.bof then
	iszst=1
else
	iszst=0
end if
rsc.close
set rsc=nothing
'-------------取公司基本信息
if idprod="" and cemail="" then
  response.end
end if
'----------判断是否在公海
isgonghai=0
if left(dotype,3)="vap" then
	sqla="select personid from crm_assign where com_id="&idprod
else
	sqla="select personid from crm_assignvap where com_id="&idprod
end if
set rsa=conn.execute(sqla)
if rsa.eof or rsa.bof then
	isgonghai=1
end if
rsa.close
set rsa=nothing

'--------- 读取供求询盘数量
curl="http://192.168.2.21/getCompcountlist/?com_id="&idprod&""
read=getHTTPPage(Curl)
arrread=split(read,"<<")
if ubound(arrread)>=1 then 
	returncount=arrread(1)
	arrreturncount=split(returncount,"|")
	if ubound(arrreturncount)>=2 then
		offercount=arrreturncount(0)
		sendcount=arrreturncount(1)
		resivercount=arrreturncount(2)
	end if
end if

set rscom=server.CreateObject("ADODB.recordset")
sql="select * from Crm_Temp_SalesComp where com_id="&idprod&""
rscom.open sql,conn,1,1
if rscom.eof or rscom.bof then
	rscom.close()
	set rscom=nothing
	set rscom=server.CreateObject("ADODB.recordset")
	sql="select * from comp_info where com_id="&idprod&""
	rscom.open sql,conn,1,1
	if rscom.eof or rscom.bof then
		response.Write("无此信息！")
		rscom.close()
		set rscom=nothing
		response.End()
	end if
end if
set rscomdd=server.CreateObject("ADODB.recordset")
sqldd="select com_email from comp_info where com_id="&idprod&""
rscomdd.open sqldd,conn,1,1
if not rscomdd.eof and not rscomdd.bof then
	cemail=rscomdd("com_email")
end if

com_id=idprod
if left(dotype,3)="sms" then
else
	'---------------判断撞单情况
	com_tel=trim(rscom("com_tel"))
	com_mobile=trim(rscom("com_mobile"))
	com_name=rscom("com_name")
	
	
	
	com_tel=clearcomptel(com_tel)
	'response.Write(com_tel)
	
	com_mobile=clearcompmobile(com_mobile)
'	
'	
'	arrcomList=""
	zhuangdanFlag=0'撞单标志
'	if com_mobile<>"" and not isnull(com_mobile)  then
'		if len(com_mobile)>10 then
'			sql="select com_id from temp_salescomp where com_mobile like '%"&right(trim(com_mobile),10)&"' and len(com_mobile)>8 and com_id<>"&com_id&""
'			set rs=conn.execute(sql)
'			if not rs.eof or not rs.bof then
'				while not rs.eof
'					arrcomList=arrcomList&rs("com_id")&","
'				rs.movenext
'				wend
'				zhuangdanFlag=1
'			end if
'			rs.close
'			set rs=nothing
'		end if
'	end if
'	if com_tel<>"" and not isnull(com_tel) and len(com_tel)>6 and len(com_tel)<9 then
'		sqlmm=""
'		if arrcomList<>"" then
'			sqlmm=" and com_id not in("&left(arrcomList,len(arrcomList)-1)&") "
'		end if
'		sql="select com_id from temp_salescomp where com_tel like '%"&right(com_tel,7)&"' and len(com_tel)>7 and com_id<>"&com_id&" "&sqlmm
'		set rs=conn.execute(sql)
'		if not rs.eof or not rs.bof then
'			while not rs.eof
'				arrcomList=arrcomList&rs("com_id")&","
'			rs.movenext
'			wend
'			zhuangdanFlag=1
'		end if
'		rs.close
'		set rs=nothing
'	end if
'	
	if isnull(com_name) then com_name=""
	com_name=replace(com_name,"个体经营","")
	com_name=replace(com_name,"(","")
	com_name=replace(com_name,")","")
	com_name=replace(com_name,"个体","")
	com_name=replace(com_name,"废品收购","")
	com_name=replace(com_name,"废品回收","")
	com_name=replace(com_name,"暂无","")
	if isnull(com_tel) then com_tel=""
	if isnull(com_mobile) then com_mobile=""
	if isnull(com_name) then com_name=""
	
	curl="http://192.168.2.21/zhuandan/?com_tel="&GBtoUTF8(com_tel)&"&com_mobile="&GBtoUTF8(com_mobile)&"&com_name="&GBtoUTF8(com_name)&"&dotype="&dotype&"&requesttype=a"
	'response.Write(curl)
	read=getHTTPPage(Curl)
	arrread=split(read,"<<")
	if ubound(arrread)>=1 then
		returncomp=arrread(1)
		arrcomList=returncomp
		arrcomList=replace(arrcomList,idprod&",","")
	end if
	
	curl="http://192.168.2.21/zhuandan/?com_tel="&GBtoUTF8(com_tel)&"&com_mobile="&GBtoUTF8(com_mobile)&"&com_name="&GBtoUTF8(com_name)&"&dotype="&dotype&"&requesttype=c"
	read=getHTTPPage(Curl)
	arrread=split(read,"<<")
	if ubound(arrread)>=1 then
		returncomp=arrread(1)
		arrcomList=arrcomList&returncomp
		arrcomList=replace(arrcomList,idprod&",","")
	end if
	
	if arrcomList<>"," and arrcomList<>"" and not isnull(arrcomList) then
		zhuangdanFlag=1
	end if
	
'	if com_name<>"" then
'		sql="select top 10 com_id from temp_salescomp where com_name like '%"&com_name&"%' and com_id<>"&com_id&" "
'		set rs=conn.execute(sql)
'		if not rs.eof or not rs.bof then
'			while not rs.eof
'				arrcomList=arrcomList&rs("com_id")&","
'			rs.movenext
'			wend
'		end if
'		rs.close
'		set rs=nothing
'	end if
	
	''------------------------------------
	'------------------服务部关联已经审核情况
	'flag=1 and shflag=1 最终关联
	'flag=0 and shflag=1 不再公海中显示
	guangLiangFlag=0
	isshFlag=0
	sqlh="select groupid from crm_complink where flag=0 and shflag=1 and com_id="&com_id&""
	set rsh=conn.execute(sqlh)
	if not rsh.eof or not rsh.bof then
		guangLiangFlag=1
		groupid=rsh("groupid")
		if zhuangdanFlag=0 then
			sqlt="select com_id from crm_complink where groupid='"&groupid&"' and com_id<>"&com_id&""
			set rst=conn.execute(sqlt)
			if not rst.eof or not rst.bof then
			while not rst.eof
				arrcomList=arrcomList&rst("com_id")&","
			rst.movenext
			wend
			end if
			rst.close
			set rst=nothing
		end if
		isshFlag=1
	end if
	rsh.close
	set rsh=nothing
	'------最终被关联的客户
	sqlh="select groupid from crm_complink where flag=1 and shflag=1 and com_id="&com_id&""
	set rsh=conn.execute(sqlh)
	if not rsh.eof or not rsh.bof then
		guangLiangFlag=1
		isshFlag=2
	end if
	rsh.close
	set rsh=nothing
	'------------------------
	if guangLiangFlag<>1 then
		'-------------已经提交服务部并等待审核
		sqlh="select GroupID,ysflag from crm_complink where shflag=0 and com_id="&com_id&""
		set rsh=conn.execute(sqlh)
		if not rsh.eof or not rsh.bof then
			guangLiangFlag=2
			GroupID=rsh(0)
			myYSFlag=rsh(1)
		end if
		rsh.close
		set rsh=nothing
		'----------判断预审情况
		
		YSFlag=0
		sqlh="select ysflag from crm_complink_main where GroupID='"&GroupID&"'"
		set rsh=conn.execute(sqlh)
		if not rsh.eof or not rsh.bof then
			if rsh(0)="1" then
				YSFlag=1
			end if
		end if
		rsh.close
		set rsh=nothing
		'---------判断预审给谁
		if YSFlag=1 then
			YSpersonid=0
			if left(dotype,3)="vap" then
				sqlh="select b.personid from crm_complink as a,crm_assignvap as b where a.GroupID='"&GroupID&"' and a.com_id=b.com_id and a.ysflag=1"
			else
				sqlh="select b.personid from crm_complink as a,crm_assign as b where a.GroupID='"&GroupID&"' and a.com_id=b.com_id and a.ysflag=1"
			end if
			set rsh=conn.execute(sqlh)
			if not rsh.eof or not rsh.bof then
				YSpersonid=rsh(0)
			end if
			rsh.close
			set rsh=nothing
			sqlu="select realname from users where id="&YSpersonid
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				YSpersonName=rsu(0)
			end if
			rsu.close
			set rsu=nothing
		end if
		'--------非预审客户
		if myYSFlag=0 and YSFlag=1 then
			guangLiangFlag=3
		end if
		'--------预审客户
		if myYSFlag=1 and YSFlag=1 then
			guangLiangFlag=4
		end if
	end if

end if
'---------------销售基本信息
if left(dotype,3)="vap" then
	salestable="comp_salesvap"
else
	salestable="comp_sales"
end if
com_id=idprod
sql="select contactType,com_rank,contactnext_time,com_kind,com_Especial from "&salestable&" where com_id="&idprod&""
set rssales=conn.execute(sql)
if rssales.eof then
	sqls="select top 1 contacttype,com_rank,contactnext_time,com_kind from comp_tel where com_id="&idprod&""
	if left(dotype,3)="vap" then
		sqls=sqls&" and telflag=4"
	end if
	sqls=sqls&" order by id desc"
	set rss=conn.execute(sqls)
	if not rss.eof or not rss.bof then
		contacttype=rss("contacttype")
		com_rank=rss("com_rank")
		contactnext_time=rss("contactnext_time")
		com_kind=rss("com_kind")
	end if
	rss.close
	set rss=nothing
	com_Especial="0"
else
	contactType=rssales("contactType")
	com_rank=rssales("com_rank")
	contactnext_time=rssales("contactnext_time")
	com_kind=rssales("com_kind")
	com_Especial=rssales("com_Especial")
end if
rssales.close
set rssales=nothing
'客户基本情况统计表
'allitems=""
'sql="select * from temp_statCount where com_id="&com_id&""
'set rs=conn.execute(sql)
'if not rs.eof or not rs.bof then
'	offercount=rs("countOffer")
'	countsendQuestion=rs("countsendQuestion")
'	countreceiveQuestion=rs("countreceiveQuestion")
'end if
'rs.close
'set rs=nothing
'----客户使用信息
sqlt="select lastlogintime,logincount,com_regtime,vip_dateto,viewcount from v_salescomp where com_id="&com_id&""
set rst=conn.execute(sqlt)
if not rst.eof or not rst.bof then
	lastlogintime=rst("lastlogintime")
	logincount=rst("logincount")
	com_regtime=rst("com_regtime")
	vip_dateto=rst("vip_dateto")
	vcount=rst("viewcount")
end if
rst.close
set rst=nothing
email=trim(rscom("com_email"))
comname=rscom("com_name")
if comname="" then comname="无公司名称"
'---参与的活动
sqla="select top 1 fromname from comp_regfrom1 where com_id="&com_id&" order by id desc"
set rsa=conn.execute(sqla)
if not rsa.bof or not rsa.eof then
	fromname=rsa("fromname")
end if
rsa.close
set rsa=nothing
%>
<html>
<head>
<title><%=comname%></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script type="text/javascript" src="http://img0.zz91.com/zz91/mobile/js/jquery.js"></script>
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="js/province.js"></SCRIPT>
<SCRIPT language=javascript src="js/compkind.js"></SCRIPT>

<SCRIPT language=javascript src="js/ext.js"></SCRIPT>
<SCRIPT language=javascript src="js/companydetail.js"></SCRIPT>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">

<style>
#alertmsg
{
	padding: 5px;
	border: 1px solid #093;
	background-color: #FFF;
}
form
{
	margin:0px;
	padding:0px;
}
</style>
</head>
<body>
<input type="hidden" id="weixinmobile" value="<%=com_mobile%>">
<input type="hidden" id="weixincom_id" value="<%=request("idprod")%>">
<div id="wrap">
<%dis="display:none"%>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#FF0000">
  <tr>
    <td bgcolor="#FFFFCC"  style="line-height:22px"><div><a href="#" style="font-size:14px; color:#F00; font-weight:bold" onClick="opencalltishi()">关于使用 CALLCENTER 录音电话系统的请注意</a></div>
      <div id="calltishi" style="display:none">
      
    1、若有更换位置，请及时将自己的聊天版上的电话号码更新<br>
    2、为了能正确匹配小记和录音文件，请在挂了电话后10分内保存小记。<br>
    3、若你拨打的电话不在公司联系方式中，请在对应弹出的提示内容框中认真填写对应的内容并在小记里注明联系方式。<br>
    4、若你不是通过电话方式联系客户，请在小记里注明你的使用什么方式联系及对方账号。<br>
    5、客户调整或小记补充说明，请在小记里注明</div></td>
  </tr>
</table>

<div  id="page_cover" style="position:absolute;display:none;z-index:100; width:230px; height:59px; color:#cccccc; left: 0; top: 0;"></div>
<div id="alertmsg" style=" <%=dis%>"></div>
<div id="regform" style=" <%=dis%>;z-index:1002; top:100px; margin-left:100px; width:400px; height:100px;" > 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size:12px">
      <tr>
        <td height="29" background="../images/greentitlebg.gif" bgcolor="#003399" class="alerttitle"><div class="alerttitlen" id="alerttile">创建产品系列</div></td>
      </tr>
      <tr>
      <form id="form2" name="form2" method="post" action="crm_assign_save.asp?selectcb=<%response.Write(idprod)%>&dostay=delselec1tcrm&closed=1&dotype=<%=dotype%>">
      <tr>
      <td height="100" bgcolor="#FFFFFF" class="alerttitle_kuang">
      
      
	  	
	  <table width="100%" border="0" cellspacing="0" cellpadding="6">
	     <%
	  sqls="select id,meno from cate_droppedinseaCause"
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
    d.style.top = parseInt(d.style.top) + percent + "px"
	
	var d = document.getElementById("alertmsg")
    d.style.top = d.style.top == "" ? "100px" : d.style.top
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
<form name="form1" method="post" target="crmeidt" action="crm_cominfoedit_re.asp?frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>#top" onSubmit="return chkfrm(this)">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top">
    <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">
		   <TABLE id=secTable cellSpacing=0 cellPadding=0 border=0>
                                    <TR>
                                        <TD nowrap class=selected onclick=secBoard(0)>基本信息</TD>
                                        <TD nowrap class=unselect><a href="http://admin1949.zz91.com/web/zz91/salecrm/viewInfo.htm?companyId=<%=request("idprod")%>&email=<%=trim(rscom("com_email"))%>" target="_blank">供求(<font color="#FF0000"><%=offercount%></font>)发送询盘(<font color="#FF0000"><%=sendcount%></font>)收到询盘(<font color="#FF0000"><%=resivercount%></font>)</a></TD>                                       
                                        <!--
                                        <TD nowrap class=unselect><a href="http://admin.zz91.com/admin1/crm/crm_messageto_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>" target="_blank">发送的询盘信息<br>有<font color="#FF0000"><%response.Write(countsendQuestion)%></font>条</a></TD>                                        
                                        <TD nowrap class=unselect><a href="http://admin.zz91.com/admin1/crm/crm_messagefrom_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>" target="_blank">收到的询盘信息<br>有<font color="#FF0000"><%response.Write(countreceiveQuestion)%></font>条</a></TD>											
                                        -->
										<TD width="99%" class=unselect>
                                        <input name="com_rankFlag" type="hidden" id="com_rankFlag" value="<%=com_rank%>">
                                        
                                        
                                        <input name="Submit" type="submit" id="submitsave1" class="button" value="  保存   " style="font-size:14px; font-weight:bold">    &nbsp;&nbsp;
<input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="关闭">
										<%if personopen=1 or opengonghai=1 then%>
                                         
                          
						  <%
						  if session("userid")="10"  then
						  	gjqx=1
						  end if
						  '-----------------------begin
							'时间条件
							getcompany=0
							if formatdatetime(now,4)>"6:30" and formatdatetime(now,4)<"9:00" then
								getcompany=1
							end if
							if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:00" then
								getcompany=1
							end if
							if formatdatetime(now,4)>"17:30" and formatdatetime(now,4)<"24:00" then
								getcompany=1
							end if
							'----------------------end
						  if dotype<>"allall" or gjqx=1 or getcompany=1 or opengonghai=1 then
						  if zhuangdanFlag=0 and guangLiangFlag<1 then
						  '----------关联客户不能放到我的客户库
						  %>
                          	<%if opengonghai=1 and requestassignFlag=0 then%>
                          		<input type="button" name="Submit5" class="button" value="放到我的客户里" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1toutmycrm&closed=1&dotype=<%=dotype%>'">
                          		<input type="button" name="Submit" class="button" value="放到重点客户准备" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=zhongdiankh&closed=1&dotype=<%=dotype%>'">
							<%end if%>
                            <%
								if requestassignFlag=1 then
									response.Write("该客户在预申请分配库里，请主管审核该预分配的客户！")
								end if
								%>
						  <%
						  end if
						  %>
						  <%
						    if dotype<>"all" and dotype<>"xm" and dotype<>"Especial" and dotype<>"allGh" and dotype<>"allzstGh" then%>
                            <%
							if zhuangdanFlag=0 and guangLiangFlag<1 then
							%>
                            	<%if opengonghai=0 then%>
									<%if left(dotype,3)<>"vap" then%>
                                    <input type="button" name="Submit3" class="button" value="放到废品池" onClick="if(confirm('提醒：请再次确认该客户手机、座机、“邮箱”均为无效联系方式，如果“邮箱”没法确定无效的，请不要随便放入废品池。如有发现该客户乱选，责任自负。')){window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1twastecom&closed=1&dotype=<%=dotype%>'}">
                                    <%else%>
                                    <input type="button" name="Submit3" class="button" value="放到死海" onClick="if(confirm('提醒：请再次确认该客户该客户为VAP的死海客户，此操作将会把该客户同时丢入公海，请确认后再放入。')){window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=droptosihai&closed=1&dotype=<%=dotype%>'}">
                                    <%end if%>
                                    <input type="button" name="Submit3" class="button" value="设置为重点" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=zhongdian&closed=1&dotype=<%=dotype%>'">
                            	<%end if%>
                            <%
							end if
							%>
                            <%if opengonghai=0 then%>
                          	<input type="button" name="Submit" class="button" value="放入公海" onClick="DropToSea(<%=iszst%>,'delselec1tcrm');">
                            	
                            <%end if%>
                            
						  <%end if
						  
						  end if
						  %>
                          <%if left(dotype,3)="vap" then%>
                          <input type="button" name="Submit" class="button" value="发送短信" onClick="window.open('http://admin.zz91.com/admin1/compinfo/SMS_SendSMS.asp?com_mobile=<%=com_mobile%>','','')">
                          <!--
                          <input type="button" name="Submit" class="button" value="放入储备公海" onClick="DropToSea(<%=iszst%>,'cbgonghai');">
                          -->
						  <%end if%>
                          <%
						  '----personopen=1 /end
						  end if
						  %>
                          <input type="button" name="Submit" class="button" value="添加为2016黄页客户" onClick="window.open('/admin1/huangye2016/add.asp?com_email=<%=email%>','','')">
                          <input type="button" name="Submit" class="button" value="CS丢公海记录" onClick="window.open('http://admin1949.zz91.com/web/zz91/crm/highsea/index.htm?companyId=<%=com_id%>','','')">
						  </TD>
									
                			        </TR>
   			    </TABLE>
<%
'sqlw="select top 1 tcontent from crm_awoke where tcheck=1 order by id desc"
'set rsw=conn.execute(sqlw)
'if not rsw.eof or not rsw.bof then
%>
<div class="tishi" style="background-color:#FF0; font-size:14px; font-weight:bold">
	<%'=rsw(0)%>
</div>
<%
'end if
'rsw.close
'set rsw=nothing
%>
		  </td>
        </tr>
        <tr>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="payinfocontent">
                <tr>
                  <td>
                  <iframe id="serverhistory" name="serverhistory"  style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="http://adminasto.zz91.com/serverhistory/?company_id=<%=rscom("com_id")%>&email=<%=cemail%>"></iframe>
				  <%
                  sqlt="select * from advKeywords where com_id="&com_id&""
                  set rst=conn.execute(sqlt)
                  if not rst.eof then
                  %>
                    <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
                      <tr bgcolor="#CCCCCC">
                        <td>关键字</td>
                        <td>广告类型</td>
                        <td>开始时间</td>
                        <td>结束时间</td>
                        <td>审核</td>
                        <td>是否过期</td>
                        <td>点击率</td>
                        </tr>
                      <%
					  while not rst.eof 
					  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100"><%=rst("keywords")%></td>
                        <td><%=showMeno(conn,"advkeywordsType",rst("TypeID"))%></td>
                        <td><%=rst("fromdate")%></td>
                        <td><%=rst("todate")%></td>
                        <td><%
						if rst("checked")=1 then
						response.Write("已审核")
						else
						response.Write("<font color=#ff0000>未审核</font>")
						end if
						%></td>
                        <td><%
						todate=rst("todate")
						if todate<>"" then
							if cdate(todate)<date() then
								response.Write("<font color=#ff0000>已过期</font>")
							end if
						end if
						%></td>
                        <td><%=rst("Kcount")%></td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  %>
                      </table>
                    <%
					 end if
					 rst.close
					 set rst=nothing
					 %>
                     <%
                  sqlt="select * from renshi_salesIncome where com_id="&com_id&""
                  set rst=conn.execute(sqlt)
                  if not rst.eof then
                  %>
                    <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
                      <tr bgcolor="#CCCCCC">
                        <td>待开通单</td>
                        
                        <td>产品分类</td>
                        <td>到款时间</td>
                        <td>到款金额</td>
                        <td>销售人员</td>
                        <td>备注</td>
                        </tr>
                      <%
					  while not rst.eof 
					  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100"></td>
                        
                        <td>
                        <%=rst("service_type")%>
                        </td>
                        <td><%=rst("sales_date")%></td>
                        <td><%=rst("sales_price")%></td>
                        <td><%=rst("realname")%></td>
                        <td><%=rst("sales_bz")%></td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  %>
                      </table>
                    <%
					 end if
					 rst.close
					 set rst=nothing
					 %>
                  
                    </td>
                </tr>
              </table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="searchtable">
        
        
        
        <tr>
          <td class="td_s2">
            
            
            <table width="100%" border="0" align="center" cellspacing="0">
              
              <tr align="center"> 
                <td colspan="6"><a name="top"></a> <iframe id=crmeidt name=crmeidt height="1" style="WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="###kang"></iframe>                        </td>
                </tr>
              </table>
            <table width="100%" border="0" align="center" cellspacing="0">
              <tr> 
                <td width="8%" align="right" nowrap>
                  <%
					 
					'-------------------------判断录入客户
				  sqlP="select com_id from crm_InsertCompWeb where com_id="&idprod&" and saletype=1"
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then
				  	comLx="<font color=#ff0000>(录入客户)</font>"
				  end if
				  rsp.close
				  set rsp=nothing
				  response.Write(comLx)
				  '------------------------------------
					  %>
                  公司名称
                  <input name="re" type="hidden" id="re" value="1"><input name="crmCheck" type="checkbox" id="cnameCheck" value="1" onClick="checkmod(this)"></td>
                <td colspan="3" nowrap>
                  <input name="cname" class="text" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" readonly maxlength="96"> 
                  <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>"> 
                  <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">特殊意向客户<input type="radio" <%if com_Especial="1" then response.Write("checked")%> name="com_Especial" value="1">
                  是
                  <input type="radio" name="com_Especial" value="0" <%if com_Especial="0" then response.Write("checked")%>>
                  否						  </td>
                <td align="right" nowrap>地址
                  <input name="crmCheck" type="checkbox" id="caddCheck" value="2" onClick="checkmod(this)"></td>
                <td nowrap>
                  <input name="cadd" type="text" class="text" id="cadd" value="<%=replacequot1(trim(rscom("com_add")))%>" size="30" readonly maxlength="255">
                  邮编
                  <input name="crmCheck" type="checkbox" id="czipCheck" value="4" onClick="checkmod(this)"><input name="czip" type="text" class="text" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20" readonly></td>
                </tr>
              
              
              
              <tr> 
                <td align="right">电话
                  <input name="crmCheck" type="checkbox" id="ctelCheck" value="6" onClick="checkmod(this)"></td>
                <td colspan="3">
                  <input name="ctel" type="text" 
						<%
						if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("style='display:none'")
						 end if
						end if
						%> class="text" id="ctel" readonly value="<%=trim(rscom("com_tel"))%>" size="30" maxlength="96">
                  <%
						if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("暂时屏蔽联系方式")
						 end if
						end if
						%><a href="file/filelistly.asp?Dtmf=<%=right(trim(rscom("com_tel")),6)%>" target="_blank">该号码录音</a>
                  
                  </td>
                <td align="right">手机
                  <input name="crmCheck" type="checkbox" id="cmobileCheck" value="7" onClick="checkmod(this)"></td>
                <td>
                  <input name="cmobile" type="text" class="text" id="cmobile" 
<%
						if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("style='display:none'")
						 end if
						end if
%> readonly value="<%=trim(rscom("com_mobile"))%>" size="30" maxlength="96" >
                  <%
						if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("暂时屏蔽联系方式")
						 end if
						end if
						%>
                  <a href="file/filelistly.asp?Dtmf=<%=right(trim(rscom("com_mobile")),8)%>" target="_blank">该号码录音</a>
                  </td>
                </tr>
              <tr> 
                <td align="right">传真
                  <input name="crmCheck" type="checkbox" id="cfaxCheck" value="8" onClick="checkmod(this)"></td>
                <td colspan="3">
                  
                  <input name="cfax" type="text" class="text" id="cfax" 
						  <%
						  if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("style='display:none'")
						 end if
						end if
						  %> readonly value="<%=trim(rscom("com_fax"))%>" size="30" maxlength="96">
                  <%
						if dotype="allall" then
						 if session("userid")="10" or session("userClass")="1" then
						 else
						    response.Write("暂时屏蔽联系方式")
						 end if
						end if
						%>          						  参加活动<select name="active_flag" id="active_flag">
                      	  <option value="">请选择参加活动</option>
                      	  <option value="xx展会活动">xx展会活动</option>
                      	  <option value="zz91_2012废金属交流会">zz91_2012废金属交流会</option>
                      	  <option value="zz91_2012台州塑交会">zz91_2012台州塑交会</option>
                      	  <option value="ZZ91_2013台州塑交会">ZZ91_2013台州塑交会</option>
                      	  <option value="zz91_2014台州塑交会">zz91_2014台州塑交会</option>
                      	  <option value="zz91_2015台州塑交会">zz91_2015台州塑交会</option>
                          
                      </select>
                      <script>selectOption("active_flag","<%=fromname%>")</script>
                      </td>
                <td align="right" nowrap>电子邮箱
                  <input name="crmCheck" type="checkbox" id="newemailCheck" value="9" onClick="checkmod(this)"></td>
                <td>
                  <%
					  if rscom("com_email")="recyclinginfo@gmail.com" then
					  redo="readonly"
					  else
					  redo=""
					  end if
					  %> <input name="newemail" type="text" class="text" id="newemail" readonly value="<%=trim(rscom("com_email"))%>" size="30" maxlength="48" <%=redo%>> 
                  <input name="cemail" type="hidden" id="cemail" value="<%=trim(rscom("com_email"))%>"></td>
                </tr>
              <tr> 
                <td align="right">网站
                  <input name="crmCheck" type="checkbox" id="cwebCheck" value="10" onClick="checkmod(this)"></td>
                <td colspan="3"> 
                  <input name="cweb" type="text" class="text" id="cweb" readonly value="<%=trim(rscom("com_website"))%>" size="20" maxlength="255">二级域名
                  <!--<input name="crmCheck" type="checkbox" id="com_subnameCheck" value="15" onClick="checkmod(this)"><input name="com_subname" type="text" class="text" id="com_subname" value="<%=trim(rscom("com_subname"))%>" size="10" readonly> 
                          <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">-->
                  <a href="http://<%=trim(rscom("com_subname"))%>.zz91.com" target="_blank"><%=trim(rscom("com_subname"))%>.zz91.com</a></td>
                <td align="right">联系人
                  <input name="crmCheck" type="checkbox" id="ccontactpCheck" value="11" onClick="checkmod(this)"></td>
                <td>
                  <input name="ccontactp" type="text" class="text" id="ccontactp" readonly value="<%=replacequot1(trim(rscom("com_contactperson")))%>" size="20" maxlength="48">
                  <%
						  select case rscom("com_desi")
						  case "先生"
						  deselect1="selected"
						  deselect2=""
						  case "女士"
						  deselect1=""
						  deselect2="selected"
						  end select
						  
						  %>
                  <input name="crmCheck" type="checkbox" id="cdesiCheck" value="16" onClick="checkmod(this)">
                  <select name="cdesi" id="cdesi">
                    <option value="先生" <%response.Write(deselect1)%>>先生</option>
                    <option value="女士" <%response.Write(deselect2)%>>女士</option>
                    </select>						  </td>
                </tr>
              
              
              <tr> 
                <td align="right">公司简介
                  <input name="crmCheck" type="checkbox" id="cintroduceCheck" value="13" onClick="checkmod(this)"></td>
                <td colspan="3" align="left" valign="top">
                  <textarea name="cintroduce" cols="50" rows="4" readonly id="cintroduce"><%=replacequot1(trim(rscom("com_intro")))%></textarea></td>
                <td align="right">主营业务
                  <input name="crmCheck" type="checkbox" id="cproductslist_enCheck" value="14" onClick="checkmod(this)"></td>
                <td align="left" valign="top">
                  
                  <textarea name="cproductslist_en" cols="50" readonly rows="4" id="cproductslist_en"><%=replacequot1(trim(rscom("com_productslist_en")))%></textarea>						</td>
                </tr>
              
              
              
              
              <tr align="center">
                <td align="right" nowrap></td>
                <td colspan="5" align="left">
                  
                  
                  </td>
                </tr>
              </table>
            
              
              <table width="100%" border="0" cellspacing="0" cellpadding="2" bgcolor="#E4EDFE">
                <tr>
                  <td height="25">行业<input name="crmCheck" type="checkbox" id="ckeywordsCheck" value="12" onClick="checkmod(this)"></td>
                  <td>
                    <select name="ckeywords" id="ckeywords" style="width:100px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="selecttextname(this);changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue();" >
                      <option value="">请选择</option>
                      <option value="1">废金属</option>
                      <option value="2">废塑料</option>
                      <option value="3">废旧轮胎与废橡胶</option>
                      <option value="4">废纺织品与废皮革</option>
                      <option value="5">废纸</option>
                      <option value="6">废电子电器</option>
                      <option value="10">废玻璃</option>
                      <option value="12">废旧二手设备</option>
                      <option value="14">其他废料</option>
                      <option value="15">服务</option>
                      </select>
                    <script>selectOption("ckeywords","<%=rscom("com_keywords")%>")</script>
                    </td>
                  <td>类型                    
                    <input name="ckindCheck" type="checkbox" id="ckindCheck" value="17" onClick="checkmod(this)"></td>
                  <td><div id="ckindmain" style="float:left"></div>
                    
                    <%
					  ckeywords=rscom("com_keywords")
					  if ckeywords="" then ckeywords=0
					  com_ctr_id=rscom("com_ctr_id")
					  %>
                    <script>changekind("<%=ckeywords%>","ckind","ckindmain")</script>
                    <script>selectOption("ckind","<%=rscom("com_kind")%>")</script></td>
                  <td>国家/地区
                    <input name="crmCheck" type="checkbox" id="countryselectCheck" value="3" onClick="checkmod(this)"></td>
                  <td><select name="countryselect" id="countryselect" onChange="selectcountry(this.value);selecttextname(this)">
                    <option value="1" <%if com_ctr_id="1" then response.Write("selected")%>>中国</option>
                    <option value="0" <%if com_ctr_id<>"1" then response.Write("selected")%>>其他国家/地区</option>
                    </select></td>
                  <td>省市
                    <input name="crmCheck" type="checkbox" id="cprovinceCheck" value="5" onClick="checkmod(this)"></td>
                  <td><div id="othercountrys" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
                    <select name="ccountry" id="ccountry" onChange="selecttextname(this)">
                      <option value="2" >台湾</option>
                      <option value="3" >香港</option>
                      <option value="218" >美国</option>
                      <option value="223" >越南</option>
                      <option value="214" >乌克兰</option>
                      <option value="209" >土耳其</option>
                      <option value="156" >尼日利亚</option>
                      <option value="162" >巴基斯坦</option>
                      <option value="168" >菲律宾</option>
                      <option value="169" >波兰</option>
                      <option value="182" >新加坡</option>
                      <option value="16" >澳大利亚</option>
                      <option value="43" >加拿大</option>
                      <option value="67" >英国</option>
                      <option value="100" >印度</option>
                      <option value="101" >印尼</option>
                      <option value="102" >伊朗</option>
                      <option value="105" >以色列</option>
                      <option value="111" >约旦</option>
                      <option value="115" >朝鲜</option>
                      <option value="116" >韩国</option>
                      <option value="132" >马来西亚</option>
                      <option value="99" >冰岛</option>
                      <option value="119" >老挝</option>
                      <option value="134" >马里</option>
                      <option value="145" >缅甸</option>
                      <option value="147" >瑙鲁</option>
                      <option value="109" >日本</option>
                      <option value="81" >德国</option>
                      <option value="83" >加纳</option>
                      <option value="85" >希腊</option>
                      <option value="89" >关岛</option>
                      <option value="95" >海地</option>
                      <option value="65" >埃及</option>
                      <option value="74" >斐济</option>
                      <option value="75" >芬兰</option>
                      <option value="76" >法国</option>
                      <option value="79" >加篷</option>
                      <option value="48" >乍得</option>
                      <option value="49" >智利</option>
                      <option value="52" >刚果</option>
                      <option value="56" >古巴</option>
                      <option value="59" >捷克</option>
                      <option value="60" >丹麦</option>
                      <option value="21" >巴林</option>
                      <option value="28" >贝宁</option>
                      <option value="30" >不丹</option>
                      <option value="36" >巴西</option>
                      <option value="37" >汶莱</option>
                      <option value="178">沙特</option>
                      <option value="187" >南非</option>
                      <option value="167" >秘鲁</option>
                      <option value="159" >挪威</option>
                      <option value="160" >阿曼</option>
                      <option value="196" >苏丹</option>
                      <option value="204" >泰国</option>
                      <option value="205" >多哥</option>
                      <option value="199" >瑞典</option>
                      <option value="200" >瑞士</option>
                      <option value="226" >也门</option>
                      <option value="231" >蒙古</option>
                      <option value="229" >赞比亚</option>
                      <option value="188" >西班牙</option>
                      <option value="175" >卢旺达</option>
                      <option value="32" >伯奈尔</option>
                      <option value="40" >布隆迪</option>
                      <option value="42" >喀麦隆</option>
                      <option value="29" >百慕大</option>
                      <option value="24" >巴布达</option>
                      <option value="26" >比利时</option>
                      <option value="27" >伯利兹</option>
                      <option value="22" >孟加拉</option>
                      <option value="15" >阿鲁巴</option>
                      <option value="20" >巴哈马</option>
                      <option value="18" >奥地利</option>
                      <option value="4" >阿富汗</option>
                      <option value="61" >吉布提</option>
                      <option value="57" >古拉索</option>
                      <option value="51" >科摩罗</option>
                      <option value="45" >佛得角</option>
                      <option value="80" >冈比亚</option>
                      <option value="94" >圭亚那</option>
                      <option value="91" >根西岛</option>
                      <option value="92" >几内亚</option>
                      <option value="86" >格陵兰</option>
                      <option value="112" >柬埔寨</option>
                      <option value="124" >利比亚</option>
                      <option value="8" >安道尔</option>
                      <option value="9" >安哥拉</option>
                      <option value="10" >安圭拉</option>
                      <option value="11" >安提瓜</option>
                      <option value="13" >阿根廷</option>
                      <option value="14" >亚美尼亚</option>
                      <option value="19" >阿塞邦疆</option>
                      <option value="23" >巴巴多斯</option>
                      <option value="25" >白俄罗斯</option>
                      <option value="31" >玻利维亚</option>
                      <option value="33" >波斯尼亚</option>
                      <option value="35" >博茨瓦纳</option>
                      <option value="38" >保加利亚</option>
                      <option value="46" >开曼群岛</option>
                      <option value="50" >哥伦比亚</option>
                      <option value="53" >库克群岛</option>
                      <option value="55" >克罗地亚</option>
                      <option value="58" >塞浦路斯</option>
                      <option value="62" >多米尼加</option>
                      <option value="64" >厄瓜多尔</option>
                      <option value="66" >萨尔瓦多</option>
                      <option value="70" >爱沙尼亚</option>
                      <option value="73" >法罗群岛</option>
                      <option value="82" >格鲁吉亚</option>
                      <option value="87" >格林纳达</option>
                      <option value="88" >瓜德罗普</option>
                      <option value="90" >危地马拉</option>
                      <option value="107" >象牙海岸</option>
                      <option value="97" >洪都拉斯</option>
                      <option value="98" >匈牙利</option>
                      <option value="103" >伊拉克</option>
                      <option value="104" >爱尔兰</option>
                      <option value="106" >意大利</option>
                      <option value="108" >牙买加</option>
                      <option value="110" >泽西岛</option>
                      <option value="113" >肯尼亚</option>
                      <option value="114" >基里巴斯</option>
                      <option value="117" >科威特</option>
                      <option value="120" >拉脱维亚</option>
                      <option value="121" >黎巴嫩</option>
                      <option value="122" >莱索托</option>
                      <option value="123" >利比里亚</option>
                      <option value="125" >列支敦士登</option>
                      <option value="126" >立陶宛</option>
                      <option value="127" >卢森堡</option>
                      <option value="128" >马斯顿</option>
                      <option value="131" >马拉维</option>
                      <option value="133" >马尔代夫</option>
                      <option value="135" >马耳他 </option>
                      <option value="136" >毛里裘斯</option>
                      <option value="138" >马提尼克</option>
                      <option value="140" >马约特岛</option>
                      <option value="141" >墨西哥</option>
                      <option value="142" >摩纳哥</option>
                      <option value="143" >摩洛哥</option>
                      <option value="144" >莫桑比克 </option>
                      <option value="146" >纳米比亚</option>
                      <option value="148" >尼泊尔</option>
                      <option value="149" >荷兰</option>
                      <option value="153" >新西兰</option>
                      <option value="154" >尼加拉瓜</option>
                      <option value="155" >尼日尔</option>
                      <option value="157" >纽埃岛</option>
                      <option value="163" >巴勒斯坦</option>
                      <option value="164" >巴拿马</option>
                      <option value="166" >巴拉圭</option>
                      <option value="130" >葡萄牙</option>
                      <option value="170" >波多黎各</option>
                      <option value="171" >卡塔尔</option>
                      <option value="172" >留尼旺达</option>
                      <option value="173" >罗马尼亚</option>
                      <option value="174" >俄罗斯</option>
                      <option value="176" >塞班岛</option>
                      <option value="179" >塞内加尔</option>
                      <option value="180" >塞舌尔</option>
                      <option value="181" >塞拉利昂</option>
                      <option value="183" >斯洛伐克</option>
                      <option value="186" >索马里 </option>
                      <option value="189" >斯里兰卡</option>
                      <option value="192" >圣基茨</option>
                      <option value="193" >圣卢西亚</option>
                      <option value="194" >圣马丁岛</option>
                      <option value="195" >圣文豪特</option>
                      <option value="197" >苏里兰</option>
                      <option value="198" >斯威士兰</option>
                      <option value="201" >叙利亚</option>
                      <option value="202" >塔希提</option>
                      <option value="203" >坦桑尼亚</option>
                      <option value="206" >汤加</option>
                      <option value="208" >突尼斯</option>
                      <option value="212" >图瓦卢</option>
                      <option value="215" >阿联酋</option>
                      <option value="213" >乌干达</option>
                      <option value="216" >乌拉圭</option>
                      <option value="220" >瓦努阿图</option>
                      <option value="221" >梵蒂冈</option>
                      <option value="222" >委内瑞拉</option>
                      <option value="224" >维珍岛</option>
                      <option value="225" >西萨摩亚</option>
                      <option value="227" >南斯拉夫</option>
                      <option value="228" >扎伊尔</option>
                      <option value="230" >津巴布韦</option>
                      <option value="235" >其他</option>
                      <option value="236" >其他国家</option>
                      <option value="150" >荷兰安的列斯群岛</option>
                      <option value="34" >波斯尼亚和黑塞哥维那 </option>
                      <option value="211" >特克斯和凯科斯群岛</option>
                      <option value="207" >特立尼达和多巴哥</option>
                      <option value="177" >圣多美和普林西比</option>
                      <option value="165" >巴布亚新几内亚</option>
                      <option value="78" >法属波利尼西亚</option>
                      <option value="12" >安提瓜和巴布达 </option>
                      <option value="63" >多米尼加共和国</option>
                      <option value="219" >乌兹别克斯坦</option>
                      <option value="217" >美属维尔群岛</option>
                      <option value="84" >直布罗陀海峡</option>
                      <option value="118" >吉尔吉斯斯坦</option>
                      <option value="158" >北马绍尔群岛</option>
                      <option value="191" >圣万斯达裘斯</option>
                      <option value="151" >荷属安的列斯</option>
                      <option value="152" >新喀里多尼亚</option>
                      <option value="190" >圣巴夫林米</option>
                      <option value="5" >阿尔巴尼亚</option>
                      <option value="6" >阿尔及利亚</option>
                      <option value="7" >美属萨摩亚</option>
                      <option value="39" >布基纳法索</option>
                      <option value="210" >土库曼斯坦</option>
                      <option value="185" >所罗门群岛</option>
                      <option value="184" >斯洛文尼亚</option>
                      <option value="44" >加那利群岛</option>
                      <option value="47" >中非共和国</option>
                      <option value="54" >哥斯达黎加</option>
                      <option value="68" >赤道几内亚</option>
                      <option value="69" >厄立特里亚</option>
                      <option value="139" >毛里坦尼亚</option>
                      <option value="72" >福克兰群岛</option>
                      <option value="71" >埃塞俄比亚</option>
                      <option value="77" >法属圭亚拉</option>
                      <option value="137" >马绍尔群岛</option>
                      <option value="129" >马达加斯加</option>
                      <option value="96" >黑塞哥维那</option>
                      <option value="93" >几内亚比绍</option>
                      <option value="232" >哈萨克斯坦</option>
                      </select>
                    <script>selectOption("ccountry","<%=com_ctr_id%>")</script>
                    <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" value="<%=replacequot1(rscom("com_province"))%>"/>
                    </div>
                    <div id="mycountry" <%if com_ctr_id="1" or isnull(com_ctr_id) or com_ctr_id="" then response.Write("style=") else response.Write("style=display:none")%>>
                      <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
                      <input type="hidden" name="province" id="province">
                      <input type="hidden" name="city" id="city">
                      <input type="hidden" name="cprovince" id="cprovince" value="0">
                      <%
                        '--------------------------根据地区名取得地区编码
                        function getprovince(meno)
                            sqlgp="select code from cate_province where meno='"&meno&"'"
                            set rsgp=conn.execute(sqlgp)
                            if not rsgp.eof or not rsgp.bof then
                                getprovince=rsgp(0)
                            end if
                            rsgp.close
                            set rsgp=nothing
                        end function
                        cprovince=replacequot1(rscom("com_province"))
                        Arrprovince=split(cprovince,"|")
                        if ubound(Arrprovince)>0 then
                            province=Arrprovince(0)
                            city=Arrprovince(1)
                        end if
                        '--------------------------直接取的地区编码
                        sqlh="select * from temp_comp_ProvinceID where com_id="&idprod
                        set rsh=conn.execute(sqlh)
                        if not rsh.eof or not rsh.bof then
                            provinceID=rsh("province")
                            cityID=rsh("city")
                            GardenID=rsh("Garden")
                        else
							sqlh1="select * from comp_ProvinceID where com_id="&idprod
							set rsh1=conn.execute(sqlh1)
							if not rsh1.eof or not rsh1.bof then
								provinceID=rsh1("province")
								cityID=rsh1("city")
								GardenID=rsh1("Garden")
							else
								provinceID=getprovince(province)
								cityID=getprovince(city)
								GardenID=""
							end if
							rsh1.close
							set rsh1=nothing
                        end if
                        rsh.close
                        set rsh=nothing
                        '---------------------------------------
                        %>
                      <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="ckeywords";//行业ID号
                            getprovincevalue();
                        </script>
                      <script>
                        function getprovincename()
                        {
							selecttextname(document.getElementById("cprovince"))
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
							
                        }
                        </script>
                      </div>
                    </td>
                  </tr>
                </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="2" bgcolor="#B3CCFC">
              <tr>
                <td width="50">主营方向</td>
                <td width="210" align="left">
                  <%
	sqlreg="select salestype,salestext,buytext,GoodCom,abroadCom,cmeet from temp_comp_salestype where com_id="&idprod
	set rsreg=conn.execute(sqlreg)
	if not rsreg.eof or not rsreg.bof then
		salestype=rsreg("salestype")
		salestext=rsreg("salestext")
		buytext=rsreg("buytext")
		GoodCom=rsreg("GoodCom")
		abroadCom=rsreg("abroadCom")
		cmeet=rsreg("cmeet")
	else
		sqlreg1="select salestype,salestext,buytext,GoodCom,abroadCom,cmeet from comp_salestype where com_id="&idprod
		set rsreg1=conn.execute(sqlreg1)
		if not rsreg1.eof or not rsreg1.bof then
			salestype=rsreg1("salestype")
			salestext=rsreg1("salestext")
			buytext=rsreg1("buytext")
			GoodCom=rsreg1("GoodCom")
			abroadCom=rsreg1("abroadCom")
			cmeet=rsreg1("cmeet")
		end if
		rsreg1.close
		set rsreg1=nothing
	end if
	rsreg.close
	set rsreg=nothing
	%>
                  <input type="radio" name="salesType" value="1" onClick="changesales(1)" <%if salestype="1" then response.Write("checked")%>/>
                  销售
                  <input type="radio" name="salesType" value="2" onClick="changesales(2)" <%if salestype="2" then response.Write("checked")%>/>
                  采购
                  <input name="salesType" type="radio" value="0" onClick="changesales(0)" <%if salestype="0" then response.Write("checked")%>/>
                  两者都有
                  </td>
                <td>

                  <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabzhuyin1">
                    <tr>
                      <td height="22"><font color="#FF0000">销售</font>的产品（提供的服务）</td>
                      </tr>
                    <tr>
                      <td height="22"><input name="salestext" type="text" class="textt" id="salestext" size="30" maxlength="30"   value="<%=salestext%>"/>
                        (30个字符内)</td>
                      </tr>
                    </table>
                  </td>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0"  id="tabzhuyin2">
                  <tr>
                    <td height="22"><font color="#FF0000">采购</font>的产品（需要的服务）</td>
                    </tr>
                  <tr>
                    <td height="22"><input name="buytext" type="text" class="textt" id="buytext" size="30" maxlength="30" value="<%=buytext%>" />
                      (30个字符内)</td>
                    </tr>
                  </table>
                  <script>changesales(<%=salestype%>)</script></td>
                </tr>
              </table>
            <%
sqlf="select remark from comp_fine where com_id="&idprod&""
set rsf=conn.execute(sqlf)
if not rsf.eof or not rsf.bof then
%>
            <table width="100%" border="0" cellspacing="0" cellpadding="2">
              <tr>
                <td height="20">
                  收集客户备注信息<%response.Write(rsf(0))%>
                  </td>
                </tr>
              </table>
            <%
end if
rsf.close
set rsf=nothing
%>
            <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#8FB2F6">
              <tr>
                <td bgcolor="#E4EDFE">最近登录</td>
                <td bgcolor="#E4EDFE">登录次数</td>
                <td bgcolor="#E4EDFE">注册时间</td>
                <td bgcolor="#E4EDFE">查看信息</td>
                <td bgcolor="#E4EDFE">到期时间</td>
                </tr>
              <tr>
                <td bgcolor="#FFFFFF"><%=lastlogintime%></td>
                <td bgcolor="#FFFFFF"><%=logincount%></td>
                <td bgcolor="#FFFFFF"><%=com_regtime%></td>
                <td bgcolor="#FFFFFF"><%=vcount%></td>
                <td bgcolor="#FFFFFF"><%=vip_dateto%></td>
                </tr>
              </table>
            
            <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#FFCC00">
              <tr>
                <td width="100" align="right" bgcolor="#ebebeb">公海除杂</td>
                <td bgcolor="#FFFF99">
                <%
				selectgonghai_out=""
				sqlo="select property_value from crm_category_info where property_id="&idprod&" and property_value like '1004____'"
				'response.Write(sqlo)
				set rso=conn.execute(sqlo)
				if not rso.eof or not rso.bof then
					while not rso.eof
						selectgonghai_out=selectgonghai_out&rso(0)&","
					rso.movenext
					wend
				end if
				rso.close
				set rso=nothing
				
				sqlz="select code,meno from crm_category where code like '1004____' and closeflag=0 order by ord asc"
				set rsz=conn.execute(sqlz)
				if not rsz.eof or not rsz.bof then
				while not rsz.eof
					
				%>
                <input type="checkbox" name="gonghai_out" id="gonghai_out" value="<%=rsz("code")%>"><%=rsz("meno")%>
                <%
				rsz.movenext
				wend
				end if
				rsz.close
				set rsz=nothing
				%>
                <script>selectCheckBox("gonghai_out","<%=selectgonghai_out%>")</script>
                </td>
              </tr>
              <tr>
                <td width="100" align="right" bgcolor="#ebebeb">客户分类</td>
                <td bgcolor="#FFCC00">
                <%
				selectzdycomp_out=""
				if left(dotype,3)="vap" then
					atype="2"
				else
					atype="1"
				end if
				sqlo="select property_value from crm_category_info where property_id="&idprod&" and property_value like '1005____'"
				set rso=conn.execute(sqlo)
				if not rso.eof or not rso.bof then
					while not rso.eof
						selectzdycomp_out=selectzdycomp_out&rso(0)&","
					rso.movenext
					wend
				end if
				rso.close
				set rso=nothing
				
				sqlz="select code,meno from crm_category where code like '1005____' and closeflag=0 and type="&atype&" order by ord asc"
				set rsz=conn.execute(sqlz)
				if not rsz.eof or not rsz.bof then
				while not rsz.eof
					
				%>
                <input type="checkbox" name="zdycomp" id="zdycomp" value="<%=rsz("code")%>"><%=rsz("meno")%>
                <%
				rsz.movenext
				wend
				end if
				rsz.close
				set rsz=nothing
				%>
                <script>selectCheckBox("zdycomp","<%=selectzdycomp_out%>")</script>
                <input type="hidden" name="usertype" value="<%=atype%>">
                </td>
              </tr>
            </table>
            
            
            </td>
          </tr>
        
        </table>
      </td>
  </tr>
</table>

<!--是否做过广告-->
<iframe id="aduse" name="aduse"  style="HEIGHT: 0px; VISIBILITY: inherit; WIDTH: opx; Z-INDEX: 2" frameborder="0" src="crm_aduse.asp?com_id=<%=rscom("com_id")%>&com_email=<%=rscom("com_email")%>"></iframe> 
<%
if contacttype="13" then
	tabdip=""
	tabdip1="style='display:none'"
else
	tabdip=""
	tabdip1=""
end if
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25" align="center" bgcolor="#243F74">

                        <%
						if (guangLiangFlag=3 or isshFlag=1) and left(dotype,3)<>"vap" then
						else
						%>
						<input type="button" id="jlButton" class=button name="Submit" onClick="jlcontact()" value="销售记录">
          <%
						end if
						%>
                        <input name="contactflag" type="hidden" id="contactflag" value="0">
                        <input name="recordflag" type="hidden" id="recordflag" value="0">
						<input name="lxcontactflag" type="hidden" id="lxcontactflag" value="0">
						<input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
<input type="button" class=button name="Submit" id="lxButton" onClick="lxcontact()" value="增加联系人">
						<input type="button" name="Submit2" class=button value="查看服务记录" onClick="window.open('http://admin1949.zz91.com/web/zz91/salecrm/viewInfo.htm?companyId=<%=idprod%>&email=<%=trim(rscom("com_email"))%>','_black','')">
<!--&nbsp;&nbsp;<input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('http://admin.zz91.com/admin1/adv/localADlistStat.asp?email=<%=trim(rscom("com_email"))%>')" value="广告使用">&nbsp;&nbsp;		-->
					<input name="Submit4" type="submit" class="button" value=" 保存 " id="submitsave2" style="font-size:14px; font-weight:bold; color:#FF0"> 
                    <input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('rizhi/salesHistory.asp?com_id=<%=trim(rscom("com_id"))%>')" value="客户日志">
                    <input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('recordservice/recordlist.asp?com_id=<%=trim(rscom("com_id"))%>')" value="查看录音数据">
                    <%
					if left(dotype,3)="sms" then
					%>
                    <!--<input type="button" class="button" onClick="window.open('http://admin1949.zz91.com/reborn-admin/sms/subscribe/addOrEditSMSSubscribe.htm?companyId=<%=rscom("com_id")%>&account=<%=rscom("com_email")%>')" value="短信订阅">-->
                    <%
					end if
					%>
                    <input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="关闭"></td>
  </tr>
</table>
<%dis="display:none"%>
<div id="jlflag" class="searchtable" style="<%=dis%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFDBA4">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="100" align="right">下次联系时间</td>
    <td><script language=javascript>createDatePicker("contactnext_time",true,Date(),false,false,false,true)</script></td>
    <td width="150" align="right">联系情况：</td>
    <td><input type='radio' name='contacttype' value="13" onClick="changeTab(13)" <%if contacttype="13"  then response.write("checked")%>/>      有效联系
      <input type='radio' name='contacttype' onClick="changeTab(12)" value="12" <%if contacttype="12"  then response.write("checked")%>/>
      无效联系</td>
    
    <td width="300"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabinfo1" <%=tabdip1%>>
  <tr>
    <td width="5">&nbsp;</td>
    <td bgcolor="#CCCC00"><input type='radio' name='c_Nocontact' value='5' />无进展<input type='radio' name='c_Nocontact' value='1' />无人接听<input type='radio' name='c_Nocontact' value='2' onClick="alert('提醒：请注意此选项，请再次确认该客户手机和座机“号码错误”，选择完后该客户将不会在公海和前台出现。如有发现该客户乱选，责任自负。')" />号码错误<input type='radio' name='c_Nocontact' value='3' />停机<input type='radio' name='c_Nocontact' value='4' />关机</td>
  </tr>
</table></td>
  </tr>
</table></td>
  </tr>
</table>

<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tabinfo" <%=tabdip%>>
                          <tr align="center"> 
                        <td width="100" align="right" bgcolor="#FFFFCC">客户等级</td>
                        <td align="left" bgcolor="#FFFFCC">
                          
                          
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="200">
                           <%if left(dotype,3)="vap" then%>
                           <select name="com_rank" id="com_rank" style="background:#ebebeb; width:150px;" onChange="changestar(this.value)">
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
                          <select name="com_rank" id="com_rank" style="background:#ebebeb" onChange="changestar(this.value)">
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
                          <script>selectOption("com_rank","<%=com_rank%>")</script></td>
                              <td>
                              <%if left(dotype,3)="vap" then%>
                              <div>
                              <input type="radio" name="paystats" id="paystats" value="0" onClick="openpayinput(this.value,0)" checked>无
                              <input type="radio" name="paystats" id="paystats" value="1" onClick="openpayinput(this.value,1)">
                              必杀期客户
                              <input type="radio" name="paystats" id="paystats" value="2" onClick="openpayinput(this.value,1)">
                              黄金客户
                              <input type="radio" name="paystats" id="paystats" value="3" onClick="openpayinput(this.value,1)">
                              新客户
                              <input type="radio" name="paystats" id="paystats" value="4" onClick="openpayinput(this.value,1)">
                              过期客户
                              <%
							  dis1="style='display:none'"
							  %>
                              <div id="paytyletxt" style="color:#F00"></div>
                              
                              </div>
                              <%end if%>
                              </td>
                              <td align="left">
                              <%
			if (session("userid")<>"1315" or session("userid")="13" or session("userid")="10") then
			%>
            <table border="0" cellspacing="0" cellpadding="3">
              <tr>
                <td width="10" nowrap bgcolor="#FFCC99"><input type="checkbox" name="idcbaoliu" id="idcbaoliu" <%if idcbaoliu=1 then response.Write("checked")%>></td>
                <td nowrap bgcolor="#FFCC99"><font color="#FF3300">开通创业版再生通</font></td>
                
                
              </tr>
            </table>
            <%
			end if
			%>
                              </td>
                            </tr>
                          </table></td>
                        </tr>
                        <%
						if com_rank<>"5" or left(dotype,3)="vap" then
							wxstyle=" style='display:none'"
						else
							wxstyle=""
						end if
						%>
                          <%if left(dotype,3)="vap" then%>
                          <tr align="center">
                            <td align="right" bgcolor="#FFFFCC"></td>
                            <td align="left" bgcolor="#FFFFCC">
                            <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#999999" id="payinput" <%=dis1%>>
                                <tr>
                                  <td bgcolor="#f2f2f2">服务类型</td>
                                  <td bgcolor="#f2f2f2">开通时间</td>
                                  <td bgcolor="#f2f2f2">结束时间</td>
                                  <td bgcolor="#f2f2f2">付款情况</td>
                                  <td bgcolor="#f2f2f2">金额</td>
                                  <td bgcolor="#f2f2f2">备注</td>
                                </tr>
                                <tr>
                                  <td bgcolor="#FFFFFF"><input name="paykind" type="checkbox" id="paykind" value="黄展" />
      黄展
      <input name="paykind" type="checkbox" id="paykind" value="标王" />
      标王
      <br>
      <input name="paykind" type="checkbox" id="paykind" value="独家" />
      独家
      <input name="paykind" type="checkbox" id="paykind" value="首页" />
      首页
      <input name="paykind" type="checkbox" id="paykind" value="其他" />
      其他</td>
                                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("payfromdate",true,"",false,true,true,true)</script></td>
                                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("paytodate",true,"",false,true,true,true)</script></td>
                                  <td bgcolor="#FFFFFF"><input type="radio" name="donation" id="donation" value="1" />
      赠送<br>
      <input type="radio" name="donation" id="donation" value="0" />
付费</td>
                                  <td bgcolor="#FFFFFF"><input name="money" type="text" id="money" value="0" size="5" />
                                    <br>
    (必须是数字，赠送填0)</td>
                                  <td bgcolor="#FFFFFF"><input name="bz" type="text" id="bz" size="10" /></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <%end if%>
                          <tr align="center" <%=wxstyle%> id="change5star">
                            <td align="right" bgcolor="#FFFFCC">五星条件及流程</td>
                            <td align="left" bgcolor="#FFFFCC">
                            <table border="0" cellspacing="0" cellpadding="0" style="background-color:#CF0">
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
										strreson="是"
									else
										strreson="<font color=#ff0000>否</font>"
									end if
									response.Write("<script>//parent.showstar5open("&com_id&");</script>")
								else
									resoncheck1=""
									resoncheck2=""
								end if
								
								rss.close
								set rss=nothing
								
							  %>
                              <tr>
                                <td nowrap><%=rscate("meno")%><input type="hidden" name="reson_typeid" id="reson_typeid" value="<%=rscate("code")%>"></td>
                                <td><input type="radio" name="reson_check<%=rscate("code")%>" id="reson_check<%=rscate("code")%>" value="1" <%=resoncheck1%>>
                                  是
                                  <input type="radio" name="reson_check<%=rscate("code")%>" id="reson_check<%=rscate("code")%>" value="0" <%=resoncheck2%>>
                                  否
                                  <input type="hidden" name="resontel" id="resontel" value="<%=rscate("meno")%>">
                                </td>
                              </tr>
                              <%
							  i=i+1
							  rscate.movenext
							  wend
							  end if
							  rscate.close
							  set rscate=nothing
							  %>
                              
                              <input type="hidden" name="reson_checklist" id="reson_checklist" value="0">
                              
                              
                            </table>
                            </td>
                          </tr>
                          <tr align="center" <%=wxstyle%>>
                            <td align="right" bgcolor="#FFFFCC">是否拖单/毁单</td>
                            <td align="left" bgcolor="#FFFFCC">
                            
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="拖单">
                            拖单
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="毁单">
                            毁单 &nbsp;&nbsp;&nbsp;&nbsp;拖单/毁单原因
                            <input name="tuohuireson" type="text" id="tuohuireson" size="40"></td>
                          </tr>
                          
                     
                      <%if left(dotype,3)="vap" then%>
                      <!--<tr align="center">
                        <td align="right" bgcolor="#FFFF00">无增值意愿：</td>
                        <td colspan="5" align="left" bgcolor="#FFFF00">
                        <%
						'sqly="select id from crm_notBussiness where com_id="&com_id&""
'						set rsy=conn.execute(sqly)
'						if not rsy.eof or not rsy.bof then
'							zengzhi=1
'						else
'							zengzhi=0
'						end if
'						rsy.close
'						set rs=nothing
						%>
                        <input type="radio" name="zengzhi" id="radio3" value="1" <%if zengzhi=1 then response.Write("checked")%>>
                          是
                        <input type="radio" name="zengzhi" id="radio4" value="0" <%if zengzhi=0 then response.Write("checked")%>>
                          否</td>
                      </tr>-->
                      <%end if%>
                      <tr align="center"> 
                        <td align="right" bgcolor="#FFFFCC">备注</td>
                        <td align="left" bgcolor="#FFFFCC">
                        
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="320" bgcolor="#FFCC00"><%
						'0 新签 1 BD部 2 续签 3 广告 4\ vap客户
						%><%if left(dotype,3)<>"vap" and left(dotype,3)<>"sms" then%><input type="radio" name="telflag" id="radio" value="0" checked>新签再生通<input type="radio" name="telflag" id="radio2" value="2">续签再生通<input name="telflag" type="radio" id="radio" value="4" >VAP销售<%elseif left(dotype,3)="vap" then%><input name="telflag" type="radio" id="radio" value="4" checked>VAP销售<%else%><input name="telflag" type="radio" id="radio" value="5" checked>SMS销售<%end if%>
                            <input name="seoflag" type="checkbox" id="seoflag">
                            SEO销售</td>
                            <td><textarea name="detail" cols="50" rows="2" id="detail" style="width:100%"></textarea></td>
                          </tr>
                        </table></td>
                      </tr>
              </table>
</div>	
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="lxflag"   style="<%=dis%>" class="searchtable">
  <tr>
    <td bgcolor="#FFCCCC"><table width="100%" border="0" cellspacing="0" cellpadding="3">
       <tr>
        <td align="right">手机<font color="#FF0000">(判单第一要素)</font>：</td>
        <td><input name="PersonMoblie" type="text" id="PersonMoblie"></td>
        <td align="right">电话<font color="#FF0000">(判单第二要素)</font>：</td>
        <td><input name="PersonTel" type="text" id="PersonTel"></td>
        <td align="right">开通公司名称：</td>
        <td><input name="personComname" type="text" id="personComname" value="" /></td>
      </tr>
      <tr>
        <td align="right" width="130">联系人：</td>
        <td nowrap><input name="PersonName" type="text" id="PersonName">
          <input name="PersonSex" type="radio" value="1" checked>
          先生
          <input type="radio" name="PersonSex" value="0">
          女士</td>
        <td align="right">关键联系人： </td>
        <td>是
          <input type="radio" name="PersonKey" value="1">
          不是
          <input name="PersonKey" type="radio" value="0" checked></td>
        <td align="right">职务：</td>
        <td><input name="PersonStation" type="text" id="PersonStation"></td>
      </tr>
     
      
      <tr>
        <td align="right">传真： </td>
        <td><input name="PersonFax" type="text" id="PersonFax"></td>
        <td align="right">其他联系方式：</td>
        <td><input name="PersonOther" type="text" id="PersonOther"></td>
        <td align="left">Email：</td>
        <td><input name="PersonEmail" type="text" id="PersonEmail"></td>
      </tr>
      <tr>
        <td align="right">备注：</td>
        <td colspan="5"><textarea name="PersonBz" rows="3" id="PersonBz"></textarea></td>
        </tr>
    </table></td>
  </tr>
</table>  
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td><iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 100px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="crm_personinfo.asp?com_id=<%=rscom("com_id")%>"></iframe></td>
  </tr>
</table>
<!--撞单情况不显示  begin-->

<%
if left(dotype,3)<>"1" then
if zhuangdanFlag=0 and guangLiangFlag<1 then
%>
<%
else
arrcomList=idprod&","&arrcomList
arrarrcomList=split(arrcomList,",")
%>
<div class="tishi" style="font-size:14px; line-height:25px; color:#F00">有<b><%=ubound(arrarrcomList)%></b>个手机或电话相似的客户，可能会发生撞单，请提交主管或区长处理！</div>
<table width="95%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#666666">
<tr>
    <td bgcolor="#CCCCCC" >&nbsp;</td>
    <td align="left" bgcolor="#CCCCCC">公司名称</td>
    <td align="left" bgcolor="#CCCCCC">email</td>
    <td align="left" bgcolor="#CCCCCC">电话</td>
    <td align="left" bgcolor="#CCCCCC">手机</td>
    <td align="left" bgcolor="#CCCCCC">挑入时间</td>
    <td align="left" bgcolor="#CCCCCC">最后联系时间</td>
    <td align="left" bgcolor="#CCCCCC">最后登录</td>
    <td align="left" bgcolor="#CCCCCC">登录次数</td>
    <td align="left" bgcolor="#CCCCCC">销售</td>
    <td align="left" bgcolor="#CCCCCC">审核</td>
    <td align="left" bgcolor="#CCCCCC">预审</td>
  </tr>
<%
'response.Write(groupID)
for i=0 to ubound(arrarrcomList)-1
acom_id=trim(arrarrcomList(i))
sqlc="select com_name,com_email,personid,com_tel,com_mobile,lastteldate,fdate,lastlogintime,logincount from v_salescomp where com_id="&acom_id
set rsc=conn.execute(sqlc)
if not rsc.eof or not rsc.bof then
	realname=""
	if rsc("personid")<>"" and not isnull(rsc("personid")) then
	  sqluser="select realname from users where id="&rsc("personid")&""
	  set rsuser=conn.execute(sqluser)
	  if not rsuser.eof or not rsuser.bof then
		  realname=rsuser("realname")
	  else
		  realname=""
	  end if
	  rsuser.close
	  set rsuser=nothing
	end if
	if i=0 then
		tdcolor="#99CCCC"
	else
		tdcolor="#FFFFFF"
	end if
	ysflag=0
	flag=0
	sqlg="select flag,ysflag from crm_compLink where groupid='"&groupID&"' and com_id="&acom_id
	set rsg=conn.execute(sqlg)
	if not rsg.eof or not rsg.bof then
		flag=rsg(0)
		ysflag=rsg(1)
	end if
	rsg.close
	set rsg=nothing
%>
  
  <tr>
    <td width="20" bgcolor="<%=tdcolor%>"><input name="glcom" type="checkbox" id="glcom" value="<%=acom_id%>" checked></td>
    <td bgcolor="<%=tdcolor%>"><a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=acom_id%>&dotype=<%=dotype%>&lmcode=<%=lmcode%>" target="_blank"><%=rsc("com_name")%></a></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("com_email")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("com_tel")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("com_mobile")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("fdate")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("lastteldate")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("lastlogintime")%></td>
    <td bgcolor="<%=tdcolor%>"><%=rsc("logincount")%></td>
    <td bgcolor="<%=tdcolor%>"><%=realname%></td>
    <td bgcolor="<%=tdcolor%>"><%=flag%></td>
    <td bgcolor="<%=tdcolor%>"><%=ysflag%></td>
  </tr>
  
<%
end if
rsc.close
set rsc=nothing
next
%>
<tr>
    <td colspan="12" bgcolor="#FFFFFF">
    <div class="tishi" style="font-size:14px; line-height:25px; color:#F00">
	<%
	'response.Write(guangLiangFlag)
	if guangLiangFlag=1 then
		response.Write("该客户已处理并经内容部审核")
	elseif guangLiangFlag=2 then
		response.Write("关联客户正在预审中......")
	elseif guangLiangFlag=3 then
		response.Write("该客户在通过关联审核时发现撞单，判单小组根据撞单制度预审判给 <b>"&YSpersonName&"</b> ")
	%>
    <%
	if isgonghai=0 then
	%>
    ，请看到后1个工作日内提交申诉或公海处理，如要申诉点击撞单申诉按钮就可<input type="button" name="button3" id="button3" value="撞单申诉" onClick="linksstack()">
    <%
	end if
	%>
	<%
	elseif guangLiangFlag=4 then
		response.Write("判单小组根据撞单制度预审判给你")
	%>
    
    
    <%
	else
	%>
    <input type="button" name="button2" id="button2" value="提交关联客户" onClick="getcom(this.form)">
    <%
	end if
	%></div>
    <%
	if guangLiangFlag=3 and isgonghai=0 then
		
		sqls="select top 10 a.ssdetail,a.fdate,b.realname from crm_compLink_ss as a,users as b where a.com_id="&com_id&" and a.personid=b.id order by a.fdate desc"
		set rss=conn.execute(sqls)
		if not rss.eof or not rss.bof then
			while not rss.eof
				response.Write(rss("ssdetail")&"<br>申述时间："&rss("fdate")&"|"&rss("realname")&"<br>")
			rss.movenext
			wend
		end if
		rss.close
		set rss=nothing
	end if
	%>
    </td>
    </tr>
</table>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <iframe id="linkss" name="linkss"  style="HEIGHT: 100px; display:none;WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="pipei/crm_compLink_ss.asp?com_id=<%=rscom("com_id")%>&GroupID=<%=GroupID%>"></iframe>
    </td>
  </tr>
</table>
<%
end if
end if
%>
<!--撞单情况不显示  end-->


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td>
    <%
	if left(dotype,3)="vap" then
		telflag=4
		telsrc="crm_tel_comp.asp?com_id="&rscom("com_id")&"&telflag=4"
	elseIF left(dotype,3)="sms" then
		telflag=5
		telsrc="crm_tel_comp.asp?com_id="&rscom("com_id")&"&telflag=5"
	else
		telflag=0
		telsrc="crm_tel_comp.asp?com_id="&rscom("com_id")&"&telflag=0"
	end if
	%>
    
    <iframe id=topt name=topt  style="HEIGHT: 500px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="<%=telsrc%>"></iframe>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center">
    <input name="Submit" type="submit" class="button" value="保存" id="submitsave">
    <input name="zhuangdanFlag" id="zhuangdanFlag" type="hidden" value="<%=zhuangdanFlag%>">
    <input name="guangLiangFlag" id="guangLiangFlag" type="hidden" value="<%=guangLiangFlag%>">
    
&nbsp;&nbsp;                          &nbsp;&nbsp; <input name="reset2" type="button" class="button" value="关闭" onClick="parent.window.close();"></td>
  </tr>
</table>
<br>
<br>
<br>
<br>
<br>
<br>
<br></td>
        </tr>
    </table>
	
	</td>
  </tr>
<%
				  rscom.close
				  set rscom=nothing
				  conn.close
				  set conn=nothing
				  %>              
</table></form>

</div>
</body>
</html>
<% 
function catesnew(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" order by ord asc"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"' onchange='setContent()'>"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then
	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function
%>
<script language="javascript" src="/admin1/crmlocal/viewhistory.asp?personid=<%=session("personid")%>&com_id=<%=idprod%>"></script>
<script language=javascript> 
document.body.oncopy=function(){ 
//event.returnValue=false; 
//var t=document.selection.createRange().text; 
//document.getElementById("copycontent").value=t
//formcopy.submit()
//var s=""; 
//window.clipboardData.setData('Text', t+s); 
} 
</script>

<form name="formcopy" method="post" action="rizhi/copysave.asp" target="copekuan">
  <input name="personid" type="hidden" id="personid" value="<%=session("personid")%>">
  <input type="hidden" name="copycontent" id="copycontent">
</form>
<iframe name="copekuan" src="" frameborder='0' width="0" height="0" scrolling="no" ></iframe>