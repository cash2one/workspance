<%@ Control Language="C#" AutoEventWireup="true" CodeFile="edit.ascx.cs" Inherits="widgets_Twitter_edit" %>
<%@ Reference Control="~/widgets/Twitter/widget.ascx" %>

<label for="<%=txtAccountUrl %>">���� Twitter �ʺ� URL</label><br />
<asp:TextBox runat="server" ID="txtAccountUrl" Width="300" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtAccountUrl" ErrorMessage="��������Ч��URL" Display="dynamic" /><br /><br />

<label for="<%=txtUrl %>">Twitter RSS �ۺ� URL</label><br />
<asp:TextBox runat="server" ID="txtUrl" Width="300" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtUrl" ErrorMessage="��������Ч��URL" Display="dynamic" /><br /><br />

<label for="<%=txtTwits %>">��ʾ����</label><br />
<asp:TextBox runat="server" ID="txtTwits" Width="30" />
<asp:RequiredFieldValidator runat="Server" ControlToValidate="txtTwits" ErrorMessage="��������Ч��" Display="dynamic" />
<asp:CompareValidator runat="server" ControlToValidate="txtTwits" Type="Integer" Operator="dataTypeCheck" ErrorMessage="��������Ч��" /><br /><br />