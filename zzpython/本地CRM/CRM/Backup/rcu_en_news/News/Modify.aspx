<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeBehind="Modify.aspx.cs" Inherits="Rcu_En_News.Web.News.Modify" Title="修改页" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
	<tr>
	<td height="25" width="30%" align="right">
		News_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:label id="lblNews_Id" runat="server"></asp:label>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Title
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtNews_Title" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Contents
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtNews_Contents" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		分类号
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtClass_Code" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Country_Id
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtCountry_Id" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Date
	</td>
	<td height="25" width="*" align="left">
		<INPUT onselectstart="return false;" onkeypress="return false" id="txtNews_Date" onfocus="setday(this)"
		 readOnly type="text" size="10" name="Text1" runat="server">
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
		<asp:TextBox id="txtBig_Pic" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Small_Pic
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtSmall_Pic" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Poster
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtNews_Poster" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		News_Hits
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtNews_Hits" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		Msg_Counts
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtMsg_Counts" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" width="30%" align="right">
		多个标签用逗号格开
	</td>
	<td height="25" width="*" align="left">
		<asp:TextBox id="txtLables" runat="server" Width="200px"></asp:TextBox>
	</td></tr>
	<tr>
	<td height="25" colspan="2"><div align="center">
		<asp:Button ID="btnAdd" runat="server" Text="· 提交 ·" OnClick="btnAdd_Click" ></asp:Button>
		<asp:Button ID="btnCancel" runat="server" Text="· 取消 ·" OnClick="btnCancel_Click" ></asp:Button>
	</div></td></tr>
</table>

</asp:Content>
