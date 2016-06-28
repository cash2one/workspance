<%@ Control Language="C#" EnableViewState="False" Inherits="BlogEngine.Core.Web.Controls.CommentViewBase" %>
<%@ Import Namespace="BlogEngine.Core" %>

<div class="box">
    <ul class="msg box_in">
        <li class="msgname"><span class="comment-quote-icon">
            <a name="cmt<%=Comment.Id %>"><img src="<%=Utils.RelativeWebRoot %>themes/SummerGreen/images/quote.gif" height="9" width="9" /></a></span>&nbsp;<%= Comment.Website != null ? "<a href=\"" + Comment.Website + "\">" + Comment.Author + "</a>" : Comment.Author %> <%= Flag %></li>
        <li class="msgarticle"><%= Text %></li>
        <li class="msgtime"><%= Comment.DateCreated %> <%= AdminLinks %></li>
    </ul>
</div>