<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
com_id=0
if request("cmobile")<>"" then
		sqlc="select * from zhanhui_list where cmobile='"&request("cmobile")&"'"
		set rsc=conn.execute(sqlc)
		if not rsc.eof or not rsc.bof then
			com_email=rsc("com_email")
			com_id=rsc("com_id")
			comname=replacequot(trim(rsc("cname")))
			comadd=replacequot(trim(rsc("cadd")))
			comprovince=replacequot(trim(rsc("province")))
			comtel=trim(rsc("ctel"))
			commobile=trim(rsc("cmobile"))
			comcontactp=replacequot(trim(rsc("ccontactp")))
			cproductslist=replacequot(trim(rsc("cproductslist")))
			com_keywords=rsc("comkeywords")
			personid=rsc("personid")
			'membertype=rsc("membertype")
			action="edit"
			'pcheck=rsc("pcheck")
		else
			sql="select top 1 * from comp_info where com_mobile='"&request("cmobile")&"'"
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				com_email=rs("com_email")
				com_id=rs("com_id")
				comname=replacequot(trim(rs("com_name")))
				comadd=replacequot(trim(rs("com_add")))
				comprovince=replacequot(trim(rs("com_province")))
				comtel=trim(rs("com_tel"))
				commobile=trim(rs("com_mobile"))
				comcontactp=replacequot(trim(rs("com_contactperson")))
				comdesi=replacequot(trim(rs("com_desi")))
				cproductslist=replacequot(trim(rs("com_productslist_en")))
				com_keywords=rs("com_keywords")
				searchno=0
			else
				searchno=1
			end if
			'membertype=0
			personid=session("personid")
			rs.close
			set rs=nothing
			action="add"
		end if
		rsc.close
		set rsc=nothing
		if commobile="" then
			commobile=request("cmobile")
		end if
	
	
end if
if personid="" then personid=session("personid")
if pcheck="" then pcheck="0"
sqlp="select realname from users where id="&personid&""
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	realname=rsp(0)
end if
rsp.close
set rsp=nothing
if request("id")<>"" then
	sqlc="select * from zhanhui_list where id='"&request("id")&"'"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		com_email=rsc("com_email")
		com_id=rsc("com_id")
		comname=replacequot(trim(rsc("cname")))
		comadd=replacequot(trim(rsc("cadd")))
		comprovince=replacequot(trim(rsc("province")))
		comtel=trim(rsc("ctel"))
		commobile=trim(rsc("cmobile"))
		comcontactp=replacequot(trim(rsc("ccontactp")))
		cproductslist=replacequot(trim(rsc("cproductslist")))
		com_keywords=rsc("comkeywords")
		personid=rsc("personid")
		'membertype=rsc("membertype")
		action="edit"
		'pcheck=rsc("pcheck")
	end if
	rsc.close
	set rsc=nothing
end if
 '--------------------------���ݵ�����ȡ�õ�������
	function getprovince(meno)
		sqlgp="select code from cate_province where meno='"&meno&"'"
		set rsgp=conn.execute(sqlgp)
		if not rsgp.eof or not rsgp.bof then
			getprovince=rsgp(0)
		end if
		rsgp.close
		set rsgp=nothing
	end function
if comprovince<>"" then
	cprovince=replacequot1(comprovince)
	Arrprovince=split(cprovince,"|")
	if ubound(Arrprovince)>0 then
		province=Arrprovince(0)
		city=Arrprovince(1)
	end if
	
	'--------------------------ֱ��ȡ�ĵ�������
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=gb2312" />
<title><%=comname%>չ��ͻ������ύ</title>
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
function searchemail(frm)
{
	frm.action="add.asp";
	document.getElementById("searchloading").innerHTML="�������������Եȡ�����";
	frm.submit();
}
function frmsubmit(frm)
{
	frm.action="save.asp"
	//if(frm.com_email.value.length<=0)
//	{
//		alert("����������!");
//		frm.com_email.focus();
//		return false;
//	}
	if(frm.cname.value.length<=0)
	{
		alert("�����빫˾����!");
		frm.cname.focus();
		return false;
	}
	getprovincename();
	if(frm.cadd.value.length<=0)
	{
		alert("�������ַ!");
		frm.cadd.focus();
		return false;
	}
	if(frm.cmobile.value.length<=0)
	{
		alert("�������ֻ�����!");
		frm.cmobile.focus();
		return false;
	}
	if(frm.ccontactp.value.length<=0)
	{
		alert("��������ϵ��!");
		frm.ccontactp.focus();
		return false;
	}
	if (frm.ckeywords.value=="")
	{
		alert("��ѡ����ҵ!");
		frm.ckeywords.focus();
		return false;
	}
	if (frm.cproductslist.value=="")
	{
		alert("����д��Ӫҵ��!");
		frm.cproductslist.focus();
		return false;
	}
	
	if (frm.province.value=="")
	{
		alert("��ѡ��ʡ��!");
		frm.province.focus();
		return false;
	}
	if (frm.city.value=="")
	{
		alert("��ѡ�����!");
		frm.city.focus();
		return false;
	}
	frm.submit();
}
</script>
</head>
<body>
<table width="600" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000" style="font-size:12px">
  <form id="form1" name="form1" method="post" action="save.asp"><div style='display:none;'><input type='hidden' id='csrfmiddlewaretoken' name='csrfmiddlewaretoken' value='ea2058828bb16b94a3f04925e82a067f' /></div>
    <tr>
      <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
      <td bgcolor="#FFFFFF"><a href="list.asp">�����б�</a></td>
    </tr>
    
    <tr>
      <td align="right" bgcolor="#FFFFFF">�ֻ�</td>
      <td bgcolor="#FFFFFF"><input name="cmobile" type="text" id="cmobile" value="<%=commobile%>" size="40" maxlength="50"/>
      <input type="button" name="button3" id="button3" value="����" onClick="searchemail(this.form)" />
      <span style="color:#F00" id="searchloading"></span>
      <input type="hidden" name="id" id="id" value="<%=request("id")%>" />
      <br />
      ����ͻ����������������ɶ�ȡ�ͻ���Ϣ</td>
    </tr>
    <%if searchno=1 then%>
    <tr>
      <td colspan="2" align="left" bgcolor="#FFFFFF"><span style="color:#F00">û���ҵ���Ӧ��˾,��¼��</span></td>
    </tr>
    <%end if%>
    <tr>
      <td width="80" align="right" bgcolor="#FFFFFF"><span class="red">*</span> ��˾����</td>
      <td bgcolor="#FFFFFF">
        <input name="cname" type="text" class="input" id="cname" value="<%=comname%>" maxlength="50"/> 
      <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=com_id%>&amp;dotype=my&amp;lmcode=134" target="_blank">�鿴</a></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"> <span class="red">*</span> ��ҵ</td>
      <td bgcolor="#FFFFFF"><select name="ckeywords" id="ckeywords">
                <option value="">--��ѡ��--</option>
                      <option value="1">�Ͻ���</option>
                      <option value="2">������</option>
                      <option value="3">�Ͼ���̥�����</option>
                      <option value="4">�Ϸ�֯Ʒ���Ƥ��</option>
                      <option value="5">��ֽ</option>
                      <option value="6">�ϵ��ӵ���</option>
                      <option value="10">�ϲ���</option>
                      <option value="12">�Ͼɶ����豸</option>
                      <option value="14">��������</option>
                      <option value="15">����</option>
                </select>
                <script>selectOption("ckeywords","<%=com_keywords%>")</script>
                </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> ����</td>
      <td bgcolor="#FFFFFF"><font id="mainprovince1"></font><font id="maincity1"></font><font id="mainGarden">��԰��</font>
              <input type="hidden" name="province" id="province">
              
              <input type="hidden" name="cprovince" id="cprovince" value="0">
              ����
              <input name="city" type="text" id="city" size="15" onClick="getprovincename()">
	    <script>
                            //������԰��
                            var Fstyle="";
                            var selectname1="province1";
                            var selectname2="city1";
                            var selectname3="Garden";
                            var Fvalue1="<%=provinceID%>";
                            var Fvalue2="<%=cityID%>";
                            var Fvalue3="<%=GardenID%>";
                            var hyID="ckeywords";//��ҵID��
                            getprovincevalue();
                        </script>
              <script>
                        function getprovincename()
                        {
							
                            document.getElementById("province").value=document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text
							//alert(document.getElementById("province1").options[document.getElementById("province1").selectedIndex].text)
							var city1=document.getElementById("city1").options[document.getElementById("city1").selectedIndex].text
							if (city1=="��ѡ��...")
							{
                            	//document.getElementById("city").value=""
							}else{
								document.getElementById("city").value=city1
							}
                        }
                        </script></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> ��Ӫҵ��</td>
      <td bgcolor="#FFFFFF"><textarea name="cproductslist" id="cproductslist" cols="45" rows="3" class="input"><%=cproductslist%></textarea></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">����</td>
      <td bgcolor="#FFFFFF"><strong>
        <input name="com_email" type="text" id="com_email" value="<%=com_email%>" size="40" />
      </strong>
        <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" /></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> ��ϵ��</td>
      <td bgcolor="#FFFFFF"><input name="ccontactp" type="text" id="ccontactp" maxlength="50" value="<%=comcontactp%><%=comdesi%>"/>
      </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">�绰</td>
      <td bgcolor="#FFFFFF"><input name="ctel" type="text" class="input" id="ctel" maxlength="50" value="<%=comtel%>"/></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> ��ַ</td>
      <td bgcolor="#FFFFFF"><textarea name="cadd" id="cadd" cols="45" rows="2" class="input"><%=comadd%></textarea></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><span class="red">*</span> �����</td>
      <td bgcolor="#FFFFFF"><input name="personname" type="text" id="personname" maxlength="50" value="<%=realname%>"/>
        <input type="hidden" name="personid" id="personid" value="<%=personid%>" />
      �ύ����ʵ����</td>
    </tr>
   
    <tr>
      <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
      <td bgcolor="#FFFFFF"><input type="button" name="button" id="button" value="�ύ" onClick="return frmsubmit(this.form)" />
        <label>
          <input type="reset" name="button2" id="button2" value="����" />
        </label></td>
    </tr>
  </form>
</table>

</body>
</html>
<%

conn.close
set conn=nothing
%>
