using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;

public partial class admin_fckeditor : System.Web.UI.UserControl
{
    public string Text
    {
        get { return FCKeditor.Value; }
        set { FCKeditor.Value = value; }
    }

    public short TabIndex
    {
        get { return 999; }
        set { ;}
    }

    private string _Type = string.Empty;
    public string Type
    {
        get { return _Type; }
        set
        {
            if (string.IsNullOrEmpty(value))
            {
                _Type = string.Empty;
            }
            else
            {
                _Type = value;
            }
            FCKeditor.ToolbarSet = _Type;
        }
    }

    private bool _Toolbar = false;
    public bool Toolbar
    {
        get { return _Toolbar; }
        set
        {
            _Toolbar = value;
            FCKeditor.ToolbarStartExpanded = _Toolbar;
        }
    }

    public Unit Width
    {
        get { return FCKeditor.Width; }
        set { FCKeditor.Width = value; }
    }

    public Unit Height
    {
        get { return FCKeditor.Height; }
        set { FCKeditor.Height = value; }
    }
}
