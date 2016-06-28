<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Editor.ascx.cs" Inherits="User_controls_xmanager_SourceEditor" %>
<h1>代码查看器: <%=_extensionName%></h1>
<div>
    <asp:TextBox ID="txtEditor" runat="server" TextMode="multiLine" Width="100%" Height="350"></asp:TextBox>
    <br />
    <asp:Button ID="btnSave" Visible="false" runat="server" Text="Save" OnClick="btnSave_Click" OnClientClick="return confirm('您的网站将会中断几秒钟。\n您确定要继续吗？');" />
</div>