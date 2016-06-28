
SEP_PADDING = 5
HANDLE_PADDING = 7
var sHeader='<link rel="stylesheet" href="css/eintrdemo.css" type="text/css">'+
            '<meta http-equiv="Content-Language" content="zh-cn">'+
            '<meta http-equiv="Content-Type" content="text/html; charset=gb2312">'+
            '<body topmargin="0" leftmargin="0" onConTextMenu="event.returnValue=false;">';


function EditLoad(data) {
  if (data!=null) data=data.replace(/&amp;/ig,'&');
  else data='';
  bLoad=true;
  parent.Composition.document.designMode="On";
  idEdit = Composition
  idEdit.document.open()
  idEdit.document.write(sHeader+data)
  idEdit.document.close()
  idEdit.focus();
}

var yToolbars =	new Array();

var YInitialized = false;

function document.onreadystatechange()
{
  if (YInitialized) return;
  YInitialized = true;

  var i, s, curr;

  for (i=0; i<document.body.all.length;	i++)
  {
    curr=document.body.all[i];
    if (curr.className == "yToolbar")
    {
      InitTB(curr);
      yToolbars[yToolbars.length] = curr;
    }
  }

  DoLayout();
  window.onresize = DoLayout;

  Composition.document.open()
  Composition.document.write("<head><style type=\"text/css\">body {font-size: 10.8pt}</style><meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"></head><BODY bgcolor=\"#FFFFFF\" MONOSPACE></body>");
  Composition.document.close()
  Composition.document.designMode="On"
}

function InitBtn(btn)
{
  btn.onmouseover = BtnMouseOver;
  btn.onmouseout = BtnMouseOut;
  btn.onmousedown = BtnMouseDown;
  btn.onmouseup	= BtnMouseUp;
  btn.ondragstart = YCancelEvent;
  btn.onselectstart = YCancelEvent;
  btn.onselect = YCancelEvent;
  btn.YUSERONCLICK = btn.onclick;
  btn.onclick =	YCancelEvent;
  btn.YINITIALIZED = true;
  return true;
}

function InitTB(y)
{
  y.TBWidth = 0;

  if (!	PopulateTB(y)) return false;

  y.style.posWidth = y.TBWidth;

  return true;
}


function YCancelEvent()
{
  event.returnValue=false;
  event.cancelBubble=true;
  return false;
}

function BtnMouseOver()
{
  if (event.srcElement.tagName != "IMG") return	false;
  var image = event.srcElement;
  var element =	image.parentElement;

  if (image.className == "Ico")	element.className = "BtnMouseOverUp";
  else if (image.className == "IcoDown") element.className = "BtnMouseOverDown";

  event.cancelBubble = true;
}

function BtnMouseOut()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;
  yRaisedElement = null;

  element.className = "Btn";
  image.className = "Ico";

  event.cancelBubble = true;
}

function BtnMouseDown()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    event.returnValue=false;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;

  element.className = "BtnMouseOverDown";
  image.className = "IcoDown";

  event.cancelBubble = true;
  event.returnValue=false;
  return false;
}

function BtnMouseUp()
{
  if (event.srcElement.tagName != "IMG") {
    event.cancelBubble = true;
    return false;
  }

  var image = event.srcElement;
  var element =	image.parentElement;

  if (element.YUSERONCLICK) eval(element.YUSERONCLICK +	"anonymous()");

  element.className = "BtnMouseOverUp";
  image.className = "Ico";

  event.cancelBubble = true;
  return false;
}

function PopulateTB(y)
{
  var i, elements, element;

  elements = y.children;
  for (i=0; i<elements.length; i++) {
    element = elements[i];
    if (element.tagName	== "SCRIPT" || element.tagName == "!") continue;

    switch (element.className) {
    case "Btn":
      if (element.YINITIALIZED == null)	{
		if (! InitBtn(element))
			return false;
      }

      element.style.posLeft = y.TBWidth;
      y.TBWidth	+= element.offsetWidth + 1;
      break;

    case "TBGen":
      element.style.posLeft = y.TBWidth;
      y.TBWidth	+= element.offsetWidth + 1;
      break;

    case "TBSep":
      element.style.posLeft = y.TBWidth	+ 2;
      y.TBWidth	+= SEP_PADDING;
      break;

    case "TBHandle":
      element.style.posLeft = 2;
      y.TBWidth	+= element.offsetWidth + HANDLE_PADDING;
      break;

    default:
      return false;
    }
  }

  y.TBWidth += 1;
  return true;
}

function DebugObject(obj)
{
  var msg = "";
  for (var i in	TB) {
    ans=prompt(i+"="+TB[i]+"\n");
    if (! ans) break;
  }
}

function LayoutTBs()
{
  NumTBs = yToolbars.length;

  if (NumTBs ==	0) return;

  var i;
  var ScrWid = (document.body.offsetWidth) - 6;
  var TotalLen = ScrWid;
  for (i = 0 ; i < NumTBs ; i++) {
    TB = yToolbars[i];
    if (TB.TBWidth > TotalLen) TotalLen	= TB.TBWidth;
  }

  var PrevTB;
  var LastStart	= 0;
  var RelTop = 0;
  var LastWid, CurrWid;

  var TB = yToolbars[0];
  TB.style.posTop = 0;
  TB.style.posLeft = 0;

  var Start = TB.TBWidth;
  for (i = 1 ; i < yToolbars.length ; i++) {
    PrevTB = TB;
    TB = yToolbars[i];
    CurrWid = TB.TBWidth;

    if ((Start + CurrWid) > ScrWid) {
      Start = 0;
      LastWid =	TotalLen - LastStart;
    }
    else {
      LastWid =	PrevTB.TBWidth;
      RelTop -=	TB.offsetHeight;
    }

    TB.style.posTop = RelTop;
    TB.style.posLeft = Start;
    PrevTB.style.width = LastWid;

    LastStart =	Start;
    Start += CurrWid;
  }

  TB.style.width = TotalLen - LastStart;

  i--;
  TB = yToolbars[i];
  var TBInd = TB.sourceIndex;
  var A	= TB.document.all;
  var item;
  for (i in A) {
    item = A.item(i);
    if (! item)	continue;
    if (! item.style) continue;
    if (item.sourceIndex <= TBInd) continue;
    if (item.style.position == "absolute") continue;
    item.style.posTop =	RelTop;
  }
}

function DoLayout()
{
  LayoutTBs();
}

function validateMode()
{
  if (!	bTextMode) return true;
  alert("请取消“查看HTML源代码”选项再使用系统编辑功能或者提交!");
  Composition.focus();
  return false;
}

function format1(what,opt)
{
  if (opt=="removeFormat")
  {
    what=opt;
    opt=null;
  }

  if (opt==null) Composition.document.execCommand(what);
  else Composition.document.execCommand(what,"",opt);

  pureText = false;
  Composition.focus();
}

function format(what,opt)
{
  if (!validateMode()) return;

  format1(what,opt);
}

function setMode(newMode)
{
  bTextMode = newMode;
  var cont;
  if (bTextMode) {
    cleanHtml();
    cleanHtml();

    cont=Composition.document.body.innerHTML;
    Composition.document.body.innerText=cont;
  } else {
    cont=Composition.document.body.innerText;
    Composition.document.body.innerHTML=cont;
  }

  Composition.focus();
}

function getEl(sTag,start)
{
  while	((start!=null) && (start.tagName!=sTag)) start = start.parentElement;
  return start;
}

function UserDialog(what)
{
  if (!validateMode()) return;

  Composition.document.execCommand(what, true);

  pureText = false;
  Composition.focus();
}

function foreColor()
{
  if (!	validateMode())	return;
  var arr = showModalDialog("selcolor.html", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0");
  if (arr != null) format('forecolor', arr);
  else Composition.focus();
}

function fortable()
{
  if (!	validateMode())	return;
  var arr = showModalDialog("table.html", "", "dialogWidth:13.5em; dialogHeight:10.5em; status:0");
  
  if (arr != null){
  var ss;
  ss=arr.split("*")
  row=ss[0];
  col=ss[1];
  var string;
  string="<table  border=1 width='100%' bordercolorlight=#000000 cellspacing='0' cellPadding=4 bordercolordark=#FFFFFF>";
  for(i=1;i<=row;i++){
  string=string+"<tr>";
  for(j=1;j<=col;j++){
  string=string+"<td align='center'>&nbsp;</td>";
  }
  string=string+"</tr>";
  }
  string=string+"</table>";
  content=Composition.document.body.innerHTML;
  content=content+string;
   Composition.document.body.innerHTML=content;
  }
  else Composition.focus();
}
function cleanHtml()
{
  var fonts = Composition.document.body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML	= curr.innerHTML;
  }
}

function getPureHtml()
{
  var str = "";
  var paras = Composition.document.body.all.tags("P");
  if (paras.length > 0)	{
    for	(var i=paras.length-1; i >= 0; i--) str	= paras[i].innerHTML + "\n" + str;
  } else {
    str	= Composition.document.body.innerHTML;
  }
  return str;
}

var bLoad=false
var pureText=true
var bodyTag="<head><style type=\"text/css\">body {font-size:	10.8pt}</style><meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"></head><BODY bgcolor=\"#FFFFFF\" MONOSPACE>"
var bTextMode=false

public_description=new Editor

function Editor()
{
  this.put_HtmlMode=setMode;
  this.put_value=putText;
  this.get_value=getText;
}

function getText()
{
	if (bTextMode)
		return Composition.document.body.innerText;
	else
	{
		cleanHtml();
		cleanHtml();
		return Composition.document.body.innerHTML;
	}
}

function putText(v)
{
	if (bTextMode)
		Composition.document.body.innerText = v;
	else
		Composition.document.body.innerHTML = v;
}

function InitDocument()
{
	Composition.document.open();
	Composition.document.write(bodyTag);
	Composition.document.close();
	bLoad=true;
}

function doSelectClick(str, el) {
	var Index = el.selectedIndex;
	if (Index != 0){
		el.selectedIndex = 0;
		if (el.id == "specialtype")
			specialtype(el.options[Index].value);
		else
			format(str,el.options[Index].value);
	}
}
var bIsIE5 = navigator.userAgent.indexOf("IE 5")  > -1;
var edit;
var RangeType;

function specialtype(Mark){
	var strHTML;
	if (bIsIE5) selectRange();	
	if (RangeType == "Text"){
		strHTML = "<" + Mark + ">" + edit.text + "</" + Mark + ">"; 
		edit.pasteHTML(strHTML);
		Composition.focus();
		edit.select();
	}		
}

function selectRange(){
	edit = Composition.document.selection.createRange();
	RangeType =  Composition.document.selection.type;
}

function rCode(s,a,b){
	var r = new RegExp(a,"gi");
	return 	s.replace(r,b); 
}


function lbcode(){
	if (!validateMode()) return;
	cont=getPureHtml(Composition.document.body.innerHTML);
	var aryCode0 = new Array("<strong>","[b]","</strong>","[/b]","<p","[p","</p>","[/p]","<a href=","[url=","</a>","[/url]");
	var aryCode1 = new Array("<em>","[i]","</em>","[/i]","<u>","[u]","</u>","[/u]","<ul>","[list]","</ul>","[/list]","<ol>","[list=1]","</ol>","[/list]");
	var aryCode2 = new Array("<li>","[*]","</li>","","<font color=","[color=","<font face=","[font=","<font size=","[size=");
	var aryCode9 = new Array(">","]","<","[","</","[/");
	var aryCode = aryCode0.concat(aryCode1).concat(aryCode2).concat(aryCode9);
	
	for (var i=0;i<aryCode.length;i+=2){
		cont=rCode(cont,aryCode[i],aryCode[i+1]);	
	}
	self.opener.FORM.inpost.value+=cont;
	self.close();
}

function help()
{
    var helpmess;
    helpmess="---------------填写帮助---------------\r\n\r\n"+
         "1.请不要发表有危险性的脚本。\r\n\r\n"+
         "2.如果要书写源代码，请选中\r\n\r\n"+
         "　查看HTML源代码书写.\r\n\r\n"+
         "3.需要你自己运行,才能看效果.\r\n\r\n"+
         "4.如果书写js，尽量不要在这儿书写.\r\n\r\n";
    alert(helpmess);

}
function media11()
{
//if (!validateMode()) return;
var string;
string="";
string=string+"<OBJECT id='MediaPlayer' style='VISIBILITY: visible' codeBase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701standby=Loading' type='application/x-oleobject' height='350' width='500' classid='CLSID:22D6F312-B0F6-11D0-94AB-0080C74C7E95' VIEWASTEXT>\n";
string=string+"							<PARAM NAME='AudioStream' VALUE='-1'>";
string=string+"							<PARAM NAME='AutoSize' VALUE='0'>";
string=string+"							<PARAM NAME='AutoStart' VALUE='-1'>";
string=string+"							<PARAM NAME='AnimationAtStart' VALUE='0'>";
string=string+"							<PARAM NAME='AllowScan' VALUE='-1'>";
string=string+"							<PARAM NAME='AllowChangeDisplaySize' VALUE='-1'>";
string=string+"							<PARAM NAME='AutoRewind' VALUE='0'>";
string=string+"							<PARAM NAME='Balance' VALUE='0'>";
string=string+"							<PARAM NAME='BaseURL' VALUE=''>";
string=string+"							<PARAM NAME='BufferingTime' VALUE='5'>";
string=string+"							<PARAM NAME='CaptioningID' VALUE=''>";
string=string+"							<PARAM NAME='ClickToPlay' VALUE='-1'>";
string=string+"							<PARAM NAME='CursorType' VALUE='0'>";
string=string+"							<PARAM NAME='CurrentPosition' VALUE='-1'>";
string=string+"							<PARAM NAME='CurrentMarker' VALUE='0'>";
string=string+"							<PARAM NAME='DefaultFrame' VALUE=''>";
string=string+"							<PARAM NAME='DisplayBackColor' VALUE='0'>";
string=string+"							<PARAM NAME='DisplayForeColor' VALUE='16777215'>";
string=string+"							<PARAM NAME='DisplayMode' VALUE='0'>";
string=string+"							<PARAM NAME='DisplaySize' VALUE='0'>";
string=string+"							<PARAM NAME='Enabled' VALUE='-1'>";
string=string+"							<PARAM NAME='EnableContextMenu' VALUE='-1'>";
string=string+"							<PARAM NAME='EnablePositionControls' VALUE='-1'>";
string=string+"							<PARAM NAME='EnableFullScreenControls' VALUE='0'>";
string=string+"							<PARAM NAME='EnableTracker' VALUE='-1'>";
string=string+"							<PARAM NAME='InvokeURLs' VALUE='-1'>";
string=string+"							<PARAM NAME='Language' VALUE='-1'>";
string=string+"							<PARAM NAME='Mute' VALUE='0'>";
string=string+"							<PARAM NAME='PlayCount' VALUE='0'>";
string=string+"							<PARAM NAME='PreviewMode' VALUE='0'>";
string=string+"							<PARAM NAME='Rate' VALUE='1'>";
string=string+"							<PARAM NAME='SAMILang' VALUE=''>";
string=string+"							<PARAM NAME='SAMIStyle' VALUE=''>";
string=string+"							<PARAM NAME='SAMIFileName' VALUE=''>";
string=string+"							<PARAM NAME='SelectionStart' VALUE='-1'>";
string=string+"							<PARAM NAME='SelectionEnd' VALUE='-1'>";
string=string+"							<PARAM NAME='SendOpenStateChangeEvents' VALUE='-1'>";
string=string+"							<PARAM NAME='SendWarningEvents' VALUE='-1'>";
string=string+"							<PARAM NAME='SendErrorEvents' VALUE='-1'>";
string=string+"							<PARAM NAME='SendKeyboardEvents' VALUE='0'>";
string=string+"							<PARAM NAME='SendMouseClickEvents' VALUE='0'>";
string=string+"							<PARAM NAME='SendMouseMoveEvents' VALUE='0'>";
string=string+"							<PARAM NAME='SendPlayStateChangeEvents' VALUE='-1'>";
string=string+"							<PARAM NAME='ShowCaptioning' VALUE='0'>";
string=string+"							<PARAM NAME='ShowControls' VALUE='0'>";
string=string+"							<PARAM NAME='ShowAudioControls' VALUE='-1'>";
string=string+"							<PARAM NAME='ShowDisplay' VALUE='0'>";
string=string+"							<PARAM NAME='ShowGotoBar' VALUE='0'>";
string=string+"							<PARAM NAME='ShowPositionControls' VALUE='0'>";
string=string+"							<PARAM NAME='ShowStatusBar' VALUE='0'>";
string=string+"							<PARAM NAME='ShowTracker' VALUE='0'>";
string=string+"							<PARAM NAME='TransparentAtStart' VALUE='0'>";
string=string+"							<PARAM NAME='VideoBorderWidth' VALUE='0'>";
string=string+"							<PARAM NAME='VideoBorderColor' VALUE='0'>";
string=string+"							<PARAM NAME='VideoBorder3D' VALUE='0'>";
string=string+"							<PARAM NAME='Volume' VALUE='0'>";
string=string+"							<PARAM NAME='WindowlessVideo' VALUE='0'>";
string=string+"							<PARAM NAME=filename value='http://www.ghbf.org/sound.mp3'>";
string=string+"					  </OBJECT>";
alert(string);
content=Composition.document.body.innerHTML;
  content=content+string;
   Composition.document.body.innerHTML=content;
}
