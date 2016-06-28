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
namespace Rcu_En_News.Web.Class
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
					//ShowInfo(Class_Id,Class_Code);
				}
			}
		}
		
private void ShowInfo(int Class_Id,int Class_Code)
{
	Rcu_En_News.BLL.rcu_en_news.Class bll=new Rcu_En_News.BLL.rcu_en_news.Class();
	Rcu_En_News.Model.rcu_en_news.Class model=bll.GetModel(Class_Id,Class_Code);
	this.lblClass_Name.Text=model.Class_Name;

}

    }
}
