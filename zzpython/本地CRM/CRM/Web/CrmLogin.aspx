<%@ Page Language="C#" AutoEventWireup="true" CodeFile="CrmLogin.aspx.cs" Inherits="CRM_CrmLogin" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title>CRM 登录页面</title>
    <style type="text/css">
        #sLogin{color:#000; font-size:12px;text-align:left; width:350px; padding:20px; font-weight:bold; }
        #sLogin strong{font-size:14pt; width:350px; text-align:center; float:left}
        #sLogin span{float:left; width:140px; padding:6px 3px; text-align: right; color:#666;}
        #sLogin div{margin-top:10px; }
    </style>
</head>
<body style="text-align: center">
    <form id="form1" runat="server">
        <div id="sLogin">
            <div>
                <strong>CRM&nbsp;&nbsp;Login</strong>
            </div>
            <div>
                <span>用户名：</span>
                <asp:TextBox ID="LoginName" runat="server" Width="150px"></asp:TextBox>
            </div>
            <div>
                <span>密码：</span>
                <asp:TextBox ID="LoginPwd" runat="server" TextMode="Password" Width="120px"></asp:TextBox>
            </div>
            <div>
                <span>
                    <asp:Button ID="But_Login" runat="server" OnClick="But_Login_Click" Text="登 陆" />
                </span><span id="err" runat="server" style="color: red; text-align: center"></span>
            </div>
        </div>
    </form>
</body>
</html>
