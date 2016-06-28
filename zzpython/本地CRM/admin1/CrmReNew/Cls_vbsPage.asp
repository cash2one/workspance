<%
Class Cls_vbsPage
	Private oConn		'连接对象
	Private iPagesize	'每页记录数
	Private sPageName	'地址栏页数参数名
	Private sDbType
	'数据库类型,AC为access,MSSQL为SQL SERVER2000存储过程版,MYSQL为mysql,PGSQL为PostGreSql
	Private iRecType	'记录总数(>0为另外取值再赋予或者固定值,0执行count设置存cookies,-1执行count不设置cookies)
	Private sJsUrl		'Cls_jsPage.js的路径
	Private sField		'字段名
	Private sTable		'表名
	Private sCondition	'条件,不需要where
	Private sOrderBy	'排序,不需要order by,需要asc或者desc
	Private sPkey		'主键,必写
	Private iRecCount

	'================================================================
	' Class_Initialize 类的初始化
	'================================================================
	Private Sub Class_Initialize
		iPageSize=20
		sPageName="Page"
		sDbType="AC"
		iRecType=0
		sJsUrl=""
		sField=" * "
	End Sub

	'================================================================
	' Conn 得到数据库连接对象
	'================================================================
	Public Property Set Conn(ByRef Value)
		Set oConn=Value
	End Property

	'================================================================
	' PageSize 设置每一页记录条数,默认10记录
	'================================================================
	Public Property Let PageSize(ByVal intPageSize)
		iPageSize=CheckNum(intPageSize,0,0,iPageSize,0) 
	End Property

	'================================================================
	' PageName 地址栏页数参数名
	'================================================================
	Public Property Let PageName(ByVal strPageName)
		sPageName=IIf(Len(strPageName)<1,sPageName,strPageName)
	End Property

	'================================================================
	' DbType 得到数据库类型
	'================================================================ 
	Public Property Let DbType(ByVal strDbType)
		sDbType=UCase(IIf(Len(strDbType)<1,sDbType,strDbType))
	End Property

	'================================================================
	' RecType 取记录总数(>0为赋值或者固定值,0执行count设置存cookies,-1执行count不设置cookies适用于搜索)
	'================================================================
	Public Property Let RecType(ByVal intRecType)
		iRecType=CheckNum(intRecType,0,0,iRecType,0) 
	End Property

	'================================================================
	' JsUrl 取得Cls_jsPage.js的路径
	'================================================================
	Public Property Let JsUrl(ByVal strJsUrl)
		sJsUrl=strJsUrl
	End Property

	'================================================================
	' Pkey 取得主键
	'================================================================
	Public Property Let Pkey(ByVal strPkey)
		sPkey=strPkey
	End Property

	'================================================================
	' Field 取得字段名
	'================================================================
	Public Property Let Field(ByVal strField)
		sField=IIf(Len(strField)<1,sField,strField)
	End Property

	'================================================================
	' Table 取得表名
	'================================================================
	Public Property Let Table(ByVal strTable)
		sTable=strTable
	End Property

	'================================================================
	' Condition 取得条件
	'================================================================
	Public Property Let Condition(ByVal strCondition)
		s=strCondition
		sCondition=IIf(Len(s)>2," WHERE "&s,"")
	End Property

	'================================================================
	' OrderBy 取得排序
	'================================================================
	Public Property Let OrderBy(ByVal strOrderBy)
		s=strOrderBy
		sOrderBy=IIf(Len(s)>4," ORDER BY "&s,"")
	End Property

	'================================================================
	' RecCount 修正记录总数
	'================================================================
	Public Property Get RecCount()
		If iRecType>0 Then
			i=iRecType
		Elseif iRecType=0 Then
			i=CheckNum(Request.Cookies("ShowoPage")(sPageName),1,0,0,0)
			s=Trim(Request.Cookies("ShowoPage")("sCond"))
			IF i=0 OR sCondition<>s or request.QueryString("page")="" or request.QueryString("page")=1 Then
				i=oConn.Execute("SELECT COUNT("&sPkey&") FROM "&sTable&" "&sCondition,0,1)(0)
				Response.Cookies("ShowoPage")(sPageName)=i
				Response.Cookies("ShowoPage")("sCond")=sCondition
			End If
		Else
			i=oConn.Execute("SELECT COUNT("&sPkey&") FROM "&sTable&" "&sCondition,0,1)(0)
		End If
		iRecCount=i
		RecCount=i
	End Property

	'================================================================
	' ResultSet 返回分页后的记录集
	'================================================================
	Public Property Get ResultSet()
		s=Null
		'记录总数
		i=iRecCount
		'当前页
		If i>0 Then
			iPageCount=Abs(Int(-Abs(i/iPageSize)))'页数
			iPageCurr=CheckNum(Request.QueryString(sPageName),1,1,1,iPageCount)'当前页
			Select Case sDbType
				Case "MSSQL" 'sqlserver2000数据库存储过程版
					Set Rs=server.CreateObject("Adodb.RecordSet")
					Set Cm=Server.CreateObject("Adodb.Command")
					Cm.CommandType=4
					Cm.ActiveConnection=oConn
					Cm.CommandText="zz91_Util_Page"
					Cm.parameters(1)=i
					Cm.parameters(2)=iPageCurr
					Cm.parameters(3)=iPageSize
					Cm.parameters(4)=sPkey
					Cm.parameters(5)=sField
					Cm.parameters(6)=sTable
					Cm.parameters(7)=Replace(sCondition," WHERE ","")
					Cm.parameters(8)=Replace(sOrderBy," ORDER BY ","")
					Rs.CursorLocation=3
					Rs.LockType=1
					Rs.Open Cm
				Case "MYSQL" 'MYSQL数据库
					ResultSet_Sql="SELECT "&sField&" FROM "&sTable&" "&sCondition&" "&sOrderBy&" LIMIT "&(iPageCurr-1)*iPageSize&","&iPageSize
					Set Rs=oConn.Execute(ResultSet_Sql)
				Case Else '其他情况按最原始的方法处理(AC同理)
					Set Rs = Server.CreateObject ("Adodb.RecordSet")
					ResultSet_Sql="SELECT "&sField&" FROM "&sTable&" "&sCondition&" "&sOrderBy
					Rs.Open ResultSet_Sql,oConn,1,1,&H0001
					Rs.AbsolutePosition=(iPageCurr-1)*iPageSize+1
			End Select
			s=Rs.GetRows(iPageSize)
			Rs.close
			Set Rs=Nothing
		End If
		ResultSet=s
	End Property

	'================================================================
	' Class_Terminate 类注销
	'================================================================
	Private Sub Class_Terminate()
		If IsObject(oConn) Then oConn.Close:Set oConn=Nothing
	End Sub

	'================================================================
	' 输入:检查字符,是否有最小值,是否有最大值,最小值(默认数字),最大值
	'================================================================
	Private Function CheckNum(ByVal strStr,ByVal blnMin,ByVal blnMax,ByVal intMin,ByVal intMax)
		Dim i,s,iMi,iMa
		s=Left(Trim(""&strStr),32):iMi=intMin:iMa=intMax
		If IsNumeric(s) Then
			i=CDbl(s)
			i=IIf(blnMin=1 And i<iMi,iMi,i)
			i=IIf(blnMax=1 And i>iMa,iMa,i)
		Else
			i=iMi
		End If
		CheckNum=i
	End Function

	'================================================================
	' 输入:简化条件判断
	'================================================================
	Private Function IIf(ByVal blnBool,ByVal strStr1,ByVal strStr2)
		Dim s
		If blnBool Then
			s=strStr1
		Else
			s=strStr2
		End If
		IIf=s
	End Function

	'================================================================
	' 上下页部分
	'================================================================
	Public Sub ShowPage()%>
		<Script Language="JavaScript" type="text/JavaScript" src="<%=sJsUrl%>Cls_jsPage.js"></Script>
		<Script Language="JavaScript" type="text/JavaScript">
		var s= new Cls_jsPage(<%=iRecCount%>,<%=iPageSize%>,3,"s"); 
		s.setPageSE("<%=sPageName%>=","");
		s.setPageInput("<%=sPageName%>");
		s.setUrl("");
		s.setPageFrist("首页","<<");
		s.setPagePrev("上页","<");
		s.setPageNext("下页",">");
		s.setPageLast("尾页",">>");
		s.setPageText("[{$PageNum}]","第{$PageNum}页");
		s.setPageTextF(" {$PageTextF} "," {$PageTextF} ");
		s.setPageSelect("{$PageNum}","第{$PageNum}页");
		s.setPageCss("","","");
		s.setHtml("共{$RecCount}记录 页次{$Page}/{$PageCount} 每页{$PageSize}条 {$PageFrist} {$PagePrev} {$PageText} {$PageNext} {$PageLast} {$PageInput} {$PageSelect}");
		s.Write();
		</Script>
	<%End Sub

End Class%>