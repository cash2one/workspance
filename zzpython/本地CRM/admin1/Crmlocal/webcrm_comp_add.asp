<%@ Language=VBScript %>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ӹ�˾</title>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
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
-->
</style>
</head>

<body scroll=yes>

<table width="100%"  border="0" align="center" cellpadding="4" cellspacing="0" class=se id=ListTable>
<script language="javascript">
function chkfrm(frm)
{
if(frm.cname.value.length<=0)
{
alert("�����빫˾����!");
frm.cname.focus();
return false;
}
if(frm.cadd.value.length<=0)
{
alert("�������ַ!");
frm.cadd.focus();
return false;
}
/*if(frm.czip.value.length<=0)
{
alert("�������ʱ�!");
frm.czip.focus();
return false;
}*/

if(frm.province.value=="ʡ��")
{
alert("������ʡ!");
frm.province.focus();
return false;
}

if(frm.ctel.value.length<=0)
{
alert("������绰!");
frm.ctel.focus();
return false;
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
if(frm.ccontactp.value.length<=0)
{
alert("��������ϵ��!");
frm.ccontactp.focus();
return false;
}
/*if(frm.cdesi.value.length<=0)
{
alert("��������ϵ�˳ƺ�!");
frm.cdesi.focus();
return false;
}*/
}
function enteremail(frm)
{
	if (frm.cmobile.value=="" && frm.ctel.value=="")
	{
		alert ("��������ϵ��ʽ��")
	}else
	{
	    if (frm.cmobile.value!="")
		{
		frm.cemail.value=frm.cmobile.value+"@zz91.com"
		}else
		{
		frm.cemail.value=frm.ctel.value+"@zz91.com"
		}
	}
}
                </script>
                  <form name="form1" method="post" action="http://www.zz91.com/admin1/Crmlocal/webcrm_comp_save.asp" onSubmit="return chkfrm(this)">
                   
                    <tr bgcolor="#D9ECFF">
                      <td height="30" colspan="4" class="tbar"><span class="bold">������Ϣ</span></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td width="80" align="right">��˾���ƣ�                      </td>
                      <td width="223"><input name="cname" type="text" id="cname" class=text value="" size="30" maxlength="96">                      </td>
                      <td width="77" align="right" nowrap>����/������</td>
                      <td width="272"><SELECT id="province" name="province"></SELECT>
<SELECT id="city" name="city"></SELECT>
<SCRIPT type=text/javascript>
	//��ĵ���
	new Dron_City("province","city",'').init();
</SCRIPT></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">��ַ��</td>
                      <td colspan="3"><input name="cadd" type="text" class=text id="cadd" size="50">
                      �ʱࣺ
                      <input class=text name="czip" type="text" id="czip" size="20" maxlength="48"></td>
                    </tr>
					
					
					
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">�绰��</td>
                      <td><input name="ctel" type="text" id="ctel" class=text size="30" maxlength="96"></td>
                      <td align="right">�ֻ���</td>
                      <td><input name="cmobile" type="text" id="cmobile" class=text size="30" maxlength="96"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">���棺</td>
                      <td><input name="cfax" type="text" class=text id="cfax" size="30" maxlength="96"></td>
                      <td align="right">�������䣺</td>
                      <td nowrap><input name="cemail" class=text type="text" id="cemail" size="30" maxlength="48" <%=redo%>>
                        <input name="Submit" type="button" class="button" onClick="enteremail(this.form)" value="����">
                        <br>
                      ���û��email���� (�ֻ���绰@zz91.com)</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">��վ��</td>
                      <td colspan="3"><input name="cweb" class=text type="text" id="cweb" size="50" maxlength="255"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">��ϵ�ˣ�</td>
                      <td colspan="3"><input class=text name="ccontactp" type="text" id="ccontactp" size="20" maxlength="48">
                      �ƺ���
                      <input type="radio" name="cdesi" id="cdesi" <%=ccf1%> value="����">
                          ���� 
                          <input type="radio" name="cdesi" id="cdesi" <%=ccf2%> value="Ůʿ">
                          Ůʿ</td>
                    </tr>
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">��˾���ͣ�</td>
                      <td colspan="3" align="left"><%getcomkindlist "0"%></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" nowrap>��˾��飺</td>
                      <td colspan="3" align="left"><textarea name="cintroduce" cols="60" rows="10" id="cintroduce"></textarea></td>
                    </tr>
                   
                    <tr align="center" bgcolor="#FFFFFF">
                      <td align="right">��Ӫҵ��</td>
                      <td colspan="3" align="left"><textarea name="cproductslist_en" cols="50" rows="4" id="cproductslist_en"></textarea></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >�ͻ����ͣ�</td>
                      <td colspan="3">
                      
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <%
					  sqlt="select code,meno from cate_webCompType where code like '__'"
					  set rst=conn.execute(sqlt)
					  if not rst.eof or not rst.bof then
					  while not rst.eof 
					  %>
                        <tr>
                          <td width="100"><%=rst("meno")%></td>
                          <td>
                          <%
						  sqlt1="select code,meno from cate_webCompType where code like '"&rst("code")&"__'"
						  set rst1=conn.execute(sqlt1)
						  if not rst1.eof or not rst1.bof then
						  while not rst1.eof 
						  %>
                          <input name="compkind" type="checkbox" id="compkind" value="<%=rst1("code")%>"><%=rst1("meno")%>
                          <%
						  rst1.movenext
						  wend
						  end if
						  rst1.close
						  set rst1=nothing
						  %>
                          </td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  end if
					  rst.close
					  set rst=nothing
					  %>
                      </table>
                                            
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >�ͻ��ȼ���</td>
                      <td colspan="3">
                      <%
					  
					  sqlcate="select * from cate_kh_csd order by code desc"
					  set rscc=server.CreateObject("ADODB.recordset")
					  rscc.open sqlcate,conn,1,1
					  if not rscc.eof then 
					  do while not rscc.eof
					  if cint(rscc("meno"))=cint(com_rank) then
					  rankchecked="checked"
					  else
					  rankchecked=""
					  end if
					  %>                        
                        <input type="radio" <%=rankchecked%> name="com_rank" value="<%=rscc("meno")%>">
                        <%
					  for i=0 to cint(rscc("meno"))-1
					  %>
                        <img src="../../admin1/newimages/art1.gif" width="13" height="12" align="absmiddle">
                      <%next
					  rscc.movenext
					  loop
					  end if
					  rscc.close()
					  set rscc=nothing
					  %></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" >����������</td>
                      <td>
					  <input name="com_subname" class=text type="text" id="com_subname">
					
					  <input name="com_subname1" type="hidden" id="com_subname1">
					  
                        .zz91.com</td>
                      <td nowrap>&nbsp;</td>
                      <td>                      </td>
                    </tr>
                    <tr align="center" bgcolor="#FFFFFF">
                      <td colspan="4" ><input name="Submit" type="submit" class="button" value="����"></td>
                    </tr>
                  </form>
</table>
</body>
</html>
