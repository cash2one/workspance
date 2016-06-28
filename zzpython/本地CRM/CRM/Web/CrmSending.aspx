<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmSending.aspx.cs" Inherits="CRM_CrmSending" %>

<%@ Register Assembly="System.Web.Extensions, Version=1.0.61025.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35"
    Namespace="System.Web.UI" TagPrefix="asp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>我要提交的文档</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <form id="form1" runat="server">
        <div id="CRM" class="right">
            <div id="PostCrm">
                <strong>我要提交的文档</strong>
                <div id="div_error" runat="server"></div>
                <div id="PostCrm_Infor">
                     <div>
                        <span>接收
                            <asp:DropDownList ID="DDL_Meno" runat="server" AutoPostBack="True" OnSelectedIndexChanged="DDL_Meno_SelectedIndexChanged">
                            </asp:DropDownList>
                            <asp:DropDownList ID="DDL_Staff" runat="server"></asp:DropDownList>
                            <span id="CrmTo" runat="server"></span>
                        </span>
                    </div>
                    <div><span>标题 </span><asp:TextBox ID="Txt_CrmTitle" runat="server" Width="300px"></asp:TextBox>
                        </div>
                    <div id="Div_Annex" runat="server"><span>附件 </span><asp:FileUpload ID="FileUpload" runat="server" Width="380px"/></div>
                    <div><span>详细内容</span></div>
                    <div>
                        <iframe src="eWebEditor/ewebeditor.htm?id=txt_Content&style=mini" frameborder="0" scrolling="no" width="550" height="350">
                        </iframe>
                        <asp:HiddenField ID="txt_Content" runat="server" />
                    </div>
                    <div>
                        <span style="width:200px; float:left">
                            <asp:Button ID="Crm_Submit" Text="提 交" runat="server" OnClick="Crm_Submit_Click"/>
                        </span>
                        <span style="width:300px;">
                            <input type="reset" id="reset" name="reset" value="重新编写" /></span></div>
                </div>
            </div>  
        </div>      
    </form>
</body>
</html>
