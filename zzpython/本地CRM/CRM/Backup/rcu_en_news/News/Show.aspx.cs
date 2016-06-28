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
namespace Rcu_En_News.Web.News
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
					//ShowInfo(News_Id);
				}
			}
		}
		
private void ShowInfo(int News_Id)
{
	Rcu_En_News.BLL.rcu_en_news.News bll=new Rcu_En_News.BLL.rcu_en_news.News();
	Rcu_En_News.Model.rcu_en_news.News model=bll.GetModel(News_Id);
	this.lblNews_Title.Text=model.News_Title;
	this.lblNews_Contents.Text=model.News_Contents;
	this.lblClass_Code.Text=model.Class_Code.ToString();
	this.lblCountry_Id.Text=model.Country_Id.ToString();
	this.lblNews_Date.Text=model.News_Date.ToString();
	this.chkNews_state.Checked=model.News_state;
	this.lblBig_Pic.Text=model.Big_Pic;
	this.lblSmall_Pic.Text=model.Small_Pic;
	this.lblNews_Poster.Text=model.News_Poster;
	this.lblNews_Hits.Text=model.News_Hits.ToString();
	this.lblMsg_Counts.Text=model.Msg_Counts.ToString();
	this.lblLables.Text=model.Lables;

}

    }
}
