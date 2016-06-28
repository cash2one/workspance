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
using System.Text.RegularExpressions;

public partial class CRM_CrmManage : System.Web.UI.Page
{
    protected static string UserQx;
    protected static string Meno_id;
    protected static string Staff_id;
    protected string strWhere_Meno = "";
    protected string postUrl;
    protected static int Pager;
    protected string ReCrm_id;
    protected string ReCrm_Title;


    protected void Page_Load(object sender, EventArgs e)
    {
        PostUrl();
        if (!object.Equals(Request.QueryString["PerId"], null) && Request.QueryString["PerId"].ToString()!="")
        {
            DDL_UserId.SelectedValue = Request.QueryString["PerId"].ToString();
            Hid_strWhere.Value = "to_staff=" + Request.QueryString["PerId"].ToString();
            RBL_Staff.SelectedValue = "1";
            GetCrmList();
        }
        if (!Page.IsPostBack)
        {
            //获取分页号
            if (!object.Equals(Request.QueryString["Page"], null))
            {
                Pager = Convert.ToInt32(Page.Request.QueryString["Page"]);
            }
            else
            {
                Pager = 1;
            }
            if (!object.Equals(Session["PersonId"], null))
            {
                UserQx = Session["UserQx"].ToString();
                Meno_id = Session["UserMeno"].ToString();
                Staff_id = Session["PersonId"].ToString();
                switch (Convert.ToInt32(UserQx))
                {
                    case 0:
                        Hid_strWhere.Value = Hid_strWhere.Value;
                        strWhere_Meno = "";
                        break;
                    case 1:
                        strWhere_Meno = "code=" + Meno_id;
                        Hid_strWhere.Value = Hid_strWhere.Value + " and (From_Meno=" + Meno_id + " or To_Meno=" + Meno_id + ")";
                        break;
                    case 2:
                        Response.Redirect("CrmReceived.aspx");
                        break;
                }
            }
            else
            {
                Response.Redirect("CrmLogOut.aspx");
            }

            BindMeno();
            GetUrlSearch();
            GetCrmList();

            //显示详细信息
            if (!object.Equals(Request.QueryString["MyCrmId"], null))
            {
                Crm_Infor.Visible = true;
                Hid_CrmId.Value = Request.QueryString["MyCrmId"].ToString();
                CrmInfor(Hid_CrmId.Value);
            }
            else
            {
                Crm_Infor.Visible = false;
            }
        }

    }
    
    void GetCrmList()
    {
        string strGetFields = "*";
        string fldName = "crm_id";
        string tblName = "crm";
        try
        {
            DataSet DsCount = Common.GetCountsInfor(tblName, Hid_strWhere.Value);
            AspNetPager1.RecordCount = (int)DsCount.Tables[0].Rows[0][0];
            AspNetPager1.PageSize = 10;
            AspNetPager1.CurrentPageIndex = Pager;
            DataSet DsGet = Common.GetPagedInfor(tblName, strGetFields, fldName, AspNetPager1.PageSize, AspNetPager1.CurrentPageIndex, true, Hid_strWhere.Value);
            DsGet.Tables[0].Columns.Add(new DataColumn("F_Staff", typeof(string)));
            DsGet.Tables[0].Columns.Add(new DataColumn("F_meno", typeof(string)));
            DsGet.Tables[0].Columns.Add(new DataColumn("T_meno", typeof(string)));
            DsGet.Tables[0].Columns.Add(new DataColumn("Crm_Request", typeof(string)));
            if (DsGet.Tables[0].Rows.Count > 0)
            {
                NoRows.Visible = false;
                for (int i = 0; i < DsGet.Tables[0].Rows.Count; i++)
                {
                    string isRe = DsGet.Tables[0].Rows[i]["isRequest"].ToString();
                    string Crm_id = DsGet.Tables[0].Rows[i]["Crm_id"].ToString();
                    string Jobto = DsGet.Tables[0].Rows[i]["to_staff"].ToString();
                    string isRequest = "";

                    if (isRe== "True")
                    {
                        string getWork = "realname";
                        string wTable = "users";
                        string wWhere = "users.id=" + Jobto;
                        DataSet DsWork = Common.ShowInfor(getWork, wTable, wWhere);

                        if (DsWork.Tables[0].Rows.Count > 0)
                        {
                            isRequest = DsWork.Tables[0].Rows[0]["realname"].ToString(); ;
                        }
                        else
                        {
                            isRequest = "<a href=\"" + postUrl + Crm_id + "\" title=\"分配任务\">未分配</a>";
                        }
                    }
                    else
                    {
                        isRequest = "<a href=\"" + postUrl + Crm_id + "\" title=\"分配任务\">未分配</a>";
                    }
                    DsGet.Tables[0].Rows[i]["Crm_Request"] = isRequest;
                    string From_mCode = DsGet.Tables[0].Rows[i]["From_meno"].ToString();
                    string From_Staff = DsGet.Tables[0].Rows[i]["From_Staff"].ToString();
                    string To_mCode = DsGet.Tables[0].Rows[i]["To_meno"].ToString();
                    try
                    {
                        string fromWhere = "code=" + From_mCode;
                        DataSet GetFromMeno = Common.ShowInfor("meno", "cate_adminuser", fromWhere);
                        string From_Meno = GetFromMeno.Tables[0].Rows[0].ItemArray[0].ToString();
                        DsGet.Tables[0].Rows[i]["F_meno"] = From_Meno;

                        string staffWhere = "id=" + From_Staff;
                        DataSet GetFromStaff = Common.ShowInfor("realname", "users", staffWhere);
                        string gF_Staff = GetFromStaff.Tables[0].Rows[0].ItemArray[0].ToString();
                        DsGet.Tables[0].Rows[i]["F_Staff"] = gF_Staff;

                    }
                    catch (Exception FS)
                    {
                        Response.Write("FS:" + FS.Message);
                        return;
                    }
                    try
                    {
                        string toWhere = "code=" + To_mCode;
                        DataSet GetToStaff = Common.ShowInfor("meno", "cate_adminuser", toWhere);
                        string To_Meno = GetToStaff.Tables[0].Rows[0].ItemArray[0].ToString();
                        DsGet.Tables[0].Rows[i]["T_meno"] = To_Meno;
                    }
                    catch (Exception TS)
                    {
                        Response.Write("TS:" + TS.Message);
                        return;
                    }
                }
                DL_ManageCrm.DataSource = DsGet.Tables[0].DefaultView;
                DL_ManageCrm.DataBind();
            }
            else
            {
                NoRows.Visible = true;
                NoRows.InnerHtml="没有您要的信息";
                return;
            }
        }
        catch (Exception er)
        {
            Response.Write(er);
            return;
        }
    }

    void BindMeno()
    {
        string rbField = "code,meno";
        string tblName = "cate_adminuser";
        DataSet DsMeno = Common.ShowInfor(rbField, tblName, strWhere_Meno);
        DDL_Meno.DataTextField = "meno";
        DDL_Meno.DataValueField = "code";
        DDL_Meno.DataSource = DsMeno.Tables[0].DefaultView;
        DDL_Meno.DataBind();
        BindUserName();
    }

    void BindUserName()
    {
        string rbField = "id,realname";
        string tblName = "users";
        string strWhere_User = "chatflag=1 and chatclose=1 and userid=" + DDL_Meno.SelectedValue;
        DataSet DsMeno = Common.ShowInfor(rbField, tblName, strWhere_User);
        DDL_UserId.DataTextField = "realname";
        DDL_UserId.DataValueField = "id";
        DDL_UserId.DataSource = DsMeno.Tables[0].DefaultView;
        DDL_UserId.DataBind();
    }

    void CrmInfor(string CrmId)
    {
            string tbField = "*";
            string tblName = "crm,cate_adminuser,users";
            string strWhere = "crm.from_meno=cate_adminuser.code and crm.from_staff=users.id and crm_id=" + CrmId;
            DataSet DsCrm = Common.ShowInfor(tbField, tblName, strWhere);
            if (DsCrm.Tables[0].Rows.Count > 0)
            {
                mCrm_Title.InnerText = DsCrm.Tables[0].Rows[0]["crm_title"].ToString();
                mCrm_AothurF.InnerText = "From: " + DsCrm.Tables[0].Rows[0]["realname"].ToString() + " [" + DsCrm.Tables[0].Rows[0]["meno"].ToString() + "]";
            
                //判断任务是否已经分配出去
                string istbField = "to_meno";
                string istblName = "crm";
                string isWhere = "isrequest=1 and crm_id=" + CrmId;
                DataSet DsIsre = Common.ShowInfor(istbField, istblName, isWhere);
                if (DsIsre.Tables[0].Rows.Count > 0)
                {
                    mCrm_state.InnerHtml = "任务已经分配";
                    mCrm_Request.Visible = false;
                    mCrm_AothurT.Visible = true;
                    string tbField_to = "realname,meno";
                    string tblName_to = "crm,cate_adminuser,users";
                    string strWhere_to = "crm.to_meno=cate_adminuser.code and crm.to_staff=users.id and crm_id=" + CrmId;
                    DataSet DsCrmTo = Common.ShowInfor(tbField_to, tblName_to, strWhere_to);
                    if (DsCrmTo.Tables[0].Rows.Count > 0)
                    {
                        mCrm_AothurT.InnerHtml = "TO:" + DsCrmTo.Tables[0].Rows[0]["realname"].ToString() + " [" + DsCrmTo.Tables[0].Rows[0]["meno"].ToString() + "]";
                    }
                }
                else
                {
                    //获取该部门的员工列表
                    mCrm_state.InnerHtml = "任务未分配";
                    mCrm_Request.Visible = true;
                    mCrm_AothurT.Visible = false;
                    string RerbField = "users.id,users.realname,cate_adminuser.meno";
                    string RetblName = "crm,cate_adminuser,users";
                    string RestrWhere_User = "crm.to_meno=cate_adminuser.code and users.userid=cate_adminuser.code and users.chatflag=1 and users.chatclose=1 and crm.crm_id=" + CrmId;
                    DataSet DsReUsers = Common.ShowInfor(RerbField, RetblName, RestrWhere_User);
                    DDL_isRequest.DataTextField = "realname";
                    DDL_isRequest.DataValueField = "id";
                    mCrm_ToMeno.InnerHtml = "To:　[" + DsReUsers.Tables[0].Rows[0]["meno"].ToString() + "]";
                    DDL_isRequest.DataSource = DsReUsers.Tables[0].DefaultView;
                    DDL_isRequest.DataBind();
                } 

                mCrm_date.InnerText = "Date: " + DsCrm.Tables[0].Rows[0]["crm_date"].ToString();
                mCrm_Content.InnerHtml = DsCrm.Tables[0].Rows[0]["crm_content"].ToString();
                string AnnexPath = DsCrm.Tables[0].Rows[0]["crm_annex"].ToString();
                string[] AnnexName = AnnexPath.Split('/');
                if (AnnexName[0].Length > 0)
                {
                    string GetAnnexName = AnnexName[4].ToString();
                    mCrm_Annex.InnerHtml = "<img width=\"14px\" height=\"14px\" border=\"0\" src=\"images/math.gif\" />附件：";
                    mCrm_Annex.InnerHtml = mCrm_Annex.InnerHtml + "<a href=\"" + AnnexPath + "\" title=\"点击下载\" target=\"_blank\">" + GetAnnexName + "</a>";
                }
                else
                {
                    mCrm_Annex.InnerHtml = "没有附件";
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
                    if (Reply_User == Staff_id)
                    {
                        ReplyColor = "green";
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
                GetReply.InnerHtml = "<strong>任务<font color=\"blue\">[" + mCrm_Title.InnerText + "]</font>没有回复和反馈信息</strong>";
                return;
            }
        }
        catch (Exception ReplyErr)
        {
            Response.Write(ReplyErr.Message);
            return;
        }
    }

    protected void But_Search_Click(object sender, EventArgs e)
    {
        Response.Redirect("CrmManage.aspx?gStaff=" + DDL_UserId.SelectedValue + "&gMeno=" + DDL_Meno.SelectedValue + "&crmState=" + RBL_Staff.SelectedValue);
    }

    protected void But_isRequest_Click(object sender, EventArgs e)
    {
        string getMenoField = "crm_id";
        string getMenoTable = "crm";
        string getMenoWhere = "to_meno=" + Meno_id + " and crm_id=" + Hid_CrmId.Value;
        DataSet DsGetMeno = Common.ShowInfor(getMenoField, getMenoTable, getMenoWhere);
        //权限判断，所分配的任务是否是本部门的任务。其他部门的任务无法分配
        if (DsGetMeno.Tables[0].Rows.Count > 0)
        {
            string isRequest = DDL_isRequest.SelectedValue;
            string Crm_Id = Hid_CrmId.Value;
            int setIsRe = (int)ClassCRM.CC_Update_CRM_isRequest(Crm_Id,isRequest,true);
            if (setIsRe > 0)
            {
                //完成任务分配,重新绑定任务列表
                PostUrl();
                GetCrmList();
                CrmInfor(Crm_Id);
                mCrm_state.InnerHtml = "任务分配成功，请通知相关员工接收任务";
            }
            else
            {
                //分配失败
                mCrm_state.InnerHtml = "分配失败任务已删除或者没有该员工,请核实";
                return;
            }
        }
        else
        {
            mCrm_state.InnerHtml = "对不起,你无权分配其他部门的任务!";
            return;
        }
    }

    protected void DDL_Meno_SelectedIndexChanged(object sender, EventArgs e)
    {
        BindUserName();
    }

    void GetUrlSearch()
    {
        if (!object.Equals(Page.Request.QueryString["gStaff"], null) && Page.Request.QueryString["gStaff"]!="")
        {
            RBL_Staff.SelectedValue = Request.QueryString["crmState"].ToString();
            DDL_Meno.SelectedValue = Request.QueryString["gMeno"].ToString();
            BindUserName();
            DDL_UserId.SelectedValue = Request.QueryString["gStaff"].ToString();
            if (RBL_Staff.SelectedValue == "1")
            {
                Hid_strWhere.Value =Hid_strWhere.Value+ " and to_staff=" + Request.QueryString["gStaff"].ToString() + " and to_meno=" + Request.QueryString["gMeno"].ToString();
            }
            else
            {
                Hid_strWhere.Value =Hid_strWhere.Value+ " and from_staff=" + Request.QueryString["gStaff"].ToString() + " and from_meno=" + Request.QueryString["gMeno"].ToString();
            }
        }
        else
        {
            BindUserName();
        }
    }

    //获取url地址中的参数
    void PostUrl()
    {
        string getUrl = Page.Request.Url.ToString();
        string[] getName = getUrl.Split('?');//判断url中是否带有参数
        if (getName[0].Length < getUrl.Length)
        {
            //url中已经有参数了
            string[] getNid = Regex.Split(getUrl, "MyCrmId=", RegexOptions.IgnoreCase);
            if (getNid[0].Length < getUrl.Length)//url中是否带有nid参数
            {
                //url中已经有nid参数
                string[] getPage = getNid[1].Split('=');//截取nid后面的字符
                //判断url中nid是不是最后一个参数
                if (getPage[0].Length < getNid[1].Length)
                {
                    //截取nid后面的参数
                    string[] addpage = getNid[1].Split('&');
                    //截取nid后面的参数放到nid之前，防止后面的参数丢失
                    postUrl = getNid[0].ToString() + addpage[1].ToString() + "&MyCrmId=";
                }
                else
                {
                    //nid之后没有其他参数
                    postUrl = getNid[0].ToString() + "MyCrmId=";//用getNid[0].ToString()是为了防止url中出现多个nid参数
                }
            }
            else
            {
                //url后面的参数中没有nid参数，添加nid参数
                postUrl = getUrl + "&MyCrmId=";
            }
        }
        else
        {
            //url后面没有带参数
            postUrl = getUrl + "?MyCrmId=";
        }
    }

}
