<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include FILE="upload_5xsoft.asp"-->
<%
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
	response.write "错误<br>"
	response.end
end if
'************上传设置
dim upload,file,formName,formPath,iCount
set upload=new upload_5xSoft ''建立上传对象
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
'***********************图片保存
iCount=1
for each formName in upload.objFile ''列出所有上传了的文件
 FoundErr=false
 EnableUpload=true
	set file=upload.file(formName)  ''生成一个文件对象
	if file.FileSize>0 then         ''如果 FileSize > 0 说明有文件数据
			 if file.FileSize>(MaxFileSize*1024) then
				msg="文件大小超过了限制，最大只能上传" & CStr(MaxFileSize) & "K的文件！请缩小图再传！"
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
				msg=filearray(1)&" 这种文件类型不允许上传！\n\n只允许上传这几种文件类型：" & UpFileType
				FoundErr=True
			end if
			if FoundErr=True then
				response.Write("<script>alert('"&msg&"');window.history.back(1)</script>")
				response.End()
			end if
		 filenamet=pday&ptime&"."&fileEXT
		 if FoundErr=false then
		    file.SaveAs Server.mappath(formPath&filenamet)   ''保存文件
		    msg=file.FilePath&file.FileName&"上传成功！！"
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
<title>无标题文档</title>
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
