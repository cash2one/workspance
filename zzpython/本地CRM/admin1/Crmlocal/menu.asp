<!-- #include file="../include/ad!$#5V.asp" -->
<!--#include file="../include/include.asp"-->
<%
 sql="select * from user_qx where uname='"&session("personid")&"'"
 set rs=server.CreateObject("adodb.recordset")
 rs.open sql,conn,1,1
 if not rs.eof then
 qx=rs("qx")
 end if
 rs.close()
 set rs=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../default.css" rel="stylesheet" type="text/css">
<SCRIPT src="../inc/DTree/dtree.js"></SCRIPT>

<style type=text/css>
body  {
	background:#F7F7EF;
	margin:0px;
	font-family: Verdana, Arial, sans-serif, 宋体;
	font-size: 9pt;
	text-decoration: none;
	color:#555555;
	SCROLLBAR-FACE-COLOR: #50846E;
	SCROLLBAR-HIGHLIGHT-COLOR: #B4D5A4;
	SCROLLBAR-SHADOW-COLOR: #B4D5A4;
	SCROLLBAR-3DLIGHT-COLOR: #B4D5A4;
	SCROLLBAR-ARROW-COLOR: #97C37E;
	SCROLLBAR-TRACK-COLOR: #97C37E;
	SCROLLBAR-DARKSHADOW-COLOR: #4D7F6A;
	background-color: #F1F8ED;
}
table  { border:0px; }
td  { font:normal 12px; }
img  { vertical-align:bottom; border:0px; }
a  {
	color:#ffffff;
	text-decoration:none;
	font-size: 12px;
	font-style: normal;
	font-weight: normal;
	font-variant: normal;
}
a:hover  { color:#ffffff;text-decoration:underline; }
.sec_menu  { border-left:1px solid white; border-right:1px solid white; border-bottom:1px solid white; overflow:hidden; background:#eeeeee; }
.menu_title  { }
.menu_title span  { position:relative; top:0px; left:0px; color:#000000; font-weight:bold}
.menu_title2  { }
.menu_title2 span  { position:relative; top:0px; left:0px; color:#000000; font-weight:bold}
.button02-out {
	font-size: 12px;
	color: #000000;
	width: 80px;
	height: 20px;
	background-image: url(../Images/Skin/But02_out.gif);
	border: none;
	padding-top: 3px;
}
</style>
<link rel="StyleSheet" href="../Inc/DTree/dtree.css" type="text/css">
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="181" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width=100% border="0" align=center cellpadding=0 cellspacing=0>
      <tr>
        <td height=23 background="../newimages/menu_fill.gif" bgcolor="#F7F7EF" class=menu_title id=menuTitle0 onmouseover=this.className='menu_title2'; onmouseout=this.className='menu_title';>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><a href="../main.asp" target="main"><img src="../newimages/menu_k3.gif" width="19" height="17" border="0"></a><a href="../Loginout.asp?Action=Logout"><img src="../newimages/ico_logout.gif" width="20" height="20" border="0"></a></td>
              <td align="right">&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
	<!--循环开始-->
	<%
    sqlw="select * from cate_qx where code like '__00' and closeflag=1 order by ord asc"
	set rsw=server.CreateObject("adodb.recordset")
	rsw.open sqlw,conn,1,3
	if not rsw.eof or not rsw.bof then
	i=1
	n=1
	do while not rsw.eof
	b=0
	if qx<>"" then
	mqx=split(qx,",",-1,1)
		for jj=0 to ubound(mqx)
			if left(rsw("code"),2)=left(trim(mqx(jj)),2) then
			b=1
			end if
		next
	end if
	if b=1 then
	%>
      <table width=100% border="0" align=center cellpadding=0 cellspacing=0>
        <tr>
          <td height=23 background="../newimages/left_dd.gif" class=menu_title id=menuTitle<%=rsw("code")%> style="cursor:hand;" onClick="showsubmenu(<%=rsw("code")%>)" onmouseover=this.className='menu_title2'; onmouseout=this.className='menu_title';>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="27" align="left">&nbsp;</td>
                <td> <span><%=rsw("meno")%></span></td>
                <td width="14">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td id='submenu<%=rsw("code")%>' style="display:none">
            <div style="width:180">
              <table width=160 border="0" cellpadding=0 cellspacing=0>
                <tr>
                  <td height=20>
	<%
tpl="<SCRIPT LANGUAGE='JavaScript'>"
tpl=tpl&"BY_News"&rsw("code")&" = new dTree('BY_News"&rsw("code")&"');"& chr(13)
tpl=tpl&"BY_News"&rsw("code")&".add(0,-1,'',null,'','main');"& chr(13)
    sqlx="select * from cate_qx where code like '"&left(rsw("code"),2)&"__'  and closeflag=1 and code<>'"&rsw("code")&"' order by ord asc"
    set rsx=server.CreateObject("adodb.recordset")
	rsx.open sqlx,conn,1,3
	if not rsx.eof or not rsx.bof then
	ii=1
	do while not rsx.eof
	a=0
	if qx<>"" then
	myqx=split(qx,",",-1,1)
		for jj=0 to ubound(myqx)
			if rsx("code")=trim(myqx(jj)) then
				a=1
			end if
		next
	end if
	%>
    <%

if a=1 then
    'if rsx("subid")="1" then
	'tpl=tpl&"BY_News"&i&".add("&ii&",0,'"&rsx("meno")&"',null,'"&rsx("meno")&"','main');"& chr(13)
	'subid=rsx("code")
	
	'else
	
	'tpl=tpl&"BY_News"&i&".add("&ii&",0,'"&rsx("meno")&"','"&rsx("link")&"?lmcode="&rsx("code")&"','"&rsx("meno")&"','main');"& chr(13)
	'end if
    if rsx("subid")="1" then
	       tpl=tpl&"BY_News"&rsw("code")&".add("&rsx("code")&",0,'"&rsx("meno")&"',null,'"&rsx("meno")&"','main');"& chr(13)
	else
	       if instr(rsx("link"),"?")>0 then
		   lmlink=rsx("link")&"&lmcode="&rsx("code")
		   else
		   lmlink=rsx("link")&"?lmcode="&rsx("code")
		   end if
		if rsx("subid")<>"0" and rsx("subid")<>"1" then
		   
		   tpl=tpl&"BY_News"&rsw("code")&".add("&rsx("code")&","&rsx("subid")&",'"&rsx("meno")&"','"&lmlink&"','"&rsx("meno")&"','main');"& chr(13)
		else
		   tpl=tpl&"BY_News"&rsw("code")&".add("&rsx("code")&",0,'"&rsx("meno")&"','"&lmlink&"','"&rsx("meno")&"','main');"& chr(13)
		end if
	end if
end if
    rsx.movenext
	ii=ii+1
	loop
	end if
	rsx.close
	set rsx=nothing
tpl=tpl&"document.write(BY_News"&rsw("code")&");"& chr(13)
tpl=tpl&"</script>"
response.Write(tpl)
%>
</td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </table>
	  <%
	  i=i+1
	  end if
	  rsw.movenext
	  
	  loop
	end if
	rsw.close
	set rsw=nothing
tmp="<SCRIPT LANGUAGE='JavaScript'>"& chr(13)
tmp=tmp&"function showsubmenu1(sid)"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"for (i=1;i<"&i&";i++)"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"whichEl = eval('submenu' + i);"& chr(13)
tmp=tmp&"if(i==sid)"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"if (whichEl.style.display == 'none')"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"eval(""submenu"" + i + "".style.display=\""\"";"");"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"else"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"eval(""submenu"" + i + "".style.display=\""none\"";"");"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"else"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"eval(""submenu"" + i + "".style.display=\""none\"";"");"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"</script>"
response.Write(tmp)

tmp="<SCRIPT LANGUAGE='JavaScript'>"& chr(13)
tmp=tmp&"function showsubmenu(sid)"& chr(13)
tmp=tmp&"{"& chr(13)
'tmp=tmp&"for (i=1;i<"&i&";i++)"& chr(13)
'tmp=tmp&"{"& chr(13)
tmp=tmp&"whichEl = eval('submenu' + sid);"& chr(13)
'tmp=tmp&"if(i==sid)"& chr(13)
'tmp=tmp&"{"& chr(13)
tmp=tmp&"if (whichEl.style.display == 'none')"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"eval(""submenu"" + sid + "".style.display=\""\"";"");"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"else"& chr(13)
tmp=tmp&"{"& chr(13)
tmp=tmp&"eval(""submenu"" + sid + "".style.display=\""none\"";"");"& chr(13)
tmp=tmp&"}"& chr(13)
'tmp=tmp&"}"& chr(13)
'tmp=tmp&"else"& chr(13)
'tmp=tmp&"{"& chr(13)
sqlw="select code from cate_qx where code like '__00' and closeflag=1 order by ord asc"
	set rsw=server.CreateObject("adodb.recordset")
	rsw.open sqlw,conn,1,3
	if not rsw.eof or not rsw.bof then
	do while not rsw.eof
	'&*&&&&&&&
	b=0
	if qx<>"" then
	mqx=split(qx,",",-1,1)
		for jj=0 to ubound(mqx)
			if left(rsw("code"),2)=left(trim(mqx(jj)),2) then
			b=1
			end if
		next
	end if
	if b=1 then
	'******************
	tmp=tmp&"if (sid!="&rsw("code")&"){"& chr(13)
       tmp=tmp&"eval(""submenu"" + "&rsw("code")&" + "".style.display=\""none\"";"");"& chr(13)
	tmp=tmp&"}"& chr(13)
	end if
    rsw.movenext
	loop
	end if
	rsw.close
	set rsw=nothing
'tmp=tmp&"}"& chr(13)
'tmp=tmp&"}"& chr(13)
tmp=tmp&"}"& chr(13)
tmp=tmp&"</script>"
response.Write(tmp)

	  %>
	  <!--循环结束-->
    </td>
    <td width="1" bgcolor="#4C7D68">
	</td>
	
  </tr>
</table>
</body>
</html>
