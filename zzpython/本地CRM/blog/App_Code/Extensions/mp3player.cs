using System;
using BlogEngine.Core;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Text.RegularExpressions;
using BlogEngine.Core.Web.Controls;

/// <summary>
/// Add flash audio player(s) to the blog post
/// Developed by Ruslan Tur (http://rtur.net)
/// Version 1.3 for BlogEngine 1.3 
/// Visit http://rtur.net for updates
/// 12/20/2007
/// </summary>
[Extension("Mp3 Flash Audio Player", "1.3.0.0", "<a href=\"http://rtur.net/blog/page/MP3-Player.aspx\">Rtur.net</a>")]
public class mp3player
{
    #region Private members
    private static string _audioroot = "audio/";
    private static string _jsfile = "player.js";
    private static string _ext = "mp3player";
    private static string _width = "width";
    private static string _height = "height";
    private static string _bgColor = "bgColor";
    private static string _bg = "bg";
    private static string _leftbg = "leftbg";
    private static string _lefticon = "lefticon";
    private static string _rightbg = "rightbg";
    private static string _rightbghover = "rightbghover";
    private static string _righticon = "righticon";
    private static string _righticonhover = "righticonhover";
    private static string _text = "text";
    private static string _slider = "slider";
    private static string _track = "track";
    private static string _border = "border";
    private static string _loader = "loader";

    private static long _cnt = 0;
    //static protected ExtensionSettings _settings = null;
    #endregion

    /// <summary>
    /// Default constructor called on application start up
    /// from Global.asax to initialize extension
    /// </summary>
    static mp3player()
    {
        // subscribe for post serving event
        Post.Serving += new EventHandler<ServingEventArgs>(Post_Serving);

        // set page that extension manager will use  
        // instead of default settings page
        //ExtensionManager.SetAdminPage("mp3player", "~/audio/Admin.aspx");

        // set default setting values
        //SetDefaultSettings();
    }

    /// <summary>
    /// An event that handles ServingEventArgs
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private static void Post_Serving(object sender, ServingEventArgs e)
    {
        if (!string.IsNullOrEmpty(e.Body))
        {
            // only process the posts
            if (e.Location == ServingLocation.PostList || e.Location == ServingLocation.SinglePost)
            {
                string regex = @"\[mp3:.*?\.mp3]";
                MatchCollection matches = Regex.Matches(e.Body, regex);

                if (matches.Count > 0)
                {
                    AddJsToTheHeader();

                    string filename = string.Empty;
                    string filepath = string.Empty;
                    string player = string.Empty;

                    foreach (Match match in matches)
                    {
                        filename = match.Value.Replace("[mp3:", "").Replace("]", "").Trim();
                        player = PlayerObject(filename);
                        player = "<script type=\"text/javascript\">InsertPlayer(\"" + player + "\");</script>";
                        e.Body = e.Body.Replace(match.Value, player);
                    }
                }
            }
        }
    }

    /// <summary>
    /// Inject JavaScript file into the header of the post
    /// </summary>
    private static void AddJsToTheHeader()
    {
        // get a page handler
        System.Web.UI.Page pg = (System.Web.UI.Page)HttpContext.Current.CurrentHandler;
        bool added = false;

        // check if script already added to the page header
        foreach (Control ctl in pg.Header.Controls)
        {
            if (ctl.GetType() == typeof(HtmlGenericControl))
            {
                HtmlGenericControl gc = (HtmlGenericControl)ctl;
		        if (gc.Attributes["src"] != null)
		        {
   		            if (gc.Attributes["src"].Contains(_jsfile))
		            {
       		            added = true;
                    }
		        }
            }
        }

        if (!added)
        {
            HtmlGenericControl js = new HtmlGenericControl("script");
            js.Attributes.Add("type", "text/javascript");
            js.Attributes.Add("src", AudioRoot() + _jsfile);

            pg.Header.Controls.Add(js);
        }
    }

    /// <summary>
    /// Build object tag
    /// </summary>
    /// <param name="soundFile">Name of the mp3 file ("my song.mp3")</param>
    /// <returns>Flash object markup</returns>
    private static string PlayerObject(string soundFile)
    {
        string sFile = string.Empty;
        string[] sFiles = soundFile.Split(",".ToCharArray());

        foreach (string file in sFiles)
        {
            if (file.Substring(0, 7) == "http://")
            {
                sFile += file;
            }
            else
            {
                sFile += AudioRoot() + file;
            }
            sFile += ",";
        }

        sFile = sFile.Substring(0, sFile.Length - 1);
        sFile = HttpUtility.UrlEncode(sFile);

        string s = "<p>"
        + "<object type='application/x-shockwave-flash' data='{0}player.swf' id='audioplayer{1}' height='{18}' width='{17}'>"
        + "<param name='movie' value='{0}player.swf'>"
        + "<param name='FlashVars' value='playerID={1}&bg=0x{5}&leftbg=0x{6}&lefticon=0x{7}&rightbg=0x{8}&rightbghover=0x{9}&righticon=0x{10}&righticonhover=0x{11}&text=0x{12}&slider=0x{13}&track=0x{14}&border=0x{15}&loader=0x{16}&soundFile={2}'>"
        + "<param name='quality' value='high'>"
        + "<param name='menu' value='{3}'>"
        + "<param name='bgcolor' value='{4}'>"
        + "</object>"
        + "</p>";

        _cnt++;

        //return String.Format(s, AudioRoot(), _cnt, sFile, "No",
        //   _settings.GetSingleValue(_bgColor),
        //   _settings.GetSingleValue(_bg),
        //   _settings.GetSingleValue(_leftbg),
        //   _settings.GetSingleValue(_lefticon),
        //   _settings.GetSingleValue(_rightbg),
        //   _settings.GetSingleValue(_rightbghover),
        //   _settings.GetSingleValue(_righticon),
        //   _settings.GetSingleValue(_righticonhover),
        //   _settings.GetSingleValue(_text),
        //   _settings.GetSingleValue(_slider),
        //   _settings.GetSingleValue(_track),
        //   _settings.GetSingleValue(_border),
        //   _settings.GetSingleValue(_loader),
        //   _settings.GetSingleValue(_width),
        //   _settings.GetSingleValue(_height));

        return String.Format(s, AudioRoot(), _cnt, sFile, "No",
            "ffffff",
            "f8f8f8",
            "eeeeee",
            "666666",
            "cccccc",
            "999999",
            "666666",
            "ffffff",
            "666666",
            "666666",
            "ffffff",
            "666666",
            "9FFFB8",
            "290",
            "24");
    }

    ///// <summary>
    ///// Initializes settings with default values
    ///// </summary>
    //protected static void SetDefaultSettings()
    //{
    //    ExtensionSettings settings = new ExtensionSettings(_ext);

    //    settings.AddParameter(_width);
    //    settings.AddParameter(_height);
    //    settings.AddParameter(_bgColor);
    //    settings.AddParameter(_bg);
    //    settings.AddParameter(_leftbg);
    //    settings.AddParameter(_lefticon);
    //    settings.AddParameter(_rightbg);
    //    settings.AddParameter(_rightbghover);
    //    settings.AddParameter(_righticon);
    //    settings.AddParameter(_righticonhover);
    //    settings.AddParameter(_text);
    //    settings.AddParameter(_slider);
    //    settings.AddParameter(_track);
    //    settings.AddParameter(_border);
    //    settings.AddParameter(_loader);

    //    settings.AddValue(_width, "290");
    //    settings.AddValue(_height, "24");
    //    settings.AddValue(_bgColor, "ffffff");
    //    settings.AddValue(_bg, "f8f8f8");
    //    settings.AddValue(_leftbg, "eeeeee");
    //    settings.AddValue(_lefticon, "666666");
    //    settings.AddValue(_rightbg, "cccccc");
    //    settings.AddValue(_rightbghover, "999999");
    //    settings.AddValue(_righticon, "666666");
    //    settings.AddValue(_righticonhover, "ffffff");
    //    settings.AddValue(_text, "666666");
    //    settings.AddValue(_slider, "666666");
    //    settings.AddValue(_track, "ffffff");
    //    settings.AddValue(_border, "666666");
    //    settings.AddValue(_loader, "9FFFB8");

    //    settings.IsScalar = true;
    //    ExtensionManager.ImportSettings(settings);
    //    _settings = ExtensionManager.GetSettings(_ext);
    //}

    /// <summary>
    /// Virtual path to audio folder
    /// </summary>
    /// <returns>Path to the audio folder</returns>
    private static string AudioRoot()
    {
        string VirtualPath = HttpContext.Current.Request.Path;
        string audioRoot = VirtualPath.Substring(0, VirtualPath.LastIndexOf("/") + 1) + _audioroot;
        return audioRoot;
    }
}
