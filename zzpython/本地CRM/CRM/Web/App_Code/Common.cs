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
using Maticsoft.DBUtility;
using System.Text.RegularExpressions;


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
        string s = "<script>alert('" + strAlert + "');location.href='" + strUrl + "';</script>";
        return s;
    }
    public static string JsAlert(string strAlert)
    {
        string alert = "<script>alert('" + strAlert + "');</script>";
        return alert;
    }


    public static string ReplaceSQL(string SqlText)
    {
        string key;
        key = SqlText;
        key = key.Replace("'", "''");
        key = key.Replace("[", "/[");
        key = key.Replace("]", "/]");
        key = key.Replace("%", "/%");
        //key = key.Replace("&", "&amp;");
        key = key.Replace("_", "/_");
        key = key.Replace("(", "/(");
        key = key.Replace(")", "/)");
        key = key.Replace("，", ",");
        key = key.Replace("<", "&lt;");
        key = key.Replace(">", "&gt;");
        return key;
    }

    public static string deReplaceSQL(string SqlText)
    {
        string key;
        key = SqlText;
        key = key.Replace("''", "'");
        key = key.Replace("/[", "[");
        key = key.Replace("/]", "]");
        key = key.Replace("/%", "%");
        key = key.Replace("/&", "&");
        key = key.Replace("/_", "_");
        key = key.Replace("/(", "(");
        key = key.Replace("/)", ")");
        key = key.Replace("&lt;", "<");
        key = key.Replace("&gt;", ">");
        return key;
    }

    public static bool isNum(string str)
    {
        try
        {
            Int32.Parse(str);
            return true;

        }
        catch (Exception)
        {
            return false;
        }
    } 

    //email验证
    public static bool isEmail(string inputEmail)
    {
        string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
        Regex re = new Regex(strRegex);
        if (re.IsMatch(inputEmail))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    //货币验证
    //“ ^[0-9]{1,3}(,[0-9]{3})*(\.[0-9]{1,2})?$ ”验证格式:200,000.00 
    public static bool isMoney(string inputNum)
    {
        string strRegex = @"^[0-9]{1,3}(,[0-9]{3})*(\.[0-9]{1,2})?$";
        Regex re = new Regex(strRegex);
        if (re.IsMatch(inputNum))
        {
            return (true);
        }
        else
        {
            return (false);
        }
    }

    /// <summary>
    /// 分页获取数据列表
    /// </summary>
    public static DataSet PageList(string tableName, string selectField, string keyField, string orderField, string orderType, string strWhere, int startIndex, int endIndex, bool docount)
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
        return DbHelperSQL.RunProcedure("PageList", parameters, "ds");
    }

    public static DataSet GetPagedInfor(string tblName, string strGetFields, string fldName, int pageSize, int PageIndex, bool OrderType, string strWhere)
    {
        SqlParameter[] parameters = {
              new SqlParameter("@tblName",tblName),
              new SqlParameter("@strGetFields",strGetFields),
              new SqlParameter("@fldName",fldName),
              new SqlParameter("@pageSize",pageSize),
              new SqlParameter("@PageIndex",PageIndex),
              new SqlParameter("@OrderType",OrderType),
              new SqlParameter("@strWhere",strWhere)
        };
        return DbHelperSQL.RunProcedure("GetPagedInfor", parameters, "ds");
    }

    public static DataSet GetCountsInfor(string tblName,string strWhere)
    {
        int counts;
        SqlParameter[] parameters = {
            new SqlParameter("@tblName",SqlDbType.VarChar,255),
            new SqlParameter("@strWhere",SqlDbType.VarChar,255)
        };
        parameters[0].Value = tblName;
        parameters[1].Value = strWhere;
        return DbHelperSQL.RunProcedure("GetCountsInfor", parameters,"ds");
    }

    //查询数据。获取结果是单个文本的内容（数组）

    public static DataSet ShowInfor(string tbField, string tblName, string strWhere)
    {
        SqlParameter[] parameters = {
                        new SqlParameter("@tbField",SqlDbType.VarChar,255),
                        new SqlParameter("@tblName",SqlDbType.VarChar,255),
                        new SqlParameter("@strWhere",SqlDbType.VarChar,255)
                       };
        parameters[0].Value = tbField;
        parameters[1].Value = tblName;
        parameters[2].Value = strWhere;
        return DbHelperSQL.RunProcedure("ShowInfor", parameters, "ds");
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
