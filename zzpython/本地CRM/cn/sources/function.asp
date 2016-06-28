<%
Function RegExpColor(patrn, strng)'搜索时，关键字加背景色
  'Dim regEx, Match, Matches
  Set regEx = New RegExp
  regEx.Pattern = patrn
  regEx.IgnoreCase = True
  regEx.Global = True
  Set Matches = regEx.Execute(strng)
  lastposition=0
  strlen=len(strng)
  For Each Match in Matches
  regstr=regstr&mid(strng,lastposition+1,Match.FirstIndex-lastposition)
  if instr("&lt;",Match.Value)>0 or instr("&gt;",Match.Value)>0 or instr("<br>",Match.Value)>0 or instr("&nbsp;",Match.Value)>0 or instr("''",Match.Value)>0 then
  regstr=regstr&Match.Value
  else
  regstr=regstr&"<span style=""background-color:#F8FA7B"">"&Match.Value&"</span>"
  end if
  'lastposition=Match.FirstIndex+len(Match.Value)
  lastposition=Match.FirstIndex+len(Match.Value)
  Next
  regstr=regstr&right(strng,len(strng)-lastposition)
  RegExpColor = RegStr
End Function

function replacehtml(inputstr)
	if inputstr<>"" then
	replacehtml=replace(replace(replace(replace(replace(inputstr,"<","&lt;"),">","&gt;"),chr(13),"<br>")," ","&nbsp;"),"'","''")
	else
	replacehtml=""
	end if
end function
function replacetext(inputstr)
	if inputstr<>"" then
	replacetext=replace(replace(replace(replace(replace(inputstr,"&lt;","<"),"&gt;",">"),"<br>",chr(13)),"<br>"," "),"''","'")
	else
	replacetext=""
	end if
end function
function replacequot(inputstr)
	if inputstr<>"" then
	replacequot=replace(inputstr,"'","”")
	else
	replacequot=""
	end if
end function
function replacequot1(inputstr)
	if inputstr<>"" then
	replacequot1=replace(inputstr,"''","'")
	else
	replacequot1=""
	end if
end function

 '二进制相互转换 
 Function getByteString(StringStr) 
  getByteString=""
  For i=1 to Len(StringStr) 
    char=Mid(StringStr,i,1) 
   getByteString=getByteString&chrB(AscB(char)) 
  Next 
 End Function 
 Function getString(StringBin) 
        getString ="" 
        For intCount = 1 to LenB(StringBin) 
         getString = getString & chr(AscB(MidB(StringBin,intCount,1)))  
        Next 
 End Function

function sendemail(mailTo,mailBody,mailTopic,emlattach)
    '***************根据需要设置常量开始***************** 
	Dim ConstFromNameCn,ConstFromNameEn,ConstFrom,ConstMailDomain,ConstMailServerUserName,ConstMailServerPassword 
	mailCharset="GB2312"
	mailContentType="text/html"
	ConstFromNameCn = "中国再生资源交易网"'发信人中文姓名(发中文邮件的时候使用)，例如‘张三’ 
	ConstFromNameEn = "RECYCLECHINA"'发信人英文姓名(发英文邮件的时候使用)，例如‘zhangsan’ 
	ConstFrom = "zz91@zz91.com"'发信人邮件地址，例如‘zhangsan@163.com’ 
	ConstMailDomain = "mail.zz91.com"'smtp服务器地址，例如smtp.163.com 
	ConstMailServerUserName = "zz91@zz91.com"'smtp服务器的信箱登陆名，例如‘zhangsan’。注意要与发信人邮件地址一致！ 
	ConstMailServerPassword = "88888888"'smtp服务器的信箱登陆密码 
	'***************根据需要设置常量结束***************** 
	'-----------------------------以下内容无需改动------------------------------ 
	'On Error Resume Next 
	Dim myJmail 
	Set myJmail = Server.CreateObject("JMail.Message") 
	myJmail.silent = true
	myJmail.Logging = True'记录日志 
	myJmail.ISOEncodeHeaders = False'邮件头不使用ISO-8859-1编码 
	myJmail.ContentTransferEncoding = "base64"'邮件编码设为base64 
	myJmail.AddHeader "Priority","3"'添加邮件头,不要改动！ 
	myJmail.AddHeader "MSMail-Priority","Normal"'添加邮件头,不要改动！ 
	myJmail.AddHeader "Mailer","Microsoft Outlook Express 6.00.2800.1437"'添加邮件头,不要改动！ 
	myJmail.AddHeader "MimeOLE","Produced By Microsoft MimeOLE V6.00.2800.1441"'添加邮件头,不要改动！ 
	myJmail.Charset = mailCharset 
	myJmail.ContentType = mailContentType 
	If UCase(mailCharset) = "GB2312" Then 
	myJmail.FromName = ConstFromNameCn 
	Else 
	myJmail.FromName = ConstFromNameEn 
	End If 
	myJmail.From = ConstFrom 
	myJmail.Subject = mailTopic 
	myJmail.Body = mailBody 
	myJmail.AddRecipient mailTo 
	myJmail.MailDomain = ConstMailDomain 
	myJmail.MailServerUserName = ConstMailServerUserName 
	myJmail.MailServerPassword = ConstMailServerPassword 
	myJmail.Send ConstMailDomain 
	myJmail.Close 
	Set myJmail=nothing 
	If Err Then 
	sendemail=false
	Err.Clear 
	Else 
	sendemail=true
	End If 
	On Error Goto 0 
end function

function b_s_class(myform,sc)'大类小类的下拉选择框
	classbig=0
	if sc<>0 then
		set rssc=server.createobject("adodb.recordset")
		sql="select * from cls_s where cs_id="&sc
		rssc.open sql,conn,1,1
		if not rssc.eof and not rssc.bof then
		classbig=rssc("cs_cb_id")
		end if
	rssc.close
	set rssc=nothing
end if

dim rs_cbcs
dim sql
dim count
set rs_cbcs=server.createobject("adodb.recordset")
sql = "select * from cls_s"
rs_cbcs.open sql,conn,1,1
%>
<script language = "JavaScript">
var onecount;
onecount=0;
subcat = new Array();
        <%
        count = 0
        do while not rs_cbcs.eof 
        %>
subcat[<%=count%>] = new Array("<%= trim(rs_cbcs("cs_chn_name"))%>","<%= trim(rs_cbcs("cs_cb_id"))%>","<%= trim(rs_cbcs("cs_id"))%>");
        <%
        count = count + 1
        rs_cbcs.movenext
        loop
        rs_cbcs.close
        %>
onecount=<%=count%>;

function changelocation(locationid)
    {
    document.<%=myform%>.sclassid.length = 0; 
    var locationid=locationid;
    var i;
    for (i=0;i < onecount; i++)
        {
            if (subcat[i][1] == locationid)
            { 
			   //alert(locationid)
			   //alert("haha");
			   //alert(subcat[i][0]);
			   //alert(subcat[i][2])
			   //alert(subcat[i][2]);
                document.<%=myform%>.sclassid.options[document.<%=myform%>.sclassid.length] = new Option(subcat[i][0], subcat[i][2]);
            }        
        }
        
    }    
</script>
<%

        sql = "select * from cls_b"
        rs_cbcs.open sql,conn,1,1
	if rs_cbcs.eof and rs_cbcs.bof then
	response.write "请先添加栏目。"
	response.end
	else
%>
<select name="classid" onChange="changelocation(document.<%=myform%>.classid.options[document.<%=myform%>.classid.selectedIndex].value)" size="1">
        <option <%if classbig=0 or classbig=rs_cbcs("cb_id") then response.write "selected"%> value="<%=trim(rs_cbcs("cb_id"))%>"><%=trim(rs_cbcs("cb_chn_name"))%></option>
<%      dim selclass
        if classbig<>0 then
		selclass=classbig
		else
		selclass=rs_cbcs("cb_id")
		end if
		response.Write selclass
        rs_cbcs.movenext
        do while not rs_cbcs.eof
%>
        <option value="<%=trim(rs_cbcs("cb_id"))%>" <%if classbig=rs_cbcs("cb_id") then response.write "selected"%>><%=trim(rs_cbcs("cb_chn_name"))%></option>
       
<%
        rs_cbcs.movenext
        loop
	end if
        rs_cbcs.close
%>
</select> 
<select name="sclassid">                  
<%sql="select * from cls_s where cs_cb_id="&selclass
rs_cbcs.open sql,conn,1,1
if not rs_cbcs.eof and not rs_cbcs.bof  then
%>
        <option <%if sc=0 or sc=rs_cbcs("cs_id") then response.write "selected"%> value="<%=rs_cbcs("cs_id")%>"><%=rs_cbcs("cs_chn_name")%></option>
<% rs_cbcs.movenext
do while not rs_cbcs.eof%>
        <option <%if sc=rs_cbcs("cs_id") then response.write "selected"%> value="<%=rs_cbcs("cs_id")%>"><%=rs_cbcs("cs_chn_name")%></option>
<% rs_cbcs.movenext
loop
end if
        rs_cbcs.close
        set rs_cbcs = nothing
        'conn.Close
        'set conn = nothing
%>
</select>
<%'response.Write selclas
end function%>

<%function b_s_class_sconly(myform,sc)'大类小类的下拉选择框,改进只有大类显示，小类不显示
classbig=0
if sc<>0 then
	set rssc=server.createobject("adodb.recordset")
	sql="select * from cls_s where cs_id="&sc
	rssc.open sql,conn,1,1
	if not rssc.eof and not rssc.bof then
	classbig=rssc("cs_cb_id")
	end if
rssc.close
set rssc=nothing
else

classbig=cint(session("bigclassid"))
if classbig=0 then classbig=1
set rs_cs00=server.createobject("adodb.recordset")
sqlcs00 = "select * from cls_s where cs_default=1 and cs_cb_id="&classbig
rs_cs00.open sqlcs00,conn,1,1
if not rs_cs00.eof and not rs_cs00.bof then
	sc=rs_cs00("cs_id")
end if
rs_cs00.close
set rs_cs00=nothing
	
end if

dim rs_cbcs
dim sql
dim count
set rs_cbcs=server.createobject("adodb.recordset")
sql = "select * from cls_s where cs_default=1"
rs_cbcs.open sql,conn,1,1
%>
<script language = "JavaScript">
var onecount;
onecount=0;
subcat = new Array();
        <%
        count = 0
        do while not rs_cbcs.eof 
        %>
subcat[<%=count%>] = new Array("<%= trim(rs_cbcs("cs_chn_name"))%>","<%= trim(rs_cbcs("cs_cb_id"))%>","<%= trim(rs_cbcs("cs_id"))%>");
        <%
        count = count + 1
        rs_cbcs.movenext
        loop
        rs_cbcs.close
        %>
onecount=<%=count%>;

function changelocation(locationid)
    {
    document.<%=myform%>.sclassid.length = 0; 
    var locationid=locationid;
    var i;
    for (i=0;i < onecount; i++)
        {
            if (subcat[i][1] == locationid)
            { 
			   //alert(locationid)
			   //alert("haha");
			   //alert(subcat[i][0]);
			   //alert(subcat[i][2])
			   //alert(subcat[i][2]);
                document.<%=myform%>.sclassid.value=subcat[i][2];
            }        
        }
        
    }    
</script>
<%

        sql = "select * from cls_b where delflag=0"
        rs_cbcs.open sql,conn,1,1
	if rs_cbcs.eof and rs_cbcs.bof then
	response.write "请先添加栏目。"
	response.end
	else
%>
<select name="classid" onChange="changelocation(document.<%=myform%>.classid.options[document.<%=myform%>.classid.selectedIndex].value)" size="1">
        <option <%if classbig=0 or classbig=rs_cbcs("cb_id") then response.write "selected"%> value="<%=trim(rs_cbcs("cb_id"))%>"><%=trim(rs_cbcs("cb_chn_name"))%></option>
<%      dim selclass
        if classbig<>0 then
		selclass=classbig
		else
		selclass=rs_cbcs("cb_id")
		end if
		response.Write selclass
        rs_cbcs.movenext
        do while not rs_cbcs.eof
%>
        <option value="<%=trim(rs_cbcs("cb_id"))%>" <%if classbig=rs_cbcs("cb_id") then response.write "selected"%>><%=trim(rs_cbcs("cb_chn_name"))%></option>
       
<%
        rs_cbcs.movenext
        loop
	end if
        rs_cbcs.close
%>
</select> 
    
   <%'=selclass%>              
<%sql="select * from cls_s where cs_default=1 and cs_cb_id="&selclass
rs_cbcs.open sql,conn,1,1
if not rs_cbcs.eof and not rs_cbcs.bof  then%>
<input name="sclassid"  type="hidden" id="sclassid" value="<%=rs_cbcs("cs_id")%>">
<%else%>
<input name="sclassid"  type="hidden" id="sclassid" value="12">
<%end if
        rs_cbcs.close
        set rs_cbcs = nothing
%>
<%
end function%>
<%'显示 供应 求购 合作 的一个下拉框
function posttypelist(ptype)
%>
<select name="ptype" id="ptype">
        <option value="" >--请选择--</option>
        <option value="1" <%if ptype=1 then response.write "selected"%>>供应信息</option>
        <option value="2" <%if ptype=2 then response.write "selected"%>>求购信息</option>
        <option value="3" <%if ptype=3 then response.write "selected"%>>合作信息</option>
</select>
<%end function%>
<%'显示 供应 求购 合作 的一个下拉框
function posttypelist_radio(ptype)
%>
<input name="ptype" type="radio" value="1" <%if ptype=1 then response.write "checked"%>/>供应信息
<input name="ptype" type="radio" value="2" <%if ptype=2 then response.write "checked"%>/>求购信息
<input name="ptype" type="radio" value="3" <%if ptype=3 then response.write "checked"%>/>合作信息
<%
end function
'************************************
function cate_radio1(tb,a,b)
	set rs_cate=server.CreateObject("adodb.recordset")
	sql_cate="select * from "&tb&" order by ord desc"
	rs_cate.open sql_cate,conn,1,1
	place=""
	if not rs_cate.eof or not rs_cate.bof then
		do while not rs_cate.eof
		if b="" then
		b="10"
		end if
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<input type='radio' name='"&a&"' value='" & rs_cate("code") & "' checked='checked' />"& rs_cate(2)
		else
		place=place&"<input type='radio' name='"&a&"' value='" & rs_cate("code") & "' />"& rs_cate(2)
		end if
		rs_cate.movenext
		loop
		rs_cate.close
		set rs_cate=nothing
	end if
	response.Write(place)
end function
Function Cate_Radio(tb,a,b)
	if  isArray(Application("Cache_"& tb)) = False  then
		set Rs_cate=server.CreateObject("adodb.recordset")
		sql_cate="select * from "&tb&" order by ord desc"
		Rs_cate.open sql_cate,conn,1,1
		place=""
		RadioArray=Rs_cate.GetRows(Rs_cate.recordcount)
		Rs_cate.close
		Set Rs_cate=Nothing
		Application.Lock 
		Application("Cache_"& tb &"")=RadioArray
		Application.UnLock 
		call GetRadiovalue (RadioArray,a,b)
	else
		RadioArray=Application("Cache_"&tb)
		call GetRadiovalue (RadioArray,a,b)
		'Application("Cache_"&tb)=false
	end if
End Function
Function GetRadiovalue(RadioArray,a,b)
    if b="" then
		b="10"
	end if
	For i=0 To Ubound(RadioArray,2)
		if cstr(trim(b))=cstr(trim(RadioArray(1,i))) then
			place=place&"<input type='radio' name='"&Trim(a)&"' value='" & Trim(RadioArray(1,i)) & "' checked='checked' />"& Trim(RadioArray(2,i))
		else
			place=place&"<input type='radio' name='"&Trim(a)&"' value='" & Trim(RadioArray(1,i)) & "' />"& Trim(RadioArray(2,i))
		end if
	Next
	Response.Write(place)
End Function
%>
<%'大类的下拉框
function bigclasslit(selbcid)%>
<select name="csbigclass" id="csbigclass">
	<%
	set rsbc=server.CreateObject("ADODB.recordset")
	sql="select * from cls_b"
	rsbc.open sql,conn,1,1
	if not rsbc.eof then
	i=0
	while not rsbc.eof 
	%>
      <option value="<%=rsbc("cb_id")%>" <%if rsbc("cb_id")=selbcid or (selbcid=0 and i=0) then response.write "selected"%>><%=rsbc("cb_chn_name")%></option>
	  <%
	i=i+1
	  rsbc.movenext
	  wend
	  end if
	  rsbc.close
	  set rsbc=nothing
	  %>
</select>

<%end function
%>
<%
Function KeyGeNl(iKeyLength)'safekey
Dim k, iCount, strMyKey
lowerbound = 65'A 
upperbound = 122'z
Randomize ' Initialize random-number generator.
for i = 1 to iKeyLength
s = 255
k = Int(((upperbound - lowerbound) + 1) * Rnd + lowerbound)
strMyKey = strMyKey & Chr(k) & ""
next
KeyGeNl = strMyKey
End Function

Function KeyGeNpass(iKeyLength)'pass
Dim k, iCount, strMyKey
lowerbound = 0'0 
upperbound = 9'Z
Randomize ' Initialize random-number generator.
for i = 1 to iKeyLength
s = 255
k = Int(((upperbound - lowerbound) + 1) * Rnd + lowerbound)
while k>=58 and k<=64
k = Int(((upperbound - lowerbound) + 1) * Rnd + lowerbound)
wend
strMyKey = strMyKey & k & ""
next
KeyGeNpass = lcase(strMyKey)'小写字母
End Function

function getpdtkind(pdtkindkey)
select case pdtkindkey
case 1 : getpdtkind="供应"
case 2 : getpdtkind="求购"
case 3 : getpdtkind="合作"
case else : getpdtkind=""
end select
end function

function getcomstatus1(comreg,comcheck)
if comreg=1 and comcheck=1 then
getcomstatus1="再生通"
else
getcomstatus1=""
end if
end function

function getcomstatus1_en(comreg,comcheck)
if comreg=1 and comcheck=1 then
getcomstatus1_en="Paid"
else
getcomstatus1_en=""
end if
end function




function fpage1(byref rsf,fpsize,byref fpage)
fpage0=trim(request("fpage"))'fpage为当前页码
  if fpage0<>"" then
  fpage=cint(fpage0)
  else
  fpage=1
  end if
rsf.pagesize=fpsize
rsf.absolutepage=fpage
fpage1=rsf.pagecount
end function
function fpage2(fpage,fpall)'fpage当前页 fpall 总页数
qurstr=Request.ServerVariables("QUERY_STRING")
if qurstr<>"" then
qurstr=replace(qurstr,"fpage="&cstr(fpage),"")
if qurstr<>"" and right(qurstr,1)<>"&" then qurstr=qurstr&"&"
end if
if fpall>1 and fpage>1 then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage-1%><%=sear%>'>上一页</a>
<%
end if
for fpi=1 to fpall
if fpage<>fpi then

%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpi%><%=sear%>'><%=fpi%></a>
<%else%>
[<b><%=fpi%></b>]
<%end if
next
if fpall>1 and fpage<fpall then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage+1%><%=sear%>'>下一页</a>
<%
end if
end function
'***************************
function fpage22(fpage,fpall)'fpage当前页 fpall 总页数
'qurstr=Request.ServerVariables("QUERY_STRING")
if qurstr<>"" then
qurstr=replace(qurstr,"fpage="&cstr(fpage),"")
if qurstr<>"" and right(qurstr,1)<>"&" then qurstr=qurstr&"&"
end if
if fpall>1 and fpage>1 then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage-1%><%=sear%>'>上一页</a>
<%
end if
for fpi=1 to fpall
if fpage<>fpi then

%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpi%><%=sear%>'><%=fpi%></a>
<%else%>
[<b><%=fpi%></b>]
<%end if
next
if fpall>1 and fpage<fpall then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage+1%><%=sear%>'>下一页</a>
<%
end if
end function
'****************************

function fpage2_myparam(fpage,fpall,myparam)'fpage当前页 fpall 总页数
if myparam<>"" then qurstr=myparam else qurstr=Request.ServerVariables("QUERY_STRING")

if qurstr<>"" then
qurstr=replace(qurstr,"fpage="&cstr(fpage),"")
if qurstr<>"" and right(qurstr,1)<>"&" then qurstr=qurstr&"&"
end if
if fpall>1 and fpage>1 then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage-1%>'>上一页</a>
<%
end if
for fpi=1 to fpall
if fpage<>fpi then

%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpi%>'><%=fpi%></a>
<%else'当前页%>
<%=fpi%>
<%end if
next
if fpall>1 and fpage<fpall then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage+1%>'>下一页</a>
<%
end if
end function
%>

<%function fpage02(fpage,fpall)'fpage当前页 fpall 总页数%>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<%
qurstr=Request.ServerVariables("QUERY_STRING")
if qurstr<>"" then
qurstr=replace(qurstr,"fpage="&cstr(fpage),"")
if qurstr<>"" and right(qurstr,1)<>"&" then qurstr=qurstr&"&"
end if
if fpall>1 then%>
<form name="formpage">
    <td><select name="menu1" onChange="MM_jumpMenu('self',this,0)">
	<%for fpi=1 to fpall%>
    <option value='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpi%>' <%if fpage=fpi then response.write "selected"%>><%=fpi%></option>
	<%next%>
  </select></td>
</form>
<%
end if%><td>&nbsp;&nbsp;
<%response.write fpage&"/"&fpall&" "
if fpall>1 and fpage>1 then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage-1%>'>上一页</a>
<%
end if
if fpall>1 and fpage<fpall then%>
<a href='../<%=Request.ServerVariables("PATH_INFO")%>?<%=qurstr%>fpage=<%=fpage+1%>'>下一页</a>
<%end if%></td>
</tr>
</table>
<%end function%>


<%function getymd(inputtime)
getymd=year(inputtime)&"-"&month(inputtime)&"-"&day(inputtime)
end function

function jycode()
dim GetCode,valicode
if trim(request("GetCode"))<>"" then
GetCode=int(trim(request("GetCode")))
else
GetCode=11111
end if
valicode=int(Session("GetCode"))
if GetCode<>valicode then
response.write "<script language='javascript'>alert('校验码错误，请核对!');window.history.go(-1);</script>"
response.end
end if
end function
%>

<%
function getcomkeywords(comkey)
if trim(comkey)<>"" then
comkeywords=split(comkey,",")
end if

	set rsbc=server.CreateObject("ADODB.recordset")
	sql="select * from cls_b where delflag=0"
	rsbc.open sql,conn,1,1
	if comkey="" or isnull(comkey) then
	comkeyub=-1
	else
	comkeyub=ubound(comkeywords)
	end if
	comkeyi=0
	if not rsbc.eof and not rsbc.bof then'bc有记录
		if comkeyub>=0 then'comkey有记录
	
			while not rsbc.eof
			%>
			<input name="ckeywords" type="checkbox" id="ckeywords" value="<%=rsbc("cb_id")%>" <%
			if comkeyi<=comkeyub then
			if rsbc("cb_id")=cint(trim(comkeywords(comkeyi))) then
				response.write "checked"
				comkeyi=comkeyi+1
			end if
			end if
			%>><%=rsbc("cb_chn_name")%>
			<%
			rsbc.movenext
			wend
		else'comkey无记录
	
			while not rsbc.eof
			%>
			<input name="ckeywords" type="checkbox" id="ckeywords" value="<%=rsbc("cb_id")%>"><%=rsbc("cb_chn_name")%>
	  <%
			rsbc.movenext
			wend
		end if
	end if
	rsbc.close
	set rsbc=nothing
end function
%>

<%
'*********************************************8
function getcomkindlist(comkind)
	sqlck="select * from comkind"
set rsck=server.CreateObject("ADODB.recordset")
rsck.open sqlck,conn,1,1
%>
	<select name="ckind" id="ckind">
	<%while not rsck.eof%>
	  <option value='<%=rsck("ck_id")%>' <%if (comkind=0 and rsck("ck_id")=1) or rsck("ck_id")=comkind then response.write "selected"%>><%=rsck("ck_chn_name")%></option>
	<%rsck.movenext
	wend
	rsck.close
	set rsck=nothing
	%>
    </select>
	<%end function
'*************************************************
%>
<%function getcomkindtext(comkind)
	comkind=cstr(comkind)
	sqlck="select * from comkind where ck_id="&comkind
	set rsck=server.CreateObject("ADODB.recordset")
	rsck.open sqlck,conn,1,1
	if not rsck.eof and not rsck.bof then getcomkindtext=rsck("ck_chn_name") else getcomkindtext="错误"
	rsck.close
	set rsck=nothing
end function%>
<%
function getcountrylist(countryid)
place="<select name='ccountry' id='ccountry' style='width:120'>"
set rscountry=server.CreateObject("ADODB.recordset")
sql="select ctr_id,ctr_chn_name,ctr_index,ctr_first from countrys where ctr_first=1 order by ctr_index asc"
rscountry.open sql,conn,1,1
if not rscountry.eof then
	while not rscountry.eof 
		place=place&"<option value="&rscountry("ctr_id")
	if rscountry("ctr_id")=countryid or (countryid=0 and rscountry("ctr_id")=1) then
		place=place&" selected"
	end if
		place=place&">"&rscountry("ctr_chn_name")&"</option>"
	rscountry.movenext
	wend
end if
rscountry.close
set rscountry=nothing
set rscountry=server.CreateObject("ADODB.recordset")
sql="select ctr_id,ctr_chn_name,ctr_index,ctr_first from countrys order by ctr_chn_name asc"
rscountry.open sql,conn,1,1
if not rscountry.eof then
	place=place&"<option value="""">--更多(A to Z)--</option>"
while not rscountry.eof 
	place=place&"<option value="&rscountry("ctr_id")
	if (rscountry("ctr_id")=countryid or (countryid=0 and rscountry("ctr_id")=1)) and rscountry("ctr_first")=0 then
	place=place&" selected"
	end if
	place=place&">"&rscountry("ctr_chn_name")&"</option>"
rscountry.movenext
wend
end if
rscountry.close
set rscountry=nothing
place=place&"</select>"
response.Write(place)
end function
'****************************************
%>
<%
function getcountrytext(ctr_id)
if ctr_id=0 then getcountrytext=""
set rscountry=server.CreateObject("ADODB.recordset")
	sql="select * from countrys where ctr_id="&cstr(ctr_id)
	rscountry.open sql,conn,1,1
	if not rscountry.eof then
	getcountrytext=rscountry("ctr_chn_name")
	end if
	rscountry.close
	set rscountry=nothing
end function
%>

<%function getposttypetext(typeid)
select case typeid
case 1 : getposttypetext="供应"
case 2 : getposttypetext="求购"
case 3 : getposttypetext="合作"
case else : getposttypetext="错误"
end select
end function%>
<%
Function lencontrol(inputstr, lenall)
	Dim i, k
	k = 0
	str = trim(inputstr)
	For i = 1 to Len(inputstr)
		IF Abs(Asc(Mid(inputstr, i, 1))) > 255 Then k = k + 2 Else k = k + 1
		IF k >= lenall Then
			inputstr = Left(inputstr, i-1) & "..."
			Exit For
		End IF
	Next
	lencontrol = inputstr
End Function
'***************************
function cates(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" order by ord asc"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"'>"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then
	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function
''''''''''''''''''''''''''''''''''''''''''
function shows(a,b)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select meno from "&a&" where code='"&b&"'"
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(trim(rs_selet("meno")))
end if
rs_selet.close
set rs_selet=nothing
end function
%>
<%function b_s_class_sconly_en(myform,sc)'大类小类的下拉选择框,改进只有大类显示，小类不显示
classbig=0
if sc<>0 then
	set rssc=server.createobject("adodb.recordset")
	sql="select * from cls_s where cs_id="&sc
	rssc.open sql,conn,1,1
	if not rssc.eof and not rssc.bof then
	classbig=rssc("cs_cb_id")
	end if
rssc.close
set rssc=nothing

else

classbig=cint(session("bigclassid"))
if classbig=0 then classbig=1
set rs_cs00=server.createobject("adodb.recordset")
sqlcs00 = "select * from cls_s where cs_default=1 and cs_cb_id="&classbig
rs_cs00.open sqlcs00,conn,1,1
if not rs_cs00.eof and not rs_cs00.bof then
	sc=rs_cs00("cs_id")
end if
rs_cs00.close
set rs_cs00=nothing
	
end if

dim rs_cbcs
dim sql
dim count
set rs_cbcs=server.createobject("adodb.recordset")
sql = "select * from cls_s where cs_default=1"
rs_cbcs.open sql,conn,1,1
%>
<script language = "JavaScript">
var onecount;
onecount=0;
subcat = new Array();
        <%
        count = 0
        do while not rs_cbcs.eof 
        %>
subcat[<%=count%>] = new Array("<%= trim(rs_cbcs("cs_eng_name"))%>","<%= trim(rs_cbcs("cs_cb_id"))%>","<%= trim(rs_cbcs("cs_id"))%>");
        <%
        count = count + 1
        rs_cbcs.movenext
        loop
        rs_cbcs.close
        %>
onecount=<%=count%>;

function changelocation(locationid)
    {
    document.<%=myform%>.sclassid.length = 0; 
    var locationid=locationid;
    var i;
    for (i=0;i < onecount; i++)
        {
            if (subcat[i][1] == locationid)
            { 
			   //alert(locationid)
			   //alert("haha");
			   //alert(subcat[i][0]);
			   //alert(subcat[i][2])
			   //alert(subcat[i][2]);
                document.<%=myform%>.sclassid.value=subcat[i][2];
            }        
        }
        
    }    
</script>
<%

        sql = "select * from cls_b"
        rs_cbcs.open sql,conn,1,1
	if rs_cbcs.eof and rs_cbcs.bof then
	response.write "请先添加栏目。"
	response.end
	else
%>
<select name="classid" onChange="changelocation(document.<%=myform%>.classid.options[document.<%=myform%>.classid.selectedIndex].value)" size="1">
        <option <%if classbig=0 or classbig=rs_cbcs("cb_id") then response.write "selected"%> value="<%=trim(rs_cbcs("cb_id"))%>"><%=trim(rs_cbcs("cb_eng_name"))%></option>
<%      dim selclass
        if classbig<>0 then
		selclass=classbig
		else
		selclass=rs_cbcs("cb_id")
		end if
		response.Write selclass
        rs_cbcs.movenext
        do while not rs_cbcs.eof
%>
        <option value="<%=trim(rs_cbcs("cb_id"))%>" <%if classbig=rs_cbcs("cb_id") then response.write "selected"%>><%=trim(rs_cbcs("cb_eng_name"))%></option>
       
<%
        rs_cbcs.movenext
        loop
	end if
       
%>
</select> 
    
   <%'=selclass%>              
<%
set rs_cbcs=server.createobject("adodb.recordset")
sql="select * from cls_s where cs_default=1 and cs_cb_id="&selclass
rs_cbcs.open sql,conn,1,1
if not rs_cbcs.eof and not rs_cbcs.bof  then%>
<input name="sclassid"  type="hidden" id="sclassid" value="<%=rs_cbcs("cs_id")%>">
<%else%>
<input name="sclassid"  type="hidden" id="sclassid" value="12">
<%end if
        rs_cbcs.close
        set rs_cbcs = nothing
%>
<%
end function
function dselectpp(a,dsort,formname)
'response.write "<table width='100%' border='0' cellpadding='0' cellspacing='0'>"& chr(13)
'response.write"        <tr>"& chr(13)
'response.write"          <td valign='top'>"& chr(13)
            
'response.write"           <table width='100%' border=0 align='center' cellpadding=0 cellspacing=0 >"& chr(13)
'response.write"                    <tr>"& chr(13)
'response.write"                      <form name='company_sec' method='get'  action='product_admin.asp'>"& chr(13)
                       
response.write"                        <input type='hidden' name='startRow' value='0'>"& chr(13)
 response.write "                      <input TYPE='hidden' NAME='maxPage' VALUE='10'>"& chr(13)

 'response.write "                      <td align=center>"& chr(13)
 'response.write"                        <table border=0 cellpadding=0 cellspacing=0 width='100%'>"& chr(13)
 'response.write "                         <tr>"& chr(13)
 'response.write "                            <td>"& chr(13)
 response.write "                              <input type='hidden' name='trade_code'>"& chr(13)
 response.write "                              <select name='secRootCatId1' onchange='changeCategory1(this)' id='secRootCatId1' style='width:80'>"& chr(13)
 response.write "                                <option selected value=''>请选择省份</option>"& chr(13)
 response.write "                              </select>"& chr(13)
 response.write "                              <select name='secSubCatId1' id='secSubCatId1' style='width:100'>"& chr(13)
 response.write  "                               <option selected value=''>请选择城市</option>"& chr(13)
 response.write  "                             </select>"& chr(13)
 'response.write  "                           </td>"& chr(13)
 'response.write  "                            </tr>"& chr(13)
 'response.write  "                          </table>"& chr(13)
  'response.write  "                       </td>"& chr(13)
 ' response.write  "                     </form>"& chr(13)
 ' response.write  "                   </tr>"& chr(13)
 ' response.write  "                 </table>"& chr(13)
 ' response.write  "         </td>"& chr(13)
  'response.write  "       </tr>"& chr(13)
  'response.write  "             <tr>"& chr(13)
 ' response.write  "               <td>"& chr(13)
	
response.write "<script>" & chr(13)   
response.write "   var catArr1 = new Array();"  & chr(13)
response.write "var cur1;"  & chr(13)
sql1="select * from "&a&" where code like '__00' "
		  set rs1=server.createobject("adodb.recordset")
                                 rs1.open sql1,conn,1,1
								 if not rs1.eof then
								 
								do while not rs1.eof

response.write " cur1 = new Category('" & trim(rs1("code")) & "','" & trim(rs1("meno")) & "');"  & chr(13)
response.write " catArr1 = catArr1.concat(cur1);"   & chr(13)
set rs2=server.createobject("adodb.recordset")

      sql2="select * from "&a&" where code like '"& left(trim(rs1("code")),2) &"__' and code<>'"&rs1("code")&"' order by code desc"
		  
                                 rs2.open sql2,conn,1,1
								 if not rs2.eof then
								 do while not rs2.eof 
response.write "cur1.addBoard(new Board('"& trim(rs1("code")) &"','" & trim(rs2("meno")) & "','" & trim(rs2("meno")) & "'));"  & chr(13)								 
								             
                     rs2.movenext
								loop
								end if
								rs2.close
								set rs2=nothing
								           
                     rs1.movenext
								loop
								end if
								rs1.close
								set rs1=nothing
								

        //init catform
 response.write "       var catForm1 = document."&formname&".secRootCatId1;"  & chr(13)
  response.write "      var boardForm1 = document."&formname&".secSubCatId1; "      & chr(13)   

  response.write "      for (var i=0;i<catArr1.length;i++) {"  & chr(13)
response.write "                catForm1.options[i+1]=new Option(catArr1[i].title,catArr1[i].id);"  & chr(13)
 response.write "       }"  & chr(13)
response.write "        changeCategory1(catForm1);"  & chr(13)
        //init  
		if dsort<>"" then
		  response.write "        selectBoard1('"&left(dsort,2)&"00','"&trim(dsort)&"');"  & chr(13)
		  end if            
        response.write "          </script>"  & chr(13)
		
response.write  "                 </td>"& chr(13)
 response.write  "              </tr>"& chr(13)
 response.write  "            </table>"& chr(13)
end function
function cate_province(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" where code like '__00' order by ord desc,meno asc"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"'>"
place=place&"<option value=>请选择省份</option>"
place=place&"<option value=>全部</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=left(trim(rs_cate("meno")),2) then
		place=place&"<option value='" & left(trim(rs_cate("meno")),2) & "' selected>"& trim(rs_cate("meno")) &"</option>"
		else
		place=place&"<option value='" & left(trim(rs_cate("meno")),2) & "'>" & trim(rs_cate("meno")) & "</option>"
		end if
	else
	    place=place&"<option value='" & left(trim(rs_cate("meno")),2) & "'>" & trim(rs_cate("meno")) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function
'***************************
function cate_cn(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" order by ord asc"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"' >"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate("meno")) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate("meno")) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate("meno")) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function
'***************************
function cate_cn_wid(tb,a,b,wid)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" order by ord asc"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"' style='width:"&wid&"px'>"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate("meno")) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate("meno")) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate("meno")) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function

sub newpage(tblName,strGetFields,fldName,PageS,PageIndex,doCount,OrderType,strWhere)
newpage=""
end sub
'*****************

'****************************
function selectcls(dbname,dbname1,selectname,dbvalue)
    temp="<STYLE>"&chr(13)
	temp=temp&".cked {"&chr(13)
	temp=temp&"	PADDING-RIGHT: 2px; DISPLAY: block; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; MARGIN: 1px; WIDTH: 100%; COLOR: highlighttext; PADDING-TOP: 2px; BACKGROUND-COLOR: highlight"&chr(13)
	temp=temp&"}"&chr(13)
	temp=temp&".nock {"&chr(13)
	temp=temp&"	PADDING-RIGHT: 2px; DISPLAY: block; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; MARGIN: 1px; WIDTH: 100%; PADDING-TOP: 2px"&chr(13)
	temp=temp&"}"&chr(13)
	temp=temp&"</STYLE>"&chr(13)
	temp=temp&"<script language='JavaScript' type='text/JavaScript'>"&chr(13)
	temp=temp&"function oncheckarea(id)"&chr(13)
	temp=temp&"{"&chr(13)
	temp=temp&"aa=document.getElementById(""L""+id)"&chr(13)
	temp=temp&"bb=document.getElementById(""t""+id)"&chr(13)
	temp=temp&"if (bb.checked==false)"&chr(13)
	temp=temp&"{"&chr(13)
	temp=temp&"aa.className=""cked"""&chr(13)
	temp=temp&"bb.checked=true"&chr(13)
	temp=temp&"}else{"&chr(13)
	temp=temp&"aa.className=""nock"""&chr(13)
	temp=temp&"bb.checked=false"&chr(13)
	temp=temp&"}"&chr(13)
	temp=temp&"}"&chr(13)
	temp=temp&"function change_sort"&selectname&"(sindustry)"&chr(13)
	temp=temp&"{"&chr(13)
	querya="select * from "&dbname&" where delflag=0"
	set resulta=conn.execute(querya)
	if not resulta.eof then
    do while not resulta.eof  
		temp=temp&"switch (sindustry) {"&chr(13)
		temp=temp&"case '"&resulta("cb_id")&"':"&chr(13)
		temp=temp&"var temp;"&chr(13)
		temp=temp&"temp=""<div style='BORDER-RIGHT: #ffffff 2px inset; BORDER-TOP: #ffffff 2px inset; OVERFLOW: auto; BORDER-LEFT: #ffffff 2px inset; WIDTH: 180px; BORDER-BOTTOM: #ffffff 2px inset; HEIGHT: 150px'>"""&chr(13)
		'temp=temp&"temp=""<select name='"&selectname&"' onChange='slet()'>"";"&chr(13)
		'temp=temp&"temp+=""<option value='' >请选择小类</option>"";"&chr(13)
		queryb="select * from "&dbname1&" where cs_cb_id="&resulta("cb_id")&" order by cs_chn_name asc"
		'response.Write(querya)
        set resultb=server.CreateObject("ADODB.recordset")
        resultb.open queryb,conn,1,1
		
        if not resultb.eof then
	      do while not resultb.eof 
		    
		    temp=temp&"temp+=""<label id=L"&resulta("cb_id")&resultb("cs_id")&" onclick='oncheckarea("&resulta("cb_id")&resultb("cs_id")&")' class=nock><input name='"&selectname&"' id='"&selectname&resulta("cb_id")&resultb("cs_id")&"' onclick='oncheckarea("&resulta("cb_id")&resultb("cs_id")&")' onBlur=validateValueout(this,'check') type='checkbox' value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"</label> """&chr(13)
		  resultb.movenext
		  loop
		end if 
		resultb.close()
		set resultb=nothing
		temp=temp&"temp+=""</div>"";"&chr(13)
		temp=temp&"document.all[""sortmain"&selectname&"""].innerHTML=temp;"&chr(13)
		temp=temp&"break;"&chr(13)
		temp=temp&"}"&chr(13)
	resulta.movenext
	loop
	end if
	resulta.close()
	set resulta=nothing
	temp=temp&" }"&chr(13)
	temp=temp&"</script>"&chr(13)
	temp=temp&"<table border=0 cellspacing=0 cellpadding=0><tr><td valign=top width=190><select style='WIDTH: 180px; HEIGHT: 157px' size=9 name=""province"&selectname&""" id=""province"&selectname&""" onChange=""javascript:change_sort"&selectname&"(this.options[this.selectedIndex].value)"">"&chr(13)
    temp=temp&"<option value=''>请选择大类</option>"&chr(13)
	if dbvalue<>"" then
    sqlleib="select cs_cb_id from "&dbname1&" where cs_id="&dbvalue&""
    set resleib=conn.execute(sqlleib)
    rscs_cb_id=resleib(0)
	resleib.close()
	set resleib=nothing
	end if
	
		query="select * from "&dbname&" where delflag=0"
		set result=conn.execute(query)
		if not result.eof then
	      do while not result.eof 
		    if cstr(rscs_cb_id)=cstr(result("cb_id")) then
			temp=temp&"<option value='"&result("cb_id")&"' selected>"&result("cb_chn_name")&"</option>"&chr(13)
			else
            temp=temp&"<option value='"&result("cb_id")&"'>"&result("cb_chn_name")&"</option>"&chr(13)
		    end if
		  result.movenext
		  loop
		end if
		result.close()
	set result=nothing
    temp=temp&"</select>"&chr(13)
	temp=temp&"</td><td valign=top><font id=""sortmain"&selectname&""">"&chr(13)
	if dbvalue<>"" then
		
		
		temp=temp&"<div style='BORDER-RIGHT: #ffffff 2px inset; BORDER-TOP: #ffffff 2px inset; OVERFLOW: auto; BORDER-LEFT: #ffffff 2px inset; WIDTH: 180px; BORDER-BOTTOM: #ffffff 2px inset; HEIGHT: 150px'>"
		queryb="select * from "&dbname1&" where cs_cb_id="&dbvalue&""
		set resultb=conn.execute(queryb)
		if not resultb.eof then
			do while not resultb.eof 
			if cstr(trim(dbvalue))=cstr(trim(resultb("cs_id"))) then
			temp=temp&"<input name='"&selectname&"' selected type='checkbox' value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"<br> "
			else
			temp=temp&"<input name='"&selectname&"' type='checkbox' value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"<br> "
			end if
			resultb.movenext
			loop
		end if 
		resultb.close()
		set resultb=nothing
		temp=temp&"</div>"
	else
		temp=temp&"<select style='WIDTH: 180px; HEIGHT: 157px' size=9 name="""&selectname&""" id="""&selectname&"""   onChange='slet()'>"&chr(13)
		temp=temp&"<option value=''>请选择小类</option>"&chr(13)
		temp=temp&"</select>"&chr(13)
	end if
    temp=temp&"</font></td></tr></table>"&chr(13)
response.Write(temp)
end function
'***************************************下拉
function selectsort(dbname,dbname1,selectname,dbvalue)
	temp="<script language='JavaScript' type='text/JavaScript'>"&chr(13)
	temp=temp&"function change_sort"&selectname&"(sindustry)"&chr(13)
	temp=temp&"{"&chr(13)
	querya="select * from "&dbname&" where delflag=0"
	set resulta=conn.execute(querya)
	if not resulta.eof then
    do while not resulta.eof  
		temp=temp&"switch (sindustry) {"&chr(13)
		temp=temp&"case '"&resulta("cb_id")&"':"&chr(13)
		temp=temp&"var temp;"&chr(13)
		temp=temp&"temp=""<select name='"&selectname&"' style='width:130px'>"";"&chr(13)
		temp=temp&"temp+=""<option value='' >请选择小类</option>"";"&chr(13)
		queryb="select * from "&dbname1&" where cs_cb_id="&resulta("cb_id")&" order by cs_chn_name asc"
		'response.Write(querya)
        set resultb=server.CreateObject("ADODB.recordset")
        resultb.open queryb,conn,1,1
		
        if not resultb.eof then
	      do while not resultb.eof  
		    temp=temp&"temp+=""<option value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"</option>;"""&chr(13)
		  resultb.movenext
		  loop
		end if 
		resultb.close()
		set resultb=nothing
		temp=temp&"temp+=""</select>"";"&chr(13)
		temp=temp&"document.all[""sortmain"&selectname&"""].innerHTML=temp;"&chr(13)
		temp=temp&"break;"&chr(13)
		temp=temp&"}"&chr(13)
	resulta.movenext
	loop
	end if
	resulta.close()
	set resulta=nothing
	temp=temp&" }"&chr(13)
	temp=temp&"</script>"&chr(13)
	temp=temp&"<select style='width:130px' name=""province"&selectname&""" id=""province"&selectname&""" onChange=""javascript:change_sort"&selectname&"(this.options[this.selectedIndex].value)"">"&chr(13)
    temp=temp&"<option value=''>请选择大类</option>"&chr(13)
	if dbvalue<>"" then
    sqlleib="select cs_cb_id from "&dbname1&" where cs_id="&dbvalue
    set resleib=conn.execute(sqlleib)
	if not resleib.eof then 
    rscs_cb_id=resleib(0)
	end if
	resleib.close()
	set resleib=nothing
	end if
	
		query="select * from "&dbname&" where delflag=0"
		set result=conn.execute(query)
		if not result.eof then
	      do while not result.eof 
		    if cstr(rscs_cb_id)=cstr(result("cb_id")) then
			temp=temp&"<option value='"&result("cb_id")&"' selected>"&result("cb_chn_name")&"</option>"&chr(13)
			else
            temp=temp&"<option value='"&result("cb_id")&"'>"&result("cb_chn_name")&"</option>"&chr(13)
		    end if
		  result.movenext
		  loop
		end if
		result.close()
	set result=nothing
    temp=temp&"</select>"&chr(13)
	temp=temp&"<font id=""sortmain"&selectname&""">"&chr(13)
	if dbvalue<>"" and rscs_cb_id<>"" then
	temp=temp&"<select style='width:130px' name="""&selectname&""" id="""&selectname&""" >"&chr(13)
    
	queryb="select * from "&dbname1&" where cs_cb_id="&rscs_cb_id&""
		set resultb=conn.execute(queryb)
		if not resultb.eof then
	      do while not resultb.eof  
		    if cstr(trim(dbvalue))=cstr(trim(resultb("cs_id"))) then
			temp=temp&"<option value='"&resultb("cs_id")&"' selected>"&resultb("cs_chn_name")&"</option>;"&chr(13)
			else
		    temp=temp&"<option value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"</option>;"&chr(13)
		    end if
		  resultb.movenext
		  loop
		end if 
		resultb.close()
	    set resultb=nothing
    temp=temp&"</select>"&chr(13)
	else
    temp=temp&"<select style='width:130px' name="""&selectname&""" id="""&selectname&""" >"&chr(13)
    temp=temp&"<option value=''>请选择小类</option>"&chr(13)
    temp=temp&"</select>"&chr(13)
	end if
    temp=temp&"</font>"&chr(13)
response.Write(temp)
end function
'**************显示地址图片/begin
function showcontactpic(com_id,regtime)
linkPath=DatePart("yyyy",regtime)&"/"&DatePart("m",regtime)&"/"&DatePart("d",regtime)&"/"&com_id&".gif"
serverpath= Server.MapPath("\")
Set fso = CreateObject("Scripting.FileSystemObject")
if fso.fileExists(serverpath&"/cn/comp_link_pic/"&linkPath) then
showcontactpic="<img src='/cn/comp_link_pic/"&linkPath&"'>"
else
editcontactpic(com_id)
showcontactpic="<img src='/cn/comp_link_pic/"&linkPath&"'>"
end if
end function
'**************显示地址图片/end
'**************修改/添加 地址图片/begin
function editcontactpic(com_id)
	dim a(5)
	dim b(5)
	b(1)="联 系 人： "
	b(2)=" "
	b(3)="电　　话： " 
	b(4)="移动电话： "
	b(5)="传　　真： "
	sqlcontactpic="Select com_id,com_contactperson,com_desi,com_tel,com_mobile,com_fax,com_regtime  From comp_info Where com_id="&com_id
	set rscontactpic=conn.execute(sqlcontactpic)
	if not rscontactpic.eof then
	if rscontactpic("com_desi")="" or isnull(rscontactpic("com_desi")) then
	a(1)=rscontactpic("com_contactperson")
	else
	a(1)=replace(replace(rscontactpic("com_contactperson"),"先生",""),"女士","")&" "&rscontactpic("com_desi") '联系人
	end if
	a(2)="     "
	a(3)=rscontactpic("com_tel") 
	a(4)=rscontactpic("com_mobile")
	a(5)=rscontactpic("com_fax")
	regtime=rscontactpic("com_regtime")
	serverpath= Server.MapPath("\")
	serverpath=serverpath&"\cn\comp_link_pic\"
	pyear=DatePart("yyyy",regtime)
	pmouth=DatePart("m",regtime) 
	pday=DatePart("d",regtime) 
	ptime=DatePart("h",regtime)&DatePart("n",regtime)&DatePart("s",regtime)  
  Set fso = CreateObject("Scripting.FileSystemObject")
  If (fso.FolderExists(serverpath&pyear)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear)
  End If
  If (fso.FolderExists(serverpath&pyear&"/"&pmouth)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear&"/"&pmouth)
  End If
  If (fso.FolderExists(serverpath&pyear&"/"&pmouth&"/"&pday)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear&"/"&pmouth&"/"&pday)
  End If
  SavePath1=DatePart("yyyy",regtime)&"\"&DatePart("m",regtime)&"\"&DatePart("d",regtime)&"\"
  SavePath=serverpath&SavePath1
  Path = Server.MapPath("/") &"\admin1\comp_link_pic\"& "a.gif"
  Set Jpeg = Server.CreateObject("Persits.Jpeg")
  Jpeg.Open Path
				m=0
				for i=1 to 5
					Jpeg.Canvas.Font.Color = &H000000'' 红色 
					Jpeg.Canvas.Font.Family = "宋体" 
					Jpeg.Canvas.Font.Size = 14
					Jpeg.Canvas.Font.ShadowColor=&H000000
					Jpeg.Canvas.Font.ShadowXoffset = 0
					Jpeg.Canvas.Font.ShadowYoffset = 0
					Jpeg.Canvas.Font.Quality = 6
					Jpeg.Canvas.Font.Bold = false 
					if a(i)<>"" then
						m=m+1
						nn=22*m
						nn=nn-10
						Jpeg.Canvas.Print 7, nn, b(i)&a(i)
					end if
				next
				picname=com_id&".gif"
				url=SavePath&picname
				Jpeg.Save url 
				Set Jpeg = Nothing 
				nn=nn+25
				websaveurl="\cn\comp_link_pic\"&SavePath1&picname '网站保存地址
				url1=websaveurl
				Set Jpeg = Server.CreateObject("Persits.Jpeg") 
				jpeg.open server.MapPath(url1) 
				picheight=Jpeg.OriginalHeight
				picwidth=jpeg.Originalwidth
				jpeg.width=picwidth
				Jpeg.Height=picheight
				jpeg.crop 0,0,400,nn   '开始切割其实是把超过52象素的下部分去掉 
				jpeg.save server.MapPath(url1) '保存 
				rscontactpic.close
				set rscontactpic=nothing
end if
end function
'**************修改/添加 地址图片/begin
function replacenull(str)
	if isnull(str) then
		replacenull=""
	else
	    replacenull=str
	end if
end function
function showsinglecontact(contactname,contacttype,pdtid,pdttime)
if not isnull(contactname) then
	contactname=changeNum(contactname)
	serverpath= Server.MapPath("/")
	serverpath=serverpath&"\cn\contactpic\"
	pyear=DatePart("yyyy",pdttime)
	pmouth=DatePart("m",pdttime) 
	pday=DatePart("d",pdttime) 
	ptime=DatePart("h",pdttime)&DatePart("n",pdttime)&DatePart("s",pdttime)  
  Set fso = CreateObject("Scripting.FileSystemObject")
  If (fso.FolderExists(serverpath&pyear)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear)
  End If
  If (fso.FolderExists(serverpath&pyear&"/"&pmouth)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear&"/"&pmouth)
  End If
  If (fso.FolderExists(serverpath&pyear&"/"&pmouth&"/"&pday)) Then
  Else
    Set f = fso.CreateFolder(serverpath&pyear&"/"&pmouth&"/"&pday)
  End If
  SavePath1=DatePart("yyyy",pdttime)&"\"&DatePart("m",pdttime)&"\"&DatePart("d",pdttime)&"\"
  SavePath=serverpath&SavePath1
    Path = Server.MapPath("/") &"\cn\contactpic\"& "a.gif"
	Set Jpeg = Server.CreateObject("Persits.Jpeg")
	Jpeg.Open Path
	picheight=Jpeg.OriginalHeight
	picwidth=jpeg.Originalwidth
	jpeg.width=picwidth
	Jpeg.Height=picheight
	jpeg.crop 0,0,lenNumeric(contactname),16 
	'response.Write(len(contactname))
	Jpeg.Canvas.Font.Color = &H000000'' 红色 
	Jpeg.Canvas.Font.Family = "宋体" 
	Jpeg.Canvas.Font.Size = 12
	Jpeg.Canvas.Font.ShadowColor=&H000000
	Jpeg.Canvas.Font.ShadowXoffset = 0
	Jpeg.Canvas.Font.ShadowYoffset = 0
	Jpeg.Canvas.Font.Quality = 6
	Jpeg.Canvas.Font.Bold = false 
	Jpeg.Canvas.Print 0, 2, contactname
	picname=pdtid&"_"&contacttype&".gif"
	url=SavePath&picname
	Jpeg.Save url 
	Set Jpeg = Nothing
	showsinglecontact="http://www.zz91.com/cn/contactpic/"&SavePath1&picname
	response.Write("<img src="&showsinglecontact&">")
end if
end function
function lenNumeric(str)
lenNumeric=0
for i=1 to len(str)
	if IsNumeric(mid(str,i,1)) then
	lenNumeric=lenNumeric+len(mid(str,i,1))*6
	else
		if Len(Hex(Asc(mid(str,i,1))))>2 Then
		lenNumeric=lenNumeric+len(mid(str,i,1))*2*6
		else
		lenNumeric=lenNumeric+len(mid(str,i,1))*6
		end if
	end if
next
end function
function changeNum(str)
changeNum=""
if isnull(str) then
else
for i=1 to len(str)
	if IsNumeric(mid(str,i,1)) then
	changeNum=changeNum&cint(mid(str,i,1))
	else
	changeNum=changeNum&cstr(mid(str,i,1))
	end if
next
end if
end function
Function RegExpTest(patrn, strng)
  Dim regEx, Match, Matches		' 建立变量。
  Set regEx = New RegExp			' 建立正则表达式。
  regEx.Pattern = patrn			' 设置模式。
  regEx.IgnoreCase = True			' 设置是否区分大小写。
  regEx.Global = True			' 设置全程可用性。
  Set Matches = regEx.Execute(strng)	' 执行搜索。
  For Each Match in Matches		' 遍历 Matches 集合。
    RetStr = RetStr & "匹配 " & I & " 位于 "
    RetStr = RetStr & Match.FirstIndex & "。匹配的长度为"
    RetStr = RetStr & Match.Length 
    RetStr = RetStr & "个字符。" & vbCRLF
  Next
  RegExpTest = RetStr
End Function
Sub zz91login_of(comemail,password)
	cemail=changeNum(trim(comemail))
	cemail=lcase(cemail)
	cemail=lcase(replace(cemail," ",""))
	cpwd=changeNum(password)
	sqlpass="select com_SafePass,com_pass from comp_info where com_email='"&cemail&"'"
	set rspass=conn.execute(sqlpass)
	if not rspass.eof then
		if isnull(rspass("com_SafePass")) or trim(rspass("com_SafePass"))="" then
			if rspass("com_pass")=cpwd then
				sqlsafe="update comp_info set com_SafePass='"&md5(cpwd,16)&"' where com_email='"&cemail&"'"
				conn.execute(sqlsafe)
			end if
		end if
	end if
	rspass.close
	set rspass=nothing
	rf=ucase(request.form)
	if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
		response.Write("<script>alert('对不起，您的用户名有非法的字符！');window.history.back(1)</script>")
		response.end
	end if
	cemail=replace(trim(cemail),"'","''")
	cpwd=replace(trim(cpwd),"'","''")
	if cemail="" then
		response.Write("<script>alert('对不起，请输入您的用户名(E-mail)！');window.history.back(1)</script>")
		response.end
	end if
	if cpwd="" then
		response.Write("<script>alert('对不起，请输入您的密码！');window.history.back(1)</script>")
		response.end
	end if
	set rslog=server.CreateObject("ADODB.recordset")
	sql="select com_id,com_pass,vipflag,vip_check,com_SafePass from comp_info where com_email='"&cemail&"'"'找出注册用户
	rslog.open sql,conn,1,1
	if not rslog.eof and not rslog.bof then'用户存在
	    '****************************
		sqlblack="select * from comp_blacklist where com_id="&rslog("com_id")
		set rsblack=conn.execute(sqlblack)
		if not rsblack.eof then
		response.write "<script language='javascript'>alert('对不起，您不能登录，如有问题，请与我们联系!电话：0571-87016311');window.history.go(-1);</script>"
		response.End()
		end if
		rsblack.close
		set rsblack=nothing
		'*********************
		if cstr(rslog("com_SafePass"))=cstr(md5(cpwd,16)) then
		'if rslog("com_pass")=cpwd then
			com_id=rslog("com_id")
			sqle="select * from comp_login where com_email='"&cemail&"'"
			set rse=conn.execute(sqle)
			if not rse.eof or not rse.bof then
			sqlp="update comp_login set logincount=logincount+1,lastlogintime=getdate(),com_id="&com_id&",viewip='"&windowip&"' where com_email='"&cemail&"'"
			else
			sqlp="insert into comp_login (com_id,com_email,logintime,lastlogintime,logincount,viewip) values("&com_id&",'"&cemail&"',getdate(),getdate(),1,'"&windowip&"')"
			end if
			conn.execute sqlp,ln
			rse.close()
			set rse=nothing
	        '***************************记录登录情况/start
			sqllogin="select * from comp_logincount where com_id="&com_id&" and Fdate='"&date()&"'"
			set rslogin=conn.execute(sqllogin)
			if not rslogin.eof then
			sqlin="update comp_logincount set Lcount=Lcount+1 where com_id="&com_id&" and Fdate='"&date()&"'"
			conn.execute(sqlin)
			else
			sqlin="insert into comp_logincount (com_id,Fdate) values("&com_id&",'"&date()&"')"
			conn.execute(sqlin)
			end if
			rslogin.close
			set rslogin=nothing
			'***************************记录登录情况/end
			if rslog("vipflag")=2 and rslog("vip_check")=1 then
			session("log_comemail")=cemail
			session("log_comid")=rslog("com_id")
			session("vipflag")=rslog("vipflag")
			session("vipcheck")=rslog("vip_check")
			session("log_guestemail")=""
			session("log_en")="cn"
			'response.Redirect(request("url"))
			else
			session("log_guestemail")=cemail
			session("log_comid")=rslog("com_id")
			session("vipflag")=rslog("vipflag")
			session("vipcheck")=rslog("vip_check")
			session("log_comemail")=""
			session("log_en")="cn"
			'response.Redirect(request("url"))
			end if
			'添加积分
			sqlPoint="select * from Forum_Point where com_id="&com_id
			set rsPoint = conn.execute(sqlPoint)
			if not (rsPoint.eof and rsPoint.bof) then
				sqlAdd="update Forum_point set com_totalPoint=com_totalPoint+1 where com_id="&com_id
				conn.execute sqlAdd
			else	'主要针对以前注册的会员,他们以前并没有积分,在他们登录时加上积分.
				if rslog("vipflag")=2 then
					pointValue=50
				else
					pointValue=20
				end if
				sqlAdd="insert into Forum_point(Com_ID,Com_totalPoint,Com_usedPoint) values("&com_id&","&pointValue&",0)"
				conn.execute sqlAdd
			end if
			rsPoint.close
			set rsPoint=nothing
			'添加结束
			
		else'密码错误
		    if rslog("com_pass")=cpwd and cstr(rslog("com_SafePass"))<>cstr(md5(cpwd,16)) then
				sqlsafe="update comp_info set com_SafePass='"&md5(cpwd,16)&"' where com_email='"&cemail&"'"
				conn.execute(sqlsafe)
			end if
		response.write "<script language='javascript'>alert('对不起，登录失败！您的密码有误!\n请再次登录并确认输入的密码是否正确！');window.history.go(-1);</script>"
		response.end
		end if
	else'用户不存在
		response.write "<script language='javascript'>alert('对不起，登录失败！您输入的用户名不存在\n或确认您的用户名格式是否为电子邮箱！\n格式如：test@zz91.com');window.history.go(-1);</script>"
		response.end
	end if
	rslog.close
	set rslog=nothing
end sub
'*****************
function gglink_index(lb,sl)
	sqlpic="select top "&sl&" * from TB_guanggao where  lb='"&lb&"' and flag='1' order by ord asc"
	set rspic=server.CreateObject("adodb.recordset")
	rspic.open sqlpic,conn,1,1
	if not rspic.eof or not rspic.bof then
	ii=1
	sw="<table width=100%  border=0 cellspacing=0 cellpadding=5><tr>"
	Do While Not rspic.EOF
	if lb="11" then
	  if rspic("geshi")="gif" or rspic("geshi")="jpg" then
	  sw=sw&"<td><a href='"&rspic("glink")&"' target='_blank'><img src="&rspic("addr")&" width='"&rspic("gwidth")&"' alt='"&rspic("title")&"' height='"&rspic("gheight")&"' border='0'></a>"
	  end if
	  if rspic("geshi")="swf" then
	  sw=sw&"<td><object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>"
      sw=sw&"<param name='movie' value='"&rspic("addr")&"'>"
      sw=sw&"<param name='quality' value='high'>"
	  sw=sw&"<param name='wmode' value='transparent'>"
      sw=sw&"<embed src='"&rspic("addr")&"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"' wmode='transparent'></embed>"
      sw=sw&"</object>"
	  end if
	  if (ii mod 7)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	else
	  sw=sw&"<td><a href='"&rspic("glink")&"' target=_blank class='link_black'><font color="&rspic("gcolor")&">"&rspic("title")&"</font></a>"
	  if (ii mod 4)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	end if
	  
	rspic.movenext
	ii=ii+1
	loop
	sw=sw&"</td></tr></table>"
	response.Write(sw)
	end if
	rspic.close()
	set rspic=nothing
end function
Function SafeRequest(ParaName,ParaType) 
'--- 传入参数 --- 
'ParaName:参数名称-字符型 
'ParaType:参数类型-数字型(1表示以上参数是数字，0表示以上参数为字符) 
 posttype=Request.ServerVariables("REQUEST_METHOD")
 Dim ParaValue
  if posttype="POST" then
 	ParaValue=Request.Form(ParaName) 
  else
    ParaValue=Request.QueryString(ParaName)
  end if
    server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
	server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
	if posttype="POST" then
		if mid(server_v1,8,len(server_v2))<>server_v2 then
			response.write "错误<br>"
			response.end
		end if
	end if
 If ParaType=1 then 
   if trim(ParaValue)<>"" then
	  If not isNumeric(ParaValue) then 
	   Response.write "参数" & ParaName & "必须为数字型！" 
	   Response.end 
	  End if 
   end if
 Else 
  	ParaValue=replace(ParaValue,"'","''") 
 End if 
 SafeRequest=ParaValue 
End function 

%>





