<%
 function   getHTTPPage(url,chartype)   
        dim   Http   
        set   http=createobject("MSXML2.XMLHTTP")     
        Http.open   "GET",url,false   
        Http.send()   
        if   Http.readystate<>4   then   
        exit   function   
        end   if   
        getHTTPPage=bytesToBSTR(Http.responseBody,chartype)
        set   http=nothing   
        if   err.number<>0   then   err.Clear   
  end   function  

  Function   BytesToBstr(body,Cset)   
	  dim   objstream   
	  set   objstream   =   Server.CreateObject("adodb.stream")   
	  objstream.Type   =   1   
	  objstream.Mode   =3   
	  objstream.Open   
	  objstream.Write   body   
	  objstream.Position   =   0   
	  objstream.Type   =   2   
	  objstream.Charset   =   Cset   
	  BytesToBstr   =   objstream.ReadText     
	  objstream.Close   
	  set   objstream   =   nothing   
  End   Function 
C_baidu_url="http://www.baidu.com/s?rn=100&q1=%E5%9B%9E%E6%94%B6%E5%85%89%E4%BC%8F%E7%84%8A%E5%B8%A6&pn=0&ie=utf-8"
C_baidu_all=getHTTPPage(C_baidu_url,"utf-8")
'response.Write(C_baidu_url)
response.Write(C_baidu_all)
%>
