<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<script language=Javascript>
<!--
function showHideMenu1() {
	if (frmMenu1.style.display=="") {
		frmMenu1.style.display="none"
		//switchPoint.innerText=4
		}
	else {
		frmMenu1.style.display=""
		//switchPoint.innerText=3
		}
}


//-->
</script>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="DatePicker.js"></SCRIPT>
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.crm_tishi {
	background-color: #F6F6F6;
	padding: 2px;
	border: 1px solid #FF9900;
	color: #CC0000;
}
-->
</style>
</head>

<body>
<br />
<br />
<br />
<table width="600"  border="0" align="center" cellpadding="0" cellspacing="0" class="searchtable">
  <form name="form2" method="post" action="crm_allcomp_list.asp">
  
  <tr>
      <td height="30" align="center" bgcolor="#f2f2f2"><strong>�ͻ�����</strong></td>
  </tr>
 
    <tr>
      <td height="30" align="center" bgcolor="#FFFFFF">
	    <table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#E6E6E6">
        <tr>
          <td nowrap>��˾��:</td>
          <td bgcolor="#FFFFFF"><input name="com_name" type="text" class=text id="com_name" style="background-color:#fff;" size="15"><input name="action" type="hidden" id="action" value="<%=doaction%>">
        <input name="dotype" type="hidden" id="dotype" value="<%=dotype%>">
        <input name="cach" type="hidden" id="cach" value="<%response.Write(docach)%>">
        <input name="zhuce" type="hidden" id="zhuce" value=<%response.write(request("zhuce"))%>>
        <input name="searchflag" type="hidden" id="searchflag" value="1" /></td>
          <td nowrap>��ϵ��:</td>
          <td bgcolor="#FFFFFF"><input name="com_contactperson" type="text" class=text id="com_contactperson" style="background-color:#fff;" size="15" value="<%=request("com_contactperson")%>"></td>
          </tr>
        <tr>
          <td>��&nbsp; ��: </td>
          <td bgcolor="#FFFFFF"><input name="com_tel" type="text" class=text id="com_tel" style="background-color:#fff;" size="15"></td>
          <td>��&nbsp; ��:</td>
          <td bgcolor="#FFFFFF"><input name="com_mobile" type="text" class=text id="com_mobile" style="background-color:#fff;" size="15"></td>
          </tr>
        <tr>
          <td>��&nbsp; ַ:</td>
          <td bgcolor="#FFFFFF"><input name="com_add" type="text" class=text id="com_add" style="background-color:#fff;" size="15"></td>
          <td>Email:</td>
          <td bgcolor="#FFFFFF"><input name="com_email" type="text" class=text id="com_email" style="background-color:#fff;" size="15" /></td>
          </tr>
        <tr>
          <td>��&nbsp; ��:</td>
          <td colspan="3" nowrap bgcolor="#FFFFFF"><%
		  select case request("comporder")
		  case "1"
		  sl1="selected"
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "2"
		  sl1=""
		  sl2="selected"
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "3"
		  sl1=""
		  sl2=""
		  sl3="selected"
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  case "4"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4="selected"
		  sl5=""
		  sl6=""
		  sl7=""
		  case "5"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5="selected"
		  sl6=""
		  sl7=""
		  case "6"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6="selected"
		  sl7=""
		  case "7"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7="selected"
		  case "8"
		  sl1=""
		  sl2=""
		  sl3=""
		  sl4=""
		  sl5=""
		  sl6=""
		  sl7=""
		  sl8="selected"
		  end select
		  %>
            <select name="comporder" id="comporder">
              <option>Ĭ��</option>
              <option value="1" <%response.Write(sl1)%>>�ͻ��ȼ�</option>
              <option value="2" <%response.Write(sl2)%>>��¼����</option>
              <option value="3" <%response.Write(sl3)%>>�´���ϵʱ��</option>
              <option value="4" <%response.Write(sl4)%>>����ʱ��</option>
              <option value="5" <%response.Write(sl5)%>>�����ϵʱ��</option>
              <option value="6" <%response.Write(sl6)%>>ע��ʱ��</option>
              <option value="7" <%response.Write(sl7)%>>�����½ʱ��</option>
              <option value="8" <%response.Write(sl8)%>>����ʱ��</option>
            </select>
            <select name="ascdesc" id="ascdesc">
              <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>����</option>
              <option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>����</option>
            </select></td>
          </tr>
      </table></td>
    </tr>
    
    
    <tr>
      <td height="30" align="center" bgcolor="#f2f2f2"><input name="ss" type="hidden" id="ss" value="1">
      <%if dotype="allall" or dotype="all" or dotype="3daynodo" or (dotype="my" and session("userid")="10") or (dotype="today" and session("userid")="10") or (dotype="contact" and session("userid")="10") or (dotype="nocontact" and session("userid")="10") or (dotype="myzst" and session("userid")="10") or (dotype="xm") then%>
            <select name="doperson" class="button" id="doperson" >
              <option value="" >��ѡ��--</option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and adminuserid<>'' and userid=13"
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>><%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
					
					 %>
            </select>			
            <%end if%>
			
		    <input type="submit" name="Submit3" value="  �� ��  " class=button>	  </td>
    </tr>
  </form>
</table>
</body>
</html>
