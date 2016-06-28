using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using BlogEngine.Core;
using System.Collections.Generic;

public partial class admin_Pages_ApprovedComments : System.Web.UI.Page
{
    protected int TotalCommentCount = 0;
    protected int UnApprovedCommentCount = 0;

    protected void Page_Load(object sender, EventArgs e)
    {
        BindComments();
    }

    protected void BindComments()
    {
        List<BlogEngine.Core.Comment> Comments = new List<Comment>();

        foreach (Post post in Post.Posts)
        {
            if (post.Comments.Count == 0)
                continue;

            TotalCommentCount += post.Comments.Count;

            foreach (Comment comment in post.Comments)
            {
                if (comment.Email == "trackback" || comment.Email == "pingback" || comment.IsApproved)
                    continue;

                Comments.Add(comment);
                UnApprovedCommentCount++;
            }
        }

        this.UnApprovedCommentList.DataSource = Comments;
        this.UnApprovedCommentList.DataBind();
    }
}