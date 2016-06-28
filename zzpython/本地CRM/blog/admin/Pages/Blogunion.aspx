<%@ Page Language="C#" MasterPageFile="~/admin/admin1.master" AutoEventWireup="true" CodeFile="Blogunion.aspx.cs" Inherits="admin_Pages_blogunion" Title="博客联盟" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphAdmin" runat="Server">
<div class="settings">
  
  <h1 style="margin: 0 0 5px 0"><%=Resources.labels.add %> blog</h1>

  <label for="<%=txtTitle.ClientID %>" class="wide"><%=Resources.labels.title %></label>
  <asp:TextBox runat="server" ID="txtTitle" Width="600px" />
  <asp:RequiredFieldValidator runat="Server" ControlToValidate="txtTitle" ErrorMessage="需填写" /><br />
  
  <label for="<%=txtDescription.ClientID %>" class="wide"><%=Resources.labels.description %></label>
  <asp:TextBox runat="server" ID="txtDescription" Width="600px" />
  <asp:RequiredFieldValidator runat="Server" ControlToValidate="txtDescription" ErrorMessage="需填写" /><br />
  
  <label for="<%=txtWebUrl.ClientID %>" class="wide"><%=Resources.labels.website %></label>
  <asp:TextBox runat="server" ID="txtWebUrl" Width="600px" />
  <asp:RequiredFieldValidator runat="Server" ControlToValidate="txtWebUrl" ErrorMessage="需填写" /><br />
  
  <label for="<%=txtFeedUrl.ClientID %>" class="wide">RSS url</label>
  <asp:TextBox runat="server" ID="txtFeedUrl" Width="600px" />
  <asp:RequiredFieldValidator runat="Server" ControlToValidate="txtFeedUrl" ErrorMessage="需填写" /><br />
  
  <label for="<%=cblXfn.ClientID %>" class="wide">XFN tag</label>
  <asp:CheckBoxList runat="server" ID="cblXfn" CssClass="nowidth" RepeatColumns="8">
    <asp:ListItem Text="contact" />
    <asp:ListItem Text="acquaintance " />
    <asp:ListItem Text="friend " />
    <asp:ListItem Text="met" />
    <asp:ListItem Text="co-worker" />
    <asp:ListItem Text="colleague " />
    <asp:ListItem Text="co-resident" />
    <asp:ListItem Text="neighbor " />
    <asp:ListItem Text="child" />
    <asp:ListItem Text="parent" />
    <asp:ListItem Text="sibling" />
    <asp:ListItem Text="spouse" />
    <asp:ListItem Text="kin" />
    <asp:ListItem Text="muse" />
    <asp:ListItem Text="crush" />
    <asp:ListItem Text="date" />
    <asp:ListItem Text="sweetheart" />
    <asp:ListItem Text="me" />
  </asp:CheckBoxList>
  
  <div style="text-align:right">
    <asp:Button runat="server" ID="btnSave" />
  </div>
  
</div>
  
  <asp:Repeater runat="Server" ID="rep">
    <HeaderTemplate>
      <table style="width:100%;background-color:White" cellspacing="0" cellpadding="3" summary="Blogroll">
    </HeaderTemplate>
    <ItemTemplate>
      <tr>
        <td>
          <a href="<%#((System.Xml.XmlNode)Container.DataItem).Attributes["xmlUrl"].Value %>"><img src="../../pics/rssButton.gif" alt="RSS feed" /></a>
          <a href="<%#((System.Xml.XmlNode)Container.DataItem).Attributes["htmlUrl"].Value %>"><%#((System.Xml.XmlNode)Container.DataItem).Attributes["title"].Value %></a>
          &nbsp;<%#((System.Xml.XmlNode)Container.DataItem).Attributes["description"].Value %>
          &nbsp;(<%#((System.Xml.XmlNode)Container.DataItem).Attributes["xfn"].Value.Replace(";", " ")%>)
        </td>
        <td style="width:50px">
          <a href="?delete=<%#((System.Xml.XmlNode)Container.DataItem).Attributes["title"].Value %>" onclick="return confirm('确定要删除该项吗？')"><%=Resources.labels.delete %></a>
        </td>
      </tr>
    </ItemTemplate>
    <AlternatingItemTemplate>
      <tr class="alt">
        <td>
          <a href="<%#((System.Xml.XmlNode)Container.DataItem).Attributes["xmlUrl"].Value %>"><img src="../../pics/rssButton.gif" alt="RSS feed" /></a>
          <a href="<%#((System.Xml.XmlNode)Container.DataItem).Attributes["htmlUrl"].Value %>"><%#((System.Xml.XmlNode)Container.DataItem).Attributes["title"].Value %></a>
          &nbsp;<%#((System.Xml.XmlNode)Container.DataItem).Attributes["description"].Value %>
          &nbsp;(<%#((System.Xml.XmlNode)Container.DataItem).Attributes["xfn"].Value.Replace(";", " ") %>)
        </td>
        <td style="width:50px">
          <a href="?delete=<%#((System.Xml.XmlNode)Container.DataItem).Attributes["title"].Value %>" onclick="return confirm('确定要删除该项吗？')"><%=Resources.labels.delete %></a>
        </td>
      </tr>
    </AlternatingItemTemplate>
    <FooterTemplate>
      </Table>
    </FooterTemplate>
  </asp:Repeater>
  

  
</asp:Content>
