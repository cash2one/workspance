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
using System.IO;

public partial class CRM_Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!object.Equals(Session["PersonId"], null))
        {
            sUserInfor.InnerHtml = "<span style=\"width:250px; float:left;\"><strong>用户名：</strong>";
            sUserInfor.InnerHtml = sUserInfor.InnerHtml + "<a href=\"CrmUser.aspx?uid="+Session["PersonId"].ToString()+"\" target=\"CrmRight\" title=\"个人信息\" style=\"margin-left:5px\">" + Session["UserName"].ToString() + "</a>";
            string ManageInfor = "";
            string showManagebar = "";
            switch (Convert.ToInt32(Session["UserQx"]))
            {
                case 0:
                    ManageInfor = "[系统管理员]";
                    showManagebar = "<span style=\"width:150px; float:right\"><a href=\"CrmManage.aspx\" target=\"CrmRight\">查看部门任务</a></span>";
                    break;
                case 1:
                    ManageInfor = "[部门主管]";
                    showManagebar = "<span style=\"width:150px; float:right\"><a href=\"CrmManage.aspx\" target=\"CrmRight\">查看部门任务</a></span>";
                    break;
                case 2:
                    ManageInfor = "[普通员工]";
                    break;
            }
            sUserInfor.InnerHtml = sUserInfor.InnerHtml + "<span style=\"margin-left:5px\"> " + ManageInfor + "</span></span>";
            string tbField="meno";
            string tblName="cate_adminuser";
            string strWhere="code="+Session["UserMeno"].ToString();
            DataSet SelMeno=Common.ShowInfor(tbField,tblName,strWhere);
            if (SelMeno.Tables[0].Rows.Count > 0)
            {
                string GetUserMeno = SelMeno.Tables[0].Rows[0]["meno"].ToString();
                sUserInfor.InnerHtml = sUserInfor.InnerHtml + "<span style=\"width:150px; float:left\"><strong> 部 门： </strong>" + GetUserMeno + "</span>";
                sUserInfor.InnerHtml = sUserInfor.InnerHtml + "<span style=\"width:120px; float:right\"><a href=\"CrmLogOut.aspx\" title=\"退出登录\">注 销</a></span>";
                sUserInfor.InnerHtml = sUserInfor.InnerHtml + showManagebar;
            }
            
        }
        else
        {
            Response.Redirect("CrmLogOut.aspx");
        }
    }
}
