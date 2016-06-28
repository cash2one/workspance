<%@ Page Language="C#" AutoEventWireup="true" CodeFile="VCode.aspx.cs" Inherits="VCode" %>

<%@ Register Src="AdminUserList.ascx" TagName="AdminUserList" TagPrefix="uc1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <link href="Crm.css" rel="stylesheet" type="text/css" />
    <title>try_catch</title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <asp:DataList ID="DataList_Crm" runat="server" DataKeyField="Crm_id" OnCancelCommand="DataList_Crm_CancelCommand" OnDeleteCommand="DataList_Crm_DeleteCommand" OnEditCommand="DataList_Crm_EditCommand" OnUpdateCommand="DataList_Crm_UpdateCommand">
        <HeaderTemplate>
            <table align="center" border="0" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr>
                        <th width="60px">Crm_Id</th>
                        <th width="60px">To_Meno</th>
                        <th width="60px">To_Staff</th>
                        <th width="300px">Crm_Title</th>
                        <th width="100px">Crm_Date</th>
                        <th width="60px">From_Meno</th>
                        <th width="60px">From_Staff</th>
                        <th width="60px">IsRequest</th>
                        <th width="40px"></th>
                        <th width="40px"></th>
                    </tr>
                </tbody>
            </table>
        </HeaderTemplate>
        <ItemTemplate>
        <table border="0" cellpadding="0" cellspacing="0">
        <tbody>
            <tr align="center">
            <td width="60px">
                <%#Eval("Crm_id")%></td>
            <td width="60px">
                <%#Eval("To_Meno")%></td>
            <td width="60px">
                <%#Eval("To_Staff")%></td>
            <td width="300px" align=left>
                <%#Eval("Crm_Title")%></td>
            <td width="100px" align=left>
                <%#Eval("Crm_Date")%></td>
            <td width="60px">
                <%#Eval("From_Meno")%></td>
            <td width="60px">
                <%#Eval("From_Staff")%></td>
            <td width="60px">
                <asp:CheckBox ID="CheckBox1" runat="server" Checked='<%#Eval("IsRequest")%>'/>
                </td>
            <td width="40px"><A href="#">编辑</A></td>
            <td width="40px"><A href="#">删除</A></td>
            </tr>
        </tbody>
        </table>
        </ItemTemplate>
        </asp:DataList>
        <asp:GridView ID="GridView2" runat="server">
        </asp:GridView>
    </div>
    </form>
</body>
</html>
