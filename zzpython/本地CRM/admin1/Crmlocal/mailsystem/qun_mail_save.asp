<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
response.Write(request.Form("tomailone"))
sql="select com_email,com_email_check,com_email_back,com_contactperson,com_desi,com_pass from comp_info where com_id="&request.Form("tomailone")&""
set rs=conn.execute(sql)
if not rs.eof then
tocttperson=rs("com_contactperson")
tocomdesi=rs("com_desi")
com_email_check=rs("com_email_check")
com_email=rs("com_email")
com_email_back=rs("com_email_back")
com_pass=rs("com_pass")
end if
if isnull(tocomdesi) then
tocomdesi=""
end if
rs.close
set rs=nothing
if isnull(com_pass) then
com_pass=""
end if
'-------------------------------------------------------------------- 
function sendemail(mailTo,mailTopic,mailBody,mailCharset,mailContentType)
    '***************������Ҫ���ó�����ʼ***************** 
	Dim ConstFromNameCn,ConstFromNameEn,ConstFrom,ConstMailDomain,ConstMailServerUserName,ConstMailServerPassword 
	
	ConstFromNameCn = "�й�������Դ������"'��������������(�������ʼ���ʱ��ʹ��)�����确������ 
	ConstFromNameEn = "RECYCLECHINA"'������Ӣ������(��Ӣ���ʼ���ʱ��ʹ��)�����确zhangsan�� 
	ConstFrom = "master@zz91.com"'�������ʼ���ַ�����确zhangsan@163.com�� 
	ConstMailDomain = "mail.zz91.com"'smtp��������ַ������smtp.163.com 
	ConstMailServerUserName = "master@zz91.com"'smtp�������������½�������确zhangsan����ע��Ҫ�뷢�����ʼ���ַһ�£� 
	ConstMailServerPassword = "88888888"'smtp�������������½���� 
	'***************������Ҫ���ó�������***************** 
	
	'-----------------------------������������Ķ�------------------------------ 
	On Error Resume Next 
	Dim myJmail 
	Set myJmail = Server.CreateObject("JMail.Message") 
	myJmail.silent = true
	myJmail.Logging = True'��¼��־ 
	myJmail.ISOEncodeHeaders = False'�ʼ�ͷ��ʹ��ISO-8859-1���� 
	myJmail.ContentTransferEncoding = "base64"'�ʼ�������Ϊbase64 
	myJmail.AddHeader "Priority","3"'����ʼ�ͷ,��Ҫ�Ķ��� 
	myJmail.AddHeader "MSMail-Priority","Normal"'����ʼ�ͷ,��Ҫ�Ķ��� 
	myJmail.AddHeader "Mailer","Microsoft Outlook Express 6.00.2800.1437"'����ʼ�ͷ,��Ҫ�Ķ��� 
	myJmail.AddHeader "MimeOLE","Produced By Microsoft MimeOLE V6.00.2800.1441"'����ʼ�ͷ,��Ҫ�Ķ��� 
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
	'sendemail=myJmail.Send(ConstMailDomain)
	myJmail.Close 
	Set myJmail=nothing 
	On Error Goto 0 
	If Err Then 
	sendemail=false
	Err.Clear 
	Else 
	sendemail=true
	End If 
end function
'-------------------------------------------------------------------- 


function replacenull(str)
	if isnull(str) then
		replacenull=""
	else
	    replacenull=str
	end if
end function
                    filename=server.mappath("mo.html")
					set myfile=server.createobject("scripting.filesystemobject")
					set mytext=myfile.opentextfile(filename)
					content=mytext.readall
					 ' sqlup="update crm_email_template set LastEmailComID="&request.Form("tomailone")&" where sendtype=1 and emailtype=1"
					 ' conn.execute (sqlup)
                    emailsubject="���ĵ�¼�ʺź�����,ע�����!"
					emailinfo=replace(content,"<!--tocontactperson-->",replacenull(tocttperson))
					emailinfo=replace(emailinfo,"<!--tocomdesi-->",replacenull(tocomdesi))
					emailinfo=replace(emailinfo,"<!--fromid-->",replacenull(id))
					emailinfo=replace(emailinfo,"<!--comid-->",replacenull(request.Form("tomailone")))
					
		
					'**********************************
					if com_email_check=0 then
					toemail_to=com_email
					else
					toemail_to=com_email_back
					end if
					emailinfo=replace(emailinfo,"<!--comemail-->",com_email)
					emailinfo=replace(emailinfo,"<!--compass-->",com_pass)
					toemail_to="kangxianyue@163.com"
					response.Write(emailinfo)
					arremail=split(toemail_to,"@",-1,1)
					if ubound(arremail)>=1 then
					  if arremail(1)="136.com" or arremail(1)="pp.com" or arremail(1)="yahoo.cn" or arremail(1)="yahoo.net" or arremail(1)="yahoo.com" then
					  else
					  
					   sendstatus=sendemail(toemail_to,emailsubject,emailinfo,"GB2312","text/html")
					   if sendstatus then
					   response.Write(sendstatus)
					   else
					   response.Write(sendstatus)
					   end if
					   'SendStat=sendemail1(toemail_to,emailinfo,emailsubject,"")
					   'SendStat = Jmail("kangxianyue@163.com",emailsubject,emailinfo,"GB2312","text/html") 
					   'SendStat = Jmail("qib2004@gmail.com",emailsubject,emailinfo,"GB2312","text/html")
					   'SendStat = Jmail("qib@zz91.com",emailsubject,emailinfo,"GB2312","text/html") 
					  end if
					end if
					'sendstatus=sendemail("qib2004@gmail.com",emailinfo,emailsubject,"")
					'sendstatus=sendemail("abin6068@hotmail.com",emailinfo,emailsubject,"")
					'sendstatus=sendemail("14703479@qq.com",emailinfo,emailsubject,"")
					'sendstatus=sendemail("kangxianyue@163.com",emailsubject,emailinfo,"GB2312","text/html")
					'response.Write(sendstatus)
					'SendStat = Jmail("kangxianyue922@yahoo.com.cn",emailsubject,emailinfo,"GB2312","text/html") 
					'SendStat = Jmail("kangxianyue@163.com",emailsubject,emailinfo,"GB2312","text/html")
					

response.Write("<script>parent.document.all.m.value="&request.Form("m")+1&";parent.sendemail();</script>")
conn.close
set conn=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<body>
</body>
</html>
