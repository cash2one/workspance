<%@ Control Language="C#" AutoEventWireup="true" EnableViewState="false" Inherits="BlogEngine.Core.Web.Controls.PostViewBase" %>
<%@ Import Namespace="BlogEngine.Core" %>

<div class="PostHead">
    <div class="PostHeadTop"></div>
	<div id="vote-wrap-8890" class="vote-wrap">
<img src="<%=Utils.RelativeWebRoot %>themes/Qeeke/images/Novo_Dangos_001.gif"/>
	</div>
		  <h1><a href="<%=Post.RelativeLink %>" rel="bookmark"><%=Server.HtmlEncode(Post.Title) %></a></h1>
          <span class="submitted"><a href="<%=VirtualPathUtility.ToAbsolute("~/") + "author/" + Post.Author %>.aspx"><%=Post.AuthorProfile != null ? Post.AuthorProfile.DisplayName : Post.Author %></a> | 发布时间: <%=Post.DateCreated.ToString("d. MMMM yyyy HH:mm") %></span>
</div>
        <div class="PostContent">
          <asp:PlaceHolder ID="BodyContent" runat="server" />
        </div>
        <div class="bottom">
            <%=Rating %>
          </div>
        <div class="clear"></div>
        <div class="tags">
          <p>标签:</p>
          <ul class="links inline"><li class="first last taxonomy_term_6"><%=TagLinks(" ") %>  分类:<%=CategoryLinks(" ")%></li></ul>
        </div>
        <div class="tools">
          <div class="links"><ul class="links inline"><li class="first comment_add"><a href="<%=Post.RelativeLink %>#comment" title="在本页添加新的评论。" class="comment_add">添加新评论</a></li>
		  <li class="last statistics_counter"><span class="statistics_counter">评论次数:<%=Post.ApprovedComments.Count %></span><span id="spn<%=Post.Id %>"> <span class="statistics_adminlinks"><%=AdminLinks %></span></span>
</li>
</ul></div>
</div>