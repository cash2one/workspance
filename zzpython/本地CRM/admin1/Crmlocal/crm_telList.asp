<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售电话记录</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td nowrap bgcolor="#CED7E7">联系时间</td>
            <td nowrap bgcolor="#CED7E7">联系情况</td>
            <td nowrap bgcolor="#CED7E7">联系人</td>
            <td nowrap bgcolor="#CED7E7">客户等级</td>
            <td nowrap bgcolor="#CED7E7">联系内容</td>
            <td nowrap bgcolor="#CED7E7">录音</td>
            <td nowrap bgcolor="#CED7E7">客户态度</td>
            <td nowrap bgcolor="#CED7E7">客户状况</td>
            <td nowrap bgcolor="#CED7E7">服务介绍</td>
            <td nowrap bgcolor="#CED7E7">跟进方式</td>
            <td nowrap bgcolor="#CED7E7">催单方式</td>
            <td nowrap bgcolor="#CED7E7">产品资料</td>
            <td nowrap bgcolor="#CED7E7">汇款帐号</td>
  </tr>
            <tr>
            <td colspan="13" align="center" bgcolor="#FFFFFF"><a href="crm_tel_comp.asp?com_id=<%=request.QueryString("com_id")%>&amp;telflag=0">再生通<strong>新签</strong>销售电话记录</a></td>
          </tr>
            <%
		telflag=0
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")&"&telflag="&telflag
		sql="select top 5 com_id,id,teldate,contacttype,personid,com_rank,detail,case1,case2,case3 from comp_tel where  com_id="&request("com_id")&" and serviceType=0 and telflag="&telflag&""
		   if cstr(request("from_date"))<>cstr(request("to_date")) then
				if request("from_date")<>"" then
					sql=sql&" and teldate>='"&request("from_date")&"'"
					sear=sear&"&from_date="&request("from_date")
				end if
				if request("to_date")<>"" then
					sql=sql&" and teldate<='"&request("to_date")&"'"
					sear=sear&"&to_date="&request("to_date")
				end if
			else
			    if request("from_date")<>"" and request("to_date")<>"" then
					sql=sql&" and year(teldate)="&year(request("from_date"))&" and month(teldate)="&month(request("from_date"))&" and day(teldate)="&day(request("from_date"))&""
				    sear=sear&"&from_date="&request("from_date")
					sear=sear&"&to_date="&request("to_date")
				end if
			end if
			if request.QueryString("orderaction")<>"" then
				sql=sql&" order by "&request.QueryString("orderaction")&" desc"
				sear=sear&"&orderaction="&request("orderaction")
			else
				sql=sql&" order by teldate desc"
			end if
		    set rs=server.CreateObject("ADODB.recordset")
		    rs.open sql,conn,1,1

		  if not rs.eof then
		  do while not rs.eof
		  sqlc="select com_id,com_name,com_email from comp_info where  com_id="&rs("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
			  com_name=rsc("com_name")
			  com_email=rsc("com_email")
		  end if
		  rsc.close
		  set rsc=nothing
		  sqlt="select * from Crm_compContactInfo where com_id="&rs("com_id")&" and telid="&rs("id")&""
		  set rst=conn.execute(sqlt)
		  if not rst.eof then
		  	c_NoContact=rst("c_NoContact")
		  	C_CustomTaidu=rst("C_CustomTaidu")
			C_CustomStation=rst("C_CustomStation")
			C_ServerIntro=rst("C_ServerIntro")
			C_ServerType=rst("C_ServerType")
			C_ServerGo=rst("C_ServerGo")
			C_ServerTo=rst("C_ServerTo")
			C_PayType=rst("C_PayType")
		  else
		  	c_NoContact="0"
		  	C_CustomTaidu="0"
			C_CustomStation="0"
			C_ServerIntro="0"
			C_ServerType="0"
			C_ServerGo="0"
			C_ServerTo="0"
			C_PayType="0"
		  end if
		  rst.close
		  set rst=nothing
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=0" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="13" bgcolor="#FF9900">
            </td>
          </tr>
  <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF">
			<%'=c_NoContact%>
			<%if rs("contacttype")="13" then%>
			<%call shows("cate_contact_about",rs("contacttype"))%>
			<%else%>
			无效联系—<%call shows("cate_crmNoContact",c_NoContact)%>
			<%end if%>			</td>
            <td bgcolor="#FFFFFF">
			<%
			call selet_("realname","users","id",rs("personid"))
			%></td>
            <td bgcolor="#FFFFFF">
			<%
			if rs("com_rank")<>"-1" then
				response.Write(rs("com_rank")&"星")
			end if
			 %>			</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%><%=rs("case1")%><%=rs("case2")%><%=rs("case3")%></td>
            <td bgcolor="#FFFFFF">
            	<%
				sqlt="select id,filepath from crm_callrecord where telid="&rs("id")&""
				set rst=conn.execute(sqlt)
				pp=1
				if not rst.eof or not rst.bof then
					while not rst.eof
					response.Write("<a href='"&luyinurl&rst("filepath")&".WAV' target=_blank>录音("&pp&")</a><br>")
					pp=pp+1
					rst.movenext
					wend
				end if
				rst.close
				set rst=nothing
				%>
            </td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmTaidu",C_CustomTaidu)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_CrmCompStation",C_CustomStation)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmserverIntro",C_ServerIntro)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerType",C_ServerType)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerGo",C_ServerGo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerTo",C_ServerTo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("Cate_CrmPayType",C_PayType)%></td>
  </tr>
          
<%
		  rs.movenext
		  loop
		  end if
		  rs.close
set rs=nothing
		  %>
          
          <tr>
            <td colspan="13" align="center" bgcolor="#FFFFFF"><a href="crm_tel_comp.asp?com_id=<%=request.QueryString("com_id")%>&amp;telflag=2">再生通<strong>续签</strong>销售电话记录</a></td>
          </tr>
            <%
		telflag=2
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")&"&telflag="&telflag
		sql="select top 5 com_id,id,teldate,contacttype,personid,com_rank,detail,case1,case2,case3 from comp_tel where  com_id="&request("com_id")&" and serviceType=0 and telflag="&telflag&""
		   if cstr(request("from_date"))<>cstr(request("to_date")) then
				if request("from_date")<>"" then
					sql=sql&" and teldate>='"&request("from_date")&"'"
					sear=sear&"&from_date="&request("from_date")
				end if
				if request("to_date")<>"" then
					sql=sql&" and teldate<='"&request("to_date")&"'"
					sear=sear&"&to_date="&request("to_date")
				end if
			else
			    if request("from_date")<>"" and request("to_date")<>"" then
					sql=sql&" and year(teldate)="&year(request("from_date"))&" and month(teldate)="&month(request("from_date"))&" and day(teldate)="&day(request("from_date"))&""
				    sear=sear&"&from_date="&request("from_date")
					sear=sear&"&to_date="&request("to_date")
				end if
			end if
			if request.QueryString("orderaction")<>"" then
				sql=sql&" order by "&request.QueryString("orderaction")&" desc"
				sear=sear&"&orderaction="&request("orderaction")
			else
				sql=sql&" order by teldate desc"
			end if
		    set rs=server.CreateObject("ADODB.recordset")
		    rs.open sql,conn,1,1

		  if not rs.eof then
		  do while not rs.eof
		  sqlc="select com_id,com_name,com_email from comp_info where  com_id="&rs("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
			  com_name=rsc("com_name")
			  com_email=rsc("com_email")
		  end if
		  rsc.close
		  set rsc=nothing
		  sqlt="select * from Crm_compContactInfo where com_id="&rs("com_id")&" and telid="&rs("id")&""
		  set rst=conn.execute(sqlt)
		  if not rst.eof then
		  	c_NoContact=rst("c_NoContact")
		  	C_CustomTaidu=rst("C_CustomTaidu")
			C_CustomStation=rst("C_CustomStation")
			C_ServerIntro=rst("C_ServerIntro")
			C_ServerType=rst("C_ServerType")
			C_ServerGo=rst("C_ServerGo")
			C_ServerTo=rst("C_ServerTo")
			C_PayType=rst("C_PayType")
		  else
		  	c_NoContact="0"
		  	C_CustomTaidu="0"
			C_CustomStation="0"
			C_ServerIntro="0"
			C_ServerType="0"
			C_ServerGo="0"
			C_ServerTo="0"
			C_PayType="0"
		  end if
		  rst.close
		  set rst=nothing
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=0" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="13" bgcolor="#FF9900">
            </td>
          </tr>
          <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF">
			<%'=c_NoContact%>
			<%if rs("contacttype")="13" then%>
			<%call shows("cate_contact_about",rs("contacttype"))%>
			<%else%>
			无效联系—<%call shows("cate_crmNoContact",c_NoContact)%>
			<%end if%>			</td>
            <td bgcolor="#FFFFFF">
			<%
			call selet_("realname","users","id",rs("personid"))
			%></td>
            <td bgcolor="#FFFFFF">
			<%
			if rs("com_rank")<>"-1" then
				response.Write(rs("com_rank")&"星")
			end if
			 %>			</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%><%=rs("case1")%><%=rs("case2")%><%=rs("case3")%></td>
            <td bgcolor="#FFFFFF">&nbsp;</td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmTaidu",C_CustomTaidu)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_CrmCompStation",C_CustomStation)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmserverIntro",C_ServerIntro)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerType",C_ServerType)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerGo",C_ServerGo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerTo",C_ServerTo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("Cate_CrmPayType",C_PayType)%></td>
  </tr>
          
<%
		  rs.movenext
		  loop
		  end if
		  rs.close
set rs=nothing
		  %>
          <tr>
            <td colspan="13" align="center" bgcolor="#FFFFFF"><a href="crm_tel_comp.asp?com_id=<%=request.QueryString("com_id")%>&amp;telflag=4"><strong>VAP</strong>销售电话记录</a></td>
          </tr>
            <%
		telflag=4
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")&"&telflag="&telflag
		sql="select top 5 com_id,id,teldate,contacttype,personid,com_rank,detail,case1,case2,case3 from comp_tel where  com_id="&request("com_id")&" and serviceType=0 and telflag="&telflag&""
		   if cstr(request("from_date"))<>cstr(request("to_date")) then
				if request("from_date")<>"" then
					sql=sql&" and teldate>='"&request("from_date")&"'"
					sear=sear&"&from_date="&request("from_date")
				end if
				if request("to_date")<>"" then
					sql=sql&" and teldate<='"&request("to_date")&"'"
					sear=sear&"&to_date="&request("to_date")
				end if
			else
			    if request("from_date")<>"" and request("to_date")<>"" then
					sql=sql&" and year(teldate)="&year(request("from_date"))&" and month(teldate)="&month(request("from_date"))&" and day(teldate)="&day(request("from_date"))&""
				    sear=sear&"&from_date="&request("from_date")
					sear=sear&"&to_date="&request("to_date")
				end if
			end if
			if request.QueryString("orderaction")<>"" then
				sql=sql&" order by "&request.QueryString("orderaction")&" desc"
				sear=sear&"&orderaction="&request("orderaction")
			else
				sql=sql&" order by teldate desc"
			end if
		    set rs=server.CreateObject("ADODB.recordset")
		    rs.open sql,conn,1,1

		  if not rs.eof then
		  do while not rs.eof
		  sqlc="select com_id,com_name,com_email from comp_info where  com_id="&rs("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
			  com_name=rsc("com_name")
			  com_email=rsc("com_email")
		  end if
		  rsc.close
		  set rsc=nothing
		  sqlt="select * from Crm_compContactInfo where com_id="&rs("com_id")&" and telid="&rs("id")&""
		  set rst=conn.execute(sqlt)
		  if not rst.eof then
		  	c_NoContact=rst("c_NoContact")
		  	C_CustomTaidu=rst("C_CustomTaidu")
			C_CustomStation=rst("C_CustomStation")
			C_ServerIntro=rst("C_ServerIntro")
			C_ServerType=rst("C_ServerType")
			C_ServerGo=rst("C_ServerGo")
			C_ServerTo=rst("C_ServerTo")
			C_PayType=rst("C_PayType")
		  else
		  	c_NoContact="0"
		  	C_CustomTaidu="0"
			C_CustomStation="0"
			C_ServerIntro="0"
			C_ServerType="0"
			C_ServerGo="0"
			C_ServerTo="0"
			C_PayType="0"
		  end if
		  rst.close
		  set rst=nothing
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=0" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="13" bgcolor="#FF9900">
            </td>
          </tr>
          <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF">
			<%'=c_NoContact%>
			<%if rs("contacttype")="13" then%>
			<%call shows("cate_contact_about",rs("contacttype"))%>
			<%else%>
			无效联系—<%call shows("cate_crmNoContact",c_NoContact)%>
			<%end if%>			</td>
            <td bgcolor="#FFFFFF">
			<%
			call selet_("realname","users","id",rs("personid"))
			%></td>
            <td bgcolor="#FFFFFF">
			<%
			if rs("com_rank")<>"-1" then
				response.Write(rs("com_rank")&"星")
			end if
			 %>			</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%><%=rs("case1")%><%=rs("case2")%><%=rs("case3")%></td>
            <td bgcolor="#FFFFFF">&nbsp;</td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmTaidu",C_CustomTaidu)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_CrmCompStation",C_CustomStation)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmserverIntro",C_ServerIntro)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerType",C_ServerType)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerGo",C_ServerGo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_crmServerTo",C_ServerTo)%></td>
            <td bgcolor="#FFFFFF"><%call shows("Cate_CrmPayType",C_PayType)%></td>
  </tr>
          
<%
		  rs.movenext
		  loop
		  end if
		  rs.close
set rs=nothing
		  %>
          
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><a href="http://admin.zz91.com/admin1/compinfo/crm_servicetel_comp.asp?com_id=<%=request("com_id")%>" target="_blank"><b>CS</b>服务记录</a></td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>