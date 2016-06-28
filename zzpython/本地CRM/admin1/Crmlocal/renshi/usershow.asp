<%IsUploadFlag=1%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
if request.QueryString("uid")<>"" then
	uid=request.QueryString("uid")
    sql="select * from renshi_user where id="&uid&""
    set rs=conn.execute(sql)
    if not rs.eof or not rs.bof then
	  username=rs("username")
	  mobile=rs("mobile")
	  station=rs("station")
	  station2=rs("station2")
	  othercontact=rs("othercontact")
	  email=rs("email")
	  sex=rs("sex")
	  education=rs("education")
	  interviewTime=rs("interviewTime")
	  resumeUrl=rs("resumeUrl")
	  laiyuan=rs("laiyuan")
	  worklonger=rs("worklonger")
	  contactstat=rs("contactstat")
    end if
    rs.close
    set rs=nothing
end if
if request.Form("edit")="1" and request.Form("uid")<>"" then
	uid=request.Form("uid")
	sql="select * from renshi_user where id="&uid&""
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if not rs.eof or not rs.bof then
		rs("interviewTime")=request.Form("interviewTime")
		rs("star")=request.Form("star")
		selectjl=request.Form("selectjl")
		if selectjl<>"" then
			if selectjl="1" then
				if request.Form("jl1")<>"" then
					rs("jl1")=request.Form("jl1")
				end if
			end if
			if selectjl="2" then
				if request.Form("jl2")<>"" then
					rs("jl2")=request.Form("jl2")
				end if
			end if
			if selectjl="3" then
				if request.Form("jl3")<>"" then
					rs("jl3")=request.Form("jl3")
				end if
			end if
			if selectjl="4" then
				if request.Form("jl4")<>"" then
					rs("jl4")=request.Form("jl4")
				end if
			end if
			if selectjl="5" then
				if request.Form("jl5")<>"" then
					rs("jl5")=request.Form("jl5")
				end if
			end if
		end if
		rs.update()
	end if
	rs.close
	set rs=nothing
	selectjl=request.Form("selectjl")
	if selectjl<>"" then
		sql="select * from renshi_history where id is null"
		set rs=server.CreateObject("adodb.recordset")
		rs.open sql,conn,1,3
		rs.addnew
		if selectjl="1" then
			rs("code")=request.Form("jl1")
		end if
		if selectjl="2" then
			rs("code")=request.Form("jl2")
		end if
		if selectjl="3" then
			rs("code")=request.Form("jl3")
		end if
		if selectjl="4" then
			rs("code")=request.Form("jl4")
		end if
		if selectjl="5" then
			rs("code")=request.Form("jl5")
		end if
		rs("nextteltime")=request.Form("interviewTime")
		rs("star")=request.Form("star")
		rs("personid")=session("personid")
		rs("uid")=request.Form("uid")
		rs("bz")=request.Form("bz")
		rs("contactstat")=request.Form("contactstat")
		
		rs.update()
		rs.close
		set rs=nothing
		response.Write("<script>alert('添加成功！');window.close()</script>")
	end if
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>招聘记录</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
function subchk(frm)
{if (frm.contactstat.value=="")
	{
		alert("请选择联系状态！")
		frm.contactstat.focus();
		return false;
	}
	var getradio=GetValueChoose(document.getElementsByName("selectjl"))
	if(getradio=="")
	{
		alert("必须选择一个记录!");
		
		return false;
	}else{
		var slea=document.getElementById("jl"+getradio)
		if (slea.value=="")
		{
			alert("请选择记录情况!");
			slea.focus();
			return false;
		}
	}
	var objstar=GetValueChoose(document.getElementsByName("star"))
	if(objstar=="")
	{
		alert("请选择一个星级!");
		return false;
	}
	
}
function GetValueChoose(elementname)
{
	var sValue = "";
	var tmpels = elementname;
		for(var i=0;i<tmpels.length;i++)
		{
			if(tmpels[i].checked)
			{
				sValue = tmpels[i].value;
			}
		}
	return sValue;
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
          <table width="800" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" cellspacing="0" cellpadding="5">
              <tr>
                <td bgcolor="#CCCCCC">人员基本信息</td>
              </tr>
            </table>
              <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#999999">
              <form action="usershow.asp" method="post" name="company_sec" onSubmit="return subchk(this)">
                <tr>
                  <td align="right" bgcolor="#FFFFFF">手机号码</td>
                  <td bgcolor="#FFFFFF"><%=mobile%>
                    <input name="uid" type="hidden" id="uid" value="<%=uid%>">
                    <input name="edit" type="hidden" id="edit" value="1"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">应聘岗位</td>
                  <td bgcolor="#FFFFFF"><%=showMeno(conn,"category",station)%> </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">第二应聘岗</td>
                  <td bgcolor="#FFFFFF"><%=showMeno(conn,"category",station2)%> </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">姓名</td>
                  <td bgcolor="#FFFFFF"><%=username%>
                    </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">其他联系方式</td>
                  <td bgcolor="#FFFFFF"><%=othercontact%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">性别</td>
                  <td bgcolor="#FFFFFF"><%=sex%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">电子信箱</td>
                  <td bgcolor="#FFFFFF"><%=email%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">最高学历</td>
                  <td bgcolor="#FFFFFF"><%=showMeno(conn,"category",education)%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">工作年限</td>
                  <td bgcolor="#FFFFFF"><%=worklonger%>年</td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">简历来源</td>
                  <td bgcolor="#FFFFFF"><%=laiyuan%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">简历下载</td>
                  <td bgcolor="#FFFFFF">
                  <%
				  if trim(resumeUrl)<>"" then
				  	response.Write("<a href="&resumeUrl&" target=_blank>下载简历</a>")
				   end if
				  %>
                  </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF"> 联系状态 </td>
                  <td bgcolor="#FFFFFF"><%=cateMeno_public(conn,"category","contactstat",request.QueryString("contactstat"),"","22")%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">必须选择一个记录</td>
                  <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><input type="radio" name="selectjl" id="selectjl" value="1"></td>
                      <td width="60" align="right">邀约记录</td>
                      <td><%=cateMeno_public(conn,"category","jl1",request.QueryString("jl1"),"","17")%></td>
                    </tr>
                    <tr>
                      <td><input type="radio" name="selectjl" id="selectjl" value="2"></td>
                      <td align="right">初试记录</td>
                      <td><%=cateMeno_public(conn,"category","jl2",request.QueryString("jl1"),"","18")%></td>
                    </tr>
                    <tr>
                      <td><input type="radio" name="selectjl" id="selectjl" value="3"></td>
                      <td align="right">复试记录</td>
                      <td><%=cateMeno_public(conn,"category","jl3",request.QueryString("jl1"),"","19")%></td>
                    </tr>
                    <tr>
                      <td><input type="radio" name="selectjl" id="selectjl" value="4"></td>
                      <td align="right">报到记录</td>
                      <td><%=cateMeno_public(conn,"category","jl4",request.QueryString("jl1"),"","20")%></td>
                    </tr>
                    <tr>
                      <td><input type="radio" name="selectjl" id="selectjl" value="5"></td>
                      <td align="right">过程结束</td>
                      <td><%=cateMeno_public(conn,"category","jl5",request.QueryString("jl1"),"","21")%></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">星级</td>
                  <td bgcolor="#FFFFFF"><input type="radio" name="star" id="radio" value="5">
                    <input type="image" name="imageField" id="imageField" src="../../newimages/art1.gif">
                    <input type="image" name="imageField2" id="imageField2" src="../../newimages/art1.gif">
                    <input type="image" name="imageField3" id="imageField3" src="../../newimages/art1.gif">
                    <input type="image" name="imageField4" id="imageField4" src="../../newimages/art1.gif">
                    <input type="image" name="imageField5" id="imageField5" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="4">
                    <input type="image" name="imageField6" id="imageField6" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField7" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField8" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField9" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="3">
                    <input type="image" name="imageField7" id="imageField10" src="../../newimages/art1.gif">
                    <input type="image" name="imageField7" id="imageField11" src="../../newimages/art1.gif">
                    <input type="image" name="imageField7" id="imageField12" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="2">
                    <input type="image" name="imageField8" id="imageField13" src="../../newimages/art1.gif">
                    <input type="image" name="imageField8" id="imageField14" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="1">
                    <input type="image" name="imageField9" id="imageField15" src="../../newimages/art1.gif"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">下次面谈时间</td>
                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("interviewTime",false,"<%=interviewTime%>",false,false,false,true)</script></td>
                </tr>
                <tr align="center">
                  <td align="right" bgcolor="#FFFFFF">联系小计</td>
                  <td align="left" bgcolor="#FFFFFF"><textarea name="bz" id="bz" cols="45" rows="5"></textarea></td>
                </tr>
                <tr align="center">
                  <td colspan="2" bgcolor="#FFFFFF">
                    <input name="Submit" type="submit" class="button01-out" value="提交">
                    <input name="Submit2" type="reset" class="button01-out" value="重置">
                    </td>
                </tr>
              </form>
            </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <td bgcolor="#CCCCCC">联系小计</td>
                </tr>
              </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><iframe id=topt name=topt  style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="telList.asp?uid=<%=uid%>"></iframe></td>
                </tr>
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