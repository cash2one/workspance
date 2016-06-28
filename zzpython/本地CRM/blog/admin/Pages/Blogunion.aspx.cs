#region Using

using System;
using System.IO;
using System.Xml;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

#endregion

using BlogEngine.Core;

public partial class admin_Pages_blogunion : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            BindBlogunion();

            if (!String.IsNullOrEmpty(Request.QueryString["delete"]))
            {
                DeleteBlog();
                Response.Redirect(Request.FilePath, true);
            }
        }

        btnSave.Click += new EventHandler(btnSave_Click);

        Page.Title = "博客联盟";
        btnSave.Text = Resources.labels.add;
    }

    #region Event handlers

    private void btnSave_Click(object sender, EventArgs e)
    {
        AddBlog();
        Response.Redirect(Request.FilePath, true);
    }

    #endregion

    #region Methods

    private void BindBlogunion()
    {
        string fileName = Server.MapPath(BlogSettings.Instance.StorageLocation) + "blogunion.xml";
        if (File.Exists(fileName))
        {
            XmlDocument doc = new XmlDocument();
            doc.Load(fileName);

            rep.DataSource = doc.SelectNodes("opml/body/outline");
            rep.DataBind();
        }
    }

    private void AddBlog()
    {
        string fileName = Server.MapPath(BlogSettings.Instance.StorageLocation) + "blogunion.xml";
        if (File.Exists(fileName))
        {
            XmlDocument doc = new XmlDocument();
            doc.Load(fileName);

            XmlElement element = doc.CreateElement("outline");

            XmlAttribute title = doc.CreateAttribute("title");
            title.InnerText = txtTitle.Text.Replace("\"", "'");
            element.Attributes.Append(title);

            XmlAttribute desc = doc.CreateAttribute("description");
            desc.InnerText = txtDescription.Text;
            element.Attributes.Append(desc);

            XmlAttribute xfn = doc.CreateAttribute("xfn");
            foreach (ListItem item in cblXfn.Items)
            {
                if (item.Selected)
                    xfn.InnerText += item.Text + " ";
            }

            if (xfn.InnerText.Length > 0)
            {
                xfn.InnerText = xfn.InnerText.Substring(0, xfn.InnerText.Length - 1);
            }

            element.Attributes.Append(xfn);

            if (!txtFeedUrl.Text.Contains("://"))
                txtFeedUrl.Text = "http://" + txtFeedUrl.Text;

            XmlAttribute feed = doc.CreateAttribute("xmlUrl");
            feed.InnerText = txtFeedUrl.Text;
            element.Attributes.Append(feed);

            if (!txtWebUrl.Text.Contains("://"))
                txtWebUrl.Text = "http://" + txtWebUrl.Text;

            XmlAttribute web = doc.CreateAttribute("htmlUrl");
            web.InnerText = txtWebUrl.Text;
            element.Attributes.Append(web);

            XmlNode body = doc.SelectSingleNode("opml/body");
            body.AppendChild(element);
            doc.Save(fileName);
            BlogunionUpdater.UpdateBlogunion();
        }
    }

    private void DeleteBlog()
    {
        string title = Request.QueryString["delete"];
        string fileName = Server.MapPath(BlogSettings.Instance.StorageLocation) + "blogunion.xml";
        if (File.Exists(fileName))
        {
            XmlDocument doc = new XmlDocument();
            doc.Load(fileName);

            XmlNode parent = doc.SelectSingleNode("opml/body");
            XmlNode child = doc.SelectSingleNode("opml/body/outline[@title=\"" + title + "\"]");
            parent.RemoveChild(child);
            doc.Save(fileName);
            BlogunionUpdater.UpdateBlogunion();
        }
    }

    #endregion

}
