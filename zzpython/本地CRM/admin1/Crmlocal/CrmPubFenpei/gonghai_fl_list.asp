<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<SCRIPT language=javascript src="../../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../js/province.js"></SCRIPT>
<SCRIPT language=javascript src="../js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="../js/list.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/jquery-1.8.0.min.js"></SCRIPT>
<link href="../css/list.css" rel="stylesheet" type="text/css">
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<script>
function submitfrm(frm)
{
	getprovincename();
}
$(document).ready(function(){
	$(".gengxin").click(function(){
		var self = $(this);
		var jsonvalue=$.parseJSON(self.attr("value"));
		var id=jsonvalue.id;
		var ranking=$("#ranking"+id.toString())
		var loading=function()
		{
			ranking.html("<img src='/images/loading_16x16.gif' border=0>");
		};
		var Response=function(data)
		{
			ranking.html(data);
		};
		ajaxurl="http://192.168.2.21/icdassign/?id="+id+"";
	    $.getScript(ajaxurl, function() {
			var result = _suggest_result_.compcount;
			ranking.html(result);
		});
		ranking.html("<img src='/images/loading_16x16.gif' border=0>");
	});
});
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
</head>

<body>
<form id="form1" name="form1" method="post" action="gonghai_fl_save.asp" onSubmit="return submitfrm(this)">
  <table border="0" cellspacing="1" cellpadding="5" bgcolor="#999999" class="searchtable">
    <tr>
    <td bgcolor="#FFFFFF">�����Ч��ϵʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("lastcontacttime1",true,"<%=lastcontacttime1%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("lastcontacttime2",true,"<%=lastcontacttime2%>",false,true,true,true)</script></td>
    <td bgcolor="#FFFFFF">�����½ʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("lastlogintime1",true,"<%=request("lastlogintime1")%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("lastlogintime2",true,"<%=request("lastlogintime2")%>",false,true,true,true)</script></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">ע��ʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("regtime1",true,"<%=request("regtime1")%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("regtime2",true,"<%=request("regtime2")%>",false,true,true,true)</script></td>
    <td bgcolor="#FFFFFF">����</td>
    <td bgcolor="#FFFFFF">
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
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">����ӹ���ʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("togonghaitime1",true,"<%=Regfromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("togonghaitime2",true,"<%=togonghaitime2%>",false,true,true,true)</script></td>
    <td bgcolor="#FFFFFF">�Ƿ�¼��ͻ�</td>
    <td bgcolor="#FFFFFF"><input type="radio" name="adminreg" id="adminreg" value="1" />
��
  <input type="radio" name="adminreg" id="adminreg" value="0" />
��</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">��ϵ����</td>
    <td bgcolor="#FFFFFF"><input name="telcount1" type="text" id="telcount1" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" />
      -
      <input name="telcount2" type="text" id="telcount2" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" /></td>
    <td bgcolor="#FFFFFF">��Ч��ϵ����</td>
    <td bgcolor="#FFFFFF"><input name="notelcount1" type="text" id="notelcount1" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" />
-
  <input name="notelcount2" type="text" id="notelcount2" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">�ͻ��ȼ�</td>
    <td bgcolor="#FFFFFF">
    <select name="comrank" id="comrank" style="background:#ebebeb; width:120px">
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
    </td>
    <td bgcolor="#FFFFFF">��ҵ</td>
    <td bgcolor="#FFFFFF"><input type="text" name="trade" id="trade" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">���ģ�����</td>
    <td bgcolor="#FFFFFF"><input name="star4" type="checkbox" id="star4" value="1" />
      ����
      <input name="star5" type="checkbox" id="star5" value="1" />
      ����</td>
    <td bgcolor="#FFFFFF">��½����</td>
    <td bgcolor="#FFFFFF"><input name="logincount1" type="text" id="logincount1" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" />
-
  <input name="logincount2" type="text" id="logincount2" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">��ʼ����ʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("assign_time",false,"<%=assign_time%>",false,false,false,false)</script></td>
    <td bgcolor="#FFFFFF">��ϵ�˴�</td>
    <td bgcolor="#FFFFFF"><input name="telpersoncount1" type="text" id="telpersoncount1" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" />
-
  <input name="telpersoncount2" type="text" id="telpersoncount2" size="10" onKeyUp="value=value.replace(/[^\d]/,'')" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">ÿ�η�������</td>
    <td bgcolor="#FFFFFF"><input name="assigncount" type="text" id="assigncount" size="10" onKeyUp="value=value.replace(/[^\d]/,'')"/></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="�ύ" /></td>
  </tr>
  
  </table>
</form>
<br />
<%
Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "icd_gonghai_assign"
		.sqlOrder= "order by id desc"
		.sqlWhere= ""
		.keyFld  = "id"    '����ȱ��
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/10)
%>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <tr>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�ͻ�����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�ѷ���</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">ÿ�η�������</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�����Ч��ϵʱ��</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�����½ʱ��</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">ע��ʱ��</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">����ӹ���ʱ��</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">��ϵ����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">��Ч��ϵ����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�ͻ��ȼ�</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">��ҵ</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">���ģ�����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">��½����</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�Ƿ�¼��ͻ�</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">&nbsp;</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">�Ƿ��Ѿ�����</td>
  </tr>
  <%
while not rs.eof 
assignflag=rs("assignflag")
if assignflag="1" then
	bgcolor="#cccccc"
	fp="<font color=#ff0000>�Ѿ�����</font>"
else
	bgcolor="#FFFFFF"
	fp="δ����"
end if
%>
  <tr>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><div id="ranking<%=rs("id")%>"><%=rs("compcount")%></div><div class="gengxin" value='{"id":"<%=rs("id")%>"}'><a href="javascript:void(0)">����</a></div></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>">
    <%sqlt="select count(0) from crm_assign_gonghai where pid="&rs("id")&""
	set rst=conn.execute(sqlt)
	response.Write(rst(0))
	%>
    </td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("assigncount")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("lastcontacttime1")%>��<%=rs("lastcontacttime2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("lastlogintime1")%>��<%=rs("lastlogintime2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("regtime1")%>��<%=rs("regtime2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("province")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("togonghaitime1")%>��<%=rs("togonghaitime2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("telcount1")%>-<%=rs("telcount2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("notelcount1")%>-<%=rs("notelcount2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("comrank")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("trade")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>">
	<%
	if rs("star5")="1" then
		response.Write("����")
	end if
	if rs("star4")="1" then
		response.Write("����")
	end if
	%>
    </td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("logincount1")%>-<%=rs("logincount2")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=rs("adminreg")%></td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><a href="http://192.168.2.21/icdassign/?id=<%=rs("id")%>&assign=1" target="_blank">�ֶ�����</td>
    <td nowrap="nowrap" bgcolor="<%=bgcolor%>"><%=fp%>  <a href="#">�رշ���</a></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	rst.close
	set rst=nothing
	conn.close
	set conn=nothing
  %>
</table>
</body>
</html>
