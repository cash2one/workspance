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
<title>付费会员服务到款确认单</title>
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
			//说明有字符不是数字 
			return 0; 
		} 
	} 
	//说明是数字 
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
		alert("请选择归属部门！");
		return false;
	}
	
	if(obj.userid.value=="")
	{
		alert("请选择部门！");
		obj.userid.focus();
		return false;
	}
	
	if(obj.service_type.value=="")
	{
		alert("请选择产品类型！");
		obj.service_type.focus();
		return false;
	}
	
	if(obj.payTime.value==""){
		alert("请填写到款时间!");
		obj.payTime.focus();
		return false;
	}

	if (chknum(obj.payMoney.value)==0)
	{
		alert("你输入到款金额必须是数字")
		obj.payMoney.focus()
		return false;
	}
	if(obj.payMoney.value==""){
		alert("请填写到款金额!");
		obj.payMoney.focus();
		return false;
	}
	if(obj.com_contactperson.value==""){
		alert("请填写客户姓名!");
		obj.com_contactperson.focus();
		return false;
	}
	if(obj.com_ly1.value==""){
		alert("请选择客户来源!");
		obj.com_ly1.focus();
		return false;
	}
	
	if (obj.com_ly2)
	{
		if(obj.com_ly2.value==""){
			alert("请选择客户来源!");
			obj.com_ly2.focus();
			return false;
		}
	}
	if(pp==1){
		if (mbflag.value==1)
		{
			alert("请选择客户类型!");
		}
		if (mbflag.value==3)
		{
			alert("请选择签单类型!");
		}
		return false;
	}
	if (obj.com_zq)
	{
		if(obj.com_zq.value==""){
			alert("请选择周期!");
			obj.com_zq.focus();
			return false;
		}
	}
	
	if (obj.com_fwq)
	{
		if(obj.com_fwq.value==""){
			alert("请选择服务期!");
			obj.com_fwq.focus();
			return false;
		}
	}
	
	if (obj.com_khdq)
	{
		if(obj.com_khdq.value==""){
			alert("请选择客户地区!");
			obj.com_khdq.focus();
			return false;
		}
	}
	
	if (obj.com_pro)
	{
		if(obj.com_pro.value==""){
			alert("请选择经营产品!");
			obj.com_pro.focus();
			return false;
		}
	}
	
	if (obj.com_cpjb)
	{
		if(obj.com_cpjb.value==""){
			alert("请选择产品量级!");
			obj.com_cpjb.focus();
			return false;
		}
	}
	
	if (obj.com_cxfs)
	{
		if(obj.com_cxfs.value==""){
			alert("请输入促销形式!");
			obj.com_cxfs.focus();
			return false;
		}
	}
	
	if (obj.com_hkfs)
	{
		if(obj.com_hkfs.value==""){
			alert("请选择付款方式!");
			obj.com_hkfs.focus();
			return false;
		}
	}
	
	if (obj.com_gjd)
	{
		if(obj.com_gjd.value==""){
			alert("请选择关键点!");
			obj.com_gjd.focus();
			return false;
		}
	}
	if (obj.com_servernum)
	{
		if (chknum(obj.com_servernum.value)==0)
		{
			alert("你输入再生通年限必须是数字")
			obj.com_servernum.focus()
			return false;
		}
		if(obj.com_servernum.value==""){
			alert("请填写再生通年限!");
			obj.com_servernum.focus();
			return false;
		}
	}
	
	if(obj.saler.value==""){
		alert("请填写销售人员!");
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
       <td width="130" align="right" bgcolor="#f2f2f2">归属部门</td>
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
        <td width="130" align="right" bgcolor="#f2f2f2">到单日期</td>
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
        <td align="right" bgcolor="#f2f2f2">产品分类</td>
        <td bgcolor="#FFFFFF">
        
        <select name='service_type' id='service_type' class="inputselect" >
          <option value=''>请选择...</option>
          <option value="再生通">再生通</option>
          <option value="品牌通">品牌通</option>
          <option value="展会产品">展会产品</option>
          <option value="广告">广告</option>
          <option value="线下纸媒">线下纸媒</option>
          <option value="短信报价">短信报价</option>
          <option value="百度优化">百度优化</option>
          <option value="国际站">国际站</option>
          <option value="移动生意管家">移动生意管家</option>
          <option value="再生通发起人">再生通发起人</option>
          <option value="终身服务">终身服务</option>
          <option value="诚信会员">诚信会员</option>
          <option value="来电宝一元">来电宝一元</option>
          <option value="来电宝五元">来电宝五元</option>
          <option value="定金">定金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      <!--<tr>
         <td align="right" bgcolor="#f2f2f2">产品分类2</td>
         <td bgcolor="#FFFFFF">
         <select name="service_type1" id="service_type1" class="inputselect">
         	<option value="">请选择...</option>
           <option value="移动生意管家">移动生意管家</option>
           <option value="再生通发起人">再生通发起人</option>
           <option value="终身服务">终身服务</option>
           <option value="无">无</option>
         </select>
         <script>selectOption(form1.service_type1,"<%=service_type1%>")</script>
         </td>
       </tr>-->
       <tr>
        <td align="right" bgcolor="#f2f2f2">到帐金额</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(必须填写实际的到款金额，否则你的销售额将不能准确统计.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">客户姓名</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户手机</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">新签客户来源1</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>请选择...</option>
          <option value="新客户">新客户</option>
          <option value="公海客户">公海客户</option>
          <option value="呼入客户">呼入客户</option>
          <option value="转介绍">转介绍</option>
          <option value="个人收集">个人收集</option>
          <option value="老客户">老客户</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">增值客户来源2</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly2" id="com_ly2" class="inputselect">
          <option value=''>请选择...</option>
          <option value="1200会员">1200会员</option>
          <option value="2180会员">2180会员</option>
          <option value="3500会员">3500会员</option>
          <option value="非会员消费">非会员消费</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_ly2,"<%=com_ly2%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">增值类型</td>
        <td bgcolor="#FFFFFF"><input type="radio" name="customType" value="续费增值">
          续费增值
          <input type="radio" name="customType" value="新增值">
          新增值
          <input type="radio" name="customType" value="新签">
          新签
          <input type="radio" name="customType" value="其他">
          其他     
          <script>selectCheckBox(document.form1,"customType","<%=customType%>")</script>
          </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">到单周期</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="现场(3天内)">现场(3天内)</option>
          <option value="短期(一周内)">短期(一周内)</option>
          <option value="短期(一个月内)">短期(一个月内)</option>
          <option value="长期(一个月外)">长期(一个月外)</option>
          <option value="长期(三个月外)">长期(三个月外)</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">服务期</td>
        <td bgcolor="#FFFFFF">
        <select name="com_fwq" id="com_fwq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="保鲜期">保鲜期</option>
          <option value="黄金期">黄金期</option>
          <option value="必杀期">必杀期</option>
          <option value="90-180">90-180</option>
          <option value="过期180">过期180</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_fwq,"<%=com_fwq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户地区</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="江浙沪">江浙沪</option>
          <option value="珠三角">珠三角</option>
          <option value="河北河南">河北河南</option>
          <option value="山东">山东</option>
          <option value="湖南湖北">湖南湖北</option>
          <option value="其他地区">其他地区</option>
        </select>
        <script>selectOption(form1.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">经营产品</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>请选择...</option>
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
        <script>selectOption(form1.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">产品量级</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>请选择...</option>
          <option value="20吨以下">20吨以下</option>
          <option value="20-100吨">20-100吨</option>
          <option value="100-500吨">100-500吨</option>
          <option value="500吨以上">500吨以上</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">促销形式</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">开通的客户邮箱</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">注册时间</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">付款方式</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value=''>请选择...</option>
          <option value="网银">网银</option>
          <option value="ATM">ATM</option>
          <option value="电话转账">电话转账</option>
          <option value="柜台">柜台</option>
          <option value="支付宝">支付宝</option>
          <option value="现金">现金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form1.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">登陆次数</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">销售人员</td>
        <td bgcolor="#FFFFFF">
        
        <input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/> 
        <a href="###" onClick="openuserlist(1)">选择</a>
        <div id="selectuser1">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist1"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">备注</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>备注：<br>
            品牌通：超过3500价格的客户，包括银牌，金牌，钻石和其他打包产品<br>
            小产品：不含再生通的付费产品（黄页，报价，黄页广告）<br>
            展会产品：不含再生通的展会产品（海报，kt板，展位，门票）</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  提  交  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<div id="mbbox2">
	<form id="form2" name="form2" method="post" action="dataedit_save.asp" onSubmit="return check(this)">
    <table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#f2f2f2">
      <tr>
        <td align="right" bgcolor="#f2f2f2">部门</td>
        <td bgcolor="#FFFFFF">
        <select name="userid" id="userid" class="inputselect">
          <option value=''>请选择...</option>
          <option value="1306">雪豹</option>
          <option value="1307">战龙</option>
          <option value="1302">赤鹰</option>
          <option value="1322">来电宝</option>
        </select>
        <script>selectOption(form2.userid,"<%=userid%>")</script>
        <input type="hidden" name="com_id" value="<%=com_id%>">
        <input type="hidden" name="personid" id="personid" value="<%=personid%>">
        <input type="hidden" name="mbradio" id="mbr2" value="2">
        <input type="hidden" name="order_no" id="order_no" value="<%=order_no%>">
		<input type="hidden" name="apply_group" id="apply_group" value="<%=apply_group%>">
        <input type="hidden" name="dingid" id="dingid" value="<%=dingid%>" />
        <input name="customType" type="radio" id="customType" value="新签" checked></td>
      </tr>
      
      <tr>
        <td width="130" align="right" bgcolor="#f2f2f2">到单日期</td>
        <td bgcolor="#FFFFFF">
          <script language=javascript>createDatePicker("payTime",true,"<%=payTime%>",false,true,true,true)</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">产品分类</td>
     
        <td bgcolor="#FFFFFF"><select name='service_type' id='service_type' class="inputselect" ><option value=''>请选择...</option>
          <option value="再生通">再生通</option>
          <option value="品牌通">品牌通</option>
          <option value="黄页">黄页</option>
          <option value="广告">广告</option>
          <option value="展会广告">展会广告</option>
          <option value="百度优化">百度优化</option>
          <option value="简版再生通">简版再生通</option>
          <option value="移动生意管家">移动生意管家</option>
          <option value="再生通发起人">再生通发起人</option>
          <option value="终身服务">终身服务</option>
          <option value="商铺服务">商铺服务</option>
          <option value="诚信会员">诚信会员</option>
          <option value="来电宝一元">来电宝一元</option>
          <option value="来电宝五元">来电宝五元</option>
          <option value="定金">定金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      
       <tr>
        <td align="right" bgcolor="#f2f2f2">到帐金额</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(必须填写实际的到款金额，否则你的销售额将不能准确统计.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">客户姓名</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户手机</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">签单类型</td>
        <td bgcolor="#FFFFFF"><input name="customType" type="radio" id="customType" value="续费">
        续费<input name="customType" type="radio" id="customType" value="新签">新签
        <script>selectCheckBox(document.form2,"customType","<%=customType%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">到单来源</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>请选择...</option>
          <option value="新客户">新客户</option>
          <option value="公海客户">公海客户</option>
          <option value="呼入客户">呼入客户</option>
          <option value="转介绍">转介绍</option>
          <option value="个人收集">个人收集</option>
          <option value="老客户">老客户</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户地区</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="江浙沪">江浙沪</option>
          <option value="珠三角">珠三角</option>
          <option value="河北河南">河北河南</option>
          <option value="山东">山东</option>
          <option value="湖南湖北">湖南湖北</option>
          <option value="其他地区">其他地区</option>
        </select>
        <script>selectOption(form2.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">经营产品</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>请选择...</option>
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
        <script>selectOption(form2.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">产品量级</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>请选择...</option>
          <option value="20吨以下">20吨以下</option>
          <option value="20-100吨">20-100吨</option>
          <option value="100-500吨">100-500吨</option>
          <option value="500吨以上">500吨以上</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">关键点</td>
        <td bgcolor="#FFFFFF">
        <select name="com_gjd" id="com_gjd" class="inputselect">
          <option value=''>请选择...</option>
          <option value="促销杀进">促销杀进</option>
          <option value="同行刺激">同行刺激</option>
          <option value="信息刺激">信息刺激</option>
          <option value="公司面谈">公司面谈</option>
          <option value="政府补贴">政府补贴</option>
          <option value="展会现场">展会现场</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.com_gjd,"<%=com_gjd%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">促销形式</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
       <tr>
        <td align="right" bgcolor="#f2f2f2">到单周期</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="现场(3天内)">现场(3天内)</option>
          <option value="短期(一周内)">短期(一周内)</option>
          <option value="短期(一个月内)">短期(一个月内)</option>
          <option value="长期(一个月外)">长期(一个月外)</option>
          <option value="长期(三个月外)">长期(三个月外)</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">开通的客户邮箱</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">注册时间</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">付款方式</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value=''>请选择...</option>
          <option value="网银">网银</option>
          <option value="ATM">ATM</option>
          <option value="电话转账">电话转账</option>
          <option value="柜台">柜台</option>
          <option value="支付宝">支付宝</option>
          <option value="现金">现金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form2.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">登陆次数</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">销售人员</td>
        <td bgcolor="#FFFFFF"><input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/> 
        <a href="###" onClick="openuserlist(2)">选择</a>
        <div id="selectuser2">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist2"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
     
      <tr>
        <td align="right" bgcolor="#f2f2f2">备注</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>备注：<br>
            品牌通：超过3500价格的客户，包括银牌，金牌，钻石和其他打包产品<br>
            小产品：不含再生通的付费产品（黄页，报价，黄页广告）<br>
            展会产品：不含再生通的展会产品（海报，kt板，展位，门票）</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  提  交  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<div id="mbbox3" >
<form id="form3" name="form3" method="post" action="dataedit_save.asp" onSubmit="return check(this)">
    <table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#f2f2f2">
      <tr>
        <td align="right" bgcolor="#f2f2f2">部门</td>
        <td bgcolor="#FFFFFF">
        <select name="userid" id="userid" class="inputselect">
          <option value=''>请选择...</option>
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
        <td width="130" align="right" bgcolor="#f2f2f2">到单日期</td>
        <td bgcolor="#FFFFFF">
          <script language=javascript>createDatePicker("payTime",true,"<%=payTime%>",false,true,true,true)</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">产品分类</td>
        <td bgcolor="#FFFFFF">
        
        <select name='service_type' id='service_type' class="inputselect" >
        <option value=''>请选择...</option>
          <option value="再生通">再生通续费</option>
          <option value="品牌通">品牌通续费</option>
          <option value="广告">广告续费</option>
          <option value="再生通">再生通</option>
          <option value="展会产品">展会产品</option>
          <option value="百度优化">百度优化</option>
          <option value="广告续费">广告续费</option>
          <option value="简版续费">简版续费</option>
          <option value="移动生意管家">移动生意管家</option>
          <option value="再生通发起人">再生通发起人</option>
          <option value="终身服务">终身服务</option>
          <option value="诚信会员">诚信会员</option>
          <option value="来电宝一元">来电宝一元</option>
          <option value="来电宝五元">来电宝五元</option>
          <option value="定金">定金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form3.service_type,"<%=service_type%>")</script>
        </td>
      </tr>
      
       <tr>
        <td align="right" bgcolor="#f2f2f2">到帐金额</td>
        <td bgcolor="#FFFFFF"><input name="payMoney" type="text" class="text input" id="payMoney" value="<%=payMoney%>"/>
          <font color="#FF0000">(必须填写实际的到款金额，否则你的销售额将不能准确统计.)</font></td>
      </tr>
      
      <tr>
         <td align="right" bgcolor="#f2f2f2">客户姓名</td>
         <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_contactperson" id="com_contactperson" value="<%=com_contactperson%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户手机</td>
        <td bgcolor="#FFFFFF"><input type="text" class="input" name="com_mobile" id="com_mobile" value="<%=com_mobile%>"></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">签单类型</td>
        <td bgcolor="#FFFFFF"><input name="customType" type="radio" id="customType" value="续费">
        续费<input name="customType" type="radio" id="customType" value="新签">新签
        <script>selectCheckBox(document.form3,"customType","<%=customType%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">到单来源</td>
        <td bgcolor="#FFFFFF">
        <select name="com_ly1" id="com_ly1" class="inputselect">
          <option value=''>请选择...</option>
          <option value="新客户">新客户</option>
          <option value="1200会员">1200会员</option>
          <option value="2180会员">2180会员</option>
          <option value="3500会员">3500会员</option>
          <option value="1580会员">1580会员</option>
          <option value="3400两年会员">3400两年会员</option>
          <option value="5000两年会员">5000两年会员</option>
          <option value="转介绍">转介绍</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form3.com_ly1,"<%=com_ly1%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">客户地区</td>
        <td bgcolor="#FFFFFF">
        <select name="com_khdq" id="com_khdq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="江浙沪">江浙沪</option>
          <option value="珠三角">珠三角</option>
          <option value="河北河南">河北河南</option>
          <option value="山东">山东</option>
          <option value="湖南湖北">湖南湖北</option>
          <option value="其他地区">其他地区</option>
        </select>
        <script>selectOption(form3.com_khdq,"<%=com_khdq%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">经营产品</td>
        <td bgcolor="#FFFFFF">
        <select name="com_pro" id="com_pro" class="inputselect">
          <option value=''>请选择...</option>
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
        <script>selectOption(form3.com_pro,"<%=com_pro%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">产品量级</td>
        <td bgcolor="#FFFFFF">
        <select name="com_cpjb" id="com_cpjb" class="inputselect">
          <option value=''>请选择...</option>
          <option value="20吨以下">20吨以下</option>
          <option value="20-100吨">20-100吨</option>
          <option value="100-500吨">100-500吨</option>
          <option value="500吨以上">500吨以上</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form3.com_cpjb,"<%=com_cpjb%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">关键点</td>
        <td bgcolor="#FFFFFF">
        <select name="com_gjd" id="com_gjd" class="inputselect">
          <option value=''>请选择...</option>
          <option value="促销杀进">促销杀进</option>
          <option value="同行刺激">同行刺激</option>
          <option value="信息刺激">信息刺激</option>
          <option value="公司面谈">公司面谈</option>
          <option value="政府补贴">政府补贴</option>
          <option value="展会现场">展会现场</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form3.com_gjd,"<%=com_gjd%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">促销形式</td>
        <td bgcolor="#FFFFFF"><input type="text" name="com_cxfs" class="text input" id="com_cxfs" value="<%=com_cxfs%>"></td>
      </tr>
       <tr>
        <td align="right" bgcolor="#f2f2f2">续费周期</td>
        <td bgcolor="#FFFFFF">
        <select name="com_zq" id="com_zq" class="inputselect">
          <option value=''>请选择...</option>
          <option value="当月到期">当月到期</option>
          <option value="未过期">未过期</option>
          <option value="过期90天内">过期90天内</option>
          <option value="短期(一周内)">短期(一周内)</option>
          <option value="过期90-180">过期90-180</option>
          <option value="过期180以上">过期180以上</option>
          <option value="365天以上">365天以上</option>
        </select>
        <script>selectOption(form3.com_zq,"<%=com_zq%>")</script>
        </td>
      </tr>
      <tr>
         <td align="right" bgcolor="#f2f2f2">再生通年限</td>
         <td bgcolor="#FFFFFF"><input type="text" name="com_servernum" class="text input" id="com_servernum" value="<%=com_servernum%>"></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">开通的客户邮箱</td>
        <td bgcolor="#FFFFFF"><input name="newemail" type="text" class="text input" id="newemail" value="<%=newemail%>" size="50" readonly/></td>
      </tr>
     
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">注册时间</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_regtime" id="com_regtime" value="<%=com_regtime%>"><%=com_regtime%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">付款方式</td>
        <td bgcolor="#FFFFFF">
        <select name="com_hkfs" id="com_hkfs" class="inputselect">
          <option value="">请选择...</option>
          <option value="网银">网银</option>
          <option value="ATM">ATM</option>
          <option value="电话转账">电话转账</option>
          <option value="柜台">柜台</option>
          <option value="支付宝">支付宝</option>
          <option value="现金">现金</option>
          <option value="其他">其他</option>
        </select>
        <script>selectOption(form3.com_hkfs,"<%=com_hkfs%>")</script>
        </td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">登陆次数</td>
        <td bgcolor="#FFFFFF"><input type="hidden" name="com_logincount" id="com_logincount" value="<%=com_logincount%>"><%=com_logincount%></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">销售人员</td>
        <td bgcolor="#FFFFFF"><input name="saler" type="text" class="text" id="saler" value="<%=realname%>" readonly/>
        <a href="###" onClick="openuserlist(3)">选择</a>
        <div id="selectuser3">
        <iframe src="about:blank" width="0" height="0" scrolling="yes" name="userlist3"></iframe>
        </div>
        <%=realname1%>
        </td>
      </tr>
      
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">备注</td>
        <td bgcolor="#FFFFFF"><textarea name="remark" cols="50" class="input" rows="5" id="remark"><%=remark%></textarea></td>
      </tr>
      
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><DIV style="color:#F00">
          <p>备注：<br>
            品牌通：超过3500价格的客户，包括银牌，金牌，钻石和其他打包产品<br>
            小产品：不含再生通的付费产品（黄页，报价，黄页广告）<br>
            展会产品：不含再生通的展会产品（海报，kt板，展位，门票）</p>
</DIV></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#f2f2f2">&nbsp;</td>
        <td bgcolor="#FFFFFF"><input type="submit" class="button" name="Submit" value="  提  交  " />
        <iframe src="about:blank" width="0" height="0" scrolling="no" name="opencomp"></iframe></td>
      </tr>
    </table>
  </form>
</div>
<script>selectmb(<%=mbradio%>)</script>
</body>
</html>


