<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include FILE="../upload_5xsoft.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
dim upload,file,formName,formPath,iCount
set upload=new upload_5xSoft ''建立上传对象

add=upload.form("add")
username=upload.form("username")
mobile=trim(upload.form("mobile"))
station=trim(upload.form("station"))
station2=trim(upload.form("station2"))
othercontact=trim(upload.form("othercontact"))
email=trim(upload.form("email"))
education=trim(upload.form("education"))
sex=trim(upload.form("sex"))
uid=upload.form("uid")
interviewTime=trim(upload.form("interviewTime"))
resumeUrl=trim(upload.form("resumeUrl"))
worklonger=trim(upload.form("worklonger"))
if worklonger="" then worklonger=0
UpFileType="doc,xls,txt,htm,html,mht"
laiyuan=trim(upload.form("laiyuan"))
if add<>"" then
    resumeUrl=uploadpic(UpFileType)
    
	set rs=server.createobject("adodb.recordset")
	sql="select * from renshi_user where id="&uid
	rs.open sql,conn,1,3
	  'rs.addnew()
	  rs("username")=username
	  rs("mobile")=trim(mobile)
	  rs("station")=station
	  rs("station2")=station2
	  rs("othercontact")=othercontact
	  rs("email")=trim(email)
	  rs("sex")=sex
	  rs("education")=education
	  rs("laiyuan")=laiyuan
	  rs("interviewTime")=interviewTime
	  rs("worklonger")=worklonger
	  if resumeUrl<>"" then
	  rs("resumeUrl")=resumeUrl
	  end if
	  rs("gmt_created")=now
	  rs("personid")=session("personid")
	  rs.update()
	  rs.close()
	  set rs=nothing
	  response.Write("<script>alert('修改成功！');window.close()</script>")
end if
if request.QueryString("uid")<>"" then
	uid=request.QueryString("uid")
    sql="select * from renshi_user where id="&uid&""
    set rs=conn.execute(sql)
    if not rs.eof or not rs.bof then
	  username=rs("username")
	  mobile=rs("mobile")
	  station2=rs("station2")
	  station=rs("station")
	  othercontact=rs("othercontact")
	  email=rs("email")
	  sex=rs("sex")
	  education=rs("education")
	  interviewTime=rs("interviewTime")
	  resumeUrl=rs("resumeUrl")
	  laiyuan=rs("laiyuan")
	  worklonger=rs("worklonger")
    end if
    rs.close
    set rs=nothing
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>人员修改</title>
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
		alert("请输入手机号码！");
		frm.mobile.focus();
		return false;
	}
   
	if  (frm.station.value=="")       
	{   
	    alert("请选择应聘岗位!\n");
	    frm.station.focus();
	    return  false;                 
	} 
	if  (frm.education.value=="")       
	{   
	    alert("请选择最高学历!\n");
	    frm.education.focus();
	    return  false;                 
	} 
	
	 if  (frm.username.value=="")       
	{   
	    alert("请输入姓名!\n");
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">添加员工</td>
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
              <form action="useredit.asp" method="post" enctype="multipart/form-data" name="company_sec" onSubmit="return subchk(this)">
                <tr>
                  <td align="right">手机号码</td>
                  <td><input type="text" name="mobile" class="input" id="mobile" value="<%=mobile%>">
                    <input name="uid" type="hidden" id="uid" value="<%=uid%>"></td>
                </tr>
                <tr>
                  <td align="right">应聘岗位</td>
                  <td><%=cateMeno_public(conn,"category","station",station,"","15")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr>
                  <td align="right">第二应聘岗</td>
                  <td><%=cateMeno_public(conn,"category","station2",station2,"","15")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr>
                  <td align="right">姓名</td>
                  <td><input name="username" type="text" class="input" id="username" value="<%=username%>">
                    <input name="add" type="hidden" id="add" value="1"></td>
                </tr>
                <tr>
                  <td align="right">其他联系方式</td>
                  <td><input name="othercontact" type="text" class="input" id="othercontact" value="<%=othercontact%>"></td>
                </tr>
                <tr>
                  <td align="right">性别</td>
                  <td><input name="sex" type="radio" id="radio" value="男" <%if sex="男" then response.Write("checked")%>>
                    男
                      <input type="radio" name="sex" id="radio2" value="女" <%if sex="女" then response.Write("checked")%>>
                      女</td>
                </tr>
                <tr>
                  <td align="right">电子信箱</td>
                  <td><input type="text" name="email" class="input" id="email" value="<%=email%>"></td>
                </tr>
                <tr>
                  <td align="right">最高学历</td>
                  <td><%=cateMeno_public(conn,"category","education",education,"","16")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr align="center">
                  <td align="right">工作年限</td>
                  <td align="left"><input name="worklonger" type="text" id="worklonger" size="5" onKeyUp="isnum(this)" value="<%=worklonger%>">年</td>
                </tr>
                <tr>
                  <td align="right">面谈时间</td>
                  <td><script language=javascript>createDatePicker("interviewTime",false,"<%=interviewTime%>",false,false,false,true)</script></td>
                </tr>
                <tr align="center">
                  <td align="right">简历来源</td>
                  <td align="left"><input type="text" name="laiyuan" id="laiyuan" value="<%=laiyuan%>"></td>
                </tr>
                <tr align="center">
                  <td align="right">简历附件</td>
                  <td align="left"><input type="file" name="fileField" id="fileField"></td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input name="Submit" type="submit" class="button01-out" value="提交">
                    <input name="Submit2" type="reset" class="button01-out" value="重置">
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