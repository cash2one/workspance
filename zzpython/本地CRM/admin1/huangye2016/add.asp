<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
com_id=0
personid=session("personid")
lpersonid=personid
lurutype=request.Form("lurutype")
if lurutype="" then
	lurutype=0
end if
sqlp="select realname,huangye_check from users where id="&personid&""
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	huangye_check=rsp("huangye_check")
	realname=rsp("realname")
	personname=realname
end if
rsp.close
set rsp=nothing
'--------------------------根据地区名取得地区编码
function getprovince(meno)
	sqlgp="select code from cate_province where meno='"&meno&"'"
	set rsgp=conn.execute(sqlgp)
	if not rsgp.eof or not rsgp.bof then
		getprovince=rsgp(0)
	end if
	rsgp.close
	set rsgp=nothing
end function
if request("com_email")<>"" or request("mobile")<>"" then
	if request("com_email")<>"" then
		sqlc="select top 1 * from huangye_list where com_email='"&request("com_email")&"' and huangye_qukan='2016'"
	else
		sqlc="select top 1 * from huangye_list where cmobile='"&request("mobile")&"' and huangye_qukan='2016'"
	end if
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		com_email=rsc("com_email")
		com_id=rsc("com_id")
		comname=replacequot(trim(rsc("cname")))
		comadd=replacequot(trim(rsc("cadd")))
		comprovince=replacequot(trim(rsc("province")))
		personname=trim(rsc("personname"))
		commobile=trim(rsc("cmobile"))
		comcontactp=replacequot(trim(rsc("ccontactp")))
		cproductslist=replacequot(trim(rsc("cproductslist")))
		com_keywords=rsc("comkeywords")
		lpersonid=rsc("personid")
		newemail=rsc("newemail")
		js1=rsc("js1")
		js2=rsc("js2")
		sl1=rsc("sl1")
		sl2=rsc("sl2")
		qt1=rsc("qt1")
		qt2=rsc("qt2")
		personid=rsc("personid")
		membertype=rsc("membertype")
		action="edit"
		pcheck=rsc("pcheck")
		errstr="改客户已经添加，请不要重复添加"
		errflag=1
	else
		if request("com_email")<>"" then
			sql="select top 1 * from comp_info where com_email='"&request("com_email")&"'"
		else
			sql="select top 1 * from comp_info where com_mobile='"&request("mobile")&"'"
		end if
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			com_email=rs("com_email")
			com_id=rs("com_id")
			comname=replacequot(trim(rs("com_name")))
			comadd=replacequot(trim(rs("com_add")))
			comprovince=replacequot(trim(rs("com_province")))
			comtel=trim(rs("com_tel"))
			commobile=trim(rs("com_mobile"))
			if commobile="" or isnull(commobile) then
				commobile=comtel
			end if
			comcontactp=replacequot(trim(rs("com_contactperson")))
			comdesi=replacequot(trim(rs("com_desi")))
			cproductslist=replacequot(trim(rs("com_productslist_en")))
			com_keywords=rs("com_keywords")
		end if
		membertype=0
		lpersonid=session("personid")
		personname=realname
		rs.close
		set rs=nothing
		action="add"
		errstr=""
		errflag=0
	end if
	rsc.close
	set rsc=nothing
	
	cprovince=replacequot1(comprovince)
	Arrprovince=split(cprovince,"|")
	if ubound(Arrprovince)>0 then
		province=Arrprovince(0)
		city=Arrprovince(1)
	end if
	
	'--------------------------直接取的地区编码
	sqlh1="select * from comp_ProvinceID where com_id="&com_id
	set rsh1=conn.execute(sqlh1)
	if not rsh1.eof or not rsh1.bof then
		provinceID=rsh1("province")
		cityID=rsh1("city")
		GardenID=rsh1("Garden")
	else
		provinceID=getprovince(province)
		cityID=getprovince(city)
		GardenID=""
	end if
	rsh1.close
	set rsh1=nothing	
end if
if request("id")<>"" then
	id=request("id")
	sqlc="select * from huangye_list where id='"&request("id")&"'"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		com_email=rsc("com_email")
		com_id=rsc("com_id")
		comname=replacequot(trim(rsc("cname")))
		comadd=replacequot(trim(rsc("cadd")))
		comprovince=replacequot(trim(rsc("province")))
		'comtel=trim(rsc("ctel"))
		commobile=trim(rsc("cmobile"))
		comcontactp=replacequot(trim(rsc("ccontactp")))
		cproductslist=replacequot(trim(rsc("cproductslist")))
		com_keywords=rsc("comkeywords")
		newemail=rsc("newemail")
		personname=trim(rsc("personname"))
		lpersonid=rsc("personid")
		js1=rsc("js1")
		js2=rsc("js2")
		sl1=rsc("sl1")
		sl2=rsc("sl2")
		qt1=rsc("qt1")
		qt2=rsc("qt2")
		membertype=rsc("membertype")
		action="edit"
		pcheck=rsc("pcheck")
		
		
		'---------城市
		cprovince=replacequot1(comprovince)
		Arrprovince=split(cprovince,"|")
		if ubound(Arrprovince)>0 then
			province=Arrprovince(0)
			city=Arrprovince(1)
		end if
		
		'--------------------------直接取的地区编码
		provinceID=getprovince(province)
		cityID=getprovince(city)
	else
		id=0
	end if
else
	id=0
end if

if pcheck="" then pcheck="0"

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
<title><%=comname%>商务大全公司信息提交</title>
<SCRIPT language=javascript src="http://192.168.2.2/cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="http://192.168.2.2/admin1/main.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="http://192.168.2.2/admin1/crmlocal/js/list.js"></SCRIPT>
<style>
.input
{
	width:90%
}
.red
{
	color:#F00
}
</style>
<link href="s.css" rel="stylesheet" type="text/css" />
<script>
function searchemail(frm){
	frm.action="add.asp"
	frm.submit();
}
function frmsubmit(frm){
	frm.action="save.asp";
	if (frm.lurutype.value=="0"){
		if(frm.com_email.value.length<=0){
			alert("请输入邮箱!");
			frm.com_email.focus();
			return false;
		}
	}else{
		if(frm.mobile.value.length<=0){
			alert("请输入手机!");
			frm.mobile.focus();
			return false;
		}
	}
	if(frm.cname.value.length<=0){
		alert("请输入公司名称!");
		frm.cname.focus();
		return false;
	}
	getprovincename();
	if(frm.cadd.value.length<=0){
		alert("请输入地址!");
		frm.cadd.focus();
		return false;
	}
	if(frm.cmobile.value.length<=0){
		alert("请输入手机号码!");
		frm.cmobile.focus();
		return false;
	}
	if(frm.ccontactp.value.length<=0){
		alert("请输入联系人!");
		frm.ccontactp.focus();
		return false;
	}
	if (frm.ckeywords.value==""){
		alert("请选择行业!");
		frm.ckeywords.focus();
		return false;
	}
	if (frm.ckeywords.value=="1"){
		if (frm.js1.value==""){
			alert("必选选择一个金属小类");
			frm.js1.focus();
			return false;
		}
		if (frm.js1.value==frm.js2.value){
			alert("小类比选择项目和其他项不能一样！");
			frm.js2.focus();
			return false;
		}
	}
	if (frm.ckeywords.value=="2"){
		if (frm.sl1.value==""){
			alert("必选选择一个塑料小类");
			frm.sl1.focus()
			return false;
		}
		if (frm.sl1.value==frm.sl2.value){
			alert("小类比选择项目和其他项不能一样！");
			frm.sl2.focus();
			return false;
		}
	}
	if (frm.cproductslist.value==""){
		alert("请填写主营业务!");
		frm.cproductslist.focus();
		return false;
	}
	
	if (frm.province.value==""){
		alert("请选择省份!");
		frm.province.focus();
		return false;
	}
	if (frm.city.value==""){
		alert("请选择城市!");
		frm.city.focus();
		return false;
	}
	frm.submit();
}
function selectleibie(obj){
	document.getElementById("leibie1").style.display="none";
	document.getElementById("leibie2").style.display="none";
	document.getElementById("leibie3").style.display="none";
	var obj1=document.getElementById("leibie"+obj);
	obj1.style.display=""
	
}
function selectsex(s){
	var obj=document.getElementById("ccontactph")
	var obj1=document.getElementById("ccontactp")
	obj1.value=obj.value+s
}
function selectlurutype(n){
	document.getElementById("lurutype").value=n;
	if (n=="0"){
		document.getElementById("luru0").style.display="block";
		document.getElementById("luru1").style.display="none";
	}
	if (n=="1"){
		document.getElementById("luru0").style.display="none";
		document.getElementById("luru1").style.display="block";
	}
}
</script>
</head>
<body scroll=yes>
<table width="700" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000" style="font-size:12px">
  <form id="form1" name="form1" method="post" action="save.asp">
    <tr>
      <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
      <td bgcolor="#FFFFFF"><a href="list.asp">返回列表</a></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">录入方式</td>
      <td bgcolor="#FFFFFF"><input type="button" name="button4" id="button4" value="有邮箱" onclick="selectlurutype(0)" />
        <input type="button" name="button5" id="button5" value="用手机" onclick="selectlurutype(1)"/>
        <input name="lurutype" type="hidden" id="lurutype" value="0" />
        <input name="addaction" type="hidden" id="addaction" value="<%=action%>" />
        <input name="id" type="hidden" id="id" value="<%=id%>" />
        <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
      <strong class="red">（有邮箱尽量用邮箱）</strong></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"></td>
      <td bgcolor="#FFFFFF">
      <div id="luru0">
      邮箱 <input name="com_email" type="text" id="com_email" value="<%=com_email%>" size="40" />
      <input type="button" name="button3" id="button3" value="搜索" onClick="searchemail(this.form)" />
      <br />
      输入客户的邮箱点击搜索即可读取客户信息
      </div>
      <div id="luru1" style="display:none">
      手机 <input type="text" name="mobile" id="mobile" value="<%=commobile%>" />
      <input type="button" name="button6" id="button6" value="搜索" onclick="searchemail(this.form)" />
      <br />
      输入客户的手机搜索即可读取客户信息
      </div>
      </td>
    </tr>
    
    <tr>
      <td align="right" bgcolor="#FFFFFF">会员类型</td>
      <td bgcolor="#FFFFFF"><input type="radio" name="membertype" id="radio" value="1" <%if membertype=1 then response.Write("checked")%>/>
        高会
      <input name="membertype" type="radio" id="radio2" value="0" <%if membertype=0 then response.Write("checked")%>/>
      普会（<strong style="color:#F00">这个要注意选择正确</strong>）</td>
    </tr>
    <tr>
    <td width="80" align="right" bgcolor="#FFFFFF"><span class="red">*</span> 公司名称</td>
    <td bgcolor="#FFFFFF">
      <input name="cname" type="text" class="input" id="cname" value="<%=comname%>" maxlength="50"/> 
      <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=com_id%>&amp;dotype=my&amp;lmcode=134" target="_blank">查看</a></td>
  </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"> <span class="red">*</span> 大类</td>
      <td bgcolor="#FFFFFF"><select name="ckeywords" id="ckeywords" onChange="selectleibie(this.value)">
                <option value="">--请选择--</option>
                      <option value="1">废金属</option>
                      <option value="2">废塑料</option>
                      <option value="3">综合</option>
                      
                </select>
                <script>selectOption("ckeywords","<%=com_keywords%>")</script>
                
                </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">小类</td>
      <td bgcolor="#FFFFFF">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none" id="leibie1">
            <tr>
              <td>必选
                <select name="js1" id="js1">
                  <option value="">金属细类</option>
                  <option value="贵金属">贵金属（金银铂钯铑等）</option>
                  <option value="稀有金属">稀有金属（钨钼钛锢等）</option>
                  <option value="有色金属">有色金属（锡铜铝锌镍等）</option>
                  <option value="钢铁">钢铁（铁钢工具钢合金钢不锈钢）</option>
                  <option value="其他类">其他类</option>
                </select>
                <script>selectOption("js1","<%=js1%>")</script>
                其他
                <select name="js2" id="js2">
                  <option value="">金属细类</option>
                  <option value="贵金属">贵金属（金银铂钯铑等）</option>
                  <option value="稀有金属">稀有金属（钨钼钛锢等）</option>
                  <option value="有色金属">有色金属（锡铜铝锌镍等）</option>
                  <option value="钢铁">钢铁（铁钢工具钢合金钢不锈钢）</option>
                  <option value="其他类">其他类</option>
                </select>
                <script>selectOption("js2","<%=js2%>")</script>
                </td>
            </tr>
          </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none" id="leibie2">
          <tr>
            <td>必选
              <select name="sl1" id="sl1">
                <option value="">塑料细类</option>
                <option value="PP">PP</option>
                <option value="PET">PET</option>
                <option value="PE">PE</option>
                <option value="ABS">ABS</option>
                <option value="PVC">PVC</option>
                <option value="PS">PS</option>
                <option value="PA">PA</option>
                <option value="PC">PC</option>
                <option value="PVB">PVB</option>
                <option value="PMMA">PMMA</option>
                <option value="EVA">EVA</option>
                <option value="PU">PU</option>
                <option value="其他类">其他类</option>
              </select>
              <script>selectOption("sl1","<%=sl1%>")</script>
             其他
              <select name="sl2" id="sl2">
                <option value="">塑料细类</option>
                <option value="PP">PP</option>
                <option value="PET">PET</option>
                <option value="PE">PE</option>
                <option value="ABS">ABS</option>
                <option value="PVC">PVC</option>
                <option value="PS">PS</option>
                <option value="PA">PA</option>
                <option value="PC">PC</option>
                <option value="PVB">PVB</option>
                <option value="PMMA">PMMA</option>
                <option value="EVA">EVA</option>
                <option value="PU">PU</option>
                <option value="其他类">其他类</option>
              </select>
              <script>selectOption("sl2","<%=sl2%>")</script>
              <script>selectleibie("<%=com_keywords%>")</script>
              </td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none" id="leibie3">
          <tr>
            <td>必选
            
              <select name="qt1" id="qt1">
                <option value="">综合细类</option>
                <option value="橡胶">橡胶</option>
                <option value="纺织">纺织</option>
                <option value="废纸">废纸</option>
                <option value="废电子电器">废电子电器</option>
                <option value="服务行业">服务行业</option>
                <option value="其他">其他</option>
              </select>
              <script>selectOption("qt1","<%=qt1%>")</script>
             其他
              <select name="qt2" id="qt2">
                <option value="">综合细类</option>
                <option value="橡胶">橡胶</option>
                <option value="纺织">纺织</option>
                <option value="废纸">废纸</option>
                <option value="废电子电器">废电子电器</option>
                <option value="服务行业">服务行业</option>
                <option value="其他">其他</option>
              </select>
              <script>selectOption("qt2","<%=qt2%>")</script>
              <script>selectleibie("<%=com_keywords%>")</script>
              </td>
          </tr>
        </table>
        </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 地区</td>
      <td bgcolor="#FFFFFF"><font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">无园区</font>
              <input type="hidden" name="province" id="province">
              
              <input type="hidden" name="cprovince" id="cprovince" value="0">
              城市
              <input name="city" type="text" id="city" size="15" onClick="getprovincename()" value="<%=city%>">
	    <script>
                            //地区及园区
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="";
                            var hyID="ckeywords";//行业ID号
                            getprovincevalue();
                        </script>
              <script>
                        function getprovincename()
                        {
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
							//alert(document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text)
							var city1=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
							if (city1=="请选择...")
							{
                            	//document.getElementById("city").value=""
							}else{
								document.getElementById("city").value=city1
							}
                        }
                        </script></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 主营业务</td>
      <td bgcolor="#FFFFFF"><textarea name="cproductslist" id="cproductslist" cols="45" rows="3" class="input"><%=cproductslist%></textarea></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 联系人</td>
      <td bgcolor="#FFFFFF">
      <input type="hidden" value="<%=comcontactp%><%=comdesi%>" id="ccontactph">
      <input name="ccontactp" type="text" id="ccontactp" maxlength="50" value="<%=comcontactp%><%=comdesi%>"/>
        <select name="select" id="select" onChange="selectsex(this.value)">
          <option value="" selected="selected"></option>
          <option value="先生">先生</option>
          <option value="女士">女士</option>
      </select></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">正确邮箱</td>
      <td bgcolor="#FFFFFF"><input type="text" name="newemail" id="newemail" value="<%=newemail%>" /></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 手机/电话</td>
      <td bgcolor="#FFFFFF"><input name="cmobile" type="text" class="input" id="cmobile" maxlength="50" value="<%=commobile%>"/></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 地址</td>
      <td bgcolor="#FFFFFF"><textarea name="cadd" id="cadd" cols="45" rows="2" class="input"><%=comadd%></textarea></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> 添加人</td>
      <td bgcolor="#FFFFFF"><input name="personname" type="text" id="personname" maxlength="50" value="<%=personname%>"/>
        <input type="hidden" name="lpersonid" id="lpersonid" value="<%=lpersonid%>" />
      提交人真实姓名</td>
    </tr>
    <%if huangye_check="1" then%>
    <tr>
      <td align="right" bgcolor="#FFFFFF">审核</td>
      <td bgcolor="#FFFFFF"><input type="radio" name="pcheck" id="pcheck" value="1" <%if pcheck="1" then response.Write("checked")%> />
        已审核
          <input type="radio" name="pcheck" id="pcheck" value="0" <%if pcheck="0" then response.Write("checked")%>/>
          未审核</td>
    </tr>
    <%end if%>
    <tr>
      <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
      <td bgcolor="#FFFFFF">
      <%if errflag=1 then
	  response.Write("<font style='color:#f00;font-weight:bold'>"&errstr&"</font>")
	  else
	  %>
      <input type="button" name="button" id="button" value="提交" onClick="return frmsubmit(this.form)" />
      <input type="reset" name="button2" id="button2" value="重置" />
      <%end if%>
        </td>
    </tr>
  </form>
</table>
<script>
selectlurutype(<%=lurutype%>)
</script>
</body>
</html>
<%

conn.close
set conn=nothing
%>
