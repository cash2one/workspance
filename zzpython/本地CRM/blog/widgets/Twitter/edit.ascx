<%@ Control Language="C#" AutoEventWireup="true" CodeFile="edit.ascx.cs" Inherits="widgets_Twitter_edit" %>
<%@ Reference Control="~/widgets/Twitter/widget.ascx" %>

<label for="<%=txtAccountUrl %>">您的 Twitter 帐号 URL</label><br />
<asp:TextBox runat="server" ID="txtAccountUrl" Width="300" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtAccountUrl" ErrorMessage="请输入有效的URL" Display="dynamic" /><br /><br />

<label for="<%=txtUrl %>">Twitter RSS 聚合 URL</label><br />
<asp:TextBox runat="server" ID="txtUrl" Width="300" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtUrl" ErrorMessage="请输入有效的URL" Display="dynamic" /><br /><br />

<label for="<%=txtTwits %>">显示条数</label><br />
<asp:TextBox runat="server" ID="txtTwits" Width="30" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtTwits" ErrorMessage="请输入有效数" Display="dynamic" />
<asp:CompareValidator runat="server" ControlToValidate="txtTwits" Type="Integer" Operator="dataTypeCheck" ErrorMessage="请输入有效数" /><br /><br />