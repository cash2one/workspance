<%
'session("myrc_lmcode")=request.QueryString("lmcode")
disp="style='display:none'"
%>

<style type="text/css">
<!--
.STYLE1le {color: #FF6600}
.lmtitle {
	font-size: 14px;
	font-weight: bold;
}
.lmfont1 {
	font-size: 14px;
	clip: rect(auto,auto,auto,auto);
	width: 100px;
}
.lmfont2 {
	font-size: 14px;
	clip: rect(auto,auto,auto,auto);
	width: 100px;
	font-weight: bold;
	color: #009900;
}
.office_sb{
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #9DC86D;
	height: 25px;
	line-height: 25px;
	padding-right: 4px;
	padding-left: 4px;
}

.lmfont2_sb {
	font-size: 14px;
	clip: rect(auto,auto,auto,auto);
	width: 100px;
	font-weight: bold;
	color: #266BB7;
}
.doubleline {
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #9DC86D;
	background-color: #E3F5D8;
	padding-left: 5px;
}
.office_focus {
	border-top-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #88C967;
	border-bottom-color: #88C967;
	border-left-color: #88C967;
	background-color: #FFFFFF;
	padding: 4px;
}
.leftlmtop {
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #9DC86D;
	padding-top: 4px;
}
-->
</style>
<script>
function pluslm(id){
//alert (id)
lm="lm"+id
img="img"+id
//alert (document.all(lm).style.display)
if (document.all(lm).style.display=="none")
	{
	document.all(lm).style.display=""
	document.all(img).src="/cn/img/open-menu.gif"
	}
else
	{
	document.all(lm).style.display="none"
    document.all(img).src="/cn/img/close-menu.gif"
	}
}
function pulssub(id){
//alert (id)
lm="sub"+id
img="img"+id
//alert (document.all(lm).style.display)
if (document.all(lm).style.display=="none")
	{
	if (id==10){
	document.all(lm).style.display="";
	document.all(img).src="/cn/img/my2.gif";
	}else
	{
	document.all(lm).style.display="";
	document.all(img).src="/cn/img/fold_2.gif";
	}
	}
else
	{
	if(id==10){
	document.all(lm).style.display="none";
    document.all(img).src="/cn/img/my1.gif";
	}
	else
	{
	document.all(lm).style.display="none";
    document.all(img).src="/cn/img/fold_1.gif";
	}
	}
}
function getfocus(code)
{
var lmvalue="focuslm"+code
document.all(lmvalue).className="office_focus"
}
</script>


<table width="180" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="32" background="/cn/img/lm_img.gif">&nbsp;&nbsp;<img src="/cn/img/leftmanu_homeindex.gif" width="16" height="18" align="absmiddle" />&nbsp;&nbsp;<span onclick="window.location='/cn/guest_office_main.asp?lmcode=0'" class="lmfont1" style="cursor:hand"><b>MYRC��ҳ</b></span></td>
  </tr>
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(10)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/my1.gif" width="16" height="16" align="absmiddle" id="img10"/>&nbsp;&nbsp;<span class="lmtitle"><font color="#ff6600">������ҵ����</font>
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub10" <%response.Write(disp)%>>
	
  <tr>
    <td>
	
	<div class="leftlmtop"></div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1001)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1001"/></td>
        <td align="left"><div class="office_sb">
		
		<span style="cursor:hand" class="lmfont1">������Ϣ</span>
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" id="lm1001" <%response.Write(disp)%>>
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm100101"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_office_post.asp?lmcode=100101"><span class="lmfont1" style="cursor:hand" >����������Ϣ</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm100102"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_office_post_list.asp?lmcode=100102"><span class="lmfont1" style="cursor:hand" >�ѷ�������Ϣ</span></a></div></td>
        </tr>
		
        
      </table>
	    <%
		if session("vipflag")>=1 and session("vipcheck")=1 then
		else
		%> 
		<div class="leftlmtop"></div>
		<%
		end if
		%>
    </td>
  </tr>
<%
	if session("vipflag")>=1 and session("vipcheck")=1 then
		proshow=1
	else
		sqlcls="select com_id from sb_com_cls where com_id="&session("log_comid")
		set rscl=conn.execute(sqlcls)
		if rscl.eof then
			proshow=0
		else
			proshow=1
		end if
		rscl.close
		set rscl=nothing
	end if
if proshow=1 then
%>  	
  <tr>
    <td>

	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1002)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1002"/></td>
        <td align="left"><div class="office_sb">
		
		<span style="cursor:hand" class="lmfont1">��ƷĿ¼</span>
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" id="lm1002" <%response.Write(disp)%>>
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm100201"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_pro_post.asp?lmcode=100201"><span class="lmfont1" style="cursor:hand" >������Ʒ </span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm100202"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_pro_list.asp?lmcode=100202"><span class="lmfont1" style="cursor:hand" >�����Ʒ</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm100203"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_pro_group.asp?lmcode=100203"><span class="lmfont1" style="cursor:hand" >�����Ʒϵ�� </span></a></div></td>
        </tr>
		
        
      </table>

	 <div class="leftlmtop"></div> 
    </td>
  </tr>
<%
end if
%>  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(11)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img11"/>&nbsp;&nbsp;<span class="lmtitle">�鿴��ҵ����
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub11"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1101)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1101"/></td>
        <td align="left"><div class="office_sb">
		
		<span style="cursor:hand" class="lmfont1">ѯ������</span>
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" id="lm1101" <%response.Write(disp)%>>
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm110101"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_receiveQuestion.asp?lmcode=110101"><span class="lmfont1" style="cursor:hand" >���յ�������</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm110102"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_sendQuestion.asp?lmcode=110102"><span class="lmfont1" style="cursor:hand" >�ҷ���������</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm110103"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_wasteQuestion.asp?lmcode=110103"><span class="lmfont1" style="cursor:hand" >��������</span></a></div></td>
        </tr>
		
        
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1102)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1102"/></td>
        <td align="left"><div class="office_sb" id="focuslm1102">
		
		<a href="/cn/mysite/my_customer.asp?lmcode=1102" ><span style="cursor:hand" class="lmfont1">�ҵĿͻ��б�</span></a> 
		</div>
		</td>
      </tr>
    </table>
	<div class="leftlmtop"></div> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1102" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(16)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img16"/>&nbsp;&nbsp;<span class="lmtitle">���ŵ���
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub16"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1601)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1601"/></td>
        <td align="left"><div class="office_sb" id="focuslm1601">
		
		<a href="/cn/mysite/my_listRemark.asp?lmcode=1601" ><span style="cursor:hand" class="lmfont1">�ͻ�����</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1601" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1602)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1602"/></td>
        <td align="left"><div class="office_sb" id="focuslm1602">
		
		<a href="/cn/mysite/my_listCertificate.asp?lmcode=1602" ><span style="cursor:hand" class="lmfont1">�ҵ�֤��</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1602" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1603)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1603"/></td>
        <td align="left"><div class="office_sb" id="focuslm1603">
		
		<a href="/cn/mysite/my_listReferral.asp?lmcode=1603" ><span style="cursor:hand" class="lmfont1">���Ųο���</span></a> 
		</div>
		</td>
      </tr>
    </table>
	<div class="leftlmtop"></div> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1603" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/mysite/images/lm_img_cx.gif" onclick="pulssub(15)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img15"/>&nbsp;&nbsp;<span class="lmtitle">�ҵ��̻�
	<img src="/cn/mysite/images/icon_new_29x14.gif" width="29" height="14" align="absmiddle">
	
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub15"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1503)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1503"/></td>
        <td align="left"><div class="office_sb" id="focuslm1503">
		
		<a href="/cn/bizexpress/web_biz_list.asp?lmcode=1503" ><span style="cursor:hand" class="lmfont1">�����̻����</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1503" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1501)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1501"/></td>
        <td align="left"><div class="office_sb" id="focuslm1501">
		
		<a href="/cn/bizexpress/web_biz_order.asp?lmcode=1501" ><span style="cursor:hand" class="lmfont1">���������̻�</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1501" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1502)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1502"/></td>
        <td align="left"><div class="office_sb" id="focuslm1502">
		
		<a href="/cn/bizexpress/web_biz_searcher.asp?lmcode=1502" ><span style="cursor:hand" class="lmfont1">�����̻�����</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1502" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1504)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1504"/></td>
        <td align="left"><div class="office_sb" id="focuslm1504">
		
		<a href="/cn/guest_basket.asp?lmcode=1504" ><span style="cursor:hand" class="lmfont1">�ҵ�����</span></a> 
		</div>
		</td>
      </tr>
    </table>
	<div class="leftlmtop"></div> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1504" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(12)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img12"/>&nbsp;&nbsp;<span class="lmtitle">�ҵ�����
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub12"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1201)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1201"/></td>
        <td align="left"><div class="office_sb" id="focuslm1201">
		
		<a href="/cn/guest_office_info.asp?lmcode=1201" ><span style="cursor:hand" class="lmfont1">�޸Ĺ�˾��Ϣ</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1201" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
<%
if session("vipflag")>=1 and session("vipcheck")=1 then
%>   	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1203)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1203"/></td>
        <td align="left"><div class="office_sb" id="focuslm1203">
		
		<a href="/cn/mysite/my_company.asp?lmcode=1203" ><span style="cursor:hand" class="lmfont1">��ҵ��Ƭ</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1203" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
<%
end if
%> 	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1202)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1202"/></td>
        <td align="left"><div class="office_sb" id="focuslm1202">
		
		<a href="/cn/guest_office_pass.asp?lmcode=1202" ><span style="cursor:hand" class="lmfont1">�޸�����</span></a> 
		</div>
		</td>
      </tr>
    </table>
	<div class="leftlmtop"></div> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1202" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>
<%
if session("vipflag")>=1 and session("vipcheck")=1 then
%>  
<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(13)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img13"/>&nbsp;&nbsp;<span class="lmtitle">�������в�
	</span></td>
  </tr>
</table>
<%
end if
%>
<%
if session("vipflag")>=1 and session("vipcheck")=1 then
%> 
<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub13"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1301)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1301"/></td>
        <td align="left"><div class="office_sb">
		
		<span style="cursor:hand" class="lmfont1">���в���� </span>
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" id="lm1301" <%response.Write(disp)%>>
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm130101"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_styleConfig.asp?lmcode=130101"><span class="lmfont1" style="cursor:hand" >���в����</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm130102"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_titleConfig.asp?lmcode=130102"><span class="lmfont1" style="cursor:hand" >�������� </span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="15" height="25">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb" id="focuslm130103"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_customset.asp?lmcode=130103"><span class="lmfont1" style="cursor:hand" >���в���ҳ���� </span></a></div></td>
        </tr>
		
        
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td>
	
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1302)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1302"/></td>
        <td align="left"><div class="office_sb" id="focuslm1302">
		
		<a href="/cn/mysite/my_Recommendview.asp?lmcode=1302" ><span style="cursor:hand" class="lmfont1">�������� </span></a> 
		</div>
		</td>
      </tr>
    </table>
	<div class="leftlmtop"></div> 
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1302" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>
<%
end if
%>
<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(14)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img14"/>&nbsp;&nbsp;<span class="lmtitle">����
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB" class="doubleline" id="sub14"  <%response.Write(disp)%>>
	
  <tr>
    <td>
	<div class="leftlmtop"></div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1401)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1401"/></td>
        <td align="left"><div class="office_sb" id="focuslm1401">
		
		<a href="http://www.zz91.com/cn/helpcenter/index.asp" target="_blank" ><span style="cursor:hand" class="lmfont1">����ָ��</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1401" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
 	
  <tr>
    <td>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onclick="pluslm(1403)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="15" height="25" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1403"/></td>
        <td align="left"><div class="office_sb" id="focuslm1403">
		
		<a href="http://www.zz91.com/cn/contactus.asp" target="_blank" ><span style="cursor:hand" class="lmfont1">�ҵ����ʷ���</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1403" <%response.Write(disp)%>>
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" />&nbsp;&nbsp;<span onclick="window.location='/cn/logout.asp'" class="lmfont1" style="cursor:hand"><b>�˳�ϵͳ</b></span></td>
  </tr>
</table>


<%
select case left(session("myrc_lmcode"),4)
case "1001"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1002"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1101"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1102"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1201"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1202"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1203"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1301"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1302"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1501"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1502"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1503"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1504"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1601"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1602"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")
case "1603"
response.Write("<script>pulssub("&left(session("myrc_lmcode"),2)&");pluslm("&left(session("myrc_lmcode"),4)&")</script>")

end select
if session("myrc_lmcode")<>"0" then
response.Write("<script>getfocus("&session("myrc_lmcode")&")</script>")
end if
%>