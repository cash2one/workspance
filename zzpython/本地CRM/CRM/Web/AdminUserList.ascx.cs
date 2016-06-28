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

public partial class AdminUserList : System.Web.UI.UserControl
{
    protected void Page_Load(object sender, EventArgs e)
    {
        ShowMeno();
    }

    void ShowMeno()
    {
        string tbField = "code,meno";
        string tblName = "cate_adminuser";
        string strWhere = "len(code)=2";
        string chWhere="";
        DataSet DsMeno = Common.ShowInfor(tbField, tblName, strWhere);
        StringBuilder MenoList = new StringBuilder();
        if (DsMeno.Tables[0].Rows.Count > 0)
        {
            foreach (DataRow oDRA in DsMeno.Tables[0].Rows)
            {
                string sCode = oDRA.ItemArray[0].ToString();
                string sMeno = oDRA.ItemArray[1].ToString();
                chWhere="code like '"+sCode+"__'";
                DataSet chDsMeno = Common.ShowInfor(tbField, tblName, chWhere);
                if (chDsMeno.Tables[0].Rows.Count > 0)
                {
                    MenoList.Append("<div>--TO:<strong>" + sMeno + "</strong></div>");
                    foreach (DataRow cHoDRA in chDsMeno.Tables[0].Rows)
                    {
                        string chCode = cHoDRA.ItemArray[0].ToString();
                        string chMeno = cHoDRA.ItemArray[1].ToString();
                        MenoList.Append("<div style=\"margin-left:20px;\">--TO:<a href='CrmSending.aspx?pCode=" + chCode + "' target=\"CrmRight\">" + chMeno + "</a></div>");
                    }
                }
                else
                {
                    MenoList.Append("<div>--TO:<a href='CrmSending.aspx?pCode=" + sCode + "' target=\"CrmRight\">" + sMeno + "</a></div>");
                }
            }

            GetMenoList.InnerHtml = MenoList.ToString();
        }
        else
        {
            return;
        }
    }
}
