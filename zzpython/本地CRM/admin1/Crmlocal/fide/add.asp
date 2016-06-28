<%IsUploadFlag=1%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
if request.QueryString("cid")<>"" then
	cid=request.QueryString("cid")
    sql="select * from ybp_company where id="&cid&""
    set rs=conn.execute(sql)
    if not rs.eof or not rs.bof then
	  weburl=rs("weburl")
	  area=rs("area")
	  shop_name=rs("shop_name")
	  mobile=rs("mobile")
	  
	  hy_type=rs("hy_type")
	  station=rs("station")
	  regtime=rs("regtime")
	  contact=rs("contact")
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
	sql="select id from ybp_assign where cid="&cid&""
	set rs=conn.execute(sql)
	if rs.eof or rs.bof then
		gh=0
	else
		gh=1
	end if
	rs.close
	set rs=nothing
end if
if request.Form("edit")="1" then
	cid=request.Form("cid")
	sdostay=request.Form("dostay")
	sql="select * from ybp_company where id is null"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew
	rs("weburl")=request.Form("weburl")
	rs("area")=request.Form("area")
	rs("shop_name")=request.Form("shop_name")
	rs("mobile")=request.Form("mobile")
	rs("hy_type")=request.Form("hy_type")
	rs("station")=request.Form("station")
	rs("contact")=request.Form("contact")
	rs.update()

	rs.close
	set rs=nothing
	sql="select max(id) from ybp_company where shop_name='"&request.Form("shop_name")&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		maxid=rs(0)
	end if
	rs.close
	set rs=nothing
	sql="insert into ybp_assign (cid,personid) values("&maxid&","&session("personid")&")"
	conn.execute(sql)
	sDetail=request.Cookies("admin_user")&"录入客户"
	sqlp="insert into ybp_dohistory (cid,personid,sDetail) values("&maxid&","&session("personid")&",'"&sDetail&"')"
	conn.execute(sqlp)
	response.Write("保存成功！")
	response.Write("<script>alert('保存成功！');window.close()</script>")
	conn.close
	set conn=nothing
	response.End()
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

	if (frm.shop_name.value.length<=0){
		alert("请填写店铺名称！")
		frm.shop_name.focus();
		return false;
	}
	if (frm.area.value.length<=0){
		alert("请填写地区！")
		frm.area.focus();
		return false;
	}
	if (frm.hy_type.value.length<=0){
		alert("请填写行业分类！")
		frm.hy_type.focus();
		return false;
	}
	if (frm.contact.value.length<=0){
		alert("请填写联系方式！")
		frm.contact.focus();
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
.text{
	width:100%
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
              <form action="add.asp" method="post" name="company_sec" id="company_sec" onSubmit="return subchk(this)">
                <!--<tr>
                  <td width="200" align="right" bgcolor="#FFFFFF">店铺网址</td>
                  <td bgcolor="#FFFFFF">
                    </td>
                </tr>-->
                <tr>
                  <td align="right" bgcolor="#FFFFFF">
                  	<input name="cid" type="hidden" id="cid" value="<%=cid%>">
                    <input name="edit" type="hidden" id="edit" value="1">
                  	<span class="title">*</span>省份</td>
                  <td bgcolor="#FFFFFF"><%=area%> <input type="text" name="area" id="area" class="text"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF"><span class="title">*</span>银行名称</td>
                  <td bgcolor="#FFFFFF"><%=shop_name%> <input type="text" name="shop_name" id="shop_name" class="text"></td>
                </tr>
                <!--<tr>
                  <td align="right" bgcolor="#FFFFFF"><span class="title">*</span>旺旺号</td>
                  <td bgcolor="#FFFFFF"><%=wangwang_no%>
                    <input type="text" name="wangwang_no" id="wangwang_no" class="text"></td>
                </tr>-->
                <tr>
                  <td align="right" bgcolor="#FFFFFF"><span class="title">*</span>属性</td>
                  <td bgcolor="#FFFFFF"><%=hy_type%>
                    <input type="text" name="hy_type" id="hy_type" class="text"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF">联系电话</td>
                  <td bgcolor="#FFFFFF"><%=mobile%>
                    <input name="mobile" type="text" class="text" id="mobile" value="0"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF"><span class="title">*</span>联系人</td>
                  <td bgcolor="#FFFFFF"><%=contact%>
                    <input type="text" name="contact" id="contact" class="text"></td>
                </tr>
                <tr>
                  <td align="right" bgcolor="#FFFFFF"><span class="title">*</span>职位备注</td>
                  <td bgcolor="#FFFFFF"><%=station%>
                    <input type="text" name="station" id="station" class="text"></td>
                </tr>
                <tr align="center">
                  <td colspan="2" bgcolor="#FFFFFF">
                    <input name="Submit" type="submit" value="保存">
                    
  <input type="hidden" name="dostay" id="dostay" value="save">
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