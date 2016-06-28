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
namespace Rcu_En_News.Web.Class
{
    public partial class Modify : System.Web.UI.Page
    {       

        		protected void Page_LoadComplete(object sender, EventArgs e)
		{
			(Master.FindControl("lblTitle") as Label).Text = "��Ϣ�޸�";
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
	this.lblClass_Id.Text=model.Class_Id.ToString();
	this.lblClass_Code.Text=model.Class_Code.ToString();
	this.txtClass_Name.Text=model.Class_Name;

}
		protected void btnAdd_Click(object sender, EventArgs e)
		{
			
	string strErr="";
	if(this.txtClass_Name.Text =="")
	{
		strErr+="Class_Name����Ϊ�գ�\\n";	
	}

	if(strErr!="")
	{
		MessageBox.Show(this,strErr);
		return;
	}
	string Class_Name=this.txtClass_Name.Text;


	Rcu_En_News.Model.rcu_en_news.Class model=new Rcu_En_News.Model.rcu_en_news.Class();
	model.Class_Name=Class_Name;
	Rcu_En_News.BLL.rcu_en_news.Class bll=new Rcu_En_News.BLL.rcu_en_news.Class();
	bll.Update(model);
		}

    }
}
