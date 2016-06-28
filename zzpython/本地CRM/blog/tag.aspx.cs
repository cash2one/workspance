#region Using

using System;
using System.Collections;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using BlogEngine.Core;
using BlogEngine.Core.Web.Controls;
using System.Net.Mail;
using System.Text.RegularExpressions;

#endregion

//by Spoony 08.02
public partial class tag : BlogBasePage
{
	protected void Page_Load(object sender, EventArgs e)
	{
        Page.Title = "±Í«©";
        base.AddMetaTag("description", "±Í«©");
	}    	

}
