<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<%
sqluser="select ywadminid,adminuserid,Partuserid from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
p_ywadminid=rsuser("ywadminid")
p_adminuserid=rsuser("adminuserid")
p_Partuserid=rsuser("Partuserid")
rsuser.close
set rsuser=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=username%>  --  �û��޸�</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
 <script language=JavaScript>
  function subchk(frm)
  {
      if(frm.userid.value=="")
      {
         alert("��ѡ����!\n");
         frm.userid.focus();
         return  false;
       }
       if  (frm.uname.value=="")       
        {   
		 alert("�������û�����!\n");
         frm.uname.focus();
         return  false;                 
        } 
		if  (frm.password.value=="")       
        {   
		 alert("�������¼����!\n");
         frm.password.focus();
         return  false;                 
        }  
		
		if (frm.realname.value=="")
		{
			alert("������������");
			frm.realname.focus();
			return false;
		}
		
              
                
   }
    </script>
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">�û��޸�</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        <table width="400" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form name="company_sec"  method="post" action="admin_useradd_save.asp" onSubmit="return subchk(this)">
              
                <tr>
                  <td width="28%" align="right">����</td>
                  <td width="72%">
          <%
		  touserid=request.QueryString("touserid")
		  if touserid<>"23" then
		  %>  
          <select name="userid" id="userid">
                    <option value="">��ѡ��...</option>
				      <%
		  sqlw="select * from cate_adminuser where code in("&left(p_ywadminid,2)&") and code like '__' and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
				      <option value="<%=rsw("code")%>">��&nbsp;&nbsp;��<%=rsw("meno")%></option>
				      <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' and code in("&p_ywadminid&") and closeflag=1  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
				      <option value="<%=rs1("code")%>">��&nbsp;&nbsp;��&nbsp;&nbsp;��<%=rs1("meno")%></option>
				     
				      <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
				      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
				      </select>
            <%
			else
			%>
            ���²�<input name="userid" type="hidden" value="<%=touserid%>">
            <%
			end if
			%>
                 <script>selectOption("userid","<%=trim(userid)%>")</script></td>
                </tr>
                <tr>
                  <td align="right">                    �û���                  </td>
                  <td width="72%"><input name="uname" type="text" id="uname" value="<%=trim(username)%>">                  </td>
                </tr>
                
                <tr>
                  <td align="right">����</td>
                  <td><input name="password" type="password" id="password" value="<%=trim(password)%>">                  </td>
                </tr>
                <tr>
                  <td align="right">��ʵ����</td>
                  <td><input name="realname" type="text" id="realname" value="<%=trim(realname)%>">                  </td>
                </tr>
                <tr align="center">
                  <td align="right">�ʺſ�ͨ���</td>
                  <td align="left">
                  
                  <input name="closeflag" type="radio" id="closeflag" value="1" checked >
                    ��ͨ
                      <input type="radio" name="closeflag" id="closeflag" value="0">
                      �ر�</td>
                </tr>
                <tr align="center">
                  <td align="right">��ͨȨ��</td>
                  <td align="left"><input name="ktqx" type="radio" id="ktqx" value="1" checked>
                    ��ǩ
                    <input type="radio" name="ktqx" id="ktqx" value="2">
                    VAP
                    <input type="radio" name="ktqx" id="ktqx" value="3">
                    ����
                    </td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input type="submit" name="Submit" value="���">                  </td>
                </tr>
              </form>
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
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