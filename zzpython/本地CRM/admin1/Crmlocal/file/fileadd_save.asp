<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include FILE="upload_5xsoft.asp"-->
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
for each formName in upload.objFile ''�г������ϴ��˵��ļ�
 FoundErr=false
 EnableUpload=true
	set file=upload.file(formName)  ''����һ���ļ�����
	if file.FileSize>0 then         ''��� FileSize > 0 ˵�����ļ�����
			 if file.FileSize>(MaxFileSize*1024) then
				msg="�ļ���С���������ƣ����ֻ���ϴ�" & CStr(MaxFileSize) & "K���ļ�������Сͼ�ٴ���"
				FoundErr=True
			 end if
			 UpFileType="doc,xls,txt"
			 filearray=split(file.FileName,".")
			 fileEXT=filearray(ubound(filearray))
			 EnableUpload=false
			 select case lcase(fileEXT)
				 case "doc"
					EnableUpload=true
				 case "xls"
					EnableUpload=true
				 case "txt"
					EnableUpload=true
				 case else 
					EnableUpload=false
			 end select
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
		    msg=file.FilePath&file.FileName&"�ϴ��ɹ�����"
		    iCount=iCount+1
		 end if
		 formPath="/admin1/file/"&DatePart("yyyy",now())&"/"&DatePart("m",now())&"/"
	     address_p=formPath&filenamet
	 end if
	 set file=nothing
next
sqlc="insert into crm_UploadFile(Filename,Filepath,userid,personid) values('"&upload.form("Filename")&"','"&address_p&"','"&upload.form("userid")&"',"&session("personid")&")"
conn.execute(sqlc)
if IsObject(conn) then
	if conn.state=1 then
		conn.close
		set conn=nothing
	end if
end if
response.Redirect("filelist.asp")
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
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
</body>
</html>
