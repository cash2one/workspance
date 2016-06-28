<%
session("myrc_lmcode")=request.QueryString("lmcode")
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
.office_sb A{
	color: #000000;
	text-decoration: none;
}
.office_sb A:hover{
	color: #000000;
	text-decoration: none;
}
.lmfont2_sb {
	font-size: 14px;
	clip: rect(auto,auto,auto,auto);
	width: 100px;
	font-weight: bold;
	color: #266BB7;
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
	document.all(lm).style.display=""
	document.all(img).src="/cn/img/fold_2.gif"
	}
else
	{
	document.all(lm).style.display="none"
    document.all(img).src="/cn/img/fold_1.gif"
	}
}
</script>


<table width="180" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="32" background="/cn/img/lm_img.gif">&nbsp;&nbsp;<img src="/cn/img/leftmanu_homeindex.gif" width="16" height="18" align="absmiddle" />&nbsp;&nbsp;<span onclick="window.location='/cn/guest_office_main.asp?lmcode=0'" class="lmfont1" style="cursor:hand"><b>MYRC首页</b></span></td>
  </tr>
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(10)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img10"/>&nbsp;&nbsp;<span class="lmtitle">发布商业机会
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="3" cellspacing="1" bgcolor="#EBEBEB" id="sub10" >
	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1001)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1001"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/guest_office_post_list.asp?lmcode=1001" ><span style="cursor:hand" class="lmfont1">供求信息</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1001" style="display:none">
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="10" height="20">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_office_post.asp?lmcode=100101"><span class="lmfont1" style="cursor:hand" >发布供求信息</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="10" height="20">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/guest_office_post_list.asp?lmcode=100102"><span class="lmfont1" style="cursor:hand" >已发供求信息</span></a></div></td>
        </tr>
		
        
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(11)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img11"/>&nbsp;&nbsp;<span class="lmtitle">查看商业往来
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="3" cellspacing="1" bgcolor="#EBEBEB" id="sub11"  >
	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1101)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1101"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/mysite/my_receiveQuestion.asp?lmcode=1101" ><span style="cursor:hand" class="lmfont1">询盘留言</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1101" style="display:none">
         
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="10" height="20">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_receiveQuestion.asp?lmcode=110101"><span class="lmfont1" style="cursor:hand" >我收到的留言</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="10" height="20">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_sendQuestion.asp?lmcode=110102"><span class="lmfont1" style="cursor:hand" >我发出的留言</span></a></div></td>
        </tr>
		
        
        <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
          <td width="10" height="20">&nbsp;</td>
          <td align="left" nowrap="nowrap"><div class="office_sb"><img src="/cn/img/open-menu.gif" width="10" height="10" />&nbsp;&nbsp;<a href="/cn/mysite/my_wasteQuestion.asp?lmcode=110103"><span class="lmfont1" style="cursor:hand" >垃圾留言</span></a></div></td>
        </tr>
		
        
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1102)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1102"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/mysite/my_customer.asp?lmcode=1102" ><span style="cursor:hand" class="lmfont1">我的客户列表</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1102" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(16)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img16"/>&nbsp;&nbsp;<span class="lmtitle">诚信档案
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="3" cellspacing="1" bgcolor="#EBEBEB" id="sub16"  style="display:none">
	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1601)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1601"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/mysite/my_listRemark.asp?lmcode=1601" ><span style="cursor:hand" class="lmfont1">客户评价</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1601" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1602)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1602"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/mysite/my_listCertificate.asp?lmcode=1602" ><span style="cursor:hand" class="lmfont1">我的证书</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1602" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1603)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1603"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/mysite/my_listReferral.asp?lmcode=1603" ><span style="cursor:hand" class="lmfont1">资信参考人</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1603" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/mysite/images/lm_img_cx.gif" onclick="pulssub(15)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img15"/>&nbsp;&nbsp;<span class="lmtitle">我的商机
	<img src="/cn/mysite/images/icon_new_29x14.gif" width="29" height="14" align="absmiddle">
	
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="3" cellspacing="1" bgcolor="#EBEBEB" id="sub15" >
	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1503)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1503"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/bizexpress/web_biz_list.asp?lmcode=1503" ><span style="cursor:hand" class="lmfont1">在线商机快递</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1503" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1501)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1501"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/bizexpress/web_biz_order.asp?lmcode=1501" ><span style="cursor:hand" class="lmfont1">订阅最新商机</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1501" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1502)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1502"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/bizexpress/web_biz_searcher.asp?lmcode=1502" ><span style="cursor:hand" class="lmfont1">订阅商机管理</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1502" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1504)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1504"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/guest_basket.asp?lmcode=1504" ><span style="cursor:hand" class="lmfont1">我的篮子</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1504" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
</table>

<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" onclick="pulssub(12)" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" id="img12"/>&nbsp;&nbsp;<span class="lmtitle">我的资料
	</span></td>
  </tr>
</table>

<table width="180" border="0" cellpadding="3" cellspacing="1" bgcolor="#EBEBEB" id="sub12">
	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1201)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1201"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/guest_office_info.asp?lmcode=1201" ><span style="cursor:hand" class="lmfont1">修改公司信息</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1201" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
  <tr>
    <td bgcolor="#FFFFFF">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="cursor:hand" onclick="pluslm(1202)">
      <tr onMouseOver="this.style.backgroundColor='#E8F5D8'" onMouseOut="this.style.backgroundColor=''">
        <td width="10" height="20" align="center"><img src="/cn/img/close-menu.gif" width="10" height="10" id="img1202"/></td>
        <td align="left"><div class="office_sb">
		
		<a href="/cn/guest_office_pass.asp?lmcode=1202" ><span style="cursor:hand" class="lmfont1">修改密码</span></a> 
		</div>
		</td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="3" id="lm1202" style="display:none">
         
      </table>
	  
    </td>
  </tr>
  	
</table>


<table width="180" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="32" background="/cn/img/lm_img.gif" style="cursor:hand">&nbsp;&nbsp;<img src="/cn/img/fold_1.gif" width="14" height="16" align="absmiddle" />&nbsp;&nbsp;<span onclick="window.location='/cn/logout.asp'" class="lmfont1" style="cursor:hand"><b>退出系统</b></span></td>
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
%>