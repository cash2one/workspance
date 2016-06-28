using System;
using System.IO;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Web;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;

public partial class VCode : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        string strTime = System.DateTime.Now.ToString("yyyy-MM-dd   hh:mm:ss");
        Response.Write(strTime);

        if (!Page.IsPostBack)
        {
            BindUsers();
            BindCrm();
        }
    }

    void BindCrm()
    {
        string tbField = "Crm_Id,To_Meno,To_Staff,Crm_Title,Crm_Date,From_Meno,From_Staff,IsRequest";
        string tblName = "crm";
        string strWhere = "";
        DataSet DsCrm = Common.ShowInfor(tbField, tblName, strWhere);
        if (DsCrm.Tables[0].Rows.Count > 0)
        {
            DataList_Crm.DataSource = DsCrm.Tables[0].DefaultView;
            DataList_Crm.DataBind();
        }
        else
        {
            return;
        }
    }

    protected void DataList_Crm_EditCommand(object source, DataListCommandEventArgs e)
    {
        DataList_Crm.EditItemIndex = e.Item.ItemIndex;
        BindCrm();
    }

    protected void DataList_Crm_CancelCommand(object source, DataListCommandEventArgs e)
    {
        DataList_Crm.EditItemIndex = -1;
        BindCrm();
    }

    //DataList_Crm更新
    protected void DataList_Crm_UpdateCommand(object source, DataListCommandEventArgs e)
    {
        string Crm_id = DataList_Crm.DataKeys[e.Item.ItemIndex].ToString();
        int update_pClass = 1;// (int)UpdateClass.Update_Country(Country_id, Country);
        if (update_pClass < 0)
        {
            Response.Write("<script>alert('update失败,信息不存在:id=" + Crm_id + "')</script>");
        }
        DataList_Crm.EditItemIndex = -1;
        BindCrm();
    }

    //DataList_Crm删除
    protected void DataList_Crm_DeleteCommand(object source, DataListCommandEventArgs e)
    {
        string Crm_id = DataList_Crm.DataKeys[e.Item.ItemIndex].ToString();
        int isDel = 1;// (int)DelClass.Del_Country(Crm_id);
        if (isDel < 0)
        {
            Response.Write("<script>alert('error,Data loss:id=" + Crm_id + "')</script>");
            return;
        }
        DataList_Crm.EditItemIndex = -1;
        BindCrm();
    }

    void BindUsers()
    {
        string tbField1 = "*";
        string tblName1 = "cate_adminuser";
        string strWhere1 = "";
        DataSet DsUser = Common.ShowInfor(tbField1, tblName1, strWhere1);
        if (DsUser.Tables[0].Rows.Count > 0)
        {
            GridView2.DataSource = DsUser.Tables[0].DefaultView;
            GridView2.DataBind();
        }
        else
        {
            return;
        }
    }
}

