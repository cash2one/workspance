<%@ Page Language="C#" AutoEventWireup="true" CodeFile="tag.aspx.cs" Inherits="tag" ValidateRequest="false" %>
<%@ Import Namespace="BlogEngine.Core" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphBody" Runat="Server">
  <div id="contact">
    <div id="divForm" runat="server">
      <h1>±Í«©</h1>
      <blog:TagCloud ID="TagCloud1" runat="server" />      
    </div>
  </div>
</asp:Content>