<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeBehind="Show.aspx.cs" Inherits="Rcu_En_News.Web.News.Show" Title="显示页" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
	<td height="25" width="30%" align="right">
		News_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Title
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Title" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Contents
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Contents" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		分类号
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblClass_Code" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Country_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblCountry_Id" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Date
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Date" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_state
	</td>
	<td height="25" width="*" align="left">
		<asp:CheckBox ID="chkNews_state" Text="News_state" runat="server" Checked="False" />
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Big_Pic
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblBig_Pic" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Small_Pic
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblSmall_Pic" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Poster
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Poster" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Hits
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblNews_Hits" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Msg_Counts
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblMsg_Counts" runat="server"></asp:Label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		多个标签用逗号格开
	</td>
	<td height="25" width="*" align="left">
		<asp:Label id="lblLables" runat="server"></asp:Label>
	</td></tr>
</table>

</asp:Content>
