<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmMain.aspx.cs" Inherits="CRM_Default" %>

<%@ Register Src="AdminUserList.ascx" TagName="AdminUserList" TagPrefix="uc1" %>

<%@ Register Assembly="System.Web.Extensions, Version=1.0.61025.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35"
    Namespace="System.Web.UI" TagPrefix="asp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>部门工作交流CRM方案</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
    <div style="width:1000px; height:100%;">
        <div id="CrmLeft" class="meno">
           <div id="To_Staff"><a href="CrmReceived.aspx" target="CrmRight">提交给我的文档</a></div>
            <div style="border:1px solid #eee">
                <div>我要提交文档</div>
               <uc1:AdminUserList ID="AdminUserList1" runat="server" />
            </div>
            <div id="From_Staff"><a href="CrmPosted.aspx" target="CrmRight">我提交过的文档</a></div>
            <div id="CrmMsg" visible="false" runat="server" class="CrmMsg" style="padding-top:10px; margin-top:10px; border-top:1px solid #333;">
                <strong>CRM系统消息</strong>
                <div>暂时没有消息</div>
            </div>
        </div>
        <div style="float:left;">
            <div style=" height:30px; margin-bottom:10px; " class="right">
            <div style ="padding:5px;" class="annex">
                <span id="sUserInfor" runat="server"></span>
            </div>
            </div>
            <div style="width:800px; float:left;">
            <iframe id="CrmRight" name="CrmRight" src="CrmReceived.aspx" width="800" height="810" frameborder="0" marginheight="0" marginwidth="0" scrolling="auto" runat="server"></iframe>
            </div>
        </div>
    </div>
    </form>
</body>
</html>
