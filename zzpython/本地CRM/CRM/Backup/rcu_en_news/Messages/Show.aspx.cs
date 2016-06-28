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
namespace Rcu_En_News.Web.Messages
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
                    //ShowInfo(id);
				}
			}
		}
		
        private void ShowInfo(int Msg_Id)
        {
	        Rcu_En_News.BLL.rcu_en_news.Messages bll=new Rcu_En_News.BLL.rcu_en_news.Messages();
	        Rcu_En_News.Model.rcu_en_news.Messages model=bll.GetModel(Msg_Id);
	        this.lblNews_Id.Text=model.News_Id.ToString();
	        this.lblMessage.Text=model.Message;
	        this.lblMsg_Date.Text=model.Msg_Date.ToString();
	        this.lblGuest.Text=model.Guest;
	        this.chkMsg_State.Checked=model.Msg_State;

        }

    }
}
