<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<script>
function UTF8UrlEncode(input){    
   
        var output = "";    
   
        var currentChar = '';    
   
        for(var counter = 0; counter < input.length; counter++){    
   
            currentChar = input.charCodeAt(counter);    
   
            if((0 <= currentChar) && (currentChar <= 127))    
   
                output = output + UTF8UrlEncodeChar(currentChar);    
   
            else   
   
                output = output + encodeURIComponent(input.charAt(counter));    
   
        }    
   
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
	function UTF8UrlEncodeChar(input){    
   
        if(input <= 0x7F) return "%" + input.toString(16);    
   
        var leadByte = 0xFF80;    
   
        var hexString = "";    
   
        var leadByteSpace = 5;    
   
        while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
   
            hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
   
            leadByte = (leadByte >> 1);    
   
            leadByteSpace--;    
   
            input = input >> 6;    
   
        }    
   
        return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
   
	} 
</script>
</head>

<body>
<%
username=request.QueryString("username")
sql="select com_email,com_name from comp_info where com_id="&request.QueryString("com_id")&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	com_email=rs(0)
	com_name=rs(1)
end if
rs.close
set rs=nothing
response.Write("正在加载...")
userid=request.QueryString("userid")
mbflag=request.QueryString("mbflag")
'response.Redirect("http://adminasto.zz91.com/openConfirm/?com_id="&request("com_id")&"&userName="&request("userName")&"&personid="&session("personid")&"&com_email="&com_email&"&com_name="&com_name&"")
'response.End()
%>
<script>
window.location="http://adminasto.zz91.com/openConfirm1/?com_id=<%=request("com_id")%>&mbflag=<%=mbflag%>&userid=<%=userid%>&username="+UTF8UrlEncode("<%=username%>")+"&personid=<%=session("personid")%>&com_email=<%=com_email%>&com_name="+UTF8UrlEncode("<%=com_name%>")
</script>
</body>
</html>
<%conn.close
set conn=nothing
%>
