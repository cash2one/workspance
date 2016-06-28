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
using System.Text;
namespace Rcu_En_News.Web.Country
{
    public partial class Show : System.Web.UI.Page
    {        
        		protected void Page_LoadComplete(object sender, EventArgs e)
		{
			(Master.FindControl("lblTitle") as Label).Text = "œÍœ∏–≈œ¢";
		}
		protected void Page_Load(object sender, EventArgs e)
		{
			if (!Page.IsPostBack)
			{
				if (Request.Params["id"] != null || Request.Params["id"].Trim() != "")
				{
					string id = Request.Params["id"];
					//ShowInfo(Country_Id);
				}
			}
		}
		
private void ShowInfo(int Country_Id)
{
	Rcu_En_News.BLL.rcu_en_news.Country bll=new Rcu_En_News.BLL.rcu_en_news.Country();
	Rcu_En_News.Model.rcu_en_news.Country model=bll.GetModel(Country_Id);
	this.lblCountry.Text=model.Country;
	this.lblNews_Counts.Text=model.News_Counts.ToString();

}

    }
}
