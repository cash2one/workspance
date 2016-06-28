using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Text;
using System.Data.SqlClient;
using System.IO;
using Maticsoft.DBUtility;//请先添加引用

/// <summary>
/// Common 的摘要说明
/// </summary>
public class Common
{
    public Common()
    {
        //
        // TODO: 在此处添加构造函数逻辑
        //
    }
    public static string ReplaceText(string strInput)
    {
        if (!String.IsNullOrEmpty(strInput))
        {
            StringBuilder temp=new StringBuilder(strInput);
            temp.Replace("&lt;", "<");
            temp.Replace("&gt;", ">");
            temp.Replace("<br>", ((char)13).ToString());
            temp.Replace("''", "'");
            temp.Replace("&nbsp;", " ");
            return temp.ToString();
        }
        else
        {
            return "";

        }
    }
    public static string JSAlertAndLocation(string strAlert, string strUrl)
    {
        string s = "<script type=\"text/javascript\">alert('" + strAlert + "');location.href='" + strUrl + "'</script>";
        return s;
    }
    /// <summary>
    /// 分页获取数据列表
    /// </summary>
    public static DataSet GetList(string tableName, string selectField, string keyField, string orderField, string orderType, string strWhere, int startIndex, int endIndex, bool docount)
    {
        SqlParameter[] parameters = {
                        new SqlParameter("@tableName",SqlDbType.VarChar,1000),
                        new SqlParameter("@selectField",SqlDbType.VarChar,1000),
                        new SqlParameter("@keyField",SqlDbType.VarChar,1000),
                        new SqlParameter("@orderField",SqlDbType.VarChar,1000),
                        new SqlParameter("@orderType",SqlDbType.VarChar,1000),
                        new SqlParameter("@strWhere", SqlDbType.VarChar, 255),
                        new SqlParameter("@startIndex", SqlDbType.Int),
                        new SqlParameter("@endIndex", SqlDbType.Int),
                        new SqlParameter("@docount", SqlDbType.Bit)
                    };
        parameters[0].Value = tableName;
        parameters[1].Value = selectField;
        parameters[2].Value = keyField;
        parameters[3].Value = orderField;
        parameters[4].Value = orderType;
        parameters[5].Value = strWhere;
        parameters[6].Value = startIndex;
        parameters[7].Value = endIndex;
        parameters[8].Value = docount;
        return DbHelperSQL.RunProcedure("RC_Common_PageList", parameters, "ds");
    }
    /// <summary>
    /// 截取字符串
    /// </summary>
    /// <param name="input"></param>
    /// <param name="length"></param>
    /// <returns></returns>
    public static string LenControl(string input, int length)
    {
        length = length * 2;
        input = RemoveHTML(input);
        string delsqace = input.Trim();
        int i = 0, j = 0;
        foreach (char chr in delsqace)
        {
            if ((int)chr > 127)
            {
                i += 2;
            }
            else
            {
                i++;
            }
            if (i > length)
            {
                delsqace = delsqace.Substring(0, j) + "...";
                break;
            }
            j++;
        }
        return delsqace;

    }
    /// <summary>
    /// 移除HTML标签
    /// </summary>
    /// <param name="HTMLStr"></param>
    /// <returns></returns>
    public static string RemoveHTML(string HTMLStr)
    {
        return System.Text.RegularExpressions.Regex.Replace(HTMLStr, "<[^>]*>", "");
    }

}
