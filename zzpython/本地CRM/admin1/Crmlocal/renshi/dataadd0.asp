<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
com_email=request.Form("com_email")

if com_email<>"" then
	userid=request.Form("userid")
	personid=request.Form("personid")
	userName=request.Form("realname")
	if userid="1315" then 
		mbflag=1
	else
		mbflag=2
	end if
	sql="select com_id,com_name from comp_info where com_email='"&com_email&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		com_id=rs(0)
		com_name=rs(1)
	end if
	rs.close
	set rs=nothing
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=bg2312">
<title>付费会员服务到款确认单</title>

<SCRIPT language=javascript src="http://img0.zz91.com/front/admin/DatePicker.js"></SCRIPT>
<script>
function UTF8UrlEncode(input){    
   
        var output = "";    
   
        var currentChar = '';    
   
        for(var counter = 0; counter < input.length; counter++){    
   
            currentChar = input.charCodeAt(counter);    
   
            if((0 <= currentChar) && (currentChar <= 127))    
   
                output = output + UTF8UrlEncodeChar(currentChar);    
   
            else   
   
                output = output + encodeURIComponent(input.charAt(counter));    
   
        }    
   
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
	function UTF8UrlEncodeChar(input){    
   
        if(input <= 0x7F) return "%" + input.toString(16);    
   
        var leadByte = 0xFF80;    
   
        var hexString = "";    
   
        var leadByteSpace = 5;    
   
        while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
   
            hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
   
            leadByte = (leadByte >> 1);    
   
            leadByteSpace--;    
   
            input = input >> 6;    
   
        }    
   
        return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
   
	} 
</script>
<script>
function substm(frm)
{
	if (frm.personid.value=="")
	{
		alert("请选择销售人员！");
		return false;
	}
	if (frm.com_email.value=="")
	{
		alert("请输入邮箱！");
		frm.com_email.focus();
		return false;
	}
}
</script>

<script>
<%if com_email<>"" and com_id<>"" then%>
window.location="http://adminasto.zz91.com/openConfirm1/?com_id=<%=com_id%>&mbflag=<%=mbflag%>&userid=<%=userid%>&username="+UTF8UrlEncode("<%=username%>")+"&personid=<%=personid%>&com_email=<%=com_email%>&com_name="+UTF8UrlEncode("<%=com_name%>")
<%end if%>
</script>
<link href="http://img0.zz91.com/front/admin/datepicker.css" rel="stylesheet" type="text/css">
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

-->
</style>

</head>

<body>
<form id="form1" name="form1" method="post" action="" onSubmit="return substm(this)">
<br />
<br />
<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>销售人员</td>
    <td><input type="text" name="realname" id="realname" value="" class="Input" readonly/>
      <input name="personid" type="hidden" id="personid" value="" />
      <input type="hidden" name="userid" id="userid" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>客户邮箱</td>
    <td>
      <input name="com_email" type="text" id="com_email" size="30" />
    </td>
    <td><input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
</table></form>

<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 500px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="selectuser.asp"></iframe></td>
  </tr>
</table>

</body>
</html>


