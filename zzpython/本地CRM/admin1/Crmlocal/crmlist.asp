<%
Function toUTF8(szInput)
    Dim wch, uch, szRet
    Dim x
    Dim nAsc, nAsc2, nAsc3
    '����������Ϊ�գ����˳�����
    If szInput = "" Then
        toUTF8 = szInput
        Exit Function
    End If
    '��ʼת��
     For x = 1 To Len(szInput)
        '����mid�����ֲ�GB��������
        wch = Mid(szInput, x, 1)
        '����ascW��������ÿһ��GB�������ֵ�Unicode�ַ�����
        'ע��asc�������ص���ANSI �ַ����룬ע������
        nAsc = AscW(wch)
        If nAsc < 0 Then nAsc = nAsc + 65536
    
        If (nAsc And &HFF80) = 0 Then
            szRet = szRet & wch
        Else
            If (nAsc And &HF000) = 0 Then
                uch = "%" & Hex(((nAsc \ 2 ^ 6)) Or &HC0) & Hex(nAsc And &H3F Or &H80)
                szRet = szRet & uch
            Else
               'GB�������ֵ�Unicode�ַ�������0800 - FFFF֮��������ֽ�ģ��
                uch = "%" & Hex((nAsc \ 2 ^ 12) Or &HE0) & "%" & _
                            Hex((nAsc \ 2 ^ 6) And &H3F Or &H80) & "%" & _
                            Hex(nAsc And &H3F Or &H80)
                szRet = szRet & uch
            End If
        End If
    Next
        
    toUTF8 = szRet
End Function
Response.Charset = "GB2312"
keywords=toUTF8(request("keywords"))
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
	getHTTPPage=bytesToBSTR(Http.responseBody,"utf-8")
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
'Response.Charset = "GB2312"
Curl="http://python.zz91.com/complist_products/?keywords="&keywords
read=getHTTPPage(Curl)
Curl="http://python.zz91.com/complist_company/?keywords="&keywords
read1=getHTTPPage(Curl)
read=read&read1
arrread=split(read,",")
response.Write(read)
response.Write(ubound(arrread))

%>
