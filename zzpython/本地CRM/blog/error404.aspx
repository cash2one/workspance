<%@ Page Language="C#" AutoEventWireup="true" CodeFile="error404.aspx.cs" Inherits="error404" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphBody" Runat="Server">
  <div class="post error404">
    <h1>噢！您正试图打开的网页没找到！</h1>
    <div id="divSearchEngine" runat="server" visible="False" class="search">
      <p>
        您刚刚在 <strong><a href="<%=Request.UrlReferrer %>"><%=Request.UrlReferrer.Host %></a></strong>
        搜索 <strong><%=SearchTerm %></strong>。但索引似乎过期。
      </p>
      <h2>条目并未失效</h2>
      <p>以下页面可能对您有用：</p>
      <div id="divSearchResult" runat="server" />
    </div>
    
    <div id="divExternalReferrer" runat="server" visible="False">
      <p>
        发生了错误并被重定位到该页面： 
        <a href="javascript:history.go(-1)"><%=Request.UrlReferrer.Host %></a> 
      </p>
      
      <p>建议您尝试以下链接：</p>
      <ul>
        <li><a href="archive.aspx"><%=Resources.labels.archive %></a></li>
        <li><a href="<%=BlogEngine.Core.Utils.RelativeWebRoot %>">首页</a></li>
      </ul>
      
      <p>您还可以尝试<strong>搜索您刚刚寻找的页面</strong>：</p>
      <blog:SearchBox runat="server" />
      
      <p>对于给您造成的不便深表歉意！</p>
    </div>
    
    <div id="divInternalReferrer" runat="server" visible="False">
      <p>
        事情已经发生了！请接受我们的道歉。由于开发能力有限，有 20 多项错误被重定位到这个页面。
        （我们将在以后的工作中修复这些问题）。
      </p>
      
      <p>您还可以尝试<strong>搜索您刚刚寻找的页面</strong>：</p>
      <blog:SearchBox ID="SearchBox2" runat="server" /><br /><br />
    </div>
    
    <div id="divDirectHit" runat="server" visible="False">
      <p>以下页面可能对您有用：</p>
      <ul>
        <asp:placeholder runat="server" id="phSearchResult" />
        <li><a href="archive.aspx"><%=Resources.labels.archive %></a></li>
        <li><a href="<%=BlogEngine.Core.Utils.RelativeWebRoot %>">首页</a></li>
      </ul>
      
      <p>您还可以尝试<strong>搜索您刚刚寻找的页面</strong>：</p>
      <blog:SearchBox ID="SearchBox1" runat="server" />
      
      <hr />
      
      <p><strong>可能由于以下因素您并未能您需要的页面：</strong></p>
      <ol type="a">
        <li>书签或收藏夹中<strong>条目已经过期</strong></li>
        <li>搜索引擎针对我们的<strong>条目已经过期</strong></li>
        <li><strong>未知的地址类型</strong></li>
      </ol>
    </div>
  </div>
</asp:Content>