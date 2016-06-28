using System;
using System.ComponentModel;
using System.Web;
using System.Web.UI.WebControls;


public partial class admin_htmlEditor : System.Web.UI.UserControl
{

  public string Text
  {
    //get { return TinyMCE1.Text; }
    //set { TinyMCE1.Text = value; }
      get { return fckeditor.Text; 
      }
      set {fckeditor.Text = value; }
  }

  public short TabIndex
  {
    //get { return TinyMCE1.TabIndex; }
    //set { TinyMCE1.TabIndex = value; }
      get { return fckeditor.TabIndex; }
      set { fckeditor.TabIndex = value; }
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
          fckeditor.Type = _Type;
      }
  }

  private bool _Toolbar = false;
  public bool Toolbar
  {
      get { return _Toolbar; }
      set
      {
          _Toolbar=value;
          fckeditor.Toolbar = _Toolbar;
      }
  }

  public Unit Width
  {
      get { return fckeditor.Width; }
      set { fckeditor.Width = value; }
  }

  public Unit Height
  {
      get { return fckeditor.Height; }
      set { fckeditor.Height = value; }
  }


}
