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
  '输出错误信息
  Private Function outputErr
    response.Write "当前页:"& fCurPage&"<br/>"
    response.Write "总页:"& fTotalPage&"<br/>"
    response.Write "分页sql:"& pageSql&"<br/>"
    response.Write "获取记录数sql:"& sqlTotalCount&"<br/>"
    response.Write "错误信息:"& err.Description&"<br/>"
    response.Write "错误来源:"& err.Source&"<br/>"
  End Function
  
  Private Sub Class_Terminate
     'outputErr
   Set sRs = Nothing
   Set fConn = Nothing
  End Sub
  
  '获取conn
  Public Property Let getConn(byRef val)
    Set fConn=val    
  End Property
  '获取pagesize
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
  
  '----------获取总页数、总记录数等
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
  'End ----------获取总页数、总记录数等

  Public Function pageRs
    getTotalPage

    Dim tSql

    pageSql = "SELECT TOP "& fPageSize&" "& sltFld&" FROM "
    tSql = " "& fFromTbl & " "& fSqlWhere & " "& fSqlOrder
    If fCurPage=1 Then   '为第一页时直接获取top
      pageSql = pageSql & tSql
    Else
      sqlStart = pageSql & fFromTbl & " "& fSqlWhere & " AND "& keyFld
      sqlEnd   = "(SELECT TOP "& (fCurPage-1)*fPageSize &" "& keyFld &" FROM "& tSql &") "
      'If Instr(fFromTbl,",")=0 And Instr(Lcase(fFromTbl)," join ")=0 Then  '表明为单表操作
       ' If Instr(Lcase(fSqlOrder)," desc")=0 Then 'asc order
          'pageSql = sqlStart &">(SELECT MAX("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        'Else
          'pageSql = sqlStart &"<(SELECT MIN("& keyFld&") FROM "& sqlEnd&" AS T)"&" "& fSqlOrder
        'End If
      'Else  '联合查询等时最好使用Not In
        pageSql = sqlStart &" NOT IN "& sqlEnd &" "& fSqlOrder
      'End If
    End If
    'response.Write pageSql
    Set pageRs=fConn.Execute(pageSql)
  End Function
  
  '******************************************
  '* 作用      : 分页时导航部分表格
  '* 传入参数  : (隐式)fCurPage--当前页 fTotalPage--总页数  fTotalRec--总记录数
  '               (显式) speTipStr-- 显示分页提示,urlPrefix--分页导航前缀
  '* 针对文件  : 在包含"/Company/rsPage.asp"的文件中分页导航，否则需显式声明 fCurPage,fTotalPage,fTotalRec
  '* 备注      : 1.queryString必须参数 page=
  '------------------------------------
  '* 修改时间  : sxg @ 2006.5.12
  ' 增加传入参数 urlPrefix,可指定分页处理页面
  '------------------------------------
  '* update   : sxg @ 2006.2.13
  '* --1.增加跳转到页输入框
  '* --2.可自定义提示总记录条数信息 speTipStr Like "记录总数|条" 以"|"进行分割
  '------------------------------------
  '* 修改时间  : sxg @ 2005.12.19
  '------------------------------------------------
  sub bottonpagenav()
   'If fTotalRec>0 Then Response.Write "共<b>"&arrTip(0)&""& abcount &"</b>"& arrTip(1)&"&nbsp;&nbsp;"
	 'Response.Write "<b><font color=#ff6600>"&fCurPage&"</font></b>/<b>"& fTotalPage&"</b> 页"
     'response.Write "&nbsp;&nbsp;<b>"& fPageSize&"</b> /页"
  end sub
  Sub pageNav(urlPrefix,speTipStr)
    urlPrefix=Trim(urlPrefix)
    If speTipStr="" Then speTipStr="&nbsp;|&nbsp;条信息"
    Dim arrTip
    arrTip=Split(speTipStr,"|")
    '----------去掉"page="参数防止重复----------
    Dim str : str=request.ServerVariables("QUERY_STRING")
    Dim queryStr
    If Len(str)>0 Then
      pos1=Instr(str,"&page=")    'querystring中是否存在"page="参数连接
      If pos1=0 Then queryStr=str Else queryStr=left(str,pos1-1)
    Else
      queryStr="1=1"
    End If
    If Len(urlPrefix)=0 Then urlPrefix=request.ServerVariables("script_name")
    If inStr(urlPrefix,"?")>0 Then   '指定urlPrefix中存在queryString参数
      pageUrl=urlPrefix
     Else
      pageUrl=urlPrefix&"?"& queryStr
    End If
    %>

    
    <%
  End Sub
End Class
%>

