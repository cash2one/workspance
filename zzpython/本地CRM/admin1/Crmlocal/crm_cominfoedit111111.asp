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
sqlb="select vipflag,vip_check from temp_salescomp where com_id="&idprod
set rsb=conn.execute(sqlb)
if not rsb.eof then
  if rsb("vipflag")=2 and rsb("vip_check")=1 then
  	  vipflag=2
  else
	  vipflag=0
  end if
end if
if vipflag=0 then
	  sqla="select personid from crm_assign where com_id="&idprod
	  set rsa=conn.execute(sqla)
	  if not rsa.eof then
		  if cstr(session("personid"))<>cstr(rsa(0)) and cstr(session("Partadmin"))<>"13" then
			if cstr(session("userid"))<>"10" and cstr(session("userClass"))<>"1"  then
			    response.Write(session("userClass")&session("userid"))
				response.Write("��û��Ȩ�޲鿴����Ϣ��<br><a href=###kang onclick='parent.window.close()'>�ر�</a>")
				response.End()
			end if
		  end if
	  else
	    '-------δ���¼��ͻ�
	  	sqlp="select com_id from crm_InsertCompWeb where com_id="&idprod&" and saletype=1 and ccheck=0"
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			response.Write("��û��Ȩ�޲鿴����Ϣ��<br><a href=###kang onclick='parent.window.close()'>�ر�</a>")
			response.End()
		end if
		rsp.close
		set rsp=nothing
		'----------
	  end if
	  rsa.close
	  set rsa=nothing
end if
'--------------�ж��Ƿ��Ƕ������ۿͻ�
sqlc="select com_id from comp_zstinfo where com_id="&idprod&""
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

set rscom=server.CreateObject("ADODB.recordset")
if idprod<>"" then
	sql="select * from comp_info where com_id="&idprod
	ch=0
else
	sql="select * from Crm_Temp_SalesComp where com_email='"&cemail&"'"
	ch=1
end if
rscom.open sql,conn,1,1
if rscom.eof or rscom.bof then
	response.Write("�޴���Ϣ��")
	rscom.close()
	set rscom=nothing
	response.end
end if
dotype=request("dotype")
'-------------�ж��Ƿ��÷Ƿ�ʹ��DOTYPE
'arrTempDotype=split(tempDotype,"|")
'
'if ubound(arrTempDotype)>0 then
'    dotypeExists=0
'	for o=0 to ubound(arrTempDotype)
'		if arrTempDotype(o)<>"" then
'			if dotype=arrTempDotype(o) then
'				dotypeExists=1
'			end if
'		end if
'	next
'	if dotypeExists=0 then
'		response.Write("��û��Ȩ�޲�����")
'		response.End()
'	end if
'end if

sql="select contactType,com_type,com_rank,contactnext_time,xiaoShouFlag,com_kind,com_sting,com_Especial from comp_sales where com_id="&idprod&""
set rssales=conn.execute(sql)
if rssales.eof then
	sqls="select top 1 contacttype,com_type,com_rank,contactnext_time,contactkind,com_kind,com_sting from comp_tel where com_id="&idprod&" order by id desc"
	set rss=server.CreateObject("adodb.recordset")
	rss.open sqls,conn,1,1
	if not rss.eof then
	contacttype=rss("contacttype")
	com_type=rss("com_type")
	com_rank=rss("com_rank")
	contactnext_time=rss("contactnext_time")
	contactkind=rss("contactkind")
	com_kind=rss("com_kind")
	com_sting=rss("com_sting")
	else
	com_sting=0
	end if
	rss.close
	set rss=nothing
	com_Especial="0"
else
	contactType=rssales("contactType")
	com_type=rssales("com_type")
	com_rank=rssales("com_rank")
	contactnext_time=rssales("contactnext_time")
	xiaoShouFlag=rssales("xiaoShouFlag")
	com_kind=rssales("com_kind")
	com_sting=rssales("com_sting")
	com_Especial=rssales("com_Especial")
end if
rssales.close
set rssales=nothing
%>
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="js/province.js"></SCRIPT>
<SCRIPT language=javascript src="js/compkind.js"></SCRIPT>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.aduse {
	color: #FFF;
	background-color: #F00;
	border: 1px solid #000;
}
.crmcheckmod {
	background-color: #FFFFCC;
}
.tishi{
	line-height: 22px;
	background-color: #FF9;
	border: 1px solid #F90;
	font-size: 12px;
	padding-right: 10px;
	padding-left: 10px;
}
.alerttitle {
	background-image: url(../images/greentitlebg.gif);
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #72A77B;
	border-left-color: #72A77B;
	background-position: 0px 0px;
}
.alerttitle_kuang {
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #72A77B;
	border-bottom-color: #72A77B;
	border-left-color: #72A77B;
	background-color: #FFFFFF;
	padding: 8px;
}
.alerttitlen {
	background-image: url(../images/ttarr.gif);
	background-repeat: no-repeat;
	background-position: 6px 0px;
	padding-left: 26px;
	font-size: 12px;
	color: #1E7362;
	CURSOR: move;
}
-->
</style>
<script type="text/javascript">
function setContent()
{
	document.getElementById("cproductslist_enCheck").checked=true;
	var content="";
		var infobox=document.getElementById("cproductslist_en");
		var com_zyyw_value=document.getElementById("com_zyyw").value;
		var com_mysl_value=document.getElementById("com_mysl").value;
		var com_Area=document.getElementById("com_Area");
		var com_area_value=com_Area.options[com_Area.selectedIndex].text;
		
		var com_jyfs=document.getElementById("com_jyfs");
		var com_jyfs_value=com_jyfs.options[com_jyfs.selectedIndex].text;
		content="��Ӫ��ʽ:"+com_jyfs_value+";";
		content+="��Ӫ:"+com_zyyw_value+";";
		content+="����:"+com_mysl_value+";";
		content+="����:"+com_area_value;
		infobox.value=content;
		infobox.className = "crmcheckmod";
		infobox.readOnly=false;
}
</script>

</head>
<body>
<div id="wrap">
<%dis="display:none"%>
<div  id="page_cover" style="position:absolute;display:none;z-index:100; width:230px; height:59px; color:#cccccc; left: 0; top: 0;"></div>
<div id="regform" style=" <%=dis%>;z-index:1002; top:100px; margin-left:100px; width:400px; height:100px;" > 
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size:12px">
      <tr>
        <td height="29" background="../images/greentitlebg.gif" bgcolor="#003399" class="alerttitle"><div class="alerttitlen" id="alerttile">������Ʒϵ��</div></td>
      </tr>
      <tr>
      <form id="form2" name="form2" method="post" action="crm_assign_save.asp?selectcb=<%response.Write(idprod)%>&dostay=delselec1tcrm&closed=1">
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
	com_rankFlag=document.getElementById("com_rankFlag").value
	if (com_rankFlag>=4)
	{
		//document.getElementById("alerttile").innerHTML="����д���빫����ԭ��"
		//openPageCover()
		if(confirm("ȷʵҪ��4-5�ǵĿͻ����빫����?"))
		{
			if (n==1)
			{
				document.getElementById("alerttile").innerHTML="����д���빫����ԭ��"
				openPageCover()
			}else{
				window.location='crm_assign_save.asp?selectcb=<%response.Write(idprod)%>&dostay=delselec1tcrm&closed=1&lmcode=<%=lmcode%>'
			}
		}
	}else
	{
		if (n==1)
		{
			document.getElementById("alerttile").innerHTML="����д���빫����ԭ��"
			openPageCover()
		}else{
			window.location='crm_assign_save.asp?selectcb=<%response.Write(idprod)%>&dostay=delselec1tcrm&closed=1&lmcode=<%=lmcode%>'
		}
	}
}
</script>
<form name="form1" method="post" target="crmeidt" action="crm_cominfoedit_re.asp?lmcode=<%=lmcode%>&frompage=<%=frompage%>&frompagequrstr=<%=frompagequrstr%>#top" onSubmit="return chkfrm(this)">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="100%" valign="top">
    <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">
		   <TABLE id=secTable cellSpacing=0 cellPadding=0 border=0>
                                    <TR>
                                        <TD nowrap class=selected onclick=secBoard(0)>������Ϣ</TD>
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_postlist_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_a','width=700,height=500')">������Ϣ</TD>                                       
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_messageto_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_b','width=700,height=500')">���͵�ѯ����Ϣ</TD>                                        
                                        <TD nowrap class=unselect onClick="window.open('http://www.zz91.com/admin1/crm/crm_messagefrom_local.asp?idprod=<%=request("idprod")%>&cemail=<%=request("cemail")%>','_c','width=700,height=500')">�յ���ѯ����Ϣ</TD>											
                                        
										<TD width="99%" class=unselect>
                                        <input name="com_rankFlag" type="hidden" id="com_rankFlag" value="<%=com_rank%>">
                                        <input name="Submit" type="submit" id="submitsave1" class="button" value="����"> 
                          <input name="closebu" type="button" class="button" id="closebu" onClick="parent.window.close();" value="�ر�">
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
						  if dotype<>"allall" or gjqx=1 or getcompany=1 then
						  %>
                          <input type="button" name="Submit" class="button" value="�ŵ��ҵĿͻ���" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1toutmycrm&closed=1&lmcode=<%=lmcode%>'">
                          
						  <%
						    if dotype<>"all" and dotype<>"xm" and dotype<>"Especial" and dotype<>"allGh" and dotype<>"allzstGh" then%>
                            <input type="button" name="Submit3" class="button" value="�ŵ���Ʒ��" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=selec1twastecom&closed=1&lmcode=<%=lmcode%>'">
						  <input type="button" name="Submit3" class="button" value="����Ϊ�ص�" onClick="window.location='crm_assign_save.asp?selectcb=<%response.Write(rscom("com_id"))%>&dostay=zhongdian&closed=1&lmcode=<%=lmcode%>'">
                          	<input type="button" name="Submit" class="button" value="���빫��" onClick="DropToSea(<%=iszst%>);">
                            
						  <%end if
						  end if
						  %>
						  </TD>
									
                			        </TR>
                			    </TABLE>
<%
mypartid=left(session("userid"),4)
partid=0
if mypartid="1301" or mypartid="1302" then partid="1"
if mypartid="1303" or mypartid="1306" then partid="2"
if session("personid")="93" then partid="1"
if session("personid")="227" then partid="2"
sqlw="select top 1 tcontent from crm_awoke where part="&partid&" order by id desc"
set rsw=conn.execute(sqlw)
if not rsw.eof or not rsw.bof then
%>
<div class="tishi">
	<%=rsw(0)%>
</div>
<%
end if
rsw.close
set rsw=nothing
%>
		  </td>
        </tr>
        <tr>
          <td>

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="searchtable">

             
           
            <tr>
              <td class="td_s2">
              <%
			  sqlt="select * from comp_continue where com_id="&idprod
				set rst=conn.execute(sqlt)
				if not rst.eof then
			  %>
              <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
              <tr bgcolor="#CCCCCC">
                <td>����ͨʹ�����</td>
                <td>���翪ͨʱ��</td>
                <td>��ǩ��ͨʱ��</td>
                <td>��ǰ��ǩ��ʵ��ʱ��</td>
                <td>����ʱ��</td>
                <td>����</td>
                <td>&nbsp;</td>
              </tr>
			  <%
			  while not rst.eof 
			  %>
              
              <tr bgcolor="#FFFFFF">
                <td width="100"><%
				continueFlag=rst("continueFlag")
				firstDateFrom=rst("firstDateFrom")'���翪ͨʱ��
				continueDateFrom=rst("continueDateFrom")'��ǩ��ͨʱ��
				currentContinueDateFrom=rst("currentContinueDateFrom")'��ǰ��ǩ��ʵ��ʱ��
				dateTo=rst("dateTo")'����ʱ��
				yearNum=rst("yearNum")
				if continueFlag=1 then
					response.Write("��ǩ")
				elseif continueFlag=0 then
					response.Write("��ǩ")
				end if
				%></td>
                <td><%=firstDateFrom%></td>
                <td><%=continueDateFrom%></td>
                <td><%=currentContinueDateFrom%></td>
                <td><%=dateTo%></td>
                <td><%=yearNum%></td>
                <td><a href="http://www.zz91.com/admin1/compinfo/cominfo_showOpenConfirmLocal.asp?com_id=<%=idprod%>" target="_blank">�鿴��ͨ��</a></td>
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
              <table width="100%" border="0" align="center" cellspacing="0">
<script language="javascript">
function rehit()
{
	alert (document.all.crmeidt.height)
	document.all.crmeidt.style.display="none"
}
function GetValueChoose(elementname)
{
var sValue = "";
var tmpels = elementname;
	for(var i=0;i<tmpels.length;i++)
	{
		if(tmpels[i].checked)
		{
			sValue= tmpels[i].value;
		}
	}
return sValue;
}
function chkfrm(frm)
{
    document.all.crmeidt.style.display="none"
	if(frm.cname.value.length<=0 && frm.cname_en.value.length<=0)
	{
		alert("�����빫˾����!");
		//frm.cname.focus();
		return false;
	}
	getprovincename();
	if(frm.cadd.value.length<=0 && frm.cadd_en.value.length<=0)
	{
		alert("�������ַ!");
		//frm.cadd.focus();
		return false;
	}
	
	if (frm.lxcontactflag.value=="1")
	{
		if (frm.PersonName.value=="")
		{
		alert("��������ϵ������!");
		frm.PersonName.focus();
		return false;
		}
	}
	if(frm.cemail.value.length<=0)
	{
		alert("����������ʼ�!");
		frm.cemail.focus();
		return false;
	}
	if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
		alert("���������ʽ����");
		frm.cemail.focus();
		return  false;
	}  
	if(frm.ccontactp.value.length<=0 && frm.ccontactp_en.value.length<=0)
	{
		alert("��������ϵ��!");
		frm.ccontactp.focus();
		return false;
	}
	//if(frm.cate_compCate.options[frm.cate_compCate.selectedIndex].value=="")
//	{
//		alert("��ѡ��ͻ�����!");
//		frm.cate_compCate.focus();
//		return false;
//	}
	//if(frm.isth.options[frm.isth.selectedIndex].value=="")
//	{
//		//alert("��ѡ���Ƿ�Ϊ�ڻ��ͻ�!");
//		//frm.isth.focus();
//		//return false;
//	}
	//if(frm.isth.selectedIndex==1)
//	{
//		if(frm.storeCate.value==""){
//			alert("����д��Ʒ���!");
//			frm.storeCate.focus();
//			return false;
//		}
//	
//		if(frm.storeDesc.value==""){
//			alert("����д��Ʒ����!");
//			frm.storeDesc.focus();
//			return false;
//		}
//		if(frm.storeCount.value==""){
//			alert("����д��Ʒ����!");
//			frm.storeCount.focus();
//			return false;
//		}
//	}
	

//�ͻ�����������
	if (frm.contactflag.value=="1")
	{
		if(frm.com_rank.value.length<=0)
		{
			alert("��ѡ��ͻ��ȼ�!");
			frm.com_rank.focus();
			return false;
		}
		if (GetValueChoose(document.getElementsByName("contacttype"))=="12")
		{
			if(GetValueChoose(document.getElementsByName("c_Nocontact"))=="")
			{
				alert("��ѡ����Ч��ϵ����!");
				return false;
			}
		}
		var telflagcheck=document.getElementsByName("telflag");
		var telischeck=false;
		for(var i=0;i<telflagcheck.length;i++)
		{
			if(telflagcheck[i].checked){
				telischeck=true;
			}
		}
		if(!telischeck)
		{
			alert("��ѡ��˿ͻ��ı�ע ��������!");
			return false;
		}
	}
	
	//����ͻ�����Ϊ��Ӫ��Ʒ����۵�,����Ӫ��ʽ
	//if(frm.cate_compCate.selectedIndex==3)
//	{
//		if(frm.com_jyfs.options[frm.com_jyfs.selectedIndex].value==""){
//			alert("��ѡ��Ӫ��ʽ!");
//			frm.com_jyfs.focus();
//			return false;
//		}
//	
//		if(frm.com_zyyw.value==""){
//			alert("����д��Ӫ!");
//			frm.com_zyyw.focus();
//			return false;
//		}
//		if(frm.com_mysl.value==""){
//			alert("����дÿ������!");
//			frm.com_mysl.focus();
//			return false;
//		}
//		if(frm.com_Area.options[frm.com_Area.selectedIndex].value==""){
//			alert("��ѡ������!");
//			frm.com_Area.focus();
//			return false;
//		}
//	}
	document.getElementById("submitsave").disabled=true;
	document.getElementById("submitsave1").disabled=true;
	document.getElementById("submitsave2").disabled=true;
	return true;
}
function checkmod(obj)
{
    var infoboxOkClass="crmcheckmod";
	var infoboxinfo=obj.id.substring(0,obj.id.length-5)
	//alert (infoboxinfo)
	var infobox= document.getElementById(infoboxinfo);
	var infoname=document.getElementById(obj.id);
	if (infoname.checked==true)
	{
	infobox.className = infoboxOkClass;
	infobox.readOnly=false
	}
	if (infoname.checked==false)
	{
	infobox.className = "text";
	infobox.readOnly=true
	}
}
</script>
                    
                      <tr align="center"> 
                        <td colspan="6"><a name="top"></a> <iframe id=crmeidt name=crmeidt height="1" style="VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="###kang"></iframe>                        </td>
                      </tr>
                      
                      <tr> 
                        <td width="13%" align="right" nowrap>
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
						��˾���ƣ�
						<input name="re" type="hidden" id="re" value="1"><input name="crmCheck" type="checkbox" id="cnameCheck" value="1" onClick="checkmod(this)"></td>
                        <td colspan="3">
                          <input name="cname" class="text" type="text" id="cname" value="<%=replacequot1(trim(rscom("com_name")))%>" size="30" readonly maxlength="96"> 
                          <input name="idprod" type="hidden" id="idprod" value="<%=idprod%>"> 
                          <input name="com_id" type="hidden" id="com_id" value="<%=rscom("com_id")%>">
						 ��������ͻ�<input type="radio" <%if com_Especial="1" then response.Write("checked")%> name="com_Especial" value="1">
��
  <input type="radio" name="com_Especial" value="0" <%if com_Especial="0" then response.Write("checked")%>>
��						  </td>
                        <td align="right" nowrap>��ַ��<input name="crmCheck" type="checkbox" id="caddCheck" value="2" onClick="checkmod(this)"></td>
                        <td nowrap>
                          <input name="cadd" type="text" class="text" id="cadd" value="<%=replacequot1(trim(rscom("com_add")))%>" size="50" readonly maxlength="255"></td>
                      </tr>
                      <tr align="center">
                        <td align="right" nowrap>������ҵ��<input name="crmCheck" type="checkbox" id="ckeywordsCheck" value="12" onClick="checkmod(this)"></td>
                        <td colspan="3" align="left"><select name="ckeywords" id="ckeywords" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue()" >
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
                        </select>                          <script>selectOption("ckeywords","<%=rscom("com_keywords")%>")</script>
						</td>
                        <td align="right">��˾���ͣ�<input name="crmCheck" type="checkbox" id="ckindCheck" value="17" onClick="checkmod(this)"></td>
                        <td align="left"><div id="ckindmain" style="float:left"></div>
                      
					  <script>
						function selectcountry(id)
						{
							if (id=="1")
							{
								document.getElementById("othercountrys").style.display="none"
								document.getElementById("mycountry").style.display=""
							}
							else
							{
								document.getElementById("othercountrys").style.display=""
								document.getElementById("mycountry").style.display="none"
								document.getElementById("othercity").value=""
							}
						}
						</script>
					  <%
					  ckeywords=rscom("com_keywords")
					  if ckeywords="" then ckeywords=0
					  com_ctr_id=rscom("com_ctr_id")
					  %>
                      <script>changekind("<%=ckeywords%>","ckind","ckindmain")</script>
                      <script>selectOption("ckind","<%=rscom("com_kind")%>")</script></td>
                      </tr>
                      <tr> 
                        <td align="right" nowrap>����/������<input name="crmCheck" type="checkbox" id="countryselectCheck" value="3" onClick="checkmod(this)"></td>
                        <td>
                        <select name="countryselect" id="countryselect" onChange="selectcountry(this.value);">
                          <option value="1" <%if com_ctr_id="1" then response.Write("selected")%>>�й�</option>
                          <option value="0" <%if com_ctr_id<>"1" then response.Write("selected")%>>��������/����</option>
                        </select>
                        <%'getcountrylist trim(rscom("com_ctr_id")) 'ccountry%>
                        </td>
                        <td align="right" nowrap>�ʱࣺ<input name="crmCheck" type="checkbox" id="czipCheck" value="4" onClick="checkmod(this)"></td>
                        <td>
<input name="czip" type="text" class="text" id="czip" value="<%=trim(rscom("com_zip"))%>" size="10" maxlength="20" readonly></td>
                        <td align="right">ʡ�У�<input name="crmCheck" type="checkbox" id="cprovinceCheck" value="5" onClick="checkmod(this)"></td>
                        <td>
                        <div id="othercountrys" <%if com_ctr_id<>"1" and not isnull(com_ctr_id) then response.Write("style=") else response.Write("style=display:none")%>>
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
                        
                          <option value="178" >ɳ��</option>
                        
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
                        sqlh="select * from comp_provinceID where com_id="&idprod
                        set rsh=conn.execute(sqlh)
                        if not rsh.eof or not rsh.bof then
                            provinceID=rsh("province")
                            cityID=rsh("city")
                            GardenID=rsh("Garden")
                        else
                            provinceID=getprovince(province)
                            cityID=getprovince(city)
                            GardenID=""
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
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
                        
                        </div>
						<!--<SELECT id="province" name="province"></SELECT>
                        <SELECT id="city" name="city"></SELECT>
                        <SCRIPT type=text/javascript>
                            //��ĵ���
                            new Dron_City("province","city",'<%'=replacequot1(trim(rscom("com_province")))%>').init();
                        </SCRIPT>
                        
                        <input name="textfield" type="text" class="text" readonly size="8" value="<%'response.Write(replacequot1(trim(rscom("com_province"))))%>">--></td>
                      </tr>
                    
                      <tr> 
                        <td align="right">�绰��<input name="crmCheck" type="checkbox" id="ctelCheck" value="6" onClick="checkmod(this)"></td>
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
						%>						</td>
                        <td align="right">�ֻ���<input name="crmCheck" type="checkbox" id="cmobileCheck" value="7" onClick="checkmod(this)"></td>
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
						%></td>
                      </tr>
                      <tr> 
                        <td align="right">���棺<input name="crmCheck" type="checkbox" id="cfaxCheck" value="8" onClick="checkmod(this)"></td>
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
						%>						  </td>
                        <td align="right" nowrap>�������䣺<input name="crmCheck" type="checkbox" id="newemailCheck" value="9" onClick="checkmod(this)"></td>
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
                        <td align="right">��վ��<input name="crmCheck" type="checkbox" id="cwebCheck" value="10" onClick="checkmod(this)"></td>
                        <td colspan="3"> 
                          <input name="cweb" type="text" class="text" id="cweb" readonly value="<%=trim(rscom("com_website"))%>" size="30" maxlength="255"></td>
                        <td align="right">��ϵ�ˣ�<input name="crmCheck" type="checkbox" id="ccontactpCheck" value="11" onClick="checkmod(this)"></td>
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
                        <td align="right"></td>
                        <td colspan="3">
						<% 
						'sqlk="select cateID from cate_compCate where com_id="&idprod
'						set rsk=conn.execute(sqlk)
'						if not rsk.eof then
'							khgl=rsk(0)
'							showComCate(rsk(0))
'						else
'							showComCate("")
'						end if
'						rsk.close
'						set rsk=nothing
						%>
</td>
                        <td><div align="right">����������<input name="crmCheck" type="checkbox" id="com_subnameCheck" value="15" onClick="checkmod(this)"></div></td>
                        <td>
						 
<input name="com_subname" type="text" class="text" readonly id="com_subname" value="<%=trim(rscom("com_subname"))%>"> 
                          <input name="com_subname1" type="hidden" id="com_subname1" value="<%=trim(rscom("com_subname"))%>">
                          .zz91.com						</td>
                      </tr>
                      
                      <tr> 
                        <td align="right">��˾��飺<input name="crmCheck" type="checkbox" id="cintroduceCheck" value="13" onClick="checkmod(this)"></td>
                        <td colspan="3" align="left" valign="top">
<textarea name="cintroduce" cols="50" rows="4" readonly id="cintroduce"><%=replacequot1(trim(rscom("com_intro")))%></textarea></td>
                        <td align="right">��Ӫҵ��<input name="crmCheck" type="checkbox" id="cproductslist_enCheck" value="14" onClick="checkmod(this)"></td>
                        <td align="left" valign="top">
						
<textarea name="cproductslist_en" cols="50" readonly rows="4" id="cproductslist_en"><%=replacequot1(trim(rscom("com_productslist_en")))%></textarea>						</td>
                      </tr>
                     
                
                     
                      
                      <tr align="center">
                      	<td align="right" nowrap></td>
                      	<td colspan="5" align="left">
						
						
						</td>
                      	</tr>
                      
                      <tr align="center">
                        <td colspan="6" align="right" nowrap bgcolor="#D4D0C8">
						
						<%
						'sqlu="select * from Crm_CompOtherInfo where com_id="&idprod
'						set rsu=conn.execute(sqlu)
'						if not rsu.eof or not rsu.bof then
'							com_jyfs=rsu("com_jyfs")
'							com_zyyw=rsu("com_zyyw")
'							com_mysl=rsu("com_mysl")
'							com_Area=rsu("com_Area")
'							com_wlyx=rsu("com_wlyx")
'						end if
'						rsu.close
'						set rsu=nothing
						%>
						<!--<table width="100%" border="0" cellspacing="0" cellpadding="3" id="jyTable" <%' If khgl<>"3" Then response.write "style='display:none;'"%>>
                          <tr>
                            <td><font color="red">*��Ӫ��ʽ</font></td>
                            <td><%'call catesnew("Cate_Crmjyfs","com_jyfs",com_jyfs)%></td>
                            <td><font color="red">*��Ӫ</font></td>
                            <td><input name="com_zyyw" type="text" id="com_zyyw" maxlength="10" value="<%'=com_zyyw%>" onKeyUp="setContent()">
                              10����</td>
                            <td><font color="red">*����/��</font></td>
                            <td><input name="com_mysl" type="text" id="com_mysl" size="3" value="<%'=com_mysl%>"  onKeyUp="setContent()"></td>
                            <td><font color="red">*����</font></td>
                            <td><%'call catesnew("Cate_CrmArea","com_Area",com_Area)%></td>
                            <td>��������</td>
                            <td><%'call catesnew("Cate_crmwlyx","com_wlyx",com_wlyx)%></td>
                          </tr>
                        </table>-->
						<% 'sqlc="select * from Crm_CompStore where com_id="&idprod
'						set rsc=conn.execute(sqlc)
'						if not(rsc.eof and rsc.bof) then
'							storeCate=rsc("storeCate")
'							storeDesc=rsc("storeDesc")
'							storePrice=rsc("storePrice")
'							storeCount=rsc("storeCount")
'						end if
'						rsc.close
'						set rsc=nothing
						 %>
						<!--<table width="100%" border="0" cellpadding="3" cellspacing="0" bgcolor="#B5EEB5">
                          <tr>
                            <td><strong>�ڻ��ͻ�:</strong>
								<select name="isth" id="isth">
									<option value="">ѡ��</option>
									<option value="1">��</option>
									<option value="0">��</option>
								</select>
							��Ʒ���
                            	<input name="storeCate" type="text" size="20" id="storeCate"  value="<%'= storeCate %>"/></td>
                            <td>��Ʒ����:
                            	<input name="storeDesc" type="text" size="40" id="storeDesc" value="<%'= storeDesc %>" /></td>
                            <td>�۸�:
                            	<input name="storePrice" type="text" size="10" id="storePrice" value="<%'= storePrice %>"/>
                            	Ԫ/��</td>
                            <td>����:
                            	<input name="storeCount" type="text" size="10" id="storeCount" value="<%'= storeCount %>" />
                            	��</td>
                            </tr>
                        </table>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td>
    <iframe id="personinfob" name="personinfob"  style="HEIGHT: 100px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="crm_compStore.asp?com_id=<%=rscom("com_id")%>"></iframe>
    
    </td>
  </tr>
</table>-->
						</td>
                      </tr>
                  </table></td>
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
                        <script>
						function jlcontact()
						{
							var jlflagT=document.getElementById("jlflag");
							var contactflag=document.getElementById("contactflag");
							var jlButton=document.getElementById("jlButton")
							if (jlflagT.style.display=="")
							{
								jlflagT.style.display="none"
								contactflag.value="0"
								jlButton.value="���ۼ�¼"
							}else
							{
								jlflagT.style.display=""
								contactflag.value="1"
								jlButton.value="��ȡ����¼"
							}
						}
						function lxcontact()
						{
							var lxflagT=document.getElementById("lxflag");
							var lxcontactflag=document.getElementById("lxcontactflag");
							var lxButton=document.getElementById("lxButton")
							if (lxflagT.style.display=="")
							{
								lxflagT.style.display="none"
								lxcontactflag.value="0"
								lxButton.value="������ϵ��"
							}
							else
							{
								lxflagT.style.display=""
								lxcontactflag.value="1"
								lxButton.value="��ȡ��������ϵ��"
							}
						}
						</script>
						<input type="button" id="jlButton" class=button name="Submit" onClick="jlcontact()" value="���ۼ�¼">
                        
                        <input name="contactflag" type="hidden" id="contactflag" value="0">
						<input name="lxcontactflag" type="hidden" id="lxcontactflag" value="0">
                        
				        <input type="button" class=button name="Submit" id="lxButton" onClick="lxcontact()" value="������ϵ��">
						<input type="button" name="Submit2" class=button value="�鿴�����¼" onClick="window.open('http://admin.zz91.com/admin1/compinfo/crm_servicetel_comp.asp?com_id=<%=idprod%>','_black','')">
&nbsp;&nbsp;<input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('http://admin.zz91.com/admin1/adv/localADlistStat.asp?email=<%=trim(rscom("com_email"))%>')" value="���ʹ��">&nbsp;&nbsp;		
					<input name="Submit4" type="submit" class="button" value="����" id="submitsave2"> 
                    <input type="button" id="aduseButton" name="aduseButton" class=button onClick="window.open('rizhi/salesHistory.asp?com_id=<%=trim(rscom("com_id"))%>')" value="�ͻ���־">
				 </td>
  </tr>
</table>
<%dis="display:none"%>
<div id="jlflag" class="searchtable" style="<%=dis%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFDBA4">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="150" align="right">�´���ϵʱ�䣺</td>
    <td><script language=javascript>createDatePicker("contactnext_time",true,Date(),false,false,false,true)</script></td>
    <td width="150" align="right">��ϵ�����</td>
    <td><input type='radio' name='contacttype' value="13" onClick="changeTab(13)" <%if contacttype="13"  then response.write("checked")%>/>      ��Ч��ϵ
      <input type='radio' name='contacttype' onClick="changeTab(12)" value="12" <%if contacttype="12"  then response.write("checked")%>/>
      ��Ч��ϵ</td>
    
    <td width="300"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="tabinfo1" <%=tabdip1%>>
  <tr>
    <td width="5">&nbsp;</td>
    <td bgcolor="#CCCC00"><input type='radio' name='c_Nocontact' value='1' />���˽���<input type='radio' name='c_Nocontact' value='2' />�������<input type='radio' name='c_Nocontact' value='3' />ͣ��<input type='radio' name='c_Nocontact' value='4' />�ػ�</td>
  </tr>
</table></td>
  </tr>
</table></td>
  </tr>
</table>

<script>
function changeTab(ID)
{
	if (ID=="13")
	{
		//document.getElementById("tabinfo").style.display=""
		document.getElementById("tabinfo1").style.display="none"
	}else
	{
		//document.getElementById("tabinfo").style.display="none"
		document.getElementById("tabinfo1").style.display=""
	}
}
</script>



<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tabinfo" <%=tabdip%>>
                          <tr align="center"> 
                        <td width="140" align="right" bgcolor="#FFFFCC">�ͻ��ȼ���</td>
                        <td align="left" bgcolor="#FFFFCC">
                       <!-- <select name="com_rank" id="com_rank" style="background:#ebebeb">
                        <option value="">--��ѡ��ȼ�--</option>
                          <%
					  'sqlcate="select * from cate_kh_csd order by code asc"
'					  set rscc=server.CreateObject("ADODB.recordset")
'					  rscc.open sqlcate,conn,1,1
'					  if not rscc.eof then 
'					  do while not rscc.eof
'					  if isnull(com_rank) then
'					  else
'						  if cstr(rscc("meno"))=cstr(com_rank) then
'						  rankchecked="selected"
'						  else
'						  rankchecked=""
'						  end if
'					  end if
					  %>
                          <option value="<%'=rscc("meno")%>" <%=rankchecked%>><%'for i=0 to cint(rscc("meno"))-1%>��<%'next%><%'=rscc("bz")%></option>
                          <%
					  'rscc.movenext
'					  loop
'					  end if
'					  rscc.close()
'					  set rscc=nothing
					  %>
                        </select>
                        -->
                        <select name="com_rank" id="com_rank" style="background:#ebebeb">
                          <option value="">--��ѡ��ȼ�--</option>
                          <option value="1" >����������������</option>
                          <option value="2" >�����������Ŀǰ������</option>
                          <option value="3" >���������ã�����һ��</option>
                          <option value="4" >����������ã������</option>
                          <option value="5" >�������ͷȷ������</option>
                        </select>
                        <script>selectOption("com_rank","<%=com_rank%>")</script>
                        </td>
                        <td align="right" nowrap bgcolor="#FFFFCC">�ͻ�̬�ȣ�</td>
                        <td align="left" bgcolor="#FFFFCC"><%= cateRadioMeno("cate_crmTaidu","c_Taidu","")%></td>
                        <td align="right" bgcolor="#FFFFCC">�ͻ�״����</td>
                        <td align="left" bgcolor="#FFFFCC"><%=cateMeno("cate_CrmCompStation","c_compStation","")%></td>
                        </tr>
                     
                          <tr align="center">
                            <td align="right" bgcolor="#FFFFCC">������ܣ�</td>
                            <td align="left" bgcolor="#FFFFCC"><%=cateRadioMeno("cate_crmserverIntro","c_serverIntro","")%></td>
                            <td align="right" bgcolor="#FFFFCC">������ʽ��</td>
                            <td align="left" bgcolor="#FFFFCC"><%=cateMeno("cate_crmServerType","c_ServerType","")%></td>
                            <td align="right" bgcolor="#FFFFCC">�ߵ���ʽ��</td>
                            <td align="left" bgcolor="#FFFFCC"><%=cateRadioMeno("cate_crmServerGo","c_ServerGo","")%></td>
                          </tr>
                          <tr align="center">
                            <td align="right" bgcolor="#FFFFCC">��Ʒ���ϣ�</td>
                            <td align="left" bgcolor="#FFFFCC"><%=cateRadioMeno("cate_crmServerTo","c_ServerTo","")%></td>
                            <td align="right" nowrap bgcolor="#FFFFCC">����ʺţ�</td>
                            <td align="left" bgcolor="#FFFFCC"><%=cateRadioMeno("Cate_CrmPayType","c_PayType","")%></td>
                            <td align="right" nowrap bgcolor="#FFFFCC">&nbsp;</td>
                            <td align="left" bgcolor="#FFFFCC">&nbsp;</td>
                          </tr>
                      <tr align="center" style="display:none">
                        <td align="right" bgcolor="#FFFFCC">��Ȥ�㣺</td>
                        <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case1" rows="3" id="case1"></textarea></td>
                        <td align="right" nowrap bgcolor="#FFFFCC">����㣺</td>
                        <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case2" rows="3" id="case2"></textarea></td>
                        <td align="right" nowrap bgcolor="#FFFFCC">�´θ����㣺</td>
                        <td width="280" align="left" bgcolor="#FFFFCC"><textarea name="case3" rows="3" id="case3"></textarea></td>
                      </tr>
                      <tr align="center"> 
                        <td align="right" bgcolor="#FFFFCC">��ע��</td>
                        <td colspan="5" align="left" bgcolor="#FFFFCC">
                        
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="210" bgcolor="#FFCC00"><%
						'0 ��ǩ 1 BD�� 2 ��ǩ 3 ���
						%><input type="radio" name="telflag" id="radio" value="0">��ǩ����ͨ<input type="radio" name="telflag" id="radio2" value="2">��ǩ����ͨ<input type="radio" name="telflag" id="radio3" value="3">���</td>
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
        <td align="right">�绰��</td>
        <td><input name="PersonTel" type="text" id="PersonTel"></td>
        <td align="right">�ֻ���</td>
        <td><input name="PersonMoblie" type="text" id="PersonMoblie"></td>
        <td align="right">Email��</td>
        <td><input name="PersonEmail" type="text" id="PersonEmail"></td>
      </tr>
      
      <tr>
        <td align="right">���棺 </td>
        <td><input name="PersonFax" type="text" id="PersonFax"></td>
        <td align="right">������ϵ��ʽ��</td>
        <td><input name="PersonOther" type="text" id="PersonOther"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="searchtable">
  <tr>
    <td><iframe id=topt name=topt  style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="crm_telList.asp?com_id=<%=rscom("com_id")%>"></iframe></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><input name="Submit" type="submit" class="button" value="����" id="submitsave"> 
&nbsp;&nbsp;                          &nbsp;&nbsp; <input name="reset2" type="button" class="button" value="�ر�" onClick="parent.window.close();"></td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</td>
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