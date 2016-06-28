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

public partial class CRM_CrmSending : System.Web.UI.Page
{
    protected string UserId;
    protected string UserName;
    protected string UserMeno;
    protected string UserQx;
    protected bool Requested=false;

    protected bool isRe = false;
    protected string Re_id="0";
    protected string reUserId;
    protected string reMenoId;
    protected string reCrmTitle;
    protected string reCrmContent;

    protected void Page_Init(object sender, EventArgs e)
    {
         if (!object.Equals(Session["PersonId"], null))
        {
            UserQx = Session["UserQx"].ToString();
            UserId = Session["PersonId"].ToString();
            UserName = Session["UserName"].ToString();
            UserMeno = Session["UserMeno"].ToString();
        }
        else
        {
            Response.Redirect("CrmLogOut.aspx");
        }
        if (!object.Equals(Request.QueryString["FbCrm_id"], null))
        {
            isRe = true;
            Re_id = Request.QueryString["FbCrm_id"].ToString();
            Crm_Submit.Text = "回 复";
            ShowReCall(Re_id);
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            if (!object.Equals(Request.QueryString["pCode"], null))
            {
                string tbWhere = "";
                tbWhere = "code=" + Request.QueryString["pCode"].ToString();
                ShowMeno(tbWhere);
                ShowStaff();
            }
        }
    }

    void ShowMeno(string tj)
    {
        string tbField="code,meno";
        string tblName="cate_adminuser";
        string strWhere="";
        if (tj != "")
        {
            strWhere = tj;
        }
        DataSet DsMeno=Common.ShowInfor(tbField,tblName,strWhere);
        if (DsMeno.Tables[0].Rows.Count > 0)
        {
            DDL_Meno.DataTextField = "meno";
            DDL_Meno.DataValueField = "code";
            DDL_Meno.DataSource = DsMeno.Tables[0].DefaultView;
            DDL_Meno.DataBind();
        }
        else
        {
            return;
        }
    }

    void ShowStaff()
    {
        string tbField = "id,name,realname";
        string tblName = "users";
        string strWhere = "chatflag=1 and chatclose=1 and userid=" + DDL_Meno.SelectedValue;

        if (UserQx == "0")
        {
            strWhere = strWhere;
        }
        else
        {
            if (DDL_Meno.SelectedValue == "10")
            {
                strWhere = strWhere;
            }
            else
            {
                if (UserQx == "1" && DDL_Meno.SelectedValue == UserMeno)
                {
                    strWhere = strWhere;
                }
                else
                {
                    strWhere = "userqx=1 and " + strWhere;
                }
            }
        }
        DataSet DsMeno = Common.ShowInfor(tbField, tblName, strWhere);
        if (DsMeno.Tables[0].Rows.Count > 0)
        {
            DDL_Staff.DataTextField = "realname";
            DDL_Staff.DataValueField = "id";
            DDL_Staff.DataSource = DsMeno.Tables[0].DefaultView;
            DDL_Staff.DataBind();
        }
        else
        {
            return;
        }
    }

    void ShowReCall(string Re_id)
    {
        string tbField = "meno,crm_content,users.realname,crm_title,crm_annex";
        string tblName = "users,cate_adminuser,crm";
        string strWhere = "crm.crm_id=" + Re_id;
        if (!object.Equals(Request.QueryString["backurl"], null) && Request.QueryString["backurl"] != "")
        {
            if (Request.QueryString["backurl"] == "CrmReceived.aspx")
            {
                tbField = "from_staff,from_meno," + tbField;
                strWhere = strWhere + " and cate_adminuser.code=crm.from_meno and users.id=crm.from_staff";
            }
            else
            {
                if (Request.QueryString["backurl"] == "CrmPosted.aspx")
                {
                    tbField = "to_staff,to_meno," + tbField;
                    strWhere = strWhere + " and cate_adminuser.code=crm.to_meno and users.id=crm.to_staff";
                }
            }
        }
        DataSet DsMeno = Common.ShowInfor(tbField, tblName, strWhere);
        if (DsMeno.Tables[0].Rows.Count > 0)
        {
            reUserId = DsMeno.Tables[0].Rows[0].ItemArray[0].ToString();
            reMenoId = DsMeno.Tables[0].Rows[0].ItemArray[1].ToString();
            reCrmTitle = DsMeno.Tables[0].Rows[0]["crm_title"].ToString();
            //reCrmContent = DsMeno.Tables[0].Rows[0]["crm_content"].ToString();
            string AnnexPath = DsMeno.Tables[0].Rows[0]["crm_annex"].ToString();
            string[] AnnexName = AnnexPath.Split('/');
            if (AnnexName[0].Length > 0)
            {
                string GetAnnexName = AnnexName[4].ToString();
                Div_Annex.InnerHtml = "<span>附件：</span><img width=\"14px\" height=\"14px\" border=\"0\" src=\"images/math.gif\" />";
                Div_Annex.InnerHtml = Div_Annex.InnerHtml + "<a href=\"" + AnnexPath + "\" title=\"点击下载\" target=\"_blank\">" + GetAnnexName + "</a>";
            }
            else
            {
                Div_Annex.InnerHtml = "<span>附件：</span>没有附件";
            }
            string reUserMeno = DsMeno.Tables[0].Rows[0]["meno"].ToString();
            string userTname = DsMeno.Tables[0].Rows[0]["realname"].ToString();

            DDL_Meno.Items.Add(new ListItem(reUserMeno, reMenoId));

            DDL_Staff.Items.Add(new ListItem(userTname, reUserId));

            Txt_CrmTitle.Text = reCrmTitle;
            Txt_CrmTitle.ReadOnly = true;
            //txt_Content.Value = "<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>";
            //txt_Content.Value = txt_Content.Value + "<span style=\"background-color:#F9FAFA; margin-left:10px; width:95%; color:#555; border:1 solid #eee; padding:10px; float:left\">";
            //txt_Content.Value = txt_Content.Value + "--------------原文信息--------------<div style=\"float:left; margin:5px; color:#555;\">";
            //txt_Content.Value = txt_Content.Value + reCrmContent + "</div></span>";
        }
        else
        {
            return;
        }

    }

    protected void DDL_Meno_SelectedIndexChanged(object sender, EventArgs e)
    {
        string tbwhere = "userid=" + DDL_Meno.SelectedValue;
        ShowStaff();
    }

    protected void Crm_Submit_Click(object sender, EventArgs e)
    {
        bool subCrm = true;
        if (txt_Content.Value == "" || Txt_CrmTitle.Text.Trim() == "")
        {
            subCrm = false;
            div_error.InnerHtml = "<strong><font color=red>请输入标题和内容</font></strong>";
            return;
        }
        if (subCrm)
        {
            string To_Meno = DDL_Meno.SelectedValue;
            string To_Staff = DDL_Staff.SelectedValue;
            string Crm_Title = Common.ReplaceSQL(Txt_CrmTitle.Text);
            string Crm_Annex = "";
            if (FileUpload.HasFile)
            {
                string name = FileUpload.PostedFile.FileName;                  // 客户端文件路径
                FileInfo file = new FileInfo(name);
                string FileName = file.Name;                                   //获取附件名称
                string SavePath = "FileUpload/" + UserId + "/" + DateTime.Now.Date.ToShortDateString() + "/" + DateTime.Now.ToString("HHmmss") + "/";
                Crm_Annex = SavePath + FileName;
                try
                {
                    if (!Directory.Exists(Server.MapPath(SavePath)))
                    {
                        Directory.CreateDirectory(Server.MapPath(SavePath));
                    }
                    FileUpload.SaveAs(Server.MapPath(SavePath + FileName));
                }
                catch (Exception uperr)
                {
                    Response.Write("附件上传失败!" + uperr.Message);
                }
            }
            else
            {
                Crm_Annex = "";
            }

            string Crm_Content = txt_Content.Value;
            string From_Meno = UserMeno;
            string From_Staff = UserId;
            if (isRe)
            {
                try
                {
                    int isReply = (int)ClassCRM.Add_CRM_Reply(Re_id, Crm_Title, Crm_Content, From_Staff);
                    if (isReply > 0)
                    {
                        if (!object.Equals(Request.QueryString["backurl"], null))
                        {
                            string back = Request.QueryString["backurl"].ToString();
                            Response.Redirect(back + "?MyCrmId=" + Re_id + "&#Re");
                        }
                        else
                        {
                            Response.Redirect("CrmPosted.aspx?pUserId=" + From_Staff);
                        }
                    }
                }
                catch (Exception ReplyErr)
                {
                    Response.Write(ReplyErr.Message);
                    return;
                }
            }
            else
            {
                try
                {
                    int NumCrm = (int)ClassCRM.Add_CRM(To_Meno, To_Staff, Crm_Title, Crm_Annex, Crm_Content, From_Meno, From_Staff);
                    if (NumCrm > 0)
                    {
                        if (UserQx == "1" && DDL_Meno.SelectedValue == UserMeno||UserQx == "0")
                        {
                            Requested = true;
                            int setRequest = (int)ClassCRM.CC_Update_CRM_isRequest(NumCrm.ToString(), To_Staff, Requested);
                        }
                        Response.Redirect("CrmPosted.aspx?pUserId=" + From_Staff);
                    }
                    else
                    {
                        div_error.InnerHtml = "<strong><font color=red>您所要通知的部门已经收到同样标题的方案了。请修改标题后重新发送</font></strong>";
                        return;
                    }
                }
                catch (Exception CrmErr)
                {
                    Response.Write(CrmErr.Message);
                    return;
                }
            }
        }
    }
}
