<%
function getZhuavalue(content,fromstr,tostr)
	zhuastr=split(content,fromstr)
	zhuastr1=split(zhuastr(1),tostr)
	zhuastr2=RemoveHTML(zhuastr1(0))
	getZhuavalue=zhuastr2
end function
function getZhuaListvalue(content,fromstr,tostr)
	zhuastr=split(content,fromstr)
	zhuastr1=split(zhuastr(1),tostr)
	zhuastr2=zhuastr1(0)
	getZhuaListvalue=replace(zhuastr2,"&nbsp;","")
end function

Function getHTTPPage(url,htmcode)
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

Function RemoveHTML(strHTML) 
  Dim objRegExp, strOutput
  strHTML = replace(strHTML," ","")
  strHTML = replace(strHTML,chr(13),"")
  strHTML = replace(strHTML,chr(10),"")
  strHTML = replace(strHTML,chr(32),"")
    Set objRegExp = New Regexp
    objRegExp.IgnoreCase = True        '忽略大小写 
    objRegExp.Global = True        '设置为全文搜索 
    objRegExp.Pattern = "<script.+?>[\s\S]+?<\/script>"
    strOutput = objRegExp.Replace(strHTML, "")
    objRegExp.Pattern = "<style.+?>[\s\S]+?<\/style>"
    strOutput = objRegExp.Replace(strOutput, "")
    objRegExp.Pattern = "<.+?>"        '取闭合的<>
    strOutput = objRegExp.Replace(strOutput, "")
        
    strOutput = Replace(strOutput, "<", "〈")
    strOutput = Replace(strOutput, ">", "〉")
	strOutput = Replace(strOutput,"&nbsp;","")
    RemoveHTML = trim(strOutput)  
    Set objRegExp = Nothing
End Function
%>