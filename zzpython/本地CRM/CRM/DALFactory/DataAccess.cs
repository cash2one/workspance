using System;
using System.Reflection;
using System.Configuration;
namespace rcu_en_news.DALFactory
{
	/// <summary>
    /// Abstract Factory pattern to create the DAL��
    /// ��������ﴴ�����󱨴�����web.config���Ƿ��޸���<add key="DAL" value="Maticsoft.SQLServerDAL" />��
	/// </summary>
    public sealed class DataAccess
    {
        private static readonly string path = ConfigurationManager.AppSettings["DAL"];
        public DataAccess()
        { }

        #region CreateObject

        //��ʹ�û���
        private static object CreateObjectNoCache(string path, string CacheKey)
        {
            try
            {
                object objType = Assembly.Load(path).CreateInstance(CacheKey);
                return objType;
            }
            catch//(System.Exception ex)
            {
                //string str=ex.Message;// ��¼������־
                return null;
            }

        }
        //ʹ�û���
        private static object CreateObject(string path, string CacheKey)
        {
            object objType = DataCache.GetCache(CacheKey);
            if (objType == null)
            {
                try
                {
                    objType = Assembly.Load(path).CreateInstance(CacheKey);
                    DataCache.SetCache(CacheKey, objType);// д�뻺��
                }
                catch//(System.Exception ex)
                {
                    //string str=ex.Message;// ��¼������־
                }
            }
            return objType;
        }
        #endregion

        #region CreateSysManage
        public static rcu_en_news.IDAL.ISysManage CreateSysManage()
        {
            //��ʽ1			
            //return (rcu_en_news.IDAL.ISysManage)Assembly.Load(path).CreateInstance(path+".SysManage");

            //��ʽ2 			
            string CacheKey = path + ".SysManage";
            object objType = CreateObject(path, CacheKey);
            return (rcu_en_news.IDAL.ISysManage)objType;
        }
        #endregion
    }
 
}