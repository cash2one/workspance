<%

'����Զ�����ϲ�ѯ���з�ҳ
'***********************************************
'��д����
'  sltFld     --select �ֶ�
'  keyFld  --����or�����ֶ�
'  FROMTbl --��������ͼ������ѯ���,���Դ�where������order by
'  sqlOrder--�����ֶ�
'  sqlWhere--�����ֶ�
'  pageSize--ҳ��С
'ֻд����
'  getConn --��ȡconn����

'Example
'   Set oPage=New clsPageRs2
'   
'   With oPage
'     .sltFld  = "ID,title,addDate,viewTimes,recommend,istop "
'     .FROMTbl = "hy_sifa"
'     .sqlOrder= "Order By ID"
'     .sqlWhere= "WHERE recommend>0"
'     .keyFld  = "ID"    '����ȱ��
'     .pageSize= 30
'     .getConn = fConn
'     Set sRs  = .pageRs
'   End With
'-----------------------------------
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
    sqlTotalCount="SELECT COUNT(1) FROM "& FromTbl &" "& sqlWhere
    
    Set sRs=fConn.Execute(sqlTotalCount)
    fTotalRec =sRs(0)
    sRs.Close
    Set sRs=Nothing
    'response.Write fTotalRec
    fTotalPage=fTotalRec\fPageSize
    If (fTotalRec Mod fPageSize) Then fTotalPage=fTotalPage+1
    If fTotalPage=0 Then fTotalPage=1
    fCurPage   =Request("page")
    If Len(fCurPage)=0 Or (Not isNumeric(fCurPage)) Then fCurPage=1
    If fCurPage-fTotalPage>0 Then fCurPage=fTotalPage
    If fCurPage-1<0 Then fCurPage=1
	getTotalPage=fTotalRec
  End Function
  function gettotal
    sqlTotalCount="SELECT COUNT(1) FROM "& FromTbl &" "& sqlWhere
    
    Set sRs=fConn.Execute(sqlTotalCount)
    gettotal =sRs(0)
    sRs.Close
    Set sRs=Nothing
 
  end function
  'End ----------��ȡ��ҳ�����ܼ�¼����

  Public Function pageRs
    getTotalPage

    Dim tSql

    pageSql = "SELECT TOP "& fPageSize&" "& sltFld&" FROM "
    tSql = " "& fFromTbl & " "& fSqlWhere & " "& fSqlOrder
    If fCurPage=1 Then   'Ϊ��һҳʱֱ�ӻ�ȡtop
      pageSql = pageSql & tSql
    Else
      sqlStart = pageSql & fFromTbl & " "& fSqlWhere & " AND "& keyFld
      sqlEnd   = "(SELECT TOP "& (fCurPage-1)*fPageSize &" "& keyFld &" FROM "& tSql &") "
      'If Instr(fFromTbl,",")=0 And Instr(Lcase(fFromTbl)," join ")=0 Then  '����Ϊ�������
       ' If Instr(Lcase(fSqlOrder)," desc")=0 Then 'asc order
          'pageSql = sqlStart &">(SELECT MAX("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        'Else
          'pageSql = sqlStart &"<(SELECT MIN("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        'End If
      'Else  '���ϲ�ѯ��ʱ���ʹ��Not In
        pageSql = sqlStart &" NOT IN "& sqlEnd &" "& fSqlOrder
      'End If
    End If
    'response.Write pageSql
    Set pageRs=fConn.Execute(pageSql)
  End Function
  Public Function pageRsid
    getTotalPage

    Dim tSql

    pageSql = "SELECT TOP "& fPageSize&" "& sltFld&" FROM "
    tSql = " "& fFromTbl & " "& fSqlWhere & " "& fSqlOrder
    If fCurPage=1 Then   'Ϊ��һҳʱֱ�ӻ�ȡtop
      pageSql = pageSql & tSql
    Else
      sqlStart = pageSql & fFromTbl & " "& fSqlWhere & " AND "& keyFld
      sqlEnd   = "(SELECT TOP "& (fCurPage-1)*fPageSize &" "& keyFld &" FROM "& tSql &") "
      'If Instr(fFromTbl,",")=0 And Instr(Lcase(fFromTbl)," join ")=0 Then  '����Ϊ�������
        If Instr(Lcase(fSqlOrder)," desc")=0 Then 'asc order
          pageSql = sqlStart &">(SELECT MAX("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        Else
          pageSql = sqlStart &"<(SELECT MIN("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        End If
      'Else  '���ϲ�ѯ��ʱ���ʹ��Not In
        'pageSql = sqlStart &" NOT IN "& sqlEnd &" "& fSqlOrder
      'End If
    End If
    'response.Write pageSql
    Set pageRsid=fConn.Execute(pageSql)
  End Function
  
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
    If speTipStr="" Then speTipStr="�ܼ�¼��:&nbsp;|&nbsp;��"
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
    <table width="100%" border=0 cellpadding=0 cellspacing=0 style="margin:5px 0px 3px 0px;">
     <tr align="center">    
     <%If fCurPage>1 Then %>
      <td><a href="<%=pageUrl%>&page=1">��ҳ</a></td>
      <td><a href="<%=pageUrl%>&page=<%=fCurPage-1%>">��һҳ</a></td>
     <%
     Else
       Response.Write "<td width='20%'>&nbsp; </td>"
     End If
     %>
     <td>
     <%
     If fTotalRec>0 Then Response.Write arrTip(0)& fTotalRec & arrTip(1)&"&nbsp;&nbsp;"

     Response.Write fCurPage&"/"& fTotalPage&" ҳ"
     response.Write "&nbsp;&nbsp;"& fPageSize&"/ҳ"
     %>
     </td> 
     <%If fCurPage-fTotalPage<0 Then %>
     <td> <a href="<%=pageUrl%>&page=<%=fCurPage+1%>">��һҳ</a></td>
     <td> <a href="<%=pageUrl%>&page=<%=fTotalPage%>">βҳ</a></td>
     <%
     Else 
       Response.Write "<td>&nbsp;</td>"
     End If
     %>
     
     <td align="center">
     &nbsp;��&nbsp;
     <input type=text size=4 onKeyUp="value=value.replace(/[^\d]/,'')" name=windowpage maxlength=5>&nbsp;ҳ&nbsp;
     <input name="GO" type="button" class="button" value="Go!" onClick="location.href='<%=pageUrl%>&page='+windowpage.value">
     
     </td>
     
    </tr>
</table>
    <%
  End Sub
End Class
%>

