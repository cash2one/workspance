<%@ Control Language="C#" AutoEventWireup="true" EnableViewState="false" Inherits="BlogEngine.Core.Web.Controls.PostViewBase" %>

<div class="post cate auth">
    <div class="postMultiTop container">
        <div class="post-date">
            <p class="month">
                <%=Post.DateCreated.ToString("MMMM") %></p>
            <p>
                <%=Post.DateCreated.ToString("d.") %>th</p>
        </div>
        <div class="postMultiRight">
            <h2 class="post-title">
                <a href="<%=Post.RelativeLink %>" class="taggedlink"><%=Server.HtmlEncode(Post.Title) %></a></h2>
            <h6 class="post-footer">
                зїеп: <a href="<%=VirtualPathUtility.ToAbsolute("~/") + "author/" + Post.Author %>.aspx"><%=Post.AuthorProfile != null ? Post.AuthorProfile.DisplayName : Post.Author %></a> | 
                <%=CategoryLinks(" | ") %>
                <%=Resources.labels.comments %>: <a rel="nofollow" href="<%=Post.RelativeLink %>#comment"><%=Post.ApprovedComments.Count %></a> | 
                <a rel="nofollow" href="<%=CommentFeed %>">Post RSS<img src="<%=VirtualPathUtility.ToAbsolute("~/pics/")%>rssButton.gif" alt="RSS comment feed" style="margin-left:3px" /></a> | 
                <%=AdminLinks %>
            </h6>
        </div>
    </div>
    <div class="post-body">
        <asp:PlaceHolder ID="BodyContent" runat="server" /></div>
        <%=Rating %>
    <h5 class="post-tags">
        Tags: <%=TagLinks(", ") %></h5>
</div>