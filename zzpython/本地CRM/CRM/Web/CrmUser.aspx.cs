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

public partial class CRM_CrmUser : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!object.Equals(Session["PersonId"], null))
        {
            HF_Id.Value = Session["PersonId"].ToString();
            string tbField = "*";
            string tblName = "users";
            string strWhere = "id="+HF_Id.Value;
            DataSet DsUser = Common.ShowInfor(tbField, tblName, strWhere);
            if (DsUser.Tables[0].Rows.Count > 0)
            {
                txtName.Text = DsUser.Tables[0].Rows[0]["name"].ToString();
                TxtRealName.Text = DsUser.Tables[0].Rows[0]["realname"].ToString();
            }
        }
        else
        {
            Response.Redirect("CrmLogOut.aspx");
        }

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        string uid = HF_Id.Value;
        string oldpwd=TxtOldPwd.Text;
        string newpwd = TxtNewPwd.Text;
        string realname = Common.ReplaceText(TxtRealName.Text);
        int Num = ClassCRM.Update_Users_Pwd(uid,oldpwd, newpwd, realname);
        if (Num > 0)
        {
            Response.Write("<script>alert('密码修改成功')</script>");
            return;
        }
        else 
        {
            Response.Write("<script>alert('原密码错误')</script>");
            return;
        }
    }
}
