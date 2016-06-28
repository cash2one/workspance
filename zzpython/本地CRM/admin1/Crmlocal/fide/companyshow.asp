<%IsUploadFlag=1%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
sqluser="select ywadminid,adminuserid,Partuserid,huangye_check from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
ywadminid=rsuser("ywadminid")
adminuserid=rsuser("adminuserid")
Partuserid=rsuser("Partuserid")
huangye_check=rsuser("huangye_check")
rsuser.close
set rsuser=nothing
cid=request.QueryString("cid")
if cid<>"" then
'-------
	if session("userid")="1314" or session("userid")="4204"  then
		'-----销售助理的权限
	else
	  sqla="select personid from ybp_assign where cid="&cid
	  set rsa=conn.execute(sqla)
	  if not rsa.eof or not rsa.bof  then
	  	  ownerpersonid=rsa(0)
	  	  if session("userid")="10" then
		  else
		  	  openqx=0
			  personopen=0
		  	  if ywadminid<>"" and not isnull(ywadminid)  then
				  sqlb="select userid from users where id="&ownerpersonid&" and userid in ("&ywadminid&")"
				  set rsb=conn.execute(sqlb)
				  if not rsb.eof or not rsb.bof then
				     openqx=1
				  end if
				  rsb.close
				  set rsb=nothing
			  end if
			  '------------个人客户
			  if cstr(session("personid"))=cstr(ownerpersonid)  then
				  personopen=1
				  openqx=1
			  end if
			  '--------主管权限
			  if partuserid<>"0" then
			  	  openqx=1
			  end if
			  if openqx=0 then
					response.Write("你没有权限查看该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
					response.End()
			  end if
			 
		  end if
		  opengonghai=0
	  else
	    
		opengonghai=1
		'----------
	  end if
	  rsa.close
	  set rsa=nothing
	end if
end if	
	
	
if request.QueryString("cid")<>"" then
	
    sql="select * from ybp_company where id="&cid&""
    set rs=conn.execute(sql)
    if not rs.eof or not rs.bof then
	  weburl=rs("weburl")
	  area=rs("area")
	  shop_name=rs("shop_name")
	  wangwang_no=rs("wangwang_no")
	  hy_type=rs("hy_type")
	  income=rs("income")
	  regtime=rs("regtime")
	  contact=rs("contact")
	  mobile=rs("mobile")
	  station=rs("station")
	  star=rs("star")  
	  personid=rs("personid")
	  interviewTime=rs("interviewTime")
	  if interviewTime<>"" then
	  	interviewTime=cdate(interviewTime)+7
	  end if
	  bankcheck=rs("bankcheck")
	  if bankcheck="" or isnull(bankcheck) then
	  	bankcheck="0"
	  end if
    end if
    rs.close
    set rs=nothing
	sql="select id from ybp_assign where cid="&cid&" and personid="&session("personid")&""
	set rs=conn.execute(sql)
	if rs.eof or rs.bof then
		gh=0
	else
		gh=1
	end if
	rs.close
	set rs=nothing
end if
if request.Form("edit")="1" and request.Form("cid")<>"" then
	cid=request.Form("cid")
	sdostay=request.Form("dostay")
	bankcheck=request.Form("bankcheck")
	if bankcheck="" or isnull(bankcheck) then
		bankcheck="0"
    end if
	sql="select * from ybp_company where id="&cid&""
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if not rs.eof or not rs.bof then
		if sdostay="save" then
			rs("interviewTime")=request.Form("interviewTime")
		end if
		rs("star")=request.Form("star")
		rs("contacttime")=now()
		rs("bankcheck")=bankcheck
		rs.update()
	end if
	rs.close
	set rs=nothing
	
	'----转4、5星记录
	sqle="select top 1 star from ybp_tel where cid="&cid&" and personid="&session("personid")&" order by fdate desc"
	set rse=conn.execute(sqle)
	if not rse.eof or not rse.bof then
		oldstar=rse(0)
	else
		oldstar=0
	end if
	rse.close
	set rse=nothing
	response.Write(oldstar&"-"&oldstar)
	nowstar=cstr(request.Form("star"))
	if nowstar="4" and int(oldstar)<4 then
		sqlp="insert into ybp_tostar(cid,personid,star) values("&cid&","&session("personid")&","&nowstar&")"
		conn.execute(sqlp)
	end if
	if nowstar="5" and int(oldstar)<5 then 
		sqlp="insert into ybp_tostar(cid,personid,star) values("&cid&","&session("personid")&","&nowstar&")"
		conn.execute(sqlp)
	end if
	
	sql="select * from ybp_tel where id is null"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew
	if sdostay="save" then
		rs("nextteltime")=request.Form("interviewTime")
	end if
	rs("star")=request.Form("star")
	rs("personid")=session("personid")
	rs("cid")=request.Form("cid")
	rs("bz")=request.Form("bz")
	
	rs("contactstat")=request.Form("contactstat")
	rs.update()
	rs.close
	set rs=nothing
	
	
	if sdostay="gonghai" then
		response.Redirect("do.asp?dostay=gonghai&selectcb="&cid&"&closeflag=1")
	else
		response.Write("<script>alert('保存成功！');window.close()</script>")
	end if
end if
dotype=request.QueryString("dotype")
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=shop_name%> - 商铺信息</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
function subchk(frm){
	if (frm.ghflag.value=="0"){
		return false;
	}
	if (frm.contactstat.value==""){
		alert("请选择联系状态！")
		frm.contactstat.focus();
		return false;
	}
	var objstat=GetValueChoose(document.getElementsByName("contactstat"))
	if(objstat==""){
		alert("请选择联系状态!");
		return false;
	}
	
	var dostay=document.getElementById("dostay");

	
	var objstar=GetValueChoose(document.getElementsByName("star"))
	if(objstar=="")
	{
		alert("请选择一个星级!");
		return false;
	}
	if (frm.interviewTime.value=="" && dostay.value=="save"){
		alert("请选择下次联系时间！")
		frm.interviewTime.focus();
		return false;
	}
	if (frm.bz.value.length<=0){
		alert("请填写联系小计！")
		frm.bz.focus();
		return false;
	}
	frm.submit()
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
function gotosave(frm){
	var dostay=document.getElementById("dostay")
	if (dostay){
		dostay.value="save"
	}
	subchk(frm)
}
function gotogonghai(frm){
	var dostay=document.getElementById("dostay")
	if (dostay){
		dostay.value="gonghai"
	}
	subchk(frm)
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">商铺信息</td>
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
                <td bgcolor="#CCCCCC">基本信息</td>
              </tr>
            </table>
              <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#999999">
              <form action="companyshow.asp" method="post" name="company_sec" id="company_sec" onSubmit="return subchk(this)">
                
                <tr>
                  <td align="right" bgcolor="#FFFFFF">地区</td>
                  <td bgcolor="#FFFFFF"><%=area%> <input name="cid" type="hidden" id="cid" value="<%=cid%>">
                    <input name="edit" type="hidden" id="edit" value="1">
                    <input name="ghflag" type="hidden" id="ghflag" value="<%=gh%>"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">银行名称</td>
                  <td bgcolor="#FFFFFF"><%=shop_name%> </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">属性</td>
                  <td bgcolor="#FFFFFF"><%=hy_type%></td>
                </tr>
                
                <tr>
                  <td align="right" bgcolor="#FFFFFF">联系人</td>
                  <td bgcolor="#FFFFFF"><%=contact%>
                    </td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">职位备注</td>
                  <td bgcolor="#FFFFFF"><%=station%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">联系电话</td>
                  <td bgcolor="#FFFFFF"><%=mobile%></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">注册时间</td>
                  <td bgcolor="#FFFFFF"><%=regtime%></td>
                </tr>
                
                <tr>
                  <td align="right" bgcolor="#FFFFFF"> 联系状态 </td>
                  <td bgcolor="#FFFFFF"><input type="radio" name="contactstat" id="contactstat" value="1">
                    有效联系
                      <input type="radio" name="contactstat" id="contactstat" value="0">
                      无效联系</td>
                </tr>
               
                <!--<tr>
                  <td align="right" bgcolor="#FFFFFF">自审</td>
                  <td bgcolor="#FFFFFF"><input type="radio" name="bankcheck" id="bankcheck" value="1" <%if bankcheck="1" then response.Write("checked")%>>
                    通过
                      <input type="radio" name="bankcheck" id="bankcheck" value="2" <%if bankcheck="2" then response.Write("checked")%>>
                      未通过
                      <input type="radio" name="bankcheck" id="bankcheck" value="0">
                      未审</td>
                </tr>-->
                <tr>
                  <td align="right" bgcolor="#FFFFFF">星级</td>
                  <td bgcolor="#FFFFFF"><input type="radio" name="star" id="radio" value="5" <%if star="5" then response.Write("checked")%>>
                    <input type="image" name="imageField" id="imageField" src="../../newimages/art1.gif">
                    <input type="image" name="imageField2" id="imageField2" src="../../newimages/art1.gif">
                    <input type="image" name="imageField3" id="imageField3" src="../../newimages/art1.gif">
                    <input type="image" name="imageField4" id="imageField4" src="../../newimages/art1.gif">
                    <input type="image" name="imageField5" id="imageField5" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="4" <%if star="4" then response.Write("checked")%>>
                    <input type="image" name="imageField6" id="imageField6" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField7" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField8" src="../../newimages/art1.gif">
                    <input type="image" name="imageField6" id="imageField9" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="3" <%if star="3" then response.Write("checked")%>>
                    <input type="image" name="imageField7" id="imageField10" src="../../newimages/art1.gif">
                    <input type="image" name="imageField7" id="imageField11" src="../../newimages/art1.gif">
                    <input type="image" name="imageField7" id="imageField12" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="2" <%if star="2" then response.Write("checked")%>>
                    <input type="image" name="imageField8" id="imageField13" src="../../newimages/art1.gif">
                    <input type="image" name="imageField8" id="imageField14" src="../../newimages/art1.gif">
                    <input type="radio" name="star" id="star" value="1" <%if star="1" then response.Write("checked")%>>
                    <input type="image" name="imageField9" id="imageField15" src="../../newimages/art1.gif"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">下次联系时间</td>
                  <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("interviewTime",true,"",false,false,false,false)</script></td>
                </tr>
                <tr align="center">
                  <td align="right" bgcolor="#FFFFFF">联系小计</td>
                  <td align="left" bgcolor="#FFFFFF"><textarea name="bz" id="bz" cols="45" rows="5"></textarea></td>
                </tr>
                <tr align="center">
                  <td colspan="2" bgcolor="#FFFFFF">
                    <%if gh=1 then%><input name="Submit" type="button" value="保存" onClick="return gotosave(this.form)">
                    &nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" name="button" id="button" value="保存后放到公海" onClick="return gotogonghai(this.form);"><%end if%>
&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="button2" id="button2" value="日志" onClick="window.open('history.asp?cid=<%=cid%>','','')">
					<input type="hidden" name="dostay" id="dostay" value="save">
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
                  <td><iframe id=topt name=topt  style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="telList.asp?cid=<%=cid%>"></iframe></td>
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