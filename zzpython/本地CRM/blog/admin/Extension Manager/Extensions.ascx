<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Extensions.ascx.cs" Inherits="User_controls_xmanager_ExtensionsList" %>
<asp:Literal ID="Literal1" runat="server" Text="<h1>插件管理</h1>" />
<div id="lblErrorMsg" style="padding:5px; color:Red;" runat="server"></div>
<asp:Label ID="lblExtensions" runat="server" Text="未找到"></asp:Label>

<br /><br />
<div style="text-align:right">
  <asp:Button runat="Server" ID="btnRestart" 
    Text="Apply changes" 
    OnClientClick="return confirm('您的网站将会中断几秒钟。\n您确定要继续吗？')" 
  />
</div>