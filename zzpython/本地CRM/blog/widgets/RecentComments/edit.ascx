<%@ Control Language="C#" AutoEventWireup="true" CodeFile="edit.ascx.cs" Inherits="widgets_RecentPosts_edit" %>

<style type="text/css">
  #body label {display: block; float:left; width:150px}
  #body input {display: block; float:left; }
</style>

<div id="body">

<label for="<%=txtNumberOfPosts.ClientID %>">��������</label>
<asp:TextBox runat="server" ID="txtNumberOfPosts" Width="30" />
<asp:CompareValidator runat="Server" ControlToValidate="txtNumberOfPosts" Type="Integer" Operator="DataTypeCheck" ErrorMessage="��������Ч��" Display="Dynamic" />
<asp:RequiredFieldValidator runat="server" ControlToValidate="txtNumberOfPosts" ErrorMessage="��������Ч��" Display="dynamic" />

</div>