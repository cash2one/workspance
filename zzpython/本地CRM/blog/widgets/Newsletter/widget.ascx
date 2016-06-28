<%@ Control Language="C#" AutoEventWireup="true" CodeFile="widget.ascx.cs" Inherits="widgets_Newsletter_widget" %>

<div id="newsletterform">
  <p>当有新文章发表时通知您。</p>

  <label for="<%=txtEmail.ClientID %>" style="font-weight:bold">输入您的 e-mail 地址</label><br />
  <asp:TextBox runat="server" ID="txtEmail" Width="100%" />
  <asp:RequiredFieldValidator 
    runat="server" 
    ControlToValidate="txtEmail" 
    ErrorMessage="请输入一个 e-mail 地址" 
    Display="dynamic" 
    ValidationGroup="newsletter" />
    
  <asp:RegularExpressionValidator 
    runat="server" 
    ControlToValidate="txtEmail" 
    ErrorMessage="<%$Resources:labels, enterValidEmail %>" 
    ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*" 
    Display="dynamic" 
    ValidationGroup="newsletter" />
    
    <br />

  <div style="text-align:center">
    <asp:Button runat="server" ID="btnSave" ValidationGroup="newsletter" Text="通知我" OnClientClick="return beginAddEmail()" />
  </div>
</div>

<div id="newsletterthanks" style="display:none;text-align:center">
  <br /><br />
  <h2 id="newsletteraction">感谢</h2>
  <br /><br />
</div>

<script type="text/javascript">
  function beginAddEmail()
  {
    if(!Page_ClientValidate())
      return false;
      
    var arg = $('<%=txtEmail.ClientID %>').value;
    var context = 'newsletter';
    <%=Page.ClientScript.GetCallbackEventReference(this, "arg", "endAddEmail", "context") %>;
    
    return false;
  }
  
  function endAddEmail(arg, context)
  {
    $('newsletterform').style.display = 'none';
    $('newsletterthanks').style.display = 'block';
    if (arg == "false")
    {
      $('newsletteraction').innerHTML = "您现在没有订阅";//by Spoony
    }
  }
</script>