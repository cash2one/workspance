<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../inc.asp" -->
<%
idprod=request.QueryString("idprod")
com_id=idprod
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="payinfocontent">
                <tr>
                  <td>
                    <%
			  sqlt="select * from comp_continue where com_id="&idprod
				set rst=conn.execute(sqlt)
				if not rst.eof then
			  %>
                    <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
                      <tr bgcolor="#CCCCCC">
                        <td colspan="7">&nbsp;</td>
                        </tr>
                      <tr bgcolor="#CCCCCC">
                        <td>����ͨʹ�����</td>
                        <td>���翪ͨʱ��</td>
                        <td>��ǩ��ͨʱ��</td>
                        <td>��ǰ��ǩ��ʵ��ʱ��</td>
                        <td>����ʱ��</td>
                        <td>����</td>
                        <td>&nbsp;</td>
                        </tr>
                      <%
			  while not rst.eof 
			  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100"><%
				continueFlag=rst("continueFlag")
				firstDateFrom=rst("firstDateFrom")'���翪ͨʱ��
				continueDateFrom=rst("continueDateFrom")'��ǩ��ͨʱ��
				currentContinueDateFrom=rst("currentContinueDateFrom")'��ǰ��ǩ��ʵ��ʱ��
				dateTo=rst("dateTo")'����ʱ��
				yearNum=rst("yearNum")
				if continueFlag=1 then
					response.Write("��ǩ")
				elseif continueFlag=0 then
					response.Write("��ǩ")
				end if
				%></td>
                        <td><%=firstDateFrom%></td>
                        <td><%=continueDateFrom%></td>
                        <td><%=currentContinueDateFrom%></td>
                        <td><%=dateTo%></td>
                        <td><%=yearNum%></td>
                        <td><a href="http://www.zz91.com/admin1/compinfo/cominfo_showOpenConfirmLocal.asp?com_id=<%=idprod%>" target="_blank">�鿴��ͨ��</a></td>
                        </tr>
                      <%
			  rst.movenext
			  wend
			  %>
                      </table>
                    <%
			 end if
			 rst.close
			 set rst=nothing
			 %>
				  <%
                  sqlt="select * from advKeywords where com_id="&com_id&""
                  set rst=conn.execute(sqlt)
                  if not rst.eof then
                  %>
                    <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
                      <tr bgcolor="#CCCCCC">
                        <td>�ؼ���</td>
                        <td>�������</td>
                        <td>��ʼʱ��</td>
                        <td>����ʱ��</td>
                        <td>���</td>
                        <td>�Ƿ����</td>
                        <td>�����</td>
                        </tr>
                      <%
					  while not rst.eof 
					  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100"><%=rst("keywords")%></td>
                        <td><%=showMeno(conn,"advkeywordsType",rst("TypeID"))%></td>
                        <td><%=rst("fromdate")%></td>
                        <td><%=rst("todate")%></td>
                        <td><%
						if rst("checked")=1 then
						response.Write("�����")
						else
						response.Write("<font color=#ff0000>δ���</font>")
						end if
						%></td>
                        <td><%
						todate=rst("todate")
						if todate<>"" then
							if cdate(todate)<date() then
								response.Write("<font color=#ff0000>�ѹ���</font>")
							end if
						end if
						%></td>
                        <td><%=rst("Kcount")%></td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  %>
                      </table>
                    <%
					 end if
					 rst.close
					 set rst=nothing
					 %>
                     <%
                  sqlt="select * from comp_payinfo where com_id="&com_id&""
                  set rst=conn.execute(sqlt)
                  if not rst.eof then
                  %>
                    <table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
                      <tr bgcolor="#CCCCCC">
                        <td>��������</td>
                        <td>����</td>
                        <td>��ͨʱ��</td>
                        <td>����ʱ��</td>
                        <td>�������</td>
                        <td>���</td>
                        <td>��ע</td>
                        </tr>
                      <%
					  while not rst.eof 
					  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100">
						<%
						paytype=rst("paytype")
						if paytype="2" then response.Write("Ʒ��ͨ")
						if paytype="3" then response.Write("���")
						if paytype="4" then response.Write("С��Ʒ")
						if paytype="5" then response.Write("����")
						%>
                        </td>
                        <td>
						<%
						paykind=rst("paykind")
						if paytype="2" then
							if paykind="1" then
								paykind="����"
							end if
							if paykind="2" then
								paykind="����"
							end if
							if paykind="3" then
								paykind="��ʯ"
							end if
						end if
						response.Write(paykind)
						
						%></td>
                        <td><%
						fromdate=rst("fromdate")
						if fromdate="1900-1-1" then
							response.Write("��")
						else
							response.Write(fromdate)
						end if
						%></td>
                        <td>
						<%
						todate=rst("todate")
						if todate="1900-1-1" then
							response.Write("��")
						else
							response.Write(todate)
						end if
						%>
                        </td>
                        <td><%
						donation=rst("donation")
						if donation="1" then
						   response.Write("����")
						elseif donation="0" then
							response.Write("����")
						end if
						%></td>
                        <td><%=rst("money")%></td>
                        <td><%=rst("bz")%></td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  %>
                      </table>
                    <%
					 end if
					 rst.close
					 set rst=nothing
					 %>
                    </td>
                </tr>
              </table>
<script>
parent.document.getElementById("payinfocontent").innerHTML=document.getElementById("payinfocontent").innerHTML
</script>
<%
conn.close
set conn=nothing
%>