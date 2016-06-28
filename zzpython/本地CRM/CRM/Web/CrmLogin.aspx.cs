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

public partial class CRM_CrmLogin : System.Web.UI.Page
{
    protected void Page_Init(object sender, EventArgs e)
    {
        HttpCookie cookie = Request.Cookies.Get("UserName");
        //string userName = cookie.Value;
        if (null == cookie)
        {
            return;
        }
        else
        {
            LoginName.Text = cookie.Value;
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!object.Equals(Session["PersonId"], null))
        {
            Response.Redirect("CrmMain.aspx");
        }

        if (LoginName.Text == "")
        {
            LoginName.Focus();
        }
        else
        {
            LoginPwd.Focus();
        }
    }

    protected void But_Login_Click(object sender, EventArgs e)
    {
        err.InnerHtml = "";
        if (LoginName.Text.Trim() == "")
        {
            err.InnerHtml = "请输入用户名";
            return;
        }

        if (LoginPwd.Text.Trim() == "")
        {
            err.InnerHtml = "请输入密码";
            return;
        }

        string tbField = "id,userid,name,userqx";
        string tblName = "users";
        string strWhere = "chatClose=1 and name='" + LoginName.Text.Trim() + "' and password='" + LoginPwd.Text.Trim() + "'";
        DataSet DsCrm = Common.ShowInfor(tbField, tblName, strWhere);
        try
        {
            if (DsCrm.Tables[0].Rows.Count > 0)
            {
                string LogUserId = DsCrm.Tables[0].Rows[0].ItemArray[0].ToString();
                string LogUserMeno = DsCrm.Tables[0].Rows[0].ItemArray[1].ToString();
                string LogUserName = DsCrm.Tables[0].Rows[0].ItemArray[2].ToString();
                string LogUserQx = DsCrm.Tables[0].Rows[0].ItemArray[3].ToString();
                Session["PersonId"] = LogUserId;
                Session["UserMeno"] = LogUserMeno;
                Session["UserName"] = LogUserName;
                int SupperManage = Convert.ToInt32(LogUserMeno);
                int Manage = Convert.ToInt32(LogUserQx);
                if (SupperManage == 10)
                {
                    Session["UserQx"] = "0";
                }
                else
                {
                    if (Manage == 1)
                    {
                        Session["UserQx"] = "1";//经理
                    }
                    else
                    {
                        Session["UserQx"] = "2";
                    }
                }
                HttpCookie cookie = new HttpCookie("UserName", LoginName.Text.Trim());
                cookie.Expires = DateTime.Now.AddDays(30);//超时时间 自定义 
                Response.Cookies.Add(cookie);
                Response.Redirect("CrmMain.aspx");
            }
            else
            {
                err.InnerHtml = "用户名或密码错误!";
            }
        }
        catch (Exception ex)
        {
            Response.Write(ex.Message);
        }
    }
}