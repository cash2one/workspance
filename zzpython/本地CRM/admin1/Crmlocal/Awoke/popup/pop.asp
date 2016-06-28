<html>
 <head>
  <title>HTMLPage1</title>
  <meta name="vs_defaultClientScript" content="JavaScript">
  <meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
  <meta name="GENERATOR" content="Microsoft Visual Studio .NET 7.1">
  <meta name="ProgId" content="VisualStudio.HTML">
  <meta name="Originator" content="Microsoft Visual Studio .NET 7.1">
 </head>
 
 <script language =javascript>
   var titlePopup
   var len;
   
   function InitMsgBox()
   { len = 0;
    titlePopup=window.createPopup();
    var titlePopupBody = titlePopup.document.body;
    titlePopupBody.style.border ="solid black 1px";
    var titleContent = "";
    titleContent = titleContent + "<table cellPadding='5' bgcolor='#65c1ff' width='100%' height='100%' border=0 cellspacing=0 cellpadding=0>";
    titleContent = titleContent + "<tr><td align=center><font size = 2>消息提醒</font></td></tr>";
    titleContent = titleContent + "<tr><td><font size = 2>内容</td></font></tr>";
    titleContent = titleContent + "<tr><td><font size = 2>内容</td></font></tr>";
    titleContent = titleContent + "<tr><td><font size = 2>日期</td></font></tr>";
    titleContent = titleContent + "</table>";    
    titlePopupBody.innerHTML = titleContent;
         
    ShowMsgBox();
   }
      
   function MsgBox()
   { 
    len += 4;      
    if (len > 110)
    {   
     window.clearInterval(tID);     
    }        
    else
    {//170固定了消息提示框的宽度
     titlePopup.show(document.body.clientWidth - 170, document.body.clientHeight - len, 170, len, top.document.body); 
    }    
   }
   
   var tID
   function ShowMsgBox()
   {
    tID = window.setInterval("MsgBox()",15);    
   }
 </script>
 
 <body MS_POSITIONING="GridLayout">
  <INPUT onclick="InitMsgBox()" id="Button1" style="Z-INDEX: 101; LEFT: 296px; POSITION: absolute; TOP: 344px" type="button"
   value="Button" name="Button1">
 </body>
</html>

