<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<%
actionlist=request.QueryString("action")
if actionlist="del" then
	sid=request.QueryString("sid")
	if sid<>"" then
	sql="delete from seo_keywordslist where sid="&sid&""
	conn.execute(sql)
	sql="delete from seo_list where id="&sid&""
	conn.execute(sql)
	end if
end if
selectcb=request("selectcb")
 dostay=request("dostay")
 doflag=request("doflag")
 topersonid=request("topersonid")
 dotype=request("dotype")
if selectcb<>"" and dostay<>"" then
	response.Redirect("list_save.asp?selectcb="&request("selectcb")&"&dostay="&request("dostay")&"&dotype="&dotype&"&personid="&request("personid")&"&userName="&request("userName")&"&doflag="&request("doflag"))
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="/cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="/admin1/main.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/list.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/jquery-1.8.0.min.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
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
<script>
$(document).ready(function(){
  $(".gengxin").click(function(){
							   
	var self = $(this);
	
	var jsonvalue=$.parseJSON(self.attr("value"));
	var kaction=jsonvalue.action;
	var id=jsonvalue.id;
	var check_url=jsonvalue.msb;
	var k=jsonvalue.name
	
	if (kaction=="check_pai"){
		var ranking=$("#ranking"+id.toString())
	}
	if (kaction=="baidu_sl"){
		var ranking=$("#baidusl"+id.toString())
	}
	if (kaction=="baidu_kz"){
		var ranking=$("#baidukz"+id.toString())
	}
	if (kaction=="baidu_fl"){
		var ranking=$("#baidufl"+id.toString())
	}
	var loading=function()
	{
		ranking.html("<img src='/images/loading_16x16.gif' border=0>");
	};
	var Response=function(data)
	{

		ranking.html(data);
		
	};
	if (kaction=="check_pai"){
		ajaxurl="http://192.168.2.21/zqweb/?check_url="+UTF8UrlEncode(check_url)+"&k="+UTF8UrlEncode(k)+"&id="+id+"";
		
	    $.getScript(ajaxurl, function() {
			var result = _suggest_result_.baiduranking;
			//alert(result)
			ranking.html(result);
									  });
		ranking.html("<img src='/images/loading_16x16.gif' border=0>");
			
		}else{
             var htmlobj=$.ajax(
					   {
						   url:"get_baidukeyGB.asp?action="+kaction+"&k="+UTF8UrlEncode(k)+"&check_url="+UTF8UrlEncode(check_url)+"&id="+id+"",
						   beforeSend:loading,
						   success:Response,
						   type:"GET",
						   cache:false,
						   contentType: "application/x-www-form-urlencoded;charset=gb2312"
						});
			 //window.open("get_baidukeyGB.asp?action="+kaction+"&k="+UTF8UrlEncode(k)+"&check_url="+UTF8UrlEncode(check_url)+"&id="+id+"","","")
    //ranking.html("get_baidukeyGB.asp?action="+kaction+"&k="+k+"&check_url="+check_url+"&id="+id+"");
	//ranking.html("更新");
		}
  });
});
function UTF8UrlEncode(input){    
        var output = "";    
        var currentChar = '';    
        for(var counter = 0; counter < input.length; counter++){    
            currentChar = input.charCodeAt(counter);    
            if((0 <= currentChar) && (currentChar <= 127))    
                output = output + UTF8UrlEncodeChar(currentChar);    
            else   
                output = output + encodeURIComponent(input.charAt(counter));    
        }    
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
} 
function UTF8UrlEncodeChar(input){    
        if(input <= 0x7F) return "%" + input.toString(16);    
        var leadByte = 0xFF80;    
        var hexString = "";    
        var leadByteSpace = 5;    
        while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
            hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
            leadByte = (leadByte >> 1);    
            leadByteSpace--;    
            input = input >> 6;    
        }    
        return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
} 
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
	var e = form.elements[i];
	if (e.name.substr(0,3)=='cbb')
	   e.checked = form.cball.checked;
	}
}
///////////////////////////////////
function postAll(form,promptText,value)
{
	selectcb="0"
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
		if (e.checked==true)
		{
			var selectcb=selectcb+","+e.value
		}
	}

	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
		alert ("选择你要的信息！")
		return false
	}
	else
	{
		if (confirm(promptText))
		{
			form.dostay.value=value
			form.submit()
		}
	}
}
</script>
<link href="s.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%
sqluser="select ywadminid,adminuserid,Partuserid from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
ywadminid=rsuser("ywadminid")
adminuserid=rsuser("adminuserid")
Partuserid=rsuser("Partuserid")
rsuser.close
set rsuser=nothing
'response.Write(Partuserid)
sql=""
sear="n="
if request("com_email")<>"" then
	sql=sql&" and com_email like '%"&request("com_email")&"%'"
	sear=sear&"&com_email="&request("com_email")
end if
if request("keywords")<>"" then
	sql=sql&" and exists(select sid from seo_keywordslist where keywords like '%"&request("keywords")&"%' and sid=v_seolist.id)"
	sear=sear&"&keywords="&request("keywords")
end if
if request("dbtype")<>"" then
	sql=sql&" and dbflag="&request("dbtype")&""
	sear=sear&"&dbtype="&request("dbtype")
end if
if request("seo_start")<>"" then
	sql=sql&" and seo_start>='"&request("seo_start")&"'"
	sear=sear&"&seo_start="&request("seo_start")
end if
if request("com_msb")<>"" then
	sql=sql&" and com_msb like '%"&request("com_msb")&"%'"
	sear=sear&"&com_msb="&request("com_msb")
end if
if request("target_assure")<>"" then
	sql=sql&" and target_assure='"&request("target_assure")&"'"
	sear=sear&"&target_assure="&request("target_assure")
end if
if request("seoperson")<>"" then
	sql=sql&" and personid="&request("seoperson")&""
	sear=sear&"&seoperson="&request("seoperson")
end if
if request("waste")<>"" then
	sql=sql&" and waste="&request("waste")&""
	sear=sear&"&waste="&request("waste")
end if
if request("expire")="0" then
	sql=sql&" and not exists(select sid from seo_keywordslist where expire_time<'"&date()&"' and expire_time>'1900-1-1' and sid=v_seolist.id)"
	sear=sear&"&expire="&request("expire")
end if
if request("expire")="1" then
	sql=sql&" and exists(select sid from seo_keywordslist where expire_time<'"&date()&"' and expire_time>'1900-1-1' and sid=v_seolist.id)"
	sear=sear&"&expire="&request("expire")
end if
if session("userid")="10" or session("userid")="13" or Partuserid="4204" or left(session("userid"),2)="13" or left(session("userid"),2)="24" then
else
	sql=sql&" and personid="&session("personid")&""
end if
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "v_seolist"
	 .sqlOrder= "order by seo_start desc"
	 .sqlWhere= "WHERE  id>0 "&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 15
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=cint(total/15)
   if total/15 > totalpg then
	  totalpg=totalpg+1
   end if
%>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" align="center" <%if request("waste")="" and request("expire")<>"1" then response.Write(" bgcolor='#CCCC00'")%>><a href="?waste=">所有客户</a></td>
    <td width="100" align="center" <%if request("waste")="0" then response.Write(" bgcolor='#CCCC00'")%>><a href="?waste=0&expire=0">在线客户</a></td>
    <td width="100" align="center" <%if request("waste")="1" then response.Write(" bgcolor='#CCCC00'")%>><a href="?waste=1">丢单客户</a></td>
    <td width="100" align="center" <%if request("expire")="1" then response.Write(" bgcolor='#CCCC00'")%>><a href="?expire=1">过期客户</a></td>
  </tr>
</table>
<form id="form1" name="form1" method="post" action="list.asp"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center"><input type="button" name="button" id="button" value="添加客户" onClick="window.location='add.asp'" /></td>
  </tr>
  <tr>
    <td height="30">邮箱：
      
        <input type="text" name="com_email" id="com_email" />
        关键字
      <input type="text" name="keywords" id="keywords" />
      开始优化时间<script language=javascript>createDatePicker("seo_start",true,"",false,true,true,true)</script> 保证达标时间<script language=javascript>createDatePicker("target_assure",true,"",false,true,true,true)</script>
      达标时间<script language=javascript>createDatePicker("target_time",true,"",false,true,true,true)</script></td>
  </tr>
  <tr>
    <td>门市部
      <input type="text" name="com_msb" id="com_msb" />
      <select name="dbtype" id="dbtype">
        <option value="">请选择达标情况...</option>
        <option value="0">未达标</option>
        <option value="1">已达标</option>
      </select>
      <script>selectOption("dbtype","<%=request("dbtype")%>")</script>
      <select name="seoperson" id="seoperson">
      <option value="">选择SEO人员</option>
      <%
	  sqla="select realname,id from users where userid='4204' and closeflag=1"
	  set rsa=conn.execute(sqla)
	  if not rsa.eof or not rsa.bof then
	  while not rsa.eof
	  %>
        <option value="<%=rsa("id")%>"><%=rsa("realname")%></option>
      <%
	  rsa.movenext
	  wend
	  
	  end if
	  rsa.close
	  set rsa=nothing
	  %>
      </select>
      
      <script>selectOption("seoperson","<%=request("seoperson")%>")</script>
      <input type="submit" name="button2" id="button2" value="搜索" />
      <input type="hidden" name="waste" value="<%=request("waste")%>">
      </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</form>

<form id="form2" name="form2" method="post" action="list_save.asp">
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#333333">
  <tr>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">&nbsp;</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">邮箱</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">公司名</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">关键词[一键更新]</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">保证达标时间</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">开始优化时间</td>
    <!--<td align="center" nowrap="nowrap" bgcolor="#ebebeb">达标时间</td>-->
    <!--<td align="center" nowrap="nowrap" bgcolor="#ebebeb">过期时间</td>-->
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">门市部</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">购买金额</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">收录量</td>
    <!--<td align="center" nowrap="nowrap" bgcolor="#ebebeb">快照更新</td>-->
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">反链</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">优化人</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">销售</td>
    <td align="center" nowrap="nowrap" bgcolor="#ebebeb">操作</td>
  </tr>
  <%
  if not rs.eof  then 
  While Not rs.EOF
  
    target_assure=rs("target_assure")
	if target_assure="1900-1-1" then
		target_assure=""
	end if
	seo_start=rs("seo_start")
	if seo_start="1900-1-1" then
		seo_start=""
	end if
  %>
  
  <tr>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
      <input name="cbb" id="cbb<%=rs("id")%>" type="checkbox" value="<%=rs("id")%>">
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><a href="http://admin1949.zz91.com/web/zz91/crm/company/adminmyrc.htm?account=<%=rs("com_email")%>" target="_blank"><%=rs("com_email")%></a></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a>
    
    <br>
    <a href="seo_dolist.asp?sid=<%=rs("id")%>&com_id=<%=rs("com_id")%>" target="_blank">SEO小计</a>
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    
    <table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#999999">
      <tr>
        <td width="150" nowrap="nowrap" bgcolor="#f2f2f2">key</td>
        <td nowrap="nowrap" bgcolor="#f2f2f2">百度排名</td>
        <td nowrap="nowrap" bgcolor="#f2f2f2">达标要求</td>
        <!--<td nowrap="nowrap" bgcolor="#f2f2f2">开始优化时间</td>-->
        <td nowrap="nowrap" bgcolor="#f2f2f2">过期时间</td>
        <td nowrap="nowrap" bgcolor="#f2f2f2">购买金额</td>
        <!--<td nowrap="nowrap" bgcolor="#f2f2f2">保证达标时间</td>-->
        <td nowrap="nowrap" bgcolor="#f2f2f2">历史</td>
        </tr>
      <%
	sqlk="select keywords,baidu_ranking,id,target_require,seo_start,expire_time,price,target_time from seo_keywordslist where sid="&rs("id")&""
	set rsk=conn.execute(sqlk)
	if not rsk.eof or not rsk.bof then
	while not rsk.eof
	expire_time1=rsk("expire_time")
	if expire_time1="1900-1-1" then
		expire_time1=""
	end if
	%>
      <tr>
        <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="http://www.baidu.com/s?wd=<%=server.URLEncode(rsk("keywords"))%>&ie=gb2312" target="_blank"><%=rsk("keywords")%></a></td>
        <td nowrap="nowrap" bgcolor="#FFFFFF"><div id="ranking<%=rsk("id")%>"><%=rsk("baidu_ranking")%></div><div class="gengxin" value='{"action":"check_pai","id":"<%=rsk("id")%>","msb":"<%=rs("com_msb")%>","name":"<%=trim(rsk("keywords"))%>"}' ><a href="javascript:void(0)">更新</a></div></td>
        <td nowrap="nowrap" bgcolor="#FFFFFF">第<%=rsk("target_require")%>页</td>
        <!--<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rsk("seo_start")%></td>-->
        <td nowrap="nowrap" bgcolor="#FFFFFF"><%=expire_time%></td>
        <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rsk("price")%></td>
        <!--<td nowrap="nowrap" bgcolor="#FFFFFF"><%=rsk("target_time")%></td>-->
        <td nowrap="nowrap" bgcolor="#FFFFFF">[<a href="k_history.asp?sid=<%=rs("id")%>&kid=<%=rsk("id")%>" target="_blank">历史</a>]</td>
      </tr>
    <%
	rsk.movenext
	wend
	end if
	rsk.close
	set rsk=nothing
	%>
    </table>
    
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><%=target_assure%></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><%=seo_start%></td>
    <!--<td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("target_time")%></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("expire_time")%></td>-->
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><a href="http://<%=rs("com_msb")%>" target="_blank"><%=rs("com_msb")%></a></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("price")%></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <a href="http://www.baidu.com/s?wd=site:<%=rs("com_msb")%>&ie=gb2312" target="_blank"><div id="baidusl<%=rs("id")%>"><%=rs("baidu_sl")%></div></a>
    <div class="gengxin" value='{"action":"baidu_sl","id":"<%=rs("id")%>","msb":"<%=rs("com_msb")%>","name":""}'><a href="javascript:void(0)">更新</a></div>
    </td>
    <!--<td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <a href="http://www.baidu.com/s?wd=<%=rs("com_msb")%>&ie=gb2312" target="_blank"><div id="baidukz<%=rs("id")%>"><%=rs("baidu_kuaizhao")%></div></a>
    <div class="gengxin" value='{"action":"baidu_kz","id":"<%=rs("id")%>","msb":"<%=rs("com_msb")%>","name":""}'><a href="javascript:void(0)">更新</a></div>
    </td>-->
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <a href="http://www.baidu.com/s?wd=domain:<%=rs("com_msb")%>&ie=gb2312" target="_blank"><div id="baidufl<%=rs("id")%>"><%=rs("baidu_fanlian")%></div></a>
    <div class="gengxin" value='{"action":"baidu_fl","id":"<%=rs("id")%>","msb":"<%=rs("com_msb")%>","name":""}'><a href="javascript:void(0)">更新</a></div>
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <%
	if rs("personid")<>"" and not isnull(rs("personid")) then
	sqlp="select realname from users where id="&rs("personid")&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		response.Write(rsp(0))
	end if
	rsp.close
	set rsp=nothing
	end if
	%>
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF">
    <%
	sqlu="select a.realname from users as a left join Crm_AssignVap as b on a.id=b.personid  where b.com_id="&rs("com_id")&""
	set rsu=conn.execute(sqlu)
	if not rsu.bof or not rsu.eof then
	response.Write(rsu("realname"))
	end if 
	rsu.close
	set rsu=nothing
	%>
    </td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><a href="?action=del&sid=<%=rs("id")%>" onClick="return confirm('确实要删除吗？')">删除</a> | <a href="edit.asp?id=<%=rs("id")%>" target="_blank">修改</a></td>
  </tr>
  <%
  rs.movenext
  wend
 END IF
 rs.close
 set rs=nothing
  %>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)"> 
    全选
    <input type="button" name="button4" id="button4" value="分配给" onClick="postAll(this.form,'确实要分配吗?','assignto')">
    <select name="topersonid" id="topersonid">
        <option value="">请选择...</option>
        <%
        sql="select realname,id from users where userid='4204' and closeflag=1"
        set rs=conn.execute(sql)
        if not rs.eof then
        while not rs.eof
        %>
      <option value="<%=rs("id")%>"><%=rs("realname")%></option>
      <%
        rs.movenext
        wend
        end if
        rs.close
        set rs=nothing
        %>
    </select>
    <input type="button" name="button3" id="button3" value="放入对单库" onClick="postAll(this.form,'确实要放到丢单库里吗?','waste')" />
    <input type="button" name="button3" id="button3" value="取消放入对单库" onClick="postAll(this.form,'确实要取消放到丢单库里吗?','nowaste')" />
    </td>
  </tr>
</table>
<input type="hidden" name="dostay">
<input type="hidden" name="selectcb">
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
