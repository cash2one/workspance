<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<%
if request.Form("khtel")<>"" then
	sql="insert into  temp_recordtel(com_id,personid,tel) values("&request.Form("com_id")&","&session("personid")&",'"&request.Form("khtel")&"')"
	response.Write(sql)
	conn.execute(sql)
	response.Write("<script>parent.document.getElementById('alertmsg').style.display='none';</script>")
	response.Write("<script>parent.document.getElementById('page_cover').style.display='none';parent.document.getElementById('recordflag').value='1';</script>")
end if
com_id=request.QueryString("com_id")
khtel=request.QueryString("comtel")
sqlm="select usertel from users where id="&session("personid")&""
set rsm=conn.execute(sqlm)
if not rsm.eof or not rsm.bof then
	mytel=rsm(0)
end if
rsm.close
set rsm=nothing
'-------------------------
sqlt="select top 1 called from record_list where caller='"&mytel&"' order by id desc"
set rst=conn.execute(sqlt)
if not rst.eof or not rst.bof then
	called=rst(0)
end if
rst.close
set rst=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style>
.err
{
	font-size: 16px;
	line-height: 30px;
	font-weight: bold;
	color: #F00;
}
body,td,th {
	font-size: 12px;
	margin:0px;
}
</style>
<script>
function strmit(frm)
{
	if (frm.usertel.value=="")
	{
		alert("������8λ�̶����룡")
		frm.usertel.focus();
		return false;
	}
	if (frm.usertel.value.length<8)
	{
		alert("������8λ�̶����룡")
		return false;
	}
}
function recordset()
{
	if (document.getElementById('othertxt').value.length>0)
	{
		parent.document.getElementById('detail').value=document.getElementById('othertxt').value+"**"+parent.document.getElementById('detail').value
	}else
	{
		alert("��������ʹ��ʲô��ʽ������ϵ��")
		return false;
	}
	
	parent.document.getElementById('alertmsg').style.display='none';
	parent.document.getElementById('page_cover').style.display='none';
	parent.document.getElementById("recordflag").value="1"
	parent.form1.submit();
}
function closerecord()
{
	parent.document.getElementById('alertmsg').style.display='none';
	parent.document.getElementById('page_cover').style.display='none';
	//parent.document.getElementById("recordflag").value="1"
}
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="khtel.asp" onSubmit="return strmit(this)">
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <td colspan="2">
    <div class="err">
    ȥ�磬����</div><br />
    <div style="color:#03C; font-size:14px; line-height:22px">��¼�������ﻹû���ҵ���Ӧ�����¼�����ݣ�<br />
    �ÿͻ�����Ϊ:
    <%=khtel%><br />
    �����¼���绰Ϊ: <%=called%></div>
    </td>
    </tr>
  <tr>
    <td width="250" align="right" valign="top">���������ղŲ���Ŀͻ��绰��ͻ��������</td>
    <td>
      <input name="khtel" type="text" id="khtel" value="" size="30"/>
      <br />
      �绰�磺057156612345
      <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
      <br />
      �ֻ��磺13666651091</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="submit" name="button" id="button" value=" �� �� " /></td>
  </tr>
  <tr>
    <td colspan="2"><div class="err">����绰��������ʽ��ϵ<br />
        ������ʹ��ʲô��ʽ������ϵ
            <input type="text" name="othertxt" id="othertxt" />
    �磺QQ,�ʼ��������򲹳�С��</div></td>
    </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="button" name="button2" id="button2" value=" ȷ������ " onClick="recordset()" /> <input type="button" name="button3" id="button3" value=" �� �� " onClick="closerecord()" /></td>
  </tr>
</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
