<%@ Control Language="C#" AutoEventWireup="true" CodeFile="edit.ascx.cs" Inherits="widgets_Most_comments_edit" %>

<label for="<%=cbShowComments.ClientID %>">��ʾ����</label><br />
<asp:CheckBox runat="Server" ID="cbShowComments" />

<br /><br />

<label for="<%=txtSize.ClientID %>">ͷ���С(����)</label><br />
<asp:TextBox runat="Server" ID="txtSize" />
<asp:CompareValidator runat="Server" ControlToValidate="txtSize" Operator="dataTypeCheck" Type="integer" ErrorMessage="������һ����Ч��" />

<br /><br />

<label for="<%=txtNumber.ClientID %>">��������</label><br />
<asp:TextBox runat="Server" ID="txtNumber" />
<asp:CompareValidator runat="Server" ControlToValidate="txtNumber" Operator="dataTypeCheck" Type="integer" ErrorMessage="������һ����Ч��" />

<br /><br />

<label for="<%=txtDays.ClientID %>">�������</label><br />
<asp:TextBox runat="Server" ID="txtDays" />
<asp:CompareValidator runat="Server" ControlToValidate="txtDays" Operator="dataTypeCheck" Type="integer" ErrorMessage="������һ����Ч��" />