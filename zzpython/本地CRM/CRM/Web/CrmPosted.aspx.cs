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

public partial class CRM_CrmPosted : System.Web.UI.Page
{
    protected string pUserId;
    protected bool CrmState=false;
    protected static int Pager;
    protected string ReCrm_id;
    protected string ReCrm_Title;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!object.Equals(Request.QueryString["Page"], null))
        {
            Pager = Convert.ToInt32(Page.Request.QueryString["Page"]);
        }

        if (!object.Equals(Session["PersonId"], null))
        {
            pUserId = Session["PersonId"].ToString();
            string UserQx = Session["UserQx"].ToString();
            if (!Page.IsPostBack)
            {
                MyCrmList();
            }
        }
        else
        {
            Response.Redirect("CrmLogOut.aspx");
        }

        if (!object.Equals(Request.QueryString["MyCrmId"], null))
        {
            string MyCrmId = Request.QueryString["MyCrmId"].ToString();
            CrmInfor(MyCrmId);
            Crm_Infor.Visible = true;
        }
        else
        {
            Crm_Infor.Visible = false;
        }
    }

    void MyCrmList()
    {
        string strGetFields = "*";
        string fldName = "crm_Id";
        string tblName = "crm,cate_adminuser,users";
        string strWhere = " users.id=crm.to_Staff and users.userid=cate_adminuser.code and From_Staff=" + pUserId;

        if (CrmState)
        {
            strWhere = strWhere + " and crm_state='1'";
        }
        else
        {
             strWhere = strWhere + " and crm_state='0'";
        }

        try
        {
            DataSet DsCount = Common.GetCountsInfor(tblName, strWhere);
            AspNetPager1.RecordCount = (int)DsCount.Tables[0].Rows[0][0];
            AspNetPager1.PageSize =10;
            AspNetPager1.CurrentPageIndex = Pager;
            DataSet DsCrm = Common.GetPagedInfor(tblName, strGetFields, fldName, AspNetPager1.PageSize, AspNetPager1.CurrentPageIndex, true, strWhere);
            DsCrm.Tables[0].Columns.Add(new DataColumn("Crm_Finish", typeof(bool)));
            DsCrm.Tables[0].Columns.Add(new DataColumn("Crm_Request", typeof(string)));
            DsCrm.Tables[0].Columns.Add(new DataColumn("Crm_Reply", typeof(string)));
            if (DsCrm.Tables[0].Rows.Count > 0)
            {
                for (int i = 0; i < DsCrm.Tables[0].Rows.Count; i++)
                {
                    string isRe = DsCrm.Tables[0].Rows[i]["isRequest"].ToString();
                    string isFinish = DsCrm.Tables[0].Rows[i]["Crm_State"].ToString();
                    string Crm_id = DsCrm.Tables[0].Rows[i]["Crm_id"].ToString();
                    string Jobto = DsCrm.Tables[0].Rows[i]["to_staff"].ToString();
                    string isRequest = "";
                    bool Chk_Enable = false;
                    if (isRe == "True")
                    {
                        string getWork = "realname";
                        string wTable = "users";
                        string wWhere = "users.id=" + Jobto;
                        DataSet DsWork = Common.ShowInfor(getWork, wTable, wWhere);
                        if (DsWork.Tables[0].Rows.Count > 0)
                        {
                            isRequest = DsWork.Tables[0].Rows[0]["realname"].ToString(); ;
                        }
                    }
                    else
                    {
                        if (Session["UserQx"].ToString() == "1")
                        {
                            isRequest = "<a href=\"CrmManage.aspx?mCrmId=" + Crm_id + "#Re\" title=\"分配任务\">未分配</a>";
                        }
                        else
                        {
                            isRequest = "<font color=\"red\">未分配</font>";
                        }
                    }

                    if (isFinish == "False" && isRe == "True")
                    {
                        Chk_Enable = true;
                    }
                    else
                    {
                        Chk_Enable = false;
                    }

                    DsCrm.Tables[0].Rows[i]["Crm_Finish"] = Chk_Enable;
                    DsCrm.Tables[0].Rows[i]["Crm_Request"] = isRequest;

                    //是否有回复信息
                    string ReplyField = "crm_id";
                    string ReplyTable = "crm_Reply";
                    string ReplyWhere = "crm_id=" + Crm_id;
                    DataSet DsReply = Common.ShowInfor(ReplyField, ReplyTable, ReplyWhere);
                    int ReplyNum = DsReply.Tables[0].Rows.Count;
                    if (ReplyNum > 0)
                    {
                        DsCrm.Tables[0].Rows[i]["Crm_Reply"] = "<a href=\"CrmPosted.aspx?MyCrmId=" + Crm_id + "#Re\">(" + ReplyNum + ")</a>";
                    }
                    else
                    {
                        DsCrm.Tables[0].Rows[i]["Crm_Reply"] = "<font color=\"red\">(0)</a>";
                    }
                }
                State_Msg.InnerHtml = "      ";
                State_Msg.InnerHtml = DDL_Crm_State.SelectedItem.Text + ": 共(" + AspNetPager1.RecordCount + ")条";
                DL_MyCrm.Visible = true;
                DL_MyCrm.DataSource = DsCrm.Tables[0].DefaultView;
                DL_MyCrm.DataBind();
                NoRows.Visible = false;
            }
            else
            {
                NoRows.Visible = true;
                DL_MyCrm.Visible = false;
                NoRows.InnerHtml = "没有任务!";
                State_Msg.InnerHtml = DDL_Crm_State.SelectedItem.Text + ": 共(0)条";
            }
        }
        catch (Exception er)
        {
            Response.Write(er.Message);
            return;
        }
    }

    void CrmInfor(string CrmId)
    {
        string tbField = "*";
        string tblName = "crm,cate_adminuser,users";
        string strWhere = "crm.to_meno=cate_adminuser.code and crm.to_staff=users.id and crm_id=" + CrmId;
        DataSet DsCrm = Common.ShowInfor(tbField, tblName, strWhere);
        if (DsCrm.Tables[0].Rows.Count > 0)
        {
            sCrm_Title.InnerText = DsCrm.Tables[0].Rows[0]["crm_title"].ToString();
            sCrm_Aothur.InnerText = "TO: " + DsCrm.Tables[0].Rows[0]["realname"].ToString() + "[meno:" + DsCrm.Tables[0].Rows[0]["meno"].ToString() + "]  Date: " + DsCrm.Tables[0].Rows[0]["crm_date"].ToString();
            sCrm_Content.InnerHtml = DsCrm.Tables[0].Rows[0]["crm_content"].ToString();
            string AnnexPath = DsCrm.Tables[0].Rows[0]["crm_annex"].ToString();
            string[] AnnexName = AnnexPath.Split('/');
            if (AnnexName[0].Length > 0)
            {
                string GetAnnexName = AnnexName[4].ToString();
                sCrm_Annex.InnerHtml = "<img width=\"14px\" height=\"14px\" border=\"0\" src=\"images/math.gif\" />附件：";
                sCrm_Annex.InnerHtml = sCrm_Annex.InnerHtml + "<a href=\"" + AnnexPath + "\" target=\"_blank\">" + GetAnnexName + "</a>";
            }
            else
            {
                sCrm_Annex.InnerHtml = "&nbsp;";
            }
            ReplyList(CrmId);
        }
        else
        {
            return;
        }
    }

    void ReplyList(string CrmId)
    {
        string tbField = "*";
        string tblName = "crm_reply,users,cate_adminuser";
        string strWhere = "cate_adminuser.code=users.userid and crm_reply.reply_user=users.id and crm_reply.crm_id=" + CrmId;
        try
        {
            DataSet DsReplyList = Common.ShowInfor(tbField, tblName, strWhere + " order by reply_id desc");
            DsReplyList.Tables[0].Columns.Add(new DataColumn("ReplyColor", typeof(string)));
            if (DsReplyList.Tables[0].Rows.Count > 0)
            {
                for (int i = 0; i < DsReplyList.Tables[0].Rows.Count; i++)
                {
                    string ReplyColor = "blue";
                    string Reply_User = DsReplyList.Tables[0].Rows[i]["Reply_User"].ToString();
                    if (Reply_User == pUserId)
                    {
                        ReplyColor = "green";
                    }
                    else
                    {
                        ReplyColor = "blue";
                    }
                    DsReplyList.Tables[0].Rows[i]["ReplyColor"] = ReplyColor;
                }
                ReCrm_id = CrmId;
                ReCrm_Title = DsReplyList.Tables[0].Rows[0]["Reply_Title"].ToString();
                DL_GetReply.DataSource = DsReplyList.Tables[0].DefaultView;
                DL_GetReply.DataBind();
            }
            else
            {
                return;
            }
        }
        catch (Exception ReplyErr)
        {
            Response.Write(ReplyErr.Message);
            return;
        }
    }

    protected void Chk_All_CheckedChanged(object sender, EventArgs e)
    {
        for (int i = 0; i < DL_MyCrm.Items.Count; i++)
        {
            foreach (DataListItem DlMsg in DL_MyCrm.Items)
            {
                CheckBox cb = (CheckBox)DlMsg.FindControl("Checkbox1");
                if (cb.Enabled)
                {
                    cb.Checked = Chk_All.Checked;
                }
                else
                {
                    cb.Checked = false;
                }
            }
        }
    }

    protected void But_State_Click(object sender, EventArgs e)
    {
        int icount = 0;
        string CrmId = "";
        //在datalist中调用checkbox控件

        foreach (DataListItem DlCrm in DL_MyCrm.Items)
        {
            Label Label1 = (Label)DlCrm.FindControl("Label1");
            CheckBox CheckBox1 = (CheckBox)DlCrm.FindControl("Checkbox1");
            if (CheckBox1.Checked == true)
            {
                if (CrmId == "")
                {
                    CrmId = Label1.Text.ToString().Trim();
                }
                else
                {
                    CrmId = CrmId + "," + Label1.Text.ToString().Trim();
                }
                icount++;//显示多少条记录
            }
        }
        if (icount == 0)
        {
            State_Msg.InnerHtml = "<font color=\"red\">您没有选择任何项</font>";
        }
        else
        {
            try
            {
                int FinishNum = (int)ClassCRM.CC_Update_CRM_State(CrmId);
                if (FinishNum > 0)
                {
                    State_Msg.InnerHtml = "<font color=\"blue\">OK: " + icount + "项任务 验收成功</font>";
                    CrmState = true;
                    MyCrmList();
                    DDL_Crm_State.SelectedValue = "1";
                }
                else 
                {
                    State_Msg.InnerHtml = "<font color=\"red\">Error: 数据丢失,请刷新页面重新获取任务信息</font>";
                }
            }
            catch (Exception CrmStateError)
            {
                Response.Write(CrmStateError.Message);
                return;
            }
        }

    }

    protected void DDL_Crm_State_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (DDL_Crm_State.SelectedValue == "0")
        {
            CrmState = false;
            Pager = 1;
        }
        else
        {
            CrmState = true;
            Pager = 1;
        }
        MyCrmList();
    }
}