<!-- #include file="pubdim.asp" -->
<%
'***********************************************
Class clsPageRs2
  Public  sltFld
  Public  keyFld
  
  Private fFromTbl
  Private fSqlOrder    'e.g.: ' ID DESC'
  Private fSqlWhere  
  Private fTotalPage,fTotalRec,fCurPage  
  Private fPageSize  
  Private sqlTotalCount
  Private pageSql
  Private fConn
  Private sRs

  Private Sub Class_Initialize
    fPageSize=15
    fCurPage=1
    fOrderSql=""
  End Sub
  '���������Ϣ
  Private Function outputErr
    response.Write "��ǰҳ:"& fCurPage&"<br/>"
    response.Write "��ҳ:"& fTotalPage&"<br/>"
    response.Write "��ҳsql:"& pageSql&"<br/>"
    response.Write "��ȡ��¼��sql:"& sqlTotalCount&"<br/>"
    response.Write "������Ϣ:"& err.Description&"<br/>"
    response.Write "������Դ:"& err.Source&"<br/>"
  End Function
  
  Private Sub Class_Terminate
     'outputErr
   Set sRs = Nothing
   Set fConn = Nothing
  End Sub
  
  '��ȡconn
  Public Property Let getConn(byRef val)
    Set fConn=val    
  End Property
  '��ȡpagesize
  Public Property Let pageSize(byRef val)
    fPageSize=val
  End Property
  
  Public Property Let FromTbl(byRef val)
    fFromTbl=val
  End Property
  Public Property Let sqlWhere(byRef val)
    fSqlWhere=val
  End Property
  Public Property Let sqlOrder(byRef val)
    fSqlOrder=val
  End Property
  
  Public Property Get totalPage
    totalPage = fTotalPage
  End Property
  Private Property Get FROMTbl
    FROMTbl = replace(fFromTbl,"'","''")
  End Property
  Private Property Get sqlWhere
    sqlWhere = fSqlWhere
    If Len(sqlWhere)=0 Then sqlWhere="WHERE 1=1"
  End Property
  Private Property Get sqlOrder
    sqlOrder = fSqlOrder
  End Property
  
  '----------��ȡ��ҳ�����ܼ�¼����
  Public Function getTotalPage
    set cmdcout=server.createobject("adodb.command") 
	cmdcout.activeconnection=conn 
	cmdcout.commandtext = "zz91_getRowCount" 
	cmdcout.CommandType=adCmdStoredProc 
	cmdcout.Parameters.Append cmdcout.CreateParameter("RETURN_VALUE", adInteger, adParamReturnValue, 4) 
	cmdcout.Parameters.Append cmdcout.CreateParameter("@sql",adVarChar,adParamInput,2000," "&fFromTbl&" "&fSqlWhere)
	cmdcout.Parameters.Append cmdcout.CreateParameter("@count",adInteger,adParamOutput,3)
	cmdcout.execute
	fTotalRec=cmdcout.Parameters("@count").value
	Set cmdcout.ActiveConnection = nothing 
	set cmdcout=nothing
	fTotalPage=fTotalRec\fPageSize
    If (fTotalRec Mod fPageSize) Then fTotalPage=fTotalPage+1
    If fTotalPage=0 Then fTotalPage=1
    fCurPage=Request("page")
    If Len(fCurPage)=0 Or (Not isNumeric(fCurPage)) Then fCurPage=1
    If fCurPage-fTotalPage>0 Then fCurPage=fTotalPage
    If fCurPage-1<0 Then fCurPage=1
	getTotalPage=fTotalRec
  End Function
  function getpage
    fCurPage=Request("page")
    If Len(fCurPage)=0 Or (Not isNumeric(fCurPage)) or fCurPage=-1 Then fCurPage=1
	getpage=fCurPage
  end function
  'End ----------��ȡ��ҳ�����ܼ�¼����

  Public Function pageRs
    getpage
	'response.Write(fCurPage)
    set cmd=server.createobject("adodb.command") 
	cmd.activeconnection=conn 
	cmd.commandtext = "zz91_Allpage" 
	cmd.CommandType=adCmdStoredProc 
	cmd.Parameters.Append cmd.CreateParameter("RETURN_VALUE", adInteger, adParamReturnValue, 4) 
	cmd.parameters.append cmd.createparameter("@qCols",adVarChar,AdParamInput,2000,sltFld) 
	cmd.parameters.append cmd.createparameter("@qTables",adVarChar,adParamInput,20,fFromTbl) 
	cmd.Parameters.Append cmd.CreateParameter("@iKey",adVarChar,adParamInput,20,keyFld)
	cmd.Parameters.Append cmd.CreateParameter("@Filter",adVarChar,adParamInput,2000,fSqlWhere)
	cmd.Parameters.Append cmd.CreateParameter("@oKey",adVarChar,adParamInput,2000,fSqlOrder)
	cmd.Parameters.Append cmd.CreateParameter("@pageSize",adInteger,adParamInput,,fPageSize)
	cmd.Parameters.Append cmd.CreateParameter("@pageNumber",adInteger,adParamInput,,fCurPage)
	cmd.Parameters.Append cmd.CreateParameter("@outsql",adVarChar,adParamOutput,2000)
	cmd.Parameters.Append cmd.CreateParameter("@counts",adInteger,adParamOutput,3)
	'cmd.execute
	'response.Write(cmd.Parameters("@outsql").value)
	set pageRs=cmd.execute
	Set cmd.ActiveConnection = nothing 
	set cmd=nothing
  End Function
  Public Function pageRsid
    getpage
	'response.Write(fCurPage)
    set cmd=server.createobject("adodb.command") 
	cmd.activeconnection=conn 
	cmd.commandtext = "zz91_AllpageID" 
	cmd.CommandType=adCmdStoredProc 
	cmd.Parameters.Append cmd.CreateParameter("RETURN_VALUE", adInteger, adParamReturnValue, 4) 
	cmd.parameters.append cmd.createparameter("@qCols",adVarChar,AdParamInput,2000,sltFld) 
	cmd.parameters.append cmd.createparameter("@qTables",adVarChar,adParamInput,200,fFromTbl) 
	cmd.Parameters.Append cmd.CreateParameter("@iKey",adVarChar,adParamInput,20,keyFld)
	cmd.Parameters.Append cmd.CreateParameter("@Filter",adVarChar,adParamInput,2000,fSqlWhere)
	cmd.Parameters.Append cmd.CreateParameter("@oKey",adVarChar,adParamInput,2000,fSqlOrder)
	cmd.Parameters.Append cmd.CreateParameter("@pageSize",adInteger,adParamInput,,fPageSize)
	cmd.Parameters.Append cmd.CreateParameter("@pageNumber",adInteger,adParamInput,,fCurPage)
	cmd.Parameters.Append cmd.CreateParameter("@outsql",adVarChar,adParamOutput,2000)
	cmd.Parameters.Append cmd.CreateParameter("@counts",adInteger,adParamOutput,3)
	'cmd.execute
	'response.Write(cmd.Parameters("@outsql").value)
	set pageRsid=cmd.execute
	Set cmd.ActiveConnection = nothing 
	set cmd=nothing
  End Function
  sub bottonpagenav()
   'If fTotalRec>0 Then Response.Write "��<b>"&arrTip(0)&""& abcount &"</b>"& arrTip(1)&"&nbsp;&nbsp;"
	 'Response.Write "<b><font color=#ff6600>"&fCurPage&"</font></b>/<b>"& fTotalPage&"</b> ҳ"
     'response.Write "&nbsp;&nbsp;<b>"& fPageSize&"</b> /ҳ"
  end sub
  '******************************************
  '* ����      : ��ҳʱ�������ֱ��
  '* �������  : (��ʽ)fCurPage--��ǰҳ fTotalPage--��ҳ��  fTotalRec--�ܼ�¼��
  '               (��ʽ) speTipStr-- ��ʾ��ҳ��ʾ,urlPrefix--��ҳ����ǰ׺
  '* ����ļ�  : �ڰ���"/Company/rsPage.asp"���ļ��з�ҳ��������������ʽ���� fCurPage,fTotalPage,fTotalRec
  '* ��ע      : 1.queryString������� page=
  '------------------------------------
  '* �޸�ʱ��  : sxg @ 2006.5.12
  ' ���Ӵ������ urlPrefix,��ָ����ҳ����ҳ��
  '------------------------------------
  '* update   : sxg @ 2006.2.13
  '* --1.������ת��ҳ�����
  '* --2.���Զ�����ʾ�ܼ�¼������Ϣ speTipStr Like "��¼����|��" ��"|"���зָ�
  '------------------------------------
  '* �޸�ʱ��  : sxg @ 2005.12.19
  '------------------------------------------------
  Sub pageNav(urlPrefix,speTipStr)
    urlPrefix=Trim(urlPrefix)
    If speTipStr="" Then speTipStr="&nbsp;|&nbsp;����Ϣ"
    Dim arrTip
    arrTip=Split(speTipStr,"|")
    '----------ȥ��"page="������ֹ�ظ�----------
    Dim str : str=request.ServerVariables("QUERY_STRING")
    Dim queryStr
    If Len(str)>0 Then
      pos1=Instr(str,"&page=")    'querystring���Ƿ����"page="��������
      If pos1=0 Then queryStr=str Else queryStr=left(str,pos1-1)
    Else
      queryStr="1=1"
    End If
    If Len(urlPrefix)=0 Then urlPrefix=request.ServerVariables("script_name")
    If inStr(urlPrefix,"?")>0 Then   'ָ��urlPrefix�д���queryString����
      pageUrl=urlPrefix
     Else
      pageUrl=urlPrefix&"?"& queryStr
    End If
    %>
	<style type="text/css">
<!--
#pageleft {
	float: left;
	width: 350px;
}
-->
</style>
	<div id="pageleft"> <%
     If fTotalRec>0 Then Response.Write "��<b>"&arrTip(0)&""& fTotalRec &"</b>"& arrTip(1)&"&nbsp;&nbsp;"

     Response.Write "<b>"&fCurPage&"</b>/<b>"& fTotalPage&"</b> ҳ"
     response.Write "&nbsp;&nbsp;<b>"& fPageSize&"</b> /ҳ"
     %></div>
<div align="right" id="pagetop"><font color="#666666">
  <%If fCurPage>1 Then %>
        <a href="<%=urlPrefix%>_p1.html">��ҳ</a>&nbsp; <a href="<%=urlPrefix%>_p<%=fCurPage-1%>.html">��һҳ</a>
        <%else%>
  ��ҳ&nbsp;��һҳ
  <%end if%>
  <%If fCurPage-fTotalPage<0 Then %>
  <a href="<%=urlPrefix%>_p<%=fCurPage+1%>.html">��һҳ</a>&nbsp;&nbsp;<a href="<%=urlPrefix%>_p<%=fTotalPage%>.html">βҳ</a>
  <%else%>
  &nbsp;��һҳ&nbsp;βҳ
<%end if%></div>
    
    <%
  End Sub
End Class
%>

