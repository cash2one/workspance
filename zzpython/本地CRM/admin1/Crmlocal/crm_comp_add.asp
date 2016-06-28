<%@ Language=VBScript %>
<%
'response.Write("系统升级，此功能暂停！")
'response.End()

%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公司</title>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="../../cn/sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="js/province_utf8.js"></SCRIPT>
<SCRIPT language=javascript src="js/compkind.js"></SCRIPT>
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td style="line-height:20px"><p style="color:#F00"><STRONG>【责任明确】本着有发现规定期内客户接到电话会告知其它同事，我对此申请已确保查询过&ldquo;公司名称&rdquo;&ldquo;座机&rdquo;&ldquo;手机&rdquo;这3要素重复，如在申请时有撞单的，我对此撞单负责。</STRONG></p>
      <p><STRONG>【撞单制度.录入事项规范】</STRONG><BR>
        <STRONG>&nbsp;6.1.2、录入规范：</STRONG><BR>
        &nbsp;&nbsp;6.1.2.1、公司、手机、座机3要素录入者必须第一先查明是否有重复。如无重复，需要录入信息详细真实填写。<BR>
        &nbsp;&nbsp;6.1.2.2、录入如有发现公司、手机、座机已有重复的不用再录入，有在别人库中的需要判单处理，提交客户申请邮件。<BR>
        &nbsp;&nbsp;6.1.2.3、发现有重复号码需要帮客户录入一个新的邮箱的，需要确定其它几个已有账号没有撞单冲突，且需要在每个原来账号里面写上&ldquo;关联小记&rdquo;。</p>
      <p><STRONG>&nbsp;6.1.3、审核标准：</STRONG><BR>
        &nbsp;&nbsp;6.1.3.1、资料完善，发现公司名称、手机、座机、邮箱乱填和不真实的不予通过审核。<BR>
        &nbsp;&nbsp;6.1.3.2、录入公司、手机、座机均无重复可方通过录入审核。如有号码需要重复录入的确保没有和其他人发生此客户的撞单，再发客户申请邮件并且需要注明具体情况。让审核人可以了解具体情况。</p>
      <p><STRONG>&nbsp;6.1.4、录入违规，审核违规处罚：</STRONG><BR>
    如发现违规情况，审核人与录入者各受50-100元罚款。违规并造成不良后果的，根据情节严重处以行政处罚。&nbsp;&nbsp;&nbsp;&nbsp;<STRONG>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <br>
    有故意引导客户或&ldquo;刻意&rdquo;录入不同公司/电话号码用来&ldquo;避免撞单&rdquo;的，属于价值观问题的，依照公司制度，严惩！</STRONG></p></td>
  </tr>
</table>
<iframe scrolling="yes" id="maina" name="maina" style="HEIGHT: 200px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" frameborder="0" src="http://adminasto.zz91.com/searchcomplistmain/"></iframe>


<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
  <tr>
    <td height="60" bgcolor="#FFFFFF">
    <iframe scrolling="no" id="mainb" name="mainb" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1" frameborder="0" src=""></iframe>
    </td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="4" cellspacing="0" class=se id=ListTable>
<script language="javascript">
function chkfrm(frm)
{
	if(frm.cname.value.length<=0)
	{
		alert("请输入公司名称!");
		frm.cname.focus();
		return false;
	}
	if(frm.cadd.value.length<=0)
	{
		alert("请输入地址!");
		frm.cadd.focus();
		return false;
	}
	
	if (frm.ckeywords.value=="")
	{
		alert("请选择主营业务！");
		frm.ckeywords.focus()
		return false
	}
	if(frm.ctel.value=="")
	{
		alert("请输入电话!");
		frm.ctel.focus();
		return false;
	}
    if (frm.ckind.value=="")
	{
		alert("请选择公司类型！")
		frm.ckind.focus()
		return false;
	}
	if (frm.countryselect.value=="1")
	{
		if(frm.province1.value=="")
		{
			alert("请选择省份！");
			frm.province1.focus();
			return false;
		}
		if(frm.city1.value=="")
		{
			alert("请选择城市！");
			frm.city1.focus();
			return false;
		}
	}
	
	if(frm.cemail.value.length<=0)
	{
		alert("请输入电子邮件!");
		frm.cemail.focus();
		return false;
	}
	if(!/^(.+)@(.+)(\.\w+)+$/ig.test(frm.cemail.value)){  
		alert("电子邮箱格式错误");
		frm.cemail.focus();
		return  false;
	}  
	if(frm.ccontactp.value.length<=0)
	{
		alert("请输入联系人!");
		frm.ccontactp.focus();
		return false;
	}
}
function enteremail(frm)
{
	if (frm.cmobile.value=="" && frm.ctel.value=="")
	{
		alert ("请输入联系方式！")
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
                <%
				tourl="http://admin.zz91.com/admin1/Crmlocal/crm_comp_save.asp"
				%>
                  <form name="form1" method="post" action="http://adminasto.zz91.com/companysave/" onSubmit="return chkfrm(this)">
                   
                    <tr bgcolor="#D9ECFF">
                      <td height="30" colspan="4" class="tbar"><span class="bold">基本信息</span></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td width="80" align="right">公司名称：                      
                      <input type="hidden" name="personid" id="personid" value="<%=session("personid")%>">
                      <input type="hidden" name="addtype" id="addtype" value="<%=request("addtype")%>"></td>
                      <td><input name="cname" type="text" id="cname" class=text value="" size="30" maxlength="96">
                      </td>
                      <td width="80" align="right" nowrap>联系人：</td>
                      <td nowrap><input class=text name="ccontactp" type="text" id="ccontactp" size="20" maxlength="48">
                      称呼：
                      <input type="radio" name="cdesi" id="cdesi" <%=ccf1%> value="先生">
                          先生 
                          <input type="radio" name="cdesi" id="cdesi" <%=ccf2%> value="女士">
                          女士</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">所处行业：</td>
                      <td>
                      <select name="ckeywords" id="ckeywords" style="width:150px" onFocus="validateValue(this)" onBlur="validateValueout(this)" onChange="changekind(this.options[this.selectedIndex].value,'ckind','ckindmain');getprovincevalue()" >
                          <option value="">请选择</option>
                          <option value="1">废金属</option>
                          <option value="2">废塑料</option>
                          <option value="3">废旧轮胎与废橡胶</option>
                          <option value="4">废纺织品与废皮革</option>
                          <option value="5">废纸</option>
                          <option value="6">废电子电器</option>
                          <option value="10">废玻璃</option>
                          <option value="12">废旧二手设备</option>
                          <option value="14">其他废料</option>
                          <option value="15">服务</option>
                        </select>
                      </td>
                      <td align="right">公司类型：</td>
                      <td>
                      <div id="ckindmain" style="float:left"></div>
                      <script>changekind("","ckind","ckindmain")</script>
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">国家/地区：</td>
                      <td>
                      <script>
						function selectcountry(id)
						{
							if (id=="1")
							{
								document.getElementById("othercountrys").style.display="none"
								document.getElementById("mycountry").style.display=""
							}
							else
							{
								document.getElementById("othercountrys").style.display=""
								document.getElementById("mycountry").style.display="none"
								document.getElementById("othercity").value=""
							}
						}
						</script>
                      <select name="countryselect" id="countryselect" onChange="selectcountry(this.value);">
                          <option value="1" >中国</option>
                          <option value="0" >其他国家/地区</option>
                      </select></td>
                      <td align="right">省市：</td>
                      <td>
                      <div id="othercountrys" style="display:none">
                        <select name="ccountry" id="ccountry" onChange="selecttextname(this)">
						<%
						
                        sqlc="select ctr_id,ctr_chn_name from countrys where ctr_id<>1 order by ctr_index asc"
                        set rsc=server.CreateObject("adodb.recordset")
                        rsc.open sqlc,conn,1,2
                        if not rsc.eof then
                        do while not rsc.eof
                        %>
                          <option value="<%=rsc(0)%>" <%if com_ctr_id=rsc(0) then response.Write("selected")%>><%=rsc(1)%></option>
                        <%
                        rsc.movenext
                        loop
                        end if
                        rsc.close
                        set rsc=nothing
                        %>
                          </select>
                          <input name="othercity" type="text" id="othercity" class="textt" onClick="selecttextname(this)" size="20" />
                        </div>
                        <div id="mycountry">
                        <font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
                        <input type="hidden" name="province" id="province">
                        <input type="hidden" name="city" id="city">
                        
                        
                        <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="ckeywords";//行业ID号
                            getprovincevalue();
                        </script>
                        <script>
                        function getprovincename()
                        {
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
                            document.getElementById("city").value=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
                        }
                        </script>
                        <input type="hidden" name="cprovince" id="cprovince" value="0">
                        </div>
                      </td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">地址：</td>
                      <td><input name="cadd" type="text" class=text id="cadd" size="50" maxlength="20"></td>
                      <td align="right">邮编：</td>
                      <td><input class=text name="czip" type="text" id="czip" size="20" maxlength="48"></td>
                    </tr>
					
					
					
                    
                    <tr bgcolor="#FFFFFF">
                      <td align="right">电话：</td>
                      <td><input name="ctel" type="text" id="ctel" class=text size="30" maxlength="96"></td>
                      <td align="right">手机：</td>
                      <td><input name="cmobile" type="text" id="cmobile" class=text size="30" maxlength="96"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">传真：</td>
                      <td><input name="cfax" type="text" class=text id="cfax" size="30" maxlength="96"></td>
                      <td align="right">电子邮箱：</td>
                      <td nowrap><input name="cemail" class=text type="text" id="cemail" size="30" maxlength="48" <%=redo%>>
                        <input name="Submit" type="button" class="button" onClick="enteremail(this.form)" value="生成"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right">网站：</td>
                      <td><input name="cweb" class=text type="text" id="cweb" size="30" maxlength="255"></td>
                      <td>&nbsp;</td>
                      <td>如果没有email请用 (手机或电话@zz91.com)</td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                      <td align="right" nowrap>公司简介：</td>
                      <td colspan="3" align="left"><textarea name="cintroduce" cols="60" rows="5" id="cintroduce"></textarea></td>
                    </tr>
                   
                    <tr align="center" bgcolor="#FFFFFF">
                      <td align="right">主营业务：</td>
                      <td colspan="3" align="left"><textarea name="cproductslist_en" cols="50" rows="4" id="cproductslist_en"></textarea></td>
                    </tr>
                    <tr align="center" bgcolor="#FFFFFF">
                      <td height="500" colspan="4" ><br>
                        <input name="Submit" type="submit" class="button" value=" 第一步保存客户信息 ">
                      <br>
                      <br>
                      <br></td>
                    </tr>
					
                    
                  </form>
</table>
</body>
</html>
