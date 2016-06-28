<%@ Control Language="C#" AutoEventWireup="true" CodeFile="edit.ascx.cs" Inherits="widgets_Most_comments_edit" %>

<label for="<%=cbShowComments.ClientID %>">显示评论</label><br />
<asp:CheckBox runat="Server" ID="cbShowComments" />

<br /><br />

<label for="<%=txtSize.ClientID %>">头像大小(像素)</label><br />
<asp:TextBox runat="Server" ID="txtSize" />
<asp:CompareValidator runat="Server" ControlToValidate="txtSize" Operator="dataTypeCheck" Type="integer" ErrorMessage="请输入一个有效数" />

<br /><br />

<label for="<%=txtNumber.ClientID %>">评论者数</label><br />
<asp:TextBox runat="Server" ID="txtNumber" />
<asp:CompareValidator runat="Server" ControlToValidate="txtNumber" Operator="dataTypeCheck" Type="integer" ErrorMessage="请输入一个有效数" />

<br /><br />

<label for="<%=txtDays.ClientID %>">最大天数</label><br />
<asp:TextBox runat="Server" ID="txtDays" />
<asp:CompareValidator runat="Server" ControlToValidate="txtDays" Operator="dataTypeCheck" Type="integer" ErrorMessage="请输入一个有效数" />