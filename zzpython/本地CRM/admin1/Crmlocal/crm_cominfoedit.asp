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
		response.Write("�޴��û�")
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
	if left(dotype,3)<>"vap"   then'---------����VAP�ͻ�
		'sqla="select com_id from crm_3monthexpired_vipcomp where com_id="&idprod&" and com_id not in (select com_id from crm_bigcomp)"
'		set rsa=conn.execute(sqla)
'		if not rsa.eof or not rsa.bof then
'			if session("userid")<>"10" then
'				response.Write("3�����ڵ��ڵĿͻ���ת�����Ŀͷ�������ǩ�����ܲ鿴������")
'				response.End()
'			end if
'		end if
'		rsa.close
'		set rsa=nothing
		'-----------�жϲ��ڱ����ͻ��������ͨ�ͻ���ǩ���۲��ܲ鿴
		if session("personid")="93" or session("personid")="456" or session("userid")="10" or huangye_check="1" or Partuserid<>"0" then
		else
			sqla="select com_id from comp_zstinfo where com_id="&idprod&" and closeflag=0"
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				response.Write("�ÿͻ��Ѿ�ת��VAP�ͻ��⣬���Ѿ����ܲ����鿴��")
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
'			response.Write("�ÿͻ��������Ż�����ͻ�����SEO���ߺ�30�����ܴ򿪣�")
'			response.End()
'		end if
'		rsa.close
'		set rsa=nothing
			'sqla="select com_id from temp_baoliucomp where com_id="&idprod&" and com_id in (select com_id from crm_assign)"
'			set rsa=conn.execute(sqla)
'			if not rsa.eof or not rsa.bof then
'				if session("userid")<>"10" then
'					response.Write("�ÿͻ���������ͨ��ǩ�����ͻ�������ܲ鿴")
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
		'-----���������Ȩ��
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
			  '------------���˿ͻ�
			  if cstr(session("personid"))=cstr(ownerpersonid)  then
				  personopen=1
				  openqx=1
			  end if
			  '--------����Ȩ��
			  if partuserid<>"0" then
			  	  openqx=1
			  end if
			  if openqx=0 then
					response.Write("��û��Ȩ�޲鿴����Ϣ��<br><a href=###kang onclick='parent.window.close()'>�ر�</a>")
					response.End()
			  end if
		  end if
		  opengonghai=0
	  else
	    '-------δ���¼��ͻ�
	  	'sqlp="select com_id from crm_InsertCompWeb where com_id="&idprod&" and saletype=1 and ccheck=0"
'		set rsp=conn.execute(sqlp)
'		if not rsp.eof or not rsp.bof then
'			response.Write("�Ŀͻ���Ϣ��û��ͨ����ˣ���û��Ȩ�޲鿴����Ϣ��<br><a href=###kang onclick='parent.window.close()'>�ر�</a>")
'			response.End()
'		end if
'		rsp.close
'		set rsp=nothing
		'----------Ԥ�����Ƿ���ˣ�δ��˲��ܷŵ��ҵĿͻ����� 2011-3-1
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
'--------------�ж��Ƿ��Ƕ������ۿͻ�
sqlc="select com_id from comp_zstinfo where com_id="&idprod&" and closeflag=0"
set rsc=conn.execute(sqlc)
if not rsc.eof or not rsc.bof then
	iszst=1
else
	iszst=0
end if
rsc.close
set rsc=nothing
'-------------ȡ��˾������Ϣ
if idprod="" and cemail="" then
  response.end
end if
'----------�ж��Ƿ��ڹ���
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

'--------- ��ȡ����ѯ������
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
		response.Write("�޴���Ϣ��")
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
	'---------------�ж�ײ�����
	com_tel=trim(rscom("com_tel"))
	com_mobile=trim(rscom("com_mobile"))
	com_name=rscom("com_name")
	
	
	
	com_tel=clearcomptel(com_tel)
	'response.Write(com_tel)
	
	com_mobile=clearcompmobile(com_mobile)
'	
'	
'	arrcomList=""
	zhuangdanFlag=0'ײ����־
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
	com_name=replace(com_name,"���徭Ӫ","")
	com_name=replace(com_name,"(","")
	com_name=replace(com_name,")","")
	com_name=replace(com_name,"����","")
	com_name=replace(com_name,"��Ʒ�չ�","")
	com_name=replace(com_name,"��Ʒ����","")
	com_name=replace(com_name,"����","")
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
	'------------------���񲿹����Ѿ�������
	'flag=1 and shflag=1 ���չ���
	'flag=0 and shflag=1 ���ٹ�������ʾ
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
	'------���ձ������Ŀͻ�
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
		'-------------�Ѿ��ύ���񲿲��ȴ����
		sqlh="select GroupID,ysflag from crm_complink where shflag=0 and com_id="&com_id&""
		set rsh=conn.execute(sqlh)
		if not rsh.eof or not rsh.bof then
			guangLiangFlag=2
			GroupID=rsh(0)
			myYSFlag=rsh(1)
		end if
		rsh.close
		set rsh=nothing
		'----------�ж�Ԥ�����
		
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
		'---------�ж�Ԥ���˭
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
		'--------��Ԥ��ͻ�
		if myYSFlag=0 and YSFlag=1 then
			guangLiangFlag=3
		end if
		'--------Ԥ��ͻ�
		if myYSFlag=1 and YSFlag=1 then
			guangLiangFlag=4
		end if
	end if

end if
'---------------���ۻ�����Ϣ
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
'�ͻ��������ͳ�Ʊ�
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
'----�ͻ�ʹ����Ϣ
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
if comname="" then comname="�޹�˾����"
'---����Ļ
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
    <td bgcolor="#FFFFCC"  style="line-height:22px"><div><a href="#" style="font-size:14px; color:#F00; font-weight:bold" onClick="opencalltishi()">����ʹ�� CALLCENTER ¼���绰ϵͳ����ע��</a></div>
      <div id="calltishi" style="display:none">
      
    1�����и���λ�ã��뼰ʱ���Լ���������ϵĵ绰�������<br>
    2��Ϊ������ȷƥ��С�Ǻ�¼���ļ������ڹ��˵绰��10���ڱ���С�ǡ�<br>
    3�����㲦��ĵ绰���ڹ�˾��ϵ��ʽ�У����ڶ�Ӧ��������ʾ���ݿ���������д��Ӧ�����ݲ���С����ע����ϵ��ʽ��<br>
    4�����㲻��ͨ���绰��ʽ��ϵ�ͻ�������С����ע�����ʹ��ʲô��ʽ��ϵ���Է��˺š�<br>
    5���ͻ�������С�ǲ���˵��������С����ע��</div></td>
  </tr>
</table>

<div  id="page_cover" style="position:absolute;display:none;z-index:100; width:230px; height:59px; color:#cccccc; left: 0; top: 0;"></div>
<div id="alertmsg" style=" <%=dis%>"></div>
<div id="regform" style=" <%=dis%>;z-index:1002; top:100px; margin-left:100px; width:400px; height:100px;" > 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size:12px">
      <tr>
        <td height="29" background="../images/greentitlebg.gif" bgcolor="#003399" class="alerttitle"><div class="alerttitlen" id="alerttile">������Ʒϵ��</div></td>
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
	      <td align="center"><input type="submit" name="button" id="button" value="�ύ"></td>
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
                                        <TD nowrap class=selected onclick=secBoard(0)>������Ϣ</TD>
                                        <TD nowrap class=unselect><a href="http://admin1949.zz91.com/web/zz91/salecrm/viewInfo.htm?companyId=<%=request("idprod")%>&email=<%=trim(rscom("com_email"))%>" target="_blank">����(<font color="#FF0000"><%=offercount%></font>)����ѯ��(<font color="#FF0000"><%=sendcount%></font>)�յ�ѯ��(<font color="#FF0000"><%=resivercount%></font>)</a></TD>                                       
                                        <!--
                                        <TD nowrap class=unselect><a href="http://admin.zz91.com/admin1/crm/crm_messageto_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>" target="_blank">���͵�ѯ����Ϣ<br>��<font color="#FF0000"><%response.Write(countsendQuestion)%></font>��</a></TD>                                        
                                        <TD nowrap class=unselect><a href="http://admin.zz91.com/admin1/crm/crm_messagefrom_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>" target="_blank">�յ���ѯ����Ϣ<br>��<font color="#FF0000"><%response.Write(countreceiveQuestion)%></font>��</a></TD>											
                                        -->
										<TD width="99%" class=unselect>
                                        <input name="com_rankFlag" type="hidden" id="com_rankFlag" value="<%=com_rank%>">
                                        
                                        
                                        <input name="Submit" type="submit" id="submitsave1" class="button" value="  ����   " style="font-size:14px; font-weight:bold">    &nbsp;&nbsp;
<input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="�ر�">
										<%if personopen=1 or opengonghai=1 then%>
                                         
                          
						  <%
						  if session("userid")="10"  then
						  	gjqx=1
						  end if
						  '-----------------------begin
							'ʱ������
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
						  '----------�����ͻ����ܷŵ��ҵĿͻ���
						  %>
                          	<%if opengonghai=1 and requestassignFlag=0 then%>
                          		<input type="button" name="Submit5" class="button" value="�ŵ��ҵĿͻ���" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1toutmycrm&closed=1&dotype=<%=dotype%>'">
                          		<input type="button" name="Submit" class="button" value="�ŵ��ص�ͻ�׼��" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=zhongdiankh&closed=1&dotype=<%=dotype%>'">
							<%end if%>
                            <%
								if requestassignFlag=1 then
									response.Write("�ÿͻ���Ԥ�����������������˸�Ԥ����Ŀͻ���")
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
                                    <input type="button" name="Submit3" class="button" value="�ŵ���Ʒ��" onClick="if(confirm('���ѣ����ٴ�ȷ�ϸÿͻ��ֻ��������������䡱��Ϊ��Ч��ϵ��ʽ����������䡱û��ȷ����Ч�ģ��벻Ҫ�������Ʒ�ء����з��ָÿͻ���ѡ�������Ը���')){window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1twastecom&closed=1&dotype=<%=dotype%>'}">
                                    <%else%>
                                    <input type="button" name="Submit3" class="button" value="�ŵ�����" onClick="if(confirm('���ѣ����ٴ�ȷ�ϸÿͻ��ÿͻ�ΪVAP�������ͻ����˲�������Ѹÿͻ�ͬʱ���빫������ȷ�Ϻ��ٷ��롣')){window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=droptosihai&closed=1&dotype=<%=dotype%>'}">
                                    <%end if%>
                                    <input type="button" name="Submit3" class="button" value="����Ϊ�ص�" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=zhongdian&closed=1&dotype=<%=dotype%>'">
                            	<%end if%>
                            <%
							end if
							%>
                            <%if opengonghai=0 then%>
                          	<input type="button" name="Submit" class="button" value="���빫��" onClick="DropToSea(<%=iszst%>,'delselec1tcrm');">
                            	
                            <%end if%>
                            
						  <%end if
						  
						  end if
						  %>
                          <%if left(dotype,3)="vap" then%>
                          <input type="button" name="Submit" class="button" value="���Ͷ���" onClick="window.open('http://admin.zz91.com/admin1/compinfo/SMS_SendSMS.asp?com_mobile=<%=com_mobile%>','','')">
                          <!--
                          <input type="button" name="Submit" class="button" value="���봢������" onClick="DropToSea(<%=iszst%>,'cbgonghai');">
                          -->
						  <%end if%>
                          <%
						  '----personopen=1 /end
						  end if
						  %>
                          <input type="button" name="Submit" class="button" value="���Ϊ2016��ҳ�ͻ�" onClick="window.open('/admin1/huangye2016/add.asp?com_email=<%=email%>','','')">
                          <input type="button" name="Submit" class="button" value="CS��������¼" onClick="window.open('http://admin1949.zz91.com/web/zz91/crm/highsea/index.htm?companyId=<%=com_id%>','','')">
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
                        <td>�ؼ���</td>
                        <td>�������</td>
                        <td>��ʼʱ��</td>
                        <td>����ʱ��</td>
                        <td>���</td>
                        <td>�Ƿ����</td>
                        <td>�����</td>
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
						response.Write("�����")
						else
						response.Write("<font color=#ff0000>δ���</font>")
						end if
						%></td>
                        <td><%
						todate=rst("todate")
						if todate<>"" then
							if cdate(todate)<date() then
								response.Write("<font color=#ff0000>�ѹ���</font>")
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
                        <td>����ͨ��</td>
                        
                        <td>��Ʒ����</td>
                        <td>����ʱ��</td>
                        <td>������</td>
                        <td>������Ա</td>
                        <td>��ע</td>
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
					 
					'-------------------------�ж�¼��ͻ�
				  sqlP="select com_id from crm_InsertCompWeb where com_id="&idprod&" and saletype=1"
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then
				  	comLx="<font color=#ff0000>(¼��ͻ�)</font>"
				  end if
				  rsp.close
				  set rsp=nothing
				  response.Write(comLx)
				  '------------------------------------
					  %>
                  ��˾����
                  <input name="re" type="hidden" id="re" value="1"><input name="crmCheck" type="checkbox" id="cnameCheck" value="1" onClick="checkmod(this)"></td>
                <td colspan="3" nowrap>
                  <input name="cname" class="text" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" readonly maxlength="96"> 
                  <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>"> 
                  <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">��������ͻ�<input type="radio" <%if com_Especial="1" then response.Write("checked")%> name="com_Especial" value="1">
                  ��
                  <input type="radio" name="com_Especial" value="0" <%if com_Especial="0" then response.Write("checked")%>>
                  ��						  </td>
                <td align="right" nowrap>��ַ
                  <input name="crmCheck" type="checkbox" id="caddCheck" value="2" onClick="checkmod(this)"></td>
                <td nowrap>
                  <input name="cadd" type="text" class="text" id="cadd" value="<%=replacequot1(trim(rscom("com_add")))%>" size="30" readonly maxlength="255">
                  �ʱ�
                  <input name="crmCheck" type="checkbox" id="czipCheck" value="4" onClick="checkmod(this)"><input name="czip" type="text" class="text" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20" readonly></td>
                </tr>
              
              
              
              <tr> 
                <td align="right">�绰
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
						    response.Write("��ʱ������ϵ��ʽ")
						 end if
						end if
						%><a href="file/filelistly.asp?Dtmf=<%=right(trim(rscom("com_tel")),6)%>" target="_blank">�ú���¼��</a>
                  
                  </td>
                <td align="right">�ֻ�
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
						    response.Write("��ʱ������ϵ��ʽ")
						 end if
						end if
						%>
                  <a href="file/filelistly.asp?Dtmf=<%=right(trim(rscom("com_mobile")),8)%>" target="_blank">�ú���¼��</a>
                  </td>
                </tr>
              <tr> 
                <td align="right">����
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
						    response.Write("��ʱ������ϵ��ʽ")
						 end if
						end if
						%>          						  �μӻ<select name="active_flag" id="active_flag">
                      	  <option value="">��ѡ��μӻ</option>
                      	  <option value="xxչ��">xxչ��</option>
                      	  <option value="zz91_2012�Ͻ���������">zz91_2012�Ͻ���������</option>
                      	  <option value="zz91_2012̨���ܽ���">zz91_2012̨���ܽ���</option>
                      	  <option value="ZZ91_2013̨���ܽ���">ZZ91_2013̨���ܽ���</option>
                      	  <option value="zz91_2014̨���ܽ���">zz91_2014̨���ܽ���</option>
                      	  <option value="zz91_2015̨���ܽ���">zz91_2015̨���ܽ���</option>
                          
                      </select>
                      <script>selectOption("active_flag","<%=fromname%>")</script>
                      </td>
                <td align="right" nowrap>��������
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
                <td align="right">��վ
                  <input name="crmCheck" type="checkbox" id="cwebCheck" value="10" onClick="checkmod(this)"></td>
                <td colspan="3"> 
                  <input name="cweb" type="text" class="text" id="cweb" readonly value="<%=trim(rscom("com_website"))%>" size="20" maxlength="255">��������
                  <!--<input name="crmCheck" type="checkbox" id="com_subnameCheck" value="15" onClick="checkmod(this)"><input name="com_subname" type="text" class="text" id="com_subname" value="<%=trim(rscom("com_subname"))%>" size="10" readonly> 
                          <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">-->
                  <a href="http://<%=trim(rscom("com_subname"))%>.zz91.com" target="_blank"><%=trim(rscom("com_subname"))%>.zz91.com</a></td>
                <td align="right">��ϵ��
                  <input name="crmCheck" type="checkbox" id="ccontactpCheck" value="11" onClick="checkmod(this)"></td>
                <td>
                  <input name="ccontactp" type="text" class="text" id="ccontactp" readonly value="<%=replacequot1(trim(rscom("com_contactperson")))%>" size="20" maxlength="48">
                  <%
						  select case rscom("com_desi")
						  case "����"
						  deselect1="selected"
						  deselect2=""
						  case "Ůʿ"
						  deselect1=""
						  deselect2="selected"
						  end select
						  
						  %>
                  <input name="crmCheck" type="checkbox" id="cdesiCheck" value="16" onClick="checkmod(this)">
                  <select name="cdesi" id="cdesi">
                    <option value="����" <%response.Write(deselect1)%>>����</option>
                    <option value="Ůʿ" <%response.Write(deselect2)%>>Ůʿ</option>
                    </select>						  </td>
                </tr>
              
              
              <tr> 
                <td align="right">��˾���
                  <input name="crmCheck" type="checkbox" id="cintroduceCheck" value="13" onClick="checkmod(this)"></td>
                <td colspan="3" align="left" valign="top">
                  <textarea name="cintroduce" cols="50" rows="4" readonly id="cintroduce"><%=replacequot1(trim(rscom("com_intro")))%></textarea></td>
                <td align="right">��Ӫҵ��
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
                  <td height="25">��ҵ<input name="crmCheck" type="checkbox" id="ckeywordsCheck" value="12" onClick="checkmod(this)"></td>
                  <td>
                    <select name="ckeywords" id="ckeywords" style="width:100px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="selecttextname(this);changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue();" >
                      <option value="">��ѡ��</option>
                      <option value="1">�Ͻ���</option>
                      <option value="2">������</option>
                      <option value="3">�Ͼ���̥�����</option>
                      <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
                      <option value="5">��ֽ</option>
                      <option value="6">�ϵ��ӵ���</option>
                      <option value="10">�ϲ���</option>
                      <option value="12">�Ͼɶ����豸</option>
                      <option value="14">��������</option>
                      <option value="15">����</option>
                      </select>
                    <script>selectOption("ckeywords","<%=rscom("com_keywords")%>")</script>
                    </td>
                  <td>����                    
                    <input name="ckindCheck" type="checkbox" id="ckindCheck" value="17" onClick="checkmod(this)"></td>
                  <td><div id="ckindmain" style="float:left"></div>
                    
                    <%
					  ckeywords=rscom("com_keywords")
					  if ckeywords="" then ckeywords=0
					  com_ctr_id=rscom("com_ctr_id")
					  %>
                    <script>changekind("<%=ckeywords%>","ckind","ckindmain")</script>
                    <script>selectOption("ckind","<%=rscom("com_kind")%>")</script></td>
                  <td>����/����
                    <input name="crmCheck" type="checkbox" id="countryselectCheck" value="3" onClick="checkmod(this)"></td>
                  <td><select name="countryselect" id="countryselect" onChange="selectcountry(this.value);selecttextname(this)">
                    <option value="1" <%if com_ctr_id="1" then response.Write("selected")%>>�й�</option>
                    <option value="0" <%if com_ctr_id<>"1" then response.Write("selected")%>>��������/����</option>
                    </select></td>
                  <td>ʡ��
                    <input name="crmCheck" type="checkbox" id="cprovinceCheck" value="5" onClick="checkmod(this)"></td>
                  <td><div id="othercountrys" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
                    <select name="ccountry" id="ccountry" onChange="selecttextname(this)">
                      <option value="2" >̨��</option>
                      <option value="3" >���</option>
                      <option value="218" >����</option>
                      <option value="223" >Խ��</option>
                      <option value="214" >�ڿ���</option>
                      <option value="209" >������</option>
                      <option value="156" >��������</option>
                      <option value="162" >�ͻ�˹̹</option>
                      <option value="168" >���ɱ�</option>
                      <option value="169" >����</option>
                      <option value="182" >�¼���</option>
                      <option value="16" >�Ĵ�����</option>
                      <option value="43" >���ô�</option>
                      <option value="67" >Ӣ��</option>
                      <option value="100" >ӡ��</option>
                      <option value="101" >ӡ��</option>
                      <option value="102" >����</option>
                      <option value="105" >��ɫ��</option>
                      <option value="111" >Լ��</option>
                      <option value="115" >����</option>
                      <option value="116" >����</option>
                      <option value="132" >��������</option>
                      <option value="99" >����</option>
                      <option value="119" >����</option>
                      <option value="134" >����</option>
                      <option value="145" >���</option>
                      <option value="147" >�³</option>
                      <option value="109" >�ձ�</option>
                      <option value="81" >�¹�</option>
                      <option value="83" >����</option>
                      <option value="85" >ϣ��</option>
                      <option value="89" >�ص�</option>
                      <option value="95" >����</option>
                      <option value="65" >����</option>
                      <option value="74" >쳼�</option>
                      <option value="75" >����</option>
                      <option value="76" >����</option>
                      <option value="79" >����</option>
                      <option value="48" >է��</option>
                      <option value="49" >����</option>
                      <option value="52" >�չ�</option>
                      <option value="56" >�Ű�</option>
                      <option value="59" >�ݿ�</option>
                      <option value="60" >����</option>
                      <option value="21" >����</option>
                      <option value="28" >����</option>
                      <option value="30" >����</option>
                      <option value="36" >����</option>
                      <option value="37" >����</option>
                      <option value="178">ɳ��</option>
                      <option value="187" >�Ϸ�</option>
                      <option value="167" >��³</option>
                      <option value="159" >Ų��</option>
                      <option value="160" >����</option>
                      <option value="196" >�յ�</option>
                      <option value="204" >̩��</option>
                      <option value="205" >���</option>
                      <option value="199" >���</option>
                      <option value="200" >��ʿ</option>
                      <option value="226" >Ҳ��</option>
                      <option value="231" >�ɹ�</option>
                      <option value="229" >�ޱ���</option>
                      <option value="188" >������</option>
                      <option value="175" >¬����</option>
                      <option value="32" >���ζ�</option>
                      <option value="40" >��¡��</option>
                      <option value="42" >����¡</option>
                      <option value="29" >��Ľ��</option>
                      <option value="24" >�Ͳ���</option>
                      <option value="26" >����ʱ</option>
                      <option value="27" >������</option>
                      <option value="22" >�ϼ���</option>
                      <option value="15" >��³��</option>
                      <option value="20" >�͹���</option>
                      <option value="18" >�µ���</option>
                      <option value="4" >������</option>
                      <option value="61" >������</option>
                      <option value="57" >������</option>
                      <option value="51" >��Ħ��</option>
                      <option value="45" >��ý�</option>
                      <option value="80" >�Ա���</option>
                      <option value="94" >������</option>
                      <option value="91" >������</option>
                      <option value="92" >������</option>
                      <option value="86" >������</option>
                      <option value="112" >����կ</option>
                      <option value="124" >������</option>
                      <option value="8" >������</option>
                      <option value="9" >������</option>
                      <option value="10" >������</option>
                      <option value="11" >�����</option>
                      <option value="13" >����͢</option>
                      <option value="14" >��������</option>
                      <option value="19" >�����</option>
                      <option value="23" >�ͰͶ�˹</option>
                      <option value="25" >�׶���˹</option>
                      <option value="31" >����ά��</option>
                      <option value="33" >��˹����</option>
                      <option value="35" >��������</option>
                      <option value="38" >��������</option>
                      <option value="46" >����Ⱥ��</option>
                      <option value="50" >���ױ���</option>
                      <option value="53" >���Ⱥ��</option>
                      <option value="55" >���޵���</option>
                      <option value="58" >����·˹</option>
                      <option value="62" >�������</option>
                      <option value="64" >��϶��</option>
                      <option value="66" >�����߶�</option>
                      <option value="70" >��ɳ����</option>
                      <option value="73" >����Ⱥ��</option>
                      <option value="82" >��³����</option>
                      <option value="87" >�����ɴ�</option>
                      <option value="88" >�ϵ�����</option>
                      <option value="90" >Σ������</option>
                      <option value="107" >��������</option>
                      <option value="97" >�鶼��˹</option>
                      <option value="98" >������</option>
                      <option value="103" >������</option>
                      <option value="104" >������</option>
                      <option value="106" >�����</option>
                      <option value="108" >�����</option>
                      <option value="110" >������</option>
                      <option value="113" >������</option>
                      <option value="114" >�����˹</option>
                      <option value="117" >������</option>
                      <option value="120" >����ά��</option>
                      <option value="121" >�����</option>
                      <option value="122" >������</option>
                      <option value="123" >��������</option>
                      <option value="125" >��֧��ʿ��</option>
                      <option value="126" >������</option>
                      <option value="127" >¬ɭ��</option>
                      <option value="128" >��˹��</option>
                      <option value="131" >����ά</option>
                      <option value="133" >�������</option>
                      <option value="135" >����� </option>
                      <option value="136" >ë����˹</option>
                      <option value="138" >�������</option>
                      <option value="140" >��Լ�ص�</option>
                      <option value="141" >ī����</option>
                      <option value="142" >Ħ�ɸ�</option>
                      <option value="143" >Ħ���</option>
                      <option value="144" >Īɣ�ȿ� </option>
                      <option value="146" >���ױ���</option>
                      <option value="148" >�Ჴ��</option>
                      <option value="149" >����</option>
                      <option value="153" >������</option>
                      <option value="154" >�������</option>
                      <option value="155" >���ն�</option>
                      <option value="157" >Ŧ����</option>
                      <option value="163" >����˹̹</option>
                      <option value="164" >������</option>
                      <option value="166" >������</option>
                      <option value="130" >������</option>
                      <option value="170" >�������</option>
                      <option value="171" >������</option>
                      <option value="172" >��������</option>
                      <option value="173" >��������</option>
                      <option value="174" >����˹</option>
                      <option value="176" >���ൺ</option>
                      <option value="179" >���ڼӶ�</option>
                      <option value="180" >�����</option>
                      <option value="181" >��������</option>
                      <option value="183" >˹�工��</option>
                      <option value="186" >������ </option>
                      <option value="189" >˹������</option>
                      <option value="192" >ʥ����</option>
                      <option value="193" >ʥ¬����</option>
                      <option value="194" >ʥ����</option>
                      <option value="195" >ʥ�ĺ���</option>
                      <option value="197" >������</option>
                      <option value="198" >˹��ʿ��</option>
                      <option value="201" >������</option>
                      <option value="202" >��ϣ��</option>
                      <option value="203" >̹ɣ����</option>
                      <option value="206" >����</option>
                      <option value="208" >ͻ��˹</option>
                      <option value="212" >ͼ��¬</option>
                      <option value="215" >������</option>
                      <option value="213" >�ڸɴ�</option>
                      <option value="216" >������</option>
                      <option value="220" >��Ŭ��ͼ</option>
                      <option value="221" >��ٸ�</option>
                      <option value="222" >ί������</option>
                      <option value="224" >ά�䵺</option>
                      <option value="225" >����Ħ��</option>
                      <option value="227" >��˹����</option>
                      <option value="228" >������</option>
                      <option value="230" >��Ͳ�Τ</option>
                      <option value="235" >����</option>
                      <option value="236" >��������</option>
                      <option value="150" >����������˹Ⱥ��</option>
                      <option value="34" >��˹���Ǻͺ�����ά�� </option>
                      <option value="211" >�ؿ�˹�Ϳ���˹Ⱥ��</option>
                      <option value="207" >�������Ͷ�͸�</option>
                      <option value="177" >ʥ��������������</option>
                      <option value="165" >�Ͳ����¼�����</option>
                      <option value="78" >��������������</option>
                      <option value="12" >����ϺͰͲ��� </option>
                      <option value="63" >������ӹ��͹�</option>
                      <option value="219" >���ȱ��˹̹</option>
                      <option value="217" >����ά��Ⱥ��</option>
                      <option value="84" >ֱ�����Ӻ�Ͽ</option>
                      <option value="118" >������˹˹̹</option>
                      <option value="158" >�����ܶ�Ⱥ��</option>
                      <option value="191" >ʥ��˹����˹</option>
                      <option value="151" >����������˹</option>
                      <option value="152" >�¿��������</option>
                      <option value="190" >ʥ�ͷ�����</option>
                      <option value="5" >����������</option>
                      <option value="6" >����������</option>
                      <option value="7" >������Ħ��</option>
                      <option value="39" >�����ɷ���</option>
                      <option value="210" >������˹̹</option>
                      <option value="185" >������Ⱥ��</option>
                      <option value="184" >˹��������</option>
                      <option value="44" >������Ⱥ��</option>
                      <option value="47" >�зǹ��͹�</option>
                      <option value="54" >��˹�����</option>
                      <option value="68" >���������</option>
                      <option value="69" >����������</option>
                      <option value="139" >ë��̹����</option>
                      <option value="72" >������Ⱥ��</option>
                      <option value="71" >���������</option>
                      <option value="77" >����������</option>
                      <option value="137" >���ܶ�Ⱥ��</option>
                      <option value="129" >����˹��</option>
                      <option value="96" >������ά��</option>
                      <option value="93" >�����Ǳ���</option>
                      <option value="232" >������˹̹</option>
                      </select>
                    <script>selectOption("ccountry","<%=com_ctr_id%>")</script>
                    <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" value="<%=replacequot1(rscom("com_province"))%>"/>
                    </div>
                    <div id="mycountry" <%if com_ctr_id="1" or isnull(com_ctr_id) or com_ctr_id="" then response.Write("style=") else response.Write("style=display:none")%>>
                      <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">��԰��</font>
                      <input type="hidden" name="province" id="province">
                      <input type="hidden" name="city" id="city">
                      <input type="hidden" name="cprovince" id="cprovince" value="0">
                      <%
                        '--------------------------���ݵ�����ȡ�õ�������
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
                        '--------------------------ֱ��ȡ�ĵ�������
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
                            //������԰��
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="ckeywords";//��ҵID��
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
                <td width="50">��Ӫ����</td>
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
                  ����
                  <input type="radio" name="salesType" value="2" onClick="changesales(2)" <%if salestype="2" then response.Write("checked")%>/>
                  �ɹ�
                  <input name="salesType" type="radio" value="0" onClick="changesales(0)" <%if salestype="0" then response.Write("checked")%>/>
                  ���߶���
                  </td>
                <td>

                  <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabzhuyin1">
                    <tr>
                      <td height="22"><font color="#FF0000">����</font>�Ĳ�Ʒ���ṩ�ķ���</td>
                      </tr>
                    <tr>
                      <td height="22"><input name="salestext" type="text" class="textt" id="salestext" size="30" maxlength="30"   value="<%=salestext%>"/>
                        (30���ַ���)</td>
                      </tr>
                    </table>
                  </td>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0"  id="tabzhuyin2">
                  <tr>
                    <td height="22"><font color="#FF0000">�ɹ�</font>�Ĳ�Ʒ����Ҫ�ķ���</td>
                    </tr>
                  <tr>
                    <td height="22"><input name="buytext" type="text" class="textt" id="buytext" size="30" maxlength="30" value="<%=buytext%>" />
                      (30���ַ���)</td>
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
                  �ռ��ͻ���ע��Ϣ<%response.Write(rsf(0))%>
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
                <td bgcolor="#E4EDFE">�����¼</td>
                <td bgcolor="#E4EDFE">��¼����</td>
                <td bgcolor="#E4EDFE">ע��ʱ��</td>
                <td bgcolor="#E4EDFE">�鿴��Ϣ</td>
                <td bgcolor="#E4EDFE">����ʱ��</td>
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
                <td width="100" align="right" bgcolor="#ebebeb">��������</td>
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
                <td width="100" align="right" bgcolor="#ebebeb">�ͻ�����</td>
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

<!--�Ƿ��������-->
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
						<input type="button" id="jlButton" class=button name="Submit" onClick="jlcontact()" value="���ۼ�¼">
          <%
						end if
						%>
                        <input name="contactflag" type="hidden" id="contactflag" value="0">
                        <input name="recordflag" type="hidden" id="recordflag" value="0">
						<input name="lxcontactflag" type="hidden" id="lxcontactflag" value="0">
						<input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
<input type="button" class=button name="Submit" id="lxButton" onClick="lxcontact()" value="������ϵ��">
						<input type="button" name="Submit2" class=button value="�鿴�����¼" onClick="window.open('http://admin1949.zz91.com/web/zz91/salecrm/viewInfo.htm?companyId=<%=idprod%>&email=<%=trim(rscom("com_email"))%>','_black','')">
<!--&nbsp;&nbsp;<input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('http://admin.zz91.com/admin1/adv/localADlistStat.asp?email=<%=trim(rscom("com_email"))%>')" value="���ʹ��">&nbsp;&nbsp;		-->
					<input name="Submit4" type="submit" class="button" value=" ���� " id="submitsave2" style="font-size:14px; font-weight:bold; color:#FF0"> 
                    <input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('rizhi/salesHistory.asp?com_id=<%=trim(rscom("com_id"))%>')" value="�ͻ���־">
                    <input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('recordservice/recordlist.asp?com_id=<%=trim(rscom("com_id"))%>')" value="�鿴¼������">
                    <%
					if left(dotype,3)="sms" then
					%>
                    <!--<input type="button" class="button" onClick="window.open('http://admin1949.zz91.com/reborn-admin/sms/subscribe/addOrEditSMSSubscribe.htm?companyId=<%=rscom("com_id")%>&account=<%=rscom("com_email")%>')" value="���Ŷ���">-->
                    <%
					end if
					%>
                    <input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="�ر�"></td>
  </tr>
</table>
<%dis="display:none"%>
<div id="jlflag" class="searchtable" style="<%=dis%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFDBA4">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="100" align="right">�´���ϵʱ��</td>
    <td><script language=javascript>createDatePicker("contactnext_time",true,Date(),false,false,false,true)</script></td>
    <td width="150" align="right">��ϵ�����</td>
    <td><input type='radio' name='contacttype' value="13" onClick="changeTab(13)" <%if contacttype="13"  then response.write("checked")%>/>      ��Ч��ϵ
      <input type='radio' name='contacttype' onClick="changeTab(12)" value="12" <%if contacttype="12"  then response.write("checked")%>/>
      ��Ч��ϵ</td>
    
    <td width="300"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabinfo1" <%=tabdip1%>>
  <tr>
    <td width="5">&nbsp;</td>
    <td bgcolor="#CCCC00"><input type='radio' name='c_Nocontact' value='5' />�޽�չ<input type='radio' name='c_Nocontact' value='1' />���˽���<input type='radio' name='c_Nocontact' value='2' onClick="alert('���ѣ���ע���ѡ����ٴ�ȷ�ϸÿͻ��ֻ���������������󡱣�ѡ�����ÿͻ��������ڹ�����ǰ̨���֡����з��ָÿͻ���ѡ�������Ը���')" />�������<input type='radio' name='c_Nocontact' value='3' />ͣ��<input type='radio' name='c_Nocontact' value='4' />�ػ�</td>
  </tr>
</table></td>
  </tr>
</table></td>
  </tr>
</table>

<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tabinfo" <%=tabdip%>>
                          <tr align="center"> 
                        <td width="100" align="right" bgcolor="#FFFFCC">�ͻ��ȼ�</td>
                        <td align="left" bgcolor="#FFFFCC">
                          
                          
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td width="200">
                           <%if left(dotype,3)="vap" then%>
                           <select name="com_rank" id="com_rank" style="background:#ebebeb; width:150px;" onChange="changestar(this.value)">
                            <option value="">--��ѡ��ȼ�--</option>
                            <option value="1" >��������������Ը</option>
                            <option value="2" >���������������Ը</option>
                            <option value="3" >������Ը����ȷ</option>
                            <option value="4" >����������ã������</option>
                            <option value="4.8" >|��������Ը��ʱ��δ��</option>
                            <option value="4.1" >|������������Ը��һ�����ڡ�</option>
                            <option value="5" >��������Ը�ã���3������</option>
                            <option value="6" >��������Ѻ���</option>
                          </select>
                           <%else%>   
                          <select name="com_rank" id="com_rank" style="background:#ebebeb" onChange="changestar(this.value)">
                            <option value="">--��ѡ��ȼ�--</option>
                            <option value="1" >����������������</option>
                            <option value="2" >�����������Ŀǰ������</option>
                            <option value="3" >���������ã�����һ��</option>
                            <option value="4" >����������ã������</option>
                            <%if left(dotype,3)<>"vap" then%>
                            <option value="4.1" >|������ͨ����</option>
                            <option value="4.8" >|������ʯ����</option>
                            <%else%>
                            <option value="4.8" >|����������</option>
                            <option value="4.1" >|����������</option>
                            <%end if%>
                            <option value="5" >�������ͷȷ������</option>
                          </select>
                          <%end if%>
                          <script>selectOption("com_rank","<%=com_rank%>")</script></td>
                              <td>
                              <%if left(dotype,3)="vap" then%>
                              <div>
                              <input type="radio" name="paystats" id="paystats" value="0" onClick="openpayinput(this.value,0)" checked>��
                              <input type="radio" name="paystats" id="paystats" value="1" onClick="openpayinput(this.value,1)">
                              ��ɱ�ڿͻ�
                              <input type="radio" name="paystats" id="paystats" value="2" onClick="openpayinput(this.value,1)">
                              �ƽ�ͻ�
                              <input type="radio" name="paystats" id="paystats" value="3" onClick="openpayinput(this.value,1)">
                              �¿ͻ�
                              <input type="radio" name="paystats" id="paystats" value="4" onClick="openpayinput(this.value,1)">
                              ���ڿͻ�
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
                <td nowrap bgcolor="#FFCC99"><font color="#FF3300">��ͨ��ҵ������ͨ</font></td>
                
                
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
                                  <td bgcolor="#f2f2f2">��������</td>
                                  <td bgcolor="#f2f2f2">��ͨʱ��</td>
                                  <td bgcolor="#f2f2f2">����ʱ��</td>
                                  <td bgcolor="#f2f2f2">�������</td>
                                  <td bgcolor="#f2f2f2">���</td>
                                  <td bgcolor="#f2f2f2">��ע</td>
                                </tr>
                                <tr>
                                  <td bgcolor="#FFFFFF"><input name="paykind" type="checkbox" id="paykind" value="��չ" />
      ��չ
      <input name="paykind" type="checkbox" id="paykind" value="����" />
      ����
      <br>
      <input name="paykind" type="checkbox" id="paykind" value="����" />
      ����
      <input name="paykind" type="checkbox" id="paykind" value="��ҳ" />
      ��ҳ
      <input name="paykind" type="checkbox" id="paykind" value="����" />
      ����</td>
                                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("payfromdate",true,"",false,true,true,true)</script></td>
                                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("paytodate",true,"",false,true,true,true)</script></td>
                                  <td bgcolor="#FFFFFF"><input type="radio" name="donation" id="donation" value="1" />
      ����<br>
      <input type="radio" name="donation" id="donation" value="0" />
����</td>
                                  <td bgcolor="#FFFFFF"><input name="money" type="text" id="money" value="0" size="5" />
                                    <br>
    (���������֣�������0)</td>
                                  <td bgcolor="#FFFFFF"><input name="bz" type="text" id="bz" size="10" /></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <%end if%>
                          <tr align="center" <%=wxstyle%> id="change5star">
                            <td align="right" bgcolor="#FFFFCC">��������������</td>
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
										strreson="��"
									else
										strreson="<font color=#ff0000>��</font>"
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
                                  ��
                                  <input type="radio" name="reson_check<%=rscate("code")%>" id="reson_check<%=rscate("code")%>" value="0" <%=resoncheck2%>>
                                  ��
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
                            <td align="right" bgcolor="#FFFFCC">�Ƿ��ϵ�/�ٵ�</td>
                            <td align="left" bgcolor="#FFFFCC">
                            
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="�ϵ�">
                            �ϵ�
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="�ٵ�">
                            �ٵ� &nbsp;&nbsp;&nbsp;&nbsp;�ϵ�/�ٵ�ԭ��
                            <input name="tuohuireson" type="text" id="tuohuireson" size="40"></td>
                          </tr>
                          
                     
                      <%if left(dotype,3)="vap" then%>
                      <!--<tr align="center">
                        <td align="right" bgcolor="#FFFF00">����ֵ��Ը��</td>
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
                          ��
                        <input type="radio" name="zengzhi" id="radio4" value="0" <%if zengzhi=0 then response.Write("checked")%>>
                          ��</td>
                      </tr>-->
                      <%end if%>
                      <tr align="center"> 
                        <td align="right" bgcolor="#FFFFCC">��ע</td>
                        <td align="left" bgcolor="#FFFFCC">
                        
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="320" bgcolor="#FFCC00"><%
						'0 ��ǩ 1 BD�� 2 ��ǩ 3 ��� 4\ vap�ͻ�
						%><%if left(dotype,3)<>"vap" and left(dotype,3)<>"sms" then%><input type="radio" name="telflag" id="radio" value="0" checked>��ǩ����ͨ<input type="radio" name="telflag" id="radio2" value="2">��ǩ����ͨ<input name="telflag" type="radio" id="radio" value="4" >VAP����<%elseif left(dotype,3)="vap" then%><input name="telflag" type="radio" id="radio" value="4" checked>VAP����<%else%><input name="telflag" type="radio" id="radio" value="5" checked>SMS����<%end if%>
                            <input name="seoflag" type="checkbox" id="seoflag">
                            SEO����</td>
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
        <td align="right">�ֻ�<font color="#FF0000">(�е���һҪ��)</font>��</td>
        <td><input name="PersonMoblie" type="text" id="PersonMoblie"></td>
        <td align="right">�绰<font color="#FF0000">(�е��ڶ�Ҫ��)</font>��</td>
        <td><input name="PersonTel" type="text" id="PersonTel"></td>
        <td align="right">��ͨ��˾���ƣ�</td>
        <td><input name="personComname" type="text" id="personComname" value="" /></td>
      </tr>
      <tr>
        <td align="right" width="130">��ϵ�ˣ�</td>
        <td nowrap><input name="PersonName" type="text" id="PersonName">
          <input name="PersonSex" type="radio" value="1" checked>
          ����
          <input type="radio" name="PersonSex" value="0">
          Ůʿ</td>
        <td align="right">�ؼ���ϵ�ˣ� </td>
        <td>��
          <input type="radio" name="PersonKey" value="1">
          ����
          <input name="PersonKey" type="radio" value="0" checked></td>
        <td align="right">ְ��</td>
        <td><input name="PersonStation" type="text" id="PersonStation"></td>
      </tr>
     
      
      <tr>
        <td align="right">���棺 </td>
        <td><input name="PersonFax" type="text" id="PersonFax"></td>
        <td align="right">������ϵ��ʽ��</td>
        <td><input name="PersonOther" type="text" id="PersonOther"></td>
        <td align="left">Email��</td>
        <td><input name="PersonEmail" type="text" id="PersonEmail"></td>
      </tr>
      <tr>
        <td align="right">��ע��</td>
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
<!--ײ���������ʾ  begin-->

<%
if left(dotype,3)<>"1" then
if zhuangdanFlag=0 and guangLiangFlag<1 then
%>
<%
else
arrcomList=idprod&","&arrcomList
arrarrcomList=split(arrcomList,",")
%>
<div class="tishi" style="font-size:14px; line-height:25px; color:#F00">��<b><%=ubound(arrarrcomList)%></b>���ֻ���绰���ƵĿͻ������ܻᷢ��ײ�������ύ���ܻ���������</div>
<table width="95%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#666666">
<tr>
    <td bgcolor="#CCCCCC" >&nbsp;</td>
    <td align="left" bgcolor="#CCCCCC">��˾����</td>
    <td align="left" bgcolor="#CCCCCC">email</td>
    <td align="left" bgcolor="#CCCCCC">�绰</td>
    <td align="left" bgcolor="#CCCCCC">�ֻ�</td>
    <td align="left" bgcolor="#CCCCCC">����ʱ��</td>
    <td align="left" bgcolor="#CCCCCC">�����ϵʱ��</td>
    <td align="left" bgcolor="#CCCCCC">����¼</td>
    <td align="left" bgcolor="#CCCCCC">��¼����</td>
    <td align="left" bgcolor="#CCCCCC">����</td>
    <td align="left" bgcolor="#CCCCCC">���</td>
    <td align="left" bgcolor="#CCCCCC">Ԥ��</td>
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
		response.Write("�ÿͻ��Ѵ��������ݲ����")
	elseif guangLiangFlag=2 then
		response.Write("�����ͻ�����Ԥ����......")
	elseif guangLiangFlag=3 then
		response.Write("�ÿͻ���ͨ���������ʱ����ײ�����е�С�����ײ���ƶ�Ԥ���и� <b>"&YSpersonName&"</b> ")
	%>
    <%
	if isgonghai=0 then
	%>
    ���뿴����1�����������ύ���߻򹫺�������Ҫ���ߵ��ײ�����߰�ť�Ϳ�<input type="button" name="button3" id="button3" value="ײ������" onClick="linksstack()">
    <%
	end if
	%>
	<%
	elseif guangLiangFlag=4 then
		response.Write("�е�С�����ײ���ƶ�Ԥ���и���")
	%>
    
    
    <%
	else
	%>
    <input type="button" name="button2" id="button2" value="�ύ�����ͻ�" onClick="getcom(this.form)">
    <%
	end if
	%></div>
    <%
	if guangLiangFlag=3 and isgonghai=0 then
		
		sqls="select top 10 a.ssdetail,a.fdate,b.realname from crm_compLink_ss as a,users as b where a.com_id="&com_id&" and a.personid=b.id order by a.fdate desc"
		set rss=conn.execute(sqls)
		if not rss.eof or not rss.bof then
			while not rss.eof
				response.Write(rss("ssdetail")&"<br>����ʱ�䣺"&rss("fdate")&"|"&rss("realname")&"<br>")
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
<!--ײ���������ʾ  end-->


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
    <input name="Submit" type="submit" class="button" value="����" id="submitsave">
    <input name="zhuangdanFlag" id="zhuangdanFlag" type="hidden" value="<%=zhuangdanFlag%>">
    <input name="guangLiangFlag" id="guangLiangFlag" type="hidden" value="<%=guangLiangFlag%>">
    
&nbsp;&nbsp;                          &nbsp;&nbsp; <input name="reset2" type="button" class="button" value="�ر�" onClick="parent.window.close();"></td>
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
place=place&"<option value=>��ѡ��</option>"
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