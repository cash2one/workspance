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
sms_code=request("sms_code")
'----------�����Ŀ����
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

'-------------�ж��Ƿ��÷Ƿ��۸�DOTYPE
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
			response.Write("��û��Ȩ�޲�����")
			response.End()
		end if
	end if
'end if

 sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 rsuser.close
 set rsuser=nothing
 'response.Write(adminuserid)
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>crm ��˾�б�</title>
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
<table width="100%" id=secTable border="0" cellspacing="0" cellpadding="0">
  <tr>
    
    <td height="28" nowrap class="<%=topselectbutton(doaction,"")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&lmcode=<%=lmcode%>'"><%=lmtextname%> -> </td>
    <%if dotype="sms_service" or dotype="sms_sales" or dotype="sms_hangqin" then%>
	<td height="28" nowrap class="<%=topselectbutton(doaction,"haveopen")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=haveopen&lmcode=<%=lmcode%>'">�ѿ�ͨ</td>
    <td height="28" nowrap class="<%=topselectbutton(doaction,"nohaveopen")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=nohaveopen&lmcode=<%=lmcode%>'">δ��ͨ</td>
    <%end if%>
    <%if left(dotype,3)="sms" then%>
    <td height="28" nowrap class="<%=topselectbutton(doaction,"duanxin")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=duanxin&lmcode=<%=lmcode%>'">���ſͻ�</td>
    <%end if%>
    <td align="left" nowrap class="unselect" onClick="window.open('http://adminasto.zz91.com/companyadd/?addtype=zst&personid=<%=session("personid")%>','_blank','width=700,height=500')">
	 �ֶ�¼��</td>
     <td align="left" nowrap class="unselect" onClick="window.open('pipei/p_add.asp','_blank','width=600,height=500')">
	 �����ͻ��ύ</td>
 	<td height="28" nowrap class="<%=topselectbutton(doaction,"orderbaojia")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=orderbaojia&lmcode=<%=lmcode%>'">�ʼ����ı��ۿͻ�</td>
    <td height="28" nowrap class="<%=topselectbutton(doaction,"orderoffer")%>" onClick="window.location='?dotype=<%response.Write(dotype)%>&sms_code=<%=sms_code%>&lmaction=<%response.Write(lmaction)%>&cach=<%response.Write(docach)%>&action=orderoffer&lmcode=<%=lmcode%>'">�ʼ����Ĺ���ͻ�</td>
    <td width="100%" align="left" class="unselect">&nbsp;</td>
  </tr>
</table>
<%
sqlw="select top 1 tcontent from crm_awoke where tcheck=1 order by id desc"
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
  <form name="form2" method="get" action="crm_complist_gonghai.asp" onSubmit="getprovincename()">
    <tr>
      <td height="30" align="left" bgcolor="#FFFFFF">
	    <table border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td align="right" nowrap>��˾��:</td>
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
            <input type="hidden" name="sms_code" id="sms_code" value="<%=sms_code%>"></td>
          <td nowrap>��ϵ��:</td>
          <td><input name="com_contactperson" type="text" class=text id="com_contactperson" style="background-color:#fff;" size="15" value="<%=request("com_contactperson")%>"></td>
          <td align="right" nowrap>�����Ч��ϵʱ��: </td>
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
          <td align="right" nowrap>�绰/�ֻ�: </td>
          <td><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15" value="<%=request("com_tel")%>"></td>
          <td><strong style="color:#F00">Email:</strong></td>
          <td><input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15"></td>
          <td align="right">�����¼ʱ��: </td>
          <td><script language=javascript>createDatePicker("Lfromdate",true,"<%=request("Lfromdate")%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("Ltodate",true,"<%=request("Ltodate")%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td align="right">��&nbsp; ַ:</td>
          <td><input name="com_add" type="text" class=text id="com_add" style="background-color:#fff;" size="15"></td>
          <td>��&nbsp; ҵ:</td>
          <td>
		  <select name="clscb" id="clscb" style="width:130px">
		  <option value="">--��ѡ��--</option>
            <option value="1">�Ͻ���</option>
            <option value="2">������</option>
            <option value="3">�Ͼ���̥�����</option>
            <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
            <option value="5">��ֽ</option>
            <option value="6">�ϵ��ӵ�����ϵ����豸</option>
            <option value="10">�ϲ������ľ��Ʒ</option>
            <option value="12">�Ͼ��豸��ɽ�ͨ����</option>
          </select>
          <script>selectOption("clscb","<%=request("clscb")%>")</script> 
          </td>
          <td align="right">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��:</td>
          <td>

<font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">��԰��</font>
                        <input type="hidden" name="province" id="province">
                        <input type="hidden" name="city" id="city">
                        <input type="hidden" name="cprovince" id="cprovince" value="0">
                <script>
                            //������԰��
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=request("province1")%>";
                            var Fvalue2="<%=request("city1")%>";
                            var Fvalue3="<%=request("Garden")%>";
                            var hyID="clscb";//��ҵID��
                            getprovincevalue();
                        </script>
                <script>
                        function getprovincename()
                        {
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
							//alert(document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text)
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>

</td>
          </tr>
        <tr>
          <td align="right">��&nbsp; ��:</td>
          <td nowrap>
          
           <select name="com_rank" id="com_rank" style="background:#ebebeb; width:120px">
              <option value="">--��ѡ��ȼ�--</option>
              <option value="1" >����������������</option>
              <option value="2" >�����������Ŀǰ������</option>
              <option value="3" >���������ã�����һ��</option>
              <option value="4" >����������ã������</option>
              <option value="4.1" >|����������</option>
              <option value="4.8" >|����������</option>
              <option value="5" >�������ͷȷ������</option>
            </select>
              <script>selectOption("com_rank","<%=request("com_rank")%>")</script></td>
          <td>&nbsp;</td>
          <td nowrap>
            
            <select name="showitems" id="showitems">
	        <option value="">��ѡ��</option>
	        <option value="offer">�й�����Ϣ</option>
	        <option value="receive">���յ�ѯ��</option>
	        <option value="send">�з���ѯ��</option>
          </select>
          <script>selectOption("showitems","<%=request("showitems")%>")</script>
            </td>
          <td align="right">ע��ʱ��:</td>
          <td><script language=javascript>createDatePicker("Regfromdate",true,"<%=Regfromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("Regtodate",true,"<%=Regtodate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td align="right">��Ӫҵ��:</td>
          <td nowrap><input name="zyyw" type="text" class="text" id="zyyw" size="15"></td>
          <td>��&nbsp; ��:</td>
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
              <option value="">Ĭ��</option>
              <option value="1">�ͻ��ȼ�</option>
              <option value="2">��¼����</option>
              <option value="3">�´���ϵʱ��</option>
              <option value="4">����ʱ��</option>
			  <option value="5">�����ϵʱ��</option>
			  <option value="6">ע��ʱ��</option>
			  <option value="7">�����½ʱ��</option>
			  <option value="8">����ʱ��</option>
              <option value="9">���в�����</option>
            </select>
			<script>selectOption("comporder","<%=request("comporder")%>")</script>
            <select name="ascdesc" id="ascdesc">
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>����</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>����</option>
	        </select><input name="duochangoder" type="checkbox" id="duochangoder" onClick="opendcorder(this)" value="1" <%if duochangoder="1" then response.Write("checked")%>></td>
          <td align="right" bgcolor="#FFFFCC">��ҵ�ؼ���:</td>
          <td bgcolor="#FFFFCC">
          <select name="keywords" id="keywords">
          	<option value="">��ѡ��</option>
            <option value="����">����</option>
            <option value="ͭ">ͭ</option>
            <option value="��">��</option>
            <option value="��">��</option>
            <option value="�����">�����</option>
            <option value="��">��</option>
            <option value="Ǧ">Ǧ</option>
            <option value="п">п</option>
          </select>
          �����ؼ���
          <script>selectOption("keywords","<%=request("keywords")%>")</script>
          <select name="selectdiqu" id="selectdiqu">
          	<option value="">��ѡ��</option>
            <option value="jzh">���㻦</option>
            <option value="gd">�㶫</option>
            <option value="sd">ɽ���ӱ�</option>
            <option value="tj">���</option>
          </select>
		  <script>selectOption("selectdiqu","<%=request("selectdiqu")%>")</script>
          </td>
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
          <option value="">Ĭ��</option>
          <option value="1">�ͻ��ȼ�</option>
          <option value="2">��¼����</option>
          <option value="3">�´���ϵʱ��</option>
          <option value="4">����ʱ��</option>
          <option value="5">�����ϵʱ��</option>
          <option value="6">ע��ʱ��</option>
          <option value="7">�����½ʱ��</option>
          <option value="8">����ʱ��</option>
          <option value="9">���в�����</option>
      </select><script>selectOption("comporder1","<%=request("comporder1")%>")</script>
      2\
      <select name="comporder2" id="comporder2" style="width:80px;">
          <option value="">Ĭ��</option>
          <option value="1">�ͻ��ȼ�</option>
          <option value="2">��¼����</option>
          <option value="3">�´���ϵʱ��</option>
          <option value="4">����ʱ��</option>
          <option value="5">�����ϵʱ��</option>
          <option value="6">ע��ʱ��</option>
          <option value="7">�����½ʱ��</option>
          <option value="8">����ʱ��</option>
          <option value="9">���в�����</option>
      </select><script>selectOption("comporder2","<%=request("comporder2")%>")</script>
      3\
      <select name="comporder3" id="comporder13" style="width:80px;">
          <option value="">Ĭ��</option>
          <option value="1">�ͻ��ȼ�</option>
          <option value="2">��¼����</option>
          <option value="3">�´���ϵʱ��</option>
          <option value="4">����ʱ��</option>
          <option value="5">�����ϵʱ��</option>
          <option value="6">ע��ʱ��</option>
          <option value="7">�����½ʱ��</option>
          <option value="8">����ʱ��</option>
          <option value="9">���в�����</option>
      </select><script>selectOption("comporder3","<%=request("comporder3")%>")</script>
      4\
      <select name="comporder4" id="comporder4" style="width:80px;">
          <option value="">Ĭ��</option>
          <option value="1">�ͻ��ȼ�</option>
          <option value="2">��¼����</option>
          <option value="3">�´���ϵʱ��</option>
          <option value="4">����ʱ��</option>
          <option value="5">�����ϵʱ��</option>
          <option value="6">ע��ʱ��</option>
          <option value="7">�����½ʱ��</option>
          <option value="8">����ʱ��</option>
          <option value="9">���в�����</option>
      </select><script>selectOption("comporder4","<%=request("comporder4")%>")</script></td>
    </tr>
    <tr>
      <td align="center" bgcolor="#f2f2f2">
	  <select name="contactcount" id="contactcount">
	    <option value="">��ϵ����</option>
	    <option value="0">0</option>
	    <option value="5">&lt;5</option>
	    <option value="10">5&lt;-&gt;10</option>
	    </select>
        <script>selectOption("contactcount","<%=request("contactcount")%>")</script>
	  <select name="logincount" id="logincount">
	    <option value="">��¼����</option>
	    <option value="2-20">2-20</option>
	    <option value="20-100">20-100</option>
	    <option value="100-300">100-300</option>
	    <option value="300">&gt;300</option>
	    </select>
        <script>selectOption("logincount","<%=request("logincount")%>")</script>
	    <select name="contactpersoncount" id="contactpersoncount">
	      <option value="">��ϵ����</option>
	      <option value="1">1�˴�</option>
	      <option value="2-5">2-5�˴�</option>
	      <option value="6-10">6-10�˴�</option>
	      <option value="10">10�˴�����</option>
	    </select>
        <script>selectOption("contactpersoncount","<%=request("contactpersoncount")%>")</script>
	    <%if left(dotype,3)="sms" then%>
        <input type="checkbox" name="konghao" id="konghao" value="1" <%if request("konghao")<>"" then response.Write("checked")%>>
	    ��ȥ������󡢿պ�
        <%end if%>
        <%if dotype="sms_bmcomp" then%>
        <select name="doperson" class="button" id="doperson" style="width:150px;">
              <option value="" >��ѡ��--</option>
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >��&nbsp;&nbsp;��&nbsp;&nbsp;��<%=userName%></option>
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
           
		<input name="ss" type="hidden" id="ss" value="1">
      <input type="submit" name="Submit3" value="  �� ��  " class=button>
      <input name="gaohaisearchFlag" type="hidden" id="gaohaisearchFlag" value="1"> 
                    
        </td>
    </tr>
  </form>
</table>

<div class="testgonghai"><a href="http://192.168.2.21/gonghaicomlist/?page=1&dotype=gonghai_all" target="_blank" style="color:#F00">�°�ICD��������</a></div>
<%end if%>
<%


'--------------����������ʱ��
errtext=""				
week=weekday(now)
nhour=Hour(now())
nMinute=Minute(now())
searchflag=0
if formatdatetime(now,4)>"07:00" and formatdatetime(now,4)<"08:30" then
	searchflag=1
end if
if formatdatetime(now,4)>"12:00" and formatdatetime(now,4)<"13:30" then
	searchflag=1
end if
if formatdatetime(now,4)>"17:30" and formatdatetime(now,4)<"24:00" then
	searchflag=1
end if
if searchflag=0 then
	errtext="<br><font color=#ff0000>�������ͻ����ܿ���ʱ�䣺���磺  12:00-13:30   ���ϣ�   17:30-22:00</font>"
end if
if cint(wtime)=7 or cint(wtime)=1 then
	searchflag=1
end if

if session("userid")="10" or session("userid")="13" or session("userid")="34" or left(dotype,3)="sms" then
	searchflag=1
end if

if dotype="gonghai_lingshi" or dotype="zhucejishenhe" or dotype="zhucejicomp" then
	searchflag=1
end if
sear=sear&"&searchflag="&request("searchflag")
'***********����/start
					sql=""
					
					
					crmpersonid="personid"
					if trim(request("com_email"))<>"" then
						sql=sql&" and com_email like '"&trim(request("com_email"))&"%'"
						sear=sear&"&com_email="&request("com_email")
					end if
					if trim(request("zyyw1"))<>"" then
						sql=sql&" and exists(select com_id from Crm_CompOtherInfo where com_id=v_salescomp_g.com_id and com_zyyw like '%"&trim(request("zyyw"))&"%')"
						sear=sear&"&zyyw="&request("zyyw")
					end if
					if trim(request("zyyw"))<>"" then
						
						
						sql=sql&" and com_productslist_en like '%"&trim(request("zyyw"))&"%'"
						'sql=sql&" and exists(select com_id from temp_gonghai_keywords where keywords='"&trim(request("zyyw"))&"' and com_id=v_salescomp_g.com_id)"
						
						sear=sear&"&zyyw="&request("zyyw")
					end if
					if request("keywords")<>"" then
						if request("keywords")="����" then
							sql=sql&" and com_keywords='1'"
						else
							sql=sql&" and exists(select com_id from temp_gonghai_keywords where keywords='"&trim(request("keywords"))&"' and com_id=v_salescomp_g.com_id)"
						end if
						sear=sear&"&keywords="&request("keywords")
					end if
					if request("nfromdate")<>"" And request("ntodate")<>"" Then
						sql=sql&" and contactnext_time>'"&cdate(request("nfromdate"))&"' and  contactnext_time<'"&cdate(request("ntodate"))+1&"'"
						sear=sear&"&nfromdate="&request("nfromdate")&"&ntodate="&request("ntodate")
					End if
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
					'ע��ʱ��
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
						sql=sql&" and exists(select com_id from temp_contactSearch where searchtext like '%"&trim(request("com_tel"))&"%' and com_id=v_salescomp_g.com_id)"
						sear=sear&"&com_tel="&request("com_tel")
					end if
					'-----��ϵ����
					if trim(request("telcount"))<>"" then
						if request("telcount")=5 then
							sql=sql&" and exists(select com_id from temp_telcount where com_id=v_salescomp_g.com_id and telcount<="&request("telcount")&")"
						elseif request("telcount")=10 then
							sql=sql&" and exists(select com_id from temp_telcount where com_id=v_salescomp_g.com_id and telcount<=10 and telcount>5)"
						end if
						sear=sear&"&telcount="&request("telcount")
					end if
					
					if trim(request("com_mobile"))<>"" then
						sql=sql&" and (com_mobile like '%"&trim(request("com_mobile"))&"%' or (com_id in (select com_id from crm_personinfo where PersonMoblie like '%"&trim(request("com_mobile"))&"%')))"
						sear=sear&"&com_mobile="&request("com_mobile")
					end if
					if trim(request("com_rank"))<>"" then
						if left(dotype,3)<>"sms" then
							sql=sql&" and com_rank='"&trim(request("com_rank"))&"'"
						else
							sql=sql&" and scom_rank='"&trim(request("com_rank"))&"'"
						end if
						
						sear=sear&"&com_rank="&request("com_rank")
					end if
					if request("province")<>"" then
						if request("province")<>"" and request("province")<>"��ѡ��..." then
							sql=sql&" and com_province like '%"&request("province")&"%'"
						end if
						'sql=sql&" and exists(select com_id from comp_provinceID where com_id=v_salescomp_g.com_id and province="&trim(request("province1"))&""
						sear=sear&"&province="&request("province")
						if request("city")<>"" and request("city")<>"��ѡ��..." then
							sql=sql&" and com_province like '%"&request("city")&"%'"
							sear=sear&"&city="&request("city")
						end if
						'if request("city1")<>"" then
'							sql=sql&" and city = "&trim(request("city1"))&""
'							sear=sear&"&city1="&request("city1")
'						end if
						if request("Garden")<>"" then
							sql=sql&" and exists(select com_id from comp_provinceID where Garden = "&trim(request("Garden"))&" and com_id=v_salescomp_g.com_id)"
							sear=sear&"&Garden="&request("Garden")
						end if
'						sql=sql&")"
					end if	
					if request("clscb")<>"" then
						sql=sql&" and com_keywords='"&request("clscb")&"'"
						sear=sear&"&clscb="&request("clscb")
					end if
					if trim(request("com_contactperson"))<>"" then
						sql=sql&" and (com_contactperson like '%"&trim(request("com_contactperson"))&"%' or (com_id in (select com_id from crm_personinfo where personname like '%"&trim(request("com_contactperson"))&"%')))"
						sear=sear&"&com_contactperson="&request("com_contactperson")
					end if
					
					'---------�ͻ������������
					if request("showitems")<>"" then
						if request("showitems")="offer" then
							sql=sql&" and exists(select com_id from temp_statCount where countOffer>0 and com_id=v_salescomp_g.com_id)"
						end if
						if request("showitems")="receive" then
							sql=sql&" and exists(select com_id from temp_statCount where countreceiveQuestion>0 and com_id=v_salescomp_g.com_id)"
						end if
						if request("showitems")="send" then
							sql=sql&" and exists(select com_id from temp_statCount where countsendQuestion>0 and com_id=v_salescomp_g.com_id)"
						end if
						sear=sear&"&showitems="&request("showitems")
					end if
					'----------------������
					if request("padminuserPart")<>"" then
						'sql=sql&" and personid in (select id from users where userid in (select code from cate_adminuser where partID="&request("padminuserPart")&"))"
						
						sql=sql&" and "&crmpersonid&" in (select id from users where partid="&request("padminuserPart")&")"
						sear=sear&"&padminuserPart="&request("padminuserPart")
					end if
					if request("paichu")="1" then
						sql=sql&" and com_id not in (select com_id from temp_baoliucomp)"
						sear=sear&"&paichu="&request("paichu")
					end if
					if request("Kfromdate")<>"" then
						sql=sql&" and exists(select com_id from comp_payinfo where v_salescomp_g.com_id=com_id and fromdate>='"&request("Kfromdate")&"')"
						sear=sear&"&Kfromdate="&request("Kfromdate")
					end if
					
					'----��¼����
					if request("logincount")<>"" then
						if request("logincount")="2-20" then
							sql=sql&" and logincount>=2 and logincount<=20 "
						end if
						if request("logincount")="20-100" then
							sql=sql&" and logincount>20 and logincount<=100"
						end if
						if request("logincount")="100-300" then
							sql=sql&" and logincount>100 and logincount<=300"
						end if
						if request("logincount")=">300" then
							sql=sql&" and logincount>300"
						end if
						sear=sear&"&logincount="&request("logincount")
					end if
					'------��ϵ����
					if request("contactcount")<>"" then
						if request("contactcount")="0" then
							sql=sql&" and exists(select com_id from temp_telcount where telcount=0 and com_id=v_salescomp_g.com_id)"
						end if
						if request("contactcount")="5" then
							sql=sql&" and exists(select com_id from temp_telcount where telcount>0 and telcount<=5 and com_id=v_salescomp_g.com_id)"
						end if
						if request("contactcount")="10" then
							sql=sql&" and exists(select com_id from temp_telcount where telcount>5 and telcount<=10 and com_id=v_salescomp_g.com_id)"
						end if	
						sear=sear&"&contactcount="&request("contactcount")
					end if
					'------��ϵ�˴�
					if request("contactpersoncount")<>"" then
						if request("contactpersoncount")="1" then
							sql=sql&" and exists(select com_id from temp_telpersonCount where pcount=1 and com_id=v_salescomp_g.com_id)"
						end if
						if request("contactpersoncount")="2-5" then
							sql=sql&" and exists(select com_id from temp_telpersonCount where pcount>=2 and pcount<=5 and com_id=v_salescomp_g.com_id)"
						end if
						if request("contactpersoncount")="6-10" then
							sql=sql&" and exists(select com_id from temp_telpersonCount where pcount>6 and pcount<=10 and com_id=v_salescomp_g.com_id)"
						end if
						if request("contactpersoncount")="10" then
							sql=sql&" and exists(select com_id from temp_telpersonCount where pcount>10 and com_id=v_salescomp_g.com_id)"
						end if	
						sear=sear&"&contactpersoncount="&request("contactpersoncount")
					end if
					selectdiqu=request("selectdiqu")
					if selectdiqu<>"" then
						if selectdiqu="jzh" then
							sql=sql&" and exists(select com_id from comp_ProvinceID where province in (21,15,11) and com_id=v_salescomp_g.com_id)"
						end if
						if selectdiqu="gd" then
							sql=sql&" and exists(select com_id from comp_ProvinceID where province=31 and com_id=v_salescomp_g.com_id)"
						end if
						if selectdiqu="sd" then
							sql=sql&" and exists(select com_id from comp_ProvinceID where province in (27,14) and com_id=v_salescomp_g.com_id)"
						end if
						if selectdiqu="tj" then
							sql=sql&" and exists(select com_id from comp_ProvinceID where province in (12,1201) and com_id=v_salescomp_g.com_id)"
						end if
						sear=sear&"&selectdiqu="&request("selectdiqu")
					end if
					'--------��ȥ������󣬿պŵĿͻ�
					if request("konghao")<>"" then
						sql=sql&" and not exists(select com_id from crm_category_info where property_id=v_salescomp_g.com_id and property_value='10040008') "
						sear=sear&"&konghao="&request("konghao")
					end if
				'***********����/end
				
				if doaction<>"" then
					if doaction="orderbaojia" then
						sql=sql&"  and exists(select com_id from comp_PriceOrder where  com_id=v_salescomp_g.com_id) "
					end if
					if doaction="orderoffer" then
						sql=sql&" and exists(select com_id from comp_bizExpress where  com_id=v_salescomp_g.com_id) "
					end if
					'-----�ѿ�ͨ��sms�ͻ�
					if doaction="haveopen" then
						sql=sql&" and exists(select company_id from sms_subscribe where company_id=v_salescomp_g.com_id) "
					end if
					'-----δ��ͨ��sms�ͻ�
					if doaction="nohaveopen" then
						sql=sql&" and not exists(select company_id from sms_subscribe where company_id=v_salescomp_g.com_id) "
					end if
					'--------��ȥ������󣬿պŵĿͻ�
					if doaction="duanxin" then
						sql=sql&" and exists(select com_id from comp_comly where com_id=v_salescomp_g.com_id and lyCode like '31%') "
					
					end if
					
					sear=sear&"&action="&doaction
				end if



if searchflag=1 then
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
      <%
	  			sear=sear&"&dotype="&dotype
				
				if left(dotype,3)<>"sms" and dotype<>"gonghai_lingshi" and dotype<>"zhucejicomp" then
					sql=sql&" and not exists(select com_id from crm_assign where com_id=v_salescomp_g.com_id) and not exists(select com_id from comp_zstinfo where com_id=v_salescomp_g.com_id) "
					sql=sql&" and not EXISTS(select com_id from crm_continue_assign where com_id=v_salescomp_g.com_id) and not exists(select com_id from crm_compzhuceji where com_id=v_salescomp_g.com_id )"
				end if
				if dotype<>"zhucejishenhe" then
					sql=sql&" and not exists(select com_id from temp_assign_comp where com_id=v_salescomp_g.com_id)"
				end if
				'---------���Ǹ߻�ȥ��
				if dotype="gonghai_all" then
					sql=sql&" and not exists(select com_id from crm_category_info where property_id=v_salescomp_g.com_id and property_value='10040004')"
				end if
				'------------�ҵĿͻ�
				if dotype="sms_my" then
					sql=sql&" and exists(select com_id from crm_assignsms where com_id=v_salescomp_g.com_id and personid="&session("personid")&") "
				end if
				'-------����Ϊע����ͻ�
				if dotype="zhucejishenhe" then
					if cint(wtime)=2 then
						sql=sql&" and com_regtime>'"&date-3&"'"
					else
						sql=sql&" and com_regtime>'"&date-1&"'"
					end if
				end if
				'-------ע����ͻ��б�
				if dotype="zhucejicomp" then
					sql=sql&" and exists(select com_id from crm_compzhuceji where com_id=v_salescomp_g.com_id )"
				end if
				
				'-----�ų����Ӻ�����пͻ�
				if dotype="gonghai_lingshi" then
					sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp_g.com_id and personid=516)"
				end if
				
				'--------------�ƶ�CRM���� begin
				if dotype="sms_gonghai" then
					sql=sql&" and not exists(select com_id from crm_assign where com_id=v_salescomp_g.com_id) "
				end if
				'-------ICD˽���ͻ�
				if dotype="sms_sihai" then
					sql=sql&" and exists(select com_id from crm_assign where com_id=v_salescomp_g.com_id) "
				end if
				
				if dotype<>"gonghai_zhucha" and dotype<>"gonghai_lingshi" and dotype<>"gonghai_chuzaALL" and dotype<>"gonghai_all" and left(dotype,3)<>"sms" then
					sql=sql&" and not exists(select com_id from crm_category_info where property_id=v_salescomp_g.com_id and left(property_value,4)='1004')"
				end if
				'--------�鿴����ע��
				if dotype="sms_viewprice" then
					sql=sql&" and exists(select com_id from comp_regFrom where com_id=v_salescomp_g.com_id and RegFrom=20) "
				end if
				
				'-------�ƶ����ſͻ�
				if dotype="sms_bmcomp" then
					sql=sql&" and exists(select com_id from crm_assignsms where com_id=v_salescomp_g.com_id) "
					if request("doperson")<>"" then
						sql=sql&" and spersonid="&request("doperson")&""
						sear=sear&"&doperson="&request("doperson")
					end if
				end if
				
				'-------���찲����ϵ
				if dotype="sms_todayContact" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and spersonid="&request("doperson")&" "
								sear=sear&"&doperson="&request("doperson")
						    end if
							sql=sql&" and ((scontactnext_time>'"&date()&"' and scontactnext_time<'"&date()+1&"') )"
						else
							sql=sql&" and spersonid="&session("personid")&" and ((scontactnext_time>'"&date()&"' and scontactnext_time<'"&date()+1&"')) "
						end if
				end if
				
				'---------�����ͻ�
				if dotype="sms_noContact" then
						if session("userid")="10" then
						    if request("doperson")<>"" then
								sql=sql&" and spersonid="&request("doperson")&""
								sear=sear&"&doperson="&request("doperson")
						    end if
						else
							sql=sql&" and spersonid='"&session("personid")&"' "
							sear=sear&"&doperson="&request("doperson")
						end if
							sql=sql&" and scontactnext_time<'"&date()&"' and scontactnext_time>'1990-1-1'"
							'--------------begin
							'��������������Ŀͻ�
							'sql=sql&" and Not EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&session("personid")&") and com_id=v_salescomp.com_id)"
							sql=sql&" and Not EXISTS(select com_id from comp_tel where com_id=v_salescomp_g.com_id and telflag=4"
							
							if request("doperson")<>"" then
								sql=sql&" and personid>"&request("doperson")&" and personid<"&request("doperson")&""
							else
								sql=sql&" and personid>"&session("personid")&" and personid<"&session("personid")&""
						    end if
							
							sql=sql&")"
				end if

				'--------------�ƶ�CRM���� end
				
				
				
				'-----����
				if dotype="gonghai_suliao" then
					sql=sql&" and com_keywords='2'"
				end if
				'-----����
				if dotype="gonghai_jinshu" then
					sql=sql&" and com_keywords='1'"
				end if
				'----���� ���㻦
				if dotype="gonghai_province_jzf" then
					sql=sql&" and exists(select com_id from comp_ProvinceID where province in (21,15,11) and com_id=v_salescomp_g.com_id)"
				end if
				'----���� �㶫
				if dotype="gonghai_province_gd" then
					sql=sql&" and exists(select com_id from comp_ProvinceID where province=31 and com_id=v_salescomp_g.com_id)"
				end if
				'----���� ɽ���ӱ�
				if dotype="gonghai_province_sd" then
					sql=sql&" and exists(select com_id from comp_ProvinceID where province in (27,14) and com_id=v_salescomp_g.com_id)"
				end if
				'----���� ���ŵ���
				if dotype="gonghai_province_remen" then
					sql=sql&" and exists(select com_id from comp_ProvinceID where province in (14,21,15,11,27,31) and com_id=v_salescomp_g.com_id)"
				end if
				'----���� ��������
				if dotype="gonghai_province_other" then
					sql=sql&" and not exists(select com_id from comp_ProvinceID where province in (14,21,15,11,27,31) and com_id=v_salescomp_g.com_id)"
				end if
				'-------��ɢ��
				if dotype="gonghai_jsd" then
					sql=sql&" and exists(select com_id from comp_ProvinceID where Garden>0 and com_id=v_salescomp_g.com_id)"
				end if
				'-------��Ӧ��
				if dotype="gonghai_sales" then
					sql=sql&" and exists(select com_id from Comp_SalesType where salesType=1 and com_id=v_salescomp_g.com_id)"
				end if
				'-------�ɹ���
				if dotype="gonghai_buy" then
					sql=sql&" and exists(select com_id from Comp_SalesType where salesType=2 and com_id=v_salescomp_g.com_id)"
				end if
				'-------�ɹ���+��Ӧ��
				if dotype="gonghai_salesbuy" then
					sql=sql&" and exists(select com_id from Comp_SalesType where salesType=0 and com_id=v_salescomp_g.com_id)"
				end if
				'-----����4��
				if dotype="gonghai_have4star" then
					sql=sql&" and exists(select com_id from temp_have4star where com_id=v_salescomp_g.com_id)"
				end if
				'-----����5��
				if dotype="gonghai_have5star" then
					sql=sql&" and exists(select com_id from temp_have5star where com_id=v_salescomp_g.com_id)"
				end if
				'-----��¼��� 2-20��
				if dotype="gonghai_login2_20" then
					sql=sql&" and logincount>=2 and logincount<=20"
				end if
				'-----��¼��� 20-100��
				if dotype="gonghai_login20_100" then
					sql=sql&" and logincount>=20 and logincount<=100"
				end if
				'-----��¼��� 20-100��
				if dotype="gonghai_login100_300" then
					sql=sql&" and logincount>100 and logincount<=300"
				end if
				'-----��ϵ��� ��δ��ϵ
				if dotype="gonghai_contact0" then
					sql=sql&" and not exists(select com_id from comp_tel where com_id=v_salescomp_g.com_id)"
				end if
				'-----��ϵ��� ����5��
				if dotype="gonghai_contact5" then
					sql=sql&" and exists(select com_id from temp_telcount where telcount<=5 and com_id=v_salescomp_g.com_id)"
				end if
				'-----��ϵ��� ����5��
				if dotype="gonghai_contact10" then
					sql=sql&" and exists(select com_id from temp_telcount where telcount>5 and telcount<=10 and com_id=v_salescomp_g.com_id)"
				end if
				'-----��ϵ��� -һ������ϵ��
				if dotype="gonghai_contact1person" then
					sql=sql&" and exists(select com_id from temp_telpersonCount where com_id=v_salescomp_g.com_id and pcount=1)"
				end if
				if dotype="gonghai_beidongdiao" then
					sql=sql&" and exists(select com_id from crm_dropedInsea where com_id=v_salescomp_g.com_id"
					if session("userid")="13" or session("userid")="10" then
					else
						sql=sql&" and personid="&session("personid")&""
					end if
					sql=sql&")"
				end if
				'----------��������
				if dotype="gonghai_zhucha" then
					gonghai_out_code=request.QueryString("gonghai_out_code")
					if gonghai_out_code<>"" then
						sql=sql&" and exists(select com_id from crm_category_info where property_id=v_salescomp_g.com_id and property_value='"&gonghai_out_code&"')"
					end if
					sear=sear&"&gonghai_out_code="&gonghai_out_code
				end if
				'-----------���Ӻ�����пͻ�
				if dotype="gonghai_chuzaALL" then
					sql=sql&" and not exists(select com_id from crm_category_info where property_id=v_salescomp_g.com_id and property_value like '1004____')"
				end if
				loclurl="commType"
				txtname="xzproductLy"
				MakeHtmltime=60000*60*60
				if ExistsTxt(loclurl,txtname,MakeHtmltime)<>0 then
					lycodep=creatText2008(loclurl,txtname,"",MakeHtmltime)
				end if
				if session("userid")="10" or session("userid")="13" or session("userid")="34" then
				else
					if lycodep<>"" then
						sql=sql&" and not exists(select com_id from comp_comly where lyCode in ("&lycodep&") and com_id=v_salescomp_g.com_id)"
					end if
				end if
				
					'-------------�ͻ�����
					function getordervalue(comporder)
						select case comporder
							case "1"
								if left(dotype,3)="vap" then
									getordervalue="vcom_rank"
								elseif left(dotype,3)="sms" then
									getordervalue="scom_rank"
								else
									getordervalue="com_rank"
								end if
							case "2"
								getordervalue="logincount"
							case "3"
								if left(dotype,3)="vap" then
									getordervalue="vcontactnext_time"
								elseif left(dotype,3)="sms" then
									getordervalue="scontactnext_time"
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
						end select
					end function
					'---------------------------VAP�ͻ����� / end
					
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
				     .sltFld  = "com_tel,com_mobile,com_email,com_province,com_id,com_name,com_rank,scom_rank,com_regtime,viewcount,logincount,lastlogintime,lastteldate,teldate,com_contactperson,com_desi,fdate"
					 .FROMTbl = "v_salescomp_g"
				     .sqlOrder= sqlorder
				     .sqlWhere= "WHERE not EXISTS (select com_id from test where com_id=v_salescomp_g.com_id) "&sql
				     .keyFld  = "com_id"    '����ȱ��
				     .pageSize= 10
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=int(total/10)+1
				   
				   outsql=""&sql
				   %>
	</td>
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="100%" class=ee id=ListTable border="0" cellpadding="2" cellspacing="0">
              <form action="crm_allcomp_list.asp" method="post" name=form1>
                <tr class="topline"> 
                  <td width="4" bgcolor="#DFEBFF"><span class="STYLE2"></span></td>
                  <td nowrap bgcolor="#DFEBFF">&nbsp;</td>
                  <%
				  if left(dotype,3)="sms" then
				  %>
                  <td nowrap bgcolor="#DFEBFF">����<BR>
                    ����</td>
                  <%
				  end if
				  %>
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
                  
                  
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF"><span class="style1 STYLE2">�鿴<br>��Ϣ</span></td>
                  
                  
                  
                  
                  <td align="center" class=sort id=td_address nowrap bgcolor="#DFEBFF">����ʱ��</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>VAP<br>
                    ������</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>ICD<br>
                    ������</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>SMS<br>
                    ������</td>
                  <td align="center" nowrap bgcolor="#DFEBFF" class=sort id=td_address>¼����</td>
                  
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
                  <td nowrap><a href="/admin1/crmlocal/rizhi/salesHistory.asp?com_id=<%=rs("com_id")%>&dotype=<%=dotype%>" target="_blank">��־</a></td>
                  <%
				  if left(dotype,3)="sms" then
				  %>
                  <td nowrap><a href="http://admin1949.zz91.com/reborn-admin/sms/subscribe/addOrEditSMSSubscribe.htm?companyId=<%=rs("com_id")%>&account=<%=rs("com_email")%>" target="_blank">����</a></td>
                  <%
				  end if
				  %>
				  <SPAN onmouseover=DoHL();  onDblClick="javascript:DoSL();javascript:oow('../crmlocal/crm_cominfoedit.asp?idprod=<%response.Write(rs("com_id"))%>&dotype=<%response.Write(dotype)%>&lmcode=<%=lmcode%>');" onmouseout=DoLL();>
				  <td nowrap>
				  
                  <%
				  comLx=""
				  
				  viprequest=0
				  
				  '---------------�ж�ע����Դ
				  sqlL="select id,RegFrom from comp_regFrom where com_id="&rs("com_id")&" and RegFrom>19"
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	regLyid=rsL(1)
				  	comLx="<font color=#CC9900>"&showregFrom(regLyid)&"</font>"
				  end if
				  rsL.close
				  set rsL=nothing
				   '---------------�ж�������վ�Ƿ�ע��
				  sqlL="select RegFromid from comp_snatchhaveExists where com_id="&rs("com_id")&""
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	regLyid=rsL(0)
				  	response.Write("�ÿͻ���"&showregFrom(regLyid)&"��ע���")
				  end if
				  rsL.close
				  set rsL=nothing
				  '-------------------------�ж�¼��ͻ�
				  sqlP="select com_id,Ccheck from crm_InsertCompWeb where com_id="&rs("com_id")&" and saletype=1"
				  set rsp=conn.execute(sqlP)
				  if not rsp.eof or not rsp.bof then
				  	if rsp(1)="0" then
				  		comLx="<font color=#003399>¼��ͻ�<font color=#ff0000>δ��</font></font>"
					else
						comLx="<font color=#003399>¼��ͻ�</font>"
					end if
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
						response.Write("<br><font color=#ff34de>��������ͨ</font>")
					  end if
					  if viprequest=3 then
						response.Write("<br><font color=#f534de>����Ʒ��ͨ</font>")
					  end if
				  end if
				'--------------------------------------
				'�Ƿ�Ϊ��չ�ͻ�
				response.Write(showlyfrom(rs("com_id")))
				
				sqld="select com_id from crm_bigcomp where com_id="&rs("com_id")
				set rsd=conn.execute(sqld)
				if not rsd.eof or not rsd.bof then
					response.Write("<br><font color=#ff0000>8000��ͻ�</font>")
				end if
				rsd.close
				set rsd=nothing
				
				%>
                <div id="laiyuan<%=rs("com_id")%>"></div>
                </td>
                  
                  
                   
                  <td nowrap >
				  
				  <%response.Write(rs("com_name"))%>
				  <span id="activeComp<%=com_id%>"></span>
				  <span id="fine<%=com_id%>"></span>
                  <!--<span id="zdcom<%=com_id%>"><img src="/images/loading_16x16.gif"></span>
                  <iframe name="zdiframe" src="crm_zdcomp.asp?com_id=<%=com_id%>&com_tel=<%=rs("com_tel")%>&com_mobile=<%=rs("com_mobile")%>&com_name=<%=rs("com_name")%>" frameborder='0' width="0" height="0" scrolling="no" ></iframe>-->
				  </td>
                  <td nowrap>
                  <%
				vcount=rs("viewcount")
				logincount=rs("logincount")
				lastlogintime=rs("lastlogintime")
				
				if left(dotype,3)="vap" then
					nowcomrank=rs("vcom_rank")
					if nowcomrank="4.1" then
					 	response.Write("��4��")
					 elseif nowcomrank="4.8" then
					 	response.Write("��4��")
					 else
					 	response.Write(nowcomrank&"��")
					 end if
				elseif left(dotype,3)="sms" then
					'response.Write(rs("scom_rank"))
					nowcomrank=rs("scom_rank")
				  if nowcomrank<>"-1" then
				  	 
					 if nowcomrank="4.1" then
					 	response.Write("��4��")
					 elseif nowcomrank="4.8" then
					 	response.Write("��4��")
					 else
					 	response.Write(nowcomrank&"��")
					 end if
				  end if
				else
				  if rs("com_rank")<>"-1" then
				  	 nowcomrank=rs("com_rank")
					 if nowcomrank="4.1" then
					 	response.Write("��4��")
					 elseif nowcomrank="4.8" then
					 	response.Write("��4��")
					 else
					 	response.Write(nowcomrank&"��")
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
				  %>
                  <input type="hidden" name="smobile<%=com_id%>" id="smobile<%=com_id%>" value="<%=clearcompmobile1(rs("com_mobile"))%>"></td>
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
                 
                  
                  <td align="center" nowrap> <a href="#kang" target="topt" onClick="window.open('../admin_user_viewlist.asp?com_email=<%response.Write(rs("com_email"))%>','_blank','width=500,height=500')"><%response.Write(vcount)%></A></td>
                  
                  
                  
                 
				 
                  
                  <td align="center" nowrap><%= rs("fdate") %></td>
                  <td align="center" nowrap><span id="realnamevap<%=com_id%>"></span></td>
                  <td align="center" nowrap><span id="realname<%=com_id%>"></span></td>
                  <td align="center" nowrap><span id="realnamesms<%=com_id%>"></span></td>
                  <td align="center" nowrap>
                    <span id="realnamelr<%=com_id%>"></span>
                  </td>                  
                  </SPAN> 
                  
                 
                </tr>
                <%
			                                   
     		RowCount = RowCount - 1
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="39">��ʱ����Ϣ��</td>
                </tr>
                <%end if
			  rs.close
			  set rs=nothing
			  %>
               
                <tr bgcolor="#FFFFFF"> 
                  <td colspan="40"> ȫѡ 
                    <input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                    <%
					if dotype="zhucejishenhe" then
					%>
                    <input type="button" name="Submit<%=dotype%>" class="button" value="����Ϊע����ͻ�" onClick="postAll(this.form,'ȷʵҪ����Ϊע����ͻ���?','do_zhucejishenhe')">
                    <%
					end if
					%>
                    <%
					if dotype="zhucejicomp" then
					%>
                    <input type="button" name="Submit<%=dotype%>" class="button" value="ȡ��Ϊע����ͻ�" onClick="postAll(this.form,'ȷʵҪȡ��Ϊע����ͻ���?','undo_zhucejishenhe')">
                    <%
					end if
					%>
                    <%
					if ((ywadminid<>"" and session("userid")="13") or session("userid")="10") and dotype="CustomIn" then
					%>
                    <input name="Submit6" type="button" class="button" value="���¼��" onClick="shenheluru(this.form)">
                    <%
					end if
					%>
                    
                    
             <%if session("userid") ="10" or session("userid")="13" or (ywadminid<>"0" and ywadminid<>"" and (not isnull(ywadminid)) and (dotype="allbm" or dotype="all" or dotype="bmzd" or dotype="nocontact" or dotype="my" or dotype="sbcomp" or dotype="MyContinue" or dotype="outgonghai" or lmaction="gonghai" or dotype="gonghai_lingshi"))  then%>
                    <input name="Submit" type="button" class="button" value="���·����" onClick="selectcrm(this.form)">
					<select name="personid" class="button" id="dopersonid" >
              <option value="" >��ѡ��--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >��&nbsp;&nbsp;��&nbsp;&nbsp;��<%=userName%></option>
			  <% End If %>
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where (code like '13%' or code like '26%') and closeflag=1"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1"
				  end if
			  end if
			  response.Write(sqlc)
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
					iszst=0
					if dotype="MyContinue" or dotype="TodayContinue" then iszst=1
					%>
                    <%if (dotype<>"my" and dotype<>"allall" and dotype<>"bmzd" and dotype<>"allbm") or getcompany=1  then%>
					<input type="button" name="Submit" class="button" value="�ŵ��ҵĿͻ���" onClick="selectoutmycrm(this.form)">
                    <input type="button" name="Submit" class="button" value="����Ԥ��Ʒ��ͨ" onClick="postAll(this.form,'ȷʵҪ����Ԥ��Ʒ��ͨ��?','yubeippt')">
					<%end if%>
                    <%if left(dotype,3)="sms" then%>
                    <input name="Submit5" type="button" class="button" value="���빫��" onClick="delselectcrm(this.form,<%=iszst%>)">
                    <%end if%>
					<%if dotype="my" or dotype="nocontact" or dotype="today" or dotype="contact" or dotype<>"allall"  then%>
                       <input type="button" name="Submit2" class="button" value="�ŵ���Ʒ��" onClick="selectwastecom(this.form)">
					<%end if%>
					
					
					
                    <input type="hidden" name="dostay" id="dostay">
                    <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
					<input type="hidden" name="selectcb" id="selectcb"> 
                    <input type="hidden" name="userName" value="<%=userName%>">
					<input type="hidden" name="flag" id="flag">
                    <input type="hidden" name="doflag" id="doflag"> 
                    <input type="hidden" name="page" value="<%=page%>">
                    <%
					if left(dotype,"3")="sms" then
					%>
                    <input type="button" name="Submit4" class="button" value="���Ͷ���" onClick="selectsendsms(this.form)">
                    <!--<input type="button" name="button" id="button" value="Ⱥ������" class="button" style="font-size:14px; font-weight:bold" onClick="smsout.submit()">-->
                    <%
					end if
					%>
                    </td>
                   
                </tr>
              </form>
            </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<!-- #include file="scriptstr_g.asp" -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="25"><!-- #include file="../include/page.asp" --></td>
  </tr>
 
</table>
<%
else
response.Write(errtext)
end if

%>
</div>
<script language="javascript" src="/admin1/crmlocal/viewhistory.asp?personid=<%=session("personid")%>&com_id=0"></script>
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
<form name="smsout" method="post" action="sms/send.asp" target="_blank">
  <input type="hidden" name="outsql" id="outsql" value="<%=outsql%>">
  <input type="hidden" name="total" id="total" value="<%=total%>">
</form>
<form name="selectsmssend" method="post" id="selectsmssend" action="http://admin1949.zz91.com/reborn-admin/sms/main/sendFromZZ91.htm" target="_blank">
  <input type="hidden" name="companyIds" id="comlist" value="">
  <input type="hidden" name="mobiles" id="mobilelist" value="">
</form>
<%
function changeNum1(str)
	changeNum1=""
	if isnull(str) then
	else
	for i=1 to len(str)
		if IsNumeric(mid(str,i,1)) then
			changeNum1=changeNum1&cint(mid(str,i,1))
		else
			'changeNum=changeNum&cstr(mid(str,i,1))
		end if
	next
	end if
end function
function clearcompmobile1(com_mobile)
	'----------------�ж��ֻ��ظ�
	if com_mobile<>"" and not isnull(com_mobile) then
		n=1
		b=""
		a=""
		com_mobile=changeNum1(com_mobile)
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
	clearcompmobile1=com_mobile
end function
%>
<iframe name="copekuan" src="" frameborder='0' width="0" height="0" scrolling="no" ></iframe>
</body>
</html>
<%
conn.close
set conn=nothing
%>