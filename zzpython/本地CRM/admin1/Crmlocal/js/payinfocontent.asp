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
                        <td>再生通使用情况</td>
                        <td>最早开通时间</td>
                        <td>续签开通时间</td>
                        <td>提前续签的实际时间</td>
                        <td>到期时间</td>
                        <td>年数</td>
                        <td>&nbsp;</td>
                        </tr>
                      <%
			  while not rst.eof 
			  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100"><%
				continueFlag=rst("continueFlag")
				firstDateFrom=rst("firstDateFrom")'最早开通时间
				continueDateFrom=rst("continueDateFrom")'续签开通时间
				currentContinueDateFrom=rst("currentContinueDateFrom")'提前续签的实际时间
				dateTo=rst("dateTo")'到期时间
				yearNum=rst("yearNum")
				if continueFlag=1 then
					response.Write("续签")
				elseif continueFlag=0 then
					response.Write("新签")
				end if
				%></td>
                        <td><%=firstDateFrom%></td>
                        <td><%=continueDateFrom%></td>
                        <td><%=currentContinueDateFrom%></td>
                        <td><%=dateTo%></td>
                        <td><%=yearNum%></td>
                        <td><a href="http://www.zz91.com/admin1/compinfo/cominfo_showOpenConfirmLocal.asp?com_id=<%=idprod%>" target="_blank">查看开通单</a></td>
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
                        <td>关键字</td>
                        <td>广告类型</td>
                        <td>开始时间</td>
                        <td>结束时间</td>
                        <td>审核</td>
                        <td>是否过期</td>
                        <td>点击率</td>
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
						response.Write("已审核")
						else
						response.Write("<font color=#ff0000>未审核</font>")
						end if
						%></td>
                        <td><%
						todate=rst("todate")
						if todate<>"" then
							if cdate(todate)<date() then
								response.Write("<font color=#ff0000>已过期</font>")
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
                        <td>服务类型</td>
                        <td>类型</td>
                        <td>开通时间</td>
                        <td>结束时间</td>
                        <td>付款情况</td>
                        <td>金额</td>
                        <td>备注</td>
                        </tr>
                      <%
					  while not rst.eof 
					  %>
                      
                      <tr bgcolor="#FFFFFF">
                        <td width="100">
						<%
						paytype=rst("paytype")
						if paytype="2" then response.Write("品牌通")
						if paytype="3" then response.Write("广告")
						if paytype="4" then response.Write("小产品")
						if paytype="5" then response.Write("其他")
						%>
                        </td>
                        <td>
						<%
						paykind=rst("paykind")
						if paytype="2" then
							if paykind="1" then
								paykind="银牌"
							end if
							if paykind="2" then
								paykind="金牌"
							end if
							if paykind="3" then
								paykind="钻石"
							end if
						end if
						response.Write(paykind)
						
						%></td>
                        <td><%
						fromdate=rst("fromdate")
						if fromdate="1900-1-1" then
							response.Write("无")
						else
							response.Write(fromdate)
						end if
						%></td>
                        <td>
						<%
						todate=rst("todate")
						if todate="1900-1-1" then
							response.Write("无")
						else
							response.Write(todate)
						end if
						%>
                        </td>
                        <td><%
						donation=rst("donation")
						if donation="1" then
						   response.Write("赠送")
						elseif donation="0" then
							response.Write("付费")
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