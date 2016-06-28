<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeBehind="Show.aspx.cs" Inherits="Rcu_En_News.Web.Country.Show" Title="ÏÔÊ¾Ò³" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
	<td height="25" width="30%" align="right">
		Country_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblCountry_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Country
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblCountry" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Counts
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Counts" runat="server"></asp:Label>
	</td></tr>
</table>

</asp:Content>
