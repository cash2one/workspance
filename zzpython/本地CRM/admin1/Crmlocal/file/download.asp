<%
Dim Stream
Dim Contents
Dim FileName
Dim FileExt
Const adTypeBinary = 1
FileName = Request.QueryString("FileName")
nname=Request.QueryString("nname")
if FileName = "" Then
    Response.Write "无效文件名."
    Response.End
End if
' 下面是不希望下载的文件
FileExt1 = Mid(FileName, InStrRev(FileName, "/") + 1)
FileExt = Mid(FileName, InStrRev(FileName, ".") + 1)
Select Case UCase(FileExt)
    Case "ASP", "ASA", "ASPX", "ASAX", "MDB"
        Response.Write "受保护文件,不能下载."
        Response.End
End Select
'' 下载这个文件
'Response.Clear
'Response.ContentType = "application/octet-stream"
'Response.AddHeader "content-disposition", "attachment; filename=" & FileExt1
'Set Stream = server.CreateObject("ADODB.Stream")
'Stream.Type = adTypeBinary
'Stream.Open
'Stream.LoadFromFile Server.MapPath(FileName)
'While Not Stream.EOS
'    Response.BinaryWrite Stream.Read(1024 * 64)
'Wend
'Stream.Close
'Set Stream = Nothing
'Response.Flush
'Response.End

Response.Clear
Response.ContentType = "application/octet-stream"
Response.AddHeader "content-disposition", "attachment; filename=" & FileExt1
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
	getHTTPPage=Http.responseBody
	Set Http=Nothing 
	'getHTTPPage=bytesToBSTR(Http.responseBody,"GB2312")
	if err.number<>0 then err.Clear 
End function
Response.BinaryWrite(getHTTPPage(request.QueryString("Filename")))
%>
