<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!--#include file="../upload_5xsoft.inc"-->
<%
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
response.write "����<br>"
response.end
end if
'************�ϴ�����
dim upload,file,formName,formPath,iCount
set upload=new upload_5xSoft ''�����ϴ�����
MaxFileSize=3072
serverpath= Server.MapPath("\")
pyear=DatePart("yyyy",now())
pmouth=DatePart("m",now()) 
pday=DatePart("d",now()) 
ptime=DatePart("h",now())&DatePart("n",now())&DatePart("s",now())  
Set fso = CreateObject("Scripting.FileSystemObject")
  If (fso.FolderExists(serverpath&"/admin1/techQuestion/"&pyear)) Then
  Else
    Set f = fso.CreateFolder(serverpath&"/admin1/techQuestion/"&pyear)
  End If
  If (fso.FolderExists(serverpath&"/admin1/techQuestion/"&pyear&"/"&pmouth)) Then
  Else
    Set f = fso.CreateFolder(serverpath&"/admin1/techQuestion/"&pyear&"/"&pmouth)
  End If
formPath="/admin1/techQuestion/"&DatePart("yyyy",now())&"/"&DatePart("m",now())&"/"
'***********************ͼƬ����
iCount=1
if upload.form("m")="1" then
	for each formName in upload.objFile ''�г������ϴ��˵��ļ�
		 FoundErr=false
		 EnableUpload=true
	  set file=upload.file(formName)  ''����һ���ļ�����
      if file.FileSize>0 then 
		 filearray=split(file.FileName,".",-1,1)
	     fileEXT=filearray(1)
		 set file=upload.file(formName)  ''����һ���ļ�����
			if file.FileSize>0 then         ''��� FileSize > 0 ˵�����ļ�����
				 filenamet=pday&ptime&"."&fileEXT
				 if FoundErr=false then
				  file.SaveAs Server.mappath(formPath&filenamet)   ''�����ļ�
				 end if
			 end if
		 set file=nothing
		 iCount=iCount+1
	  end if
	next
formPath="/admin1/techQuestion/"&DatePart("yyyy",now())&"/"&DatePart("m",now())&"/"
address_p=formPath&filenamet
'***********�ϴ�����
%>

 
<%  
if iCount>1 then
response.write "<script language='javascript'>var content='<a href="&address_p&">����</a>';parent.eWebEditor1.insertHTML(content);window.location='trade_file.asp'</script>"
response.end
end if
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script type="text/javascript" src="../../editor/fckeditor.js"></script>
<title>�ޱ����ĵ�</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

</head>

<body>

<form id="form1" name="form1" enctype="multipart/form-data" method="post" action="">
  <input type="file" name="file" />
  <input type="submit" name="Submit" value="�ϴ�" />
  <input name="m" type="hidden" id="m" value="1">
</form>

</body>
</html>

