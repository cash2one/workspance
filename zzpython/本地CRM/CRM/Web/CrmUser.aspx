<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmUser.aspx.cs" Inherits="CRM_CrmUser" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>无标题页</title>
    <link href="Crm.css" rel="stylesheet" type="text/css" />
    
</head>
<body>
    <form id="form1" runat="server">
    <div class="right">
        <table align="center" border="0" cellpadding="10" cellspacing="1" width="400">
            <tr>
                <td>
                    <table align="center" border="0" cellpadding="4" cellspacing="0" width="400">
                            <tr>
                                <td width="100">
                                    用户名</td>
                                <td>
                                    <asp:TextBox ID="txtName" runat="server" ReadOnly="True"></asp:TextBox>
                                    <asp:HiddenField ID="HF_Id" runat="server" /> 
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    原密码</td>
                                <td>
                                    <asp:TextBox ID="TxtOldPwd" runat="server" TextMode="Password" ValidationGroup="up"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="TxtOldPwd"
                                        ErrorMessage="*" ValidationGroup="up"></asp:RequiredFieldValidator></td>
                            </tr>
                            <tr>
                                <td>
                                    新密码</td>
                                <td>
                                    <asp:TextBox ID="TxtNewPwd" runat="server" TextMode="Password" ValidationGroup="up"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="TxtNewPwd"
                                        ErrorMessage="*" Width="2px" ValidationGroup="up"></asp:RequiredFieldValidator></td>
                            </tr>
                        <tr>
                            <td>
                                密码确认</td>
                            <td style="width: 292px">
                                <asp:TextBox ID="TxtPwdVail" runat="server" TextMode="Password" ValidationGroup="up"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="TxtPwdVail"
                                    ErrorMessage="*" ValidationGroup="up"></asp:RequiredFieldValidator><asp:CompareValidator ID="CompareValidator1"
                                        runat="server" ControlToCompare="TxtNewPwd" ControlToValidate="TxtPwdVail" ErrorMessage="两次输入的密码不一致" ValidationGroup="up"></asp:CompareValidator></td>
                        </tr>
                            <tr>
                                <td>
                                    真实姓名</td>
                                <td style="width: 292px">
                                    <asp:TextBox ID="TxtRealName" runat="server"></asp:TextBox></td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" style="padding-left:140px; text-align:left">
                                    <asp:Button ID="Button1" runat="server" Text="修 改" Width="60px" OnClick="Button1_Click" ValidationGroup="up" /></td>
                            </tr>
                    </table>
                </td>
            </tr>
        </table>
        </div>
    </form>
</body>
</html>
