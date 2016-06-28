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

public partial class CRM_CrmReceived : System.Web.UI.Page
{
    protected string pUserId;
    protected static int Pager;
    protected string ReCrm_id;
    protected string ReCrm_Title;
    protected bool CrmState = false;

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!object.Equals(Request.QueryString["Page"], null))
        {
            Pager = Convert.ToInt32(Page.Request.QueryString["Page"]);
        }
        
        if (!object.Equals(Session["PersonId"], null))
        {
            string UserQx = Session["UserQx"].ToString();
            pUserId = Session["PersonId"].ToString();
            GetCrmList();
            if (!object.Equals(Request.QueryString["MyCrmid"], null))
            {
                string Crm_id = Request.QueryString["MyCrmid"].ToString();
                CrmInfor(Crm_id);
                GetCrm_Infor.Visible = true;
            }
            else
            {
                GetCrm_Infor.Visible = false;
            }
        }
        else
        {
            Response.Redirect("CrmLogOut.aspx");
        }
    }

    void GetCrmList()
    {
        string strGetFields = "*";
        string fldName = "crm_Id";
        string tblName = "crm,cate_adminuser,users";
        string strWhere = " crm.from_Staff=users.id and users.userid=cate_adminuser.code and To_Staff=" + pUserId;

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
            AspNetPager1.PageSize = 10;
            AspNetPager1.CurrentPageIndex = Pager;
            DataSet DsGet = Common.GetPagedInfor(tblName, strGetFields, fldName, AspNetPager1.PageSize, AspNetPager1.CurrentPageIndex, true, strWhere);
            DsGet.Tables[0].Columns.Add(new DataColumn("Crm_Request", typeof(string)));
            DsGet.Tables[0].Columns.Add(new DataColumn("Crm_Reply", typeof(string)));
            if (DsGet.Tables[0].Rows.Count > 0)
            {
                NoRows.Visible = false;
                for (int i = 0; i < DsGet.Tables[0].Rows.Count; i++)
                {
                    string isRe = DsGet.Tables[0].Rows[i]["isRequest"].ToString();
                    string Crm_id = DsGet.Tables[0].Rows[i]["Crm_id"].ToString();
                    string Jobto = DsGet.Tables[0].Rows[i]["to_staff"].ToString();
                    string isRequest = "";
                    if (isRe=="True")
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
                        isRequest = "<a href=\"CrmManage.aspx?mCrmId=" + Crm_id + "#Re\" title=\"分配任务\">未分配</a>";
                    }
                    DsGet.Tables[0].Rows[i]["Crm_Request"] = isRequest;

                    //是否有回复信息
                    string ReplyField = "crm_id";
                    string ReplyTable = "crm_Reply";
                    string ReplyWhere = "crm_id=" + Crm_id;
                    DataSet DsReplyNum = Common.ShowInfor(ReplyField, ReplyTable, ReplyWhere);
                    int ReplyNum = DsReplyNum.Tables[0].Rows.Count;
                    if (ReplyNum > 0)
                    {
                        DsGet.Tables[0].Rows[i]["Crm_Reply"] = "<a href=\"CrmReceived.aspx?MyCrmid=" + Crm_id + "#Re\">(" + ReplyNum + ")</a>";
                    }
                    else
                    {
                        DsGet.Tables[0].Rows[i]["Crm_Reply"] = "<font color=\"red\">(0)</a>";
                    }
                }

                State_Msg.InnerHtml = "<span style=\"margin-left:20px;\"></span>";
                State_Msg.InnerHtml = DDL_Crm_State.SelectedItem.Text + ": 共(" + AspNetPager1.RecordCount + ")条";

                DL_GetCrm.Visible = true;
                DL_GetCrm.DataSource = DsGet.Tables[0].DefaultView;
                DL_GetCrm.DataBind();
            }
            else
            {
                NoRows.Visible = true;
                DL_GetCrm.Visible = false;
                NoRows.InnerHtml = "没有任务!";
                State_Msg.InnerHtml = DDL_Crm_State.SelectedItem.Text + ": 共(0)条";
            }
        }
        catch (Exception er)
        {
            Page.RegisterStartupScript("err", "<script>alert('"+er.Message+"')</script>");
            return;
        }
    }

    void CrmInfor(string CrmId)
    {
        string tbField = "*";
        string tblName = "crm,cate_adminuser,users";
        string strWhere = "crm.from_meno=cate_adminuser.code and crm.from_staff=users.id and crm_id=" + CrmId;
        DataSet DsCrm = Common.ShowInfor(tbField, tblName, strWhere);
        if (DsCrm.Tables[0].Rows.Count > 0)
        {
            sCrm_Title.InnerText = DsCrm.Tables[0].Rows[0]["crm_title"].ToString();
            sCrm_Aothur.InnerText = "From: " + DsCrm.Tables[0].Rows[0]["realname"].ToString() + "[Meno:" + DsCrm.Tables[0].Rows[0]["meno"].ToString() + "]  Date: " + DsCrm.Tables[0].Rows[0]["crm_date"].ToString();
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
                sCrm_Annex.InnerHtml = "<img width=\"14px\" height=\"14px\" border=\"0\" src=\"images/math.gif\" />附件：该信息没有附件";
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
            DataSet DsReplyList = Common.ShowInfor(tbField, tblName, strWhere+" order by reply_id desc");
            DsReplyList.Tables[0].Columns.Add(new DataColumn("ReplyColor", typeof(string)));
            if (DsReplyList.Tables[0].Rows.Count > 0)
            {
                for (int i = 0; i < DsReplyList.Tables[0].Rows.Count; i++)
                {
                    string Reply_User = DsReplyList.Tables[0].Rows[i]["Reply_User"].ToString();
                    string ReplyColor="blue";
                    if (Reply_User == pUserId)
                    {
                        ReplyColor = "green";
                    }
                    DsReplyList.Tables[0].Rows[i]["ReplyColor"]=ReplyColor;
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
        catch(Exception ReplyErr)
        {
            Response.Write(ReplyErr.Message);
            return;
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
        GetCrmList();
    }
}
