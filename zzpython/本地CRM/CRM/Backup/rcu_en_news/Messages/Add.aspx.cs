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
    public partial class Add : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
        }

        		protected void Page_LoadComplete(object sender, EventArgs e)
		{
			(Master.FindControl("lblTitle") as Label).Text = "��Ϣ���";
		}
		protected void btnAdd_Click(object sender, EventArgs e)
		{
			
	string strErr="";
	if(!PageValidate.IsNumber(txtNews_Id.Text))
	{
		strErr+="News_Id�������֣�\\n";	
	}
	if(this.txtMessage.Text =="")
	{
		strErr+="Message����Ϊ�գ�\\n";	
	}
	if(!PageValidate.IsDateTime(txtMsg_Date.Text))
	{
		strErr+="Msg_Date����ʱ���ʽ��\\n";	
	}
	if(this.txtGuest.Text =="")
	{
		strErr+="Guest����Ϊ�գ�\\n";	
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
	bll.Add(model);
		}

    }
}
