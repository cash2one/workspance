<%@ Page Language="C#" MasterPageFile="~/admin/admin1.master" AutoEventWireup="true" CodeFile="ApprovedComments.aspx.cs" Inherits="admin_Pages_ApprovedComments" Title="评论列表" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphAdmin" Runat="Server">

<div class="settings" style="background-color:#FFFFDD;" runat="server">
<h1>说明与使用提示</h1>
评论审核功能将待审核的评论地址列出来以便让您快速得知您的博客中有哪些评论需要您进行审核。您可以点击“原文链接”查看评论并进行审核。<br />
如果您需要这项功能，请在“<a href="Settings.aspx">高级设置</a>”页面的“评论”中勾选“允许评论”和“管理评论”。
</div>

<div class="settings" runat="server">        
    <h1>
        评论审核 - 评论共 <%=TotalCommentCount.ToString("###,###,##0")%> 条，其中 <span style="color:Red;"><%=UnApprovedCommentCount.ToString("###,###,##0")%></span> 条待审核。
    </h1>
    <asp:Repeater ID="UnApprovedCommentList" runat="server">
    <HeaderTemplate>
    <table width="100%">
    <thead><td><strong>评论者</strong></td><td><strong>评论时间</strong></td><td><strong>原文链接</strong></td></thead>
    </HeaderTemplate>
    
    <ItemTemplate>
       <tr><td><%# Eval("Author")%></td><td><%# Eval("DateCreated")%></td><td><a href="<%# Eval("AbsoluteLink")%>" target="_blank"><%# Eval("AbsoluteLink")%></a></td></tr>
    </ItemTemplate>
   
   <FooterTemplate>
   </table>
   </FooterTemplate>
  
   </asp:Repeater>
</div>
</asp:Content>

