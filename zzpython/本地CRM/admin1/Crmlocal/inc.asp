<% sub showComCate(cateID) %>
<select name="cate_compCate" id="cate_compCate" onchange="if(this.selectedIndex==3){document.getElementById('jyTable').style.display='';}else{document.getElementById('jyTable').style.display='none';}">
	<option value="">��ѡ��</option>
	<option value="1" <% If cateID="1" Then response.write "selected" %>>�ͻ���˾��Ϣ����Ʒ��Ϣ����ϵ��ʽ����׼ȷ</option>
	<option value="2" <% If cateID="2" Then response.write "selected" %>>�ͻ����������ϡ������������ҵ</option>
	<option value="3" <% If cateID="3" Then response.write "selected" %>>��Ӫ��Ʒ���󣨿ͻ������������Ʒ�ģ�</option>
	<option value="4" <% If cateID="4" Then response.write "selected" %>>��ϵ��ʽ�д���ģ����붼����ȷ��</option>
	<option value="5" <% If cateID="5" Then response.write "selected" %>>�����</option>
</select>
<% end sub %>
<%
lgmmserverpath= Server.MapPath("\")&"/"
serverpath= Server.MapPath("\")
Set fso = CreateObject("Scripting.FileSystemObject")
function showMeno(connName,tbName,subjectValues)
	sqlshow="select meno from "&tbName&" where code ='"&subjectValues&"'"
	set rsshow=connName.execute(sqlshow)
	if not rsshow.eof or not rsshow.bof then
		showMeno=rsshow(0)
	else
		showMeno=""
	end if
	rsshow.close
	set rsshow=nothing
end function
'----------���������
function cateMeno(connName,tbNmae,subjectName,subjectValues,jsvalue)
    loclurl="commType"
	txtname=tbNmae
	MakeHtmltime=60000*60*60
	Htmltemp="<select name='"& subjectName &"' id='"&subjectName&"' "&jsvalue&">"
	if ExistsTxt(loclurl,txtname,MakeHtmltime)=0 then
		sql_cate="select code,meno from "&tbNmae&""
		if tbNmae="cate_product_ly" then
		sql_cate=sql_cate&" where code like '__'"
		end if
		sql_cate=sql_cate&" order by ord asc"
		set rs_cate=connName.execute(sql_cate)
		place=""
		if tbNmae="cate_product_ly" then
		place=place&"<option value=''>��ѡ��ͻ���Դ</option>"
		else
		place=place&"<option value=''>��ѡ��...</option>"
		end if
		if not rs_cate.eof or not rs_cate.bof then
			while not rs_cate.eof
				 place=place&"<option value=" & rs_cate("code") & ">" & rs_cate("meno") & "</option>"
				 sqlcate1="select code,meno from "&tbNmae&" where code like '"&rs_cate("code")&"__' order by ord asc"
				 set rscate1=connName.execute(sqlcate1)
				 if not rscate1.eof or rscate1.bof then
					 while not rscate1.eof
					 	place=place&"<option value=" & rscate1("code") & ">��&nbsp;&nbsp;��" & rscate1("meno") & "</option>"
					 		 sqlcate2="select code,meno from "&tbNmae&" where code like '"&rscate1("code")&"__' order by ord asc"
							 set rscate2=connName.execute(sqlcate2)
							 if not rscate2.eof or rscate2.bof then
								 while not rscate2.eof
									place=place&"<option value=" & rscate2("code") & ">��&nbsp;&nbsp;��&nbsp;&nbsp;��" & rscate2("meno") & "</option>"
									rscate2.movenext
								 wend
							 end if
							 rscate2.close
							 set rscate2=nothing
						rscate1.movenext
					 wend
				 end if
				 rscate1.close
				 set rscate1=nothing
			rs_cate.movenext
			wend
		end if
		rs_cate.close
		set rs_cate=nothing
		
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,place,MakeHtmltime)
	else
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,"",MakeHtmltime)
	end if
	Htmltemp=Htmltemp&"</select>"
	if subjectValues<>"" and not isnull(subjectValues) then
		Htmltemp=Htmltemp&"<script>selectOption("""&subjectName&""","""&subjectValues&""")</script>"
	end if
	cateMeno=Htmltemp
end function
'----------���������
function cateMeno_public(connName,tbNmae,subjectName,subjectValues,jsvalue,lableCode)
    loclurl="commType"
	txtname=tbNmae&lableCode
	MakeHtmltime=60000*60*60
	Htmltemp="<select name='"& subjectName &"' id='"&subjectName&"' "&jsvalue&">"
	if ExistsTxt(loclurl,txtname,MakeHtmltime)=0 then
		sql_cate="select code,meno from "&tbNmae&" where code like '"&lableCode&"__' order by ord asc"
		set rs_cate=connName.execute(sql_cate)
		place=""
		if tbNmae="cate_product_ly" then
		place=place&"<option value=''>��ѡ��ͻ���Դ</option>"
		else
		place=place&"<option value=''>��ѡ��...</option>"
		end if
		if not rs_cate.eof or not rs_cate.bof then
			while not rs_cate.eof
				 place=place&"<option value=" & rs_cate("code") & ">" & rs_cate("meno") & "</option>"
				 sqlcate1="select code,meno from "&tbNmae&" where code like '"&rs_cate("code")&"__' order by ord asc"
				 set rscate1=connName.execute(sqlcate1)
				 if not rscate1.eof or rscate1.bof then
					 while not rscate1.eof
					 	place=place&"<option value=" & rscate1("code") & ">��&nbsp;&nbsp;��" & rscate1("meno") & "</option>"
					 		 sqlcate2="select code,meno from "&tbNmae&" where code like '"&rscate1("code")&"__' order by ord asc"
							 set rscate2=connName.execute(sqlcate2)
							 if not rscate2.eof or rscate2.bof then
								 while not rscate2.eof
									place=place&"<option value=" & rscate2("code") & ">��&nbsp;&nbsp;��&nbsp;&nbsp;��" & rscate2("meno") & "</option>"
									rscate2.movenext
								 wend
							 end if
							 rscate2.close
							 set rscate2=nothing
						rscate1.movenext
					 wend
				 end if
				 rscate1.close
				 set rscate1=nothing
			rs_cate.movenext
			wend
		end if
		rs_cate.close
		set rs_cate=nothing
		'place=place&"</select>"
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,place,MakeHtmltime)
	else
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,"",MakeHtmltime)
	end if
	Htmltemp=Htmltemp&"</select>"
	if subjectValues<>"" and not isnull(subjectValues) then
		Htmltemp=Htmltemp&"<script>selectOption("""&subjectName&""","""&subjectValues&""")</script>"
	end if
	cateMeno_public=Htmltemp
end function
'----------���������
function cateMeno_public2(connName,tbNmae,subjectName,subjectValues,jsvalue,lableCode)
    loclurl="commType"
	txtname=tbNmae&lableCode
	MakeHtmltime=60000*60*60
	Htmltemp="<select name='"& subjectName &"' id='"&subjectName&"' "&jsvalue&">"
	if ExistsTxt(loclurl,txtname,MakeHtmltime)=0 then
		sql_cate="select code,meno from "&tbNmae&" where code like '"&lableCode&"____' order by ord asc"
		set rs_cate=connName.execute(sql_cate)
		place=""
		if tbNmae="cate_product_ly" then
		place=place&"<option value=''>��ѡ��ͻ���Դ</option>"
		else
		place=place&"<option value=''>��ѡ��...</option>"
		end if
		if not rs_cate.eof or not rs_cate.bof then
			while not rs_cate.eof
				 place=place&"<option value=" & rs_cate("code") & ">" & rs_cate("meno") & "</option>"
				 sqlcate1="select code,meno from "&tbNmae&" where code like '"&rs_cate("code")&"____' order by ord asc"
				 set rscate1=connName.execute(sqlcate1)
				 if not rscate1.eof or rscate1.bof then
					 while not rscate1.eof
					 	place=place&"<option value=" & rscate1("code") & ">��&nbsp;&nbsp;��" & rscate1("meno") & "</option>"
					 		 sqlcate2="select code,meno from "&tbNmae&" where code like '"&rscate1("code")&"____' order by ord asc"
							 set rscate2=connName.execute(sqlcate2)
							 if not rscate2.eof or rscate2.bof then
								 while not rscate2.eof
									place=place&"<option value=" & rscate2("code") & ">��&nbsp;&nbsp;��&nbsp;&nbsp;��" & rscate2("meno") & "</option>"
									rscate2.movenext
								 wend
							 end if
							 rscate2.close
							 set rscate2=nothing
						rscate1.movenext
					 wend
				 end if
				 rscate1.close
				 set rscate1=nothing
			rs_cate.movenext
			wend
		end if
		rs_cate.close
		set rs_cate=nothing
		'place=place&"</select>"
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,place,MakeHtmltime)
	else
		Htmltemp=Htmltemp&creatText2008(loclurl,txtname,"",MakeHtmltime)
	end if
	Htmltemp=Htmltemp&"</select>"
	if subjectValues<>"" and not isnull(subjectValues) then
		Htmltemp=Htmltemp&"<script>selectOption("""&subjectName&""","""&subjectValues&""")</script>"
	end if
	cateMeno_public2=Htmltemp
end function
'----------���������
function cateRadioMeno(tbNmae,subjectName,subjectValues)
    loclurl="commType"
	txtname=tbNmae
	MakeHtmltime=60000*60*60
	if ExistsTxt(loclurl,txtname,MakeHtmltime)=0 then
		set rs_cate=server.CreateObject("adodb.recordset")
		sql_cate="select * from "&tbNmae&" order by ord desc"
		rs_cate.open sql_cate,conn,1,1
		place=""
		if not rs_cate.eof or not rs_cate.bof then
			do while not rs_cate.eof
				place=place&"<input type='radio' name='"&subjectName&"' value='" & rs_cate("code") & "' />"& rs_cate("meno")&""
				rs_cate.movenext
			loop
			rs_cate.close
			set rs_cate=nothing
		end if
		Htmltemp=creatText2008(loclurl,txtname,place,MakeHtmltime)
	else
		Htmltemp=creatText2008(loclurl,txtname,"",MakeHtmltime)
	end if
	
	if subjectValues<>"" and not isnull(subjectValues) then
		Htmltemp=Htmltemp&"<script>selectOption("""&subjectName&""","""&subjectValues&""")</script>"
	end if
	cateRadioMeno=Htmltemp
end function
'--------���ɾ�̬�ı����� 2008-7-3
'---------------------------------------
function creatText2008(url,TxtName,Content,MakeHtmlTime)
	set myfile=server.createobject("scripting.filesystemobject")
	'-----����Ŀ¼
	TxtName=replace(TxtName,"/","-")
	ArrUrl=split(url,"\")
	NUrl=""
	for i=0 to ubound(ArrUrl)
		if myfile.FolderExists(lgmmserverpath&NUrl&ArrUrl(i)) then
		else
			Set f = myfile.CreateFolder(lgmmserverpath&NUrl&ArrUrl(i))
		end if
		NUrl=NUrl&ArrUrl(i)&"\"
	next
	'-------------------
	Filen=lgmmserverpath&url&"\"&TxtName&".txt"
	if myfile.fileExists(Filen) then
	    set f=myfile.getfile(Filen)
		modifyTime=f.DateLastModified'�޸�ʱ��
		intSizeB = f.Size'�ļ���С
		if datediff("s",cdate(modifyTime),now)<MakeHtmlTime then
			set mytext=myfile.opentextfile(Filen)
			if intSizeB>0 then
				ContentText=mytext.readall
				creatText2008=ContentText
			else
				creatText2008=""
			end if
		else
			Set Site_Config=myfile.CreateTextFile(Filen,true, False)
			Site_Config.Write Content
			Site_Config.Close
			Set myfile = Nothing
			creatText2008=Content
		end if
	else
		Set Site_Config=myfile.CreateTextFile(Filen,true, False)
		Site_Config.Write Content
		Site_Config.Close
		Set myfile = Nothing
		creatText2008=Content
	end if
end function
'----------------------------------------end
function DelTxt2008(url,TxtName)
	set myfile=server.createobject("scripting.filesystemobject")
	'-----����Ŀ¼
	ArrUrl=split(url,"\")
	NUrl=""
	for i=0 to ubound(ArrUrl)
		if myfile.FolderExists(lgmmserverpath&NUrl&ArrUrl(i)) then
		else
			Set f = myfile.CreateFolder(lgmmserverpath&NUrl&ArrUrl(i))
		end if
		NUrl=NUrl&ArrUrl(i)&"\"
	next
	'-------------------
	Filen=lgmmserverpath&url&"\"&TxtName&".txt"
	if myfile.fileExists(Filen) then
		myfile.DeleteFile Filen,true
	end if
end function
'--------------------�ж��ļ��Ƿ����
function ExistsTxt(url,TxtName,MakeHtmlTime)
	set myfile=server.createobject("scripting.filesystemobject")
	Filen=lgmmserverpath&url&"\"&TxtName&".txt"
	if myfile.fileExists(Filen) then
		ExistsTxt=1
		set f=myfile.getfile(Filen)
		modifyTime=f.DateLastModified'�޸�ʱ��
		intSizeB = f.Size'�ļ���С
		if datediff("s",cdate(modifyTime),now)<MakeHtmlTime then
			set mytext=myfile.opentextfile(Filen)
			if intSizeB>0 then
			else
				ExistsTxt=0
			end if
		else
			ExistsTxt=0
		end if
	else
		ExistsTxt=0
	end if
end function
function uploadpic(UpFileType)
'************�ϴ�����
	MaxFileSize=3072
	serverpath= Server.MapPath("\")
	pyear=DatePart("yyyy",now())
	pmouth=DatePart("m",now()) 
	pday=DatePart("d",now()) 
	ptime=DatePart("h",now())&DatePart("n",now())&DatePart("s",now())  
	Set fso = CreateObject("Scripting.FileSystemObject")
	  If (fso.FolderExists(serverpath&"/admin1/file/"&pyear)) Then
	  Else
		Set f = fso.CreateFolder(serverpath&"/admin1/file/"&pyear)
	  End If
	  If (fso.FolderExists(serverpath&"/admin1/file/"&pyear&"/"&pmouth)) Then
	  Else
		Set f = fso.CreateFolder(serverpath&"/admin1/file/"&pyear&"/"&pmouth)
	  End If
  formPath="/admin1/file/"&DatePart("yyyy",now())&"/"&DatePart("m",now())&"/"
'***********************ͼƬ����
iCount=1
address_p=""
for each formName in upload.objFile ''�г������ϴ��˵��ļ�
    FoundErr=false
    EnableUpload=true
	set file=upload.file(formName)  ''����һ���ļ�����
	if file.FileSize>0 then         ''��� FileSize > 0 ˵�����ļ�����
			 if file.FileSize>(MaxFileSize*1024) then
				msg="�ļ���С���������ƣ����ֻ���ϴ�" & CStr(MaxFileSize) & "K���ļ�������Сͼ�ٴ���"
				FoundErr=True
			 end if
			 
			 filearray=split(file.FileName,".")
			 fileEXT=filearray(ubound(filearray))
			 EnableUpload=false
			 arrUpFileType=split(UpFileType,",")
			 EnableUpload=false
			 for i=0 to ubound(arrUpFileType)
			 	if lcase(fileEXT)=arrUpFileType(i) then
					EnableUpload=true
				end if
			 next
			if EnableUpload=false then
				msg=filearray(1)&" �����ļ����Ͳ������ϴ���\n\nֻ�����ϴ��⼸���ļ����ͣ�" & UpFileType
				FoundErr=True
			end if
			if FoundErr=True then
				response.Write("<script>alert('"&msg&"');window.history.back(1)</script>")
				response.End()
			end if
		 filenamet=pday&ptime&"."&fileEXT
		 if FoundErr=false then
		    file.SaveAs Server.mappath(formPath&filenamet)   ''�����ļ�
		    'msg=file.FilePath&file.FileName&"�ϴ��ɹ�����"
		    iCount=iCount+1
		 end if
		 formPath="/admin1/file/"&DatePart("yyyy",now())&"/"&DatePart("m",now())&"/"
	     address_p=formPath&filenamet
	 end if
	 set file=nothing
next
uploadpic=address_p
end function


'----------------�ͻ���Դ
function showlyfrom(comID)
 	sqlyy="select lyCode from comp_comly where com_id="&comID&""
	set rsyy=conn.execute(sqlyy)
	if not rsyy.eof or not rsyy.bof then
	while not rsyy.eof
		lycode=rsyy(0)
		sqlly="select meno from cate_product_ly where code="&lycode&""
		set rsly=conn.execute(sqlly)
		if not rsly.eof or not rsly.bof then
			showlyfrom=showlyfrom&","&rsly(0)
		end if
		rsly.close
		set rsly=nothing
		rsyy.movenext
	wend
	else
		showlyfrom=""
	end if
	rsyy.close
	set rsyy=nothing
end function
'------------ע����Դ
function showregFrom(fromid)
	sqlf="select meno from cate_compRegFrom where code ='"&fromid&"'"
	set rsf=conn.execute(sqlf)
	if not rsf.eof or not rsf.bof then
		showregFrom=rsf(0)
	else
		showregFrom=""
	end if
	rsf.close
	set rsf=nothing
end function
'------�б�ͷ��ѡ������
function topselectbutton(doaction,selecttype)
	if cstr(doaction)=cstr(selecttype) then
		topselectbutton="selected"
	else
		topselectbutton="unselect"
	end if
end function
'---------�绰����
function clearcomptel(com_tel)
	if not isnull(com_tel) and com_tel<>"" then
		arrtel=split(com_tel,"-")
		com_tel_=arrtel(ubound(arrtel))
		if com_tel_="" and ubound(arrtel)>1 then
			com_tel=arrtel(ubound(arrtel)-1)
		else
			com_tel=com_tel_
		end if
	end if
	com_tel=right(com_tel,8)
	'------------------�жϵ绰�ظ�
	if com_tel<>"" and not isnull(com_tel) then
		n=1
		b=""
		for i=1 to len(com_tel)
			a=mid(com_tel,i,1)
			if cstr(a)=cstr(b) then
				n=n+1
			end if
			b=a
		next
		if n>4 or left(com_tel,7)="1234567" or com_tel="888" or right(com_tel,7)="1234567" or right(com_tel,7)="6543210" or com_tel="7654321" or com_tel="87654321" then 
			com_tel=""
		end if
	end if
	clearcomptel=com_tel
end function
'---------�ֻ�����
function clearcompmobile(com_mobile)
	'----------------�ж��ֻ��ظ�
	if com_mobile<>"" and not isnull(com_mobile) then
		if not isnull(com_mobile) and com_mobile<>"" then
			arrcom_mobile=split(com_mobile,"-")
			com_mobile=arrcom_mobile(ubound(arrcom_mobile))
		end if
		n=1
		b=""
		a=""
		for i=1 to len(com_mobile)
			a=mid(com_mobile,i,1)
			if cstr(a)=cstr(b) then
				n=n+1
			end if
			b=a
		next
		if n>4 or left(com_mobile,7)="1234567" then 
			com_mobile=""
		end if
	end if
	clearcompmobile=com_mobile
end function
Function getHTTPPage(url) 
    Dim Http
    Set Http=Server.CreateObject("MSXML2.XMLHTTP.3.0") 
    With Http
        .open "GET",url,False
        .Send
    End With 
    On Error Resume Next 
    If Http.Status<>200 then 
        Set Http=Nothing 
        
        Exit function
    End if
	getHTTPPage=bytesToBSTR(Http.responseBody,"gb2312")
	if err.number<>0 then err.Clear 
End function

Function BytesToBstr(body,Cset) 
  dim objstream
  set objstream = Server.CreateObject("adodb.stream")
  objstream.Type = 1
  objstream.Mode =3
  objstream.Open
  objstream.Write body
  objstream.Position = 0
  objstream.Type = 2
  objstream.Charset = Cset
  BytesToBstr = objstream.ReadText 
  objstream.Close
  set objstream = nothing
End Function

%>