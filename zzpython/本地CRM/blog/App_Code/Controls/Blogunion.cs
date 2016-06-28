#region Using

using System;
using System.IO;
using System.Web;
using System.Net;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Xml;
using System.Web.UI;
using System.Web.UI.HtmlControls;

#endregion

using BlogEngine.Core;
using System.Globalization;

namespace Controls
{
    /// <summary>
    /// Creates and displays a dynamic blogunion.
    /// </summary>
    public class Blogunion : Control
    {
        static Blogunion()
        {
            BlogSettings.Changed += new EventHandler<EventArgs>(BlogSettings_Changed);
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (!Page.IsPostBack && !Page.IsCallback)
            {
                HtmlGenericControl ul = DisplayBlogroll();
                StringWriter sw = new StringWriter();
                ul.RenderControl(new HtmlTextWriter(sw));
                string html = sw.ToString();

                writer.WriteLine("<div id=\"blogroll\">");
                writer.WriteLine(html);
                writer.WriteLine("</div>");
            }
        }

        private static void BlogSettings_Changed(object sender, EventArgs e)
        {
            _Items = null;
        }

        #region Private fields

        private static Collection<RssItem> _Items;
        private static DateTime _LastUpdated = DateTime.Now;

        #endregion

        #region Methods

        public static void Update()
        {
            _Items = null;
        }

        private static object _SyncRoot = new object();

        /// <summary>
        /// Displays the RSS item collection.
        /// </summary>
        private HtmlGenericControl DisplayBlogroll()
        {
            if (DateTime.Now > _LastUpdated.AddMinutes(BlogSettings.Instance.BlogrollUpdateMinutes) && BlogSettings.Instance.BlogrollVisiblePosts > 0)
            {
                _Items = null;
                _LastUpdated = DateTime.Now;
            }

            if (_Items == null)
            {
                lock (_SyncRoot)
                {
                    if (_Items == null)
                    {
                        _Items = new Collection<RssItem>();
                        CreateList();
                    }
                }
            }

            return BindControls();
        }

        /// <summary>
        /// Adds the feeds to the blogroll.
        /// </summary>
        private void CreateList()
        {
            string fileName = Context.Server.MapPath(BlogSettings.Instance.StorageLocation) + "blogunion.xml";
            if (File.Exists(fileName))
            {
                XmlDocument doc = new XmlDocument();
                doc.Load(fileName);

                foreach (XmlNode node in doc.SelectNodes("opml/body/outline"))
                {
                    string title = node.Attributes["title"].InnerText;
                    string description = node.Attributes["description"].InnerText;
                    string rss = node.Attributes["xmlUrl"].InnerText;
                    string website = node.Attributes["htmlUrl"].InnerText;
                    string xfn = null;
                    if (node.Attributes["xfn"] != null)
                        xfn = node.Attributes["xfn"].InnerText.Replace(";", string.Empty);

                    AddBlog(title, description, rss, website, xfn);
                }
            }
        }

        /// <summary>
        /// Parses the processed RSS items and returns the HTML
        /// </summary>
        private HtmlGenericControl BindControls()
        {
            HtmlGenericControl ul = new HtmlGenericControl("ul");
            ul.Attributes.Add("class", "oxox");
            ul.Attributes.Add("style", "margin:0px;padding:0px;");//by Spoony
            foreach (RssItem item in _Items)
            {
                HtmlAnchor feedAnchor = new HtmlAnchor();
                feedAnchor.HRef = item.RssUrl;
                feedAnchor.Attributes.Add("target", "_blank");//by Spoony

                HtmlImage image = new HtmlImage();
                image.Src = Utils.RelativeWebRoot + "pics/rssButton.gif";
                image.Alt = "RSS feed for " + item.Name;
                image.Align = "middle";

                feedAnchor.Controls.Add(image);

                HtmlAnchor webAnchor = new HtmlAnchor();
                webAnchor.HRef = item.WebsiteUrl;
                webAnchor.Attributes.Add("target", "_blank");//by Spoony
                webAnchor.InnerHtml = EnsureLength(item.Name);

                if (!String.IsNullOrEmpty(item.Xfn))
                    webAnchor.Attributes["rel"] = item.Xfn;

                HtmlGenericControl li = new HtmlGenericControl("li");
                li.Controls.Add(feedAnchor);
                li.Controls.Add(webAnchor);
                li.Attributes.Add("style", "list-style-type:none;margin:0px;padding:0px;");//by Spoony

                AddRssChildItems(item, li);
                ul.Controls.Add(li);
            }

            return ul;
        }

        private void AddRssChildItems(RssItem item, HtmlGenericControl li)
        {
            if (item.Items.Count > 0 && BlogSettings.Instance.BlogrollVisiblePosts > 0)
            {
                HtmlGenericControl div = new HtmlGenericControl("ul");
                int i = 0;
                foreach (string key in item.Items.Keys)
                {
                    if (i >= BlogSettings.Instance.BlogrollVisiblePosts) break;

                    HtmlGenericControl subLi = new HtmlGenericControl("li");
                    HtmlAnchor a = new HtmlAnchor();
                    a.HRef = item.Items[key];
                    a.Attributes.Add("target", "_blank");//by Spoony
                    a.Title = HttpUtility.HtmlEncode(key);
                    a.InnerHtml = EnsureLength(key);

                    subLi.Controls.Add(a);
                    div.Controls.Add(subLi);
                    i++;
                }

                li.Controls.Add(div);
            }
        }

        /// <summary>
        /// Ensures that the name is no longer than the MaxLength.
        /// </summary>
        private string EnsureLength(string textToShorten)
        {
            //if (textToShorten.Length > BlogSettings.Instance.BlogrollMaxLength)
            //  return textToShorten.Substring(0, BlogSettings.Instance.BlogrollMaxLength).Trim() + "...";

            return HttpUtility.HtmlEncode(textToShorten);
        }

        public string DateStringFromNow(DateTime dt)
        {
            TimeSpan span = DateTime.Now - dt;
            if (span.TotalDays > 60)
            {
                return dt.ToShortDateString();
            }
            else
                if (span.TotalDays > 30)
                {
                    return
                    "1个月前";
                }
                else
                    if (span.TotalDays > 14)
                    {
                        return
                        "2周前";
                    }
                    else
                        if (span.TotalDays > 7)
                        {
                            return
                            "1周前";
                        }
                        else
                            if (span.TotalDays > 1)
                            {
                                return
                                string.Format("{0}天前", (int)Math.Floor(span.TotalDays));
                            }
                            else
                                if (span.TotalHours > 1)
                                {
                                    return
                                    string.Format("{0}小时前", (int)Math.Floor(span.TotalHours));
                                }
                                else
                                    if (span.TotalMinutes > 1)
                                    {
                                        return
                                        string.Format("{0}分钟前", (int)Math.Floor(span.TotalMinutes));
                                    }
                                    else
                                        if (span.TotalSeconds >= 1)
                                        {
                                            return
                                            string.Format("{0}秒前", (int)Math.Floor(span.TotalSeconds));
                                        }
                                        else
                                        {
                                            return
                                            "1秒前";
                                        }
        }


        /// <summary>
        /// Adds a blog to the item collection and start retrieving the blogs.
        /// </summary>
        private static void AddBlog(string name, string description, string feedUrl, string website, string xfn)
        {
            RssItem item = new RssItem();
            item.RssUrl = feedUrl;
            item.WebsiteUrl = website;
            item.Name = name;
            item.Description = description;
            item.Xfn = xfn;

            item.Request = (HttpWebRequest)WebRequest.Create(feedUrl);
            item.Request.Credentials = CredentialCache.DefaultNetworkCredentials;

            _Items.Add(item);

            item.Request.BeginGetResponse(ProcessRespose, item);
        }

        /// <summary>
        /// Gets the request and processes the response.
        /// </summary>
        private static void ProcessRespose(IAsyncResult async)
        {
            RssItem item = (RssItem)async.AsyncState;
            try
            {
                using (HttpWebResponse response = (HttpWebResponse)item.Request.EndGetResponse(async))
                {
                    XmlDocument doc = new XmlDocument();
                    doc.Load(response.GetResponseStream());

                    XmlNodeList nodes = doc.SelectNodes("rss/channel/item");
                    foreach (XmlNode node in nodes)
                    {
                        string title = node.SelectSingleNode("title").InnerText;
                        string link = node.SelectSingleNode("link").InnerText;
                        DateTime date = DateTime.Now;
                        if (node.SelectSingleNode("pubDate") != null)
                            date = DateTime.Parse(node.SelectSingleNode("pubDate").InnerText);

                        //item.Items.Add(title, link);
                        item.Items.Add(title + " [" + DateTimeHelper.GetManReadable(date) + "]", link);//by Spoony
                    }
                }
            }
            catch
            { }
        }

        #endregion

        #region RssItem class

        /// <summary>
        /// The RSS items used to display on the blogroll.
        /// </summary>
        private class RssItem
        {
            public HttpWebRequest Request;
            public string RssUrl;
            public string WebsiteUrl;
            public string Name;
            public string Description;
            public string Xfn;
            public Dictionary<string, string> Items = new Dictionary<string, string>();
        }

        #endregion

    }
}

public static class BlogunionUpdater
{
    public static void UpdateBlogunion()
    {
        Controls.Blogunion.Update();
    }
}

public class DateTimeHelper
{
    #region 返回友好时间显示

    #region 带时间

    public static string GetManReadable(string datetime)
    {
        try
        {
            return GetManReadable(Convert.ToDateTime(datetime));
        }
        catch
        {
            return datetime;
        }
    }

    public static string GetManReadable(object datetime)
    {
        try
        {
            return GetManReadable(Convert.ToDateTime(datetime));
        }
        catch
        {
            return datetime.ToString();
        }
    }

    public static string GetManReadable(DateTime datetime)
    {
        string time = datetime.ToShortTimeString();
        return GetShortManReadable(datetime) + " " + time;
    }
    #endregion

    #region 不带时间

    public static string GetShortManReadable(string datetime)
    {
        if (string.IsNullOrEmpty(datetime.Trim()))
            return string.Empty;
        try
        {
            return GetShortManReadable(Convert.ToDateTime(datetime));
        }
        catch
        {
            return datetime;
        }
    }

    public static string GetShortManReadable(object datetime)
    {
        if (datetime == null || datetime.ToString().Trim() == string.Empty)
            return string.Empty;
        try
        {
            return GetShortManReadable(Convert.ToDateTime(datetime));
        }
        catch
        {
            return datetime.ToString();
        }
    }

    public static string GetShortManReadable(DateTime datetime)
    {
        DateTime now = DateTime.Now;
        if (now.Year == datetime.Year)//以下的前提是两时间都为同一年
        {
            TimeSpan span = now.Date - datetime.Date;
            int days = span.Days;
            switch (days)
            {
                case 1:
                    return "昨天";
                case 0:
                    return "今天";
                case -1:
                    return "明天";
                default:
                    break;
            }

            if (days >= -14 || days <= 14)
            {
                GregorianCalendar gc = new GregorianCalendar();
                int dateWeekofYear = gc.GetWeekOfYear(datetime, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
                int nowWeekofYear = gc.GetWeekOfYear(now, CalendarWeekRule.FirstDay, DayOfWeek.Monday);
                string dateDayofWeek = gc.GetDayOfWeek(datetime).ToString();
                int weeks = nowWeekofYear - dateWeekofYear;
                switch (weeks)
                {
                    case 1:
                        return string.Format("上周{0}", WhichDay(dateDayofWeek));
                    case 0:
                        return string.Format("本周{0}", WhichDay(dateDayofWeek));
                    case -1:
                        return string.Format("下周{0}", WhichDay(dateDayofWeek));
                    default:
                        break;
                }
            }

            if (days >= -62 || days <= 62)
            {
                int months = now.Month - datetime.Month;
                int dayofMonth = datetime.Day;
                switch (months)
                {
                    case 1:
                        return string.Format("上月{0}号", dayofMonth);
                    case 0:
                        return string.Format("本月{0}号", dayofMonth);
                    case -1:
                        return string.Format("下月{0}号", dayofMonth);
                    default:
                        break;
                }
            }

        }
        else//以下的前提是两时间不同年
        {

        }

        return datetime.ToShortDateString();
    }
    #endregion

    public static string WhichDay(string enWeek)
    {
        switch (enWeek.Trim())
        {
            case "Sunday":
                return "日";
            case "Monday":
                return "一";
            case "Tuesday":
                return "二";
            case "Wednesday":
                return "三";
            case "Thursday":
                return "四";
            case "Friday":
                return "五";
            case "Saturday":
                return "六";
            default:
                return enWeek;
        }
    }

    #endregion

    #region 生日提醒

    public static string GetBirthdayTip(DateTime birthday)
    {
        DateTime now = DateTime.Now;
        //TimeSpan span = DateTime.Now - birthday;
        int nowMonth = now.Month;
        int birtMonth = birthday.Month;
        if (nowMonth == 12 && birtMonth == 1)
            return string.Format("下月{0}号", birthday.Day);
        if (nowMonth == 1 && birtMonth == 12)
            return string.Format("上月{0}号", birthday.Day);
        int months = now.Month - birthday.Month;
        //int days = now.Day - birthday.Day;
        if (months == 1)
            return string.Format("上月{0}号", birthday.Day);
        else if (months == -1)
            return string.Format("下月{0}号", birthday.Day);
        else if (months == 0)
        {
            if (now.Day == birthday.Day)
                return "今天";
            return string.Format("本月{0}号", birthday.Day);
        }
        else
            return birthday.ToShortDateString();
    }
    public static string GetBirthdayTip(string birthday)
    {
        try
        {
            return GetBirthdayTip(Convert.ToDateTime(birthday));
        }
        catch
        {
            return birthday;
        }
    }

    #endregion

    #region 其他日期相关

    /// <summary>
    /// 返回日期加短时间格式
    /// </summary>
    public static string GetDateShortTime(DateTime dt)
    {
        return dt.ToString("yyyy-MM-dd hh:mm");
    }
    public static string GetDateShortTime(object o1)
    {
        try
        {
            return GetDateShortTime(Convert.ToDateTime(o1));

        }
        catch
        {
            return o1.ToString();
        }
    }

    /// <summary>
    /// 返回短日期
    /// </summary>
    public static string GetShortDate(DateTime dt)
    {
        return dt.ToString("yyyy-MM-dd");
    }
    public static string GetShortDate(object o1)
    {
        try
        {
            return GetShortDate(Convert.ToDateTime(o1));

        }
        catch
        {
            return o1.ToString();
        }
    }

    /// <summary>
    /// 获取下个月是几月
    /// </summary>
    public static int GetPreviousMonth(DateTime date)
    {
        return date.AddMonths(-1).Month;
    }

    /// <summary>
    /// 获取当月是几月
    /// </summary>
    public static int GetThisMonth(DateTime date)
    {
        return date.Month;
    }

    /// <summary>
    /// 获取下个月是几月
    /// </summary>
    public static int GetNextMonth(DateTime date)
    {
        return date.AddMonths(1).Month;
    }

    /// <summary>
    /// 获取前或后几个月是几月
    /// </summary>
    public static int GetMonth(int i, DateTime date, out int year)
    {
        DateTime time = date.AddMonths(i);
        year = time.Year;
        return time.Month;
    }
    public static string GetWeek()
    {
        return string.Empty;
    }

    #endregion

}
