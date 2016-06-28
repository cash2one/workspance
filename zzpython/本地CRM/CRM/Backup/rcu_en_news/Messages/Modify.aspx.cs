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
namespace Rcu_En_News.Web.Messages
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
					//ShowInfo(Msg_Id);
				}
			}
		}
			
private void ShowInfo(int Msg_Id)
{
	Rcu_En_News.BLL.rcu_en_news.Messages bll=new Rcu_En_News.BLL.rcu_en_news.Messages();
	Rcu_En_News.Model.rcu_en_news.Messages model=bll.GetModel(Msg_Id);
	this.lblMsg_Id.Text=model.Msg_Id.ToString();
	this.txtNews_Id.Text=model.News_Id.ToString();
	this.txtMessage.Text=model.Message;
	this.txtMsg_Date.Text=model.Msg_Date.ToString();
	this.txtGuest.Text=model.Guest;
	this.chkMsg_State.Checked=model.Msg_State;

}
		protected void btnAdd_Click(object sender, EventArgs e)
		{
			
	string strErr="";
	if(!PageValidate.IsNumber(txtNews_Id.Text))
	{
		strErr+="News_Id不是数字！\\n";	
	}
	if(this.txtMessage.Text =="")
	{
		strErr+="Message不能为空！\\n";	
	}
	if(!PageValidate.IsDateTime(txtMsg_Date.Text))
	{
		strErr+="Msg_Date不是时间格式！\\n";	
	}
	if(this.txtGuest.Text =="")
	{
		strErr+="Guest不能为空！\\n";	
	}

	if(strErr!="")
	{
		MessageBox.Show(this,strErr);
		return;
	}
	int News_Id=int.Parse(this.txtNews_Id.Text);
	string Message=this.txtMessage.Text;
	DateTime Msg_Date=DateTime.Parse(this.txtMsg_Date.Text);
	string Guest=this.txtGuest.Text;
	bool Msg_State=this.chkMsg_State.Checked;


	Rcu_En_News.Model.rcu_en_news.Messages model=new Rcu_En_News.Model.rcu_en_news.Messages();
	model.News_Id=News_Id;
	model.Message=Message;
	model.Msg_Date=Msg_Date;
	model.Guest=Guest;
	model.Msg_State=Msg_State;
	Rcu_En_News.BLL.rcu_en_news.Messages bll=new Rcu_En_News.BLL.rcu_en_news.Messages();
	bll.Update(model);
		}

    }
}
