using System;
using System.Reflection;
using System.Configuration;
namespace rcu_en_news.DALFactory
{
	/// <summary>
    /// Abstract Factory pattern to create the DAL。
    /// 如果在这里创建对象报错，请检查web.config里是否修改了<add key="DAL" value="Maticsoft.SQLServerDAL" />。
	/// </summary>
    public sealed class DataAccess
    {
        private static readonly string path = ConfigurationManager.AppSettings["DAL"];
        public DataAccess()
        { }

        #region CreateObject

        //不使用缓存
        private static object CreateObjectNoCache(string path, string CacheKey)
        {
            try
            {
                object objType = Assembly.Load(path).CreateInstance(CacheKey);
                return objType;
            }
            catch//(System.Exception ex)
            {
                //string str=ex.Message;// 记录错误日志
                return null;
            }

        }
        //使用缓存
        private static object CreateObject(string path, string CacheKey)
        {
            object objType = DataCache.GetCache(CacheKey);
            if (objType == null)
            {
                try
                {
                    objType = Assembly.Load(path).CreateInstance(CacheKey);
                    DataCache.SetCache(CacheKey, objType);// 写入缓存
                }
                catch//(System.Exception ex)
                {
                    //string str=ex.Message;// 记录错误日志
                }
            }
            return objType;
        }
        #endregion

        #region CreateSysManage
        public static rcu_en_news.IDAL.ISysManage CreateSysManage()
        {
            //方式1			
            //return (rcu_en_news.IDAL.ISysManage)Assembly.Load(path).CreateInstance(path+".SysManage");

            //方式2 			
            string CacheKey = path + ".SysManage";
            object objType = CreateObject(path, CacheKey);
            return (rcu_en_news.IDAL.ISysManage)objType;
        }
        #endregion
    }
 
}