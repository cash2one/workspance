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
using LTP.Common;
namespace Rcu_En_News.Web.Country
{
    public partial class Modify : System.Web.UI.Page
    {       

        		protected void Page_LoadComplete(object sender, EventArgs e)
		{
			(Master.FindControl("lblTitle") as Label).Text = "信息修改";
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
	this.lblCountry_Id.Text=model.Country_Id.ToString();
	this.txtCountry.Text=model.Country;
	this.txtNews_Counts.Text=model.News_Counts.ToString();

}
		protected void btnAdd_Click(object sender, EventArgs e)
		{
			
	string strErr="";
	if(this.txtCountry.Text =="")
	{
		strErr+="Country不能为空！\\n";	
	}
	if(!PageValidate.IsNumber(txtNews_Counts.Text))
	{
		strErr+="News_Counts不是数字！\\n";	
	}

	if(strErr!="")
	{
		MessageBox.Show(this,strErr);
		return;
	}
	string Country=this.txtCountry.Text;
	int News_Counts=int.Parse(this.txtNews_Counts.Text);


	Rcu_En_News.Model.rcu_en_news.Country model=new Rcu_En_News.Model.rcu_en_news.Country();
	model.Country=Country;
	model.News_Counts=News_Counts;
	Rcu_En_News.BLL.rcu_en_news.Country bll=new Rcu_En_News.BLL.rcu_en_news.Country();
	bll.Update(model);
		}

    }
}
