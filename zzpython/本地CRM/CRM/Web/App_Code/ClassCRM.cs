using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Maticsoft.DBUtility;
using System.Data.SqlClient;

/// <summary>
/// ClassCRM 的摘要说明
/// </summary>
public class ClassCRM
{
    public ClassCRM()
    {
        //
        // TODO: 在此处添加构造函数逻辑
        //
    }

    public static string GetName = "";//用户登陆时获取的用户名，这里暂时借用
    public static string GetMeno = "";//用户登陆时获取的用户名，这里暂时借用
    public static string GetUserId = ""; //用户登陆时获取的用户名id，这里暂时借用依照对象是admin的id

    /// <summary>
    /// 添加部门工作交流方案
    /// </summary>
    /// <param name="To_Meno">接收者所在部门</param>
    /// <param name="To_Staff">接受者</param>
    /// <param name="Crm_Title">标题</param>
    /// <param name="Crm_Annex">附件</param>
    /// <param name="Crm_Content">内容</param>
    /// <param name="From_Meno">发送者所在部门</param>
    /// <param name="From_Staff">发送者</param>
    /// <returns>返回新添加的新闻的编号</returns>
    /// 
    public static int Add_CRM(string To_Meno, string To_Staff, string Crm_Title, string Crm_Annex, string Crm_Content, string From_Meno, string From_Staff)
    {
        SqlParameter[] parameters = {
                        new SqlParameter("@To_Meno",SqlDbType.Int),
                        new SqlParameter("@To_Staff",SqlDbType.Int),
                        new SqlParameter("@Crm_Title",SqlDbType.VarChar,255),
                        new SqlParameter("@Crm_Annex",SqlDbType.VarChar,255),
                        new SqlParameter("@Crm_Content",SqlDbType.NVarChar,4000),
                        new SqlParameter("@From_Meno",SqlDbType.Int),
                        new SqlParameter("@From_Staff",SqlDbType.Int),
                        new SqlParameter("@Crm_Id",SqlDbType.Int)
                       };
        parameters[0].Value = To_Meno;
        parameters[1].Value = To_Staff;
        parameters[2].Value = Crm_Title;
        parameters[3].Value = Crm_Annex;
        parameters[4].Value = Crm_Content;
        parameters[5].Value = From_Meno;
        parameters[6].Value = From_Staff;
        parameters[7].Direction = ParameterDirection.Output;
        DbHelperSQL.RunProcedure("CC_Add_CRM", parameters);
        return (int)parameters[7].Value;
    }

    /// <summary>
    /// 添加部门工作交流方案回复信息
    /// </summary>
    /// <param name="Crm_Id">回复的方案</param>
    /// <param name="Reply_Title">标题</param>
    /// <param name="Reply_Contents">内容</param>
    /// <param name="Reply_User">回复者</param>
    /// <returns>Reply_Id返回新添加的回复编号</returns>
    /// 
    public static int Add_CRM_Reply(string Crm_Id, string Reply_Title, string Reply_Contents, string Reply_User)
    {
        SqlParameter[] parameters = {
                        new SqlParameter("@Crm_Id",SqlDbType.Int),
                        new SqlParameter("@Reply_Title",SqlDbType.VarChar,255),
                        new SqlParameter("@Reply_Contents",SqlDbType.NVarChar,4000),
                        new SqlParameter("@Reply_User",SqlDbType.Int),
                        new SqlParameter("@Reply_Id",SqlDbType.Int)
        };
        parameters[0].Value = Crm_Id;
        parameters[1].Value = Reply_Title;
        parameters[2].Value = Reply_Contents;
        parameters[3].Value = Reply_User;
        parameters[4].Direction = ParameterDirection.Output;
        DbHelperSQL.RunProcedure("CC_Add_CRM_Reply", parameters);
        return (int)parameters[4].Value;
    }

    /// <summary>
    /// CRM任务分配
    /// </summary>
    /// <param name="Crm_Id">CRM编号</param>
    /// <param name="To_Staff">接受者</param>
    /// <returns>返回是否分配成功</returns>
    public static int CC_Update_CRM_isRequest(string Crm_Id, string To_Staff, bool IsRequest)
    {
        SqlParameter[] parameters = {
            new SqlParameter("@Crm_Id",SqlDbType.Int),
            new SqlParameter("@To_Staff",SqlDbType.Int),
            new SqlParameter("@isRequest",SqlDbType.Bit),
            new SqlParameter("@return",SqlDbType.Int)
                       };
        parameters[0].Value = Crm_Id;
        parameters[1].Value = To_Staff;
        parameters[2].Value = IsRequest;
        parameters[3].Direction = ParameterDirection.Output;
        DbHelperSQL.RunProcedure("CC_Update_CRM_isRequest", parameters);
        return (int)parameters[3].Value;
    }

    /// <summary>
    /// CRM任务验收
    /// </summary>
    /// <param name="Crm_Id">CRM编号</param>
    /// <returns>返回是否验收成功</returns>
    public static int CC_Update_CRM_State(string Crm_Id)
    {
        SqlParameter[] parameters = {
            new SqlParameter("@CrmId",SqlDbType.VarChar,5000),
            new SqlParameter("@return",SqlDbType.Int)
                       };
        parameters[0].Value = Crm_Id;
        parameters[1].Direction = ParameterDirection.Output;
        DbHelperSQL.RunProcedure("CC_Update_CRM_State", parameters);
        return (int)parameters[1].Value;
    }

    //获取随机密码
    public static string GetVailed(int i, bool t)
    {
        string VNum = "";
        Random Rnd = new Random();
        string Vchar;
        if (t)
        {
            Vchar = "0,1,2,3,4,5,6,7,8,9,a,s,d,f,g,h,j,k,l,q,w,e,r,t,y,u,i,o,p,z,x,c,v,b,n,m,Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Z,X,C,V,B,N,M";
        }
        else
        {
            Vchar = "0,1,2,3,4,5,6,7,8,9";
        }
        string[] Vc = Vchar.Split(',');
        int num = Vc.Length - 1;
        for (int k = 0; k < i; k++)
        {
            VNum = VNum + Vc[Rnd.Next(0, num)];
        }
        return VNum;
    }

    //用户密码修改
    public static int Update_Users_Pwd(string uId ,string OldPwd,string NewPwd, string realName)
    {
        SqlParameter[] parameters = {
            new SqlParameter("@id",SqlDbType.Int),
            new SqlParameter("@OldPwd",SqlDbType.VarChar,255),
            new SqlParameter("@NewPwd",SqlDbType.VarChar,255),
            new SqlParameter("@realname",SqlDbType.VarChar,255),
            new SqlParameter("@return",SqlDbType.Int)
                       };
        parameters[0].Value = uId;
        parameters[1].Value = OldPwd;
        parameters[2].Value = NewPwd;
        parameters[3].Value = realName;
        parameters[4].Direction = ParameterDirection.Output; 
        DbHelperSQL.RunProcedure("Update_Users_Pwd", parameters);
        return (int)parameters[4].Value;
    }
}
