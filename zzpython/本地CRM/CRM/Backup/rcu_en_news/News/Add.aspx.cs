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
namespace Rcu_En_News.Web.News
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
	if(this.txtNews_Title.Text =="")
	{
		strErr+="News_Title不能为空！\\n";	
	}
	if(this.txtNews_Contents.Text =="")
	{
		strErr+="News_Contents不能为空！\\n";	
	}
	if(!PageValidate.IsNumber(txtClass_Code.Text))
	{
		strErr+="Class_Code不是数字！\\n";	
	}
	if(!PageValidate.IsNumber(txtCountry_Id.Text))
	{
		strErr+="Country_Id不是数字！\\n";	
	}
	if(!PageValidate.IsDateTime(txtNews_Date.Text))
	{
		strErr+="News_Date不是时间格式！\\n";	
	}
	if(this.txtBig_Pic.Text =="")
	{
		strErr+="Big_Pic不能为空！\\n";	
	}
	if(this.txtSmall_Pic.Text =="")
	{
		strErr+="Small_Pic不能为空！\\n";	
	}
	if(this.txtNews_Poster.Text =="")
	{
		strErr+="News_Poster不能为空！\\n";	
	}
	if(!PageValidate.IsNumber(txtNews_Hits.Text))
	{
		strErr+="News_Hits不是数字！\\n";	
	}
	if(!PageValidate.IsNumber(txtMsg_Counts.Text))
	{
		strErr+="Msg_Counts不是数字！\\n";	
	}
	if(this.txtLables.Text =="")
	{
		strErr+="Lables不能为空！\\n";	
	}

	if(strErr!="")
	{
		MessageBox.Show(this,strErr);
		return;
	}
	string News_Title=this.txtNews_Title.Text;
	string News_Contents=this.txtNews_Contents.Text;
	int Class_Code=int.Parse(this.txtClass_Code.Text);
	int Country_Id=int.Parse(this.txtCountry_Id.Text);
	DateTime News_Date=DateTime.Parse(this.txtNews_Date.Text);
	bool News_state=this.chkNews_state.Checked;
	string Big_Pic=this.txtBig_Pic.Text;
	string Small_Pic=this.txtSmall_Pic.Text;
	string News_Poster=this.txtNews_Poster.Text;
	int News_Hits=int.Parse(this.txtNews_Hits.Text);
	int Msg_Counts=int.Parse(this.txtMsg_Counts.Text);
	string Lables=this.txtLables.Text;


	Rcu_En_News.Model.rcu_en_news.News model=new Rcu_En_News.Model.rcu_en_news.News();
	model.News_Title=News_Title;
	model.News_Contents=News_Contents;
	model.Class_Code=Class_Code;
	model.Country_Id=Country_Id;
	model.News_Date=News_Date;
	model.News_state=News_state;
	model.Big_Pic=Big_Pic;
	model.Small_Pic=Small_Pic;
	model.News_Poster=News_Poster;
	model.News_Hits=News_Hits;
	model.Msg_Counts=Msg_Counts;
	model.Lables=Lables;
	Rcu_En_News.BLL.rcu_en_news.News bll=new Rcu_En_News.BLL.rcu_en_news.News();
	bll.Add(model);
		}

    }
}
