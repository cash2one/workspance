<%
Response.Expires = -1
Response.AddHeader "Pragma","no-cache"
Response.AddHeader "cache-ctrol","no-cache"
dim zNum,i,j
dim Ados,Ados1
Randomize timer
zNum = cint(8999*Rnd+1000)
Session("GetCode") = zNum
response.Write(zNum)
%>
