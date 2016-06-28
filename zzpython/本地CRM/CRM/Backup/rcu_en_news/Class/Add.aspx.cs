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
    public partial class Add : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
        }

        		protected void Page_LoadComplete(object sender, EventArgs e)
		{
			(Master.FindControl("lblTitle") as Label).Text = "信息添加";
		}
		protected void btnAdd_Click(object sender, EventArgs e)
		{
	        string strErr="";
	        if(this.txtClass_Name.Text =="")
	        {
		        strErr+="Class_Name不能为空！\\n";	
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
	        bll.Add(model);
		}

    }
}
