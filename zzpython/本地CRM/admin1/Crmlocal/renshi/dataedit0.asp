<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
dingid=request("id")
sql="select * from renshi_salesIncome where id="&request("id")&""
set rs=conn.execute(sql)
if rs.eof or rs.bof then
	response.write("Error!")
	response.end()
else
	order_no=rs("order_no")
	personid=rs("personid")
	userid=rs("userid")
	if userid="1315" then
		mbradio=1
	elseif left(userid,2)="24" then
		mbradio=3
	else
		mbradio=2
	end if
	realname=rs("realname")
	payTime=rs("sales_date")
	com_id=rs("com_id")
	service_type=rs("service_type")
	service_type1=rs("service_type1")
	customType=rs("sales_type")
	payMoney=rs("sales_price")
	newemail=rs("sales_email")
	com_mobile=rs("sales_mobile")
	remark=rs("sales_bz")
	com_contactperson=rs("com_contactperson")
	com_mobile=rs("com_mobile")
	com_ly1=rs("com_ly1")
	com_ly2=rs("com_ly2")
	com_zq=rs("com_zq")
	com_fwq=rs("com_fwq")
	com_khdq=rs("com_khdq")
	com_pro=rs("com_pro")
	com_cpjb=rs("com_cpjb")
	com_cxfs=rs("com_cxfs")
	com_regtime=rs("com_regtime")
	com_hkfs=rs("com_hkfs")
	com_logincount=rs("com_logincount")
	com_gjd=rs("com_gjd")
	com_servernum=rs("com_servernum")
	if newemail="" or isnull(newemail) then
		sqlc="select com_email from comp_info where com_id="&com_id&""
		set rsc=conn.execute(sqlc)
		if not rsc.eof or not rsc.bof then
			newemail=rsc(0)
		end if
		rsc.close
		set rsc=nothing
	end if
end if
rs.close
set rs=nothing
sqlp="select realname from users where id="&personid&""
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	realname1=rsp(0)
end if
rsp.close
set rsp=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>���ѻ�Ա���񵽿�ȷ�ϵ�</title>
<script type="text/javascript">
function chknum(NUM) 
{ 
	var i,j,strTemp; 
	strTemp=".0123456789"; 
	if ( NUM.length== 0) 
	return 0 
	for (i=0;i<NUM.length;i++) 
	{ 
		j=strTemp.indexOf(NUM.charAt(i)); 
		if (j==-1) 
		{ 
			//˵�����ַ��������� 
			return 0; 
		} 
	} 
	//˵�������� 
	return 1; 
}
function check(frm){
	
	var obj=frm
	
	var radios=obj.customType;
	var pp=1;
	var mbflag=document.getElementById("mbflag");
	if (mbflag.value==1 || mbflag.value==3)
	{
		for(var i=0;i<radios.length;i++){
			if(radios[i].checked){      
				pp=0;
			}
		}
	}else{
		pp=0
	}
	
	if (document.getElementById("mbflag").value=="")
	{
		alert("��ѡ��������ţ�");
		return false;
	}
	
	if(obj.userid.value=="")
	{
		alert("��ѡ���ţ�");
		obj.userid.focus();
		return false;
	}
	
	if(obj.service_type.value=="")
	{
		alert("��ѡ���Ʒ���ͣ�");
		obj.service_type.focus();
		return false;
	}
	
	if(obj.payTime.value==""){
		alert("����д����ʱ��!");
		obj.payTime.focus();
		return false;
	}

	if (chknum(obj.payMoney.value)==0)
	{
		alert("�����뵽�������������")
		obj.payMoney.focus()
		return false;
	}
	if(obj.payMoney.value==""){
		alert("����д������!");
		obj.payMoney.focus();
		return false;
	}
	if(obj.com_contactperson.value==""){
		alert("����д�ͻ�����!");
		obj.com_contactperson.focus();
		return false;
	}
	if(obj.com_ly1.value==""){
		alert("��ѡ��ͻ���Դ!");
		obj.com_ly1.focus();
		return false;
	}
	
	if (obj.com_ly2)
	{
		if(obj.com_ly2.value==""){
			alert("��ѡ��ͻ���Դ!");
			obj.com_ly2.focus();
			return false;
		}
	}
	if(pp==1){
		if (mbflag.value==1)
		{
			alert("��ѡ��ͻ�����!");
		}
		if (mbflag.value==3)
		{
			alert("��ѡ��ǩ������!");
		}
		return false;
	}
	if (obj.com_zq)
	{
		if(obj.com_zq.value==""){
			alert("��ѡ������!");
			obj.com_zq.focus();
			return false;
		}
	}
	
	if (obj.com_fwq)
	{
		if(obj.com_fwq.value==""){
			alert("��ѡ�������!");
			obj.com_fwq.focus();
			return false;
		}
	}
	
	if (obj.com_khdq)
	{
		if(obj.com_khdq.value==""){
			alert("��ѡ��ͻ�����!");
			obj.com_khdq.focus();
			return false;
		}
	}
	
	if (obj.com_pro)
	{
		if(obj.com_pro.value==""){
			alert("��ѡ��Ӫ��Ʒ!");
			obj.com_pro.focus();
			return false;
		}
	}
	
	if (obj.com_cpjb)
	{
		if(obj.com_cpjb.value==""){
			alert("��ѡ���Ʒ����!");
			obj.com_cpjb.focus();
			return false;
		}
	}
	
	if (obj.com_cxfs)
	{
		if(obj.com_cxfs.value==""){
			alert("�����������ʽ!");
			obj.com_cxfs.focus();
			return false;
		}
	}
	
	if (obj.com_hkfs)
	{
		if(obj.com_hkfs.value==""){
			alert("��ѡ�񸶿ʽ!");
			obj.com_hkfs.focus();
			return false;
		}
	}
	
	if (obj.com_gjd)
	{
		if(obj.com_gjd.value==""){
			alert("��ѡ��ؼ���!");
			obj.com_gjd.focus();
			return false;
		}
	}
	if (obj.com_servernum)
	{
		if (chknum(obj.com_servernum.value)==0)
		{
			alert("����������ͨ���ޱ���������")
			obj.com_servernum.focus()
			return false;
		}
		if(obj.com_servernum.value==""){
			alert("����д����ͨ����!");
			obj.com_servernum.focus();
			return false;
		}
	}
	
	if(obj.saler.value==""){
		alert("����д������Ա!");
		obj.saler.focus();
		return false;
	}
}
function changeID(obj,flag)
{
	var o=document.getElementById("confirmID");
	var v=o.value;
	if(flag=="continue"){
		o.value=obj.value+v.substring(1);
	}
	else if(flag=="year"){
		o.value=v.substring(0,v.length-1)+obj.value;
	}
}
function changeform(order_nostr,no,apply_groupstr)
{
	//
	var objform=document.getElementById("form"+no);
	
	objform.action="http://192.168.2.2/admin1/compinfo/openConfirm_save1.asp";
	objform.target="_self";
	objform.order_no.value=order_nostr
	objform.apply_group.value=apply_groupstr
	objform.submit()
}
function selectmb(n)
{
	for (i=1;i<=3;i=i+1)
	{
		document.getElementById("mb"+i).className="mb";
		//document.getElementById("mbr"+i).checked=false;
		document.getElementById("mbbox"+i).style.display="none";
	}
	var obj=document.getElementById("mb"+n);
	if (obj)
	{
		obj.className="mb_on";
		//document.getElementById("mbr"+n).checked=true;
		document.getElementById("mbbox"+n).style.display="";
		document.getElementById("mbflag").value=n;
	}
	
}

function selectOption(menuname,value)
{
    var menu = menuname;
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
function selectCheckBox(frm,boxname,thevalue)
{
	var boxes = frm.elements[boxname];
	for(var i=0;i<boxes.length;i++){
		if(thevalue.indexOf(boxes[i].value)>=0)
		{
			boxes[i].checked = true;
		}
	}
}

function openuserlist(n)
{
	var obj=document.getElementById("userlist"+n)
	obj.src="selectuser1.asp?n="+n
	obj.width="300px";
	obj.height="300px";
}

</script>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	background-color: #C8DAEC;
}
form
{
	padding:0px;
	margin:0px;
}
.mb
{
	float: left;
	width: 80px;
	margin-right: 10px;
	color: #FFF;
	background-color: #390;
	height: 30px;
	line-height: 30px;
	font-weight: bold;
	text-align: center;
	cursor:pointer;
}
.mb_on
{
	float: left;
	width: 80px;
	margin-right: 10px;
	color: #FFF;
	background-color: #F60;
	height: 30px;
	line-height: 30px;
	font-weight: bold;
	text-align: center;
}
.input 
{
	width:100%;
}
.inputselect
{
	width:200px;
}
-->
</style></head>

<body>
<table width="600" border="0" align="center" cellpadding="6" cellspacing="0">
  <tr>
       <td width="130" align="right" bgcolor="#f2f2f2">��������</td>
       <td bgcolor="#FFFFFF"><input type="hidden" name="mbflag" id="mbflag" value="">
         <div class="mb" id="mb1" onClick="selectmb(1)">
     
     VAP</div> <div class="mb" id="mb2" onClick="selectmb(2)">
     
     ICD</div> <div class="mb" id="mb3" onClick="selectmb(3)">

    CS</div></td>
    </tr>
</table>
<div id="mbbox1">
  <form id="form1" name="form1" method="post" action="dataedit_save.asp" onSubmit="return check(this)">
    <table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#f2f2f2">
      
      
      
      <tr>
        <td width="130" align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
          <script language=javascript>createDatePicker("payTime",true,"<%=payTime%>",false,true,true,true)</script>
        <input name="userid" type="hidden" id="userid" value="1315">
        <input type="hidden" name="order_no" id="order_no" value="<%=order_no%>">
		<input type="hidden" name="apply_group" id="apply_group" value="<%=apply_group%>">
		
          <input type="hidden" name="com_id" value="<%=com_id%>">
          <input type="hidden" name="personid" id="personid" value="<%=personid%>">
          <input type="hidden" name="mbradio" id="mbr1" value="1">
        <input type="hidden" name="dingid" id="dingid" value="<%=dingid%>" /></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
        <td bgcolor="#FFFFFF">
        
        <select name='service_type' id='service_type' class="inputselect" >
          <option value=''>��ѡ��...</option>
          <option value="����ͨ">����ͨ</option>
          <option value="Ʒ��ͨ">Ʒ��ͨ</option>
          <option value="չ���Ʒ">չ���Ʒ</option>
          <option value="���">���</option>
          <option value="����ֽý">����ֽý</option>
          <option value="���ű���">���ű���</option>
          <option value="�ٶ��Ż�">�ٶ��Ż�</option>
          <option value="����վ">����վ</option>
          <option value="�ƶ�����ܼ�">�ƶ�����ܼ�</option>
          <option value="����ͨ������">����ͨ������</option>
          <option value="�������">�������</option>
          <option value="���Ż�Ա">���Ż�Ա</option>
          <option value="���籦һԪ">���籦һԪ</option>
          <option value="���籦��Ԫ">���籦��Ԫ</option>
          <option value="����">����</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      <!--<tr>
         <td align="right" bgcolor="#f2f2f2">��Ʒ����2</td>
         <td bgcolor="#FFFFFF">
         <select name="service_type1" id="service_type1" class="inputselect">
         	<option value="">��ѡ��...</option>
           <option value="�ƶ�����ܼ�">�ƶ�����ܼ�</option>
           <option value="����ͨ������">����ͨ������</option>
           <option value="�������">�������</option>
           <option value="��">��</option>
         </select>
         <script>selectOption(form1.service_type1,"<%=service_type1%>")</script>
         </td>
       </tr>-->
       <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ��</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(������дʵ�ʵĵ��������������۶����׼ȷͳ��.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ��ֻ�</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ǩ�ͻ���Դ1</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="�¿ͻ�">�¿ͻ�</option>
          <option value="�����ͻ�">�����ͻ�</option>
          <option value="����ͻ�">����ͻ�</option>
          <option value="ת����">ת����</option>
          <option value="�����ռ�">�����ռ�</option>
          <option value="�Ͽͻ�">�Ͽͻ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ֵ�ͻ���Դ2</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly2" id="com_ly2" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="1200��Ա">1200��Ա</option>
          <option value="2180��Ա">2180��Ա</option>
          <option value="3500��Ա">3500��Ա</option>
          <option value="�ǻ�Ա����">�ǻ�Ա����</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_ly2,"<%=com_ly2%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ֵ����</td>
        <td bgcolor="#FFFFFF"><input type="radio" name="customType" value="������ֵ">
          ������ֵ
          <input type="radio" name="customType" value="����ֵ">
          ����ֵ
          <input type="radio" name="customType" value="��ǩ">
          ��ǩ
          <input type="radio" name="customType" value="����">
          ����     
          <script>selectCheckBox(document.form1,"customType","<%=customType%>")</script>
          </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="�ֳ�(3����)">�ֳ�(3����)</option>
          <option value="����(һ����)">����(һ����)</option>
          <option value="����(һ������)">����(һ������)</option>
          <option value="����(һ������)">����(һ������)</option>
          <option value="����(��������)">����(��������)</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������</td>
        <td bgcolor="#FFFFFF">
        <select name="com_fwq" id="com_fwq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="������">������</option>
          <option value="�ƽ���">�ƽ���</option>
          <option value="��ɱ��">��ɱ��</option>
          <option value="90-180">90-180</option>
          <option value="����180">����180</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_fwq,"<%=com_fwq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="���㻦">���㻦</option>
          <option value="������">������</option>
          <option value="�ӱ�����">�ӱ�����</option>
          <option value="ɽ��">ɽ��</option>
          <option value="���Ϻ���">���Ϻ���</option>
          <option value="��������">��������</option>
        </select>
        <script>selectOption(form1.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ӫ��Ʒ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>��ѡ��...</option>
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
        <script>selectOption(form1.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="20������">20������</option>
          <option value="20-100��">20-100��</option>
          <option value="100-500��">100-500��</option>
          <option value="500������">500������</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������ʽ</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ͨ�Ŀͻ�����</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">ע��ʱ��</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="����">����</option>
          <option value="ATM">ATM</option>
          <option value="�绰ת��">�绰ת��</option>
          <option value="��̨">��̨</option>
          <option value="֧����">֧����</option>
          <option value="�ֽ�">�ֽ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form1.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��½����</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������Ա</td>
        <td bgcolor="#FFFFFF">
        
        <input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/> 
        <a href="###" onClick="openuserlist(1)">ѡ��</a>
        <div id="selectuser1">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist1"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ע</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>��ע��<br>
            Ʒ��ͨ������3500�۸�Ŀͻ����������ƣ����ƣ���ʯ�����������Ʒ<br>
            С��Ʒ����������ͨ�ĸ��Ѳ�Ʒ����ҳ�����ۣ���ҳ��棩<br>
            չ���Ʒ����������ͨ��չ���Ʒ��������kt�壬չλ����Ʊ��</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  ��  ��  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<div id="mbbox2">
	<form id="form2" name="form2" method="post" action="dataedit_save.asp" onSubmit="return check(this)">
    <table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#f2f2f2">
      <tr>
        <td align="right" bgcolor="#f2f2f2">����</td>
        <td bgcolor="#FFFFFF">
        <select name="userid" id="userid" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="1306">ѩ��</option>
          <option value="1307">ս��</option>
          <option value="1302">��ӥ</option>
          <option value="1322">���籦</option>
        </select>
        <script>selectOption(form2.userid,"<%=userid%>")</script>
        <input type="hidden" name="com_id" value="<%=com_id%>">
        <input type="hidden" name="personid" id="personid" value="<%=personid%>">
        <input type="hidden" name="mbradio" id="mbr2" value="2">
        <input type="hidden" name="order_no" id="order_no" value="<%=order_no%>">
		<input type="hidden" name="apply_group" id="apply_group" value="<%=apply_group%>">
        <input type="hidden" name="dingid" id="dingid" value="<%=dingid%>" />
        <input name="customType" type="radio" id="customType" value="��ǩ" checked></td>
      </tr>
      
      <tr>
        <td width="130" align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
          <script language=javascript>createDatePicker("payTime",true,"<%=payTime%>",false,true,true,true)</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
     
        <td bgcolor="#FFFFFF"><select name='service_type' id='service_type' class="inputselect" ><option value=''>��ѡ��...</option>
          <option value="����ͨ">����ͨ</option>
          <option value="Ʒ��ͨ">Ʒ��ͨ</option>
          <option value="��ҳ">��ҳ</option>
          <option value="���">���</option>
          <option value="չ����">չ����</option>
          <option value="�ٶ��Ż�">�ٶ��Ż�</option>
          <option value="�������ͨ">�������ͨ</option>
          <option value="�ƶ�����ܼ�">�ƶ�����ܼ�</option>
          <option value="����ͨ������">����ͨ������</option>
          <option value="�������">�������</option>
          <option value="���̷���">���̷���</option>
          <option value="���Ż�Ա">���Ż�Ա</option>
          <option value="���籦һԪ">���籦һԪ</option>
          <option value="���籦��Ԫ">���籦��Ԫ</option>
          <option value="����">����</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      
       <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ��</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(������дʵ�ʵĵ��������������۶����׼ȷͳ��.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ��ֻ�</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">ǩ������</td>
        <td bgcolor="#FFFFFF"><input name="customType" type="radio" id="customType" value="����">
        ����<input name="customType" type="radio" id="customType" value="��ǩ">��ǩ
        <script>selectCheckBox(document.form2,"customType","<%=customType%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������Դ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="�¿ͻ�">�¿ͻ�</option>
          <option value="�����ͻ�">�����ͻ�</option>
          <option value="����ͻ�">����ͻ�</option>
          <option value="ת����">ת����</option>
          <option value="�����ռ�">�����ռ�</option>
          <option value="�Ͽͻ�">�Ͽͻ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="���㻦">���㻦</option>
          <option value="������">������</option>
          <option value="�ӱ�����">�ӱ�����</option>
          <option value="ɽ��">ɽ��</option>
          <option value="���Ϻ���">���Ϻ���</option>
          <option value="��������">��������</option>
        </select>
        <script>selectOption(form2.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ӫ��Ʒ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>��ѡ��...</option>
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
        <script>selectOption(form2.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="20������">20������</option>
          <option value="20-100��">20-100��</option>
          <option value="100-500��">100-500��</option>
          <option value="500������">500������</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ؼ���</td>
        <td bgcolor="#FFFFFF">
        <select name="com_gjd" id="com_gjd" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="����ɱ��">����ɱ��</option>
          <option value="ͬ�д̼�">ͬ�д̼�</option>
          <option value="��Ϣ�̼�">��Ϣ�̼�</option>
          <option value="��˾��̸">��˾��̸</option>
          <option value="��������">��������</option>
          <option value="չ���ֳ�">չ���ֳ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.com_gjd,"<%=com_gjd%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������ʽ</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
       <tr>
        <td align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="�ֳ�(3����)">�ֳ�(3����)</option>
          <option value="����(һ����)">����(һ����)</option>
          <option value="����(һ������)">����(һ������)</option>
          <option value="����(һ������)">����(һ������)</option>
          <option value="����(��������)">����(��������)</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ͨ�Ŀͻ�����</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">ע��ʱ��</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="����">����</option>
          <option value="ATM">ATM</option>
          <option value="�绰ת��">�绰ת��</option>
          <option value="��̨">��̨</option>
          <option value="֧����">֧����</option>
          <option value="�ֽ�">�ֽ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form2.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��½����</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������Ա</td>
        <td bgcolor="#FFFFFF"><input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/> 
        <a href="###" onClick="openuserlist(2)">ѡ��</a>
        <div id="selectuser2">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist2"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
     
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ע</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>��ע��<br>
            Ʒ��ͨ������3500�۸�Ŀͻ����������ƣ����ƣ���ʯ�����������Ʒ<br>
            С��Ʒ����������ͨ�ĸ��Ѳ�Ʒ����ҳ�����ۣ���ҳ��棩<br>
            չ���Ʒ����������ͨ��չ���Ʒ��������kt�壬չλ����Ʊ��</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  ��  ��  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<div id="mbbox3" >
<form id="form3" name="form3" method="post" action="dataedit_save.asp" onSubmit="return check(this)">
    <table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#f2f2f2">
      <tr>
        <td align="right" bgcolor="#f2f2f2">����</td>
        <td bgcolor="#FFFFFF">
        <select name="userid" id="userid" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="24">cs</option>
        </select>
        <script>selectOption(form3.userid,"<%=userid%>")</script>
        <input type="hidden" name="com_id" value="<%=com_id%>">
        <input type="hidden" name="personid" id="personid" value="<%=personid%>">
        <input type="hidden" name="mbradio" id="mbr3" value="3">
        <input type="hidden" name="dingid" id="dingid" value="<%=dingid%>" />
        <input type="hidden" name="order_no" id="order_no" value="<%=order_no%>">
		<input type="hidden" name="apply_group" id="apply_group" value="<%=apply_group%>">
        </td>
      </tr>
      
      <tr>
        <td width="130" align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
          <script language=javascript>createDatePicker("payTime",true,"<%=payTime%>",false,true,true,true)</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
        <td bgcolor="#FFFFFF">
        
        <select name='service_type' id='service_type' class="inputselect" >
        <option value=''>��ѡ��...</option>
          <option value="����ͨ">����ͨ����</option>
          <option value="Ʒ��ͨ">Ʒ��ͨ����</option>
          <option value="���">�������</option>
          <option value="����ͨ">����ͨ</option>
          <option value="չ���Ʒ">չ���Ʒ</option>
          <option value="�ٶ��Ż�">�ٶ��Ż�</option>
          <option value="�������">�������</option>
          <option value="�������">�������</option>
          <option value="�ƶ�����ܼ�">�ƶ�����ܼ�</option>
          <option value="����ͨ������">����ͨ������</option>
          <option value="�������">�������</option>
          <option value="���Ż�Ա">���Ż�Ա</option>
          <option value="���籦һԪ">���籦һԪ</option>
          <option value="���籦��Ԫ">���籦��Ԫ</option>
          <option value="����">����</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form3.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      
       <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ��</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(������дʵ�ʵĵ��������������۶����׼ȷͳ��.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ��ֻ�</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">ǩ������</td>
        <td bgcolor="#FFFFFF"><input name="customType" type="radio" id="customType" value="����">
        ����<input name="customType" type="radio" id="customType" value="��ǩ">��ǩ
        <script>selectCheckBox(document.form3,"customType","<%=customType%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������Դ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="�¿ͻ�">�¿ͻ�</option>
          <option value="1200��Ա">1200��Ա</option>
          <option value="2180��Ա">2180��Ա</option>
          <option value="3500��Ա">3500��Ա</option>
          <option value="1580��Ա">1580��Ա</option>
          <option value="3400�����Ա">3400�����Ա</option>
          <option value="5000�����Ա">5000�����Ա</option>
          <option value="ת����">ת����</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form3.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ͻ�����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="���㻦">���㻦</option>
          <option value="������">������</option>
          <option value="�ӱ�����">�ӱ�����</option>
          <option value="ɽ��">ɽ��</option>
          <option value="���Ϻ���">���Ϻ���</option>
          <option value="��������">��������</option>
        </select>
        <script>selectOption(form3.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ӫ��Ʒ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>��ѡ��...</option>
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
        <script>selectOption(form3.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��Ʒ����</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="20������">20������</option>
          <option value="20-100��">20-100��</option>
          <option value="100-500��">100-500��</option>
          <option value="500������">500������</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form3.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">�ؼ���</td>
        <td bgcolor="#FFFFFF">
        <select name="com_gjd" id="com_gjd" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="����ɱ��">����ɱ��</option>
          <option value="ͬ�д̼�">ͬ�д̼�</option>
          <option value="��Ϣ�̼�">��Ϣ�̼�</option>
          <option value="��˾��̸">��˾��̸</option>
          <option value="��������">��������</option>
          <option value="չ���ֳ�">չ���ֳ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form3.com_gjd,"<%=com_gjd%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������ʽ</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
       <tr>
        <td align="right" bgcolor="#f2f2f2">��������</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>��ѡ��...</option>
          <option value="���µ���">���µ���</option>
          <option value="δ����">δ����</option>
          <option value="����90����">����90����</option>
          <option value="����(һ����)">����(һ����)</option>
          <option value="����90-180">����90-180</option>
          <option value="����180����">����180����</option>
          <option value="365������">365������</option>
        </select>
        <script>selectOption(form3.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
         <td align="right" bgcolor="#f2f2f2">����ͨ����</td>
         <td bgcolor="#FFFFFF"><input type="text" name="com_servernum" class="text input" id="com_servernum" value="<%=com_servernum%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ͨ�Ŀͻ�����</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">ע��ʱ��</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">���ʽ</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value="">��ѡ��...</option>
          <option value="����">����</option>
          <option value="ATM">ATM</option>
          <option value="�绰ת��">�绰ת��</option>
          <option value="��̨">��̨</option>
          <option value="֧����">֧����</option>
          <option value="�ֽ�">�ֽ�</option>
          <option value="����">����</option>
        </select>
        <script>selectOption(form3.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">��½����</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">������Ա</td>
        <td bgcolor="#FFFFFF"><input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/>
        <a href="###" onClick="openuserlist(3)">ѡ��</a>
        <div id="selectuser3">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist3"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">��ע</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>��ע��<br>
            Ʒ��ͨ������3500�۸�Ŀͻ����������ƣ����ƣ���ʯ�����������Ʒ<br>
            С��Ʒ����������ͨ�ĸ��Ѳ�Ʒ����ҳ�����ۣ���ҳ��棩<br>
            չ���Ʒ����������ͨ��չ���Ʒ��������kt�壬չλ����Ʊ��</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  ��  ��  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<script>selectmb(<%=mbradio%>)</script>
</body>
</html>


