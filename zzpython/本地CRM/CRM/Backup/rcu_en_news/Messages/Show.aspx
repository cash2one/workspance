<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeBehind="Show.aspx.cs" Inherits="Rcu_En_News.Web.Messages.Show" Title="ÏÔÊ¾Ò³" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
	<td height="25" width="30%" align="right">
		Msg_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblMsg_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Message
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblMessage" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Msg_Date
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblMsg_Date" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Guest
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblGuest" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Msg_State
	</td>
	<td height="25" width="*" align="left">
		<asp:CheckBox ID="chkMsg_State" Text="Msg_State" runat="server" Checked="False" />
	</td></tr>
</table>

</asp:Content>
