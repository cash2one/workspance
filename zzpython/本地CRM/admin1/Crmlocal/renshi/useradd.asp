<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include FILE="../upload_5xsoft.asp"-->
<!--#include file="../inc.asp"-->
<%
dim upload,file,formName,formPath,iCount
set upload=new upload_5xSoft ''�����ϴ�����

add=upload.form("add")
username=upload.form("username")
mobile=trim(upload.form("mobile"))
station=trim(upload.form("station"))
station2=trim(upload.form("station2"))
if station2="" then station2=0
othercontact=trim(upload.form("othercontact"))
email=trim(upload.form("email"))
education=trim(upload.form("education"))
sex=trim(upload.form("sex"))
laiyuan=trim(upload.form("laiyuan"))
interviewTime=trim(upload.form("interviewTime"))
resumeUrl=trim(upload.form("resumeUrl"))
worklonger=trim(upload.form("worklonger"))
if worklonger="" then worklonger=0
UpFileType="doc,xls,txt,htm,html,mht"
if add<>"" then
    resumeUrl=uploadpic(UpFileType)
	set rs=server.createobject("adodb.recordset")
	sql="select id from renshi_user where mobile='"&trim(mobile)&"'"
	rs.open sql,conn,1,1
	if not rs.eof then
		response.write "<script languang='javascript'>alert('���ֻ��Ѿ����ڣ�');</script>"
		response.write "<script languang='javascript'>javascript:history.back(1);</script>"
		response.End()
	end if
	rs.close()
	set rs=server.createobject("adodb.recordset")
	sql="select * from renshi_user where id is null"
	rs.open sql,conn,1,3
	  rs.addnew()
	  rs("username")=username
	  rs("mobile")=trim(mobile)
	  rs("station")=station
	  rs("station2")=station2
	  rs("othercontact")=othercontact
	  rs("email")=trim(email)
	  rs("sex")=sex
	  rs("education")=education
	  rs("laiyuan")=laiyuan
	  'rs("interviewTime")=interviewTime
	  rs("resumeUrl")=resumeUrl
	  rs("gmt_created")=now
	  rs("personid")=session("personid")
	  rs("worklonger")=worklonger
	  rs.update()
	  rs.close()
	  set rs=nothing
	  sql="select max(id) from renshi_user where mobile='"&trim(mobile)&"'"
	  set rs=conn.execute(sql)
	  if not rs.eof or not rs.bof then
	  	 uid=rs(0)
	  end if
	  rs.close
	  set rs=nothing
	  Response.Cookies("renshi_station")=station
	  Response.Cookies("renshi_laiyuan")=laiyuan
	  sql="insert into renshi_assign(uid,personid) values("&uid&","&session("personid")&")"
	  conn.execute(sql)
	  
	  response.Write("<script>alert('��ӳɹ������Լ�����ӣ�');window.close()</script>")
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
function subchk(frm)
{
	if (frm.mobile.value=="")
	{
		alert("�������ֻ����룡");
		frm.mobile.focus();
		return false;
	}
   
	if  (frm.station.value=="")       
	{   
	    alert("��ѡ��ӦƸ��λ!\n");
	    frm.station.focus();
	    return  false;                 
	} 
	if  (frm.education.value=="")       
	{   
	    alert("��ѡ�����ѧ��!\n");
	    frm.education.focus();
	    return  false;                 
	} 
	
	 if  (frm.username.value=="")       
	{   
	    alert("����������!\n");
	    frm.username.focus();
	    return  false;                 
	}	
}
function isnum(f)
{
	if (isNaN(f.value))
	{
		f.value="";
	}
}
</script>
<style>
.input
{
	width:150px;
}
</style>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">���ӦƸ��Ա</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><br>
          <table width="400" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form action="useradd.asp" method="post" enctype="multipart/form-data" name="company_sec" onSubmit="return subchk(this)">
                <tr>
                  <td align="right">�ֻ�����</td>
                  <td><input type="text" name="mobile" class="input" id="mobile"></td>
                </tr>
                <tr>
                  <td align="right">ӦƸ��λ</td>
                  <td><%=cateMeno_public(conn,"category","station",request.Cookies("renshi_station"),"","15")%> <a href="sort_list.asp?lmcode=4202" target="_blank">����</a></td>
                </tr>
                <tr>
                  <td align="right">�ڶ�ӦƸ��</td>
                  <td><%=cateMeno_public(conn,"category","station2","","","15")%> <a href="sort_list.asp?lmcode=4202" target="_blank">����</a></td>
                </tr>
                <tr>
                  <td align="right">����</td>
                  <td><input name="username" type="text" class="input" id="username">
                    <input name="add" type="hidden" id="add" value="1"></td>
                </tr>
                <tr>
                  <td align="right">������ϵ��ʽ</td>
                  <td><input name="othercontact" type="text" class="input" id="othercontact"></td>
                </tr>
                <tr>
                  <td align="right">�Ա�</td>
                  <td><input name="sex" type="radio" id="radio" value="��" checked>
                    ��
                      <input type="radio" name="sex" id="radio2" value="Ů">
                      Ů</td>
                </tr>
                <tr>
                  <td align="right">��������</td>
                  <td><input type="text" name="email" class="input" id="email"></td>
                </tr>
                <tr>
                  <td align="right">���ѧ��</td>
                  <td>
                    <%=cateMeno_public(conn,"category","education","","","16")%> <a href="sort_list.asp?lmcode=4202" target="_blank">����</a></td>
                </tr>
                
                <tr align="center">
                  <td align="right">��������</td>
                  <td align="left"><input name="worklonger" type="text" id="worklonger" size="5" onKeyUp="isnum(this)">                    ��</td>
                </tr>
                <tr align="center">
                  <td align="right">������Դ</td>
                  <td align="left"><input type="text" name="laiyuan" id="laiyuan" value="<%=request.Cookies("renshi_laiyuan")%>"></td>
                </tr>
                <tr align="center">
                  <td align="right">��������</td>
                  <td align="left"><input type="file" name="fileField" id="fileField"></td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input name="Submit" type="submit" class="button01-out" value="�ύ">
                    <input name="Submit2" type="reset" class="button01-out" value="����">
                    </td>
                </tr>
              </form>
            </table></td>
          </tr>
        </table>
          </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../../conn_end.asp" -->