<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeBehind="Show.aspx.cs" Inherits="Rcu_En_News.Web.Class.Show" Title="ÏÔÊ¾Ò³" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
	<td height="25" width="30%" align="right">
		Class_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblClass_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Class_Code
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblClass_Code" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Class_Name
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblClass_Name" runat="server"></asp:Label>
	</td></tr>
</table>

</asp:Content>
