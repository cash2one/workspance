<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../js/province.js"></SCRIPT>
<SCRIPT language=javascript src="../js/compkind.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="searchtable">
<form name="form2" method="get" action="selectcomp.asp">
    <tr>
      <td height="30" align="center" bgcolor="#FFFFFF">
	    <table width="100%" border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td align="left" nowrap>��&nbsp; ַ:</td>
          <td align="left"><input name="com_add" type="text" class=text id="com_add" style="background-color:#fff;" size="15" value="<%=request("com_add")%>" /></td>
          <td align="left" nowrap>�����ϵʱ��: </td>
          <td align="left"><%
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
          <td align="left" nowrap>��&nbsp; ҵ:</td>
          <td align="left"><select name="clscb" id="clscb" style="width:130px">
            <option value="">--��ѡ��--</option>
            <option value="1">�Ͻ���</option>
            <option value="2">������</option>
            <option value="3">�Ͼ���̥�����</option>
            <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
            <option value="5">��ֽ</option>
            <option value="6">�ϵ��ӵ�����ϵ����豸</option>
            <option value="10">�ϲ������ľ��Ʒ</option>
            <option value="12">�Ͼ��豸��ɽ�ͨ����</option>
          </select></td>
          <td align="left">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ��:</td>
          <td align="left"><font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">��԰��</font>
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
                        </script></td>
          </tr>
        <tr>
          <td align="left">�ͻ�����:</td>
          <td align="left"><select name="companytype" id="companytype">
            <option value="">ѡ��ͻ�����</option>
            <option value="1">8000��ͻ�</option>
          </select></td>
          <td align="left">�����¼ʱ��:</td>
          <td align="left"><script language=javascript>createDatePicker("Lfromdate",true,"<%=Lfromdate%>",false,true,true,true)</script>&nbsp;��&nbsp;<script language=javascript>createDatePicker("Ltodate",true,"<%=Ltodate%>",false,true,true,true)</script></td>
          </tr>
        <tr>
          <td align="left">&nbsp;</td>
          <td align="left" nowrap><select name="showitems" id="showitems">
            <option>��ѡ��</option>
            <option value="offer">�й�����Ϣ</option>
            <option value="receive">���յ�ѯ��</option>
            <option value="send">�з���ѯ��</option>
          </select></td>
          <td align="left">��Ӫҵ��:</td>
          <td align="left" nowrap><input name="zyyw" type="text" class="text" id="zyyw" size="10" /></td>
          </tr>
      </table></td>
    </tr>
    
    
    <tr>
      <td height="45" align="center" bgcolor="#f2f2f2"><script>selectOption("gonghaifw","<%=request("gonghaifw")%>")</script>
        <script>selectOption("showitems","<%=request("showitems")%>")</script>
        <!--<select name="selectgg" id="selectgg" onChange="document.getElementById('zyyw').value=this.value">
          <option value="">���ɿ���</option>
	  	  <option value="����">����</option>
          <option value="���">���</option>
          <option value="�����">�����</option>
          <option value="��е">��е</option>
  	    </select>-->
    <%
          pdtly=request("pdt_ly")
          %>
          <strong style="color:#ff0000">��Դ</strong>
          <%=cateMeno(conn,"cate_product_ly","pdt_ly","","")%>
          <input type="submit" name="Submit3" value="  �� ��  " class=button>
      </td>
    </tr>
  </form>
</table>
<%
sql=""
if trim(request("com_add"))<>"" then
	sql=sql&" and com_add like '%"&trim(request("com_add"))&"%'"
	sear=sear&"&com_add="&request("com_add")
end if
sqlmain="select count(0) from v_salescomp where 1=1"&sql
set rs=conn.execute(sqlmain)

%>
<br />
<table width="100%" border="0" cellspacing="0" cellpadding="5" class="searchtable">
  <tr>
    <td>���� <b><font color="#FF0000"><%=rs(0)%></font></b> ���ͻ�</td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
