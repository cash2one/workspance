using System;
using System.Collections;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Text;
using System.Threading;

public partial class User_controls_xmanager_ExtensionsList : System.Web.UI.UserControl
{
    /// <summary>
    /// handles page load event
    /// </summary>
    /// <param name="sender">Page</param>
    /// <param name="e">Arguments</param>
    protected void Page_Load(object sender, EventArgs e)
    {
        lblErrorMsg.InnerHtml = string.Empty;
        lblErrorMsg.Visible = false;
        lblExtensions.Text = GetExtensions();
        btnRestart.Visible = false;


        object act = Request.QueryString["act"];
        object ext = Request.QueryString["ext"];

        if (act != null && ext != null)
        {
            ChangeStatus(act.ToString(), ext.ToString());
        }

        btnRestart.Click += new EventHandler(btnRestart_Click);
    }

    /// <summary>
    /// Test stuff - ignore for now
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    void btnRestart_Click(object sender, EventArgs e)
    {
        // This short cercuits the IIS process. Need to find a better way to restart the app.
        //ThreadPool.QueueUserWorkItem(delegate { ForceRestart(); });
        //ThreadStart threadStart = delegate { ForceRestart(); };
        //Thread thread = new Thread(threadStart);
        //thread.IsBackground = true;
        //thread.Start();
        Response.Redirect(Request.RawUrl, true);

    }
    public void ForceRestart()
    {
        throw new ApplicationException();
    }

    /// <summary>
    /// Get extensions data from extension manager
    /// and format data as html table
    /// </summary>
    /// <returns>Html-formated table</returns>
    private string GetExtensions()
    {
        //TODO: localize strings
        string confirm = "您的网站将中断运行几秒钟。您确定要继续吗？";//by Spoony
        string jsOnClick = "onclick=\"if (confirm('" + confirm + "')) { window.location.href = this.href } return false;\"";
        string clickToEnable = "点击启用 ";
        string clickToDisable = "点击禁用 ";
        string enabled = "已启用";
        string disabled = "已禁用";

        List<ManagedExtension> extList = ExtensionManager.Extensions;
        StringBuilder sb = new StringBuilder("<table style='background:#fff;width:100%'>");
        sb.Append("<tr style='background:#fff'>");
        sb.Append("<th>名称</th>");
        sb.Append("<th>版本</th>");
        sb.Append("<th>描述</th>");
        sb.Append("<th>作者</th>");
        sb.Append("<th>状态</th>");
        sb.Append("<th>代码</th>");
        sb.Append("<th>配置</th>");
        sb.Append("</tr>");

        if (extList != null)
        {
            int alt = 0;
            foreach (ManagedExtension x in extList)
            {
                if (alt % 2 == 0)
                    sb.Append("<tr style='background:#f9f9f9'>");
                else
                    sb.Append("<tr>");

                sb.Append("<td style='padding:2px'>" + x.Name + "</td>");
                sb.Append("<td style='padding:2px'>" + x.Version + "</td>");
                sb.Append("<td style='padding:2px'>" + x.Description + "</td>");
                sb.Append("<td style='padding:2px'>" + x.Author + "</td>");

                if (x.Enabled)
                    sb.Append("<td align='center' style='background:#ccffcc'><a href='?act=dis&ext=" + x.Name + "' title='" + clickToDisable + x.Name + "' " + jsOnClick + ">" + enabled + "</a></td>");
                else
                    sb.Append("<td align='center' style='background:#ffcc66'><a href='?act=enb&ext=" + x.Name + "' title='" + clickToEnable + x.Name + "' " + jsOnClick + ">" + disabled + "</a></td>");

                sb.Append("<td align='center'><a href='?ctrl=editor&ext=" + x.Name + "'>" + "查看" + "</a></td>");

                // link to settings page
                if (!string.IsNullOrEmpty(x.AdminPage))
                {
                    string url = BlogEngine.Core.Utils.AbsoluteWebRoot.AbsoluteUri;
                    if (!url.EndsWith("/"))
                        url += "/";

                    if (x.AdminPage.StartsWith("~/"))
                        url += x.AdminPage.Substring(2);
                    else if (x.AdminPage.StartsWith("/"))
                        url += x.AdminPage.Substring(1);
                    else
                        url += x.AdminPage;

                    sb.Append("<td align='center'><a href='" + url + "'>" + Resources.labels.edit + "</a></td>");
                }
                else
                {
                    if (x.Settings == null)
                    {
                        sb.Append("<td>&nbsp;</td>");
                    }
                    else
                    {
						if (x.Settings.Count == 0 || (x.Settings.Count == 1 && x.Settings[0] == null) || x.ShowSettings == false)
							sb.Append("<td>&nbsp;</td>");
						else
							sb.Append("<td align='center'><a href='?ctrl=params&ext=" + x.Name + "'>" + Resources.labels.edit + "</a></td>");
                    }
                }

                sb.Append("</tr>");
                alt++;
            }
        }
        sb.Append("</table>");
        return sb.ToString();
    }
    
    /// <summary>
    /// Method to change extension status
    /// to enable or disable extension and
    /// then will restart applicaton by
    /// touching web.config file
    /// </summary>
    /// <param name="act">Enable or Disable</param>
    /// <param name="ext">Extension Name</param>
    void ChangeStatus(string act, string ext)
    {
        // UnloadAppDomain() requires full trust - touch web.config to reload app
        try
        {
            if (act == "dis")
            {
                ExtensionManager.ChangeStatus(ext, false);
            }
            else
            {
                ExtensionManager.ChangeStatus(ext, true);
            }

            if (ExtensionManager.FileAccessException == null)
            {
                //string ConfigPath = HttpContext.Current.Request.PhysicalApplicationPath + "\\web.config";
                //System.IO.File.SetLastWriteTimeUtc(ConfigPath, DateTime.UtcNow);
                Response.Redirect("default.aspx");
            }
            else
            {
                ShowError(ExtensionManager.FileAccessException);
            }
        }
        catch (Exception e)
        {
            ShowError(e);
        }
    }
    
    /// <summary>
    /// Show error message if something
    /// goes wrong
    /// </summary>
    /// <param name="e">Exception</param>
    void ShowError(Exception e)
    {
        lblErrorMsg.Visible = true;
        lblErrorMsg.InnerHtml = "尚未应用更改: " + e.Message;//by Spoony
    }
}
